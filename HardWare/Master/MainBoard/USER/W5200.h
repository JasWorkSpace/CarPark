#ifndef	_W5200_H_
#define	_W5200_H_

/*-----------------------------------------------------------------------------*/
/*----------------- W5200 Register definition(W5200�Ĵ�������)-----------------*/
/*-----------------------------------------------------------------------------*/

#define	COMMON_BASE	0x0000		//Base address(������Ҫ�޸Ļ���ַ)

/*-----------------------------------------------------------------------------*/
/*-------------------- Common register(W5200����Ĵ�������)--------------------*/
/*-----------------------------------------------------------------------------*/

#define MR				(COMMON_BASE + 0x0000)//ģʽ�Ĵ�����ַ 
#define GAR0			(COMMON_BASE + 0x0001)//����IP��ַ�Ĵ�����ַ 
#define SUBR0			(COMMON_BASE + 0x0005)//��������Ĵ�����ַ 
#define SHAR0			(COMMON_BASE + 0x0009)//���������ַ(MAC)�Ĵ�����ַ	
#define SIPR0			(COMMON_BASE + 0x000F)//����IP��ַ�Ĵ���   
#define IR				(COMMON_BASE + 0x0015)//�жϼĴ��� 
#define IR2				(COMMON_BASE + 0x0034)//W5200 SOCKET�жϼĴ��� 
#define PHY				(COMMON_BASE + 0x0035)//W5200�����״̬�Ĵ���
#define IMR2			(COMMON_BASE + 0x0016)//�ж����μĴ���2	
#define IMR1			(COMMON_BASE + 0x0036)//�ж�����Ĵ���	
#define RTR				(COMMON_BASE + 0x0017)//�ط�ʱ��Ĵ���	
#define RCR				(COMMON_BASE + 0x0019)//�ط������Ĵ���	
#define PATR0			(COMMON_BASE + 0x001C)//PPPoEģʽ�������֤ģʽ	
#define PPPALGO         (COMMON_BASE + 0x001E)//PPPoEģʽ����֤�㷨	 
#define PTIMER 		    (COMMON_BASE + 0x0028)//PPP ���ӿ���Э������ʱ�Ĵ��� 
#define PMAGIC 		    (COMMON_BASE + 0x0029)//PPP ���ӿ���Э������Ĵ��� 
#define VERSIONR		(COMMON_BASE + 0x001F)//W5200оƬ�汾�Ĵ���   
#define UIPR0			(COMMON_BASE + 0x002A)//UDPĿ��IP��ַģ�� 
#define UPORT0			(COMMON_BASE + 0x002E)//UDPģʽĿ�Ķ˿ڼĴ���  
#define INTLEVEL0		(COMMON_BASE + 0x0030)//�͵�ƽ�ж϶�ʱ���Ĵ��� 
#define INTLEVEL1		(COMMON_BASE + 0x0031) 


#define CH_SIZE		        0x0100//�˿ڼĴ�����ʼ��ַƫ����
#define CH_BASE             (COMMON_BASE + 0x4000)//�˿ڼĴ�����ʼ��ַ
#define Sn_MR(ch)		    (CH_BASE + ch * CH_SIZE + 0x0000)//Socket n-th ģʽ�Ĵ���			   
#define Sn_CR(ch)			(CH_BASE + ch * CH_SIZE + 0x0001)//Socket n-th ����Ĵ���			   
#define Sn_IR(ch)			(CH_BASE + ch * CH_SIZE + 0x0002)//Socket n-th�жϼĴ���			   
#define Sn_SR(ch)			(CH_BASE + ch * CH_SIZE + 0x0003)//Socket n-th״̬�Ĵ���			   
#define Sn_PORT0(ch)		(CH_BASE + ch * CH_SIZE + 0x0004)//Socket n-thԴ�˿ڼĴ���	   		   
#define Sn_DHAR0(ch)		(CH_BASE + ch * CH_SIZE + 0x0006)//Socket n-thĿ��MAC��ַ�Ĵ���		   
#define Sn_DIPR0(ch)		(CH_BASE + ch * CH_SIZE + 0x000C)//Socket n-thĿ��IP��ַ�Ĵ���		   
#define Sn_DPORT0(ch)		(CH_BASE + ch * CH_SIZE + 0x0010)//Socket n-thĿ��˿ڼĴ���		   
#define Sn_MSSR0(ch)		(CH_BASE + ch * CH_SIZE + 0x0012)//Socket n-th���ֶμĴ��� 		   
#define Sn_PROTO(ch)		(CH_BASE + ch * CH_SIZE + 0x0014)//Socket n-th IPЭ��Ĵ���			   
#define Sn_TOS(ch)			(CH_BASE + ch * CH_SIZE + 0x0015)//Socket n-th IP�������ͼĴ���		   
#define Sn_TTL(ch)			(CH_BASE + ch * CH_SIZE + 0x0016)//Socket n-th IP����ʱ��Ĵ���		   
#define Sn_RXMEM_SIZE(ch)	(CH_BASE + ch * CH_SIZE + 0x001E)//Socket n-th ���Ճȴ��С�Ĵ���	   
#define Sn_TXMEM_SIZE(ch)	(CH_BASE + ch * CH_SIZE + 0x001F)//Socket n-th �����ڴ��С�Ĵ���	   
#define Sn_TX_FSR0(ch)		(CH_BASE + ch * CH_SIZE + 0x0020)//Socket n-th ����ռ��С�Ĵ���
#define Sn_TX_RD0(ch)		(CH_BASE + ch * CH_SIZE + 0x0022)//Socket n-th �����ָ��Ĵ���
#define Sn_TX_WR0(ch)		(CH_BASE + ch * CH_SIZE + 0x0024)//Socket n-th ����дָ��Ĵ���
#define Sn_RX_RSR0(ch)		(CH_BASE + ch * CH_SIZE + 0x0026)//�ѽ��մ�С�Ĵ���
#define Sn_RX_RD0(ch)		(CH_BASE + ch * CH_SIZE + 0x0028)//Socket  n-th ���ն�ָ��Ĵ���
#define Sn_RX_WR0(ch)		(CH_BASE + ch * CH_SIZE + 0x002A)//Socket  n-th ����дָ��Ĵ���
#define Sn_IMR(ch)			(CH_BASE + ch * CH_SIZE + 0x002C)//Socket n-th �ж�����Ĵ���
#define Sn_FRAG(ch)			(CH_BASE + ch * CH_SIZE + 0x002D)//Socket n-th�ֶμĴ���
#define Sn_KEEP_TIMER(ch)	(CH_BASE + ch * CH_SIZE + 0x002F)//����ʱ��Ĵ���

#define TX_MEM		COMMON_BASE+0x8000//�������ݵ���ʼ��ַ
#define RX_MEM		COMMON_BASE+0xc000//�������ݵ���ʼ��ַ

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

#define S_RX_SIZE	2048	/*����Socket���ջ������Ĵ�С�����Ը���W5200_RMSR�������޸� */
#define S_TX_SIZE	2048  	/*����Socket���ͻ������Ĵ�С�����Ը���W5200_TMSR�������޸� */

/***************----- W51200 GPIO���� -----***************/
#define W5200_SCS		GPIO_Pin_4	//����W5200��CS����	 
#define W5200_SCS_PORT	GPIOA
	
#define W5200_RST		GPIO_Pin_5	//����W5200��RST����
#define W5200_RST_PORT	GPIOC

#define W5200_INT		GPIO_Pin_4	//����W5200��INT����
#define W5200_INT_PORT	GPIOC

/***************----- ��������������� -----***************/
extern unsigned char Gateway_IP[4];	//����IP��ַ 
extern unsigned char Sub_Mask[4];	//�������� 
extern unsigned char Phy_Addr[6];	//�����ַ(MAC) 
extern unsigned char IP_Addr[4];	//����IP��ַ 

extern unsigned char S0_Port[2];	//�˿�0�Ķ˿ں�(5000) 
extern unsigned char S0_DIP[4];		//�˿�0Ŀ��IP��ַ 
extern unsigned char S0_DPort[2];	//�˿�0Ŀ�Ķ˿ں�(6000) 

extern unsigned char UDP_DIPR[4];	//UDP(�㲥)ģʽ,Ŀ������IP��ַ
extern unsigned char UDP_DPORT[2];	//UDP(�㲥)ģʽ,Ŀ�������˿ں�

/***************----- �˿ڵ�����ģʽ -----***************/
extern unsigned char S0_Mode;	//�˿�0������ģʽ,0:TCP������ģʽ,1:TCP�ͻ���ģʽ,2:UDP(�㲥)ģʽ
#define TCP_SERVER		0x00	//TCP������ģʽ
#define TCP_CLIENT		0x01	//TCP�ͻ���ģʽ 
#define UDP_MODE		0x02	//UDP(�㲥)ģʽ 

/***************----- �˿ڵ�����״̬ -----***************/
extern unsigned char S0_State;	//�˿�0״̬��¼,1:�˿���ɳ�ʼ��,2�˿��������(����������������) 
#define S_INIT			0x01	//�˿���ɳ�ʼ�� 
#define S_CONN			0x02	//�˿��������,���������������� 

/***************----- �˿��շ����ݵ�״̬ -----***************/
extern unsigned char S0_Data;		//�˿�0���պͷ������ݵ�״̬,1:�˿ڽ��յ�����,2:�˿ڷ���������� 
#define S_RECEIVE		0x01		//�˿ڽ��յ�һ�����ݰ� 
#define S_TRANSMITOK	0x02		//�˿ڷ���һ�����ݰ���� 

/***************----- �˿����ݻ����� -----***************/
extern unsigned char Rx_Buffer[2000];	//�˿ڽ������ݻ����� 
extern unsigned char Tx_Buffer[2000];	//�˿ڷ������ݻ����� 

extern unsigned char W5200_Interrupt;	//W52200�жϱ�־(0:���ж�,1:���ж�)
typedef unsigned char SOCKET;			//�Զ���˿ں���������

extern void Delay(unsigned int d);//��ʱ����(ms)
extern void W5200_GPIO_Configuration(void);//W5200 GPIO��ʼ������
extern void W5200_NVIC_Configuration(void);//W5200 ���������ж����ȼ�����
extern void SPI_Configuration(void);//W5200 SPI��ʼ������(STM32 SPI1)
extern void W5200_Hardware_Reset(void);//Ӳ����λW5200
extern void W5200_Init(void);//��ʼ��W5200�Ĵ�������
extern unsigned char Detect_Gateway(void);//������ط�����
extern void Socket_Init(SOCKET s);//ָ��Socket(0~3)��ʼ��
extern unsigned char Socket_Connect(SOCKET s);//����ָ��Socket(0~3)Ϊ�ͻ�����Զ�̷���������
extern unsigned char Socket_Listen(SOCKET s);//����ָ��Socket(0~3)��Ϊ�������ȴ�Զ������������
extern unsigned char Socket_UDP(SOCKET s);//����ָ��Socket(0~3)ΪUDPģʽ
extern unsigned short S_rx_process(SOCKET s);//ָ��Socket(0~3)�������ݴ���
extern unsigned char S_tx_process(SOCKET s, unsigned int size);//ָ��Socket(0~3)�������ݴ���
extern void W5200_Interrupt_Process(void);//W5200�жϴ��������
void RCC_Configuration(void);		//����ϵͳʱ��Ϊ72MHZ(������Ը�����Ҫ��)
void NVIC_Configuration(void);		//STM32�ж�������������
void Timer2_Init_Config(void);		//Timer2��ʼ������
void System_Initialization(void);	//STM32ϵͳ��ʼ������(��ʼ��STM32ʱ�Ӽ�����)
void Delay(unsigned int d);			//��ʱ����(ms)
void EXTI4_Configuration(void);
void ReceiveDataProcess();

//�û����庯��

#endif

