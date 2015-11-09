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
# 6 "C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.c"
	psect config,class=CONFIG,delta=2 ;#
# 6 "C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.c"
	dw 0x061C ;#
# 7 "C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.c"
	psect config,class=CONFIG,delta=2 ;#
# 7 "C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.c"
	dw 0x0233 ;#
	FNCALL	_main,_system_state_init
	FNCALL	_main,_load_system_state
	FNCALL	_main,_lcd_init
	FNCALL	_main,_lcd_write_command
	FNCALL	_main,_KaiJi
	FNCALL	_main,_SelectMode
	FNCALL	_main,_LoadCurrentDealWith
	FNCALL	_main,_SwitchBatteryState
	FNCALL	_main,_LedDisplay
	FNCALL	_main,_SolarPanelDealWith
	FNCALL	_main,_PWMCharge
	FNCALL	_PWMCharge,_GetBatteryVoltage
	FNCALL	_SolarPanelDealWith,_CTKSoftDelay
	FNCALL	_SolarPanelDealWith,_GetSolarPanelVoltage
	FNCALL	_SolarPanelDealWith,___wmul
	FNCALL	_SwitchBatteryState,_GetBatteryVoltage
	FNCALL	_SwitchBatteryState,_BatteryStateSwitch
	FNCALL	_SwitchBatteryState,_lcd_move_char
	FNCALL	_SwitchBatteryState,_lcd_write_data
	FNCALL	_SwitchBatteryState,_CTKSoftDelay
	FNCALL	_LoadCurrentDealWith,_GetLoadCurrentVoltage
	FNCALL	_KaiJi,_GetBatteryVoltage
	FNCALL	_KaiJi,_GetSolarPanelVoltage
	FNCALL	_KaiJi,___wmul
	FNCALL	_KaiJi,_GetLoadCurrentVoltage
	FNCALL	_GetLoadCurrentVoltage,_getADValue
	FNCALL	_GetSolarPanelVoltage,_getADValue
	FNCALL	_GetBatteryVoltage,_getADValue
	FNCALL	_lcd_move_char,_API_LCD_MOVE_POINT
	FNCALL	_load_system_state,_readFromEEPROM
	FNCALL	_getADValue,_getADValueOneTime
	FNCALL	_readFromEEPROM,_ReadEE
	FNCALL	_readFromEEPROM,___lwtoft
	FNCALL	_readFromEEPROM,___ftdiv
	FNCALL	_readFromEEPROM,___fttol
	FNCALL	_API_LCD_MOVE_POINT,_lcd_write_command
	FNCALL	_lcd_init,_lcd_write_command
	FNCALL	_lcd_init,_CTKSoftDelay
	FNCALL	___lwtoft,___ftpack
	FNCALL	___ftdiv,___ftpack
	FNCALL	_getADValueOneTime,_CTKSoftDelay
	FNCALL	_lcd_write_data,_CTKSoftDelay
	FNCALL	_lcd_write_command,_CTKSoftDelay
	FNCALL	_system_state_init,_CTKSoftDelay
	FNROOT	_main
	FNCALL	_ISR_Timer,_handlerKeyEventInput
	FNCALL	intlevel1,_ISR_Timer
	global	intlevel1
	FNROOT	intlevel1
	global	_KeyEvent_PendKeyCode
	global	_SystemModeType
	global	_Battery_12V
	global	_Battery_24V
psect	idataBANK0,class=CODE,space=0,delta=2
global __pidataBANK0
__pidataBANK0:
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\main.c"
	line	20

;initializer for _KeyEvent_PendKeyCode
	retlw	01h
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.h"
	line	97

;initializer for _SystemModeType
	retlw	03h
psect	idataBANK1,class=CODE,space=0,delta=2
global __pidataBANK1
__pidataBANK1:
	line	93

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

	line	94

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

	global	_ADBase
	global	_ADVoltage
	global	_ClampVoltage
	global	_EnhanceCharge_Time
	global	_EqualizingCharge_Time
	global	_OverLoadTime
	global	_SDBatteryVoltage
	global	_SDSolarPanelVoltage
	global	_TimeModeHour
	global	_gBatteryVoltage
	global	_gFbVoltage
	global	_gSolarPanelVoltage
	global	_BatteryLastState
	global	_BatteryStandard
	global	_BatteryState
	global	_LPVCount
	global	_LightTime
	global	_PVCount
	global	_PwmCount
	global	_lcd_extinguwish_timer
	global	_lcd_state
	global	_DutyRatio
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
	global	_isSaveSettings
	global	_isSettingMode
	global	_needInitSetting
	global	_LoadFlag
psect	bitnvCOMMON,class=COMMON,bit,space=1
global __pbitnvCOMMON
__pbitnvCOMMON:
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

psect	bitbssCOMMON,class=COMMON,bit,space=1
global __pbitbssCOMMON
__pbitbssCOMMON:
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

_isSaveSettings:
       ds      1

_isSettingMode:
       ds      1

_needInitSetting:
       ds      1

psect	bssCOMMON,class=COMMON,space=1
global __pbssCOMMON
__pbssCOMMON:
_DutyRatio:
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

_TimeModeHour:
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

_LPVCount:
       ds      1

_LightTime:
       ds      1

_PVCount:
       ds      1

_PwmCount:
       ds      1

_lcd_extinguwish_timer:
       ds      1

_lcd_state:
       ds      1

psect	dataBANK0,class=BANK0,space=1
global __pdataBANK0
__pdataBANK0:
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\main.c"
	line	20
_KeyEvent_PendKeyCode:
       ds      1

psect	dataBANK0
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.h"
	line	97
_SystemModeType:
       ds      1

psect	dataBANK1,class=BANK1,space=1
global __pdataBANK1
__pdataBANK1:
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.h"
	line	93
_Battery_12V:
       ds      22

psect	dataBANK1
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.h"
	line	94
_Battery_24V:
       ds      22

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
; Clear objects allocated to BITCOMMON
psect cinit,class=CODE,delta=2
	global __pbitbssCOMMON
	clrf	((__pbitbssCOMMON/8)+0)&07Fh
	clrf	((__pbitbssCOMMON/8)+1)&07Fh
; Clear objects allocated to COMMON
psect cinit,class=CODE,delta=2
	global __pbssCOMMON
	clrf	((__pbssCOMMON)+0)&07Fh
; Clear objects allocated to BANK0
psect cinit,class=CODE,delta=2
	global __pbssBANK0
	movlw	low(__pbssBANK0)
	movwf	fsr0l
	movlw	high(__pbssBANK0)
	movwf	fsr0h
	movlw	021h
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
	fcall	__pidataBANK0+1		;fetch initializer
	movwf	__pdataBANK0+1&07fh		
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
	movlw 02Ch
	fcall init_ram
psect cinit,class=CODE,delta=2
global end_of_initialization

;End of C runtime variable initialization code

end_of_initialization:
movlb 0
ljmp _main	;jump to C main() function
psect	cstackBANK1,class=BANK1,space=1
global __pcstackBANK1
__pcstackBANK1:
	global	readFromEEPROM@l_byte
readFromEEPROM@l_byte:	; 1 bytes @ 0x0
	ds	1
	global	readFromEEPROM@h_byte
readFromEEPROM@h_byte:	; 1 bytes @ 0x1
	ds	1
	global	readFromEEPROM@read_data
readFromEEPROM@read_data:	; 2 bytes @ 0x2
	ds	2
	global	readFromEEPROM@type
readFromEEPROM@type:	; 1 bytes @ 0x4
	ds	1
psect	cstackCOMMON,class=COMMON,space=1
global __pcstackCOMMON
__pcstackCOMMON:
	global	?_system_state_init
?_system_state_init:	; 0 bytes @ 0x0
	global	?_load_system_state
?_load_system_state:	; 0 bytes @ 0x0
	global	?_lcd_init
?_lcd_init:	; 0 bytes @ 0x0
	global	?_lcd_write_command
?_lcd_write_command:	; 0 bytes @ 0x0
	global	?_KaiJi
?_KaiJi:	; 0 bytes @ 0x0
	global	?_SelectMode
?_SelectMode:	; 0 bytes @ 0x0
	global	?_LoadCurrentDealWith
?_LoadCurrentDealWith:	; 0 bytes @ 0x0
	global	?_SwitchBatteryState
?_SwitchBatteryState:	; 0 bytes @ 0x0
	global	?_LedDisplay
?_LedDisplay:	; 0 bytes @ 0x0
	global	?_SolarPanelDealWith
?_SolarPanelDealWith:	; 0 bytes @ 0x0
	global	?_PWMCharge
?_PWMCharge:	; 0 bytes @ 0x0
	global	?_handlerKeyEventInput
?_handlerKeyEventInput:	; 0 bytes @ 0x0
	global	?_main
?_main:	; 0 bytes @ 0x0
	global	?_ISR_Timer
?_ISR_Timer:	; 0 bytes @ 0x0
	global	?_lcd_write_data
?_lcd_write_data:	; 0 bytes @ 0x0
	global	?_API_LCD_MOVE_POINT
?_API_LCD_MOVE_POINT:	; 0 bytes @ 0x0
	global	?_lcd_move_char
?_lcd_move_char:	; 0 bytes @ 0x0
	global	?_ReadEE
?_ReadEE:	; 1 bytes @ 0x0
	global	handlerKeyEventInput@keyCode
handlerKeyEventInput@keyCode:	; 2 bytes @ 0x0
	ds	2
	global	??_handlerKeyEventInput
??_handlerKeyEventInput:	; 0 bytes @ 0x2
	ds	2
	global	??_SelectMode
??_SelectMode:	; 0 bytes @ 0x4
	global	??_LedDisplay
??_LedDisplay:	; 0 bytes @ 0x4
	global	?_CTKSoftDelay
?_CTKSoftDelay:	; 0 bytes @ 0x4
	global	??_ReadEE
??_ReadEE:	; 0 bytes @ 0x4
	global	??_ISR_Timer
??_ISR_Timer:	; 0 bytes @ 0x4
	global	??___wmul
??___wmul:	; 0 bytes @ 0x4
	global	??___lwtoft
??___lwtoft:	; 0 bytes @ 0x4
	global	?_BatteryStateSwitch
?_BatteryStateSwitch:	; 1 bytes @ 0x4
	global	ReadEE@addr
ReadEE@addr:	; 1 bytes @ 0x4
	global	CTKSoftDelay@x
CTKSoftDelay@x:	; 2 bytes @ 0x4
	global	BatteryStateSwitch@BatteryVoltage
BatteryStateSwitch@BatteryVoltage:	; 2 bytes @ 0x4
	global	___wmul@product
___wmul@product:	; 2 bytes @ 0x4
	ds	1
	global	??_load_system_state
??_load_system_state:	; 0 bytes @ 0x5
	global	??_readFromEEPROM
??_readFromEEPROM:	; 0 bytes @ 0x5
	ds	1
	global	??_system_state_init
??_system_state_init:	; 0 bytes @ 0x6
	global	??_lcd_init
??_lcd_init:	; 0 bytes @ 0x6
	global	??_lcd_write_command
??_lcd_write_command:	; 0 bytes @ 0x6
	global	??_LoadCurrentDealWith
??_LoadCurrentDealWith:	; 0 bytes @ 0x6
	global	??_PWMCharge
??_PWMCharge:	; 0 bytes @ 0x6
	global	??_CTKSoftDelay
??_CTKSoftDelay:	; 0 bytes @ 0x6
	global	??_main
??_main:	; 0 bytes @ 0x6
	global	??_lcd_write_data
??_lcd_write_data:	; 0 bytes @ 0x6
	global	??_lcd_move_char
??_lcd_move_char:	; 0 bytes @ 0x6
	global	??_GetBatteryVoltage
??_GetBatteryVoltage:	; 0 bytes @ 0x6
	global	??_GetSolarPanelVoltage
??_GetSolarPanelVoltage:	; 0 bytes @ 0x6
	global	??_GetLoadCurrentVoltage
??_GetLoadCurrentVoltage:	; 0 bytes @ 0x6
psect	cstackBANK0,class=BANK0,space=1
global __pcstackBANK0
__pcstackBANK0:
	global	??_BatteryStateSwitch
??_BatteryStateSwitch:	; 0 bytes @ 0x0
	global	?___wmul
?___wmul:	; 2 bytes @ 0x0
	global	?___ftpack
?___ftpack:	; 3 bytes @ 0x0
	global	CTKSoftDelay@a
CTKSoftDelay@a:	; 2 bytes @ 0x0
	global	___wmul@multiplier
___wmul@multiplier:	; 2 bytes @ 0x0
	global	___ftpack@arg
___ftpack@arg:	; 3 bytes @ 0x0
	ds	2
	global	CTKSoftDelay@b
CTKSoftDelay@b:	; 2 bytes @ 0x2
	global	___wmul@multiplicand
___wmul@multiplicand:	; 2 bytes @ 0x2
	ds	1
	global	___ftpack@exp
___ftpack@exp:	; 1 bytes @ 0x3
	ds	1
	global	?_getADValueOneTime
?_getADValueOneTime:	; 2 bytes @ 0x4
	global	lcd_write_command@command
lcd_write_command@command:	; 1 bytes @ 0x4
	global	lcd_write_data@data
lcd_write_data@data:	; 1 bytes @ 0x4
	global	___ftpack@sign
___ftpack@sign:	; 1 bytes @ 0x4
	global	BatteryStateSwitch@adjust
BatteryStateSwitch@adjust:	; 2 bytes @ 0x4
	ds	1
	global	??___ftpack
??___ftpack:	; 0 bytes @ 0x5
	global	??_API_LCD_MOVE_POINT
??_API_LCD_MOVE_POINT:	; 0 bytes @ 0x5
	ds	1
	global	??_getADValueOneTime
??_getADValueOneTime:	; 0 bytes @ 0x6
	global	API_LCD_MOVE_POINT@position
API_LCD_MOVE_POINT@position:	; 1 bytes @ 0x6
	ds	1
	global	API_LCD_MOVE_POINT@i
API_LCD_MOVE_POINT@i:	; 1 bytes @ 0x7
	ds	1
	global	?___fttol
?___fttol:	; 4 bytes @ 0x8
	global	lcd_move_char@postion
lcd_move_char@postion:	; 1 bytes @ 0x8
	global	getADValueOneTime@channel
getADValueOneTime@channel:	; 1 bytes @ 0x8
	global	___fttol@f1
___fttol@f1:	; 3 bytes @ 0x8
	ds	1
	global	getADValueOneTime@AD_Result
getADValueOneTime@AD_Result:	; 2 bytes @ 0x9
	ds	2
	global	?_getADValue
?_getADValue:	; 2 bytes @ 0xB
	ds	1
	global	??___fttol
??___fttol:	; 0 bytes @ 0xC
	ds	1
	global	??_getADValue
??_getADValue:	; 0 bytes @ 0xD
	ds	2
	global	getADValue@channel
getADValue@channel:	; 1 bytes @ 0xF
	global	___fttol@sign1
___fttol@sign1:	; 1 bytes @ 0xF
	ds	1
	global	getADValue@AD_Result
getADValue@AD_Result:	; 2 bytes @ 0x10
	global	___fttol@lval
___fttol@lval:	; 4 bytes @ 0x10
	ds	2
	global	getADValue@max
getADValue@max:	; 2 bytes @ 0x12
	ds	2
	global	___fttol@exp1
___fttol@exp1:	; 1 bytes @ 0x14
	global	getADValue@min
getADValue@min:	; 2 bytes @ 0x14
	ds	1
	global	?___lwtoft
?___lwtoft:	; 3 bytes @ 0x15
	global	___lwtoft@c
___lwtoft@c:	; 2 bytes @ 0x15
	ds	1
	global	getADValue@i
getADValue@i:	; 2 bytes @ 0x16
	ds	2
	global	?___ftdiv
?___ftdiv:	; 3 bytes @ 0x18
	global	getADValue@AD_OneResult
getADValue@AD_OneResult:	; 2 bytes @ 0x18
	global	___ftdiv@f2
___ftdiv@f2:	; 3 bytes @ 0x18
	ds	2
	global	?_GetBatteryVoltage
?_GetBatteryVoltage:	; 2 bytes @ 0x1A
	global	?_GetSolarPanelVoltage
?_GetSolarPanelVoltage:	; 2 bytes @ 0x1A
	global	?_GetLoadCurrentVoltage
?_GetLoadCurrentVoltage:	; 2 bytes @ 0x1A
	ds	1
	global	___ftdiv@f1
___ftdiv@f1:	; 3 bytes @ 0x1B
	ds	2
	global	??_KaiJi
??_KaiJi:	; 0 bytes @ 0x1D
	global	??_SwitchBatteryState
??_SwitchBatteryState:	; 0 bytes @ 0x1D
	global	??_SolarPanelDealWith
??_SolarPanelDealWith:	; 0 bytes @ 0x1D
	global	PWMCharge@ChangeBatteryVoltag
PWMCharge@ChangeBatteryVoltag:	; 2 bytes @ 0x1D
	ds	1
	global	??___ftdiv
??___ftdiv:	; 0 bytes @ 0x1E
	ds	3
	global	___ftdiv@cntr
___ftdiv@cntr:	; 1 bytes @ 0x21
	ds	1
	global	___ftdiv@f3
___ftdiv@f3:	; 3 bytes @ 0x22
	ds	1
	global	SolarPanelDealWith@SolarPanelVoltage
SolarPanelDealWith@SolarPanelVoltage:	; 2 bytes @ 0x23
	ds	2
	global	___ftdiv@exp
___ftdiv@exp:	; 1 bytes @ 0x25
	ds	1
	global	___ftdiv@sign
___ftdiv@sign:	; 1 bytes @ 0x26
	ds	1
	global	?_readFromEEPROM
?_readFromEEPROM:	; 2 bytes @ 0x27
	ds	2
;;Data sizes: Strings 0, constant 0, data 46, bss 34, persistent 0 stack 0
;;Auto spaces:   Size  Autos    Used
;; COMMON          14      6      10
;; BANK0           80     41      76
;; BANK1           80      5      49
;; BANK2           80      0       0

;;
;; Pointer list with targets:

;; ?___ftpack	float  size(1) Largest target is 0
;;
;; ?_readFromEEPROM	unsigned int  size(1) Largest target is 0
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
;; ?___fttol	long  size(1) Largest target is 0
;;
;; ?___ftdiv	float  size(1) Largest target is 0
;;
;; ?___lwtoft	float  size(1) Largest target is 0
;;
;; charPointer	PTR const unsigned char  size(2) Largest target is 4096
;;		 -> ROM(CODE[4096]), 
;;
;; BatteryStandard	PTR unsigned int  size(1) Largest target is 22
;;		 -> Battery_24V(BANK1[22]), Battery_12V(BANK1[22]), NULL(NULL[0]), 
;;


;;
;; Critical Paths under _main in COMMON
;;
;;   _SolarPanelDealWith->_CTKSoftDelay
;;   _SolarPanelDealWith->___wmul
;;   _SwitchBatteryState->_BatteryStateSwitch
;;   _SwitchBatteryState->_CTKSoftDelay
;;   _KaiJi->___wmul
;;   _readFromEEPROM->_ReadEE
;;   _lcd_init->_CTKSoftDelay
;;   _getADValueOneTime->_CTKSoftDelay
;;   _lcd_write_data->_CTKSoftDelay
;;   _lcd_write_command->_CTKSoftDelay
;;   _system_state_init->_CTKSoftDelay
;;
;; Critical Paths under _ISR_Timer in COMMON
;;
;;   _ISR_Timer->_handlerKeyEventInput
;;
;; Critical Paths under _main in BANK0
;;
;;   _PWMCharge->_GetBatteryVoltage
;;   _SolarPanelDealWith->_GetSolarPanelVoltage
;;   _SwitchBatteryState->_GetBatteryVoltage
;;   _LoadCurrentDealWith->_GetLoadCurrentVoltage
;;   _KaiJi->_GetBatteryVoltage
;;   _KaiJi->_GetSolarPanelVoltage
;;   _KaiJi->_GetLoadCurrentVoltage
;;   _GetLoadCurrentVoltage->_getADValue
;;   _GetSolarPanelVoltage->_getADValue
;;   _GetBatteryVoltage->_getADValue
;;   _lcd_move_char->_API_LCD_MOVE_POINT
;;   _load_system_state->_readFromEEPROM
;;   _getADValue->_getADValueOneTime
;;   _readFromEEPROM->___ftdiv
;;   _API_LCD_MOVE_POINT->_lcd_write_command
;;   _lcd_init->_lcd_write_command
;;   ___lwtoft->___fttol
;;   ___ftdiv->___lwtoft
;;   _getADValueOneTime->_CTKSoftDelay
;;   _lcd_write_data->_CTKSoftDelay
;;   _lcd_write_command->_CTKSoftDelay
;;   _system_state_init->_CTKSoftDelay
;;   ___fttol->___ftpack
;;
;; Critical Paths under _ISR_Timer in BANK0
;;
;;   None.
;;
;; Critical Paths under _main in BANK1
;;
;;   _load_system_state->_readFromEEPROM
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
;; (0) _main                                                 0     0      0    9361
;;                  _system_state_init
;;                  _load_system_state
;;                           _lcd_init
;;                  _lcd_write_command
;;                              _KaiJi
;;                         _SelectMode
;;                _LoadCurrentDealWith
;;                 _SwitchBatteryState
;;                         _LedDisplay
;;                 _SolarPanelDealWith
;;                          _PWMCharge
;; ---------------------------------------------------------------------------------
;; (1) _PWMCharge                                            2     2      0     767
;;                                             29 BANK0      2     2      0
;;                  _GetBatteryVoltage
;; ---------------------------------------------------------------------------------
;; (1) _SolarPanelDealWith                                   8     8      0    1141
;;                                             29 BANK0      8     8      0
;;                       _CTKSoftDelay
;;               _GetSolarPanelVoltage
;;                             ___wmul
;; ---------------------------------------------------------------------------------
;; (1) _SwitchBatteryState                                   6     6      0    1925
;;                                             29 BANK0      6     6      0
;;                  _GetBatteryVoltage
;;                 _BatteryStateSwitch
;;                      _lcd_move_char
;;                     _lcd_write_data
;;                       _CTKSoftDelay
;; ---------------------------------------------------------------------------------
;; (1) _LoadCurrentDealWith                                  2     2      0     801
;;                                             29 BANK0      2     2      0
;;              _GetLoadCurrentVoltage
;; ---------------------------------------------------------------------------------
;; (1) _KaiJi                                                2     2      0    2242
;;                                             29 BANK0      2     2      0
;;                  _GetBatteryVoltage
;;               _GetSolarPanelVoltage
;;                             ___wmul
;;              _GetLoadCurrentVoltage
;; ---------------------------------------------------------------------------------
;; (2) _GetLoadCurrentVoltage                                3     1      2     702
;;                                             26 BANK0      3     1      2
;;                         _getADValue
;; ---------------------------------------------------------------------------------
;; (2) _GetSolarPanelVoltage                                 3     1      2     702
;;                                             26 BANK0      3     1      2
;;                         _getADValue
;; ---------------------------------------------------------------------------------
;; (2) _GetBatteryVoltage                                    3     1      2     702
;;                                             26 BANK0      3     1      2
;;                         _getADValue
;; ---------------------------------------------------------------------------------
;; (2) _lcd_move_char                                        1     1      0     294
;;                                              8 BANK0      1     1      0
;;                 _API_LCD_MOVE_POINT
;; ---------------------------------------------------------------------------------
;; (1) _load_system_state                                    0     0      0    1755
;;                     _readFromEEPROM
;; ---------------------------------------------------------------------------------
;; (3) _getADValue                                          15    13      2     668
;;                                             11 BANK0     15    13      2
;;                  _getADValueOneTime
;; ---------------------------------------------------------------------------------
;; (2) _readFromEEPROM                                       7     5      2    1755
;;                                             39 BANK0      2     0      2
;;                                              0 BANK1      5     5      0
;;                             _ReadEE
;;                           ___lwtoft
;;                            ___ftdiv
;;                            ___fttol
;; ---------------------------------------------------------------------------------
;; (3) _API_LCD_MOVE_POINT                                   3     3      0     263
;;                                              5 BANK0      3     3      0
;;                  _lcd_write_command
;; ---------------------------------------------------------------------------------
;; (1) _lcd_init                                             0     0      0     365
;;                  _lcd_write_command
;;                       _CTKSoftDelay
;; ---------------------------------------------------------------------------------
;; (3) ___lwtoft                                             3     0      3     343
;;                                             21 BANK0      3     0      3
;;                           ___ftpack
;;                            ___fttol (ARG)
;; ---------------------------------------------------------------------------------
;; (3) ___ftdiv                                             15     9      6     732
;;                                             24 BANK0     15     9      6
;;                           ___ftpack
;;                           ___lwtoft (ARG)
;;                            ___fttol (ARG)
;; ---------------------------------------------------------------------------------
;; (4) _getADValueOneTime                                    7     5      2     238
;;                                              4 BANK0      7     5      2
;;                       _CTKSoftDelay
;; ---------------------------------------------------------------------------------
;; (2) _lcd_write_data                                       1     1      0     198
;;                                              4 BANK0      1     1      0
;;                       _CTKSoftDelay
;; ---------------------------------------------------------------------------------
;; (4) _lcd_write_command                                    1     1      0     198
;;                                              4 BANK0      1     1      0
;;                       _CTKSoftDelay
;; ---------------------------------------------------------------------------------
;; (1) _system_state_init                                    0     0      0     167
;;                       _CTKSoftDelay
;; ---------------------------------------------------------------------------------
;; (3) ___fttol                                             13     9      4     371
;;                                              8 BANK0     13     9      4
;;                           ___ftpack (ARG)
;; ---------------------------------------------------------------------------------
;; (4) ___ftpack                                             8     3      5     312
;;                                              0 BANK0      8     3      5
;; ---------------------------------------------------------------------------------
;; (2) ___wmul                                               6     2      4     136
;;                                              4 COMMON     2     2      0
;;                                              0 BANK0      4     0      4
;; ---------------------------------------------------------------------------------
;; (2) _BatteryStateSwitch                                   8     6      2     564
;;                                              4 COMMON     2     0      2
;;                                              0 BANK0      6     6      0
;; ---------------------------------------------------------------------------------
;; (3) _ReadEE                                               1     1      0      31
;;                                              4 COMMON     1     1      0
;; ---------------------------------------------------------------------------------
;; (5) _CTKSoftDelay                                         6     4      2     167
;;                                              4 COMMON     2     0      2
;;                                              0 BANK0      4     4      0
;; ---------------------------------------------------------------------------------
;; (1) _LedDisplay                                           0     0      0       0
;; ---------------------------------------------------------------------------------
;; (1) _SelectMode                                           0     0      0       0
;; ---------------------------------------------------------------------------------
;; Estimated maximum stack depth 5
;; ---------------------------------------------------------------------------------
;; (Depth) Function   	        Calls       Base Space   Used Autos Params    Refs
;; ---------------------------------------------------------------------------------
;; (6) _ISR_Timer                                            0     0      0     110
;;               _handlerKeyEventInput
;; ---------------------------------------------------------------------------------
;; (7) _handlerKeyEventInput                                 4     2      2     110
;;                                              0 COMMON     4     2      2
;; ---------------------------------------------------------------------------------
;; Estimated maximum stack depth 7
;; ---------------------------------------------------------------------------------

;; Call Graph Graphs:

;; _main (ROOT)
;;   _system_state_init
;;     _CTKSoftDelay
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
;;       _CTKSoftDelay
;;     _CTKSoftDelay
;;   _lcd_write_command
;;     _CTKSoftDelay
;;   _KaiJi
;;     _GetBatteryVoltage
;;       _getADValue
;;         _getADValueOneTime
;;           _CTKSoftDelay
;;     _GetSolarPanelVoltage
;;       _getADValue
;;         _getADValueOneTime
;;           _CTKSoftDelay
;;     ___wmul
;;     _GetLoadCurrentVoltage
;;       _getADValue
;;         _getADValueOneTime
;;           _CTKSoftDelay
;;   _SelectMode
;;   _LoadCurrentDealWith
;;     _GetLoadCurrentVoltage
;;       _getADValue
;;         _getADValueOneTime
;;           _CTKSoftDelay
;;   _SwitchBatteryState
;;     _GetBatteryVoltage
;;       _getADValue
;;         _getADValueOneTime
;;           _CTKSoftDelay
;;     _BatteryStateSwitch
;;     _lcd_move_char
;;       _API_LCD_MOVE_POINT
;;         _lcd_write_command
;;           _CTKSoftDelay
;;     _lcd_write_data
;;       _CTKSoftDelay
;;     _CTKSoftDelay
;;   _LedDisplay
;;   _SolarPanelDealWith
;;     _CTKSoftDelay
;;     _GetSolarPanelVoltage
;;       _getADValue
;;         _getADValueOneTime
;;           _CTKSoftDelay
;;     ___wmul
;;   _PWMCharge
;;     _GetBatteryVoltage
;;       _getADValue
;;         _getADValueOneTime
;;           _CTKSoftDelay
;;
;; _ISR_Timer (ROOT)
;;   _handlerKeyEventInput
;;

;; Address spaces:

;;Name               Size   Autos  Total    Cost      Usage
;;BIGRAM              F0      0       0       0        0.0%
;;EEDATA             100      0       0       0        0.0%
;;NULL                 0      0       0       0        0.0%
;;CODE                 0      0       0       0        0.0%
;;BITCOMMON            E      0       3       1       21.4%
;;BITSFR0              0      0       0       1        0.0%
;;SFR0                 0      0       0       1        0.0%
;;COMMON               E      6       A       2       71.4%
;;BITSFR1              0      0       0       2        0.0%
;;SFR1                 0      0       0       2        0.0%
;;BITSFR2              0      0       0       3        0.0%
;;SFR2                 0      0       0       3        0.0%
;;STACK                0      0       9       3        0.0%
;;BITSFR3              0      0       0       4        0.0%
;;SFR3                 0      0       0       4        0.0%
;;ABS                  0      0      87       4        0.0%
;;BITBANK0            50      0       0       5        0.0%
;;BITSFR4              0      0       0       5        0.0%
;;SFR4                 0      0       0       5        0.0%
;;BANK0               50     29      4C       6       95.0%
;;BITSFR5              0      0       0       6        0.0%
;;SFR5                 0      0       0       6        0.0%
;;BITBANK1            50      0       0       7        0.0%
;;BITSFR6              0      0       0       7        0.0%
;;SFR6                 0      0       0       7        0.0%
;;BANK1               50      5      31       8       61.3%
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
;;DATA                 0      0      90      11        0.0%
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
;;		line 101 in file "C:\CTK\CTK80A 150928\CTK80A_Jas\main.c"
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
;; Hardware stack levels required when called:    7
;; This function calls:
;;		_system_state_init
;;		_load_system_state
;;		_lcd_init
;;		_lcd_write_command
;;		_KaiJi
;;		_SelectMode
;;		_LoadCurrentDealWith
;;		_SwitchBatteryState
;;		_LedDisplay
;;		_SolarPanelDealWith
;;		_PWMCharge
;; This function is called by:
;;		Startup code after reset
;; This function uses a non-reentrant model
;;
psect	maintext
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\main.c"
	line	101
	global	__size_of_main
	__size_of_main	equ	__end_of_main-_main
	
_main:	
	opt	stack 9
; Regs used in _main: [wreg-status,0+pclath+cstack]
	line	104
	
l12550:	
;main.c: 104: system_state_init();
	fcall	_system_state_init
	line	105
	
l12552:	
;main.c: 105: load_system_state();
	fcall	_load_system_state
	line	108
	
l12554:	
;main.c: 108: lcd_init();
	fcall	_lcd_init
	line	109
	
l12556:	
;main.c: 109: lcd_write_command(0x80);
	movlw	(080h)
	fcall	_lcd_write_command
	line	117
	
l12558:	
;main.c: 117: (LATC |= (1 << 4));
	movlb 2	; select bank2
	bsf	(270)^0100h+(4/8),(4)&7	;volatile
	line	119
	
l12560:	
;main.c: 119: KaiJi();
	fcall	_KaiJi
	line	122
	
l12562:	
# 122 "C:\CTK\CTK80A 150928\CTK80A_Jas\main.c"
CLRWDT ;#
psect	maintext
	line	124
;main.c: 124: SelectMode();
	fcall	_SelectMode
	line	125
	
l12564:	
;main.c: 125: SystemModeType = 0x01;
	clrf	(_SystemModeType)
	incf	(_SystemModeType),f
	line	126
	
l12566:	
;main.c: 126: LoadCurrentDealWith();
	fcall	_LoadCurrentDealWith
	line	127
	
l12568:	
;main.c: 127: SwitchBatteryState();
	fcall	_SwitchBatteryState
	line	128
	
l12570:	
;main.c: 128: LedDisplay();
	fcall	_LedDisplay
	line	129
	
l12572:	
;main.c: 129: if(PVCount > 50)
	movlw	(033h)
	movlb 0	; select bank0
	subwf	(_PVCount),w
	skipc
	goto	u3871
	goto	u3870
u3871:
	goto	l12578
u3870:
	line	131
	
l12574:	
;main.c: 130: {
;main.c: 131: PVCount = 0;
	clrf	(_PVCount)
	line	132
	
l12576:	
;main.c: 132: SolarPanelDealWith();
	fcall	_SolarPanelDealWith
	line	134
	
l12578:	
;main.c: 133: }
;main.c: 134: if(PWMChargeFlag == 1)
	btfss	(_PWMChargeFlag/8),(_PWMChargeFlag)&7
	goto	u3881
	goto	u3880
u3881:
	goto	l12584
u3880:
	line	136
	
l12580:	
;main.c: 135: {
;main.c: 136: PWMChargeFlag = 0;
	bcf	(_PWMChargeFlag/8),(_PWMChargeFlag)&7
	line	137
	
l12582:	
;main.c: 137: PWMCharge();
	fcall	_PWMCharge
	line	142
	
l12584:	
;main.c: 138: }
;main.c: 142: if (lcd_extinguwish_timer == 70) {
	movlb 0	; select bank0
	movf	(_lcd_extinguwish_timer),w
	xorlw	046h&0ffh
	skipz
	goto	u3891
	goto	u3890
u3891:
	goto	l12590
u3890:
	line	144
	
l12586:	
;main.c: 144: lcd_extinguwish_timer++;
	incf	(_lcd_extinguwish_timer),f
	line	146
	
l12588:	
;main.c: 146: lcd_write_command(0x08);
	movlw	(08h)
	fcall	_lcd_write_command
	line	149
	
l12590:	
;main.c: 147: }
;main.c: 149: if (isSaveSettings) {
	btfss	(_isSaveSettings/8),(_isSaveSettings)&7
	goto	u3901
	goto	u3900
u3901:
	goto	l12598
u3900:
	line	150
	
l12592:	
;main.c: 151: isNeedChange = 1;
	bcf	(_isSaveSettings/8),(_isSaveSettings)&7
	line	152
	
l12594:	
;main.c: 152: lcd_state = 2;
	movlw	(02h)
	movwf	(_lcd_state)
	line	153
	
l12596:	
;main.c: 153: isSettingMode = 0;
	bcf	(_isSettingMode/8),(_isSettingMode)&7
	line	157
	
l12598:	
;main.c: 155: }
;main.c: 157: if (isSettingMode && needInitSetting) {
	btfss	(_isSettingMode/8),(_isSettingMode)&7
	goto	u3911
	goto	u3910
u3911:
	goto	l12562
u3910:
	
l12600:	
	btfss	(_needInitSetting/8),(_needInitSetting)&7
	goto	u3921
	goto	u3920
u3921:
	goto	l12562
u3920:
	line	158
	
l12602:	
;main.c: 159: isNeedChange = 1;
	bcf	(_needInitSetting/8),(_needInitSetting)&7
	line	160
	
l12604:	
;main.c: 161: is_second_setting = 0;
	movlw	(03h)
	movwf	(_lcd_state)
	goto	l12562
	global	start
	ljmp	start
	opt stack 0
psect	maintext
	line	165
GLOBAL	__end_of_main
	__end_of_main:
;; =============== function _main ends ============

	signat	_main,88
	global	_PWMCharge
psect	text820,local,class=CODE,delta=2
global __ptext820
__ptext820:

;; *************** function _PWMCharge *****************
;; Defined at:
;;		line 448 in file "C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.c"
;; Parameters:    Size  Location     Type
;;		None
;; Auto vars:     Size  Location     Type
;;  ChangeBatter    2   29[BANK0 ] unsigned int 
;; Return value:  Size  Location     Type
;;		None               void
;; Registers used:
;;		wreg, status,2, status,0, pclath, cstack
;; Tracked objects:
;;		On entry : 1F/0
;;		On exit  : 1E/0
;;		Unchanged: FFFE0/0
;; Data sizes:     COMMON   BANK0   BANK1   BANK2
;;      Params:         0       0       0       0
;;      Locals:         0       2       0       0
;;      Temps:          0       0       0       0
;;      Totals:         0       2       0       0
;;Total ram usage:        2 bytes
;; Hardware stack levels used:    1
;; Hardware stack levels required when called:    6
;; This function calls:
;;		_GetBatteryVoltage
;; This function is called by:
;;		_main
;; This function uses a non-reentrant model
;;
psect	text820
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.c"
	line	448
	global	__size_of_PWMCharge
	__size_of_PWMCharge	equ	__end_of_PWMCharge-_PWMCharge
	
_PWMCharge:	
	opt	stack 9
; Regs used in _PWMCharge: [wreg+status,2+status,0+pclath+cstack]
	line	450
	
l12512:	
;mypic.c: 449: unsigned int ChangeBatteryVoltag;
;mypic.c: 450: ChangeBatteryVoltag = GetBatteryVoltage();
	fcall	_GetBatteryVoltage
	movf	(1+(?_GetBatteryVoltage)),w
	movwf	(PWMCharge@ChangeBatteryVoltag+1)
	movf	(0+(?_GetBatteryVoltage)),w
	movwf	(PWMCharge@ChangeBatteryVoltag)
	line	451
	
l12514:	
;mypic.c: 451: if((EnhanceChargeFlag == 1) || (EqualizingChargeFlag == 1) || (FloatingChargeFlag == 1))
	btfsc	(_EnhanceChargeFlag/8),(_EnhanceChargeFlag)&7
	goto	u3771
	goto	u3770
u3771:
	goto	l8765
u3770:
	
l12516:	
	btfsc	(_EqualizingChargeFlag/8),(_EqualizingChargeFlag)&7
	goto	u3781
	goto	u3780
u3781:
	goto	l8765
u3780:
	
l12518:	
	btfss	(_FloatingChargeFlag/8),(_FloatingChargeFlag)&7
	goto	u3791
	goto	u3790
u3791:
	goto	l8763
u3790:
	
l8765:	
	line	453
;mypic.c: 452: {
;mypic.c: 453: (PORTB &= ~(1 << 0));
	bcf	(13)+(0/8),(0)&7	;volatile
	line	455
	
l12520:	
;mypic.c: 455: if(ChangeBatteryVoltag < ClampVoltage)
	movf	(_ClampVoltage+1),w
	subwf	(PWMCharge@ChangeBatteryVoltag+1),w
	skipz
	goto	u3805
	movf	(_ClampVoltage),w
	subwf	(PWMCharge@ChangeBatteryVoltag),w
u3805:
	skipnc
	goto	u3801
	goto	u3800
u3801:
	goto	l12532
u3800:
	line	457
	
l12522:	
;mypic.c: 456: {
;mypic.c: 457: PVChargeFlag = 1;
	bsf	(_PVChargeFlag/8),(_PVChargeFlag)&7
	line	458
	
l12524:	
;mypic.c: 458: if(DutyRatio > 249)
	movlw	(0FAh)
	subwf	(_DutyRatio),w
	skipc
	goto	u3811
	goto	u3810
u3811:
	goto	l8767
u3810:
	line	460
	
l12526:	
;mypic.c: 459: {
;mypic.c: 460: TMR2IE = 0;
	movlb 1	; select bank1
	bcf	(1161/8)^080h,(1161)&7
	line	461
;mypic.c: 461: T2Flag = 0;
	bcf	(_T2Flag/8),(_T2Flag)&7
	line	462
;mypic.c: 462: PWMFlag = 0;
	bcf	(_PWMFlag/8),(_PWMFlag)&7
	line	463
;mypic.c: 463: (PORTA &= ~(1 << 0));
	movlb 0	; select bank0
	bcf	(12)+(0/8),(0)&7	;volatile
	line	464
;mypic.c: 464: }
	goto	l12532
	line	465
	
l8767:	
	line	467
;mypic.c: 465: else
;mypic.c: 466: {
;mypic.c: 467: if(T2Flag == 0)
	btfsc	(_T2Flag/8),(_T2Flag)&7
	goto	u3821
	goto	u3820
u3821:
	goto	l12530
u3820:
	line	469
	
l12528:	
;mypic.c: 468: {
;mypic.c: 469: TMR2IE = 1;
	movlb 1	; select bank1
	bsf	(1161/8)^080h,(1161)&7
	line	470
;mypic.c: 470: T2Flag = 1;
	bsf	(_T2Flag/8),(_T2Flag)&7
	line	471
;mypic.c: 471: PWMFlag = 1;
	bsf	(_PWMFlag/8),(_PWMFlag)&7
	line	473
	
l12530:	
;mypic.c: 472: }
;mypic.c: 473: DutyRatio = DutyRatio+10;
	movlw	(0Ah)
	addwf	(_DutyRatio),f
	line	476
	
l12532:	
;mypic.c: 474: }
;mypic.c: 475: }
;mypic.c: 476: if(ChangeBatteryVoltag > ClampVoltage)
	movlb 0	; select bank0
	movf	(PWMCharge@ChangeBatteryVoltag+1),w
	subwf	(_ClampVoltage+1),w
	skipz
	goto	u3835
	movf	(PWMCharge@ChangeBatteryVoltag),w
	subwf	(_ClampVoltage),w
u3835:
	skipnc
	goto	u3831
	goto	u3830
u3831:
	goto	l8776
u3830:
	line	478
	
l12534:	
;mypic.c: 477: {
;mypic.c: 478: if(DutyRatio < 9)
	movlw	(09h)
	subwf	(_DutyRatio),w
	skipnc
	goto	u3841
	goto	u3840
u3841:
	goto	l8771
u3840:
	line	480
	
l12536:	
;mypic.c: 479: {
;mypic.c: 480: TMR2IE = 0;
	movlb 1	; select bank1
	bcf	(1161/8)^080h,(1161)&7
	line	481
;mypic.c: 481: T2Flag = 0;
	bcf	(_T2Flag/8),(_T2Flag)&7
	line	482
;mypic.c: 482: PWMFlag = 0;
	bcf	(_PWMFlag/8),(_PWMFlag)&7
	line	483
;mypic.c: 483: (PORTA |= (1 << 0));
	movlb 0	; select bank0
	bsf	(12)+(0/8),(0)&7	;volatile
	line	484
;mypic.c: 484: PVChargeFlag = 0;
	bcf	(_PVChargeFlag/8),(_PVChargeFlag)&7
	line	485
;mypic.c: 485: }
	goto	l8776
	line	486
	
l8771:	
	line	488
;mypic.c: 486: else
;mypic.c: 487: {
;mypic.c: 488: PVChargeFlag = 1;
	bsf	(_PVChargeFlag/8),(_PVChargeFlag)&7
	line	489
;mypic.c: 489: if(T2Flag == 0)
	btfsc	(_T2Flag/8),(_T2Flag)&7
	goto	u3851
	goto	u3850
u3851:
	goto	l12540
u3850:
	line	491
	
l12538:	
;mypic.c: 490: {
;mypic.c: 491: TMR2IE = 1;
	movlb 1	; select bank1
	bsf	(1161/8)^080h,(1161)&7
	line	492
;mypic.c: 492: T2Flag = 1;
	bsf	(_T2Flag/8),(_T2Flag)&7
	line	493
;mypic.c: 493: PWMFlag = 1;
	bsf	(_PWMFlag/8),(_PWMFlag)&7
	line	495
	
l12540:	
;mypic.c: 494: }
;mypic.c: 495: DutyRatio = DutyRatio-10;
	movlw	(0F6h)
	addwf	(_DutyRatio),f
	goto	l8776
	line	499
	
l8763:	
	line	501
;mypic.c: 499: else
;mypic.c: 500: {
;mypic.c: 501: if(T2Flag == 1)
	btfss	(_T2Flag/8),(_T2Flag)&7
	goto	u3861
	goto	u3860
u3861:
	goto	l12544
u3860:
	line	503
	
l12542:	
;mypic.c: 502: {
;mypic.c: 503: TMR2IE = 0;
	movlb 1	; select bank1
	bcf	(1161/8)^080h,(1161)&7
	line	504
;mypic.c: 504: T2Flag = 0;
	bcf	(_T2Flag/8),(_T2Flag)&7
	line	505
;mypic.c: 505: PWMFlag = 0;
	bcf	(_PWMFlag/8),(_PWMFlag)&7
	line	507
	
l12544:	
;mypic.c: 506: }
;mypic.c: 507: PwmCount = 0;
	movlb 0	; select bank0
	clrf	(_PwmCount)
	line	508
;mypic.c: 508: DutyRatio = 0;
	clrf	(_DutyRatio)
	line	509
	
l12546:	
;mypic.c: 509: (PORTB |= (1 << 0));
	bsf	(13)+(0/8),(0)&7	;volatile
	line	511
	
l12548:	
;mypic.c: 511: PVChargeFlag = 0;
	bcf	(_PVChargeFlag/8),(_PVChargeFlag)&7
	line	513
	
l8776:	
	return
	opt stack 0
GLOBAL	__end_of_PWMCharge
	__end_of_PWMCharge:
;; =============== function _PWMCharge ends ============

	signat	_PWMCharge,88
	global	_SolarPanelDealWith
psect	text821,local,class=CODE,delta=2
global __ptext821
__ptext821:

;; *************** function _SolarPanelDealWith *****************
;; Defined at:
;;		line 356 in file "C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.c"
;; Parameters:    Size  Location     Type
;;		None
;; Auto vars:     Size  Location     Type
;;  SolarPanelVo    2   35[BANK0 ] unsigned int 
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
;; Hardware stack levels required when called:    6
;; This function calls:
;;		_CTKSoftDelay
;;		_GetSolarPanelVoltage
;;		___wmul
;; This function is called by:
;;		_main
;; This function uses a non-reentrant model
;;
psect	text821
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.c"
	line	356
	global	__size_of_SolarPanelDealWith
	__size_of_SolarPanelDealWith	equ	__end_of_SolarPanelDealWith-_SolarPanelDealWith
	
_SolarPanelDealWith:	
	opt	stack 9
; Regs used in _SolarPanelDealWith: [wreg+fsr1l-status,0+pclath+cstack]
	line	358
	
l12430:	
;mypic.c: 357: unsigned int SolarPanelVoltage;
;mypic.c: 358: if(PWMFlag == 0)
	btfsc	(_PWMFlag/8),(_PWMFlag)&7
	goto	u3641
	goto	u3640
u3641:
	goto	l12468
u3640:
	line	360
	
l12432:	
;mypic.c: 359: {
;mypic.c: 360: if(DutyRatio > 249)
	movlw	(0FAh)
	subwf	(_DutyRatio),w
	skipc
	goto	u3651
	goto	u3650
u3651:
	goto	l12442
u3650:
	line	362
	
l12434:	
;mypic.c: 361: {
;mypic.c: 362: (PORTB |= (1 << 0));
	bsf	(13)+(0/8),(0)&7	;volatile
	line	363
	
l12436:	
;mypic.c: 363: CTKSoftDelay(200);
	movlw	0C8h
	movwf	(?_CTKSoftDelay)
	clrf	(?_CTKSoftDelay+1)
	fcall	_CTKSoftDelay
	line	364
;mypic.c: 364: SolarPanelVoltage = GetSolarPanelVoltage();
	fcall	_GetSolarPanelVoltage
	movf	(1+(?_GetSolarPanelVoltage)),w
	movwf	(SolarPanelDealWith@SolarPanelVoltage+1)
	movf	(0+(?_GetSolarPanelVoltage)),w
	movwf	(SolarPanelDealWith@SolarPanelVoltage)
	line	365
	
l12438:	
;mypic.c: 365: gSolarPanelVoltage = SolarPanelVoltage;
	movf	(SolarPanelDealWith@SolarPanelVoltage+1),w
	movwf	(_gSolarPanelVoltage+1)
	movf	(SolarPanelDealWith@SolarPanelVoltage),w
	movwf	(_gSolarPanelVoltage)
	line	366
	
l12440:	
;mypic.c: 366: (PORTB &= ~(1 << 0));
	bcf	(13)+(0/8),(0)&7	;volatile
	line	368
	
l12442:	
;mypic.c: 367: }
;mypic.c: 368: if(DutyRatio == 0)
	movf	(_DutyRatio),f
	skipz
	goto	u3661
	goto	u3660
u3661:
	goto	l12468
u3660:
	line	370
	
l12444:	
;mypic.c: 369: {
;mypic.c: 370: (PORTB |= (1 << 0));
	bsf	(13)+(0/8),(0)&7	;volatile
	line	371
	
l12446:	
;mypic.c: 371: if(LPVCount == 0)
	movf	(_LPVCount),f
	skipz
	goto	u3671
	goto	u3670
u3671:
	goto	l8748
u3670:
	line	373
	
l12448:	
;mypic.c: 372: {
;mypic.c: 373: (PORTA &= ~(1 << 0));
	bcf	(12)+(0/8),(0)&7	;volatile
	line	374
	
l12450:	
;mypic.c: 374: CTKSoftDelay(1000);
	movlw	low(03E8h)
	movwf	(?_CTKSoftDelay)
	movlw	high(03E8h)
	movwf	((?_CTKSoftDelay))+1
	fcall	_CTKSoftDelay
	line	375
;mypic.c: 375: SolarPanelVoltage = GetSolarPanelVoltage();
	fcall	_GetSolarPanelVoltage
	movf	(1+(?_GetSolarPanelVoltage)),w
	movwf	(SolarPanelDealWith@SolarPanelVoltage+1)
	movf	(0+(?_GetSolarPanelVoltage)),w
	movwf	(SolarPanelDealWith@SolarPanelVoltage)
	line	376
	
l12452:	
;mypic.c: 376: gSolarPanelVoltage = SolarPanelVoltage;
	movf	(SolarPanelDealWith@SolarPanelVoltage+1),w
	movwf	(_gSolarPanelVoltage+1)
	movf	(SolarPanelDealWith@SolarPanelVoltage),w
	movwf	(_gSolarPanelVoltage)
	line	377
	
l12454:	
;mypic.c: 377: (PORTA |= (1 << 0));
	bsf	(12)+(0/8),(0)&7	;volatile
	line	378
;mypic.c: 378: }
	goto	l12468
	line	379
	
l8748:	
	line	381
;mypic.c: 379: else
;mypic.c: 380: {
;mypic.c: 381: (PORTA &= ~(1 << 0));
	bcf	(12)+(0/8),(0)&7	;volatile
	line	382
	
l12456:	
;mypic.c: 382: if(LPVCount > 99)
	movlw	(064h)
	subwf	(_LPVCount),w
	skipc
	goto	u3681
	goto	u3680
u3681:
	goto	l8747
u3680:
	line	384
	
l12458:	
;mypic.c: 383: {
;mypic.c: 384: LPVCount = 0;
	clrf	(_LPVCount)
	line	385
	
l12460:	
;mypic.c: 385: SolarPanelVoltage = GetSolarPanelVoltage();
	fcall	_GetSolarPanelVoltage
	movf	(1+(?_GetSolarPanelVoltage)),w
	movwf	(SolarPanelDealWith@SolarPanelVoltage+1)
	movf	(0+(?_GetSolarPanelVoltage)),w
	movwf	(SolarPanelDealWith@SolarPanelVoltage)
	line	386
	
l12462:	
;mypic.c: 386: gSolarPanelVoltage = SolarPanelVoltage;
	movf	(SolarPanelDealWith@SolarPanelVoltage+1),w
	movwf	(_gSolarPanelVoltage+1)
	movf	(SolarPanelDealWith@SolarPanelVoltage),w
	movwf	(_gSolarPanelVoltage)
	line	387
	
l12464:	
;mypic.c: 387: (PORTA |= (1 << 0));
	bsf	(12)+(0/8),(0)&7	;volatile
	line	388
	
l12466:	
;mypic.c: 388: LPVFlag = 1;
	bsf	(_LPVFlag/8),(_LPVFlag)&7
	goto	l12468
	line	391
	
l8747:	
	line	393
	
l12468:	
;mypic.c: 389: }
;mypic.c: 390: }
;mypic.c: 391: }
;mypic.c: 392: }
;mypic.c: 393: if(PWMFlag == 1)
	btfss	(_PWMFlag/8),(_PWMFlag)&7
	goto	u3691
	goto	u3690
u3691:
	goto	l12482
u3690:
	line	395
	
l12470:	
;mypic.c: 394: {
;mypic.c: 395: (PORTB |= (1 << 0));
	bsf	(13)+(0/8),(0)&7	;volatile
	line	396
;mypic.c: 396: PWMFlag = 0;
	bcf	(_PWMFlag/8),(_PWMFlag)&7
	line	398
;mypic.c: 398: (PORTA &= ~(1 << 0));
	bcf	(12)+(0/8),(0)&7	;volatile
	line	399
	
l12472:	
;mypic.c: 399: CTKSoftDelay(200);
	movlw	0C8h
	movwf	(?_CTKSoftDelay)
	clrf	(?_CTKSoftDelay+1)
	fcall	_CTKSoftDelay
	line	400
;mypic.c: 400: SolarPanelVoltage = GetSolarPanelVoltage();
	fcall	_GetSolarPanelVoltage
	movf	(1+(?_GetSolarPanelVoltage)),w
	movwf	(SolarPanelDealWith@SolarPanelVoltage+1)
	movf	(0+(?_GetSolarPanelVoltage)),w
	movwf	(SolarPanelDealWith@SolarPanelVoltage)
	line	401
	
l12474:	
;mypic.c: 401: gSolarPanelVoltage = SolarPanelVoltage;
	movf	(SolarPanelDealWith@SolarPanelVoltage+1),w
	movwf	(_gSolarPanelVoltage+1)
	movf	(SolarPanelDealWith@SolarPanelVoltage),w
	movwf	(_gSolarPanelVoltage)
	line	402
	
l12476:	
;mypic.c: 402: (PORTA |= (1 << 0));
	bsf	(12)+(0/8),(0)&7	;volatile
	line	403
	
l12478:	
;mypic.c: 403: PWMFlag = 1;
	bsf	(_PWMFlag/8),(_PWMFlag)&7
	line	404
	
l12480:	
;mypic.c: 404: (PORTB &= ~(1 << 0));
	bcf	(13)+(0/8),(0)&7	;volatile
	line	406
	
l12482:	
;mypic.c: 405: }
;mypic.c: 406: if(gSolarPanelVoltage <= 24)
	movlw	high(019h)
	subwf	(_gSolarPanelVoltage+1),w
	movlw	low(019h)
	skipnz
	subwf	(_gSolarPanelVoltage),w
	skipnc
	goto	u3701
	goto	u3700
u3701:
	goto	l8752
u3700:
	line	408
	
l12484:	
;mypic.c: 407: {
;mypic.c: 408: PVState = 1;
	bsf	(_PVState/8),(_PVState)&7
	line	409
;mypic.c: 409: }
	goto	l12486
	line	410
	
l8752:	
	line	412
;mypic.c: 410: else
;mypic.c: 411: {
;mypic.c: 412: PVState = 0;
	bcf	(_PVState/8),(_PVState)&7
	line	413
;mypic.c: 413: PVChargeFlag = 0;
	bcf	(_PVChargeFlag/8),(_PVChargeFlag)&7
	line	416
	
l12486:	
;mypic.c: 414: }
;mypic.c: 416: if(((gBatteryVoltage*26) - (gSolarPanelVoltage*23)) > *(BatteryStandard + 9))
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
	goto	u3715
	movf	0+(??_SolarPanelDealWith+2)+0,w
	subwf	0+(??_SolarPanelDealWith+4)+0,w
u3715:
	skipnc
	goto	u3711
	goto	u3710
u3711:
	goto	l12496
u3710:
	line	418
	
l12488:	
;mypic.c: 417: {
;mypic.c: 418: LPVFlag = 0;
	bcf	(_LPVFlag/8),(_LPVFlag)&7
	line	419
	
l12490:	
;mypic.c: 419: LPVCount = 0;
	clrf	(_LPVCount)
	line	420
	
l12492:	
;mypic.c: 420: RB6 = 1;
	bsf	(110/8),(110)&7
	line	421
	
l12494:	
;mypic.c: 421: DAYTIME = 1;
	bsf	(_DAYTIME/8),(_DAYTIME)&7
	line	423
	
l12496:	
;mypic.c: 422: }
;mypic.c: 423: if(((gBatteryVoltage*26) - (gSolarPanelVoltage*23)) < 3686)
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
	goto	u3721
	goto	u3720
u3721:
	goto	l8755
u3720:
	line	425
	
l12498:	
;mypic.c: 424: {
;mypic.c: 425: if(DAYTIME == 1)
	btfss	(_DAYTIME/8),(_DAYTIME)&7
	goto	u3731
	goto	u3730
u3731:
	goto	l8755
u3730:
	line	427
	
l12500:	
;mypic.c: 426: {
;mypic.c: 427: if(LPVCount == 0)
	movf	(_LPVCount),f
	skipz
	goto	u3741
	goto	u3740
u3741:
	goto	l8755
u3740:
	line	429
	
l12502:	
;mypic.c: 428: {
;mypic.c: 429: LPVCount = 1;
	clrf	(_LPVCount)
	incf	(_LPVCount),f
	line	432
	
l8755:	
	line	433
;mypic.c: 430: }
;mypic.c: 431: }
;mypic.c: 432: }
;mypic.c: 433: if(LPVFlag == 1)
	btfss	(_LPVFlag/8),(_LPVFlag)&7
	goto	u3751
	goto	u3750
u3751:
	goto	l8760
u3750:
	line	435
	
l12504:	
;mypic.c: 434: {
;mypic.c: 435: LPVFlag = 0;
	bcf	(_LPVFlag/8),(_LPVFlag)&7
	line	436
	
l12506:	
;mypic.c: 436: LPVCount = 0;
	clrf	(_LPVCount)
	line	437
	
l12508:	
;mypic.c: 437: if(((gBatteryVoltage*26) - (gSolarPanelVoltage*23)) < *(BatteryStandard + 10))
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
	goto	u3765
	movf	0+(??_SolarPanelDealWith+0)+0,w
	subwf	0+(??_SolarPanelDealWith+4)+0,w
u3765:
	skipnc
	goto	u3761
	goto	u3760
u3761:
	goto	l8760
u3760:
	line	439
	
l12510:	
;mypic.c: 438: {
;mypic.c: 439: RB6 = 0;
	bcf	(110/8),(110)&7
	line	440
;mypic.c: 440: DAYTIME = 0;
	bcf	(_DAYTIME/8),(_DAYTIME)&7
	line	443
	
l8760:	
	return
	opt stack 0
GLOBAL	__end_of_SolarPanelDealWith
	__end_of_SolarPanelDealWith:
;; =============== function _SolarPanelDealWith ends ============

	signat	_SolarPanelDealWith,88
	global	_SwitchBatteryState
psect	text822,local,class=CODE,delta=2
global __ptext822
__ptext822:

;; *************** function _SwitchBatteryState *****************
;; Defined at:
;;		line 556 in file "C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.c"
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
;; Hardware stack levels required when called:    6
;; This function calls:
;;		_GetBatteryVoltage
;;		_BatteryStateSwitch
;;		_lcd_move_char
;;		_lcd_write_data
;;		_CTKSoftDelay
;; This function is called by:
;;		_main
;; This function uses a non-reentrant model
;;
psect	text822
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.c"
	line	556
	global	__size_of_SwitchBatteryState
	__size_of_SwitchBatteryState	equ	__end_of_SwitchBatteryState-_SwitchBatteryState
	
_SwitchBatteryState:	
	opt	stack 9
; Regs used in _SwitchBatteryState: [wreg-status,0+pclath+cstack]
	line	557
	
l12152:	
;mypic.c: 557: switch(BatteryState)
	goto	l12428
	line	561
	
l12154:	
;mypic.c: 560: {
;mypic.c: 561: lcd_state = 0;
	clrf	(_lcd_state)
	line	562
	
l12156:	
;mypic.c: 562: gBatteryVoltage = GetBatteryVoltage();
	fcall	_GetBatteryVoltage
	movf	(1+(?_GetBatteryVoltage)),w
	movwf	(_gBatteryVoltage+1)
	movf	(0+(?_GetBatteryVoltage)),w
	movwf	(_gBatteryVoltage)
	line	563
	
l12158:	
;mypic.c: 563: BatteryState = BatteryStateSwitch(gBatteryVoltage);
	movf	(_gBatteryVoltage+1),w
	movwf	(?_BatteryStateSwitch+1)
	movf	(_gBatteryVoltage),w
	movwf	(?_BatteryStateSwitch)
	fcall	_BatteryStateSwitch
	movwf	(_BatteryState)
	line	564
	
l12160:	
;mypic.c: 564: EnhanceChargeFlag = 0;
	bcf	(_EnhanceChargeFlag/8),(_EnhanceChargeFlag)&7
	line	565
	
l12162:	
;mypic.c: 565: EnhanceCharge_Time = 0;
	clrf	(_EnhanceCharge_Time)
	clrf	(_EnhanceCharge_Time+1)
	line	566
	
l12164:	
;mypic.c: 566: EqualizingChargeFlag = 0;
	bcf	(_EqualizingChargeFlag/8),(_EqualizingChargeFlag)&7
	line	567
	
l12166:	
;mypic.c: 567: EqualizingCharge_Time = 0;
	clrf	(_EqualizingCharge_Time)
	clrf	(_EqualizingCharge_Time+1)
	line	568
	
l12168:	
;mypic.c: 568: FloatingChargeFlag = 0;
	bcf	(_FloatingChargeFlag/8),(_FloatingChargeFlag)&7
	line	569
	
l12170:	
;mypic.c: 569: BatteryLastState = 0;
	clrf	(_BatteryLastState)
	line	570
;mypic.c: 570: break;
	goto	l8833
	line	575
	
l12172:	
;mypic.c: 573: {
;mypic.c: 575: if (lcd_state == 2) {
	movf	(_lcd_state),w
	xorlw	02h&0ffh
	skipz
	goto	u3381
	goto	u3380
u3381:
	goto	l12176
u3380:
	line	577
	
l12174:	
;mypic.c: 577: lcd_move_char(11);
	movlw	(0Bh)
	fcall	_lcd_move_char
	line	578
;mypic.c: 578: lcd_write_data(0xff);
	movlw	(0FFh)
	fcall	_lcd_write_data
	line	581
	
l12176:	
;mypic.c: 580: }
;mypic.c: 581: if(BatteryLastState != 1)
	decf	(_BatteryLastState),w
	skipnz
	goto	u3391
	goto	u3390
u3391:
	goto	l12188
u3390:
	line	584
	
l12178:	
;mypic.c: 582: {
;mypic.c: 584: EnhanceChargeFlag = 0;
	bcf	(_EnhanceChargeFlag/8),(_EnhanceChargeFlag)&7
	line	585
	
l12180:	
;mypic.c: 585: EnhanceCharge_Time = 0;
	clrf	(_EnhanceCharge_Time)
	clrf	(_EnhanceCharge_Time+1)
	line	586
	
l12182:	
;mypic.c: 586: EqualizingChargeFlag = 0;
	bcf	(_EqualizingChargeFlag/8),(_EqualizingChargeFlag)&7
	line	587
;mypic.c: 587: EqualizingCharge_Time = 0;
	clrf	(_EqualizingCharge_Time)
	clrf	(_EqualizingCharge_Time+1)
	line	588
	
l12184:	
;mypic.c: 588: FloatingChargeFlag = 0;
	bcf	(_FloatingChargeFlag/8),(_FloatingChargeFlag)&7
	line	589
	
l12186:	
;mypic.c: 589: BatteryLastState = 1;
	clrf	(_BatteryLastState)
	incf	(_BatteryLastState),f
	line	591
	
l12188:	
;mypic.c: 590: }
;mypic.c: 591: if(PVState == 1)
	btfss	(_PVState/8),(_PVState)&7
	goto	u3401
	goto	u3400
u3401:
	goto	l8798
u3400:
	line	593
	
l12190:	
;mypic.c: 592: {
;mypic.c: 593: ClampVoltage = *(BatteryStandard + 6) - TemBase + TemVoltage - ADBase + ADVoltage;
	movf	(_BatteryStandard),w
	addlw	0Ch
	movwf	fsr1l
	clrf fsr1h	
	
	moviw	[0]fsr1
	movwf	(_ClampVoltage)
	moviw	[1]fsr1
	movwf	(_ClampVoltage+1)
	
l12192:	
	movf	(_ADBase),w
	subwf	(_ClampVoltage),f
	movf	(_ADBase+1),w
	subwfb	(_ClampVoltage+1),f
	
l12194:	
	movf	(_ADVoltage),w
	addwf	(_ClampVoltage),f
	movf	(_ADVoltage+1),w
	addwfc	(_ClampVoltage+1),f
	line	594
	
l12196:	
;mypic.c: 594: if(EnhanceChargeFlag == 0)
	btfsc	(_EnhanceChargeFlag/8),(_EnhanceChargeFlag)&7
	goto	u3411
	goto	u3410
u3411:
	goto	l12210
u3410:
	line	596
	
l12198:	
;mypic.c: 595: {
;mypic.c: 596: EnhanceChargeFlag = 1;
	bsf	(_EnhanceChargeFlag/8),(_EnhanceChargeFlag)&7
	line	597
	
l12200:	
;mypic.c: 597: EnhanceCharge_Time = 0;
	clrf	(_EnhanceCharge_Time)
	clrf	(_EnhanceCharge_Time+1)
	goto	l12210
	line	600
	
l8798:	
	line	602
;mypic.c: 600: else
;mypic.c: 601: {
;mypic.c: 602: EnhanceChargeFlag = 0;
	bcf	(_EnhanceChargeFlag/8),(_EnhanceChargeFlag)&7
	line	603
	
l12202:	
;mypic.c: 603: EnhanceCharge_Time = 0;
	clrf	(_EnhanceCharge_Time)
	clrf	(_EnhanceCharge_Time+1)
	line	604
	
l12204:	
;mypic.c: 604: gBatteryVoltage = GetBatteryVoltage();
	fcall	_GetBatteryVoltage
	movf	(1+(?_GetBatteryVoltage)),w
	movwf	(_gBatteryVoltage+1)
	movf	(0+(?_GetBatteryVoltage)),w
	movwf	(_gBatteryVoltage)
	line	605
	
l12206:	
;mypic.c: 605: if(gBatteryVoltage > *(BatteryStandard + 1) - TemBase + TemVoltage - ADBase + ADVoltage)
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
	goto	u3425
	movf	(_gBatteryVoltage),w
	subwf	0+(??_SwitchBatteryState+4)+0,w
u3425:
	skipnc
	goto	u3421
	goto	u3420
u3421:
	goto	l12210
u3420:
	line	607
	
l12208:	
;mypic.c: 606: {
;mypic.c: 607: BatteryState = BatteryStateSwitch(gBatteryVoltage);
	movf	(_gBatteryVoltage+1),w
	movwf	(?_BatteryStateSwitch+1)
	movf	(_gBatteryVoltage),w
	movwf	(?_BatteryStateSwitch)
	fcall	_BatteryStateSwitch
	movwf	(_BatteryState)
	line	611
	
l12210:	
;mypic.c: 608: }
;mypic.c: 609: }
;mypic.c: 611: if(EnhanceCharge_Time >= 36000)
	movlw	high(08CA0h)
	subwf	(_EnhanceCharge_Time+1),w
	movlw	low(08CA0h)
	skipnz
	subwf	(_EnhanceCharge_Time),w
	skipc
	goto	u3431
	goto	u3430
u3431:
	goto	l8833
u3430:
	line	613
	
l12212:	
;mypic.c: 612: {
;mypic.c: 613: EnhanceChargeFlag = 0;
	bcf	(_EnhanceChargeFlag/8),(_EnhanceChargeFlag)&7
	line	614
	
l12214:	
;mypic.c: 614: EnhanceCharge_Time = 0;
	clrf	(_EnhanceCharge_Time)
	clrf	(_EnhanceCharge_Time+1)
	line	615
	
l12216:	
;mypic.c: 615: CTKSoftDelay(100);
	movlw	064h
	movwf	(?_CTKSoftDelay)
	clrf	(?_CTKSoftDelay+1)
	fcall	_CTKSoftDelay
	line	616
	
l12218:	
;mypic.c: 616: gBatteryVoltage = GetBatteryVoltage();
	fcall	_GetBatteryVoltage
	movf	(1+(?_GetBatteryVoltage)),w
	movwf	(_gBatteryVoltage+1)
	movf	(0+(?_GetBatteryVoltage)),w
	movwf	(_gBatteryVoltage)
	line	617
	
l12220:	
;mypic.c: 617: if(gBatteryVoltage > *(BatteryStandard + 1) - TemBase + TemVoltage - ADBase + ADVoltage)
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
	goto	u3445
	movf	(_gBatteryVoltage),w
	subwf	0+(??_SwitchBatteryState+4)+0,w
u3445:
	skipnc
	goto	u3441
	goto	u3440
u3441:
	goto	l8833
u3440:
	line	619
	
l12222:	
;mypic.c: 618: {
;mypic.c: 619: BatteryState = BatteryStateSwitch(gBatteryVoltage);
	movf	(_gBatteryVoltage+1),w
	movwf	(?_BatteryStateSwitch+1)
	movf	(_gBatteryVoltage),w
	movwf	(?_BatteryStateSwitch)
	fcall	_BatteryStateSwitch
	movwf	(_BatteryState)
	goto	l8833
	line	627
	
l12224:	
;mypic.c: 625: {
;mypic.c: 627: if(BatteryLastState != 2)
	movf	(_BatteryLastState),w
	xorlw	02h&0ffh
	skipnz
	goto	u3451
	goto	u3450
u3451:
	goto	l12236
u3450:
	line	630
	
l12226:	
;mypic.c: 628: {
;mypic.c: 630: EnhanceChargeFlag = 0;
	bcf	(_EnhanceChargeFlag/8),(_EnhanceChargeFlag)&7
	line	631
	
l12228:	
;mypic.c: 631: EnhanceCharge_Time = 0;
	clrf	(_EnhanceCharge_Time)
	clrf	(_EnhanceCharge_Time+1)
	line	632
	
l12230:	
;mypic.c: 632: EqualizingChargeFlag = 0;
	bcf	(_EqualizingChargeFlag/8),(_EqualizingChargeFlag)&7
	line	633
;mypic.c: 633: EqualizingCharge_Time = 0;
	clrf	(_EqualizingCharge_Time)
	clrf	(_EqualizingCharge_Time+1)
	line	634
	
l12232:	
;mypic.c: 634: FloatingChargeFlag = 0;
	bcf	(_FloatingChargeFlag/8),(_FloatingChargeFlag)&7
	line	635
	
l12234:	
;mypic.c: 635: BatteryLastState = 2;
	movlw	(02h)
	movwf	(_BatteryLastState)
	line	637
	
l12236:	
;mypic.c: 636: }
;mypic.c: 637: if(PVState == 1)
	btfss	(_PVState/8),(_PVState)&7
	goto	u3461
	goto	u3460
u3461:
	goto	l8806
u3460:
	line	639
	
l12238:	
;mypic.c: 638: {
;mypic.c: 639: ClampVoltage = *(BatteryStandard + 7) - TemBase + TemVoltage - ADBase + ADVoltage;
	movf	(_BatteryStandard),w
	addlw	0Eh
	movwf	fsr1l
	clrf fsr1h	
	
	moviw	[0]fsr1
	movwf	(_ClampVoltage)
	moviw	[1]fsr1
	movwf	(_ClampVoltage+1)
	
l12240:	
	movf	(_ADBase),w
	subwf	(_ClampVoltage),f
	movf	(_ADBase+1),w
	subwfb	(_ClampVoltage+1),f
	
l12242:	
	movf	(_ADVoltage),w
	addwf	(_ClampVoltage),f
	movf	(_ADVoltage+1),w
	addwfc	(_ClampVoltage+1),f
	line	640
	
l12244:	
;mypic.c: 640: if(EqualizingChargeFlag == 0)
	btfsc	(_EqualizingChargeFlag/8),(_EqualizingChargeFlag)&7
	goto	u3471
	goto	u3470
u3471:
	goto	l12250
u3470:
	line	642
	
l12246:	
;mypic.c: 641: {
;mypic.c: 642: EqualizingChargeFlag = 1;
	bsf	(_EqualizingChargeFlag/8),(_EqualizingChargeFlag)&7
	line	643
	
l12248:	
;mypic.c: 643: EqualizingCharge_Time = 0;
	clrf	(_EqualizingCharge_Time)
	clrf	(_EqualizingCharge_Time+1)
	line	645
	
l12250:	
;mypic.c: 644: }
;mypic.c: 645: gBatteryVoltage = GetBatteryVoltage();
	fcall	_GetBatteryVoltage
	movf	(1+(?_GetBatteryVoltage)),w
	movwf	(_gBatteryVoltage+1)
	movf	(0+(?_GetBatteryVoltage)),w
	movwf	(_gBatteryVoltage)
	line	646
	
l12252:	
;mypic.c: 646: if(gBatteryVoltage < *(BatteryStandard + 0) - TemBase + TemVoltage - ADBase + ADVoltage)
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
	goto	u3485
	movf	0+(??_SwitchBatteryState+4)+0,w
	subwf	(_gBatteryVoltage),w
u3485:
	skipnc
	goto	u3481
	goto	u3480
u3481:
	goto	l12262
u3480:
	line	648
	
l12254:	
;mypic.c: 647: {
;mypic.c: 648: BatteryState = 1;
	clrf	(_BatteryState)
	incf	(_BatteryState),f
	goto	l12262
	line	651
	
l8806:	
	line	653
;mypic.c: 651: else
;mypic.c: 652: {
;mypic.c: 653: EqualizingChargeFlag = 0;
	bcf	(_EqualizingChargeFlag/8),(_EqualizingChargeFlag)&7
	line	654
	
l12256:	
;mypic.c: 654: EqualizingCharge_Time = 0;
	clrf	(_EqualizingCharge_Time)
	clrf	(_EqualizingCharge_Time+1)
	line	655
	
l12258:	
;mypic.c: 655: gBatteryVoltage = GetBatteryVoltage();
	fcall	_GetBatteryVoltage
	movf	(1+(?_GetBatteryVoltage)),w
	movwf	(_gBatteryVoltage+1)
	movf	(0+(?_GetBatteryVoltage)),w
	movwf	(_gBatteryVoltage)
	line	656
	
l12260:	
;mypic.c: 656: BatteryState = BatteryStateSwitch(gBatteryVoltage);
	movf	(_gBatteryVoltage+1),w
	movwf	(?_BatteryStateSwitch+1)
	movf	(_gBatteryVoltage),w
	movwf	(?_BatteryStateSwitch)
	fcall	_BatteryStateSwitch
	movwf	(_BatteryState)
	line	659
	
l12262:	
;mypic.c: 657: }
;mypic.c: 659: if(EqualizingCharge_Time >= 36000)
	movlw	high(08CA0h)
	subwf	(_EqualizingCharge_Time+1),w
	movlw	low(08CA0h)
	skipnz
	subwf	(_EqualizingCharge_Time),w
	skipc
	goto	u3491
	goto	u3490
u3491:
	goto	l8833
u3490:
	line	661
	
l12264:	
;mypic.c: 660: {
;mypic.c: 661: EqualizingChargeFlag = 0;
	bcf	(_EqualizingChargeFlag/8),(_EqualizingChargeFlag)&7
	line	662
	
l12266:	
;mypic.c: 662: EqualizingCharge_Time = 0;
	clrf	(_EqualizingCharge_Time)
	clrf	(_EqualizingCharge_Time+1)
	line	663
	
l12268:	
;mypic.c: 663: CTKSoftDelay(100);
	movlw	064h
	movwf	(?_CTKSoftDelay)
	clrf	(?_CTKSoftDelay+1)
	fcall	_CTKSoftDelay
	line	664
	
l12270:	
;mypic.c: 664: gBatteryVoltage = GetBatteryVoltage();
	fcall	_GetBatteryVoltage
	movf	(1+(?_GetBatteryVoltage)),w
	movwf	(_gBatteryVoltage+1)
	movf	(0+(?_GetBatteryVoltage)),w
	movwf	(_gBatteryVoltage)
	goto	l12222
	line	672
	
l12274:	
;mypic.c: 671: {
;mypic.c: 672: if(BatteryLastState != 3)
	movf	(_BatteryLastState),w
	xorlw	03h&0ffh
	skipnz
	goto	u3501
	goto	u3500
u3501:
	goto	l12286
u3500:
	line	675
	
l12276:	
;mypic.c: 673: {
;mypic.c: 675: EnhanceChargeFlag = 0;
	bcf	(_EnhanceChargeFlag/8),(_EnhanceChargeFlag)&7
	line	676
	
l12278:	
;mypic.c: 676: EnhanceCharge_Time = 0;
	clrf	(_EnhanceCharge_Time)
	clrf	(_EnhanceCharge_Time+1)
	line	677
	
l12280:	
;mypic.c: 677: EqualizingChargeFlag = 0;
	bcf	(_EqualizingChargeFlag/8),(_EqualizingChargeFlag)&7
	line	678
;mypic.c: 678: EqualizingCharge_Time = 0;
	clrf	(_EqualizingCharge_Time)
	clrf	(_EqualizingCharge_Time+1)
	line	679
	
l12282:	
;mypic.c: 679: FloatingChargeFlag = 0;
	bcf	(_FloatingChargeFlag/8),(_FloatingChargeFlag)&7
	line	680
	
l12284:	
;mypic.c: 680: BatteryLastState = 3;
	movlw	(03h)
	movwf	(_BatteryLastState)
	line	683
	
l12286:	
;mypic.c: 682: }
;mypic.c: 683: if(PVState == 1)
	btfss	(_PVState/8),(_PVState)&7
	goto	u3511
	goto	u3510
u3511:
	goto	l8813
u3510:
	line	685
	
l12288:	
;mypic.c: 684: {
;mypic.c: 685: ClampVoltage = *(BatteryStandard + 7) - TemBase + TemVoltage - ADBase + ADVoltage;
	movf	(_BatteryStandard),w
	addlw	0Eh
	movwf	fsr1l
	clrf fsr1h	
	
	moviw	[0]fsr1
	movwf	(_ClampVoltage)
	moviw	[1]fsr1
	movwf	(_ClampVoltage+1)
	
l12290:	
	movf	(_ADBase),w
	subwf	(_ClampVoltage),f
	movf	(_ADBase+1),w
	subwfb	(_ClampVoltage+1),f
	
l12292:	
	movf	(_ADVoltage),w
	addwf	(_ClampVoltage),f
	movf	(_ADVoltage+1),w
	addwfc	(_ClampVoltage+1),f
	line	686
	
l12294:	
;mypic.c: 686: if(EqualizingChargeFlag == 0)
	btfsc	(_EqualizingChargeFlag/8),(_EqualizingChargeFlag)&7
	goto	u3521
	goto	u3520
u3521:
	goto	l12300
u3520:
	line	688
	
l12296:	
;mypic.c: 687: {
;mypic.c: 688: EqualizingChargeFlag = 1;
	bsf	(_EqualizingChargeFlag/8),(_EqualizingChargeFlag)&7
	line	689
	
l12298:	
;mypic.c: 689: EqualizingCharge_Time = 0;
	clrf	(_EqualizingCharge_Time)
	clrf	(_EqualizingCharge_Time+1)
	line	691
	
l12300:	
;mypic.c: 690: }
;mypic.c: 691: gBatteryVoltage = GetBatteryVoltage();
	fcall	_GetBatteryVoltage
	movf	(1+(?_GetBatteryVoltage)),w
	movwf	(_gBatteryVoltage+1)
	movf	(0+(?_GetBatteryVoltage)),w
	movwf	(_gBatteryVoltage)
	line	692
	
l12302:	
;mypic.c: 692: if(gBatteryVoltage < *(BatteryStandard + 1) - TemBase + TemVoltage - ADBase + ADVoltage)
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
	goto	u3535
	movf	0+(??_SwitchBatteryState+4)+0,w
	subwf	(_gBatteryVoltage),w
u3535:
	skipnc
	goto	u3531
	goto	u3530
u3531:
	goto	l12312
u3530:
	line	694
	
l12304:	
;mypic.c: 693: {
;mypic.c: 694: BatteryState = BatteryStateSwitch(gBatteryVoltage);
	movf	(_gBatteryVoltage+1),w
	movwf	(?_BatteryStateSwitch+1)
	movf	(_gBatteryVoltage),w
	movwf	(?_BatteryStateSwitch)
	fcall	_BatteryStateSwitch
	movwf	(_BatteryState)
	goto	l12312
	line	697
	
l8813:	
	line	699
;mypic.c: 697: else
;mypic.c: 698: {
;mypic.c: 699: EqualizingChargeFlag = 0;
	bcf	(_EqualizingChargeFlag/8),(_EqualizingChargeFlag)&7
	line	700
	
l12306:	
;mypic.c: 700: EqualizingCharge_Time = 0;
	clrf	(_EqualizingCharge_Time)
	clrf	(_EqualizingCharge_Time+1)
	line	701
	
l12308:	
;mypic.c: 701: gBatteryVoltage = GetBatteryVoltage();
	fcall	_GetBatteryVoltage
	movf	(1+(?_GetBatteryVoltage)),w
	movwf	(_gBatteryVoltage+1)
	movf	(0+(?_GetBatteryVoltage)),w
	movwf	(_gBatteryVoltage)
	goto	l12304
	line	705
	
l12312:	
;mypic.c: 703: }
;mypic.c: 705: if(EqualizingCharge_Time >= 36000)
	movlw	high(08CA0h)
	subwf	(_EqualizingCharge_Time+1),w
	movlw	low(08CA0h)
	skipnz
	subwf	(_EqualizingCharge_Time),w
	skipc
	goto	u3541
	goto	u3540
u3541:
	goto	l8833
u3540:
	goto	l12264
	line	718
	
l12324:	
;mypic.c: 717: {
;mypic.c: 718: if(BatteryLastState != 4)
	movf	(_BatteryLastState),w
	xorlw	04h&0ffh
	skipnz
	goto	u3551
	goto	u3550
u3551:
	goto	l12336
u3550:
	line	721
	
l12326:	
;mypic.c: 719: {
;mypic.c: 721: EnhanceChargeFlag = 0;
	bcf	(_EnhanceChargeFlag/8),(_EnhanceChargeFlag)&7
	line	722
	
l12328:	
;mypic.c: 722: EnhanceCharge_Time = 0;
	clrf	(_EnhanceCharge_Time)
	clrf	(_EnhanceCharge_Time+1)
	line	723
	
l12330:	
;mypic.c: 723: EqualizingChargeFlag = 0;
	bcf	(_EqualizingChargeFlag/8),(_EqualizingChargeFlag)&7
	line	724
;mypic.c: 724: EqualizingCharge_Time = 0;
	clrf	(_EqualizingCharge_Time)
	clrf	(_EqualizingCharge_Time+1)
	line	725
	
l12332:	
;mypic.c: 725: FloatingChargeFlag = 0;
	bcf	(_FloatingChargeFlag/8),(_FloatingChargeFlag)&7
	line	726
	
l12334:	
;mypic.c: 726: BatteryLastState = 4;
	movlw	(04h)
	movwf	(_BatteryLastState)
	line	729
	
l12336:	
;mypic.c: 728: }
;mypic.c: 729: if(PVState == 1)
	btfss	(_PVState/8),(_PVState)&7
	goto	u3561
	goto	u3560
u3561:
	goto	l8820
u3560:
	line	731
	
l12338:	
;mypic.c: 730: {
;mypic.c: 731: ClampVoltage = *(BatteryStandard + 7) - TemBase + TemVoltage - ADBase + ADVoltage;
	movf	(_BatteryStandard),w
	addlw	0Eh
	movwf	fsr1l
	clrf fsr1h	
	
	moviw	[0]fsr1
	movwf	(_ClampVoltage)
	moviw	[1]fsr1
	movwf	(_ClampVoltage+1)
	
l12340:	
	movf	(_ADBase),w
	subwf	(_ClampVoltage),f
	movf	(_ADBase+1),w
	subwfb	(_ClampVoltage+1),f
	
l12342:	
	movf	(_ADVoltage),w
	addwf	(_ClampVoltage),f
	movf	(_ADVoltage+1),w
	addwfc	(_ClampVoltage+1),f
	line	732
	
l12344:	
;mypic.c: 732: if(EqualizingChargeFlag == 0)
	btfsc	(_EqualizingChargeFlag/8),(_EqualizingChargeFlag)&7
	goto	u3571
	goto	u3570
u3571:
	goto	l12350
u3570:
	line	734
	
l12346:	
;mypic.c: 733: {
;mypic.c: 734: EqualizingChargeFlag = 1;
	bsf	(_EqualizingChargeFlag/8),(_EqualizingChargeFlag)&7
	line	735
	
l12348:	
;mypic.c: 735: EqualizingCharge_Time = 0;
	clrf	(_EqualizingCharge_Time)
	clrf	(_EqualizingCharge_Time+1)
	line	737
	
l12350:	
;mypic.c: 736: }
;mypic.c: 737: gBatteryVoltage = GetBatteryVoltage();
	fcall	_GetBatteryVoltage
	movf	(1+(?_GetBatteryVoltage)),w
	movwf	(_gBatteryVoltage+1)
	movf	(0+(?_GetBatteryVoltage)),w
	movwf	(_gBatteryVoltage)
	line	738
	
l12352:	
;mypic.c: 738: if(gBatteryVoltage < *(BatteryStandard + 3) - TemBase + TemVoltage - ADBase + ADVoltage)
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
	goto	u3585
	movf	0+(??_SwitchBatteryState+4)+0,w
	subwf	(_gBatteryVoltage),w
u3585:
	skipnc
	goto	u3581
	goto	u3580
u3581:
	goto	l12362
u3580:
	line	740
	
l12354:	
;mypic.c: 739: {
;mypic.c: 740: BatteryState = BatteryStateSwitch(gBatteryVoltage);
	movf	(_gBatteryVoltage+1),w
	movwf	(?_BatteryStateSwitch+1)
	movf	(_gBatteryVoltage),w
	movwf	(?_BatteryStateSwitch)
	fcall	_BatteryStateSwitch
	movwf	(_BatteryState)
	goto	l12362
	line	743
	
l8820:	
	line	745
;mypic.c: 743: else
;mypic.c: 744: {
;mypic.c: 745: EqualizingChargeFlag = 0;
	bcf	(_EqualizingChargeFlag/8),(_EqualizingChargeFlag)&7
	line	746
	
l12356:	
;mypic.c: 746: EqualizingCharge_Time = 0;
	clrf	(_EqualizingCharge_Time)
	clrf	(_EqualizingCharge_Time+1)
	line	747
	
l12358:	
;mypic.c: 747: gBatteryVoltage = GetBatteryVoltage();
	fcall	_GetBatteryVoltage
	movf	(1+(?_GetBatteryVoltage)),w
	movwf	(_gBatteryVoltage+1)
	movf	(0+(?_GetBatteryVoltage)),w
	movwf	(_gBatteryVoltage)
	goto	l12354
	line	751
	
l12362:	
;mypic.c: 749: }
;mypic.c: 751: if(EqualizingCharge_Time >= 36000)
	movlw	high(08CA0h)
	subwf	(_EqualizingCharge_Time+1),w
	movlw	low(08CA0h)
	skipnz
	subwf	(_EqualizingCharge_Time),w
	skipc
	goto	u3591
	goto	u3590
u3591:
	goto	l8833
u3590:
	goto	l12264
	line	764
	
l12374:	
;mypic.c: 763: {
;mypic.c: 764: if(BatteryLastState != 5)
	movf	(_BatteryLastState),w
	xorlw	05h&0ffh
	skipnz
	goto	u3601
	goto	u3600
u3601:
	goto	l12386
u3600:
	line	767
	
l12376:	
;mypic.c: 765: {
;mypic.c: 767: EnhanceChargeFlag = 0;
	bcf	(_EnhanceChargeFlag/8),(_EnhanceChargeFlag)&7
	line	768
	
l12378:	
;mypic.c: 768: EnhanceCharge_Time = 0;
	clrf	(_EnhanceCharge_Time)
	clrf	(_EnhanceCharge_Time+1)
	line	769
	
l12380:	
;mypic.c: 769: EqualizingChargeFlag = 0;
	bcf	(_EqualizingChargeFlag/8),(_EqualizingChargeFlag)&7
	line	770
;mypic.c: 770: EqualizingCharge_Time = 0;
	clrf	(_EqualizingCharge_Time)
	clrf	(_EqualizingCharge_Time+1)
	line	771
	
l12382:	
;mypic.c: 771: FloatingChargeFlag = 0;
	bcf	(_FloatingChargeFlag/8),(_FloatingChargeFlag)&7
	line	772
	
l12384:	
;mypic.c: 772: BatteryLastState = 5;
	movlw	(05h)
	movwf	(_BatteryLastState)
	line	774
	
l12386:	
;mypic.c: 773: }
;mypic.c: 774: if(PVState == 1)
	btfss	(_PVState/8),(_PVState)&7
	goto	u3611
	goto	u3610
u3611:
	goto	l8827
u3610:
	line	776
	
l12388:	
;mypic.c: 775: {
;mypic.c: 776: ClampVoltage = *(BatteryStandard + 8) - TemBase + TemVoltage - ADBase + ADVoltage;
	movf	(_BatteryStandard),w
	addlw	010h
	movwf	fsr1l
	clrf fsr1h	
	
	moviw	[0]fsr1
	movwf	(_ClampVoltage)
	moviw	[1]fsr1
	movwf	(_ClampVoltage+1)
	
l12390:	
	movf	(_ADBase),w
	subwf	(_ClampVoltage),f
	movf	(_ADBase+1),w
	subwfb	(_ClampVoltage+1),f
	
l12392:	
	movf	(_ADVoltage),w
	addwf	(_ClampVoltage),f
	movf	(_ADVoltage+1),w
	addwfc	(_ClampVoltage+1),f
	line	777
	
l12394:	
;mypic.c: 777: FloatingChargeFlag = 1;
	bsf	(_FloatingChargeFlag/8),(_FloatingChargeFlag)&7
	line	778
;mypic.c: 778: }
	goto	l12270
	line	779
	
l8827:	
	line	781
;mypic.c: 779: else
;mypic.c: 780: {
;mypic.c: 781: FloatingChargeFlag = 0;
	bcf	(_FloatingChargeFlag/8),(_FloatingChargeFlag)&7
	goto	l12270
	line	790
	
l12400:	
;mypic.c: 789: {
;mypic.c: 790: if(BatteryLastState != 6)
	movf	(_BatteryLastState),w
	xorlw	06h&0ffh
	skipnz
	goto	u3621
	goto	u3620
u3621:
	goto	l12422
u3620:
	line	792
	
l12402:	
;mypic.c: 791: {
;mypic.c: 792: ClampVoltage = 0;
	clrf	(_ClampVoltage)
	clrf	(_ClampVoltage+1)
	line	793
	
l12404:	
;mypic.c: 793: TMR2IE = 0;
	movlb 1	; select bank1
	bcf	(1161/8)^080h,(1161)&7
	line	794
	
l12406:	
;mypic.c: 794: T2Flag = 0;
	bcf	(_T2Flag/8),(_T2Flag)&7
	line	795
	
l12408:	
;mypic.c: 795: PWMFlag = 0;
	bcf	(_PWMFlag/8),(_PWMFlag)&7
	line	796
	
l12410:	
;mypic.c: 796: (PORTB |= (1 << 0));
	movlb 0	; select bank0
	bsf	(13)+(0/8),(0)&7	;volatile
	line	797
	
l12412:	
;mypic.c: 797: (PORTA |= (1 << 0));
	bsf	(12)+(0/8),(0)&7	;volatile
	line	799
	
l12414:	
;mypic.c: 799: EnhanceChargeFlag = 0;
	bcf	(_EnhanceChargeFlag/8),(_EnhanceChargeFlag)&7
	line	800
;mypic.c: 800: EnhanceCharge_Time = 0;
	clrf	(_EnhanceCharge_Time)
	clrf	(_EnhanceCharge_Time+1)
	line	801
	
l12416:	
;mypic.c: 801: EqualizingChargeFlag = 0;
	bcf	(_EqualizingChargeFlag/8),(_EqualizingChargeFlag)&7
	line	802
;mypic.c: 802: EqualizingCharge_Time = 0;
	clrf	(_EqualizingCharge_Time)
	clrf	(_EqualizingCharge_Time+1)
	line	803
	
l12418:	
;mypic.c: 803: FloatingChargeFlag = 0;
	bcf	(_FloatingChargeFlag/8),(_FloatingChargeFlag)&7
	line	804
	
l12420:	
;mypic.c: 804: BatteryLastState = 6;
	movlw	(06h)
	movwf	(_BatteryLastState)
	line	806
	
l12422:	
;mypic.c: 805: }
;mypic.c: 806: gBatteryVoltage = GetBatteryVoltage();
	fcall	_GetBatteryVoltage
	movf	(1+(?_GetBatteryVoltage)),w
	movwf	(_gBatteryVoltage+1)
	movf	(0+(?_GetBatteryVoltage)),w
	movwf	(_gBatteryVoltage)
	line	807
	
l12424:	
;mypic.c: 807: if(gBatteryVoltage < *(BatteryStandard + 5) - TemBase + TemVoltage - ADBase + ADVoltage)
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
	goto	u3635
	movf	0+(??_SwitchBatteryState+4)+0,w
	subwf	(_gBatteryVoltage),w
u3635:
	skipnc
	goto	u3631
	goto	u3630
u3631:
	goto	l8833
u3630:
	goto	l12222
	line	557
	
l12428:	
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
	goto	l12154
	xorlw	1^0	; case 1
	skipnz
	goto	l12172
	xorlw	2^1	; case 2
	skipnz
	goto	l12224
	xorlw	3^2	; case 3
	skipnz
	goto	l12274
	xorlw	4^3	; case 4
	skipnz
	goto	l12324
	xorlw	5^4	; case 5
	skipnz
	goto	l12374
	xorlw	6^5	; case 6
	skipnz
	goto	l12400
	xorlw	7^6	; case 7
	skipnz
	goto	l8833
	goto	l8833

	line	817
	
l8833:	
	return
	opt stack 0
GLOBAL	__end_of_SwitchBatteryState
	__end_of_SwitchBatteryState:
;; =============== function _SwitchBatteryState ends ============

	signat	_SwitchBatteryState,88
	global	_LoadCurrentDealWith
psect	text823,local,class=CODE,delta=2
global __ptext823
__ptext823:

;; *************** function _LoadCurrentDealWith *****************
;; Defined at:
;;		line 290 in file "C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.c"
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
;; Hardware stack levels required when called:    6
;; This function calls:
;;		_GetLoadCurrentVoltage
;; This function is called by:
;;		_main
;; This function uses a non-reentrant model
;;
psect	text823
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.c"
	line	290
	global	__size_of_LoadCurrentDealWith
	__size_of_LoadCurrentDealWith	equ	__end_of_LoadCurrentDealWith-_LoadCurrentDealWith
	
_LoadCurrentDealWith:	
	opt	stack 9
; Regs used in _LoadCurrentDealWith: [wreg+status,2+status,0+pclath+cstack]
	line	291
	
l12096:	
;mypic.c: 291: if((BatteryState != 1) && (BatteryState != 6) && LoadOpen)
	decf	(_BatteryState),w
	skipnz
	goto	u3271
	goto	u3270
u3271:
	goto	l8726
u3270:
	
l12098:	
	movf	(_BatteryState),w
	xorlw	06h&0ffh
	skipnz
	goto	u3281
	goto	u3280
u3281:
	goto	l8726
u3280:
	
l12100:	
	btfss	(_LoadOpen/8),(_LoadOpen)&7
	goto	u3291
	goto	u3290
u3291:
	goto	l8726
u3290:
	line	295
	
l12102:	
	fcall	_GetLoadCurrentVoltage
	line	296
	
l12104:	
	line	297
	
l12106:	
;mypic.c: 297: if((FbVoltage < 134) && (LoadState == 1))
	
l12108:	
	btfss	(_LoadState/8),(_LoadState)&7
	goto	u3301
	goto	u3300
u3301:
	goto	l12120
u3300:
	line	299
	
l12110:	
;mypic.c: 298: {
;mypic.c: 299: if(LoadFlag == 1)
	btfss	(_LoadFlag/8),(_LoadFlag)&7
	goto	u3311
	goto	u3310
u3311:
	goto	l12120
u3310:
	line	301
	
l12112:	
;mypic.c: 300: {
;mypic.c: 301: LoadState = 1;
	bsf	(_LoadState/8),(_LoadState)&7
	line	302
	
l12114:	
;mypic.c: 302: OverLoadTime = 0;
	clrf	(_OverLoadTime)
	clrf	(_OverLoadTime+1)
	line	303
	
l12116:	
;mypic.c: 303: (PORTA &= ~(1 << 4));
	bcf	(12)+(4/8),(4)&7	;volatile
	line	304
	
l12118:	
;mypic.c: 304: LoadFlag = 0;
	bcf	(_LoadFlag/8),(_LoadFlag)&7
	line	307
	
l12120:	
;mypic.c: 305: }
;mypic.c: 306: }
;mypic.c: 307: if((FbVoltage >= 134) || (LoadState == 0))
	
l12122:	
	btfsc	(_LoadState/8),(_LoadState)&7
	goto	u3321
	goto	u3320
u3321:
	goto	l8742
u3320:
	
l8731:	
	line	309
;mypic.c: 308: {
;mypic.c: 309: LoadState = 0;
	bcf	(_LoadState/8),(_LoadState)&7
	line	310
	
l12124:	
;mypic.c: 310: if(( FbVoltage >= 400) || (LoadShort == 1))
	
l12126:	
	btfss	(_LoadShort/8),(_LoadShort)&7
	goto	u3331
	goto	u3330
u3331:
	goto	l12132
u3330:
	
l8734:	
	line	312
;mypic.c: 311: {
;mypic.c: 312: (PORTA |= (1 << 4));
	bsf	(12)+(4/8),(4)&7	;volatile
	line	313
;mypic.c: 313: LoadFlag = 1;
	bsf	(_LoadFlag/8),(_LoadFlag)&7
	line	314
	
l12128:	
;mypic.c: 314: OverLoadTime = 0;
	clrf	(_OverLoadTime)
	clrf	(_OverLoadTime+1)
	line	315
	
l12130:	
;mypic.c: 318: ErrorType = 5;
	bsf	(_LoadShort/8),(_LoadShort)&7
	line	319
;mypic.c: 319: }
	goto	l8742
	line	322
	
l12132:	
;mypic.c: 320: else
;mypic.c: 321: {
;mypic.c: 322: if(OverLoadTime == 0)
	movf	((_OverLoadTime+1)),w
	iorwf	((_OverLoadTime)),w
	skipz
	goto	u3341
	goto	u3340
u3341:
	goto	l12136
u3340:
	line	324
	
l12134:	
;mypic.c: 323: {
;mypic.c: 324: OverLoadTime = 1;
	clrf	(_OverLoadTime)
	incf	(_OverLoadTime),f
	clrf	(_OverLoadTime+1)
	line	326
	
l12136:	
;mypic.c: 325: }
;mypic.c: 326: if(OverLoadTime >= 3000)
	movlw	high(0BB8h)
	subwf	(_OverLoadTime+1),w
	movlw	low(0BB8h)
	skipnz
	subwf	(_OverLoadTime),w
	skipc
	goto	u3351
	goto	u3350
u3351:
	goto	l8742
u3350:
	line	328
	
l12138:	
;mypic.c: 327: {
;mypic.c: 328: OverLoadTime = 1;
	clrf	(_OverLoadTime)
	incf	(_OverLoadTime),f
	clrf	(_OverLoadTime+1)
	line	329
	
l12140:	
;mypic.c: 329: if(LoadFlag == 0)
	btfsc	(_LoadFlag/8),(_LoadFlag)&7
	goto	u3361
	goto	u3360
u3361:
	goto	l8738
u3360:
	line	331
	
l12142:	
;mypic.c: 330: {
;mypic.c: 331: (PORTA |= (1 << 4));
	bsf	(12)+(4/8),(4)&7	;volatile
	line	332
;mypic.c: 332: LoadFlag = 1;
	bsf	(_LoadFlag/8),(_LoadFlag)&7
	line	333
;mypic.c: 333: }
	goto	l8742
	line	334
	
l8738:	
;mypic.c: 334: else if(LoadFlag == 1)
	btfss	(_LoadFlag/8),(_LoadFlag)&7
	goto	u3371
	goto	u3370
u3371:
	goto	l8735
u3370:
	line	336
	
l12144:	
;mypic.c: 335: {
;mypic.c: 336: (PORTA &= ~(1 << 4));
	bcf	(12)+(4/8),(4)&7	;volatile
	line	337
;mypic.c: 337: LoadFlag = 0;
	bcf	(_LoadFlag/8),(_LoadFlag)&7
	line	338
;mypic.c: 338: LoadState = 1;
	bsf	(_LoadState/8),(_LoadState)&7
	line	339
	
l12146:	
;mypic.c: 339: OverLoadTime = 0;
	clrf	(_OverLoadTime)
	clrf	(_OverLoadTime+1)
	goto	l8742
	line	342
	
l8735:	
	goto	l8742
	line	345
	
l8726:	
	line	347
;mypic.c: 345: else
;mypic.c: 346: {
;mypic.c: 347: (PORTA |= (1 << 4));
	bsf	(12)+(4/8),(4)&7	;volatile
	line	348
;mypic.c: 348: LoadFlag = 1;
	bsf	(_LoadFlag/8),(_LoadFlag)&7
	line	349
	
l12148:	
;mypic.c: 349: OverLoadTime = 0;
	clrf	(_OverLoadTime)
	clrf	(_OverLoadTime+1)
	line	350
	
l12150:	
;mypic.c: 350: LoadState = 1;
	bsf	(_LoadState/8),(_LoadState)&7
	line	352
	
l8742:	
	return
	opt stack 0
GLOBAL	__end_of_LoadCurrentDealWith
	__end_of_LoadCurrentDealWith:
;; =============== function _LoadCurrentDealWith ends ============

	signat	_LoadCurrentDealWith,88
	global	_KaiJi
psect	text824,local,class=CODE,delta=2
global __ptext824
__ptext824:

;; *************** function _KaiJi *****************
;; Defined at:
;;		line 822 in file "C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.c"
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
;; Hardware stack levels required when called:    6
;; This function calls:
;;		_GetBatteryVoltage
;;		_GetSolarPanelVoltage
;;		___wmul
;;		_GetLoadCurrentVoltage
;; This function is called by:
;;		_main
;; This function uses a non-reentrant model
;;
psect	text824
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.c"
	line	822
	global	__size_of_KaiJi
	__size_of_KaiJi	equ	__end_of_KaiJi-_KaiJi
	
_KaiJi:	
	opt	stack 9
; Regs used in _KaiJi: [wreg+status,2+status,0+pclath+cstack]
	line	826
	
l12074:	
;mypic.c: 826: gBatteryVoltage = GetBatteryVoltage();
	fcall	_GetBatteryVoltage
	movf	(1+(?_GetBatteryVoltage)),w
	movwf	(_gBatteryVoltage+1)
	movf	(0+(?_GetBatteryVoltage)),w
	movwf	(_gBatteryVoltage)
	line	827
	
l12076:	
;mypic.c: 827: if(gBatteryVoltage < 391)
	movlw	high(0187h)
	subwf	(_gBatteryVoltage+1),w
	movlw	low(0187h)
	skipnz
	subwf	(_gBatteryVoltage),w
	skipnc
	goto	u3231
	goto	u3230
u3231:
	goto	l12080
u3230:
	line	829
	
l12078:	
;mypic.c: 831: System24V = 0;
	movlw	(_Battery_12V)&0ffh
	movwf	(_BatteryStandard)
	line	832
;mypic.c: 832: }
	goto	l12086
	line	833
	
l12080:	
;mypic.c: 833: else if((gBatteryVoltage < 782) && (gBatteryVoltage > 512))
	movlw	high(030Eh)
	subwf	(_gBatteryVoltage+1),w
	movlw	low(030Eh)
	skipnz
	subwf	(_gBatteryVoltage),w
	skipnc
	goto	u3241
	goto	u3240
u3241:
	goto	l12086
u3240:
	
l12082:	
	movlw	high(0201h)
	subwf	(_gBatteryVoltage+1),w
	movlw	low(0201h)
	skipnz
	subwf	(_gBatteryVoltage),w
	skipc
	goto	u3251
	goto	u3250
u3251:
	goto	l12086
u3250:
	line	835
	
l12084:	
;mypic.c: 837: System24V = 1;
	movlw	(_Battery_24V)&0ffh
	movwf	(_BatteryStandard)
	line	849
;mypic.c: 838: }
	
l12086:	
;mypic.c: 845: }
;mypic.c: 849: SDSolarPanelVoltage = GetSolarPanelVoltage();
	fcall	_GetSolarPanelVoltage
	movf	(1+(?_GetSolarPanelVoltage)),w
	movwf	(_SDSolarPanelVoltage+1)
	movf	(0+(?_GetSolarPanelVoltage)),w
	movwf	(_SDSolarPanelVoltage)
	line	850
;mypic.c: 850: SDBatteryVoltage = GetBatteryVoltage();
	fcall	_GetBatteryVoltage
	movf	(1+(?_GetBatteryVoltage)),w
	movwf	(_SDBatteryVoltage+1)
	movf	(0+(?_GetBatteryVoltage)),w
	movwf	(_SDBatteryVoltage)
	line	852
;mypic.c: 851: if((SDSolarPanelVoltage*47) < ((SDBatteryVoltage*48) - 2400)
;mypic.c: 852: || (SDSolarPanelVoltage*47) > ((SDBatteryVoltage*48) + 2400))
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
	goto	u3265
	movf	0+(??_KaiJi+0)+0,w
	subwf	(0+(?___wmul)),w
u3265:
	skipc
	goto	u3261
	goto	u3260
u3261:
	goto	l12090
u3260:
	
l12088:	
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
	line	859
	
l12090:	
;mypic.c: 858: }
;mypic.c: 859: gFbVoltage = GetLoadCurrentVoltage();
	fcall	_GetLoadCurrentVoltage
	movf	(1+(?_GetLoadCurrentVoltage)),w
	movwf	(_gFbVoltage+1)
	movf	(0+(?_GetLoadCurrentVoltage)),w
	movwf	(_gFbVoltage)
	line	867
	
l12094:	
;mypic.c: 866: }
;mypic.c: 867: ADVoltage = 0;
	clrf	(_ADVoltage)
	clrf	(_ADVoltage+1)
	line	868
;mypic.c: 868: ADBase = 0;
	clrf	(_ADBase)
	clrf	(_ADBase+1)
	line	869
;mypic.c: 869: PVCount = 0;
	clrf	(_PVCount)
	line	870
	
l8844:	
	return
	opt stack 0
GLOBAL	__end_of_KaiJi
	__end_of_KaiJi:
;; =============== function _KaiJi ends ============

	signat	_KaiJi,88
	global	_GetLoadCurrentVoltage
psect	text825,local,class=CODE,delta=2
global __ptext825
__ptext825:

;; *************** function _GetLoadCurrentVoltage *****************
;; Defined at:
;;		line 225 in file "C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.c"
;; Parameters:    Size  Location     Type
;;		None
;; Auto vars:     Size  Location     Type
;;  channel         1    0        unsigned char 
;; Return value:  Size  Location     Type
;;                  2   26[BANK0 ] unsigned int 
;; Registers used:
;;		wreg, status,2, status,0, pclath, cstack
;; Tracked objects:
;;		On entry : 1F/0
;;		On exit  : 1F/0
;;		Unchanged: FFFE0/0
;; Data sizes:     COMMON   BANK0   BANK1   BANK2
;;      Params:         0       2       0       0
;;      Locals:         0       1       0       0
;;      Temps:          0       0       0       0
;;      Totals:         0       3       0       0
;;Total ram usage:        3 bytes
;; Hardware stack levels used:    1
;; Hardware stack levels required when called:    5
;; This function calls:
;;		_getADValue
;; This function is called by:
;;		_LoadCurrentDealWith
;;		_KaiJi
;; This function uses a non-reentrant model
;;
psect	text825
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.c"
	line	225
	global	__size_of_GetLoadCurrentVoltage
	__size_of_GetLoadCurrentVoltage	equ	__end_of_GetLoadCurrentVoltage-_GetLoadCurrentVoltage
	
_GetLoadCurrentVoltage:	
	opt	stack 9
; Regs used in _GetLoadCurrentVoltage: [wreg+status,2+status,0+pclath+cstack]
	line	226
	
l12068:	
	line	227
	
l12070:	
;mypic.c: 227: return getADValue(channel);
	movlw	(019h)
	fcall	_getADValue
	movf	(1+(?_getADValue)),w
	movwf	(?_GetLoadCurrentVoltage+1)
	movf	(0+(?_getADValue)),w
	movwf	(?_GetLoadCurrentVoltage)
	line	228
	
l8705:	
	return
	opt stack 0
GLOBAL	__end_of_GetLoadCurrentVoltage
	__end_of_GetLoadCurrentVoltage:
;; =============== function _GetLoadCurrentVoltage ends ============

	signat	_GetLoadCurrentVoltage,90
	global	_GetSolarPanelVoltage
psect	text826,local,class=CODE,delta=2
global __ptext826
__ptext826:

;; *************** function _GetSolarPanelVoltage *****************
;; Defined at:
;;		line 209 in file "C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.c"
;; Parameters:    Size  Location     Type
;;		None
;; Auto vars:     Size  Location     Type
;;  channel         1    0        unsigned char 
;; Return value:  Size  Location     Type
;;                  2   26[BANK0 ] unsigned int 
;; Registers used:
;;		wreg, status,2, status,0, pclath, cstack
;; Tracked objects:
;;		On entry : 1F/0
;;		On exit  : 1F/0
;;		Unchanged: FFFE0/0
;; Data sizes:     COMMON   BANK0   BANK1   BANK2
;;      Params:         0       2       0       0
;;      Locals:         0       1       0       0
;;      Temps:          0       0       0       0
;;      Totals:         0       3       0       0
;;Total ram usage:        3 bytes
;; Hardware stack levels used:    1
;; Hardware stack levels required when called:    5
;; This function calls:
;;		_getADValue
;; This function is called by:
;;		_SolarPanelDealWith
;;		_KaiJi
;; This function uses a non-reentrant model
;;
psect	text826
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.c"
	line	209
	global	__size_of_GetSolarPanelVoltage
	__size_of_GetSolarPanelVoltage	equ	__end_of_GetSolarPanelVoltage-_GetSolarPanelVoltage
	
_GetSolarPanelVoltage:	
	opt	stack 9
; Regs used in _GetSolarPanelVoltage: [wreg+status,2+status,0+pclath+cstack]
	line	210
	
l12062:	
	line	211
	
l12064:	
;mypic.c: 211: return getADValue(channel);
	movlw	(021h)
	fcall	_getADValue
	movf	(1+(?_getADValue)),w
	movwf	(?_GetSolarPanelVoltage+1)
	movf	(0+(?_getADValue)),w
	movwf	(?_GetSolarPanelVoltage)
	line	212
	
l8699:	
	return
	opt stack 0
GLOBAL	__end_of_GetSolarPanelVoltage
	__end_of_GetSolarPanelVoltage:
;; =============== function _GetSolarPanelVoltage ends ============

	signat	_GetSolarPanelVoltage,90
	global	_GetBatteryVoltage
psect	text827,local,class=CODE,delta=2
global __ptext827
__ptext827:

;; *************** function _GetBatteryVoltage *****************
;; Defined at:
;;		line 201 in file "C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.c"
;; Parameters:    Size  Location     Type
;;		None
;; Auto vars:     Size  Location     Type
;;  channel         1    0        unsigned char 
;; Return value:  Size  Location     Type
;;                  2   26[BANK0 ] unsigned int 
;; Registers used:
;;		wreg, status,2, status,0, pclath, cstack
;; Tracked objects:
;;		On entry : 1D/0
;;		On exit  : 1F/0
;;		Unchanged: FFFE0/0
;; Data sizes:     COMMON   BANK0   BANK1   BANK2
;;      Params:         0       2       0       0
;;      Locals:         0       1       0       0
;;      Temps:          0       0       0       0
;;      Totals:         0       3       0       0
;;Total ram usage:        3 bytes
;; Hardware stack levels used:    1
;; Hardware stack levels required when called:    5
;; This function calls:
;;		_getADValue
;; This function is called by:
;;		_PWMCharge
;;		_SwitchBatteryState
;;		_KaiJi
;; This function uses a non-reentrant model
;;
psect	text827
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.c"
	line	201
	global	__size_of_GetBatteryVoltage
	__size_of_GetBatteryVoltage	equ	__end_of_GetBatteryVoltage-_GetBatteryVoltage
	
_GetBatteryVoltage:	
	opt	stack 9
; Regs used in _GetBatteryVoltage: [wreg+status,2+status,0+pclath+cstack]
	line	202
	
l12056:	
	line	203
	
l12058:	
;mypic.c: 203: return getADValue(channel);
	movlw	(029h)
	fcall	_getADValue
	movf	(1+(?_getADValue)),w
	movwf	(?_GetBatteryVoltage+1)
	movf	(0+(?_getADValue)),w
	movwf	(?_GetBatteryVoltage)
	line	204
	
l8696:	
	return
	opt stack 0
GLOBAL	__end_of_GetBatteryVoltage
	__end_of_GetBatteryVoltage:
;; =============== function _GetBatteryVoltage ends ============

	signat	_GetBatteryVoltage,90
	global	_lcd_move_char
psect	text828,local,class=CODE,delta=2
global __ptext828
__ptext828:

;; *************** function _lcd_move_char *****************
;; Defined at:
;;		line 109 in file "C:\CTK\CTK80A 150928\CTK80A_Jas\LM016.c"
;; Parameters:    Size  Location     Type
;;  postion         1    wreg     unsigned char 
;; Auto vars:     Size  Location     Type
;;  postion         1    8[BANK0 ] unsigned char 
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
;;      Locals:         0       1       0       0
;;      Temps:          0       0       0       0
;;      Totals:         0       1       0       0
;;Total ram usage:        1 bytes
;; Hardware stack levels used:    1
;; Hardware stack levels required when called:    5
;; This function calls:
;;		_API_LCD_MOVE_POINT
;; This function is called by:
;;		_SwitchBatteryState
;; This function uses a non-reentrant model
;;
psect	text828
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\LM016.c"
	line	109
	global	__size_of_lcd_move_char
	__size_of_lcd_move_char	equ	__end_of_lcd_move_char-_lcd_move_char
	
_lcd_move_char:	
	opt	stack 9
; Regs used in _lcd_move_char: [wreg+status,2+status,0+pclath+cstack]
;lcd_move_char@postion stored from wreg
	movwf	(lcd_move_char@postion)
	line	110
	
l12054:	
;LM016.c: 110: API_LCD_MOVE_POINT(postion);
	movf	(lcd_move_char@postion),w
	fcall	_API_LCD_MOVE_POINT
	line	111
	
l4307:	
	return
	opt stack 0
GLOBAL	__end_of_lcd_move_char
	__end_of_lcd_move_char:
;; =============== function _lcd_move_char ends ============

	signat	_lcd_move_char,4216
	global	_load_system_state
psect	text829,local,class=CODE,delta=2
global __ptext829
__ptext829:

;; *************** function _load_system_state *****************
;; Defined at:
;;		line 125 in file "C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.c"
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
;; Hardware stack levels required when called:    5
;; This function calls:
;;		_readFromEEPROM
;; This function is called by:
;;		_main
;; This function uses a non-reentrant model
;;
psect	text829
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.c"
	line	125
	global	__size_of_load_system_state
	__size_of_load_system_state	equ	__end_of_load_system_state-_load_system_state
	
_load_system_state:	
	opt	stack 10
; Regs used in _load_system_state: [wreg-fsr0h+status,2+status,0+pclath+cstack]
	line	139
	
l12052:	
;mypic.c: 139: en_charge = readFromEEPROM(1);
	movlw	(01h)
	fcall	_readFromEEPROM
	line	140
;mypic.c: 140: eq_charge = readFromEEPROM(2);
	movlw	(02h)
	fcall	_readFromEEPROM
	line	141
;mypic.c: 141: flo_charge = readFromEEPROM(3);
	movlw	(03h)
	fcall	_readFromEEPROM
	line	142
;mypic.c: 142: time_charge = readFromEEPROM(4);
	movlw	(04h)
	fcall	_readFromEEPROM
	line	143
;mypic.c: 143: under_vol = readFromEEPROM(5);
	movlw	(05h)
	fcall	_readFromEEPROM
	line	144
;mypic.c: 144: re_under = readFromEEPROM(6);
	movlw	(06h)
	fcall	_readFromEEPROM
	line	145
;mypic.c: 145: over_vol = readFromEEPROM(7);
	movlw	(07h)
	fcall	_readFromEEPROM
	line	146
;mypic.c: 146: re_over = readFromEEPROM(8);
	movlw	(08h)
	fcall	_readFromEEPROM
	line	147
;mypic.c: 147: day_vol = readFromEEPROM(9);
	movlw	(09h)
	fcall	_readFromEEPROM
	line	148
;mypic.c: 148: night_vol = readFromEEPROM(10);
	movlw	(0Ah)
	fcall	_readFromEEPROM
	line	150
	
l8671:	
	return
	opt stack 0
GLOBAL	__end_of_load_system_state
	__end_of_load_system_state:
;; =============== function _load_system_state ends ============

	signat	_load_system_state,88
	global	_getADValue
psect	text830,local,class=CODE,delta=2
global __ptext830
__ptext830:

;; *************** function _getADValue *****************
;; Defined at:
;;		line 181 in file "C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.c"
;; Parameters:    Size  Location     Type
;;  channel         1    wreg     unsigned char 
;; Auto vars:     Size  Location     Type
;;  channel         1   15[BANK0 ] unsigned char 
;;  AD_OneResult    2   24[BANK0 ] unsigned int 
;;  i               2   22[BANK0 ] unsigned int 
;;  min             2   20[BANK0 ] unsigned int 
;;  max             2   18[BANK0 ] unsigned int 
;;  AD_Result       2   16[BANK0 ] unsigned int 
;; Return value:  Size  Location     Type
;;                  2   11[BANK0 ] unsigned int 
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
;; Hardware stack levels required when called:    4
;; This function calls:
;;		_getADValueOneTime
;; This function is called by:
;;		_GetBatteryVoltage
;;		_GetSolarPanelVoltage
;;		_GetLoadCurrentVoltage
;; This function uses a non-reentrant model
;;
psect	text830
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.c"
	line	181
	global	__size_of_getADValue
	__size_of_getADValue	equ	__end_of_getADValue-_getADValue
	
_getADValue:	
	opt	stack 9
; Regs used in _getADValue: [wreg+status,2+status,0+pclath+cstack]
;getADValue@channel stored from wreg
	movlb 0	; select bank0
	movwf	(getADValue@channel)
	line	182
	
l12024:	
;mypic.c: 182: unsigned int AD_Result = 0;
	clrf	(getADValue@AD_Result)
	clrf	(getADValue@AD_Result+1)
	line	186
;mypic.c: 184: unsigned int max;
;mypic.c: 185: unsigned int min;
;mypic.c: 186: unsigned int i = 0 ;
	clrf	(getADValue@i)
	clrf	(getADValue@i+1)
	line	187
;mypic.c: 187: for (i = 0 ; i < 10; i++){
	clrf	(getADValue@i)
	clrf	(getADValue@i+1)
	line	188
	
l12030:	
;mypic.c: 188: AD_OneResult = getADValueOneTime(channel);
	movf	(getADValue@channel),w
	fcall	_getADValueOneTime
	movf	(1+(?_getADValueOneTime)),w
	movwf	(getADValue@AD_OneResult+1)
	movf	(0+(?_getADValueOneTime)),w
	movwf	(getADValue@AD_OneResult)
	line	189
	
l12032:	
;mypic.c: 189: AD_Result += AD_OneResult;
	movf	(getADValue@AD_OneResult),w
	addwf	(getADValue@AD_Result),f
	movf	(getADValue@AD_OneResult+1),w
	addwfc	(getADValue@AD_Result+1),f
	line	190
	
l12034:	
;mypic.c: 190: max = (max > AD_OneResult ? max : AD_OneResult);
	movf	(getADValue@max+1),w
	subwf	(getADValue@AD_OneResult+1),w
	skipz
	goto	u3205
	movf	(getADValue@max),w
	subwf	(getADValue@AD_OneResult),w
u3205:
	skipc
	goto	u3201
	goto	u3200
u3201:
	goto	l12038
u3200:
	
l12036:	
	movf	(getADValue@AD_OneResult+1),w
	movwf	(getADValue@max+1)
	movf	(getADValue@AD_OneResult),w
	movwf	(getADValue@max)
	line	191
	
l12038:	
;mypic.c: 191: min = (min < AD_OneResult ? min : AD_OneResult);
	movf	(getADValue@AD_OneResult+1),w
	subwf	(getADValue@min+1),w
	skipz
	goto	u3215
	movf	(getADValue@AD_OneResult),w
	subwf	(getADValue@min),w
u3215:
	skipc
	goto	u3211
	goto	u3210
u3211:
	goto	l12042
u3210:
	
l12040:	
	movf	(getADValue@AD_OneResult+1),w
	movwf	(getADValue@min+1)
	movf	(getADValue@AD_OneResult),w
	movwf	(getADValue@min)
	line	187
	
l12042:	
	incf	(getADValue@i),f
	skipnz
	incf	(getADValue@i+1),f
	
l12044:	
	movlw	high(0Ah)
	subwf	(getADValue@i+1),w
	movlw	low(0Ah)
	skipnz
	subwf	(getADValue@i),w
	skipc
	goto	u3221
	goto	u3220
u3221:
	goto	l12030
u3220:
	line	193
	
l12046:	
;mypic.c: 192: }
;mypic.c: 193: return ((AD_Result - max - min) >> 3);
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
	
l12048:	
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
	line	194
	
l8693:	
	return
	opt stack 0
GLOBAL	__end_of_getADValue
	__end_of_getADValue:
;; =============== function _getADValue ends ============

	signat	_getADValue,4218
	global	_readFromEEPROM
psect	text831,local,class=CODE,delta=2
global __ptext831
__ptext831:

;; *************** function _readFromEEPROM *****************
;; Defined at:
;;		line 1043 in file "C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.c"
;; Parameters:    Size  Location     Type
;;  type            1    wreg     unsigned char 
;; Auto vars:     Size  Location     Type
;;  type            1    4[BANK1 ] unsigned char 
;;  read_data       2    2[BANK1 ] unsigned int 
;;  h_byte          1    1[BANK1 ] unsigned char 
;;  l_byte          1    0[BANK1 ] unsigned char 
;; Return value:  Size  Location     Type
;;                  2   39[BANK0 ] unsigned int 
;; Registers used:
;;		wreg, fsr0l, fsr0h, status,2, status,0, pclath, cstack
;; Tracked objects:
;;		On entry : 17F/0
;;		On exit  : 17F/0
;;		Unchanged: FFE80/0
;; Data sizes:     COMMON   BANK0   BANK1   BANK2
;;      Params:         0       2       0       0
;;      Locals:         0       0       5       0
;;      Temps:          0       0       0       0
;;      Totals:         0       2       5       0
;;Total ram usage:        7 bytes
;; Hardware stack levels used:    1
;; Hardware stack levels required when called:    4
;; This function calls:
;;		_ReadEE
;;		___lwtoft
;;		___ftdiv
;;		___fttol
;; This function is called by:
;;		_load_system_state
;; This function uses a non-reentrant model
;;
psect	text831
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.c"
	line	1043
	global	__size_of_readFromEEPROM
	__size_of_readFromEEPROM	equ	__end_of_readFromEEPROM-_readFromEEPROM
	
_readFromEEPROM:	
	opt	stack 10
; Regs used in _readFromEEPROM: [wreg-fsr0h+status,2+status,0+pclath+cstack]
;readFromEEPROM@type stored from wreg
	line	1045
	movlb 1	; select bank1
	movwf	(readFromEEPROM@type)^080h
	line	1044
	
l12000:	
	line	1045
;mypic.c: 1045: unsigned char h_byte = 0;
	clrf	(readFromEEPROM@h_byte)^080h
	line	1046
;mypic.c: 1046: unsigned int read_data = 0;
	clrf	(readFromEEPROM@read_data)^080h
	clrf	(readFromEEPROM@read_data+1)^080h
	line	1047
;mypic.c: 1047: switch(type) {
	goto	l12018
	line	1051
	
l8906:	
	line	1055
	
l12002:	
;mypic.c: 1049: case 2:
;mypic.c: 1050: case 3:
;mypic.c: 1051: case 5:
;mypic.c: 1052: case 6:
;mypic.c: 1053: case 7:
;mypic.c: 1054: case 8:
;mypic.c: 1055: l_byte = ReadEE(type * 2);
	lslf	(readFromEEPROM@type)^080h,w
	fcall	_ReadEE
	movlb 1	; select bank1
	movwf	(readFromEEPROM@l_byte)^080h
	line	1056
	
l12004:	
;mypic.c: 1056: h_byte = ReadEE(type * 2 + 1);
	setc
	rlf	(readFromEEPROM@type)^080h,w
	fcall	_ReadEE
	movlb 1	; select bank1
	movwf	(readFromEEPROM@h_byte)^080h
	line	1057
	
l12006:	
;mypic.c: 1057: read_data = l_byte + ( ((unsigned int)h_byte) << 8);
	movf	(readFromEEPROM@h_byte)^080h,w
	movwf	(readFromEEPROM@read_data)^080h
	clrf	(readFromEEPROM@read_data+1)^080h
	
l12008:	
	movf	(readFromEEPROM@read_data)^080h,w
	movwf	(readFromEEPROM@read_data+1)^080h
	clrf	(readFromEEPROM@read_data)^080h
	
l12010:	
	movf	(readFromEEPROM@l_byte)^080h,w
	addwf	(readFromEEPROM@read_data)^080h,f
	skipnc
	incf	(readFromEEPROM@read_data+1)^080h,f
	line	1058
	
l12012:	
;mypic.c: 1058: read_data = (unsigned int) (read_data / 2.3684f);
	movf	(readFromEEPROM@read_data+1)^080h,w
	movlb 0	; select bank0
	movwf	(?___lwtoft+1)
	movlb 1	; select bank1
	movf	(readFromEEPROM@read_data)^080h,w
	movlb 0	; select bank0
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
	movlb 1	; select bank1
	movwf	(readFromEEPROM@read_data+1)^080h
	movlb 0	; select bank0
	movf	0+(((0+(?___fttol)))),w
	movlb 1	; select bank1
	movwf	(readFromEEPROM@read_data)^080h
	line	1059
;mypic.c: 1059: break;
	goto	l12020
	line	1063
	
l12014:	
;mypic.c: 1061: case 10:
;mypic.c: 1062: case 4:
;mypic.c: 1063: read_data = ReadEE(type * 2);
	lslf	(readFromEEPROM@type)^080h,w
	fcall	_ReadEE
	movlb 1	; select bank1
	movwf	(readFromEEPROM@read_data)^080h
	clrf	(readFromEEPROM@read_data+1)^080h
	goto	l12020
	line	1047
	
l12018:	
	movf	(readFromEEPROM@type)^080h,w
	; Switch size 1, requested type "space"
; Number of cases is 10, Range of values is 1 to 10
; switch strategies available:
; Name         Bytes Cycles
; simple_byte    31    16 (average)
; direct_byte    39    19 (fixed)
;	Chosen strategy is simple_byte

	xorlw	1^0	; case 1
	skipnz
	goto	l8906
	xorlw	2^1	; case 2
	skipnz
	goto	l12002
	xorlw	3^2	; case 3
	skipnz
	goto	l12002
	xorlw	4^3	; case 4
	skipnz
	goto	l12014
	xorlw	5^4	; case 5
	skipnz
	goto	l12002
	xorlw	6^5	; case 6
	skipnz
	goto	l12002
	xorlw	7^6	; case 7
	skipnz
	goto	l12002
	xorlw	8^7	; case 8
	skipnz
	goto	l12002
	xorlw	9^8	; case 9
	skipnz
	goto	l12014
	xorlw	10^9	; case 10
	skipnz
	goto	l12014
	goto	l12020

	line	1067
	
l12020:	
;mypic.c: 1067: return read_data;
	movf	(readFromEEPROM@read_data+1)^080h,w
	movlb 0	; select bank0
	movwf	(?_readFromEEPROM+1)
	movlb 1	; select bank1
	movf	(readFromEEPROM@read_data)^080h,w
	movlb 0	; select bank0
	movwf	(?_readFromEEPROM)
	line	1068
	
l8915:	
	return
	opt stack 0
GLOBAL	__end_of_readFromEEPROM
	__end_of_readFromEEPROM:
;; =============== function _readFromEEPROM ends ============

	signat	_readFromEEPROM,4218
	global	_API_LCD_MOVE_POINT
psect	text832,local,class=CODE,delta=2
global __ptext832
__ptext832:

;; *************** function _API_LCD_MOVE_POINT *****************
;; Defined at:
;;		line 90 in file "C:\CTK\CTK80A 150928\CTK80A_Jas\LM016.c"
;; Parameters:    Size  Location     Type
;;  position        1    wreg     unsigned char 
;; Auto vars:     Size  Location     Type
;;  position        1    6[BANK0 ] unsigned char 
;;  i               1    7[BANK0 ] unsigned char 
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
;;      Temps:          0       1       0       0
;;      Totals:         0       3       0       0
;;Total ram usage:        3 bytes
;; Hardware stack levels used:    1
;; Hardware stack levels required when called:    4
;; This function calls:
;;		_lcd_write_command
;; This function is called by:
;;		_lcd_move_char
;; This function uses a non-reentrant model
;;
psect	text832
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\LM016.c"
	line	90
	global	__size_of_API_LCD_MOVE_POINT
	__size_of_API_LCD_MOVE_POINT	equ	__end_of_API_LCD_MOVE_POINT-_API_LCD_MOVE_POINT
	
_API_LCD_MOVE_POINT:	
	opt	stack 9
; Regs used in _API_LCD_MOVE_POINT: [wreg+status,2+status,0+pclath+cstack]
;API_LCD_MOVE_POINT@position stored from wreg
	movwf	(API_LCD_MOVE_POINT@position)
	line	91
	
l11990:	
;LM016.c: 91: unsigned char i = 0;
	clrf	(API_LCD_MOVE_POINT@i)
	line	92
	
l11992:	
;LM016.c: 92: lcd_write_command(0x0f);
	movlw	(0Fh)
	fcall	_lcd_write_command
	line	93
	
l11994:	
;LM016.c: 93: lcd_write_command(0x02);
	movlw	(02h)
	fcall	_lcd_write_command
	line	94
;LM016.c: 94: while(i++ < position) {
	goto	l11998
	line	95
	
l11996:	
;LM016.c: 95: lcd_write_command(0x14);
	movlw	(014h)
	fcall	_lcd_write_command
	line	94
	
l11998:	
	movf	(API_LCD_MOVE_POINT@i),w
	incf	(API_LCD_MOVE_POINT@i),f
	movwf	(??_API_LCD_MOVE_POINT+0)+0
	movf	(API_LCD_MOVE_POINT@position),w
	subwf	(??_API_LCD_MOVE_POINT+0)+0,w
	skipc
	goto	u3191
	goto	u3190
u3191:
	goto	l11996
u3190:
	line	97
	
l4297:	
	return
	opt stack 0
GLOBAL	__end_of_API_LCD_MOVE_POINT
	__end_of_API_LCD_MOVE_POINT:
;; =============== function _API_LCD_MOVE_POINT ends ============

	signat	_API_LCD_MOVE_POINT,4216
	global	_lcd_init
psect	text833,local,class=CODE,delta=2
global __ptext833
__ptext833:

;; *************** function _lcd_init *****************
;; Defined at:
;;		line 53 in file "C:\CTK\CTK80A 150928\CTK80A_Jas\LM016.c"
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
;; Hardware stack levels required when called:    4
;; This function calls:
;;		_lcd_write_command
;;		_CTKSoftDelay
;; This function is called by:
;;		_main
;; This function uses a non-reentrant model
;;
psect	text833
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\LM016.c"
	line	53
	global	__size_of_lcd_init
	__size_of_lcd_init	equ	__end_of_lcd_init-_lcd_init
	
_lcd_init:	
	opt	stack 11
; Regs used in _lcd_init: [wreg+status,2+status,0+pclath+cstack]
	line	54
	
l11988:	
;LM016.c: 54: lcd_write_command(0x38);
	movlw	(038h)
	fcall	_lcd_write_command
	line	55
;LM016.c: 55: lcd_write_command(0x0c);
	movlw	(0Ch)
	fcall	_lcd_write_command
	line	56
;LM016.c: 56: lcd_write_command(0x06);
	movlw	(06h)
	fcall	_lcd_write_command
	line	57
;LM016.c: 57: lcd_write_command(0x01);
	movlw	(01h)
	fcall	_lcd_write_command
	line	58
;LM016.c: 58: CTKSoftDelay(1000);
	movlw	low(03E8h)
	movwf	(?_CTKSoftDelay)
	movlw	high(03E8h)
	movwf	((?_CTKSoftDelay))+1
	fcall	_CTKSoftDelay
	line	59
	
l4273:	
	return
	opt stack 0
GLOBAL	__end_of_lcd_init
	__end_of_lcd_init:
;; =============== function _lcd_init ends ============

	signat	_lcd_init,88
	global	___lwtoft
psect	text834,local,class=CODE,delta=2
global __ptext834
__ptext834:

;; *************** function ___lwtoft *****************
;; Defined at:
;;		line 29 in file "C:\Program Files\HI-TECH Software\PICC\9.80\sources\lwtoft.c"
;; Parameters:    Size  Location     Type
;;  c               2   21[BANK0 ] unsigned int 
;; Auto vars:     Size  Location     Type
;;		None
;; Return value:  Size  Location     Type
;;                  3   21[BANK0 ] float 
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
;; Hardware stack levels required when called:    3
;; This function calls:
;;		___ftpack
;; This function is called by:
;;		_readFromEEPROM
;; This function uses a non-reentrant model
;;
psect	text834
	file	"C:\Program Files\HI-TECH Software\PICC\9.80\sources\lwtoft.c"
	line	29
	global	__size_of___lwtoft
	__size_of___lwtoft	equ	__end_of___lwtoft-___lwtoft
	
___lwtoft:	
	opt	stack 10
; Regs used in ___lwtoft: [wreg+status,2+status,0+pclath+cstack]
	line	30
	
l11984:	
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
	
l9147:	
	return
	opt stack 0
GLOBAL	__end_of___lwtoft
	__end_of___lwtoft:
;; =============== function ___lwtoft ends ============

	signat	___lwtoft,4219
	global	___ftdiv
psect	text835,local,class=CODE,delta=2
global __ptext835
__ptext835:

;; *************** function ___ftdiv *****************
;; Defined at:
;;		line 50 in file "C:\Program Files\HI-TECH Software\PICC\9.80\sources\ftdiv.c"
;; Parameters:    Size  Location     Type
;;  f2              3   24[BANK0 ] float 
;;  f1              3   27[BANK0 ] float 
;; Auto vars:     Size  Location     Type
;;  f3              3   34[BANK0 ] float 
;;  sign            1   38[BANK0 ] unsigned char 
;;  exp             1   37[BANK0 ] unsigned char 
;;  cntr            1   33[BANK0 ] unsigned char 
;; Return value:  Size  Location     Type
;;                  3   24[BANK0 ] float 
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
;; Hardware stack levels required when called:    3
;; This function calls:
;;		___ftpack
;; This function is called by:
;;		_readFromEEPROM
;; This function uses a non-reentrant model
;;
psect	text835
	file	"C:\Program Files\HI-TECH Software\PICC\9.80\sources\ftdiv.c"
	line	50
	global	__size_of___ftdiv
	__size_of___ftdiv	equ	__end_of___ftdiv-___ftdiv
	
___ftdiv:	
	opt	stack 10
; Regs used in ___ftdiv: [wreg+status,2+status,0+pclath+cstack]
	line	55
	
l11940:	
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
	goto	u3151
	goto	u3150
u3151:
	goto	l11946
u3150:
	line	56
	
l11942:	
	clrf	(?___ftdiv)
	clrf	(?___ftdiv+1)
	clrf	(?___ftdiv+2)
	goto	l9013
	line	57
	
l11946:	
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
	goto	u3161
	goto	u3160
u3161:
	goto	l9014
u3160:
	line	58
	
l11948:	
	clrf	(?___ftdiv)
	clrf	(?___ftdiv+1)
	clrf	(?___ftdiv+2)
	goto	l9013
	
l9014:	
	line	59
	clrf	(___ftdiv@f3)
	clrf	(___ftdiv@f3+1)
	clrf	(___ftdiv@f3+2)
	line	60
	
l11952:	
	movlw	(089h)
	addwf	(___ftdiv@sign),w
	movwf	(??___ftdiv+0)+0
	movf	0+(??___ftdiv+0)+0,w
	subwf	(___ftdiv@exp),f
	line	61
	
l11954:	
	movf	0+(((___ftdiv@f1))+2),w
	movwf	(___ftdiv@sign)
	line	62
	
l11956:	
	movf	0+(((___ftdiv@f2))+2),w
	xorwf	(___ftdiv@sign),f
	line	63
	
l11958:	
	movlw	(080h)
	andwf	(___ftdiv@sign),f
	line	64
	
l11960:	
	bsf	(___ftdiv@f1)+(15/8),(15)&7
	line	65
	
l11962:	
	movlw	0FFh
	andwf	(___ftdiv@f1),f
	movlw	0FFh
	andwf	(___ftdiv@f1+1),f
	movlw	0
	andwf	(___ftdiv@f1+2),f
	line	66
	
l11964:	
	bsf	(___ftdiv@f2)+(15/8),(15)&7
	line	67
	
l11966:	
	movlw	0FFh
	andwf	(___ftdiv@f2),f
	movlw	0FFh
	andwf	(___ftdiv@f2+1),f
	movlw	0
	andwf	(___ftdiv@f2+2),f
	line	68
	
l11968:	
	movlw	(018h)
	movwf	(___ftdiv@cntr)
	line	70
	
l11970:	
	lslf	(___ftdiv@f3),f
	rlf	(___ftdiv@f3+1),f
	rlf	(___ftdiv@f3+2),f
	line	71
	movf	(___ftdiv@f2+2),w
	subwf	(___ftdiv@f1+2),w
	skipz
	goto	u3175
	movf	(___ftdiv@f2+1),w
	subwf	(___ftdiv@f1+1),w
	skipz
	goto	u3175
	movf	(___ftdiv@f2),w
	subwf	(___ftdiv@f1),w
u3175:
	skipc
	goto	u3171
	goto	u3170
u3171:
	goto	l11976
u3170:
	line	72
	
l11972:	
	movf	(___ftdiv@f2),w
	subwf	(___ftdiv@f1),f
	movf	(___ftdiv@f2+1),w
	subwfb	(___ftdiv@f1+1),f
	movf	(___ftdiv@f2+2),w
	subwfb	(___ftdiv@f1+2),f
	line	73
	
l11974:	
	bsf	(___ftdiv@f3)+(0/8),(0)&7
	line	75
	
l11976:	
	lslf	(___ftdiv@f1),f
	rlf	(___ftdiv@f1+1),f
	rlf	(___ftdiv@f1+2),f
	line	76
	
l11978:	
	decfsz	(___ftdiv@cntr),f
	goto	u3181
	goto	u3180
u3181:
	goto	l11970
u3180:
	line	77
	
l11980:	
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
	
l9013:	
	return
	opt stack 0
GLOBAL	__end_of___ftdiv
	__end_of___ftdiv:
;; =============== function ___ftdiv ends ============

	signat	___ftdiv,8315
	global	_getADValueOneTime
psect	text836,local,class=CODE,delta=2
global __ptext836
__ptext836:

;; *************** function _getADValueOneTime *****************
;; Defined at:
;;		line 167 in file "C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.c"
;; Parameters:    Size  Location     Type
;;  channel         1    wreg     unsigned char 
;; Auto vars:     Size  Location     Type
;;  channel         1    8[BANK0 ] unsigned char 
;;  AD_Result       2    9[BANK0 ] unsigned int 
;; Return value:  Size  Location     Type
;;                  2    4[BANK0 ] unsigned int 
;; Registers used:
;;		wreg, status,2, status,0, pclath, cstack
;; Tracked objects:
;;		On entry : 1F/0
;;		On exit  : 1F/0
;;		Unchanged: FFFE0/0
;; Data sizes:     COMMON   BANK0   BANK1   BANK2
;;      Params:         0       2       0       0
;;      Locals:         0       3       0       0
;;      Temps:          0       2       0       0
;;      Totals:         0       7       0       0
;;Total ram usage:        7 bytes
;; Hardware stack levels used:    1
;; Hardware stack levels required when called:    3
;; This function calls:
;;		_CTKSoftDelay
;; This function is called by:
;;		_getADValue
;; This function uses a non-reentrant model
;;
psect	text836
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.c"
	line	167
	global	__size_of_getADValueOneTime
	__size_of_getADValueOneTime	equ	__end_of_getADValueOneTime-_getADValueOneTime
	
_getADValueOneTime:	
	opt	stack 9
; Regs used in _getADValueOneTime: [wreg+status,2+status,0+pclath+cstack]
;getADValueOneTime@channel stored from wreg
	line	169
	movwf	(getADValueOneTime@channel)
	line	168
	
l11922:	
	line	169
;mypic.c: 169: FVRCON = 0b00000000;
	movlb 2	; select bank2
	clrf	(279)^0100h	;volatile
	line	170
	
l11924:	
;mypic.c: 170: ADCON0 = (channel & 0xff);
	movlb 0	; select bank0
	movf	(getADValueOneTime@channel),w
	movlb 1	; select bank1
	movwf	(157)^080h	;volatile
	line	171
	
l11926:	
;mypic.c: 171: ADCON1 = 0b10010000;;
	movlw	(090h)
	movwf	(158)^080h	;volatile
	line	172
	
l11928:	
;mypic.c: 172: CTKSoftDelay(20);
	movlw	014h
	movwf	(?_CTKSoftDelay)
	clrf	(?_CTKSoftDelay+1)
	fcall	_CTKSoftDelay
	line	173
	
l11930:	
;mypic.c: 173: ADGO = 1;
	movlb 1	; select bank1
	bsf	(1257/8)^080h,(1257)&7
	line	174
;mypic.c: 174: while(ADGO);
	
l8677:	
	btfsc	(1257/8)^080h,(1257)&7
	goto	u3131
	goto	u3130
u3131:
	goto	l8677
u3130:
	line	175
	
l11932:	
;mypic.c: 175: AD_Result = ADRESL & 0x00FF;
	movf	(155)^080h,w	;volatile
	movlb 0	; select bank0
	movwf	(getADValueOneTime@AD_Result)
	clrf	(getADValueOneTime@AD_Result+1)
	line	176
;mypic.c: 176: AD_Result |= ADRESH <<8 ;
	movlb 1	; select bank1
	movf	(156)^080h,w	;volatile
	movlb 0	; select bank0
	movwf	(??_getADValueOneTime+0)+0
	clrf	(??_getADValueOneTime+0)+0+1
	movlw	08h
u3145:
	lslf	(??_getADValueOneTime+0)+0,f
	rlf	(??_getADValueOneTime+0)+1,f
	decfsz	wreg,f
	goto	u3145
	movf	0+(??_getADValueOneTime+0)+0,w
	iorwf	(getADValueOneTime@AD_Result),f
	movf	1+(??_getADValueOneTime+0)+0,w
	iorwf	(getADValueOneTime@AD_Result+1),f
	line	177
;mypic.c: 177: ADCON0 = 0b00101000;
	movlw	(028h)
	movlb 1	; select bank1
	movwf	(157)^080h	;volatile
	line	178
	
l11934:	
;mypic.c: 178: CTKSoftDelay(20);
	movlw	014h
	movwf	(?_CTKSoftDelay)
	clrf	(?_CTKSoftDelay+1)
	fcall	_CTKSoftDelay
	line	179
	
l11936:	
;mypic.c: 179: return AD_Result;
	movf	(getADValueOneTime@AD_Result+1),w
	movwf	(?_getADValueOneTime+1)
	movf	(getADValueOneTime@AD_Result),w
	movwf	(?_getADValueOneTime)
	line	180
	
l8680:	
	return
	opt stack 0
GLOBAL	__end_of_getADValueOneTime
	__end_of_getADValueOneTime:
;; =============== function _getADValueOneTime ends ============

	signat	_getADValueOneTime,4218
	global	_lcd_write_data
psect	text837,local,class=CODE,delta=2
global __ptext837
__ptext837:

;; *************** function _lcd_write_data *****************
;; Defined at:
;;		line 27 in file "C:\CTK\CTK80A 150928\CTK80A_Jas\LM016.c"
;; Parameters:    Size  Location     Type
;;  data            1    wreg     unsigned char 
;; Auto vars:     Size  Location     Type
;;  data            1    4[BANK0 ] unsigned char 
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
;;      Locals:         0       1       0       0
;;      Temps:          0       0       0       0
;;      Totals:         0       1       0       0
;;Total ram usage:        1 bytes
;; Hardware stack levels used:    1
;; Hardware stack levels required when called:    3
;; This function calls:
;;		_CTKSoftDelay
;; This function is called by:
;;		_SwitchBatteryState
;; This function uses a non-reentrant model
;;
psect	text837
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\LM016.c"
	line	27
	global	__size_of_lcd_write_data
	__size_of_lcd_write_data	equ	__end_of_lcd_write_data-_lcd_write_data
	
_lcd_write_data:	
	opt	stack 11
; Regs used in _lcd_write_data: [wreg+status,2+status,0+pclath+cstack]
;lcd_write_data@data stored from wreg
	movwf	(lcd_write_data@data)
	line	28
	
l11912:	
;LM016.c: 28: RC1 = 1;
	bsf	(113/8),(113)&7
	line	29
;LM016.c: 29: RC2 = 0;
	bcf	(114/8),(114)&7
	line	30
	
l11914:	
;LM016.c: 30: PORTD = data;
	movf	(lcd_write_data@data),w
	movwf	(15)	;volatile
	line	34
	
l11916:	
;LM016.c: 34: CTKSoftDelay(30);
	movlw	01Eh
	movwf	(?_CTKSoftDelay)
	clrf	(?_CTKSoftDelay+1)
	fcall	_CTKSoftDelay
	line	35
	
l11918:	
;LM016.c: 35: RC3 = 1;
	bsf	(115/8),(115)&7
	line	36
	
l11920:	
;LM016.c: 36: RC3 = 0;
	bcf	(115/8),(115)&7
	line	37
	
l4265:	
	return
	opt stack 0
GLOBAL	__end_of_lcd_write_data
	__end_of_lcd_write_data:
;; =============== function _lcd_write_data ends ============

	signat	_lcd_write_data,4216
	global	_lcd_write_command
psect	text838,local,class=CODE,delta=2
global __ptext838
__ptext838:

;; *************** function _lcd_write_command *****************
;; Defined at:
;;		line 14 in file "C:\CTK\CTK80A 150928\CTK80A_Jas\LM016.c"
;; Parameters:    Size  Location     Type
;;  command         1    wreg     unsigned char 
;; Auto vars:     Size  Location     Type
;;  command         1    4[BANK0 ] unsigned char 
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
;;      Locals:         0       1       0       0
;;      Temps:          0       0       0       0
;;      Totals:         0       1       0       0
;;Total ram usage:        1 bytes
;; Hardware stack levels used:    1
;; Hardware stack levels required when called:    3
;; This function calls:
;;		_CTKSoftDelay
;; This function is called by:
;;		_main
;;		_lcd_init
;;		_API_LCD_MOVE_POINT
;; This function uses a non-reentrant model
;;
psect	text838
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\LM016.c"
	line	14
	global	__size_of_lcd_write_command
	__size_of_lcd_write_command	equ	__end_of_lcd_write_command-_lcd_write_command
	
_lcd_write_command:	
	opt	stack 9
; Regs used in _lcd_write_command: [wreg+status,2+status,0+pclath+cstack]
;lcd_write_command@command stored from wreg
	movwf	(lcd_write_command@command)
	line	15
	
l11902:	
;LM016.c: 15: RC1 = 0;
	bcf	(113/8),(113)&7
	line	16
;LM016.c: 16: RC2 = 0;
	bcf	(114/8),(114)&7
	line	17
	
l11904:	
;LM016.c: 17: PORTD = command;
	movf	(lcd_write_command@command),w
	movwf	(15)	;volatile
	line	21
	
l11906:	
;LM016.c: 21: CTKSoftDelay(50);
	movlw	032h
	movwf	(?_CTKSoftDelay)
	clrf	(?_CTKSoftDelay+1)
	fcall	_CTKSoftDelay
	line	22
	
l11908:	
;LM016.c: 22: RC3 = 1;
	bsf	(115/8),(115)&7
	line	23
	
l11910:	
;LM016.c: 23: RC3 = 0;
	bcf	(115/8),(115)&7
	line	24
	
l4262:	
	return
	opt stack 0
GLOBAL	__end_of_lcd_write_command
	__end_of_lcd_write_command:
;; =============== function _lcd_write_command ends ============

	signat	_lcd_write_command,4216
	global	_system_state_init
psect	text839,local,class=CODE,delta=2
global __ptext839
__ptext839:

;; *************** function _system_state_init *****************
;; Defined at:
;;		line 42 in file "C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.c"
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
;;		_CTKSoftDelay
;; This function is called by:
;;		_main
;; This function uses a non-reentrant model
;;
psect	text839
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.c"
	line	42
	global	__size_of_system_state_init
	__size_of_system_state_init	equ	__end_of_system_state_init-_system_state_init
	
_system_state_init:	
	opt	stack 12
; Regs used in _system_state_init: [wreg+status,2+status,0+pclath+cstack]
	line	44
	
l11858:	
;mypic.c: 44: OSCCON = 0x70;
	movlw	(070h)
	movlb 1	; select bank1
	movwf	(153)^080h	;volatile
	line	45
;mypic.c: 45: WDTCON = 0x18;
	movlw	(018h)
	movwf	(151)^080h	;volatile
	line	48
;mypic.c: 48: TRISA = 0b00101010;
	movlw	(02Ah)
	movwf	(140)^080h	;volatile
	line	49
;mypic.c: 49: TRISB = 0b00111110;
	movlw	(03Eh)
	movwf	(141)^080h	;volatile
	line	50
	
l11860:	
;mypic.c: 50: TRISC = 0x00;
	clrf	(142)^080h	;volatile
	line	51
	
l11862:	
;mypic.c: 51: TRISD = 0x00;
	clrf	(143)^080h	;volatile
	line	52
;mypic.c: 52: TRISE = 0b00000010;
	movlw	(02h)
	movwf	(144)^080h	;volatile
	line	54
;mypic.c: 54: ANSELA = 0b00101010;
	movlw	(02Ah)
	movlb 3	; select bank3
	movwf	(396)^0180h	;volatile
	line	55
;mypic.c: 55: ANSELB = 0b00000110;
	movlw	(06h)
	movwf	(397)^0180h	;volatile
	line	56
;mypic.c: 56: ANSELE = 0b00000010;
	movlw	(02h)
	movwf	(400)^0180h	;volatile
	line	66
;mypic.c: 66: TMR1H = (65536 - (100000 / 4)) >> 8;
	movlw	(09Eh)
	movlb 0	; select bank0
	movwf	(23)	;volatile
	line	67
;mypic.c: 67: TMR1L = (65536 - (100000 / 4)) & 0xFF;
	movlw	(058h)
	movwf	(22)	;volatile
	line	68
	
l11864:	
;mypic.c: 68: PEIE = 1;
	bsf	(94/8),(94)&7
	line	69
	
l11866:	
;mypic.c: 69: TMR1IF = 0;
	bcf	(136/8),(136)&7
	line	70
	
l11868:	
;mypic.c: 70: TMR1IE = 1;
	movlb 1	; select bank1
	bsf	(1160/8)^080h,(1160)&7
	line	71
;mypic.c: 71: T1CON = 0x31;
	movlw	(031h)
	movlb 0	; select bank0
	movwf	(24)	;volatile
	line	72
	
l11870:	
;mypic.c: 72: TMR0IE = 1;
	bsf	(93/8),(93)&7
	line	75
	
l11872:	
;mypic.c: 75: TMR2IF = 0;
	bcf	(137/8),(137)&7
	line	76
;mypic.c: 76: T2CON = 0x06;
	movlw	(06h)
	movwf	(28)	;volatile
	line	77
;mypic.c: 77: PR2 = 8;
	movlw	(08h)
	movwf	(27)	;volatile
	line	78
	
l11874:	
;mypic.c: 78: TMR2IE = 1;
	movlb 1	; select bank1
	bsf	(1161/8)^080h,(1161)&7
	line	82
	
l11876:	
;mypic.c: 82: TMR4IF = 0;
	movlb 0	; select bank0
	bcf	(153/8),(153)&7
	line	85
;mypic.c: 85: T4CON = 0b00000101;
	movlw	(05h)
	movlb 8	; select bank8
	movwf	(1047)^0400h	;volatile
	line	86
;mypic.c: 86: PR4 = 250;
	movlw	(0FAh)
	movwf	(1046)^0400h	;volatile
	line	87
	
l11878:	
;mypic.c: 87: TMR4IE = 1;
	movlb 1	; select bank1
	bsf	(1177/8)^080h,(1177)&7
	line	100
;mypic.c: 100: IOCBP = 0x38;
	movlw	(038h)
	movlb 7	; select bank7
	movwf	(916)^0380h	;volatile
	line	101
;mypic.c: 101: IOCBN = 0x38;
	movlw	(038h)
	movwf	(917)^0380h	;volatile
	line	105
	
l11880:	
;mypic.c: 105: PEIE = 1;
	bsf	(94/8),(94)&7
	line	106
	
l11882:	
;mypic.c: 106: GIE = 1;
	bsf	(95/8),(95)&7
	line	109
	
l11884:	
;mypic.c: 109: (PORTA |= (1 << 0));
	movlb 0	; select bank0
	bsf	(12)+(0/8),(0)&7	;volatile
	line	110
	
l11886:	
;mypic.c: 110: (PORTB |= (1 << 0));
	bsf	(13)+(0/8),(0)&7	;volatile
	line	111
	
l11888:	
;mypic.c: 111: (PORTA |= (1 << 4));
	bsf	(12)+(4/8),(4)&7	;volatile
	line	112
	
l11890:	
;mypic.c: 112: LoadFlag = 1;
	bsf	(_LoadFlag/8),(_LoadFlag)&7
	line	113
	
l11892:	
;mypic.c: 113: LoadState = 1;
	bsf	(_LoadState/8),(_LoadState)&7
	line	114
	
l11894:	
;mypic.c: 114: (PORTE |= (1 << 0));
	bsf	(16)+(0/8),(0)&7	;volatile
	line	115
	
l11896:	
;mypic.c: 115: CTKSoftDelay(20);
	movlw	014h
	movwf	(?_CTKSoftDelay)
	clrf	(?_CTKSoftDelay+1)
	fcall	_CTKSoftDelay
	line	116
	
l11898:	
;mypic.c: 116: (PORTE &= ~(1 << 0));
	bcf	(16)+(0/8),(0)&7	;volatile
	line	117
	
l11900:	
;mypic.c: 117: CTKSoftDelay(20);
	movlw	014h
	movwf	(?_CTKSoftDelay)
	clrf	(?_CTKSoftDelay+1)
	fcall	_CTKSoftDelay
	line	120
	
l8668:	
	return
	opt stack 0
GLOBAL	__end_of_system_state_init
	__end_of_system_state_init:
;; =============== function _system_state_init ends ============

	signat	_system_state_init,88
	global	___fttol
psect	text840,local,class=CODE,delta=2
global __ptext840
__ptext840:

;; *************** function ___fttol *****************
;; Defined at:
;;		line 45 in file "C:\Program Files\HI-TECH Software\PICC\9.80\sources\fttol.c"
;; Parameters:    Size  Location     Type
;;  f1              3    8[BANK0 ] float 
;; Auto vars:     Size  Location     Type
;;  lval            4   16[BANK0 ] unsigned long 
;;  exp1            1   20[BANK0 ] unsigned char 
;;  sign1           1   15[BANK0 ] unsigned char 
;; Return value:  Size  Location     Type
;;                  4    8[BANK0 ] long 
;; Registers used:
;;		wreg, status,2, status,0
;; Tracked objects:
;;		On entry : 17F/0
;;		On exit  : 17F/0
;;		Unchanged: FFE80/0
;; Data sizes:     COMMON   BANK0   BANK1   BANK2
;;      Params:         0       4       0       0
;;      Locals:         0       6       0       0
;;      Temps:          0       3       0       0
;;      Totals:         0      13       0       0
;;Total ram usage:       13 bytes
;; Hardware stack levels used:    1
;; Hardware stack levels required when called:    2
;; This function calls:
;;		Nothing
;; This function is called by:
;;		_readFromEEPROM
;; This function uses a non-reentrant model
;;
psect	text840
	file	"C:\Program Files\HI-TECH Software\PICC\9.80\sources\fttol.c"
	line	45
	global	__size_of___fttol
	__size_of___fttol	equ	__end_of___fttol-___fttol
	
___fttol:	
	opt	stack 11
; Regs used in ___fttol: [wreg+status,2+status,0]
	line	49
	
l11820:	
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
	goto	u3051
	goto	u3050
u3051:
	goto	l11824
u3050:
	line	50
	
l11822:	
	clrf	(?___fttol)
	clrf	(?___fttol+1)
	clrf	(?___fttol+2)
	clrf	(?___fttol+3)
	goto	l9034
	line	51
	
l11824:	
	movf	(___fttol@f1),w
	movwf	((??___fttol+0)+0)
	movf	(___fttol@f1+1),w
	movwf	((??___fttol+0)+0+1)
	movf	(___fttol@f1+2),w
	movwf	((??___fttol+0)+0+2)
	movlw	017h
u3065:
	lsrf	(??___fttol+0)+2,f
	rrf	(??___fttol+0)+1,f
	rrf	(??___fttol+0)+0,f
u3060:
	decfsz	wreg,f
	goto	u3065
	movf	0+(??___fttol+0)+0,w
	movwf	(___fttol@sign1)
	line	52
	
l11826:	
	bsf	(___fttol@f1)+(15/8),(15)&7
	line	53
	
l11828:	
	movlw	0FFh
	andwf	(___fttol@f1),f
	movlw	0FFh
	andwf	(___fttol@f1+1),f
	movlw	0
	andwf	(___fttol@f1+2),f
	line	54
	
l11830:	
	movf	(___fttol@f1),w
	movwf	(___fttol@lval)
	movf	(___fttol@f1+1),w
	movwf	((___fttol@lval))+1
	movf	(___fttol@f1+2),w
	movwf	((___fttol@lval))+2
	clrf	((___fttol@lval))+3
	line	55
	
l11832:	
	movlw	low(08Eh)
	subwf	(___fttol@exp1),f
	line	56
	
l11834:	
	btfss	(___fttol@exp1),7
	goto	u3071
	goto	u3070
u3071:
	goto	l11844
u3070:
	line	57
	
l11836:	
	movf	(___fttol@exp1),w
	xorlw	80h
	addlw	-((-15)^80h)
	skipnc
	goto	u3081
	goto	u3080
u3081:
	goto	l11840
u3080:
	goto	l11822
	line	60
	
l11840:	
	lsrf	(___fttol@lval+3),f
	rrf	(___fttol@lval+2),f
	rrf	(___fttol@lval+1),f
	rrf	(___fttol@lval),f
	line	61
	
l11842:	
	incfsz	(___fttol@exp1),f
	goto	u3091
	goto	u3090
u3091:
	goto	l11840
u3090:
	goto	l11850
	line	63
	
l11844:	
	movlw	(018h)
	subwf	(___fttol@exp1),w
	skipc
	goto	u3101
	goto	u3100
u3101:
	goto	l9041
u3100:
	goto	l11822
	line	66
	
l11848:	
	lslf	(___fttol@lval),f
	rlf	(___fttol@lval+1),f
	rlf	(___fttol@lval+2),f
	rlf	(___fttol@lval+3),f
	line	67
	decf	(___fttol@exp1),f
	line	68
	
l9041:	
	line	65
	movf	(___fttol@exp1),f
	skipz
	goto	u3111
	goto	u3110
u3111:
	goto	l11848
u3110:
	line	70
	
l11850:	
	movf	(___fttol@sign1),w
	skipz
	goto	u3120
	goto	l11854
u3120:
	line	71
	
l11852:	
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
	
l11854:	
	movf	(___fttol@lval+3),w
	movwf	(?___fttol+3)
	movf	(___fttol@lval+2),w
	movwf	(?___fttol+2)
	movf	(___fttol@lval+1),w
	movwf	(?___fttol+1)
	movf	(___fttol@lval),w
	movwf	(?___fttol)

	line	73
	
l9034:	
	return
	opt stack 0
GLOBAL	__end_of___fttol
	__end_of___fttol:
;; =============== function ___fttol ends ============

	signat	___fttol,4220
	global	___ftpack
psect	text841,local,class=CODE,delta=2
global __ptext841
__ptext841:

;; *************** function ___ftpack *****************
;; Defined at:
;;		line 63 in file "C:\Program Files\HI-TECH Software\PICC\9.80\sources\float.c"
;; Parameters:    Size  Location     Type
;;  arg             3    0[BANK0 ] unsigned um
;;  exp             1    3[BANK0 ] unsigned char 
;;  sign            1    4[BANK0 ] unsigned char 
;; Auto vars:     Size  Location     Type
;;		None
;; Return value:  Size  Location     Type
;;                  3    0[BANK0 ] float 
;; Registers used:
;;		wreg, status,2, status,0
;; Tracked objects:
;;		On entry : 17F/0
;;		On exit  : 17F/0
;;		Unchanged: FFE80/0
;; Data sizes:     COMMON   BANK0   BANK1   BANK2
;;      Params:         0       5       0       0
;;      Locals:         0       0       0       0
;;      Temps:          0       3       0       0
;;      Totals:         0       8       0       0
;;Total ram usage:        8 bytes
;; Hardware stack levels used:    1
;; Hardware stack levels required when called:    2
;; This function calls:
;;		Nothing
;; This function is called by:
;;		___ftdiv
;;		___lwtoft
;; This function uses a non-reentrant model
;;
psect	text841
	file	"C:\Program Files\HI-TECH Software\PICC\9.80\sources\float.c"
	line	63
	global	__size_of___ftpack
	__size_of___ftpack	equ	__end_of___ftpack-___ftpack
	
___ftpack:	
	opt	stack 10
; Regs used in ___ftpack: [wreg+status,2+status,0]
	line	64
	
l11792:	
	movf	(___ftpack@exp),w
	skipz
	goto	u2980
	goto	l11796
u2980:
	
l11794:	
	movf	(___ftpack@arg+2),w
	iorwf	(___ftpack@arg+1),w
	iorwf	(___ftpack@arg),w
	skipz
	goto	u2991
	goto	u2990
u2991:
	goto	l11802
u2990:
	line	65
	
l11796:	
	clrf	(?___ftpack)
	clrf	(?___ftpack+1)
	clrf	(?___ftpack+2)
	goto	l9259
	line	67
	
l11800:	
	incf	(___ftpack@exp),f
	line	68
	lsrf	(___ftpack@arg+2),f
	rrf	(___ftpack@arg+1),f
	rrf	(___ftpack@arg),f
	line	66
	
l11802:	
	movlw	low highword(0FE0000h)
	andwf	(___ftpack@arg+2),w
	btfss	status,2
	goto	u3001
	goto	u3000
u3001:
	goto	l11800
u3000:
	goto	l11806
	line	71
	
l11804:	
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
	
l11806:	
	movlw	low highword(0FF0000h)
	andwf	(___ftpack@arg+2),w
	btfss	status,2
	goto	u3011
	goto	u3010
u3011:
	goto	l11804
u3010:
	goto	l11810
	line	76
	
l11808:	
	decf	(___ftpack@exp),f
	line	77
	lslf	(___ftpack@arg),f
	rlf	(___ftpack@arg+1),f
	rlf	(___ftpack@arg+2),f
	line	75
	
l11810:	
	btfss	(___ftpack@arg+1),(15)&7
	goto	u3021
	goto	u3020
u3021:
	goto	l11808
u3020:
	
l9268:	
	line	79
	btfsc	(___ftpack@exp),(0)&7
	goto	u3031
	goto	u3030
u3031:
	goto	l9269
u3030:
	line	80
	
l11812:	
	bcf	(___ftpack@arg)+(15/8),(15)&7
	
l9269:	
	line	81
	lsrf	(___ftpack@exp),f
	line	82
	
l11814:	
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
	
l11816:	
	movf	(___ftpack@sign),w
	skipz
	goto	u3040
	goto	l9270
u3040:
	line	84
	
l11818:	
	bsf	(___ftpack@arg)+(23/8),(23)&7
	
l9270:	
	line	85
	line	86
	
l9259:	
	return
	opt stack 0
GLOBAL	__end_of___ftpack
	__end_of___ftpack:
;; =============== function ___ftpack ends ============

	signat	___ftpack,12411
	global	___wmul
psect	text842,local,class=CODE,delta=2
global __ptext842
__ptext842:

;; *************** function ___wmul *****************
;; Defined at:
;;		line 3 in file "C:\Program Files\HI-TECH Software\PICC\9.80\sources\wmul.c"
;; Parameters:    Size  Location     Type
;;  multiplier      2    0[BANK0 ] unsigned int 
;;  multiplicand    2    2[BANK0 ] unsigned int 
;; Auto vars:     Size  Location     Type
;;  product         2    4[COMMON] unsigned int 
;; Return value:  Size  Location     Type
;;                  2    0[BANK0 ] unsigned int 
;; Registers used:
;;		wreg, status,2, status,0
;; Tracked objects:
;;		On entry : 1F/0
;;		On exit  : 1F/0
;;		Unchanged: FFFE0/0
;; Data sizes:     COMMON   BANK0   BANK1   BANK2
;;      Params:         0       4       0       0
;;      Locals:         2       0       0       0
;;      Temps:          0       0       0       0
;;      Totals:         2       4       0       0
;;Total ram usage:        6 bytes
;; Hardware stack levels used:    1
;; Hardware stack levels required when called:    2
;; This function calls:
;;		Nothing
;; This function is called by:
;;		_SolarPanelDealWith
;;		_KaiJi
;; This function uses a non-reentrant model
;;
psect	text842
	file	"C:\Program Files\HI-TECH Software\PICC\9.80\sources\wmul.c"
	line	3
	global	__size_of___wmul
	__size_of___wmul	equ	__end_of___wmul-___wmul
	
___wmul:	
	opt	stack 12
; Regs used in ___wmul: [wreg+status,2+status,0]
	line	4
	
l11776:	
	clrf	(___wmul@product)
	clrf	(___wmul@product+1)
	line	7
	
l11778:	
	btfss	(___wmul@multiplier),(0)&7
	goto	u2961
	goto	u2960
u2961:
	goto	l11782
u2960:
	line	8
	
l11780:	
	movf	(___wmul@multiplicand),w
	addwf	(___wmul@product),f
	movf	(___wmul@multiplicand+1),w
	addwfc	(___wmul@product+1),f
	line	9
	
l11782:	
	lslf	(___wmul@multiplicand),f
	rlf	(___wmul@multiplicand+1),f
	line	10
	
l11784:	
	lsrf	(___wmul@multiplier+1),f
	rrf	(___wmul@multiplier),f
	line	11
	
l11786:	
	movf	((___wmul@multiplier+1)),w
	iorwf	((___wmul@multiplier)),w
	skipz
	goto	u2971
	goto	u2970
u2971:
	goto	l11778
u2970:
	line	12
	
l11788:	
	movf	(___wmul@product+1),w
	movwf	(?___wmul+1)
	movf	(___wmul@product),w
	movwf	(?___wmul)
	line	13
	
l8927:	
	return
	opt stack 0
GLOBAL	__end_of___wmul
	__end_of___wmul:
;; =============== function ___wmul ends ============

	signat	___wmul,8314
	global	_BatteryStateSwitch
psect	text843,local,class=CODE,delta=2
global __ptext843
__ptext843:

;; *************** function _BatteryStateSwitch *****************
;; Defined at:
;;		line 516 in file "C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.c"
;; Parameters:    Size  Location     Type
;;  BatteryVolta    2    4[COMMON] unsigned int 
;; Auto vars:     Size  Location     Type
;;  adjust          2    4[BANK0 ] unsigned int 
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
;;      Locals:         0       2       0       0
;;      Temps:          0       4       0       0
;;      Totals:         2       6       0       0
;;Total ram usage:        8 bytes
;; Hardware stack levels used:    1
;; Hardware stack levels required when called:    2
;; This function calls:
;;		Nothing
;; This function is called by:
;;		_SwitchBatteryState
;; This function uses a non-reentrant model
;;
psect	text843
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.c"
	line	516
	global	__size_of_BatteryStateSwitch
	__size_of_BatteryStateSwitch	equ	__end_of_BatteryStateSwitch-_BatteryStateSwitch
	
_BatteryStateSwitch:	
	opt	stack 12
; Regs used in _BatteryStateSwitch: [wreg+fsr1l-status,0]
	line	517
	
l11720:	
;mypic.c: 517: unsigned int adjust = (TemVoltage + ADVoltage - TemBase - ADBase );
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
	
l11722:	
	movf	(_ADVoltage),w
	addwf	(BatteryStateSwitch@adjust),f
	movf	(_ADVoltage+1),w
	addwfc	(BatteryStateSwitch@adjust+1),f
	line	518
	
l11724:	
;mypic.c: 518: if(BatteryVoltage < *(BatteryStandard + 0) + adjust){
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
	goto	u2875
	movf	0+(??_BatteryStateSwitch+2)+0,w
	subwf	(BatteryStateSwitch@BatteryVoltage),w
u2875:
	skipnc
	goto	u2871
	goto	u2870
u2871:
	goto	l11732
u2870:
	line	519
	
l11726:	
;mypic.c: 519: return 1;
	movlw	(01h)
	goto	l8780
	line	521
	
l11732:	
;mypic.c: 521: && BatteryVoltage < (*(BatteryStandard + 2) + adjust)){
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
	goto	u2885
	movf	0+(??_BatteryStateSwitch+2)+0,w
	subwf	(BatteryStateSwitch@BatteryVoltage),w
u2885:
	skipc
	goto	u2881
	goto	u2880
u2881:
	goto	l11742
u2880:
	
l11734:	
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
	goto	u2895
	movf	0+(??_BatteryStateSwitch+2)+0,w
	subwf	(BatteryStateSwitch@BatteryVoltage),w
u2895:
	skipnc
	goto	u2891
	goto	u2890
u2891:
	goto	l11742
u2890:
	line	522
	
l11736:	
;mypic.c: 522: return 2;
	movlw	(02h)
	goto	l8780
	line	524
	
l11742:	
;mypic.c: 524: && BatteryVoltage <( *(BatteryStandard + 1) + adjust)){
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
	goto	u2905
	movf	0+(??_BatteryStateSwitch+2)+0,w
	subwf	(BatteryStateSwitch@BatteryVoltage),w
u2905:
	skipc
	goto	u2901
	goto	u2900
u2901:
	goto	l11752
u2900:
	
l11744:	
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
	goto	u2915
	movf	0+(??_BatteryStateSwitch+2)+0,w
	subwf	(BatteryStateSwitch@BatteryVoltage),w
u2915:
	skipnc
	goto	u2911
	goto	u2910
u2911:
	goto	l11752
u2910:
	line	525
	
l11746:	
;mypic.c: 525: return 3;
	movlw	(03h)
	goto	l8780
	line	527
	
l11752:	
;mypic.c: 527: && BatteryVoltage < (*(BatteryStandard + 3) + adjust)){
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
	goto	u2925
	movf	0+(??_BatteryStateSwitch+2)+0,w
	subwf	(BatteryStateSwitch@BatteryVoltage),w
u2925:
	skipc
	goto	u2921
	goto	u2920
u2921:
	goto	l11762
u2920:
	
l11754:	
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
	goto	u2935
	movf	0+(??_BatteryStateSwitch+2)+0,w
	subwf	(BatteryStateSwitch@BatteryVoltage),w
u2935:
	skipnc
	goto	u2931
	goto	u2930
u2931:
	goto	l11762
u2930:
	line	528
	
l11756:	
;mypic.c: 528: return 4;
	movlw	(04h)
	goto	l8780
	line	530
	
l11762:	
;mypic.c: 530: && BatteryVoltage < (*(BatteryStandard + 4) + adjust)){
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
	goto	u2945
	movf	0+(??_BatteryStateSwitch+2)+0,w
	subwf	(BatteryStateSwitch@BatteryVoltage),w
u2945:
	skipc
	goto	u2941
	goto	u2940
u2941:
	goto	l11772
u2940:
	
l11764:	
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
	goto	u2955
	movf	0+(??_BatteryStateSwitch+2)+0,w
	subwf	(BatteryStateSwitch@BatteryVoltage),w
u2955:
	skipnc
	goto	u2951
	goto	u2950
u2951:
	goto	l11772
u2950:
	line	531
	
l11766:	
;mypic.c: 531: return 5;
	movlw	(05h)
	goto	l8780
	line	533
	
l11772:	
;mypic.c: 533: return 6;
	movlw	(06h)
	line	552
	
l8780:	
	return
	opt stack 0
GLOBAL	__end_of_BatteryStateSwitch
	__end_of_BatteryStateSwitch:
;; =============== function _BatteryStateSwitch ends ============

	signat	_BatteryStateSwitch,4217
	global	_ReadEE
psect	text844,local,class=CODE,delta=2
global __ptext844
__ptext844:

;; *************** function _ReadEE *****************
;; Defined at:
;;		line 31 in file "C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.c"
;; Parameters:    Size  Location     Type
;;  addr            1    wreg     unsigned char 
;; Auto vars:     Size  Location     Type
;;  addr            1    4[COMMON] unsigned char 
;; Return value:  Size  Location     Type
;;                  1    wreg      unsigned char 
;; Registers used:
;;		wreg
;; Tracked objects:
;;		On entry : 17F/1
;;		On exit  : 17F/3
;;		Unchanged: FFE80/0
;; Data sizes:     COMMON   BANK0   BANK1   BANK2
;;      Params:         0       0       0       0
;;      Locals:         1       0       0       0
;;      Temps:          0       0       0       0
;;      Totals:         1       0       0       0
;;Total ram usage:        1 bytes
;; Hardware stack levels used:    1
;; Hardware stack levels required when called:    2
;; This function calls:
;;		Nothing
;; This function is called by:
;;		_readFromEEPROM
;; This function uses a non-reentrant model
;;
psect	text844
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.c"
	line	31
	global	__size_of_ReadEE
	__size_of_ReadEE	equ	__end_of_ReadEE-_ReadEE
	
_ReadEE:	
	opt	stack 11
; Regs used in _ReadEE: [wreg]
;ReadEE@addr stored from wreg
	movwf	(ReadEE@addr)
	line	32
	
l11706:	
;mypic.c: 32: while(RD == 1);
	
l8659:	
	movlb 3	; select bank3
	btfsc	(3240/8)^0180h,(3240)&7
	goto	u2851
	goto	u2850
u2851:
	goto	l8659
u2850:
	line	33
	
l11708:	
;mypic.c: 33: EEADRL = addr;
	movf	(ReadEE@addr),w
	movwf	(401)^0180h	;volatile
	line	34
	
l11710:	
;mypic.c: 34: EEPGD = 0;
	bcf	(3247/8)^0180h,(3247)&7
	line	35
	
l11712:	
;mypic.c: 35: CFGS = 0;
	bcf	(3246/8)^0180h,(3246)&7
	line	36
	
l11714:	
;mypic.c: 36: RD = 1;
	bsf	(3240/8)^0180h,(3240)&7
	line	37
;mypic.c: 37: while(RD == 1);
	
l8662:	
	btfsc	(3240/8)^0180h,(3240)&7
	goto	u2861
	goto	u2860
u2861:
	goto	l8662
u2860:
	line	38
	
l11716:	
;mypic.c: 38: return EEDATL;
	movf	(403)^0180h,w	;volatile
	line	39
	
l8665:	
	return
	opt stack 0
GLOBAL	__end_of_ReadEE
	__end_of_ReadEE:
;; =============== function _ReadEE ends ============

	signat	_ReadEE,4217
	global	_CTKSoftDelay
psect	text845,local,class=CODE,delta=2
global __ptext845
__ptext845:

;; *************** function _CTKSoftDelay *****************
;; Defined at:
;;		line 6 in file "C:\CTK\CTK80A 150928\CTK80A_Jas\public.c"
;; Parameters:    Size  Location     Type
;;  x               2    4[COMMON] unsigned int 
;; Auto vars:     Size  Location     Type
;;  b               2    2[BANK0 ] unsigned int 
;;  a               2    0[BANK0 ] unsigned int 
;; Return value:  Size  Location     Type
;;		None               void
;; Registers used:
;;		wreg, status,2, status,0
;; Tracked objects:
;;		On entry : 1E/0
;;		On exit  : 1F/0
;;		Unchanged: FFFE0/0
;; Data sizes:     COMMON   BANK0   BANK1   BANK2
;;      Params:         2       0       0       0
;;      Locals:         0       4       0       0
;;      Temps:          0       0       0       0
;;      Totals:         2       4       0       0
;;Total ram usage:        6 bytes
;; Hardware stack levels used:    1
;; Hardware stack levels required when called:    2
;; This function calls:
;;		Nothing
;; This function is called by:
;;		_lcd_write_command
;;		_lcd_write_data
;;		_lcd_init
;;		_system_state_init
;;		_getADValueOneTime
;;		_SolarPanelDealWith
;;		_SwitchBatteryState
;; This function uses a non-reentrant model
;;
psect	text845
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\public.c"
	line	6
	global	__size_of_CTKSoftDelay
	__size_of_CTKSoftDelay	equ	__end_of_CTKSoftDelay-_CTKSoftDelay
	
_CTKSoftDelay:	
	opt	stack 9
; Regs used in _CTKSoftDelay: [wreg+status,2+status,0]
	line	8
	
l11686:	
;public.c: 7: unsigned int a,b;
;public.c: 8: for(a=x;a>0;a--)
	movf	(CTKSoftDelay@x+1),w
	movlb 0	; select bank0
	movwf	(CTKSoftDelay@a+1)
	movf	(CTKSoftDelay@x),w
	movwf	(CTKSoftDelay@a)
	
l11688:	
	movf	((CTKSoftDelay@a+1)),w
	iorwf	((CTKSoftDelay@a)),w
	skipz
	goto	u2831
	goto	u2830
u2831:
	goto	l11692
u2830:
	goto	l4328
	line	9
	
l11692:	
;public.c: 9: for(b=10;b>0;b--);
	movlw	0Ah
	movwf	(CTKSoftDelay@b)
	clrf	(CTKSoftDelay@b+1)
	
l11698:	
	movlw	low(01h)
	subwf	(CTKSoftDelay@b),f
	movlw	high(01h)
	subwfb	(CTKSoftDelay@b+1),f
	
l11700:	
	movf	((CTKSoftDelay@b+1)),w
	iorwf	((CTKSoftDelay@b)),w
	skipz
	goto	u2841
	goto	u2840
u2841:
	goto	l11698
u2840:
	line	8
	
l11702:	
	movlw	low(01h)
	subwf	(CTKSoftDelay@a),f
	movlw	high(01h)
	subwfb	(CTKSoftDelay@a+1),f
	goto	l11688
	line	10
	
l4328:	
	return
	opt stack 0
GLOBAL	__end_of_CTKSoftDelay
	__end_of_CTKSoftDelay:
;; =============== function _CTKSoftDelay ends ============

	signat	_CTKSoftDelay,4216
	global	_LedDisplay
psect	text846,local,class=CODE,delta=2
global __ptext846
__ptext846:

;; *************** function _LedDisplay *****************
;; Defined at:
;;		line 956 in file "C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.c"
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
;; Hardware stack levels required when called:    2
;; This function calls:
;;		Nothing
;; This function is called by:
;;		_main
;; This function uses a non-reentrant model
;;
psect	text846
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.c"
	line	956
	global	__size_of_LedDisplay
	__size_of_LedDisplay	equ	__end_of_LedDisplay-_LedDisplay
	
_LedDisplay:	
	opt	stack 13
; Regs used in _LedDisplay: []
	line	957
	
l10674:	
;mypic.c: 957: if(LoadFlag == 0)
	btfsc	(_LoadFlag/8),(_LoadFlag)&7
	goto	u1531
	goto	u1530
u1531:
	goto	l8875
u1530:
	line	959
	
l10676:	
;mypic.c: 958: {
;mypic.c: 959: (LATB |= (1 << 7));
	movlb 2	; select bank2
	bsf	(269)^0100h+(7/8),(7)&7	;volatile
	line	960
	
l8875:	
	line	961
;mypic.c: 960: }
;mypic.c: 961: if(LoadFlag == 1)
	btfss	(_LoadFlag/8),(_LoadFlag)&7
	goto	u1541
	goto	u1540
u1541:
	goto	l8876
u1540:
	line	963
	
l10678:	
;mypic.c: 962: {
;mypic.c: 963: (LATB &= ~(1 << 7));
	movlb 2	; select bank2
	bcf	(269)^0100h+(7/8),(7)&7	;volatile
	line	964
	
l8876:	
	line	966
;mypic.c: 964: }
;mypic.c: 966: if(PVChargeFlag == 1)
	btfss	(_PVChargeFlag/8),(_PVChargeFlag)&7
	goto	u1551
	goto	u1550
u1551:
	goto	l8877
u1550:
	line	968
	
l10680:	
;mypic.c: 967: {
;mypic.c: 968: (LATB |= (1 << 6));
	movlb 2	; select bank2
	bsf	(269)^0100h+(6/8),(6)&7	;volatile
	line	969
	
l8877:	
	line	970
;mypic.c: 969: }
;mypic.c: 970: if(PVChargeFlag == 0)
	btfsc	(_PVChargeFlag/8),(_PVChargeFlag)&7
	goto	u1561
	goto	u1560
u1561:
	goto	l8879
u1560:
	line	972
	
l10682:	
;mypic.c: 971: {
;mypic.c: 972: (LATB &= ~(1 << 6));
	movlb 2	; select bank2
	bcf	(269)^0100h+(6/8),(6)&7	;volatile
	line	974
	
l8879:	
	return
	opt stack 0
GLOBAL	__end_of_LedDisplay
	__end_of_LedDisplay:
;; =============== function _LedDisplay ends ============

	signat	_LedDisplay,88
	global	_SelectMode
psect	text847,local,class=CODE,delta=2
global __ptext847
__ptext847:

;; *************** function _SelectMode *****************
;; Defined at:
;;		line 234 in file "C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.c"
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
;; Hardware stack levels required when called:    2
;; This function calls:
;;		Nothing
;; This function is called by:
;;		_main
;; This function uses a non-reentrant model
;;
psect	text847
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\mypic.c"
	line	234
	global	__size_of_SelectMode
	__size_of_SelectMode	equ	__end_of_SelectMode-_SelectMode
	
_SelectMode:	
	opt	stack 13
; Regs used in _SelectMode: [wreg-fsr0h+status,2+status,0]
	line	235
	
l10648:	
;mypic.c: 235: switch(SystemModeType)
	goto	l10672
	line	237
;mypic.c: 236: {
;mypic.c: 237: case 0X05:
	
l8709:	
	line	239
;mypic.c: 238: {
;mypic.c: 239: if((DAYTIME == 0) && LightTime)
	btfsc	(_DAYTIME/8),(_DAYTIME)&7
	goto	u1461
	goto	u1460
u1461:
	goto	l8710
u1460:
	
l10650:	
	movf	(_LightTime),w
	skipz
	goto	u1470
	goto	l8710
u1470:
	line	241
	
l10652:	
;mypic.c: 240: {
;mypic.c: 241: if(TimeModeHour >= 36000)
	movlw	high(08CA0h)
	subwf	(_TimeModeHour+1),w
	movlw	low(08CA0h)
	skipnz
	subwf	(_TimeModeHour),w
	skipc
	goto	u1481
	goto	u1480
u1481:
	goto	l10658
u1480:
	line	243
	
l10654:	
;mypic.c: 242: {
;mypic.c: 243: TimeModeHour = 0;
	clrf	(_TimeModeHour)
	clrf	(_TimeModeHour+1)
	line	244
	
l10656:	
;mypic.c: 244: LightTime--;
	decf	(_LightTime),f
	line	246
	
l10658:	
;mypic.c: 245: }
;mypic.c: 246: if(LightTime != 0)
	movf	(_LightTime),w
	skipz
	goto	u1490
	goto	l8712
u1490:
	line	248
	
l10660:	
;mypic.c: 247: {
;mypic.c: 248: LoadOpen = 1;
	bsf	(_LoadOpen/8),(_LoadOpen)&7
	line	249
;mypic.c: 249: }
	goto	l8723
	line	250
	
l8712:	
	line	252
;mypic.c: 250: else
;mypic.c: 251: {
;mypic.c: 252: LoadOpen = 0;
	bcf	(_LoadOpen/8),(_LoadOpen)&7
	goto	l8723
	line	255
	
l8710:	
;mypic.c: 255: else if(DAYTIME == 1)
	btfss	(_DAYTIME/8),(_DAYTIME)&7
	goto	u1501
	goto	u1500
u1501:
	goto	l8723
u1500:
	line	257
	
l10662:	
;mypic.c: 256: {
;mypic.c: 257: LoadOpen = 0;
	bcf	(_LoadOpen/8),(_LoadOpen)&7
	line	259
	
l10664:	
;mypic.c: 259: TimeModeHour = 0;
	clrf	(_TimeModeHour)
	clrf	(_TimeModeHour+1)
	goto	l8723
	line	263
;mypic.c: 262: }
;mypic.c: 263: case 0x02:
	
l8717:	
	line	265
;mypic.c: 264: {
;mypic.c: 265: if(DAYTIME == 0)
	btfsc	(_DAYTIME/8),(_DAYTIME)&7
	goto	u1511
	goto	u1510
u1511:
	goto	l8718
u1510:
	goto	l10660
	line	269
	
l8718:	
;mypic.c: 269: else if(DAYTIME == 1)
	btfss	(_DAYTIME/8),(_DAYTIME)&7
	goto	u1521
	goto	u1520
u1521:
	goto	l8723
u1520:
	goto	l8712
	line	235
	
l10672:	
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
	goto	l10660
	xorlw	2^1	; case 2
	skipnz
	goto	l8717
	xorlw	3^2	; case 3
	skipnz
	goto	l8723
	xorlw	5^3	; case 5
	skipnz
	goto	l8709
	goto	l8723

	line	285
	
l8723:	
	return
	opt stack 0
GLOBAL	__end_of_SelectMode
	__end_of_SelectMode:
;; =============== function _SelectMode ends ============

	signat	_SelectMode,88
	global	_ISR_Timer
psect	intentry,class=CODE,delta=2
global __pintentry
__pintentry:

;; *************** function _ISR_Timer *****************
;; Defined at:
;;		line 168 in file "C:\CTK\CTK80A 150928\CTK80A_Jas\main.c"
;; Parameters:    Size  Location     Type
;;		None
;; Auto vars:     Size  Location     Type
;;		None
;; Return value:  Size  Location     Type
;;		None               void
;; Registers used:
;;		wreg, status,2, status,0, pclath, cstack
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
;;		_handlerKeyEventInput
;; This function is called by:
;;		Interrupt level 1
;; This function uses a non-reentrant model
;;
psect	intentry
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\main.c"
	line	168
	global	__size_of_ISR_Timer
	__size_of_ISR_Timer	equ	__end_of_ISR_Timer-_ISR_Timer
	
_ISR_Timer:	
	opt	stack 9
; Regs used in _ISR_Timer: [wreg+status,2+status,0+pclath+cstack]
psect	intentry
	pagesel	$
	line	169
	
i1l10684:	
;main.c: 169: if(IOCBF3 && lcd_state != 0 && lcd_state != 1)
	movlb 7	; select bank7
	btfss	(7347/8)^0380h,(7347)&7
	goto	u157_21
	goto	u157_20
u157_21:
	goto	i1l10696
u157_20:
	
i1l10686:	
	movlb 0	; select bank0
	movf	(_lcd_state),w
	skipz
	goto	u158_20
	goto	i1l10696
u158_20:
	
i1l10688:	
	decf	(_lcd_state),w
	skipnz
	goto	u159_21
	goto	u159_20
u159_21:
	goto	i1l10696
u159_20:
	line	172
	
i1l10690:	
;main.c: 170: {
;main.c: 172: if (RB3 == 0) {
	btfsc	(107/8),(107)&7
	goto	u160_21
	goto	u160_20
u160_21:
	goto	i1l10694
u160_20:
	line	173
	
i1l10692:	
;main.c: 173: handlerKeyEventInput(3);
	movlw	03h
	movwf	(?_handlerKeyEventInput)
	clrf	(?_handlerKeyEventInput+1)
	fcall	_handlerKeyEventInput
	line	175
	
i1l10694:	
;main.c: 174: }
;main.c: 175: IOCBF3 = 0;
	movlb 7	; select bank7
	bcf	(7347/8)^0380h,(7347)&7
	line	177
	
i1l10696:	
;main.c: 176: }
;main.c: 177: if(IOCBF5 && lcd_state != 0 && lcd_state != 1)
	movlb 7	; select bank7
	btfss	(7349/8)^0380h,(7349)&7
	goto	u161_21
	goto	u161_20
u161_21:
	goto	i1l10708
u161_20:
	
i1l10698:	
	movlb 0	; select bank0
	movf	(_lcd_state),w
	skipz
	goto	u162_20
	goto	i1l10708
u162_20:
	
i1l10700:	
	decf	(_lcd_state),w
	skipnz
	goto	u163_21
	goto	u163_20
u163_21:
	goto	i1l10708
u163_20:
	line	179
	
i1l10702:	
;main.c: 178: {
;main.c: 179: if (RB5 == 0) {
	btfsc	(109/8),(109)&7
	goto	u164_21
	goto	u164_20
u164_21:
	goto	i1l10706
u164_20:
	line	180
	
i1l10704:	
;main.c: 180: handlerKeyEventInput(4);
	movlw	04h
	movwf	(?_handlerKeyEventInput)
	clrf	(?_handlerKeyEventInput+1)
	fcall	_handlerKeyEventInput
	line	182
	
i1l10706:	
;main.c: 181: }
;main.c: 182: IOCBF5 = 0;
	movlb 7	; select bank7
	bcf	(7349/8)^0380h,(7349)&7
	line	185
	
i1l10708:	
;main.c: 183: }
;main.c: 185: if(IOCBF4)
	movlb 7	; select bank7
	btfss	(7348/8)^0380h,(7348)&7
	goto	u165_21
	goto	u165_20
u165_21:
	goto	i1l10716
u165_20:
	line	188
	
i1l10710:	
;main.c: 186: {
;main.c: 188: if (RB4 == 0) {
	movlb 0	; select bank0
	btfsc	(108/8),(108)&7
	goto	u166_21
	goto	u166_20
u166_21:
	goto	i1l10714
u166_20:
	line	189
	
i1l10712:	
;main.c: 189: handlerKeyEventInput(2);
	movlw	02h
	movwf	(?_handlerKeyEventInput)
	clrf	(?_handlerKeyEventInput+1)
	fcall	_handlerKeyEventInput
	line	192
	
i1l10714:	
;main.c: 190: }
;main.c: 192: IOCBF4 = 0;
	movlb 7	; select bank7
	bcf	(7348/8)^0380h,(7348)&7
	line	195
	
i1l10716:	
;main.c: 196: {
;main.c: 197: OS_Time ++;
	movlb 0	; select bank0
	btfss	(136/8),(136)&7
	goto	u167_21
	goto	u167_20
u167_21:
	goto	i1l10722
u167_20:
	line	198
	
i1l10718:	
;main.c: 198: TMR1IF = 0;
	bcf	(136/8),(136)&7
	line	199
	
i1l10720:	
;main.c: 199: TMR1H = (65536 - (100000 / 4)) >> 8;
	movlw	(09Eh)
	movwf	(23)	;volatile
	line	200
;main.c: 200: TMR1L = (65536 - (100000 / 4)) & 0xFF;
	movlw	(058h)
	movwf	(22)	;volatile
	line	202
	
i1l10722:	
;main.c: 201: }
;main.c: 202: if(TMR2IF)
	btfss	(137/8),(137)&7
	goto	u168_21
	goto	u168_20
u168_21:
	goto	i1l2237
u168_20:
	line	204
	
i1l10724:	
;main.c: 203: {
;main.c: 204: TMR2IF = 0;
	bcf	(137/8),(137)&7
	line	205
;main.c: 205: if(PWMFlag == 1)
	btfss	(_PWMFlag/8),(_PWMFlag)&7
	goto	u169_21
	goto	u169_20
u169_21:
	goto	i1l2237
u169_20:
	line	207
	
i1l10726:	
;main.c: 206: {
;main.c: 207: PwmCount++;
	incf	(_PwmCount),f
	line	209
	
i1l10728:	
;main.c: 209: if(PwmCount >= DutyRatio)
	movf	(_DutyRatio),w
	subwf	(_PwmCount),w
	skipc
	goto	u170_21
	goto	u170_20
u170_21:
	goto	i1l2239
u170_20:
	line	210
	
i1l10730:	
;main.c: 210: (PORTA |= (1 << 0));
	bsf	(12)+(0/8),(0)&7	;volatile
	goto	i1l2237
	line	211
	
i1l2239:	
	line	212
;main.c: 211: else
;main.c: 212: (PORTA &= ~(1 << 0));
	bcf	(12)+(0/8),(0)&7	;volatile
	line	214
	
i1l2237:	
	line	215
;main.c: 213: }
;main.c: 214: }
;main.c: 215: if(TMR4IF)
	btfss	(153/8),(153)&7
	goto	u171_21
	goto	u171_20
u171_21:
	goto	i1l10736
u171_20:
	line	217
	
i1l10732:	
;main.c: 216: {
;main.c: 217: TMR4IF = 0;
	bcf	(153/8),(153)&7
	line	218
	
i1l10734:	
;main.c: 218: RE2 = ~RE2;
	movlw	1<<((130)&7)
	xorwf	((130)/8),f
	line	220
	
i1l10736:	
;main.c: 219: }
;main.c: 220: if(TMR6IF)
	btfss	(155/8),(155)&7
	goto	u172_21
	goto	u172_20
u172_21:
	goto	i1l2243
u172_20:
	line	222
	
i1l10738:	
;main.c: 221: {
;main.c: 222: TMR6IF = 0;
	bcf	(155/8),(155)&7
	line	224
	
i1l2243:	
	retfie
	opt stack 0
GLOBAL	__end_of_ISR_Timer
	__end_of_ISR_Timer:
;; =============== function _ISR_Timer ends ============

	signat	_ISR_Timer,88
	global	_handlerKeyEventInput
psect	text849,local,class=CODE,delta=2
global __ptext849
__ptext849:

;; *************** function _handlerKeyEventInput *****************
;; Defined at:
;;		line 30 in file "C:\CTK\CTK80A 150928\CTK80A_Jas\main.c"
;; Parameters:    Size  Location     Type
;;  keyCode         2    0[COMMON] int 
;; Auto vars:     Size  Location     Type
;;		None
;; Return value:  Size  Location     Type
;;		None               void
;; Registers used:
;;		wreg
;; Tracked objects:
;;		On entry : 1F/0
;;		On exit  : 1F/0
;;		Unchanged: FFFE0/0
;; Data sizes:     COMMON   BANK0   BANK1   BANK2
;;      Params:         2       0       0       0
;;      Locals:         0       0       0       0
;;      Temps:          2       0       0       0
;;      Totals:         4       0       0       0
;;Total ram usage:        4 bytes
;; Hardware stack levels used:    1
;; This function calls:
;;		Nothing
;; This function is called by:
;;		_ISR_Timer
;; This function uses a non-reentrant model
;;
psect	text849
	file	"C:\CTK\CTK80A 150928\CTK80A_Jas\main.c"
	line	30
	global	__size_of_handlerKeyEventInput
	__size_of_handlerKeyEventInput	equ	__end_of_handlerKeyEventInput-_handlerKeyEventInput
	
_handlerKeyEventInput:	
	opt	stack 9
; Regs used in _handlerKeyEventInput: [wreg]
	line	32
	
i1l10638:	
;main.c: 32: if(keyCode == 2 || keyCode == 3 || keyCode == 4){
		movf	(handlerKeyEventInput@keyCode),w
	xorlw	2
	iorwf	(handlerKeyEventInput@keyCode+1),w

	skipnz
	goto	u142_21
	goto	u142_20
u142_21:
	goto	i1l10644
u142_20:
	
i1l10640:	
		movf	(handlerKeyEventInput@keyCode),w
	xorlw	3
	iorwf	(handlerKeyEventInput@keyCode+1),w

	skipnz
	goto	u143_21
	goto	u143_20
u143_21:
	goto	i1l10644
u143_20:
	
i1l10642:	
		movf	(handlerKeyEventInput@keyCode),w
	xorlw	4
	iorwf	(handlerKeyEventInput@keyCode+1),w

	skipz
	goto	u144_21
	goto	u144_20
u144_21:
	goto	i1l2204
u144_20:
	line	33
	
i1l10644:	
;main.c: 33: if(KeyEvent_PendKeyCode != keyCode){
	movf	(_KeyEvent_PendKeyCode),w
	movwf	(??_handlerKeyEventInput+0)+0
	clrf	(??_handlerKeyEventInput+0)+0+1
	movf	(handlerKeyEventInput@keyCode+1),w
	xorwf	1+(??_handlerKeyEventInput+0)+0,w
	skipz
	goto	u145_25
	movf	(handlerKeyEventInput@keyCode),w
	xorwf	0+(??_handlerKeyEventInput+0)+0,w
u145_25:

	skipnz
	goto	u145_21
	goto	u145_20
u145_21:
	goto	i1l2204
u145_20:
	line	34
	
i1l10646:	
;main.c: 35: KeyEvent_KeyTime = OS_Time;
	movf	(handlerKeyEventInput@keyCode),w
	movwf	(_KeyEvent_PendKeyCode)
	line	38
	
i1l2204:	
	return
	opt stack 0
GLOBAL	__end_of_handlerKeyEventInput
	__end_of_handlerKeyEventInput:
;; =============== function _handlerKeyEventInput ends ============

	signat	_handlerKeyEventInput,4216
psect	text850,local,class=CODE,delta=2
global __ptext850
__ptext850:
	global	btemp
	btemp set 07Eh

	DABS	1,126,2	;btemp
	global	wtemp0
	wtemp0 set btemp
	end
