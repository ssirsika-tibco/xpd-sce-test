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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.bom.modeler.custom.internal.Messages;
import com.tibco.xpd.bom.modeler.custom.internal.propertysection.AbstractGeneralSection;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * Section added to the general tab for maximum length of a text attrubute
 * 
 */
public class TextLengthSection extends AbstractGeneralSection {

    private Text maxLengthTxt;

    private Label label;

    @Override
    protected boolean shouldDisplay(EObject eo) {
        // Only display the length field for text attributes
        if (eo instanceof Property) {

            // Hide for caseId of type Auto.
            if (BOMGlobalDataUtils.isAutoCID((Property) eo)) {
                return false;
            }
            // We need to support all properties, as in theory the type could be
            // changed and we need to be able to pick up on that and either show
            // or hide the field on the screen
            return true;
        }
        return false;
    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = (Composite) super.doCreateControls(parent, toolkit);

        label =
                createLabel(root,
                        toolkit,
                        Messages.TextLengthSection_maximum_length_label);
        maxLengthTxt = toolkit.createText(root, ""); //$NON-NLS-1$
        maxLengthTxt.setTextLimit(9);
        maxLengthTxt
                .setToolTipText(Messages.TextLengthSection_maximum_length_tooltip);
        setLayoutData(maxLengthTxt);
        manageControlUpdateOnDeactivate(maxLengthTxt);

        // Add a Verify Listener that will check to ensure that only
        // numbers are added to the Text Box
        maxLengthTxt.addVerifyListener(new VerifyListener() {
            @Override
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
        if (obj != maxLengthTxt) {
            return null;
        }

        final Property prop = getSemanticInput();
        if (prop == null) {
            return null;
        }

        // Calculate the existing value
        Integer currentMaxTextLen = 0;
        if (!(prop.getType() instanceof PrimitiveType)) {
            return null;
        }
        final PrimitiveType primType =
                (PrimitiveType) (prop.getType());
        // Get the max text length
        Object maxLengthAttribute =
                PrimitivesUtil.getFacetPropertyValue(primType,
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_TEXT_LENGTH,
                        prop);

        if (maxLengthAttribute instanceof Integer) {
            currentMaxTextLen = (Integer) maxLengthAttribute;
        }

        if ((maxLengthTxt.getText() == null)
                || maxLengthTxt.getText().isEmpty()) {
            return null;
        }

        // Only need to run the command if the value has changed
        if (maxLengthTxt.getText().compareTo(currentMaxTextLen.toString()) != 0) {
            return new RecordingCommand(
                    (TransactionalEditingDomain) getEditingDomain()) {
                @Override
                protected void doExecute() {
                    Integer newVal = Integer.parseInt(maxLengthTxt.getText());
                    PrimitivesUtil.setFacetPropertyValue(primType,
                            PrimitivesUtil.BOM_PRIMITIVE_FACET_TEXT_LENGTH,
                            newVal,
                            prop);
                }
            };
        }

        return null;
    }

    @Override
    protected void doRefresh() {
        final Property prop = getSemanticInput();
        if (prop == null) {
            label.setVisible(false);
            maxLengthTxt.setVisible(false);
            return;
        }

        if (maxLengthTxt != null && !maxLengthTxt.isDisposed()) {
            if (prop.getType() instanceof PrimitiveType) {
                PrimitiveType primType =
                        (PrimitiveType) (prop.getType());

                // Check to see if this is a text type
                if (PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME.compareTo(primType
                        .getName()) == 0) {
                    // Check if already visible
                    if (!maxLengthTxt.isVisible()) {
                        label.setVisible(true);
                        maxLengthTxt.setVisible(true);
                    }
                    // Get the max text length
                    Object maxLengthAttribute =
                            PrimitivesUtil
                                    .getFacetPropertyValue(primType,
                                            PrimitivesUtil.BOM_PRIMITIVE_FACET_TEXT_LENGTH,
                                            prop);

                    if (maxLengthAttribute instanceof Integer) {
                        Integer maxTextLen = (Integer) maxLengthAttribute;
                        updateText(maxLengthTxt, (maxTextLen).toString());
                    }
                } else {
                    // Check if already visible
                    if (maxLengthTxt.isVisible()) {
                        label.setVisible(false);
                        maxLengthTxt.setVisible(false);
                    }
                }
            }
        }
    }

    /**
     * Retrieves the property or association (as NamedElement)
     * 
     * @return
     */
    private Property getSemanticInput() {
        EObject input = getInput();
        if ((input instanceof Property)) {
            return (Property) input;
        }
        return null;
    }
}
