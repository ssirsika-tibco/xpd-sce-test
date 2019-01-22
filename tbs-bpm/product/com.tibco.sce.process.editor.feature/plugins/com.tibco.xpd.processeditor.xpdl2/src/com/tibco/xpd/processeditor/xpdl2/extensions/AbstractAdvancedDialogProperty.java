/**
 * AbstractAdvancedDialogProperty.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2008
 */
package com.tibco.xpd.processeditor.xpdl2.extensions;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import com.tibco.xpd.processeditor.xpdl2.extensions.AbstractAdvancedModelFeatureProperty;
import com.tibco.xpd.processeditor.xpdl2.properties.advanced.DialogPropertyDescriptor;
import com.tibco.xpd.processeditor.xpdl2.properties.advanced.DialogPropertyDescriptor.PropertyValueAndContext;

/**
 * AbstractAdvancedDialogProperty
 * 
 */
public abstract class AbstractAdvancedDialogProperty extends
        AbstractAdvancedModelFeatureProperty {

    /**
     * Open the dialog when the user presses [...] in the dialog cell editor.
     * 
     * @param cellEditorWindow
     * @param propertyValueAndContext
     * 
     * @return new PropertyValueAndContext with the new value set.
     */
    protected abstract DialogPropertyDescriptor.PropertyValueAndContext openDialogBox(
            Control cellEditorWindow,
            DialogPropertyDescriptor.PropertyValueAndContext propertyValueAndContext);

    /**
     * @return true if a [Clear] button is required alongside the [...] open
     *         dialog button.
     */
    protected abstract boolean isClearEnabled();

    @Override
    public PropertyDescriptor createPropertyDescriptor(String descriptorId,
            String displayName) {
        DialogPropertyDescriptor desc =
                new DialogPropertyDescriptor(descriptorId, displayName,
                        isClearEnabled()) {

                    @Override
                    protected PropertyValueAndContext openDialogBox(
                            Control cellEditorWindow,
                            PropertyValueAndContext propertyValueAndContext) {

                        return AbstractAdvancedDialogProperty.this
                                .openDialogBox(cellEditorWindow,
                                        propertyValueAndContext);
                    }

                };

        return desc;
    }

    @Override
    public Object getValue(EObject input, int sequenceIndex) {
        //
        // For DialogPropertyDescriptor's, the actual property value MUST be
        // wrapped up in a PropertyValueAndContext object.
        return new DialogPropertyDescriptor.PropertyValueAndContext(input,
                super.getValue(input, sequenceIndex));
    }

    @Override
    public Command getSetValueCommand(EditingDomain editingDomain,
            EObject input, Object value, int sequenceIndex) {
        //
        // For DialogPropertyDescriptor's, the property value is wrapped up in a
        // PropertyValueAndContext object.
        if (value instanceof PropertyValueAndContext) {
            if (((PropertyValueAndContext)value).getValue() == null) {
                return super.getRemoveValueCommand(editingDomain, input);
            } else {
                return super.getSetValueCommand(editingDomain,
                    input,
                    ((PropertyValueAndContext) value).getValue(),
                    sequenceIndex);
            }
        }

        return UnexecutableCommand.INSTANCE;
    }

    @Override
    public boolean valueChanged(EObject input, Object newValue,
            int sequenceIndex) {
        //
        // For DialogPropertyDescriptor's, the property value is wrapped up in a
        // PropertyValueAndContext object.
        if (newValue instanceof PropertyValueAndContext) {
            Object realNewValue =
                    ((PropertyValueAndContext) newValue).getValue();

            Object curValue = super.getValue(input, sequenceIndex);

            if (curValue == null) {
                if (newValue == null) {
                    return false;
                } else {
                    return true;
                }
            }

            return !curValue.equals(realNewValue);

        }
        return super.valueChanged(input, newValue, sequenceIndex);
    }

}
