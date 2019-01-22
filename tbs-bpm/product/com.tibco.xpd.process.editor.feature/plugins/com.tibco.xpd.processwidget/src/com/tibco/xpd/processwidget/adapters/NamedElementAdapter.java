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

package com.tibco.xpd.processwidget.adapters;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;

/**
 * Adapter for object that has Id and Name
 * 
 * @author wzurek
 */
public interface NamedElementAdapter extends BaseProcessAdapter {

    /**
     * @return unique object name, not displayed to the user. The ID is
     *         unsettable for the widget
     */
    String getId();

    /**
     * @return human readable name (label)
     */
    String getName();

    /**
     * Command to change Name
     * 
     * @param editingDomain
     * @param newName
     * @return
     */
    Command getSetNameCommand(EditingDomain editingDomain, String newName);

    /*
     * @return the token name
     */
    String getTokenName();
}
