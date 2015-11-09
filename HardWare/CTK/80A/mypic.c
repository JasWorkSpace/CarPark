
#include "mypic.h"
/********************************************
������һЩ���õı�����������
*********************************************/
__CONFIG(0x061C);
__CONFIG(0x0233);



void WriteEE(uchar data, uchar addr)
{
    while(WR == 1);//�ȴ�д���
    
    GIE = 0; //���ж�
    EEADRL = addr;//д���ַ��Ϣ
    EEDATL = data;//д��������Ϣ
    EEPGD = 0;//����EEPROM
    CFGS = 0;
    WREN = 1; //дEEPROM����
    EECON2 = 0x55;//д���ض�ʱ��
    EECON2 = 0xaa;
    WR = 1; //ִ��д����
    GIE = 1; //���ж�
    while(WR == 1);//�ȴ�д���
    WREN = 0;//��ֹд��EEPROM
}

//EEPROM�����ݺ���
uchar ReadEE(uchar addr)
{
    while(RD == 1);//�ȴ������
    EEADRL = addr;//д��Ҫ����ַַ
    EEPGD = 0;//����EEPROM
    CFGS = 0;
    RD = 1;//ִ�ж�����
    while(RD == 1);//�ȴ������
    return EEDATL;//���ض�ȡ������
}

void system_state_init()
{
    //1��ϵͳʱ�����ã��ɿ����־���������ʹ��Ĭ�ϵ�4M�ڲ�����
	OSCCON = 0x70;      //FIC16F1933���ó�8M�ڲ���    OSCCON = 0x78;//FIC16F1933���ó�16M�ڲ�
	WDTCON = 0x18;		//���Ź�4s

    //2��I/O������
    TRISA = 0b00101010;		//
    TRISB = 0b00111110;		//RB3/RB4/RB5Ϊ��������
    TRISC = 0x00;			//ȫ���
    TRISD = 0x00;			//��Һ��������ʼȫ���
    TRISE = 0b00000010;		//

    ANSELA = 0b00101010;
    ANSELB = 0b00000110;
    ANSELE = 0b00000010;	//

    //3����ʱ������

    //��ʱ��0���ã�65us�ж�һ��
    // T0CS = 0;                              //ʹ���ڲ�ʱ��
    // PSA = 1;                               //��ʹ�÷�Ƶ
    // TMR0 = 191;                            //��256-65����65us�ж�һ�Σ����Ƶ�ʲ��ԣ�ͨ��΢�����ͬʱ�жϴ�������Ҳ��Ҫ����

    //��ʱ��1���ã�100ms�ж�һ�Σ�ʹ��TIMER1
    TMR1H = (65536 - (100000 / 4)) >> 8;
    TMR1L = (65536 - (100000 / 4)) & 0xFF;//100ms�ж�һ��
    PEIE = 1;                             //���������ж�
    TMR1IF = 0;                           //���timer1�жϱ�־λ
    TMR1IE = 1;                           //����timer1����ж�
    T1CON = 0x31;                         //�ڲ�8/4 = 2Mʱ�ӣ�8��Ƶ������4us������timer1
    TMR0IE = 1;
#if 1
    //��ʱ��2����
    TMR2IF = 0;                           //���timer2�жϱ�־λ
    T2CON = 0x06;                         //�ڲ�8/4 = 2Mʱ�ӣ�16��Ƶ���޺��Ƶ������timer2
    PR2 = 8;                              //����������Ϊ8us����ʱΪ64us�ж�һ�Σ�255��ֵѭ��һ�Σ�61.3Hz����
    TMR2IE = 1;                           //����timer2��PR2ƥ���ж�
#endif

    //��ʱ��4����	����������ѹ��1KHz
    TMR4IF = 0;                           //���timer4�жϱ�־λ
//    T4CON = 0x06;                         //�ڲ�8/4 = 2Mʱ�ӣ�16��Ƶ���޺��Ƶ������timer4
//    T4CON = 0b00000110;						//�ڲ�8/4 = 2Mʱ�ӣ�16��Ƶ���޺��Ƶ������timer4
    T4CON = 0b00000101;						//�ڲ�8/4 = 2Mʱ�ӣ�4��Ƶ���޺��Ƶ������timer4
    PR4 = 250;                              // ����������Ϊ2us����ʱΪ0.5ms�ж�һ��
    TMR4IE = 1;                           //����timer4��PR4ƥ���ж�

#if 0
    //��ʱ��6����
    TMR6IF = 0;                           //���timer6�жϱ�־λ
    T6CON = 0x06;                         //16Ԥ��Ƶ��2M/16 = 128k , ����Ϊ8us
    PR6 = 250;                            //�Ĵ�����˭�Ƚϵ�ֵ
    TMR6IE = 1;                           //����timer6��PR6ƥ���ж�
//  T6CON = 0x01;                         //�ڲ�8/4 = 2Mʱ�ӣ�4��Ƶ���޺��Ƶ���ر�timer6
//  T6CON = 0x05;                         //�ڲ�8/4 = 2Mʱ�ӣ�4��Ƶ���޺��Ƶ����timer6
#endif
    //4���ⲿ�ж�����
    
    IOCBP = 0x38;                          //�����ص�ƽ�仯�жϼĴ���
    IOCBN = 0x38;                          //�����ص�ƽ�仯�жϼĴ���
    // IOCIE = 1;                              //���������PORTB ���Ų����ж�

    //5���ж�����
    PEIE = 1;                              //���������ж�
    GIE = 1;                               //��ȫ���ж�

#if 1
	TURN_OFF_PwmCtrl;
	TURN_OFF_ChargeCtrl; 
	TURN_OFF_LoadCtrl;
	LoadFlag = LOAD_NOTCONNECT;
	LoadState = 1;//��������
	TURN_ON_OverCurrentSet;			//OverCurrentSet�øߣ��������
	CTKSoftDelay(20);//50us  1ms
	TURN_OFF_OverCurrentSet;		//OverCurrentSet���㣻����ʹ�ܹ���
	CTKSoftDelay(20);//50us  1ms
#endif

}

//add by Jas
uint getADValueOneTime(uchar channel){
	uint AD_Result = 0;
    FVRCON = 0b00000000;	//5V�����AD
	ADCON0 = (channel & 0xff); 
	ADCON1 = 0b10010000;;    //���ݽ���Ҷ��룬8��Ƶ���ڲ��ο���ѹ
	CTKSoftDelay(20);//1mS 	/*����ӳٺ�����xԼΪ50us*/
	ADGO = 1;//��ʼת��
	while(ADGO);
 	AD_Result = ADRESL & 0x00FF;	//��ȡADC�������
	AD_Result |= ADRESH <<8 ;
	ADCON0 = 0b00101000;    //��ʹ��ADת��
	CTKSoftDelay(20);//1mS 	/*����ӳٺ�����xԼΪ50us*/
	return AD_Result;
}
uint getADValue(uchar channel){
	uint AD_Result = 0;
	uint AD_OneResult = 0;
	uint max;
	uint min;
	uint i = 0 ;
	for (i = 0 ; i < 10; i++){
		AD_OneResult = getADValueOneTime(channel);
		AD_Result   += AD_OneResult;
		max = (max > AD_OneResult ? max : AD_OneResult);
		min = (min < AD_OneResult ? min : AD_OneResult);
	}
    return ((AD_Result - max - min) >> 3);
}
//end add by Jas


#if 1
/*ͨ��ADC��ȡ���ص�ѹ		�����ʵ��ֵ��(AD_R*5*44.1/5.1/1024)	*/
uint GetBatteryVoltage()   //�˴��о���ʱ��ȡƽ��ֵ�����壬��ϵͳЧ�����ȣ�ʡ�Լ�Ȩƽ��
{
	uchar channel = 0b00101001;    //RB1/AN10,ʹ��ADת��
	return getADValue(channel);
}
#endif
#if 1
/*���ذ��ѹ		�����ʵ��ֵ��	*/
uint GetSolarPanelVoltage()   //�˴��о���ʱ��ȡƽ��ֵ�����壬��ϵͳЧ�����ȣ�ʡ�Լ�Ȩƽ��
{
	uchar channel = 0b00100001;    //RB2/AN8,ʹ��ADת��
	return getADValue(channel);
}
#endif
#if 1
/*������������ѹ*/
uint GetChargeCurrentVoltage()   //�˴��о���ʱ��ȡƽ��ֵ�����壬��ϵͳЧ�����ȣ�ʡ�Լ�Ȩƽ��
{
	uchar channel = 0b00010001;    //RA5/AN4,ʹ��ADת��
	return getADValue(channel);
}
#endif
#if 1
/*�ŵ����������ѹ*/
uint GetLoadCurrentVoltage()   //�˴��о���ʱ��ȡƽ��ֵ�����壬��ϵͳЧ�����ȣ�ʡ�Լ�Ȩƽ��
{
	uchar channel = 0b00011001;    //RE1/AN6,ʹ��ADת��
	return getADValue(channel);
}
#endif



void SelectMode()
{
    switch(SystemModeType)
    {
        case TIME_MODE:
        {
            if((DAYTIME == 0) && LightTime) //������ǿ�����㣬����δ�����趨ʱ��
            {
                if(TimeModeHour >= 36000)   //����һСʱ36000
                {
                    TimeModeHour = 0;
                    LightTime--;
                }
                if(LightTime != 0)
                {
                    LoadOpen = 1;//���Կ�������
                }
                else
                {
                    LoadOpen = 0;//ʱ�䵽�رո��أ��ȴ���һ�ι�ǿ
                }
            }
            else if(DAYTIME == 1)  //������ǿ�����㣬���Ὺ������
            {
                LoadOpen = 0;//���ɿ�����
//                LightTime = ReadEE();//���ù�ǿʱ��
				TimeModeHour = 0;
            }
            break;
        }
        case LIGHT_MODE://��ʱû������ʱ20s�������趨
        {
            if(DAYTIME == 0)     //������ǿ������
            {
                LoadOpen = 1;//���Կ�������
            }
            else if(DAYTIME == 1)
            {
                LoadOpen = 0;//���ɿ�����
            }
            break;
        }
        case OUTPUT_MODE:
        {
            LoadOpen = 1;   //���ؿ��Գ���
            break;
        }
        case MANUAL_MODE:   //ͨ���������ƣ����ֶ�ģʽ�£�ÿ��һ�ΰ����ı�һ�¸���״̬
        {
            break;
        }
    }
}


/*���ݷ�����ѹ���жϸ��������������Ӧ�Ĵ������践��*/
void LoadCurrentDealWith()
{
    if((BatteryState != BATTERY_STATE_VOLTAGE_UNDER) && (BatteryState != BATTERY_STATE_VOLTAGE_OVER) && LoadOpen) 
	//�������״̬&&ģʽΪ����򿪸���&&������ģʽ
    {
        uint FbVoltage;
        FbVoltage = GetLoadCurrentVoltage();                       //��ȡ������ѹ
        FbVoltage = 0;
        if((FbVoltage < LOAD_FB_VOLTAGE_NORMAL) && (LoadState == 1))            //��������
        {
            if(LoadFlag == LOAD_NOTCONNECT)
            {
                LoadState = 1;                        //�޸ĸ���������־λ�����Կ�������
                OverLoadTime = 0;                     //����û�й��أ�����Ҫ��ʱ
                TURN_ON_LoadCtrl;                     //��ͨ����
                LoadFlag = LOAD_CONNECT;
            }
        }
        if((FbVoltage >= LOAD_FB_VOLTAGE_NORMAL) || (LoadState == 0))           //���ز�����
        {
            LoadState = 0;
            if(( FbVoltage >= LOAD_FB_VOLTAGE_OVER) || (LoadShort == 1))        //���ض�·����������������LoadShort���ܻ�ԭ
            {
                TURN_OFF_LoadCtrl;                            //�رո���
                LoadFlag = LOAD_NOTCONNECT;
                OverLoadTime = 0;                   //��·״̬Ҳ�����м�ʱ
                LoadShort = 1;

				SystemErrorState = 1;		//������ѭ��
				ErrorType = 5;				//��·����
            }
            else                                              //���ع��أ���δ��·
            {
                if(OverLoadTime == 0)                         //������ʱ���
                {
                    OverLoadTime = 1;
                }   
                if(OverLoadTime >= OVER_LOAD_TIME)  //5���ӵ�ʱ
                {
                    OverLoadTime = 1;
                    if(LoadFlag == LOAD_CONNECT)
                    {
                        TURN_OFF_LoadCtrl;                    //�رո���
                        LoadFlag = LOAD_NOTCONNECT;
                    }
                    else if(LoadFlag == LOAD_NOTCONNECT)
                    {
                        TURN_ON_LoadCtrl;                     //��ͨ����
                        LoadFlag = LOAD_CONNECT;
                        LoadState = 1;                        //�޸ĸ���������־λ
                        OverLoadTime = 0;                     //�رռ�ʱ
                    }
                }
            }
        }
    }
    else
    {
        TURN_OFF_LoadCtrl;              //�رո���
        LoadFlag = LOAD_NOTCONNECT;
		OverLoadTime = 0;
		LoadState = 1;
    }
}


void SolarPanelDealWith()
{
   uint SolarPanelVoltage; 
	if(PWMFlag == 0) //PWM��תû�п���
	{
		if(DutyRatio > 249)//������ڳ�磬ֱ��
		{
			TURN_OFF_ChargeCtrl;
			CTKSoftDelay(200);//10mS
			SolarPanelVoltage = GetSolarPanelVoltage();
			gSolarPanelVoltage = SolarPanelVoltage;
			TURN_ON_ChargeCtrl;
		}
		if(DutyRatio == 0)//���û�г��
		{
			TURN_OFF_ChargeCtrl;
			if(LPVCount == 0)//���ڵ�ѹ�����
			{
				TURN_ON_PwmCtrl;
				CTKSoftDelay(1000);//10mS  50mS
				SolarPanelVoltage = GetSolarPanelVoltage();
				gSolarPanelVoltage = SolarPanelVoltage;
				TURN_OFF_PwmCtrl;
			}
			else//�ڵ�ѹ�����
			{
				TURN_ON_PwmCtrl;
				if(LPVCount > 99)//10S����
				{
					LPVCount = 0;
					SolarPanelVoltage = GetSolarPanelVoltage();
					gSolarPanelVoltage = SolarPanelVoltage;
					TURN_OFF_PwmCtrl;
					LPVFlag = 1;
				}
			}
		}
	}
	if(PWMFlag == 1)//PWM��ת�Ѿ�����
	{
		TURN_OFF_ChargeCtrl;
		PWMFlag = 0;						   //�ر�PWM���

		TURN_ON_PwmCtrl;
		CTKSoftDelay(200);//10mS  50us*200=
	    SolarPanelVoltage = GetSolarPanelVoltage();           //��ȡ̫���ܵ�ذ�ĵ�ѹ
		gSolarPanelVoltage = SolarPanelVoltage;
		TURN_OFF_PwmCtrl; 
		PWMFlag = 1;						   //����PWM���
		TURN_ON_ChargeCtrl;
	}
    if(gSolarPanelVoltage <= 24)
    {
		PVState = 1;                                      //�ܳ��״̬
    }
    else												  //����
    {
		PVState = 0;                                      //���ܳ��״̬
		PVChargeFlag = 0;//���ص�ѹ���߻���û�йⲻ����ˣ�LEDҪָʾ������
    }

    if(((gBatteryVoltage*26) - (gSolarPanelVoltage*23)) > *(BatteryStandard + DAY_VOLTAGE))//  �й⣬ ���ش���7V   DAY_VOLTAGE(7V) 4840
    {
		LPVFlag = 0;	//��ѹ����־
		LPVCount = 0;	//��ѹ����ʱ
		Led_C = 1;		//������
		DAYTIME = 1;
    }
    if(((gBatteryVoltage*26) - (gSolarPanelVoltage*23)) < 3686)//�����������ص�ѹ����6V�����ǲ����ж��Ƿ��Ǻ�ҹ
    {
		if(DAYTIME == 1)//��ǰ״̬�ǰ���ſ���������Ѿ���ҹ���ˣ��ǾͲ���
		{
			if(LPVCount == 0)
			{
				LPVCount = 1;//������ʱ��ʱ��100mS��ʱһ��
			}
		}
    }
	if(LPVFlag == 1)//��ѹ������
	{
		LPVFlag = 0;	//��ѹ����־
		LPVCount = 0;	//��ѹ����ʱ
		if(((gBatteryVoltage*26) - (gSolarPanelVoltage*23)) < *(BatteryStandard + NIGHT_VOLTAGE))//  û�й⣬ ����С��3V   NIGHT_VOLTAGE(3V) 1908
	    {
			Led_C = 0;//������
			DAYTIME = 0;
	    }
	}	
}



void PWMCharge()
{
	uint ChangeBatteryVoltag;                   //Ϊʲô���ܶ���  uchar BatteryVoltag��������ֱ��ʹ���������ﶨ����ģ���������
	ChangeBatteryVoltag = GetBatteryVoltage();
	if((EnhanceChargeFlag == 1) || (EqualizingChargeFlag == 1) || (FloatingChargeFlag == 1))
	{
		TURN_ON_ChargeCtrl;

		if(ChangeBatteryVoltag < ClampVoltage)
		{
			PVChargeFlag = 1;//���ڳ���У�LEDҪѭ��ָʾ
			if(DutyRatio > 249)
			{
				TMR2IE = 0;                              //�ر�timer2�ж�					//��PWM***********************	
				T2Flag = 0;
				PWMFlag = 0;						   //�ر�PWM���
	            TURN_ON_PwmCtrl;					   //ֱ�Ӵ����
			}
			else 
			{
				if(T2Flag == 0)                        //�����δ���ж�
				{
					TMR2IE = 1;                              //��timer2�ж�				    	//��PWM************************
					T2Flag = 1;
					PWMFlag = 1;						   //����PWM���
				}
				DutyRatio = DutyRatio+10;
			}
		}
		if(ChangeBatteryVoltag > ClampVoltage)
		{
			if(DutyRatio < 9)
			{
				TMR2IE = 0;                              //�رմ�timer2�ж�			   		//��PWM************************	
				T2Flag = 0;
				PWMFlag = 0;						   //�ر�PWM���
				TURN_OFF_PwmCtrl;
				PVChargeFlag = 0;//���ص�ѹ���߻���û�йⲻ����ˣ�LEDҪָʾ������
			}
			else 
			{
				PVChargeFlag = 1;//���ڳ���У�LEDҪѭ��ָʾ
				if(T2Flag == 0)							//�����δ���ж�
				{
					TMR2IE = 1;                              //��timer2�ж�				    	//��PWM************************
					T2Flag = 1;
					PWMFlag = 1;						   //����PWM���
				}
				DutyRatio = DutyRatio-10;
			}
		}
	}
	else
	{
		if(T2Flag == 1)                //����Ѿ����ж�
		{
			TMR2IE = 0;                  //�ر�timer2�ж�	
			T2Flag = 0;
			PWMFlag = 0;			   //�ر�PWM���         
		}
		PwmCount = 0;
		DutyRatio = 0;		
		TURN_OFF_ChargeCtrl;           //�رճ�翪��1
//		TURN_OFF_PwmCtrl;			   //��PWM���	
		PVChargeFlag = 0;//���ص�ѹ���߻���û�йⲻ����ˣ�LEDҪָʾ������
	} 
}

uchar BatteryStateSwitch(uint BatteryVoltage)
{
	uint adjust = (TemVoltage + ADVoltage - TemBase - ADBase );
	if(BatteryVoltage < *(BatteryStandard + BATTERY_VOLTAGE_UNDER) + adjust){
        return BATTERY_STATE_VOLTAGE_UNDER;
    }else if(BatteryVoltage >= (*(BatteryStandard + BATTERY_VOLTAGE_UNDER) + adjust) 
			&& BatteryVoltage < (*(BatteryStandard + BATTERY_VOLTAGE_WARN) + adjust)){
        return BATTERY_STATE_VOLTAGE_WARN;
    }else if(BatteryVoltage >= (*(BatteryStandard + BATTERY_VOLTAGE_WARN) + adjust) 
			&& BatteryVoltage <( *(BatteryStandard + RETURN_VOLTAGE_UNDER) + adjust)){
        return BATTERY_STATE_VOLTAGE_NORMAL_50;
    }else if(BatteryVoltage >= (*(BatteryStandard + RETURN_VOLTAGE_UNDER) + adjust) 
			&& BatteryVoltage < (*(BatteryStandard + BATTERY_VOLTAGE_NORMAL) + adjust)){
        return BATTERY_STATE_VOLTAGE_NORMAL_75;
    }else if(BatteryVoltage >= (*(BatteryStandard + BATTERY_VOLTAGE_NORMAL) + adjust) 
			&& BatteryVoltage < (*(BatteryStandard + BATTERY_VOLTAGE_FULL) + adjust)){
        return BATTERY_STATE_VOLTAGE_FULL;
    }else {
        return BATTERY_STATE_VOLTAGE_OVER;
    }
    /*if(BatteryVoltage < *(BatteryStandard + BATTERY_VOLTAGE_UNDER) - TemBase + TemVoltage - ADBase + ADVoltage)
        return BATTERY_STATE_VOLTAGE_UNDER;
    else if(BatteryVoltage >= (*(BatteryStandard + BATTERY_VOLTAGE_UNDER) - TemBase + TemVoltage - ADBase + ADVoltage) 
			&& BatteryVoltage < (*(BatteryStandard + BATTERY_VOLTAGE_WARN) - TemBase + TemVoltage - ADBase + ADVoltage))
        return BATTERY_STATE_VOLTAGE_WARN;
    else if(BatteryVoltage >= (*(BatteryStandard + BATTERY_VOLTAGE_WARN) - TemBase + TemVoltage - ADBase + ADVoltage) 
			&& BatteryVoltage <( *(BatteryStandard + RETURN_VOLTAGE_UNDER) - TemBase + TemVoltage - ADBase + ADVoltage))
        return BATTERY_STATE_VOLTAGE_NORMAL_50;
    else if(BatteryVoltage >= (*(BatteryStandard + RETURN_VOLTAGE_UNDER) - TemBase + TemVoltage - ADBase + ADVoltage) 
			&& BatteryVoltage < (*(BatteryStandard + BATTERY_VOLTAGE_NORMAL) - TemBase + TemVoltage - ADBase + ADVoltage))
        return BATTERY_STATE_VOLTAGE_NORMAL_75;
    else if(BatteryVoltage >= (*(BatteryStandard + BATTERY_VOLTAGE_NORMAL) - TemBase + TemVoltage - ADBase + ADVoltage) 
			&& BatteryVoltage < (*(BatteryStandard + BATTERY_VOLTAGE_FULL) - TemBase + TemVoltage - ADBase + ADVoltage))
        return BATTERY_STATE_VOLTAGE_FULL;
    else 
        return BATTERY_STATE_VOLTAGE_OVER;
        */
}


void SwitchBatteryState()
{
    switch(BatteryState)
    {
        case BATTERY_STATE_POWER_UP:
        {
        	lcd_state = 0;
			gBatteryVoltage = GetBatteryVoltage();//��һ���ϵ�Ҫ��״̬
            BatteryState = BatteryStateSwitch(gBatteryVoltage);
			EnhanceChargeFlag = 0;//��ʼ������  ������ͬ
			EnhanceCharge_Time = 0;
			EqualizingChargeFlag = 0;
			EqualizingCharge_Time = 0;
			FloatingChargeFlag = 0;
            BatteryLastState = BATTERY_STATE_POWER_UP;
            break;
        }
        case BATTERY_STATE_VOLTAGE_UNDER:   //10.8����  0%
        {
        	// added 0926
        	if (lcd_state == 2) {
        		// ֻ�д���״̬2�����ܽ��ĸ������״̬�ı�
        		lcd_move_char(11);
        		lcd_write_data(0xff);

        	}
            if(BatteryLastState != BATTERY_STATE_VOLTAGE_UNDER)//�״ν���
            {
//                ClampVoltage = *(BatteryStandard + Enhance_Charge_VOLTAGE) - TemBase + TemVoltage;
				EnhanceChargeFlag = 0;//��ʼ������  ������ͬ
				EnhanceCharge_Time = 0;
				EqualizingChargeFlag = 0;
                EqualizingCharge_Time = 0;
				FloatingChargeFlag = 0;
				BatteryLastState = BATTERY_STATE_VOLTAGE_UNDER; 
            }
            if(PVState == 1)//���Գ��
            {
				ClampVoltage = *(BatteryStandard + Enhance_Charge_VOLTAGE) - TemBase + TemVoltage - ADBase + ADVoltage;
				if(EnhanceChargeFlag == 0)//û�н����������
				{
					EnhanceChargeFlag = 1; 	//����������磬��ʼ��ʱ
					EnhanceCharge_Time = 0;
				}
            }
            else
            {
	            EnhanceChargeFlag = 0;              //���ɽ����������
	            EnhanceCharge_Time = 0;
	            gBatteryVoltage = GetBatteryVoltage();
	            if(gBatteryVoltage > *(BatteryStandard + RETURN_VOLTAGE_UNDER) - TemBase + TemVoltage - ADBase + ADVoltage)          //Ƿѹ���ص�ѹ
	            {
	                BatteryState = BatteryStateSwitch(gBatteryVoltage);
	            }
            }

            if(EnhanceCharge_Time >= CHARGE_TIME)                 //׼���л���һ״̬
			{
				EnhanceChargeFlag = 0;
				EnhanceCharge_Time = 0;//�Ѿ����㣬������ڱ�״̬��������¼�ʱ
				CTKSoftDelay(100);                                       // �ȴ�һ��ʱ�䣬�ȴ���ص�ѹ����
				gBatteryVoltage = GetBatteryVoltage();
				if(gBatteryVoltage > *(BatteryStandard + RETURN_VOLTAGE_UNDER) - TemBase + TemVoltage - ADBase + ADVoltage)         //Ƿѹ���ص�ѹ
				{
					BatteryState = BatteryStateSwitch(gBatteryVoltage);//�Ѿ����㣬������ڱ�״̬��������¼�ʱ
				}
			}
            break;
        }
        case BATTERY_STATE_VOLTAGE_WARN:  //10.8����12  25%
        {

            if(BatteryLastState != BATTERY_STATE_VOLTAGE_WARN)     //��һ�ν����״̬
            {
//                ClampVoltage = *(BatteryStandard + Equalizing_Charge_VOLTAGE) - TemBase + TemVoltage;  //ǯλ��ѹ 
				EnhanceChargeFlag = 0;//��ʼ������  ������ͬ
				EnhanceCharge_Time = 0;
				EqualizingChargeFlag = 0;
                EqualizingCharge_Time = 0;
				FloatingChargeFlag = 0;
				BatteryLastState = BATTERY_STATE_VOLTAGE_WARN;
            }
            if(PVState == 1)                                     //�ܳ��״̬
            {
                ClampVoltage = *(BatteryStandard + Equalizing_Charge_VOLTAGE) - TemBase + TemVoltage - ADBase + ADVoltage;  //ǯλ��ѹ 
				if(EqualizingChargeFlag == 0)//û�н��г��
				{
					EqualizingChargeFlag = 1; 	//���о����磬��ʼ��ʱ
					EqualizingCharge_Time = 0;	//���¼�ʱ
				}
				gBatteryVoltage = GetBatteryVoltage();
				if(gBatteryVoltage <  *(BatteryStandard + BATTERY_VOLTAGE_UNDER) - TemBase + TemVoltage - ADBase + ADVoltage) //���Ĺ�����Ҳ�������µ�
				{
					BatteryState = BATTERY_STATE_VOLTAGE_UNDER;
				}
            }
            else//û�й�������жϵ�ص�ѹ״̬
            {
                EqualizingChargeFlag = 0;
                EqualizingCharge_Time = 0;
                gBatteryVoltage = GetBatteryVoltage();
                BatteryState = BatteryStateSwitch(gBatteryVoltage);
            }

            if(EqualizingCharge_Time >= CHARGE_TIME)              //׼���л���һ״̬
            {
                EqualizingChargeFlag = 0;
                EqualizingCharge_Time = 0;//�Ѿ����㣬������ڱ�״̬��������¼�ʱ
                CTKSoftDelay(100);
                gBatteryVoltage = GetBatteryVoltage();

                BatteryState = BatteryStateSwitch(gBatteryVoltage);//�Ѿ����㣬������ڱ�״̬��������¼�ʱ
            }
            break; 
        }
        case BATTERY_STATE_VOLTAGE_NORMAL_50:		//12����12.8  50%
        {
            if(BatteryLastState != BATTERY_STATE_VOLTAGE_NORMAL_50)     //��һ�ν����״̬
            {
//                ClampVoltage = *(BatteryStandard + Equalizing_Charge_VOLTAGE) - TemBase + TemVoltage;  //ǯλ��ѹ
				EnhanceChargeFlag = 0;//��ʼ������  ������ͬ
				EnhanceCharge_Time = 0;
				EqualizingChargeFlag = 0;
                EqualizingCharge_Time = 0;
				FloatingChargeFlag = 0;
	            BatteryLastState = BATTERY_STATE_VOLTAGE_NORMAL_50;

            }
            if(PVState == 1)                                     //�ܳ��״̬
            {
                ClampVoltage = *(BatteryStandard + Equalizing_Charge_VOLTAGE) - TemBase + TemVoltage - ADBase + ADVoltage;  //ǯλ��ѹ
				if(EqualizingChargeFlag == 0)//û�н��г��
				{
					EqualizingChargeFlag = 1; 	//���о����磬��ʼ��ʱ
					EqualizingCharge_Time = 0;	//���¼�ʱ
				}
				gBatteryVoltage = GetBatteryVoltage();
				if(gBatteryVoltage <  *(BatteryStandard + RETURN_VOLTAGE_UNDER) - TemBase + TemVoltage - ADBase + ADVoltage) //���Ĺ�����Ҳ�������µ�
				{
					BatteryState = BatteryStateSwitch(gBatteryVoltage);
				}
            }
            else
            {
                EqualizingChargeFlag = 0;
                EqualizingCharge_Time = 0;
                gBatteryVoltage = GetBatteryVoltage();
                BatteryState = BatteryStateSwitch(gBatteryVoltage);
            }

            if(EqualizingCharge_Time >= CHARGE_TIME)              //׼���л���һ״̬
            {
                EqualizingChargeFlag = 0;
                EqualizingCharge_Time = 0;//�Ѿ����㣬������ڱ�״̬��������¼�ʱ
                CTKSoftDelay(100);
                gBatteryVoltage = GetBatteryVoltage();

                BatteryState = BatteryStateSwitch(gBatteryVoltage);//�Ѿ����㣬������ڱ�״̬��������¼�ʱ
            }
            break;
        }
        case BATTERY_STATE_VOLTAGE_NORMAL_75:		//12.8����13.2  75%
        {
            if(BatteryLastState != BATTERY_STATE_VOLTAGE_NORMAL_75)     //��һ�ν����״̬
            {
//                ClampVoltage = *(BatteryStandard + Equalizing_Charge_VOLTAGE) - TemBase + TemVoltage;  //ǯλ��ѹ
				EnhanceChargeFlag = 0;//��ʼ������  ������ͬ
				EnhanceCharge_Time = 0;
				EqualizingChargeFlag = 0;
                EqualizingCharge_Time = 0;
				FloatingChargeFlag = 0;
	            BatteryLastState = BATTERY_STATE_VOLTAGE_NORMAL_75;

            }
            if(PVState == 1)                                     //�ܳ��״̬
            {
                ClampVoltage = *(BatteryStandard + Equalizing_Charge_VOLTAGE) - TemBase + TemVoltage - ADBase + ADVoltage;  //ǯλ��ѹ
				if(EqualizingChargeFlag == 0)//û�н��г��
				{
					EqualizingChargeFlag = 1; 	//���о����磬��ʼ��ʱ
					EqualizingCharge_Time = 0;	//���¼�ʱ
				}
				gBatteryVoltage = GetBatteryVoltage();
				if(gBatteryVoltage <  *(BatteryStandard + BATTERY_VOLTAGE_NORMAL) - TemBase + TemVoltage - ADBase + ADVoltage) //���Ĺ�����Ҳ�������µ�
				{
					BatteryState = BatteryStateSwitch(gBatteryVoltage);
				}
            }
            else
            {
                EqualizingChargeFlag = 0;
                EqualizingCharge_Time = 0;
                gBatteryVoltage = GetBatteryVoltage();
                BatteryState = BatteryStateSwitch(gBatteryVoltage);
            }

            if(EqualizingCharge_Time >= CHARGE_TIME)              //׼���л���һ״̬
            {
                EqualizingChargeFlag = 0;
                EqualizingCharge_Time = 0;//�Ѿ����㣬������ڱ�״̬��������¼�ʱ
                CTKSoftDelay(100);
                gBatteryVoltage = GetBatteryVoltage();

                BatteryState = BatteryStateSwitch(gBatteryVoltage);//�Ѿ����㣬������ڱ�״̬��������¼�ʱ
            }
            break;
        }
        case BATTERY_STATE_VOLTAGE_FULL:		//13.2����  100%
        {
            if(BatteryLastState != BATTERY_STATE_VOLTAGE_FULL)     //��һ�ν����״̬
            {
//                ClampVoltage = *(BatteryStandard + Floating_Charge_VOLTAGE) - TemBase + TemVoltage;            //ǯλ��ѹ
				EnhanceChargeFlag = 0;//��ʼ������  ������ͬ
				EnhanceCharge_Time = 0;
				EqualizingChargeFlag = 0;
                EqualizingCharge_Time = 0;
				FloatingChargeFlag = 0;
				BatteryLastState = BATTERY_STATE_VOLTAGE_FULL;
            }
            if(PVState == 1)                                       //�ܳ��״̬
            {
                ClampVoltage = *(BatteryStandard + Floating_Charge_VOLTAGE) - TemBase + TemVoltage - ADBase + ADVoltage;            //ǯλ��ѹ
                FloatingChargeFlag = 1;                            //���и�����
            }
            else
            {
                FloatingChargeFlag = 0;
            }

            gBatteryVoltage = GetBatteryVoltage();                 //��ȡ���ص�ѹ
            BatteryState = BatteryStateSwitch(gBatteryVoltage);      //���ݵ�ǰ�����ص�ѹ�����л�
            break;
        }
        case BATTERY_STATE_VOLTAGE_OVER:
        {
            if(BatteryLastState != BATTERY_STATE_VOLTAGE_OVER)       //��һ�ν����״̬
            {
                ClampVoltage = 0;           //ǯλ��ѹ 
                TMR2IE = 0;                                          //�ر�timer2�ж� //��PWM���
                T2Flag = 0;
                PWMFlag = 0;                                        //�ر�PWM���
				TURN_OFF_ChargeCtrl;
				TURN_OFF_PwmCtrl;

				EnhanceChargeFlag = 0;//��ʼ������  ������ͬ
				EnhanceCharge_Time = 0;
				EqualizingChargeFlag = 0;
                EqualizingCharge_Time = 0;
				FloatingChargeFlag = 0;
				BatteryLastState = BATTERY_STATE_VOLTAGE_OVER;
            }
            gBatteryVoltage = GetBatteryVoltage();                 //��ȡ���ص�ѹ
            if(gBatteryVoltage < *(BatteryStandard + RETURN_VOLTAGE_FULL) - TemBase + TemVoltage - ADBase + ADVoltage)           //��ѹ���ص�ѹ
            {
                BatteryState = BatteryStateSwitch(gBatteryVoltage);
            }
            break;
        }
        case BATTERY_STATE_ERROR:
        {
        }
    }
}



void KaiJi()
{
	//����Ӳ��֣�������ʾ�Լ����
	//�Լ����ֻҪ��������Ӷ�Ӧ�Ĵ������;����ˣ������ѹ���Ͱ�ErrorType��1�����ض�·���Ͱ�ErrorType��2֮���
	//����Ӳ��֣�������ʾ��ǰϵͳ��ѹ�����Ƿ�����Լ����
	gBatteryVoltage = GetBatteryVoltage();
	if(gBatteryVoltage < 391)
	{
		BatteryStandard = Battery_12V;
		System12V = 1;
		System24V = 0;
	}
	else if((gBatteryVoltage < 782) && (gBatteryVoltage > 512))
	{
		BatteryStandard = Battery_24V;
		System12V = 0;
		System24V = 1;
	}
	else
	{
		SystemErrorState = 1;
		ErrorType = 1;
		System12V = 0;
		System24V = 0;
	}

//����·��	����7	����ʱ&�����ʱ
//�ŵ��·��	����8	����ʱ&���ŵ�ʱ
	SDSolarPanelVoltage = GetSolarPanelVoltage();
	SDBatteryVoltage = GetBatteryVoltage();
	if((SDSolarPanelVoltage*47) < ((SDBatteryVoltage*48) - 2400) 
	|| (SDSolarPanelVoltage*47) > ((SDBatteryVoltage*48) + 2400))// 50*48 ����·�� ����7
	{
		SystemErrorState = 1;
		ErrorType = 7;
		System12V = 0;
		System24V = 0;
	}
	gFbVoltage = GetLoadCurrentVoltage();
	if(gFbVoltage > 13)//�ŵ��·�� ����8	//�Ŵ󾫶�5mV�Ŵ�10��  ʵ�ʲ��Կ���10һ��  12.5��0.5A
	{
		SystemErrorState = 1;
		ErrorType = 8;
		System12V = 0;
		System24V = 0;
	}
	ADVoltage = 0;
	ADBase = 0;
	PVCount = 0;
}


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
uchar GetBoradTemperature()   //8λ���У����þ�ȷ
{
	FVRCON = 0b00000000;	//5�����AD
	ADCON0 = 0b00001101;    //RA3/AN3,ʹ��ADת��
    ADCON1 = 0b00010000;;    //���ݽ������룬8��Ƶ���ڲ��ο���ѹ

	uchar x[6];
	uint i = 0 ;

	for (i = 0 ; i < 6; i++)
	{
		FVRCON = 0b00000000;	//5�����AD
	    ADCON0 = 0b00001101;    //RA3/AN3,ʹ��ADת��
	    ADCON1 = 0b00010000;;    //���ݽ������룬8��Ƶ���ڲ��ο���ѹ
		CTKSoftDelay(20);//1mS 	/*����ӳٺ�����xԼΪ50us*/
		ADGO = 1;//��ʼת��
		while(ADGO);
		x[i] = ADRESH;	//��ȡADC�������
		ADCON0 = 0b00001100;    //��ʹ��ADת��
	} 

	uint AD_Result = 0;
	uchar max , min;
	max = min = x[0]; 				//�ҳ����ֵ����Сֵ
	for(i=0;i<6;i++) 
	{
		if(max<x[i])
			max=x[i]; 
		if(min>x[i])
			min=x[i];
	}
	for(i=0;i<6;i++) 			//��ͣ�ȥ�����ֵ����Сֵ
	{
		AD_Result += x[i];
	}

	AD_Result = AD_Result - max - min;
	AD_Result = AD_Result >> 2;
    return AD_Result;
}
uchar GetOutTemperature()   //8λ���У����þ�ȷ
{
	FVRCON = 0b00000000;	//5�����AD
	ADCON0 = 0b00000101;    //RA3/AN3,ʹ��ADת��
    ADCON1 = 0b00010000;;    //���ݽ������룬8��Ƶ���ڲ��ο���ѹ

	uchar x[6];
	uint i = 0 ;

	for (i = 0 ; i < 6; i++)
	{
		FVRCON = 0b00000000;	//5�����AD
	    ADCON0 = 0b00000101;    //RA3/AN3,ʹ��ADת��
	    ADCON1 = 0b00010000;;    //���ݽ������룬8��Ƶ���ڲ��ο���ѹ
		CTKSoftDelay(20);//1mS 	/*����ӳٺ�����xԼΪ50us*/
		ADGO = 1;//��ʼת��
		while(ADGO);
		x[i] = ADRESH;	//��ȡADC�������
		ADCON0 = 0b00000100;    //��ʹ��ADת��
	} 

	uint AD_Result = 0;
	uchar max , min;
	max = min = x[0]; 				//�ҳ����ֵ����Сֵ
	for(i=0;i<6;i++) 
	{
		if(max<x[i])
			max=x[i]; 
		if(min>x[i])
			min=x[i];
	}
	for(i=0;i<6;i++) 			//��ͣ�ȥ�����ֵ����Сֵ
	{
		AD_Result += x[i];
	}

	AD_Result = AD_Result - max - min;
	AD_Result = AD_Result >> 2;
    return AD_Result;
}

void LedDisplay()//LED��ʾ&��Ƿѹ�������ʾ&������˸��ʾ
{
	if(LoadFlag == LOAD_CONNECT)
	{
		TURN_ON_Led_F;
	}
	if(LoadFlag == LOAD_NOTCONNECT)
	{
		TURN_OFF_Led_F;
	}

	if(PVChargeFlag == 1)//���ڳ����
	{
		TURN_ON_Led_C;
	}
	if(PVChargeFlag == 0)//���ص�ѹ���߻���û�йⲻ����ˣ�LEDҪָʾ������
	{
		TURN_OFF_Led_C;	
	}
}


