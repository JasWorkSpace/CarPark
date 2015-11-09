
/********************************************

*********************************************/
#include <pic.h>
#include "mypic.h"
#include "kernel.h"
#include "LM016.h"
#include "74HC595.h"
#include "stdio.h"
///////////////////////////////////////////////////////////////////////////
/////// 按键相关逻辑
/// HW KeyInput --> KeyEvent_PendKeyCode -->(if long down after setKeyEventDispatchered)
/// --> KeyEvent_KeyCode
#define KEYCODE_UNKNOW  1
#define KEYCODE_SET     2
#define KEYCODE_UP      3
#define KEYCODE_DOWN    4

#define KEYDOWN    LOW

uchar   KeyEvent_PendKeyCode = KEYCODE_UNKNOW;
uint32  KeyEvent_KeyTime     = 0;
uchar   KeyEvent_KeyCode     = KEYCODE_UNKNOW;

void setKeyEventDealed(){
	KeyEvent_KeyCode = KEYCODE_UNKNOW;
}
void setKeyEventDispatchered(){
	KeyEvent_PendKeyCode = KEYCODE_UNKNOW;
	KeyEvent_KeyTime     = OS_Time;
}
void handlerKeyEventInput(int keyCode){
	// KEYCODE_SET > KEYCODE_UP > KEYCODE_DOWN
	if(keyCode == KEYCODE_SET || keyCode == KEYCODE_UP || keyCode == KEYCODE_DOWN){
		if(KeyEvent_PendKeyCode != keyCode){
			KeyEvent_PendKeyCode = keyCode;
			KeyEvent_KeyTime     = OS_Time;
		}
	}
}
void handlerKeyEvent(){
	setKeyEventDealed();//清除按键,防止重复处理
	if(KeyEvent_PendKeyCode == KEYCODE_SET && IO_KEY_SET != KEYCODE_SET){
		KeyEvent_KeyCode = KEYCODE_SET;
		setKeyEventDispatchered();
	}else if(KeyEvent_PendKeyCode == KEYCODE_UP && IO_KEY_UP != KEYCODE_UP){
		KeyEvent_KeyCode = KEYCODE_UP;
		setKeyEventDispatchered();
	}else if(KeyEvent_PendKeyCode == KEYCODE_DOWN && IO_KEY_DOWN != KEYCODE_DOWN){
		KeyEvent_KeyCode = KEYCODE_DOWN; 
		setKeyEventDispatchered();
	}	
}
//////////////////////////////////////////////////////////////////////////////
/// 显示相关逻辑
uchar LCD_PAGE  = 0;
uint  LCD_POINT = 0;
////////
void updateLCDShow(uchar page,uchar point_line){
	if(page == LCD_PAGE)return;
	LCD_PAGE = page;
	uchar data[15] = {0};
	switch(LCD_PAGE){
	case 0:{//Bv13.2V soc0000
		sprintf(data, "Bv13.2V Soc0000");
	}break;
	case 1:{
		//
	}break;
	}
}


#define LCD_STATE_IDLE 0
#define LCD_STATE_WAIT 1
#define LCD_STATE_WORK 2
#define LCD_STATE_SET  3

uchar   LCD_CurrentState = LCD_STATE_IDLE;
uchar   LCD_LastState    = LCD_STATE_IDLE;
uint32  LCD_StateTime    = 0;
void setLCDBackground(uchar open){
	if(open == OPEN){
		TURN_ON_Lcd_L;
	}else {
		TURN_OFF_Lcd_L;
	}
}
void tranformLCDState(int state){
	if(state != LCD_CurrentState){
		LCD_LastState    = LCD_CurrentState;
		LCD_CurrentState = state;
		LCD_StateTime    = OS_Time;
	}
}
uint handlerLCD(){
	switch(LCD_CurrentState){
	case LCD_STATE_IDLE:{
		if(LCD_LastState != LCD_STATE_IDLE){//第一次进入IDLE状态
			API_LCD_INIT();
			API_LCD_CLEAR();
		}
		tranformLCDState(LCD_STATE_WORK);//显示5s
	}break;
	case LCD_STATE_WAIT:{
		if(LCD_LastState != LCD_STATE_WAIT){
			API_LCD_CLEAR();
		}
		setLCDBackground(CLOSE);
		if(KeyEvent_KeyCode != KEYCODE_UNKNOW){//有按键按下
			setKeyEventDealed();
			tranformLCDState(LCD_STATE_WORK);//显示5s
		}
	}break;
	case LCD_STATE_WORK:{
		if(LCD_LastState != LCD_STATE_WORK){
			setLCDBackground(OPEN);
			
		}else{

		}
	}break;
	}
}

//////////////////////////////////////////////////////////////////////////////
///关于充电等处理逻辑

//////////////////////////////////////////////////////////////////////////////
/// PWM 处理逻辑
void setPWM(char open){
	if(open == OPEN){
		TURN_ON_PwmCtrl;
	}else{
		TURN_OFF_PwmCtrl;
	}
}
void openOrClosePWM(uchar open){
	PWM_CONTROL_FLAG = open;
	TMR2IE = open; //关闭timer2中断	
	setPWM(open);
}
void handlerPWMInterrupt(){
	if(PWM_CONTROL_FLAG == CLOSE){//
		openOrClosePWM(CLOSE);
	}else{
		if(PWM_CONTROL_TIMEOUT != CLOSE){
			
		}
	}
}
////////////////////////////////////////////

bit isSettingMode = 0; // 是否处于设置模式
bit isSaveSettings = 0;
bit rb4_flag = 0; // SET是否被长按的flag
bit is_second_setting; //设置模式中改变第一个值还是第二个值
bit needInitSetting; //需要初始化设置
uchar batteryType = 0; // 标识哪一种电池类型
uchar const *charPointer; //字符指针

////////////////////////////////////////////////////////////
/// OS 状态逻辑处理
#define OS_STATE_IDLE 0
#define OS_STATE_LOAD 1
#define OS_STATE_WORK 2

uchar   OS_CurrentState = OS_STATE_IDLE;
uint32  OS_StateTime    = 0;

void tranform_OS_State(int state){
	if(state != OS_CurrentState){
		OS_CurrentState = state;
		OS_StateTime    = OS_Time;
	}
}
uchar handlerOSStateMachine(){
	switch(OS_CurrentState){
	case OS_STATE_IDLE:{
		TURN_OFF_Lcd_L;//关液晶屏背光
		PWM_CONTROL_FLAG = CLOSE;//关闭PWM
	}break;
	case OS_STATE_LOAD:{

	}break;
	case OS_STATE_WORK:{

	}break;
	}
}
//////////////////////////////////////////////////////////////////
void main(){
	system_state_init();
	LoadParamFromEPPROM(); // 恢复系统状态
	// 液晶屏初始化
 	API_LCD_INIT();
	API_LCD_CLEAR();
	TURN_OFF_Lcd_L;//关液晶屏背光
	KaiJi();//开机启动，确认上电电压，使用数组，电路自检等
	while(1)
	{
		asm("CLRWDT");		//在用户设置的时候应该也加入看门狗
		
		SelectMode();
		SystemModeType = OUTPUT_MODE;
		LoadCurrentDealWith();
		SwitchBatteryState();
		LedDisplay();
		if(PVCount > 50)                      //5s检测处理一次 50
		{
			PVCount = 0;
			SolarPanelDealWith();			   //根据太阳能电池板的电压，进行相应的处理，并获得当前太阳能电池板的状态 
		}
		if(PWMChargeFlag == 1)                 //调用PWM充电函数100ms 监测一次
		{
			PWMChargeFlag = 0;                 //定时器值还原
			PWMCharge();
		} 


		
		if (lcd_extinguwish_timer == AUTO_EXTINGUWISH_TIME) {
			// 不需要一直更新状态
			lcd_extinguwish_timer++;
			// 清除屏幕
			lcd_write_command(0x08);
		}

		if (isSaveSettings) {
			isSaveSettings = 0;
			isNeedChange = 1;
			lcd_state = 2;
			isSettingMode = 0;
			// todo: 保存当前系统状态到EEPROM中
		}

		if (isSettingMode && needInitSetting) {
			needInitSetting = 0;
			isNeedChange = 1;
			lcd_state = 3;
			is_second_setting = 0;
		}
		
	}
}

void interrupt ISR_Timer()
{
	if(IOCBF3 && lcd_state != 0 && lcd_state != 1)//RB3外部中断，UP
	{	
		// 只响应下边沿中断
		if (RB3 == 0) {
			handlerKeyEventInput(KEYCODE_UP);
		}
		IOCBF3 = 0;//清除外部中断标志位，最后清除，防止期间又有中断出现
	}
	if(IOCBF5 && lcd_state != 0 && lcd_state != 1)//RB5外部中断, DOWN
	{
		if (RB5 == 0) {
			handlerKeyEventInput(KEYCODE_DOWN);
		}		
		IOCBF5 = 0;//清除外部中断标志位，最后清除，防止期间又有中断出现
	}

	if(IOCBF4)//RB4外部中断，SET
	{	
		// SET按下时
		if (RB4 == 0) {
			handlerKeyEventInput(KEYCODE_SET);
		}
		// SET抬起时
		IOCBF4 = 0;//清除外部中断标志位，最后清除，防止期间又有中断出现
	}

	if(TMR1IF)//用于较长时间的定时，必须使用16位定时器
	{
	    OS_Time ++;//系统基准时间
		TMR1IF = 0;								//定时器1的中断处理
		TMR1H = (65536 - (100000 / 4)) >> 8;
    	TMR1L = (65536 - (100000 / 4)) & 0xFF; //100ms中断一次
	}
	if(TMR2IF)									//定时器2的中断处理 充电函数
    {
        TMR2IF = 0;								//清除timer2中断标志位
		if(PWMFlag == 1)
		{
	        PwmCount++;							//255自动重装

	        if(PwmCount >= DutyRatio)
	            TURN_OFF_PwmCtrl;
	        else
	            TURN_ON_PwmCtrl;
		}
		handlerPWMInterrupt();
	}
	if(TMR4IF)									//定时器4的中断处理 
    {
        TMR4IF = 0;								//清除timer4中断标志位
		RE2 = ~RE2;
	}
	if(TMR6IF)									//定时器6的中断处理
    {
        TMR6IF = 0;								//清除timer6中断标志位
	}
}