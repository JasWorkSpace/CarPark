
//add by jas
#ifndef _MYPIC_H_
#define _MYPIC_H_

#include <pic.h>
#include "LM016.h"
#include "public.h"
//end add by Jas


//���ؿ��ƺ�
#define TURN_ON_Led_C		(LATB |= (1 << 6))
#define TURN_OFF_Led_C		(LATB &= ~(1 << 6))

#define TURN_ON_Led_F		(LATB |= (1 << 7))
#define TURN_OFF_Led_F		(LATB &= ~(1 << 7))

#define TURN_ON_Lcd_L		(LATC &= ~(1 << 4))
#define TURN_OFF_Lcd_L		(LATC |= (1 << 4))

#define TURN_OFF_LoadCtrl	(PORTA |= (1 << 4))		//���ؿ���
#define TURN_ON_LoadCtrl	(PORTA &= ~(1 << 4))

#define TURN_OFF_PwmCtrl	(PORTA |= (1 << 0))		//PwmCtrl����
#define TURN_ON_PwmCtrl		(PORTA &= ~(1 << 0))

#define TURN_OFF_ChargeCtrl	(PORTB |= (1 << 0))		//ChargeCtrl����
#define TURN_ON_ChargeCtrl	(PORTB &= ~(1 << 0))

#define TURN_ON_OverCurrentSet	(PORTE |= (1 << 0))     //OverCurrentSet�øߣ��������
#define TURN_OFF_OverCurrentSet	(PORTE &= ~(1 << 0))	//OverCurrentSet���㣻����ʹ�ܹ���

#define Led_C		RB6
#define Led_F		RB7
#define IO_KEY_UP   RB3
#define IO_KEY_DOWN	RB5
#define IO_KEY_SET 	RB4



/*ϵͳģʽ����*/
#define OUTPUT_MODE                 0x01    //�̶����ģʽ
#define LIGHT_MODE                  0x02    //���ģʽ
#define MANUAL_MODE                 0x03    //��ť�����������
#define TEST_MODE                   0x04    //
#define TIME_MODE                   0X05    //���+ʱ��

/*ϵͳ״̬����*/
#define BATTERY_STATE_POWER_UP			0	//ϵͳ��һ���ϵ�
#define BATTERY_STATE_VOLTAGE_UNDER  	1	//����Ƿѹ
#define BATTERY_STATE_VOLTAGE_WARN   	2	//����Ƿѹ�澯
#define BATTERY_STATE_VOLTAGE_NORMAL_50 3	//���ص�ѹ����
#define BATTERY_STATE_VOLTAGE_NORMAL_75 4	//���ص�ѹ����
#define BATTERY_STATE_VOLTAGE_FULL   	5	//������
#define BATTERY_STATE_VOLTAGE_OVER   	6	//���ع�ѹ
#define BATTERY_STATE_ERROR          	7	//ϵͳ���� 

/*���帺�ؽ����־*/
#define LOAD_CONNECT			0		//�����Ѿ�����
#define LOAD_NOTCONNECT			1		//���ضϿ�

/*���ط�����ѹ��ֵ*/
#define LOAD_FB_VOLTAGE_NORMAL	134//125	//������ѹС�ڸ���ֵ��10A/20A��500mVȡֵ125��ʵ��134����30A��750mVȡֵ188
#define LOAD_FB_VOLTAGE_OVER	400			//������ѹ�����ϸ���ֵ��С�ڱ���ֵ�����ع��ء�10A/20A��600mVȡֵ150��30A��900mVȡֵ225
											//������ֱ�Ӵ���2V

#define CHARGE_TIME				36000		//ʹ�ö�ʱ��1�����ʱ�䣬100ms�жϣ����ʱ��Ϊ1Сʱ
#define OVER_LOAD_TIME			3000//		100//3000	//����Ͽ��ͽ�ͨ���ص�ʱ����(��ʱ������Ϊÿ100ms����һ���жϣ�5���ӵļ����Ҫ3000���ж�)

/*������ֵת���ɶ�����ȡǰ��λ */
#define BATTERY_VOLTAGE_UNDER           0   //
#define RETURN_VOLTAGE_UNDER            1   //
#define BATTERY_VOLTAGE_WARN            2   //
#define BATTERY_VOLTAGE_NORMAL          3   //
#define BATTERY_VOLTAGE_FULL            4   //
#define RETURN_VOLTAGE_FULL             5   //
#define Enhance_Charge_VOLTAGE          6   //
#define Equalizing_Charge_VOLTAGE       7   //
#define Floating_Charge_VOLTAGE         8   //
#define DAY_VOLTAGE                     9	//�����ҹ�ж���ֵ�����Կ��ص�DAY_VOLTAGE(7V) 62, NIGHT_VOLTAGE(3V) 26
#define NIGHT_VOLTAGE                   10
/*����������*/

uint *BatteryStandard = (void *)0; //����һ��ָ��ָ��ĳ����׼������
/*  ǰ�������ֱַ����
BATTERY_VOLTAGE_UNDER ��10.8��, RETURN_VOLTAGE_UNDER ��12.8��, BATTERY_VOLTAGE_WARN ��12.0��, BATTERY_VOLTAGE_NORMAL ��13.2��,
BATTERY_VOLTAGE_FULL ��16.5��, RETURN_VOLTAGE_FULL��15.0��

���������ֱַ����
Enhance_Charge_VOLTAGE ��14.8��, Equalizing_Charge_VOLTAGE ��14.4��, Floating_Charge_VOLTAGE��13.8��
�ٺ�����λ�ǰ����ҹ�ж���ֵ�����Կ��ص�

					  /*10.8  12.8  12    13.2  16.5  15*/
uint Battery_12V[]   = {256 , 303 , 284 , 313 , 391 , 355 , 351 , 341 , 327 , 3965 , 900};  //12Vϵͳ������ϵͳ�������
uint Battery_24V[]   = {512 , 606 , 568 , 626 , 782 , 710 , 702 , 682 , 654 , 8231 , 3333};  //24Vϵͳ������ϵͳ�������

uchar SystemErrorState = 0;
uchar SystemModeType = MANUAL_MODE;        //���浱ǰϵͳ������״̬
uchar EENum = 0;                            //����EEPROM�е�����
uchar BatteryState = BATTERY_STATE_POWER_UP; //����״̬Ĭ��Ϊ��һ���ϵ�״̬
uchar BatteryLastState = BATTERY_STATE_POWER_UP;

bit LoadOpen = 0;               //���������Ƿ���Դ�
bit LoadState = 0;              //Ϊ0���ز�������Ϊ1��������
bit LoadShort = 0;              //Ϊ0������Ϊ1���ض�·��
bit LoadFlag = 0;               //Ϊ0���ؽ��룬Ϊ1�Ͽ�
bit PVState = 0;                //Ϊ0���ܳ�磬Ϊ1���Գ��

bit EnhanceChargeFlag = 0;			//��������Ƿ���
bit EqualizingChargeFlag = 0;		//�������Ƿ���
bit FloatingChargeFlag = 0;			//�������Ƿ���
bit PWMChargeFlag = 0;				//time1�У�100ms�ж�һ�Σ�ChangePWMFlag��תһ�Σ�����һ��PWMռ�ձ�
bit PWMFlag = 0;					//Ϊһʱ���Բ���PWM
bit T2Flag = 0;						//����T0�Ƿ��
bit PVChargeFlag = 0;				//���ص�ѹ���߻���û�йⲻ����ˣ�LEDҪָʾ������

bit System12V = 0;		//Ĭ��12Vϵͳ
bit System24V = 0;

bit DAYTIME = 0;		//Ϊ1�ǰ��죻Ϊ0��ҹ��

uchar LightTime = 0;			//ʱ��ģʽ��ʱ��ʱ��
uint TimeModeHour = 0;			//������¼ʱ��ģʽ��һСʱʱ��
uint EnhanceCharge_Time = 0;        //������綨ʱ��־����ʱ������Ϊÿ100ms����һ���жϣ�
									//����Ҫ����36000���жϲ��ܼǹ�һ��Сʱ����Ҫע��EnhanceCharge_Time�����ͣ�

uint EqualizingCharge_Time = 0;     //�����綨ʱ��־
uint OverLoadTime = 0 ;             //���ع���ʱ���ŵ綨ʱ

uint SDSolarPanelVoltage = 0;		//ÿ��һ��ʱ��Ҫ�Լ�һ�γ���·�����������Լ��õĹ��ذ��ѹ
uint SDBatteryVoltage = 0;			//ÿ��һ��ʱ��Ҫ�Լ�һ�γ���·�����������Լ��õ����ذ��ѹ����ʱ��

uint gSolarPanelVoltage;    //������¼̫���ܵ�ذ��ѹ��ȫ�ֱ���
uint gBatteryVoltage;       //������¼��ص�ѹ��ȫ�ֱ���
uint gFbVoltage;
uint ClampVoltage = 0;		//ǯλ��ѹ
uint TemVoltage = 0;		//�¶Ȳ�����ѹ
uint TemBase = 0;			//�¶Ȳ�����ѹ���õĻ�����Ϊ���������ţ��ȼ�ȥ��ֵ���ټ����¶Ȳ�����ѹ
uint ADVoltage = 0;			//�Զ�У����ѹ
uint ADBase = 0;			//�Զ�У����ѹ���õĻ�����Ϊ���������ţ��ȼ�ȥ��ֵ���ټ����Զ�У����ѹ

uchar DutyRatio = 0;		//0-255����ֵԽ��ռ�ձ�Խ��
uchar PwmCount = 0;			//��ǰ����ֵ
uchar PVCount = 0;			//��ذ�5���Ӽ��һ�Σ����ڶ�ʱ��1�У�=50��ʱ��5��


uchar ErrorType = 0;		//���ڱ�����ִ����

uint SystemErrorCount = 0;	//ϵͳ�����5�����Զ�����

bit LPVFlag = 0;			//��ѹ����־
uchar LPVCount = 0;			//��ѹ����ʱ

uchar globalState = 0;

// �öδ�lm016.h�ƹ�������ʶLCD��״̬
uchar lcd_state = 0;
uchar lcd_setting_state = 0;
uchar lcd_timer = 0;
uchar lcd_extinguwish_timer = 0; // Ϩ��ʱ��
uchar enter_settings_timer = 0;  // ������������ģʽ
uchar setting_save_timer = 0; // ��������
uchar setting_no_save_timer = 0; // ��ʱ������
bit isNeedChange = 0;

///////////////////////////////////////////////////////
uchar ReadEE(uchar addr);
void WriteEE(uchar data, uchar addr);
uint getADValue(uchar channel);
///////////////////////////////////////////////////////////
void system_state_init();

///////////////////////////////////////////////////////
void load_system_state();
void save_system_state();

uint GetBatteryVoltage();
uint GetSolarPanelVoltage();
uint GetChargeCurrentVoltage();
uint GetLoadCurrentVoltage();
//EEPROMд�뺯����Ĭ��д���ַΪ�ռ��0��ַ

void SelectMode();

void LoadCurrentDealWith();

void SolarPanelDealWith();

void PWMCharge();

uchar BatteryStateSwitch(uint BatteryVoltage);

void SwitchBatteryState();

void KaiJi();

uchar GetBoradTemperature();
uchar GetOutTemperature();

void LedDisplay();

void saveToEEPROM(uint data, uchar type);
uint readFromEEPROM(uchar type);




//add by Jas
#endif
//end add by Jas