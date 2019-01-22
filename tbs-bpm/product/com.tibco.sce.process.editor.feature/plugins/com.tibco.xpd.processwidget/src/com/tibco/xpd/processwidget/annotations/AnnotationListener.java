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

package com.tibco.xpd.processwidget.annotations;

import org.eclipse.gef.EditPart;

/**
 * @author wzurek
 */
public interface AnnotationListener {
    void createAnnotations();

    void removeAnnotations();
    
    Object getModelObject();
    
    EditPart getHostEditPart();
}
