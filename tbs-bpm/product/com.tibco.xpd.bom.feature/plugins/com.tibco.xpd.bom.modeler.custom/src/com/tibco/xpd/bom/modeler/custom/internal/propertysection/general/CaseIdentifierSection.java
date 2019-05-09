package com.tibco.xpd.bom.modeler.custom.internal.propertysection.general;

import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.DynamicEObjectImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;

import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager;
import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager.StereotypeKind;
import com.tibco.xpd.bom.modeler.custom.internal.Messages;
import com.tibco.xpd.bom.modeler.custom.internal.propertysection.AbstractGeneralSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * Displays the section that allows the user to change the type of case
 * identifier that is being defined
 * 
 */
public class CaseIdentifierSection extends AbstractGeneralSection {

    private Button caseIdType[];

    private StereotypeKind currentValue = null;

    private Composite rootOverload = null;

    private Composite rootControl = null;

    public CaseIdentifierSection() {
    }

    @Override
    protected boolean shouldDisplay(EObject eo) {
        // Generate the Case Identifier class for all properties, we can then
        // hide or show the specific details based on the type. It is done this
        // way because of the ability to change case identifiers to attributes
        // and the reverse
        if (eo instanceof Property) {
            return true;
        }
        return false;
    }

    @Override
    protected Control doCreateControls(Composite parent,
            XpdFormToolkit toolkit) {

        rootOverload = toolkit.createComposite(parent);

        GridLayout gridLayout = new GridLayout(1, false);
        // Need to set the margin to zero, as we do not want it indented more
        // that the rest
        gridLayout.marginWidth = 0;
        rootOverload.setLayout(gridLayout);

        // Default grid data will hide the Case Identifier settings
        GridData gd = new GridData(SWT.FILL, SWT.TOP, true, false);
        gd.heightHint = 1;

        rootControl = (Composite) super.doCreateControls(rootOverload, toolkit);
        rootControl.setLayoutData(gd);
        rootControl.setVisible(false);

        createLabel(rootControl, toolkit, Messages.CaseIdentifierSection_label);

        // Create the case identifier type radio buttons
        Group radioGroup = toolkit.createGroup(rootControl, ""); //$NON-NLS-1$
        RowLayout layout = new RowLayout();
        layout.spacing = 20;
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        radioGroup.setLayout(layout);
        caseIdType = new Button[2];

        caseIdType[0] = toolkit.createButton(radioGroup,
                Messages.CaseIdentifierSection_auto_label,
                SWT.RADIO);
        caseIdType[0].setData(StereotypeKind.AUTO_CASE_IDENTIFIER);
        caseIdType[0]
                .setToolTipText(Messages.CaseIdentifierSection_auto_tooltip);
        manageControl(caseIdType[0]);
        caseIdType[1] = toolkit.createButton(radioGroup,
                Messages.CaseIdentifierSection_custom_label,
                SWT.RADIO);
        caseIdType[1].setData(StereotypeKind.CID);
        caseIdType[1]
                .setToolTipText(Messages.CaseIdentifierSection_custom_tooltip);
        manageControl(caseIdType[1]);
        /* Composite case identifier removed in SCE */

        setLayoutData(radioGroup);

        return rootOverload;
    }

    @Override
    protected Command doGetCommand(Object obj) {
        Button selectedButton = null;
        // Global data : Case Identifier change
        for (Button radioOption : caseIdType) {
            if (radioOption == obj) {
                selectedButton = radioOption;
                break;
            }
        }
        if (selectedButton == null) {
            return null;
        }

        final Property prop = getSemanticInput();
        if (prop == null) {
            return null;
        }

        final Stereotype stereotypeAuto = GlobalDataProfileManager.getInstance()
                .getStereotype(StereotypeKind.AUTO_CASE_IDENTIFIER);
        final Stereotype stereotypeComposite =
                GlobalDataProfileManager.getInstance().getStereotype(
                        StereotypeKind.COMPOSITE_CASE_IDENTIFIER);
        final Stereotype stereotypeCustom = GlobalDataProfileManager
                .getInstance().getStereotype(StereotypeKind.CID);

        // Check the new selection
        StereotypeKind newSelection = null;
        Object data = selectedButton.getData();
        if (data instanceof StereotypeKind) {
            newSelection = (StereotypeKind) data;
        }

        // Make sure the new selection is not the same as the old selection
        if ((newSelection != null) && (newSelection != currentValue)) {
            final StereotypeKind newValue = newSelection;
            return new RecordingCommand(
                    (TransactionalEditingDomain) getEditingDomain()) {
                @Override
                protected void doExecute() {
                    StereotypeKind previousType = null;

                    // Check if there is a stereotype to be removed
                    if (GlobalDataProfileManager.getInstance()
                            .isAutoCaseIdentifier(prop)) {
                        prop.unapplyStereotype(stereotypeAuto);
                        previousType = StereotypeKind.AUTO_CASE_IDENTIFIER;
                    } else if (GlobalDataProfileManager.getInstance()
                            .isCompositeCaseIdentifier(prop)) {
                        prop.unapplyStereotype(stereotypeComposite);
                        previousType = StereotypeKind.COMPOSITE_CASE_IDENTIFIER;
                    } else if (GlobalDataProfileManager.getInstance()
                            .isCID(prop)) {
                        prop.unapplyStereotype(stereotypeCustom);
                        previousType = StereotypeKind.CID;
                    }

                    // Now set the new value
                    final Stereotype stereotypeNew = GlobalDataProfileManager
                            .getInstance().getStereotype(newValue);
                    prop.applyStereotype(stereotypeNew);
                    // Make sure that the auto type is set as read-only
                    // and the other types are not
                    prop.setIsReadOnly(newValue == StereotypeKind.AUTO_CASE_IDENTIFIER);
                }
            };
        }

        return null;
    }

    @Override
    protected void doRefresh() {
        final Property prop = getSemanticInput();
        if ((prop == null) || (rootControl == null)
                || rootControl.isDisposed()) {
            return;
        }

        // Need to work out if this is a case Identifier of some type, if it is,
        // then we need to display the selection for which type of case
        // identifier
        if (GlobalDataProfileManager.getInstance().isCID(prop)
                || GlobalDataProfileManager.getInstance()
                        .isAutoCaseIdentifier(prop)
                || GlobalDataProfileManager.getInstance()
                        .isCompositeCaseIdentifier(prop)) {
            // Only need to show the details if they are not already shown
            if (!rootControl.getVisible()) {
                GridData gd = new GridData(GridData.FILL_HORIZONTAL);
                rootControl.setLayoutData(gd);
                rootControl.setVisible(true);
                rootOverload.getParent().getParent().layout(true);
            }
        } else {
            // Only need to hide the details if they are currently showing
            if (rootControl.getVisible()) {
                GridData gd = new GridData();
                gd.heightHint = 1;
                rootControl.setLayoutData(gd);
                rootControl.setVisible(false);
                rootOverload.getParent().getParent().layout(true);
            }
        }

        // Make sure the tick box is initialised before we do anything
        if (caseIdType != null) {
            StereotypeKind previousValue = currentValue;
            if (GlobalDataProfileManager.getInstance()
                    .isAutoCaseIdentifier(prop)) {
                currentValue = StereotypeKind.AUTO_CASE_IDENTIFIER;
                caseIdType[0].setSelection(true);
                caseIdType[1].setSelection(false);
            } else if (GlobalDataProfileManager.getInstance()
                    .isCompositeCaseIdentifier(prop)) {
                // Removed in SCE: Needs to be handled by the validation.
                currentValue = StereotypeKind.COMPOSITE_CASE_IDENTIFIER;
                caseIdType[0].setSelection(false);
                caseIdType[1].setSelection(false);
            } else if (GlobalDataProfileManager.getInstance().isCID(prop)) {
                currentValue = StereotypeKind.CID;
                caseIdType[1].setSelection(true);
                caseIdType[0].setSelection(false);
            } else if (previousValue != null) {
                // Refresh has been called when there is no CID, make sure we
                // reset the current value to unset
                currentValue = null;
            }

            if ((currentValue != null) && (previousValue != currentValue)) {
                refreshTabs();
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

    @Override
    protected boolean shouldRefresh(List<Notification> notifications) {

        boolean refresh = super.shouldRefresh(notifications);

        if (refresh) {
            return refresh;
        }

        if (notifications != null) {
            for (Notification notification : notifications) {
                if (!notification.isTouch()) {
                    if (notification != null
                            && notification.getNotifier() instanceof Resource) {
                        Object oldValue = notification.getOldValue();
                        Object newValue = notification.getNewValue();

                        switch (notification.getEventType()) {
                        case Notification.ADD:
                            refresh = newValue instanceof DynamicEObjectImpl;
                            break;
                        case Notification.REMOVE:
                            refresh = oldValue instanceof DynamicEObjectImpl;
                            break;
                        }
                    }

                    if (refresh) {
                        break;
                    }
                }
            }
        }

        return refresh;
    }

}
