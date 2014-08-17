#include <stm32f10x.h>
#include "USART.h"
#include <stdarg.h>

//#pragma message ("调试模式波特率设置成115200")
void usart1_init(void)
{
	GPIO_InitTypeDef GPIO_InitStructure;
	USART_InitTypeDef USART_InitStructure;
	NVIC_InitTypeDef NVIC_InitStructure;
	USART_ClockInitTypeDef USART_ClockInitStruct;

	/* Enable the USART1 Interrupt */
//	NVIC_PriorityGroupConfig(NVIC_PriorityGroup_3);
	NVIC_InitStructure.NVIC_IRQChannel = USART1_IRQn;
	NVIC_InitStructure.NVIC_IRQChannelPreemptionPriority = 2;
	NVIC_InitStructure.NVIC_IRQChannelSubPriority = 0;
	NVIC_InitStructure.NVIC_IRQChannelCmd = ENABLE;
	NVIC_Init(&NVIC_InitStructure);

	RCC_APB2PeriphClockCmd(RCC_APB2Periph_GPIOA,ENABLE);
	//USART1时钟使能
	RCC_APB2PeriphClockCmd(RCC_APB2Periph_AFIO|RCC_APB2Periph_USART1, ENABLE);
	/* Configure USART1_Tx as alternate function push-pull */
	GPIO_InitStructure.GPIO_Pin = GPIO_Pin_9;
	GPIO_InitStructure.GPIO_Speed = GPIO_Speed_50MHz;
	GPIO_InitStructure.GPIO_Mode = GPIO_Mode_AF_PP;
	GPIO_Init(GPIOA, &GPIO_InitStructure);
	/* Configure USART1_Rx as input floating */
	GPIO_InitStructure.GPIO_Pin = GPIO_Pin_10;
	GPIO_InitStructure.GPIO_Mode = GPIO_Mode_IN_FLOATING;
	GPIO_InitStructure.GPIO_Speed=GPIO_Speed_50MHz;
	GPIO_Init(GPIOA, &GPIO_InitStructure);

	USART_ClockInitStruct.USART_Clock = USART_Clock_Disable;
	USART_ClockInitStruct.USART_CPOL = USART_CPOL_Low;
	USART_ClockInitStruct.USART_CPHA = USART_CPHA_2Edge;
	USART_ClockInitStruct.USART_LastBit = USART_LastBit_Disable;

	USART_ClockInit(USART1,&USART_ClockInitStruct);

	USART_InitStructure.USART_BaudRate = 9600;
	USART_InitStructure.USART_WordLength = USART_WordLength_8b;
	USART_InitStructure.USART_StopBits = USART_StopBits_1;
	USART_InitStructure.USART_Parity = USART_Parity_No ;
	USART_InitStructure.USART_HardwareFlowControl = USART_HardwareFlowControl_None;
	USART_InitStructure.USART_Mode = USART_Mode_Rx | USART_Mode_Tx;
	/* Configure the USART1 */
	USART_Init(USART1, &USART_InitStructure);
	//使能USART1接收中断
	USART_ITConfig(USART1, USART_IT_RXNE, ENABLE);
	/* Enable the USART1 */
	USART_Cmd(USART1, ENABLE);
}
void USART1_Putc(char ch)
{
	USART_SendData(USART1, ch);
	while(USART_GetFlagStatus(USART1, USART_FLAG_TXE) == RESET );
}
void USART1_Puts(char *str)
{
	 while(*str)
		USART1_Putc(*str++);
}

void u_tx_c(char TxData) 
{
	USART1->DR = TxData;
	while(!(USART1->SR & USART_FLAG_TXE));
}


void U1TxData(char TxData) 
{
	USART1->DR = TxData;
	while(!(USART1->SR & USART_FLAG_TXE));
}
//USART2
void usart2_init(void)
{
	GPIO_InitTypeDef GPIO_InitStructure;
	USART_InitTypeDef USART_InitStructure;
	NVIC_InitTypeDef NVIC_InitStructure;
	USART_ClockInitTypeDef USART_ClockInitStruct;

	/* Enable the USART1 Interrupt */
	NVIC_InitStructure.NVIC_IRQChannel = USART2_IRQn;
	NVIC_InitStructure.NVIC_IRQChannelPreemptionPriority = 2;
	NVIC_InitStructure.NVIC_IRQChannelSubPriority = 1;
	NVIC_InitStructure.NVIC_IRQChannelCmd = ENABLE;
	NVIC_Init(&NVIC_InitStructure);

	RCC_APB2PeriphClockCmd(RCC_APB2Periph_GPIOA,ENABLE);
	//USART1时钟使能
	RCC_APB1PeriphClockCmd(RCC_APB1Periph_USART2, ENABLE);
	/* Configure USART1_Tx as alternate function push-pull */
	GPIO_InitStructure.GPIO_Pin = GPIO_Pin_2;
	GPIO_InitStructure.GPIO_Speed = GPIO_Speed_50MHz;
	GPIO_InitStructure.GPIO_Mode = GPIO_Mode_AF_PP;
	GPIO_Init(GPIOA, &GPIO_InitStructure);
	/* Configure USART1_Rx as input floating */
	GPIO_InitStructure.GPIO_Pin = GPIO_Pin_3;
	GPIO_InitStructure.GPIO_Mode = GPIO_Mode_IN_FLOATING;
	GPIO_InitStructure.GPIO_Speed=GPIO_Speed_50MHz;
	GPIO_Init(GPIOA, &GPIO_InitStructure);

	USART_ClockInitStruct.USART_Clock = USART_Clock_Disable;
	USART_ClockInitStruct.USART_CPOL = USART_CPOL_Low;
	USART_ClockInitStruct.USART_CPHA = USART_CPHA_2Edge;
	USART_ClockInitStruct.USART_LastBit = USART_LastBit_Disable;

	USART_ClockInit(USART2,&USART_ClockInitStruct);

	USART_InitStructure.USART_BaudRate = 9600;
	USART_InitStructure.USART_WordLength = USART_WordLength_8b;
	USART_InitStructure.USART_StopBits = USART_StopBits_1;
	USART_InitStructure.USART_Parity = USART_Parity_No ;
	USART_InitStructure.USART_HardwareFlowControl = USART_HardwareFlowControl_None;
	USART_InitStructure.USART_Mode = USART_Mode_Rx | USART_Mode_Tx;
	/* Configure the USART1 */
	USART_Init(USART2, &USART_InitStructure);
	//使能USART1接收中断
	USART_ITConfig(USART2, USART_IT_RXNE, ENABLE);
	/* Enable the USART1 */
	USART_Cmd(USART2, ENABLE);
}
void USART2_Putc(char ch)
{
	USART_SendData(USART2, ch);
	while(USART_GetFlagStatus(USART2, USART_FLAG_TXE) == RESET );
}
void USART2_Puts(char *str)
{
	 while(*str)
		USART2_Putc(*str++);
}
//USART4
void usart4_init(void)
{
	GPIO_InitTypeDef GPIO_InitStructure;
	USART_InitTypeDef USART_InitStructure;
	NVIC_InitTypeDef NVIC_InitStructure;
	USART_ClockInitTypeDef USART_ClockInitStruct;

	/* Enable the USART1 Interrupt */
	NVIC_InitStructure.NVIC_IRQChannel = UART4_IRQn;
	NVIC_InitStructure.NVIC_IRQChannelPreemptionPriority = 2;
	NVIC_InitStructure.NVIC_IRQChannelSubPriority = 0;
	NVIC_InitStructure.NVIC_IRQChannelCmd = ENABLE;
	NVIC_Init(&NVIC_InitStructure);

	RCC_APB2PeriphClockCmd(RCC_APB2Periph_GPIOC,ENABLE);
	//USART1时钟使能
	RCC_APB1PeriphClockCmd(RCC_APB1Periph_UART4, ENABLE);
	/* Configure USART1_Tx as alternate function push-pull */
	GPIO_InitStructure.GPIO_Pin = GPIO_Pin_10;
	GPIO_InitStructure.GPIO_Speed = GPIO_Speed_50MHz;
	GPIO_InitStructure.GPIO_Mode = GPIO_Mode_AF_PP;
	GPIO_Init(GPIOC, &GPIO_InitStructure);
	/* Configure USART1_Rx as input floating */
	GPIO_InitStructure.GPIO_Pin = GPIO_Pin_11;
	GPIO_InitStructure.GPIO_Mode = GPIO_Mode_IN_FLOATING;
	GPIO_InitStructure.GPIO_Speed=GPIO_Speed_50MHz;
	GPIO_Init(GPIOC, &GPIO_InitStructure);
/*
	USART_ClockInitStruct.USART_Clock = USART_Clock_Disable;
	USART_ClockInitStruct.USART_CPOL = USART_CPOL_Low;
	USART_ClockInitStruct.USART_CPHA = USART_CPHA_2Edge;
	USART_ClockInitStruct.USART_LastBit = USART_LastBit_Disable;

	USART_ClockInit(UART4,&USART_ClockInitStruct);
*/
	USART_InitStructure.USART_BaudRate = 9600;
	USART_InitStructure.USART_WordLength = USART_WordLength_8b;
	USART_InitStructure.USART_StopBits = USART_StopBits_1;
	USART_InitStructure.USART_Parity = USART_Parity_No ;
	USART_InitStructure.USART_HardwareFlowControl = USART_HardwareFlowControl_None;
	USART_InitStructure.USART_Mode = USART_Mode_Rx | USART_Mode_Tx;
	/* Configure the USART1 */
	USART_Init(UART4, &USART_InitStructure);
	//使能USART1接收中断
	USART_ITConfig(UART4, USART_IT_RXNE, ENABLE);
	/* Enable the USART1 */
	USART_Cmd(UART4, ENABLE);
}
void USART4_Putc(char ch)
{
	USART_SendData(UART4, ch);
	while(USART_GetFlagStatus(UART4, USART_FLAG_TXE) == RESET );
}
void USART4_Puts(char *str)
{
	 while(*str)
		USART4_Putc(*str++);
}
//USART5
void usart5_init(void)
{
	GPIO_InitTypeDef GPIO_InitStructure;
	USART_InitTypeDef USART_InitStructure;
	NVIC_InitTypeDef NVIC_InitStructure;
	USART_ClockInitTypeDef USART_ClockInitStruct;

	/* Enable the USART1 Interrupt */
//	NVIC_PriorityGroupConfig(NVIC_PriorityGroup_3);
	NVIC_InitStructure.NVIC_IRQChannel = UART5_IRQn;
	NVIC_InitStructure.NVIC_IRQChannelPreemptionPriority = 2;
	NVIC_InitStructure.NVIC_IRQChannelSubPriority = 0;
	NVIC_InitStructure.NVIC_IRQChannelCmd = ENABLE;
	NVIC_Init(&NVIC_InitStructure);

	RCC_APB2PeriphClockCmd(RCC_APB2Periph_GPIOC,ENABLE);
	RCC_APB2PeriphClockCmd(RCC_APB2Periph_GPIOD,ENABLE);
	//USART1时钟使能
	RCC_APB1PeriphClockCmd(RCC_APB1Periph_UART5, ENABLE);
	/* Configure USART1_Tx as alternate function push-pull */
	GPIO_InitStructure.GPIO_Pin = GPIO_Pin_12;
	GPIO_InitStructure.GPIO_Speed = GPIO_Speed_50MHz;
	GPIO_InitStructure.GPIO_Mode = GPIO_Mode_AF_PP;
	GPIO_Init(GPIOC, &GPIO_InitStructure);
	/* Configure USART1_Rx as input floating */
	GPIO_InitStructure.GPIO_Pin = GPIO_Pin_2;
	GPIO_InitStructure.GPIO_Mode = GPIO_Mode_IN_FLOATING;
	GPIO_InitStructure.GPIO_Speed=GPIO_Speed_50MHz;
	GPIO_Init(GPIOD, &GPIO_InitStructure);
/*
	USART_ClockInitStruct.USART_Clock = USART_Clock_Disable;
	USART_ClockInitStruct.USART_CPOL = USART_CPOL_Low;
	USART_ClockInitStruct.USART_CPHA = USART_CPHA_2Edge;
	USART_ClockInitStruct.USART_LastBit = USART_LastBit_Disable;

	USART_ClockInit(UART4,&USART_ClockInitStruct);
*/
	USART_InitStructure.USART_BaudRate = 9600;
	USART_InitStructure.USART_WordLength = USART_WordLength_8b;
	USART_InitStructure.USART_StopBits = USART_StopBits_1;
	USART_InitStructure.USART_Parity = USART_Parity_No ;
	USART_InitStructure.USART_HardwareFlowControl = USART_HardwareFlowControl_None;
	USART_InitStructure.USART_Mode = USART_Mode_Rx | USART_Mode_Tx;
	/* Configure the USART1 */
	USART_Init(UART5, &USART_InitStructure);
	//使能USART1接收中断
	USART_ITConfig(UART5, USART_IT_RXNE, ENABLE);
	/* Enable the USART1 */
	USART_Cmd(UART5, ENABLE);
}
void USART5_Putc(char ch)
{
	USART_SendData(UART5, ch);
	while(USART_GetFlagStatus(UART5, USART_FLAG_TXE) == RESET );
}
void USART5_Puts(char *str)
{
	 while(*str)
		USART5_Putc(*str++);
}

char *itoa(int num,char *str,int radix)
{     /* 索引表*/
    char index[]="0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    unsigned unum; /* 中间变量 */
    int i=0,j,k;
	char temp;
    /* 确定unum的值 */
    if(radix==10&&num<0) /* 十进制负数 */
    {
    unum=(unsigned)-num;
    str[i++]='-';
    }
    else unum=(unsigned)num; /* 其他情况 */
    /* 转换 */   
    do{
        str[i++]=index[unum%(unsigned)radix];
        unum/=radix;
    }while(unum);
    str[i]='\0';
    /* 逆序 */
    if(str[0]=='-') k=1; /* 十进制负数 */
    else k=0;
    
    for(j=k;j<=(i-1)/2;j++)
    {
        temp=str[j];
        str[j] = str[i-1+k-j];
        str[i-1+k-j] = temp;
    }
    return str;
}
void s_printf2(const char* fmt,...)
{
	const char* s;
	int d;
	char buf[16];
	va_list ap;
	va_start(ap,fmt);	// 将ap指向fmt（即可变参数的第一个?下一个？）
	while (*fmt)
	{
		if (*fmt != '%')
		{
			u_tx_c(*fmt++);	// 正常发送
			continue;	
		}
		switch (*++fmt)	// next of %
		{
		case 's':
			s = va_arg(ap,const char*);	// 将ap指向者转成char*型，并返回之
			for (; *s; s++)
				u_tx_c(*s);
			break;
		case 'x':
			d = va_arg(ap,int);		// 将ap指向者转成int型，并返回之
			itoa(d,buf,16);			// 将整型d以16进制转到buf中
			// 下面的做法还不如直接用库函数简单、代码量少！
			#ifdef USERHEX
			while (d)
			{
				const uint8 table[] = "0123456789ABCDEF";
				uint8 i = 0;
				buf[i++] = table[d & 0x0f];
				d >>= 4;
			}
			#endif

			for (s = buf; *s; s++)
				u_tx_c(*s);
			break;
		case 'd':
			d = va_arg(ap,int);
			itoa(d,buf,10);			// 将整型d以10进制转到buf中
			for (s = buf; *s; s++)
				u_tx_c(*s);
			break;
		default:
			u_tx_c(*fmt);
			break;
		}
		fmt++;
	}
	va_end(ap);
}

#ifdef using_printf
int fputc(int ch, FILE *f)
{
	/* Write a character to the USART */
	UART4->DR = ch;
	while(!(UART4->SR & USART_FLAG_TXE));
	return ch;
}
#else
#endif
