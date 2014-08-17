#include "stm32f10x.h"
#include "exit.h"
SpeedDetectStruct speedDetectStructBuf[8];
extern u32 sysTimeCounter;
void InitExit()
{
	GPIO_InitTypeDef GPIO_InitStructure;
	EXTI_InitTypeDef InitStruct;
	//NVIC Config
	NVIC_InitTypeDef NVIC_InitStructure; 
	NVIC_InitStructure.NVIC_IRQChannel = EXTI0_IRQn;     //ѡ���ж�ͨ��2
	NVIC_InitStructure.NVIC_IRQChannelPreemptionPriority = 1; //��ռʽ�ж����ȼ�����Ϊ0
	NVIC_InitStructure.NVIC_IRQChannelSubPriority = 0;        //��Ӧʽ�ж����ȼ�����Ϊ0
	NVIC_InitStructure.NVIC_IRQChannelCmd = ENABLE;                                   //ʹ���ж�
	NVIC_Init(&NVIC_InitStructure);
	//Exit Interrupt port config
	GPIO_InitStructure.GPIO_Pin = GPIO_Pin_0|GPIO_Pin_1|GPIO_Pin_2 | GPIO_Pin_3 |
								  GPIO_Pin_4|GPIO_Pin_5|GPIO_Pin_6|GPIO_Pin_7; 
	GPIO_InitStructure.GPIO_Mode = GPIO_Mode_IN_FLOATING; //ѡ������ģʽΪ��������
	GPIO_InitStructure.GPIO_Speed = GPIO_Speed_50MHz;          //���Ƶ�����50MHz
	GPIO_Init(GPIOA,&GPIO_InitStructure);                                 


	
	InitStruct.EXTI_Line=EXTI_Line0|EXTI_Line1 |EXTI_Line2 |EXTI_Line3 |EXTI_Line4 |EXTI_Line5 |EXTI_Line6 |EXTI_Line7 ;
	InitStruct.EXTI_Mode=EXTI_Mode_Interrupt;
	InitStruct.EXTI_Trigger=EXTI_Trigger_Rising_Falling;
	InitStruct.EXTI_LineCmd=ENABLE;
	EXTI_Init(&InitStruct);

	GPIO_EXTILineConfig(GPIO_PortSourceGPIOA,GPIO_PinSource0);
	GPIO_EXTILineConfig(GPIO_PortSourceGPIOA,GPIO_PinSource1);
	GPIO_EXTILineConfig(GPIO_PortSourceGPIOA,GPIO_PinSource2);
	GPIO_EXTILineConfig(GPIO_PortSourceGPIOA,GPIO_PinSource3);
	GPIO_EXTILineConfig(GPIO_PortSourceGPIOA,GPIO_PinSource4);
	GPIO_EXTILineConfig(GPIO_PortSourceGPIOA,GPIO_PinSource5);
	GPIO_EXTILineConfig(GPIO_PortSourceGPIOA,GPIO_PinSource6);
	GPIO_EXTILineConfig(GPIO_PortSourceGPIOA,GPIO_PinSource7);
}
//�ⲿ�ж� 0-7 ������������
void EXTI0_IRQHandler(void)
{
	u32 timeCountBuf1;
	u32 pulseWidth0,pulseWidth1;
	//����LED��
	if(GPIO_ReadOutputDataBit(GPIOD,GPIO_Pin_12))
	{
		GPIO_ResetBits(GPIOD,GPIO_Pin_12);
	}
	else
	{
		GPIO_SetBits(GPIOD,GPIO_Pin_12);
	}
	//�����ٶ� ����͵�ƽ�Ŀ��
	if(GPIO_ReadInputDataBit(GPIOA,GPIO_Pin_0)==0)
	{
		if (speedDetectStructBuf[0].indexNo<2)
		{
			speedDetectStructBuf[0].fallingEdgeCount[speedDetectStructBuf[0].indexNo]=sysTimeCounter;
			//speedDetectStructBuf[0].indexNo++;
		}
		else
		{
			speedDetectStructBuf[0].indexNo=0;
		}
	}
	else
	{
		if (speedDetectStructBuf[0].indexNo<2)
		{
			speedDetectStructBuf[0].risingEdgeCount[speedDetectStructBuf[0].indexNo]=sysTimeCounter;
			speedDetectStructBuf[0].indexNo++;
		}
		else
		{
			speedDetectStructBuf[0].indexNo=0;
		}
	}
	//���ݴ���
	//������ٽ���
	if(speedDetectStructBuf[0].indexNo==2)
	{
		//����������� �����ٶ�
		timeCountBuf1=speedDetectStructBuf[0].fallingEdgeCount[1]-speedDetectStructBuf[0].risingEdgeCount[0];
		pulseWidth0=speedDetectStructBuf[0].risingEdgeCount[0]-speedDetectStructBuf[0].fallingEdgeCount[0];
		pulseWidth1=speedDetectStructBuf[0].risingEdgeCount[1]-speedDetectStructBuf[0].fallingEdgeCount[1];
		if (timeCountBuf1<0)
		{
			timeCountBuf1=timeCountBuf1+0xffffffff;
		}
		if (pulseWidth0<0)
		{
			pulseWidth0=pulseWidth0+0xffffffff;
		}
		if (pulseWidth1<0)
		{
			pulseWidth1=pulseWidth1+0xffffffff;
		}
		if(timeCountBuf1<2000000)
		{
			speedDetectStructBuf[0].timeCount=timeCountBuf1;
			if (pulseWidth0>pulseWidth1)
			{
				speedDetectStructBuf[0].direction=1;
			}
			else
			{
				speedDetectStructBuf[0].direction=0;
			}
			speedDetectStructBuf[0].indexNo=0;
		}
		else	//������ݲ����� ���ڶ������ݷŵ���һ�����ٴ��յ���������Ϊ�ڶ�������
		{
			speedDetectStructBuf[0].risingEdgeCount[0]=speedDetectStructBuf[0].risingEdgeCount[1];
			speedDetectStructBuf[0].fallingEdgeCount[0]=speedDetectStructBuf[0].fallingEdgeCount[1];
			speedDetectStructBuf[0].indexNo=1;
		}
	}
	//����жϱ�־λ����ֹ���������ж�
	EXTI_ClearITPendingBit(EXTI_Line0);
}
