/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.internal.propertysection.general;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.TypedElement;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager;
import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager.StereotypeKind;
import com.tibco.xpd.bom.modeler.custom.internal.Messages;
import com.tibco.xpd.bom.modeler.custom.internal.propertysection.AbstractGeneralSection;
import com.tibco.xpd.bom.resources.ui.util.BomUIUtil;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * BOM property section for a {@link TypedElement}. This will allow setting of
 * the type.
 * 
 * @author njpatel
 * 
 */
public class TypedElementSection extends AbstractGeneralSection {

    private TypePickerControl typeCtrl;

    private Label label;

    @Override
    protected boolean shouldDisplay(EObject eo) {
        boolean isACaseId = eo instanceof Property
                && BOMGlobalDataUtils.isCID((Property) eo);
        // Hide for caseIds properties ...
        if (isACaseId) {
            // Don't display this even if the caseId has not the Text type.
            // This must be fixed by the validation quick-fix.

            // Property prop = (Property) eo;
            // boolean isTextType = prop.getType() instanceof PrimitiveType
            // && PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME
            // .equals(prop.getType().getName());
            // // ...unless it's not a text type.
            // if (!isTextType) {
            // return true;
            // }
            return false;
        }
        return eo instanceof TypedElement;
    }

    @Override
    protected Control doCreateControls(Composite parent,
            XpdFormToolkit toolkit) {
        Composite root = (Composite) super.doCreateControls(parent, toolkit);

        label = createLabel(root,
                toolkit,
                Messages.TypedElementSection_type_label);
        typeCtrl = new TypePickerControl(root, toolkit, getEditingDomain());
        typeCtrl.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        return root;
    }

    @Override
    protected Command doGetCommand(Object obj) {
        // Picker will take care of the update
        return null;
    }

    @Override
    protected void doRefresh() {
        if (label != null && !label.isDisposed()) {
            String message = Messages.TypedElementSection_type_label;

            /*
             * Check to see if this is a Case State property, in which case we
             * want to change the label text to show that only enumerations can
             * be selected
             */
            EObject input = getInput();
            if (input instanceof Property) {
                final Property prop = (Property) input;

                if (GlobalDataProfileManager.getInstance().isCaseState(prop)) {
                    message =
                            Messages.TypedElementSection_enumeration_type_label;
                }
            }
            label.setText(message);
        }

        Type value = null;

        if (typeCtrl != null && !typeCtrl.isDisposed()) {
            EObject input = getInput();
            boolean isEnabled = true;
            if (input instanceof TypedElement) {
                value = ((TypedElement) input).getType();

                if ((input instanceof Property) && (value != null)) {
                    Property property = (Property) input;

                    boolean isInteger = false;

                    /*
                     * Get the name of the type - This may be null if it was a
                     * user defined type, that now no longer exists
                     */
                    if (value.getName() != null) {
                        if (PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME
                                .compareTo(value.getName()) == 0) {
                            isInteger = true;
                        }
                    }

                    /*
                     * If this is an Association Property Or AutoCaseId then we
                     * can't change the type Unless it is an incorrect type at
                     * the moment, then a change may be required
                     */
                    isEnabled = !(property.getAssociation() != null
                            || (GlobalDataProfileManager.getInstance()
                                    .isAutoCaseIdentifier(property)
                                    && isInteger));
                }
            }

            typeCtrl.setValue(value);
            typeCtrl.setEnabled(isEnabled);
        }
    }

    /**
     * Type picker control.
     * 
     * @author njpatel
     * 
     */
    private class TypePickerControl extends BOMPickerControl<Type> {

        private Type value;

        public TypePickerControl(Composite parent, XpdFormToolkit toolkit,
                EditingDomain editingDomain) {
            super(parent, SWT.NONE, toolkit, editingDomain);
            setToolTipText(
                    Messages.TypedElementSection_selectType_button_tooltip);
            setClearTooltip(
                    Messages.TypedElementSection_clearType_button_tooltip);
            setLabelProvider(new PickerControlLabelProvider());
            setHyperlinkActive(true);
        }

        @Override
        public void setValue(Type value) {
            this.value = value;
            /* If the value is a proxy then set to null */
            super.setValue(value != null && value.eIsProxy() ? null : value);
            /*
             * If this value is from a resource in the workspace then enable the
             * hyperlink, otherwise don't as user won't be able to 'go to' the
             * resource
             */
            setHyperlinkActive(value != null && !value.eIsProxy()
                    && value.eResource() != null
                    && value.eResource().getURI().isPlatformResource());
        }

        @Override
        protected String getClearText() {
            if (value != null && value.eIsProxy()) {
                return UNRESOLVED_REFERENCE;
            }
            return super.getClearText();
        }

        @Override
        protected Type doBrowse(Control control) {
            EObject input = getInput();
            if (input != null) {
                return BomUIUtil.getTypeFromPicker(control.getShell(), input);
            }
            return null;
        }

        @Override
        protected Command getClearValueCommand(EditingDomain editingDomain,
                Type value) {
            Command cmd = null;
            EObject input = getInput();
            if (input instanceof TypedElement) {
                cmd = SetCommand.create(editingDomain,
                        input,
                        UMLPackage.eINSTANCE.getTypedElement_Type(),
                        SetCommand.UNSET_VALUE);

                if (input instanceof Property) {
                    final Property prop = (Property) input;

                    Command clearSearchCmd = getClearSearchableCommand(prop);
                    if (clearSearchCmd != null) {
                        CompoundCommand compoundCommand = new CompoundCommand();
                        // Add the existing command
                        compoundCommand.append(cmd);
                        compoundCommand.append(clearSearchCmd);
                        cmd = compoundCommand;
                    }
                }
            }
            return cmd;
        }

        @Override
        protected Command getSetValueCommand(EditingDomain editingDomain,
                Type value) {
            Command cmd = null;
            EObject input = getInput();
            if (input instanceof TypedElement) {
                cmd = SetCommand.create(editingDomain,
                        input,
                        UMLPackage.eINSTANCE.getTypedElement_Type(),
                        value);

                if ((input instanceof Property)
                        && (value instanceof PrimitiveType)) {
                    PrimitiveType primType = PrimitivesUtil
                            .getBasePrimitiveType((PrimitiveType) value);

                    // Clear searchable from durations
                    if (PrimitivesUtil.BOM_PRIMITIVE_DURATION_NAME
                            .equals(primType.getName())) {
                        final Property prop = (Property) input;
                        Command clearSearchCmd =
                                getClearSearchableCommand(prop);
                        if (clearSearchCmd != null) {
                            CompoundCommand compoundCommand =
                                    new CompoundCommand();
                            // Add the existing command
                            compoundCommand.append(cmd);
                            compoundCommand.append(clearSearchCmd);
                            cmd = compoundCommand;
                        }
                    }
                }
            }
            return cmd;
        }

        /**
         * Creates a command to clear the searchable stereotype if set
         * 
         * @param prop
         *            Property to check for stereotype
         * @return Command to run, or null if no change needed
         */
        private Command getClearSearchableCommand(final Property prop) {
            Command cmd = null;
            final Stereotype stereotype = GlobalDataProfileManager.getInstance()
                    .getStereotype(StereotypeKind.SEARCHABLE);
            // Check if the searchable stereotype is already applied
            Stereotype appliedStereo =
                    prop.getAppliedStereotype(stereotype.getQualifiedName());
            if (appliedStereo != null) {
                // Create command to remove the searchable stereotype
                cmd = new RecordingCommand(
                        (TransactionalEditingDomain) getEditingDomain()) {
                    @Override
                    protected void doExecute() {
                        // Remove the stereotype if not enabled
                        prop.unapplyStereotype(stereotype);
                    }
                };
            }
            return cmd;
        }
    }
}
