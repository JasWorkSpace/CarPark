package com.greenorange.gooutdoor.framework.Model.Event;

/**
 * Created by JasWorkSpace on 15/4/20.
 */
public class EventID {

    /////////////////////////////
    // Click Event ID
    public final static int ID_CLICK = 1;
    public final static int ID_CLICK_FloatControlSmileFlash = ID_CLICK+1;
    public final static int ID_CLICK_FloatControlMenu       = ID_CLICK_FloatControlSmileFlash + 1;
    public final static int ID_CLICK_FloatControlSports     = ID_CLICK_FloatControlMenu + 1;
    public final static int ID_CLICK_TimerRelativeLayout    = ID_CLICK_FloatControlSports + 1;
    public final static int ID_CLICK_FinishSportsDialog     = ID_CLICK_TimerRelativeLayout + 1;
    public final static int ID_CLICK_MapDataInfo            = ID_CLICK_FinishSportsDialog + 1;

    /////////////////////////////
    // State Event ID
    public final static int ID_STATE = 1000;
    public final static int ID_STATE_FloatControlMenu             = ID_STATE + 1;
    public final static int ID_STATE_TimerRelativeLayout          = ID_STATE_FloatControlMenu + 1;
    public final static int ID_STATE_FragmentFloatControl         = ID_STATE_TimerRelativeLayout + 1;
    public final static int ID_STATE_APPLICATION_SPORTSSTATE      = ID_STATE_FragmentFloatControl + 1;


    ///////////////////////////////
    // Msg Event ID
    public final static int ID_MSG  = 2000;
    public final static int ID_MSG_FragmentFloatControl           = ID_MSG + 1;
    public final static int ID_MSG_LocationControl                = ID_MSG_FragmentFloatControl + 1;
    public final static int ID_MSG_FlashDao                       = ID_MSG_LocationControl + 1;
    public final static int ID_MSG_SaveStepChange                 = ID_MSG_FlashDao + 1;

}
