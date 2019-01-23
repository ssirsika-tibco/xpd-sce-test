/**
 * AbstractAdvancedModelFeatureProperty.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2008
 */
package com.tibco.xpd.processeditor.xpdl2.extensions;

import java.util.Iterator;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

/**
 * AbstractAdvancedModelFeatureProperty
 * <p>
 * Abstract class allowing simple implementation of object advanced properties.
 * </p>
 * <p>
 * This abstract class allows the sub-class to specify...
 * <li>An EMF model container element that contains the model feature for the
 * property.</li>
 * <li>The EMF structural feature for the model feature for the property value.</li>
 * <li>A method of getting or creating the property model feature's container
 * as part of an EMF command to set the property</li>
 * <li>A label to set on the EMF command created to set the property.</li>
 * </p>
 * <p>
 * Using these subclass methods this class is able to support the
 * AbstractContributedAdvancedProperty methods to get and set model value for
 * the property etc.
 * </p>
 * <br/>
 * <p>
 * <b>NOTE:</b> This class also handles clearing up of the stored EMF model
 * value for the property when (a) The sub-class says it is no longer applicable
 * to the given input object and (b) it is currently set in the model.
 * </p>
 * 
 */
public abstract class AbstractAdvancedModelFeatureProperty extends
        AbstractContributedAdvancedProperty {

    /**
     * Return the EMF structural feature that is used to store the value of the
     * property.
     * 
     * @return
     */
    protected abstract EStructuralFeature getPropertyModelFeature();

    /**
     * Return the label for the set property command.
     * 
     * @return
     */
    protected abstract String getSetPropertyCommandLabel();

    /**
     * Return the EMF EObject parent container element for the EMF feature that
     * stores the property value.
     * 
     * @param input
     * @return
     */
    protected abstract EObject getPropertyModelContainer(EObject input);

    /**
     * Return the EMF EObject parent container element for the EMF feature that
     * stores the property value - <b>append a command</b> to add it to the
     * input object if necessary.
     * 
     * @param input
     * @param editingDomain
     * @param cmd
     * @return
     */
    protected abstract EObject getOrCreatePropertyContainer(EObject input,
            EditingDomain editingDomain, CompoundCommand cmd);

    /**
     * This method is called when the LAST content of the property's container
     * is removed (when no longer applicable to given input).
     * <p>
     * The sub-class can therefore choose to remove the WHOLE proeprty container
     * element in order to properly tidy up the model when it's properties are
     * no longer appropriate to the given input.
     * </p>
     * 
     * @param editingDomain
     * @param input
     * @return Command to remove property's model container element or null if
     *         just the property should be removed.
     */
    protected abstract Command getPropertyContainerCleanupCommand(
            EditingDomain editingDomain, EObject input);

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractContributedAdvancedProperty#getRemoveValueCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject)
     */
    @Override
    public Command getRemoveValueCommand(EditingDomain editingDomain,
            EObject input) {
        //
        // When requested by advanced properties extension point remove the
        // model value for the property (this happens when the property
        // contribution says the property is set but no longer applicable to the
        // given input object.
        //

        if (input instanceof EObject) {

            EObject attribContainer =
                    getPropertyModelContainer((EObject) input);

            if (attribContainer != null) {
                if (attribContainer.eIsSet(getPropertyModelFeature())) {

                    int numContent = countEObjectContents(attribContainer);
                    if (numContent == 1) {
                        //
                        // We are about to delete the last property from the
                        // model container.
                        //
                        // Give the sub-class the opportunity to delete the
                        // whole container.
                        Command remCmd =
                                getPropertyContainerCleanupCommand(editingDomain,
                                        input);
                        if (remCmd != null) {
                            CompoundCommand cmd = new CompoundCommand();
                            cmd.append(remCmd);
                            cmd.setLabel(getSetPropertyCommandLabel());
                            
                            return cmd;
                        }
                    }

                    // 
                    // There are other attributes/elements in container OR the
                    // subclass never wants to remove the property container
                    // from model. So just unset the property model value.
                    CompoundCommand cmd = new CompoundCommand();
                    cmd.append(SetCommand.create(editingDomain,
                            attribContainer,
                            getPropertyModelFeature(),
                            SetCommand.UNSET_VALUE));
                    cmd.setLabel(getSetPropertyCommandLabel());
                    return cmd;
                }
            }
        }
        return null;
    }

    /**
     * Get the number of attributes and sub-elements in the given eobject.
     * 
     * @param container
     * @return
     */
    public int countEObjectContents(EObject container) {
        TreeIterator<EObject> content =
                EcoreUtil.getAllContents(container, false);
        int numContent = 0;

        for (Iterator iterator = content; iterator.hasNext();) {
            EObject eo = (EObject) iterator.next();

            numContent++;
        }

        EList<EAttribute> attrs = container.eClass().getEAllAttributes();
        for (Iterator iterator = attrs.iterator(); iterator.hasNext();) {
            EAttribute attr = (EAttribute) iterator.next();

            if (container.eIsSet(attr)) {
                numContent++;
            }
        }
        return numContent;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractContributedAdvancedProperty#getSetValueCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, java.lang.Object)
     */
    @Override
    public Command getSetValueCommand(EditingDomain editingDomain,
            EObject input, Object value, int sequenceIndex) {

        CompoundCommand cmd = new CompoundCommand();
        cmd.setLabel(getSetPropertyCommandLabel());

        EObject attribContainer =
                getOrCreatePropertyContainer((EObject) input,
                        editingDomain,
                        cmd);

        cmd.append(SetCommand.create(editingDomain,
                attribContainer,
                getPropertyModelFeature(),
                value));

        return cmd;
    }

    /**
     * <b>NOTE: You should NOT override this method. The other method
     * implementations in this class cannot handle sequences of properties.
     */
    @Override
    public int getSequenceCount(EObject input) {
        return 1;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractContributedAdvancedProperty#getValue(org.eclipse.emf.ecore.EObject)
     */
    @Override
    public Object getValue(EObject input, int sequenceIndex) {
        Object value = null;

        EObject attribContainer = getPropertyModelContainer(input);

        if (attribContainer != null) {
            if (attribContainer.eIsSet(getPropertyModelFeature())) {
                value = attribContainer.eGet(getPropertyModelFeature());
            }
        }

        return value;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractContributedAdvancedProperty#isSet(org.eclipse.emf.ecore.EObject)
     */
    @Override
    public boolean isSet(EObject input) {
        EObject attribContainer = getPropertyModelContainer(input);
        if (attribContainer != null) {
            if (attribContainer.eIsSet(getPropertyModelFeature())) {
                return true;
            }
        }

        return false;
    }

}
