#include <stm32f10x.h>
#include "GPIO.h"
#define BIT(i) (1<<i)
//GPIO��ʼ��
void GPIOInit()
{
	GPIO_InitTypeDef typeStruct;

	//GPIOʱ��ʹ��
	RCC_APB2PeriphClockCmd(RCC_APB2Periph_GPIOB,ENABLE);
	RCC_APB2PeriphClockCmd(RCC_APB2Periph_GPIOE,ENABLE);
	RCC_APB2PeriphClockCmd(RCC_APB2Periph_GPIOD,ENABLE);
	RCC_APB2PeriphClockCmd(RCC_APB2Periph_GPIOF,ENABLE);
//	GPIO_DeInit();
//	GPIO_AFIODeInit();
	typeStruct.GPIO_Mode=GPIO_Mode_Out_PP;
	typeStruct.GPIO_Pin=BIT(5);
	typeStruct.GPIO_Speed=GPIO_Speed_50MHz;
	GPIO_Init(GPIOB,&typeStruct);

	typeStruct.GPIO_Mode=GPIO_Mode_Out_PP;
	typeStruct.GPIO_Pin=GPIO_Pin_8;
	typeStruct.GPIO_Speed=GPIO_Speed_50MHz;
	GPIO_Init(GPIOB,&typeStruct);
	//Init DO Port
	//PE8--PE15 ��Ӧ DO0--DO7
	typeStruct.GPIO_Mode=GPIO_Mode_Out_PP;
	typeStruct.GPIO_Pin = GPIO_Pin_8|GPIO_Pin_9|GPIO_Pin_10|GPIO_Pin_11|GPIO_Pin_12|GPIO_Pin_13|GPIO_Pin_14|GPIO_Pin_15;
	typeStruct.GPIO_Speed=GPIO_Speed_50MHz;
	GPIO_Init(GPIOE,&typeStruct);
	//PB10:DO8  PB11:DO9
	//change by Jas
	#if 1
	typeStruct.GPIO_Mode=GPIO_Mode_Out_PP;
	typeStruct.GPIO_Pin=GPIO_Pin_10;
	typeStruct.GPIO_Speed=GPIO_Speed_50MHz;
	GPIO_Init(GPIOB,&typeStruct);
	typeStruct.GPIO_Pin = GPIO_Pin_11;
	GPIO_Init(GPIOB,&typeStruct);
	#else
	typeStruct.GPIO_Pin = GPIO_Pin_10|GPIO_Pin_11;
	GPIO_Init(GPIOB,&typeStruct);
	#endif
	//PD9-PD14��ӦDO10--DO15
	typeStruct.GPIO_Pin = GPIO_Pin_9|GPIO_Pin_10|GPIO_Pin_11|GPIO_Pin_12|GPIO_Pin_13|GPIO_Pin_14;
	GPIO_Init(GPIOD,&typeStruct);
	//Init DI Port
	//DI0-DI4��ӦPE2--PE6
	typeStruct.GPIO_Mode=GPIO_Mode_IPU;
	typeStruct.GPIO_Pin=GPIO_Pin_2|GPIO_Pin_3|GPIO_Pin_4|GPIO_Pin_5|GPIO_Pin_6;
	typeStruct.GPIO_Speed=GPIO_Speed_50MHz;
	GPIO_Init(GPIOE,&typeStruct);
	//DI5--DI15��ӦPF0--PF10
	typeStruct.GPIO_Pin=GPIO_Pin_0|GPIO_Pin_1|GPIO_Pin_2|GPIO_Pin_3|GPIO_Pin_4|GPIO_Pin_5|
											GPIO_Pin_6|GPIO_Pin_7|GPIO_Pin_8|GPIO_Pin_9|GPIO_Pin_10;
	GPIO_Init(GPIOF,&typeStruct);
}
/*
�������������
���������channeln��ͨ����
					states����ͨ����״̬
					DOѡͨ�͵�ƽ��Ч
					states=1����ͨ���ĵ�ƽΪ��
					states=0����ͨ���ĵ�ƽΪ��
*/
void DOControl(unsigned char channeln,unsigned char states)
{
	if(states!=0)
	{
		switch(channeln)
		{
			case 0:
				GPIO_ResetBits(GPIOE,GPIO_Pin_8);
				break;
			case 1:
				GPIO_ResetBits(GPIOE,GPIO_Pin_9);
				break;
			case 2:
				GPIO_ResetBits(GPIOE,GPIO_Pin_10);
				break;
			case 3:
				GPIO_ResetBits(GPIOE,GPIO_Pin_11);
				break;
			case 4:
				GPIO_ResetBits(GPIOE,GPIO_Pin_12);
				break;
			case 5:
				GPIO_ResetBits(GPIOE,GPIO_Pin_13);
				break;
			case 6:
				GPIO_ResetBits(GPIOE,GPIO_Pin_14);
				break;
			case 7:
				GPIO_ResetBits(GPIOE,GPIO_Pin_15);
				break;
			case 8:
				GPIO_ResetBits(GPIOB,GPIO_Pin_10);
				break;
			case 9:
				GPIO_ResetBits(GPIOB,GPIO_Pin_11);
				break;
			case 10:
				GPIO_ResetBits(GPIOD,GPIO_Pin_9);
				break;
			case 11:
				GPIO_ResetBits(GPIOD,GPIO_Pin_10);
				break;
			case 12:
				GPIO_ResetBits(GPIOD,GPIO_Pin_11);
				break;
			case 13:
				GPIO_ResetBits(GPIOD,GPIO_Pin_12);
				break;
			case 14:
				GPIO_ResetBits(GPIOD,GPIO_Pin_13);
				break;
			case 15:
				GPIO_ResetBits(GPIOD,GPIO_Pin_14);
				break;
			default:
				break;
		}
	}
	else
	{
		switch(channeln)
		{
			case 0:
				GPIO_SetBits(GPIOE,GPIO_Pin_8);
				break;
			case 1:
				GPIO_SetBits(GPIOE,GPIO_Pin_9);
				break;
			case 2:
				GPIO_SetBits(GPIOE,GPIO_Pin_10);
				break;
			case 3:
				GPIO_SetBits(GPIOE,GPIO_Pin_11);
				break;
			case 4:
				GPIO_SetBits(GPIOE,GPIO_Pin_12);
				break;
			case 5:
				GPIO_SetBits(GPIOE,GPIO_Pin_13);
				break;
			case 6:
				GPIO_SetBits(GPIOE,GPIO_Pin_14);
				break;
			case 7:
				GPIO_SetBits(GPIOE,GPIO_Pin_15);
				break;
			case 8:
				GPIO_SetBits(GPIOB,GPIO_Pin_10);
				break;
			case 9:
				GPIO_SetBits(GPIOB,GPIO_Pin_11);
				break;
			case 10:
				GPIO_SetBits(GPIOD,GPIO_Pin_9);
				break;
			case 11:
				GPIO_SetBits(GPIOD,GPIO_Pin_10);
				break;
			case 12:
				GPIO_SetBits(GPIOD,GPIO_Pin_11);
				break;
			case 13:
				GPIO_SetBits(GPIOD,GPIO_Pin_12);
				break;
			case 14:
				GPIO_SetBits(GPIOD,GPIO_Pin_13);
				break;
			case 15:
				GPIO_SetBits(GPIOD,GPIO_Pin_14);
				break;
			default:
				break;
		}
	}
}
/*����������
	���������channeln��Ҫ��ȡ��DIͨ����No
	����ֵ�� ���ظ�ͨ����״̬
	���ⲿ���رպ�ʱ��Ӧ��MCU�ĵ�ƽʱ��
	���ضϿ�ʱ��Ӧ�ĵ�ƽʱ��
*/
unsigned char ReadDIState(unsigned char channeln)
{
	switch(channeln)
	{
		case 0:
			return GPIO_ReadInputDataBit(GPIOE,GPIO_Pin_2);
			break;
		case 1:
			return GPIO_ReadInputDataBit(GPIOE,GPIO_Pin_3);
			break;
		case 2:
			return GPIO_ReadInputDataBit(GPIOE,GPIO_Pin_4);
			break;
		case 3:
			return GPIO_ReadInputDataBit(GPIOE,GPIO_Pin_5);
			break;
		case 4:
			return GPIO_ReadInputDataBit(GPIOE,GPIO_Pin_6);
			break;
		case 5:
			return GPIO_ReadInputDataBit(GPIOF,GPIO_Pin_0);
			break;
		case 6:
			return GPIO_ReadInputDataBit(GPIOF,GPIO_Pin_1);
			break;
		case 7:
			return GPIO_ReadInputDataBit(GPIOF,GPIO_Pin_2);
			break;
		case 8:
			return GPIO_ReadInputDataBit(GPIOF,GPIO_Pin_3);
			break;
		case 9:
			return GPIO_ReadInputDataBit(GPIOF,GPIO_Pin_4);
			break;
		case 10:
			return GPIO_ReadInputDataBit(GPIOF,GPIO_Pin_5);
			break;
		case 11:
			return GPIO_ReadInputDataBit(GPIOF,GPIO_Pin_6);
			break;
		case 12:
			return GPIO_ReadInputDataBit(GPIOF,GPIO_Pin_7);
			break;
		case 13:
			return GPIO_ReadInputDataBit(GPIOF,GPIO_Pin_8);
			break;
		case 14:
			return GPIO_ReadInputDataBit(GPIOF,GPIO_Pin_9);
			break;
		case 15:
			return GPIO_ReadInputDataBit(GPIOF,GPIO_Pin_10);
			break;
		default:
			return 0xff;
			break;
	}
}