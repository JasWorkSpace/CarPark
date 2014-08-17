#include <stm32f10x.h>
#include "time.h"

void InitSysTick1us()
{
	//内核系统定时器
	SysTick_Config(72);//1ms
}

 