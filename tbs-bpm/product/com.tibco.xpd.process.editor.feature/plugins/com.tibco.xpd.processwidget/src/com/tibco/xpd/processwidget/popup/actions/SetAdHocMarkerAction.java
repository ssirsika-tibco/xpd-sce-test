/*
 ** 
 **  MODULE:             $RCSfile: $ 
 **                      $Revision: $ 
 **                      $Date: $ 
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2005 TIBCO Software Inc., All Rights Reserved. 
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: $ 
 ** 
 */

package com.tibco.xpd.processwidget.popup.actions;

import com.tibco.xpd.processwidget.adapters.ActivityMarkerType;

/**
 * @author wzurek
 *
 */
public class SetAdHocMarkerAction extends BaseSetMarkerAction {
    public SetAdHocMarkerAction() {
        super(ActivityMarkerType.MARKER_AD_HOC_LITERAL);
    }
}
