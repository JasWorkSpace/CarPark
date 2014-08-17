/**********************************************************************************
 * 文件名  ：W5200.c
 * 描述    ：W5200 驱动函数库         
 * 库版本  ：ST_v3.5
 * 作者    ：泥人通信模块开发团队
 * 博客    ：http://nirenelec.blog.163.com
 * 淘宝    ：http://nirenelec.taobao.com
 * 说明：
 * 		本软件包括5个部分：
 *   	1. W5200初始化
 *   	2. W5200的Socket初始化
 *   	3. Socket连接
 *   	   如果Socket设置为TCP服务器模式，则调用Socket_Listen()函数,W5200处于侦听状态，直到远程客户端与它连接。
 *   	   如果Socket设置为TCP客户端模式，则调用Socket_Connect()函数，
 *   	                                  每调用一次Socket_Connect(s)函数，产生一次连接，
 *   	                                  如果连接不成功，则产生超时中断，然后可以再调用该函数进行连接。
 *   	   如果Socket设置为UDP模式,则调用Socket_UDP函数
 *   	4. Socket数据接收和发送
 *   	5. W5200中断处理

 *   置W5200为服务器模式的调用过程：W5200_Init()-->Socket_Init(s)-->Socket_Listen(s)，设置过程即完成，等待客户端的连接。
 *   置W5200为客户端模式的调用过程：W5200_Init()-->Socket_Init(s)-->Socket_Connect(s)，设置过程即完成，并与远程服务器连接。
 *   置W5200为UDP模式的调用过程：W5200_Init()-->Socket_Init(s)-->Socket_UDP(s)，设置过程即完成，可以与远程主机UDP通信。

 *   W5200产生的连接成功、终止连接、接收数据、发送数据、超时等事件，都可以从中断状态中获得。
**********************************************************************************/

#include "stm32f10x.h"
#include "stm32f10x_spi.h"				
#include "W5200.h"	
#include "USART.h"
#include "GPIO.h"

/***************----- 网络参数变量定义 -----***************/
unsigned char Gateway_IP[4];//网关IP地址 
unsigned char Sub_Mask[4];	//子网掩码 
unsigned char Phy_Addr[6];	//物理地址(MAC) 
unsigned char IP_Addr[4];	//本机IP地址 

unsigned char S0_Port[2];	//端口0的端口号(5000) 
unsigned char S0_DIP[4];	//端口0目的IP地址 
unsigned char S0_DPort[2];	//端口0目的端口号(6000) 

unsigned char UDP_DIPR[4];	//UDP(广播)模式,目的主机IP地址
unsigned char UDP_DPORT[2];	//UDP(广播)模式,目的主机端口号

/***************----- 端口的运行模式 -----***************/
unsigned char S0_Mode =3;	//端口0的运行模式,0:TCP服务器模式,1:TCP客户端模式,2:UDP(广播)模式
#define TCP_SERVER	0x00	//TCP服务器模式
#define TCP_CLIENT	0x01	//TCP客户端模式 
#define UDP_MODE	0x02	//UDP(广播)模式 

/***************----- 端口的运行状态 -----***************/
unsigned char S0_State =0;	//端口0状态记录,1:端口完成初始化,2端口完成连接(可以正常传输数据) 
#define S_INIT		0x01	//端口完成初始化 
#define S_CONN		0x02	//端口完成连接,可以正常传输数据 

/***************----- 端口收发数据的状态 -----***************/
unsigned char S0_Data;		//端口0接收和发送数据的状态,1:端口接收到数据,2:端口发送数据完成 
#define S_RECEIVE	 0x01	//端口接收到一个数据包 
#define S_TRANSMITOK 0x02	//端口发送一个数据包完成 

/***************----- 端口数据缓冲区 -----***************/
unsigned char Rx_Buffer[2000];	//端口接收数据缓冲区 
unsigned char Tx_Buffer[2000];	//端口发送数据缓冲区 

unsigned char W5200_Interrupt;	//W5200中断标志(0:无中断,1:有中断)

//命令缓冲区
unsigned char RXCmdBuf[2000];
unsigned int rxDataLen;
unsigned int rxIndex;

/*******************************************************************************
* 函数名  : W5200_GPIO_Configuration
* 描述    : W5200 GPIO初始化配置
* 输入    : 无
* 输出    : 无
* 返回值  : 无
* 说明    : 无
*******************************************************************************/
void W5200_GPIO_Configuration(void)
{
	GPIO_InitTypeDef  GPIO_InitStructure;
//	EXTI_InitTypeDef  EXTI_InitStructure;	

	/* W5200_RST引脚初始化配置(PC5) */
	GPIO_InitStructure.GPIO_Pin  = W5200_RST;
	GPIO_InitStructure.GPIO_Speed=GPIO_Speed_10MHz;
	GPIO_InitStructure.GPIO_Mode = GPIO_Mode_Out_PP;
	GPIO_Init(W5200_RST_PORT, &GPIO_InitStructure);
	GPIO_SetBits(W5200_RST_PORT, W5200_RST);
	
	/* W5200_INT引脚初始化配置(PC4) */	
	GPIO_InitStructure.GPIO_Pin = W5200_INT;
	GPIO_InitStructure.GPIO_Speed=GPIO_Speed_50MHz;
	GPIO_InitStructure.GPIO_Mode = GPIO_Mode_IPU;
	GPIO_Init(W5200_INT_PORT, &GPIO_InitStructure);
		
//	/* Connect EXTI Line4 to PC4 */
//	GPIO_EXTILineConfig(GPIO_PortSourceGPIOC, GPIO_PinSource4);
//
//	/* PC4 as W5200 interrupt input */
//	EXTI_InitStructure.EXTI_Line = EXTI_Line4;
//	EXTI_InitStructure.EXTI_Mode = EXTI_Mode_Interrupt;
//	EXTI_InitStructure.EXTI_Trigger = EXTI_Trigger_Falling;
//	EXTI_InitStructure.EXTI_LineCmd = ENABLE;
//	EXTI_Init(&EXTI_InitStructure);
}


/*******************************************************************************
* 函数名  : W5200_NVIC_Configuration
* 描述    : W5200 接收引脚中断优先级设置
* 输入    : 无
* 输出    : 无
* 返回值  : 无
* 说明    : 无
*******************************************************************************/
void W5200_NVIC_Configuration(void)
{
  	NVIC_InitTypeDef NVIC_InitStructure;

//	/* Enable the EXTI0 Interrupt */
	NVIC_InitStructure.NVIC_IRQChannel = EXTI4_IRQn;
	NVIC_InitStructure.NVIC_IRQChannelPreemptionPriority = 0;
	NVIC_InitStructure.NVIC_IRQChannelSubPriority = 1;
	NVIC_InitStructure.NVIC_IRQChannelCmd = ENABLE;
	NVIC_Init(&NVIC_InitStructure);
		
	NVIC_InitStructure.NVIC_IRQChannel = TIM2_IRQn;				//设置中断向量号
	NVIC_InitStructure.NVIC_IRQChannelPreemptionPriority = 0;	//设置抢先优先级
	NVIC_InitStructure.NVIC_IRQChannelSubPriority = 0;			//设置响应优先级
	NVIC_InitStructure.NVIC_IRQChannelCmd = ENABLE;				//使能NVIC
	NVIC_Init(&NVIC_InitStructure);
}

/*******************************************************************************
* 函数名  : SPI_Configuration
* 描述    : W5200 SPI初始化配置(STM32 SPI1)
* 输入    : 无
* 输出    : 无
* 返回值  : 无
* 说明    : 无
*******************************************************************************/
void SPI_Configuration(void)
{
	GPIO_InitTypeDef 	GPIO_InitStructure;
	SPI_InitTypeDef   	SPI_InitStructure;	   

  	RCC_APB2PeriphClockCmd(RCC_APB2Periph_GPIOA | RCC_APB2Periph_SPI1 | RCC_APB2Periph_AFIO, ENABLE);	

	/* 初始化SCK、MISO、MOSI引脚 */
	GPIO_InitStructure.GPIO_Pin = GPIO_Pin_5 | GPIO_Pin_6 | GPIO_Pin_7;
	GPIO_InitStructure.GPIO_Speed = GPIO_Speed_50MHz;
	GPIO_InitStructure.GPIO_Mode = GPIO_Mode_AF_PP;
	GPIO_Init(GPIOA, &GPIO_InitStructure);
	GPIO_SetBits(GPIOA,GPIO_Pin_5|GPIO_Pin_6|GPIO_Pin_7);

	/* 初始化CS引脚 */
	GPIO_InitStructure.GPIO_Pin = W5200_SCS;
	GPIO_InitStructure.GPIO_Speed=GPIO_Speed_50MHz;
	GPIO_InitStructure.GPIO_Mode=GPIO_Mode_Out_PP;
	GPIO_Init(W5200_SCS_PORT, &GPIO_InitStructure);
	GPIO_SetBits(W5200_SCS_PORT, W5200_SCS);

	/* 初始化配置STM32 SPI1 */
	SPI_InitStructure.SPI_Direction=SPI_Direction_2Lines_FullDuplex;	//SPI设置为双线双向全双工
	SPI_InitStructure.SPI_Mode=SPI_Mode_Master;							//设置为主SPI
	SPI_InitStructure.SPI_DataSize=SPI_DataSize_8b;						//SPI发送接收8位帧结构
	SPI_InitStructure.SPI_CPOL=SPI_CPOL_Low;							//时钟悬空低
	SPI_InitStructure.SPI_CPHA=SPI_CPHA_1Edge;							//数据捕获于第1个时钟沿
	SPI_InitStructure.SPI_NSS=SPI_NSS_Soft;								//NSS由外部管脚管理
	SPI_InitStructure.SPI_BaudRatePrescaler=SPI_BaudRatePrescaler_8;	//波特率预分频值为8
	SPI_InitStructure.SPI_FirstBit=SPI_FirstBit_MSB;					//数据传输从MSB位开始
	SPI_InitStructure.SPI_CRCPolynomial=7;								//CRC多项式为7
	SPI_Init(SPI1,&SPI_InitStructure);									//根据SPI_InitStruct中指定的参数初始化外设SPI1寄存器

	SPI_Cmd(SPI1,ENABLE);	//STM32使能SPI1
}

/*******************************************************************************
* 函数名  : SPI_SendByte
* 描述    : STM32 SPI1发送一个字节数据，并返回接收到的数据
* 输入    : dt:待发送的数据
* 输出    : 无
* 返回值  : STM32 SPI1接收到的数据
* 说明    : 通过SPI发送一个字节并返回接收的一个字节，该子程序与Read_W5200
*			和Write_W5200配合使用
*******************************************************************************/
unsigned char SPI_SendByte(unsigned char dt)
{
	while(SPI_I2S_GetFlagStatus(SPI1, SPI_I2S_FLAG_TXE) == RESET); //等待数据寄存器空
	SPI_I2S_SendData(SPI1, dt); //通过SPI2接口发送数据
	while(SPI_I2S_GetFlagStatus(SPI1, SPI_I2S_FLAG_RXNE) == RESET); //等待接收到一个字节的数据
	return SPI_I2S_ReceiveData(SPI1); //返回接收的数据
}

/*******************************************************************************
* 函数名  : Read_W5200
* 描述    : 读W5200指定地址的寄存器数据
* 输入    : addr:(地址)
* 输出    : 无
* 返回值  : 读取到的寄存器数据
* 说明    : 从W5200指定的地址读取一个字节
*******************************************************************************/
unsigned char Read_W5200(unsigned short addr)
{
	unsigned char i;

	GPIO_ResetBits(W5200_SCS_PORT, W5200_SCS); //置W5200的CS为低电平
	SPI_SendByte(addr/256);	//发送地址高位
	SPI_SendByte(addr);		//发送地址低位
	SPI_SendByte(0x00);		//发送读命令
	SPI_SendByte(0x01);		//发送读数据的长度
	i=SPI_SendByte(0);		//发送一个哑数据并读取数据
	GPIO_SetBits(W5200_SCS_PORT, W5200_SCS); //置W5200的CS为高电平

	return i; //返回读取到的寄存器数据
}

/*******************************************************************************
* 函数名  : Write_W5200	
* 描述    : 向W5200指定地址寄存器写一个字节数据
* 输入    : addr:(地址) dat:(待写入数据)
* 输出    : 无
* 返回值  : 无
* 说明    : 将一个字节写入W5200指定的地址
*******************************************************************************/
void Write_W5200(unsigned short addr, unsigned char dat)
{
	GPIO_ResetBits(W5200_SCS_PORT, W5200_SCS); //置W5200的CS为低电平	
	SPI_SendByte(addr/256);	//发送地址高位
	SPI_SendByte(addr);		//发送地址低位
	SPI_SendByte(0x80);		//发送待写入的数据长度高位(写数据最高位为1)
	SPI_SendByte(0x01);		//发送待写入的数据长度低位,1个字节
	SPI_SendByte(dat);		//写入数据 
	GPIO_SetBits(W5200_SCS_PORT, W5200_SCS); //置W5200的CS为高电平 
}

/*******************************************************************************
* 函数名  : WriteBuff_W5200
* 描述    : 向W5200指定地址寄存器写len个字节数据
* 输入    : addr:(地址) dat:(待写入数据)
* 输出    : 无
* 返回值  : 无
* 说明    : 将一个字节写入W5200指定的地址
*******************************************************************************/
unsigned short WriteBuff_W5200(unsigned short addr,unsigned char* buff,unsigned short len)
{
	unsigned short i = 0;
	  
	GPIO_ResetBits(W5200_SCS_PORT, W5200_SCS); //置W5200的CS为低电平                         	
	SPI_SendByte(addr/256);	//发送地址高位
	SPI_SendByte(addr);		//发送地址低位
	i = len|0x8000;			//写数据最高位为1
	SPI_SendByte(i/256);	//发送待写入的数据长度高位
	SPI_SendByte(i);		//发送待写入的数据长度低位

	for(i=0; i<len; i++)	//循环写入len个字节的数据
	{		
		SPI_SendByte(buff[i]);
	}
	GPIO_SetBits(W5200_SCS_PORT, W5200_SCS); //置W5200的CS为高电平 
	
	return len;	//返回数据的长度
}

/*******************************************************************************
* 函数名  : ReadBuff_W5200	
* 描述    : 向W5200指定地址寄存器写len个字节数据
* 输入    : addr:(地址) dat:(待写入数据)
* 输出    : 无
* 返回值  : 无
* 说明    : 将一个字节写入W5200指定的地址
*******************************************************************************/
unsigned short ReadBuff_W5200(unsigned short addr, unsigned char* buff,unsigned short len)
{
	unsigned short i = 0;
		
	GPIO_ResetBits(W5200_SCS_PORT, W5200_SCS); //置W5200的CS为低电平                         	
	SPI_SendByte(addr/256);	//发送地址高位
	SPI_SendByte(addr);		//发送地址低位
	i = len&0x7fff;			//写数据最高位为1
	SPI_SendByte(i/256);	//发送待写入的数据长度高位
	SPI_SendByte(i);		//发送待写入的数据长度低位		        

	for(i=0; i<len; i++)//循环读取len个字节的数据                         
	{
	 	buff[i] = SPI_SendByte(0x00);
		
	}
	GPIO_SetBits(W5200_SCS_PORT, W5200_SCS); //置W5200的CS为高电平 

	return len;
}

/*******************************************************************************
* 函数名  : W5200_Hardware_Reset
* 描述    : 硬件复位W5200
* 输入    : 无
* 输出    : 无
* 返回值  : 无
* 说明    : 无
*******************************************************************************/
void W5200_Hardware_Reset(void)
{
	GPIO_ResetBits(W5200_RST_PORT,W5200_RST);
	Delay(100);
	GPIO_SetBits(W5200_RST_PORT,W5200_RST);
	Delay(1000);
}

/*******************************************************************************
* 函数名  : W5200_Init
* 描述    : 初始化W5200寄存器函数
* 输入    : 无
* 输出    : 无
* 返回值  : 无
* 说明    : 在使用W5200之前，先对W5200初始化
*******************************************************************************/
void W5200_Init(void)
{
	u8 i=0;

	Write_W5200(MR,MR_RST);//软件复位W5200,置1有效,复位后自动清0
	Delay(100);//延时100ms,自己定义该函数

	//设置网关(Gateway)的IP地址,Gateway_IP为4字节unsigned char数组,自己定义 
	//使用网关可以使通信突破子网的局限，通过网关可以访问到其它子网或进入Internet
	WriteBuff_W5200(GAR0,Gateway_IP,4);	
			
	//设置子网掩码(MASK)值,SUB_MASK为4字节unsigned char数组,自己定义
	//子网掩码用于子网运算
	WriteBuff_W5200(SUBR0,Sub_Mask,4);		
	
	//设置物理地址,PHY_ADDR为6字节unsigned char数组,自己定义,用于唯一标识网络设备的物理地址值
	//该地址值需要到IEEE申请，按照OUI的规定，前3个字节为厂商代码，后三个字节为产品序号
	//如果自己定义物理地址，注意第一个字节必须为偶数
	WriteBuff_W5200(SHAR0,Phy_Addr,6);		

	//设置本机的IP地址,IP_ADDR为4字节unsigned char数组,自己定义
	//注意，网关IP必须与本机IP属于同一个子网，否则本机将无法找到网关
	WriteBuff_W5200(SIPR0,IP_Addr,4);		
	
	//设置发送缓冲区和接收缓冲区的大小，参考W5200数据手册
	for(i=0;i<8;i++)
	{
		Write_W5200(Sn_RXMEM_SIZE(i),0x02);//Socket Rx memory size=2k	
		Write_W5200(Sn_TXMEM_SIZE(i),0x02);//Socket Tx mempry size=2k
	}

	//设置重试时间，默认为2000(200ms) 
	//每一单位数值为100微秒,初始化时值设为2000(0x07D0),等于200毫秒
	Write_W5200(RTR,0x07);
	Write_W5200(RTR+1,0xd0);

	//设置重试次数，默认为8次 
	//如果重发的次数超过设定值,则产生超时中断(相关的端口中断寄存器中的Sn_IR 超时位(TIMEOUT)置“1”)
	Write_W5200(RCR,8);

	//启动中断，参考W5200数据手册确定自己需要的中断类型
	//IMR_CONFLICT是IP地址冲突异常中断,IMR_UNREACH是UDP通信时，地址无法到达的异常中断
	//其它是Socket事件中断，根据需要添加
	Write_W5200(IMR1,IR_CONFLICT);
	Write_W5200(IMR2,IR_SOCK(0));
	Write_W5200(Sn_IMR(0),Sn_IR_SEND_OK | Sn_IR_TIMEOUT | Sn_IR_RECV | Sn_IR_DISCON | Sn_IR_CON);
}

/*******************************************************************************
* 函数名  : Read_IR_W5200
* 描述    : 读取W5200指定端口中断寄存器的值
* 输入    : s:指定的端口号(0~7)
* 输出    : 无
* 返回值  : i(返回W5200指定端口中断寄存器的值)
* 说明    : 无
*******************************************************************************/
unsigned char Read_IR_W5200(SOCKET s)
{
	unsigned char i;

	i = Read_W5200(Sn_IR(s));
	if(i>0)	Write_W5200(Sn_IR(s),i);

	return i;
}

/*******************************************************************************
* 函数名  : Detect_Gateway
* 描述    : 检查网关服务器
* 输入    : 无
* 输出    : 无
* 返回值  : 成功返回TRUE(0xFF),失败返回FALSE(0x00)
* 说明    : 无
*******************************************************************************/
unsigned char Detect_Gateway(void)
{
	unsigned char i;

	while(1)
	{
		Write_W5200(Sn_MR(0),Sn_MR_TCP);//设置socket0为TCP模式
		Write_W5200(Sn_CR(0),Sn_CR_OPEN);//打开socket0
		Delay(10);
		//如果socket0打开失败
		if(Read_W5200(Sn_SR(0))!=SOCK_INIT)
		{
			Write_W5200(Sn_CR(0),Sn_CR_CLOSE);//打开不成功,关闭Socket
			if(i >= 10)
			{
				return FALSE;//返回FALSE(0x00)
			}
		}
		else
		{
			break;
		}
	}

	//检查网关及获取网关的物理地址
	for(i=0;i<4;i++)
		Write_W5200(Sn_DIPR0(0)+i,IP_Addr[i]+1);//向目的地址寄存器写入与本机IP不同的IP值
	//打开socket0的TCP连接
	Write_W5200(Sn_CR(0),Sn_CR_CONNECT);
	//延时20ms 
	Delay(50);						

	do
	{
		if(Read_IR_W5200(0)&Sn_IR_TIMEOUT)//超时
		{
			Write_W5200(Sn_CR(0),Sn_CR_CLOSE);
			return FALSE;
		}
		if(Read_W5200(Sn_DHAR0(0))!=0xff)//目标MAC地址寄存器第一个字节不为0xff
		{
			Write_W5200(Sn_CR(0),Sn_CR_CLOSE);
			break;
		}
	}while(1);

	return TRUE;
}

/*******************************************************************************
* 函数名  : Socket_Init
* 描述    : 指定Socket(0~7)初始化
* 输入    : s:待初始化的端口
* 输出    : 无
* 返回值  : 无
* 说明    : 无
*******************************************************************************/
void Socket_Init(SOCKET s)
{
	//设置分片长度，参考W5200数据手册，该值可以不修改
	//最大分片字节数=1460(0x5b4)
	Write_W5200(Sn_MSSR0(s),0x05);		
	Write_W5200(Sn_MSSR0(s)+1,0xb4);
	//设置指定端口
	switch(s)
	{
		case 0:
			//设置端口0的端口号
			WriteBuff_W5200(Sn_PORT0(0),S0_Port,2);				
			
			break;

		case 1:
			break;

		case 2:
			break;

		case 3:
			break;

		case 4:
			break;

		case 5:
			break;

		case 6:
			break;

		case 7:
			break;

		default:
			break;
	}
}

/*******************************************************************************
* 函数名  : Socket_Connect
* 描述    : 设置指定Socket(0~7)为客户端与远程服务器连接
* 输入    : s:待设定的端口
* 输出    : 无
* 返回值  : 成功返回TRUE(0xFF),失败返回FALSE(0x00)
* 说明    : 当本机Socket工作在客户端模式时,引用该程序,与远程服务器建立连接
*			如果启动连接后出现超时中断，则与服务器连接失败,需要重新调用该程序连接
*			该程序每调用一次,就与服务器产生一次连接
*******************************************************************************/
unsigned char Socket_Connect(SOCKET s)
{
	Write_W5200(Sn_MR(s), Sn_MR_TCP);//设置socket为TCP模式
	Write_W5200(Sn_CR(s), Sn_CR_OPEN);//打开Socket
	//如果socket打开失败
	if(Read_W5200(Sn_SR(s))!=SOCK_INIT)
	{
		Write_W5200(Sn_CR(s),Sn_CR_CLOSE);//打开不成功,关闭Socket
		return FALSE;//返回FALSE(0x00)
	}
	//设置Socket为Connect模式
	Write_W5200(Sn_CR(s),Sn_CR_CONNECT);

	return TRUE;

	//至此完成了Socket的打开连接工作,至于它是否与远程服务器建立连接,则需要等待Socket中断，
	//以判断Socket的连接是否成功。参考W5200数据手册的Socket中断状态
}

/*******************************************************************************
* 函数名  : Socket_Listen
* 描述    : 设置指定Socket(0~7)作为服务器等待远程主机的连接
* 输入    : s:待设定的端口
* 输出    : 无
* 返回值  : 成功返回TRUE(0xFF),失败返回FALSE(0x00)
* 说明    : 当本机Socket工作在服务器模式时,引用该程序,等等远程主机的连接
*			该程序只调用一次,就使W5200设置为服务器模式
*******************************************************************************/
unsigned char Socket_Listen(SOCKET s)
{
	Write_W5200(Sn_MR(s), Sn_MR_TCP);//设置socket为TCP模式 
	Write_W5200(Sn_CR(s), Sn_CR_OPEN);//打开Socket
	//如果socket打开失败
	if(Read_W5200(Sn_SR(s))!=SOCK_INIT)
	{
		Write_W5200(Sn_CR(s),Sn_CR_CLOSE);//打开不成功,关闭Socket
		return FALSE;//返回FALSE(0x00)
	}
	//设置Socket为侦听模式
	Write_W5200(Sn_CR(s), Sn_CR_LISTEN);
	//如果socket设置失败
	if(Read_W5200(Sn_SR(s))!=SOCK_LISTEN)
	{
		Write_W5200(Sn_CR(s), Sn_CR_CLOSE);//设置不成功,关闭Socket
		return FALSE;//返回FALSE(0x00)
	}

	return TRUE;

	//至此完成了Socket的打开和设置侦听工作,至于远程客户端是否与它建立连接,则需要等待Socket中断，
	//以判断Socket的连接是否成功。参考W5200数据手册的Socket中断状态
	//在服务器侦听模式不需要设置目的IP和目的端口号
}

/*******************************************************************************
* 函数名  : Socket_UDP
* 描述    : 设置指定Socket(0~3)为UDP模式
* 输入    : s:待设定的端口
* 输出    : 无
* 返回值  : 成功返回TRUE(0xFF),失败返回FALSE(0x00)
* 说明    : 如果Socket工作在UDP模式,引用该程序,在UDP模式下,Socket通信不需要建立连接
*			该程序只调用一次，就使W5200设置为UDP模式
*******************************************************************************/
unsigned char Socket_UDP(SOCKET s)
{
	Write_W5200(Sn_MR(s), Sn_MR_UDP);//设置Socket为UDP模式*/
	Write_W5200(Sn_CR(s), Sn_CR_OPEN);//打开Socket*/
	//如果Socket打开失败
	if(Read_W5200(Sn_SR(s))!=SOCK_UDP)
	{
		Write_W5200(Sn_CR(s), Sn_CR_CLOSE);//打开不成功,关闭Socket
		return FALSE;//返回FALSE(0x00)
	}
	else
		return TRUE;

	//至此完成了Socket的打开和UDP模式设置,在这种模式下它不需要与远程主机建立连接
	//因为Socket不需要建立连接,所以在发送数据前都可以设置目的主机IP和目的Socket的端口号
	//如果目的主机IP和目的Socket的端口号是固定的,在运行过程中没有改变,那么也可以在这里设置
}

/*******************************************************************************
* 函数名  : S_rx_process
* 描述    : 指定Socket(0~3)接收数据处理
* 输入    : s:端口
* 输出    : 无
* 返回值  : 返回接收到数据的长度
* 说明    : 如果Socket产生接收数据的中断,则引用该程序进行处理
*			该程序将Socket的接收到的数据缓存到Rx_buffer数组中,并返回接收的数据字节数
*******************************************************************************/
unsigned short S_rx_process(SOCKET s)
{
    
	unsigned short i;
	unsigned short rx_size,rx_offset,rx_offset1;

	//读取接收数据的字节数
	rx_size=Read_W5200(Sn_RX_RSR0(s));
	rx_size*=256;
	rx_size+=Read_W5200(Sn_RX_RSR0(s)+1);

	//读取接收缓冲区的偏移量
	rx_offset=Read_W5200(Sn_RX_RD0(0));
	rx_offset*=256;
	rx_offset+=Read_W5200(Sn_RX_RD0(0)+1);
	rx_offset1=rx_offset;

	i=rx_offset/S_RX_SIZE; //计算实际的物理偏移量，S0_RX_SIZE需要在前面#define中定义
						   //注意S_RX_SIZE的值在W5200_Init()函数的Sn_MSSR0中确定
	rx_offset=rx_offset-i*S_RX_SIZE;

	if((rx_size+rx_offset)<=S_RX_SIZE)
	{
		ReadBuff_W5200((RX_MEM+s*S_RX_SIZE+rx_offset),Rx_Buffer,rx_size);			
	}
	else
	{
		i=S_RX_SIZE-rx_offset;
		ReadBuff_W5200((RX_MEM+s*S_RX_SIZE+rx_offset),Rx_Buffer,i);
		ReadBuff_W5200((RX_MEM+s*S_RX_SIZE),Rx_Buffer+i,(rx_size-i));		
	}
	//计算下一次偏移量
	rx_offset1+=rx_size;
	Write_W5200(Sn_RX_RD0(0), (rx_offset1/256));
	Write_W5200(Sn_RX_RD0(0)+1, rx_offset1);

	Write_W5200(Sn_CR(0), Sn_CR_RECV);//设置RECV命令，等等下一次接收
	while(Read_W5200(Sn_CR(0)));

	return rx_size;//返回接收的数据字节数	
}


/*******************************************************************************
* 函数名  : S_tx_process
* 描述    : 指定Socket(0~3)发送数据处理
* 输入    : s:端口,size(发送数据的长度)
* 输出    : 无
* 返回值  : 成功返回TRUE(0xFF),失败返回FALSE(0x00)
* 说明    : 要发送的数据缓存在Tx_buffer中
*******************************************************************************/
unsigned char S_tx_process(SOCKET s, unsigned int size)
{
	unsigned short i;
	unsigned short tx_offset,tx_offset1;

	//如果是UDP模式,可以在此设置目的主机的IP和端口号
	if((Read_W5200(Sn_MR(s))&0x0f)==0x02)
	{
		//设置目的主机IP
		WriteBuff_W5200(Sn_DIPR0(s),UDP_DIPR,4);
  		//设置目的主机端口号
		WriteBuff_W5200(Sn_DPORT0(s),UDP_DPORT,2);				
	}

	//读取缓冲区剩余的长度
	i=Read_W5200(Sn_TX_FSR0(s));
	i*=256;
	i+=Read_W5200(Sn_TX_FSR0(s)+1);
	if(i<size) return FALSE; //如果剩余的字节长度小于发送字节长度,则返回*/
		
	//读取发送缓冲区的偏移量
	tx_offset=Read_W5200(Sn_TX_WR0(s));
	tx_offset*=256;
	tx_offset+=Read_W5200(Sn_TX_WR0(s)+1);
	tx_offset1=tx_offset;

	i=tx_offset/S_TX_SIZE; //计算实际的物理偏移量，S0_TX_SIZE需要在前面#define中定义
						   //注意S0_TX_SIZE的值在W5200_Init()函数的W5200_TMSR中确定
	tx_offset=tx_offset-i*S_TX_SIZE;
	//将待发送数据写入W5200发送缓冲区
	if((tx_offset+size)<=S_TX_SIZE)
	{
		WriteBuff_W5200((TX_MEM+s*S_TX_SIZE+tx_offset),Tx_Buffer,size);
	}
	else
	{
		i=S_TX_SIZE-tx_offset;
		WriteBuff_W5200((TX_MEM+s*S_TX_SIZE+tx_offset),Tx_Buffer,i);
		WriteBuff_W5200((TX_MEM+s*S_TX_SIZE),Tx_Buffer+i,(size-i));
	}

	//计算下一次的偏移量
	tx_offset1+=size;
	Write_W5200(Sn_TX_WR0(s),(tx_offset1/256));
	Write_W5200(Sn_TX_WR0(s)+1,tx_offset1);

	Write_W5200(Sn_CR(s), Sn_CR_SEND);//设置SEND命令,启动发送
	i = 0;
	while((Read_IR_W5200(0) & Sn_IR_SEND_OK)==0)//等待发送完成
	{
		Delay(1);
		if(++i>40)
		{
			break;
		}
	}

	return TRUE;//返回成功
}

/*******************************************************************************
* 函数名  : W5200_Interrupt_Process
* 描述    : W5200中断处理程序框架
* 输入    : 无
* 输出    : 无
* 返回值  : 无
* 说明    : 无
*******************************************************************************/
void W5200_Interrupt_Process(void)
{
	unsigned char i,j;

	W5200_Interrupt=0;//清零中断标志	
	i=Read_W5200(IR);//读取中断标志寄存器
	Write_W5200(IR, i);//回写清除中断标志

	if((i & IR_CONFLICT) == IR_CONFLICT)//IP地址冲突异常处理
	{
		 //自己添加代码
	}

	if((i & IR_UNREACH) == IR_UNREACH)//UDP模式下地址无法到达异常处理
	{
		//自己添加代码
	}

	i=Read_W5200(IR2);//读取中断标志寄存器
	if((i & IR_SOCK(0)) == IR_SOCK(0))//Socket0事件处理 
	{
		j=Read_W5200(Sn_IR(0));//读取Socket0中断标志寄存器
		Write_W5200(Sn_IR(0), j);//回写清中断标志 
		if(j&Sn_IR_CON)//在TCP模式下,Socket0成功连接 
		{
			S0_State|=S_CONN;//网络连接状态0x02,端口完成连接，可以正常传输数据
		}
		if(j&Sn_IR_DISCON)//在TCP模式下Socket断开连接处理
		{
			Write_W5200(Sn_CR(0), Sn_CR_CLOSE);// 关闭端口,等待重新打开连接 
			S0_State=0;//网络连接状态0x00,端口连接失败
		}
		if(j&Sn_IR_SEND_OK)//Socket0数据发送完成,可以再次启动S_tx_process()函数发送数据 
		{
			S0_Data|=S_TRANSMITOK;//端口发送一个数据包完成 
		}
		if(j&Sn_IR_RECV)//Socket接收到数据,可以启动S_rx_process()函数 
		{
			S0_Data|=S_RECEIVE;//端口接收到一个数据包
		}
		if(j&Sn_IR_TIMEOUT)//Socket连接或数据传输超时处理 
		{
			Write_W5200(Sn_CR(0), Sn_CR_CLOSE);//关闭端口,等待重新打开连接
			S0_State=0;//网络连接状态0x00,端口连接失败
		}
	}
}




//用户定义函数
unsigned int Timer2_Counter=0; //Timer2定时器计数变量(ms)
unsigned int W5200_Send_Delay_Counter=0; //W5200发送延时计数变量(ms)

/*******************************************************************************
* 函数名  : W5200_Initialization
* 描述    : W5200初始货配置
* 输入    : 无
* 输出    : 无
* 返回值  : 无
* 说明    : 无
*******************************************************************************/
void W5200_Initialization(void)
{
	W5200_NVIC_Configuration();
	W5200_GPIO_Configuration();	//W5200 GPIO初始化配置	
	Timer2_Init_Config();		//Timer2初始化配置
	EXTI4_Configuration();
	SPI_Configuration();		//W5200 SPI初始化配置(STM32 SPI1)
	
	W5200_Hardware_Reset();		//硬件复位W5200
	W5200_Init();		//初始化W5200寄存器函数
	Detect_Gateway();	//检查网关服务器 
	Socket_Init(0);		//指定Socket(0~3)初始化,初始化端口0
}

/*******************************************************************************
* 函数名  : Load_Net_Parameters
* 描述    : 装载网络参数
* 输入    : 无
* 输出    : 无
* 返回值  : 无
* 说明    : 网关、掩码、物理地址、本机IP地址、端口号、目的IP地址、目的端口号、端口工作模式
*******************************************************************************/
void Load_Net_Parameters(void)
{
	Gateway_IP[0] = 192;//加载网关参数
	Gateway_IP[1] = 168;
	Gateway_IP[2] = 1;
	Gateway_IP[3] = 1;

	Sub_Mask[0]=255;//加载子网掩码
	Sub_Mask[1]=255;
	Sub_Mask[2]=0;
	Sub_Mask[3]=0;

	Phy_Addr[0]=0x0c;//加载物理地址
	Phy_Addr[1]=0x29;
	Phy_Addr[2]=0xab;
	Phy_Addr[3]=0x7c;
	Phy_Addr[4]=0x00;
	Phy_Addr[5]=0x01;

	IP_Addr[0]=192;//加载本机IP地址
	IP_Addr[1]=168;
	IP_Addr[2]=1;
	IP_Addr[3]=199;

	S0_Port[0] = 0x13;//加载端口0的端口号5000 
	S0_Port[1] = 0x88;

//	S0_DIP[0]=192;//加载端口0的目的IP地址
//	S0_DIP[1]=168;
//	S0_DIP[2]=1;
//	S0_DIP[3]=190;
//	
//	S0_DPort[0] = 0x17;//加载端口0的目的端口号6000
//	S0_DPort[1] = 0x70;

	S0_Mode=TCP_SERVER;//加载端口0的工作模式,TCP服务端模式
}

/*******************************************************************************
* 函数名  : W5200_Socket_Set
* 描述    : W5200端口初始化配置
* 输入    : 无
* 输出    : 无
* 返回值  : 无
* 说明    : 分别设置4个端口,根据端口工作模式,将端口置于TCP服务器、TCP客户端或UDP模式.
*			从端口状态字节Socket_State可以判断端口的工作情况
*******************************************************************************/
void W5200_Socket_Set(void)
{
	if(S0_State==0)//端口0初始化配置
	{
		if(S0_Mode==TCP_SERVER)//TCP服务器模式 
		{
			if(Socket_Listen(0)==TRUE)
				S0_State=S_INIT;
			else
				S0_State=0;
		}
		else if(S0_Mode==TCP_CLIENT)//TCP客户端模式 
		{
			if(Socket_Connect(0)==TRUE)
				S0_State=S_INIT;
			else
				S0_State=0;
		}
		else//UDP模式 
		{
			if(Socket_UDP(0)==TRUE)
				S0_State=S_INIT|S_CONN;
			else
				S0_State=0;
		}
	}
}

/*******************************************************************************
* 函数名  : Process_Socket_Data
* 描述    : W5200接收并发送接收到的数据
* 输入    : s:端口号
* 输出    : 无
* 返回值  : 无
* 说明    : 本过程先调用S_rx_process()从W5200的端口接收数据缓冲区读取数据,
*			然后将读取的数据从Rx_Buffer拷贝到Temp_Buffer缓冲区进行处理。
*			处理完毕，将数据从Temp_Buffer拷贝到Tx_Buffer缓冲区。调用S_tx_process()
*			发送数据。
*******************************************************************************/
void Process_Socket_Data(SOCKET s)
{
	unsigned short size;
	unsigned short i;
	size=S_rx_process(s);
	//再次开始组帧
	for (i=0;i<size;i++)
	{
		RXCmdBuf[rxIndex]=Rx_Buffer[i];
		if (rxIndex==0)
		{
			if (RXCmdBuf[rxIndex]==0xBA)
			{
				rxIndex++;
			}
			else
			{
				rxIndex=0;
			}
		}
		else
			if (rxIndex==1)
			{
				if (RXCmdBuf[rxIndex]==0xBA)
				{
					rxIndex++;
				}
				else
				{
					rxIndex==0;
				}
			}
			else// 数据帧头接收完毕 开始接收数据
			{
				if(rxIndex>=2)
				{
					rxDataLen=RXCmdBuf[2];
					if (rxDataLen<rxIndex+1)//接收数据出错 重新接收
					{
						rxIndex=0;
						return;
					}
				}
				//检测是否是帧尾
				if (RXCmdBuf[rxIndex]==0xBC)
				{
					//判断数据帧长度是否合法
					rxDataLen=RXCmdBuf[2];
					if (rxDataLen == rxIndex+1)
					{
						//数据帧接收完成，开始处理此帧数据
						rxIndex=0;
						ReceiveDataProcess();
					}
					else
					{
						//如果数据不合法 重新接收数据
						rxIndex=0;
					}
				}
				else
				{
					if (rxIndex<rxDataLen)
					{
						rxIndex++;
					}
					else
					{
						rxIndex=0;
					}
				}
			}
	}
	memcpy(Tx_Buffer, Rx_Buffer, size);			
	S_tx_process(s, size);
	USART2_Puts("Jas Process_Socket_Data \n");
} 



/*******************************************************************************
* 函数名  : Timer2_Init_Config
* 描述    : Timer2初始化配置
* 输入    : 无
* 输出    : 无
* 返回    : 无 
* 说明    : 无
*******************************************************************************/
void Timer2_Init_Config(void)
{
	TIM_TimeBaseInitTypeDef TIM_TimeBaseStructure;
	
	RCC_APB1PeriphClockCmd(RCC_APB1Periph_TIM2, ENABLE);		//使能Timer2时钟
	
	TIM_TimeBaseStructure.TIM_Period = 9;						//设置在下一个更新事件装入活动的自动重装载寄存器周期的值(计数到10为1ms)
	TIM_TimeBaseStructure.TIM_Prescaler = 7199;					//设置用来作为TIMx时钟频率除数的预分频值(10KHz的计数频率)
	TIM_TimeBaseStructure.TIM_ClockDivision = TIM_CKD_DIV1;		//设置时钟分割:TDTS = TIM_CKD_DIV1
	TIM_TimeBaseStructure.TIM_CounterMode = TIM_CounterMode_Up;	//TIM向上计数模式
	TIM_TimeBaseInit(TIM2, &TIM_TimeBaseStructure);				//根据TIM_TimeBaseInitStruct中指定的参数初始化TIMx的时间基数单位
	 
	TIM_ITConfig(TIM2, TIM_IT_Update, ENABLE ); 				//使能TIM2指定的中断
	
	TIM_Cmd(TIM2, ENABLE);  									//使能TIMx外设
}

/*******************************************************************************
* 函数名  : TIM2_IRQHandler
* 描述    : 定时器2中断断服务函数
* 输入    : 无
* 输出    : 无
* 返回    : 无 
* 说明    : 无
*******************************************************************************/
void TIM2_IRQHandler(void)
{
	if(TIM_GetITStatus(TIM2, TIM_IT_Update) == SET)
	{
		TIM_ClearITPendingBit(TIM2, TIM_IT_Update);
		Timer2_Counter++;
		W5200_Send_Delay_Counter++;
//		W5200_Socket_Set();//W5200端口初始化配置
	}
}
/*******************************************************************************
* 函数名  : Delay
* 描述    : 延时函数(ms)
* 输入    : d:延时系数，单位为毫秒
* 输出    : 无
* 返回    : 无 
* 说明    : 延时是利用Timer2定时器产生的1毫秒的计数来实现的
*******************************************************************************/
void Delay(unsigned int d)
{
	Timer2_Counter=0; 
	while(Timer2_Counter < d);
}
void EXTI4_Configuration(void)
{
  EXTI_InitTypeDef  EXTI_InitStructure;


  /* Configure EXTI Line3 to generate an interrupt on falling edge */  
  EXTI_InitStructure.EXTI_Line = EXTI_Line4;                   //外部中断通道3
  EXTI_InitStructure.EXTI_Mode = EXTI_Mode_Interrupt;
  EXTI_InitStructure.EXTI_Trigger = EXTI_Trigger_Falling;    //下降沿触发
  EXTI_InitStructure.EXTI_LineCmd = ENABLE;        //使能
  EXTI_Init(&EXTI_InitStructure);
  GPIO_EXTILineConfig(GPIO_PortSourceGPIOC, GPIO_PinSource4);  //连接到W5200外部中断
}

/*******************************************************************************
* 函数名  : EXTI4_IRQHandler
* 描述    : 中断线4中断服务函数(W5200 INT引脚中断服务函数)
* 输入    : 无
* 输出    : 无
* 返回值  : 无
* 说明    : 无
*******************************************************************************/
void EXTI4_IRQHandler(void)
{
	if(EXTI_GetITStatus(EXTI_Line4) != RESET)
	{
		EXTI_ClearITPendingBit(EXTI_Line4);
	//	W5200_Interrupt=1;
		W5200_Interrupt_Process();//W5200中断处理程序框架
		if((S0_Data & S_RECEIVE) == S_RECEIVE)//如果Socket0接收到数据
		{
			S0_Data&=~S_RECEIVE;
			Process_Socket_Data(0);//W5200接收并发送接收到的数据
		}
		W5200_Socket_Set();//W5200端口初始化配置
	}
}
void ReceiveDataProcess()
{
	unsigned char FuncByte;
	unsigned char DOChannelNo;
	unsigned char DOValue;
	FuncByte = RXCmdBuf[3];
	switch (FuncByte)
	{
	case 0xF0:	//文件头 
		break;
	case 0xF1:
		break;
	case 0xF2:  //文件接收结束
		break;
	case 0xC1:	//数字量输出  
		/*
			数据帧格式：BA BA + Len(1 byte)+Fun(1 byte)+ChannelNo(1 byte)+Value(1 byte)+BC
					Eg: BA BA 07 C1 01 01 BC
		*/
		DOChannelNo=RXCmdBuf[4];
		DOValue = RXCmdBuf[5];
		DOControl(DOChannelNo,DOValue);
		break;
	}
	USART2_Puts("ReceiveDataProcess\n");
}


