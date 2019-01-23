/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.properties.description;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.forms.widgets.FormText;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.DescribedElement;
import com.tibco.xpd.xpdl2.Description;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.edit.ui.internal.Messages;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Process related element description section.
 * 
 * 
 * @author aallway
 * @since 25 Oct 2012
 */
public abstract class AbstractDescriptionSection extends
        AbstractFilteredTransactionalSection {

    private Text txtDescription = null;

    private Text txtDocumentationUrl;

    private FormText documentUrlLabel;

    private Button openDocumentationUrl;

    private static final String URL_HYPERLINK_HREF = "Url.HyperLink"; //$NON-NLS-1$

    private static final String URL_HYPERLINK_FORMATSTR =
            "<text><p><a href='%s'>%s</a>:</p></text>"; //$NON-NLS-1$

    /**
     * @param eClass
     */
    public AbstractDescriptionSection(EClass eClass) {
        super(eClass);
    }

    /**
     * @return The described element that should be used to read/write from.
     */
    protected abstract DescribedElement getDescribedElement();

    /**
     * @return If necessary, append commands to given command to create the
     *         DescribedElement
     */
    protected abstract DescribedElement getOrCreateDescribedElement(
            EditingDomain editingDomain, CompoundCommand cmd);

    /**
     * @return Should editing be enabled?
     */
    protected abstract boolean canEdit();

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.properties.BaseXpdSection#doCreateControls(org.eclipse
     * .swt.widgets.Composite, com.tibco.xpd.ui.properties.XpdFormToolkit)
     */
    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = toolkit.createComposite(parent);
        root.setLayout(new GridLayout(3, false));

        documentUrlLabel = toolkit.createFormText(root, true);
        documentUrlLabel.setText(String.format(URL_HYPERLINK_FORMATSTR,
                URL_HYPERLINK_HREF,
                Messages.DescriptionSection_DocURL_label2), true, false);
        documentUrlLabel
                .setToolTipText(Messages.AbstractDescriptionSection_OpenURL_tooltip);

        documentUrlLabel.setLayoutData(new GridData(
                GridData.VERTICAL_ALIGN_CENTER));
        manageControl(documentUrlLabel);

        Composite urlContainer = toolkit.createComposite(root);
        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        urlContainer.setLayoutData(gd);
        urlContainer.setLayout(new ConstrictedLayout());

        txtDocumentationUrl = toolkit.createText(urlContainer, "", SWT.None); //$NON-NLS-1$
        txtDocumentationUrl
                .setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        manageControl(txtDocumentationUrl);

        openDocumentationUrl = toolkit.createButton(root, "", SWT.PUSH); //$NON-NLS-1$
        openDocumentationUrl.setImage(Xpdl2ResourcesPlugin.getDefault()
                .getImageRegistry().get(Xpdl2ResourcesConsts.ICON_WEB_BROWSER));
        openDocumentationUrl
                .setToolTipText(Messages.AbstractDescriptionSection_OpenURL_tooltip);

        openDocumentationUrl.setLayoutData(new GridData(
                GridData.VERTICAL_ALIGN_CENTER));
        manageControl(openDocumentationUrl);

        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 3;

        Label descLabel =
                toolkit.createLabel(root,
                        Messages.DescriptionSection_description_label);
        descLabel.setLayoutData(gd);

        /*
         * Wrap up the scrollable text control in a composite that prevents
         * copntrol sizing to size of text. (i.e. make sure it's size is
         * restricted so it wraps,
         */
        Composite descContainer = toolkit.createComposite(root);
        gd = new GridData(GridData.FILL_BOTH);
        gd.horizontalSpan = 3;
        descContainer.setLayoutData(gd);

        descContainer.setLayout(new ConstrictedLayout());

        txtDescription =
                toolkit.createText(descContainer,
                        "", SWT.MULTI | SWT.WRAP | SWT.V_SCROLL); //$NON-NLS-1$
        manageControl(txtDescription);

        return root;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.properties.BaseXpdSection#doGetCommand(java.lang.Object)
     */
    @Override
    public Command doGetCommand(Object obj) {

        if (obj.equals(txtDocumentationUrl)) {
            CompoundCommand cmd = new CompoundCommand();
            cmd.setLabel(Messages.DescriptionSection_SetDocURL_menu);

            DescribedElement describedElement =
                    getOrCreateDescribedElement(getEditingDomain(), cmd);

            if (describedElement != null) {

                Description desc = describedElement.getDescription();
                if (desc == null) {
                    desc = Xpdl2Factory.eINSTANCE.createDescription();

                    cmd.append(SetCommand.create(getEditingDomain(),
                            describedElement,
                            Xpdl2Package.eINSTANCE
                                    .getDescribedElement_Description(),
                            desc));
                }

                cmd.append(Xpdl2ModelUtil
                        .getSetOtherAttributeCommand(getEditingDomain(),
                                desc,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_DocumentationURL(),
                                txtDocumentationUrl.getText()));
                return cmd;

            }
        } else if (obj.equals(txtDescription)) {
            CompoundCommand cmd = new CompoundCommand();
            cmd.setLabel(Messages.DescriptionSection_SetDesc_menu);

            DescribedElement describedElement =
                    getOrCreateDescribedElement(getEditingDomain(), cmd);

            if (describedElement != null) {

                Description desc = describedElement.getDescription();
                if (desc == null) {
                    desc = Xpdl2Factory.eINSTANCE.createDescription();

                    cmd.append(SetCommand.create(getEditingDomain(),
                            describedElement,
                            Xpdl2Package.eINSTANCE
                                    .getDescribedElement_Description(),
                            desc));
                }

                cmd.append(SetCommand.create(getEditingDomain(),
                        desc,
                        Xpdl2Package.eINSTANCE.getDescription_Value(),
                        txtDescription.getText()));
                return cmd;
            }

        } else if (URL_HYPERLINK_HREF.equals(obj)
                || obj == openDocumentationUrl) {
            String documentationURL = txtDocumentationUrl.getText();

            if (documentationURL != null && documentationURL.length() > 0) {
                DocumentationURLBrowserUtil
                        .launchBrowserForElement(Xpdl2ModelUtil
                                .getAncestor(getInput(), NamedElement.class),
                                documentationURL);
            }
        }

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.ui.properties.BaseXpdSection#doRefresh()
     */
    @Override
    protected void doRefresh() {

        if (txtDescription == null || txtDescription.isDisposed()) {
            return;
        }

        DescribedElement describedElement = getDescribedElement();

        if (describedElement == null) {
            updateText(txtDescription, ""); //$NON-NLS-1$
            updateText(txtDocumentationUrl, ""); //$NON-NLS-1$
            return;
        }

        Description desc = describedElement.getDescription();

        boolean canEdit = canEdit();

        txtDescription.setEditable(canEdit);
        txtDocumentationUrl.setEditable(canEdit);

        // Update the description from the model
        if (desc != null) {
            updateText(txtDescription, desc.getValue());
        } else {
            updateText(txtDescription, ""); //$NON-NLS-1$
        }

        String docURL =
                (String) Xpdl2ModelUtil.getOtherAttribute(desc,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_DocumentationURL());
        if (docURL != null) {
            updateText(txtDocumentationUrl, docURL);
        } else {
            updateText(txtDocumentationUrl, ""); //$NON-NLS-1$
        }
    }

    @Override
    public void setInput(IWorkbenchPart part, ISelection selection) {
        super.setInput(part, selection);
    }

    /**
     * Returns help context for sections to show individual help details
     * 
     * @return
     */
    public String getHelpContextID() {
        return "com.tibco.xpd.process.analyst.doc.Description"; //$NON-NLS-1$
    }

    /**
     * Constricts width of wrapping text fields so that they don't size
     * themselves to the size of the text and hence not wrap.
     * 
     * @author aallway
     * @since 26 Oct 2012
     */
    private final class ConstrictedLayout extends Layout {
        @Override
        protected void layout(Composite composite, boolean flushCache) {
            Point sz = composite.getSize();
            composite.getChildren()[0].setBounds(0, 0, sz.x, sz.y);
        }

        @Override
        protected Point computeSize(Composite composite, int wHint, int hHint,
                boolean flushCache) {
            int width = 1;
            if (wHint > 0) {
                width = wHint;
            }
            int height;
            if (hHint == SWT.DEFAULT) {
                height =
                        composite.getChildren()[0].computeSize(SWT.DEFAULT,
                                hHint).y;
            } else {
                height = hHint;
            }

            return new Point(width, height);
        }
    }

}
