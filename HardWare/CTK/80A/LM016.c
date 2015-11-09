

#include "LM016.h"
#include <pic.h>


/*******����������********/
#define LCD_RS   RC1
#define LCD_RW   RC2
#define LCD_EN   RC3
#define LCD_DATA PORTD

//��������
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

//д������
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

// ��֪Ϊ�μ��æ�����ã����ڴ���������ʱ,���������һ��
void lcd_wait_for_busy(void){
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
/////////////////////////////////////
void API_LCD_INIT(void){
	lcd_write_command(0x38);//8bit 5*7char
	lcd_write_command(0x0c);//�޹��
	lcd_write_command(0x06);// ��ʾ����
	lcd_write_command(0x01);// ����Ļ
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
		lcd_write_command(0x80 + postion); // �������
	}else if(linenumber == LCD_LINENUMBER_2){
		lcd_write_command(0xc0 + postion);
		lcd_write_command(0x0c);
	}
	API_LCD_PRINTString(charPointer);
}
void API_LCD_MOVE_POINT(uchar position){
	uchar i = 0;
	lcd_write_command(0x0f); // ��ʾ��겢��˸
	lcd_write_command(0x02); // ����λ
	while(i++ < position) {
		lcd_write_command(0x14); //�������һλ
	}
}
// ����������ݣ���ʾ��һ��,����Ϊ�ַ���ָ�룬�ӵڼ���λ�ÿ�ʼ���Ƿ�����Ļ
void lcd_print_line1(uchar *charPointer, uchar postion, uchar clear) {
	if(clear)API_LCD_CLEAR();
	API_LCD_PRINT_LINE(LCD_LINENUMBER_1, charPointer, postion);
}

// ��ִ�д�ӡ��һ�еĻ����ϣ���ʾ�ڶ��У�����Ϊ�ַ���ָ�룬�ӵڼ���λ�ÿ�ʼ
void lcd_print_line2(uchar *charPointer, uchar postion) {
	API_LCD_PRINT_LINE(LCD_LINENUMBER_2, charPointer, postion);
}
// ������ƶ���λ��
void lcd_move_char(uchar postion){
	API_LCD_MOVE_POINT(postion);
}

// ������ת��Ϊ�ַ�����������ʾ��Ϊ�˷��㣬��ʹ��������
// ���磬��ʾ17.5��������175, point ��ʾλ�������֧��3λ
// 3λ���Ǵ�С�������һλ�ģ�2λ�ǲ���С�����
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


