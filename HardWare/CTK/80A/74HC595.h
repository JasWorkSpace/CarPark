


#define SET_HC595_latch (PORTA |= (1 << 1))//上升沿数据打入8 位锁存器，下降沿锁存器数据不变
#define CLR_HC595_latch (PORTA &= ~(1 << 1))
#define SET_HC595_sclk (PORTA |= (1 << 2))//上升沿数据移位，下降沿数据不变
#define CLR_HC595_sclk (PORTA &= ~(1 << 2))
#define SET_HC595_data (PORTA |= (1 << 0))//串行数据输入端
#define CLR_HC595_data (PORTA &= ~(1 << 0))

//发送一个字节
void HC595_Send_Data(unsigned char byte)
{

}

void Display_num(uchar code)
{

}