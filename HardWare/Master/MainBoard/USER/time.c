#include <stm32f10x.h>
#include "time.h"

void InitSysTick1us()
{
	//�ں�ϵͳ��ʱ��
	SysTick_Config(72);//1ms
}

 