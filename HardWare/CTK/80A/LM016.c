

#include "LM016.h"
#include <pic.h>


/*******常亮声明区********/
#define LCD_RS   RC1
#define LCD_RW   RC2
#define LCD_EN   RC3
#define LCD_DATA PORTD

//接收命令
void lcd_write_command(uchar command){
	LCD_RS = 0;
	LCD_RW = 0;
	LCD_DATA = command;
	// lcd_wait_for_busy();
	// LCD_RS = 0;
	// LCD_RW = 0;
	CTKSoftDelay(50);
	LCD_EN = 1;
	LCD_EN = 0;
}

//写入数据
void lcd_write_data(uchar data){
	LCD_RS = 1;
	LCD_RW = 0;
	LCD_DATA = data;
	// lcd_wait_for_busy();
	// LCD_RS = 1;
	// LCD_RW = 0;
	CTKSoftDelay(30);
	LCD_EN = 1;
	LCD_EN = 0;
}

// 不知为何检测忙不可用，先在代码中用延时,真机可以试一下
void lcd_wait_for_busy(void){
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
/////////////////////////////////////
void API_LCD_INIT(void){
	lcd_write_command(0x38);//8bit 5*7char
	lcd_write_command(0x0c);//无光标
	lcd_write_command(0x06);// 显示递增
	lcd_write_command(0x01);// 清屏幕
	CTKSoftDelay(1000);
}
void API_LCD_CLEAR(void){
	lcd_write_command(0x01);
	CTKSoftDelay(1000);
}
void API_LCD_PRINTString(uchar *charPointer){
	while(*charPointer != 0) {
		lcd_write_data(*charPointer);
		charPointer++;
	}
}
void API_LCD_PRINT_LINE(uchar linenumber, uchar *charPointer, uchar postion){
	if(linenumber == LCD_LINENUMBER_1){
		lcd_write_command(0x0c);
		lcd_write_command(0x80 + postion); // 光标右移
	}else if(linenumber == LCD_LINENUMBER_2){
		lcd_write_command(0xc0 + postion);
		lcd_write_command(0x0c);
	}
	API_LCD_PRINTString(charPointer);
}
void API_LCD_MOVE_POINT(uchar position){
	uchar i = 0;
	lcd_write_command(0x0f); // 显示光标并闪烁
	lcd_write_command(0x02); // 光标归位
	while(i++ < position) {
		lcd_write_command(0x14); //光标右移一位
	}
}
// 清除所有内容，显示第一行,参数为字符串指针，从第几个位置开始，是否清屏幕
void lcd_print_line1(uchar *charPointer, uchar postion, uchar clear) {
	if(clear)API_LCD_CLEAR();
	API_LCD_PRINT_LINE(LCD_LINENUMBER_1, charPointer, postion);
}

// 在执行打印第一行的基础上，显示第二行，参数为字符串指针，从第几个位置开始
void lcd_print_line2(uchar *charPointer, uchar postion) {
	API_LCD_PRINT_LINE(LCD_LINENUMBER_2, charPointer, postion);
}
// 将光标移动的位置
void lcd_move_char(uchar postion){
	API_LCD_MOVE_POINT(postion);
}

// 将数字转换为字符串，用于显示，为了方便，请使用整数，
// 例如，显示17.5，就输入175, point 表示位数，最多支持3位
// 3位就是带小数点后面一位的，2位是不带小数点的
void num_to_char(int num, uchar point) {

	uchar tmp, t = 0;
	//add by Jas
	uchar lcdNum4[4] = {0,0,0,0};
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


