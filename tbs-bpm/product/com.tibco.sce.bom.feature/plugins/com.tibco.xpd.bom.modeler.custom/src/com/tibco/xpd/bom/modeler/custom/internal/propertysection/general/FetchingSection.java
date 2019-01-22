package com.tibco.xpd.bom.modeler.custom.internal.propertysection.general;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;

import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager;
import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager.StereotypeKind;
import com.tibco.xpd.bom.modeler.custom.internal.Messages;
import com.tibco.xpd.bom.modeler.custom.internal.propertysection.AbstractGeneralSection;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.utils.UML2ModelUtil;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * Section added to the general tab for Compositions or attributes of
 * "Global Class" type to allow the fetching batch size to be added
 * 
 * IMPORTANT: This is currently not activated in the plugin.xml file This is
 * because it was decided that this option should not be exposed to the user at
 * this point
 * 
 */
public class FetchingSection extends AbstractGeneralSection {

    private Text batchSizeTxt;

    @Override
    protected boolean shouldDisplay(EObject eo) {
        // Only display the fetching information for properties
        // or compositions with upper multiplicity
        if (eo instanceof Property) {
            Property prop = (Property) eo;

            // Avoid putting fetching when the multiplicity on a composition
            // is selected, just want the actual composition
            if (prop.getAssociation() == null) {
                if ((prop.getUpper() > 1) || (prop.getUpper() == -1)) {
                    return true;
                }
            }
        } else if (eo instanceof Association) {
            Association assoc = (Association) eo;
            if (UML2ModelUtil.getAggregationType(assoc) == AggregationKind.COMPOSITE_LITERAL) {
                for (Property assocEnd : assoc.getMemberEnds()) {
                    if ((assocEnd.getUpper() > 1)
                            || (assocEnd.getUpper() == -1)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = (Composite) super.doCreateControls(parent, toolkit);

        createLabel(root, toolkit, Messages.FetchingSection_BatchSize_label);
        batchSizeTxt = toolkit.createText(root, ""); //$NON-NLS-1$
        batchSizeTxt.setTextLimit(9);
        batchSizeTxt.setToolTipText(Messages.FetchingSection_BatchSize_tooltip);
        setLayoutData(batchSizeTxt);
        manageControlUpdateOnDeactivate(batchSizeTxt);

        // Add a Verify Listener that will check to ensure that only
        // numbers are added to the Text Box
        batchSizeTxt.addVerifyListener(new VerifyListener() {
            public void verifyText(VerifyEvent e) {
                if (Character.isDigit(e.character) || e.keyCode == SWT.DEL
                        || e.keyCode == SWT.BS || e.character == 0) {
                    e.doit = true;
                } else {
                    e.doit = false;
                }
            }
        });

        return root;
    }

    @Override
    protected Command doGetCommand(Object obj) {
        // Global data : Batch Size tick-box attribute for attribute
        if (obj != batchSizeTxt) {
            return null;
        }

        final NamedElement prop = getSemanticInput();
        if (prop == null) {
            return null;
        }

        // get Stereotype for Fetching Batch Size
        final Stereotype stereotype =
                GlobalDataProfileManager.getInstance()
                        .getStereotype(StereotypeKind.FETCHING);
        // return if Stereotype is not found
        if (stereotype == null) {
            return null;
        }

        // Get the existing stereotype
        final Stereotype appliedStereo =
                prop.getAppliedStereotype(stereotype.getQualifiedName());

        // Calculate the existing value
        String existingValueString = "";
        if (appliedStereo != null) {
            Object value =
                    prop.getValue(stereotype,
                            BOMResourcesPlugin.ModelGlobalDataProfile_Fetching_BatchSize);
            if (value instanceof Integer) {
                existingValueString = ((Integer) value).toString();
            }
        }

        // Only need to run the command if the value has changed
        if (existingValueString.compareTo(batchSizeTxt.getText()) != 0) {
            // Stereotype not already applied, apply stereotype and
            // then update the fetching property value in model.
            return new RecordingCommand(
                    (TransactionalEditingDomain) getEditingDomain()) {
                @Override
                protected void doExecute() {
                    if ((batchSizeTxt.getText() == null)
                            || (batchSizeTxt.getText() == "")) {
                        if (appliedStereo != null) {
                            prop.unapplyStereotype(stereotype);
                        }
                    } else {
                        if (appliedStereo == null) {
                            prop.applyStereotype(stereotype);
                        }
                        prop.setValue(stereotype,
                                BOMResourcesPlugin.ModelGlobalDataProfile_Fetching_BatchSize,
                                Integer.parseInt(batchSizeTxt.getText().trim()));
                    }
                }
            };
        }

        return null;
    }

    @Override
    protected void doRefresh() {
        final NamedElement prop = getSemanticInput();
        if (prop == null) {
            return;
        }

        if (batchSizeTxt != null && !batchSizeTxt.isDisposed()) {
            Stereotype stereo =
                    GlobalDataProfileManager.getInstance()
                            .getStereotype(StereotypeKind.FETCHING);
            if (stereo != null && prop.getAppliedStereotypes().contains(stereo)) {
                Object value =
                        prop.getValue(stereo,
                                BOMResourcesPlugin.ModelGlobalDataProfile_Fetching_BatchSize);
                if (value instanceof Integer) {
                    updateText(batchSizeTxt, ((Integer) value).toString());
                }
            } else {
                // Reset the tag to empty
                updateText(batchSizeTxt, "");
            }
        }
    }

    /**
     * Retrieves the property or association (as NamedElement)
     * 
     * @return
     */
    private NamedElement getSemanticInput() {
        EObject input = getInput();
        if ((input instanceof Association) || (input instanceof Property)) {
            return (NamedElement) input;
        }
        return null;
    }
}
