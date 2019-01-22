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

package com.tibco.xpd.processwidget.actions;

import org.eclipse.gef.Disposable;

/**
 * Action that needs to be disposed when no longer needed
 * 
 * @author wzurek
 * @deprecated Use {@link Disposable} instead.
 */
@Deprecated
public interface DisposableAction extends Disposable {

}
