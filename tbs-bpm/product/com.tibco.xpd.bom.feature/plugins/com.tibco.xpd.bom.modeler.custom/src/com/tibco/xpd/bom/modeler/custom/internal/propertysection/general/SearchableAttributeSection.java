package com.tibco.xpd.bom.modeler.custom.internal.propertysection.general;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager;
import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager.StereotypeKind;
import com.tibco.xpd.bom.modeler.custom.internal.Messages;
import com.tibco.xpd.bom.modeler.custom.internal.propertysection.AbstractGeneralSection;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * Handles the addition of the search-able tick box for attributes on Case and
 * Global classes
 * 
 */
public class SearchableAttributeSection extends AbstractGeneralSection {

    private Button searchCheck = null;

    @Override
    protected boolean shouldDisplay(EObject eo) {

        // Only display the search-able data if dealing with a Global Data
        // related class
        if (eo instanceof Property) {
            Property prop = (Property) eo;
            // Hide for caseId or caseState properties.
            if (BOMGlobalDataUtils.isCID(prop)
                    || BOMGlobalDataUtils.isCaseState(prop)) {
                return false;
            }
            // Do not want to display search-able for properties (i.e.
            // multiplicity) on associations
            if (prop.getAssociation() == null) {
                Class class_ = prop.getClass_();
                if ((class_ != null)
                        && (BOMGlobalDataUtils.isCaseClass(class_)
                                || BOMGlobalDataUtils.isGlobalClass(class_))) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected Command doGetCommand(Object obj) {
        // Global data : Search-able tick-box attribute for attribute
        if (obj != searchCheck) {
            return null;
        }

        final Property prop = getSemanticInput();
        if (prop == null) {
            return null;
        }

        // Get Stereotype for Search-able
        boolean existingValue = false;
        final Stereotype stereotype =
                GlobalDataProfileManager.getInstance()
                        .getStereotype(StereotypeKind.SEARCHABLE);
        // return if Stereotype is not found
        if (stereotype == null) {
            return null;
        }

        // Check if the stereotype is already applied
        Stereotype appliedStereo =
                prop.getAppliedStereotype(stereotype.getQualifiedName());
        if (appliedStereo != null) {
            // Get the existing value, only want to do things on a change
            Object value =
                    prop.getValue(stereotype,
                            BOMResourcesPlugin.ModelGlobalDataProfile_attribute_Searchable);
            // Existing value
            existingValue =
                    (value instanceof Boolean) ? ((Boolean) value)
                            .booleanValue() : false;
        }

        // Check if the value has changed
        if ((searchCheck != null)
                && (searchCheck.getSelection() != existingValue)) {
            return new RecordingCommand(
                    (TransactionalEditingDomain) getEditingDomain()) {
                @Override
                protected void doExecute() {
                    // If ticked then enable the stereotype
                    if (searchCheck.getSelection()) {
                        prop.applyStereotype(stereotype);
                        prop.setValue(stereotype,
                                BOMResourcesPlugin.ModelGlobalDataProfile_attribute_Searchable,
                                true);
                    } else {
                        // Remove the stereotype if not enabled
                        prop.unapplyStereotype(stereotype);
                    }
                }
            };
        }
        return null;
    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = (Composite) super.doCreateControls(parent, toolkit);

        createLabel(root, toolkit, Messages.Searchable_label);
        searchCheck = toolkit.createButton(root, "", SWT.CHECK); //$NON-NLS-1$
        searchCheck.setToolTipText(Messages.Searchable_tooltip);
        setLayoutData(searchCheck);
        manageControl(searchCheck);
        return root;
    }

    @Override
    protected void doRefresh() {
        final Property prop = getSemanticInput();
        if (prop == null) {
            return;
        }

        // Make sure the tick box is initialised before we do anything
        if (searchCheck != null && !searchCheck.isDisposed()) {
            // If this is a case Identifier or a case state, then it should be
            // set by default and not edit-able
            if (GlobalDataProfileManager.getInstance().isCID(prop)
                    || GlobalDataProfileManager.getInstance()
                            .isAutoCaseIdentifier(prop)
                    || GlobalDataProfileManager.getInstance()
                            .isCompositeCaseIdentifier(prop)
                    || GlobalDataProfileManager.getInstance().isCaseState(prop)) {
                searchCheck.setSelection(true);
                searchCheck.setEnabled(false);
                return;
            }

            final Stereotype stereo =
                    GlobalDataProfileManager.getInstance()
                            .getStereotype(StereotypeKind.SEARCHABLE);
            // Make sure it is enabled
            searchCheck.setEnabled(true);
            if (stereo != null && prop.getAppliedStereotypes().contains(stereo)) {
                Object value =
                        prop.getValue(stereo,
                                BOMResourcesPlugin.ModelGlobalDataProfile_attribute_Searchable);
                if (value instanceof Boolean) {
                    searchCheck.setSelection((Boolean) value);
                }
            } else {
                // Reset search-able to false
                searchCheck.setSelection(false);
            }

            // If it is not searchable make sure it is not enabled, and is
            // unticked
            if (!isSearchSupported(prop)) {
                searchCheck.setSelection(false);
                searchCheck.setEnabled(false);
            }
        }
    }

    /**
     * Retrieves the property
     * 
     * @return
     */
    private Property getSemanticInput() {
        EObject input = getInput();
        if (input instanceof Property) {
            return (Property) input;
        }
        return null;
    }

    /**
     * Check if searching is supported for this property
     * 
     * @param prop
     *            Property to check
     * @return
     */
    private boolean isSearchSupported(Property prop) {
        // Check to see if this is a Duration type, we do not allow
        // searchable for a Duration Primitive type
        Type type = prop.getType();
        if (type instanceof PrimitiveType) {
            // Get the base type in case this is a BOM primitive type defined by
            // the user, we want to get the type of that for checking
            PrimitiveType primType =
                    PrimitivesUtil.getBasePrimitiveType((PrimitiveType) type);

            // Reject durations
            if (PrimitivesUtil.BOM_PRIMITIVE_DURATION_NAME.equals(primType
                    .getName())) {
                return false;
            }
        }

        return true;
    }
}
