/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
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
public class NamedElementSection extends AbstractFilteredTransactionalSection
        implements TextFieldVerifier {

    public static int NOMINAL_LEFT_MARGIN = 5;

    private Composite sectionParent;

    private boolean canFinish = true;

    protected CLabel displayLabel;

    protected Text display;

    protected CLabel nameLabel;

    protected Text name;

    protected boolean nameValid = true;

    protected boolean displayNameValid = true;

    protected String err = null;

    private GridLayout nameControlsParentLayout;

    private Composite sectionComposite;

    protected boolean ignoreTextChanges = false;

    public NamedElementSection() {
        super(Xpdl2Package.eINSTANCE.getNamedElement());
        setShouldUseExtraSpace(false);
        setMinimumHeight(SWT.DEFAULT);
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doCreateControls(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     * 
     * @param parent
     * @param toolkit
     * @return
     */
    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {

        sectionParent = parent;

        sectionComposite = toolkit.createComposite(parent);

        nameControlsParentLayout = new GridLayout(2, false);
        // The following setting keep the Label/Name controls in this section
        // same as those in SashDividedNamedElementSection. If you change these
        // then you should change
        // SashDividedNamedElementSection.createGeneralSection() to ensure that
        // they line up correctly (they have to be set up slightly different to
        // do so so compare visually to get it right!
        nameControlsParentLayout.marginTop =
                nameControlsParentLayout.marginHeight;
        sectionComposite.setLayout(nameControlsParentLayout);

        displayLabel =
                toolkit.createCLabel(sectionComposite,
                        Messages.NamedElementSection_displayNameLabel);
        displayLabel
                .setLayoutData(new GridData(GridData.VERTICAL_ALIGN_CENTER));

        display =
                toolkit.createText(sectionComposite,
                        "", "nameElement_displayName_text"); //$NON-NLS-1$ //$NON-NLS-2$
        display.setLayoutData(new GridData(GridData.FILL_HORIZONTAL
                | GridData.VERTICAL_ALIGN_CENTER));

        nameLabel =
                toolkit.createCLabel(sectionComposite,
                        Messages.NamedElementSection_nameLabel);
        nameLabel.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_CENTER));

        name =
                toolkit.createText(sectionComposite,
                        "", "nameElement_name_text"); //$NON-NLS-1$ //$NON-NLS-2$
        name.setLayoutData(new GridData(GridData.FILL_HORIZONTAL
                | GridData.VERTICAL_ALIGN_CENTER));

        manageControlUpdateOnDeactivate(name);

        manageControlUpdateOnDeactivate(display);

        updateVisibility();

        return sectionComposite;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    protected Command doGetCommand(Object obj) {
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
            }
        }
        return cmd;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     * 
     */
    @Override
    protected void doRefresh() {
        EObject input = getInput();
        if (input instanceof NamedElement) {
            NamedElement named = (NamedElement) input;

            // SID - Update display name first because of the way verifyText()
            // works this will prevent an unnecessary forceLayout.
            if (display != null && !display.isDisposed()) {
                String displayName =
                        (String) Xpdl2ModelUtil.getOtherAttribute(named,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_DisplayName());
                if (displayName == null) {
                    displayName = ""; //$NON-NLS-1$
                }
                updateText(display, displayName);
                // XPD-55
                display.setSelection(0, displayName.length());
            }

            if (name != null && !name.isDisposed()) {
                updateText(name, named.getName());
            }
        }

        updateVisibility();
    }

    protected void updateVisibility() {
        //        System.out.println("Update visibility"); //$NON-NLS-1$
        boolean tokenNameVisible = CapabilityUtil.isDeveloperActivityEnabled();

        if (name.isDisposed()) {
            //            System.out.println("   <== disposed"); //$NON-NLS-1$
            return;
        }

        boolean changed =
                (name.getVisible() != tokenNameVisible)
                        || (nameLabel.getVisible() != tokenNameVisible);

        nameLabel.setVisible(tokenNameVisible);
        name.setVisible(tokenNameVisible);

        //
        // Don't attempt any fancy layout stuff until text control has a size
        // (otherwise the layouts when it does have a size seem to be ignored.
        if (display.getSize().y < 1) {
            //            System.out.println("   <== labelsize < 1"); //$NON-NLS-1$
            return;
        }

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

            // Setting min height of section isn't quite enough (because the
            // TabContents control only uses it when initially setting up the
            // sections in page (for the minHeight of a grid control)).
            //
            // So we have to do something really nasty! and set the minimum
            // height assuming that our parent has a grid layout (so this may
            // change).
            if (getSectionContainerType() == ContainerType.PROPERTYVIEW) {
                // For properties view then TabContents puts our sectionRoot in
                // another composite within its parent's grid layout.
                if (sectionParent.getLayoutData() instanceof GridData) {
                    ((GridData) sectionParent.getLayoutData()).heightHint =
                            minSectHeight;
                }
            } else {
                // For wizard, there's one level of composites less.
                if (sectionComposite.getLayoutData() instanceof GridData) {
                    GridData gd = new GridData(GridData.FILL_HORIZONTAL);
                    gd.heightHint = minSectHeight;
                    sectionComposite.setLayoutData(gd);
                }
            }

            //            System.out.println("       forcing layout"); //$NON-NLS-1$

            forceLayout();
            //            System.out.println("   <== done"); //$NON-NLS-1$

        } else {
            //            System.out.println("   <== not changed"); //$NON-NLS-1$

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
                // for error icon
                // if it is a reserved word then we dont want to force layout as
                // it can
                // cause another dorefresh which in turn inserts another char
                // into the name field
                // leaving it out of sync with label.
                String nameText =
                        NameUtil.getInternalName(text, !allowLeadingNumerics());
                if (!isReservedWord(nameText) || getReservedPrefix(nameText) == null) {
                    forceLayout();
                }
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

        EObject duplicate;
        if (text.length() == 0
                && Xpdl2ModelUtil.shouldHaveDisplayName(getInput())) {
            displayNameValid = false;
            err = Messages.NamedElementSection_LabelEmpty;
        } else if (!text.trim().equals(text)) { //$NON-NLS-1$
            displayNameValid = false;
            err = Messages.NamedElementSection_LeadingTrailingSpaces1;
        } else {
            /* XPD-1717: No longer complain about duplicate labels. */

            // EObject input = getInput();
            // if ((duplicate =
            // Xpdl2ModelUtil
            // .getDuplicateDisplayNameObject(getInputContainer(),
            // input,
            // text)) != null) {
            // displayNameValid = false;
            // err = getDuplicateDisplayNameMesage(duplicate);
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
            if (displayLabel.getImage() == null) {
                /* Need to reset the layout data. */
                displayLabel.setLayoutData(new GridData(
                        GridData.VERTICAL_ALIGN_CENTER));
            }
            displayLabel.setImage(Xpdl2UiPlugin.getDefault().getImageRegistry()
                    .get(Xpdl2UiPlugin.IMG_ERROR));
            displayLabel.setToolTipText(err);
        } else {
            if (displayLabel.getImage() != null) {
                /* Need to reset the layout data. */
                displayLabel.setLayoutData(new GridData(
                        GridData.VERTICAL_ALIGN_CENTER));
            }
            displayLabel.setImage(null);
            displayLabel.setToolTipText(""); //$NON-NLS-1$
        }
    }

    /**
     * @param duplicate
     * @return
     */
    protected String getDuplicateDisplayNameMesage(EObject duplicate) {
        return Messages.NamedElementSection_LabelNotUnique;
    }

    protected boolean updateNameFromDisplayName(String text) {
        boolean changed = false;
        if (name.getText().equals(NameUtil.getInternalName(display.getText(),
                !allowLeadingNumerics()))) {
            String nameText =
                    NameUtil.getInternalName(text, !allowLeadingNumerics());
            ignoreTextChanges = true;
            name.setText(nameText);
            ignoreTextChanges = false;
            verifyName(nameText);
            changed = true;
        }
        return changed;
    }

    /**
     * Verify that the name text is valid.
     * 
     * @param nameText
     */
    protected void verifyName(String nameText) {
        nameValid = true;
        EObject duplicate;

        /*
         * XPD-6949: Saket: We don't care about whether the element container
         * should have a token name or not. We need to call
         * Xpdl2ModelUtil.shouldHaveTokenName() for the element itself.
         */
        if ((nameText == null || nameText.length() == 0)
                && requiresTokenName(getInput())) {
            nameValid = false;
            err = Messages.NamedElementSection_NameEmpty;
        } else if (!NameUtil.isValidName(nameText, allowLeadingNumerics())) {
            nameValid = false;
            if (allowLeadingNumerics()) {
                err = Messages.NamedElementSection_invalidNameErrorMessage;
            } else {
                err =
                        Messages.NamedElementSection_invalidNameNumericErrorMessage;
            }
        } else if ((duplicate =
                Xpdl2ModelUtil.getDuplicateNameObject(getInputContainer(),
                        getInput(),
                        nameText)) != null) {
            nameValid = false;
            err = getDuplicateNameMessage(duplicate);
        } else {
            if (isReservedWord(nameText)) {
                err = Messages.NamedElementSection_NameReservedWord;
                nameValid = false;
            } else {
                /*
                 * Sid ACE-118 also prevent names with reserved prefixes.
                 */
                String reservedPrefix = getReservedPrefix(nameText);

                if (reservedPrefix != null) {
                    err = String.format(Messages.NamedElementSection__ReservedPrefix_longdesc, nameText);
                    nameValid = false;
                }

            }
        }
        if (!nameValid) {
            if (nameLabel.getImage() == null) {
                /* Need to reset the layout data. */
                nameLabel.setLayoutData(new GridData(
                        GridData.VERTICAL_ALIGN_CENTER));
            }
            nameLabel.setImage(Xpdl2UiPlugin.getDefault().getImageRegistry()
                    .get(Xpdl2UiPlugin.IMG_ERROR));
            nameLabel.setToolTipText(err);
        } else {
            if (nameLabel.getImage() != null) {
                /* Need to reset the layout data. */
                nameLabel.setLayoutData(new GridData(
                        GridData.VERTICAL_ALIGN_CENTER));
            }
            nameLabel.setImage(null);
            nameLabel.setToolTipText(""); //$NON-NLS-1$
        }

    }

    /**
     * @return <code>true</code> if we need to verify the name. Override this
     *         method in subclasses if we know that the element requires token
     *         name.
     */
    protected boolean requiresTokenName(EObject input) {

        return Xpdl2ModelUtil.shouldHaveTokenName(input);
    }

    /**
     * @param nameText
     * @return <code>true</code> if the chosen name is a reserved word.
     */
    protected boolean isReservedWord(String nameText) {
        return false;
    }

    /**
     * @param nameText
     * @return <code>String reserved-prefix</code> if the name starts with a
     *         reserved prefix.
     */
    protected String getReservedPrefix(String nameText) {
        return null;
    }

    protected boolean allowLeadingNumerics() {
        return true;
    }

    protected String getDuplicateNameMessage(EObject duplicate) {
        return Messages.NamedElementSection_NameNotUnique;
    }

    @Override
    public void setCanFinish(boolean canFinish, String cantFinishMessage) {
        this.canFinish = canFinish;
        super.setCanFinish(canFinish, cantFinishMessage);
    }

    public boolean canFinish() {
        return canFinish;
    }
}
