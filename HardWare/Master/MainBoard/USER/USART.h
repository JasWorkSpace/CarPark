#ifndef _UART_H_
#define _UART_H_
//#include <stm32f10x_lib.h> 
#include "stdio.h"

//初始化串口1
void usart1_init(void);
//发送一个字符
void USART1_Putc(char ch);
//发送一串字符串
void USART1_Puts(char *str);

void usart2_init(void);
void USART2_Putc(char ch);
void USART2_Puts(char *str);

void usart4_init(void);
void USART4_Putc(char ch);
void USART4_Puts(char *str);
void usart5_init(void);
void USART5_Putc(char ch);
void USART5_Puts(char *str);

#define using_printf
#ifdef using_printf
#include "stdio.h"
#ifdef __GNUC__
/* With GCC/RAISONANCE, small s_printf (option LD Linker->Libraries->Small s_printf
set to 'Yes') calls __io_putchar() */
#define PUTCHAR_PROTOTYPE int __io_putchar(int ch)
#else
#define PUTCHAR_PROTOTYPE int fputc(int ch, FILE *f)

PUTCHAR_PROTOTYPE;
#define	s_printf	printf
#endif /* __GNUC__ */
#else//#ifdef using_printf
extern void s_printf(const char * _Format, ...);
#endif//using_printf

#endif


