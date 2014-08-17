#ifndef	_W5200_H_
#define	_W5200_H_

/*-----------------------------------------------------------------------------*/
/*----------------- W5200 Register definition(W5200寄存器定义)-----------------*/
/*-----------------------------------------------------------------------------*/

#define	COMMON_BASE	0x0000		//Base address(根据需要修改基地址)

/*-----------------------------------------------------------------------------*/
/*-------------------- Common register(W5200命令寄存器定义)--------------------*/
/*-----------------------------------------------------------------------------*/

#define MR				(COMMON_BASE + 0x0000)//模式寄存器地址 
#define GAR0			(COMMON_BASE + 0x0001)//网关IP地址寄存器地址 
#define SUBR0			(COMMON_BASE + 0x0005)//子网掩码寄存器地址 
#define SHAR0			(COMMON_BASE + 0x0009)//本机物理地址(MAC)寄存器地址	
#define SIPR0			(COMMON_BASE + 0x000F)//本机IP地址寄存器   
#define IR				(COMMON_BASE + 0x0015)//中断寄存器 
#define IR2				(COMMON_BASE + 0x0034)//W5200 SOCKET中断寄存器 
#define PHY				(COMMON_BASE + 0x0035)//W5200物理层状态寄存器
#define IMR2			(COMMON_BASE + 0x0016)//中断屏蔽寄存器2	
#define IMR1			(COMMON_BASE + 0x0036)//中断掩码寄存器	
#define RTR				(COMMON_BASE + 0x0017)//重发时间寄存器	
#define RCR				(COMMON_BASE + 0x0019)//重发计数寄存器	
#define PATR0			(COMMON_BASE + 0x001C)//PPPoE模式的身份验证模式	
#define PPPALGO         (COMMON_BASE + 0x001E)//PPPoE模式的认证算法	 
#define PTIMER 		    (COMMON_BASE + 0x0028)//PPP 连接控制协议请求定时寄存器 
#define PMAGIC 		    (COMMON_BASE + 0x0029)//PPP 连接控制协议幻数寄存器 
#define VERSIONR		(COMMON_BASE + 0x001F)//W5200芯片版本寄存器   
#define UIPR0			(COMMON_BASE + 0x002A)//UDP目的IP地址模块 
#define UPORT0			(COMMON_BASE + 0x002E)//UDP模式目的端口寄存器  
#define INTLEVEL0		(COMMON_BASE + 0x0030)//低电平中断定时器寄存器 
#define INTLEVEL1		(COMMON_BASE + 0x0031) 


#define CH_SIZE		        0x0100//端口寄存器起始地址偏移量
#define CH_BASE             (COMMON_BASE + 0x4000)//端口寄存器起始地址
#define Sn_MR(ch)		    (CH_BASE + ch * CH_SIZE + 0x0000)//Socket n-th 模式寄存器			   
#define Sn_CR(ch)			(CH_BASE + ch * CH_SIZE + 0x0001)//Socket n-th 命令寄存器			   
#define Sn_IR(ch)			(CH_BASE + ch * CH_SIZE + 0x0002)//Socket n-th中断寄存器			   
#define Sn_SR(ch)			(CH_BASE + ch * CH_SIZE + 0x0003)//Socket n-th状态寄存器			   
#define Sn_PORT0(ch)		(CH_BASE + ch * CH_SIZE + 0x0004)//Socket n-th源端口寄存器	   		   
#define Sn_DHAR0(ch)		(CH_BASE + ch * CH_SIZE + 0x0006)//Socket n-th目标MAC地址寄存器		   
#define Sn_DIPR0(ch)		(CH_BASE + ch * CH_SIZE + 0x000C)//Socket n-th目标IP地址寄存器		   
#define Sn_DPORT0(ch)		(CH_BASE + ch * CH_SIZE + 0x0010)//Socket n-th目标端口寄存器		   
#define Sn_MSSR0(ch)		(CH_BASE + ch * CH_SIZE + 0x0012)//Socket n-th最大分段寄存器 		   
#define Sn_PROTO(ch)		(CH_BASE + ch * CH_SIZE + 0x0014)//Socket n-th IP协议寄存器			   
#define Sn_TOS(ch)			(CH_BASE + ch * CH_SIZE + 0x0015)//Socket n-th IP服务类型寄存器		   
#define Sn_TTL(ch)			(CH_BASE + ch * CH_SIZE + 0x0016)//Socket n-th IP生存时间寄存器		   
#define Sn_RXMEM_SIZE(ch)	(CH_BASE + ch * CH_SIZE + 0x001E)//Socket n-th 接收內存大小寄存器	   
#define Sn_TXMEM_SIZE(ch)	(CH_BASE + ch * CH_SIZE + 0x001F)//Socket n-th 传输内存大小寄存器	   
#define Sn_TX_FSR0(ch)		(CH_BASE + ch * CH_SIZE + 0x0020)//Socket n-th 传输空间大小寄存器
#define Sn_TX_RD0(ch)		(CH_BASE + ch * CH_SIZE + 0x0022)//Socket n-th 传输读指针寄存器
#define Sn_TX_WR0(ch)		(CH_BASE + ch * CH_SIZE + 0x0024)//Socket n-th 传输写指针寄存器
#define Sn_RX_RSR0(ch)		(CH_BASE + ch * CH_SIZE + 0x0026)//已接收大小寄存器
#define Sn_RX_RD0(ch)		(CH_BASE + ch * CH_SIZE + 0x0028)//Socket  n-th 接收读指针寄存器
#define Sn_RX_WR0(ch)		(CH_BASE + ch * CH_SIZE + 0x002A)//Socket  n-th 接收写指针寄存器
#define Sn_IMR(ch)			(CH_BASE + ch * CH_SIZE + 0x002C)//Socket n-th 中断掩码寄存器
#define Sn_FRAG(ch)			(CH_BASE + ch * CH_SIZE + 0x002D)//Socket n-th分段寄存器
#define Sn_KEEP_TIMER(ch)	(CH_BASE + ch * CH_SIZE + 0x002F)//保持时间寄存器

#define TX_MEM		COMMON_BASE+0x8000//发送数据的起始地址
#define RX_MEM		COMMON_BASE+0xc000//接收数据的起始地址

/* MODE register values */
#define MR_RST			0x80 /**< reset */
#define MR_WOL			0x20 /**< Wake on Lan */
#define MR_PB			0x10 /**< ping block */
#define MR_PPPOE		0x08 /**< enable pppoe */
#define MR_LB  		    0x04 /**< little or big endian selector in indirect mode */
#define MR_AI			0x02 /**< auto-increment in indirect mode */
#define MR_IND			0x01 /**< enable indirect mode */

/* IR register values */
#define IR_CONFLICT	    0x80 /**< check ip confict */
#define IR_UNREACH	    0x40 /**< get the destination unreachable message in UDP sending */
#define IR_PPPoE		0x20 /**< get the PPPoE close message */
#define IR_MAGIC		0x10 /**< get the magic packet interrupt */
#define IR_SOCK(ch)	    (0x01 << ch) /**< check socket interrupt */

/* Sn_MR values */
#define Sn_MR_CLOSE		0x00		/**< unused socket */
#define Sn_MR_TCP		0x01		/**< TCP */
#define Sn_MR_UDP		0x02		/**< UDP */
#define Sn_MR_IPRAW	    0x03		/**< IP LAYER RAW SOCK */
#define Sn_MR_MACRAW	0x04		/**< MAC LAYER RAW SOCK */
#define Sn_MR_PPPOE		0x05		/**< PPPoE */
#define Sn_MR_ND		0x20		/**< No Delayed Ack(TCP) flag */
#define Sn_MR_MULTI		0x80		/**< support multicating */

/* Sn_CR values */
#define Sn_CR_OPEN		0x01		/**< initialize or open socket */
#define Sn_CR_LISTEN	0x02		/**< wait connection request in tcp mode(Server mode) */
#define Sn_CR_CONNECT	0x04		/**< send connection request in tcp mode(Client mode) */
#define Sn_CR_DISCON	0x08		/**< send closing reqeuset in tcp mode */
#define Sn_CR_CLOSE		0x10		/**< close socket */
#define Sn_CR_SEND		0x20		/**< update txbuf pointer, send data */
#define Sn_CR_SEND_MAC	0x21		/**< send data with MAC address, so without ARP process */
#define Sn_CR_SEND_KEEP 0x22		/**<  send keep alive message */
#define Sn_CR_RECV		0x40		/**< update rxbuf pointer, recv data */

#ifdef __DEF_IINCHIP_PPP__
	#define Sn_CR_PCON		0x23		 
	#define Sn_CR_PDISCON	0x24		 
	#define Sn_CR_PCR		0x25		 
	#define Sn_CR_PCN		0x26		
	#define Sn_CR_PCJ		0x27		
#endif

/* Sn_IR values */
#ifdef __DEF_IINCHIP_PPP__
	#define Sn_IR_PRECV		0x80		
	#define Sn_IR_PFAIL		0x40		
	#define Sn_IR_PNEXT		0x20		
#endif
#define Sn_IR_SEND_OK		0x10		/**< complete sending */
#define Sn_IR_TIMEOUT		0x08		/**< assert timeout */
#define Sn_IR_RECV			0x04		/**< receiving data */
#define Sn_IR_DISCON		0x02		/**< closed socket */
#define Sn_IR_CON			0x01		/**< established connection */

/* Sn_SR values */
#define SOCK_CLOSED			0x00		/**< closed */
#define SOCK_INIT 			0x13		/**< init state */
#define SOCK_LISTEN			0x14		/**< listen state */
#define SOCK_SYNSENT	   	0x15		/**< connection state */
#define SOCK_SYNRECV		0x16		/**< connection state */
#define SOCK_ESTABLISHED	0x17		/**< success to connect */
#define SOCK_FIN_WAIT		0x18		/**< closing state */
#define SOCK_CLOSING		0x1A		/**< closing state */
#define SOCK_TIME_WAIT		0x1B		/**< closing state */
#define SOCK_CLOSE_WAIT		0x1C		/**< closing state */
#define SOCK_LAST_ACK		0x1D		/**< closing state */
#define SOCK_UDP			0x22		/**< udp socket */
#define SOCK_IPRAW			0x32		/**< ip raw mode socket */
#define SOCK_MACRAW			0x42		/**< mac raw mode socket */
#define SOCK_PPPOE			0x5F		/**< pppoe socket */

/* IP PROTOCOL */
#define IPPROTO_IP              0           /**< Dummy for IP */
#define IPPROTO_ICMP            1           /**< Control message protocol */
#define IPPROTO_IGMP            2           /**< Internet group management protocol */
#define IPPROTO_GGP             3           /**< Gateway^2 (deprecated) */
#define IPPROTO_TCP             6           /**< TCP */
#define IPPROTO_PUP             12          /**< PUP */
#define IPPROTO_UDP             17          /**< UDP */
#define IPPROTO_IDP             22          /**< XNS idp */
#define IPPROTO_ND              77          /**< UNOFFICIAL net disk protocol */
#define IPPROTO_RAW             255         /**< Raw IP packet */

#define TRUE	0xff
#define FALSE	0x00

#define S_RX_SIZE	2048	/*定义Socket接收缓冲区的大小，可以根据W5200_RMSR的设置修改 */
#define S_TX_SIZE	2048  	/*定义Socket发送缓冲区的大小，可以根据W5200_TMSR的设置修改 */

/***************----- W51200 GPIO定义 -----***************/
#define W5200_SCS		GPIO_Pin_4	//定义W5200的CS引脚	 
#define W5200_SCS_PORT	GPIOA
	
#define W5200_RST		GPIO_Pin_5	//定义W5200的RST引脚
#define W5200_RST_PORT	GPIOC

#define W5200_INT		GPIO_Pin_4	//定义W5200的INT引脚
#define W5200_INT_PORT	GPIOC

/***************----- 网络参数变量定义 -----***************/
extern unsigned char Gateway_IP[4];	//网关IP地址 
extern unsigned char Sub_Mask[4];	//子网掩码 
extern unsigned char Phy_Addr[6];	//物理地址(MAC) 
extern unsigned char IP_Addr[4];	//本机IP地址 

extern unsigned char S0_Port[2];	//端口0的端口号(5000) 
extern unsigned char S0_DIP[4];		//端口0目的IP地址 
extern unsigned char S0_DPort[2];	//端口0目的端口号(6000) 

extern unsigned char UDP_DIPR[4];	//UDP(广播)模式,目的主机IP地址
extern unsigned char UDP_DPORT[2];	//UDP(广播)模式,目的主机端口号

/***************----- 端口的运行模式 -----***************/
extern unsigned char S0_Mode;	//端口0的运行模式,0:TCP服务器模式,1:TCP客户端模式,2:UDP(广播)模式
#define TCP_SERVER		0x00	//TCP服务器模式
#define TCP_CLIENT		0x01	//TCP客户端模式 
#define UDP_MODE		0x02	//UDP(广播)模式 

/***************----- 端口的运行状态 -----***************/
extern unsigned char S0_State;	//端口0状态记录,1:端口完成初始化,2端口完成连接(可以正常传输数据) 
#define S_INIT			0x01	//端口完成初始化 
#define S_CONN			0x02	//端口完成连接,可以正常传输数据 

/***************----- 端口收发数据的状态 -----***************/
extern unsigned char S0_Data;		//端口0接收和发送数据的状态,1:端口接收到数据,2:端口发送数据完成 
#define S_RECEIVE		0x01		//端口接收到一个数据包 
#define S_TRANSMITOK	0x02		//端口发送一个数据包完成 

/***************----- 端口数据缓冲区 -----***************/
extern unsigned char Rx_Buffer[2000];	//端口接收数据缓冲区 
extern unsigned char Tx_Buffer[2000];	//端口发送数据缓冲区 

extern unsigned char W5200_Interrupt;	//W52200中断标志(0:无中断,1:有中断)
typedef unsigned char SOCKET;			//自定义端口号数据类型

extern void Delay(unsigned int d);//延时函数(ms)
extern void W5200_GPIO_Configuration(void);//W5200 GPIO初始化配置
extern void W5200_NVIC_Configuration(void);//W5200 接收引脚中断优先级设置
extern void SPI_Configuration(void);//W5200 SPI初始化配置(STM32 SPI1)
extern void W5200_Hardware_Reset(void);//硬件复位W5200
extern void W5200_Init(void);//初始化W5200寄存器函数
extern unsigned char Detect_Gateway(void);//检查网关服务器
extern void Socket_Init(SOCKET s);//指定Socket(0~3)初始化
extern unsigned char Socket_Connect(SOCKET s);//设置指定Socket(0~3)为客户端与远程服务器连接
extern unsigned char Socket_Listen(SOCKET s);//设置指定Socket(0~3)作为服务器等待远程主机的连接
extern unsigned char Socket_UDP(SOCKET s);//设置指定Socket(0~3)为UDP模式
extern unsigned short S_rx_process(SOCKET s);//指定Socket(0~3)接收数据处理
extern unsigned char S_tx_process(SOCKET s, unsigned int size);//指定Socket(0~3)发送数据处理
extern void W5200_Interrupt_Process(void);//W5200中断处理程序框架
void RCC_Configuration(void);		//设置系统时钟为72MHZ(这个可以根据需要改)
void NVIC_Configuration(void);		//STM32中断向量表配配置
void Timer2_Init_Config(void);		//Timer2初始化配置
void System_Initialization(void);	//STM32系统初始化函数(初始化STM32时钟及外设)
void Delay(unsigned int d);			//延时函数(ms)
void EXTI4_Configuration(void);
void ReceiveDataProcess();

//用户定义函数

#endif

