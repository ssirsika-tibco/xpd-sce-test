/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.part.custom.firstclassprofiles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardNode;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import com.tibco.xpd.bom.modeler.diagram.part.BOMDiagramEditorPlugin;
import com.tibco.xpd.bom.modeler.diagram.part.Messages;
import com.tibco.xpd.bom.resources.firstclassprofiles.FirstClassProfileManager;
import com.tibco.xpd.bom.resources.firstclassprofiles.IFirstClassProfileExtension;
import com.tibco.xpd.bom.resources.utils.BOMUtils;
import com.tibco.xpd.fragments.IFragment;
import com.tibco.xpd.fragments.wizard.TemplatesWizardPage;
import com.tibco.xpd.ui.dialogs.AbstractXpdSelectionWizardPage;

/**
 * Wizard page that allows the user to select the type of Business Object Model
 * (first-class profiles).
 * 
 * @author robg
 * 
 */
public class BOMTypesWizardPage extends AbstractXpdSelectionWizardPage {

    private Button[] radiosBOMType;

    private String extId = null;

    private final List<Image> images;

    private static final String BOM_IMAGE_PATH = "icons/obj16/wizard.png"; //$NON-NLS-1$

    private final ButtonSelectionListener listener;

    private final BOMWizardNode wizardNode;

    public BOMTypesWizardPage(String pageName) {
        super(pageName);
        images = new ArrayList<Image>();
        listener = new ButtonSelectionListener();
        wizardNode = new BOMWizardNode();
    }

    /**
     * Check if the user decided to apply a template. Only available when
     * generic BOM type is selected.
     * 
     * @return <code>true</code> if user decided to apply template
     */
    public boolean isApplyTemplate() {
        IWizardNode node = getSelectedNode();
        if (node != null && node.getWizard() instanceof BOMWizard) {
            return ((BOMWizard) node.getWizard()).isApplyTemplate();
        }
        return false;
    }

    /**
     * Get the template to apply if any. This is only applicable if the generic
     * BOM type is selected.
     * 
     * @return
     */
    public IFragment getTemplate() {
        IWizardNode node = getSelectedNode();
        if (node != null && node.getWizard() instanceof BOMWizard) {
            return ((BOMWizard) node.getWizard()).getTemplate();
        }
        return null;
    }

    @Override
    public void createControl(Composite parent) {
        initializeDialogUnits(parent);
        // top level group
        Composite topLevel = new Composite(parent, SWT.NONE);
        topLevel.setLayout(new GridLayout());
        topLevel.setFont(parent.getFont());

        // Add radio buttons to select BOM model type, OR not

        List<IFirstClassProfileExtension> profileExts =
                FirstClassProfileManager.getInstance().getExtensions();
        Set<String> excludedExts = Collections.<String>singleton(BOMUtils.CDS_EXTENSION_ID);

        profileExts =
                BOMUtils.getCreatableProfileExtensions(profileExts,
                        excludedExts);
        if ((profileExts != null) && !profileExts.isEmpty()) {

            // label and group for BOM type field
            Group grp = new Group(topLevel, SWT.NONE);
            grp.setText(Messages.UMLCreationBOMTypeWizardPage_GroupBOMTypes_label);
            grp.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

            GridLayout grpGridLayout = new GridLayout();
            grpGridLayout.marginHeight = 8;
            grpGridLayout.marginWidth = 25;
            grpGridLayout.horizontalSpacing = 8;
            grpGridLayout.verticalSpacing = 8;
            grpGridLayout.marginRight = 0;
            grp.setLayout(grpGridLayout);

            // Default BOM type option
            radiosBOMType = new Button[profileExts.size() + 1];
            radiosBOMType[0] = new Button(grp, SWT.RADIO);
            radiosBOMType[0]
                    .setText(Messages.UMLCreationBOMTypeWizardPage_BOMType_label);

            ImageDescriptor desc =
                    BOMDiagramEditorPlugin
                            .getBundledImageDescriptor(BOM_IMAGE_PATH);
            if (desc != null) {
                Image img = desc.createImage();
                images.add(img);
                radiosBOMType[0].setImage(img);
            }
            radiosBOMType[0].addSelectionListener(listener);
            radiosBOMType[0].setSelection(true);
            setNode(wizardNode);

            // Other BOM types
            int idx = 1;
            for (IFirstClassProfileExtension ext : profileExts) {
                Button btn = new Button(grp, SWT.RADIO);
                btn.setText(ext.getLabel());
                btn.setData(ext.getId());

                ImageDescriptor icon = ext.getIcon();
                if (icon != null) {
                    Image img = icon.createImage();
                    btn.setImage(img);
                    images.add(img);
                }

                btn.addSelectionListener(listener);
                radiosBOMType[idx++] = btn;
            }
        }

        setPageComplete(true);
        setControl(topLevel);
    }

    /**
     * Get the id of the selected bom model type.
     * 
     * @return id if a first-class profile model is selected, <code>null</code>
     *         if generic BOM is selected.
     */
    public String getSelectedExtensionId() {
        return extId;
    }

    @Override
    public void dispose() {
        for (Image img : images) {
            img.dispose();
        }
        images.clear();
        super.dispose();
    }

    @Override
    public boolean isPageComplete() {
        boolean complete = super.isPageComplete();

        if (complete && getSelectedNode() != null) {
            IWizard wizard = getSelectedNode().getWizard();
            if (wizard != null) {
                complete = wizard.canFinish();
            }
        }

        return complete;
    }

    private void setNode(IWizardNode node) {
        setSelectedNode(node);
        if (node != null) {
            IWizard wizard = node.getWizard();
            if (wizard != null) {
                wizard.setContainer(getContainer());
            }
        }
    }

    /**
     * Button selection adapter that will listen for model type selection.
     * 
     * @author njpatel
     * 
     */
    private class ButtonSelectionListener extends SelectionAdapter {
        @Override
        public void widgetSelected(SelectionEvent e) {
            if (e.widget instanceof Button
                    && ((Button) e.widget).getSelection()) {
                Object data = e.widget.getData();
                if (data instanceof String) {
                    extId = (String) data;
                    setNode(null);
                } else {
                    extId = null;
                    setNode(wizardNode);
                }
            }
        }
    }

    /**
     * Wizard node set when generic BOM is selected. This will include the
     * fragment (template) selection page.
     * 
     * @author njpatel
     * 
     */
    private class BOMWizardNode implements IWizardNode {

        private final BOMWizard wizard;

        public BOMWizardNode() {
            wizard = new BOMWizard();
        }

        @Override
        public void dispose() {
            wizard.dispose();
        }

        @Override
        public Point getExtent() {
            return new Point(-1, -1);
        }

        @Override
        public IWizard getWizard() {
            return wizard;
        }

        @Override
        public boolean isContentCreated() {
            return wizard.isCreated();
        }
    }

    /**
     * Wizard for the wizard node that adds the template page. This is used when
     * the generic BOM type is selected.
     * 
     * @author njpatel
     * 
     */
    private class BOMWizard extends Wizard {
        private boolean isCreated = false;

        private TemplatesWizardPage templatesPage;

        public boolean isCreated() {
            return isCreated;
        }

        @Override
        public IWizardPage[] getPages() {
            if (!isCreated) {
                // If pages have not been loaded then do so
                addPages();
            }
            return super.getPages();
        }

        /**
         * Check if a template should be applied
         * 
         * @return
         */
        public boolean isApplyTemplate() {
            return templatesPage != null && templatesPage.applyTemplate();
        }

        /**
         * Get the template (if selected) to apply.
         * 
         * @return
         */
        public IFragment getTemplate() {
            return templatesPage != null ? templatesPage.getTemplate() : null;
        }

        @Override
        public void addPages() {
            templatesPage =
                    new TemplatesWizardPage(
                            "templates", //$NON-NLS-1$
                            Messages.UMLCreationWizard_templatesPage_title,
                            null,
                            new String[] { "com.tibco.xpd.bom.fragments" });
            templatesPage
                    .setDescription(Messages.UMLCreationWizard_templatesPage_shortdesc);
            templatesPage.setSelectTemplateCheck(false);

            addPage(templatesPage);

            // Update wizard title
            IWizard parentWizard = BOMTypesWizardPage.this.getWizard();
            if (parentWizard != null) {
                setWindowTitle(parentWizard.getWindowTitle());
            }

            isCreated = true;
        }

        @Override
        public boolean performFinish() {
            // Main wizard will perform the operation
            return true;
        }
    }
}
