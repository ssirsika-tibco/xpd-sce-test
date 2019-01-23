/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.bom.gen.ui.preferences;

import java.util.Collection;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceProxy;
import org.eclipse.core.resources.IResourceProxyVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbenchPropertyPage;

import com.tibco.xpd.bom.gen.ui.Activator;
import com.tibco.xpd.bom.gen.ui.internal.Messages;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.resources.builder.BuildJob;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.SpecialFolderUtil;

/**
 * Project Property page for BOM generation settings.
 * 
 * Added to support XPD-5145.
 *
 * @author nwilson
 * @since 23 Nov 2015
 */
public class BomGenProjectPropertyPage extends PreferencePage implements
        IWorkbenchPropertyPage {

    private IProject project;

    private boolean keepNamespaceFileExtensionFlag;

    private Button keepNamespaceFileExtension;

    private BomGenPropertyStore store;

    /**
     * Default constructor sets an empty title.
     */
    public BomGenProjectPropertyPage() {
        super(""); //$NON-NLS-1$
    }

    /**
     * @param title
     *            The property page title.
     */
    public BomGenProjectPropertyPage(String title) {
        super(title, null);
    }

    /**
     * @param title
     *            The property page title.
     * @param image
     *            The property page image.
     */
    public BomGenProjectPropertyPage(String title, ImageDescriptor image) {
        super(title, image);
        noDefaultAndApplyButton();
    }

    /**
     * @see org.eclipse.ui.IWorkbenchPropertyPage#getElement()
     *
     * @return The project.
     */
    @Override
    public IAdaptable getElement() {
        return project;
    }

    /**
     * @see org.eclipse.ui.IWorkbenchPropertyPage#setElement(org.eclipse.core.runtime.IAdaptable)
     *
     * @param element
     *            The project.
     */
    @Override
    public void setElement(IAdaptable element) {
        project = (IProject) element.getAdapter(IProject.class);
        store = new BomGenPropertyStore(project, null);
    }

    /**
     * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
     *
     * @param parent
     *            The parent composite.
     * @return The page root composite.
     */
    @Override
    protected Control createContents(Composite parent) {
        Composite root = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout(2, false);
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        root.setLayout(layout);

        keepNamespaceFileExtension = new Button(root, SWT.CHECK);
        keepNamespaceFileExtension.setLayoutData(new GridData(SWT.CENTER,
                SWT.CENTER, false, false));
        Label keepNamespaceFileExtensionLabel = new Label(root, SWT.NONE);
        keepNamespaceFileExtensionLabel.setLayoutData(new GridData(SWT.LEAD,
                SWT.CENTER, true, false));
        keepNamespaceFileExtensionLabel
                .setText(Messages.BomGenProjectPropertyPage_keepNamespaceFileExtension);

        keepNamespaceFileExtensionFlag =
                Boolean.valueOf(store
                        .getProjectProperty(BOMResourcesPlugin.P_KEEP_NAMESPACE_FILE_EXTENSION_BOM_GENERATION));
        keepNamespaceFileExtension.setSelection(keepNamespaceFileExtensionFlag);

        Label lbl = new Label(root, SWT.WRAP);
        lbl.setText(Messages.BomGenProjectPropertyPage_ProjectRebuildNote);
        lbl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

        return root;
    }

    /**
     * Sets the project properties and rebuilds the project.
     * 
     * @see org.eclipse.jface.preference.PreferencePage#performOk()
     *
     * @return true.
     */
    @Override
    public boolean performOk() {
        boolean newKeepNamespaceFileExtensionFlag =
                keepNamespaceFileExtension.getSelection();
        if (keepNamespaceFileExtensionFlag != newKeepNamespaceFileExtensionFlag) {
            store.setProjectProperty(BOMResourcesPlugin.P_KEEP_NAMESPACE_FILE_EXTENSION_BOM_GENERATION,
                    Boolean.toString(newKeepNamespaceFileExtensionFlag));

            // Delete generated BOMs
            Collection<SpecialFolder> specialFolders =
                    SpecialFolderUtil.getGeneratedSpecialFoldersOfKind(project,
                            BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND,
                            null);
            final String bomFileExtension =
                    "." + BOMResourcesPlugin.BOM_FILE_EXTENSION; //$NON-NLS-1$
            for (SpecialFolder specialFolder : specialFolders) {
                IFolder folder = specialFolder.getFolder();
                try {
                    folder.accept(new IResourceProxyVisitor() {

                        @Override
                        public boolean visit(IResourceProxy proxy)
                                throws CoreException {
                            if (proxy.getName().endsWith(bomFileExtension)) {
                                proxy.requestResource().delete(true, null);
                            }
                            return true;
                        }
                    }, IResource.NONE);
                } catch (CoreException e) {
                    Activator.getDefault().getLogger().error(e);
                }
            }

            // Rebuild
            BuildJob job =
                    new BuildJob(
                            Messages.BomGenProjectPropertyPage_ProjectBuildMessage,
                            project);
            job.setRule(ResourcesPlugin.getWorkspace().getRuleFactory()
                    .buildRule());
            job.schedule();
        }
        return super.performOk();
    }
}
