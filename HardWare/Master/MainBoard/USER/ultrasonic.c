#include "stm32f10x.h"
#include "ultrasonic.h"
#include"delay.h"

/********************************************************************************************************************************************
@f_name: void Ultran_Init(void)
@brief:	 ������Ӳ��ģ���ʼ��
@param:	 None
@return: None
*********************************************************************************************************************************************/
void Ultran_Init(void)
{
	GPIO_InitTypeDef GPIO_InitStructure;
 	RCC_APB2PeriphClockCmd(ULTRA_CLK, ENABLE);

	GPIO_InitStructure.GPIO_Pin =ULTRA_TRIG;       //���͵�ƽ����
 	GPIO_InitStructure.GPIO_Speed = GPIO_Speed_50MHz;
 	GPIO_InitStructure.GPIO_Mode = GPIO_Mode_Out_PP;//�������
 	GPIO_Init(ULTRA_PORT, &GPIO_InitStructure);
	GPIO_ResetBits(ULTRA_PORT,ULTRA_TRIG);

	GPIO_InitStructure.GPIO_Pin =ULTRA_ECHO;		//���ص�ƽ����
 	GPIO_InitStructure.GPIO_Speed = GPIO_Speed_50MHz;
 	GPIO_InitStructure.GPIO_Mode = GPIO_Mode_IN_FLOATING;//��������
 	GPIO_Init(ULTRA_PORT, &GPIO_InitStructure);
}
/********************************************************************************************************************************************
@f_name: void Ranging(void)
@brief:	 ��������࣬��ֵ5�Σ�ʵ�� �������̷��ֲ�����̬�ıȽ��ȶ�����̬�Ĳ��ȶ���3�׷�Χ�ڱȽ��ȶ�
@param:	 float *p:��໺�����
@return: None
*********************************************************************************************************************************************/
int Ultra_Ranging(float *p)
{
	u8 i;
	u32 j;
	u32 Count = 0;
	int Time  = 0;
	float Ultr_Temp;
	for(i=0;i<5;i++)
	{
		GPIO_SetBits(ULTRA_PORT,ULTRA_TRIG);
		delay_ms(10);
		GPIO_ResetBits(ULTRA_PORT,ULTRA_TRIG);
		Count = 1000;
		j=0;
		while((Count-->0)&&!GPIO_ReadInputDataBit(ULTRA_PORT,ULTRA_ECHO));
		if(Count<0)continue;
		while(GPIO_ReadInputDataBit(ULTRA_PORT,ULTRA_ECHO)){
			delay_us(10);
			j++;
		}
		Ultr_Temp+=340/2*j*10;//  ģ�����ɲ��3m
		Time++;
		delay_ms(60);//��ֹ�����źŶԻ����źŵ�Ӱ��
	}
	if(Time>0)*p=Ultr_Temp/Time/1000000;
	return Time;
}
