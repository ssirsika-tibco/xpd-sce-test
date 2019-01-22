/**
 * DropDownPropertyDescriptor.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2008
 */
package com.tibco.xpd.processeditor.xpdl2.properties.advanced;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;

/**
 * DropDownPropertyDescriptor
 * <p>
 * Drop-down list (combo) advanced property tab property descriptor.
 * </p>
 * <p>
 * Normally the value passed into and out of the descriptor is an integer into
 * the possible list of items. This isn't very nice as it leaves the property
 * source sometimes having to deal with integers and sometimes with the actual
 * model element value for the property. </P
 * <p>
 * This class simplifies using drop down list properties by accepting an array
 * of labels and corresponding array of real value (model elements) that are the
 * things that will always be passed in / out of this descriptor as values.
 * 
 */
public class DropDownPropertyDescriptor extends ComboBoxPropertyDescriptor {

    private String[] labels;

    private Object[] values;

    /**
     * * Drop-down list (combo) advanced property tab property descriptor.
     * </p>
     * <p>
     * Normally the value passed into and out of the descriptor is an integer
     * into the possible list of items. This isn't very nice as it leaves the
     * property source sometimes having to deal with integers and sometimes with
     * the actual model element value for the property. </P
     * <p>
     * This class simplifies using drop down list properties by accepting an
     * array of labels and corresponding array of real value (model elements)
     * that are the things that will always be passed in / out of this
     * descriptor as values.
     * 
     * @param id
     * @param displayName
     * @param labels
     * @param values
     */
    public DropDownPropertyDescriptor(Object id, String displayName,
            String[] labels, Object[] values) {
        super(id, displayName, labels);

        if (labels.length != values.length) {
            throw new RuntimeException(
                    "Label and value array must be same length."); //$NON-NLS-1$
        }

        this.labels = labels;
        this.values = values;

    }

    @Override
    public ILabelProvider getLabelProvider() {
        return new LabelProvider() {
            @Override
            public String getText(Object element) {
                for (int i = 0; i < values.length; i++) {
                    if (values[i].equals(element)) {
                        return labels[i];
                    }
                }
                return ""; //$NON-NLS-1$
            }
        };
    }

    public CellEditor createPropertyEditor(Composite parent) {
        //
        // Create a combobox cell editor that coerces the std comb's value of
        // 'index into list' into a Boolean.
        CellEditor editor =
                new ComboBoxCellEditor(parent, labels, SWT.READ_ONLY) {
                    @Override
                    protected void doSetValue(Object value) {
                        //
                        // ON set value convert from owner's values to base
                        // combo's expected item index.
                        for (int i = 0; i < values.length; i++) {
                            if (values[i].equals(value)) {
                                super.doSetValue(new Integer(i));
                                break;
                            }
                        }
                        return;
                    }

                    @Override
                    protected Object doGetValue() {
                        //
                        // ON get value convert from combo's Integer value to
                        // owner's values
                        Object o = super.doGetValue();
                        if (o instanceof Integer) {
                            int idx = ((Integer)o).intValue();
                            
                            if (idx >= 0 && idx < values.length) {
                                return values[idx];
                            }
                        }
                        return null;
                    }
                };

        if (getValidator() != null) {
            editor.setValidator(getValidator());
        }
        return editor;
    }

}
