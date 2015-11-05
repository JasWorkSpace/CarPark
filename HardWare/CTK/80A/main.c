
/********************************************

*********************************************/
#include <pic.h>
#include "mypic.h"
#include "LM016.h"
#include "74HC595.h"

bit isSettingMode = 0; // 是否处于设置模式
bit isSaveSettings = 0;
bit rb4_flag = 0; // SET是否被长按的flag
bit is_second_setting; //设置模式中改变第一个值还是第二个值
bit needInitSetting; //需要初始化设置
uchar batteryType = 0; // 标识哪一种电池类型
uchar const *charPointer; //字符指针

void main()
{

	
	system_state_init();
	load_system_state(); // 恢复系统状态

	// 液晶屏初始化
	lcd_init();
	lcd_write_command(0x80);
	charPointer = welcome;
	lcd_print_line1(charPointer, 0, 0);
	charPointer = checking;
	lcd_print_line2(charPointer, 0);
	
	// lcd_write_command(0x0f);//闪烁

	TURN_OFF_Lcd_L;//关液晶屏背光

	KaiJi();//开机启动，确认上电电压，使用数组，电路自检等
	while(1)
	{
		asm("CLRWDT");		//在用户设置的时候应该也加入看门狗

		SelectMode();
		SystemModeType = OUTPUT_MODE;
		LoadCurrentDealWith();
		SwitchBatteryState();
		LedDisplay();
		if(PVCount > 50)                      //5s检测处理一次 50
		{
			PVCount = 0;
			SolarPanelDealWith();			   //根据太阳能电池板的电压，进行相应的处理，并获得当前太阳能电池板的状态 
		}
		if(PWMChargeFlag == 1)                 //调用PWM充电函数100ms 监测一次
		{
			PWMChargeFlag = 0;                 //定时器值还原
			PWMCharge();
		} 


		if (isNeedChange) {
			isNeedChange = 0;
			switch(lcd_state) {
			case 1:
				// todo:在这里检测系统的状态，并决定显示12V、24V或者系统错误
				charPointer = systemType1;
				lcd_print_line1(charPointer, 0 ,1);
				break;
			case 2:
				charPointer = state1_1;
				lcd_print_line1(charPointer, 0, 1);
				charPointer = state1_2;
				lcd_print_line2(charPointer, 0);
				break;
			// case 3:
			// 	charPointer = state2_1;
			// 	lcd_print_line1(charPointer, 0, 1);
			// 	charPointer = state2_2;
			// 	lcd_print_line2(charPointer, 0);
			// 	if (isSettingMode) {
			// 		if (is_second_setting) {
			// 			lcd_move_char(0x7 + 0x40);
			// 		} else {
			// 			lcd_move_char(0x7);
			// 		}
			// 	}
			// 	break;
			// case 4:
			// 	charPointer = state3_1;
			// 	lcd_print_line1(charPointer, 0, 1);
			// 	charPointer = state3_2;
			// 	lcd_print_line2(charPointer, 0);
			// 	if (isSettingMode) {
			// 		if (is_second_setting) {
			// 			lcd_move_char(0x7 + 0x40);
			// 		} else {
			// 			lcd_move_char(0x7);
			// 		}
			// 	}
			// 	break;
			// case 5:
			// 	charPointer = state4_1;
			// 	lcd_print_line1(charPointer, 0, 1);
			// 	charPointer = state4_2;
			// 	lcd_print_line2(charPointer, 0);
			// 	break;
			case 3:
				charPointer = state2_1;
				lcd_print_line1(charPointer, 0, 1);
				charPointer = batteryStatePointer[batteryType];
				lcd_print_line2(charPointer, 0);
				if (isSettingMode) {
					if (is_second_setting) {
						lcd_move_char(0x40);
					} else {
						// 这里为了统一逻辑，电池选项本来第一行没有设置的
						lcd_move_char(0x20);
					}
				}
				break;
			case 4:
				charPointer = state3_1;
				lcd_print_line1(charPointer, 0, 1);
				charPointer = state3_2;
				lcd_print_line2(charPointer, 0);
				if (isSettingMode) {
					if (is_second_setting) {
						lcd_move_char(0xe + 0x40);
					} else {
						lcd_move_char(0xe);
					}
				}
				break;
			case 5:
				charPointer = state4_1;
				lcd_print_line1(charPointer, 0, 1);
				charPointer = batteryStatePointer[1];
				lcd_print_line2(charPointer, 0);
				if (isSettingMode) {
					if (is_second_setting) {
						lcd_move_char(0xC + 0x40);
					} else {
						lcd_move_char(0xe);
					}
				}
				break;
			case 6:
				charPointer = state5_1;
				lcd_print_line1(charPointer, 0, 1);
				charPointer = state5_2;
				lcd_print_line2(charPointer, 0);
				if (isSettingMode) {
					if (is_second_setting) {
						lcd_move_char(0xd + 0x40);
					} else {
						lcd_move_char(0xe);
					}
				}
				break;
			case 7:
				charPointer = state6_1;
				lcd_print_line1(charPointer, 0, 1);
				charPointer = state6_2;
				lcd_print_line2(charPointer, 0);
				if (isSettingMode) {
					if (is_second_setting) {
						lcd_move_char(0xc + 0x40);
					} else {
						lcd_move_char(0xd);
					}
				}
				break;
			case 8:
				charPointer = state7_1;
				lcd_print_line1(charPointer, 0, 1);
				charPointer = state7_2;
				lcd_print_line2(charPointer, 0);
				if (isSettingMode) {
					if (is_second_setting) {
						lcd_move_char(0xe + 0x40);
					} else {
						lcd_move_char(0xc);
					}
				}
				break;
			default:
				break;
			}
			
		}

		if (lcd_extinguwish_timer == AUTO_EXTINGUWISH_TIME) {
			// 不需要一直更新状态
			lcd_extinguwish_timer++;
			// 清除屏幕
			lcd_write_command(0x08);
		}

		if (isSaveSettings) {
			isSaveSettings = 0;
			isNeedChange = 1;
			lcd_state = 2;
			isSettingMode = 0;
			// todo: 保存当前系统状态到EEPROM中
		}

		if (isSettingMode && needInitSetting) {
			needInitSetting = 0;
			isNeedChange = 1;
			lcd_state = 3;
			is_second_setting = 0;
		}
		
	}
}

void interrupt ISR_Timer()
{
	if(IOCBF3 && lcd_state != 0 && lcd_state != 1)//RB3外部中断，UP
	{	
		// 只响应下边沿中断
		if (RB3 == 0) {
			if (!isSettingMode) {
				lcd_extinguwish_timer = 0;
				if (lcd_state == 2) {
					lcd_state = 8;
				} else {
					lcd_state--;
				}
				isNeedChange = 1;	
			} else {
				//设置模式下
				setting_save_timer = 0;
				setting_no_save_timer = 0;
				// todo: 判断当前场景并修改当前的值
				switch (lcd_state) {
					case 3: // 电池选择,第二行才生效
						if (is_second_setting) {
							batteryType == 0 ? batteryType = 2 : batteryType--;
							isNeedChange = 1;
						}
						break;
					case 4: // 提升充电，均衡充电，此时就需要更改数值了，需要你计算之后写入
						break;
					case 5: // 浮充充电，充电时间
					case 6: // 欠压电压，欠压返回
					case 7: // 过压电压，过压回复
					case 8: // 白天、晚上识别电压
					default:
						break;
				}
			}
		}
		
		
		IOCBF3 = 0;//清除外部中断标志位，最后清除，防止期间又有中断出现
	}

	if(IOCBF5 && lcd_state != 0 && lcd_state != 1)//RB5外部中断, DOWN
	{
		if (RB5 == 0) {
			if (!isSettingMode) {
				lcd_extinguwish_timer = 0;
				if (lcd_state == 8) {
					lcd_state = 2;
				} else {
					lcd_state++;
				}
				isNeedChange = 1;
			} else {
				// 设置模式下
				setting_save_timer = 0;
				setting_no_save_timer = 0;
				// todo: 判断当前场景并修改当前的值，此处没有写全，需要补充
				switch (lcd_state) {
					case 3: // 电池选择
						if (is_second_setting) {
							batteryType == 2 ? batteryType = 0 : batteryType++;
							isNeedChange = 1;
						}
						break;
					case 4: // 提升充电，均衡充电，此时就需要更改数值了，需要你计算之后写入
						break;
					case 5: // 浮充充电，充电时间
					case 6: // 欠压电压，欠压返回
					case 7: // 过压电压，过压回复
					case 8: // 白天、晚上识别电压
					default:
						break;
				}
			}
		}
		
		
		IOCBF5 = 0;//清除外部中断标志位，最后清除，防止期间又有中断出现
	}

	if(IOCBF4)//RB4外部中断，SET
	{
		
		// todo：这里处理按键的响应，不能放到定时器处理时间函数
		// SET按下时
		if (RB4 == 0) {
			rb4_flag = 1;
			if (!isSettingMode) {
				lcd_extinguwish_timer = 0;
				isNeedChange = 1;
				enter_settings_timer = 0;
			} else{
				setting_save_timer = 0;
				setting_no_save_timer = 0;
				if (!is_second_setting) {
					is_second_setting = 1;
				} else {
					is_second_setting = 0;
					if (lcd_state == 8) {
						lcd_state = 3;
					} else {
						lcd_state ++;
					}
				}
				isNeedChange = 1;
			}
		}

		// SET抬起时
		if (RB4 == 1) {
			rb4_flag = 0;
		}

		IOCBF4 = 0;//清除外部中断标志位，最后清除，防止期间又有中断出现
	}


	if(TMR1IF)//用于较长时间的定时，必须使用16位定时器
	{
		if (lcd_extinguwish_timer < AUTO_EXTINGUWISH_TIME && !isSettingMode) {
				lcd_extinguwish_timer++;
			if (rb4_flag) {
				enter_settings_timer++;
				
				if (enter_settings_timer == ENTER_SETTINGS_TIME) {
					enter_settings_timer = 0;
					isSettingMode = 1;
					needInitSetting = 1;
				}
			} else {
				enter_settings_timer = 0;
			}
		} else if (setting_save_timer < SETTING_SAVE_TIME || setting_no_save_timer < SETTING_NO_SAVE_TIME && isSettingMode) {
			if (rb4_flag) {
				setting_save_timer++;
				setting_no_save_timer = 0;
				if (setting_save_timer == SETTING_SAVE_TIME) {
					isSettingMode = 0;
					isSaveSettings = 1;
				}
			} else {
				setting_save_timer = 0;
				setting_no_save_timer++;
				if (setting_no_save_timer == SETTING_NO_SAVE_TIME) {
					isSettingMode = 0;
					lcd_state = 2;
					isNeedChange = 1;
				}
			}
			
			
		}
		

		// 系统初始化的两个状态
		switch(lcd_state) {
			case 0:
				if (lcd_timer < CHECKING_DURATION_TIME) {
					lcd_timer++;
				} else {
					lcd_timer = 0;
					lcd_state++;
					isNeedChange = 1;
				}
				break;
			case 1:
				if (lcd_timer < SYSTEM_STATE_DISPLAY) {
					lcd_timer++;
				} else {
					lcd_timer = 0;
					lcd_state++;
					isNeedChange = 1;
				}
				break;	
			default:
				break;
		}
		TMR1IF = 0;								//定时器1的中断处理
		TMR1H = (65536 - (100000 / 4)) >> 8;
    	TMR1L = (65536 - (100000 / 4)) & 0xFF; //100ms中断一次

		PWMChargeFlag = 1;//100mS调用一次充电函数改变占空比，调用后清零

    	if(SystemModeType == TIME_MODE)		//时控模式下计时
    	{
    		TimeModeHour++;
    	}
		else
		{
			TimeModeHour = 0;//防止影响第二次进入
		}

    	if(EnhanceChargeFlag)             //提升充电
		{
			EnhanceCharge_Time++;   
		}
        if(EqualizingChargeFlag)          //均衡充电
		{
			EqualizingCharge_Time++;   
		}
        if(OverLoadTime != 0)				//负载5分钟翻转一次
		{
			OverLoadTime++;
		}
		if(SystemErrorCount)//如果系统错误了，开启计时，5分钟后自动重启
		{
			SystemErrorCount++;
		}
		PVCount++;//电池板5秒钟检测一次，用在定时器1中，=50的时候5秒

		if(LPVCount)//如果开启了低压检测计时
		{
			LPVCount++;		//低压检测计时
		}
	}
	if(TMR2IF)									//定时器2的中断处理 充电函数
    {
        TMR2IF = 0;								//清除timer2中断标志位
		if(PWMFlag == 1)
		{
	        PwmCount++;							//255自动重装

	        if(PwmCount >= DutyRatio)
	            TURN_OFF_PwmCtrl;
	        else
	            TURN_ON_PwmCtrl;
		}
	}
	if(TMR4IF)									//定时器4的中断处理 
    {
        TMR4IF = 0;								//清除timer4中断标志位
		RE2 = ~RE2;
	}
	if(TMR6IF)									//定时器6的中断处理
    {
        TMR6IF = 0;								//清除timer6中断标志位
	}
}