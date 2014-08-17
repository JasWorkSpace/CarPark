/**********************************************************************************
 * �ļ���  ��W5200.c
 * ����    ��W5200 ����������         
 * ��汾  ��ST_v3.5
 * ����    ������ͨ��ģ�鿪���Ŷ�
 * ����    ��http://nirenelec.blog.163.com
 * �Ա�    ��http://nirenelec.taobao.com
 * ˵����
 * 		���������5�����֣�
 *   	1. W5200��ʼ��
 *   	2. W5200��Socket��ʼ��
 *   	3. Socket����
 *   	   ���Socket����ΪTCP������ģʽ�������Socket_Listen()����,W5200��������״̬��ֱ��Զ�̿ͻ����������ӡ�
 *   	   ���Socket����ΪTCP�ͻ���ģʽ�������Socket_Connect()������
 *   	                                  ÿ����һ��Socket_Connect(s)����������һ�����ӣ�
 *   	                                  ������Ӳ��ɹ����������ʱ�жϣ�Ȼ������ٵ��øú����������ӡ�
 *   	   ���Socket����ΪUDPģʽ,�����Socket_UDP����
 *   	4. Socket���ݽ��պͷ���
 *   	5. W5200�жϴ���

 *   ��W5200Ϊ������ģʽ�ĵ��ù��̣�W5200_Init()-->Socket_Init(s)-->Socket_Listen(s)�����ù��̼���ɣ��ȴ��ͻ��˵����ӡ�
 *   ��W5200Ϊ�ͻ���ģʽ�ĵ��ù��̣�W5200_Init()-->Socket_Init(s)-->Socket_Connect(s)�����ù��̼���ɣ�����Զ�̷��������ӡ�
 *   ��W5200ΪUDPģʽ�ĵ��ù��̣�W5200_Init()-->Socket_Init(s)-->Socket_UDP(s)�����ù��̼���ɣ�������Զ������UDPͨ�š�

 *   W5200���������ӳɹ�����ֹ���ӡ��������ݡ��������ݡ���ʱ���¼��������Դ��ж�״̬�л�á�
**********************************************************************************/

#include "stm32f10x.h"
#include "stm32f10x_spi.h"				
#include "W5200.h"	
#include "USART.h"
#include "GPIO.h"

/***************----- ��������������� -----***************/
unsigned char Gateway_IP[4];//����IP��ַ 
unsigned char Sub_Mask[4];	//�������� 
unsigned char Phy_Addr[6];	//�����ַ(MAC) 
unsigned char IP_Addr[4];	//����IP��ַ 

unsigned char S0_Port[2];	//�˿�0�Ķ˿ں�(5000) 
unsigned char S0_DIP[4];	//�˿�0Ŀ��IP��ַ 
unsigned char S0_DPort[2];	//�˿�0Ŀ�Ķ˿ں�(6000) 

unsigned char UDP_DIPR[4];	//UDP(�㲥)ģʽ,Ŀ������IP��ַ
unsigned char UDP_DPORT[2];	//UDP(�㲥)ģʽ,Ŀ�������˿ں�

/***************----- �˿ڵ�����ģʽ -----***************/
unsigned char S0_Mode =3;	//�˿�0������ģʽ,0:TCP������ģʽ,1:TCP�ͻ���ģʽ,2:UDP(�㲥)ģʽ
#define TCP_SERVER	0x00	//TCP������ģʽ
#define TCP_CLIENT	0x01	//TCP�ͻ���ģʽ 
#define UDP_MODE	0x02	//UDP(�㲥)ģʽ 

/***************----- �˿ڵ�����״̬ -----***************/
unsigned char S0_State =0;	//�˿�0״̬��¼,1:�˿���ɳ�ʼ��,2�˿��������(����������������) 
#define S_INIT		0x01	//�˿���ɳ�ʼ�� 
#define S_CONN		0x02	//�˿��������,���������������� 

/***************----- �˿��շ����ݵ�״̬ -----***************/
unsigned char S0_Data;		//�˿�0���պͷ������ݵ�״̬,1:�˿ڽ��յ�����,2:�˿ڷ���������� 
#define S_RECEIVE	 0x01	//�˿ڽ��յ�һ�����ݰ� 
#define S_TRANSMITOK 0x02	//�˿ڷ���һ�����ݰ���� 

/***************----- �˿����ݻ����� -----***************/
unsigned char Rx_Buffer[2000];	//�˿ڽ������ݻ����� 
unsigned char Tx_Buffer[2000];	//�˿ڷ������ݻ����� 

unsigned char W5200_Interrupt;	//W5200�жϱ�־(0:���ж�,1:���ж�)

//�������
unsigned char RXCmdBuf[2000];
unsigned int rxDataLen;
unsigned int rxIndex;

/*******************************************************************************
* ������  : W5200_GPIO_Configuration
* ����    : W5200 GPIO��ʼ������
* ����    : ��
* ���    : ��
* ����ֵ  : ��
* ˵��    : ��
*******************************************************************************/
void W5200_GPIO_Configuration(void)
{
	GPIO_InitTypeDef  GPIO_InitStructure;
//	EXTI_InitTypeDef  EXTI_InitStructure;	

	/* W5200_RST���ų�ʼ������(PC5) */
	GPIO_InitStructure.GPIO_Pin  = W5200_RST;
	GPIO_InitStructure.GPIO_Speed=GPIO_Speed_10MHz;
	GPIO_InitStructure.GPIO_Mode = GPIO_Mode_Out_PP;
	GPIO_Init(W5200_RST_PORT, &GPIO_InitStructure);
	GPIO_SetBits(W5200_RST_PORT, W5200_RST);
	
	/* W5200_INT���ų�ʼ������(PC4) */	
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
* ������  : W5200_NVIC_Configuration
* ����    : W5200 ���������ж����ȼ�����
* ����    : ��
* ���    : ��
* ����ֵ  : ��
* ˵��    : ��
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
		
	NVIC_InitStructure.NVIC_IRQChannel = TIM2_IRQn;				//�����ж�������
	NVIC_InitStructure.NVIC_IRQChannelPreemptionPriority = 0;	//�����������ȼ�
	NVIC_InitStructure.NVIC_IRQChannelSubPriority = 0;			//������Ӧ���ȼ�
	NVIC_InitStructure.NVIC_IRQChannelCmd = ENABLE;				//ʹ��NVIC
	NVIC_Init(&NVIC_InitStructure);
}

/*******************************************************************************
* ������  : SPI_Configuration
* ����    : W5200 SPI��ʼ������(STM32 SPI1)
* ����    : ��
* ���    : ��
* ����ֵ  : ��
* ˵��    : ��
*******************************************************************************/
void SPI_Configuration(void)
{
	GPIO_InitTypeDef 	GPIO_InitStructure;
	SPI_InitTypeDef   	SPI_InitStructure;	   

  	RCC_APB2PeriphClockCmd(RCC_APB2Periph_GPIOA | RCC_APB2Periph_SPI1 | RCC_APB2Periph_AFIO, ENABLE);	

	/* ��ʼ��SCK��MISO��MOSI���� */
	GPIO_InitStructure.GPIO_Pin = GPIO_Pin_5 | GPIO_Pin_6 | GPIO_Pin_7;
	GPIO_InitStructure.GPIO_Speed = GPIO_Speed_50MHz;
	GPIO_InitStructure.GPIO_Mode = GPIO_Mode_AF_PP;
	GPIO_Init(GPIOA, &GPIO_InitStructure);
	GPIO_SetBits(GPIOA,GPIO_Pin_5|GPIO_Pin_6|GPIO_Pin_7);

	/* ��ʼ��CS���� */
	GPIO_InitStructure.GPIO_Pin = W5200_SCS;
	GPIO_InitStructure.GPIO_Speed=GPIO_Speed_50MHz;
	GPIO_InitStructure.GPIO_Mode=GPIO_Mode_Out_PP;
	GPIO_Init(W5200_SCS_PORT, &GPIO_InitStructure);
	GPIO_SetBits(W5200_SCS_PORT, W5200_SCS);

	/* ��ʼ������STM32 SPI1 */
	SPI_InitStructure.SPI_Direction=SPI_Direction_2Lines_FullDuplex;	//SPI����Ϊ˫��˫��ȫ˫��
	SPI_InitStructure.SPI_Mode=SPI_Mode_Master;							//����Ϊ��SPI
	SPI_InitStructure.SPI_DataSize=SPI_DataSize_8b;						//SPI���ͽ���8λ֡�ṹ
	SPI_InitStructure.SPI_CPOL=SPI_CPOL_Low;							//ʱ�����յ�
	SPI_InitStructure.SPI_CPHA=SPI_CPHA_1Edge;							//���ݲ����ڵ�1��ʱ����
	SPI_InitStructure.SPI_NSS=SPI_NSS_Soft;								//NSS���ⲿ�ܽŹ���
	SPI_InitStructure.SPI_BaudRatePrescaler=SPI_BaudRatePrescaler_8;	//������Ԥ��ƵֵΪ8
	SPI_InitStructure.SPI_FirstBit=SPI_FirstBit_MSB;					//���ݴ����MSBλ��ʼ
	SPI_InitStructure.SPI_CRCPolynomial=7;								//CRC����ʽΪ7
	SPI_Init(SPI1,&SPI_InitStructure);									//����SPI_InitStruct��ָ���Ĳ�����ʼ������SPI1�Ĵ���

	SPI_Cmd(SPI1,ENABLE);	//STM32ʹ��SPI1
}

/*******************************************************************************
* ������  : SPI_SendByte
* ����    : STM32 SPI1����һ���ֽ����ݣ������ؽ��յ�������
* ����    : dt:�����͵�����
* ���    : ��
* ����ֵ  : STM32 SPI1���յ�������
* ˵��    : ͨ��SPI����һ���ֽڲ����ؽ��յ�һ���ֽڣ����ӳ�����Read_W5200
*			��Write_W5200���ʹ��
*******************************************************************************/
unsigned char SPI_SendByte(unsigned char dt)
{
	while(SPI_I2S_GetFlagStatus(SPI1, SPI_I2S_FLAG_TXE) == RESET); //�ȴ����ݼĴ�����
	SPI_I2S_SendData(SPI1, dt); //ͨ��SPI2�ӿڷ�������
	while(SPI_I2S_GetFlagStatus(SPI1, SPI_I2S_FLAG_RXNE) == RESET); //�ȴ����յ�һ���ֽڵ�����
	return SPI_I2S_ReceiveData(SPI1); //���ؽ��յ�����
}

/*******************************************************************************
* ������  : Read_W5200
* ����    : ��W5200ָ����ַ�ļĴ�������
* ����    : addr:(��ַ)
* ���    : ��
* ����ֵ  : ��ȡ���ļĴ�������
* ˵��    : ��W5200ָ���ĵ�ַ��ȡһ���ֽ�
*******************************************************************************/
unsigned char Read_W5200(unsigned short addr)
{
	unsigned char i;

	GPIO_ResetBits(W5200_SCS_PORT, W5200_SCS); //��W5200��CSΪ�͵�ƽ
	SPI_SendByte(addr/256);	//���͵�ַ��λ
	SPI_SendByte(addr);		//���͵�ַ��λ
	SPI_SendByte(0x00);		//���Ͷ�����
	SPI_SendByte(0x01);		//���Ͷ����ݵĳ���
	i=SPI_SendByte(0);		//����һ�������ݲ���ȡ����
	GPIO_SetBits(W5200_SCS_PORT, W5200_SCS); //��W5200��CSΪ�ߵ�ƽ

	return i; //���ض�ȡ���ļĴ�������
}

/*******************************************************************************
* ������  : Write_W5200	
* ����    : ��W5200ָ����ַ�Ĵ���дһ���ֽ�����
* ����    : addr:(��ַ) dat:(��д������)
* ���    : ��
* ����ֵ  : ��
* ˵��    : ��һ���ֽ�д��W5200ָ���ĵ�ַ
*******************************************************************************/
void Write_W5200(unsigned short addr, unsigned char dat)
{
	GPIO_ResetBits(W5200_SCS_PORT, W5200_SCS); //��W5200��CSΪ�͵�ƽ	
	SPI_SendByte(addr/256);	//���͵�ַ��λ
	SPI_SendByte(addr);		//���͵�ַ��λ
	SPI_SendByte(0x80);		//���ʹ�д������ݳ��ȸ�λ(д�������λΪ1)
	SPI_SendByte(0x01);		//���ʹ�д������ݳ��ȵ�λ,1���ֽ�
	SPI_SendByte(dat);		//д������ 
	GPIO_SetBits(W5200_SCS_PORT, W5200_SCS); //��W5200��CSΪ�ߵ�ƽ 
}

/*******************************************************************************
* ������  : WriteBuff_W5200
* ����    : ��W5200ָ����ַ�Ĵ���дlen���ֽ�����
* ����    : addr:(��ַ) dat:(��д������)
* ���    : ��
* ����ֵ  : ��
* ˵��    : ��һ���ֽ�д��W5200ָ���ĵ�ַ
*******************************************************************************/
unsigned short WriteBuff_W5200(unsigned short addr,unsigned char* buff,unsigned short len)
{
	unsigned short i = 0;
	  
	GPIO_ResetBits(W5200_SCS_PORT, W5200_SCS); //��W5200��CSΪ�͵�ƽ                         	
	SPI_SendByte(addr/256);	//���͵�ַ��λ
	SPI_SendByte(addr);		//���͵�ַ��λ
	i = len|0x8000;			//д�������λΪ1
	SPI_SendByte(i/256);	//���ʹ�д������ݳ��ȸ�λ
	SPI_SendByte(i);		//���ʹ�д������ݳ��ȵ�λ

	for(i=0; i<len; i++)	//ѭ��д��len���ֽڵ�����
	{		
		SPI_SendByte(buff[i]);
	}
	GPIO_SetBits(W5200_SCS_PORT, W5200_SCS); //��W5200��CSΪ�ߵ�ƽ 
	
	return len;	//�������ݵĳ���
}

/*******************************************************************************
* ������  : ReadBuff_W5200	
* ����    : ��W5200ָ����ַ�Ĵ���дlen���ֽ�����
* ����    : addr:(��ַ) dat:(��д������)
* ���    : ��
* ����ֵ  : ��
* ˵��    : ��һ���ֽ�д��W5200ָ���ĵ�ַ
*******************************************************************************/
unsigned short ReadBuff_W5200(unsigned short addr, unsigned char* buff,unsigned short len)
{
	unsigned short i = 0;
		
	GPIO_ResetBits(W5200_SCS_PORT, W5200_SCS); //��W5200��CSΪ�͵�ƽ                         	
	SPI_SendByte(addr/256);	//���͵�ַ��λ
	SPI_SendByte(addr);		//���͵�ַ��λ
	i = len&0x7fff;			//д�������λΪ1
	SPI_SendByte(i/256);	//���ʹ�д������ݳ��ȸ�λ
	SPI_SendByte(i);		//���ʹ�д������ݳ��ȵ�λ		        

	for(i=0; i<len; i++)//ѭ����ȡlen���ֽڵ�����                         
	{
	 	buff[i] = SPI_SendByte(0x00);
		
	}
	GPIO_SetBits(W5200_SCS_PORT, W5200_SCS); //��W5200��CSΪ�ߵ�ƽ 

	return len;
}

/*******************************************************************************
* ������  : W5200_Hardware_Reset
* ����    : Ӳ����λW5200
* ����    : ��
* ���    : ��
* ����ֵ  : ��
* ˵��    : ��
*******************************************************************************/
void W5200_Hardware_Reset(void)
{
	GPIO_ResetBits(W5200_RST_PORT,W5200_RST);
	Delay(100);
	GPIO_SetBits(W5200_RST_PORT,W5200_RST);
	Delay(1000);
}

/*******************************************************************************
* ������  : W5200_Init
* ����    : ��ʼ��W5200�Ĵ�������
* ����    : ��
* ���    : ��
* ����ֵ  : ��
* ˵��    : ��ʹ��W5200֮ǰ���ȶ�W5200��ʼ��
*******************************************************************************/
void W5200_Init(void)
{
	u8 i=0;

	Write_W5200(MR,MR_RST);//�����λW5200,��1��Ч,��λ���Զ���0
	Delay(100);//��ʱ100ms,�Լ�����ú���

	//��������(Gateway)��IP��ַ,Gateway_IPΪ4�ֽ�unsigned char����,�Լ����� 
	//ʹ�����ؿ���ʹͨ��ͻ�������ľ��ޣ�ͨ�����ؿ��Է��ʵ��������������Internet
	WriteBuff_W5200(GAR0,Gateway_IP,4);	
			
	//������������(MASK)ֵ,SUB_MASKΪ4�ֽ�unsigned char����,�Լ�����
	//��������������������
	WriteBuff_W5200(SUBR0,Sub_Mask,4);		
	
	//���������ַ,PHY_ADDRΪ6�ֽ�unsigned char����,�Լ�����,����Ψһ��ʶ�����豸�������ֵַ
	//�õ�ֵַ��Ҫ��IEEE���룬����OUI�Ĺ涨��ǰ3���ֽ�Ϊ���̴��룬�������ֽ�Ϊ��Ʒ���
	//����Լ����������ַ��ע���һ���ֽڱ���Ϊż��
	WriteBuff_W5200(SHAR0,Phy_Addr,6);		

	//���ñ�����IP��ַ,IP_ADDRΪ4�ֽ�unsigned char����,�Լ�����
	//ע�⣬����IP�����뱾��IP����ͬһ�����������򱾻����޷��ҵ�����
	WriteBuff_W5200(SIPR0,IP_Addr,4);		
	
	//���÷��ͻ������ͽ��ջ������Ĵ�С���ο�W5200�����ֲ�
	for(i=0;i<8;i++)
	{
		Write_W5200(Sn_RXMEM_SIZE(i),0x02);//Socket Rx memory size=2k	
		Write_W5200(Sn_TXMEM_SIZE(i),0x02);//Socket Tx mempry size=2k
	}

	//��������ʱ�䣬Ĭ��Ϊ2000(200ms) 
	//ÿһ��λ��ֵΪ100΢��,��ʼ��ʱֵ��Ϊ2000(0x07D0),����200����
	Write_W5200(RTR,0x07);
	Write_W5200(RTR+1,0xd0);

	//�������Դ�����Ĭ��Ϊ8�� 
	//����ط��Ĵ��������趨ֵ,�������ʱ�ж�(��صĶ˿��жϼĴ����е�Sn_IR ��ʱλ(TIMEOUT)�á�1��)
	Write_W5200(RCR,8);

	//�����жϣ��ο�W5200�����ֲ�ȷ���Լ���Ҫ���ж�����
	//IMR_CONFLICT��IP��ַ��ͻ�쳣�ж�,IMR_UNREACH��UDPͨ��ʱ����ַ�޷�������쳣�ж�
	//������Socket�¼��жϣ�������Ҫ���
	Write_W5200(IMR1,IR_CONFLICT);
	Write_W5200(IMR2,IR_SOCK(0));
	Write_W5200(Sn_IMR(0),Sn_IR_SEND_OK | Sn_IR_TIMEOUT | Sn_IR_RECV | Sn_IR_DISCON | Sn_IR_CON);
}

/*******************************************************************************
* ������  : Read_IR_W5200
* ����    : ��ȡW5200ָ���˿��жϼĴ�����ֵ
* ����    : s:ָ���Ķ˿ں�(0~7)
* ���    : ��
* ����ֵ  : i(����W5200ָ���˿��жϼĴ�����ֵ)
* ˵��    : ��
*******************************************************************************/
unsigned char Read_IR_W5200(SOCKET s)
{
	unsigned char i;

	i = Read_W5200(Sn_IR(s));
	if(i>0)	Write_W5200(Sn_IR(s),i);

	return i;
}

/*******************************************************************************
* ������  : Detect_Gateway
* ����    : ������ط�����
* ����    : ��
* ���    : ��
* ����ֵ  : �ɹ�����TRUE(0xFF),ʧ�ܷ���FALSE(0x00)
* ˵��    : ��
*******************************************************************************/
unsigned char Detect_Gateway(void)
{
	unsigned char i;

	while(1)
	{
		Write_W5200(Sn_MR(0),Sn_MR_TCP);//����socket0ΪTCPģʽ
		Write_W5200(Sn_CR(0),Sn_CR_OPEN);//��socket0
		Delay(10);
		//���socket0��ʧ��
		if(Read_W5200(Sn_SR(0))!=SOCK_INIT)
		{
			Write_W5200(Sn_CR(0),Sn_CR_CLOSE);//�򿪲��ɹ�,�ر�Socket
			if(i >= 10)
			{
				return FALSE;//����FALSE(0x00)
			}
		}
		else
		{
			break;
		}
	}

	//������ؼ���ȡ���ص������ַ
	for(i=0;i<4;i++)
		Write_W5200(Sn_DIPR0(0)+i,IP_Addr[i]+1);//��Ŀ�ĵ�ַ�Ĵ���д���뱾��IP��ͬ��IPֵ
	//��socket0��TCP����
	Write_W5200(Sn_CR(0),Sn_CR_CONNECT);
	//��ʱ20ms 
	Delay(50);						

	do
	{
		if(Read_IR_W5200(0)&Sn_IR_TIMEOUT)//��ʱ
		{
			Write_W5200(Sn_CR(0),Sn_CR_CLOSE);
			return FALSE;
		}
		if(Read_W5200(Sn_DHAR0(0))!=0xff)//Ŀ��MAC��ַ�Ĵ�����һ���ֽڲ�Ϊ0xff
		{
			Write_W5200(Sn_CR(0),Sn_CR_CLOSE);
			break;
		}
	}while(1);

	return TRUE;
}

/*******************************************************************************
* ������  : Socket_Init
* ����    : ָ��Socket(0~7)��ʼ��
* ����    : s:����ʼ���Ķ˿�
* ���    : ��
* ����ֵ  : ��
* ˵��    : ��
*******************************************************************************/
void Socket_Init(SOCKET s)
{
	//���÷�Ƭ���ȣ��ο�W5200�����ֲᣬ��ֵ���Բ��޸�
	//����Ƭ�ֽ���=1460(0x5b4)
	Write_W5200(Sn_MSSR0(s),0x05);		
	Write_W5200(Sn_MSSR0(s)+1,0xb4);
	//����ָ���˿�
	switch(s)
	{
		case 0:
			//���ö˿�0�Ķ˿ں�
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
* ������  : Socket_Connect
* ����    : ����ָ��Socket(0~7)Ϊ�ͻ�����Զ�̷���������
* ����    : s:���趨�Ķ˿�
* ���    : ��
* ����ֵ  : �ɹ�����TRUE(0xFF),ʧ�ܷ���FALSE(0x00)
* ˵��    : ������Socket�����ڿͻ���ģʽʱ,���øó���,��Զ�̷�������������
*			����������Ӻ���ֳ�ʱ�жϣ��������������ʧ��,��Ҫ���µ��øó�������
*			�ó���ÿ����һ��,�������������һ������
*******************************************************************************/
unsigned char Socket_Connect(SOCKET s)
{
	Write_W5200(Sn_MR(s), Sn_MR_TCP);//����socketΪTCPģʽ
	Write_W5200(Sn_CR(s), Sn_CR_OPEN);//��Socket
	//���socket��ʧ��
	if(Read_W5200(Sn_SR(s))!=SOCK_INIT)
	{
		Write_W5200(Sn_CR(s),Sn_CR_CLOSE);//�򿪲��ɹ�,�ر�Socket
		return FALSE;//����FALSE(0x00)
	}
	//����SocketΪConnectģʽ
	Write_W5200(Sn_CR(s),Sn_CR_CONNECT);

	return TRUE;

	//���������Socket�Ĵ����ӹ���,�������Ƿ���Զ�̷�������������,����Ҫ�ȴ�Socket�жϣ�
	//���ж�Socket�������Ƿ�ɹ����ο�W5200�����ֲ��Socket�ж�״̬
}

/*******************************************************************************
* ������  : Socket_Listen
* ����    : ����ָ��Socket(0~7)��Ϊ�������ȴ�Զ������������
* ����    : s:���趨�Ķ˿�
* ���    : ��
* ����ֵ  : �ɹ�����TRUE(0xFF),ʧ�ܷ���FALSE(0x00)
* ˵��    : ������Socket�����ڷ�����ģʽʱ,���øó���,�ȵ�Զ������������
*			�ó���ֻ����һ��,��ʹW5200����Ϊ������ģʽ
*******************************************************************************/
unsigned char Socket_Listen(SOCKET s)
{
	Write_W5200(Sn_MR(s), Sn_MR_TCP);//����socketΪTCPģʽ 
	Write_W5200(Sn_CR(s), Sn_CR_OPEN);//��Socket
	//���socket��ʧ��
	if(Read_W5200(Sn_SR(s))!=SOCK_INIT)
	{
		Write_W5200(Sn_CR(s),Sn_CR_CLOSE);//�򿪲��ɹ�,�ر�Socket
		return FALSE;//����FALSE(0x00)
	}
	//����SocketΪ����ģʽ
	Write_W5200(Sn_CR(s), Sn_CR_LISTEN);
	//���socket����ʧ��
	if(Read_W5200(Sn_SR(s))!=SOCK_LISTEN)
	{
		Write_W5200(Sn_CR(s), Sn_CR_CLOSE);//���ò��ɹ�,�ر�Socket
		return FALSE;//����FALSE(0x00)
	}

	return TRUE;

	//���������Socket�Ĵ򿪺�������������,����Զ�̿ͻ����Ƿ�������������,����Ҫ�ȴ�Socket�жϣ�
	//���ж�Socket�������Ƿ�ɹ����ο�W5200�����ֲ��Socket�ж�״̬
	//�ڷ���������ģʽ����Ҫ����Ŀ��IP��Ŀ�Ķ˿ں�
}

/*******************************************************************************
* ������  : Socket_UDP
* ����    : ����ָ��Socket(0~3)ΪUDPģʽ
* ����    : s:���趨�Ķ˿�
* ���    : ��
* ����ֵ  : �ɹ�����TRUE(0xFF),ʧ�ܷ���FALSE(0x00)
* ˵��    : ���Socket������UDPģʽ,���øó���,��UDPģʽ��,Socketͨ�Ų���Ҫ��������
*			�ó���ֻ����һ�Σ���ʹW5200����ΪUDPģʽ
*******************************************************************************/
unsigned char Socket_UDP(SOCKET s)
{
	Write_W5200(Sn_MR(s), Sn_MR_UDP);//����SocketΪUDPģʽ*/
	Write_W5200(Sn_CR(s), Sn_CR_OPEN);//��Socket*/
	//���Socket��ʧ��
	if(Read_W5200(Sn_SR(s))!=SOCK_UDP)
	{
		Write_W5200(Sn_CR(s), Sn_CR_CLOSE);//�򿪲��ɹ�,�ر�Socket
		return FALSE;//����FALSE(0x00)
	}
	else
		return TRUE;

	//���������Socket�Ĵ򿪺�UDPģʽ����,������ģʽ��������Ҫ��Զ��������������
	//��ΪSocket����Ҫ��������,�����ڷ�������ǰ����������Ŀ������IP��Ŀ��Socket�Ķ˿ں�
	//���Ŀ������IP��Ŀ��Socket�Ķ˿ں��ǹ̶���,�����й�����û�иı�,��ôҲ��������������
}

/*******************************************************************************
* ������  : S_rx_process
* ����    : ָ��Socket(0~3)�������ݴ���
* ����    : s:�˿�
* ���    : ��
* ����ֵ  : ���ؽ��յ����ݵĳ���
* ˵��    : ���Socket�����������ݵ��ж�,�����øó�����д���
*			�ó���Socket�Ľ��յ������ݻ��浽Rx_buffer������,�����ؽ��յ������ֽ���
*******************************************************************************/
unsigned short S_rx_process(SOCKET s)
{
    
	unsigned short i;
	unsigned short rx_size,rx_offset,rx_offset1;

	//��ȡ�������ݵ��ֽ���
	rx_size=Read_W5200(Sn_RX_RSR0(s));
	rx_size*=256;
	rx_size+=Read_W5200(Sn_RX_RSR0(s)+1);

	//��ȡ���ջ�������ƫ����
	rx_offset=Read_W5200(Sn_RX_RD0(0));
	rx_offset*=256;
	rx_offset+=Read_W5200(Sn_RX_RD0(0)+1);
	rx_offset1=rx_offset;

	i=rx_offset/S_RX_SIZE; //����ʵ�ʵ�����ƫ������S0_RX_SIZE��Ҫ��ǰ��#define�ж���
						   //ע��S_RX_SIZE��ֵ��W5200_Init()������Sn_MSSR0��ȷ��
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
	//������һ��ƫ����
	rx_offset1+=rx_size;
	Write_W5200(Sn_RX_RD0(0), (rx_offset1/256));
	Write_W5200(Sn_RX_RD0(0)+1, rx_offset1);

	Write_W5200(Sn_CR(0), Sn_CR_RECV);//����RECV����ȵ���һ�ν���
	while(Read_W5200(Sn_CR(0)));

	return rx_size;//���ؽ��յ������ֽ���	
}


/*******************************************************************************
* ������  : S_tx_process
* ����    : ָ��Socket(0~3)�������ݴ���
* ����    : s:�˿�,size(�������ݵĳ���)
* ���    : ��
* ����ֵ  : �ɹ�����TRUE(0xFF),ʧ�ܷ���FALSE(0x00)
* ˵��    : Ҫ���͵����ݻ�����Tx_buffer��
*******************************************************************************/
unsigned char S_tx_process(SOCKET s, unsigned int size)
{
	unsigned short i;
	unsigned short tx_offset,tx_offset1;

	//�����UDPģʽ,�����ڴ�����Ŀ��������IP�Ͷ˿ں�
	if((Read_W5200(Sn_MR(s))&0x0f)==0x02)
	{
		//����Ŀ������IP
		WriteBuff_W5200(Sn_DIPR0(s),UDP_DIPR,4);
  		//����Ŀ�������˿ں�
		WriteBuff_W5200(Sn_DPORT0(s),UDP_DPORT,2);				
	}

	//��ȡ������ʣ��ĳ���
	i=Read_W5200(Sn_TX_FSR0(s));
	i*=256;
	i+=Read_W5200(Sn_TX_FSR0(s)+1);
	if(i<size) return FALSE; //���ʣ����ֽڳ���С�ڷ����ֽڳ���,�򷵻�*/
		
	//��ȡ���ͻ�������ƫ����
	tx_offset=Read_W5200(Sn_TX_WR0(s));
	tx_offset*=256;
	tx_offset+=Read_W5200(Sn_TX_WR0(s)+1);
	tx_offset1=tx_offset;

	i=tx_offset/S_TX_SIZE; //����ʵ�ʵ�����ƫ������S0_TX_SIZE��Ҫ��ǰ��#define�ж���
						   //ע��S0_TX_SIZE��ֵ��W5200_Init()������W5200_TMSR��ȷ��
	tx_offset=tx_offset-i*S_TX_SIZE;
	//������������д��W5200���ͻ�����
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

	//������һ�ε�ƫ����
	tx_offset1+=size;
	Write_W5200(Sn_TX_WR0(s),(tx_offset1/256));
	Write_W5200(Sn_TX_WR0(s)+1,tx_offset1);

	Write_W5200(Sn_CR(s), Sn_CR_SEND);//����SEND����,��������
	i = 0;
	while((Read_IR_W5200(0) & Sn_IR_SEND_OK)==0)//�ȴ��������
	{
		Delay(1);
		if(++i>40)
		{
			break;
		}
	}

	return TRUE;//���سɹ�
}

/*******************************************************************************
* ������  : W5200_Interrupt_Process
* ����    : W5200�жϴ��������
* ����    : ��
* ���    : ��
* ����ֵ  : ��
* ˵��    : ��
*******************************************************************************/
void W5200_Interrupt_Process(void)
{
	unsigned char i,j;

	W5200_Interrupt=0;//�����жϱ�־	
	i=Read_W5200(IR);//��ȡ�жϱ�־�Ĵ���
	Write_W5200(IR, i);//��д����жϱ�־

	if((i & IR_CONFLICT) == IR_CONFLICT)//IP��ַ��ͻ�쳣����
	{
		 //�Լ���Ӵ���
	}

	if((i & IR_UNREACH) == IR_UNREACH)//UDPģʽ�µ�ַ�޷������쳣����
	{
		//�Լ���Ӵ���
	}

	i=Read_W5200(IR2);//��ȡ�жϱ�־�Ĵ���
	if((i & IR_SOCK(0)) == IR_SOCK(0))//Socket0�¼����� 
	{
		j=Read_W5200(Sn_IR(0));//��ȡSocket0�жϱ�־�Ĵ���
		Write_W5200(Sn_IR(0), j);//��д���жϱ�־ 
		if(j&Sn_IR_CON)//��TCPģʽ��,Socket0�ɹ����� 
		{
			S0_State|=S_CONN;//��������״̬0x02,�˿�������ӣ�����������������
		}
		if(j&Sn_IR_DISCON)//��TCPģʽ��Socket�Ͽ����Ӵ���
		{
			Write_W5200(Sn_CR(0), Sn_CR_CLOSE);// �رն˿�,�ȴ����´����� 
			S0_State=0;//��������״̬0x00,�˿�����ʧ��
		}
		if(j&Sn_IR_SEND_OK)//Socket0���ݷ������,�����ٴ�����S_tx_process()������������ 
		{
			S0_Data|=S_TRANSMITOK;//�˿ڷ���һ�����ݰ���� 
		}
		if(j&Sn_IR_RECV)//Socket���յ�����,��������S_rx_process()���� 
		{
			S0_Data|=S_RECEIVE;//�˿ڽ��յ�һ�����ݰ�
		}
		if(j&Sn_IR_TIMEOUT)//Socket���ӻ����ݴ��䳬ʱ���� 
		{
			Write_W5200(Sn_CR(0), Sn_CR_CLOSE);//�رն˿�,�ȴ����´�����
			S0_State=0;//��������״̬0x00,�˿�����ʧ��
		}
	}
}




//�û����庯��
unsigned int Timer2_Counter=0; //Timer2��ʱ����������(ms)
unsigned int W5200_Send_Delay_Counter=0; //W5200������ʱ��������(ms)

/*******************************************************************************
* ������  : W5200_Initialization
* ����    : W5200��ʼ������
* ����    : ��
* ���    : ��
* ����ֵ  : ��
* ˵��    : ��
*******************************************************************************/
void W5200_Initialization(void)
{
	W5200_NVIC_Configuration();
	W5200_GPIO_Configuration();	//W5200 GPIO��ʼ������	
	Timer2_Init_Config();		//Timer2��ʼ������
	EXTI4_Configuration();
	SPI_Configuration();		//W5200 SPI��ʼ������(STM32 SPI1)
	
	W5200_Hardware_Reset();		//Ӳ����λW5200
	W5200_Init();		//��ʼ��W5200�Ĵ�������
	Detect_Gateway();	//������ط����� 
	Socket_Init(0);		//ָ��Socket(0~3)��ʼ��,��ʼ���˿�0
}

/*******************************************************************************
* ������  : Load_Net_Parameters
* ����    : װ���������
* ����    : ��
* ���    : ��
* ����ֵ  : ��
* ˵��    : ���ء����롢�����ַ������IP��ַ���˿ںš�Ŀ��IP��ַ��Ŀ�Ķ˿ںš��˿ڹ���ģʽ
*******************************************************************************/
void Load_Net_Parameters(void)
{
	Gateway_IP[0] = 192;//�������ز���
	Gateway_IP[1] = 168;
	Gateway_IP[2] = 1;
	Gateway_IP[3] = 1;

	Sub_Mask[0]=255;//������������
	Sub_Mask[1]=255;
	Sub_Mask[2]=0;
	Sub_Mask[3]=0;

	Phy_Addr[0]=0x0c;//���������ַ
	Phy_Addr[1]=0x29;
	Phy_Addr[2]=0xab;
	Phy_Addr[3]=0x7c;
	Phy_Addr[4]=0x00;
	Phy_Addr[5]=0x01;

	IP_Addr[0]=192;//���ر���IP��ַ
	IP_Addr[1]=168;
	IP_Addr[2]=1;
	IP_Addr[3]=199;

	S0_Port[0] = 0x13;//���ض˿�0�Ķ˿ں�5000 
	S0_Port[1] = 0x88;

//	S0_DIP[0]=192;//���ض˿�0��Ŀ��IP��ַ
//	S0_DIP[1]=168;
//	S0_DIP[2]=1;
//	S0_DIP[3]=190;
//	
//	S0_DPort[0] = 0x17;//���ض˿�0��Ŀ�Ķ˿ں�6000
//	S0_DPort[1] = 0x70;

	S0_Mode=TCP_SERVER;//���ض˿�0�Ĺ���ģʽ,TCP�����ģʽ
}

/*******************************************************************************
* ������  : W5200_Socket_Set
* ����    : W5200�˿ڳ�ʼ������
* ����    : ��
* ���    : ��
* ����ֵ  : ��
* ˵��    : �ֱ�����4���˿�,���ݶ˿ڹ���ģʽ,���˿�����TCP��������TCP�ͻ��˻�UDPģʽ.
*			�Ӷ˿�״̬�ֽ�Socket_State�����ж϶˿ڵĹ������
*******************************************************************************/
void W5200_Socket_Set(void)
{
	if(S0_State==0)//�˿�0��ʼ������
	{
		if(S0_Mode==TCP_SERVER)//TCP������ģʽ 
		{
			if(Socket_Listen(0)==TRUE)
				S0_State=S_INIT;
			else
				S0_State=0;
		}
		else if(S0_Mode==TCP_CLIENT)//TCP�ͻ���ģʽ 
		{
			if(Socket_Connect(0)==TRUE)
				S0_State=S_INIT;
			else
				S0_State=0;
		}
		else//UDPģʽ 
		{
			if(Socket_UDP(0)==TRUE)
				S0_State=S_INIT|S_CONN;
			else
				S0_State=0;
		}
	}
}

/*******************************************************************************
* ������  : Process_Socket_Data
* ����    : W5200���ղ����ͽ��յ�������
* ����    : s:�˿ں�
* ���    : ��
* ����ֵ  : ��
* ˵��    : �������ȵ���S_rx_process()��W5200�Ķ˿ڽ������ݻ�������ȡ����,
*			Ȼ�󽫶�ȡ�����ݴ�Rx_Buffer������Temp_Buffer���������д���
*			������ϣ������ݴ�Temp_Buffer������Tx_Buffer������������S_tx_process()
*			�������ݡ�
*******************************************************************************/
void Process_Socket_Data(SOCKET s)
{
	unsigned short size;
	unsigned short i;
	size=S_rx_process(s);
	//�ٴο�ʼ��֡
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
			else// ����֡ͷ������� ��ʼ��������
			{
				if(rxIndex>=2)
				{
					rxDataLen=RXCmdBuf[2];
					if (rxDataLen<rxIndex+1)//�������ݳ��� ���½���
					{
						rxIndex=0;
						return;
					}
				}
				//����Ƿ���֡β
				if (RXCmdBuf[rxIndex]==0xBC)
				{
					//�ж�����֡�����Ƿ�Ϸ�
					rxDataLen=RXCmdBuf[2];
					if (rxDataLen == rxIndex+1)
					{
						//����֡������ɣ���ʼ�����֡����
						rxIndex=0;
						ReceiveDataProcess();
					}
					else
					{
						//������ݲ��Ϸ� ���½�������
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
* ������  : Timer2_Init_Config
* ����    : Timer2��ʼ������
* ����    : ��
* ���    : ��
* ����    : �� 
* ˵��    : ��
*******************************************************************************/
void Timer2_Init_Config(void)
{
	TIM_TimeBaseInitTypeDef TIM_TimeBaseStructure;
	
	RCC_APB1PeriphClockCmd(RCC_APB1Periph_TIM2, ENABLE);		//ʹ��Timer2ʱ��
	
	TIM_TimeBaseStructure.TIM_Period = 9;						//��������һ�������¼�װ�����Զ���װ�ؼĴ������ڵ�ֵ(������10Ϊ1ms)
	TIM_TimeBaseStructure.TIM_Prescaler = 7199;					//����������ΪTIMxʱ��Ƶ�ʳ�����Ԥ��Ƶֵ(10KHz�ļ���Ƶ��)
	TIM_TimeBaseStructure.TIM_ClockDivision = TIM_CKD_DIV1;		//����ʱ�ӷָ�:TDTS = TIM_CKD_DIV1
	TIM_TimeBaseStructure.TIM_CounterMode = TIM_CounterMode_Up;	//TIM���ϼ���ģʽ
	TIM_TimeBaseInit(TIM2, &TIM_TimeBaseStructure);				//����TIM_TimeBaseInitStruct��ָ���Ĳ�����ʼ��TIMx��ʱ�������λ
	 
	TIM_ITConfig(TIM2, TIM_IT_Update, ENABLE ); 				//ʹ��TIM2ָ�����ж�
	
	TIM_Cmd(TIM2, ENABLE);  									//ʹ��TIMx����
}

/*******************************************************************************
* ������  : TIM2_IRQHandler
* ����    : ��ʱ��2�ж϶Ϸ�����
* ����    : ��
* ���    : ��
* ����    : �� 
* ˵��    : ��
*******************************************************************************/
void TIM2_IRQHandler(void)
{
	if(TIM_GetITStatus(TIM2, TIM_IT_Update) == SET)
	{
		TIM_ClearITPendingBit(TIM2, TIM_IT_Update);
		Timer2_Counter++;
		W5200_Send_Delay_Counter++;
//		W5200_Socket_Set();//W5200�˿ڳ�ʼ������
	}
}
/*******************************************************************************
* ������  : Delay
* ����    : ��ʱ����(ms)
* ����    : d:��ʱϵ������λΪ����
* ���    : ��
* ����    : �� 
* ˵��    : ��ʱ������Timer2��ʱ��������1����ļ�����ʵ�ֵ�
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
  EXTI_InitStructure.EXTI_Line = EXTI_Line4;                   //�ⲿ�ж�ͨ��3
  EXTI_InitStructure.EXTI_Mode = EXTI_Mode_Interrupt;
  EXTI_InitStructure.EXTI_Trigger = EXTI_Trigger_Falling;    //�½��ش���
  EXTI_InitStructure.EXTI_LineCmd = ENABLE;        //ʹ��
  EXTI_Init(&EXTI_InitStructure);
  GPIO_EXTILineConfig(GPIO_PortSourceGPIOC, GPIO_PinSource4);  //���ӵ�W5200�ⲿ�ж�
}

/*******************************************************************************
* ������  : EXTI4_IRQHandler
* ����    : �ж���4�жϷ�����(W5200 INT�����жϷ�����)
* ����    : ��
* ���    : ��
* ����ֵ  : ��
* ˵��    : ��
*******************************************************************************/
void EXTI4_IRQHandler(void)
{
	if(EXTI_GetITStatus(EXTI_Line4) != RESET)
	{
		EXTI_ClearITPendingBit(EXTI_Line4);
	//	W5200_Interrupt=1;
		W5200_Interrupt_Process();//W5200�жϴ��������
		if((S0_Data & S_RECEIVE) == S_RECEIVE)//���Socket0���յ�����
		{
			S0_Data&=~S_RECEIVE;
			Process_Socket_Data(0);//W5200���ղ����ͽ��յ�������
		}
		W5200_Socket_Set();//W5200�˿ڳ�ʼ������
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
	case 0xF0:	//�ļ�ͷ 
		break;
	case 0xF1:
		break;
	case 0xF2:  //�ļ����ս���
		break;
	case 0xC1:	//���������  
		/*
			����֡��ʽ��BA BA + Len(1 byte)+Fun(1 byte)+ChannelNo(1 byte)+Value(1 byte)+BC
					Eg: BA BA 07 C1 01 01 BC
		*/
		DOChannelNo=RXCmdBuf[4];
		DOValue = RXCmdBuf[5];
		DOControl(DOChannelNo,DOValue);
		break;
	}
	USART2_Puts("ReceiveDataProcess\n");
}


