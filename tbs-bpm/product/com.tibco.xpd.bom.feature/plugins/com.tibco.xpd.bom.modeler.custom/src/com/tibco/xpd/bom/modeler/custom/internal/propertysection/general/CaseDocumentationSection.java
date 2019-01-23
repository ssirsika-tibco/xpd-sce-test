package com.tibco.xpd.bom.modeler.custom.internal.propertysection.general;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Stereotype;

import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager;
import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager.StereotypeKind;
import com.tibco.xpd.bom.modeler.custom.internal.Messages;
import com.tibco.xpd.bom.modeler.custom.internal.propertysection.AbstractGeneralSection;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * Handles the addition of the documentation tick box for Case Classes
 * 
 */
public class CaseDocumentationSection extends AbstractGeneralSection {

    private Button caseDocCheck = null;

    private Label docLabel = null;

    @Override
    protected boolean shouldDisplay(EObject eo) {

        // TODO: THIS SUPPORT IS CURRENTLY DISABLED XPD-6593
        
        // Only display the case documentation data if dealing with a Global
        // Data Case classes
//        if (eo instanceof Class) {
//            return true;
//        }
        return false;
    }

    @Override
    protected Command doGetCommand(Object obj) {
        // Global data : Case Documentation tick-box attribute for Case Class
        if (obj != caseDocCheck) {
            return null;
        }

        final Class prop = getSemanticInput();
        if (prop == null) {
            return null;
        }

        // Get Stereotype for Case Documentation
        boolean existingValue = false;
        final Stereotype stereotype =
                GlobalDataProfileManager
                        .getInstance()
                        .getStereotype(StereotypeKind.CASE_DOCUMENTATION_ENABLED);
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
                            BOMResourcesPlugin.ModelGlobalDataProfile_case_documentation_enabled);
            // Existing value
            existingValue =
                    (value instanceof Boolean) ? ((Boolean) value)
                            .booleanValue() : false;
        }

        // Check if the value has changed
        if ((caseDocCheck != null)
                && (caseDocCheck.getSelection() != existingValue)) {
            return new RecordingCommand(
                    (TransactionalEditingDomain) getEditingDomain()) {
                @Override
                protected void doExecute() {
                    // If ticked then enable the stereotype
                    if (caseDocCheck.getSelection()) {
                        prop.applyStereotype(stereotype);
                        prop.setValue(stereotype,
                                BOMResourcesPlugin.ModelGlobalDataProfile_case_documentation_enabled,
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

        docLabel = createLabel(root, toolkit, Messages.CaseDocumentation_label);
        caseDocCheck = toolkit.createButton(root, "", SWT.CHECK); //$NON-NLS-1$
        caseDocCheck.setToolTipText(Messages.CaseDocumentation_tooltip);
        setLayoutData(caseDocCheck);
        manageControl(caseDocCheck);
        return root;
    }

    @Override
    protected void doRefresh() {
        final Class prop = getSemanticInput();
        if (prop == null) {
            return;
        }

        // Make sure the tick box is initialised before we do anything
        if (caseDocCheck != null && !caseDocCheck.isDisposed()) {
            // Check the type of class - need to make sure if a user changes the
            // type of a class from a case to global or local then we no longer
            // display the check box
            if (!GlobalDataProfileManager.getInstance().isCase(prop)) {
                caseDocCheck.setSelection(false);
                caseDocCheck.setVisible(false);
                docLabel.setVisible(false);
            } else {
                docLabel.setVisible(true);
                caseDocCheck.setVisible(true);
                final Stereotype stereo =
                        GlobalDataProfileManager
                                .getInstance()
                                .getStereotype(StereotypeKind.CASE_DOCUMENTATION_ENABLED);
                if (stereo != null
                        && prop.getAppliedStereotypes().contains(stereo)) {
                    Object value =
                            prop.getValue(stereo,
                                    BOMResourcesPlugin.ModelGlobalDataProfile_case_documentation_enabled);
                    if (value instanceof Boolean) {
                        caseDocCheck.setSelection((Boolean) value);
                    }
                } else {
                    // Reset search-able to false
                    caseDocCheck.setSelection(false);
                }
            }
        }
    }

    /**
     * Retrieves the property
     * 
     * @return
     */
    private Class getSemanticInput() {
        EObject input = getInput();
        if (input instanceof Class) {
            return (Class) input;
        }
        return null;
    }
}
