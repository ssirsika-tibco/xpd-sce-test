/*
 **
 **  MODULE:             $RCSfile: IntEngConsts.java $
 **                      $Revision: 1.11 $
 **                      $Date: 2005/04/12 13:53:26Z $
 **
 ** DESCRIPTION:
 **
 **
 **  ENVIRONMENT:  Java - Platform independent
 **
 **  COPYRIGHT:    (c) 2004 Staffware plc, All Rights Reserved.
 **
 **  MODIFICATION HISTORY:
 **
 **    $Log: IntEngConsts.java $
 **    Revision 1.11  2005/04/12 13:53:26Z  KamleshU
 **    Revision 1.10  2005/03/29 11:49:53Z  tstephen
 **    moved from int eng web 
 **    Revision 1.9  2005/03/09 17:38:35Z  KamleshU
 **    Revision 1.8  2005/03/04 11:47:24Z  KamleshU
 **    Revision 1.7  2005/03/01 19:24:33Z  KamleshU
 **    Revision 1.6  2005/03/01 19:20:25Z  KamleshU
 **    Revision 1.5  2004/07/26 10:30:23Z  KamleshU
 **    Added a constant for file type
 **    Revision 1.4  2004/04/08 16:02:15Z  WojciechZ
 **    code review
 **    move to apache xml beans
 **    xpdl data use xml beans to hold the data
 **    Revision 1.3  2004/04/02 14:19:40Z  WojciechZ
 **    work up to 02/04/2004
 **    Revision 1.2  2004/03/29 08:48:42Z  WojciechZ
 **    Work up to 29-03-2004
 **    Revision 1.0  22-Mar-2004  WojciechZ
 **    Initial revision
 **
 */
package com.tibco.inteng;
/**
 * set of constant values in InteractionImpl Engine
 *
 * @author WojciechZ
 */
public final class IntEngConsts {	
    /** value of the navigation extended attribute on which we should skip form */
    public static final String SKIP_FORM_ON = "SKIP";
    /** value of the navigation extended attribute on which we should validate form */
    public static final String VALIDATE_FORM_ON = "VALIDATE";    
    /** special attribute that can change form behavior */
    public static final String NAVIGATION = "navigation";
    /** special sub element of FileType element, this has to present to 
     * ascertain whether some value has been entered by the user*/
    public static final String FILENAME = "fileName";    
    /** the button value which will save state of the current InteractionImpl */
    public static final String SAVE_STATE = "SAVE_STATE";	
    
    public static final String ROUTE_AS_MANUAL = "RouteAsManual";
    public static final String NOOP_IMPL_AS_MANUAL = "NoOpImplAsManual";
    public static final String AUTO_ACT_AS_MANUAL = "AutoActivityAsManual";
    public static final String SUB_PROCESS_AS_MANUAL = "SubProcessAsManual";
    public static final String THREAD_DELIM = "::";
    
    /*
     * Validation Consts while loading the package.     
     */
    /**
     * This key tells the InteractionRepository to ignore Application checks 
     * used on the activity.
     */
    public static final String IGNORE_APP_REFERENCE = "IgnoreAppReference";
    public static final String IGNORE_SUBFLOW_REFERENCE = "IgnoreSubFlowReference";
}