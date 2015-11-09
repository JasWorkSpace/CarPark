
#ifndef _KERNEL_H_
#define _KERNEL_H_

#include "public.h"

//////////////////////////////////////////////////
// EPPROM ADDR alloction, 

#define ADDR_EPPROM 0
#define ADDR_EPPROM_battery_type (ADDR_EPPROM +2)//电池类型
#define ADDR_EPPROM_en_charge    (ADDR_EPPROM_battery_type +2)
#define ADDR_EPPROM_eq_charge    (ADDR_EPPROM_en_charge +2)
#define ADDR_EPPROM_flo_charge   (ADDR_EPPROM_eq_charge +2)
#define ADDR_EPPROM_time_change  (ADDR_EPPROM_flo_charge +2)
#define ADDR_EPPROM_under_vol    (ADDR_EPPROM_time_change +2)
#define ADDR_EPPROM_re_under     (ADDR_EPPROM_under_vol +2)
#define ADDR_EPPROM_over_vol     (ADDR_EPPROM_re_under +2)
#define ADDR_EPPROM_re_over      (ADDR_EPPROM_over_vol +2)
#define ADDR_EPPROM_day_vol      (ADDR_EPPROM_re_over +2)
#define ADDR_EPPROM_night_vol    (ADDR_EPPROM_day_vol +2)


/*
    saveToEEPROM(en_charge, 1);//(提升充电)
	saveToEEPROM(eq_charge, 2);//(均衡充电)
	saveToEEPROM(flo_charge, 3);//(浮充充电)
	saveToEEPROM(time_charge, 4);//(充电时间？)
	saveToEEPROM(under_vol, 5);//（欠压电压）
	saveToEEPROM(re_under, 6);//(欠压返回)
	saveToEEPROM(over_vol, 7);//(过压电压)
	saveToEEPROM(re_over, 8);//(过压回复)
	saveToEEPROM(day_vol, 9);//(白天识别电压)
	saveToEEPROM(night_vol, 10);//(夜晚识别电压)
*/
//////////////////////////////////////////////////////////
///// 配置参数 
void LoadParamFromEPPROM();

void SaveParamToEPPROM();
//default value point in ParamConfig here
#define Param_battery_type 0
#define Param_en_charge    1
#define Param_eq_charge    2
#define Param_flo_charge   3
#define Param_time_change  4
#define Param_under_vol    5
#define Param_re_under     6
#define Param_over_vol     7
#define Param_re_over      8
#define Param_day_vol      9
#define Param_night_vol    10

uint ParamConfig[11] = {0,0,0,0,0,0,0,0,0,0,0};

#define HW_BATTERY_TYPE_GEL  0
#define HW_BATTERY_TYPE_OPEN 1
#define HW_BATTERY_TYPE_SELF 2


///////////////////////////////////////////////////////////
//// PWM 配置

uchar  PWM_CONTROL_FLAG    = CLOSE;//关闭PWM.  PWM使能开关
uint32 PWM_CONTROL_TIMEOUT = CLOSE;//定时时间.0为关闭定时.其余时间为定时时长.精度为系统的基准时间精度100ms
uchar  PWM_ON_TIME         = CLOSE;////占空比 0-125级可调.//定时器2的中断处理


///////////////////////////////////////////////////////////
//// AD 配置

#define ADC_CHANNEL_BATTERY       0b00101001;    //RB1/AN10,使能AD转换
#define ADC_CHANNEL_SOLARPANEL    0b00100001;    //RB2/AN8,使能AD转换
#define ADC_CHANNEL_CHARGE        0b00010001;    //RA5/AN4,使能AD转换
#define ADC_CHANNEL_LOAD          0b00011001;    //RE1/AN6,使能AD转换

//////////////////////////////////////////////////////////////
//// IO 配置

typedef struct _InputIOState{
	uchar  HWState;  //硬件状态
	uchar  enable;   //使能
	uchar  swState;  //软件状态
	uint   ADValue;  //AD 采样值
	uint32 ADTime;   //AD 采样时间
};
struct _InputIOState BATTERY     = {ERROR, CLOSE, ERROR, ERROR, ERROR};
struct _InputIOState SOLARPANEL  = {ERROR, CLOSE, ERROR, ERROR, ERROR};
struct _InputIOState CHARGE      = {ERROR, CLOSE, ERROR, ERROR, ERROR};
struct _InputIOState LOAD        = {ERROR, CLOSE, ERROR, ERROR, ERROR};
//////////////////////////////////////////////////////////////////////////////
//// System 配置
#define SYSTEM_CONFIG_12V 1
#define SYSTEM_CONFIG_24V 2

typedef struct _SystemConfig{
	uchar Param_config;
	uint  Param_BATTERY_VOLTAGE_UNDER;
	uint  Param_RETURN_VOLTAGE_UNDER;
	uint  Param_BATTERY_VOLTAGE_WARN;
	uint  Param_BATTERY_VOLTAGE_NORMAL;
	uint  Param_BATTERY_VOLTAGE_FULL;
	uint  Param_RETURN_VOLTAGE_FULL;
	uint  Param_Enhance_Charge_VOLTAGE;
	uint  Param_Equalizing_Charge_VOLTAGE;
	uint  Param_Floating_Charge_VOLTAGE;
	uint  Param_DAY_VOLTAGE;
	uint  Param_NIGHT_VOLTAGE;
};
//12V 系统
struct _SystemConfig SystemConfig12V = {SYSTEM_CONFIG_12V ,256 , 303 , 284 , 313 , 391 , 355 , 351 , 341 , 327 , 3965 , 900};
//24V 系统
struct _SystemConfig SystemConfig24V = {SYSTEM_CONFIG_24V ,512 , 606 , 568 , 626 , 782 , 710 , 702 , 682 , 654 , 8231 , 3333};
//系统指针
struct _SystemConfig *SystemConfig;



#endif

