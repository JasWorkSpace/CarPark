


#define SET_HC595_latch (PORTA |= (1 << 1))//���������ݴ���8 λ���������½������������ݲ���
#define CLR_HC595_latch (PORTA &= ~(1 << 1))
#define SET_HC595_sclk (PORTA |= (1 << 2))//������������λ���½������ݲ���
#define CLR_HC595_sclk (PORTA &= ~(1 << 2))
#define SET_HC595_data (PORTA |= (1 << 0))//�������������
#define CLR_HC595_data (PORTA &= ~(1 << 0))

//����һ���ֽ�
void HC595_Send_Data(unsigned char byte)
{

}

void Display_num(uchar code)
{

}