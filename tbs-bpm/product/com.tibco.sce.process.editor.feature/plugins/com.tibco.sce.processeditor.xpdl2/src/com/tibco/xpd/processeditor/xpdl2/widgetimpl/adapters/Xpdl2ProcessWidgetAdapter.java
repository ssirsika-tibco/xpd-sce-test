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

package com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters;

import org.eclipse.emf.common.notify.Adapter;

import com.tibco.xpd.processeditor.xpdl2.widgetimpl.Xpdl2ProcessWidgetAdapterFactory;

/**
 * @author wzurek
 */
public interface Xpdl2ProcessWidgetAdapter extends Adapter {

    void setAdapterFactory(Xpdl2ProcessWidgetAdapterFactory factory);

}
