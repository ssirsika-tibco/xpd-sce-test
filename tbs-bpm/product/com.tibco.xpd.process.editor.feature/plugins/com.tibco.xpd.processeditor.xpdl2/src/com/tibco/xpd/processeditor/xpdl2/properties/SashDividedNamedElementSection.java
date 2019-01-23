/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.osgi.service.environment.Constants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessDialogUtil;
import com.tibco.xpd.ui.properties.TextFieldVerifier;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.ui.util.CapabilityUtil;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.edit.ui.Xpdl2UiPlugin;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author NWilson
 * 
 */
public abstract class SashDividedNamedElementSection extends
        SashDividedFilteredTransactionalSection implements TextFieldVerifier {

    private boolean canFinish = true;

    private String cantFinishMessage = null;

    private boolean subClassCanFinish = true;

    private String subClassCantFinishMessage = null;

    protected CLabel displayLabel;

    protected Text display;

    protected CLabel nameLabel;

    protected Text name;

    protected boolean nameValid = true;

    protected boolean displayNameValid = true;

    private String err = null;;

    private Composite nameCtrlsContainer;

    private GridLayout nameControlsParentLayout;

    private boolean ignoreTextChanges = false;

    private boolean firstUpdateLayoutDone = false;

    public SashDividedNamedElementSection(EClass eClass, String actionIdPrefix) {
        super(eClass, actionIdPrefix);
    }

    @Override
    protected final Composite createGeneralSection(Composite parent,
            XpdFormToolkit toolkit) {

        Composite container = toolkit.createComposite(parent);

        GridLayout containerLayout = new GridLayout(1, false);
        // The following setting keep the Label/Name controls in this section
        // same as those in NamedElementSection. If you change these
        // then you should change
        // NamedElementSection.doCreateControls() to ensure that
        // they line up correctly (they have to be set up slightly different to
        // do so so compare visually to get it right!
        containerLayout.marginTop = containerLayout.marginHeight;
        containerLayout.marginHeight = 0;
        containerLayout.marginWidth = 0;
        container.setLayout(containerLayout);

        nameCtrlsContainer = toolkit.createComposite(container);
        nameCtrlsContainer.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
                true, false));

        nameControlsParentLayout = new GridLayout(2, false);
        nameCtrlsContainer.setLayout(nameControlsParentLayout);

        displayLabel =
                toolkit.createCLabel(nameCtrlsContainer,
                        Messages.NamedElementSection_displayNameLabel);
        displayLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
                false));

        display =
                toolkit.createText(nameCtrlsContainer,
                        "", "nameElement_displayName_text"); //$NON-NLS-1$ //$NON-NLS-2$
        display.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

        nameLabel =
                toolkit.createCLabel(nameCtrlsContainer,
                        Messages.NamedElementSection_nameLabel);
        nameLabel
                .setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));

        name =
                toolkit.createText(nameCtrlsContainer,
                        "", "nameElement_name_text"); //$NON-NLS-1$ //$NON-NLS-2$
        name.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

        manageControlUpdateOnDeactivate(name);
        manageControlUpdateOnDeactivate(display);

        // updateVisibility();
        Composite general = doCreateGeneralSection(container, toolkit);
        general.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        return container;
    }

    @Override
    protected final Composite createDetailsSection(Composite parent,
            XpdFormToolkit toolkit) {
        Composite composite = doCreateDetailsSection(parent, toolkit);

        return composite;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    protected Command doGetCommand(Object obj) {
        if (obj != null && "enableSolution".equals(obj)) { //$NON-NLS-1$
            if (getSite() != null && getSite().getShell() != null) {
                ProcessDialogUtil.enableSolutionDesignCapability(getSite()
                        .getShell(), true);
                refresh();
            }
            return null;
        } else if (obj != null && "disableSolution".equals(obj)) { //$NON-NLS-1$
            if (getSite() != null && getSite().getShell() != null) {
                ProcessDialogUtil.enableSolutionDesignCapability(getSite()
                        .getShell(), false);
                refresh();
            }
            return null;
        }
        Command cmd = null;
        EObject input = getInput();
        if (input instanceof NamedElement) {
            NamedElement named = (NamedElement) input;
            EditingDomain ed = getEditingDomain();

            if (obj == name || obj == display) {

                boolean hasNameChanged = false;
                String nameText = name.getText();
                if (nameText == null) {
                    nameText = ""; //$NON-NLS-1$
                }

                String nameModelText = named.getName();
                if (nameModelText == null) {
                    nameModelText = ""; //$NON-NLS-1$
                }

                if (!nameText.equals(nameModelText)) {
                    hasNameChanged = true;
                }

                boolean hasDisplayChanged = false;
                String displayText = display.getText();
                if (displayText == null) {
                    displayText = ""; //$NON-NLS-1$
                }
                displayText = displayText.trim();

                String displayModelText =
                        (String) Xpdl2ModelUtil.getOtherAttribute(named,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_DisplayName());
                if (displayModelText == null) {
                    displayModelText = ""; //$NON-NLS-1$
                }

                if (!displayText.equals(displayModelText)) {
                    hasDisplayChanged = true;
                }

                if (hasNameChanged || hasDisplayChanged) {
                    String message;
                    if (obj == name) {
                        message = Messages.NamedElementSection_SetNameCommand;
                    } else {
                        message = Messages.NamedElementSection_SetLabelCommand;
                    }

                    if ((nameValid && displayNameValid)) {
                        // If both the name and the display name are valid then
                        // set them (regardless of which actually changed).
                        CompoundCommand command = new CompoundCommand(message);
                        // Setting name to null temporarily to avoid
                        // circumstances where the precommit listener would
                        // attempt to change
                        // the token name when it shouldn't.
                        command.append(SetCommand.create(ed,
                                named,
                                Xpdl2Package.eINSTANCE.getNamedElement_Name(),
                                null));
                        command.append(Xpdl2ModelUtil
                                .getSetOtherAttributeCommand(ed,
                                        named,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_DisplayName(),
                                        displayText));
                        command.append(SetCommand.create(ed,
                                named,
                                Xpdl2Package.eINSTANCE.getNamedElement_Name(),
                                nameText));
                        cmd = command;

                    } else if (obj == name && nameValid) {
                        // If the token name is changing and valid (but the
                        // display name is invalid - then STILL want to set the
                        // token name.
                        CompoundCommand command = new CompoundCommand(message);
                        // Setting name to null temporarily to avoid
                        // circumstances where the precommit listener would
                        // attempt to change
                        // the token name when it shouldn't.
                        command.append(SetCommand.create(ed,
                                named,
                                Xpdl2Package.eINSTANCE.getNamedElement_Name(),
                                null));
                        command.append(SetCommand.create(ed,
                                named,
                                Xpdl2Package.eINSTANCE.getNamedElement_Name(),
                                nameText));
                        cmd = command;

                    } else if (obj == display && displayNameValid) {
                        // If the token name is changing and valid (but the
                        // display name is invalid - then STILL want to set the
                        // token name.
                        CompoundCommand command = new CompoundCommand(message);
                        // Setting name to null temporarily to avoid
                        // circumstances where the precommit listener would
                        // attempt to change
                        // the token name when it shouldn't.
                        command.append(SetCommand.create(ed,
                                named,
                                Xpdl2Package.eINSTANCE.getNamedElement_Name(),
                                null));
                        command.append(Xpdl2ModelUtil
                                .getSetOtherAttributeCommand(ed,
                                        named,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_DisplayName(),
                                        displayText));
                        command.append(SetCommand.create(ed,
                                named,
                                Xpdl2Package.eINSTANCE.getNamedElement_Name(),
                                nameModelText));
                        cmd = command;

                    }

                }
            } else {
                cmd = doGetDetailsCommand(obj);
            }
        }
        return cmd;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     * 
     */
    @Override
    protected final void doRefresh() {
        super.doRefresh();

        EObject input = getInput();
        if (input instanceof NamedElement) {
            NamedElement named = (NamedElement) input;

            // SID - Update display name first because of the way verifyText()
            // works this will prevent an unnecessary forceLayout.
            if (!display.isDisposed()) {
                updateText(display, getNamedElementDisplayName());
            }

            if (name != null && !name.isDisposed()) {
                updateText(name, getNamedElementName());
            }
        }
        updateVisibility();
        doRefreshDetailsSection();
        doRefreshImplementationSection();

        return;
    }

    protected String getNamedElementName() {
        String name = null;

        EObject input = getInput();
        if (input instanceof NamedElement) {
            NamedElement named = (NamedElement) input;

            name = named.getName();
        }

        return name == null ? "" : name; //$NON-NLS-1$
    }

    protected String getNamedElementDisplayName() {
        String displayName = null;

        EObject input = getInput();
        if (input instanceof NamedElement) {
            NamedElement named = (NamedElement) input;

            displayName =
                    (String) Xpdl2ModelUtil.getOtherAttribute(named,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName());

        }

        return displayName == null ? "" : displayName; //$NON-NLS-1$
    }

    protected void updateVisibility() {

        boolean tokenNameVisible = CapabilityUtil.isDeveloperActivityEnabled();

        boolean changed =
                (name.getVisible() != tokenNameVisible)
                        || (nameLabel.getVisible() != tokenNameVisible);

        nameLabel.setVisible(tokenNameVisible);
        name.setVisible(tokenNameVisible);

        int verticalSpacing = nameControlsParentLayout.verticalSpacing;
        int marginHeight = nameControlsParentLayout.marginHeight;

        int minSectHeight;

        // doing a display.getSize().y DOES NOT WORK. On Win2000 look-n-feel
        // this DOES NOT include the borders BUT on WinXP look-n-feel it does.
        // The line height seems to be a reliable figure so we'll take that and
        // allow for top and bottom border.
        // not :o(
        int textBoxHeight = display.getLineHeight() + 2 + 2;

        if (!tokenNameVisible) {
            minSectHeight =
                    textBoxHeight + (0 * verticalSpacing) + (2 * marginHeight);

        } else {

            minSectHeight =
                    (textBoxHeight * 2) + (1 * verticalSpacing)
                            + (2 * marginHeight);

        }

        minSectHeight += nameControlsParentLayout.marginTop;
        minSectHeight += nameControlsParentLayout.marginBottom;

        if (getMinimumHeight() != minSectHeight) {
            changed = true;
        }

        if (changed) {
            setMinimumHeight(minSectHeight);

            ((GridData) nameCtrlsContainer.getLayoutData()).heightHint =
                    minSectHeight;

            nameCtrlsContainer.getParent().layout(true, true);

            /*
             * XPD-4813: On Mac OS, forceLayout() wasn't redrawing the property
             * section for Script Task correctly, so avoid calling this for Mac.
             * It seems that forceLayout() isn't required on Windows either as
             * it works fine without it, but in order to avoid any problems,
             * we'll keep it and play safe.
             */

            if (getSectionContainerType() != ContainerType.WIZARD
                    && !Platform.getOS().equals(Constants.OS_MACOSX)) {
                forceLayout();
            }
        }

    }

    @Override
    public void verifyText(Event event) {
        if (!ignoreTextChanges) {
            boolean beforeNameValid = nameValid;
            boolean beforeDisplayNameValid = displayNameValid;

            Text textControl = ((Text) event.widget);
            String text =
                    textControl.getText(0, event.start - 1)
                            + event.text
                            + textControl.getText(event.end,
                                    textControl.getCharCount() - 1);
            if (textControl == name) {
                verifyName(text);
                verifyDisplayName(display.getText(), false);
            } else if (textControl == display) {
                verifyDisplayName(text, true);
            }

            setCanFinish(nameValid && displayNameValid, err);

            if (beforeDisplayNameValid != displayNameValid
                    || beforeNameValid != nameValid) {
                // Force layout on whole section to ensure that there is room
                // for
                // error icon
                forceLayout();
            }
        }
    }

    /**
     * @param text
     */
    private void verifyDisplayName(String text, boolean updateName) {
        displayNameValid = true;

        boolean changed = false;
        if (updateName) {
            changed = updateNameFromDisplayName(text);
        }

        if (text.length() == 0
                && Xpdl2ModelUtil.shouldHaveDisplayName(getInput())) {
            displayNameValid = false;
            err = Messages.NamedElementSection_LabelEmpty;
        } else if (!text.trim().equals(text)) { //$NON-NLS-1$
            displayNameValid = false;
            err = Messages.NamedElementSection_LeadingTrailingSpaces1;
        } else {
            /* XPD-1717: DOn't care about duplicate labels anymore. */
            // EObject input = getInput();
            // if (Xpdl2ModelUtil
            // .getDuplicateDisplayNameObject(getInputContainer(),
            // input,
            // text) != null) {
            // displayNameValid = false;
            // err = getDuplicateNameMessage();
            // }
        }
        if (displayNameValid && changed && !nameValid) {
            displayNameValid = false;

            String origErr = err;
            err = Messages.NamedElementSection_InvalidGeneratedNameMessage;
            if (origErr != null) {
                err += " (" + origErr + ")."; //$NON-NLS-1$ //$NON-NLS-2$
            }
        }

        if (!displayNameValid) {
            displayLabel.setImage(Xpdl2UiPlugin.getDefault().getImageRegistry()
                    .get(Xpdl2UiPlugin.IMG_ERROR));
            displayLabel.setToolTipText(err);
        } else {
            displayLabel.setImage(null);
            displayLabel.setToolTipText(""); //$NON-NLS-1$
        }
    }

    /**
     * @param text
     */
    protected boolean updateNameFromDisplayName(String text) {
        boolean changed = false;
        if (name.getText().equals(NameUtil.getInternalName(display.getText(),
                false))) {
            String nameText = NameUtil.getInternalName(text, false);
            ignoreTextChanges = true;
            name.setText(nameText);
            ignoreTextChanges = false;
            verifyName(nameText);
            changed = true;
        }
        return changed;
    }

    private void verifyName(String nameText) {
        nameValid = true;
        if (!NameUtil.isValidName(nameText, true)) {
            nameValid = false;
            err = Messages.NamedElementSection_invalidNameErrorMessage;
        } else if (Xpdl2ModelUtil.getDuplicateNameObject(getInputContainer(),
                getInput(),
                nameText) != null) {
            nameValid = false;
            err = getDuplicateNameMessage();
        }
        if (!nameValid) {
            nameLabel.setImage(Xpdl2UiPlugin.getDefault().getImageRegistry()
                    .get(Xpdl2UiPlugin.IMG_ERROR));
            nameLabel.setToolTipText(err);
        } else {
            nameLabel.setImage(null);
            nameLabel.setToolTipText(""); //$NON-NLS-1$
        }
    }

    protected String getDuplicateNameMessage() {
        return Messages.NamedElementSection_NameNotUnique;
    }

    @Override
    public void setCanFinish(boolean canFinish, String cantFinishMessage) {
        this.canFinish = canFinish;
        this.cantFinishMessage = cantFinishMessage;

        String message;
        if (!this.canFinish && this.cantFinishMessage != null
                && cantFinishMessage.length() > 0) {
            message = this.cantFinishMessage;
        } else if (!this.subClassCanFinish
                && this.subClassCantFinishMessage != null
                && this.subClassCantFinishMessage.length() > 0) {
            message = this.subClassCantFinishMessage;
        } else {
            message = null;
        }

        super.setCanFinish(this.canFinish && this.subClassCanFinish, message);

        return;
    }

    public void setSubClassCanFinish(boolean subClassCanFinish,
            String subClassCantFinishMessage) {
        this.subClassCanFinish = subClassCanFinish;
        this.subClassCantFinishMessage = subClassCantFinishMessage;

        String message;
        if (!this.canFinish && this.cantFinishMessage != null
                && cantFinishMessage.length() > 0) {
            message = this.cantFinishMessage;
        } else if (!this.subClassCanFinish
                && this.subClassCantFinishMessage != null
                && this.subClassCantFinishMessage.length() > 0) {
            message = this.subClassCantFinishMessage;
        } else {
            message = null;
        }

        super.setCanFinish(this.canFinish && this.subClassCanFinish, message);

        return;
    }

    protected abstract Composite doCreateGeneralSection(Composite parent,
            XpdFormToolkit toolkit);

    protected abstract Composite doCreateDetailsSection(Composite parent,
            XpdFormToolkit toolkit);

    protected abstract void doRefreshDetailsSection();

    protected abstract void doRefreshImplementationSection();

    protected Command doGetDetailsCommand(Object obj) {
        return null;
    }

    protected void checkDuplicateName() {
    }

}
