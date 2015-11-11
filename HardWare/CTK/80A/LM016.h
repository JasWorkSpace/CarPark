
//add by jas
#ifndef _LM016_H_
#define _LM016_H_

#include "public.h"
//end add by Jas

#define LCD_LINENUMBER_1 0
#define LCD_LINENUMBER_2 1

#define LCD_LINENUMBER_MAXCHAR 16//15个字符

/***********变量声明区**************/
/*uchar welcome[] = "WELCOME";
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
*/
//uchar *batteryStatePointer[] = {state2_2, state2_3, state2_4};
// 设置模式下，光标依次应该在哪个位置闪烁
//uchar flicker_position[] = {0x5, 0x5};

//以下是使用定时器1的时间 100ms为单位
/*
#define CHECKING_DURATION_TIME 30
#define SYSTEM_STATE_DISPLAY   30
#define AUTO_EXTINGUWISH_TIME  70  // 自动息屏时间
#define AUTO_EXTINGUWISH_TIME  70  // 自动息屏时间
#define ENTER_SETTINGS_TIME    50
#define SETTING_SAVE_TIME      50 //长按SET保存的时间
#define SETTING_NO_SAVE_TIME   100 //设置状态下不保存的时间
*/

void API_LCD_INIT(void);
void API_LCD_CLEAR(void);
void API_LCD_PRINT_LINE(uchar linenumber,uchar * charPointer,uchar postion);
void lcd_write_command(uchar command);
void lcd_write_data(uchar data);
void lcd_print_line1(uchar  *charPaint, uchar postion, uchar clear);
void lcd_print_line2(uchar  *charPaint, uchar clear);
void lcd_move_char(uchar postion);
void num_to_char(int number, uchar point); 
//add by Jas
#endif
//end add by Jas