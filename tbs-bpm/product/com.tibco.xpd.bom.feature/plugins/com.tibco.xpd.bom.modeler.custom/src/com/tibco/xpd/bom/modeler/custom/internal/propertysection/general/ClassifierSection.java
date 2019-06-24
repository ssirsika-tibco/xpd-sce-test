/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.internal.propertysection.general;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.AssociationClass;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.modeler.custom.internal.Messages;
import com.tibco.xpd.bom.modeler.custom.internal.propertysection.AbstractGeneralSection;
import com.tibco.xpd.bom.resources.ui.util.BomUIUtil;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * BOM property section for a {@link Classifier}. This will allow the setting of
 * the super-class.
 * 
 * @author njpatel
 * 
 */
public class ClassifierSection extends AbstractGeneralSection {

    private SuperclassPickerControl superClassCtrl;

    @Override
    protected boolean shouldDisplay(EObject eo) {
        if (eo instanceof Classifier) {
            if (eo instanceof Association && !(eo instanceof AssociationClass)) {
                // This section should be shown for an AssociationClass but not
                // for Association
                return false;
            } else if (eo instanceof PrimitiveType) {
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = (Composite) super.doCreateControls(parent, toolkit);

        createLabel(root, toolkit, Messages.ClassifierSection_baseType_label);
        superClassCtrl =
                new SuperclassPickerControl(root, toolkit, getEditingDomain());
        superClassCtrl.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        return root;
    }

    @Override
    protected Command doGetCommand(Object obj) {
        // Nothing to do - the picker will take care of setting the command
        return null;
    }

    @Override
    protected void doRefresh() {
        if (superClassCtrl != null && !superClassCtrl.isDisposed()) {
            EObject input = getInput();
            Classifier value = null;
            if (input instanceof Classifier) {
                EList<Generalization> generalizations =
                        ((Classifier) input).getGeneralizations();
                if (generalizations != null && !generalizations.isEmpty()) {
                    if (generalizations.size() == 1) {
                        value = generalizations.get(0).getGeneral();
                    } else {
                        // Multiple inheritance is not allowed so indicate that
                        // there is an invalid value
                        superClassCtrl.setIsInvalidValue();
                        return;
                    }
                }
            }
            superClassCtrl.setValue(value);
        }
    }

    /**
     * Superclass picker control.
     * 
     * @author njpatel
     * 
     */
    private class SuperclassPickerControl extends BOMPickerControl<Classifier> {

        private Object value;

        private final Object INVALID_VALUE = new Object();

        public SuperclassPickerControl(Composite parent,
                XpdFormToolkit toolkit, EditingDomain editingDomain) {
            super(parent, SWT.NONE, toolkit, editingDomain);
            setBrowseTooltip(Messages.ClassifierSection_selectSuperclass_button_tooltip);
            setClearTooltip(Messages.ClassifierSection_clearSuperclass_button_tooltip);
            setLabelProvider(new PickerControlLabelProvider());
        }

        @Override
        protected Classifier doBrowse(Control control) {
            EObject input = getInput();
            if (input != null) {
                return BomUIUtil.getSuperclassFromPicker(control.getShell(),
                        input);
            }
            return null;
        }

        @Override
        protected Command getClearValueCommand(EditingDomain editingDomain,
                Classifier value) {
            Command cmd = null;

            EObject input = getInput();
            if (input instanceof Classifier) {
                cmd =
                        RemoveCommand.create(editingDomain,
                                input,
                                UMLPackage.eINSTANCE
                                        .getClassifier_Generalization(),
                                ((Classifier) input).getGeneralizations());
            }

            return cmd;
        }

        @Override
        protected Command getSetValueCommand(EditingDomain editingDomain,
                Classifier value) {
            Command cmd = null;
            EObject input = getInput();
            if (input instanceof Classifier) {
                Generalization gen =
                        UMLFactory.eINSTANCE.createGeneralization();
                gen.setGeneral(value);
                EList<Generalization> genList =
                        new BasicEList<Generalization>(1);
                genList.add(gen);

                cmd =
                        SetCommand.create(editingDomain,
                                input,
                                UMLPackage.eINSTANCE
                                        .getClassifier_Generalization(),
                                genList);
            }
            return cmd;
        }

        @Override
        public void setValue(Classifier value) {
            this.value = value;
            // If the value is a proxy then set to null
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

        /**
         * Invalid generalisation so set invalid message in this control.
         */
        public void setIsInvalidValue() {
            this.value = INVALID_VALUE;
            super.setValue(null);
            setHyperlinkActive(false);
        }

        @Override
        protected String getClearText() {
            if (value != null) {
                if (value == INVALID_VALUE) {
                    return Messages.ClassifierSection_invalidValue_error_shortdesc;
                } else if (value instanceof EObject
                        && ((EObject) value).eIsProxy()) {
                    return UNRESOLVED_REFERENCE;
                }
            }
            return super.getClearText();
        }
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractTransactionalSection#dispose()
     * 
     */
    @Override
    public void dispose() {
        if (superClassCtrl != null) {
            superClassCtrl.dispose();
        }
        super.dispose();
    }

}
