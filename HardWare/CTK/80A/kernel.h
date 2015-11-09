
#ifndef _KERNEL_H_
#define _KERNEL_H_

#include "public.h"

//////////////////////////////////////////////////
// EPPROM ADDR alloction, 

#define ADDR_EPPROM 0
#define ADDR_EPPROM_battery_type (ADDR_EPPROM +2)//�������
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
    saveToEEPROM(en_charge, 1);//(�������)
	saveToEEPROM(eq_charge, 2);//(������)
	saveToEEPROM(flo_charge, 3);//(������)
	saveToEEPROM(time_charge, 4);//(���ʱ�䣿)
	saveToEEPROM(under_vol, 5);//��Ƿѹ��ѹ��
	saveToEEPROM(re_under, 6);//(Ƿѹ����)
	saveToEEPROM(over_vol, 7);//(��ѹ��ѹ)
	saveToEEPROM(re_over, 8);//(��ѹ�ظ�)
	saveToEEPROM(day_vol, 9);//(����ʶ���ѹ)
	saveToEEPROM(night_vol, 10);//(ҹ��ʶ���ѹ)
*/
//////////////////////////////////////////////////////////
///// ���ò��� 
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
//// PWM ����

uchar  PWM_CONTROL_FLAG    = CLOSE;//�ر�PWM.  PWMʹ�ܿ���
uint32 PWM_CONTROL_TIMEOUT = CLOSE;//��ʱʱ��.0Ϊ�رն�ʱ.����ʱ��Ϊ��ʱʱ��.����Ϊϵͳ�Ļ�׼ʱ�侫��100ms
uchar  PWM_ON_TIME         = CLOSE;////ռ�ձ� 0-125���ɵ�.//��ʱ��2���жϴ���


///////////////////////////////////////////////////////////
//// AD ����

#define ADC_CHANNEL_BATTERY       0b00101001;    //RB1/AN10,ʹ��ADת��
#define ADC_CHANNEL_SOLARPANEL    0b00100001;    //RB2/AN8,ʹ��ADת��
#define ADC_CHANNEL_CHARGE        0b00010001;    //RA5/AN4,ʹ��ADת��
#define ADC_CHANNEL_LOAD          0b00011001;    //RE1/AN6,ʹ��ADת��

//////////////////////////////////////////////////////////////
//// IO ����

typedef struct _InputIOState{
	uchar  HWState;  //Ӳ��״̬
	uchar  enable;   //ʹ��
	uchar  swState;  //���״̬
	uint   ADValue;  //AD ����ֵ
	uint32 ADTime;   //AD ����ʱ��
};
struct _InputIOState BATTERY     = {ERROR, CLOSE, ERROR, ERROR, ERROR};
struct _InputIOState SOLARPANEL  = {ERROR, CLOSE, ERROR, ERROR, ERROR};
struct _InputIOState CHARGE      = {ERROR, CLOSE, ERROR, ERROR, ERROR};
struct _InputIOState LOAD        = {ERROR, CLOSE, ERROR, ERROR, ERROR};
//////////////////////////////////////////////////////////////////////////////
//// System ����
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
//12V ϵͳ
struct _SystemConfig SystemConfig12V = {SYSTEM_CONFIG_12V ,256 , 303 , 284 , 313 , 391 , 355 , 351 , 341 , 327 , 3965 , 900};
//24V ϵͳ
struct _SystemConfig SystemConfig24V = {SYSTEM_CONFIG_24V ,512 , 606 , 568 , 626 , 782 , 710 , 702 , 682 , 654 , 8231 , 3333};
//ϵͳָ��
struct _SystemConfig *SystemConfig;



#endif

