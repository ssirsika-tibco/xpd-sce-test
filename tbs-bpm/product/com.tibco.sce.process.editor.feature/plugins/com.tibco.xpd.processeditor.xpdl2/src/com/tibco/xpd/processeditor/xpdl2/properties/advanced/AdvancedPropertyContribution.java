/**
 * PropertyContribution.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2008
 */
package com.tibco.xpd.processeditor.xpdl2.properties.advanced;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import com.tibco.xpd.processeditor.xpdl2.extensions.AbstractContributedAdvancedProperty;

/**
 * PropertyContribution
 * <p>
 * Class representing a property contributed via the
 * <code>com.tibco.xpd.processeditor.xpdl2.advancedProperties</code> extension
 * point.
 * </p>
 * <p>
 * This handles most of the requirements for handling get/set value on property
 * and the PropertyDescriptor handling for Xpdl2PropertySource.
 * </p>
 * <p>
 * Some of this is delegated to the extension point contributor.
 * </p>
 * 
 */
class AdvancedPropertyContribution implements IAdvancedPropertyContribution {
    /** The id of this property contribution */
    private String id;

    /** The display label name of property. */
    private String name;

    /** The original property contribution from extension point contribution. */
    private AbstractContributedAdvancedProperty extPointContribution;

    /** The property descriptor created by the property contributor. */
    private PropertyDescriptor descriptor = null;

    /**
     * The sequenceIndex number of the property (when the contributed property
     * returns > 1 from getSequenceCount()
     */
    private int sequenceIndex = 0;

    public AdvancedPropertyContribution(String id, String name,
            AbstractContributedAdvancedProperty extPointContribution,
            int sequenceIndex) {
        super();
        this.id = id;
        this.name = name;
        this.extPointContribution = extPointContribution;
        this.sequenceIndex = sequenceIndex;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public PropertyDescriptor getPropertyDescriptor(EObject input) {
        if (descriptor == null) {
            descriptor =
                    extPointContribution.createPropertyDescriptor(getId(),
                            getName(),
                            input,
                            sequenceIndex);

            if (descriptor == null) {
                descriptor =
                        extPointContribution.createPropertyDescriptor(getId(),
                                getName());
            }
        }
        return descriptor;
    };

    public boolean isApplicable(EObject input) {
        return extPointContribution.isApplicable(input);
    }

    public Object getValue(EObject input) {
        return extPointContribution.getValue(input, sequenceIndex);
    }

    public Object getDefaultValue(EObject input) {
        return extPointContribution.getDefaultValue(input);
    }

    public Command getSetValueCommand(EditingDomain editingDomain,
            EObject input, Object value) {
        if (extPointContribution.valueChanged(input, value, sequenceIndex)) {
            return extPointContribution.getSetValueCommand(editingDomain,
                    input,
                    value,
                    sequenceIndex);
        }
        return null;
    }

    public boolean isSet(EObject input) {
        return extPointContribution.isSet(input);
    }

    public Command getRemoveValueCommand(EditingDomain editingDomain,
            EObject input) {
        return extPointContribution.getRemoveValueCommand(editingDomain, input);
    }

    /**
     * @return the sequenceIndex
     */
    public int getSequenceIndex() {
        return sequenceIndex;
    }

    /**
     * @return the extPointContribution
     */
    public AbstractContributedAdvancedProperty getExtPointContribution() {
        return extPointContribution;
    }
}
