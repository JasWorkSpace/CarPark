opt subtitle "HI-TECH Software Omniscient Code Generator (PRO mode) build 6738"

opt pagewidth 120

	opt pm

	processor	16F1934
clrc	macro
	bcf	3,0
	endm
clrz	macro
	bcf	3,2
	endm
setc	macro
	bsf	3,0
	endm
setz	macro
	bsf	3,2
	endm
skipc	macro
	btfss	3,0
	endm
skipz	macro
	btfss	3,2
	endm
skipnc	macro
	btfsc	3,0
	endm
skipnz	macro
	btfsc	3,2
	endm
indf	equ	0
indf0	equ	0
indf1	equ	1
pc	equ	2
pcl	equ	2
status	equ	3
fsr0l	equ	4
fsr0h	equ	5
fsr1l	equ	6
fsr1h	equ	7
bsr	equ	8
wreg	equ	9
intcon	equ	11
c	equ	1
z	equ	0
pclath	equ	10
# 4 "C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.h"
	psect config,class=CONFIG,delta=2 ;#
# 4 "C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.h"
	dw 0x061C ;#
# 5 "C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.h"
	psect config,class=CONFIG,delta=2 ;#
# 5 "C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.h"
	dw 0x0233 ;#
	FNCALL	_main,_system_state_init
	FNCALL	_main,_load_system_state
	FNCALL	_main,_lcd_init
	FNCALL	_main,_lcd_write_command
	FNCALL	_main,_lcd_print_line1
	FNCALL	_main,_lcd_print_line2
	FNCALL	_main,_KaiJi
	FNCALL	_main,_SelectMode
	FNCALL	_main,_LoadCurrentDealWith
	FNCALL	_main,_SwitchBatteryState
	FNCALL	_main,_LedDisplay
	FNCALL	_main,_SolarPanelDealWith
	FNCALL	_main,_PWMCharge
	FNCALL	_main,_lcd_move_char
	FNCALL	_KaiJi,_GetBatteryVoltage
	FNCALL	_KaiJi,_GetSolarPanelVoltage
	FNCALL	_KaiJi,___wmul
	FNCALL	_KaiJi,_GetLoadCurrentVoltage
	FNCALL	_SwitchBatteryState,_GetBatteryVoltage
	FNCALL	_SwitchBatteryState,_BatteryStateSwitch
	FNCALL	_SwitchBatteryState,_lcd_move_char
	FNCALL	_SwitchBatteryState,_lcd_write_data
	FNCALL	_SwitchBatteryState,_Delay
	FNCALL	_PWMCharge,_GetBatteryVoltage
	FNCALL	_SolarPanelDealWith,_Delay
	FNCALL	_SolarPanelDealWith,_GetSolarPanelVoltage
	FNCALL	_SolarPanelDealWith,___wmul
	FNCALL	_LoadCurrentDealWith,_GetLoadCurrentVoltage
	FNCALL	_GetLoadCurrentVoltage,_getADValue
	FNCALL	_GetSolarPanelVoltage,_getADValue
	FNCALL	_GetBatteryVoltage,_getADValue
	FNCALL	_load_system_state,_readFromEEPROM
	FNCALL	_lcd_print_line2,_lcd_write_command
	FNCALL	_lcd_print_line2,_lcd_write_data
	FNCALL	_lcd_print_line1,_lcd_write_command
	FNCALL	_lcd_print_line1,_delay
	FNCALL	_lcd_print_line1,_lcd_write_data
	FNCALL	_lcd_init,_lcd_write_command
	FNCALL	_lcd_init,_delay
	FNCALL	_lcd_move_char,_lcd_write_command
	FNCALL	_getADValue,_getADValueOneTime
	FNCALL	_readFromEEPROM,_ReadEE
	FNCALL	_readFromEEPROM,___lwtoft
	FNCALL	_readFromEEPROM,___ftdiv
	FNCALL	_readFromEEPROM,___fttol
	FNCALL	___lwtoft,___ftpack
	FNCALL	___ftdiv,___ftpack
	FNCALL	_lcd_write_command,_delay
	FNCALL	_lcd_write_data,_delay
	FNCALL	_getADValueOneTime,_Delay
	FNCALL	_system_state_init,_Delay
	FNROOT	_main
	FNCALL	intlevel1,_ISR_Timer
	global	intlevel1
	FNROOT	intlevel1
	global	_SystemModeType
	global	_batteryStatePointer
	global	_Battery_12V
	global	_Battery_24V
	global	_checking
	global	_systemType1
	global	_welcome
psect	idataBANK0,class=CODE,space=0,delta=2
global __pidataBANK0
__pidataBANK0:
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.h"
	line	96

;initializer for _SystemModeType
	retlw	03h
psect	idataBANK1,class=CODE,space=0,delta=2
global __pidataBANK1
__pidataBANK1:
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\LM016.h"
	line	37

;initializer for _batteryStatePointer
	retlw	_state2_2|8000h&0ffh
	retlw	_state2_3|8000h&0ffh
	retlw	_state2_4|8000h&0ffh
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.h"
	line	92

;initializer for _Battery_12V
	retlw	0
	retlw	01h

	retlw	02Fh
	retlw	01h

	retlw	01Ch
	retlw	01h

	retlw	039h
	retlw	01h

	retlw	087h
	retlw	01h

	retlw	063h
	retlw	01h

	retlw	05Fh
	retlw	01h

	retlw	055h
	retlw	01h

	retlw	047h
	retlw	01h

	retlw	07Dh
	retlw	0Fh

	retlw	084h
	retlw	03h

	line	93

;initializer for _Battery_24V
	retlw	0
	retlw	02h

	retlw	05Eh
	retlw	02h

	retlw	038h
	retlw	02h

	retlw	072h
	retlw	02h

	retlw	0Eh
	retlw	03h

	retlw	0C6h
	retlw	02h

	retlw	0BEh
	retlw	02h

	retlw	0AAh
	retlw	02h

	retlw	08Eh
	retlw	02h

	retlw	027h
	retlw	020h

	retlw	05h
	retlw	0Dh

	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\LM016.h"
	line	9

;initializer for _checking
	retlw	043h
	retlw	048h
	retlw	045h
	retlw	043h
	retlw	04Bh
	retlw	049h
	retlw	04Eh
	retlw	047h
	retlw	02Eh
	retlw	02Eh
	retlw	02Eh
	retlw	0
	line	10

;initializer for _systemType1
	retlw	031h
	retlw	032h
	retlw	056h
	retlw	020h
	retlw	053h
	retlw	059h
	retlw	053h
	retlw	054h
	retlw	045h
	retlw	04Dh
	retlw	0
	line	8

;initializer for _welcome
	retlw	057h
	retlw	045h
	retlw	04Ch
	retlw	043h
	retlw	04Fh
	retlw	04Dh
	retlw	045h
	retlw	0
	global	_state3_1
psect	strings,class=STRING,delta=2
global __pstrings
__pstrings:
	global    __stringtab
__stringtab:
	retlw	0
psect	strings
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\LM016.h"
	line	26
_state3_1:
	retlw	045h
	retlw	06Eh
	retlw	05Fh
	retlw	043h
	retlw	068h
	retlw	061h
	retlw	072h
	retlw	067h
	retlw	065h
	retlw	03Ah
	retlw	020h
	retlw	078h
	retlw	078h
	retlw	02Eh
	retlw	078h
	retlw	056h
	retlw	0
	global	_state3_2
psect	strings
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\LM016.h"
	line	27
_state3_2:
	retlw	045h
	retlw	071h
	retlw	05Fh
	retlw	043h
	retlw	068h
	retlw	061h
	retlw	072h
	retlw	067h
	retlw	065h
	retlw	03Ah
	retlw	020h
	retlw	078h
	retlw	078h
	retlw	02Eh
	retlw	078h
	retlw	056h
	retlw	0
	global	_state4_1
psect	strings
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\LM016.h"
	line	28
_state4_1:
	retlw	046h
	retlw	06Ch
	retlw	06Fh
	retlw	05Fh
	retlw	063h
	retlw	068h
	retlw	061h
	retlw	072h
	retlw	067h
	retlw	065h
	retlw	03Ah
	retlw	078h
	retlw	078h
	retlw	02Eh
	retlw	078h
	retlw	056h
	retlw	0
	global	_state5_1
psect	strings
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\LM016.h"
	line	30
_state5_1:
	retlw	055h
	retlw	04Eh
	retlw	044h
	retlw	045h
	retlw	052h
	retlw	020h
	retlw	056h
	retlw	04Fh
	retlw	04Ch
	retlw	03Ah
	retlw	020h
	retlw	078h
	retlw	078h
	retlw	02Eh
	retlw	078h
	retlw	056h
	retlw	0
	global	_state7_2
psect	strings
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\LM016.h"
	line	35
_state7_2:
	retlw	04Eh
	retlw	049h
	retlw	047h
	retlw	048h
	retlw	054h
	retlw	020h
	retlw	056h
	retlw	04Fh
	retlw	04Ch
	retlw	03Ah
	retlw	020h
	retlw	078h
	retlw	078h
	retlw	02Eh
	retlw	078h
	retlw	076h
	retlw	0
	global	_state1_1
psect	strings
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\LM016.h"
	line	14
_state1_1:
	retlw	042h
	retlw	076h
	retlw	078h
	retlw	078h
	retlw	02Eh
	retlw	078h
	retlw	076h
	retlw	020h
	retlw	053h
	retlw	06Fh
	retlw	063h
	retlw	0FFh
	retlw	0FFh
	retlw	0FFh
	retlw	0FFh
	retlw	0
	global	_state1_2
psect	strings
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\LM016.h"
	line	15
_state1_2:
	retlw	06Ch
	retlw	063h
	retlw	078h
	retlw	078h
	retlw	02Eh
	retlw	078h
	retlw	041h
	retlw	020h
	retlw	06Ch
	retlw	06Fh
	retlw	078h
	retlw	078h
	retlw	02Eh
	retlw	078h
	retlw	041h
	retlw	0
	global	_state5_2
psect	strings
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\LM016.h"
	line	31
_state5_2:
	retlw	052h
	retlw	045h
	retlw	020h
	retlw	055h
	retlw	04Eh
	retlw	044h
	retlw	045h
	retlw	052h
	retlw	03Ah
	retlw	020h
	retlw	078h
	retlw	078h
	retlw	02Eh
	retlw	078h
	retlw	056h
	retlw	0
	global	_state6_1
psect	strings
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\LM016.h"
	line	32
_state6_1:
	retlw	04Fh
	retlw	056h
	retlw	045h
	retlw	052h
	retlw	020h
	retlw	056h
	retlw	04Fh
	retlw	04Ch
	retlw	03Ah
	retlw	020h
	retlw	058h
	retlw	058h
	retlw	02Eh
	retlw	058h
	retlw	056h
	retlw	0
	global	_state6_2
psect	strings
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\LM016.h"
	line	33
_state6_2:
	retlw	052h
	retlw	045h
	retlw	020h
	retlw	04Fh
	retlw	056h
	retlw	045h
	retlw	052h
	retlw	03Ah
	retlw	020h
	retlw	078h
	retlw	078h
	retlw	02Eh
	retlw	078h
	retlw	056h
	retlw	0
	global	_state7_1
psect	strings
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\LM016.h"
	line	34
_state7_1:
	retlw	044h
	retlw	041h
	retlw	059h
	retlw	020h
	retlw	056h
	retlw	04Fh
	retlw	04Ch
	retlw	03Ah
	retlw	020h
	retlw	078h
	retlw	078h
	retlw	02Eh
	retlw	078h
	retlw	056h
	retlw	0
	global	_state2_1
psect	strings
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\LM016.h"
	line	22
_state2_1:
	retlw	042h
	retlw	061h
	retlw	074h
	retlw	074h
	retlw	065h
	retlw	072h
	retlw	079h
	retlw	020h
	retlw	074h
	retlw	079h
	retlw	070h
	retlw	065h
	retlw	0
	global	_state2_4
psect	strings
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\LM016.h"
	line	25
_state2_4:
	retlw	053h
	retlw	065h
	retlw	06Ch
	retlw	066h
	retlw	020h
	retlw	073h
	retlw	065h
	retlw	074h
	retlw	074h
	retlw	069h
	retlw	06Eh
	retlw	067h
	retlw	0
	global	_state2_2
psect	strings
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\LM016.h"
	line	23
_state2_2:
	retlw	047h
	retlw	065h
	retlw	06Ch
	retlw	020h
	retlw	062h
	retlw	061h
	retlw	074h
	retlw	074h
	retlw	065h
	retlw	072h
	retlw	079h
	retlw	0
	global	_state2_3
psect	strings
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\LM016.h"
	line	24
_state2_3:
	retlw	04Fh
	retlw	070h
	retlw	065h
	retlw	06Eh
	retlw	020h
	retlw	063h
	retlw	065h
	retlw	06Ch
	retlw	06Ch
	retlw	0
	global	_state3_1
	global	_state3_2
	global	_state4_1
	global	_state5_1
	global	_state7_2
	global	_state1_1
	global	_state1_2
	global	_state5_2
	global	_state6_1
	global	_state6_2
	global	_state7_1
	global	_state2_1
	global	_state2_4
	global	_state2_2
	global	_state2_3
	global	_ADBase
	global	_ADVoltage
	global	_ClampVoltage
	global	_EnhanceCharge_Time
	global	_EqualizingCharge_Time
	global	_OverLoadTime
	global	_SDBatteryVoltage
	global	_SDSolarPanelVoltage
	global	_SystemErrorCount
	global	_TimeModeHour
	global	_charPointer
	global	_gBatteryVoltage
	global	_gFbVoltage
	global	_gSolarPanelVoltage
	global	_BatteryLastState
	global	_BatteryStandard
	global	_BatteryState
	global	_DutyRatio
	global	_LPVCount
	global	_LightTime
	global	_PVCount
	global	_PwmCount
	global	_batteryType
	global	_enter_settings_timer
	global	_lcd_extinguwish_timer
	global	_lcd_state
	global	_lcd_timer
	global	_setting_no_save_timer
	global	_setting_save_timer
	global	_DAYTIME
	global	_EnhanceChargeFlag
	global	_EqualizingChargeFlag
	global	_FloatingChargeFlag
	global	_LPVFlag
	global	_LoadOpen
	global	_LoadShort
	global	_PVChargeFlag
	global	_PVState
	global	_PWMChargeFlag
	global	_PWMFlag
	global	_T2Flag
	global	_isNeedChange
	global	_isSaveSettings
	global	_isSettingMode
	global	_is_second_setting
	global	_needInitSetting
	global	_rb4_flag
	global	_LoadFlag
psect	bitnvBANK0,class=BANK0,bit,space=1
global __pbitnvBANK0
__pbitnvBANK0:
_LoadFlag:
       ds      1

	global	_LoadState
_LoadState:
       ds      1

	global	_PORTA
_PORTA	set	12
	global	_PORTB
_PORTB	set	13
	global	_PORTD
_PORTD	set	15
	global	_PORTE
_PORTE	set	16
	global	_PR2
_PR2	set	27
	global	_T1CON
_T1CON	set	24
	global	_T2CON
_T2CON	set	28
	global	_TMR1H
_TMR1H	set	23
	global	_TMR1L
_TMR1L	set	22
	global	_GIE
_GIE	set	95
	global	_PEIE
_PEIE	set	94
	global	_RB3
_RB3	set	107
	global	_RB4
_RB4	set	108
	global	_RB5
_RB5	set	109
	global	_RB6
_RB6	set	110
	global	_RC1
_RC1	set	113
	global	_RC2
_RC2	set	114
	global	_RC3
_RC3	set	115
	global	_RE2
_RE2	set	130
	global	_TMR0IE
_TMR0IE	set	93
	global	_TMR1IF
_TMR1IF	set	136
	global	_TMR2IF
_TMR2IF	set	137
	global	_TMR4IF
_TMR4IF	set	153
	global	_TMR6IF
_TMR6IF	set	155
	global	_ADCON0
_ADCON0	set	157
	global	_ADCON1
_ADCON1	set	158
	global	_ADRESH
_ADRESH	set	156
	global	_ADRESL
_ADRESL	set	155
	global	_OSCCON
_OSCCON	set	153
	global	_TRISA
_TRISA	set	140
	global	_TRISB
_TRISB	set	141
	global	_TRISC
_TRISC	set	142
	global	_TRISD
_TRISD	set	143
	global	_TRISE
_TRISE	set	144
	global	_WDTCON
_WDTCON	set	151
	global	_ADGO
_ADGO	set	1257
	global	_TMR1IE
_TMR1IE	set	1160
	global	_TMR2IE
_TMR2IE	set	1161
	global	_TMR4IE
_TMR4IE	set	1177
	global	_FVRCON
_FVRCON	set	279
	global	_LATB
_LATB	set	269
	global	_LATC
_LATC	set	270
	global	_ANSELA
_ANSELA	set	396
	global	_ANSELB
_ANSELB	set	397
	global	_ANSELE
_ANSELE	set	400
	global	_EEADRL
_EEADRL	set	401
	global	_EEDATL
_EEDATL	set	403
	global	_CFGS
_CFGS	set	3246
	global	_EEPGD
_EEPGD	set	3247
	global	_RD
_RD	set	3240
	global	_IOCBN
_IOCBN	set	917
	global	_IOCBP
_IOCBP	set	916
	global	_IOCBF3
_IOCBF3	set	7347
	global	_IOCBF4
_IOCBF4	set	7348
	global	_IOCBF5
_IOCBF5	set	7349
	global	_PR4
_PR4	set	1046
	global	_T4CON
_T4CON	set	1047
	file	"battery.as"
	line	#
psect cinit,class=CODE,delta=2
global start_initialization
start_initialization:

psect	bitbssBANK0,class=BANK0,bit,space=1
global __pbitbssBANK0
__pbitbssBANK0:
_DAYTIME:
       ds      1

_EnhanceChargeFlag:
       ds      1

_EqualizingChargeFlag:
       ds      1

_FloatingChargeFlag:
       ds      1

_LPVFlag:
       ds      1

_LoadOpen:
       ds      1

_LoadShort:
       ds      1

_PVChargeFlag:
       ds      1

_PVState:
       ds      1

_PWMChargeFlag:
       ds      1

_PWMFlag:
       ds      1

_T2Flag:
       ds      1

_isNeedChange:
       ds      1

_isSaveSettings:
       ds      1

_isSettingMode:
       ds      1

_is_second_setting:
       ds      1

_needInitSetting:
       ds      1

_rb4_flag:
       ds      1

psect	bssBANK0,class=BANK0,space=1
global __pbssBANK0
__pbssBANK0:
_ADBase:
       ds      2

_ADVoltage:
       ds      2

_ClampVoltage:
       ds      2

_EnhanceCharge_Time:
       ds      2

_EqualizingCharge_Time:
       ds      2

_OverLoadTime:
       ds      2

_SDBatteryVoltage:
       ds      2

_SDSolarPanelVoltage:
       ds      2

_SystemErrorCount:
       ds      2

_TimeModeHour:
       ds      2

_charPointer:
       ds      2

_gBatteryVoltage:
       ds      2

_gFbVoltage:
       ds      2

_gSolarPanelVoltage:
       ds      2

_BatteryLastState:
       ds      1

_BatteryStandard:
       ds      1

_BatteryState:
       ds      1

_DutyRatio:
       ds      1

_LPVCount:
       ds      1

_LightTime:
       ds      1

_PVCount:
       ds      1

_PwmCount:
       ds      1

_batteryType:
       ds      1

_enter_settings_timer:
       ds      1

_lcd_extinguwish_timer:
       ds      1

_lcd_state:
       ds      1

_lcd_timer:
       ds      1

_setting_no_save_timer:
       ds      1

_setting_save_timer:
       ds      1

psect	dataBANK0,class=BANK0,space=1
global __pdataBANK0
__pdataBANK0:
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.h"
	line	96
_SystemModeType:
       ds      1

psect	dataBANK1,class=BANK1,space=1
global __pdataBANK1
__pdataBANK1:
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\LM016.h"
	line	37
_batteryStatePointer:
       ds      3

psect	dataBANK1
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.h"
	line	92
_Battery_12V:
       ds      22

psect	dataBANK1
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.h"
	line	93
_Battery_24V:
       ds      22

psect	dataBANK1
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\LM016.h"
	line	9
_checking:
       ds      12

psect	dataBANK1
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\LM016.h"
	line	10
_systemType1:
       ds      11

psect	dataBANK1
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\LM016.h"
	line	8
_welcome:
       ds      8

psect clrtext,class=CODE,delta=2
global clear_ram
;	Called with FSR0 containing the base address, and
;	WREG with the size to clear
clear_ram:
	clrwdt			;clear the watchdog before getting into this loop
clrloop:
	clrf	indf0		;clear RAM location pointed to by FSR
	addfsr	0,1
	decfsz wreg		;Have we reached the end of clearing yet?
	goto clrloop	;have we reached the end yet?
	retlw	0		;all done for this memory range, return
; Clear objects allocated to BITBANK0
psect cinit,class=CODE,delta=2
	global __pbitbssBANK0
	clrf	((__pbitbssBANK0/8)+0)&07Fh
	clrf	((__pbitbssBANK0/8)+1)&07Fh
	clrf	((__pbitbssBANK0/8)+2)&07Fh
; Clear objects allocated to BANK0
psect cinit,class=CODE,delta=2
	global __pbssBANK0
	movlw	low(__pbssBANK0)
	movwf	fsr0l
	movlw	high(__pbssBANK0)
	movwf	fsr0h
	movlw	02Bh
	fcall	clear_ram
psect inittext,class=CODE,delta=2
global init_ram,btemp
init_ram:
	movwf btemp,f
initloop:
	moviw fsr0++
	movwi fsr1++
	decfsz btemp
	goto initloop
	retlw 0
; Initialize objects allocated to BANK0
	global __pidataBANK0,__pdataBANK0
psect cinit,class=CODE,delta=2
	fcall	__pidataBANK0+0		;fetch initializer
	movwf	__pdataBANK0+0&07fh		
; Initialize objects allocated to BANK1
	global __pidataBANK1,__pdataBANK1
psect cinit,class=CODE,delta=2
	movlw low(__pidataBANK1)
	movwf fsr0l
	movlw high(__pidataBANK1)|80h
	movwf fsr0h
	movlw low(__pdataBANK1)
	movwf fsr1l
	movlw high(__pdataBANK1)
	movwf fsr1h
	movlw 04Eh
	fcall init_ram
psect cinit,class=CODE,delta=2
global end_of_initialization

;End of C runtime variable initialization code

end_of_initialization:
movlb 0
ljmp _main	;jump to C main() function
psect	cstackCOMMON,class=COMMON,space=1
global __pcstackCOMMON
__pcstackCOMMON:
	global	?_lcd_move_char
?_lcd_move_char:	; 0 bytes @ 0x0
	global	?_lcd_write_data
?_lcd_write_data:	; 0 bytes @ 0x0
	global	?_lcd_write_command
?_lcd_write_command:	; 0 bytes @ 0x0
	global	?_delay
?_delay:	; 0 bytes @ 0x0
	global	?_Delay
?_Delay:	; 0 bytes @ 0x0
	global	?_system_state_init
?_system_state_init:	; 0 bytes @ 0x0
	global	??_ReadEE
??_ReadEE:	; 0 bytes @ 0x0
	global	?_load_system_state
?_load_system_state:	; 0 bytes @ 0x0
	global	?_SelectMode
?_SelectMode:	; 0 bytes @ 0x0
	global	??_SelectMode
??_SelectMode:	; 0 bytes @ 0x0
	global	?_LoadCurrentDealWith
?_LoadCurrentDealWith:	; 0 bytes @ 0x0
	global	?_SolarPanelDealWith
?_SolarPanelDealWith:	; 0 bytes @ 0x0
	global	?_PWMCharge
?_PWMCharge:	; 0 bytes @ 0x0
	global	?_SwitchBatteryState
?_SwitchBatteryState:	; 0 bytes @ 0x0
	global	?_KaiJi
?_KaiJi:	; 0 bytes @ 0x0
	global	?_LedDisplay
?_LedDisplay:	; 0 bytes @ 0x0
	global	??_LedDisplay
??_LedDisplay:	; 0 bytes @ 0x0
	global	?_lcd_init
?_lcd_init:	; 0 bytes @ 0x0
	global	?_main
?_main:	; 0 bytes @ 0x0
	global	?_ISR_Timer
?_ISR_Timer:	; 0 bytes @ 0x0
	global	??_ISR_Timer
??_ISR_Timer:	; 0 bytes @ 0x0
	global	?_ReadEE
?_ReadEE:	; 1 bytes @ 0x0
	global	?_BatteryStateSwitch
?_BatteryStateSwitch:	; 1 bytes @ 0x0
	global	?___wmul
?___wmul:	; 2 bytes @ 0x0
	global	?___ftpack
?___ftpack:	; 3 bytes @ 0x0
	global	ReadEE@addr
ReadEE@addr:	; 1 bytes @ 0x0
	global	delay@x
delay@x:	; 2 bytes @ 0x0
	global	Delay@x
Delay@x:	; 2 bytes @ 0x0
	global	BatteryStateSwitch@BatteryVoltage
BatteryStateSwitch@BatteryVoltage:	; 2 bytes @ 0x0
	global	___wmul@multiplier
___wmul@multiplier:	; 2 bytes @ 0x0
	global	___ftpack@arg
___ftpack@arg:	; 3 bytes @ 0x0
	ds	2
	global	??_delay
??_delay:	; 0 bytes @ 0x2
	global	??_Delay
??_Delay:	; 0 bytes @ 0x2
	global	??_BatteryStateSwitch
??_BatteryStateSwitch:	; 0 bytes @ 0x2
	global	delay@a
delay@a:	; 2 bytes @ 0x2
	global	Delay@a
Delay@a:	; 2 bytes @ 0x2
	global	___wmul@multiplicand
___wmul@multiplicand:	; 2 bytes @ 0x2
	ds	1
	global	___ftpack@exp
___ftpack@exp:	; 1 bytes @ 0x3
	ds	1
	global	??___wmul
??___wmul:	; 0 bytes @ 0x4
	global	___ftpack@sign
___ftpack@sign:	; 1 bytes @ 0x4
	global	delay@b
delay@b:	; 2 bytes @ 0x4
	global	Delay@b
Delay@b:	; 2 bytes @ 0x4
	global	___wmul@product
___wmul@product:	; 2 bytes @ 0x4
	ds	1
	global	??___ftpack
??___ftpack:	; 0 bytes @ 0x5
	ds	1
	global	??_lcd_write_data
??_lcd_write_data:	; 0 bytes @ 0x6
	global	??_lcd_write_command
??_lcd_write_command:	; 0 bytes @ 0x6
	global	??_system_state_init
??_system_state_init:	; 0 bytes @ 0x6
	global	?_getADValueOneTime
?_getADValueOneTime:	; 2 bytes @ 0x6
	global	lcd_write_command@command
lcd_write_command@command:	; 1 bytes @ 0x6
	global	lcd_write_data@data
lcd_write_data@data:	; 1 bytes @ 0x6
	global	BatteryStateSwitch@adjust
BatteryStateSwitch@adjust:	; 2 bytes @ 0x6
	ds	1
	global	??_lcd_move_char
??_lcd_move_char:	; 0 bytes @ 0x7
	global	??_lcd_init
??_lcd_init:	; 0 bytes @ 0x7
	global	?_lcd_print_line1
?_lcd_print_line1:	; 0 bytes @ 0x7
	global	?_lcd_print_line2
?_lcd_print_line2:	; 0 bytes @ 0x7
	global	lcd_move_char@postion
lcd_move_char@postion:	; 1 bytes @ 0x7
	global	lcd_print_line1@charPointer
lcd_print_line1@charPointer:	; 2 bytes @ 0x7
	global	lcd_print_line2@charPointer
lcd_print_line2@charPointer:	; 2 bytes @ 0x7
	ds	1
	global	??_getADValueOneTime
??_getADValueOneTime:	; 0 bytes @ 0x8
	global	?___fttol
?___fttol:	; 4 bytes @ 0x8
	global	lcd_move_char@i
lcd_move_char@i:	; 1 bytes @ 0x8
	global	___fttol@f1
___fttol@f1:	; 3 bytes @ 0x8
	ds	1
	global	lcd_print_line1@postion
lcd_print_line1@postion:	; 1 bytes @ 0x9
	global	lcd_print_line2@postion
lcd_print_line2@postion:	; 1 bytes @ 0x9
	ds	1
	global	??_lcd_print_line2
??_lcd_print_line2:	; 0 bytes @ 0xA
	global	getADValueOneTime@channel
getADValueOneTime@channel:	; 1 bytes @ 0xA
	global	lcd_print_line1@clear
lcd_print_line1@clear:	; 1 bytes @ 0xA
	ds	1
	global	??_lcd_print_line1
??_lcd_print_line1:	; 0 bytes @ 0xB
	global	getADValueOneTime@AD_Result
getADValueOneTime@AD_Result:	; 2 bytes @ 0xB
	ds	1
	global	??___lwtoft
??___lwtoft:	; 0 bytes @ 0xC
	global	?_readFromEEPROM
?_readFromEEPROM:	; 2 bytes @ 0xC
	ds	1
	global	??_GetBatteryVoltage
??_GetBatteryVoltage:	; 0 bytes @ 0xD
	global	??_GetSolarPanelVoltage
??_GetSolarPanelVoltage:	; 0 bytes @ 0xD
	global	??_GetLoadCurrentVoltage
??_GetLoadCurrentVoltage:	; 0 bytes @ 0xD
	ds	1
	global	??_readFromEEPROM
??_readFromEEPROM:	; 0 bytes @ 0xE
	global	??_load_system_state
??_load_system_state:	; 0 bytes @ 0xE
	global	??_LoadCurrentDealWith
??_LoadCurrentDealWith:	; 0 bytes @ 0xE
	global	??_PWMCharge
??_PWMCharge:	; 0 bytes @ 0xE
	global	??_main
??_main:	; 0 bytes @ 0xE
psect	cstackBANK0,class=BANK0,space=1
global __pcstackBANK0
__pcstackBANK0:
	global	??___fttol
??___fttol:	; 0 bytes @ 0x0
	global	?_getADValue
?_getADValue:	; 2 bytes @ 0x0
	ds	2
	global	??_getADValue
??_getADValue:	; 0 bytes @ 0x2
	ds	1
	global	___fttol@sign1
___fttol@sign1:	; 1 bytes @ 0x3
	ds	1
	global	getADValue@channel
getADValue@channel:	; 1 bytes @ 0x4
	global	___fttol@lval
___fttol@lval:	; 4 bytes @ 0x4
	ds	1
	global	getADValue@AD_Result
getADValue@AD_Result:	; 2 bytes @ 0x5
	ds	2
	global	getADValue@max
getADValue@max:	; 2 bytes @ 0x7
	ds	1
	global	___fttol@exp1
___fttol@exp1:	; 1 bytes @ 0x8
	ds	1
	global	?___lwtoft
?___lwtoft:	; 3 bytes @ 0x9
	global	getADValue@min
getADValue@min:	; 2 bytes @ 0x9
	global	___lwtoft@c
___lwtoft@c:	; 2 bytes @ 0x9
	ds	2
	global	getADValue@i
getADValue@i:	; 2 bytes @ 0xB
	ds	1
	global	?___ftdiv
?___ftdiv:	; 3 bytes @ 0xC
	global	___ftdiv@f2
___ftdiv@f2:	; 3 bytes @ 0xC
	ds	1
	global	getADValue@AD_OneResult
getADValue@AD_OneResult:	; 2 bytes @ 0xD
	ds	2
	global	?_GetBatteryVoltage
?_GetBatteryVoltage:	; 2 bytes @ 0xF
	global	?_GetSolarPanelVoltage
?_GetSolarPanelVoltage:	; 2 bytes @ 0xF
	global	?_GetLoadCurrentVoltage
?_GetLoadCurrentVoltage:	; 2 bytes @ 0xF
	global	___ftdiv@f1
___ftdiv@f1:	; 3 bytes @ 0xF
	ds	2
	global	??_SolarPanelDealWith
??_SolarPanelDealWith:	; 0 bytes @ 0x11
	global	??_SwitchBatteryState
??_SwitchBatteryState:	; 0 bytes @ 0x11
	global	??_KaiJi
??_KaiJi:	; 0 bytes @ 0x11
	global	PWMCharge@ChangeBatteryVoltag
PWMCharge@ChangeBatteryVoltag:	; 2 bytes @ 0x11
	ds	1
	global	??___ftdiv
??___ftdiv:	; 0 bytes @ 0x12
	ds	3
	global	___ftdiv@cntr
___ftdiv@cntr:	; 1 bytes @ 0x15
	ds	1
	global	___ftdiv@f3
___ftdiv@f3:	; 3 bytes @ 0x16
	ds	1
	global	SolarPanelDealWith@SolarPanelVoltage
SolarPanelDealWith@SolarPanelVoltage:	; 2 bytes @ 0x17
	ds	2
	global	___ftdiv@exp
___ftdiv@exp:	; 1 bytes @ 0x19
	ds	1
	global	___ftdiv@sign
___ftdiv@sign:	; 1 bytes @ 0x1A
	ds	1
	global	readFromEEPROM@l_byte
readFromEEPROM@l_byte:	; 1 bytes @ 0x1B
	ds	1
	global	readFromEEPROM@h_byte
readFromEEPROM@h_byte:	; 1 bytes @ 0x1C
	ds	1
	global	readFromEEPROM@read_data
readFromEEPROM@read_data:	; 2 bytes @ 0x1D
	ds	2
	global	readFromEEPROM@type
readFromEEPROM@type:	; 1 bytes @ 0x1F
	ds	1
;;Data sizes: Strings 0, constant 227, data 79, bss 43, persistent 0 stack 0
;;Auto spaces:   Size  Autos    Used
;; COMMON          14     14      14
;; BANK0           80     32      80
;; BANK1           80      0      78
;; BANK2           80      0       0

;;
;; Pointer list with targets:

;; ?___ftpack	float  size(1) Largest target is 0
;;
;; ?_readFromEEPROM	unsigned int  size(1) Largest target is 0
;;
;; ?___ftdiv	float  size(1) Largest target is 0
;;
;; ?___fttol	long  size(1) Largest target is 0
;;
;; ?___lwtoft	float  size(1) Largest target is 0
;;
;; ?___wmul	unsigned int  size(1) Largest target is 0
;;
;; ?_GetLoadCurrentVoltage	unsigned int  size(1) Largest target is 0
;;
;; ?_GetSolarPanelVoltage	unsigned int  size(1) Largest target is 0
;;
;; ?_GetBatteryVoltage	unsigned int  size(1) Largest target is 0
;;
;; ?_getADValue	unsigned int  size(1) Largest target is 0
;;
;; ?_getADValueOneTime	unsigned int  size(1) Largest target is 0
;;
;; charPointer	PTR const unsigned char  size(2) Largest target is 4096
;;		 -> ROM(CODE[4096]), state7_2(CODE[17]), state7_1(CODE[15]), state6_2(CODE[15]), 
;;		 -> state6_1(CODE[16]), state5_2(CODE[16]), state5_1(CODE[17]), state4_1(CODE[17]), 
;;		 -> state3_2(CODE[17]), state3_1(CODE[17]), state2_1(CODE[13]), state1_2(CODE[16]), 
;;		 -> state1_1(CODE[16]), systemType1(BANK1[11]), checking(BANK1[12]), welcome(BANK1[8]), 
;;		 -> state2_4(CODE[13]), state2_3(CODE[10]), state2_2(CODE[12]), 
;;
;; lcd_print_line2@charPointer	PTR const unsigned char  size(2) Largest target is 4096
;;		 -> ROM(CODE[4096]), state7_2(CODE[17]), state7_1(CODE[15]), state6_2(CODE[15]), 
;;		 -> state6_1(CODE[16]), state5_2(CODE[16]), state5_1(CODE[17]), state4_1(CODE[17]), 
;;		 -> state3_2(CODE[17]), state3_1(CODE[17]), state2_1(CODE[13]), state1_2(CODE[16]), 
;;		 -> state1_1(CODE[16]), systemType1(BANK1[11]), checking(BANK1[12]), welcome(BANK1[8]), 
;;		 -> state2_4(CODE[13]), state2_3(CODE[10]), state2_2(CODE[12]), 
;;
;; lcd_print_line1@charPointer	PTR const unsigned char  size(2) Largest target is 4096
;;		 -> ROM(CODE[4096]), state7_2(CODE[17]), state7_1(CODE[15]), state6_2(CODE[15]), 
;;		 -> state6_1(CODE[16]), state5_2(CODE[16]), state5_1(CODE[17]), state4_1(CODE[17]), 
;;		 -> state3_2(CODE[17]), state3_1(CODE[17]), state2_1(CODE[13]), state1_2(CODE[16]), 
;;		 -> state1_1(CODE[16]), systemType1(BANK1[11]), checking(BANK1[12]), welcome(BANK1[8]), 
;;		 -> state2_4(CODE[13]), state2_3(CODE[10]), state2_2(CODE[12]), 
;;
;; batteryStatePointer	PTR unsigned char [3] size(1) Largest target is 13
;;		 -> state2_4(CODE[13]), state2_3(CODE[10]), state2_2(CODE[12]), 
;;
;; BatteryStandard	PTR unsigned int  size(1) Largest target is 22
;;		 -> Battery_24V(BANK1[22]), Battery_12V(BANK1[22]), NULL(NULL[0]), 
;;


;;
;; Critical Paths under _main in COMMON
;;
;;   _KaiJi->_GetBatteryVoltage
;;   _KaiJi->_GetSolarPanelVoltage
;;   _KaiJi->_GetLoadCurrentVoltage
;;   _SwitchBatteryState->_GetBatteryVoltage
;;   _PWMCharge->_GetBatteryVoltage
;;   _SolarPanelDealWith->_GetSolarPanelVoltage
;;   _LoadCurrentDealWith->_GetLoadCurrentVoltage
;;   _load_system_state->_readFromEEPROM
;;   _lcd_print_line2->_lcd_write_command
;;   _lcd_print_line2->_lcd_write_data
;;   _lcd_print_line1->_lcd_write_command
;;   _lcd_print_line1->_lcd_write_data
;;   _lcd_init->_lcd_write_command
;;   _lcd_move_char->_lcd_write_command
;;   _getADValue->_getADValueOneTime
;;   _readFromEEPROM->___fttol
;;   ___lwtoft->___fttol
;;   ___ftdiv->___fttol
;;   _lcd_write_command->_delay
;;   _lcd_write_data->_delay
;;   _getADValueOneTime->_Delay
;;   _system_state_init->_Delay
;;   ___fttol->___ftpack
;;
;; Critical Paths under _ISR_Timer in COMMON
;;
;;   None.
;;
;; Critical Paths under _main in BANK0
;;
;;   _KaiJi->_GetBatteryVoltage
;;   _KaiJi->_GetSolarPanelVoltage
;;   _KaiJi->_GetLoadCurrentVoltage
;;   _SwitchBatteryState->_GetBatteryVoltage
;;   _PWMCharge->_GetBatteryVoltage
;;   _SolarPanelDealWith->_GetSolarPanelVoltage
;;   _LoadCurrentDealWith->_GetLoadCurrentVoltage
;;   _GetLoadCurrentVoltage->_getADValue
;;   _GetSolarPanelVoltage->_getADValue
;;   _GetBatteryVoltage->_getADValue
;;   _load_system_state->_readFromEEPROM
;;   _readFromEEPROM->___ftdiv
;;   ___lwtoft->___fttol
;;   ___ftdiv->___lwtoft
;;
;; Critical Paths under _ISR_Timer in BANK0
;;
;;   None.
;;
;; Critical Paths under _main in BANK1
;;
;;   None.
;;
;; Critical Paths under _ISR_Timer in BANK1
;;
;;   None.
;;
;; Critical Paths under _main in BANK2
;;
;;   None.
;;
;; Critical Paths under _ISR_Timer in BANK2
;;
;;   None.

;;
;;Main: autosize = 0, tempsize = 0, incstack = 0, save=0
;;

;;
;;Call Graph Tables:
;;
;; ---------------------------------------------------------------------------------
;; (Depth) Function   	        Calls       Base Space   Used Autos Params    Refs
;; ---------------------------------------------------------------------------------
;; (0) _main                                                 0     0      0    7362
;;                  _system_state_init
;;                  _load_system_state
;;                           _lcd_init
;;                  _lcd_write_command
;;                    _lcd_print_line1
;;                    _lcd_print_line2
;;                              _KaiJi
;;                         _SelectMode
;;                _LoadCurrentDealWith
;;                 _SwitchBatteryState
;;                         _LedDisplay
;;                 _SolarPanelDealWith
;;                          _PWMCharge
;;                      _lcd_move_char
;; ---------------------------------------------------------------------------------
;; (1) _KaiJi                                                2     2      0    1529
;;                                             17 BANK0      2     2      0
;;                  _GetBatteryVoltage
;;               _GetSolarPanelVoltage
;;                             ___wmul
;;              _GetLoadCurrentVoltage
;; ---------------------------------------------------------------------------------
;; (1) _SwitchBatteryState                                   6     6      0    1309
;;                                             17 BANK0      6     6      0
;;                  _GetBatteryVoltage
;;                 _BatteryStateSwitch
;;                      _lcd_move_char
;;                     _lcd_write_data
;;                              _Delay
;; ---------------------------------------------------------------------------------
;; (1) _PWMCharge                                            2     2      0     524
;;                                             17 BANK0      2     2      0
;;                  _GetBatteryVoltage
;; ---------------------------------------------------------------------------------
;; (1) _SolarPanelDealWith                                   8     8      0     777
;;                                             17 BANK0      8     8      0
;;                              _Delay
;;               _GetSolarPanelVoltage
;;                             ___wmul
;; ---------------------------------------------------------------------------------
;; (1) _LoadCurrentDealWith                                  2     2      0     547
;;                                             17 BANK0      2     2      0
;;              _GetLoadCurrentVoltage
;; ---------------------------------------------------------------------------------
;; (2) _GetLoadCurrentVoltage                                3     1      2     479
;;                                             13 COMMON     1     1      0
;;                                             15 BANK0      2     0      2
;;                         _getADValue
;; ---------------------------------------------------------------------------------
;; (2) _GetSolarPanelVoltage                                 3     1      2     479
;;                                             13 COMMON     1     1      0
;;                                             15 BANK0      2     0      2
;;                         _getADValue
;; ---------------------------------------------------------------------------------
;; (2) _GetBatteryVoltage                                    3     1      2     479
;;                                             13 COMMON     1     1      0
;;                                             15 BANK0      2     0      2
;;                         _getADValue
;; ---------------------------------------------------------------------------------
;; (1) _load_system_state                                    0     0      0    1180
;;                     _readFromEEPROM
;; ---------------------------------------------------------------------------------
;; (1) _lcd_print_line2                                      3     0      3     339
;;                                              7 COMMON     3     0      3
;;                  _lcd_write_command
;;                     _lcd_write_data
;; ---------------------------------------------------------------------------------
;; (1) _lcd_print_line1                                      4     0      4     475
;;                                              7 COMMON     4     0      4
;;                  _lcd_write_command
;;                              _delay
;;                     _lcd_write_data
;; ---------------------------------------------------------------------------------
;; (1) _lcd_init                                             0     0      0     250
;;                  _lcd_write_command
;;                              _delay
;; ---------------------------------------------------------------------------------
;; (1) _lcd_move_char                                        2     2      0     182
;;                                              7 COMMON     2     2      0
;;                  _lcd_write_command
;; ---------------------------------------------------------------------------------
;; (3) _getADValue                                          15    13      2     456
;;                                              0 BANK0     15    13      2
;;                  _getADValueOneTime
;; ---------------------------------------------------------------------------------
;; (2) _readFromEEPROM                                       7     5      2    1180
;;                                             12 COMMON     2     0      2
;;                                             27 BANK0      5     5      0
;;                             _ReadEE
;;                           ___lwtoft
;;                            ___ftdiv
;;                            ___fttol
;; ---------------------------------------------------------------------------------
;; (3) ___lwtoft                                             3     0      3     231
;;                                              9 BANK0      3     0      3
;;                           ___ftpack
;;                            ___fttol (ARG)
;; ---------------------------------------------------------------------------------
;; (3) ___ftdiv                                             15     9      6     489
;;                                             12 BANK0     15     9      6
;;                           ___ftpack
;;                           ___lwtoft (ARG)
;;                            ___fttol (ARG)
;; ---------------------------------------------------------------------------------
;; (2) _lcd_write_command                                    1     1      0     136
;;                                              6 COMMON     1     1      0
;;                              _delay
;; ---------------------------------------------------------------------------------
;; (2) _lcd_write_data                                       1     1      0     136
;;                                              6 COMMON     1     1      0
;;                              _delay
;; ---------------------------------------------------------------------------------
;; (4) _getADValueOneTime                                    7     5      2     161
;;                                              6 COMMON     7     5      2
;;                              _Delay
;; ---------------------------------------------------------------------------------
;; (1) _system_state_init                                    0     0      0     114
;;                              _Delay
;; ---------------------------------------------------------------------------------
;; (3) ___fttol                                             13     9      4     252
;;                                              8 COMMON     4     0      4
;;                                              0 BANK0      9     9      0
;;                           ___ftpack (ARG)
;; ---------------------------------------------------------------------------------
;; (4) ___ftpack                                             8     3      5     209
;;                                              0 COMMON     8     3      5
;; ---------------------------------------------------------------------------------
;; (2) ___wmul                                               6     2      4      92
;;                                              0 COMMON     6     2      4
;; ---------------------------------------------------------------------------------
;; (1) _LedDisplay                                           0     0      0       0
;; ---------------------------------------------------------------------------------
;; (2) _BatteryStateSwitch                                   8     6      2     398
;;                                              0 COMMON     8     6      2
;; ---------------------------------------------------------------------------------
;; (1) _SelectMode                                           0     0      0       0
;; ---------------------------------------------------------------------------------
;; (3) _ReadEE                                               1     1      0      22
;;                                              0 COMMON     1     1      0
;; ---------------------------------------------------------------------------------
;; (5) _Delay                                                6     4      2     114
;;                                              0 COMMON     6     4      2
;; ---------------------------------------------------------------------------------
;; (3) _delay                                                6     4      2     114
;;                                              0 COMMON     6     4      2
;; ---------------------------------------------------------------------------------
;; Estimated maximum stack depth 5
;; ---------------------------------------------------------------------------------
;; (Depth) Function   	        Calls       Base Space   Used Autos Params    Refs
;; ---------------------------------------------------------------------------------
;; (6) _ISR_Timer                                            0     0      0       0
;; ---------------------------------------------------------------------------------
;; Estimated maximum stack depth 6
;; ---------------------------------------------------------------------------------

;; Call Graph Graphs:

;; _main (ROOT)
;;   _system_state_init
;;     _Delay
;;   _load_system_state
;;     _readFromEEPROM
;;       _ReadEE
;;       ___lwtoft
;;         ___ftpack
;;         ___fttol (ARG)
;;           ___ftpack (ARG)
;;       ___ftdiv
;;         ___ftpack
;;         ___lwtoft (ARG)
;;           ___ftpack
;;           ___fttol (ARG)
;;             ___ftpack (ARG)
;;         ___fttol (ARG)
;;           ___ftpack (ARG)
;;       ___fttol
;;         ___ftpack (ARG)
;;   _lcd_init
;;     _lcd_write_command
;;       _delay
;;     _delay
;;   _lcd_write_command
;;     _delay
;;   _lcd_print_line1
;;     _lcd_write_command
;;       _delay
;;     _delay
;;     _lcd_write_data
;;       _delay
;;   _lcd_print_line2
;;     _lcd_write_command
;;       _delay
;;     _lcd_write_data
;;       _delay
;;   _KaiJi
;;     _GetBatteryVoltage
;;       _getADValue
;;         _getADValueOneTime
;;           _Delay
;;     _GetSolarPanelVoltage
;;       _getADValue
;;         _getADValueOneTime
;;           _Delay
;;     ___wmul
;;     _GetLoadCurrentVoltage
;;       _getADValue
;;         _getADValueOneTime
;;           _Delay
;;   _SelectMode
;;   _LoadCurrentDealWith
;;     _GetLoadCurrentVoltage
;;       _getADValue
;;         _getADValueOneTime
;;           _Delay
;;   _SwitchBatteryState
;;     _GetBatteryVoltage
;;       _getADValue
;;         _getADValueOneTime
;;           _Delay
;;     _BatteryStateSwitch
;;     _lcd_move_char
;;       _lcd_write_command
;;         _delay
;;     _lcd_write_data
;;       _delay
;;     _Delay
;;   _LedDisplay
;;   _SolarPanelDealWith
;;     _Delay
;;     _GetSolarPanelVoltage
;;       _getADValue
;;         _getADValueOneTime
;;           _Delay
;;     ___wmul
;;   _PWMCharge
;;     _GetBatteryVoltage
;;       _getADValue
;;         _getADValueOneTime
;;           _Delay
;;   _lcd_move_char
;;     _lcd_write_command
;;       _delay
;;
;; _ISR_Timer (ROOT)
;;

;; Address spaces:

;;Name               Size   Autos  Total    Cost      Usage
;;BIGRAM              F0      0       0       0        0.0%
;;EEDATA             100      0       0       0        0.0%
;;NULL                 0      0       0       0        0.0%
;;CODE                 0      0       0       0        0.0%
;;BITCOMMON            E      0       0       1        0.0%
;;BITSFR0              0      0       0       1        0.0%
;;SFR0                 0      0       0       1        0.0%
;;COMMON               E      E       E       2      100.0%
;;BITSFR1              0      0       0       2        0.0%
;;SFR1                 0      0       0       2        0.0%
;;BITSFR2              0      0       0       3        0.0%
;;SFR2                 0      0       0       3        0.0%
;;STACK                0      0       5       3        0.0%
;;BITSFR3              0      0       0       4        0.0%
;;SFR3                 0      0       0       4        0.0%
;;ABS                  0      0      AC       4        0.0%
;;BITBANK0            50      0       4       5        5.0%
;;BITSFR4              0      0       0       5        0.0%
;;SFR4                 0      0       0       5        0.0%
;;BANK0               50     20      50       6      100.0%
;;BITSFR5              0      0       0       6        0.0%
;;SFR5                 0      0       0       6        0.0%
;;BITBANK1            50      0       0       7        0.0%
;;BITSFR6              0      0       0       7        0.0%
;;SFR6                 0      0       0       7        0.0%
;;BANK1               50      0      4E       8       97.5%
;;BITSFR7              0      0       0       8        0.0%
;;SFR7                 0      0       0       8        0.0%
;;BITBANK2            50      0       0       9        0.0%
;;BITSFR8              0      0       0       9        0.0%
;;SFR8                 0      0       0       9        0.0%
;;BANK2               50      0       0      10        0.0%
;;BITSFR9              0      0       0      10        0.0%
;;SFR9                 0      0       0      10        0.0%
;;BITSFR10             0      0       0      11        0.0%
;;SFR10                0      0       0      11        0.0%
;;DATA                 0      0      B1      11        0.0%
;;BITSFR11             0      0       0      12        0.0%
;;SFR11                0      0       0      12        0.0%
;;BITSFR12             0      0       0      13        0.0%
;;SFR12                0      0       0      13        0.0%
;;BITSFR13             0      0       0      14        0.0%
;;SFR13                0      0       0      14        0.0%
;;BITSFR14             0      0       0      15        0.0%
;;SFR14                0      0       0      15        0.0%
;;BITSFR15             0      0       0      16        0.0%
;;SFR15                0      0       0      16        0.0%
;;BITSFR16             0      0       0      17        0.0%
;;SFR16                0      0       0      17        0.0%
;;BITSFR17             0      0       0      18        0.0%
;;SFR17                0      0       0      18        0.0%
;;BITSFR18             0      0       0      19        0.0%
;;SFR18                0      0       0      19        0.0%
;;BITSFR19             0      0       0      20        0.0%
;;SFR19                0      0       0      20        0.0%
;;BITSFR20             0      0       0      21        0.0%
;;SFR20                0      0       0      21        0.0%
;;BITSFR21             0      0       0      22        0.0%
;;SFR21                0      0       0      22        0.0%
;;BITSFR22             0      0       0      23        0.0%
;;SFR22                0      0       0      23        0.0%
;;BITSFR23             0      0       0      24        0.0%
;;SFR23                0      0       0      24        0.0%
;;BITSFR24             0      0       0      25        0.0%
;;SFR24                0      0       0      25        0.0%
;;BITSFR25             0      0       0      26        0.0%
;;SFR25                0      0       0      26        0.0%
;;BITSFR26             0      0       0      27        0.0%
;;SFR26                0      0       0      27        0.0%
;;BITSFR27             0      0       0      28        0.0%
;;SFR27                0      0       0      28        0.0%
;;BITSFR28             0      0       0      29        0.0%
;;SFR28                0      0       0      29        0.0%
;;BITSFR29             0      0       0      30        0.0%
;;SFR29                0      0       0      30        0.0%
;;BITSFR30             0      0       0      31        0.0%
;;SFR30                0      0       0      31        0.0%
;;BITSFR31             0      0       0      32        0.0%
;;SFR31                0      0       0      32        0.0%

	global	_main
psect	maintext,global,class=CODE,delta=2
global __pmaintext
__pmaintext:

;; *************** function _main *****************
;; Defined at:
;;		line 19 in file "C:\CTK\CTK80A 150928\CTK80A_Jas\main.c"
;; Parameters:    Size  Location     Type
;;		None
;; Auto vars:     Size  Location     Type
;;		None
;; Return value:  Size  Location     Type
;;		None               void
;; Registers used:
;;		wreg, fsr0l, fsr0h, fsr1l, fsr1h, status,2, status,0, pclath, cstack
;; Tracked objects:
;;		On entry : 17F/0
;;		On exit  : 1F/0
;;		Unchanged: FFE00/0
;; Data sizes:     COMMON   BANK0   BANK1   BANK2
;;      Params:         0       0       0       0
;;      Locals:         0       0       0       0
;;      Temps:          0       0       0       0
;;      Totals:         0       0       0       0
;;Total ram usage:        0 bytes
;; Hardware stack levels required when called:    6
;; This function calls:
;;		_system_state_init
;;		_load_system_state
;;		_lcd_init
;;		_lcd_write_command
;;		_lcd_print_line1
;;		_lcd_print_line2
;;		_KaiJi
;;		_SelectMode
;;		_LoadCurrentDealWith
;;		_SwitchBatteryState
;;		_LedDisplay
;;		_SolarPanelDealWith
;;		_PWMCharge
;;		_lcd_move_char
;; This function is called by:
;;		Startup code after reset
;; This function uses a non-reentrant model
;;
psect	maintext
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\main.c"
	line	19
	global	__size_of_main
	__size_of_main	equ	__end_of_main-_main
	
_main:	
	opt	stack 10
; Regs used in _main: [wreg-status,0+pclath+cstack]
	line	22
	
l5786:	
;main.c: 22: system_state_init();
	fcall	_system_state_init
	line	23
	
l5788:	
;main.c: 23: load_system_state();
	fcall	_load_system_state
	line	26
	
l5790:	
;main.c: 26: lcd_init();
	fcall	_lcd_init
	line	27
	
l5792:	
;main.c: 27: lcd_write_command(0x80);
	movlw	(080h)
	fcall	_lcd_write_command
	line	28
	
l5794:	
;main.c: 28: charPointer = welcome;
	movlw	(_welcome&0ffh)
	movwf	(_charPointer)
	movlw	0x1/2
	movwf	(_charPointer+1)
	line	29
	
l5796:	
;main.c: 29: lcd_print_line1(charPointer, 0, 0);
	movf	(_charPointer+1),w
	movwf	(?_lcd_print_line1+1)
	movf	(_charPointer),w
	movwf	(?_lcd_print_line1)
	clrf	0+(?_lcd_print_line1)+02h
	clrf	0+(?_lcd_print_line1)+03h
	fcall	_lcd_print_line1
	line	30
	
l5798:	
;main.c: 30: charPointer = checking;
	movlw	(_checking&0ffh)
	movwf	(_charPointer)
	movlw	0x1/2
	movwf	(_charPointer+1)
	line	31
	
l5800:	
;main.c: 31: lcd_print_line2(charPointer, 0);
	movf	(_charPointer+1),w
	movwf	(?_lcd_print_line2+1)
	movf	(_charPointer),w
	movwf	(?_lcd_print_line2)
	clrf	0+(?_lcd_print_line2)+02h
	fcall	_lcd_print_line2
	line	35
	
l5802:	
;main.c: 35: (LATC |= (1 << 4));
	movlb 2	; select bank2
	bsf	(270)^0100h+(4/8),(4)&7	;volatile
	line	37
	
l5804:	
;main.c: 37: KaiJi();
	fcall	_KaiJi
	line	40
	
l5806:	
# 40 "C:\CTK\CTK80A 150928\CTK80A_Jas\main.c"
CLRWDT ;#
psect	maintext
	line	42
;main.c: 42: SelectMode();
	fcall	_SelectMode
	line	43
	
l5808:	
;main.c: 43: SystemModeType = 0x01;
	clrf	(_SystemModeType)
	incf	(_SystemModeType),f
	line	44
	
l5810:	
;main.c: 44: LoadCurrentDealWith();
	fcall	_LoadCurrentDealWith
	line	45
	
l5812:	
;main.c: 45: SwitchBatteryState();
	fcall	_SwitchBatteryState
	line	46
	
l5814:	
;main.c: 46: LedDisplay();
	fcall	_LedDisplay
	line	47
	
l5816:	
;main.c: 47: if(PVCount > 50)
	movlw	(033h)
	movlb 0	; select bank0
	subwf	(_PVCount),w
	skipc
	goto	u3521
	goto	u3520
u3521:
	goto	l5822
u3520:
	line	49
	
l5818:	
;main.c: 48: {
;main.c: 49: PVCount = 0;
	clrf	(_PVCount)
	line	50
	
l5820:	
;main.c: 50: SolarPanelDealWith();
	fcall	_SolarPanelDealWith
	line	52
	
l5822:	
;main.c: 51: }
;main.c: 52: if(PWMChargeFlag == 1)
	btfss	(_PWMChargeFlag/8),(_PWMChargeFlag)&7
	goto	u3531
	goto	u3530
u3531:
	goto	l5828
u3530:
	line	54
	
l5824:	
;main.c: 53: {
;main.c: 54: PWMChargeFlag = 0;
	bcf	(_PWMChargeFlag/8),(_PWMChargeFlag)&7
	line	55
	
l5826:	
;main.c: 55: PWMCharge();
	fcall	_PWMCharge
	line	59
	
l5828:	
;main.c: 56: }
;main.c: 59: if (isNeedChange) {
	btfss	(_isNeedChange/8),(_isNeedChange)&7
	goto	u3541
	goto	u3540
u3541:
	goto	l5932
u3540:
	line	60
	
l5830:	
;main.c: 60: isNeedChange = 0;
	bcf	(_isNeedChange/8),(_isNeedChange)&7
	line	61
;main.c: 61: switch(lcd_state) {
	goto	l5930
	line	64
	
l5832:	
;main.c: 64: charPointer = systemType1;
	movlw	(_systemType1&0ffh)
	movwf	(_charPointer)
	movlw	0x1/2
	movwf	(_charPointer+1)
	line	65
	
l5834:	
;main.c: 65: lcd_print_line1(charPointer, 0 ,1);
	movf	(_charPointer+1),w
	movwf	(?_lcd_print_line1+1)
	movf	(_charPointer),w
	movwf	(?_lcd_print_line1)
	clrf	0+(?_lcd_print_line1)+02h
	clrf	0+(?_lcd_print_line1)+03h
	incf	0+(?_lcd_print_line1)+03h,f
	fcall	_lcd_print_line1
	line	66
;main.c: 66: break;
	goto	l5932
	line	68
	
l5836:	
;main.c: 68: charPointer = state1_1;
	movlw	low(_state1_1|8000h)
	movwf	(_charPointer)
	movlw	high(_state1_1|8000h)
	movwf	(_charPointer+1)
	line	69
	
l5838:	
;main.c: 69: lcd_print_line1(charPointer, 0, 1);
	movf	(_charPointer+1),w
	movwf	(?_lcd_print_line1+1)
	movf	(_charPointer),w
	movwf	(?_lcd_print_line1)
	clrf	0+(?_lcd_print_line1)+02h
	clrf	0+(?_lcd_print_line1)+03h
	incf	0+(?_lcd_print_line1)+03h,f
	fcall	_lcd_print_line1
	line	70
	
l5840:	
;main.c: 70: charPointer = state1_2;
	movlw	low(_state1_2|8000h)
	movwf	(_charPointer)
	movlw	high(_state1_2|8000h)
	movwf	(_charPointer+1)
	line	71
;main.c: 71: lcd_print_line2(charPointer, 0);
	movf	(_charPointer+1),w
	movwf	(?_lcd_print_line2+1)
	movf	(_charPointer),w
	movwf	(?_lcd_print_line2)
	clrf	0+(?_lcd_print_line2)+02h
	fcall	_lcd_print_line2
	line	72
;main.c: 72: break;
	goto	l5932
	line	106
	
l5842:	
;main.c: 106: charPointer = state2_1;
	movlw	low(_state2_1|8000h)
	movwf	(_charPointer)
	movlw	high(_state2_1|8000h)
	movwf	(_charPointer+1)
	line	107
	
l5844:	
;main.c: 107: lcd_print_line1(charPointer, 0, 1);
	movf	(_charPointer+1),w
	movwf	(?_lcd_print_line1+1)
	movf	(_charPointer),w
	movwf	(?_lcd_print_line1)
	clrf	0+(?_lcd_print_line1)+02h
	clrf	0+(?_lcd_print_line1)+03h
	incf	0+(?_lcd_print_line1)+03h,f
	fcall	_lcd_print_line1
	line	108
	
l5846:	
;main.c: 108: charPointer = batteryStatePointer[batteryType];
	movf	(_batteryType),w
	addlw	_batteryStatePointer&0ffh
	movwf	fsr1l
	clrf fsr1h	
	
	movf	indf1,w
	movwf	(_charPointer)
	movlw	high(__stringtab)|80h
	movwf	(_charPointer+1)
	line	109
	
l5848:	
;main.c: 109: lcd_print_line2(charPointer, 0);
	movf	(_charPointer+1),w
	movwf	(?_lcd_print_line2+1)
	movf	(_charPointer),w
	movwf	(?_lcd_print_line2)
	clrf	0+(?_lcd_print_line2)+02h
	fcall	_lcd_print_line2
	line	110
	
l5850:	
;main.c: 110: if (isSettingMode) {
	btfss	(_isSettingMode/8),(_isSettingMode)&7
	goto	u3551
	goto	u3550
u3551:
	goto	l5932
u3550:
	line	111
	
l5852:	
;main.c: 111: if (is_second_setting) {
	btfss	(_is_second_setting/8),(_is_second_setting)&7
	goto	u3561
	goto	u3560
u3561:
	goto	l5856
u3560:
	line	112
	
l5854:	
;main.c: 112: lcd_move_char(0x40);
	movlw	(040h)
	fcall	_lcd_move_char
	line	113
;main.c: 113: } else {
	goto	l5932
	line	115
	
l5856:	
;main.c: 115: lcd_move_char(0x20);
	movlw	(020h)
	fcall	_lcd_move_char
	goto	l5932
	line	120
	
l5858:	
;main.c: 120: charPointer = state3_1;
	movlw	low(_state3_1|8000h)
	movwf	(_charPointer)
	movlw	high(_state3_1|8000h)
	movwf	(_charPointer+1)
	line	121
	
l5860:	
;main.c: 121: lcd_print_line1(charPointer, 0, 1);
	movf	(_charPointer+1),w
	movwf	(?_lcd_print_line1+1)
	movf	(_charPointer),w
	movwf	(?_lcd_print_line1)
	clrf	0+(?_lcd_print_line1)+02h
	clrf	0+(?_lcd_print_line1)+03h
	incf	0+(?_lcd_print_line1)+03h,f
	fcall	_lcd_print_line1
	line	122
	
l5862:	
;main.c: 122: charPointer = state3_2;
	movlw	low(_state3_2|8000h)
	movwf	(_charPointer)
	movlw	high(_state3_2|8000h)
	movwf	(_charPointer+1)
	line	123
;main.c: 123: lcd_print_line2(charPointer, 0);
	movf	(_charPointer+1),w
	movwf	(?_lcd_print_line2+1)
	movf	(_charPointer),w
	movwf	(?_lcd_print_line2)
	clrf	0+(?_lcd_print_line2)+02h
	fcall	_lcd_print_line2
	line	124
	
l5864:	
;main.c: 124: if (isSettingMode) {
	btfss	(_isSettingMode/8),(_isSettingMode)&7
	goto	u3571
	goto	u3570
u3571:
	goto	l5932
u3570:
	line	125
	
l5866:	
;main.c: 125: if (is_second_setting) {
	btfss	(_is_second_setting/8),(_is_second_setting)&7
	goto	u3581
	goto	u3580
u3581:
	goto	l5870
u3580:
	line	126
	
l5868:	
;main.c: 126: lcd_move_char(0xe + 0x40);
	movlw	(04Eh)
	fcall	_lcd_move_char
	line	127
;main.c: 127: } else {
	goto	l5932
	line	128
	
l5870:	
;main.c: 128: lcd_move_char(0xe);
	movlw	(0Eh)
	fcall	_lcd_move_char
	goto	l5932
	line	133
	
l5872:	
;main.c: 133: charPointer = state4_1;
	movlw	low(_state4_1|8000h)
	movwf	(_charPointer)
	movlw	high(_state4_1|8000h)
	movwf	(_charPointer+1)
	line	134
	
l5874:	
;main.c: 134: lcd_print_line1(charPointer, 0, 1);
	movf	(_charPointer+1),w
	movwf	(?_lcd_print_line1+1)
	movf	(_charPointer),w
	movwf	(?_lcd_print_line1)
	clrf	0+(?_lcd_print_line1)+02h
	clrf	0+(?_lcd_print_line1)+03h
	incf	0+(?_lcd_print_line1)+03h,f
	fcall	_lcd_print_line1
	line	135
	
l5876:	
;main.c: 135: charPointer = batteryStatePointer[1];
	movlb 1	; select bank1
	movf	(0+_batteryStatePointer+01h)^080h,w
	movlb 0	; select bank0
	movwf	(_charPointer)
	movlw	high(__stringtab)|80h
	movwf	(_charPointer+1)
	line	136
;main.c: 136: lcd_print_line2(charPointer, 0);
	movf	(_charPointer+1),w
	movwf	(?_lcd_print_line2+1)
	movf	(_charPointer),w
	movwf	(?_lcd_print_line2)
	clrf	0+(?_lcd_print_line2)+02h
	fcall	_lcd_print_line2
	line	137
	
l5878:	
;main.c: 137: if (isSettingMode) {
	btfss	(_isSettingMode/8),(_isSettingMode)&7
	goto	u3591
	goto	u3590
u3591:
	goto	l5932
u3590:
	line	138
	
l5880:	
;main.c: 138: if (is_second_setting) {
	btfss	(_is_second_setting/8),(_is_second_setting)&7
	goto	u3601
	goto	u3600
u3601:
	goto	l5884
u3600:
	line	139
	
l5882:	
;main.c: 139: lcd_move_char(0xC + 0x40);
	movlw	(04Ch)
	fcall	_lcd_move_char
	line	140
;main.c: 140: } else {
	goto	l5932
	line	141
	
l5884:	
;main.c: 141: lcd_move_char(0xe);
	movlw	(0Eh)
	fcall	_lcd_move_char
	goto	l5932
	line	146
	
l5886:	
;main.c: 146: charPointer = state5_1;
	movlw	low(_state5_1|8000h)
	movwf	(_charPointer)
	movlw	high(_state5_1|8000h)
	movwf	(_charPointer+1)
	line	147
	
l5888:	
;main.c: 147: lcd_print_line1(charPointer, 0, 1);
	movf	(_charPointer+1),w
	movwf	(?_lcd_print_line1+1)
	movf	(_charPointer),w
	movwf	(?_lcd_print_line1)
	clrf	0+(?_lcd_print_line1)+02h
	clrf	0+(?_lcd_print_line1)+03h
	incf	0+(?_lcd_print_line1)+03h,f
	fcall	_lcd_print_line1
	line	148
	
l5890:	
;main.c: 148: charPointer = state5_2;
	movlw	low(_state5_2|8000h)
	movwf	(_charPointer)
	movlw	high(_state5_2|8000h)
	movwf	(_charPointer+1)
	line	149
;main.c: 149: lcd_print_line2(charPointer, 0);
	movf	(_charPointer+1),w
	movwf	(?_lcd_print_line2+1)
	movf	(_charPointer),w
	movwf	(?_lcd_print_line2)
	clrf	0+(?_lcd_print_line2)+02h
	fcall	_lcd_print_line2
	line	150
	
l5892:	
;main.c: 150: if (isSettingMode) {
	btfss	(_isSettingMode/8),(_isSettingMode)&7
	goto	u3611
	goto	u3610
u3611:
	goto	l5932
u3610:
	line	151
	
l5894:	
;main.c: 151: if (is_second_setting) {
	btfss	(_is_second_setting/8),(_is_second_setting)&7
	goto	u3621
	goto	u3620
u3621:
	goto	l5898
u3620:
	line	152
	
l5896:	
;main.c: 152: lcd_move_char(0xd + 0x40);
	movlw	(04Dh)
	fcall	_lcd_move_char
	line	153
;main.c: 153: } else {
	goto	l5932
	line	154
	
l5898:	
;main.c: 154: lcd_move_char(0xe);
	movlw	(0Eh)
	fcall	_lcd_move_char
	goto	l5932
	line	159
	
l5900:	
;main.c: 159: charPointer = state6_1;
	movlw	low(_state6_1|8000h)
	movwf	(_charPointer)
	movlw	high(_state6_1|8000h)
	movwf	(_charPointer+1)
	line	160
	
l5902:	
;main.c: 160: lcd_print_line1(charPointer, 0, 1);
	movf	(_charPointer+1),w
	movwf	(?_lcd_print_line1+1)
	movf	(_charPointer),w
	movwf	(?_lcd_print_line1)
	clrf	0+(?_lcd_print_line1)+02h
	clrf	0+(?_lcd_print_line1)+03h
	incf	0+(?_lcd_print_line1)+03h,f
	fcall	_lcd_print_line1
	line	161
	
l5904:	
;main.c: 161: charPointer = state6_2;
	movlw	low(_state6_2|8000h)
	movwf	(_charPointer)
	movlw	high(_state6_2|8000h)
	movwf	(_charPointer+1)
	line	162
;main.c: 162: lcd_print_line2(charPointer, 0);
	movf	(_charPointer+1),w
	movwf	(?_lcd_print_line2+1)
	movf	(_charPointer),w
	movwf	(?_lcd_print_line2)
	clrf	0+(?_lcd_print_line2)+02h
	fcall	_lcd_print_line2
	line	163
	
l5906:	
;main.c: 163: if (isSettingMode) {
	btfss	(_isSettingMode/8),(_isSettingMode)&7
	goto	u3631
	goto	u3630
u3631:
	goto	l5932
u3630:
	line	164
	
l5908:	
;main.c: 164: if (is_second_setting) {
	btfss	(_is_second_setting/8),(_is_second_setting)&7
	goto	u3641
	goto	u3640
u3641:
	goto	l5912
u3640:
	line	165
	
l5910:	
;main.c: 165: lcd_move_char(0xc + 0x40);
	movlw	(04Ch)
	fcall	_lcd_move_char
	line	166
;main.c: 166: } else {
	goto	l5932
	line	167
	
l5912:	
;main.c: 167: lcd_move_char(0xd);
	movlw	(0Dh)
	fcall	_lcd_move_char
	goto	l5932
	line	172
	
l5914:	
;main.c: 172: charPointer = state7_1;
	movlw	low(_state7_1|8000h)
	movwf	(_charPointer)
	movlw	high(_state7_1|8000h)
	movwf	(_charPointer+1)
	line	173
	
l5916:	
;main.c: 173: lcd_print_line1(charPointer, 0, 1);
	movf	(_charPointer+1),w
	movwf	(?_lcd_print_line1+1)
	movf	(_charPointer),w
	movwf	(?_lcd_print_line1)
	clrf	0+(?_lcd_print_line1)+02h
	clrf	0+(?_lcd_print_line1)+03h
	incf	0+(?_lcd_print_line1)+03h,f
	fcall	_lcd_print_line1
	line	174
	
l5918:	
;main.c: 174: charPointer = state7_2;
	movlw	low(_state7_2|8000h)
	movwf	(_charPointer)
	movlw	high(_state7_2|8000h)
	movwf	(_charPointer+1)
	line	175
;main.c: 175: lcd_print_line2(charPointer, 0);
	movf	(_charPointer+1),w
	movwf	(?_lcd_print_line2+1)
	movf	(_charPointer),w
	movwf	(?_lcd_print_line2)
	clrf	0+(?_lcd_print_line2)+02h
	fcall	_lcd_print_line2
	line	176
	
l5920:	
;main.c: 176: if (isSettingMode) {
	btfss	(_isSettingMode/8),(_isSettingMode)&7
	goto	u3651
	goto	u3650
u3651:
	goto	l5932
u3650:
	line	177
	
l5922:	
;main.c: 177: if (is_second_setting) {
	btfss	(_is_second_setting/8),(_is_second_setting)&7
	goto	u3661
	goto	u3660
u3661:
	goto	l5926
u3660:
	line	178
	
l5924:	
;main.c: 178: lcd_move_char(0xe + 0x40);
	movlw	(04Eh)
	fcall	_lcd_move_char
	line	179
;main.c: 179: } else {
	goto	l5932
	line	180
	
l5926:	
;main.c: 180: lcd_move_char(0xc);
	movlw	(0Ch)
	fcall	_lcd_move_char
	goto	l5932
	line	61
	
l5930:	
	movf	(_lcd_state),w
	; Switch size 1, requested type "space"
; Number of cases is 8, Range of values is 1 to 8
; switch strategies available:
; Name         Bytes Cycles
; simple_byte    25    13 (average)
; direct_byte    35    19 (fixed)
;	Chosen strategy is simple_byte

	xorlw	1^0	; case 1
	skipnz
	goto	l5832
	xorlw	2^1	; case 2
	skipnz
	goto	l5836
	xorlw	3^2	; case 3
	skipnz
	goto	l5842
	xorlw	4^3	; case 4
	skipnz
	goto	l5858
	xorlw	5^4	; case 5
	skipnz
	goto	l5872
	xorlw	6^5	; case 6
	skipnz
	goto	l5886
	xorlw	7^6	; case 7
	skipnz
	goto	l5900
	xorlw	8^7	; case 8
	skipnz
	goto	l5914
	goto	l5932

	line	190
	
l5932:	
;main.c: 188: }
;main.c: 190: if (lcd_extinguwish_timer == 70) {
	movf	(_lcd_extinguwish_timer),w
	xorlw	046h&0ffh
	skipz
	goto	u3671
	goto	u3670
u3671:
	goto	l5938
u3670:
	line	192
	
l5934:	
;main.c: 192: lcd_extinguwish_timer++;
	incf	(_lcd_extinguwish_timer),f
	line	194
	
l5936:	
;main.c: 194: lcd_write_command(0x08);
	movlw	(08h)
	fcall	_lcd_write_command
	line	197
	
l5938:	
;main.c: 195: }
;main.c: 197: if (isSaveSettings) {
	btfss	(_isSaveSettings/8),(_isSaveSettings)&7
	goto	u3681
	goto	u3680
u3681:
	goto	l5946
u3680:
	line	198
	
l5940:	
;main.c: 198: isSaveSettings = 0;
	bcf	(_isSaveSettings/8),(_isSaveSettings)&7
	line	199
;main.c: 199: isNeedChange = 1;
	bsf	(_isNeedChange/8),(_isNeedChange)&7
	line	200
	
l5942:	
;main.c: 200: lcd_state = 2;
	movlw	(02h)
	movwf	(_lcd_state)
	line	201
	
l5944:	
;main.c: 201: isSettingMode = 0;
	bcf	(_isSettingMode/8),(_isSettingMode)&7
	line	205
	
l5946:	
;main.c: 203: }
;main.c: 205: if (isSettingMode && needInitSetting) {
	btfss	(_isSettingMode/8),(_isSettingMode)&7
	goto	u3691
	goto	u3690
u3691:
	goto	l5806
u3690:
	
l5948:	
	btfss	(_needInitSetting/8),(_needInitSetting)&7
	goto	u3701
	goto	u3700
u3701:
	goto	l5806
u3700:
	line	206
	
l5950:	
;main.c: 206: needInitSetting = 0;
	bcf	(_needInitSetting/8),(_needInitSetting)&7
	line	207
;main.c: 207: isNeedChange = 1;
	bsf	(_isNeedChange/8),(_isNeedChange)&7
	line	208
	
l5952:	
;main.c: 208: lcd_state = 3;
	movlw	(03h)
	movwf	(_lcd_state)
	line	209
	
l5954:	
;main.c: 209: is_second_setting = 0;
	bcf	(_is_second_setting/8),(_is_second_setting)&7
	goto	l5806
	global	start
	ljmp	start
	opt stack 0
psect	maintext
	line	213
GLOBAL	__end_of_main
	__end_of_main:
;; =============== function _main ends ============

	signat	_main,88
	global	_KaiJi
psect	text585,local,class=CODE,delta=2
global __ptext585
__ptext585:

;; *************** function _KaiJi *****************
;; Defined at:
;;		line 998 in file "C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.h"
;; Parameters:    Size  Location     Type
;;		None
;; Auto vars:     Size  Location     Type
;;		None
;; Return value:  Size  Location     Type
;;		None               void
;; Registers used:
;;		wreg, status,2, status,0, pclath, cstack
;; Tracked objects:
;;		On entry : 17F/2
;;		On exit  : 17F/0
;;		Unchanged: FFE80/0
;; Data sizes:     COMMON   BANK0   BANK1   BANK2
;;      Params:         0       0       0       0
;;      Locals:         0       0       0       0
;;      Temps:          0       2       0       0
;;      Totals:         0       2       0       0
;;Total ram usage:        2 bytes
;; Hardware stack levels used:    1
;; Hardware stack levels required when called:    5
;; This function calls:
;;		_GetBatteryVoltage
;;		_GetSolarPanelVoltage
;;		___wmul
;;		_GetLoadCurrentVoltage
;; This function is called by:
;;		_main
;; This function uses a non-reentrant model
;;
psect	text585
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.h"
	line	998
	global	__size_of_KaiJi
	__size_of_KaiJi	equ	__end_of_KaiJi-_KaiJi
	
_KaiJi:	
	opt	stack 10
; Regs used in _KaiJi: [wreg+status,2+status,0+pclath+cstack]
	line	1002
	
l5764:	
;mypic.h: 1002: gBatteryVoltage = GetBatteryVoltage();
	fcall	_GetBatteryVoltage
	movf	(1+(?_GetBatteryVoltage)),w
	movwf	(_gBatteryVoltage+1)
	movf	(0+(?_GetBatteryVoltage)),w
	movwf	(_gBatteryVoltage)
	line	1003
	
l5766:	
;mypic.h: 1003: if(gBatteryVoltage < 391)
	movlw	high(0187h)
	subwf	(_gBatteryVoltage+1),w
	movlw	low(0187h)
	skipnz
	subwf	(_gBatteryVoltage),w
	skipnc
	goto	u3481
	goto	u3480
u3481:
	goto	l5770
u3480:
	line	1005
	
l5768:	
;mypic.h: 1007: System24V = 0;
	movlw	(_Battery_12V)&0ffh
	movwf	(_BatteryStandard)
	line	1008
;mypic.h: 1008: }
	goto	l5776
	line	1009
	
l5770:	
;mypic.h: 1009: else if((gBatteryVoltage < 782) && (gBatteryVoltage > 512))
	movlw	high(030Eh)
	subwf	(_gBatteryVoltage+1),w
	movlw	low(030Eh)
	skipnz
	subwf	(_gBatteryVoltage),w
	skipnc
	goto	u3491
	goto	u3490
u3491:
	goto	l5776
u3490:
	
l5772:	
	movlw	high(0201h)
	subwf	(_gBatteryVoltage+1),w
	movlw	low(0201h)
	skipnz
	subwf	(_gBatteryVoltage),w
	skipc
	goto	u3501
	goto	u3500
u3501:
	goto	l5776
u3500:
	line	1011
	
l5774:	
;mypic.h: 1013: System24V = 1;
	movlw	(_Battery_24V)&0ffh
	movwf	(_BatteryStandard)
	line	1025
;mypic.h: 1014: }
	
l5776:	
;mypic.h: 1021: }
;mypic.h: 1025: SDSolarPanelVoltage = GetSolarPanelVoltage();
	fcall	_GetSolarPanelVoltage
	movf	(1+(?_GetSolarPanelVoltage)),w
	movwf	(_SDSolarPanelVoltage+1)
	movf	(0+(?_GetSolarPanelVoltage)),w
	movwf	(_SDSolarPanelVoltage)
	line	1026
;mypic.h: 1026: SDBatteryVoltage = GetBatteryVoltage();
	fcall	_GetBatteryVoltage
	movf	(1+(?_GetBatteryVoltage)),w
	movwf	(_SDBatteryVoltage+1)
	movf	(0+(?_GetBatteryVoltage)),w
	movwf	(_SDBatteryVoltage)
	line	1028
;mypic.h: 1027: if((SDSolarPanelVoltage*47) < ((SDBatteryVoltage*48) - 2400)
;mypic.h: 1028: || (SDSolarPanelVoltage*47) > ((SDBatteryVoltage*48) + 2400))
	movf	(_SDBatteryVoltage+1),w
	movwf	(?___wmul+1)
	movf	(_SDBatteryVoltage),w
	movwf	(?___wmul)
	movlw	030h
	movwf	0+(?___wmul)+02h
	clrf	1+(?___wmul)+02h
	fcall	___wmul
	movf	(0+(?___wmul)),w
	addlw	low(0F6A0h)
	movwf	(??_KaiJi+0)+0
	movlw	high(0F6A0h)
	addwfc	(1+(?___wmul)),w
	movwf	1+(??_KaiJi+0)+0
	movf	(_SDSolarPanelVoltage+1),w
	movwf	(?___wmul+1)
	movf	(_SDSolarPanelVoltage),w
	movwf	(?___wmul)
	movlw	02Fh
	movwf	0+(?___wmul)+02h
	clrf	1+(?___wmul)+02h
	fcall	___wmul
	movf	1+(??_KaiJi+0)+0,w
	subwf	(1+(?___wmul)),w
	skipz
	goto	u3515
	movf	0+(??_KaiJi+0)+0,w
	subwf	(0+(?___wmul)),w
u3515:
	skipc
	goto	u3511
	goto	u3510
u3511:
	goto	l5780
u3510:
	
l5778:	
	movf	(_SDBatteryVoltage+1),w
	movwf	(?___wmul+1)
	movf	(_SDBatteryVoltage),w
	movwf	(?___wmul)
	movlw	030h
	movwf	0+(?___wmul)+02h
	clrf	1+(?___wmul)+02h
	fcall	___wmul
	movf	(_SDSolarPanelVoltage+1),w
	movwf	(?___wmul+1)
	movf	(_SDSolarPanelVoltage),w
	movwf	(?___wmul)
	movlw	02Fh
	movwf	0+(?___wmul)+02h
	clrf	1+(?___wmul)+02h
	fcall	___wmul
	line	1035
	
l5780:	
;mypic.h: 1034: }
;mypic.h: 1035: gFbVoltage = GetLoadCurrentVoltage();
	fcall	_GetLoadCurrentVoltage
	movf	(1+(?_GetLoadCurrentVoltage)),w
	movwf	(_gFbVoltage+1)
	movf	(0+(?_GetLoadCurrentVoltage)),w
	movwf	(_gFbVoltage)
	line	1043
	
l5784:	
;mypic.h: 1042: }
;mypic.h: 1043: ADVoltage = 0;
	clrf	(_ADVoltage)
	clrf	(_ADVoltage+1)
	line	1044
;mypic.h: 1044: ADBase = 0;
	clrf	(_ADBase)
	clrf	(_ADBase+1)
	line	1045
;mypic.h: 1045: PVCount = 0;
	clrf	(_PVCount)
	line	1046
	
l2423:	
	return
	opt stack 0
GLOBAL	__end_of_KaiJi
	__end_of_KaiJi:
;; =============== function _KaiJi ends ============

	signat	_KaiJi,88
	global	_SwitchBatteryState
psect	text586,local,class=CODE,delta=2
global __ptext586
__ptext586:

;; *************** function _SwitchBatteryState *****************
;; Defined at:
;;		line 734 in file "C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.h"
;; Parameters:    Size  Location     Type
;;		None
;; Auto vars:     Size  Location     Type
;;		None
;; Return value:  Size  Location     Type
;;		None               void
;; Registers used:
;;		wreg, fsr0l, fsr0h, fsr1l, fsr1h, status,2, status,0, pclath, cstack
;; Tracked objects:
;;		On entry : 1F/0
;;		On exit  : 1F/0
;;		Unchanged: FFFE0/0
;; Data sizes:     COMMON   BANK0   BANK1   BANK2
;;      Params:         0       0       0       0
;;      Locals:         0       0       0       0
;;      Temps:          0       6       0       0
;;      Totals:         0       6       0       0
;;Total ram usage:        6 bytes
;; Hardware stack levels used:    1
;; Hardware stack levels required when called:    5
;; This function calls:
;;		_GetBatteryVoltage
;;		_BatteryStateSwitch
;;		_lcd_move_char
;;		_lcd_write_data
;;		_Delay
;; This function is called by:
;;		_main
;; This function uses a non-reentrant model
;;
psect	text586
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.h"
	line	734
	global	__size_of_SwitchBatteryState
	__size_of_SwitchBatteryState	equ	__end_of_SwitchBatteryState-_SwitchBatteryState
	
_SwitchBatteryState:	
	opt	stack 10
; Regs used in _SwitchBatteryState: [wreg-status,0+pclath+cstack]
	line	735
	
l5486:	
;mypic.h: 735: switch(BatteryState)
	goto	l5762
	line	739
	
l5488:	
;mypic.h: 738: {
;mypic.h: 739: lcd_state = 0;
	clrf	(_lcd_state)
	line	740
	
l5490:	
;mypic.h: 740: gBatteryVoltage = GetBatteryVoltage();
	fcall	_GetBatteryVoltage
	movf	(1+(?_GetBatteryVoltage)),w
	movwf	(_gBatteryVoltage+1)
	movf	(0+(?_GetBatteryVoltage)),w
	movwf	(_gBatteryVoltage)
	line	741
	
l5492:	
;mypic.h: 741: BatteryState = BatteryStateSwitch(gBatteryVoltage);
	movf	(_gBatteryVoltage+1),w
	movwf	(?_BatteryStateSwitch+1)
	movf	(_gBatteryVoltage),w
	movwf	(?_BatteryStateSwitch)
	fcall	_BatteryStateSwitch
	movwf	(_BatteryState)
	line	742
	
l5494:	
;mypic.h: 742: EnhanceChargeFlag = 0;
	bcf	(_EnhanceChargeFlag/8),(_EnhanceChargeFlag)&7
	line	743
	
l5496:	
;mypic.h: 743: EnhanceCharge_Time = 0;
	clrf	(_EnhanceCharge_Time)
	clrf	(_EnhanceCharge_Time+1)
	line	744
	
l5498:	
;mypic.h: 744: EqualizingChargeFlag = 0;
	bcf	(_EqualizingChargeFlag/8),(_EqualizingChargeFlag)&7
	line	745
	
l5500:	
;mypic.h: 745: EqualizingCharge_Time = 0;
	clrf	(_EqualizingCharge_Time)
	clrf	(_EqualizingCharge_Time+1)
	line	746
	
l5502:	
;mypic.h: 746: FloatingChargeFlag = 0;
	bcf	(_FloatingChargeFlag/8),(_FloatingChargeFlag)&7
	line	747
	
l5504:	
;mypic.h: 747: BatteryLastState = 0;
	clrf	(_BatteryLastState)
	line	748
;mypic.h: 748: break;
	goto	l2412
	line	753
	
l5506:	
;mypic.h: 751: {
;mypic.h: 753: if (lcd_state == 2) {
	movf	(_lcd_state),w
	xorlw	02h&0ffh
	skipz
	goto	u3221
	goto	u3220
u3221:
	goto	l5510
u3220:
	line	755
	
l5508:	
;mypic.h: 755: lcd_move_char(11);
	movlw	(0Bh)
	fcall	_lcd_move_char
	line	756
;mypic.h: 756: lcd_write_data(0xff);
	movlw	(0FFh)
	fcall	_lcd_write_data
	line	759
	
l5510:	
;mypic.h: 758: }
;mypic.h: 759: if(BatteryLastState != 1)
	decf	(_BatteryLastState),w
	skipnz
	goto	u3231
	goto	u3230
u3231:
	goto	l5522
u3230:
	line	762
	
l5512:	
;mypic.h: 760: {
;mypic.h: 762: EnhanceChargeFlag = 0;
	bcf	(_EnhanceChargeFlag/8),(_EnhanceChargeFlag)&7
	line	763
	
l5514:	
;mypic.h: 763: EnhanceCharge_Time = 0;
	clrf	(_EnhanceCharge_Time)
	clrf	(_EnhanceCharge_Time+1)
	line	764
	
l5516:	
;mypic.h: 764: EqualizingChargeFlag = 0;
	bcf	(_EqualizingChargeFlag/8),(_EqualizingChargeFlag)&7
	line	765
;mypic.h: 765: EqualizingCharge_Time = 0;
	clrf	(_EqualizingCharge_Time)
	clrf	(_EqualizingCharge_Time+1)
	line	766
	
l5518:	
;mypic.h: 766: FloatingChargeFlag = 0;
	bcf	(_FloatingChargeFlag/8),(_FloatingChargeFlag)&7
	line	767
	
l5520:	
;mypic.h: 767: BatteryLastState = 1;
	clrf	(_BatteryLastState)
	incf	(_BatteryLastState),f
	line	769
	
l5522:	
;mypic.h: 768: }
;mypic.h: 769: if(PVState == 1)
	btfss	(_PVState/8),(_PVState)&7
	goto	u3241
	goto	u3240
u3241:
	goto	l2377
u3240:
	line	771
	
l5524:	
;mypic.h: 770: {
;mypic.h: 771: ClampVoltage = *(BatteryStandard + 6) - TemBase + TemVoltage - ADBase + ADVoltage;
	movf	(_BatteryStandard),w
	addlw	0Ch
	movwf	fsr1l
	clrf fsr1h	
	
	moviw	[0]fsr1
	movwf	(_ClampVoltage)
	moviw	[1]fsr1
	movwf	(_ClampVoltage+1)
	
l5526:	
	movf	(_ADBase),w
	subwf	(_ClampVoltage),f
	movf	(_ADBase+1),w
	subwfb	(_ClampVoltage+1),f
	
l5528:	
	movf	(_ADVoltage),w
	addwf	(_ClampVoltage),f
	movf	(_ADVoltage+1),w
	addwfc	(_ClampVoltage+1),f
	line	772
	
l5530:	
;mypic.h: 772: if(EnhanceChargeFlag == 0)
	btfsc	(_EnhanceChargeFlag/8),(_EnhanceChargeFlag)&7
	goto	u3251
	goto	u3250
u3251:
	goto	l5544
u3250:
	line	774
	
l5532:	
;mypic.h: 773: {
;mypic.h: 774: EnhanceChargeFlag = 1;
	bsf	(_EnhanceChargeFlag/8),(_EnhanceChargeFlag)&7
	line	775
	
l5534:	
;mypic.h: 775: EnhanceCharge_Time = 0;
	clrf	(_EnhanceCharge_Time)
	clrf	(_EnhanceCharge_Time+1)
	goto	l5544
	line	778
	
l2377:	
	line	780
;mypic.h: 778: else
;mypic.h: 779: {
;mypic.h: 780: EnhanceChargeFlag = 0;
	bcf	(_EnhanceChargeFlag/8),(_EnhanceChargeFlag)&7
	line	781
	
l5536:	
;mypic.h: 781: EnhanceCharge_Time = 0;
	clrf	(_EnhanceCharge_Time)
	clrf	(_EnhanceCharge_Time+1)
	line	782
	
l5538:	
;mypic.h: 782: gBatteryVoltage = GetBatteryVoltage();
	fcall	_GetBatteryVoltage
	movf	(1+(?_GetBatteryVoltage)),w
	movwf	(_gBatteryVoltage+1)
	movf	(0+(?_GetBatteryVoltage)),w
	movwf	(_gBatteryVoltage)
	line	783
	
l5540:	
;mypic.h: 783: if(gBatteryVoltage > *(BatteryStandard + 1) - TemBase + TemVoltage - ADBase + ADVoltage)
	movf	(_BatteryStandard),w
	addlw	02h
	movwf	fsr1l
	clrf fsr1h	
	
	moviw	[0]fsr1
	movwf	(??_SwitchBatteryState+0)+0
	moviw	[1]fsr1
	movwf	(??_SwitchBatteryState+0)+0+1
	movf	(_ADBase),w
	subwf	0+(??_SwitchBatteryState+0)+0,w
	movwf	(??_SwitchBatteryState+2)+0
	movf	(_ADBase+1),w
	subwfb	1+(??_SwitchBatteryState+0)+0,w
	movwf	1+(??_SwitchBatteryState+2)+0
	movf	(_ADVoltage),w
	addwf	0+(??_SwitchBatteryState+2)+0,w
	movwf	(??_SwitchBatteryState+4)+0
	movf	(_ADVoltage+1),w
	addwfc	1+(??_SwitchBatteryState+2)+0,w
	movwf	1+(??_SwitchBatteryState+4)+0
	movf	(_gBatteryVoltage+1),w
	subwf	1+(??_SwitchBatteryState+4)+0,w
	skipz
	goto	u3265
	movf	(_gBatteryVoltage),w
	subwf	0+(??_SwitchBatteryState+4)+0,w
u3265:
	skipnc
	goto	u3261
	goto	u3260
u3261:
	goto	l5544
u3260:
	line	785
	
l5542:	
;mypic.h: 784: {
;mypic.h: 785: BatteryState = BatteryStateSwitch(gBatteryVoltage);
	movf	(_gBatteryVoltage+1),w
	movwf	(?_BatteryStateSwitch+1)
	movf	(_gBatteryVoltage),w
	movwf	(?_BatteryStateSwitch)
	fcall	_BatteryStateSwitch
	movwf	(_BatteryState)
	line	789
	
l5544:	
;mypic.h: 786: }
;mypic.h: 787: }
;mypic.h: 789: if(EnhanceCharge_Time >= 36000)
	movlw	high(08CA0h)
	subwf	(_EnhanceCharge_Time+1),w
	movlw	low(08CA0h)
	skipnz
	subwf	(_EnhanceCharge_Time),w
	skipc
	goto	u3271
	goto	u3270
u3271:
	goto	l2412
u3270:
	line	791
	
l5546:	
;mypic.h: 790: {
;mypic.h: 791: EnhanceChargeFlag = 0;
	bcf	(_EnhanceChargeFlag/8),(_EnhanceChargeFlag)&7
	line	792
	
l5548:	
;mypic.h: 792: EnhanceCharge_Time = 0;
	clrf	(_EnhanceCharge_Time)
	clrf	(_EnhanceCharge_Time+1)
	line	793
	
l5550:	
;mypic.h: 793: Delay(100);
	movlw	064h
	movwf	(?_Delay)
	clrf	(?_Delay+1)
	fcall	_Delay
	line	794
	
l5552:	
;mypic.h: 794: gBatteryVoltage = GetBatteryVoltage();
	fcall	_GetBatteryVoltage
	movf	(1+(?_GetBatteryVoltage)),w
	movwf	(_gBatteryVoltage+1)
	movf	(0+(?_GetBatteryVoltage)),w
	movwf	(_gBatteryVoltage)
	line	795
	
l5554:	
;mypic.h: 795: if(gBatteryVoltage > *(BatteryStandard + 1) - TemBase + TemVoltage - ADBase + ADVoltage)
	movf	(_BatteryStandard),w
	addlw	02h
	movwf	fsr1l
	clrf fsr1h	
	
	moviw	[0]fsr1
	movwf	(??_SwitchBatteryState+0)+0
	moviw	[1]fsr1
	movwf	(??_SwitchBatteryState+0)+0+1
	movf	(_ADBase),w
	subwf	0+(??_SwitchBatteryState+0)+0,w
	movwf	(??_SwitchBatteryState+2)+0
	movf	(_ADBase+1),w
	subwfb	1+(??_SwitchBatteryState+0)+0,w
	movwf	1+(??_SwitchBatteryState+2)+0
	movf	(_ADVoltage),w
	addwf	0+(??_SwitchBatteryState+2)+0,w
	movwf	(??_SwitchBatteryState+4)+0
	movf	(_ADVoltage+1),w
	addwfc	1+(??_SwitchBatteryState+2)+0,w
	movwf	1+(??_SwitchBatteryState+4)+0
	movf	(_gBatteryVoltage+1),w
	subwf	1+(??_SwitchBatteryState+4)+0,w
	skipz
	goto	u3285
	movf	(_gBatteryVoltage),w
	subwf	0+(??_SwitchBatteryState+4)+0,w
u3285:
	skipnc
	goto	u3281
	goto	u3280
u3281:
	goto	l2412
u3280:
	line	797
	
l5556:	
;mypic.h: 796: {
;mypic.h: 797: BatteryState = BatteryStateSwitch(gBatteryVoltage);
	movf	(_gBatteryVoltage+1),w
	movwf	(?_BatteryStateSwitch+1)
	movf	(_gBatteryVoltage),w
	movwf	(?_BatteryStateSwitch)
	fcall	_BatteryStateSwitch
	movwf	(_BatteryState)
	goto	l2412
	line	805
	
l5558:	
;mypic.h: 803: {
;mypic.h: 805: if(BatteryLastState != 2)
	movf	(_BatteryLastState),w
	xorlw	02h&0ffh
	skipnz
	goto	u3291
	goto	u3290
u3291:
	goto	l5570
u3290:
	line	808
	
l5560:	
;mypic.h: 806: {
;mypic.h: 808: EnhanceChargeFlag = 0;
	bcf	(_EnhanceChargeFlag/8),(_EnhanceChargeFlag)&7
	line	809
	
l5562:	
;mypic.h: 809: EnhanceCharge_Time = 0;
	clrf	(_EnhanceCharge_Time)
	clrf	(_EnhanceCharge_Time+1)
	line	810
	
l5564:	
;mypic.h: 810: EqualizingChargeFlag = 0;
	bcf	(_EqualizingChargeFlag/8),(_EqualizingChargeFlag)&7
	line	811
;mypic.h: 811: EqualizingCharge_Time = 0;
	clrf	(_EqualizingCharge_Time)
	clrf	(_EqualizingCharge_Time+1)
	line	812
	
l5566:	
;mypic.h: 812: FloatingChargeFlag = 0;
	bcf	(_FloatingChargeFlag/8),(_FloatingChargeFlag)&7
	line	813
	
l5568:	
;mypic.h: 813: BatteryLastState = 2;
	movlw	(02h)
	movwf	(_BatteryLastState)
	line	815
	
l5570:	
;mypic.h: 814: }
;mypic.h: 815: if(PVState == 1)
	btfss	(_PVState/8),(_PVState)&7
	goto	u3301
	goto	u3300
u3301:
	goto	l2385
u3300:
	line	817
	
l5572:	
;mypic.h: 816: {
;mypic.h: 817: ClampVoltage = *(BatteryStandard + 7) - TemBase + TemVoltage - ADBase + ADVoltage;
	movf	(_BatteryStandard),w
	addlw	0Eh
	movwf	fsr1l
	clrf fsr1h	
	
	moviw	[0]fsr1
	movwf	(_ClampVoltage)
	moviw	[1]fsr1
	movwf	(_ClampVoltage+1)
	
l5574:	
	movf	(_ADBase),w
	subwf	(_ClampVoltage),f
	movf	(_ADBase+1),w
	subwfb	(_ClampVoltage+1),f
	
l5576:	
	movf	(_ADVoltage),w
	addwf	(_ClampVoltage),f
	movf	(_ADVoltage+1),w
	addwfc	(_ClampVoltage+1),f
	line	818
	
l5578:	
;mypic.h: 818: if(EqualizingChargeFlag == 0)
	btfsc	(_EqualizingChargeFlag/8),(_EqualizingChargeFlag)&7
	goto	u3311
	goto	u3310
u3311:
	goto	l5584
u3310:
	line	820
	
l5580:	
;mypic.h: 819: {
;mypic.h: 820: EqualizingChargeFlag = 1;
	bsf	(_EqualizingChargeFlag/8),(_EqualizingChargeFlag)&7
	line	821
	
l5582:	
;mypic.h: 821: EqualizingCharge_Time = 0;
	clrf	(_EqualizingCharge_Time)
	clrf	(_EqualizingCharge_Time+1)
	line	823
	
l5584:	
;mypic.h: 822: }
;mypic.h: 823: gBatteryVoltage = GetBatteryVoltage();
	fcall	_GetBatteryVoltage
	movf	(1+(?_GetBatteryVoltage)),w
	movwf	(_gBatteryVoltage+1)
	movf	(0+(?_GetBatteryVoltage)),w
	movwf	(_gBatteryVoltage)
	line	824
	
l5586:	
;mypic.h: 824: if(gBatteryVoltage < *(BatteryStandard + 0) - TemBase + TemVoltage - ADBase + ADVoltage)
	movf	(_BatteryStandard),w
	movwf	fsr1l
	clrf fsr1h	
	
	moviw	[0]fsr1
	movwf	(??_SwitchBatteryState+0)+0
	moviw	[1]fsr1
	movwf	(??_SwitchBatteryState+0)+0+1
	movf	(_ADBase),w
	subwf	0+(??_SwitchBatteryState+0)+0,w
	movwf	(??_SwitchBatteryState+2)+0
	movf	(_ADBase+1),w
	subwfb	1+(??_SwitchBatteryState+0)+0,w
	movwf	1+(??_SwitchBatteryState+2)+0
	movf	(_ADVoltage),w
	addwf	0+(??_SwitchBatteryState+2)+0,w
	movwf	(??_SwitchBatteryState+4)+0
	movf	(_ADVoltage+1),w
	addwfc	1+(??_SwitchBatteryState+2)+0,w
	movwf	1+(??_SwitchBatteryState+4)+0
	movf	1+(??_SwitchBatteryState+4)+0,w
	subwf	(_gBatteryVoltage+1),w
	skipz
	goto	u3325
	movf	0+(??_SwitchBatteryState+4)+0,w
	subwf	(_gBatteryVoltage),w
u3325:
	skipnc
	goto	u3321
	goto	u3320
u3321:
	goto	l5596
u3320:
	line	826
	
l5588:	
;mypic.h: 825: {
;mypic.h: 826: BatteryState = 1;
	clrf	(_BatteryState)
	incf	(_BatteryState),f
	goto	l5596
	line	829
	
l2385:	
	line	831
;mypic.h: 829: else
;mypic.h: 830: {
;mypic.h: 831: EqualizingChargeFlag = 0;
	bcf	(_EqualizingChargeFlag/8),(_EqualizingChargeFlag)&7
	line	832
	
l5590:	
;mypic.h: 832: EqualizingCharge_Time = 0;
	clrf	(_EqualizingCharge_Time)
	clrf	(_EqualizingCharge_Time+1)
	line	833
	
l5592:	
;mypic.h: 833: gBatteryVoltage = GetBatteryVoltage();
	fcall	_GetBatteryVoltage
	movf	(1+(?_GetBatteryVoltage)),w
	movwf	(_gBatteryVoltage+1)
	movf	(0+(?_GetBatteryVoltage)),w
	movwf	(_gBatteryVoltage)
	line	834
	
l5594:	
;mypic.h: 834: BatteryState = BatteryStateSwitch(gBatteryVoltage);
	movf	(_gBatteryVoltage+1),w
	movwf	(?_BatteryStateSwitch+1)
	movf	(_gBatteryVoltage),w
	movwf	(?_BatteryStateSwitch)
	fcall	_BatteryStateSwitch
	movwf	(_BatteryState)
	line	837
	
l5596:	
;mypic.h: 835: }
;mypic.h: 837: if(EqualizingCharge_Time >= 36000)
	movlw	high(08CA0h)
	subwf	(_EqualizingCharge_Time+1),w
	movlw	low(08CA0h)
	skipnz
	subwf	(_EqualizingCharge_Time),w
	skipc
	goto	u3331
	goto	u3330
u3331:
	goto	l2412
u3330:
	line	839
	
l5598:	
;mypic.h: 838: {
;mypic.h: 839: EqualizingChargeFlag = 0;
	bcf	(_EqualizingChargeFlag/8),(_EqualizingChargeFlag)&7
	line	840
	
l5600:	
;mypic.h: 840: EqualizingCharge_Time = 0;
	clrf	(_EqualizingCharge_Time)
	clrf	(_EqualizingCharge_Time+1)
	line	841
	
l5602:	
;mypic.h: 841: Delay(100);
	movlw	064h
	movwf	(?_Delay)
	clrf	(?_Delay+1)
	fcall	_Delay
	line	842
	
l5604:	
;mypic.h: 842: gBatteryVoltage = GetBatteryVoltage();
	fcall	_GetBatteryVoltage
	movf	(1+(?_GetBatteryVoltage)),w
	movwf	(_gBatteryVoltage+1)
	movf	(0+(?_GetBatteryVoltage)),w
	movwf	(_gBatteryVoltage)
	goto	l5556
	line	850
	
l5608:	
;mypic.h: 849: {
;mypic.h: 850: if(BatteryLastState != 3)
	movf	(_BatteryLastState),w
	xorlw	03h&0ffh
	skipnz
	goto	u3341
	goto	u3340
u3341:
	goto	l5620
u3340:
	line	853
	
l5610:	
;mypic.h: 851: {
;mypic.h: 853: EnhanceChargeFlag = 0;
	bcf	(_EnhanceChargeFlag/8),(_EnhanceChargeFlag)&7
	line	854
	
l5612:	
;mypic.h: 854: EnhanceCharge_Time = 0;
	clrf	(_EnhanceCharge_Time)
	clrf	(_EnhanceCharge_Time+1)
	line	855
	
l5614:	
;mypic.h: 855: EqualizingChargeFlag = 0;
	bcf	(_EqualizingChargeFlag/8),(_EqualizingChargeFlag)&7
	line	856
;mypic.h: 856: EqualizingCharge_Time = 0;
	clrf	(_EqualizingCharge_Time)
	clrf	(_EqualizingCharge_Time+1)
	line	857
	
l5616:	
;mypic.h: 857: FloatingChargeFlag = 0;
	bcf	(_FloatingChargeFlag/8),(_FloatingChargeFlag)&7
	line	858
	
l5618:	
;mypic.h: 858: BatteryLastState = 3;
	movlw	(03h)
	movwf	(_BatteryLastState)
	line	861
	
l5620:	
;mypic.h: 860: }
;mypic.h: 861: if(PVState == 1)
	btfss	(_PVState/8),(_PVState)&7
	goto	u3351
	goto	u3350
u3351:
	goto	l2392
u3350:
	line	863
	
l5622:	
;mypic.h: 862: {
;mypic.h: 863: ClampVoltage = *(BatteryStandard + 7) - TemBase + TemVoltage - ADBase + ADVoltage;
	movf	(_BatteryStandard),w
	addlw	0Eh
	movwf	fsr1l
	clrf fsr1h	
	
	moviw	[0]fsr1
	movwf	(_ClampVoltage)
	moviw	[1]fsr1
	movwf	(_ClampVoltage+1)
	
l5624:	
	movf	(_ADBase),w
	subwf	(_ClampVoltage),f
	movf	(_ADBase+1),w
	subwfb	(_ClampVoltage+1),f
	
l5626:	
	movf	(_ADVoltage),w
	addwf	(_ClampVoltage),f
	movf	(_ADVoltage+1),w
	addwfc	(_ClampVoltage+1),f
	line	864
	
l5628:	
;mypic.h: 864: if(EqualizingChargeFlag == 0)
	btfsc	(_EqualizingChargeFlag/8),(_EqualizingChargeFlag)&7
	goto	u3361
	goto	u3360
u3361:
	goto	l5634
u3360:
	line	866
	
l5630:	
;mypic.h: 865: {
;mypic.h: 866: EqualizingChargeFlag = 1;
	bsf	(_EqualizingChargeFlag/8),(_EqualizingChargeFlag)&7
	line	867
	
l5632:	
;mypic.h: 867: EqualizingCharge_Time = 0;
	clrf	(_EqualizingCharge_Time)
	clrf	(_EqualizingCharge_Time+1)
	line	869
	
l5634:	
;mypic.h: 868: }
;mypic.h: 869: gBatteryVoltage = GetBatteryVoltage();
	fcall	_GetBatteryVoltage
	movf	(1+(?_GetBatteryVoltage)),w
	movwf	(_gBatteryVoltage+1)
	movf	(0+(?_GetBatteryVoltage)),w
	movwf	(_gBatteryVoltage)
	line	870
	
l5636:	
;mypic.h: 870: if(gBatteryVoltage < *(BatteryStandard + 1) - TemBase + TemVoltage - ADBase + ADVoltage)
	movf	(_BatteryStandard),w
	addlw	02h
	movwf	fsr1l
	clrf fsr1h	
	
	moviw	[0]fsr1
	movwf	(??_SwitchBatteryState+0)+0
	moviw	[1]fsr1
	movwf	(??_SwitchBatteryState+0)+0+1
	movf	(_ADBase),w
	subwf	0+(??_SwitchBatteryState+0)+0,w
	movwf	(??_SwitchBatteryState+2)+0
	movf	(_ADBase+1),w
	subwfb	1+(??_SwitchBatteryState+0)+0,w
	movwf	1+(??_SwitchBatteryState+2)+0
	movf	(_ADVoltage),w
	addwf	0+(??_SwitchBatteryState+2)+0,w
	movwf	(??_SwitchBatteryState+4)+0
	movf	(_ADVoltage+1),w
	addwfc	1+(??_SwitchBatteryState+2)+0,w
	movwf	1+(??_SwitchBatteryState+4)+0
	movf	1+(??_SwitchBatteryState+4)+0,w
	subwf	(_gBatteryVoltage+1),w
	skipz
	goto	u3375
	movf	0+(??_SwitchBatteryState+4)+0,w
	subwf	(_gBatteryVoltage),w
u3375:
	skipnc
	goto	u3371
	goto	u3370
u3371:
	goto	l5646
u3370:
	line	872
	
l5638:	
;mypic.h: 871: {
;mypic.h: 872: BatteryState = BatteryStateSwitch(gBatteryVoltage);
	movf	(_gBatteryVoltage+1),w
	movwf	(?_BatteryStateSwitch+1)
	movf	(_gBatteryVoltage),w
	movwf	(?_BatteryStateSwitch)
	fcall	_BatteryStateSwitch
	movwf	(_BatteryState)
	goto	l5646
	line	875
	
l2392:	
	line	877
;mypic.h: 875: else
;mypic.h: 876: {
;mypic.h: 877: EqualizingChargeFlag = 0;
	bcf	(_EqualizingChargeFlag/8),(_EqualizingChargeFlag)&7
	line	878
	
l5640:	
;mypic.h: 878: EqualizingCharge_Time = 0;
	clrf	(_EqualizingCharge_Time)
	clrf	(_EqualizingCharge_Time+1)
	line	879
	
l5642:	
;mypic.h: 879: gBatteryVoltage = GetBatteryVoltage();
	fcall	_GetBatteryVoltage
	movf	(1+(?_GetBatteryVoltage)),w
	movwf	(_gBatteryVoltage+1)
	movf	(0+(?_GetBatteryVoltage)),w
	movwf	(_gBatteryVoltage)
	goto	l5638
	line	883
	
l5646:	
;mypic.h: 881: }
;mypic.h: 883: if(EqualizingCharge_Time >= 36000)
	movlw	high(08CA0h)
	subwf	(_EqualizingCharge_Time+1),w
	movlw	low(08CA0h)
	skipnz
	subwf	(_EqualizingCharge_Time),w
	skipc
	goto	u3381
	goto	u3380
u3381:
	goto	l2412
u3380:
	goto	l5598
	line	896
	
l5658:	
;mypic.h: 895: {
;mypic.h: 896: if(BatteryLastState != 4)
	movf	(_BatteryLastState),w
	xorlw	04h&0ffh
	skipnz
	goto	u3391
	goto	u3390
u3391:
	goto	l5670
u3390:
	line	899
	
l5660:	
;mypic.h: 897: {
;mypic.h: 899: EnhanceChargeFlag = 0;
	bcf	(_EnhanceChargeFlag/8),(_EnhanceChargeFlag)&7
	line	900
	
l5662:	
;mypic.h: 900: EnhanceCharge_Time = 0;
	clrf	(_EnhanceCharge_Time)
	clrf	(_EnhanceCharge_Time+1)
	line	901
	
l5664:	
;mypic.h: 901: EqualizingChargeFlag = 0;
	bcf	(_EqualizingChargeFlag/8),(_EqualizingChargeFlag)&7
	line	902
;mypic.h: 902: EqualizingCharge_Time = 0;
	clrf	(_EqualizingCharge_Time)
	clrf	(_EqualizingCharge_Time+1)
	line	903
	
l5666:	
;mypic.h: 903: FloatingChargeFlag = 0;
	bcf	(_FloatingChargeFlag/8),(_FloatingChargeFlag)&7
	line	904
	
l5668:	
;mypic.h: 904: BatteryLastState = 4;
	movlw	(04h)
	movwf	(_BatteryLastState)
	line	907
	
l5670:	
;mypic.h: 906: }
;mypic.h: 907: if(PVState == 1)
	btfss	(_PVState/8),(_PVState)&7
	goto	u3401
	goto	u3400
u3401:
	goto	l2399
u3400:
	line	909
	
l5672:	
;mypic.h: 908: {
;mypic.h: 909: ClampVoltage = *(BatteryStandard + 7) - TemBase + TemVoltage - ADBase + ADVoltage;
	movf	(_BatteryStandard),w
	addlw	0Eh
	movwf	fsr1l
	clrf fsr1h	
	
	moviw	[0]fsr1
	movwf	(_ClampVoltage)
	moviw	[1]fsr1
	movwf	(_ClampVoltage+1)
	
l5674:	
	movf	(_ADBase),w
	subwf	(_ClampVoltage),f
	movf	(_ADBase+1),w
	subwfb	(_ClampVoltage+1),f
	
l5676:	
	movf	(_ADVoltage),w
	addwf	(_ClampVoltage),f
	movf	(_ADVoltage+1),w
	addwfc	(_ClampVoltage+1),f
	line	910
	
l5678:	
;mypic.h: 910: if(EqualizingChargeFlag == 0)
	btfsc	(_EqualizingChargeFlag/8),(_EqualizingChargeFlag)&7
	goto	u3411
	goto	u3410
u3411:
	goto	l5684
u3410:
	line	912
	
l5680:	
;mypic.h: 911: {
;mypic.h: 912: EqualizingChargeFlag = 1;
	bsf	(_EqualizingChargeFlag/8),(_EqualizingChargeFlag)&7
	line	913
	
l5682:	
;mypic.h: 913: EqualizingCharge_Time = 0;
	clrf	(_EqualizingCharge_Time)
	clrf	(_EqualizingCharge_Time+1)
	line	915
	
l5684:	
;mypic.h: 914: }
;mypic.h: 915: gBatteryVoltage = GetBatteryVoltage();
	fcall	_GetBatteryVoltage
	movf	(1+(?_GetBatteryVoltage)),w
	movwf	(_gBatteryVoltage+1)
	movf	(0+(?_GetBatteryVoltage)),w
	movwf	(_gBatteryVoltage)
	line	916
	
l5686:	
;mypic.h: 916: if(gBatteryVoltage < *(BatteryStandard + 3) - TemBase + TemVoltage - ADBase + ADVoltage)
	movf	(_BatteryStandard),w
	addlw	06h
	movwf	fsr1l
	clrf fsr1h	
	
	moviw	[0]fsr1
	movwf	(??_SwitchBatteryState+0)+0
	moviw	[1]fsr1
	movwf	(??_SwitchBatteryState+0)+0+1
	movf	(_ADBase),w
	subwf	0+(??_SwitchBatteryState+0)+0,w
	movwf	(??_SwitchBatteryState+2)+0
	movf	(_ADBase+1),w
	subwfb	1+(??_SwitchBatteryState+0)+0,w
	movwf	1+(??_SwitchBatteryState+2)+0
	movf	(_ADVoltage),w
	addwf	0+(??_SwitchBatteryState+2)+0,w
	movwf	(??_SwitchBatteryState+4)+0
	movf	(_ADVoltage+1),w
	addwfc	1+(??_SwitchBatteryState+2)+0,w
	movwf	1+(??_SwitchBatteryState+4)+0
	movf	1+(??_SwitchBatteryState+4)+0,w
	subwf	(_gBatteryVoltage+1),w
	skipz
	goto	u3425
	movf	0+(??_SwitchBatteryState+4)+0,w
	subwf	(_gBatteryVoltage),w
u3425:
	skipnc
	goto	u3421
	goto	u3420
u3421:
	goto	l5696
u3420:
	line	918
	
l5688:	
;mypic.h: 917: {
;mypic.h: 918: BatteryState = BatteryStateSwitch(gBatteryVoltage);
	movf	(_gBatteryVoltage+1),w
	movwf	(?_BatteryStateSwitch+1)
	movf	(_gBatteryVoltage),w
	movwf	(?_BatteryStateSwitch)
	fcall	_BatteryStateSwitch
	movwf	(_BatteryState)
	goto	l5696
	line	921
	
l2399:	
	line	923
;mypic.h: 921: else
;mypic.h: 922: {
;mypic.h: 923: EqualizingChargeFlag = 0;
	bcf	(_EqualizingChargeFlag/8),(_EqualizingChargeFlag)&7
	line	924
	
l5690:	
;mypic.h: 924: EqualizingCharge_Time = 0;
	clrf	(_EqualizingCharge_Time)
	clrf	(_EqualizingCharge_Time+1)
	line	925
	
l5692:	
;mypic.h: 925: gBatteryVoltage = GetBatteryVoltage();
	fcall	_GetBatteryVoltage
	movf	(1+(?_GetBatteryVoltage)),w
	movwf	(_gBatteryVoltage+1)
	movf	(0+(?_GetBatteryVoltage)),w
	movwf	(_gBatteryVoltage)
	goto	l5688
	line	929
	
l5696:	
;mypic.h: 927: }
;mypic.h: 929: if(EqualizingCharge_Time >= 36000)
	movlw	high(08CA0h)
	subwf	(_EqualizingCharge_Time+1),w
	movlw	low(08CA0h)
	skipnz
	subwf	(_EqualizingCharge_Time),w
	skipc
	goto	u3431
	goto	u3430
u3431:
	goto	l2412
u3430:
	goto	l5598
	line	942
	
l5708:	
;mypic.h: 941: {
;mypic.h: 942: if(BatteryLastState != 5)
	movf	(_BatteryLastState),w
	xorlw	05h&0ffh
	skipnz
	goto	u3441
	goto	u3440
u3441:
	goto	l5720
u3440:
	line	945
	
l5710:	
;mypic.h: 943: {
;mypic.h: 945: EnhanceChargeFlag = 0;
	bcf	(_EnhanceChargeFlag/8),(_EnhanceChargeFlag)&7
	line	946
	
l5712:	
;mypic.h: 946: EnhanceCharge_Time = 0;
	clrf	(_EnhanceCharge_Time)
	clrf	(_EnhanceCharge_Time+1)
	line	947
	
l5714:	
;mypic.h: 947: EqualizingChargeFlag = 0;
	bcf	(_EqualizingChargeFlag/8),(_EqualizingChargeFlag)&7
	line	948
;mypic.h: 948: EqualizingCharge_Time = 0;
	clrf	(_EqualizingCharge_Time)
	clrf	(_EqualizingCharge_Time+1)
	line	949
	
l5716:	
;mypic.h: 949: FloatingChargeFlag = 0;
	bcf	(_FloatingChargeFlag/8),(_FloatingChargeFlag)&7
	line	950
	
l5718:	
;mypic.h: 950: BatteryLastState = 5;
	movlw	(05h)
	movwf	(_BatteryLastState)
	line	952
	
l5720:	
;mypic.h: 951: }
;mypic.h: 952: if(PVState == 1)
	btfss	(_PVState/8),(_PVState)&7
	goto	u3451
	goto	u3450
u3451:
	goto	l2406
u3450:
	line	954
	
l5722:	
;mypic.h: 953: {
;mypic.h: 954: ClampVoltage = *(BatteryStandard + 8) - TemBase + TemVoltage - ADBase + ADVoltage;
	movf	(_BatteryStandard),w
	addlw	010h
	movwf	fsr1l
	clrf fsr1h	
	
	moviw	[0]fsr1
	movwf	(_ClampVoltage)
	moviw	[1]fsr1
	movwf	(_ClampVoltage+1)
	
l5724:	
	movf	(_ADBase),w
	subwf	(_ClampVoltage),f
	movf	(_ADBase+1),w
	subwfb	(_ClampVoltage+1),f
	
l5726:	
	movf	(_ADVoltage),w
	addwf	(_ClampVoltage),f
	movf	(_ADVoltage+1),w
	addwfc	(_ClampVoltage+1),f
	line	955
	
l5728:	
;mypic.h: 955: FloatingChargeFlag = 1;
	bsf	(_FloatingChargeFlag/8),(_FloatingChargeFlag)&7
	line	956
;mypic.h: 956: }
	goto	l5604
	line	957
	
l2406:	
	line	959
;mypic.h: 957: else
;mypic.h: 958: {
;mypic.h: 959: FloatingChargeFlag = 0;
	bcf	(_FloatingChargeFlag/8),(_FloatingChargeFlag)&7
	goto	l5604
	line	968
	
l5734:	
;mypic.h: 967: {
;mypic.h: 968: if(BatteryLastState != 6)
	movf	(_BatteryLastState),w
	xorlw	06h&0ffh
	skipnz
	goto	u3461
	goto	u3460
u3461:
	goto	l5756
u3460:
	line	970
	
l5736:	
;mypic.h: 969: {
;mypic.h: 970: ClampVoltage = 0;
	clrf	(_ClampVoltage)
	clrf	(_ClampVoltage+1)
	line	971
	
l5738:	
;mypic.h: 971: TMR2IE = 0;
	movlb 1	; select bank1
	bcf	(1161/8)^080h,(1161)&7
	line	972
	
l5740:	
;mypic.h: 972: T2Flag = 0;
	movlb 0	; select bank0
	bcf	(_T2Flag/8),(_T2Flag)&7
	line	973
	
l5742:	
;mypic.h: 973: PWMFlag = 0;
	bcf	(_PWMFlag/8),(_PWMFlag)&7
	line	974
	
l5744:	
;mypic.h: 974: (PORTB |= (1 << 0));
	bsf	(13)+(0/8),(0)&7	;volatile
	line	975
	
l5746:	
;mypic.h: 975: (PORTA |= (1 << 0));
	bsf	(12)+(0/8),(0)&7	;volatile
	line	977
	
l5748:	
;mypic.h: 977: EnhanceChargeFlag = 0;
	bcf	(_EnhanceChargeFlag/8),(_EnhanceChargeFlag)&7
	line	978
;mypic.h: 978: EnhanceCharge_Time = 0;
	clrf	(_EnhanceCharge_Time)
	clrf	(_EnhanceCharge_Time+1)
	line	979
	
l5750:	
;mypic.h: 979: EqualizingChargeFlag = 0;
	bcf	(_EqualizingChargeFlag/8),(_EqualizingChargeFlag)&7
	line	980
;mypic.h: 980: EqualizingCharge_Time = 0;
	clrf	(_EqualizingCharge_Time)
	clrf	(_EqualizingCharge_Time+1)
	line	981
	
l5752:	
;mypic.h: 981: FloatingChargeFlag = 0;
	bcf	(_FloatingChargeFlag/8),(_FloatingChargeFlag)&7
	line	982
	
l5754:	
;mypic.h: 982: BatteryLastState = 6;
	movlw	(06h)
	movwf	(_BatteryLastState)
	line	984
	
l5756:	
;mypic.h: 983: }
;mypic.h: 984: gBatteryVoltage = GetBatteryVoltage();
	fcall	_GetBatteryVoltage
	movf	(1+(?_GetBatteryVoltage)),w
	movwf	(_gBatteryVoltage+1)
	movf	(0+(?_GetBatteryVoltage)),w
	movwf	(_gBatteryVoltage)
	line	985
	
l5758:	
;mypic.h: 985: if(gBatteryVoltage < *(BatteryStandard + 5) - TemBase + TemVoltage - ADBase + ADVoltage)
	movf	(_BatteryStandard),w
	addlw	0Ah
	movwf	fsr1l
	clrf fsr1h	
	
	moviw	[0]fsr1
	movwf	(??_SwitchBatteryState+0)+0
	moviw	[1]fsr1
	movwf	(??_SwitchBatteryState+0)+0+1
	movf	(_ADBase),w
	subwf	0+(??_SwitchBatteryState+0)+0,w
	movwf	(??_SwitchBatteryState+2)+0
	movf	(_ADBase+1),w
	subwfb	1+(??_SwitchBatteryState+0)+0,w
	movwf	1+(??_SwitchBatteryState+2)+0
	movf	(_ADVoltage),w
	addwf	0+(??_SwitchBatteryState+2)+0,w
	movwf	(??_SwitchBatteryState+4)+0
	movf	(_ADVoltage+1),w
	addwfc	1+(??_SwitchBatteryState+2)+0,w
	movwf	1+(??_SwitchBatteryState+4)+0
	movf	1+(??_SwitchBatteryState+4)+0,w
	subwf	(_gBatteryVoltage+1),w
	skipz
	goto	u3475
	movf	0+(??_SwitchBatteryState+4)+0,w
	subwf	(_gBatteryVoltage),w
u3475:
	skipnc
	goto	u3471
	goto	u3470
u3471:
	goto	l2412
u3470:
	goto	l5556
	line	735
	
l5762:	
	movf	(_BatteryState),w
	; Switch size 1, requested type "space"
; Number of cases is 8, Range of values is 0 to 7
; switch strategies available:
; Name         Bytes Cycles
; simple_byte    25    13 (average)
; direct_byte    32    16 (fixed)
;	Chosen strategy is simple_byte

	xorlw	0^0	; case 0
	skipnz
	goto	l5488
	xorlw	1^0	; case 1
	skipnz
	goto	l5506
	xorlw	2^1	; case 2
	skipnz
	goto	l5558
	xorlw	3^2	; case 3
	skipnz
	goto	l5608
	xorlw	4^3	; case 4
	skipnz
	goto	l5658
	xorlw	5^4	; case 5
	skipnz
	goto	l5708
	xorlw	6^5	; case 6
	skipnz
	goto	l5734
	xorlw	7^6	; case 7
	skipnz
	goto	l2412
	goto	l2412

	line	995
	
l2412:	
	return
	opt stack 0
GLOBAL	__end_of_SwitchBatteryState
	__end_of_SwitchBatteryState:
;; =============== function _SwitchBatteryState ends ============

	signat	_SwitchBatteryState,88
	global	_PWMCharge
psect	text587,local,class=CODE,delta=2
global __ptext587
__ptext587:

;; *************** function _PWMCharge *****************
;; Defined at:
;;		line 628 in file "C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.h"
;; Parameters:    Size  Location     Type
;;		None
;; Auto vars:     Size  Location     Type
;;  ChangeBatter    2   17[BANK0 ] unsigned int 
;; Return value:  Size  Location     Type
;;		None               void
;; Registers used:
;;		wreg, status,2, status,0, pclath, cstack
;; Tracked objects:
;;		On entry : 1F/0
;;		On exit  : 1F/0
;;		Unchanged: FFFE0/0
;; Data sizes:     COMMON   BANK0   BANK1   BANK2
;;      Params:         0       0       0       0
;;      Locals:         0       2       0       0
;;      Temps:          0       0       0       0
;;      Totals:         0       2       0       0
;;Total ram usage:        2 bytes
;; Hardware stack levels used:    1
;; Hardware stack levels required when called:    5
;; This function calls:
;;		_GetBatteryVoltage
;; This function is called by:
;;		_main
;; This function uses a non-reentrant model
;;
psect	text587
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.h"
	line	628
	global	__size_of_PWMCharge
	__size_of_PWMCharge	equ	__end_of_PWMCharge-_PWMCharge
	
_PWMCharge:	
	opt	stack 10
; Regs used in _PWMCharge: [wreg+status,2+status,0+pclath+cstack]
	line	630
	
l5448:	
;mypic.h: 629: unsigned int ChangeBatteryVoltag;
;mypic.h: 630: ChangeBatteryVoltag = GetBatteryVoltage();
	fcall	_GetBatteryVoltage
	movf	(1+(?_GetBatteryVoltage)),w
	movwf	(PWMCharge@ChangeBatteryVoltag+1)
	movf	(0+(?_GetBatteryVoltage)),w
	movwf	(PWMCharge@ChangeBatteryVoltag)
	line	631
	
l5450:	
;mypic.h: 631: if((EnhanceChargeFlag == 1) || (EqualizingChargeFlag == 1) || (FloatingChargeFlag == 1))
	btfsc	(_EnhanceChargeFlag/8),(_EnhanceChargeFlag)&7
	goto	u3121
	goto	u3120
u3121:
	goto	l2344
u3120:
	
l5452:	
	btfsc	(_EqualizingChargeFlag/8),(_EqualizingChargeFlag)&7
	goto	u3131
	goto	u3130
u3131:
	goto	l2344
u3130:
	
l5454:	
	btfss	(_FloatingChargeFlag/8),(_FloatingChargeFlag)&7
	goto	u3141
	goto	u3140
u3141:
	goto	l2342
u3140:
	
l2344:	
	line	633
;mypic.h: 632: {
;mypic.h: 633: (PORTB &= ~(1 << 0));
	bcf	(13)+(0/8),(0)&7	;volatile
	line	635
	
l5456:	
;mypic.h: 635: if(ChangeBatteryVoltag < ClampVoltage)
	movf	(_ClampVoltage+1),w
	subwf	(PWMCharge@ChangeBatteryVoltag+1),w
	skipz
	goto	u3155
	movf	(_ClampVoltage),w
	subwf	(PWMCharge@ChangeBatteryVoltag),w
u3155:
	skipnc
	goto	u3151
	goto	u3150
u3151:
	goto	l5468
u3150:
	line	637
	
l5458:	
;mypic.h: 636: {
;mypic.h: 637: PVChargeFlag = 1;
	bsf	(_PVChargeFlag/8),(_PVChargeFlag)&7
	line	638
	
l5460:	
;mypic.h: 638: if(DutyRatio > 249)
	movlw	(0FAh)
	subwf	(_DutyRatio),w
	skipc
	goto	u3161
	goto	u3160
u3161:
	goto	l2346
u3160:
	line	640
	
l5462:	
;mypic.h: 639: {
;mypic.h: 640: TMR2IE = 0;
	movlb 1	; select bank1
	bcf	(1161/8)^080h,(1161)&7
	line	641
;mypic.h: 641: T2Flag = 0;
	movlb 0	; select bank0
	bcf	(_T2Flag/8),(_T2Flag)&7
	line	642
;mypic.h: 642: PWMFlag = 0;
	bcf	(_PWMFlag/8),(_PWMFlag)&7
	line	643
;mypic.h: 643: (PORTA &= ~(1 << 0));
	bcf	(12)+(0/8),(0)&7	;volatile
	line	644
;mypic.h: 644: }
	goto	l5468
	line	645
	
l2346:	
	line	647
;mypic.h: 645: else
;mypic.h: 646: {
;mypic.h: 647: if(T2Flag == 0)
	btfsc	(_T2Flag/8),(_T2Flag)&7
	goto	u3171
	goto	u3170
u3171:
	goto	l5466
u3170:
	line	649
	
l5464:	
;mypic.h: 648: {
;mypic.h: 649: TMR2IE = 1;
	movlb 1	; select bank1
	bsf	(1161/8)^080h,(1161)&7
	line	650
;mypic.h: 650: T2Flag = 1;
	movlb 0	; select bank0
	bsf	(_T2Flag/8),(_T2Flag)&7
	line	651
;mypic.h: 651: PWMFlag = 1;
	bsf	(_PWMFlag/8),(_PWMFlag)&7
	line	653
	
l5466:	
;mypic.h: 652: }
;mypic.h: 653: DutyRatio = DutyRatio+10;
	movlw	(0Ah)
	addwf	(_DutyRatio),f
	line	656
	
l5468:	
;mypic.h: 654: }
;mypic.h: 655: }
;mypic.h: 656: if(ChangeBatteryVoltag > ClampVoltage)
	movf	(PWMCharge@ChangeBatteryVoltag+1),w
	subwf	(_ClampVoltage+1),w
	skipz
	goto	u3185
	movf	(PWMCharge@ChangeBatteryVoltag),w
	subwf	(_ClampVoltage),w
u3185:
	skipnc
	goto	u3181
	goto	u3180
u3181:
	goto	l2355
u3180:
	line	658
	
l5470:	
;mypic.h: 657: {
;mypic.h: 658: if(DutyRatio < 9)
	movlw	(09h)
	subwf	(_DutyRatio),w
	skipnc
	goto	u3191
	goto	u3190
u3191:
	goto	l2350
u3190:
	line	660
	
l5472:	
;mypic.h: 659: {
;mypic.h: 660: TMR2IE = 0;
	movlb 1	; select bank1
	bcf	(1161/8)^080h,(1161)&7
	line	661
;mypic.h: 661: T2Flag = 0;
	movlb 0	; select bank0
	bcf	(_T2Flag/8),(_T2Flag)&7
	line	662
;mypic.h: 662: PWMFlag = 0;
	bcf	(_PWMFlag/8),(_PWMFlag)&7
	line	663
;mypic.h: 663: (PORTA |= (1 << 0));
	bsf	(12)+(0/8),(0)&7	;volatile
	line	664
;mypic.h: 664: PVChargeFlag = 0;
	bcf	(_PVChargeFlag/8),(_PVChargeFlag)&7
	line	665
;mypic.h: 665: }
	goto	l2355
	line	666
	
l2350:	
	line	668
;mypic.h: 666: else
;mypic.h: 667: {
;mypic.h: 668: PVChargeFlag = 1;
	bsf	(_PVChargeFlag/8),(_PVChargeFlag)&7
	line	669
;mypic.h: 669: if(T2Flag == 0)
	btfsc	(_T2Flag/8),(_T2Flag)&7
	goto	u3201
	goto	u3200
u3201:
	goto	l5476
u3200:
	line	671
	
l5474:	
;mypic.h: 670: {
;mypic.h: 671: TMR2IE = 1;
	movlb 1	; select bank1
	bsf	(1161/8)^080h,(1161)&7
	line	672
;mypic.h: 672: T2Flag = 1;
	movlb 0	; select bank0
	bsf	(_T2Flag/8),(_T2Flag)&7
	line	673
;mypic.h: 673: PWMFlag = 1;
	bsf	(_PWMFlag/8),(_PWMFlag)&7
	line	675
	
l5476:	
;mypic.h: 674: }
;mypic.h: 675: DutyRatio = DutyRatio-10;
	movlw	(0F6h)
	addwf	(_DutyRatio),f
	goto	l2355
	line	679
	
l2342:	
	line	681
;mypic.h: 679: else
;mypic.h: 680: {
;mypic.h: 681: if(T2Flag == 1)
	btfss	(_T2Flag/8),(_T2Flag)&7
	goto	u3211
	goto	u3210
u3211:
	goto	l5480
u3210:
	line	683
	
l5478:	
;mypic.h: 682: {
;mypic.h: 683: TMR2IE = 0;
	movlb 1	; select bank1
	bcf	(1161/8)^080h,(1161)&7
	line	684
;mypic.h: 684: T2Flag = 0;
	movlb 0	; select bank0
	bcf	(_T2Flag/8),(_T2Flag)&7
	line	685
;mypic.h: 685: PWMFlag = 0;
	bcf	(_PWMFlag/8),(_PWMFlag)&7
	line	687
	
l5480:	
;mypic.h: 686: }
;mypic.h: 687: PwmCount = 0;
	clrf	(_PwmCount)
	line	688
;mypic.h: 688: DutyRatio = 0;
	clrf	(_DutyRatio)
	line	689
	
l5482:	
;mypic.h: 689: (PORTB |= (1 << 0));
	bsf	(13)+(0/8),(0)&7	;volatile
	line	691
	
l5484:	
;mypic.h: 691: PVChargeFlag = 0;
	bcf	(_PVChargeFlag/8),(_PVChargeFlag)&7
	line	693
	
l2355:	
	return
	opt stack 0
GLOBAL	__end_of_PWMCharge
	__end_of_PWMCharge:
;; =============== function _PWMCharge ends ============

	signat	_PWMCharge,88
	global	_SolarPanelDealWith
psect	text588,local,class=CODE,delta=2
global __ptext588
__ptext588:

;; *************** function _SolarPanelDealWith *****************
;; Defined at:
;;		line 539 in file "C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.h"
;; Parameters:    Size  Location     Type
;;		None
;; Auto vars:     Size  Location     Type
;;  SolarPanelVo    2   23[BANK0 ] unsigned int 
;; Return value:  Size  Location     Type
;;		None               void
;; Registers used:
;;		wreg, fsr1l, fsr1h, status,2, status,0, pclath, cstack
;; Tracked objects:
;;		On entry : 1F/0
;;		On exit  : 1F/0
;;		Unchanged: FFFE0/0
;; Data sizes:     COMMON   BANK0   BANK1   BANK2
;;      Params:         0       0       0       0
;;      Locals:         0       2       0       0
;;      Temps:          0       6       0       0
;;      Totals:         0       8       0       0
;;Total ram usage:        8 bytes
;; Hardware stack levels used:    1
;; Hardware stack levels required when called:    5
;; This function calls:
;;		_Delay
;;		_GetSolarPanelVoltage
;;		___wmul
;; This function is called by:
;;		_main
;; This function uses a non-reentrant model
;;
psect	text588
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.h"
	line	539
	global	__size_of_SolarPanelDealWith
	__size_of_SolarPanelDealWith	equ	__end_of_SolarPanelDealWith-_SolarPanelDealWith
	
_SolarPanelDealWith:	
	opt	stack 10
; Regs used in _SolarPanelDealWith: [wreg+fsr1l-status,0+pclath+cstack]
	line	541
	
l5366:	
;mypic.h: 540: unsigned int SolarPanelVoltage;
;mypic.h: 541: if(PWMFlag == 0)
	btfsc	(_PWMFlag/8),(_PWMFlag)&7
	goto	u2991
	goto	u2990
u2991:
	goto	l5404
u2990:
	line	543
	
l5368:	
;mypic.h: 542: {
;mypic.h: 543: if(DutyRatio > 249)
	movlw	(0FAh)
	subwf	(_DutyRatio),w
	skipc
	goto	u3001
	goto	u3000
u3001:
	goto	l5378
u3000:
	line	545
	
l5370:	
;mypic.h: 544: {
;mypic.h: 545: (PORTB |= (1 << 0));
	bsf	(13)+(0/8),(0)&7	;volatile
	line	546
	
l5372:	
;mypic.h: 546: Delay(200);
	movlw	0C8h
	movwf	(?_Delay)
	clrf	(?_Delay+1)
	fcall	_Delay
	line	547
;mypic.h: 547: SolarPanelVoltage = GetSolarPanelVoltage();
	fcall	_GetSolarPanelVoltage
	movf	(1+(?_GetSolarPanelVoltage)),w
	movwf	(SolarPanelDealWith@SolarPanelVoltage+1)
	movf	(0+(?_GetSolarPanelVoltage)),w
	movwf	(SolarPanelDealWith@SolarPanelVoltage)
	line	548
	
l5374:	
;mypic.h: 548: gSolarPanelVoltage = SolarPanelVoltage;
	movf	(SolarPanelDealWith@SolarPanelVoltage+1),w
	movwf	(_gSolarPanelVoltage+1)
	movf	(SolarPanelDealWith@SolarPanelVoltage),w
	movwf	(_gSolarPanelVoltage)
	line	549
	
l5376:	
;mypic.h: 549: (PORTB &= ~(1 << 0));
	bcf	(13)+(0/8),(0)&7	;volatile
	line	551
	
l5378:	
;mypic.h: 550: }
;mypic.h: 551: if(DutyRatio == 0)
	movf	(_DutyRatio),f
	skipz
	goto	u3011
	goto	u3010
u3011:
	goto	l5404
u3010:
	line	553
	
l5380:	
;mypic.h: 552: {
;mypic.h: 553: (PORTB |= (1 << 0));
	bsf	(13)+(0/8),(0)&7	;volatile
	line	554
	
l5382:	
;mypic.h: 554: if(LPVCount == 0)
	movf	(_LPVCount),f
	skipz
	goto	u3021
	goto	u3020
u3021:
	goto	l2327
u3020:
	line	556
	
l5384:	
;mypic.h: 555: {
;mypic.h: 556: (PORTA &= ~(1 << 0));
	bcf	(12)+(0/8),(0)&7	;volatile
	line	557
	
l5386:	
;mypic.h: 557: Delay(1000);
	movlw	low(03E8h)
	movwf	(?_Delay)
	movlw	high(03E8h)
	movwf	((?_Delay))+1
	fcall	_Delay
	line	558
;mypic.h: 558: SolarPanelVoltage = GetSolarPanelVoltage();
	fcall	_GetSolarPanelVoltage
	movf	(1+(?_GetSolarPanelVoltage)),w
	movwf	(SolarPanelDealWith@SolarPanelVoltage+1)
	movf	(0+(?_GetSolarPanelVoltage)),w
	movwf	(SolarPanelDealWith@SolarPanelVoltage)
	line	559
	
l5388:	
;mypic.h: 559: gSolarPanelVoltage = SolarPanelVoltage;
	movf	(SolarPanelDealWith@SolarPanelVoltage+1),w
	movwf	(_gSolarPanelVoltage+1)
	movf	(SolarPanelDealWith@SolarPanelVoltage),w
	movwf	(_gSolarPanelVoltage)
	line	560
	
l5390:	
;mypic.h: 560: (PORTA |= (1 << 0));
	bsf	(12)+(0/8),(0)&7	;volatile
	line	561
;mypic.h: 561: }
	goto	l5404
	line	562
	
l2327:	
	line	564
;mypic.h: 562: else
;mypic.h: 563: {
;mypic.h: 564: (PORTA &= ~(1 << 0));
	bcf	(12)+(0/8),(0)&7	;volatile
	line	565
	
l5392:	
;mypic.h: 565: if(LPVCount > 99)
	movlw	(064h)
	subwf	(_LPVCount),w
	skipc
	goto	u3031
	goto	u3030
u3031:
	goto	l2326
u3030:
	line	567
	
l5394:	
;mypic.h: 566: {
;mypic.h: 567: LPVCount = 0;
	clrf	(_LPVCount)
	line	568
	
l5396:	
;mypic.h: 568: SolarPanelVoltage = GetSolarPanelVoltage();
	fcall	_GetSolarPanelVoltage
	movf	(1+(?_GetSolarPanelVoltage)),w
	movwf	(SolarPanelDealWith@SolarPanelVoltage+1)
	movf	(0+(?_GetSolarPanelVoltage)),w
	movwf	(SolarPanelDealWith@SolarPanelVoltage)
	line	569
	
l5398:	
;mypic.h: 569: gSolarPanelVoltage = SolarPanelVoltage;
	movf	(SolarPanelDealWith@SolarPanelVoltage+1),w
	movwf	(_gSolarPanelVoltage+1)
	movf	(SolarPanelDealWith@SolarPanelVoltage),w
	movwf	(_gSolarPanelVoltage)
	line	570
	
l5400:	
;mypic.h: 570: (PORTA |= (1 << 0));
	bsf	(12)+(0/8),(0)&7	;volatile
	line	571
	
l5402:	
;mypic.h: 571: LPVFlag = 1;
	bsf	(_LPVFlag/8),(_LPVFlag)&7
	goto	l5404
	line	574
	
l2326:	
	line	576
	
l5404:	
;mypic.h: 572: }
;mypic.h: 573: }
;mypic.h: 574: }
;mypic.h: 575: }
;mypic.h: 576: if(PWMFlag == 1)
	btfss	(_PWMFlag/8),(_PWMFlag)&7
	goto	u3041
	goto	u3040
u3041:
	goto	l5418
u3040:
	line	578
	
l5406:	
;mypic.h: 577: {
;mypic.h: 578: (PORTB |= (1 << 0));
	bsf	(13)+(0/8),(0)&7	;volatile
	line	579
;mypic.h: 579: PWMFlag = 0;
	bcf	(_PWMFlag/8),(_PWMFlag)&7
	line	581
;mypic.h: 581: (PORTA &= ~(1 << 0));
	bcf	(12)+(0/8),(0)&7	;volatile
	line	582
	
l5408:	
;mypic.h: 582: Delay(200);
	movlw	0C8h
	movwf	(?_Delay)
	clrf	(?_Delay+1)
	fcall	_Delay
	line	583
;mypic.h: 583: SolarPanelVoltage = GetSolarPanelVoltage();
	fcall	_GetSolarPanelVoltage
	movf	(1+(?_GetSolarPanelVoltage)),w
	movwf	(SolarPanelDealWith@SolarPanelVoltage+1)
	movf	(0+(?_GetSolarPanelVoltage)),w
	movwf	(SolarPanelDealWith@SolarPanelVoltage)
	line	584
	
l5410:	
;mypic.h: 584: gSolarPanelVoltage = SolarPanelVoltage;
	movf	(SolarPanelDealWith@SolarPanelVoltage+1),w
	movwf	(_gSolarPanelVoltage+1)
	movf	(SolarPanelDealWith@SolarPanelVoltage),w
	movwf	(_gSolarPanelVoltage)
	line	585
	
l5412:	
;mypic.h: 585: (PORTA |= (1 << 0));
	bsf	(12)+(0/8),(0)&7	;volatile
	line	586
	
l5414:	
;mypic.h: 586: PWMFlag = 1;
	bsf	(_PWMFlag/8),(_PWMFlag)&7
	line	587
	
l5416:	
;mypic.h: 587: (PORTB &= ~(1 << 0));
	bcf	(13)+(0/8),(0)&7	;volatile
	line	589
	
l5418:	
;mypic.h: 588: }
;mypic.h: 589: if(gSolarPanelVoltage <= 24)
	movlw	high(019h)
	subwf	(_gSolarPanelVoltage+1),w
	movlw	low(019h)
	skipnz
	subwf	(_gSolarPanelVoltage),w
	skipnc
	goto	u3051
	goto	u3050
u3051:
	goto	l2331
u3050:
	line	591
	
l5420:	
;mypic.h: 590: {
;mypic.h: 591: PVState = 1;
	bsf	(_PVState/8),(_PVState)&7
	line	592
;mypic.h: 592: }
	goto	l5422
	line	593
	
l2331:	
	line	595
;mypic.h: 593: else
;mypic.h: 594: {
;mypic.h: 595: PVState = 0;
	bcf	(_PVState/8),(_PVState)&7
	line	596
;mypic.h: 596: PVChargeFlag = 0;
	bcf	(_PVChargeFlag/8),(_PVChargeFlag)&7
	line	599
	
l5422:	
;mypic.h: 597: }
;mypic.h: 599: if(((gBatteryVoltage*26) - (gSolarPanelVoltage*23)) > *(BatteryStandard + 9))
	movf	(_gBatteryVoltage+1),w
	movwf	(?___wmul+1)
	movf	(_gBatteryVoltage),w
	movwf	(?___wmul)
	movlw	01Ah
	movwf	0+(?___wmul)+02h
	clrf	1+(?___wmul)+02h
	fcall	___wmul
	movf	(0+?___wmul),w
	movwf	(??_SolarPanelDealWith+0)+0
	movf	(1+?___wmul),w
	movwf	((??_SolarPanelDealWith+0)+0+1)
	movf	(_gSolarPanelVoltage+1),w
	movwf	(?___wmul+1)
	movf	(_gSolarPanelVoltage),w
	movwf	(?___wmul)
	movlw	low(-23)
	movwf	0+(?___wmul)+02h
	movlw	high(-23)
	movwf	(0+(?___wmul)+02h)+1
	fcall	___wmul
	movf	(0+(?___wmul)),w
	addwf	0+(??_SolarPanelDealWith+0)+0,w
	movwf	(??_SolarPanelDealWith+2)+0
	movf	(1+(?___wmul)),w
	addwfc	1+(??_SolarPanelDealWith+0)+0,w
	movwf	1+(??_SolarPanelDealWith+2)+0
	movf	(_BatteryStandard),w
	addlw	012h
	movwf	fsr1l
	clrf fsr1h	
	
	moviw	[0]fsr1
	movwf	(??_SolarPanelDealWith+4)+0
	moviw	[1]fsr1
	movwf	(??_SolarPanelDealWith+4)+0+1
	movf	1+(??_SolarPanelDealWith+2)+0,w
	subwf	1+(??_SolarPanelDealWith+4)+0,w
	skipz
	goto	u3065
	movf	0+(??_SolarPanelDealWith+2)+0,w
	subwf	0+(??_SolarPanelDealWith+4)+0,w
u3065:
	skipnc
	goto	u3061
	goto	u3060
u3061:
	goto	l5432
u3060:
	line	601
	
l5424:	
;mypic.h: 600: {
;mypic.h: 601: LPVFlag = 0;
	bcf	(_LPVFlag/8),(_LPVFlag)&7
	line	602
	
l5426:	
;mypic.h: 602: LPVCount = 0;
	clrf	(_LPVCount)
	line	603
	
l5428:	
;mypic.h: 603: RB6 = 1;
	bsf	(110/8),(110)&7
	line	604
	
l5430:	
;mypic.h: 604: DAYTIME = 1;
	bsf	(_DAYTIME/8),(_DAYTIME)&7
	line	606
	
l5432:	
;mypic.h: 605: }
;mypic.h: 606: if(((gBatteryVoltage*26) - (gSolarPanelVoltage*23)) < 3686)
	movf	(_gBatteryVoltage+1),w
	movwf	(?___wmul+1)
	movf	(_gBatteryVoltage),w
	movwf	(?___wmul)
	movlw	01Ah
	movwf	0+(?___wmul)+02h
	clrf	1+(?___wmul)+02h
	fcall	___wmul
	movf	(0+?___wmul),w
	movwf	(??_SolarPanelDealWith+0)+0
	movf	(1+?___wmul),w
	movwf	((??_SolarPanelDealWith+0)+0+1)
	movf	(_gSolarPanelVoltage+1),w
	movwf	(?___wmul+1)
	movf	(_gSolarPanelVoltage),w
	movwf	(?___wmul)
	movlw	low(-23)
	movwf	0+(?___wmul)+02h
	movlw	high(-23)
	movwf	(0+(?___wmul)+02h)+1
	fcall	___wmul
	movf	(0+(?___wmul)),w
	addwf	0+(??_SolarPanelDealWith+0)+0,w
	movwf	(??_SolarPanelDealWith+2)+0
	movf	(1+(?___wmul)),w
	addwfc	1+(??_SolarPanelDealWith+0)+0,w
	movwf	1+(??_SolarPanelDealWith+2)+0
	movlw	high(0E66h)
	subwf	1+(??_SolarPanelDealWith+2)+0,w
	movlw	low(0E66h)
	skipnz
	subwf	0+(??_SolarPanelDealWith+2)+0,w
	skipnc
	goto	u3071
	goto	u3070
u3071:
	goto	l2334
u3070:
	line	608
	
l5434:	
;mypic.h: 607: {
;mypic.h: 608: if(DAYTIME == 1)
	btfss	(_DAYTIME/8),(_DAYTIME)&7
	goto	u3081
	goto	u3080
u3081:
	goto	l2334
u3080:
	line	610
	
l5436:	
;mypic.h: 609: {
;mypic.h: 610: if(LPVCount == 0)
	movf	(_LPVCount),f
	skipz
	goto	u3091
	goto	u3090
u3091:
	goto	l2334
u3090:
	line	612
	
l5438:	
;mypic.h: 611: {
;mypic.h: 612: LPVCount = 1;
	clrf	(_LPVCount)
	incf	(_LPVCount),f
	line	615
	
l2334:	
	line	616
;mypic.h: 613: }
;mypic.h: 614: }
;mypic.h: 615: }
;mypic.h: 616: if(LPVFlag == 1)
	btfss	(_LPVFlag/8),(_LPVFlag)&7
	goto	u3101
	goto	u3100
u3101:
	goto	l2339
u3100:
	line	618
	
l5440:	
;mypic.h: 617: {
;mypic.h: 618: LPVFlag = 0;
	bcf	(_LPVFlag/8),(_LPVFlag)&7
	line	619
	
l5442:	
;mypic.h: 619: LPVCount = 0;
	clrf	(_LPVCount)
	line	620
	
l5444:	
;mypic.h: 620: if(((gBatteryVoltage*26) - (gSolarPanelVoltage*23)) < *(BatteryStandard + 10))
	movf	(_BatteryStandard),w
	addlw	014h
	movwf	fsr1l
	clrf fsr1h	
	
	moviw	[0]fsr1
	movwf	(??_SolarPanelDealWith+0)+0
	moviw	[1]fsr1
	movwf	(??_SolarPanelDealWith+0)+0+1
	movf	(_gBatteryVoltage+1),w
	movwf	(?___wmul+1)
	movf	(_gBatteryVoltage),w
	movwf	(?___wmul)
	movlw	01Ah
	movwf	0+(?___wmul)+02h
	clrf	1+(?___wmul)+02h
	fcall	___wmul
	movf	(0+?___wmul),w
	movwf	(??_SolarPanelDealWith+2)+0
	movf	(1+?___wmul),w
	movwf	((??_SolarPanelDealWith+2)+0+1)
	movf	(_gSolarPanelVoltage+1),w
	movwf	(?___wmul+1)
	movf	(_gSolarPanelVoltage),w
	movwf	(?___wmul)
	movlw	low(-23)
	movwf	0+(?___wmul)+02h
	movlw	high(-23)
	movwf	(0+(?___wmul)+02h)+1
	fcall	___wmul
	movf	(0+(?___wmul)),w
	addwf	0+(??_SolarPanelDealWith+2)+0,w
	movwf	(??_SolarPanelDealWith+4)+0
	movf	(1+(?___wmul)),w
	addwfc	1+(??_SolarPanelDealWith+2)+0,w
	movwf	1+(??_SolarPanelDealWith+4)+0
	movf	1+(??_SolarPanelDealWith+0)+0,w
	subwf	1+(??_SolarPanelDealWith+4)+0,w
	skipz
	goto	u3115
	movf	0+(??_SolarPanelDealWith+0)+0,w
	subwf	0+(??_SolarPanelDealWith+4)+0,w
u3115:
	skipnc
	goto	u3111
	goto	u3110
u3111:
	goto	l2339
u3110:
	line	622
	
l5446:	
;mypic.h: 621: {
;mypic.h: 622: RB6 = 0;
	bcf	(110/8),(110)&7
	line	623
;mypic.h: 623: DAYTIME = 0;
	bcf	(_DAYTIME/8),(_DAYTIME)&7
	line	626
	
l2339:	
	return
	opt stack 0
GLOBAL	__end_of_SolarPanelDealWith
	__end_of_SolarPanelDealWith:
;; =============== function _SolarPanelDealWith ends ============

	signat	_SolarPanelDealWith,88
	global	_LoadCurrentDealWith
psect	text589,local,class=CODE,delta=2
global __ptext589
__ptext589:

;; *************** function _LoadCurrentDealWith *****************
;; Defined at:
;;		line 475 in file "C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.h"
;; Parameters:    Size  Location     Type
;;		None
;; Auto vars:     Size  Location     Type
;;  FbVoltage       2    0        unsigned int 
;; Return value:  Size  Location     Type
;;		None               void
;; Registers used:
;;		wreg, status,2, status,0, pclath, cstack
;; Tracked objects:
;;		On entry : 1F/0
;;		On exit  : 1F/0
;;		Unchanged: FFFE0/0
;; Data sizes:     COMMON   BANK0   BANK1   BANK2
;;      Params:         0       0       0       0
;;      Locals:         0       2       0       0
;;      Temps:          0       0       0       0
;;      Totals:         0       2       0       0
;;Total ram usage:        2 bytes
;; Hardware stack levels used:    1
;; Hardware stack levels required when called:    5
;; This function calls:
;;		_GetLoadCurrentVoltage
;; This function is called by:
;;		_main
;; This function uses a non-reentrant model
;;
psect	text589
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.h"
	line	475
	global	__size_of_LoadCurrentDealWith
	__size_of_LoadCurrentDealWith	equ	__end_of_LoadCurrentDealWith-_LoadCurrentDealWith
	
_LoadCurrentDealWith:	
	opt	stack 10
; Regs used in _LoadCurrentDealWith: [wreg+status,2+status,0+pclath+cstack]
	line	476
	
l5310:	
;mypic.h: 476: if((BatteryState != 1) && (BatteryState != 6) && LoadOpen)
	decf	(_BatteryState),w
	skipnz
	goto	u2881
	goto	u2880
u2881:
	goto	l2305
u2880:
	
l5312:	
	movf	(_BatteryState),w
	xorlw	06h&0ffh
	skipnz
	goto	u2891
	goto	u2890
u2891:
	goto	l2305
u2890:
	
l5314:	
	btfss	(_LoadOpen/8),(_LoadOpen)&7
	goto	u2901
	goto	u2900
u2901:
	goto	l2305
u2900:
	line	480
	
l5316:	
	fcall	_GetLoadCurrentVoltage
	line	481
	
l5318:	
	line	482
	
l5320:	
;mypic.h: 482: if((FbVoltage < 134) && (LoadState == 1))
	
l5322:	
	btfss	(_LoadState/8),(_LoadState)&7
	goto	u2911
	goto	u2910
u2911:
	goto	l5334
u2910:
	line	484
	
l5324:	
;mypic.h: 483: {
;mypic.h: 484: if(LoadFlag == 1)
	btfss	(_LoadFlag/8),(_LoadFlag)&7
	goto	u2921
	goto	u2920
u2921:
	goto	l5334
u2920:
	line	486
	
l5326:	
;mypic.h: 485: {
;mypic.h: 486: LoadState = 1;
	bsf	(_LoadState/8),(_LoadState)&7
	line	487
	
l5328:	
;mypic.h: 487: OverLoadTime = 0;
	clrf	(_OverLoadTime)
	clrf	(_OverLoadTime+1)
	line	488
	
l5330:	
;mypic.h: 488: (PORTA &= ~(1 << 4));
	bcf	(12)+(4/8),(4)&7	;volatile
	line	489
	
l5332:	
;mypic.h: 489: LoadFlag = 0;
	bcf	(_LoadFlag/8),(_LoadFlag)&7
	line	492
	
l5334:	
;mypic.h: 490: }
;mypic.h: 491: }
;mypic.h: 492: if((FbVoltage >= 134) || (LoadState == 0))
	
l5336:	
	btfsc	(_LoadState/8),(_LoadState)&7
	goto	u2931
	goto	u2930
u2931:
	goto	l2321
u2930:
	
l2310:	
	line	494
;mypic.h: 493: {
;mypic.h: 494: LoadState = 0;
	bcf	(_LoadState/8),(_LoadState)&7
	line	495
	
l5338:	
;mypic.h: 495: if(( FbVoltage >= 400) || (LoadShort == 1))
	
l5340:	
	btfss	(_LoadShort/8),(_LoadShort)&7
	goto	u2941
	goto	u2940
u2941:
	goto	l5346
u2940:
	
l2313:	
	line	497
;mypic.h: 496: {
;mypic.h: 497: (PORTA |= (1 << 4));
	bsf	(12)+(4/8),(4)&7	;volatile
	line	498
;mypic.h: 498: LoadFlag = 1;
	bsf	(_LoadFlag/8),(_LoadFlag)&7
	line	499
	
l5342:	
;mypic.h: 499: OverLoadTime = 0;
	clrf	(_OverLoadTime)
	clrf	(_OverLoadTime+1)
	line	500
	
l5344:	
;mypic.h: 503: ErrorType = 5;
	bsf	(_LoadShort/8),(_LoadShort)&7
	line	504
;mypic.h: 504: }
	goto	l2321
	line	507
	
l5346:	
;mypic.h: 505: else
;mypic.h: 506: {
;mypic.h: 507: if(OverLoadTime == 0)
	movf	((_OverLoadTime+1)),w
	iorwf	((_OverLoadTime)),w
	skipz
	goto	u2951
	goto	u2950
u2951:
	goto	l5350
u2950:
	line	509
	
l5348:	
;mypic.h: 508: {
;mypic.h: 509: OverLoadTime = 1;
	clrf	(_OverLoadTime)
	incf	(_OverLoadTime),f
	clrf	(_OverLoadTime+1)
	line	511
	
l5350:	
;mypic.h: 510: }
;mypic.h: 511: if(OverLoadTime >= 3000)
	movlw	high(0BB8h)
	subwf	(_OverLoadTime+1),w
	movlw	low(0BB8h)
	skipnz
	subwf	(_OverLoadTime),w
	skipc
	goto	u2961
	goto	u2960
u2961:
	goto	l2321
u2960:
	line	513
	
l5352:	
;mypic.h: 512: {
;mypic.h: 513: OverLoadTime = 1;
	clrf	(_OverLoadTime)
	incf	(_OverLoadTime),f
	clrf	(_OverLoadTime+1)
	line	514
	
l5354:	
;mypic.h: 514: if(LoadFlag == 0)
	btfsc	(_LoadFlag/8),(_LoadFlag)&7
	goto	u2971
	goto	u2970
u2971:
	goto	l2317
u2970:
	line	516
	
l5356:	
;mypic.h: 515: {
;mypic.h: 516: (PORTA |= (1 << 4));
	bsf	(12)+(4/8),(4)&7	;volatile
	line	517
;mypic.h: 517: LoadFlag = 1;
	bsf	(_LoadFlag/8),(_LoadFlag)&7
	line	518
;mypic.h: 518: }
	goto	l2321
	line	519
	
l2317:	
;mypic.h: 519: else if(LoadFlag == 1)
	btfss	(_LoadFlag/8),(_LoadFlag)&7
	goto	u2981
	goto	u2980
u2981:
	goto	l2314
u2980:
	line	521
	
l5358:	
;mypic.h: 520: {
;mypic.h: 521: (PORTA &= ~(1 << 4));
	bcf	(12)+(4/8),(4)&7	;volatile
	line	522
;mypic.h: 522: LoadFlag = 0;
	bcf	(_LoadFlag/8),(_LoadFlag)&7
	line	523
;mypic.h: 523: LoadState = 1;
	bsf	(_LoadState/8),(_LoadState)&7
	line	524
	
l5360:	
;mypic.h: 524: OverLoadTime = 0;
	clrf	(_OverLoadTime)
	clrf	(_OverLoadTime+1)
	goto	l2321
	line	527
	
l2314:	
	goto	l2321
	line	530
	
l2305:	
	line	532
;mypic.h: 530: else
;mypic.h: 531: {
;mypic.h: 532: (PORTA |= (1 << 4));
	bsf	(12)+(4/8),(4)&7	;volatile
	line	533
;mypic.h: 533: LoadFlag = 1;
	bsf	(_LoadFlag/8),(_LoadFlag)&7
	line	534
	
l5362:	
;mypic.h: 534: OverLoadTime = 0;
	clrf	(_OverLoadTime)
	clrf	(_OverLoadTime+1)
	line	535
	
l5364:	
;mypic.h: 535: LoadState = 1;
	bsf	(_LoadState/8),(_LoadState)&7
	line	537
	
l2321:	
	return
	opt stack 0
GLOBAL	__end_of_LoadCurrentDealWith
	__end_of_LoadCurrentDealWith:
;; =============== function _LoadCurrentDealWith ends ============

	signat	_LoadCurrentDealWith,88
	global	_GetLoadCurrentVoltage
psect	text590,local,class=CODE,delta=2
global __ptext590
__ptext590:

;; *************** function _GetLoadCurrentVoltage *****************
;; Defined at:
;;		line 414 in file "C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.h"
;; Parameters:    Size  Location     Type
;;		None
;; Auto vars:     Size  Location     Type
;;  channel         1    0        unsigned char 
;; Return value:  Size  Location     Type
;;                  2   15[BANK0 ] unsigned int 
;; Registers used:
;;		wreg, status,2, status,0, pclath, cstack
;; Tracked objects:
;;		On entry : 1F/0
;;		On exit  : 1F/0
;;		Unchanged: FFFE0/0
;; Data sizes:     COMMON   BANK0   BANK1   BANK2
;;      Params:         0       2       0       0
;;      Locals:         1       0       0       0
;;      Temps:          0       0       0       0
;;      Totals:         1       2       0       0
;;Total ram usage:        3 bytes
;; Hardware stack levels used:    1
;; Hardware stack levels required when called:    4
;; This function calls:
;;		_getADValue
;; This function is called by:
;;		_LoadCurrentDealWith
;;		_KaiJi
;; This function uses a non-reentrant model
;;
psect	text590
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.h"
	line	414
	global	__size_of_GetLoadCurrentVoltage
	__size_of_GetLoadCurrentVoltage	equ	__end_of_GetLoadCurrentVoltage-_GetLoadCurrentVoltage
	
_GetLoadCurrentVoltage:	
	opt	stack 10
; Regs used in _GetLoadCurrentVoltage: [wreg+status,2+status,0+pclath+cstack]
	line	415
	
l5304:	
	line	416
	
l5306:	
;mypic.h: 416: return getADValue(channel);
	movlw	(019h)
	fcall	_getADValue
	movf	(1+(?_getADValue)),w
	movwf	(?_GetLoadCurrentVoltage+1)
	movf	(0+(?_getADValue)),w
	movwf	(?_GetLoadCurrentVoltage)
	line	417
	
l2284:	
	return
	opt stack 0
GLOBAL	__end_of_GetLoadCurrentVoltage
	__end_of_GetLoadCurrentVoltage:
;; =============== function _GetLoadCurrentVoltage ends ============

	signat	_GetLoadCurrentVoltage,90
	global	_GetSolarPanelVoltage
psect	text591,local,class=CODE,delta=2
global __ptext591
__ptext591:

;; *************** function _GetSolarPanelVoltage *****************
;; Defined at:
;;		line 398 in file "C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.h"
;; Parameters:    Size  Location     Type
;;		None
;; Auto vars:     Size  Location     Type
;;  channel         1    0        unsigned char 
;; Return value:  Size  Location     Type
;;                  2   15[BANK0 ] unsigned int 
;; Registers used:
;;		wreg, status,2, status,0, pclath, cstack
;; Tracked objects:
;;		On entry : 1F/0
;;		On exit  : 1F/0
;;		Unchanged: FFFE0/0
;; Data sizes:     COMMON   BANK0   BANK1   BANK2
;;      Params:         0       2       0       0
;;      Locals:         1       0       0       0
;;      Temps:          0       0       0       0
;;      Totals:         1       2       0       0
;;Total ram usage:        3 bytes
;; Hardware stack levels used:    1
;; Hardware stack levels required when called:    4
;; This function calls:
;;		_getADValue
;; This function is called by:
;;		_SolarPanelDealWith
;;		_KaiJi
;; This function uses a non-reentrant model
;;
psect	text591
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.h"
	line	398
	global	__size_of_GetSolarPanelVoltage
	__size_of_GetSolarPanelVoltage	equ	__end_of_GetSolarPanelVoltage-_GetSolarPanelVoltage
	
_GetSolarPanelVoltage:	
	opt	stack 10
; Regs used in _GetSolarPanelVoltage: [wreg+status,2+status,0+pclath+cstack]
	line	399
	
l5298:	
	line	400
	
l5300:	
;mypic.h: 400: return getADValue(channel);
	movlw	(021h)
	fcall	_getADValue
	movf	(1+(?_getADValue)),w
	movwf	(?_GetSolarPanelVoltage+1)
	movf	(0+(?_getADValue)),w
	movwf	(?_GetSolarPanelVoltage)
	line	401
	
l2278:	
	return
	opt stack 0
GLOBAL	__end_of_GetSolarPanelVoltage
	__end_of_GetSolarPanelVoltage:
;; =============== function _GetSolarPanelVoltage ends ============

	signat	_GetSolarPanelVoltage,90
	global	_GetBatteryVoltage
psect	text592,local,class=CODE,delta=2
global __ptext592
__ptext592:

;; *************** function _GetBatteryVoltage *****************
;; Defined at:
;;		line 390 in file "C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.h"
;; Parameters:    Size  Location     Type
;;		None
;; Auto vars:     Size  Location     Type
;;  channel         1    0        unsigned char 
;; Return value:  Size  Location     Type
;;                  2   15[BANK0 ] unsigned int 
;; Registers used:
;;		wreg, status,2, status,0, pclath, cstack
;; Tracked objects:
;;		On entry : 1D/2
;;		On exit  : 1F/0
;;		Unchanged: FFFE0/0
;; Data sizes:     COMMON   BANK0   BANK1   BANK2
;;      Params:         0       2       0       0
;;      Locals:         1       0       0       0
;;      Temps:          0       0       0       0
;;      Totals:         1       2       0       0
;;Total ram usage:        3 bytes
;; Hardware stack levels used:    1
;; Hardware stack levels required when called:    4
;; This function calls:
;;		_getADValue
;; This function is called by:
;;		_PWMCharge
;;		_SwitchBatteryState
;;		_KaiJi
;; This function uses a non-reentrant model
;;
psect	text592
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.h"
	line	390
	global	__size_of_GetBatteryVoltage
	__size_of_GetBatteryVoltage	equ	__end_of_GetBatteryVoltage-_GetBatteryVoltage
	
_GetBatteryVoltage:	
	opt	stack 10
; Regs used in _GetBatteryVoltage: [wreg+status,2+status,0+pclath+cstack]
	line	391
	
l5292:	
	line	392
	
l5294:	
;mypic.h: 392: return getADValue(channel);
	movlw	(029h)
	fcall	_getADValue
	movf	(1+(?_getADValue)),w
	movwf	(?_GetBatteryVoltage+1)
	movf	(0+(?_getADValue)),w
	movwf	(?_GetBatteryVoltage)
	line	393
	
l2275:	
	return
	opt stack 0
GLOBAL	__end_of_GetBatteryVoltage
	__end_of_GetBatteryVoltage:
;; =============== function _GetBatteryVoltage ends ============

	signat	_GetBatteryVoltage,90
	global	_load_system_state
psect	text593,local,class=CODE,delta=2
global __ptext593
__ptext593:

;; *************** function _load_system_state *****************
;; Defined at:
;;		line 317 in file "C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.h"
;; Parameters:    Size  Location     Type
;;		None
;; Auto vars:     Size  Location     Type
;;		None
;; Return value:  Size  Location     Type
;;		None               void
;; Registers used:
;;		wreg, fsr0l, fsr0h, status,2, status,0, pclath, cstack
;; Tracked objects:
;;		On entry : 17F/0
;;		On exit  : 17F/0
;;		Unchanged: FFE80/0
;; Data sizes:     COMMON   BANK0   BANK1   BANK2
;;      Params:         0       0       0       0
;;      Locals:         0       0       0       0
;;      Temps:          0       0       0       0
;;      Totals:         0       0       0       0
;;Total ram usage:        0 bytes
;; Hardware stack levels used:    1
;; Hardware stack levels required when called:    4
;; This function calls:
;;		_readFromEEPROM
;; This function is called by:
;;		_main
;; This function uses a non-reentrant model
;;
psect	text593
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.h"
	line	317
	global	__size_of_load_system_state
	__size_of_load_system_state	equ	__end_of_load_system_state-_load_system_state
	
_load_system_state:	
	opt	stack 11
; Regs used in _load_system_state: [wreg-fsr0h+status,2+status,0+pclath+cstack]
	line	331
	
l5290:	
;mypic.h: 331: en_charge = readFromEEPROM(1);
	movlw	(01h)
	fcall	_readFromEEPROM
	line	332
;mypic.h: 332: eq_charge = readFromEEPROM(2);
	movlw	(02h)
	fcall	_readFromEEPROM
	line	333
;mypic.h: 333: flo_charge = readFromEEPROM(3);
	movlw	(03h)
	fcall	_readFromEEPROM
	line	334
;mypic.h: 334: time_charge = readFromEEPROM(4);
	movlw	(04h)
	fcall	_readFromEEPROM
	line	335
;mypic.h: 335: under_vol = readFromEEPROM(5);
	movlw	(05h)
	fcall	_readFromEEPROM
	line	336
;mypic.h: 336: re_under = readFromEEPROM(6);
	movlw	(06h)
	fcall	_readFromEEPROM
	line	337
;mypic.h: 337: over_vol = readFromEEPROM(7);
	movlw	(07h)
	fcall	_readFromEEPROM
	line	338
;mypic.h: 338: re_over = readFromEEPROM(8);
	movlw	(08h)
	fcall	_readFromEEPROM
	line	339
;mypic.h: 339: day_vol = readFromEEPROM(9);
	movlw	(09h)
	fcall	_readFromEEPROM
	line	340
;mypic.h: 340: night_vol = readFromEEPROM(10);
	movlw	(0Ah)
	fcall	_readFromEEPROM
	line	342
	
l2250:	
	return
	opt stack 0
GLOBAL	__end_of_load_system_state
	__end_of_load_system_state:
;; =============== function _load_system_state ends ============

	signat	_load_system_state,88
	global	_lcd_print_line2
psect	text594,local,class=CODE,delta=2
global __ptext594
__ptext594:

;; *************** function _lcd_print_line2 *****************
;; Defined at:
;;		line 136 in file "C:\CTK\CTK80A 150928\CTK80A_Jas\LM016.h"
;; Parameters:    Size  Location     Type
;;  charPointer     2    7[COMMON] PTR const unsigned char 
;;		 -> ROM(4096), state7_2(17), state7_1(15), state6_2(15), 
;;		 -> state6_1(16), state5_2(16), state5_1(17), state4_1(17), 
;;		 -> state3_2(17), state3_1(17), state2_1(13), state1_2(16), 
;;		 -> state1_1(16), systemType1(11), checking(12), welcome(8), 
;;		 -> state2_4(13), state2_3(10), state2_2(12), 
;;  postion         1    9[COMMON] unsigned char 
;; Auto vars:     Size  Location     Type
;;		None
;; Return value:  Size  Location     Type
;;		None               void
;; Registers used:
;;		wreg, fsr0l, fsr0h, status,2, status,0, pclath, cstack
;; Tracked objects:
;;		On entry : 1F/0
;;		On exit  : 1F/0
;;		Unchanged: FFFE0/0
;; Data sizes:     COMMON   BANK0   BANK1   BANK2
;;      Params:         3       0       0       0
;;      Locals:         0       0       0       0
;;      Temps:          0       0       0       0
;;      Totals:         3       0       0       0
;;Total ram usage:        3 bytes
;; Hardware stack levels used:    1
;; Hardware stack levels required when called:    3
;; This function calls:
;;		_lcd_write_command
;;		_lcd_write_data
;; This function is called by:
;;		_main
;; This function uses a non-reentrant model
;;
psect	text594
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\LM016.h"
	line	136
	global	__size_of_lcd_print_line2
	__size_of_lcd_print_line2	equ	__end_of_lcd_print_line2-_lcd_print_line2
	
_lcd_print_line2:	
	opt	stack 12
; Regs used in _lcd_print_line2: [wreg-fsr0h+status,2+status,0+pclath+cstack]
	line	137
	
l5282:	
;LM016.h: 137: lcd_write_command(0xc0 + postion);
	movf	(lcd_print_line2@postion),w
	addlw	0C0h
	fcall	_lcd_write_command
	line	138
;LM016.h: 138: lcd_write_command(0x0c);
	movlw	(0Ch)
	fcall	_lcd_write_command
	line	139
;LM016.h: 139: while(*charPointer != '\0') {
	goto	l5288
	line	140
	
l5284:	
;LM016.h: 140: lcd_write_data(*charPointer);
	movf	(lcd_print_line2@charPointer),w
	movwf	fsr0l
	movf	((lcd_print_line2@charPointer+1)),w
	movwf	fsr0h
	movf	indf0,w ;code access
	fcall	_lcd_write_data
	line	141
	
l5286:	
;LM016.h: 141: charPointer++;
	incf	(lcd_print_line2@charPointer),f
	skipnz
	incf	(lcd_print_line2@charPointer+1),f
	line	139
	
l5288:	
	movf	(lcd_print_line2@charPointer),w
	movwf	fsr0l
	movf	((lcd_print_line2@charPointer+1)),w
	movwf	fsr0h
	movf	indf0,w ;code access
	iorlw	0
	skipz
	goto	u2871
	goto	u2870
u2871:
	goto	l5284
u2870:
	line	143
	
l2521:	
	return
	opt stack 0
GLOBAL	__end_of_lcd_print_line2
	__end_of_lcd_print_line2:
;; =============== function _lcd_print_line2 ends ============

	signat	_lcd_print_line2,8312
	global	_lcd_print_line1
psect	text595,local,class=CODE,delta=2
global __ptext595
__ptext595:

;; *************** function _lcd_print_line1 *****************
;; Defined at:
;;		line 122 in file "C:\CTK\CTK80A 150928\CTK80A_Jas\LM016.h"
;; Parameters:    Size  Location     Type
;;  charPointer     2    7[COMMON] PTR const unsigned char 
;;		 -> ROM(4096), state7_2(17), state7_1(15), state6_2(15), 
;;		 -> state6_1(16), state5_2(16), state5_1(17), state4_1(17), 
;;		 -> state3_2(17), state3_1(17), state2_1(13), state1_2(16), 
;;		 -> state1_1(16), systemType1(11), checking(12), welcome(8), 
;;		 -> state2_4(13), state2_3(10), state2_2(12), 
;;  postion         1    9[COMMON] unsigned char 
;;  clear           1   10[COMMON] unsigned char 
;; Auto vars:     Size  Location     Type
;;		None
;; Return value:  Size  Location     Type
;;		None               void
;; Registers used:
;;		wreg, fsr0l, fsr0h, status,2, status,0, pclath, cstack
;; Tracked objects:
;;		On entry : 1F/0
;;		On exit  : 1F/0
;;		Unchanged: FFFE0/0
;; Data sizes:     COMMON   BANK0   BANK1   BANK2
;;      Params:         4       0       0       0
;;      Locals:         0       0       0       0
;;      Temps:          0       0       0       0
;;      Totals:         4       0       0       0
;;Total ram usage:        4 bytes
;; Hardware stack levels used:    1
;; Hardware stack levels required when called:    3
;; This function calls:
;;		_lcd_write_command
;;		_delay
;;		_lcd_write_data
;; This function is called by:
;;		_main
;; This function uses a non-reentrant model
;;
psect	text595
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\LM016.h"
	line	122
	global	__size_of_lcd_print_line1
	__size_of_lcd_print_line1	equ	__end_of_lcd_print_line1-_lcd_print_line1
	
_lcd_print_line1:	
	opt	stack 12
; Regs used in _lcd_print_line1: [wreg-fsr0h+status,2+status,0+pclath+cstack]
	line	123
	
l5272:	
;LM016.h: 123: if (clear) {
	movf	(lcd_print_line1@clear),w
	skipz
	goto	u2850
	goto	l2511
u2850:
	line	124
	
l5274:	
;LM016.h: 124: lcd_write_command(0x01);
	movlw	(01h)
	fcall	_lcd_write_command
	line	125
;LM016.h: 125: delay(1000);
	movlw	low(03E8h)
	movwf	(?_delay)
	movlw	high(03E8h)
	movwf	((?_delay))+1
	fcall	_delay
	line	126
	
l2511:	
	line	127
;LM016.h: 126: }
;LM016.h: 127: lcd_write_command(0x0c);
	movlw	(0Ch)
	fcall	_lcd_write_command
	line	128
;LM016.h: 128: lcd_write_command(0x80 + postion);
	movf	(lcd_print_line1@postion),w
	addlw	080h
	fcall	_lcd_write_command
	line	129
;LM016.h: 129: while(*charPointer != '\0') {
	goto	l5280
	line	130
	
l5276:	
;LM016.h: 130: lcd_write_data(*charPointer);
	movf	(lcd_print_line1@charPointer),w
	movwf	fsr0l
	movf	((lcd_print_line1@charPointer+1)),w
	movwf	fsr0h
	movf	indf0,w ;code access
	fcall	_lcd_write_data
	line	131
	
l5278:	
;LM016.h: 131: charPointer++;
	incf	(lcd_print_line1@charPointer),f
	skipnz
	incf	(lcd_print_line1@charPointer+1),f
	line	129
	
l5280:	
	movf	(lcd_print_line1@charPointer),w
	movwf	fsr0l
	movf	((lcd_print_line1@charPointer+1)),w
	movwf	fsr0h
	movf	indf0,w ;code access
	iorlw	0
	skipz
	goto	u2861
	goto	u2860
u2861:
	goto	l5276
u2860:
	line	133
	
l2515:	
	return
	opt stack 0
GLOBAL	__end_of_lcd_print_line1
	__end_of_lcd_print_line1:
;; =============== function _lcd_print_line1 ends ============

	signat	_lcd_print_line1,12408
	global	_lcd_init
psect	text596,local,class=CODE,delta=2
global __ptext596
__ptext596:

;; *************** function _lcd_init *****************
;; Defined at:
;;		line 73 in file "C:\CTK\CTK80A 150928\CTK80A_Jas\LM016.h"
;; Parameters:    Size  Location     Type
;;		None
;; Auto vars:     Size  Location     Type
;;		None
;; Return value:  Size  Location     Type
;;		None               void
;; Registers used:
;;		wreg, status,2, status,0, pclath, cstack
;; Tracked objects:
;;		On entry : 17F/0
;;		On exit  : 17F/0
;;		Unchanged: FFE80/0
;; Data sizes:     COMMON   BANK0   BANK1   BANK2
;;      Params:         0       0       0       0
;;      Locals:         0       0       0       0
;;      Temps:          0       0       0       0
;;      Totals:         0       0       0       0
;;Total ram usage:        0 bytes
;; Hardware stack levels used:    1
;; Hardware stack levels required when called:    3
;; This function calls:
;;		_lcd_write_command
;;		_delay
;; This function is called by:
;;		_main
;; This function uses a non-reentrant model
;;
psect	text596
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\LM016.h"
	line	73
	global	__size_of_lcd_init
	__size_of_lcd_init	equ	__end_of_lcd_init-_lcd_init
	
_lcd_init:	
	opt	stack 12
; Regs used in _lcd_init: [wreg+status,2+status,0+pclath+cstack]
	line	74
	
l5270:	
;LM016.h: 74: lcd_write_command(0x38);
	movlw	(038h)
	fcall	_lcd_write_command
	line	75
;LM016.h: 75: lcd_write_command(0x0c);
	movlw	(0Ch)
	fcall	_lcd_write_command
	line	76
;LM016.h: 76: lcd_write_command(0x06);
	movlw	(06h)
	fcall	_lcd_write_command
	line	77
;LM016.h: 77: lcd_write_command(0x01);
	movlw	(01h)
	fcall	_lcd_write_command
	line	78
;LM016.h: 78: delay(1000);
	movlw	low(03E8h)
	movwf	(?_delay)
	movlw	high(03E8h)
	movwf	((?_delay))+1
	fcall	_delay
	line	79
	
l2497:	
	return
	opt stack 0
GLOBAL	__end_of_lcd_init
	__end_of_lcd_init:
;; =============== function _lcd_init ends ============

	signat	_lcd_init,88
	global	_lcd_move_char
psect	text597,local,class=CODE,delta=2
global __ptext597
__ptext597:

;; *************** function _lcd_move_char *****************
;; Defined at:
;;		line 146 in file "C:\CTK\CTK80A 150928\CTK80A_Jas\LM016.h"
;; Parameters:    Size  Location     Type
;;  postion         1    wreg     unsigned char 
;; Auto vars:     Size  Location     Type
;;  postion         1    7[COMMON] unsigned char 
;;  i               1    8[COMMON] unsigned char 
;; Return value:  Size  Location     Type
;;		None               void
;; Registers used:
;;		wreg, status,2, status,0, pclath, cstack
;; Tracked objects:
;;		On entry : 1F/0
;;		On exit  : 1F/0
;;		Unchanged: FFFE0/0
;; Data sizes:     COMMON   BANK0   BANK1   BANK2
;;      Params:         0       0       0       0
;;      Locals:         2       0       0       0
;;      Temps:          0       0       0       0
;;      Totals:         2       0       0       0
;;Total ram usage:        2 bytes
;; Hardware stack levels used:    1
;; Hardware stack levels required when called:    3
;; This function calls:
;;		_lcd_write_command
;; This function is called by:
;;		_SwitchBatteryState
;;		_main
;; This function uses a non-reentrant model
;;
psect	text597
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\LM016.h"
	line	146
	global	__size_of_lcd_move_char
	__size_of_lcd_move_char	equ	__end_of_lcd_move_char-_lcd_move_char
	
_lcd_move_char:	
	opt	stack 12
; Regs used in _lcd_move_char: [wreg+status,2+status,0+pclath+cstack]
;lcd_move_char@postion stored from wreg
	movwf	(lcd_move_char@postion)
	line	147
	
l5258:	
;LM016.h: 147: unsigned char i = 0;
	clrf	(lcd_move_char@i)
	line	148
	
l5260:	
;LM016.h: 148: lcd_write_command(0x0f);
	movlw	(0Fh)
	fcall	_lcd_write_command
	line	149
	
l5262:	
;LM016.h: 149: lcd_write_command(0x02);
	movlw	(02h)
	fcall	_lcd_write_command
	line	150
;LM016.h: 150: while(i < postion) {
	goto	l5268
	line	151
	
l5264:	
;LM016.h: 151: lcd_write_command(0x14);
	movlw	(014h)
	fcall	_lcd_write_command
	line	152
	
l5266:	
;LM016.h: 152: i++;
	incf	(lcd_move_char@i),f
	line	150
	
l5268:	
	movf	(lcd_move_char@postion),w
	subwf	(lcd_move_char@i),w
	skipc
	goto	u2841
	goto	u2840
u2841:
	goto	l5264
u2840:
	line	155
	
l2527:	
	return
	opt stack 0
GLOBAL	__end_of_lcd_move_char
	__end_of_lcd_move_char:
;; =============== function _lcd_move_char ends ============

	signat	_lcd_move_char,4216
	global	_getADValue
psect	text598,local,class=CODE,delta=2
global __ptext598
__ptext598:

;; *************** function _getADValue *****************
;; Defined at:
;;		line 372 in file "C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.h"
;; Parameters:    Size  Location     Type
;;  channel         1    wreg     unsigned char 
;; Auto vars:     Size  Location     Type
;;  channel         1    4[BANK0 ] unsigned char 
;;  AD_OneResult    2   13[BANK0 ] unsigned int 
;;  i               2   11[BANK0 ] unsigned int 
;;  min             2    9[BANK0 ] unsigned int 
;;  max             2    7[BANK0 ] unsigned int 
;;  AD_Result       2    5[BANK0 ] unsigned int 
;; Return value:  Size  Location     Type
;;                  2    0[BANK0 ] unsigned int 
;; Registers used:
;;		wreg, status,2, status,0, pclath, cstack
;; Tracked objects:
;;		On entry : 1D/0
;;		On exit  : 1F/0
;;		Unchanged: FFFE0/0
;; Data sizes:     COMMON   BANK0   BANK1   BANK2
;;      Params:         0       2       0       0
;;      Locals:         0      11       0       0
;;      Temps:          0       2       0       0
;;      Totals:         0      15       0       0
;;Total ram usage:       15 bytes
;; Hardware stack levels used:    1
;; Hardware stack levels required when called:    3
;; This function calls:
;;		_getADValueOneTime
;; This function is called by:
;;		_GetBatteryVoltage
;;		_GetSolarPanelVoltage
;;		_GetLoadCurrentVoltage
;; This function uses a non-reentrant model
;;
psect	text598
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.h"
	line	372
	global	__size_of_getADValue
	__size_of_getADValue	equ	__end_of_getADValue-_getADValue
	
_getADValue:	
	opt	stack 10
; Regs used in _getADValue: [wreg+status,2+status,0+pclath+cstack]
;getADValue@channel stored from wreg
	movlb 0	; select bank0
	movwf	(getADValue@channel)
	line	373
	
l5230:	
;mypic.h: 373: unsigned int AD_Result = 0;
	clrf	(getADValue@AD_Result)
	clrf	(getADValue@AD_Result+1)
	line	377
;mypic.h: 375: unsigned int max;
;mypic.h: 376: unsigned int min;
;mypic.h: 377: unsigned int i = 0 ;
	clrf	(getADValue@i)
	clrf	(getADValue@i+1)
	line	378
;mypic.h: 378: for (i = 0 ; i < 10; i++){
	clrf	(getADValue@i)
	clrf	(getADValue@i+1)
	line	379
	
l5236:	
;mypic.h: 379: AD_OneResult = getADValueOneTime(channel);
	movf	(getADValue@channel),w
	fcall	_getADValueOneTime
	movf	(1+(?_getADValueOneTime)),w
	movlb 0	; select bank0
	movwf	(getADValue@AD_OneResult+1)
	movf	(0+(?_getADValueOneTime)),w
	movwf	(getADValue@AD_OneResult)
	line	380
	
l5238:	
;mypic.h: 380: AD_Result += AD_OneResult;
	movf	(getADValue@AD_OneResult),w
	addwf	(getADValue@AD_Result),f
	movf	(getADValue@AD_OneResult+1),w
	addwfc	(getADValue@AD_Result+1),f
	line	381
	
l5240:	
;mypic.h: 381: max = (max > AD_OneResult ? max : AD_OneResult);
	movf	(getADValue@max+1),w
	subwf	(getADValue@AD_OneResult+1),w
	skipz
	goto	u2815
	movf	(getADValue@max),w
	subwf	(getADValue@AD_OneResult),w
u2815:
	skipc
	goto	u2811
	goto	u2810
u2811:
	goto	l5244
u2810:
	
l5242:	
	movf	(getADValue@AD_OneResult+1),w
	movwf	(getADValue@max+1)
	movf	(getADValue@AD_OneResult),w
	movwf	(getADValue@max)
	line	382
	
l5244:	
;mypic.h: 382: min = (min < AD_OneResult ? min : AD_OneResult);
	movf	(getADValue@AD_OneResult+1),w
	subwf	(getADValue@min+1),w
	skipz
	goto	u2825
	movf	(getADValue@AD_OneResult),w
	subwf	(getADValue@min),w
u2825:
	skipc
	goto	u2821
	goto	u2820
u2821:
	goto	l5248
u2820:
	
l5246:	
	movf	(getADValue@AD_OneResult+1),w
	movwf	(getADValue@min+1)
	movf	(getADValue@AD_OneResult),w
	movwf	(getADValue@min)
	line	378
	
l5248:	
	incf	(getADValue@i),f
	skipnz
	incf	(getADValue@i+1),f
	
l5250:	
	movlw	high(0Ah)
	subwf	(getADValue@i+1),w
	movlw	low(0Ah)
	skipnz
	subwf	(getADValue@i),w
	skipc
	goto	u2831
	goto	u2830
u2831:
	goto	l5236
u2830:
	line	384
	
l5252:	
;mypic.h: 383: }
;mypic.h: 384: return ((AD_Result - max - min) >> 3);
	movf	(getADValue@min),w
	addwf	(getADValue@max),w
	movwf	(??_getADValue+0)+0
	movf	(getADValue@min+1),w
	addwfc	(getADValue@max+1),w
	movwf	1+(??_getADValue+0)+0
	comf	(??_getADValue+0)+0,f
	comf	(??_getADValue+0)+1,f
	incf	(??_getADValue+0)+0,f
	skipnz
	incf	(??_getADValue+0)+1,f
	movf	0+(??_getADValue+0)+0,w
	movwf	(?_getADValue)
	movf	1+(??_getADValue+0)+0,w
	movwf	(?_getADValue+1)
	
l5254:	
	movf	(getADValue@AD_Result),w
	addwf	(?_getADValue),f
	movf	(getADValue@AD_Result+1),w
	addwfc	(?_getADValue+1),f
	lsrf	(?_getADValue+1),f
	rrf	(?_getADValue),f
	lsrf	(?_getADValue+1),f
	rrf	(?_getADValue),f
	lsrf	(?_getADValue+1),f
	rrf	(?_getADValue),f
	line	385
	
l2272:	
	return
	opt stack 0
GLOBAL	__end_of_getADValue
	__end_of_getADValue:
;; =============== function _getADValue ends ============

	signat	_getADValue,4218
	global	_readFromEEPROM
psect	text599,local,class=CODE,delta=2
global __ptext599
__ptext599:

;; *************** function _readFromEEPROM *****************
;; Defined at:
;;		line 1216 in file "C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.h"
;; Parameters:    Size  Location     Type
;;  type            1    wreg     unsigned char 
;; Auto vars:     Size  Location     Type
;;  type            1   31[BANK0 ] unsigned char 
;;  read_data       2   29[BANK0 ] unsigned int 
;;  h_byte          1   28[BANK0 ] unsigned char 
;;  l_byte          1   27[BANK0 ] unsigned char 
;; Return value:  Size  Location     Type
;;                  2   12[COMMON] unsigned int 
;; Registers used:
;;		wreg, fsr0l, fsr0h, status,2, status,0, pclath, cstack
;; Tracked objects:
;;		On entry : 17F/0
;;		On exit  : 17F/0
;;		Unchanged: FFE80/0
;; Data sizes:     COMMON   BANK0   BANK1   BANK2
;;      Params:         2       0       0       0
;;      Locals:         0       5       0       0
;;      Temps:          0       0       0       0
;;      Totals:         2       5       0       0
;;Total ram usage:        7 bytes
;; Hardware stack levels used:    1
;; Hardware stack levels required when called:    3
;; This function calls:
;;		_ReadEE
;;		___lwtoft
;;		___ftdiv
;;		___fttol
;; This function is called by:
;;		_load_system_state
;; This function uses a non-reentrant model
;;
psect	text599
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.h"
	line	1216
	global	__size_of_readFromEEPROM
	__size_of_readFromEEPROM	equ	__end_of_readFromEEPROM-_readFromEEPROM
	
_readFromEEPROM:	
	opt	stack 11
; Regs used in _readFromEEPROM: [wreg-fsr0h+status,2+status,0+pclath+cstack]
;readFromEEPROM@type stored from wreg
	line	1218
	movwf	(readFromEEPROM@type)
	line	1217
	
l5206:	
	line	1218
;mypic.h: 1218: unsigned char h_byte = 0;
	clrf	(readFromEEPROM@h_byte)
	line	1219
;mypic.h: 1219: unsigned int read_data = 0;
	clrf	(readFromEEPROM@read_data)
	clrf	(readFromEEPROM@read_data+1)
	line	1220
;mypic.h: 1220: switch(type) {
	goto	l5224
	line	1224
	
l2485:	
	line	1228
	
l5208:	
;mypic.h: 1222: case 2:
;mypic.h: 1223: case 3:
;mypic.h: 1224: case 5:
;mypic.h: 1225: case 6:
;mypic.h: 1226: case 7:
;mypic.h: 1227: case 8:
;mypic.h: 1228: l_byte = ReadEE(type * 2);
	lslf	(readFromEEPROM@type),w
	fcall	_ReadEE
	movlb 0	; select bank0
	movwf	(readFromEEPROM@l_byte)
	line	1229
	
l5210:	
;mypic.h: 1229: h_byte = ReadEE(type * 2 + 1);
	setc
	rlf	(readFromEEPROM@type),w
	fcall	_ReadEE
	movlb 0	; select bank0
	movwf	(readFromEEPROM@h_byte)
	line	1230
	
l5212:	
;mypic.h: 1230: read_data = l_byte + ( ((unsigned int)h_byte) << 8);
	movf	(readFromEEPROM@h_byte),w
	movwf	(readFromEEPROM@read_data)
	clrf	(readFromEEPROM@read_data+1)
	
l5214:	
	movf	(readFromEEPROM@read_data),w
	movwf	(readFromEEPROM@read_data+1)
	clrf	(readFromEEPROM@read_data)
	
l5216:	
	movf	(readFromEEPROM@l_byte),w
	addwf	(readFromEEPROM@read_data),f
	skipnc
	incf	(readFromEEPROM@read_data+1),f
	line	1231
	
l5218:	
;mypic.h: 1231: read_data = (unsigned int) (read_data / 2.3684f);
	movf	(readFromEEPROM@read_data+1),w
	movwf	(?___lwtoft+1)
	movf	(readFromEEPROM@read_data),w
	movwf	(?___lwtoft)
	fcall	___lwtoft
	movf	(0+(?___lwtoft)),w
	movwf	0+(?___ftdiv)+03h
	movf	(1+(?___lwtoft)),w
	movwf	1+(?___ftdiv)+03h
	movf	(2+(?___lwtoft)),w
	movwf	2+(?___ftdiv)+03h
	movlw	0x94
	movwf	(?___ftdiv)
	movlw	0x17
	movwf	(?___ftdiv+1)
	movlw	0x40
	movwf	(?___ftdiv+2)
	fcall	___ftdiv
	movf	(0+(?___ftdiv)),w
	movwf	(?___fttol)
	movf	(1+(?___ftdiv)),w
	movwf	(?___fttol+1)
	movf	(2+(?___ftdiv)),w
	movwf	(?___fttol+2)
	fcall	___fttol
	movf	1+(((0+(?___fttol)))),w
	movwf	(readFromEEPROM@read_data+1)
	movf	0+(((0+(?___fttol)))),w
	movwf	(readFromEEPROM@read_data)
	line	1232
;mypic.h: 1232: break;
	goto	l5226
	line	1236
	
l5220:	
;mypic.h: 1234: case 10:
;mypic.h: 1235: case 4:
;mypic.h: 1236: read_data = ReadEE(type * 2);
	lslf	(readFromEEPROM@type),w
	fcall	_ReadEE
	movlb 0	; select bank0
	movwf	(readFromEEPROM@read_data)
	clrf	(readFromEEPROM@read_data+1)
	goto	l5226
	line	1220
	
l5224:	
	movf	(readFromEEPROM@type),w
	; Switch size 1, requested type "space"
; Number of cases is 10, Range of values is 1 to 10
; switch strategies available:
; Name         Bytes Cycles
; simple_byte    31    16 (average)
; direct_byte    39    19 (fixed)
;	Chosen strategy is simple_byte

	xorlw	1^0	; case 1
	skipnz
	goto	l2485
	xorlw	2^1	; case 2
	skipnz
	goto	l5208
	xorlw	3^2	; case 3
	skipnz
	goto	l5208
	xorlw	4^3	; case 4
	skipnz
	goto	l5220
	xorlw	5^4	; case 5
	skipnz
	goto	l5208
	xorlw	6^5	; case 6
	skipnz
	goto	l5208
	xorlw	7^6	; case 7
	skipnz
	goto	l5208
	xorlw	8^7	; case 8
	skipnz
	goto	l5208
	xorlw	9^8	; case 9
	skipnz
	goto	l5220
	xorlw	10^9	; case 10
	skipnz
	goto	l5220
	goto	l5226

	line	1240
	
l5226:	
;mypic.h: 1240: return read_data;
	movf	(readFromEEPROM@read_data+1),w
	movwf	(?_readFromEEPROM+1)
	movf	(readFromEEPROM@read_data),w
	movwf	(?_readFromEEPROM)
	line	1241
	
l2494:	
	return
	opt stack 0
GLOBAL	__end_of_readFromEEPROM
	__end_of_readFromEEPROM:
;; =============== function _readFromEEPROM ends ============

	signat	_readFromEEPROM,4218
	global	___lwtoft
psect	text600,local,class=CODE,delta=2
global __ptext600
__ptext600:

;; *************** function ___lwtoft *****************
;; Defined at:
;;		line 29 in file "C:\Program Files\HI-TECH Software\PICC\9.80\sources\lwtoft.c"
;; Parameters:    Size  Location     Type
;;  c               2    9[BANK0 ] unsigned int 
;; Auto vars:     Size  Location     Type
;;		None
;; Return value:  Size  Location     Type
;;                  3    9[BANK0 ] float 
;; Registers used:
;;		wreg, status,2, status,0, pclath, cstack
;; Tracked objects:
;;		On entry : 17F/0
;;		On exit  : 17F/0
;;		Unchanged: FFE80/0
;; Data sizes:     COMMON   BANK0   BANK1   BANK2
;;      Params:         0       3       0       0
;;      Locals:         0       0       0       0
;;      Temps:          0       0       0       0
;;      Totals:         0       3       0       0
;;Total ram usage:        3 bytes
;; Hardware stack levels used:    1
;; Hardware stack levels required when called:    2
;; This function calls:
;;		___ftpack
;; This function is called by:
;;		_readFromEEPROM
;; This function uses a non-reentrant model
;;
psect	text600
	file	"C:\Program Files\HI-TECH Software\PICC\9.80\sources\lwtoft.c"
	line	29
	global	__size_of___lwtoft
	__size_of___lwtoft	equ	__end_of___lwtoft-___lwtoft
	
___lwtoft:	
	opt	stack 11
; Regs used in ___lwtoft: [wreg+status,2+status,0+pclath+cstack]
	line	30
	
l5202:	
	movf	(___lwtoft@c),w
	movwf	(?___ftpack)
	movf	(___lwtoft@c+1),w
	movwf	(?___ftpack+1)
	clrf	(?___ftpack+2)
	movlw	(08Eh)
	movwf	0+(?___ftpack)+03h
	clrf	0+(?___ftpack)+04h
	fcall	___ftpack
	movf	(0+(?___ftpack)),w
	movwf	(?___lwtoft)
	movf	(1+(?___ftpack)),w
	movwf	(?___lwtoft+1)
	movf	(2+(?___ftpack)),w
	movwf	(?___lwtoft+2)
	line	31
	
l2903:	
	return
	opt stack 0
GLOBAL	__end_of___lwtoft
	__end_of___lwtoft:
;; =============== function ___lwtoft ends ============

	signat	___lwtoft,4219
	global	___ftdiv
psect	text601,local,class=CODE,delta=2
global __ptext601
__ptext601:

;; *************** function ___ftdiv *****************
;; Defined at:
;;		line 50 in file "C:\Program Files\HI-TECH Software\PICC\9.80\sources\ftdiv.c"
;; Parameters:    Size  Location     Type
;;  f2              3   12[BANK0 ] float 
;;  f1              3   15[BANK0 ] float 
;; Auto vars:     Size  Location     Type
;;  f3              3   22[BANK0 ] float 
;;  sign            1   26[BANK0 ] unsigned char 
;;  exp             1   25[BANK0 ] unsigned char 
;;  cntr            1   21[BANK0 ] unsigned char 
;; Return value:  Size  Location     Type
;;                  3   12[BANK0 ] float 
;; Registers used:
;;		wreg, status,2, status,0, pclath, cstack
;; Tracked objects:
;;		On entry : 17F/0
;;		On exit  : 17F/0
;;		Unchanged: FFE80/0
;; Data sizes:     COMMON   BANK0   BANK1   BANK2
;;      Params:         0       6       0       0
;;      Locals:         0       6       0       0
;;      Temps:          0       3       0       0
;;      Totals:         0      15       0       0
;;Total ram usage:       15 bytes
;; Hardware stack levels used:    1
;; Hardware stack levels required when called:    2
;; This function calls:
;;		___ftpack
;; This function is called by:
;;		_readFromEEPROM
;; This function uses a non-reentrant model
;;
psect	text601
	file	"C:\Program Files\HI-TECH Software\PICC\9.80\sources\ftdiv.c"
	line	50
	global	__size_of___ftdiv
	__size_of___ftdiv	equ	__end_of___ftdiv-___ftdiv
	
___ftdiv:	
	opt	stack 11
; Regs used in ___ftdiv: [wreg+status,2+status,0+pclath+cstack]
	line	55
	
l5158:	
	movf	(___ftdiv@f1),w
	movwf	((??___ftdiv+0)+0)
	movf	(___ftdiv@f1+1),w
	movwf	((??___ftdiv+0)+0+1)
	movf	(___ftdiv@f1+2),w
	movwf	((??___ftdiv+0)+0+2)
	clrc
	rlf	(??___ftdiv+0)+1,w
	rlf	(??___ftdiv+0)+2,w
	movwf	(___ftdiv@exp)
	movf	((___ftdiv@exp)),f
	skipz
	goto	u2771
	goto	u2770
u2771:
	goto	l5164
u2770:
	line	56
	
l5160:	
	clrf	(?___ftdiv)
	clrf	(?___ftdiv+1)
	clrf	(?___ftdiv+2)
	goto	l2769
	line	57
	
l5164:	
	movf	(___ftdiv@f2),w
	movwf	((??___ftdiv+0)+0)
	movf	(___ftdiv@f2+1),w
	movwf	((??___ftdiv+0)+0+1)
	movf	(___ftdiv@f2+2),w
	movwf	((??___ftdiv+0)+0+2)
	clrc
	rlf	(??___ftdiv+0)+1,w
	rlf	(??___ftdiv+0)+2,w
	movwf	(___ftdiv@sign)
	movf	((___ftdiv@sign)),f
	skipz
	goto	u2781
	goto	u2780
u2781:
	goto	l2770
u2780:
	line	58
	
l5166:	
	clrf	(?___ftdiv)
	clrf	(?___ftdiv+1)
	clrf	(?___ftdiv+2)
	goto	l2769
	
l2770:	
	line	59
	clrf	(___ftdiv@f3)
	clrf	(___ftdiv@f3+1)
	clrf	(___ftdiv@f3+2)
	line	60
	
l5170:	
	movlw	(089h)
	addwf	(___ftdiv@sign),w
	movwf	(??___ftdiv+0)+0
	movf	0+(??___ftdiv+0)+0,w
	subwf	(___ftdiv@exp),f
	line	61
	
l5172:	
	movf	0+(((___ftdiv@f1))+2),w
	movwf	(___ftdiv@sign)
	line	62
	
l5174:	
	movf	0+(((___ftdiv@f2))+2),w
	xorwf	(___ftdiv@sign),f
	line	63
	
l5176:	
	movlw	(080h)
	andwf	(___ftdiv@sign),f
	line	64
	
l5178:	
	bsf	(___ftdiv@f1)+(15/8),(15)&7
	line	65
	
l5180:	
	movlw	0FFh
	andwf	(___ftdiv@f1),f
	movlw	0FFh
	andwf	(___ftdiv@f1+1),f
	movlw	0
	andwf	(___ftdiv@f1+2),f
	line	66
	
l5182:	
	bsf	(___ftdiv@f2)+(15/8),(15)&7
	line	67
	
l5184:	
	movlw	0FFh
	andwf	(___ftdiv@f2),f
	movlw	0FFh
	andwf	(___ftdiv@f2+1),f
	movlw	0
	andwf	(___ftdiv@f2+2),f
	line	68
	
l5186:	
	movlw	(018h)
	movwf	(___ftdiv@cntr)
	line	70
	
l5188:	
	lslf	(___ftdiv@f3),f
	rlf	(___ftdiv@f3+1),f
	rlf	(___ftdiv@f3+2),f
	line	71
	movf	(___ftdiv@f2+2),w
	subwf	(___ftdiv@f1+2),w
	skipz
	goto	u2795
	movf	(___ftdiv@f2+1),w
	subwf	(___ftdiv@f1+1),w
	skipz
	goto	u2795
	movf	(___ftdiv@f2),w
	subwf	(___ftdiv@f1),w
u2795:
	skipc
	goto	u2791
	goto	u2790
u2791:
	goto	l5194
u2790:
	line	72
	
l5190:	
	movf	(___ftdiv@f2),w
	subwf	(___ftdiv@f1),f
	movf	(___ftdiv@f2+1),w
	subwfb	(___ftdiv@f1+1),f
	movf	(___ftdiv@f2+2),w
	subwfb	(___ftdiv@f1+2),f
	line	73
	
l5192:	
	bsf	(___ftdiv@f3)+(0/8),(0)&7
	line	75
	
l5194:	
	lslf	(___ftdiv@f1),f
	rlf	(___ftdiv@f1+1),f
	rlf	(___ftdiv@f1+2),f
	line	76
	
l5196:	
	decfsz	(___ftdiv@cntr),f
	goto	u2801
	goto	u2800
u2801:
	goto	l5188
u2800:
	line	77
	
l5198:	
	movf	(___ftdiv@f3),w
	movwf	(?___ftpack)
	movf	(___ftdiv@f3+1),w
	movwf	(?___ftpack+1)
	movf	(___ftdiv@f3+2),w
	movwf	(?___ftpack+2)
	movf	(___ftdiv@exp),w
	movwf	0+(?___ftpack)+03h
	movf	(___ftdiv@sign),w
	movwf	0+(?___ftpack)+04h
	fcall	___ftpack
	movf	(0+(?___ftpack)),w
	movwf	(?___ftdiv)
	movf	(1+(?___ftpack)),w
	movwf	(?___ftdiv+1)
	movf	(2+(?___ftpack)),w
	movwf	(?___ftdiv+2)
	line	78
	
l2769:	
	return
	opt stack 0
GLOBAL	__end_of___ftdiv
	__end_of___ftdiv:
;; =============== function ___ftdiv ends ============

	signat	___ftdiv,8315
	global	_lcd_write_command
psect	text602,local,class=CODE,delta=2
global __ptext602
__ptext602:

;; *************** function _lcd_write_command *****************
;; Defined at:
;;		line 82 in file "C:\CTK\CTK80A 150928\CTK80A_Jas\LM016.h"
;; Parameters:    Size  Location     Type
;;  command         1    wreg     unsigned char 
;; Auto vars:     Size  Location     Type
;;  command         1    6[COMMON] unsigned char 
;; Return value:  Size  Location     Type
;;		None               void
;; Registers used:
;;		wreg, status,2, status,0, pclath, cstack
;; Tracked objects:
;;		On entry : 1F/0
;;		On exit  : 1F/0
;;		Unchanged: FFFE0/0
;; Data sizes:     COMMON   BANK0   BANK1   BANK2
;;      Params:         0       0       0       0
;;      Locals:         1       0       0       0
;;      Temps:          0       0       0       0
;;      Totals:         1       0       0       0
;;Total ram usage:        1 bytes
;; Hardware stack levels used:    1
;; Hardware stack levels required when called:    2
;; This function calls:
;;		_delay
;; This function is called by:
;;		_lcd_init
;;		_lcd_print_line1
;;		_lcd_print_line2
;;		_lcd_move_char
;;		_main
;; This function uses a non-reentrant model
;;
psect	text602
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\LM016.h"
	line	82
	global	__size_of_lcd_write_command
	__size_of_lcd_write_command	equ	__end_of_lcd_write_command-_lcd_write_command
	
_lcd_write_command:	
	opt	stack 12
; Regs used in _lcd_write_command: [wreg+status,2+status,0+pclath+cstack]
;lcd_write_command@command stored from wreg
	movwf	(lcd_write_command@command)
	line	83
	
l5148:	
;LM016.h: 83: RC1 = 0;
	bcf	(113/8),(113)&7
	line	84
;LM016.h: 84: RC2 = 0;
	bcf	(114/8),(114)&7
	line	85
	
l5150:	
;LM016.h: 85: PORTD = command;
	movf	(lcd_write_command@command),w
	movwf	(15)	;volatile
	line	89
	
l5152:	
;LM016.h: 89: delay(50);
	movlw	032h
	movwf	(?_delay)
	clrf	(?_delay+1)
	fcall	_delay
	line	90
	
l5154:	
;LM016.h: 90: RC3 = 1;
	bsf	(115/8),(115)&7
	line	91
	
l5156:	
;LM016.h: 91: RC3 = 0;
	bcf	(115/8),(115)&7
	line	92
	
l2500:	
	return
	opt stack 0
GLOBAL	__end_of_lcd_write_command
	__end_of_lcd_write_command:
;; =============== function _lcd_write_command ends ============

	signat	_lcd_write_command,4216
	global	_lcd_write_data
psect	text603,local,class=CODE,delta=2
global __ptext603
__ptext603:

;; *************** function _lcd_write_data *****************
;; Defined at:
;;		line 95 in file "C:\CTK\CTK80A 150928\CTK80A_Jas\LM016.h"
;; Parameters:    Size  Location     Type
;;  data            1    wreg     unsigned char 
;; Auto vars:     Size  Location     Type
;;  data            1    6[COMMON] unsigned char 
;; Return value:  Size  Location     Type
;;		None               void
;; Registers used:
;;		wreg, status,2, status,0, pclath, cstack
;; Tracked objects:
;;		On entry : 1F/0
;;		On exit  : 1F/0
;;		Unchanged: FFFE0/0
;; Data sizes:     COMMON   BANK0   BANK1   BANK2
;;      Params:         0       0       0       0
;;      Locals:         1       0       0       0
;;      Temps:          0       0       0       0
;;      Totals:         1       0       0       0
;;Total ram usage:        1 bytes
;; Hardware stack levels used:    1
;; Hardware stack levels required when called:    2
;; This function calls:
;;		_delay
;; This function is called by:
;;		_SwitchBatteryState
;;		_lcd_print_line1
;;		_lcd_print_line2
;; This function uses a non-reentrant model
;;
psect	text603
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\LM016.h"
	line	95
	global	__size_of_lcd_write_data
	__size_of_lcd_write_data	equ	__end_of_lcd_write_data-_lcd_write_data
	
_lcd_write_data:	
	opt	stack 12
; Regs used in _lcd_write_data: [wreg+status,2+status,0+pclath+cstack]
;lcd_write_data@data stored from wreg
	movwf	(lcd_write_data@data)
	line	96
	
l5138:	
;LM016.h: 96: RC1 = 1;
	bsf	(113/8),(113)&7
	line	97
;LM016.h: 97: RC2 = 0;
	bcf	(114/8),(114)&7
	line	98
	
l5140:	
;LM016.h: 98: PORTD = data;
	movf	(lcd_write_data@data),w
	movwf	(15)	;volatile
	line	102
	
l5142:	
;LM016.h: 102: delay(30);
	movlw	01Eh
	movwf	(?_delay)
	clrf	(?_delay+1)
	fcall	_delay
	line	103
	
l5144:	
;LM016.h: 103: RC3 = 1;
	bsf	(115/8),(115)&7
	line	104
	
l5146:	
;LM016.h: 104: RC3 = 0;
	bcf	(115/8),(115)&7
	line	105
	
l2503:	
	return
	opt stack 0
GLOBAL	__end_of_lcd_write_data
	__end_of_lcd_write_data:
;; =============== function _lcd_write_data ends ============

	signat	_lcd_write_data,4216
	global	_getADValueOneTime
psect	text604,local,class=CODE,delta=2
global __ptext604
__ptext604:

;; *************** function _getADValueOneTime *****************
;; Defined at:
;;		line 358 in file "C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.h"
;; Parameters:    Size  Location     Type
;;  channel         1    wreg     unsigned char 
;; Auto vars:     Size  Location     Type
;;  channel         1   10[COMMON] unsigned char 
;;  AD_Result       2   11[COMMON] unsigned int 
;; Return value:  Size  Location     Type
;;                  2    6[COMMON] unsigned int 
;; Registers used:
;;		wreg, status,2, status,0, pclath, cstack
;; Tracked objects:
;;		On entry : 1F/0
;;		On exit  : 1F/1
;;		Unchanged: FFFE0/0
;; Data sizes:     COMMON   BANK0   BANK1   BANK2
;;      Params:         2       0       0       0
;;      Locals:         3       0       0       0
;;      Temps:          2       0       0       0
;;      Totals:         7       0       0       0
;;Total ram usage:        7 bytes
;; Hardware stack levels used:    1
;; Hardware stack levels required when called:    2
;; This function calls:
;;		_Delay
;; This function is called by:
;;		_getADValue
;; This function uses a non-reentrant model
;;
psect	text604
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.h"
	line	358
	global	__size_of_getADValueOneTime
	__size_of_getADValueOneTime	equ	__end_of_getADValueOneTime-_getADValueOneTime
	
_getADValueOneTime:	
	opt	stack 10
; Regs used in _getADValueOneTime: [wreg+status,2+status,0+pclath+cstack]
;getADValueOneTime@channel stored from wreg
	line	360
	movwf	(getADValueOneTime@channel)
	line	359
	
l5120:	
	line	360
;mypic.h: 360: FVRCON = 0b00000000;
	movlb 2	; select bank2
	clrf	(279)^0100h	;volatile
	line	361
	
l5122:	
;mypic.h: 361: ADCON0 = (channel & 0xff);
	movf	(getADValueOneTime@channel),w
	movlb 1	; select bank1
	movwf	(157)^080h	;volatile
	line	362
	
l5124:	
;mypic.h: 362: ADCON1 = 0b10010000;;
	movlw	(090h)
	movwf	(158)^080h	;volatile
	line	363
	
l5126:	
;mypic.h: 363: Delay(20);
	movlw	014h
	movwf	(?_Delay)
	clrf	(?_Delay+1)
	fcall	_Delay
	line	364
	
l5128:	
;mypic.h: 364: ADGO = 1;
	bsf	(1257/8)^080h,(1257)&7
	line	365
;mypic.h: 365: while(ADGO);
	
l2256:	
	btfsc	(1257/8)^080h,(1257)&7
	goto	u2751
	goto	u2750
u2751:
	goto	l2256
u2750:
	line	366
	
l5130:	
;mypic.h: 366: AD_Result = ADRESL & 0x00FF;
	movf	(155)^080h,w	;volatile
	movwf	(getADValueOneTime@AD_Result)
	clrf	(getADValueOneTime@AD_Result+1)
	line	367
;mypic.h: 367: AD_Result |= ADRESH <<8 ;
	movf	(156)^080h,w	;volatile
	movwf	(??_getADValueOneTime+0)+0
	clrf	(??_getADValueOneTime+0)+0+1
	movlw	08h
u2765:
	lslf	(??_getADValueOneTime+0)+0,f
	rlf	(??_getADValueOneTime+0)+1,f
	decfsz	wreg,f
	goto	u2765
	movf	0+(??_getADValueOneTime+0)+0,w
	iorwf	(getADValueOneTime@AD_Result),f
	movf	1+(??_getADValueOneTime+0)+0,w
	iorwf	(getADValueOneTime@AD_Result+1),f
	line	368
;mypic.h: 368: ADCON0 = 0b00101000;
	movlw	(028h)
	movwf	(157)^080h	;volatile
	line	369
	
l5132:	
;mypic.h: 369: Delay(20);
	movlw	014h
	movwf	(?_Delay)
	clrf	(?_Delay+1)
	fcall	_Delay
	line	370
	
l5134:	
;mypic.h: 370: return AD_Result;
	movf	(getADValueOneTime@AD_Result+1),w
	movwf	(?_getADValueOneTime+1)
	movf	(getADValueOneTime@AD_Result),w
	movwf	(?_getADValueOneTime)
	line	371
	
l2259:	
	return
	opt stack 0
GLOBAL	__end_of_getADValueOneTime
	__end_of_getADValueOneTime:
;; =============== function _getADValueOneTime ends ============

	signat	_getADValueOneTime,4218
	global	_system_state_init
psect	text605,local,class=CODE,delta=2
global __ptext605
__ptext605:

;; *************** function _system_state_init *****************
;; Defined at:
;;		line 204 in file "C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.h"
;; Parameters:    Size  Location     Type
;;		None
;; Auto vars:     Size  Location     Type
;;		None
;; Return value:  Size  Location     Type
;;		None               void
;; Registers used:
;;		wreg, status,2, status,0, pclath, cstack
;; Tracked objects:
;;		On entry : 17F/0
;;		On exit  : 17F/0
;;		Unchanged: FFE80/0
;; Data sizes:     COMMON   BANK0   BANK1   BANK2
;;      Params:         0       0       0       0
;;      Locals:         0       0       0       0
;;      Temps:          0       0       0       0
;;      Totals:         0       0       0       0
;;Total ram usage:        0 bytes
;; Hardware stack levels used:    1
;; Hardware stack levels required when called:    2
;; This function calls:
;;		_Delay
;; This function is called by:
;;		_main
;; This function uses a non-reentrant model
;;
psect	text605
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.h"
	line	204
	global	__size_of_system_state_init
	__size_of_system_state_init	equ	__end_of_system_state_init-_system_state_init
	
_system_state_init:	
	opt	stack 13
; Regs used in _system_state_init: [wreg+status,2+status,0+pclath+cstack]
	line	206
	
l5076:	
;mypic.h: 206: OSCCON = 0x70;
	movlw	(070h)
	movlb 1	; select bank1
	movwf	(153)^080h	;volatile
	line	207
;mypic.h: 207: WDTCON = 0x18;
	movlw	(018h)
	movwf	(151)^080h	;volatile
	line	210
;mypic.h: 210: TRISA = 0b00101010;
	movlw	(02Ah)
	movwf	(140)^080h	;volatile
	line	211
;mypic.h: 211: TRISB = 0b00111110;
	movlw	(03Eh)
	movwf	(141)^080h	;volatile
	line	212
	
l5078:	
;mypic.h: 212: TRISC = 0x00;
	clrf	(142)^080h	;volatile
	line	213
	
l5080:	
;mypic.h: 213: TRISD = 0x00;
	clrf	(143)^080h	;volatile
	line	214
;mypic.h: 214: TRISE = 0b00000010;
	movlw	(02h)
	movwf	(144)^080h	;volatile
	line	216
;mypic.h: 216: ANSELA = 0b00101010;
	movlw	(02Ah)
	movlb 3	; select bank3
	movwf	(396)^0180h	;volatile
	line	217
;mypic.h: 217: ANSELB = 0b00000110;
	movlw	(06h)
	movwf	(397)^0180h	;volatile
	line	218
;mypic.h: 218: ANSELE = 0b00000010;
	movlw	(02h)
	movwf	(400)^0180h	;volatile
	line	228
;mypic.h: 228: TMR1H = (65536 - (100000 / 4)) >> 8;
	movlw	(09Eh)
	movlb 0	; select bank0
	movwf	(23)	;volatile
	line	229
;mypic.h: 229: TMR1L = (65536 - (100000 / 4)) & 0xFF;
	movlw	(058h)
	movwf	(22)	;volatile
	line	230
	
l5082:	
;mypic.h: 230: PEIE = 1;
	bsf	(94/8),(94)&7
	line	231
	
l5084:	
;mypic.h: 231: TMR1IF = 0;
	bcf	(136/8),(136)&7
	line	232
	
l5086:	
;mypic.h: 232: TMR1IE = 1;
	movlb 1	; select bank1
	bsf	(1160/8)^080h,(1160)&7
	line	233
;mypic.h: 233: T1CON = 0x31;
	movlw	(031h)
	movlb 0	; select bank0
	movwf	(24)	;volatile
	line	234
	
l5088:	
;mypic.h: 234: TMR0IE = 1;
	bsf	(93/8),(93)&7
	line	237
	
l5090:	
;mypic.h: 237: TMR2IF = 0;
	bcf	(137/8),(137)&7
	line	238
;mypic.h: 238: T2CON = 0x06;
	movlw	(06h)
	movwf	(28)	;volatile
	line	239
;mypic.h: 239: PR2 = 8;
	movlw	(08h)
	movwf	(27)	;volatile
	line	240
	
l5092:	
;mypic.h: 240: TMR2IE = 1;
	movlb 1	; select bank1
	bsf	(1161/8)^080h,(1161)&7
	line	244
	
l5094:	
;mypic.h: 244: TMR4IF = 0;
	movlb 0	; select bank0
	bcf	(153/8),(153)&7
	line	247
;mypic.h: 247: T4CON = 0b00000101;
	movlw	(05h)
	movlb 8	; select bank8
	movwf	(1047)^0400h	;volatile
	line	248
;mypic.h: 248: PR4 = 250;
	movlw	(0FAh)
	movwf	(1046)^0400h	;volatile
	line	249
	
l5096:	
;mypic.h: 249: TMR4IE = 1;
	movlb 1	; select bank1
	bsf	(1177/8)^080h,(1177)&7
	line	262
;mypic.h: 262: IOCBP = 0x38;
	movlw	(038h)
	movlb 7	; select bank7
	movwf	(916)^0380h	;volatile
	line	263
;mypic.h: 263: IOCBN = 0x38;
	movlw	(038h)
	movwf	(917)^0380h	;volatile
	line	267
	
l5098:	
;mypic.h: 267: PEIE = 1;
	bsf	(94/8),(94)&7
	line	268
	
l5100:	
;mypic.h: 268: GIE = 1;
	bsf	(95/8),(95)&7
	line	271
	
l5102:	
;mypic.h: 271: (PORTA |= (1 << 0));
	movlb 0	; select bank0
	bsf	(12)+(0/8),(0)&7	;volatile
	line	272
	
l5104:	
;mypic.h: 272: (PORTB |= (1 << 0));
	bsf	(13)+(0/8),(0)&7	;volatile
	line	273
	
l5106:	
;mypic.h: 273: (PORTA |= (1 << 4));
	bsf	(12)+(4/8),(4)&7	;volatile
	line	274
	
l5108:	
;mypic.h: 274: LoadFlag = 1;
	bsf	(_LoadFlag/8),(_LoadFlag)&7
	line	275
	
l5110:	
;mypic.h: 275: LoadState = 1;
	bsf	(_LoadState/8),(_LoadState)&7
	line	276
	
l5112:	
;mypic.h: 276: (PORTE |= (1 << 0));
	bsf	(16)+(0/8),(0)&7	;volatile
	line	277
	
l5114:	
;mypic.h: 277: Delay(20);
	movlw	014h
	movwf	(?_Delay)
	clrf	(?_Delay+1)
	fcall	_Delay
	line	278
	
l5116:	
;mypic.h: 278: (PORTE &= ~(1 << 0));
	bcf	(16)+(0/8),(0)&7	;volatile
	line	279
	
l5118:	
;mypic.h: 279: Delay(20);
	movlw	014h
	movwf	(?_Delay)
	clrf	(?_Delay+1)
	fcall	_Delay
	line	282
	
l2229:	
	return
	opt stack 0
GLOBAL	__end_of_system_state_init
	__end_of_system_state_init:
;; =============== function _system_state_init ends ============

	signat	_system_state_init,88
	global	___fttol
psect	text606,local,class=CODE,delta=2
global __ptext606
__ptext606:

;; *************** function ___fttol *****************
;; Defined at:
;;		line 45 in file "C:\Program Files\HI-TECH Software\PICC\9.80\sources\fttol.c"
;; Parameters:    Size  Location     Type
;;  f1              3    8[COMMON] float 
;; Auto vars:     Size  Location     Type
;;  lval            4    4[BANK0 ] unsigned long 
;;  exp1            1    8[BANK0 ] unsigned char 
;;  sign1           1    3[BANK0 ] unsigned char 
;; Return value:  Size  Location     Type
;;                  4    8[COMMON] long 
;; Registers used:
;;		wreg, status,2, status,0
;; Tracked objects:
;;		On entry : 17F/0
;;		On exit  : 17F/0
;;		Unchanged: FFE80/0
;; Data sizes:     COMMON   BANK0   BANK1   BANK2
;;      Params:         4       0       0       0
;;      Locals:         0       6       0       0
;;      Temps:          0       3       0       0
;;      Totals:         4       9       0       0
;;Total ram usage:       13 bytes
;; Hardware stack levels used:    1
;; Hardware stack levels required when called:    1
;; This function calls:
;;		Nothing
;; This function is called by:
;;		_readFromEEPROM
;; This function uses a non-reentrant model
;;
psect	text606
	file	"C:\Program Files\HI-TECH Software\PICC\9.80\sources\fttol.c"
	line	45
	global	__size_of___fttol
	__size_of___fttol	equ	__end_of___fttol-___fttol
	
___fttol:	
	opt	stack 12
; Regs used in ___fttol: [wreg+status,2+status,0]
	line	49
	
l5038:	
	movf	(___fttol@f1),w
	movwf	((??___fttol+0)+0)
	movf	(___fttol@f1+1),w
	movwf	((??___fttol+0)+0+1)
	movf	(___fttol@f1+2),w
	movwf	((??___fttol+0)+0+2)
	clrc
	rlf	(??___fttol+0)+1,w
	rlf	(??___fttol+0)+2,w
	movwf	(___fttol@exp1)
	movf	((___fttol@exp1)),f
	skipz
	goto	u2671
	goto	u2670
u2671:
	goto	l5042
u2670:
	line	50
	
l5040:	
	clrf	(?___fttol)
	clrf	(?___fttol+1)
	clrf	(?___fttol+2)
	clrf	(?___fttol+3)
	goto	l2790
	line	51
	
l5042:	
	movf	(___fttol@f1),w
	movwf	((??___fttol+0)+0)
	movf	(___fttol@f1+1),w
	movwf	((??___fttol+0)+0+1)
	movf	(___fttol@f1+2),w
	movwf	((??___fttol+0)+0+2)
	movlw	017h
u2685:
	lsrf	(??___fttol+0)+2,f
	rrf	(??___fttol+0)+1,f
	rrf	(??___fttol+0)+0,f
u2680:
	decfsz	wreg,f
	goto	u2685
	movf	0+(??___fttol+0)+0,w
	movwf	(___fttol@sign1)
	line	52
	
l5044:	
	bsf	(___fttol@f1)+(15/8),(15)&7
	line	53
	
l5046:	
	movlw	0FFh
	andwf	(___fttol@f1),f
	movlw	0FFh
	andwf	(___fttol@f1+1),f
	movlw	0
	andwf	(___fttol@f1+2),f
	line	54
	
l5048:	
	movf	(___fttol@f1),w
	movwf	(___fttol@lval)
	movf	(___fttol@f1+1),w
	movwf	((___fttol@lval))+1
	movf	(___fttol@f1+2),w
	movwf	((___fttol@lval))+2
	clrf	((___fttol@lval))+3
	line	55
	
l5050:	
	movlw	low(08Eh)
	subwf	(___fttol@exp1),f
	line	56
	
l5052:	
	btfss	(___fttol@exp1),7
	goto	u2691
	goto	u2690
u2691:
	goto	l5062
u2690:
	line	57
	
l5054:	
	movf	(___fttol@exp1),w
	xorlw	80h
	addlw	-((-15)^80h)
	skipnc
	goto	u2701
	goto	u2700
u2701:
	goto	l5058
u2700:
	goto	l5040
	line	60
	
l5058:	
	lsrf	(___fttol@lval+3),f
	rrf	(___fttol@lval+2),f
	rrf	(___fttol@lval+1),f
	rrf	(___fttol@lval),f
	line	61
	
l5060:	
	incfsz	(___fttol@exp1),f
	goto	u2711
	goto	u2710
u2711:
	goto	l5058
u2710:
	goto	l5068
	line	63
	
l5062:	
	movlw	(018h)
	subwf	(___fttol@exp1),w
	skipc
	goto	u2721
	goto	u2720
u2721:
	goto	l2797
u2720:
	goto	l5040
	line	66
	
l5066:	
	lslf	(___fttol@lval),f
	rlf	(___fttol@lval+1),f
	rlf	(___fttol@lval+2),f
	rlf	(___fttol@lval+3),f
	line	67
	decf	(___fttol@exp1),f
	line	68
	
l2797:	
	line	65
	movf	(___fttol@exp1),f
	skipz
	goto	u2731
	goto	u2730
u2731:
	goto	l5066
u2730:
	line	70
	
l5068:	
	movf	(___fttol@sign1),w
	skipz
	goto	u2740
	goto	l5072
u2740:
	line	71
	
l5070:	
	comf	(___fttol@lval),f
	comf	(___fttol@lval+1),f
	comf	(___fttol@lval+2),f
	comf	(___fttol@lval+3),f
	incf	(___fttol@lval),f
	skipnz
	incf	(___fttol@lval+1),f
	skipnz
	incf	(___fttol@lval+2),f
	skipnz
	incf	(___fttol@lval+3),f
	line	72
	
l5072:	
	movf	(___fttol@lval+3),w
	movwf	(?___fttol+3)
	movf	(___fttol@lval+2),w
	movwf	(?___fttol+2)
	movf	(___fttol@lval+1),w
	movwf	(?___fttol+1)
	movf	(___fttol@lval),w
	movwf	(?___fttol)

	line	73
	
l2790:	
	return
	opt stack 0
GLOBAL	__end_of___fttol
	__end_of___fttol:
;; =============== function ___fttol ends ============

	signat	___fttol,4220
	global	___ftpack
psect	text607,local,class=CODE,delta=2
global __ptext607
__ptext607:

;; *************** function ___ftpack *****************
;; Defined at:
;;		line 63 in file "C:\Program Files\HI-TECH Software\PICC\9.80\sources\float.c"
;; Parameters:    Size  Location     Type
;;  arg             3    0[COMMON] unsigned um
;;  exp             1    3[COMMON] unsigned char 
;;  sign            1    4[COMMON] unsigned char 
;; Auto vars:     Size  Location     Type
;;		None
;; Return value:  Size  Location     Type
;;                  3    0[COMMON] float 
;; Registers used:
;;		wreg, status,2, status,0
;; Tracked objects:
;;		On entry : 17F/0
;;		On exit  : 17F/0
;;		Unchanged: FFE80/0
;; Data sizes:     COMMON   BANK0   BANK1   BANK2
;;      Params:         5       0       0       0
;;      Locals:         0       0       0       0
;;      Temps:          3       0       0       0
;;      Totals:         8       0       0       0
;;Total ram usage:        8 bytes
;; Hardware stack levels used:    1
;; Hardware stack levels required when called:    1
;; This function calls:
;;		Nothing
;; This function is called by:
;;		___ftdiv
;;		___lwtoft
;; This function uses a non-reentrant model
;;
psect	text607
	file	"C:\Program Files\HI-TECH Software\PICC\9.80\sources\float.c"
	line	63
	global	__size_of___ftpack
	__size_of___ftpack	equ	__end_of___ftpack-___ftpack
	
___ftpack:	
	opt	stack 11
; Regs used in ___ftpack: [wreg+status,2+status,0]
	line	64
	
l5010:	
	movf	(___ftpack@exp),w
	skipz
	goto	u2600
	goto	l5014
u2600:
	
l5012:	
	movf	(___ftpack@arg+2),w
	iorwf	(___ftpack@arg+1),w
	iorwf	(___ftpack@arg),w
	skipz
	goto	u2611
	goto	u2610
u2611:
	goto	l5020
u2610:
	line	65
	
l5014:	
	clrf	(?___ftpack)
	clrf	(?___ftpack+1)
	clrf	(?___ftpack+2)
	goto	l3015
	line	67
	
l5018:	
	incf	(___ftpack@exp),f
	line	68
	lsrf	(___ftpack@arg+2),f
	rrf	(___ftpack@arg+1),f
	rrf	(___ftpack@arg),f
	line	66
	
l5020:	
	movlw	low highword(0FE0000h)
	andwf	(___ftpack@arg+2),w
	btfss	status,2
	goto	u2621
	goto	u2620
u2621:
	goto	l5018
u2620:
	goto	l5024
	line	71
	
l5022:	
	incf	(___ftpack@exp),f
	line	72
	incf	(___ftpack@arg),f
	skipnz
	incf	(___ftpack@arg+1),f
	skipnz
	incf	(___ftpack@arg+2),f
	line	73
	lsrf	(___ftpack@arg+2),f
	rrf	(___ftpack@arg+1),f
	rrf	(___ftpack@arg),f
	line	70
	
l5024:	
	movlw	low highword(0FF0000h)
	andwf	(___ftpack@arg+2),w
	btfss	status,2
	goto	u2631
	goto	u2630
u2631:
	goto	l5022
u2630:
	goto	l5028
	line	76
	
l5026:	
	decf	(___ftpack@exp),f
	line	77
	lslf	(___ftpack@arg),f
	rlf	(___ftpack@arg+1),f
	rlf	(___ftpack@arg+2),f
	line	75
	
l5028:	
	btfss	(___ftpack@arg+1),(15)&7
	goto	u2641
	goto	u2640
u2641:
	goto	l5026
u2640:
	
l3024:	
	line	79
	btfsc	(___ftpack@exp),(0)&7
	goto	u2651
	goto	u2650
u2651:
	goto	l3025
u2650:
	line	80
	
l5030:	
	bcf	(___ftpack@arg)+(15/8),(15)&7
	
l3025:	
	line	81
	lsrf	(___ftpack@exp),f
	line	82
	
l5032:	
	movf	(___ftpack@exp),w
	movwf	((??___ftpack+0)+0+2)
	clrf	((??___ftpack+0)+0+1)
	clrf	((??___ftpack+0)+0+0)
	movf	0+(??___ftpack+0)+0,w
	iorwf	(___ftpack@arg),f
	movf	1+(??___ftpack+0)+0,w
	iorwf	(___ftpack@arg+1),f
	movf	2+(??___ftpack+0)+0,w
	iorwf	(___ftpack@arg+2),f
	line	83
	
l5034:	
	movf	(___ftpack@sign),w
	skipz
	goto	u2660
	goto	l3026
u2660:
	line	84
	
l5036:	
	bsf	(___ftpack@arg)+(23/8),(23)&7
	
l3026:	
	line	85
	line	86
	
l3015:	
	return
	opt stack 0
GLOBAL	__end_of___ftpack
	__end_of___ftpack:
;; =============== function ___ftpack ends ============

	signat	___ftpack,12411
	global	___wmul
psect	text608,local,class=CODE,delta=2
global __ptext608
__ptext608:

;; *************** function ___wmul *****************
;; Defined at:
;;		line 3 in file "C:\Program Files\HI-TECH Software\PICC\9.80\sources\wmul.c"
;; Parameters:    Size  Location     Type
;;  multiplier      2    0[COMMON] unsigned int 
;;  multiplicand    2    2[COMMON] unsigned int 
;; Auto vars:     Size  Location     Type
;;  product         2    4[COMMON] unsigned int 
;; Return value:  Size  Location     Type
;;                  2    0[COMMON] unsigned int 
;; Registers used:
;;		wreg, status,2, status,0
;; Tracked objects:
;;		On entry : 1F/0
;;		On exit  : 1F/0
;;		Unchanged: FFFE0/0
;; Data sizes:     COMMON   BANK0   BANK1   BANK2
;;      Params:         4       0       0       0
;;      Locals:         2       0       0       0
;;      Temps:          0       0       0       0
;;      Totals:         6       0       0       0
;;Total ram usage:        6 bytes
;; Hardware stack levels used:    1
;; Hardware stack levels required when called:    1
;; This function calls:
;;		Nothing
;; This function is called by:
;;		_SolarPanelDealWith
;;		_KaiJi
;; This function uses a non-reentrant model
;;
psect	text608
	file	"C:\Program Files\HI-TECH Software\PICC\9.80\sources\wmul.c"
	line	3
	global	__size_of___wmul
	__size_of___wmul	equ	__end_of___wmul-___wmul
	
___wmul:	
	opt	stack 13
; Regs used in ___wmul: [wreg+status,2+status,0]
	line	4
	
l4994:	
	clrf	(___wmul@product)
	clrf	(___wmul@product+1)
	line	7
	
l4996:	
	btfss	(___wmul@multiplier),(0)&7
	goto	u2581
	goto	u2580
u2581:
	goto	l5000
u2580:
	line	8
	
l4998:	
	movf	(___wmul@multiplicand),w
	addwf	(___wmul@product),f
	movf	(___wmul@multiplicand+1),w
	addwfc	(___wmul@product+1),f
	line	9
	
l5000:	
	lslf	(___wmul@multiplicand),f
	rlf	(___wmul@multiplicand+1),f
	line	10
	
l5002:	
	lsrf	(___wmul@multiplier+1),f
	rrf	(___wmul@multiplier),f
	line	11
	
l5004:	
	movf	((___wmul@multiplier+1)),w
	iorwf	((___wmul@multiplier)),w
	skipz
	goto	u2591
	goto	u2590
u2591:
	goto	l4996
u2590:
	line	12
	
l5006:	
	movf	(___wmul@product+1),w
	movwf	(?___wmul+1)
	movf	(___wmul@product),w
	movwf	(?___wmul)
	line	13
	
l2683:	
	return
	opt stack 0
GLOBAL	__end_of___wmul
	__end_of___wmul:
;; =============== function ___wmul ends ============

	signat	___wmul,8314
	global	_LedDisplay
psect	text609,local,class=CODE,delta=2
global __ptext609
__ptext609:

;; *************** function _LedDisplay *****************
;; Defined at:
;;		line 1130 in file "C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.h"
;; Parameters:    Size  Location     Type
;;		None
;; Auto vars:     Size  Location     Type
;;		None
;; Return value:  Size  Location     Type
;;		None               void
;; Registers used:
;;		None
;; Tracked objects:
;;		On entry : 1F/0
;;		On exit  : 1D/0
;;		Unchanged: FFFE0/0
;; Data sizes:     COMMON   BANK0   BANK1   BANK2
;;      Params:         0       0       0       0
;;      Locals:         0       0       0       0
;;      Temps:          0       0       0       0
;;      Totals:         0       0       0       0
;;Total ram usage:        0 bytes
;; Hardware stack levels used:    1
;; Hardware stack levels required when called:    1
;; This function calls:
;;		Nothing
;; This function is called by:
;;		_main
;; This function uses a non-reentrant model
;;
psect	text609
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.h"
	line	1130
	global	__size_of_LedDisplay
	__size_of_LedDisplay	equ	__end_of_LedDisplay-_LedDisplay
	
_LedDisplay:	
	opt	stack 14
; Regs used in _LedDisplay: []
	line	1131
	
l4776:	
;mypic.h: 1131: if(LoadFlag == 0)
	btfsc	(_LoadFlag/8),(_LoadFlag)&7
	goto	u2081
	goto	u2080
u2081:
	goto	l2454
u2080:
	line	1133
	
l4778:	
;mypic.h: 1132: {
;mypic.h: 1133: (LATB |= (1 << 7));
	movlb 2	; select bank2
	bsf	(269)^0100h+(7/8),(7)&7	;volatile
	line	1134
	
l2454:	
	line	1135
;mypic.h: 1134: }
;mypic.h: 1135: if(LoadFlag == 1)
	movlb 0	; select bank0
	btfss	(_LoadFlag/8),(_LoadFlag)&7
	goto	u2091
	goto	u2090
u2091:
	goto	l2455
u2090:
	line	1137
	
l4780:	
;mypic.h: 1136: {
;mypic.h: 1137: (LATB &= ~(1 << 7));
	movlb 2	; select bank2
	bcf	(269)^0100h+(7/8),(7)&7	;volatile
	line	1138
	
l2455:	
	line	1140
;mypic.h: 1138: }
;mypic.h: 1140: if(PVChargeFlag == 1)
	movlb 0	; select bank0
	btfss	(_PVChargeFlag/8),(_PVChargeFlag)&7
	goto	u2101
	goto	u2100
u2101:
	goto	l2456
u2100:
	line	1142
	
l4782:	
;mypic.h: 1141: {
;mypic.h: 1142: (LATB |= (1 << 6));
	movlb 2	; select bank2
	bsf	(269)^0100h+(6/8),(6)&7	;volatile
	line	1143
	
l2456:	
	line	1144
;mypic.h: 1143: }
;mypic.h: 1144: if(PVChargeFlag == 0)
	movlb 0	; select bank0
	btfsc	(_PVChargeFlag/8),(_PVChargeFlag)&7
	goto	u2111
	goto	u2110
u2111:
	goto	l2458
u2110:
	line	1146
	
l4784:	
;mypic.h: 1145: {
;mypic.h: 1146: (LATB &= ~(1 << 6));
	movlb 2	; select bank2
	bcf	(269)^0100h+(6/8),(6)&7	;volatile
	line	1148
	
l2458:	
	return
	opt stack 0
GLOBAL	__end_of_LedDisplay
	__end_of_LedDisplay:
;; =============== function _LedDisplay ends ============

	signat	_LedDisplay,88
	global	_BatteryStateSwitch
psect	text610,local,class=CODE,delta=2
global __ptext610
__ptext610:

;; *************** function _BatteryStateSwitch *****************
;; Defined at:
;;		line 696 in file "C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.h"
;; Parameters:    Size  Location     Type
;;  BatteryVolta    2    0[COMMON] unsigned int 
;; Auto vars:     Size  Location     Type
;;  adjust          2    6[COMMON] unsigned int 
;; Return value:  Size  Location     Type
;;                  1    wreg      unsigned char 
;; Registers used:
;;		wreg, fsr1l, fsr1h, status,2, status,0
;; Tracked objects:
;;		On entry : 1F/0
;;		On exit  : 1F/0
;;		Unchanged: FFFE0/0
;; Data sizes:     COMMON   BANK0   BANK1   BANK2
;;      Params:         2       0       0       0
;;      Locals:         2       0       0       0
;;      Temps:          4       0       0       0
;;      Totals:         8       0       0       0
;;Total ram usage:        8 bytes
;; Hardware stack levels used:    1
;; Hardware stack levels required when called:    1
;; This function calls:
;;		Nothing
;; This function is called by:
;;		_SwitchBatteryState
;; This function uses a non-reentrant model
;;
psect	text610
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.h"
	line	696
	global	__size_of_BatteryStateSwitch
	__size_of_BatteryStateSwitch	equ	__end_of_BatteryStateSwitch-_BatteryStateSwitch
	
_BatteryStateSwitch:	
	opt	stack 13
; Regs used in _BatteryStateSwitch: [wreg+fsr1l-status,0]
	line	697
	
l4720:	
;mypic.h: 697: unsigned int adjust = (TemVoltage + ADVoltage - TemBase - ADBase );
	comf	(_ADBase),w
	movwf	(??_BatteryStateSwitch+0)+0
	comf	(_ADBase+1),w
	movwf	((??_BatteryStateSwitch+0)+0+1)
	incf	(??_BatteryStateSwitch+0)+0,f
	skipnz
	incf	((??_BatteryStateSwitch+0)+0+1),f
	movf	0+(??_BatteryStateSwitch+0)+0,w
	movwf	(BatteryStateSwitch@adjust)
	movf	1+(??_BatteryStateSwitch+0)+0,w
	movwf	(BatteryStateSwitch@adjust+1)
	
l4722:	
	movf	(_ADVoltage),w
	addwf	(BatteryStateSwitch@adjust),f
	movf	(_ADVoltage+1),w
	addwfc	(BatteryStateSwitch@adjust+1),f
	line	698
	
l4724:	
;mypic.h: 698: if(BatteryVoltage < *(BatteryStandard + 0) + adjust){
	movf	(_BatteryStandard),w
	movwf	fsr1l
	clrf fsr1h	
	
	moviw	[0]fsr1
	movwf	(??_BatteryStateSwitch+0)+0
	moviw	[1]fsr1
	movwf	(??_BatteryStateSwitch+0)+0+1
	movf	(BatteryStateSwitch@adjust),w
	addwf	0+(??_BatteryStateSwitch+0)+0,w
	movwf	(??_BatteryStateSwitch+2)+0
	movf	(BatteryStateSwitch@adjust+1),w
	addwfc	1+(??_BatteryStateSwitch+0)+0,w
	movwf	1+(??_BatteryStateSwitch+2)+0
	movf	1+(??_BatteryStateSwitch+2)+0,w
	subwf	(BatteryStateSwitch@BatteryVoltage+1),w
	skipz
	goto	u1995
	movf	0+(??_BatteryStateSwitch+2)+0,w
	subwf	(BatteryStateSwitch@BatteryVoltage),w
u1995:
	skipnc
	goto	u1991
	goto	u1990
u1991:
	goto	l4732
u1990:
	line	699
	
l4726:	
;mypic.h: 699: return 1;
	movlw	(01h)
	goto	l2359
	line	701
	
l4732:	
;mypic.h: 701: && BatteryVoltage < (*(BatteryStandard + 2) + adjust)){
	movf	(_BatteryStandard),w
	movwf	fsr1l
	clrf fsr1h	
	
	moviw	[0]fsr1
	movwf	(??_BatteryStateSwitch+0)+0
	moviw	[1]fsr1
	movwf	(??_BatteryStateSwitch+0)+0+1
	movf	(BatteryStateSwitch@adjust),w
	addwf	0+(??_BatteryStateSwitch+0)+0,w
	movwf	(??_BatteryStateSwitch+2)+0
	movf	(BatteryStateSwitch@adjust+1),w
	addwfc	1+(??_BatteryStateSwitch+0)+0,w
	movwf	1+(??_BatteryStateSwitch+2)+0
	movf	1+(??_BatteryStateSwitch+2)+0,w
	subwf	(BatteryStateSwitch@BatteryVoltage+1),w
	skipz
	goto	u2005
	movf	0+(??_BatteryStateSwitch+2)+0,w
	subwf	(BatteryStateSwitch@BatteryVoltage),w
u2005:
	skipc
	goto	u2001
	goto	u2000
u2001:
	goto	l4742
u2000:
	
l4734:	
	movf	(_BatteryStandard),w
	addlw	04h
	movwf	fsr1l
	clrf fsr1h	
	
	moviw	[0]fsr1
	movwf	(??_BatteryStateSwitch+0)+0
	moviw	[1]fsr1
	movwf	(??_BatteryStateSwitch+0)+0+1
	movf	(BatteryStateSwitch@adjust),w
	addwf	0+(??_BatteryStateSwitch+0)+0,w
	movwf	(??_BatteryStateSwitch+2)+0
	movf	(BatteryStateSwitch@adjust+1),w
	addwfc	1+(??_BatteryStateSwitch+0)+0,w
	movwf	1+(??_BatteryStateSwitch+2)+0
	movf	1+(??_BatteryStateSwitch+2)+0,w
	subwf	(BatteryStateSwitch@BatteryVoltage+1),w
	skipz
	goto	u2015
	movf	0+(??_BatteryStateSwitch+2)+0,w
	subwf	(BatteryStateSwitch@BatteryVoltage),w
u2015:
	skipnc
	goto	u2011
	goto	u2010
u2011:
	goto	l4742
u2010:
	line	702
	
l4736:	
;mypic.h: 702: return 2;
	movlw	(02h)
	goto	l2359
	line	704
	
l4742:	
;mypic.h: 704: && BatteryVoltage <( *(BatteryStandard + 1) + adjust)){
	movf	(_BatteryStandard),w
	addlw	04h
	movwf	fsr1l
	clrf fsr1h	
	
	moviw	[0]fsr1
	movwf	(??_BatteryStateSwitch+0)+0
	moviw	[1]fsr1
	movwf	(??_BatteryStateSwitch+0)+0+1
	movf	(BatteryStateSwitch@adjust),w
	addwf	0+(??_BatteryStateSwitch+0)+0,w
	movwf	(??_BatteryStateSwitch+2)+0
	movf	(BatteryStateSwitch@adjust+1),w
	addwfc	1+(??_BatteryStateSwitch+0)+0,w
	movwf	1+(??_BatteryStateSwitch+2)+0
	movf	1+(??_BatteryStateSwitch+2)+0,w
	subwf	(BatteryStateSwitch@BatteryVoltage+1),w
	skipz
	goto	u2025
	movf	0+(??_BatteryStateSwitch+2)+0,w
	subwf	(BatteryStateSwitch@BatteryVoltage),w
u2025:
	skipc
	goto	u2021
	goto	u2020
u2021:
	goto	l4752
u2020:
	
l4744:	
	movf	(_BatteryStandard),w
	addlw	02h
	movwf	fsr1l
	clrf fsr1h	
	
	moviw	[0]fsr1
	movwf	(??_BatteryStateSwitch+0)+0
	moviw	[1]fsr1
	movwf	(??_BatteryStateSwitch+0)+0+1
	movf	(BatteryStateSwitch@adjust),w
	addwf	0+(??_BatteryStateSwitch+0)+0,w
	movwf	(??_BatteryStateSwitch+2)+0
	movf	(BatteryStateSwitch@adjust+1),w
	addwfc	1+(??_BatteryStateSwitch+0)+0,w
	movwf	1+(??_BatteryStateSwitch+2)+0
	movf	1+(??_BatteryStateSwitch+2)+0,w
	subwf	(BatteryStateSwitch@BatteryVoltage+1),w
	skipz
	goto	u2035
	movf	0+(??_BatteryStateSwitch+2)+0,w
	subwf	(BatteryStateSwitch@BatteryVoltage),w
u2035:
	skipnc
	goto	u2031
	goto	u2030
u2031:
	goto	l4752
u2030:
	line	705
	
l4746:	
;mypic.h: 705: return 3;
	movlw	(03h)
	goto	l2359
	line	707
	
l4752:	
;mypic.h: 707: && BatteryVoltage < (*(BatteryStandard + 3) + adjust)){
	movf	(_BatteryStandard),w
	addlw	02h
	movwf	fsr1l
	clrf fsr1h	
	
	moviw	[0]fsr1
	movwf	(??_BatteryStateSwitch+0)+0
	moviw	[1]fsr1
	movwf	(??_BatteryStateSwitch+0)+0+1
	movf	(BatteryStateSwitch@adjust),w
	addwf	0+(??_BatteryStateSwitch+0)+0,w
	movwf	(??_BatteryStateSwitch+2)+0
	movf	(BatteryStateSwitch@adjust+1),w
	addwfc	1+(??_BatteryStateSwitch+0)+0,w
	movwf	1+(??_BatteryStateSwitch+2)+0
	movf	1+(??_BatteryStateSwitch+2)+0,w
	subwf	(BatteryStateSwitch@BatteryVoltage+1),w
	skipz
	goto	u2045
	movf	0+(??_BatteryStateSwitch+2)+0,w
	subwf	(BatteryStateSwitch@BatteryVoltage),w
u2045:
	skipc
	goto	u2041
	goto	u2040
u2041:
	goto	l4762
u2040:
	
l4754:	
	movf	(_BatteryStandard),w
	addlw	06h
	movwf	fsr1l
	clrf fsr1h	
	
	moviw	[0]fsr1
	movwf	(??_BatteryStateSwitch+0)+0
	moviw	[1]fsr1
	movwf	(??_BatteryStateSwitch+0)+0+1
	movf	(BatteryStateSwitch@adjust),w
	addwf	0+(??_BatteryStateSwitch+0)+0,w
	movwf	(??_BatteryStateSwitch+2)+0
	movf	(BatteryStateSwitch@adjust+1),w
	addwfc	1+(??_BatteryStateSwitch+0)+0,w
	movwf	1+(??_BatteryStateSwitch+2)+0
	movf	1+(??_BatteryStateSwitch+2)+0,w
	subwf	(BatteryStateSwitch@BatteryVoltage+1),w
	skipz
	goto	u2055
	movf	0+(??_BatteryStateSwitch+2)+0,w
	subwf	(BatteryStateSwitch@BatteryVoltage),w
u2055:
	skipnc
	goto	u2051
	goto	u2050
u2051:
	goto	l4762
u2050:
	line	708
	
l4756:	
;mypic.h: 708: return 4;
	movlw	(04h)
	goto	l2359
	line	710
	
l4762:	
;mypic.h: 710: && BatteryVoltage < (*(BatteryStandard + 4) + adjust)){
	movf	(_BatteryStandard),w
	addlw	06h
	movwf	fsr1l
	clrf fsr1h	
	
	moviw	[0]fsr1
	movwf	(??_BatteryStateSwitch+0)+0
	moviw	[1]fsr1
	movwf	(??_BatteryStateSwitch+0)+0+1
	movf	(BatteryStateSwitch@adjust),w
	addwf	0+(??_BatteryStateSwitch+0)+0,w
	movwf	(??_BatteryStateSwitch+2)+0
	movf	(BatteryStateSwitch@adjust+1),w
	addwfc	1+(??_BatteryStateSwitch+0)+0,w
	movwf	1+(??_BatteryStateSwitch+2)+0
	movf	1+(??_BatteryStateSwitch+2)+0,w
	subwf	(BatteryStateSwitch@BatteryVoltage+1),w
	skipz
	goto	u2065
	movf	0+(??_BatteryStateSwitch+2)+0,w
	subwf	(BatteryStateSwitch@BatteryVoltage),w
u2065:
	skipc
	goto	u2061
	goto	u2060
u2061:
	goto	l4772
u2060:
	
l4764:	
	movf	(_BatteryStandard),w
	addlw	08h
	movwf	fsr1l
	clrf fsr1h	
	
	moviw	[0]fsr1
	movwf	(??_BatteryStateSwitch+0)+0
	moviw	[1]fsr1
	movwf	(??_BatteryStateSwitch+0)+0+1
	movf	(BatteryStateSwitch@adjust),w
	addwf	0+(??_BatteryStateSwitch+0)+0,w
	movwf	(??_BatteryStateSwitch+2)+0
	movf	(BatteryStateSwitch@adjust+1),w
	addwfc	1+(??_BatteryStateSwitch+0)+0,w
	movwf	1+(??_BatteryStateSwitch+2)+0
	movf	1+(??_BatteryStateSwitch+2)+0,w
	subwf	(BatteryStateSwitch@BatteryVoltage+1),w
	skipz
	goto	u2075
	movf	0+(??_BatteryStateSwitch+2)+0,w
	subwf	(BatteryStateSwitch@BatteryVoltage),w
u2075:
	skipnc
	goto	u2071
	goto	u2070
u2071:
	goto	l4772
u2070:
	line	711
	
l4766:	
;mypic.h: 711: return 5;
	movlw	(05h)
	goto	l2359
	line	713
	
l4772:	
;mypic.h: 713: return 6;
	movlw	(06h)
	line	732
	
l2359:	
	return
	opt stack 0
GLOBAL	__end_of_BatteryStateSwitch
	__end_of_BatteryStateSwitch:
;; =============== function _BatteryStateSwitch ends ============

	signat	_BatteryStateSwitch,4217
	global	_SelectMode
psect	text611,local,class=CODE,delta=2
global __ptext611
__ptext611:

;; *************** function _SelectMode *****************
;; Defined at:
;;		line 420 in file "C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.h"
;; Parameters:    Size  Location     Type
;;		None
;; Auto vars:     Size  Location     Type
;;		None
;; Return value:  Size  Location     Type
;;		None               void
;; Registers used:
;;		wreg, fsr0l, fsr0h, status,2, status,0
;; Tracked objects:
;;		On entry : 0/0
;;		On exit  : 1F/0
;;		Unchanged: FFFE0/0
;; Data sizes:     COMMON   BANK0   BANK1   BANK2
;;      Params:         0       0       0       0
;;      Locals:         0       0       0       0
;;      Temps:          0       0       0       0
;;      Totals:         0       0       0       0
;;Total ram usage:        0 bytes
;; Hardware stack levels used:    1
;; Hardware stack levels required when called:    1
;; This function calls:
;;		Nothing
;; This function is called by:
;;		_main
;; This function uses a non-reentrant model
;;
psect	text611
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.h"
	line	420
	global	__size_of_SelectMode
	__size_of_SelectMode	equ	__end_of_SelectMode-_SelectMode
	
_SelectMode:	
	opt	stack 14
; Regs used in _SelectMode: [wreg-fsr0h+status,2+status,0]
	line	421
	
l4694:	
;mypic.h: 421: switch(SystemModeType)
	goto	l4718
	line	423
;mypic.h: 422: {
;mypic.h: 423: case 0X05:
	
l2288:	
	line	425
;mypic.h: 424: {
;mypic.h: 425: if((DAYTIME == 0) && LightTime)
	btfsc	(_DAYTIME/8),(_DAYTIME)&7
	goto	u1921
	goto	u1920
u1921:
	goto	l2289
u1920:
	
l4696:	
	movf	(_LightTime),w
	skipz
	goto	u1930
	goto	l2289
u1930:
	line	427
	
l4698:	
;mypic.h: 426: {
;mypic.h: 427: if(TimeModeHour >= 36000)
	movlw	high(08CA0h)
	subwf	(_TimeModeHour+1),w
	movlw	low(08CA0h)
	skipnz
	subwf	(_TimeModeHour),w
	skipc
	goto	u1941
	goto	u1940
u1941:
	goto	l4704
u1940:
	line	429
	
l4700:	
;mypic.h: 428: {
;mypic.h: 429: TimeModeHour = 0;
	clrf	(_TimeModeHour)
	clrf	(_TimeModeHour+1)
	line	430
	
l4702:	
;mypic.h: 430: LightTime--;
	decf	(_LightTime),f
	line	432
	
l4704:	
;mypic.h: 431: }
;mypic.h: 432: if(LightTime != 0)
	movf	(_LightTime),w
	skipz
	goto	u1950
	goto	l2291
u1950:
	line	434
	
l4706:	
;mypic.h: 433: {
;mypic.h: 434: LoadOpen = 1;
	bsf	(_LoadOpen/8),(_LoadOpen)&7
	line	435
;mypic.h: 435: }
	goto	l2302
	line	436
	
l2291:	
	line	438
;mypic.h: 436: else
;mypic.h: 437: {
;mypic.h: 438: LoadOpen = 0;
	bcf	(_LoadOpen/8),(_LoadOpen)&7
	goto	l2302
	line	441
	
l2289:	
;mypic.h: 441: else if(DAYTIME == 1)
	btfss	(_DAYTIME/8),(_DAYTIME)&7
	goto	u1961
	goto	u1960
u1961:
	goto	l2302
u1960:
	line	443
	
l4708:	
;mypic.h: 442: {
;mypic.h: 443: LoadOpen = 0;
	bcf	(_LoadOpen/8),(_LoadOpen)&7
	line	445
	
l4710:	
;mypic.h: 445: TimeModeHour = 0;
	clrf	(_TimeModeHour)
	clrf	(_TimeModeHour+1)
	goto	l2302
	line	449
;mypic.h: 448: }
;mypic.h: 449: case 0x02:
	
l2296:	
	line	451
;mypic.h: 450: {
;mypic.h: 451: if(DAYTIME == 0)
	btfsc	(_DAYTIME/8),(_DAYTIME)&7
	goto	u1971
	goto	u1970
u1971:
	goto	l2297
u1970:
	goto	l4706
	line	455
	
l2297:	
;mypic.h: 455: else if(DAYTIME == 1)
	btfss	(_DAYTIME/8),(_DAYTIME)&7
	goto	u1981
	goto	u1980
u1981:
	goto	l2302
u1980:
	goto	l2291
	line	421
	
l4718:	
	movlb 0	; select bank0
	movf	(_SystemModeType),w
	; Switch size 1, requested type "space"
; Number of cases is 4, Range of values is 1 to 5
; switch strategies available:
; Name         Bytes Cycles
; simple_byte    13     7 (average)
; direct_byte    29    19 (fixed)
;	Chosen strategy is simple_byte

	xorlw	1^0	; case 1
	skipnz
	goto	l4706
	xorlw	2^1	; case 2
	skipnz
	goto	l2296
	xorlw	3^2	; case 3
	skipnz
	goto	l2302
	xorlw	5^3	; case 5
	skipnz
	goto	l2288
	goto	l2302

	line	471
	
l2302:	
	return
	opt stack 0
GLOBAL	__end_of_SelectMode
	__end_of_SelectMode:
;; =============== function _SelectMode ends ============

	signat	_SelectMode,88
	global	_ReadEE
psect	text612,local,class=CODE,delta=2
global __ptext612
__ptext612:

;; *************** function _ReadEE *****************
;; Defined at:
;;		line 305 in file "C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.h"
;; Parameters:    Size  Location     Type
;;  addr            1    wreg     unsigned char 
;; Auto vars:     Size  Location     Type
;;  addr            1    0[COMMON] unsigned char 
;; Return value:  Size  Location     Type
;;                  1    wreg      unsigned char 
;; Registers used:
;;		wreg
;; Tracked objects:
;;		On entry : 17F/0
;;		On exit  : 17F/3
;;		Unchanged: FFE80/0
;; Data sizes:     COMMON   BANK0   BANK1   BANK2
;;      Params:         0       0       0       0
;;      Locals:         1       0       0       0
;;      Temps:          0       0       0       0
;;      Totals:         1       0       0       0
;;Total ram usage:        1 bytes
;; Hardware stack levels used:    1
;; Hardware stack levels required when called:    1
;; This function calls:
;;		Nothing
;; This function is called by:
;;		_readFromEEPROM
;; This function uses a non-reentrant model
;;
psect	text612
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.h"
	line	305
	global	__size_of_ReadEE
	__size_of_ReadEE	equ	__end_of_ReadEE-_ReadEE
	
_ReadEE:	
	opt	stack 12
; Regs used in _ReadEE: [wreg]
;ReadEE@addr stored from wreg
	movwf	(ReadEE@addr)
	line	306
	
l4680:	
;mypic.h: 306: while(RD == 1);
	
l2241:	
	movlb 3	; select bank3
	btfsc	(3240/8)^0180h,(3240)&7
	goto	u1901
	goto	u1900
u1901:
	goto	l2241
u1900:
	line	307
	
l4682:	
;mypic.h: 307: EEADRL = addr;
	movf	(ReadEE@addr),w
	movwf	(401)^0180h	;volatile
	line	308
	
l4684:	
;mypic.h: 308: EEPGD = 0;
	bcf	(3247/8)^0180h,(3247)&7
	line	309
	
l4686:	
;mypic.h: 309: CFGS = 0;
	bcf	(3246/8)^0180h,(3246)&7
	line	310
	
l4688:	
;mypic.h: 310: RD = 1;
	bsf	(3240/8)^0180h,(3240)&7
	line	311
;mypic.h: 311: while(RD == 1);
	
l2244:	
	btfsc	(3240/8)^0180h,(3240)&7
	goto	u1911
	goto	u1910
u1911:
	goto	l2244
u1910:
	line	312
	
l4690:	
;mypic.h: 312: return EEDATL;
	movf	(403)^0180h,w	;volatile
	line	313
	
l2247:	
	return
	opt stack 0
GLOBAL	__end_of_ReadEE
	__end_of_ReadEE:
;; =============== function _ReadEE ends ============

	signat	_ReadEE,4217
	global	_Delay
psect	text613,local,class=CODE,delta=2
global __ptext613
__ptext613:

;; *************** function _Delay *****************
;; Defined at:
;;		line 198 in file "C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.h"
;; Parameters:    Size  Location     Type
;;  x               2    0[COMMON] unsigned int 
;; Auto vars:     Size  Location     Type
;;  b               2    4[COMMON] unsigned int 
;;  a               2    2[COMMON] unsigned int 
;; Return value:  Size  Location     Type
;;		None               void
;; Registers used:
;;		wreg, status,2, status,0
;; Tracked objects:
;;		On entry : 1E/0
;;		On exit  : 1E/0
;;		Unchanged: FFFE1/0
;; Data sizes:     COMMON   BANK0   BANK1   BANK2
;;      Params:         2       0       0       0
;;      Locals:         4       0       0       0
;;      Temps:          0       0       0       0
;;      Totals:         6       0       0       0
;;Total ram usage:        6 bytes
;; Hardware stack levels used:    1
;; Hardware stack levels required when called:    1
;; This function calls:
;;		Nothing
;; This function is called by:
;;		_system_state_init
;;		_getADValueOneTime
;;		_SolarPanelDealWith
;;		_SwitchBatteryState
;; This function uses a non-reentrant model
;;
psect	text613
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.h"
	line	198
	global	__size_of_Delay
	__size_of_Delay	equ	__end_of_Delay-_Delay
	
_Delay:	
	opt	stack 10
; Regs used in _Delay: [wreg+status,2+status,0]
	line	200
	
l4660:	
;mypic.h: 199: unsigned int a,b;
;mypic.h: 200: for(a=x;a>0;a--)
	movf	(Delay@x+1),w
	movwf	(Delay@a+1)
	movf	(Delay@x),w
	movwf	(Delay@a)
	
l4662:	
	movf	((Delay@a+1)),w
	iorwf	((Delay@a)),w
	skipz
	goto	u1881
	goto	u1880
u1881:
	goto	l4666
u1880:
	goto	l2226
	line	201
	
l4666:	
;mypic.h: 201: for(b=10;b>0;b--);
	movlw	0Ah
	movwf	(Delay@b)
	clrf	(Delay@b+1)
	
l4672:	
	movlw	low(01h)
	subwf	(Delay@b),f
	movlw	high(01h)
	subwfb	(Delay@b+1),f
	
l4674:	
	movf	((Delay@b+1)),w
	iorwf	((Delay@b)),w
	skipz
	goto	u1891
	goto	u1890
u1891:
	goto	l4672
u1890:
	line	200
	
l4676:	
	movlw	low(01h)
	subwf	(Delay@a),f
	movlw	high(01h)
	subwfb	(Delay@a+1),f
	goto	l4662
	line	202
	
l2226:	
	return
	opt stack 0
GLOBAL	__end_of_Delay
	__end_of_Delay:
;; =============== function _Delay ends ============

	signat	_Delay,4216
	global	_delay
psect	text614,local,class=CODE,delta=2
global __ptext614
__ptext614:

;; *************** function _delay *****************
;; Defined at:
;;		line 191 in file "C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.h"
;; Parameters:    Size  Location     Type
;;  x               2    0[COMMON] unsigned int 
;; Auto vars:     Size  Location     Type
;;  b               2    4[COMMON] unsigned int 
;;  a               2    2[COMMON] unsigned int 
;; Return value:  Size  Location     Type
;;		None               void
;; Registers used:
;;		wreg, status,2, status,0
;; Tracked objects:
;;		On entry : 1F/0
;;		On exit  : 1F/0
;;		Unchanged: FFFE0/0
;; Data sizes:     COMMON   BANK0   BANK1   BANK2
;;      Params:         2       0       0       0
;;      Locals:         4       0       0       0
;;      Temps:          0       0       0       0
;;      Totals:         6       0       0       0
;;Total ram usage:        6 bytes
;; Hardware stack levels used:    1
;; Hardware stack levels required when called:    1
;; This function calls:
;;		Nothing
;; This function is called by:
;;		_lcd_init
;;		_lcd_write_command
;;		_lcd_write_data
;;		_lcd_print_line1
;; This function uses a non-reentrant model
;;
psect	text614
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.h"
	line	191
	global	__size_of_delay
	__size_of_delay	equ	__end_of_delay-_delay
	
_delay:	
	opt	stack 12
; Regs used in _delay: [wreg+status,2+status,0]
	line	193
	
l4640:	
;mypic.h: 192: unsigned int a,b;
;mypic.h: 193: for(a=x;a>0;a--)
	movf	(delay@x+1),w
	movwf	(delay@a+1)
	movf	(delay@x),w
	movwf	(delay@a)
	
l4642:	
	movf	((delay@a+1)),w
	iorwf	((delay@a)),w
	skipz
	goto	u1861
	goto	u1860
u1861:
	goto	l4646
u1860:
	goto	l2219
	line	194
	
l4646:	
;mypic.h: 194: for(b=10;b>0;b--);
	movlw	0Ah
	movwf	(delay@b)
	clrf	(delay@b+1)
	
l4652:	
	movlw	low(01h)
	subwf	(delay@b),f
	movlw	high(01h)
	subwfb	(delay@b+1),f
	
l4654:	
	movf	((delay@b+1)),w
	iorwf	((delay@b)),w
	skipz
	goto	u1871
	goto	u1870
u1871:
	goto	l4652
u1870:
	line	193
	
l4656:	
	movlw	low(01h)
	subwf	(delay@a),f
	movlw	high(01h)
	subwfb	(delay@a+1),f
	goto	l4642
	line	195
	
l2219:	
	return
	opt stack 0
GLOBAL	__end_of_delay
	__end_of_delay:
;; =============== function _delay ends ============

	signat	_delay,4216
	global	_ISR_Timer
psect	intentry,class=CODE,delta=2
global __pintentry
__pintentry:

;; *************** function _ISR_Timer *****************
;; Defined at:
;;		line 216 in file "C:\CTK\CTK80A 150928\CTK80A_Jas\main.c"
;; Parameters:    Size  Location     Type
;;		None
;; Auto vars:     Size  Location     Type
;;		None
;; Return value:  Size  Location     Type
;;		None               void
;; Registers used:
;;		wreg, fsr0l, fsr0h, status,2, status,0
;; Tracked objects:
;;		On entry : 0/0
;;		On exit  : 1F/0
;;		Unchanged: FFFE0/0
;; Data sizes:     COMMON   BANK0   BANK1   BANK2
;;      Params:         0       0       0       0
;;      Locals:         0       0       0       0
;;      Temps:          0       0       0       0
;;      Totals:         0       0       0       0
;;Total ram usage:        0 bytes
;; Hardware stack levels used:    1
;; This function calls:
;;		Nothing
;; This function is called by:
;;		Interrupt level 1
;; This function uses a non-reentrant model
;;
psect	intentry
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\main.c"
	line	216
	global	__size_of_ISR_Timer
	__size_of_ISR_Timer	equ	__end_of_ISR_Timer-_ISR_Timer
	
_ISR_Timer:	
	opt	stack 10
; Regs used in _ISR_Timer: [wreg-fsr0h+status,2+status,0]
psect	intentry
	pagesel	$
	line	217
	
i1l4786:	
;main.c: 217: if(IOCBF3 && lcd_state != 0 && lcd_state != 1)
	movlb 7	; select bank7
	btfss	(7347/8)^0380h,(7347)&7
	goto	u212_21
	goto	u212_20
u212_21:
	goto	i1l2587
u212_20:
	
i1l4788:	
	movlb 0	; select bank0
	movf	(_lcd_state),w
	skipz
	goto	u213_20
	goto	i1l2587
u213_20:
	
i1l4790:	
	decf	(_lcd_state),w
	skipnz
	goto	u214_21
	goto	u214_20
u214_21:
	goto	i1l2587
u214_20:
	line	220
	
i1l4792:	
;main.c: 218: {
;main.c: 220: if (RB3 == 0) {
	btfsc	(107/8),(107)&7
	goto	u215_21
	goto	u215_20
u215_21:
	goto	i1l2588
u215_20:
	line	221
	
i1l4794:	
;main.c: 221: if (!isSettingMode) {
	btfsc	(_isSettingMode/8),(_isSettingMode)&7
	goto	u216_21
	goto	u216_20
u216_21:
	goto	i1l4806
u216_20:
	line	222
	
i1l4796:	
;main.c: 222: lcd_extinguwish_timer = 0;
	clrf	(_lcd_extinguwish_timer)
	line	223
	
i1l4798:	
;main.c: 223: if (lcd_state == 2) {
	movf	(_lcd_state),w
	xorlw	02h&0ffh
	skipz
	goto	u217_21
	goto	u217_20
u217_21:
	goto	i1l4802
u217_20:
	line	224
	
i1l4800:	
;main.c: 224: lcd_state = 8;
	movlw	(08h)
	movwf	(_lcd_state)
	line	225
;main.c: 225: } else {
	goto	i1l4804
	line	226
	
i1l4802:	
;main.c: 226: lcd_state--;
	decf	(_lcd_state),f
	line	228
	
i1l4804:	
;main.c: 227: }
;main.c: 228: isNeedChange = 1;
	bsf	(_isNeedChange/8),(_isNeedChange)&7
	line	229
;main.c: 229: } else {
	goto	i1l2588
	line	231
	
i1l4806:	
;main.c: 231: setting_save_timer = 0;
	clrf	(_setting_save_timer)
	line	232
;main.c: 232: setting_no_save_timer = 0;
	clrf	(_setting_no_save_timer)
	line	234
;main.c: 234: switch (lcd_state) {
	goto	i1l4818
	line	235
;main.c: 235: case 3:
	
i1l2594:	
	line	236
;main.c: 236: if (is_second_setting) {
	btfss	(_is_second_setting/8),(_is_second_setting)&7
	goto	u218_21
	goto	u218_20
u218_21:
	goto	i1l2588
u218_20:
	line	237
	
i1l4808:	
;main.c: 237: batteryType == 0 ? batteryType = 2 : batteryType--;
	movf	(_batteryType),f
	skipz
	goto	u219_21
	goto	u219_20
u219_21:
	goto	i1l4812
u219_20:
	
i1l4810:	
	movlw	(02h)
	movwf	(_batteryType)
	goto	i1l4804
	
i1l4812:	
	decf	(_batteryType),f
	goto	i1l4804
	line	239
	
i1l2595:	
	line	240
;main.c: 239: }
;main.c: 240: break;
	goto	i1l2588
	line	234
	
i1l4818:	
	movf	(_lcd_state),w
	; Switch size 1, requested type "space"
; Number of cases is 6, Range of values is 3 to 8
; switch strategies available:
; Name         Bytes Cycles
; simple_byte    19    10 (average)
; direct_byte    31    19 (fixed)
;	Chosen strategy is simple_byte

	xorlw	3^0	; case 3
	skipnz
	goto	i1l2594
	xorlw	4^3	; case 4
	skipnz
	goto	i1l2588
	xorlw	5^4	; case 5
	skipnz
	goto	i1l2595
	xorlw	6^5	; case 6
	skipnz
	goto	i1l2588
	xorlw	7^6	; case 7
	skipnz
	goto	i1l2588
	xorlw	8^7	; case 8
	skipnz
	goto	i1l2588
	goto	i1l2588

	line	251
	
i1l2588:	
	line	254
;main.c: 250: }
;main.c: 251: }
;main.c: 254: IOCBF3 = 0;
	movlb 7	; select bank7
	bcf	(7347/8)^0380h,(7347)&7
	line	255
	
i1l2587:	
	line	257
;main.c: 255: }
;main.c: 257: if(IOCBF5 && lcd_state != 0 && lcd_state != 1)
	movlb 7	; select bank7
	btfss	(7349/8)^0380h,(7349)&7
	goto	u220_21
	goto	u220_20
u220_21:
	goto	i1l2607
u220_20:
	
i1l4820:	
	movlb 0	; select bank0
	movf	(_lcd_state),w
	skipz
	goto	u221_20
	goto	i1l2607
u221_20:
	
i1l4822:	
	decf	(_lcd_state),w
	skipnz
	goto	u222_21
	goto	u222_20
u222_21:
	goto	i1l2607
u222_20:
	line	259
	
i1l4824:	
;main.c: 258: {
;main.c: 259: if (RB5 == 0) {
	btfsc	(109/8),(109)&7
	goto	u223_21
	goto	u223_20
u223_21:
	goto	i1l2608
u223_20:
	line	260
	
i1l4826:	
;main.c: 260: if (!isSettingMode) {
	btfsc	(_isSettingMode/8),(_isSettingMode)&7
	goto	u224_21
	goto	u224_20
u224_21:
	goto	i1l4838
u224_20:
	line	261
	
i1l4828:	
;main.c: 261: lcd_extinguwish_timer = 0;
	clrf	(_lcd_extinguwish_timer)
	line	262
	
i1l4830:	
;main.c: 262: if (lcd_state == 8) {
	movf	(_lcd_state),w
	xorlw	08h&0ffh
	skipz
	goto	u225_21
	goto	u225_20
u225_21:
	goto	i1l4834
u225_20:
	line	263
	
i1l4832:	
;main.c: 263: lcd_state = 2;
	movlw	(02h)
	movwf	(_lcd_state)
	line	264
;main.c: 264: } else {
	goto	i1l4836
	line	265
	
i1l4834:	
;main.c: 265: lcd_state++;
	incf	(_lcd_state),f
	line	267
	
i1l4836:	
;main.c: 266: }
;main.c: 267: isNeedChange = 1;
	bsf	(_isNeedChange/8),(_isNeedChange)&7
	line	268
;main.c: 268: } else {
	goto	i1l2608
	line	270
	
i1l4838:	
;main.c: 270: setting_save_timer = 0;
	clrf	(_setting_save_timer)
	line	271
;main.c: 271: setting_no_save_timer = 0;
	clrf	(_setting_no_save_timer)
	line	273
;main.c: 273: switch (lcd_state) {
	goto	i1l4850
	line	274
;main.c: 274: case 3:
	
i1l2614:	
	line	275
;main.c: 275: if (is_second_setting) {
	btfss	(_is_second_setting/8),(_is_second_setting)&7
	goto	u226_21
	goto	u226_20
u226_21:
	goto	i1l2608
u226_20:
	line	276
	
i1l4840:	
;main.c: 276: batteryType == 2 ? batteryType = 0 : batteryType++;
	movf	(_batteryType),w
	xorlw	02h&0ffh
	skipz
	goto	u227_21
	goto	u227_20
u227_21:
	goto	i1l4844
u227_20:
	
i1l4842:	
	clrf	(_batteryType)
	goto	i1l4836
	
i1l4844:	
	incf	(_batteryType),f
	goto	i1l4836
	line	278
	
i1l2615:	
	line	279
;main.c: 278: }
;main.c: 279: break;
	goto	i1l2608
	line	273
	
i1l4850:	
	movf	(_lcd_state),w
	; Switch size 1, requested type "space"
; Number of cases is 6, Range of values is 3 to 8
; switch strategies available:
; Name         Bytes Cycles
; simple_byte    19    10 (average)
; direct_byte    31    19 (fixed)
;	Chosen strategy is simple_byte

	xorlw	3^0	; case 3
	skipnz
	goto	i1l2614
	xorlw	4^3	; case 4
	skipnz
	goto	i1l2608
	xorlw	5^4	; case 5
	skipnz
	goto	i1l2615
	xorlw	6^5	; case 6
	skipnz
	goto	i1l2608
	xorlw	7^6	; case 7
	skipnz
	goto	i1l2608
	xorlw	8^7	; case 8
	skipnz
	goto	i1l2608
	goto	i1l2608

	line	290
	
i1l2608:	
	line	293
;main.c: 289: }
;main.c: 290: }
;main.c: 293: IOCBF5 = 0;
	movlb 7	; select bank7
	bcf	(7349/8)^0380h,(7349)&7
	line	294
	
i1l2607:	
	line	296
;main.c: 294: }
;main.c: 296: if(IOCBF4)
	movlb 7	; select bank7
	btfss	(7348/8)^0380h,(7348)&7
	goto	u228_21
	goto	u228_20
u228_21:
	goto	i1l2627
u228_20:
	line	301
	
i1l4852:	
;main.c: 297: {
;main.c: 301: if (RB4 == 0) {
	movlb 0	; select bank0
	btfsc	(108/8),(108)&7
	goto	u229_21
	goto	u229_20
u229_21:
	goto	i1l4874
u229_20:
	line	302
	
i1l4854:	
;main.c: 302: rb4_flag = 1;
	bsf	(_rb4_flag/8),(_rb4_flag)&7
	line	303
;main.c: 303: if (!isSettingMode) {
	btfsc	(_isSettingMode/8),(_isSettingMode)&7
	goto	u230_21
	goto	u230_20
u230_21:
	goto	i1l4860
u230_20:
	line	304
	
i1l4856:	
;main.c: 304: lcd_extinguwish_timer = 0;
	clrf	(_lcd_extinguwish_timer)
	line	305
	
i1l4858:	
;main.c: 305: isNeedChange = 1;
	bsf	(_isNeedChange/8),(_isNeedChange)&7
	line	306
;main.c: 306: enter_settings_timer = 0;
	clrf	(_enter_settings_timer)
	line	307
;main.c: 307: } else{
	goto	i1l4874
	line	308
	
i1l4860:	
;main.c: 308: setting_save_timer = 0;
	clrf	(_setting_save_timer)
	line	309
;main.c: 309: setting_no_save_timer = 0;
	clrf	(_setting_no_save_timer)
	line	310
	
i1l4862:	
;main.c: 310: if (!is_second_setting) {
	btfsc	(_is_second_setting/8),(_is_second_setting)&7
	goto	u231_21
	goto	u231_20
u231_21:
	goto	i1l2631
u231_20:
	line	311
	
i1l4864:	
;main.c: 311: is_second_setting = 1;
	bsf	(_is_second_setting/8),(_is_second_setting)&7
	line	312
;main.c: 312: } else {
	goto	i1l4872
	
i1l2631:	
	line	313
;main.c: 313: is_second_setting = 0;
	bcf	(_is_second_setting/8),(_is_second_setting)&7
	line	314
	
i1l4866:	
;main.c: 314: if (lcd_state == 8) {
	movf	(_lcd_state),w
	xorlw	08h&0ffh
	skipz
	goto	u232_21
	goto	u232_20
u232_21:
	goto	i1l4870
u232_20:
	line	315
	
i1l4868:	
;main.c: 315: lcd_state = 3;
	movlw	(03h)
	movwf	(_lcd_state)
	line	316
;main.c: 316: } else {
	goto	i1l4872
	line	317
	
i1l4870:	
;main.c: 317: lcd_state ++;
	incf	(_lcd_state),f
	line	320
	
i1l4872:	
;main.c: 318: }
;main.c: 319: }
;main.c: 320: isNeedChange = 1;
	bsf	(_isNeedChange/8),(_isNeedChange)&7
	line	325
	
i1l4874:	
;main.c: 321: }
;main.c: 322: }
;main.c: 325: if (RB4 == 1) {
	btfss	(108/8),(108)&7
	goto	u233_21
	goto	u233_20
u233_21:
	goto	i1l2635
u233_20:
	line	326
	
i1l4876:	
;main.c: 326: rb4_flag = 0;
	bcf	(_rb4_flag/8),(_rb4_flag)&7
	line	327
	
i1l2635:	
	line	329
;main.c: 327: }
;main.c: 329: IOCBF4 = 0;
	movlb 7	; select bank7
	bcf	(7348/8)^0380h,(7348)&7
	line	330
	
i1l2627:	
	line	333
;main.c: 330: }
;main.c: 333: if(TMR1IF)
	movlb 0	; select bank0
	btfss	(136/8),(136)&7
	goto	u234_21
	goto	u234_20
u234_21:
	goto	i1l4976
u234_20:
	line	335
	
i1l4878:	
;main.c: 334: {
;main.c: 335: if (lcd_extinguwish_timer < 70 && !isSettingMode) {
	movlw	(046h)
	subwf	(_lcd_extinguwish_timer),w
	skipnc
	goto	u235_21
	goto	u235_20
u235_21:
	goto	i1l4898
u235_20:
	
i1l4880:	
	btfsc	(_isSettingMode/8),(_isSettingMode)&7
	goto	u236_21
	goto	u236_20
u236_21:
	goto	i1l4898
u236_20:
	line	336
	
i1l4882:	
;main.c: 336: lcd_extinguwish_timer++;
	incf	(_lcd_extinguwish_timer),f
	line	337
	
i1l4884:	
;main.c: 337: if (rb4_flag) {
	btfss	(_rb4_flag/8),(_rb4_flag)&7
	goto	u237_21
	goto	u237_20
u237_21:
	goto	i1l4896
u237_20:
	line	338
	
i1l4886:	
;main.c: 338: enter_settings_timer++;
	incf	(_enter_settings_timer),f
	line	340
	
i1l4888:	
;main.c: 340: if (enter_settings_timer == 50) {
	movf	(_enter_settings_timer),w
	xorlw	032h&0ffh
	skipz
	goto	u238_21
	goto	u238_20
u238_21:
	goto	i1l2641
u238_20:
	line	341
	
i1l4890:	
;main.c: 341: enter_settings_timer = 0;
	clrf	(_enter_settings_timer)
	line	342
	
i1l4892:	
;main.c: 342: isSettingMode = 1;
	bsf	(_isSettingMode/8),(_isSettingMode)&7
	line	343
	
i1l4894:	
;main.c: 343: needInitSetting = 1;
	bsf	(_needInitSetting/8),(_needInitSetting)&7
	goto	i1l4946
	line	346
	
i1l4896:	
;main.c: 346: enter_settings_timer = 0;
	clrf	(_enter_settings_timer)
	goto	i1l4946
	line	348
	
i1l4898:	
	movlw	(032h)
	subwf	(_setting_save_timer),w
	skipc
	goto	u239_21
	goto	u239_20
u239_21:
	goto	i1l2644
u239_20:
	
i1l4900:	
	movlw	(064h)
	subwf	(_setting_no_save_timer),w
	skipnc
	goto	u240_21
	goto	u240_20
u240_21:
	goto	i1l4946
u240_20:
	
i1l4902:	
	btfss	(_isSettingMode/8),(_isSettingMode)&7
	goto	u241_21
	goto	u241_20
u241_21:
	goto	i1l4946
u241_20:
	
i1l2644:	
	line	349
;main.c: 349: if (rb4_flag) {
	btfss	(_rb4_flag/8),(_rb4_flag)&7
	goto	u242_21
	goto	u242_20
u242_21:
	goto	i1l4912
u242_20:
	line	350
	
i1l4904:	
;main.c: 350: setting_save_timer++;
	incf	(_setting_save_timer),f
	line	351
	
i1l4906:	
;main.c: 351: setting_no_save_timer = 0;
	clrf	(_setting_no_save_timer)
	line	352
	
i1l4908:	
;main.c: 352: if (setting_save_timer == 50) {
	movf	(_setting_save_timer),w
	xorlw	032h&0ffh
	skipz
	goto	u243_21
	goto	u243_20
u243_21:
	goto	i1l4946
u243_20:
	line	353
	
i1l4910:	
;main.c: 353: isSettingMode = 0;
	bcf	(_isSettingMode/8),(_isSettingMode)&7
	line	354
;main.c: 354: isSaveSettings = 1;
	bsf	(_isSaveSettings/8),(_isSaveSettings)&7
	goto	i1l4946
	line	357
	
i1l4912:	
;main.c: 357: setting_save_timer = 0;
	clrf	(_setting_save_timer)
	line	358
	
i1l4914:	
;main.c: 358: setting_no_save_timer++;
	incf	(_setting_no_save_timer),f
	line	359
	
i1l4916:	
;main.c: 359: if (setting_no_save_timer == 100) {
	movf	(_setting_no_save_timer),w
	xorlw	064h&0ffh
	skipz
	goto	u244_21
	goto	u244_20
u244_21:
	goto	i1l4946
u244_20:
	line	360
	
i1l4918:	
;main.c: 360: isSettingMode = 0;
	bcf	(_isSettingMode/8),(_isSettingMode)&7
	line	361
	
i1l4920:	
;main.c: 361: lcd_state = 2;
	movlw	(02h)
	movwf	(_lcd_state)
	line	362
	
i1l4922:	
;main.c: 362: isNeedChange = 1;
	bsf	(_isNeedChange/8),(_isNeedChange)&7
	goto	i1l4946
	line	371
	
i1l2641:	
;main.c: 363: }
;main.c: 364: }
;main.c: 367: }
;main.c: 371: switch(lcd_state) {
	goto	i1l4946
	line	373
	
i1l4924:	
;main.c: 373: if (lcd_timer < 30) {
	movlw	(01Eh)
	subwf	(_lcd_timer),w
	skipnc
	goto	u245_21
	goto	u245_20
u245_21:
	goto	i1l4928
u245_20:
	line	374
	
i1l4926:	
;main.c: 374: lcd_timer++;
	incf	(_lcd_timer),f
	line	375
;main.c: 375: } else {
	goto	i1l2653
	line	376
	
i1l4928:	
;main.c: 376: lcd_timer = 0;
	clrf	(_lcd_timer)
	line	377
	
i1l4930:	
;main.c: 377: lcd_state++;
	incf	(_lcd_state),f
	line	378
	
i1l4932:	
;main.c: 378: isNeedChange = 1;
	bsf	(_isNeedChange/8),(_isNeedChange)&7
	goto	i1l2653
	line	382
	
i1l4934:	
;main.c: 382: if (lcd_timer < 30) {
	movlw	(01Eh)
	subwf	(_lcd_timer),w
	skipnc
	goto	u246_21
	goto	u246_20
u246_21:
	goto	i1l4928
u246_20:
	goto	i1l4926
	line	371
	
i1l4946:	
	movf	(_lcd_state),w
	; Switch size 1, requested type "space"
; Number of cases is 2, Range of values is 0 to 1
; switch strategies available:
; Name         Bytes Cycles
; simple_byte     7     4 (average)
; direct_byte    20    16 (fixed)
;	Chosen strategy is simple_byte

	xorlw	0^0	; case 0
	skipnz
	goto	i1l4924
	xorlw	1^0	; case 1
	skipnz
	goto	i1l4934
	goto	i1l2653

	line	392
	
i1l2653:	
	line	393
;main.c: 393: TMR1IF = 0;
	bcf	(136/8),(136)&7
	line	394
	
i1l4948:	
;main.c: 394: TMR1H = (65536 - (100000 / 4)) >> 8;
	movlw	(09Eh)
	movwf	(23)	;volatile
	line	395
;main.c: 395: TMR1L = (65536 - (100000 / 4)) & 0xFF;
	movlw	(058h)
	movwf	(22)	;volatile
	line	397
	
i1l4950:	
;main.c: 397: PWMChargeFlag = 1;
	bsf	(_PWMChargeFlag/8),(_PWMChargeFlag)&7
	line	399
;main.c: 399: if(SystemModeType == 0X05)
	movf	(_SystemModeType),w
	xorlw	05h&0ffh
	skipz
	goto	u247_21
	goto	u247_20
u247_21:
	goto	i1l4954
u247_20:
	line	401
	
i1l4952:	
;main.c: 400: {
;main.c: 401: TimeModeHour++;
	incf	(_TimeModeHour),f
	skipnz
	incf	(_TimeModeHour+1),f
	line	402
;main.c: 402: }
	goto	i1l4956
	line	405
	
i1l4954:	
;main.c: 403: else
;main.c: 404: {
;main.c: 405: TimeModeHour = 0;
	clrf	(_TimeModeHour)
	clrf	(_TimeModeHour+1)
	line	408
	
i1l4956:	
;main.c: 406: }
;main.c: 408: if(EnhanceChargeFlag)
	btfss	(_EnhanceChargeFlag/8),(_EnhanceChargeFlag)&7
	goto	u248_21
	goto	u248_20
u248_21:
	goto	i1l4960
u248_20:
	line	410
	
i1l4958:	
;main.c: 409: {
;main.c: 410: EnhanceCharge_Time++;
	incf	(_EnhanceCharge_Time),f
	skipnz
	incf	(_EnhanceCharge_Time+1),f
	line	412
	
i1l4960:	
;main.c: 411: }
;main.c: 412: if(EqualizingChargeFlag)
	btfss	(_EqualizingChargeFlag/8),(_EqualizingChargeFlag)&7
	goto	u249_21
	goto	u249_20
u249_21:
	goto	i1l4964
u249_20:
	line	414
	
i1l4962:	
;main.c: 413: {
;main.c: 414: EqualizingCharge_Time++;
	incf	(_EqualizingCharge_Time),f
	skipnz
	incf	(_EqualizingCharge_Time+1),f
	line	416
	
i1l4964:	
;main.c: 415: }
;main.c: 416: if(OverLoadTime != 0)
	movf	(_OverLoadTime+1),w
	iorwf	(_OverLoadTime),w
	skipnz
	goto	u250_21
	goto	u250_20
u250_21:
	goto	i1l4968
u250_20:
	line	418
	
i1l4966:	
;main.c: 417: {
;main.c: 418: OverLoadTime++;
	incf	(_OverLoadTime),f
	skipnz
	incf	(_OverLoadTime+1),f
	line	420
	
i1l4968:	
;main.c: 419: }
;main.c: 420: if(SystemErrorCount)
	movf	(_SystemErrorCount+1),w
	iorwf	(_SystemErrorCount),w
	skipnz
	goto	u251_21
	goto	u251_20
u251_21:
	goto	i1l2663
u251_20:
	line	422
	
i1l4970:	
;main.c: 421: {
;main.c: 422: SystemErrorCount++;
	incf	(_SystemErrorCount),f
	skipnz
	incf	(_SystemErrorCount+1),f
	line	423
	
i1l2663:	
	line	424
;main.c: 423: }
;main.c: 424: PVCount++;
	incf	(_PVCount),f
	line	426
	
i1l4972:	
;main.c: 426: if(LPVCount)
	movf	(_LPVCount),w
	skipz
	goto	u252_20
	goto	i1l4976
u252_20:
	line	428
	
i1l4974:	
;main.c: 427: {
;main.c: 428: LPVCount++;
	incf	(_LPVCount),f
	line	431
	
i1l4976:	
;main.c: 429: }
;main.c: 430: }
;main.c: 431: if(TMR2IF)
	btfss	(137/8),(137)&7
	goto	u253_21
	goto	u253_20
u253_21:
	goto	i1l2665
u253_20:
	line	433
	
i1l4978:	
;main.c: 432: {
;main.c: 433: TMR2IF = 0;
	bcf	(137/8),(137)&7
	line	434
;main.c: 434: if(PWMFlag == 1)
	btfss	(_PWMFlag/8),(_PWMFlag)&7
	goto	u254_21
	goto	u254_20
u254_21:
	goto	i1l2665
u254_20:
	line	436
	
i1l4980:	
;main.c: 435: {
;main.c: 436: PwmCount++;
	incf	(_PwmCount),f
	line	438
	
i1l4982:	
;main.c: 438: if(PwmCount >= DutyRatio)
	movf	(_DutyRatio),w
	subwf	(_PwmCount),w
	skipc
	goto	u255_21
	goto	u255_20
u255_21:
	goto	i1l2667
u255_20:
	line	439
	
i1l4984:	
;main.c: 439: (PORTA |= (1 << 0));
	bsf	(12)+(0/8),(0)&7	;volatile
	goto	i1l2665
	line	440
	
i1l2667:	
	line	441
;main.c: 440: else
;main.c: 441: (PORTA &= ~(1 << 0));
	bcf	(12)+(0/8),(0)&7	;volatile
	line	443
	
i1l2665:	
	line	444
;main.c: 442: }
;main.c: 443: }
;main.c: 444: if(TMR4IF)
	btfss	(153/8),(153)&7
	goto	u256_21
	goto	u256_20
u256_21:
	goto	i1l4990
u256_20:
	line	446
	
i1l4986:	
;main.c: 445: {
;main.c: 446: TMR4IF = 0;
	bcf	(153/8),(153)&7
	line	447
	
i1l4988:	
;main.c: 447: RE2 = ~RE2;
	movlw	1<<((130)&7)
	xorwf	((130)/8),f
	line	449
	
i1l4990:	
;main.c: 448: }
;main.c: 449: if(TMR6IF)
	btfss	(155/8),(155)&7
	goto	u257_21
	goto	u257_20
u257_21:
	goto	i1l2671
u257_20:
	line	451
	
i1l4992:	
;main.c: 450: {
;main.c: 451: TMR6IF = 0;
	bcf	(155/8),(155)&7
	line	453
	
i1l2671:	
	retfie
	opt stack 0
GLOBAL	__end_of_ISR_Timer
	__end_of_ISR_Timer:
;; =============== function _ISR_Timer ends ============

	signat	_ISR_Timer,88
psect	intentry
	global	btemp
	btemp set 07Eh

	DABS	1,126,2	;btemp
	global	wtemp0
	wtemp0 set btemp
	end
