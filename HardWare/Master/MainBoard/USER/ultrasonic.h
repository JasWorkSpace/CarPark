#ifndef _ultrasonic_H
#define _ultrasonic_H
#include"sys.h"



//------------------------------------------------------------ÒÆÖ²ÐÞ¸ÄÇø-----------------------------------------------------------------------

#define ULTRA_PORT	GPIOB
#define ULTRA_CLK       RCC_APB2Periph_GPIOB
#define ULTRA_TRIG			GPIO_Pin_5
#define ULTRA_ECHO			GPIO_Pin_6



//---------------------------------------------------------------------------------------------------------------------------------------------
void Ultran_Init(void);
int Ultra_Ranging(float *p);




#endif

