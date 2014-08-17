/**********************************************************************************
 * 工程名  ：泥人W5200模块－服务端模式例程
 * 描述    ：W5200的端口0工作在服务端模式,则等待泥人提供的《TCP&UDP测试工具》上创建的客户端主动与服务端连接,
 *			 连接成功后，服务端以500ms的时间间隔定时给客户端发送字符串"\r\nWelcome To NiRenElec!\r\n",
 *			 同时将接收到客户端发来的数据回发给客户端。
 * 实验平台：NiRen_TwoHeart系统板(或用户STM32开发板) + NiRen_W5200以太网(TCP/IP)模块
 * 硬件连接：  PC5 -> W5200_RST   
 *             PC4 -> W5200_INT     
 *             PA4 -> W5200_SCS      
 *             PA5 -> W5200_SCK    
 *             PA6 -> W5200_MISO    
 *             PA7 -> W5200_MOSI    
 * 库版本  ：ST_v3.5
 * 作者    ：泥人通信模块开发团队
 * 博客    ：http://nirenelec.blog.163.com
 * 淘宝    ：http://nirenelec.taobao.com
***************************************************************************************/

/*例程网络参数*/
//网关：192.168.1.1
//掩码:	255.255.255.0
//物理地址：0C 29 AB 7C 00 01
//本机IP地址:192.168.1.199
//端口0的端口号：5000

#include "stm32f10x.h"		

#include "sys.h"
#include "W5200.h"			
#include <string.h>
#include "littlec.h"
#include "usart.h"
#include "time.h"
#include "exit.h"


u32 sysTimeCounter;
extern SpeedDetectStruct speedDetectStructBuf[8];

SpeedStruct speedBuf[8];
#define  RECEIVE_BUF_MAX 100
unsigned char receiveSpeedDataBuf[RECEIVE_BUF_MAX];
unsigned int receiveSpeedIndex;
unsigned int receiveSpeedDataLen;


volatile FLASH_Status FLASHStatus;
ErrorStatus HSEStartUpStatus;
#define FlashStartAddress 0x8010000
void WriteFlash(u8 *pDataBuf , u16 size)
{
	  u16 i,dataBuf;
	  FLASHStatus = FLASH_COMPLETE;

    /* Unlock the Flash Program Erase controller */
    FLASH_Unlock();	//FLASH解锁

    /* Clear All pending flags */
    FLASH_ClearFlag(FLASH_FLAG_BSY | FLASH_FLAG_EOP | FLASH_FLAG_PGERR | FLASH_FLAG_WRPRTERR);//清标志位
		//擦出flash
	  FLASHStatus = FLASH_ErasePage(FlashStartAddress + size);
		//写flash
	  i=0;	  
		while((i < size+1) && (FLASHStatus == FLASH_COMPLETE))
		{
			dataBuf=*((u16*)(pDataBuf+i));
			FLASHStatus = FLASH_ProgramHalfWord(FlashStartAddress+i, dataBuf);
			i+=2;
		}
		FLASH_Lock();
}
void ReadFlash(u8 *pDataBuf , u16 size)
{

    u16 i;
	u16 dataBuf;
	i=0;
	while(i<size+1)//&&(p>(u32 *)StartAddr))
	{
//	    printf("%x",Data);
	//	dataBuf =*(( u16 *)(FlashStartAddress+i));
	  *((u16 *)(pDataBuf+i)) =*(( u16 *)(FlashStartAddress+i));;
		i+=2;
	}
}
void mydelay()
{
	int i;
	for(i=0;i<30000;i++);
}
void Delay_Jas(){
   int i;
	 for(i=100;i>0;i--)mydelay();
}
/*******************************************************************************
* 函数名  : main
* 描述    : 主函数，用户程序从main函数开始运行
* 输入    : 无
* 输出    : 无
* 返回值  : int:返回值为一个16位整形数
* 说明    : 无
*******************************************************************************/
int main(void)
{
	u8 dataBuf[2000];
	u8 ReadDataBuf[2000];
	u16 i;
	System_Initialization();	//STM32系统初始化函数(初始化STM32时钟及外设)
	GPIOInit();
	usart1_init();
	usart2_init();
	usart4_init();
	usart5_init();
	InitSysTick1us();
	//InitExit();
	
	Load_Net_Parameters();		//装载网络参数	
	W5200_Initialization();		//W5200初始货配置
	
	
	for(i=0;i<1000;i++)
	{
		dataBuf[i]=i+1;
		ReadDataBuf[i]=0;
	}

//	WriteFlash(dataBuf,1000);
//	ReadFlash(ReadDataBuf,1000);
		W5200_Socket_Set();//W5200端口初始化配置
	while (1)
	{
		GPIO_ResetBits(GPIOB,GPIO_Pin_10);
Delay_Jas();
GPIO_SetBits(GPIOB,GPIO_Pin_10);
Delay_Jas();
//W5200_Socket_Set();
//		DOControl(0,0);
//		DOControl(0,1);
//		DOControl(1,0);
//		DOControl(1,1);
		#if 0
		if(sysTimeCounter%1000000 ==0)
		{
			if (speedDetectStructBuf[0].indexNo==1)
				{
					if (sysTimeCounter-speedDetectStructBuf[0].risingEdgeCount[0]>5000000)
					{
						speedDetectStructBuf[0].timeCount=0;
						speedDetectStructBuf[0].indexNo=0;
					}
				}
			if (speedDetectStructBuf[0].timeCount>0)
			{////
				if(sysTimeCounter-speedDetectStructBuf[0].risingEdgeCount[1]>20000000)
				{
					speedDetectStructBuf[0].timeCount=0;
					speedDetectStructBuf[0].indexNo=0;
				}
			}
			USART2_Puts("Hello USART2\n");
	//		s_printf("dirction:%d  ,time:%d,",speedDetectStructBuf[0].direction,speedDetectStructBuf[0].timeCount);
	//		s_printf("PW0:%d,PW1:%d,",(speedDetectStructBuf[0].risingEdgeCount[0]-speedDetectStructBuf[0].fallingEdgeCount[0]),(speedDetectStructBuf[0].risingEdgeCount[1]-speedDetectStructBuf[0].fallingEdgeCount[1]));
	//		s_printf("falledge0:%d ,risingEdge0:%d ,fallEdge1:%d ,risingEdge1:%d,index:%d \n",speedDetectStructBuf[0].risingEdgeCount[0],speedDetectStructBuf[0].fallingEdgeCount[0],
	//							speedDetectStructBuf[0].risingEdgeCount[1],speedDetectStructBuf[0].fallingEdgeCount[1],speedDetectStructBuf[0].indexNo);
			
	//		USART4_Puts("bbbbbbbbbb\n");
			if(GPIO_ReadOutputDataBit(GPIOB,GPIO_Pin_9))
			{
				GPIO_ResetBits(GPIOB,GPIO_Pin_9);
			}
			else
			{
				GPIO_SetBits(GPIOB,GPIO_Pin_9);
			}
		} 
		#endif
	}
}

void USART1_IRQHandler(void)
{
	char rxdata;
	if(USART_GetITStatus(USART1,USART_IT_RXNE)==SET)
	{
		USART_ClearITPendingBit(USART1,USART_IT_RXNE);
		rxdata = USART1->DR & (u16)0x01FF;

		USART1_Putc(rxdata);
	}
	//溢出-如果发生溢出需要先读SR,再读DR寄存器 则可清除不断入中断的问题
	if(USART_GetFlagStatus(USART1,USART_FLAG_ORE)==SET)
	{
		USART_ClearFlag(USART1,USART_FLAG_ORE);    //读SR
		USART_ReceiveData(USART1);                //读DR
	}
}
void USART2_IRQHandler(void)
{
	char rxdata;
	if(USART_GetITStatus(USART2,USART_IT_RXNE)==SET)
	{
		USART_ClearITPendingBit(USART2,USART_IT_RXNE);
		rxdata = USART2->DR & (u16)0x01FF;

		USART2_Putc(rxdata);
	}
	//溢出-如果发生溢出需要先读SR,再读DR寄存器 则可清除不断入中断的问题
	if(USART_GetFlagStatus(USART2,USART_FLAG_ORE)==SET)
	{
		USART_ClearFlag(USART2,USART_FLAG_ORE);    //读SR
		USART_ReceiveData(USART2);                //读DR
	}
}
void UART4_IRQHandler(void)
{
	unsigned char i;
	if(USART_GetITStatus(UART4, USART_IT_RXNE) != RESET)
	{
		USART_ClearITPendingBit(UART4,USART_IT_RXNE);
        receiveSpeedDataBuf[receiveSpeedIndex] = USART_ReceiveData(UART4);
			 // USART4_Putc(status);
			  
	//	USART1_Putc(receiveDataBuf[receiveIndex]);
		if (receiveSpeedIndex==0)
		{
			if (receiveSpeedDataBuf[receiveSpeedIndex]==0xBA)
			{
				receiveSpeedIndex++;
			}
		}
		else
		{
			if (receiveSpeedIndex==1)
			{
				if (receiveSpeedDataBuf[receiveSpeedIndex]==0xBA)
				{
					receiveSpeedIndex++;
				}
			}
			else
			{
				if(receiveSpeedIndex>=2)
				{
					receiveSpeedDataLen=receiveSpeedDataBuf[2];
					if (receiveSpeedDataLen<receiveSpeedIndex+1)//接收数据出错 重新接收
					{
						receiveSpeedIndex=0;
						return;
					}
				}
				if (receiveSpeedDataBuf[receiveSpeedIndex]==0xBC)//如果检测到帧尾
				{
					if (receiveSpeedIndex>=4)
					{
						receiveSpeedDataLen=receiveSpeedDataBuf[2];
						if (receiveSpeedDataLen==receiveSpeedIndex+1)
						{
							receiveSpeedIndex=0;
							//数据接收完成 并且数据合法，数据接收结束，下面开始处理数据
							//保存个通道的脉宽
							for(i=0;i<8;i++)
							{
								speedBuf[i].direction=receiveSpeedDataBuf[i*5+5];
								speedBuf[i].timeCount[0]=receiveSpeedDataBuf[i*5+6];
								speedBuf[i].timeCount[1]=receiveSpeedDataBuf[i*5+7];
								speedBuf[i].timeCount[2]=receiveSpeedDataBuf[i*5+8];
								speedBuf[i].timeCount[3]=receiveSpeedDataBuf[i*5+9];
							}
							//s_printf("Len:123,index:456\n");
							//DataProcess();
						}
						else
						{
							receiveSpeedIndex=0;					
						}
					}
					else
					{
						receiveSpeedIndex=0;
					}
				}
				else
				{
					if(receiveSpeedIndex<RECEIVE_BUF_MAX)
						receiveSpeedIndex++;
				}
			}
		}
      //  printf("%c",status);
    }
}
void UART5_IRQHandler(void)
{
	unsigned char status;
	if(USART_GetITStatus(UART5, USART_IT_RXNE) != RESET)
	{
        status = USART_ReceiveData(UART5);
			  USART5_Putc(status);
      //  printf("%c",status);
    }
}
/*
内核时钟 用于时间戳
*/
void SysTick_Handler(void)
{
	sysTimeCounter++;
/*	if(GPIO_ReadOutputDataBit(GPIOD,GPIO_Pin_11))
	{
		GPIO_ResetBits(GPIOD,GPIO_Pin_11);
	}
	else
	{
		GPIO_SetBits(GPIOD,GPIO_Pin_11);
	}
	*/
}

