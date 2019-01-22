/**
 * Xpdl2PropertySource.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2008
 */
package com.tibco.xpd.processeditor.xpdl2.properties.advanced;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import com.tibco.xpd.processeditor.xpdl2.extensions.AbstractContributedAdvancedProperty;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Xpdl2PropertySource
 * <p>
 * This is the Advanced Properties Tab property source for Xpdl2.
 * </p>
 * <p>
 * It represents a set of properties within a given category (top level or
 * sub-category).
 * </p>
 * <p>
 * Although the input member is of type EObject, currently we only use this
 * property source for NamedElement's as selectable items in proj explorer /
 * prodcess editor are generally these. However, if we find a selection that
 * isn't it will still work provided that an extra adapter factory is added for
 * the given selection object's type.
 * </p>
 * <p>
 * When a selection is made then any AdvancedPropertySection contributed to a
 * property tab by the selection provider will use this source provider for
 * NamedElement's. We then return the properties contributed by selected
 * destination environments via the
 * com.tibco.xpd.processeditor.xpdl2.advancedProperties extension point that are
 * applicable to the current selection.
 * </p>
 */
class Xpdl2AdvancedPropertySource implements IPropertySource {
    /** The current selected input object */
    private EObject input;

    /** The working copy for the input */
    private WorkingCopy workingCopy;

    /**
     * Map of property descriptor id's to property contributions.
     * <p>
     * This is required to ensure that property descriptors are cached because
     * on refresh the PropertyViewer will merge it's current properties with the
     * latest returned. Any category type property descriptor entries that are
     * 'new' will be created collapsed. So returning the same property
     * descriptor for the same categories should ensure (to an extent) that the
     * category is not collapsed on every refresh or selection of a different
     * (but same type) object.
     */
    private Map<Object, IAdvancedPropertyContribution> contributions;

    /**
     * Create a property source representing the given list of property
     * contributions (which may be properties OR sub-categories).
     * <p>
     * Each sub-category will also have it's own Xpdl2PropertySource.
     * 
     * @param input
     * @param contributions
     */
    public Xpdl2AdvancedPropertySource(EObject input,
            Collection<IAdvancedPropertyContribution> contributions) {

        super();

        this.input = input;

        workingCopy = WorkingCopyUtil.getWorkingCopyFor(input);

        if (workingCopy == null) {
            this.contributions = Collections.EMPTY_MAP;
            // throw new IllegalArgumentException(
            // "Property Source Input is not in a working copy: " + input);
            // //$NON-NLS-1$
        } else {

            //
            // Cache a map of property descriptor id's to the original property
            // contribution. Then when we are asked to get/set value for a given
            // property id we can associate it back to the contribution and
            // delegate
            // the get/set value to it.
            this.contributions =
                    new HashMap<Object, IAdvancedPropertyContribution>();

            for (IAdvancedPropertyContribution contr : contributions) {
                if (contr instanceof AdvancedPropertyCategoryContribution) {
                    this.contributions.put(contr.getPropertyDescriptor(input)
                            .getId(), contr);

                } else if (contr instanceof AdvancedPropertyContribution) {
                    //
                    // Check whether the contribution is a sequence
                    // and act appropriately...
                    //
                    AdvancedPropertyContribution propContr =
                            (AdvancedPropertyContribution) contr;

                    AbstractContributedAdvancedProperty extPointContribution =
                            propContr.getExtPointContribution();

                    int sequenceCount =
                            extPointContribution.getSequenceCount(input);

                    if (sequenceCount == 1) {
                        /*
                         * SIA-1 Can't afford to use the same
                         * AdvancedPropertyContribution because the descriptor
                         * maybe specific to a given model element.
                         * 
                         * So we will now use the contributions as the source to
                         * create new copies each time the selection changes
                         * (just like we always did for sequence elements).
                         */
                        AdvancedPropertyContribution seqContr =
                                new AdvancedPropertyContribution(
                                        propContr.getId(), propContr.getName(),
                                        extPointContribution, 0);

                        PropertyDescriptor propertyDescriptor =
                                seqContr.getPropertyDescriptor(input);
                        propertyDescriptor.setCategory(contr
                                .getPropertyDescriptor(input).getCategory());
                        this.contributions.put(propertyDescriptor.getId(),
                                seqContr);

                    } else if (sequenceCount > 1) {
                        // If the count is > 1 then we need to
                        // create and append further contributions
                        // for the number of elements in sequence.

                        // The extension point helper always caches
                        // a contribution so we already have the
                        // first element in sequence.

                        // Build name so that sort order is always correct by
                        // sequence number.
                        int numDigits = 0;
                        int tmpCnt = sequenceCount;
                        while (tmpCnt > 0) {
                            numDigits++;
                            tmpCnt = tmpCnt / 10;
                        }

                        String formatStr =
                                String.format("%%s [%%0%dd]", numDigits); //$NON-NLS-1$

                        // Then add the rest up to count.
                        for (int sequenceIndex = 0; sequenceIndex < sequenceCount; sequenceIndex++) {
                            AdvancedPropertyContribution seqContr =
                                    new AdvancedPropertyContribution(
                                            sequenceIndex
                                                    + ":" + propContr.getId(), //$NON-NLS-1$
                                            String.format(formatStr,
                                                    propContr.getName(),
                                                    sequenceIndex + 1),
                                            extPointContribution, sequenceIndex);

                            PropertyDescriptor propertyDescriptor =
                                    seqContr.getPropertyDescriptor(input);
                            propertyDescriptor
                                    .setCategory(contr
                                            .getPropertyDescriptor(input)
                                            .getCategory());
                            this.contributions.put(propertyDescriptor.getId(),
                                    seqContr);

                        }

                    } else {
                        // If the count is zero then it's an empty
                        // sequence so don't add the default 'first
                        // sequence element contribution that was
                        // added by the helper'.
                    }

                }

            }
        }
    }

    @Override
    public Object getEditableValue() {
        return input;
    }

    @Override
    public IPropertyDescriptor[] getPropertyDescriptors() {
        List<IPropertyDescriptor> descriptors =
                new ArrayList<IPropertyDescriptor>();

        for (IAdvancedPropertyContribution contr : contributions.values()) {
            descriptors.add(contr.getPropertyDescriptor(input));
        }

        return descriptors.toArray(new IPropertyDescriptor[descriptors.size()]);
    }

    @Override
    public Object getPropertyValue(Object id) {
        Object ret = null;
        // Get the original contributor of the property descriptor and delegate
        // the get value to it.
        IAdvancedPropertyContribution contr = contributions.get(id);
        if (contr != null) {
            ret = contr.getValue(input);
        }
        return ret != null ? ret : ""; //$NON-NLS-1$
    }

    @Override
    public boolean isPropertySet(Object id) {
        // Get the original contributor of the property descriptor and delegate
        // to it.
        IAdvancedPropertyContribution contr = contributions.get(id);
        if (contr instanceof AdvancedPropertyContribution) {
            AdvancedPropertyContribution propContr =
                    (AdvancedPropertyContribution) contr;

            // Check whether the contributor says that the current value has
            // changed from what it thinks the default valuer should be.
            if (propContr.getExtPointContribution().valueChanged(input,
                    propContr.getDefaultValue(input),
                    propContr.getSequenceIndex())) {
                return true;
            }

        }

        return false;
    }

    @Override
    public void resetPropertyValue(Object id) {
        // Get the original contributor of the property descriptor and delegate
        // to it.
        IAdvancedPropertyContribution contr = contributions.get(id);
        if (contr instanceof AdvancedPropertyContribution) {
            AdvancedPropertyContribution propContr =
                    (AdvancedPropertyContribution) contr;

            // Set the value back to it's original value -
            Object value = propContr.getDefaultValue(input);

            // Don't do anything unless the value is actually changing.
            if (propContr.getExtPointContribution().valueChanged(input,
                    value,
                    propContr.getSequenceIndex())) {

                Command cmd = null;

                // if the default value is null then remove it
                if (value == null) {
                    cmd =
                            propContr.getRemoveValueCommand(workingCopy
                                    .getEditingDomain(), input);
                } else {
                    // Set it back to it's original value.
                    cmd =
                            ((AdvancedPropertyContribution) contr)
                                    .getSetValueCommand(workingCopy
                                            .getEditingDomain(), input, value);
                }

                if (cmd != null && cmd.canExecute()) {
                    workingCopy.getEditingDomain().getCommandStack()
                            .execute(cmd);
                }

            }

        }

    }

    @Override
    public void setPropertyValue(Object id, Object value) {
        // Get the original contributor of the property descriptor and delegate
        // the get value to it.
        IAdvancedPropertyContribution contr = contributions.get(id);
        if (contr instanceof AdvancedPropertyContribution) {
            Object currValue = contr.getValue(input);
            if (!areEqual(value, currValue)) {
                Command cmd =
                        ((AdvancedPropertyContribution) contr)
                                .getSetValueCommand(workingCopy
                                        .getEditingDomain(), input, value);
                if (cmd != null && cmd.canExecute()) {
                    workingCopy.getEditingDomain().getCommandStack()
                            .execute(cmd);
                }
            }
        }
        return;
    }

    private boolean areEqual(Object newValue, Object currValue) {
        return (newValue == null) ? currValue == null : newValue
                .equals(currValue);
    }

}