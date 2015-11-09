
//add by jas
#ifndef _MYPIC_H_
#define _MYPIC_H_

#include <pic.h>
#include "LM016.h"
#include "public.h"
//end add by Jas


//开关控制宏
#define TURN_ON_Led_C		(LATB |= (1 << 6))
#define TURN_OFF_Led_C		(LATB &= ~(1 << 6))

#define TURN_ON_Led_F		(LATB |= (1 << 7))
#define TURN_OFF_Led_F		(LATB &= ~(1 << 7))

#define TURN_ON_Lcd_L		(LATC &= ~(1 << 4))
#define TURN_OFF_Lcd_L		(LATC |= (1 << 4))

#define TURN_OFF_LoadCtrl	(PORTA |= (1 << 4))		//负载开关
#define TURN_ON_LoadCtrl	(PORTA &= ~(1 << 4))

#define TURN_OFF_PwmCtrl	(PORTA |= (1 << 0))		//PwmCtrl开关
#define TURN_ON_PwmCtrl		(PORTA &= ~(1 << 0))

#define TURN_OFF_ChargeCtrl	(PORTB |= (1 << 0))		//ChargeCtrl开关
#define TURN_ON_ChargeCtrl	(PORTB &= ~(1 << 0))

#define TURN_ON_OverCurrentSet	(PORTE |= (1 << 0))     //OverCurrentSet置高；清除过流
#define TURN_OFF_OverCurrentSet	(PORTE &= ~(1 << 0))	//OverCurrentSet清零；可以使能过流

#define Led_C		RB6
#define Led_F		RB7
#define IO_KEY_UP   RB3
#define IO_KEY_DOWN	RB5
#define IO_KEY_SET 	RB4



/*系统模式定义*/
#define OUTPUT_MODE                 0x01    //固定输出模式
#define LIGHT_MODE                  0x02    //光控模式
#define MANUAL_MODE                 0x03    //按钮用来控制输出
#define TEST_MODE                   0x04    //
#define TIME_MODE                   0X05    //光控+时控

/*系统状态定义*/
#define BATTERY_STATE_POWER_UP			0	//系统第一次上电
#define BATTERY_STATE_VOLTAGE_UNDER  	1	//蓄电池欠压
#define BATTERY_STATE_VOLTAGE_WARN   	2	//蓄电池欠压告警
#define BATTERY_STATE_VOLTAGE_NORMAL_50 3	//蓄电池电压正常
#define BATTERY_STATE_VOLTAGE_NORMAL_75 4	//蓄电池电压正常
#define BATTERY_STATE_VOLTAGE_FULL   	5	//蓄电池满
#define BATTERY_STATE_VOLTAGE_OVER   	6	//蓄电池过压
#define BATTERY_STATE_ERROR          	7	//系统错误 

/*定义负载接入标志*/
#define LOAD_CONNECT			0		//负载已经接入
#define LOAD_NOTCONNECT			1		//负载断开

/*负载反馈电压阈值*/
#define LOAD_FB_VOLTAGE_NORMAL	134//125	//反馈电压小于该阈值，10A/20A：500mV取值125（实际134）；30A：750mV取值188
#define LOAD_FB_VOLTAGE_OVER	400			//反馈电压大于上个阈值，小于本阈值，则负载过载。10A/20A：600mV取值150；30A：900mV取值225
											//触发后直接大于2V

#define CHARGE_TIME				36000		//使用定时器1，充电时间，100ms中断，充电时间为1小时
#define OVER_LOAD_TIME			3000//		100//3000	//定义断开和接通负载的时间间隔(定时器设置为每100ms进行一次中断，5分钟的间隔需要3000次中断)

/*数组阈值转化成二进制取前八位 */
#define BATTERY_VOLTAGE_UNDER           0   //
#define RETURN_VOLTAGE_UNDER            1   //
#define BATTERY_VOLTAGE_WARN            2   //
#define BATTERY_VOLTAGE_NORMAL          3   //
#define BATTERY_VOLTAGE_FULL            4   //
#define RETURN_VOLTAGE_FULL             5   //
#define Enhance_Charge_VOLTAGE          6   //
#define Equalizing_Charge_VOLTAGE       7   //
#define Floating_Charge_VOLTAGE         8   //
#define DAY_VOLTAGE                     9	//白天黑夜判定阈值，可以开关灯DAY_VOLTAGE(7V) 62, NIGHT_VOLTAGE(3V) 26
#define NIGHT_VOLTAGE                   10
/*变量定义区*/

uint *BatteryStandard = (void *)0; //定义一个指针指向某个标准的数组
/*  前六个数字分别代表
BATTERY_VOLTAGE_UNDER （10.8）, RETURN_VOLTAGE_UNDER （12.8）, BATTERY_VOLTAGE_WARN （12.0）, BATTERY_VOLTAGE_NORMAL （13.2）,
BATTERY_VOLTAGE_FULL （16.5）, RETURN_VOLTAGE_FULL（15.0）

后三个数字分别代表
Enhance_Charge_VOLTAGE （14.8）, Equalizing_Charge_VOLTAGE （14.4）, Floating_Charge_VOLTAGE（13.8）
再后面两位是白天黑夜判定阈值，可以开关灯

					  /*10.8  12.8  12    13.2  16.5  15*/
uint Battery_12V[]   = {256 , 303 , 284 , 313 , 391 , 355 , 351 , 341 , 327 , 3965 , 900};  //12V系统，其他系统自行添加
uint Battery_24V[]   = {512 , 606 , 568 , 626 , 782 , 710 , 702 , 682 , 654 , 8231 , 3333};  //24V系统，其他系统自行添加

uchar SystemErrorState = 0;
uchar SystemModeType = MANUAL_MODE;        //保存当前系统所处的状态
uchar EENum = 0;                            //保存EEPROM中的数字
uchar BatteryState = BATTERY_STATE_POWER_UP; //蓄电池状态默认为第一次上电状态
uchar BatteryLastState = BATTERY_STATE_POWER_UP;

bit LoadOpen = 0;               //决定负载是否可以打开
bit LoadState = 0;              //为0负载不正常，为1负载正常
bit LoadShort = 0;              //为0正常，为1负载短路过
bit LoadFlag = 0;               //为0负载接入，为1断开
bit PVState = 0;                //为0不能充电，为1可以充电

bit EnhanceChargeFlag = 0;			//提升充电是否开启
bit EqualizingChargeFlag = 0;		//均衡充电是否开启
bit FloatingChargeFlag = 0;			//浮充充电是否开启
bit PWMChargeFlag = 0;				//time1中，100ms中断一次，ChangePWMFlag翻转一次，更改一次PWM占空比
bit PWMFlag = 0;					//为一时可以产生PWM
bit T2Flag = 0;						//监视T0是否打开
bit PVChargeFlag = 0;				//因电池电压过高或者没有光不充电了，LED要指示电量了

bit System12V = 0;		//默认12V系统
bit System24V = 0;

bit DAYTIME = 0;		//为1是白天；为0是夜晚

uchar LightTime = 0;			//时控模式中时控时间
uint TimeModeHour = 0;			//用来记录时控模式的一小时时间
uint EnhanceCharge_Time = 0;        //提升充电定时标志（定时器设置为每100ms进行一次中断，
									//则需要进行36000次中断才能记够一个小时，需要注意EnhanceCharge_Time的类型）

uint EqualizingCharge_Time = 0;     //均衡充电定时标志
uint OverLoadTime = 0 ;             //负载过载时，放电定时

uint SDSolarPanelVoltage = 0;		//每隔一段时间要自检一次充电电路，用来保存自检用的光电池板电压
uint SDBatteryVoltage = 0;			//每隔一段时间要自检一次充电电路，用来保存自检用的蓄电池板电压，当时的

uint gSolarPanelVoltage;    //用来记录太阳能电池板电压的全局变量
uint gBatteryVoltage;       //用来记录电池电压的全局变量
uint gFbVoltage;
uint ClampVoltage = 0;		//钳位电压
uint TemVoltage = 0;		//温度补偿电压
uint TemBase = 0;			//温度补偿电压所用的基础，为了消除负号，先减去该值，再加上温度补偿电压
uint ADVoltage = 0;			//自动校正电压
uint ADBase = 0;			//自动校正电压所用的基础，为了消除负号，先减去该值，再加上自动校正电压

uchar DutyRatio = 0;		//0-255，数值越大占空比越大
uchar PwmCount = 0;			//当前计数值
uchar PVCount = 0;			//电池板5秒钟检测一次，用在定时器1中，=50的时候5秒


uchar ErrorType = 0;		//用于标记哪种错误的

uint SystemErrorCount = 0;	//系统错误后，5分钟自动重启

bit LPVFlag = 0;			//低压检测标志
uchar LPVCount = 0;			//低压检测计时

uchar globalState = 0;

// 该段从lm016.h移过来，标识LCD的状态
uchar lcd_state = 0;
uchar lcd_setting_state = 0;
uchar lcd_timer = 0;
uchar lcd_extinguwish_timer = 0; // 熄灭定时器
uchar enter_settings_timer = 0;  // 长按键入设置模式
uchar setting_save_timer = 0; // 长按保存
uchar setting_no_save_timer = 0; // 超时不保存
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
//EEPROM写入函数，默认写入地址为空间的0地址

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