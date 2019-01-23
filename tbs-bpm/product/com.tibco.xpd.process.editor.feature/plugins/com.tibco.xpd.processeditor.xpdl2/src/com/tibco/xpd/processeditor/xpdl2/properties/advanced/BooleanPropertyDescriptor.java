/**
 * BooleanPropertyDescriptor.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2008
 */
package com.tibco.xpd.processeditor.xpdl2.properties.advanced;

import com.tibco.xpd.processeditor.xpdl2.internal.Messages;

/**
 * BooleanPropertyDescriptor
 * 
 * Boolean false/true combo box PropertyDescriptor (and cell editor).
 * <p>
 * The values passed into and out of the descriptor should be a Boolean object
 * or null (for unspecified).
 */
public class BooleanPropertyDescriptor extends DropDownPropertyDescriptor {

    public BooleanPropertyDescriptor(Object id, String displayName) {
        super(id, displayName, new String[] {
                Messages.BooleanPropertyDescriptor_false_label,
                Messages.BooleanPropertyDescriptor_true_label }, new Object[] {
                Boolean.FALSE, Boolean.TRUE });
    }

}
