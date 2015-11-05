/*******常亮声明区********/
#define LCD_RS RC1
#define LCD_RW RC2
#define LCD_EN RC3
#define LCD_DATA PORTD

/***********变量声明区**************/
uchar welcome[] = "WELCOME";
uchar checking[] = "CHECKING...";
uchar systemType1[] = "12V SYSTEM";
uchar systemType2[] = "24V SYSTEM";
uchar error[] = "ERROR";
//这些名字不知道怎么取
uchar const state1_1[] = {'B', 'v', 'x', 'x', '.', 'x', 'v', ' ', 'S', 'o', 'c', 0xff, 0xff, 0xff, 0xff, '\0'};
uchar const state1_2[] = "lcxx.xA loxx.xA";//替换分别在2位置和10位置
// uchar const state2_1[] = "N_Qi: xxKWh"; //替换数据位置6
// uchar const state2_2[] = "N_QO: XXKWh";
// uchar const state3_1[] = "L_Qi: xxKWh";
// uchar const state3_2[] = "L_Qo: xxKWh";
// uchar const state4_1[] = {'T', 'e', 'm', 'p', '1', ':', 'x', 'x', 0xdf, 'C', '\0'};
// uchar const state4_2[] = {'T', 'e', 'm', 'p', '2', ':', 'x', 'x', 0xdf, 'C', '\0'};
uchar const state2_1[] = "Battery type";
uchar const state2_2[] = "Gel battery";
uchar const state2_3[] = "Open cell";
uchar const state2_4[] = "Self setting";
uchar const state3_1[] = "En_Charge: xx.xV";
uchar const state3_2[] = "Eq_Charge: xx.xV";
uchar const state4_1[] = "Flo_charge:xx.xV";
uchar const state4_2[] = "Time_Charge: 2H";
uchar const state5_1[] = "UNDER VOL: xx.xV";
uchar const state5_2[] = "RE UNDER: xx.xV";
uchar const state6_1[] = "OVER VOL: XX.XV";
uchar const state6_2[] = "RE OVER: xx.xV";
uchar const state7_1[] = "DAY VOL: xx.xV";
uchar const state7_2[] = "NIGHT VOL: xx.xv";

uchar *batteryStatePointer[] = {state2_2, state2_3, state2_4};
// 设置模式下，光标依次应该在哪个位置闪烁
uchar const flicker_position[] = {0x5, 0x5};

//以下是使用定时器1的时间 100ms为单位
#define CHECKING_DURATION_TIME 30
#define SYSTEM_STATE_DISPLAY 30
#define AUTO_EXTINGUWISH_TIME 70  // 自动息屏时间
#define ENTER_SETTINGS_TIME 50
#define SETTING_SAVE_TIME 50 //长按SET保存的时间
#define SETTING_NO_SAVE_TIME 100 //设置状态下不保存的时间

// 该段由于在mypic.h中使用了，所以移到该文件中
// uchar lcd_state = 0;
// uchar lcd_setting_state = 0;
// uchar lcd_timer = 0;
// uchar lcd_extinguwish_timer = 0; // 熄灭定时器
// uchar enter_settings_timer = 0;  // 长按键入设置模式
// uchar setting_save_timer = 0; // 长按保存
// uchar setting_no_save_timer = 0; // 超时不保存
// bit isNeedChange = 0;
// /***********函数声明部分*******/
// void lcd_init();
// void lcd_write_command(uchar);
// void lcd_write_data(uchar);
// void lcd_wait_for_busy();
// void lcd_print_line1(uchar const*, uchar, uchar);
// void lcd_print_line2(uchar const*, uchar);
// void lcd_move_char(uchar postion);
// void num_to_char(int, uchar)；

// 存储所需显示的数字，临时存放在这里
char lcdNum4[5];


//液晶屏初始化
void lcd_init() {
	lcd_write_command(0x38);//8bit 5*7char
	lcd_write_command(0x0c);//无光标
	lcd_write_command(0x06);// 显示递增
	lcd_write_command(0x01);// 清屏幕
	delay(1000);
}

//接收命令
void lcd_write_command(uchar command) {
	LCD_RS = 0;
	LCD_RW = 0;
	LCD_DATA = command;
	// lcd_wait_for_busy();
	// LCD_RS = 0;
	// LCD_RW = 0;
	delay(50);
	LCD_EN = 1;
	LCD_EN = 0;
}

//写入数据
void lcd_write_data(uchar data) {
	LCD_RS = 1;
	LCD_RW = 0;
	LCD_DATA = data;
	// lcd_wait_for_busy();
	// LCD_RS = 1;
	// LCD_RW = 0;
	delay(30);
	LCD_EN = 1;
	LCD_EN = 0;
}

// 不知为何检测忙不可用，先在代码中用延时,真机可以试一下
void lcd_wait_for_busy() {
	uchar readin = 0;
	do {
		LCD_RS = 0;
		LCD_RW = 1;
		TRISD = 0xFF; //portd此时为读
		LCD_EN = 1;
		readin = PORTD;
		LCD_EN = 0;
	}while(RD7 == 1);
	TRISD = 0x00;
}

// 清除所有内容，显示第一行,参数为字符串指针，从第几个位置开始，是否清屏幕
void lcd_print_line1(uchar const *charPointer, uchar postion, uchar clear) {
	if (clear) {
		lcd_write_command(0x01);
		delay(1000);
	}
	lcd_write_command(0x0c);
	lcd_write_command(0x80 + postion); // 光标右移
	while(*charPointer != '\0') {
		lcd_write_data(*charPointer);
		charPointer++;
	}
}

// 在执行打印第一行的基础上，显示第二行，参数为字符串指针，从第几个位置开始
void lcd_print_line2(uchar const *charPointer, uchar postion) {
	lcd_write_command(0xc0 + postion);
	lcd_write_command(0x0c);
	while(*charPointer != '\0') {
		lcd_write_data(*charPointer);
		charPointer++;
	}
}

// 将光标移动的位置
void lcd_move_char(uchar postion) {
	uchar i = 0;
	lcd_write_command(0x0f); // 显示光标并闪烁
	lcd_write_command(0x02); // 光标归位
	while(i < postion) {
		lcd_write_command(0x14); //光标右移一位
		i++;
	}
	
}

// 将数字转换为字符串，用于显示，为了方便，请使用整数，
// 例如，显示17.5，就输入175, point 表示位数，最多支持3位
// 3位就是带小数点后面一位的，2位是不带小数点的
void num_to_char(int num, uchar point) {

	uchar tmp, t = 0;

	switch(point) {
		case 1:
			lcdNum4[0] = '0' + num;
			break;
		case 2:
			tmp = num / 10;
			lcdNum4[0] = '0' + tmp;
			tmp = num % 10;
			lcdNum4[1] = '0' + tmp;
			lcdNum4[2] = '\0';
			break;
		case 3:
			tmp = num / 100;
			if (tmp != 0) {
				lcdNum4[t] = '0' + tmp;
				t++;
			}
			
			tmp = (num / 10) % 10;
			lcdNum4[t] = '0' + tmp;
			t++;

			lcdNum4[t] = '.';
			t++;

			tmp = num % 10;
			lcdNum4[t] = '0' + tmp;
			t++;

			lcdNum4[t] = '\0';
			break;
		default:
			break;
	}		
}