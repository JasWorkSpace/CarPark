#ifndef _EXIT_H_
#define _EXIT_H_
typedef struct 
{
	u32 risingEdgeCount[2];
	u32 fallingEdgeCount[2];
	unsigned char indexNo;
	unsigned char direction;
	u32 timeCount;
}SpeedDetectStruct;
typedef struct  
{
	unsigned char direction;
	unsigned char timeCount[4];
}SpeedStruct;
void InitExit(void);
#endif
