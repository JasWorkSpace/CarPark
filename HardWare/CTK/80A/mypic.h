/********************************************
这里存放一些公用的变量、函数等
*********************************************/
__CONFIG(0x061C);
__CONFIG(0x0233);

#define uchar unsigned char
#define uint  unsigned int

//开关控制宏
#define TURN_ON_Led_C		(LATB |= (1 << 6))
#define TURN_OFF_Led_C		(LATB &= ~(1 << 6))

#define TURN_ON_Led_F		(LATB |= (1 << 7))
#define TURN_OFF_Led_F		(LATB &= ~(1 << 7))

#define TURN_ON_Lcd_L		(LATC &= ~(1 << 4))
#define TURN_OFF_Lcd_L		(LATC |= (1 << 4))

#define TURN_OFF_LoadCtrl	(PORTA |= (1 << 4))		//负载开关
#define TURN_ON_LoadCtrl	(PORTA &= ~(1 << 4))

#define TURN_OFF_PwmCtrl	(PORTA |= (1 << 0))		//PwmCtrl开关
#define TURN_ON_PwmCtrl		(PORTA &= ~(1 << 0))

#define TURN_OFF_ChargeCtrl	(PORTB |= (1 << 0))		//ChargeCtrl开关
#define TURN_ON_ChargeCtrl	(PORTB &= ~(1 << 0))

#define TURN_ON_OverCurrentSet	(PORTE |= (1 << 0))     //OverCurrentSet置高；清除过流
#define TURN_OFF_OverCurrentSet	(PORTE &= ~(1 << 0))	//OverCurrentSet清零；可以使能过流

#define Led_C		RB6
#define Led_F		RB7
#define KEY_UP		RB3
#define KEY_DOWN	RB5
#define KEY_SET 	RB4

/*系统模式定义*/
#define OUTPUT_MODE                 0x01    //固定输出模式
#define LIGHT_MODE                  0x02    //光控模式
#define MANUAL_MODE                 0x03    //按钮用来控制输出
#define TEST_MODE                   0x04    //
#define TIME_MODE                   0X05    //光控+时控

/*系统状态定义*/
#define BATTERY_STATE_POWER_UP			0	//系统第一次上电
#define BATTERY_STATE_VOLTAGE_UNDER  	1	//蓄电池欠压
#define BATTERY_STATE_VOLTAGE_WARN   	2	//蓄电池欠压告警
#define BATTERY_STATE_VOLTAGE_NORMAL_50 3	//蓄电池电压正常
#define BATTERY_STATE_VOLTAGE_NORMAL_75 4	//蓄电池电压正常
#define BATTERY_STATE_VOLTAGE_FULL   	5	//蓄电池满
#define BATTERY_STATE_VOLTAGE_OVER   	6	//蓄电池过压
#define BATTERY_STATE_ERROR          	7	//系统错误 

/*定义负载接入标志*/
#define LOAD_CONNECT			0		//负载已经接入
#define LOAD_NOTCONNECT			1		//负载断开

/*负载反馈电压阈值*/
#define LOAD_FB_VOLTAGE_NORMAL	134//125	//反馈电压小于该阈值，10A/20A：500mV取值125（实际134）；30A：750mV取值188
#define LOAD_FB_VOLTAGE_OVER	400			//反馈电压大于上个阈值，小于本阈值，则负载过载。10A/20A：600mV取值150；30A：900mV取值225
											//触发后直接大于2V

#define CHARGE_TIME				36000		//使用定时器1，充电时间，100ms中断，充电时间为1小时
#define OVER_LOAD_TIME			3000//		100//3000	//定义断开和接通负载的时间间隔(定时器设置为每100ms进行一次中断，5分钟的间隔需要3000次中断)

/*数组阈值
转化成二进制取前八位 */
#define BATTERY_VOLTAGE_UNDER           0   //
#define RETURN_VOLTAGE_UNDER            1   //
#define BATTERY_VOLTAGE_WARN            2   //
#define BATTERY_VOLTAGE_NORMAL          3   //
#define BATTERY_VOLTAGE_FULL            4   //
#define RETURN_VOLTAGE_FULL             5   //
#define Enhance_Charge_VOLTAGE          6   //
#define Equalizing_Charge_VOLTAGE       7   //
#define Floating_Charge_VOLTAGE         8   //
#define DAY_VOLTAGE                     9	//白天黑夜判定阈值，可以开关灯DAY_VOLTAGE(7V) 62, NIGHT_VOLTAGE(3V) 26
#define NIGHT_VOLTAGE                   10
/*变量定义区*/

uint *BatteryStandard = (void *)0; //定义一个指针指向某个标准的数组
/*  前六个数字分别代表
BATTERY_VOLTAGE_UNDER （10.8）, RETURN_VOLTAGE_UNDER （12.8）, BATTERY_VOLTAGE_WARN （12.0）, BATTERY_VOLTAGE_NORMAL （13.2）,
BATTERY_VOLTAGE_FULL （16.5）, RETURN_VOLTAGE_FULL（15.0）

后三个数字分别代表
Enhance_Charge_VOLTAGE （14.8）, Equalizing_Charge_VOLTAGE （14.4）, Floating_Charge_VOLTAGE（13.8）
再后面两位是白天黑夜判定阈值，可以开关灯

					  /*10.8  12.8  12    13.2  16.5  15*/
uint Battery_12V[]   = {256 , 303 , 284 , 313 , 391 , 355 , 351 , 341 , 327 , 3965 , 900};  //12V系统，其他系统自行添加
uint Battery_24V[]   = {512 , 606 , 568 , 626 , 782 , 710 , 702 , 682 , 654 , 8231 , 3333};  //24V系统，其他系统自行添加

uchar SystemErrorState = 0;
uchar SystemModeType = MANUAL_MODE;        //保存当前系统所处的状态
uchar EENum = 0;                            //保存EEPROM中的数字
uchar BatteryState = BATTERY_STATE_POWER_UP; //蓄电池状态默认为第一次上电状态
uchar BatteryLastState = BATTERY_STATE_POWER_UP;

bit LoadOpen = 0;               //决定负载是否可以打开
bit LoadState = 0;              //为0负载不正常，为1负载正常
bit LoadShort = 0;              //为0正常，为1负载短路过
bit LoadFlag = 0;               //为0负载接入，为1断开
bit PVState = 0;                //为0不能充电，为1可以充电

bit EnhanceChargeFlag = 0;			//提升充电是否开启
bit EqualizingChargeFlag = 0;		//均衡充电是否开启
bit FloatingChargeFlag = 0;			//浮充充电是否开启
bit PWMChargeFlag = 0;				//time1中，100ms中断一次，ChangePWMFlag翻转一次，更改一次PWM占空比
bit PWMFlag = 0;					//为一时可以产生PWM
bit T2Flag = 0;						//监视T0是否打开
bit PVChargeFlag = 0;				//因电池电压过高或者没有光不充电了，LED要指示电量了

bit System12V = 0;		//默认12V系统
bit System24V = 0;

bit DAYTIME = 0;		//为1是白天；为0是夜晚

uchar LightTime = 0;			//时控模式中时控时间
uint TimeModeHour = 0;			//用来记录时控模式的一小时时间
uint EnhanceCharge_Time = 0;        //提升充电定时标志（定时器设置为每100ms进行一次中断，
									//则需要进行36000次中断才能记够一个小时，需要注意EnhanceCharge_Time的类型）

uint EqualizingCharge_Time = 0;     //均衡充电定时标志
uint OverLoadTime = 0 ;             //负载过载时，放电定时

uint SDSolarPanelVoltage = 0;		//每隔一段时间要自检一次充电电路，用来保存自检用的光电池板电压
uint SDBatteryVoltage = 0;			//每隔一段时间要自检一次充电电路，用来保存自检用的蓄电池板电压，当时的

uint gSolarPanelVoltage;    //用来记录太阳能电池板电压的全局变量
uint gBatteryVoltage;       //用来记录电池电压的全局变量
uint gFbVoltage;
uint ClampVoltage = 0;		//钳位电压
uint TemVoltage = 0;		//温度补偿电压
uint TemBase = 0;			//温度补偿电压所用的基础，为了消除负号，先减去该值，再加上温度补偿电压
uint ADVoltage = 0;			//自动校正电压
uint ADBase = 0;			//自动校正电压所用的基础，为了消除负号，先减去该值，再加上自动校正电压

uchar DutyRatio = 0;		//0-255，数值越大占空比越大
uchar PwmCount = 0;			//当前计数值
uchar PVCount = 0;			//电池板5秒钟检测一次，用在定时器1中，=50的时候5秒


uchar ErrorType = 0;		//用于标记哪种错误的

uint SystemErrorCount = 0;	//系统错误后，5分钟自动重启

bit LPVFlag = 0;			//低压检测标志
uchar LPVCount = 0;			//低压检测计时

uchar globalState = 0;

// 该段从lm016.h移过来，标识LCD的状态
uchar lcd_state = 0;
uchar lcd_setting_state = 0;
uchar lcd_timer = 0;
uchar lcd_extinguwish_timer = 0; // 熄灭定时器
uchar enter_settings_timer = 0;  // 长按键入设置模式
uchar setting_save_timer = 0; // 长按保存
uchar setting_no_save_timer = 0; // 超时不保存
bit isNeedChange = 0;


uint en_charge; //(提升充电)
uint eq_charge;//(均衡充电)
uint flo_charge;//(浮充充电)
uint time_charge;//(充电时间？)
uint under_vol;//（欠压电压）
uint re_under;//(欠压返回)
uint over_vol;//(过压电压)
uint re_over;//(过压回复)
uint day_vol;//(白天识别电压)
uint night_vol;//(夜晚识别电压)


/***********函数声明部分*******/
void lcd_init();
void lcd_write_command(uchar);
void lcd_write_data(uchar);
void lcd_wait_for_busy();
void lcd_print_line1(uchar const*, uchar, uchar);
void lcd_print_line2(uchar const*, uchar);
void lcd_move_char(uchar postion);
void num_to_char(int, uchar);
uint readFromEEPROM(uchar);
void saveToEEPROM(uint, uchar);

/*软件延迟函数，x约为50us*/
void delay(uint x)
{
    uint a,b;
    for(a=x;a>0;a--)
        for(b=10;b>0;b--);
} 
/*软件延迟函数，x约为50us*/
void Delay(uint x)
{
    uint a,b;
    for(a=x;a>0;a--)
        for(b=10;b>0;b--);
}
void system_state_init()
{
    //1、系统时钟配置，由控制字决定，这里使用默认的4M内部晶振
	OSCCON = 0x70;      //FIC16F1933配置成8M内部；    OSCCON = 0x78;//FIC16F1933配置成16M内部
	WDTCON = 0x18;		//看门狗4s

    //2、I/O口配置
    TRISA = 0b00101010;		//
    TRISB = 0b00111110;		//RB3/RB4/RB5为按键输入
    TRISC = 0x00;			//全输出
    TRISD = 0x00;			//接液晶屏，初始全输出
    TRISE = 0b00000010;		//

    ANSELA = 0b00101010;
    ANSELB = 0b00000110;
    ANSELE = 0b00000010;	//

    //3、定时器配置

    //定时器0配置，65us中断一次
    // T0CS = 0;                              //使用内部时钟
    // PSA = 1;                               //不使用分频
    // TMR0 = 191;                            //（256-65），65us中断一次，如果频率不对，通过微调此项，同时中断处理函数中也需要调整

    //定时器1配置，100ms中断一次，使用TIMER1
    TMR1H = (65536 - (100000 / 4)) >> 8;
    TMR1L = (65536 - (100000 / 4)) & 0xFF;//100ms中断一次
    PEIE = 1;                             //允许外设中断
    TMR1IF = 0;                           //清除timer1中断标志位
    TMR1IE = 1;                           //允许timer1溢出中断
    T1CON = 0x31;                         //内部8/4 = 2M时钟，8分频，周期4us，启动timer1
    TMR0IE = 1;
#if 1
    //定时器2配置
    TMR2IF = 0;                           //清除timer2中断标志位
    T2CON = 0x06;                         //内部8/4 = 2M时钟，16分频，无后分频，启动timer2
    PR2 = 8;                              //计数器周期为8us，定时为64us中断一次，255数值循环一次；61.3Hz左右
    TMR2IE = 1;                           //允许timer2与PR2匹配中断
#endif

    //定时器4配置	用来产生负压，1KHz
    TMR4IF = 0;                           //清除timer4中断标志位
//    T4CON = 0x06;                         //内部8/4 = 2M时钟，16分频，无后分频，启动timer4
//    T4CON = 0b00000110;						//内部8/4 = 2M时钟，16分频，无后分频，启动timer4
    T4CON = 0b00000101;						//内部8/4 = 2M时钟，4分频，无后分频，启动timer4
    PR4 = 250;                              // 计数器周期为2us，定时为0.5ms中断一次
    TMR4IE = 1;                           //允许timer4与PR4匹配中断

#if 0
    //定时器6配置
    TMR6IF = 0;                           //清除timer6中断标志位
    T6CON = 0x06;                         //16预分频，2M/16 = 128k , 周期为8us
    PR6 = 250;                            //寄存器和谁比较的值
    TMR6IE = 1;                           //允许timer6与PR6匹配中断
//  T6CON = 0x01;                         //内部8/4 = 2M时钟，4分频，无后分频，关闭timer6
//  T6CON = 0x05;                         //内部8/4 = 2M时钟，4分频，无后分频，打开timer6
#endif
    //4、外部中断设置
    
    IOCBP = 0x38;                          //正边沿电平变化中断寄存器
    IOCBN = 0x38;                          //负边沿电平变化中断寄存器
    // IOCIE = 1;                              //允许独立的PORTB 引脚产生中断

    //5、中断设置
    PEIE = 1;                              //允许外设中断
    GIE = 1;                               //打开全局中断

#if 1
	TURN_OFF_PwmCtrl;
	TURN_OFF_ChargeCtrl; 
	TURN_OFF_LoadCtrl;
	LoadFlag = LOAD_NOTCONNECT;
	LoadState = 1;//负载正常
	TURN_ON_OverCurrentSet;			//OverCurrentSet置高；清除过流
	Delay(20);//50us  1ms
	TURN_OFF_OverCurrentSet;		//OverCurrentSet清零；可以使能过流
	Delay(20);//50us  1ms
#endif

}

//EEPROM写入函数，默认写入地址为空间的0地址
void WriteEE(uchar data, uchar addr)
{
    while(WR == 1);//等待写完成
    
    GIE = 0; //关中断
    EEADRL = addr;//写入地址信息
    EEDATL = data;//写入数据信息
    EEPGD = 0;//操作EEPROM
    CFGS = 0;
    WREN = 1; //写EEPROM允许
    EECON2 = 0x55;//写入特定时序
    EECON2 = 0xaa;
    WR = 1; //执行写操作
    GIE = 1; //开中断
    while(WR == 1);//等待写完成
    WREN = 0;//禁止写入EEPROM
}

//EEPROM读数据函数
uchar ReadEE(uchar addr)
{
    while(RD == 1);//等待读完成
    EEADRL = addr;//写入要读的址址
    EEPGD = 0;//操作EEPROM
    CFGS = 0;
    RD = 1;//执行读操作
    while(RD == 1);//等待读完成
    return EEDATL;//返回读取的数据
}

// 载入所有的系统状态，从EEPROM中读取
void load_system_state() 
{
    // todo:在这里恢复EEPROM中写入的值
    			//////////////////////////////
				// type:1   en_charge(提升充电)
				// type:2   eq_charge(均衡充电)
				// type:3   flo_charge(浮充充电)
				// type:4   time_charge(充电时间？)
				// type:5   under_vol（欠压电压）
				// type:6   re_under(欠压返回)
				// type:7   over_vol(过压电压)
				// type:8   re_over(过压回复)
				// type:9   day_vol(白天识别电压)
				// type:10  night_vol(夜晚识别电压)
				// /////////////////////////////////
    en_charge = readFromEEPROM(1); //(提升充电)
	eq_charge = readFromEEPROM(2);//(均衡充电)
	flo_charge = readFromEEPROM(3);//(浮充充电)
	time_charge = readFromEEPROM(4);//(充电时间？)
	under_vol = readFromEEPROM(5);//（欠压电压）
	re_under = readFromEEPROM(6);//(欠压返回)
	over_vol = readFromEEPROM(7);//(过压电压)
	re_over = readFromEEPROM(8);//(过压回复)
	day_vol = readFromEEPROM(9);//(白天识别电压)
	night_vol = readFromEEPROM(10);//(夜晚识别电压)

}

// 保存所有的系统状态，将当前系统状态保存到系统中
void save_system_state() {
	saveToEEPROM(en_charge, 1);//(提升充电)
	saveToEEPROM(eq_charge, 2);//(均衡充电)
	saveToEEPROM(flo_charge, 3);//(浮充充电)
	saveToEEPROM(time_charge, 4);//(充电时间？)
	saveToEEPROM(under_vol, 5);//（欠压电压）
	saveToEEPROM(re_under, 6);//(欠压返回)
	saveToEEPROM(over_vol, 7);//(过压电压)
	saveToEEPROM(re_over, 8);//(过压回复)
	saveToEEPROM(day_vol, 9);//(白天识别电压)
	saveToEEPROM(night_vol, 10);//(夜晚识别电压)
}
//add by Jas
uint getADValueOneTime(uchar channel){
	uint AD_Result = 0;
    FVRCON = 0b00000000;	//5V分配给AD
	ADCON0 = (channel & 0xff); 
	ADCON1 = 0b10010000;;    //数据结果右对齐，8分频，内部参考电压
	Delay(20);//1mS 	/*软件延迟函数，x约为50us*/
	ADGO = 1;//开始转换
	while(ADGO);
 	AD_Result = ADRESL & 0x00FF;	//读取ADC采样结果
	AD_Result |= ADRESH <<8 ;
	ADCON0 = 0b00101000;    //不使能AD转换
	Delay(20);//1mS 	/*软件延迟函数，x约为50us*/
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
/*通过ADC读取蓄电池电压		换算成实际值：(AD_R*5*44.1/5.1/1024)	*/
uint GetBatteryVoltage()   //此处感觉短时间取平均值无意义，且系统效率优先，省略加权平均
{
	uchar channel = 0b00101001;    //RB1/AN10,使能AD转换
	return getADValue(channel);
}
#endif
#if 1
/*光电池板电压		换算成实际值：	*/
uint GetSolarPanelVoltage()   //此处感觉短时间取平均值无意义，且系统效率优先，省略加权平均
{
	uchar channel = 0b00100001;    //RB2/AN8,使能AD转换
	return getADValue(channel);
}
#endif
#if 1
/*充电电流反馈电压*/
uint GetChargeCurrentVoltage()   //此处感觉短时间取平均值无意义，且系统效率优先，省略加权平均
{
	uchar channel = 0b00010001;    //RA5/AN4,使能AD转换
	return getADValue(channel);
}
#endif
#if 1
/*放电电流反馈电压*/
uint GetLoadCurrentVoltage()   //此处感觉短时间取平均值无意义，且系统效率优先，省略加权平均
{
	uchar channel = 0b00011001;    //RE1/AN6,使能AD转换
	return getADValue(channel);
}
#endif
void SelectMode()
{
    switch(SystemModeType)
    {
        case TIME_MODE:
        {
            if((DAYTIME == 0) && LightTime) //降到光强启动点，并且未到达设定时间
            {
                if(TimeModeHour >= 36000)   //到了一小时36000
                {
                    TimeModeHour = 0;
                    LightTime--;
                }
                if(LightTime != 0)
                {
                    LoadOpen = 1;//可以开启负载
                }
                else
                {
                    LoadOpen = 0;//时间到关闭负载，等待下一次光强
                }
            }
            else if(DAYTIME == 1)  //升到光强启动点，不会开启负载
            {
                LoadOpen = 0;//不可开负载
//                LightTime = ReadEE();//重置光强时间
				TimeModeHour = 0;
            }
            break;
        }
        case LIGHT_MODE://暂时没有做延时20s启动的设定
        {
            if(DAYTIME == 0)     //降到光强启动点
            {
                LoadOpen = 1;//可以开启负载
            }
            else if(DAYTIME == 1)
            {
                LoadOpen = 0;//不可开负载
            }
            break;
        }
        case OUTPUT_MODE:
        {
            LoadOpen = 1;   //负载可以常开
            break;
        }
        case MANUAL_MODE:   //通过按键控制，在手动模式下，每按一次按键改变一下负载状态
        {
            break;
        }
    }
}

/*根据反馈电压，判断负载情况，进行相应的处理，无需返回*/
void LoadCurrentDealWith()
{
    if((BatteryState != BATTERY_STATE_VOLTAGE_UNDER) && (BatteryState != BATTERY_STATE_VOLTAGE_OVER) && LoadOpen) 
	//电池正常状态&&模式为允许打开负载&&非设置模式
    {
        uint FbVoltage;
        FbVoltage = GetLoadCurrentVoltage();                       //读取反馈电压
        FbVoltage = 0;
        if((FbVoltage < LOAD_FB_VOLTAGE_NORMAL) && (LoadState == 1))            //负载正常
        {
            if(LoadFlag == LOAD_NOTCONNECT)
            {
                LoadState = 1;                        //修改负载正常标志位，可以开启负载
                OverLoadTime = 0;                     //负载没有过载，不需要定时
                TURN_ON_LoadCtrl;                     //接通负载
                LoadFlag = LOAD_CONNECT;
            }
        }
        if((FbVoltage >= LOAD_FB_VOLTAGE_NORMAL) || (LoadState == 0))           //负载不正常
        {
            LoadState = 0;
            if(( FbVoltage >= LOAD_FB_VOLTAGE_OVER) || (LoadShort == 1))        //负载短路，必须重启，否则LoadShort不能还原
            {
                TURN_OFF_LoadCtrl;                            //关闭负载
                LoadFlag = LOAD_NOTCONNECT;
                OverLoadTime = 0;                   //短路状态也不进行计时
                LoadShort = 1;

				SystemErrorState = 1;		//进入死循环
				ErrorType = 5;				//短路错误
            }
            else                                              //负载过载，但未短路
            {
                if(OverLoadTime == 0)                         //开启定时语句
                {
                    OverLoadTime = 1;
                }   
                if(OverLoadTime >= OVER_LOAD_TIME)  //5分钟到时
                {
                    OverLoadTime = 1;
                    if(LoadFlag == LOAD_CONNECT)
                    {
                        TURN_OFF_LoadCtrl;                    //关闭负载
                        LoadFlag = LOAD_NOTCONNECT;
                    }
                    else if(LoadFlag == LOAD_NOTCONNECT)
                    {
                        TURN_ON_LoadCtrl;                     //接通负载
                        LoadFlag = LOAD_CONNECT;
                        LoadState = 1;                        //修改负载正常标志位
                        OverLoadTime = 0;                     //关闭计时
                    }
                }
            }
        }
    }
    else
    {
        TURN_OFF_LoadCtrl;              //关闭负载
        LoadFlag = LOAD_NOTCONNECT;
		OverLoadTime = 0;
		LoadState = 1;
    }
}
void SolarPanelDealWith()
{
   uint SolarPanelVoltage; 
	if(PWMFlag == 0) //PWM翻转没有开启
	{
		if(DutyRatio > 249)//如果正在充电，直充
		{
			TURN_OFF_ChargeCtrl;
			Delay(200);//10mS
			SolarPanelVoltage = GetSolarPanelVoltage();
			gSolarPanelVoltage = SolarPanelVoltage;
			TURN_ON_ChargeCtrl;
		}
		if(DutyRatio == 0)//如果没有充电
		{
			TURN_OFF_ChargeCtrl;
			if(LPVCount == 0)//不在低压检测中
			{
				TURN_ON_PwmCtrl;
				Delay(1000);//10mS  50mS
				SolarPanelVoltage = GetSolarPanelVoltage();
				gSolarPanelVoltage = SolarPanelVoltage;
				TURN_OFF_PwmCtrl;
			}
			else//在低压检测中
			{
				TURN_ON_PwmCtrl;
				if(LPVCount > 99)//10S以上
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
	if(PWMFlag == 1)//PWM翻转已经开启
	{
		TURN_OFF_ChargeCtrl;
		PWMFlag = 0;						   //关闭PWM输出

		TURN_ON_PwmCtrl;
		Delay(200);//10mS  50us*200=
	    SolarPanelVoltage = GetSolarPanelVoltage();           //读取太阳能电池板的电压
		gSolarPanelVoltage = SolarPanelVoltage;
		TURN_OFF_PwmCtrl; 
		PWMFlag = 1;						   //开启PWM输出
		TURN_ON_ChargeCtrl;
	}
    if(gSolarPanelVoltage <= 24)
    {
		PVState = 1;                                      //能充电状态
    }
    else												  //弱光
    {
		PVState = 0;                                      //不能充电状态
		PVChargeFlag = 0;//因电池电压过高或者没有光不充电了，LED要指示电量了
    }

    if(((gBatteryVoltage*26) - (gSolarPanelVoltage*23)) > *(BatteryStandard + DAY_VOLTAGE))//  有光， 光电池大于7V   DAY_VOLTAGE(7V) 4840
    {
		LPVFlag = 0;	//低压检测标志
		LPVCount = 0;	//低压检测计时
		Led_C = 1;		//充电灯亮
		DAYTIME = 1;
    }
    if(((gBatteryVoltage*26) - (gSolarPanelVoltage*23)) < 3686)//正常测量光电池电压低于6V，但是不能判断是否是黑夜
    {
		if(DAYTIME == 1)//当前状态是白天才开启，如果已经是夜晚了，那就不用
		{
			if(LPVCount == 0)
			{
				LPVCount = 1;//开启延时计时，100mS计时一次
			}
		}
    }
	if(LPVFlag == 1)//低压检测完成
	{
		LPVFlag = 0;	//低压检测标志
		LPVCount = 0;	//低压检测计时
		if(((gBatteryVoltage*26) - (gSolarPanelVoltage*23)) < *(BatteryStandard + NIGHT_VOLTAGE))//  没有光， 光电池小于3V   NIGHT_VOLTAGE(3V) 1908
	    {
			Led_C = 0;//充电灯灭
			DAYTIME = 0;
	    }
	}	
}
void PWMCharge()
{
	uint ChangeBatteryVoltag;                   //为什么不能定义  uchar BatteryVoltag；？或者直接使用主函数里定义过的？？？？？
	ChangeBatteryVoltag = GetBatteryVoltage();
	if((EnhanceChargeFlag == 1) || (EqualizingChargeFlag == 1) || (FloatingChargeFlag == 1))
	{
		TURN_ON_ChargeCtrl;

		if(ChangeBatteryVoltag < ClampVoltage)
		{
			PVChargeFlag = 1;//正在充电中，LED要循环指示
			if(DutyRatio > 249)
			{
				TMR2IE = 0;                              //关闭timer2中断					//关PWM***********************	
				T2Flag = 0;
				PWMFlag = 0;						   //关闭PWM输出
	            TURN_ON_PwmCtrl;					   //直接打开输出
			}
			else 
			{
				if(T2Flag == 0)                        //如果尚未打开中断
				{
					TMR2IE = 1;                              //打开timer2中断				    	//开PWM************************
					T2Flag = 1;
					PWMFlag = 1;						   //开启PWM输出
				}
				DutyRatio = DutyRatio+10;
			}
		}
		if(ChangeBatteryVoltag > ClampVoltage)
		{
			if(DutyRatio < 9)
			{
				TMR2IE = 0;                              //关闭打开timer2中断			   		//关PWM************************	
				T2Flag = 0;
				PWMFlag = 0;						   //关闭PWM输出
				TURN_OFF_PwmCtrl;
				PVChargeFlag = 0;//因电池电压过高或者没有光不充电了，LED要指示电量了
			}
			else 
			{
				PVChargeFlag = 1;//正在充电中，LED要循环指示
				if(T2Flag == 0)							//如果尚未打开中断
				{
					TMR2IE = 1;                              //打开timer2中断				    	//开PWM************************
					T2Flag = 1;
					PWMFlag = 1;						   //开启PWM输出
				}
				DutyRatio = DutyRatio-10;
			}
		}
	}
	else
	{
		if(T2Flag == 1)                //如果已经打开中断
		{
			TMR2IE = 0;                  //关闭timer2中断	
			T2Flag = 0;
			PWMFlag = 0;			   //关闭PWM输出         
		}
		PwmCount = 0;
		DutyRatio = 0;		
		TURN_OFF_ChargeCtrl;           //关闭充电开关1
//		TURN_OFF_PwmCtrl;			   //关PWM充电	
		PVChargeFlag = 0;//因电池电压过高或者没有光不充电了，LED要指示电量了
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
			gBatteryVoltage = GetBatteryVoltage();//第一次上电要读状态
            BatteryState = BatteryStateSwitch(gBatteryVoltage);
			EnhanceChargeFlag = 0;//初始化变量  以下相同
			EnhanceCharge_Time = 0;
			EqualizingChargeFlag = 0;
			EqualizingCharge_Time = 0;
			FloatingChargeFlag = 0;
            BatteryLastState = BATTERY_STATE_POWER_UP;
            break;
        }
        case BATTERY_STATE_VOLTAGE_UNDER:   //10.8以下  0%
        {
        	// added 0926
        	if (lcd_state == 2) {
        		// 只有处于状态2，才能将四个方格的状态改变
        		lcd_move_char(11);
        		lcd_write_data(0xff);

        	}
            if(BatteryLastState != BATTERY_STATE_VOLTAGE_UNDER)//首次进入
            {
//                ClampVoltage = *(BatteryStandard + Enhance_Charge_VOLTAGE) - TemBase + TemVoltage;
				EnhanceChargeFlag = 0;//初始化变量  以下相同
				EnhanceCharge_Time = 0;
				EqualizingChargeFlag = 0;
                EqualizingCharge_Time = 0;
				FloatingChargeFlag = 0;
				BatteryLastState = BATTERY_STATE_VOLTAGE_UNDER; 
            }
            if(PVState == 1)//可以充电
            {
				ClampVoltage = *(BatteryStandard + Enhance_Charge_VOLTAGE) - TemBase + TemVoltage - ADBase + ADVoltage;
				if(EnhanceChargeFlag == 0)//没有进行提升充电
				{
					EnhanceChargeFlag = 1; 	//进行提升充电，开始计时
					EnhanceCharge_Time = 0;
				}
            }
            else
            {
	            EnhanceChargeFlag = 0;              //不可进行提升充电
	            EnhanceCharge_Time = 0;
	            gBatteryVoltage = GetBatteryVoltage();
	            if(gBatteryVoltage > *(BatteryStandard + RETURN_VOLTAGE_UNDER) - TemBase + TemVoltage - ADBase + ADVoltage)          //欠压返回电压
	            {
	                BatteryState = BatteryStateSwitch(gBatteryVoltage);
	            }
            }

            if(EnhanceCharge_Time >= CHARGE_TIME)                 //准备切换下一状态
			{
				EnhanceChargeFlag = 0;
				EnhanceCharge_Time = 0;//已经清零，如果还在本状态则继续重新计时
				Delay(100);                                       // 等待一段时间，等待电池电压回落
				gBatteryVoltage = GetBatteryVoltage();
				if(gBatteryVoltage > *(BatteryStandard + RETURN_VOLTAGE_UNDER) - TemBase + TemVoltage - ADBase + ADVoltage)         //欠压返回电压
				{
					BatteryState = BatteryStateSwitch(gBatteryVoltage);//已经清零，如果还在本状态则继续重新计时
				}
			}
            break;
        }
        case BATTERY_STATE_VOLTAGE_WARN:  //10.8——12  25%
        {

            if(BatteryLastState != BATTERY_STATE_VOLTAGE_WARN)     //第一次进入该状态
            {
//                ClampVoltage = *(BatteryStandard + Equalizing_Charge_VOLTAGE) - TemBase + TemVoltage;  //钳位电压 
				EnhanceChargeFlag = 0;//初始化变量  以下相同
				EnhanceCharge_Time = 0;
				EqualizingChargeFlag = 0;
                EqualizingCharge_Time = 0;
				FloatingChargeFlag = 0;
				BatteryLastState = BATTERY_STATE_VOLTAGE_WARN;
            }
            if(PVState == 1)                                     //能充电状态
            {
                ClampVoltage = *(BatteryStandard + Equalizing_Charge_VOLTAGE) - TemBase + TemVoltage - ADBase + ADVoltage;  //钳位电压 
				if(EqualizingChargeFlag == 0)//没有进行充电
				{
					EqualizingChargeFlag = 1; 	//进行均衡充电，开始计时
					EqualizingCharge_Time = 0;	//重新计时
				}
				gBatteryVoltage = GetBatteryVoltage();
				if(gBatteryVoltage <  *(BatteryStandard + BATTERY_VOLTAGE_UNDER) - TemBase + TemVoltage - ADBase + ADVoltage) //充电的过程中也可以向下掉
				{
					BatteryState = BATTERY_STATE_VOLTAGE_UNDER;
				}
            }
            else//没有光则继续判断电池电压状态
            {
                EqualizingChargeFlag = 0;
                EqualizingCharge_Time = 0;
                gBatteryVoltage = GetBatteryVoltage();
                BatteryState = BatteryStateSwitch(gBatteryVoltage);
            }

            if(EqualizingCharge_Time >= CHARGE_TIME)              //准备切换下一状态
            {
                EqualizingChargeFlag = 0;
                EqualizingCharge_Time = 0;//已经清零，如果还在本状态则继续重新计时
                Delay(100);
                gBatteryVoltage = GetBatteryVoltage();

                BatteryState = BatteryStateSwitch(gBatteryVoltage);//已经清零，如果还在本状态则继续重新计时
            }
            break; 
        }
        case BATTERY_STATE_VOLTAGE_NORMAL_50:		//12——12.8  50%
        {
            if(BatteryLastState != BATTERY_STATE_VOLTAGE_NORMAL_50)     //第一次进入该状态
            {
//                ClampVoltage = *(BatteryStandard + Equalizing_Charge_VOLTAGE) - TemBase + TemVoltage;  //钳位电压
				EnhanceChargeFlag = 0;//初始化变量  以下相同
				EnhanceCharge_Time = 0;
				EqualizingChargeFlag = 0;
                EqualizingCharge_Time = 0;
				FloatingChargeFlag = 0;
	            BatteryLastState = BATTERY_STATE_VOLTAGE_NORMAL_50;

            }
            if(PVState == 1)                                     //能充电状态
            {
                ClampVoltage = *(BatteryStandard + Equalizing_Charge_VOLTAGE) - TemBase + TemVoltage - ADBase + ADVoltage;  //钳位电压
				if(EqualizingChargeFlag == 0)//没有进行充电
				{
					EqualizingChargeFlag = 1; 	//进行均衡充电，开始计时
					EqualizingCharge_Time = 0;	//重新计时
				}
				gBatteryVoltage = GetBatteryVoltage();
				if(gBatteryVoltage <  *(BatteryStandard + RETURN_VOLTAGE_UNDER) - TemBase + TemVoltage - ADBase + ADVoltage) //充电的过程中也可以向下掉
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

            if(EqualizingCharge_Time >= CHARGE_TIME)              //准备切换下一状态
            {
                EqualizingChargeFlag = 0;
                EqualizingCharge_Time = 0;//已经清零，如果还在本状态则继续重新计时
                Delay(100);
                gBatteryVoltage = GetBatteryVoltage();

                BatteryState = BatteryStateSwitch(gBatteryVoltage);//已经清零，如果还在本状态则继续重新计时
            }
            break;
        }
        case BATTERY_STATE_VOLTAGE_NORMAL_75:		//12.8——13.2  75%
        {
            if(BatteryLastState != BATTERY_STATE_VOLTAGE_NORMAL_75)     //第一次进入该状态
            {
//                ClampVoltage = *(BatteryStandard + Equalizing_Charge_VOLTAGE) - TemBase + TemVoltage;  //钳位电压
				EnhanceChargeFlag = 0;//初始化变量  以下相同
				EnhanceCharge_Time = 0;
				EqualizingChargeFlag = 0;
                EqualizingCharge_Time = 0;
				FloatingChargeFlag = 0;
	            BatteryLastState = BATTERY_STATE_VOLTAGE_NORMAL_75;

            }
            if(PVState == 1)                                     //能充电状态
            {
                ClampVoltage = *(BatteryStandard + Equalizing_Charge_VOLTAGE) - TemBase + TemVoltage - ADBase + ADVoltage;  //钳位电压
				if(EqualizingChargeFlag == 0)//没有进行充电
				{
					EqualizingChargeFlag = 1; 	//进行均衡充电，开始计时
					EqualizingCharge_Time = 0;	//重新计时
				}
				gBatteryVoltage = GetBatteryVoltage();
				if(gBatteryVoltage <  *(BatteryStandard + BATTERY_VOLTAGE_NORMAL) - TemBase + TemVoltage - ADBase + ADVoltage) //充电的过程中也可以向下掉
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

            if(EqualizingCharge_Time >= CHARGE_TIME)              //准备切换下一状态
            {
                EqualizingChargeFlag = 0;
                EqualizingCharge_Time = 0;//已经清零，如果还在本状态则继续重新计时
                Delay(100);
                gBatteryVoltage = GetBatteryVoltage();

                BatteryState = BatteryStateSwitch(gBatteryVoltage);//已经清零，如果还在本状态则继续重新计时
            }
            break;
        }
        case BATTERY_STATE_VOLTAGE_FULL:		//13.2以上  100%
        {
            if(BatteryLastState != BATTERY_STATE_VOLTAGE_FULL)     //第一次进入该状态
            {
//                ClampVoltage = *(BatteryStandard + Floating_Charge_VOLTAGE) - TemBase + TemVoltage;            //钳位电压
				EnhanceChargeFlag = 0;//初始化变量  以下相同
				EnhanceCharge_Time = 0;
				EqualizingChargeFlag = 0;
                EqualizingCharge_Time = 0;
				FloatingChargeFlag = 0;
				BatteryLastState = BATTERY_STATE_VOLTAGE_FULL;
            }
            if(PVState == 1)                                       //能充电状态
            {
                ClampVoltage = *(BatteryStandard + Floating_Charge_VOLTAGE) - TemBase + TemVoltage - ADBase + ADVoltage;            //钳位电压
                FloatingChargeFlag = 1;                            //进行浮充充电
            }
            else
            {
                FloatingChargeFlag = 0;
            }

            gBatteryVoltage = GetBatteryVoltage();                 //读取蓄电池电压
            BatteryState = BatteryStateSwitch(gBatteryVoltage);      //根据当前的蓄电池电压进行切换
            break;
        }
        case BATTERY_STATE_VOLTAGE_OVER:
        {
            if(BatteryLastState != BATTERY_STATE_VOLTAGE_OVER)       //第一次进入该状态
            {
                ClampVoltage = 0;           //钳位电压 
                TMR2IE = 0;                                          //关闭timer2中断 //关PWM充电
                T2Flag = 0;
                PWMFlag = 0;                                        //关闭PWM输出
				TURN_OFF_ChargeCtrl;
				TURN_OFF_PwmCtrl;

				EnhanceChargeFlag = 0;//初始化变量  以下相同
				EnhanceCharge_Time = 0;
				EqualizingChargeFlag = 0;
                EqualizingCharge_Time = 0;
				FloatingChargeFlag = 0;
				BatteryLastState = BATTERY_STATE_VOLTAGE_OVER;
            }
            gBatteryVoltage = GetBatteryVoltage();                 //读取蓄电池电压
            if(gBatteryVoltage < *(BatteryStandard + RETURN_VOLTAGE_FULL) - TemBase + TemVoltage - ADBase + ADVoltage)           //过压返回电压
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
	//新添加部分，用于显示自检错误
	//自检错误只要在这里添加对应的错误类型就行了，比如过压，就把ErrorType置1，负载短路，就把ErrorType置2之类的
	//新添加部分，用于显示当前系统电压或者是否出现自检错误
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

//充电电路损坏	错误7	开机时&不充电时
//放电电路损坏	错误8	开机时&不放电时
	SDSolarPanelVoltage = GetSolarPanelVoltage();
	SDBatteryVoltage = GetBatteryVoltage();
	if((SDSolarPanelVoltage*47) < ((SDBatteryVoltage*48) - 2400) 
	|| (SDSolarPanelVoltage*47) > ((SDBatteryVoltage*48) + 2400))// 50*48 充电电路损坏 错误7
	{
		SystemErrorState = 1;
		ErrorType = 7;
		System12V = 0;
		System24V = 0;
	}
	gFbVoltage = GetLoadCurrentVoltage();
	if(gFbVoltage > 13)//放电电路损坏 错误8	//放大精度5mV放大10倍  实际测试空载10一下  12.5是0.5A
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
uchar GetBoradTemperature()   //8位就行，不用精确
{
	FVRCON = 0b00000000;	//5分配给AD
	ADCON0 = 0b00001101;    //RA3/AN3,使能AD转换
    ADCON1 = 0b00010000;;    //数据结果左对齐，8分频，内部参考电压

	uchar x[6];
	uint i = 0 ;

	for (i = 0 ; i < 6; i++)
	{
		FVRCON = 0b00000000;	//5分配给AD
	    ADCON0 = 0b00001101;    //RA3/AN3,使能AD转换
	    ADCON1 = 0b00010000;;    //数据结果左对齐，8分频，内部参考电压
		Delay(20);//1mS 	/*软件延迟函数，x约为50us*/
		ADGO = 1;//开始转换
		while(ADGO);
		x[i] = ADRESH;	//读取ADC采样结果
		ADCON0 = 0b00001100;    //不使能AD转换
	} 

	uint AD_Result = 0;
	uchar max , min;
	max = min = x[0]; 				//找出最大值和最小值
	for(i=0;i<6;i++) 
	{
		if(max<x[i])
			max=x[i]; 
		if(min>x[i])
			min=x[i];
	}
	for(i=0;i<6;i++) 			//求和，去除最大值和最小值
	{
		AD_Result += x[i];
	}

	AD_Result = AD_Result - max - min;
	AD_Result = AD_Result >> 2;
    return AD_Result;
}
uchar GetOutTemperature()   //8位就行，不用精确
{
	FVRCON = 0b00000000;	//5分配给AD
	ADCON0 = 0b00000101;    //RA3/AN3,使能AD转换
    ADCON1 = 0b00010000;;    //数据结果左对齐，8分频，内部参考电压

	uchar x[6];
	uint i = 0 ;

	for (i = 0 ; i < 6; i++)
	{
		FVRCON = 0b00000000;	//5分配给AD
	    ADCON0 = 0b00000101;    //RA3/AN3,使能AD转换
	    ADCON1 = 0b00010000;;    //数据结果左对齐，8分频，内部参考电压
		Delay(20);//1mS 	/*软件延迟函数，x约为50us*/
		ADGO = 1;//开始转换
		while(ADGO);
		x[i] = ADRESH;	//读取ADC采样结果
		ADCON0 = 0b00000100;    //不使能AD转换
	} 

	uint AD_Result = 0;
	uchar max , min;
	max = min = x[0]; 				//找出最大值和最小值
	for(i=0;i<6;i++) 
	{
		if(max<x[i])
			max=x[i]; 
		if(min>x[i])
			min=x[i];
	}
	for(i=0;i<6;i++) 			//求和，去除最大值和最小值
	{
		AD_Result += x[i];
	}

	AD_Result = AD_Result - max - min;
	AD_Result = AD_Result >> 2;
    return AD_Result;
}

void LedDisplay()//LED显示&过欠压数码管显示&过流闪烁显示
{
	if(LoadFlag == LOAD_CONNECT)
	{
		TURN_ON_Led_F;
	}
	if(LoadFlag == LOAD_NOTCONNECT)
	{
		TURN_OFF_Led_F;
	}

	if(PVChargeFlag == 1)//正在充电中
	{
		TURN_ON_Led_C;
	}
	if(PVChargeFlag == 0)//因电池电压过高或者没有光不充电了，LED要指示电量了
	{
		TURN_OFF_Led_C;	
	}
}

/**
此函数用来将二进制电压数据存入到eeprom中，为了便于计算提升性能，电压都以int型
参数：
data: 显示的电压，为了提升性能和便于显示，采用整数存储，比如18.2v，data就是182；5.0v，data就是50，
充电时间、白天识别电压、夜晚识别电压为特殊（原始值）
type：需要保存的电压类型：
				//////////////////////////////
				type:1   en_charge(提升充电)
				type:2   eq_charge(均衡充电)
				type:3   flo_charge(浮充充电)
				type:4   time_charge(充电时间？)
				type:5   under_vol（欠压电压）
				type:6   re_under(欠压返回)
				type:7   over_vol(过压电压)
				type:8   re_over(过压回复)
				type:9   day_vol(白天识别电压)
				type:10  night_vol(夜晚识别电压)
				/////////////////////////////////
**/
void saveToEEPROM(uint data, uchar type) {
	uchar l_byte = 0;
	uchar h_byte = 0;
	uint save_data = 0;
	switch(type) {
		case 1:
		case 2:
		case 3:
		case 5:
		case 6:
		case 7:
		case 8:
			save_data = (int) (data * 2.3684f);
			l_byte = save_data && 0x00ff;
			h_byte = (save_data && 0xff00) >> 8;
			// 分别写入相邻的两个地址
			WriteEE(l_byte, type * 2);
			WriteEE(h_byte, type * 2 + 1);
			break;
		case 9:
		case 10:
		case 4:
			l_byte = save_data;
			WriteEE(l_byte, type * 2);
			break;
		default:
			break;
	}
	return;
}

/**
同上个函数，此函数的作用是将EEPROM中的值读出来，返回一个int型，具体值的意义见每个类型
				//////////////////////////////
				type:1   en_charge(提升充电)
				type:2   eq_charge(均衡充电)
				type:3   flo_charge(浮充充电)
				type:4   time_charge(充电时间？)
				type:5   under_vol（欠压电压）
				type:6   re_under(欠压返回)
				type:7   over_vol(过压电压)
				type:8   re_over(过压回复)
				type:9   day_vol(白天识别电压)
				type:10  night_vol(夜晚识别电压)
				/////////////////////////////////
**/
uint readFromEEPROM(uchar type)
{
	uchar l_byte = 0;
	uchar h_byte = 0;
	uint read_data = 0;
	switch(type) {
		case 1:
		case 2:
		case 3:
		case 5:
		case 6:
		case 7:
		case 8:
			l_byte = ReadEE(type * 2);
			h_byte = ReadEE(type * 2 + 1);
			read_data = l_byte + ( ((uint)h_byte) << 8);
			read_data = (uint) (read_data / 2.3684f);
			break;
		case 9:
		case 10:
		case 4:
			read_data = ReadEE(type * 2);
		default:
			break;
	}
	return read_data;
}