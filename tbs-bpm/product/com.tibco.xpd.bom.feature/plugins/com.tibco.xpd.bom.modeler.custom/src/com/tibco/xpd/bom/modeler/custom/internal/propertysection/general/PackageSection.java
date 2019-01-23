package com.tibco.xpd.bom.modeler.custom.internal.propertysection.general;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Stereotype;

import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager;
import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager.StereotypeKind;
import com.tibco.xpd.bom.modeler.custom.internal.Messages;
import com.tibco.xpd.bom.modeler.custom.internal.propertysection.AbstractGeneralSection;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * Section added to the General section whenever a package is selected
 * 
 */
public class PackageSection extends AbstractGeneralSection {

    private Text tagTxt;

    @Override
    protected boolean shouldDisplay(EObject eo) {
        // Only display the Tag if on a Model or sub-package
        if (eo instanceof Package) {
            // Also needs to be a global data BOM
            Model model = null;
            // Check the case for a sub-packages, in this case we need a tag
            // to be displayed
            if (eo instanceof Model) {
                model = (Model) eo;
            } else {
                model = ((Package) eo).getModel();
            }

            if (BOMGlobalDataUtils.hasGlobalDataProfile(model)) {
                return true;
            }
        }

        // Don't display as default
        return false;
    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = (Composite) super.doCreateControls(parent, toolkit);

        createLabel(root, toolkit, Messages.PackageSection_tag_label);
        tagTxt = toolkit.createText(root, ""); //$NON-NLS-1$
        tagTxt.setToolTipText(Messages.PackageSection_tag_tooltip);
        tagTxt.setTextLimit(6);
        setLayoutData(tagTxt);
        manageControlUpdateOnDeactivate(tagTxt);
        return root;
    }

    @Override
    protected Command doGetCommand(Object obj) {
        // Global data : Tag attribute for model
        if (obj != tagTxt) {
            return null;
        }

        final Package pkg = getSemanticInput();

        if (pkg == null) {
            return null;
        }

        // get Stereotype for ModelTag
        final Stereotype stereotype =
                GlobalDataProfileManager.getInstance()
                        .getStereotype(StereotypeKind.TAG);
        // return if Stereotype is not found
        if (stereotype == null) {
            return null;
        }

        // Get the existing stereotype
        final Stereotype appliedStereo =
                pkg.getAppliedStereotype(stereotype.getQualifiedName());

        // Calculate the existing value
        String existingValueString = "";
        if (appliedStereo != null) {
            Object value =
                    pkg.getValue(stereotype,
                            BOMResourcesPlugin.ModelGlobalDataProfile_attribute_Tag);
            if (value instanceof String) {
                existingValueString = (String) value;
            }
        }

        // Only need to run the command if the value has changed
        if (existingValueString.compareTo(tagTxt.getText()) != 0) {
            return new RecordingCommand(
                    (TransactionalEditingDomain) getEditingDomain()) {
                @Override
                protected void doExecute() {
                    if ((tagTxt.getText() == null) || (tagTxt.getText() == "")) {
                        if (appliedStereo != null) {
                            pkg.unapplyStereotype(stereotype);
                        }
                    } else {
                        if (appliedStereo == null) {
                            pkg.applyStereotype(stereotype);
                        }
                        pkg.setValue(stereotype,
                                BOMResourcesPlugin.ModelGlobalDataProfile_attribute_Tag,
                                tagTxt.getText().trim());
                    }
                }
            };
        }
        return null;
    }

    @Override
    protected void doRefresh() {
        Package pkg = getSemanticInput();
        if (pkg == null) {
            return;
        }

        if (tagTxt != null && !tagTxt.isDisposed()) {
            Stereotype stereo =
                    GlobalDataProfileManager.getInstance()
                            .getStereotype(StereotypeKind.TAG);
            if (stereo != null && pkg.getAppliedStereotypes().contains(stereo)) {
                Object value =
                        pkg.getValue(stereo,
                                BOMResourcesPlugin.ModelGlobalDataProfile_attribute_Tag);
                if (value instanceof String) {
                    updateText(tagTxt, (String) value);
                }
            } else {
                // Reset the tag to empty
                updateText(tagTxt, "");
            }
        }
    }

    /**
     * Get the semantic model of the input of this section. If this is the
     * Diagram then the Model will be returned.
     * 
     * @return
     */
    private Package getSemanticInput() {
        EObject input = getInput();
        if (input instanceof Diagram) {
            input = ((Diagram) input).getElement();
        }

        Package pkg = null;
        if (input instanceof Package) {
            pkg = (Package) input;
        }
        return pkg;
    }
}
