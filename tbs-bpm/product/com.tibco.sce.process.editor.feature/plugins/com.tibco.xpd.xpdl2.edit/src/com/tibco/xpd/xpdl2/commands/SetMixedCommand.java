/* 
 ** 
 **  MODULE:             $RCSfile: SetMixedCommand.java $ 
 **                      $Revision: 1.3 $ 
 **                      $Date: 2005/03/08 13:05:33Z $ 
 ** 
 ** DESCRIPTION    :           
 **                                              
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2004 Staffware plc, All Rights Reserved. 
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: SetMixedCommand.java $ 
 **    Revision 1.3  2005/03/08 13:05:33Z  wzurek 
 **    Revision 1.2  2005/01/13 18:00:38Z  WojciechZ 
 **    work in progress 
 **    Revision 1.1  2004/11/23 16:49:11Z  WojciechZ 
 **    Initial revision 
 **    Revision 1.0  29-Oct-2004  WojciechZ 
 **    Initial revision 
 ** 
 */
package com.tibco.xpd.xpdl2.commands;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap.Entry;

/**
 * Command to easy manipulate mixed element's content
 * 
 * @author WojciechZ
 */
public class SetMixedCommand extends AbstractCommand {

    private final FeatureMap featureMap;
    private final Object value;
    private final int index;
    private FeatureMap.Entry oldValue;
    private final EStructuralFeature structuralFeature;

    /**
     * @param featureMap
     * @param structuralFeature
     * @param index
     * @param value
     */
    public SetMixedCommand(FeatureMap featureMap,
            EStructuralFeature structuralFeature, int index, Object value) {

        this.featureMap = featureMap;
        this.structuralFeature = structuralFeature;
        this.index = index;
        this.value = value;
        isExecutable = true;
    }
    /**
     * @see org.eclipse.emf.common.command.Command#execute()
     */
    public void execute() {
        oldValue = null;
        if (index >= 0 && index < featureMap.size()) {
            oldValue = (Entry) featureMap.get(index);
        }
        if (value == null) {
            featureMap.unset(structuralFeature);
        } else {
            if (oldValue == null) {
                featureMap.add(index, structuralFeature, value);
            } else {
                featureMap.setValue(index, value);
            }
        }
    }

    /**
     * @see org.eclipse.emf.common.command.Command#redo()
     */
    public void redo() {
        execute();
    }

    /**
     * @see org.eclipse.emf.common.command.AbstractCommand#undo()
     */
    public void undo() {
        if (oldValue == null) {
            featureMap.unset(structuralFeature);
        } else {
            featureMap.setValue(index, oldValue.getValue());
        }
    }

    /**
     * @see org.eclipse.emf.common.command.AbstractCommand#canExecute()
     */
    public boolean canExecute() {
        return true;
    }
}