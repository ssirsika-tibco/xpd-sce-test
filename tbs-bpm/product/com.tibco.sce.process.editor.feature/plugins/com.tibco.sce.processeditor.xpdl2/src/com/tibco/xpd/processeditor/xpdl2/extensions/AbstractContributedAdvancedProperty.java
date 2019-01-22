/**
 * AbstractContributedAdvancedProperty.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2008
 */
package com.tibco.xpd.processeditor.xpdl2.extensions;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.ui.views.properties.PropertyDescriptor;

/**
 * AbstractContributedAdvancedProperty
 * 
 * <p>
 * Interface that must be implementd by contributions to the
 * <code>com.tibco.xpd.processeditor.xpdl2.advancedProperties</code> extension
 * point in order to contribute properties to the process editor advanced tab.
 * </p>
 * 
 */
public abstract class AbstractContributedAdvancedProperty {

    /**
     * Create the property descriptor for display / edit of the contributed
     * property.
     * <p>
     * <b>It is important that you MUST use the given descriptorId and name when
     * creating you property descriptor.</b> If you do not then your property
     * will not be settable.
     * <p>
     * If the alternative method
     * {@link #createPropertyDescriptor(String, String, EObject, int)} returns
     * non-null then this method will not be used.
     * 
     * @param descriptorId
     *            Id of this descriptor.
     * @param displayName
     *            Property display label
     * 
     * @return A property descriptor (such as PropertyDescriptor for display
     *         only items, TextPropertyDescriptor for a string and so on)
     */
    public abstract PropertyDescriptor createPropertyDescriptor(
            String descriptorId, String displayName);

    /**
     * Create the property descriptor (label, valueLabel and editing provision).
     * <p>
     * There is one {@link AbstractContributedAdvancedProperty} per property
     * contribution but that may relate to multiple items in a sequence. In the
     * case that there may be some different handling needed specific to each
     * individual element in sequence.
     * <p>
     * If this method returns null then it is used in preference to
     * {@link #createPropertyDescriptor(String, String)}
     * 
     * @param descriptorId
     * @param displayName
     * @param input
     * @param sequenceIdx
     * 
     * @return Property descriptor.
     */
    public PropertyDescriptor createPropertyDescriptor(String descriptorId,
            String displayName, EObject input, int sequenceIdx) {
        return null;
    }

    /**
     * Return whether this property is applicable to the given input object.
     * <p>
     * This is used to filter properties in/out depending on the
     * type/current-state of selected object.
     * <p>
     * In the case of a the contributor using a sequence then the entire
     * sequence is deemed to be either applicable or not applicable. Therefore
     * you should return true if any element is set in the sequence.
     * 
     * @param input
     * @return
     */
    public abstract boolean isApplicable(EObject input);

    /**
     * The Advanced property contribution extension point can handle repeating
     * sequences of a property.
     * <p>
     * This is controlled by whether you return > 1 from this method.
     * <p>
     * If you do so then the getValue(), getSetValueCommand() and valudChanged()
     * methods will be passed a sequence index number with a value of 0 ->
     * (sequenceCount - 1).
     * 
     * @param input
     * @return Greater than 1 if a repeating sequence of the same property is
     *         required. 1 if this is a static non-sequence property or zero if
     *         this is an empty sequence.
     */
    public abstract int getSequenceCount(EObject input);

    /**
     * Get the current value of the this property for the given object.
     * <p>
     * This method will only be called if you have returned true to
     * isApplicable() for the same input object.
     * 
     * @param input
     *            The currently selected input object.
     * @param sequenceIndex
     *            If the getSequenceCount() method returns > 1 then this
     *            indicates the ZERO-based index within the repeating sequence
     *            of this property.
     * @return
     */
    public abstract Object getValue(EObject input, int sequenceIndex);

    /**
     * Get the default value (if any) for the property for the given input
     * object.
     * <p>
     * This method is used by the extension point to set a default value when...
     * <li>A change is made to the given input object (or an object parented by
     * it) OR a destination environment applicable to the contribution is
     * switched ON.</li>
     * 
     * <li>And one of the destinations in the contributions destination list is
     * enabled in the input object's context.</li>
     * 
     * <li>This contribution's isApplicable() returns true.</li>
     * 
     * <li>And this contribution's isSet() method returns false.</li>
     * <p>
     * The extension point then calls getDefaultValue() and if this returns a
     * value, uses the getSetValueCommand() to set a default value for the
     * property.
     * 
     * 
     * @param input
     * @return Default value of property or <code>null</code> if there is no
     *         default value.
     */
    public abstract Object getDefaultValue(EObject input);

    /**
     * Return the EMF command to set the value of the property on the given
     * input object.
     * <p>
     * <b>Note that this method is only called IF valueChanged() returns true on
     * comparing the current and new values.
     * 
     * @param editingDomain
     * @param input
     *            The currently selected input object.
     * @param value
     *            The new value.
     * @param sequenceIndex
     *            TODO
     * @return Command to set the value or <code>null</code> if no command is
     *         required/available.
     */
    public abstract Command getSetValueCommand(EditingDomain editingDomain,
            EObject input, Object value, int sequenceIndex);

    /**
     * Return whether the property is actually set on the given input object.
     * <p>
     * Note that this method may be called even when isApplicable() would return
     * false because this method is used to aid in cleaning up no-longer
     * applicable properties.
     * <p>
     * In the case of a the contributor using a sequence then the entire
     * sequence is deemed to be either applicable or not applicable. Therefore
     * you should return true if any element is set in the sequence.
     * 
     * @param input
     * 
     * @return true if the model representation of this property is present in
     *         the model for the given input object.
     */
    public abstract boolean isSet(EObject input);

    /**
     * Return the EMF command that will remove the property value from the
     * model.
     * <p>
     * This method is called when an operation is performed on a NamedElement
     * (user selectable element within a process package) and gives the
     * contributor the opportunity to tidy up the model if this contributed
     * property no longer applies to a particular object in it's new state.
     * </p>
     * <p>
     * The contribution's isSet() is called after an Add/Set/Remove operation is
     * performed where a NamedElement object is the 'owner' specified in the EMF
     * command.
     * <p>
     * If isSet() returns <b>true</b> then isApplicable() is called after the
     * operation is performed.
     * <p>
     * If isApplicable() returns <b>false</b> then the contributed
     * getRemoveValueCommand() is called, to remove the property value from the
     * model.
     * <p>
     * In the case of a the contributor using a sequence then the entire
     * sequence is deemed to be either applicable or not applicable. Therefore
     * when the properties are no longer applicable you should remove the entire
     * sequence (that is why this method is not given a sequence index).
     * 
     * @param editingDomain
     * @param input
     * 
     * @return Command to remove the property model value or <code>null</code>
     *         if no command is required.
     */
    public abstract Command getRemoveValueCommand(EditingDomain editingDomain,
            EObject input);

    /**
     * Advanced Proeprties Cell Editors have a habit of performing multiple 'set
     * values' which would nominally lead to multiple repeats of
     * getSetValueCommand() in this class.
     * <p>
     * The advanced properties extension point will call this method before
     * calling getSetValueCommand() and only get and execute the command if the
     * value has changed.
     * <p>
     * This default implementation should fit moist purposes.
     * 
     * @param input
     * @param newValue
     * @param sequenceIndex
     *            TODO
     * @return
     */
    public boolean valueChanged(EObject input, Object newValue,
            int sequenceIndex) {
        Object curValue = getValue(input, sequenceIndex);

        if (curValue == null) {
            if (newValue == null) {
                return false;
            } else {
                return true;
            }
        }

        return !curValue.equals(newValue);
    }

}
