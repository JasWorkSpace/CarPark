#ifndef _GPIO_H_
#define _GPIO_H_

//GPIO≥ı ºªØ
void GPIOInit(void);
void DOControl(unsigned char channeln,unsigned char states);
unsigned char ReadDIState(unsigned char channeln);

#endif
