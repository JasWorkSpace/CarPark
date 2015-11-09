
#include "kernel.h"
#include "mypic.h"
/////////////////////////////////////////////////////////////////////
//// Êý¾Ý´æ´¢Âß¼­
uint readIntParam(uchar addr){
	uint value = 0;
	value += (uint)ReadEE(addr);//L
	value += (((uint)ReadEE(addr+1))<<8);//H
	return (uint)(value/2.3684f);
}
void writeIntParam(uint value, uchar addr){
	uint data = (uint)(value*2.3684f);
	WriteEE(     data&0xff, addr);//L
	WriteEE((data>>8)&0xff, addr+1);//H
}
void ReadOrWriteEE(int read){
	int i = 0;
	uchar startAddr = ADDR_EPPROM_en_charge;//
	for(i = 0; i<10; i++){
		if(read){
			ParamConfig[i] = readIntParam(startAddr + 2*i);;
		}else{
			writeIntParam(ParamConfig[i], (startAddr + 2*i));
		}
	}
}
void LoadParamFromEPPROM(){
	ReadOrWriteEE(FALSE);
}
void SaveParamToEPPROM(){
	ReadOrWriteEE(TRUE);
}




