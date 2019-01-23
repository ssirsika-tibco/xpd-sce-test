/**
 * GraphicalNodeColorAdapter.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */
package com.tibco.xpd.processwidget.adapters;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;

/**
 * GraphicalNodeColorAdapter
 * 
 * Interface to be supported by anything that can have line/fill color set on
 * it.
 * 
 */
public interface GraphicalColorAdapter {

    String getFillColor();

    Command getSetFillColorCommand(EditingDomain editingDomain,
            String colorString);

    String getLineColor();

    Command getSetLineColorCommand(EditingDomain editingDomain,
            String colorString);

}
