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

import java.util.Set;

import com.tibco.xpd.processwidget.adapters.ActivityMarkerType;

/**
 * @author wzurek
 *
 */
public class SetMultipleMarkerAction extends BaseSetMarkerAction {
    public SetMultipleMarkerAction() {
        super(ActivityMarkerType.MARKER_MULTIPLE_LITERAL);
    }
    
    protected void validateMarkerSet(Set markerTypes) {
    	// In BPMN Loop and Multiple Instance are mutually exclusive
		// (because Multiple is just another type of loop).
		// If our Loop marker type has been set then remove the multiple
		// instance marker.
    	if (markerTypes.contains(ActivityMarkerType.MARKER_MULTIPLE_LITERAL)) {
    		markerTypes.remove(ActivityMarkerType.MARKER_LOOP_LITERAL);
    	}
    	
    	super.validateMarkerSet(markerTypes);
    }

}
