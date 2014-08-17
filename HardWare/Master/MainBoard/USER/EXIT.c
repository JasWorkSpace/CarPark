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
	NVIC_InitStructure.NVIC_IRQChannel = EXTI0_IRQn;     //选择中断通道2
	NVIC_InitStructure.NVIC_IRQChannelPreemptionPriority = 1; //抢占式中断优先级设置为0
	NVIC_InitStructure.NVIC_IRQChannelSubPriority = 0;        //响应式中断优先级设置为0
	NVIC_InitStructure.NVIC_IRQChannelCmd = ENABLE;                                   //使能中断
	NVIC_Init(&NVIC_InitStructure);
	//Exit Interrupt port config
	GPIO_InitStructure.GPIO_Pin = GPIO_Pin_0|GPIO_Pin_1|GPIO_Pin_2 | GPIO_Pin_3 |
								  GPIO_Pin_4|GPIO_Pin_5|GPIO_Pin_6|GPIO_Pin_7; 
	GPIO_InitStructure.GPIO_Mode = GPIO_Mode_IN_FLOATING; //选择输入模式为浮空输入
	GPIO_InitStructure.GPIO_Speed = GPIO_Speed_50MHz;          //输出频率最大50MHz
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
//外部中断 0-7 用来测量脉宽
void EXTI0_IRQHandler(void)
{
	u32 timeCountBuf1;
	u32 pulseWidth0,pulseWidth1;
	//点亮LED灯
	if(GPIO_ReadOutputDataBit(GPIOD,GPIO_Pin_12))
	{
		GPIO_ResetBits(GPIOD,GPIO_Pin_12);
	}
	else
	{
		GPIO_SetBits(GPIOD,GPIO_Pin_12);
	}
	//计算速度 计算低电平的宽度
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
	//数据处理
	//如果测速结束
	if(speedDetectStructBuf[0].indexNo==2)
	{
		//如果数据正常 计算速度
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
		else	//如果数据不正常 将第二个数据放到第一个；再次收到的数据作为第二个数据
		{
			speedDetectStructBuf[0].risingEdgeCount[0]=speedDetectStructBuf[0].risingEdgeCount[1];
			speedDetectStructBuf[0].fallingEdgeCount[0]=speedDetectStructBuf[0].fallingEdgeCount[1];
			speedDetectStructBuf[0].indexNo=1;
		}
	}
	//清空中断标志位，防止持续进入中断
	EXTI_ClearITPendingBit(EXTI_Line0);
}
