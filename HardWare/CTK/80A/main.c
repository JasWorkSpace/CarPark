
/********************************************

*********************************************/
#include <pic.h>
#include "mypic.h"
#include "kernel.h"
#include "LM016.h"
#include "74HC595.h"
#include "stdio.h"
///////////////////////////////////////////////////////////////////////////
/////// ��������߼�
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
	setKeyEventDealed();//�������,��ֹ�ظ�����
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
/// ��ʾ����߼�
#define LCD_AUTO_EXTINGUWISH_TIME  50  // �Զ�Ϣ��ʱ��, 50*100ms = 5s

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
	if(LCD_PAGE != page || force){//force ǿ��ˢ��
		LCD_PAGE = (page%7);//����ѭ��
		uchar data1[LCD_LINENUMBER_MAXCHAR] = {0}; 
		uchar data2[LCD_LINENUMBER_MAXCHAR] = {0};
		switch(LCD_PAGE){
			case 0:{//������Ҫ����Ĳ����޸�
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
		if(LCD_LastState != LCD_STATE_IDLE){//��һ�ν���IDLE״̬
			API_LCD_INIT();
			API_LCD_CLEAR();
		}
		pendingState = LCD_STATE_WORK;//��ʾ5s
	}break;
	case LCD_STATE_WAIT:{
		if(LCD_LastState != LCD_STATE_WAIT){
			API_LCD_CLEAR();
		}
		setLCDBackground(CLOSE);
		if(KeyEvent_KeyCode != KEYCODE_UNKNOW){//�а�������
			setKeyEventDealed();
			pendingState = LCD_STATE_WORK;//��ʾ5s
		}
	}break;
	case LCD_STATE_WORK:{
		if(LCD_LastState != LCD_STATE_WORK){
			if(LCD_LastState == LCD_STATE_SET){
				LoadParamFromEPPROM();//ȷ��������ȷ
			}
			setLCDBackground(OPEN);
			showNormalPage(TRUE, 0);////tranform it to first.
		}else{
			if(hasLongPressKey() && KeyEvent_PendKeyCode == KEYCODE_SET){
				setKeyEventDispatchered();
				pendingState = LCD_STATE_SET;//��������ģʽ
			}else if(KeyEvent_KeyCode == KEYCODE_SET){
				setKeyEventDealed();
				showNormalPage(FALSE, ++LCD_PAGE);//ѭ����ҳ��ʾ
				resetLCDStateTime();
			}else{
				if((OS_Time - LCD_StateTime) > LCD_AUTO_EXTINGUWISH_TIME){
					pendingState = LCD_STATE_WAIT;//�ر���ʾ
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
				pendingState = LCD_STATE_WORK;//��������ģʽ
			}else if(KeyEvent_KeyCode == KEYCODE_SET){
				setKeyEventDealed();
				showSettingsPage(FALSE, ++LCD_Settings_Index);
				resetLCDStateTime();
			}else if(KeyEvent_KeyCode == KEYCODE_UP){
				ParamConfig[HW_BATTERY_TYPE][Param_battery_type] = HW_BATTERY_TYPE_SELF;//���Ϊ����ģʽ
				setKeyEventDealed();
				ParamConfig[HW_BATTERY_TYPE][LCD_Settings_Index] ++;
				showSettingsPage(TRUE, LCD_Settings_Index);
			}else if(KeyEvent_KeyCode == KEYCODE_DOWN){
				ParamConfig[HW_BATTERY_TYPE][Param_battery_type] = HW_BATTERY_TYPE_SELF;//���Ϊ����ģʽ
				setKeyEventDealed();
				ParamConfig[HW_BATTERY_TYPE][LCD_Settings_Index] --;
				showSettingsPage(TRUE, LCD_Settings_Index);
			}else{
				if((OS_Time - LCD_StateTime) > LCD_AUTO_EXTINGUWISH_TIME){
					pendingState = LCD_STATE_WORK;//��������������ʾ
				}
			}
		}
	}break;
	}
	tranformLCDState(pendingState);//�л�״̬
	return TRUE;
}

//////////////////////////////////////////////////////////////////////////////
///���ڳ��ȴ����߼�

//////////////////////////////////////////////////////////////////////////////
/// PWM �����߼�
void setPWM(uchar open){
	if(open == OPEN){
		TURN_ON_PwmCtrl;
	}else{
		TURN_OFF_PwmCtrl;
	}
}

void openOrClosePWM(uchar open){
	PWM_CONTROL_FLAG = open;
	TMR2IE = open; //�ر�timer2�ж�	
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
/// OS ״̬�߼�����
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
		TURN_OFF_Lcd_L;//��Һ��������
		PWM_CONTROL_FLAG = CLOSE;//�ر�PWM
		pendingState   =  OS_STATE_LOAD;
	}break;
	case OS_STATE_LOAD:{//ϵͳ���״̬
		if(OS_LastState != OS_STATE_LOAD){
			showWelcome();
		}else{
			//�������ӿ�����״̬���
			KaiJi();//����������ȷ���ϵ��ѹ��ʹ�����飬��·�Լ��
			if((OS_Time-OS_StateTime) > 30){//��ʾ3S. 30*100 = 3S
				pendingState = OS_STATE_WORK;
			}
		}
	}break;
	case OS_STATE_WORK:{//ϵͳ��������״̬
		handlerLCD();//������ʾ
		////////////////////////////////////////////////////////////////////////////////////////////////////
		//////  ������Ҫ���Ӻ��޸� PWM, ��ؼ��Ȳ���.                                                  ///
		//////    PWM�밴��"PWM�����߼�"������Ӧ������                                                   ///
		//////    ���ڵ�ѹ��AD, ���������һ��ѭ����ȡһ��ֵ. �ڳ�ŵ���߼������н�ֹ��ȡ.����Ч�ʺܸ�  ///
		////////////////////////////////////////////////////////////////////////////////////////////////////

		//SelectMode();
		//SystemModeType = OUTPUT_MODE;
		//LoadCurrentDealWith();
		//SwitchBatteryState();
		//LedDisplay();
		//if(PVCount > 50)                      //5s��⴦��һ�� 50
		//{
		//	PVCount = 0;
		//	SolarPanelDealWith();			   //����̫���ܵ�ذ�ĵ�ѹ��������Ӧ�Ĵ�������õ�ǰ̫���ܵ�ذ��״̬ 
		//}
		//if(PWMChargeFlag == 1)                 //����PWM��纯��100ms ���һ��
		//{
		//	PWMChargeFlag = 0;                 //��ʱ��ֵ��ԭ
		//	PWMCharge();
		//} 
		////////////////////////////////////////////////////////////////////////////////////////////////
		handlerKeyEvent();//û���߼����İ���.���ｫ�������ĵ�
	}break;
	}
	tranform_OS_State(pendingState);
	return TRUE;
}
//////////////////////////////////////////////////////////////////
void main(){
	system_state_init();
	LoadParamFromEPPROM(); // �ָ�ϵͳ״̬
	// Һ������ʼ��
 	API_LCD_INIT();
	API_LCD_CLEAR();
	TURN_OFF_Lcd_L;//��Һ��������
	while(1)
	{
		asm("CLRWDT");//���û����õ�ʱ��Ӧ��Ҳ���뿴�Ź�
		if(handlerOSStateMachine() != TRUE){
			tranform_OS_State(OS_STATE_IDLE);//�����ܷ���,����ϵͳ
		}
		//handlerLCD();
	}
}

void interrupt ISR_Timer()
{
	if(IOCBF3 && lcd_state != 0 && lcd_state != 1)//RB3�ⲿ�жϣ�UP
	{	
		// ֻ��Ӧ�±����ж�
		if (RB3 == 0) {
			handlerKeyEventInput(KEYCODE_UP);
		}
		IOCBF3 = 0;//����ⲿ�жϱ�־λ������������ֹ�ڼ������жϳ���
	}
	if(IOCBF5 && lcd_state != 0 && lcd_state != 1)//RB5�ⲿ�ж�, DOWN
	{
		if (RB5 == 0) {
			handlerKeyEventInput(KEYCODE_DOWN);
		}		
		IOCBF5 = 0;//����ⲿ�жϱ�־λ������������ֹ�ڼ������жϳ���
	}

	if(IOCBF4)//RB4�ⲿ�жϣ�SET
	{	
		// SET����ʱ
		if (RB4 == 0) {
			handlerKeyEventInput(KEYCODE_SET);
		}
		// SEŢ��ʱ
		IOCBF4 = 0;//����ⲿ�жϱ�־λ������������ֹ�ڼ������жϳ���
	}

	if(TMR1IF)//���ڽϳ�ʱ��Ķ�ʱ������ʹ��16λ��ʱ��
	{
	    OS_Time ++;//ϵͳ��׼ʱ��
		TMR1IF = 0;								//��ʱ��1���жϴ���
		TMR1H = (65536 - (100000 / 4)) >> 8;
    	TMR1L = (65536 - (100000 / 4)) & 0xFF; //100ms�ж�һ��
	}
	if(TMR2IF)									//��ʱ��2���жϴ��� ��纯��
    {
        TMR2IF = 0;								//���timer2�жϱ�־λ
		if(PWMFlag == 1)
		{
	        PwmCount++;							//255�Զ���װ

	        if(PwmCount >= DutyRatio)
	            TURN_OFF_PwmCtrl;
	        else
	            TURN_ON_PwmCtrl;
		}
		handlerPWMInterrupt();
	}
	if(TMR4IF)									//��ʱ��4���жϴ��� 
    {
        TMR4IF = 0;								//���timer4�жϱ�־λ
		RE2 = ~RE2;
	}
	if(TMR6IF)									//��ʱ��6���жϴ���
    {
        TMR6IF = 0;								//���timer6�жϱ�־λ
	}
}