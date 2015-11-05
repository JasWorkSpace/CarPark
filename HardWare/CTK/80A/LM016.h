/*******����������********/
#define LCD_RS RC1
#define LCD_RW RC2
#define LCD_EN RC3
#define LCD_DATA PORTD

/***********����������**************/
uchar welcome[] = "WELCOME";
uchar checking[] = "CHECKING...";
uchar systemType1[] = "12V SYSTEM";
uchar systemType2[] = "24V SYSTEM";
uchar error[] = "ERROR";
//��Щ���ֲ�֪����ôȡ
uchar const state1_1[] = {'B', 'v', 'x', 'x', '.', 'x', 'v', ' ', 'S', 'o', 'c', 0xff, 0xff, 0xff, 0xff, '\0'};
uchar const state1_2[] = "lcxx.xA loxx.xA";//�滻�ֱ���2λ�ú�10λ��
// uchar const state2_1[] = "N_Qi: xxKWh"; //�滻����λ��6
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
// ����ģʽ�£��������Ӧ�����ĸ�λ����˸
uchar const flicker_position[] = {0x5, 0x5};

//������ʹ�ö�ʱ��1��ʱ�� 100msΪ��λ
#define CHECKING_DURATION_TIME 30
#define SYSTEM_STATE_DISPLAY 30
#define AUTO_EXTINGUWISH_TIME 70  // �Զ�Ϣ��ʱ��
#define ENTER_SETTINGS_TIME 50
#define SETTING_SAVE_TIME 50 //����SET�����ʱ��
#define SETTING_NO_SAVE_TIME 100 //����״̬�²������ʱ��

// �ö�������mypic.h��ʹ���ˣ������Ƶ����ļ���
// uchar lcd_state = 0;
// uchar lcd_setting_state = 0;
// uchar lcd_timer = 0;
// uchar lcd_extinguwish_timer = 0; // Ϩ��ʱ��
// uchar enter_settings_timer = 0;  // ������������ģʽ
// uchar setting_save_timer = 0; // ��������
// uchar setting_no_save_timer = 0; // ��ʱ������
// bit isNeedChange = 0;
// /***********������������*******/
// void lcd_init();
// void lcd_write_command(uchar);
// void lcd_write_data(uchar);
// void lcd_wait_for_busy();
// void lcd_print_line1(uchar const*, uchar, uchar);
// void lcd_print_line2(uchar const*, uchar);
// void lcd_move_char(uchar postion);
// void num_to_char(int, uchar)��

// �洢������ʾ�����֣���ʱ���������
char lcdNum4[5];


//Һ������ʼ��
void lcd_init() {
	lcd_write_command(0x38);//8bit 5*7char
	lcd_write_command(0x0c);//�޹��
	lcd_write_command(0x06);// ��ʾ����
	lcd_write_command(0x01);// ����Ļ
	delay(1000);
}

//��������
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

//д������
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

// ��֪Ϊ�μ��æ�����ã����ڴ���������ʱ,���������һ��
void lcd_wait_for_busy() {
	uchar readin = 0;
	do {
		LCD_RS = 0;
		LCD_RW = 1;
		TRISD = 0xFF; //portd��ʱΪ��
		LCD_EN = 1;
		readin = PORTD;
		LCD_EN = 0;
	}while(RD7 == 1);
	TRISD = 0x00;
}

// ����������ݣ���ʾ��һ��,����Ϊ�ַ���ָ�룬�ӵڼ���λ�ÿ�ʼ���Ƿ�����Ļ
void lcd_print_line1(uchar const *charPointer, uchar postion, uchar clear) {
	if (clear) {
		lcd_write_command(0x01);
		delay(1000);
	}
	lcd_write_command(0x0c);
	lcd_write_command(0x80 + postion); // �������
	while(*charPointer != '\0') {
		lcd_write_data(*charPointer);
		charPointer++;
	}
}

// ��ִ�д�ӡ��һ�еĻ����ϣ���ʾ�ڶ��У�����Ϊ�ַ���ָ�룬�ӵڼ���λ�ÿ�ʼ
void lcd_print_line2(uchar const *charPointer, uchar postion) {
	lcd_write_command(0xc0 + postion);
	lcd_write_command(0x0c);
	while(*charPointer != '\0') {
		lcd_write_data(*charPointer);
		charPointer++;
	}
}

// ������ƶ���λ��
void lcd_move_char(uchar postion) {
	uchar i = 0;
	lcd_write_command(0x0f); // ��ʾ��겢��˸
	lcd_write_command(0x02); // ����λ
	while(i < postion) {
		lcd_write_command(0x14); //�������һλ
		i++;
	}
	
}

// ������ת��Ϊ�ַ�����������ʾ��Ϊ�˷��㣬��ʹ��������
// ���磬��ʾ17.5��������175, point ��ʾλ�������֧��3λ
// 3λ���Ǵ�С�������һλ�ģ�2λ�ǲ���С�����
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