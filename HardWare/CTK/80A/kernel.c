
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
	uchar startAddr = ADDR_EPPROM_battery_type;//
	if(read){
		HW_BATTERY_TYPE = (uchar)readIntParam(startAddr);
	}else{
		writeIntParam((uint)HW_BATTERY_TYPE, startAddr);
	}
	for(i = 1; i<10; i++){//see EPPROM ADDR alloction, in Kernel.h
		if(read){
			ParamConfig[HW_BATTERY_TYPE_SELF][i] = readIntParam(startAddr + 2*i);;
		}else{
			writeIntParam(ParamConfig[HW_BATTERY_TYPE_SELF][i], (startAddr + 2*i));
		}
	}
}
void LoadParamFromEPPROM(){
	ReadOrWriteEE(FALSE);
}
void SaveParamToEPPROM(){
	ReadOrWriteEE(TRUE);
}




