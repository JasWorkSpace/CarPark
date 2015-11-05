
/********************************************

*********************************************/
#include <pic.h>
#include "mypic.h"
#include "LM016.h"
#include "74HC595.h"

bit isSettingMode = 0; // �Ƿ�������ģʽ
bit isSaveSettings = 0;
bit rb4_flag = 0; // SET�Ƿ񱻳�����flag
bit is_second_setting; //����ģʽ�иı��һ��ֵ���ǵڶ���ֵ
bit needInitSetting; //��Ҫ��ʼ������
uchar batteryType = 0; // ��ʶ��һ�ֵ������
uchar const *charPointer; //�ַ�ָ��

void main()
{

	
	system_state_init();
	load_system_state(); // �ָ�ϵͳ״̬

	// Һ������ʼ��
	lcd_init();
	lcd_write_command(0x80);
	charPointer = welcome;
	lcd_print_line1(charPointer, 0, 0);
	charPointer = checking;
	lcd_print_line2(charPointer, 0);
	
	// lcd_write_command(0x0f);//��˸

	TURN_OFF_Lcd_L;//��Һ��������

	KaiJi();//����������ȷ���ϵ��ѹ��ʹ�����飬��·�Լ��
	while(1)
	{
		asm("CLRWDT");		//���û����õ�ʱ��Ӧ��Ҳ���뿴�Ź�

		SelectMode();
		SystemModeType = OUTPUT_MODE;
		LoadCurrentDealWith();
		SwitchBatteryState();
		LedDisplay();
		if(PVCount > 50)                      //5s��⴦��һ�� 50
		{
			PVCount = 0;
			SolarPanelDealWith();			   //����̫���ܵ�ذ�ĵ�ѹ��������Ӧ�Ĵ�������õ�ǰ̫���ܵ�ذ��״̬ 
		}
		if(PWMChargeFlag == 1)                 //����PWM��纯��100ms ���һ��
		{
			PWMChargeFlag = 0;                 //��ʱ��ֵ��ԭ
			PWMCharge();
		} 


		if (isNeedChange) {
			isNeedChange = 0;
			switch(lcd_state) {
			case 1:
				// todo:��������ϵͳ��״̬����������ʾ12V��24V����ϵͳ����
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
						// ����Ϊ��ͳһ�߼������ѡ�����һ��û�����õ�
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
			// ����Ҫһֱ����״̬
			lcd_extinguwish_timer++;
			// �����Ļ
			lcd_write_command(0x08);
		}

		if (isSaveSettings) {
			isSaveSettings = 0;
			isNeedChange = 1;
			lcd_state = 2;
			isSettingMode = 0;
			// todo: ���浱ǰϵͳ״̬��EEPROM��
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
	if(IOCBF3 && lcd_state != 0 && lcd_state != 1)//RB3�ⲿ�жϣ�UP
	{	
		// ֻ��Ӧ�±����ж�
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
				//����ģʽ��
				setting_save_timer = 0;
				setting_no_save_timer = 0;
				// todo: �жϵ�ǰ�������޸ĵ�ǰ��ֵ
				switch (lcd_state) {
					case 3: // ���ѡ��,�ڶ��в���Ч
						if (is_second_setting) {
							batteryType == 0 ? batteryType = 2 : batteryType--;
							isNeedChange = 1;
						}
						break;
					case 4: // ������磬�����磬��ʱ����Ҫ������ֵ�ˣ���Ҫ�����֮��д��
						break;
					case 5: // �����磬���ʱ��
					case 6: // Ƿѹ��ѹ��Ƿѹ����
					case 7: // ��ѹ��ѹ����ѹ�ظ�
					case 8: // ���졢����ʶ���ѹ
					default:
						break;
				}
			}
		}
		
		
		IOCBF3 = 0;//����ⲿ�жϱ�־λ������������ֹ�ڼ������жϳ���
	}

	if(IOCBF5 && lcd_state != 0 && lcd_state != 1)//RB5�ⲿ�ж�, DOWN
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
				// ����ģʽ��
				setting_save_timer = 0;
				setting_no_save_timer = 0;
				// todo: �жϵ�ǰ�������޸ĵ�ǰ��ֵ���˴�û��дȫ����Ҫ����
				switch (lcd_state) {
					case 3: // ���ѡ��
						if (is_second_setting) {
							batteryType == 2 ? batteryType = 0 : batteryType++;
							isNeedChange = 1;
						}
						break;
					case 4: // ������磬�����磬��ʱ����Ҫ������ֵ�ˣ���Ҫ�����֮��д��
						break;
					case 5: // �����磬���ʱ��
					case 6: // Ƿѹ��ѹ��Ƿѹ����
					case 7: // ��ѹ��ѹ����ѹ�ظ�
					case 8: // ���졢����ʶ���ѹ
					default:
						break;
				}
			}
		}
		
		
		IOCBF5 = 0;//����ⲿ�жϱ�־λ������������ֹ�ڼ������жϳ���
	}

	if(IOCBF4)//RB4�ⲿ�жϣ�SET
	{
		
		// todo�����ﴦ��������Ӧ�����ܷŵ���ʱ������ʱ�亯��
		// SET����ʱ
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

		// SEŢ��ʱ
		if (RB4 == 1) {
			rb4_flag = 0;
		}

		IOCBF4 = 0;//����ⲿ�жϱ�־λ������������ֹ�ڼ������жϳ���
	}


	if(TMR1IF)//���ڽϳ�ʱ��Ķ�ʱ������ʹ��16λ��ʱ��
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
		

		// ϵͳ��ʼ��������״̬
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
		TMR1IF = 0;								//��ʱ��1���жϴ���
		TMR1H = (65536 - (100000 / 4)) >> 8;
    	TMR1L = (65536 - (100000 / 4)) & 0xFF; //100ms�ж�һ��

		PWMChargeFlag = 1;//100mS����һ�γ�纯���ı�ռ�ձȣ����ú�����

    	if(SystemModeType == TIME_MODE)		//ʱ��ģʽ�¼�ʱ
    	{
    		TimeModeHour++;
    	}
		else
		{
			TimeModeHour = 0;//��ֹӰ��ڶ��ν���
		}

    	if(EnhanceChargeFlag)             //�������
		{
			EnhanceCharge_Time++;   
		}
        if(EqualizingChargeFlag)          //������
		{
			EqualizingCharge_Time++;   
		}
        if(OverLoadTime != 0)				//����5���ӷ�תһ��
		{
			OverLoadTime++;
		}
		if(SystemErrorCount)//���ϵͳ�����ˣ�������ʱ��5���Ӻ��Զ�����
		{
			SystemErrorCount++;
		}
		PVCount++;//��ذ�5���Ӽ��һ�Σ����ڶ�ʱ��1�У�=50��ʱ��5��

		if(LPVCount)//��������˵�ѹ����ʱ
		{
			LPVCount++;		//��ѹ����ʱ
		}
	}
	if(TMR2IF)									//��ʱ��2���жϴ��� ��纯��
    {
        TMR2IF = 0;								//���timer2�жϱ�־λ
		if(PWMFlag == 1)
		{
	        PwmCount++;							//255�Զ���װ

	        if(PwmCount >= DutyRatio)
	            TURN_OFF_PwmCtrl;
	        else
	            TURN_ON_PwmCtrl;
		}
	}
	if(TMR4IF)									//��ʱ��4���жϴ��� 
    {
        TMR4IF = 0;								//���timer4�жϱ�־λ
		RE2 = ~RE2;
	}
	if(TMR6IF)									//��ʱ��6���жϴ���
    {
        TMR6IF = 0;								//���timer6�жϱ�־λ
	}
}