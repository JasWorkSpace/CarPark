
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
		if(LCD_LastState != LCD_STATE_IDLE){//��һ�ν���IDLE״̬
			API_LCD_INIT();
			API_LCD_CLEAR();
		}
		tranformLCDState(LCD_STATE_WORK);//��ʾ5s
	}break;
	case LCD_STATE_WAIT:{
		if(LCD_LastState != LCD_STATE_WAIT){
			API_LCD_CLEAR();
		}
		setLCDBackground(CLOSE);
		if(KeyEvent_KeyCode != KEYCODE_UNKNOW){//�а�������
			setKeyEventDealed();
			tranformLCDState(LCD_STATE_WORK);//��ʾ5s
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
///���ڳ��ȴ����߼�

//////////////////////////////////////////////////////////////////////////////
/// PWM �����߼�
void setPWM(char open){
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

bit isSettingMode = 0; // �Ƿ�������ģʽ
bit isSaveSettings = 0;
bit rb4_flag = 0; // SET�Ƿ񱻳�����flag
bit is_second_setting; //����ģʽ�иı��һ��ֵ���ǵڶ���ֵ
bit needInitSetting; //��Ҫ��ʼ������
uchar batteryType = 0; // ��ʶ��һ�ֵ������
uchar const *charPointer; //�ַ�ָ��

////////////////////////////////////////////////////////////
/// OS ״̬�߼�����
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
		TURN_OFF_Lcd_L;//��Һ��������
		PWM_CONTROL_FLAG = CLOSE;//�ر�PWM
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
	LoadParamFromEPPROM(); // �ָ�ϵͳ״̬
	// Һ������ʼ��
 	API_LCD_INIT();
	API_LCD_CLEAR();
	TURN_OFF_Lcd_L;//��Һ��������
	KaiJi();//����������ȷ���ϵ��ѹ��ʹ�����飬��·�Լ��
	while(1)
	{
		asm("CLRWDT");		//���û����õ�ʱ��Ӧ��Ҳ���뿴�Ź�
		
		SelectMode();
		SystemModeType = OUTPUT_MODE;
		LoadCurrentDealWith();
		SwitchBatteryState();
		LedDisplay();
		if(PVCount > 50)                      //5s��⴦��һ�� 50
		{
			PVCount = 0;
			SolarPanelDealWith();			   //����̫���ܵ�ذ�ĵ�ѹ��������Ӧ�Ĵ�������õ�ǰ̫���ܵ�ذ��״̬ 
		}
		if(PWMChargeFlag == 1)                 //����PWM��纯��100ms ���һ��
		{
			PWMChargeFlag = 0;                 //��ʱ��ֵ��ԭ
			PWMCharge();
		} 


		
		if (lcd_extinguwish_timer == AUTO_EXTINGUWISH_TIME) {
			// ����Ҫһֱ����״̬
			lcd_extinguwish_timer++;
			// �����Ļ
			lcd_write_command(0x08);
		}

		if (isSaveSettings) {
			isSaveSettings = 0;
			isNeedChange = 1;
			lcd_state = 2;
			isSettingMode = 0;
			// todo: ���浱ǰϵͳ״̬��EEPROM��
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