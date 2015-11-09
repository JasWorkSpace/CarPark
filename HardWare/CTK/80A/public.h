//add by jas
#ifndef _PUBLIC_H_
#define _PUBLIC_H_

#ifndef uchar 
#define uchar unsigned char
#endif

#ifndef uint 
#define uint  unsigned int
#endif

#ifndef uint32 
#define uint32  unsigned long
#endif

#ifndef FALSE 
#define FALSE  0
#endif

#ifndef TRUE 
#define TRUE  1
#endif

#ifndef HIGHT 
#define HIGHT  1
#endif

#ifndef LOW 
#define LOW  0
#endif

#ifndef CLOSE 
#define CLOSE  0
#endif

#ifndef OPEN 
#define OPEN  1
#endif

#ifndef NORMAL 
#define NORMAL  0
#endif

#ifndef ERROR 
#define ERROR  1
#endif
//做为系统的基准时间
uint32 OS_Time = 0;

/*软件延迟函数，x约为50us*/
void CTKSoftDelay(uint x);

#endif