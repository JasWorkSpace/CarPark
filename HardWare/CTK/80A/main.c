
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
uint hasLongPressKey(){
	if(KeyEvent_PendKeyCode != KEYCODE_UNKNOW
		&& (OS_Time-KeyEvent_KeyTime) > 30){//3s
		return TRUE;
	}
	return FALSE;
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
#define LCD_AUTO_EXTINGUWISH_TIME  50  // 自动息屏时间, 50*100ms = 5s

uchar LCD_PAGE  = 0;
uchar LCD_Settings_Index = 0;
void getBatteryType(uchar *data){
	switch(ParamConfig[HW_BATTERY_TYPE][0]){
	case HW_BATTERY_TYPE_BAT:{
		sprintf(data, "Lead-acid Bat");
	}break;
	case HW_BATTERY_TYPE_GEL:{
		sprintf(data, "Gel battery");
	}break;
	case HW_BATTERY_TYPE_OPEN:{
		sprintf(data, "Open cell");
	}break;
	case HW_BATTERY_TYPE_SELF:{
		sprintf(data, "Self settings");
	}break;
	}
}
void getParamLibString(uchar index, uchar *data){
	switch(index){
	case Param_battery_type:{
		getBatteryType(data);
	}break;
	case Param_en_charge:{
		sprintf(data,"En_Charge:%03.01fV", ParamConfig[HW_BATTERY_TYPE][Param_en_charge]);
	}break;
	case Param_eq_charge:{
		sprintf(data,"Eq_Charge:%03.01fV", ParamConfig[HW_BATTERY_TYPE][Param_eq_charge]);
	}break;
	case Param_flo_charge:{
		sprintf(data,"Fl_Charge:%03.01fV", ParamConfig[HW_BATTERY_TYPE][Param_flo_charge]);
	}break;
	case Param_time_change:{
		sprintf(data,"TIME:     %03.01fV", ParamConfig[HW_BATTERY_TYPE][Param_time_change]);
	}break;
	case Param_under_vol:{
		sprintf(data,"UNDER VOL:%03.01fV", ParamConfig[HW_BATTERY_TYPE][Param_under_vol]);
	}break;
	case Param_re_under:{
		sprintf(data,"RE UNDER :%03.01fV", ParamConfig[HW_BATTERY_TYPE][Param_re_under]);
	}break;
	case Param_over_vol:{
		sprintf(data,"VOER  VOL:%03.01fV", ParamConfig[HW_BATTERY_TYPE][Param_over_vol]);
	}break;
	case Param_re_over:{
		sprintf(data,"RE   OVER:%03.01fV", ParamConfig[HW_BATTERY_TYPE][Param_re_over]);
	}break;
	case Param_day_vol:{
		sprintf(data,"DAY   VOL:%03.01fV", ParamConfig[HW_BATTERY_TYPE][Param_day_vol]);
	}break;
	case Param_night_vol:{
		sprintf(data,"NIGHT VOL:%03.01fV", ParamConfig[HW_BATTERY_TYPE][Param_night_vol]);
	}break;
	}
}
void showNormalPage(int force, int page){
	if(LCD_PAGE != page || force){//force 强制刷新
		LCD_PAGE = (page%7);//控制循环
		uchar data1[LCD_LINENUMBER_MAXCHAR] = {0}; 
		uchar data2[LCD_LINENUMBER_MAXCHAR] = {0};
		switch(LCD_PAGE){
			case 0:{//这里需要具体的参数修改
				sprintf(data1,"Bv13.3V Soc0000");
				sprintf(data2,"Ic15.3A Io18.8A");
			}break;
			case 1:{
				sprintf(data1,"Battery Type");
				getParamLibString(Param_battery_type, data2);
			}break;
			case 2:{
				getParamLibString(Param_en_charge, data1);
				getParamLibString(Param_eq_charge, data2);
			}break;
			case 3:{
				getParamLibString(Param_flo_charge,  data1);
				getParamLibString(Param_time_change, data2);
			}break;
			case 4:{
				getParamLibString(Param_under_vol, data1);
				getParamLibString(Param_re_under,  data2);
			}break;
			case 5:{
				getParamLibString(Param_over_vol, data1);
				getParamLibString(Param_re_over,  data2);
			}break;
			case 6:{
				getParamLibString(Param_day_vol,    data1);
				getParamLibString(Param_night_vol,  data2);
			}break;
		}
		API_LCD_PRINT_LINE(LCD_LINENUMBER_1, data1, 0);
		API_LCD_PRINT_LINE(LCD_LINENUMBER_2, data2, 0);
	}
}
void showSettingsPage(int force, int page){
	if(page != LCD_Settings_Index || force){
		LCD_Settings_Index = (page%Param_MAX);
		uchar data1[LCD_LINENUMBER_MAXCHAR] = {0}; 
		getParamLibString(LCD_Settings_Index,  data1);
		API_LCD_PRINT_LINE(LCD_LINENUMBER_1, data1, 0);
	}
}
void showWelcome(){
	uchar data1[LCD_LINENUMBER_MAXCHAR] = {"Welcome"}; 
	uchar data2[LCD_LINENUMBER_MAXCHAR] = {"Waiting..."};
	API_LCD_PRINT_LINE(LCD_LINENUMBER_1, data1, 0);
	API_LCD_PRINT_LINE(LCD_LINENUMBER_2, data2, 0);
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
void resetLCDStateTime(){
	LCD_StateTime    = OS_Time;
}
void tranformLCDState(int state){
	if(state != LCD_CurrentState){
		LCD_LastState    = LCD_CurrentState;
		LCD_CurrentState = state;
		LCD_StateTime    = OS_Time;
	}
}
uchar handlerLCD(){
	uchar pendingState = LCD_CurrentState;
	switch(pendingState){
	case LCD_STATE_IDLE:{
		if(LCD_LastState != LCD_STATE_IDLE){//第一次进入IDLE状态
			API_LCD_INIT();
			API_LCD_CLEAR();
		}
		pendingState = LCD_STATE_WORK;//显示5s
	}break;
	case LCD_STATE_WAIT:{
		if(LCD_LastState != LCD_STATE_WAIT){
			API_LCD_CLEAR();
		}
		setLCDBackground(CLOSE);
		if(KeyEvent_KeyCode != KEYCODE_UNKNOW){//有按键按下
			setKeyEventDealed();
			pendingState = LCD_STATE_WORK;//显示5s
		}
	}break;
	case LCD_STATE_WORK:{
		if(LCD_LastState != LCD_STATE_WORK){
			if(LCD_LastState == LCD_STATE_SET){
				LoadParamFromEPPROM();//确保数据正确
			}
			setLCDBackground(OPEN);
			showNormalPage(TRUE, 0);////tranform it to first.
		}else{
			if(hasLongPressKey() && KeyEvent_PendKeyCode == KEYCODE_SET){
				setKeyEventDispatchered();
				pendingState = LCD_STATE_SET;//进入设置模式
			}else if(KeyEvent_KeyCode == KEYCODE_SET){
				setKeyEventDealed();
				showNormalPage(FALSE, ++LCD_PAGE);//循环翻页显示
				resetLCDStateTime();
			}else{
				if((OS_Time - LCD_StateTime) > LCD_AUTO_EXTINGUWISH_TIME){
					pendingState = LCD_STATE_WAIT;//关闭显示
				}
			}
		}
	}break;
	case LCD_STATE_SET:{
		if(LCD_LastState != LCD_STATE_SET){
			showSettingsPage(TRUE, 0);//show first
			uchar data1[LCD_LINENUMBER_MAXCHAR] = {0};
			sprintf(data1,"SETTING UP DOWN");
			API_LCD_PRINT_LINE(LCD_LINENUMBER_2, data1, 0);
		}else{
			if(hasLongPressKey() && KeyEvent_PendKeyCode == KEYCODE_SET){
				setKeyEventDispatchered();
				SaveParamToEPPROM();
				pendingState = LCD_STATE_WORK;//进入设置模式
			}else if(KeyEvent_KeyCode == KEYCODE_SET){
				setKeyEventDealed();
				showSettingsPage(FALSE, ++LCD_Settings_Index);
				resetLCDStateTime();
			}else if(KeyEvent_KeyCode == KEYCODE_UP){
				ParamConfig[HW_BATTERY_TYPE][Param_battery_type] = HW_BATTERY_TYPE_SELF;//电池为设置模式
				setKeyEventDealed();
				ParamConfig[HW_BATTERY_TYPE][LCD_Settings_Index] ++;
				showSettingsPage(TRUE, LCD_Settings_Index);
			}else if(KeyEvent_KeyCode == KEYCODE_DOWN){
				ParamConfig[HW_BATTERY_TYPE][Param_battery_type] = HW_BATTERY_TYPE_SELF;//电池为设置模式
				setKeyEventDealed();
				ParamConfig[HW_BATTERY_TYPE][LCD_Settings_Index] --;
				showSettingsPage(TRUE, LCD_Settings_Index);
			}else{
				if((OS_Time - LCD_StateTime) > LCD_AUTO_EXTINGUWISH_TIME){
					pendingState = LCD_STATE_WORK;//进入正常工作显示
				}
			}
		}
	}break;
	}
	tranformLCDState(pendingState);//切换状态
	return TRUE;
}

//////////////////////////////////////////////////////////////////////////////
///关于充电等处理逻辑

//////////////////////////////////////////////////////////////////////////////
/// PWM 处理逻辑
void setPWM(uchar open){
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

////////////////////////////////////////////////////////////
/// OS 状态逻辑处理
#define OS_STATE_IDLE 0
#define OS_STATE_LOAD 1
#define OS_STATE_WORK 2

uchar   OS_CurrentState = OS_STATE_IDLE;
uchar   OS_LastState    = OS_STATE_IDLE;
uint32  OS_StateTime    = 0;

void tranform_OS_State(int state){
	if(state != OS_CurrentState){
		OS_LastState    = OS_CurrentState;
		OS_CurrentState = state;
		OS_StateTime    = OS_Time;
	}
}
uchar handlerOSStateMachine(){
	uchar pendingState = OS_CurrentState;
	switch(OS_CurrentState){
	case OS_STATE_IDLE:{
		TURN_OFF_Lcd_L;//关液晶屏背光
		PWM_CONTROL_FLAG = CLOSE;//关闭PWM
		pendingState   =  OS_STATE_LOAD;
	}break;
	case OS_STATE_LOAD:{//系统检测状态
		if(OS_LastState != OS_STATE_LOAD){
			showWelcome();
		}else{
			//这里增加开机的状态监测
			KaiJi();//开机启动，确认上电电压，使用数组，电路自检等
			if((OS_Time-OS_StateTime) > 30){//显示3S. 30*100 = 3S
				pendingState = OS_STATE_WORK;
			}
		}
	}break;
	case OS_STATE_WORK:{//系统正常工作状态
		handlerLCD();//处理显示
		////////////////////////////////////////////////////////////////////////////////////////////////////
		//////  这里需要增加和修改 PWM, 电池检测等操作.                                                  ///
		//////    PWM请按照"PWM处理逻辑"进行相应的增加                                                   ///
		//////    关于电压的AD, 在这里可以一次循环读取一个值. 在充放电的逻辑处理中禁止读取.这样效率很高  ///
		////////////////////////////////////////////////////////////////////////////////////////////////////

		//SelectMode();
		//SystemModeType = OUTPUT_MODE;
		//LoadCurrentDealWith();
		//SwitchBatteryState();
		//LedDisplay();
		//if(PVCount > 50)                      //5s检测处理一次 50
		//{
		//	PVCount = 0;
		//	SolarPanelDealWith();			   //根据太阳能电池板的电压，进行相应的处理，并获得当前太阳能电池板的状态 
		//}
		//if(PWMChargeFlag == 1)                 //调用PWM充电函数100ms 监测一次
		//{
		//	PWMChargeFlag = 0;                 //定时器值还原
		//	PWMCharge();
		//} 
		////////////////////////////////////////////////////////////////////////////////////////////////
		handlerKeyEvent();//没有逻辑消耗按键.这里将按键消耗掉
	}break;
	}
	tranform_OS_State(pendingState);
	return TRUE;
}
//////////////////////////////////////////////////////////////////
void main(){
	system_state_init();
	LoadParamFromEPPROM(); // 恢复系统状态
	// 液晶屏初始化
 	API_LCD_INIT();
	API_LCD_CLEAR();
	TURN_OFF_Lcd_L;//关液晶屏背光
	while(1)
	{
		asm("CLRWDT");//在用户设置的时候应该也加入看门狗
		if(handlerOSStateMachine() != TRUE){
			tranform_OS_State(OS_STATE_IDLE);//代码跑飞了,重启系统
		}
		//handlerLCD();
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