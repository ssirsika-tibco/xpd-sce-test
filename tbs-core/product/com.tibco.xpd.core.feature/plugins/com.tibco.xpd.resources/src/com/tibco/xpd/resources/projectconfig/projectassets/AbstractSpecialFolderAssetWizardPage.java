/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.resources.projectconfig.projectassets;

import java.beans.PropertyChangeEvent;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.IWorkbenchHelpContextIds;

import com.tibco.xpd.resources.internal.Messages;

/**
 * Abstract asset configuration wizard page for the new XPD project wizard. This
 * can be used by the asset contributors to display a special folder input.
 * Subclasses can extend this to provide more controls on the page using method
 * {@link #createMainContent(Composite) createMainContent}. If more content is
 * added to the page then you may want to extend method {@link #validatePage()
 * validatePage} to add validation for the extra content (remember to call the
 * super method).
 * 
 * <p>
 * Call method {@link #getSpecialFolderName() getSpecialFolderName} to get the
 * name of the folder entered in the page.
 * </p>
 * 
 * @author njpatel
 * 
 */
public abstract class AbstractSpecialFolderAssetWizardPage extends WizardPage
        implements IAssetWizardPage, IAssetProjectPropertyChangeListener {

    /**
     * The recommended width hint of labels on this page.
     */
    protected static final int TEXT_WIDTH_HINT = 150;

    private Object config;

    private Text txtFolder;

    private String folderName;

    /**
     * Constructor.
     */
    public AbstractSpecialFolderAssetWizardPage(String id) {
        super(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.newproject.assets.IAssetWizardPage#init(java.lang.Object
     * )
     */
    @Override
    public void init(Object config) {
        this.config = config;

        folderName = getDefaultSpecialFolderName();

        // If no default folder name specified then set page to incomplete
        setPageComplete(folderName != null && folderName.length() > 0);
    }

    /**
     * Get the asset configuration object.
     * 
     * @return configuration object
     */
    protected Object getConfiguration() {
        return config;
    }

    /**
     * Get the name of the special folder entered in the page.
     * 
     * @return name
     */
    protected String getSpecialFolderName() {
        return folderName;
    }

    /**
     * Creates the top level control for this dialog page under the given parent
     * composite.
     * <p>
     * Implementors are responsible for ensuring that the created control can be
     * accessed via getControl
     * </p>
     * 
     * @see IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    @Override
    public final void createControl(Composite parent) {
        Composite composite = new Composite(parent, SWT.NULL);
        composite.setFont(parent.getFont());
        initializeDialogUnits(parent);
        composite.setLayout(new GridLayout());

        // Add special folder content
        Composite folderContent = createSpecialFolderContent(composite);
        if (folderContent != null) {
            folderContent.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        }

        // Add main content
        Composite mainContent = createMainContent(composite);

        if (mainContent != null) {
            mainContent.setLayoutData(new GridData(GridData.FILL_BOTH));
        }

        setControl(composite);

        // Validate the page
        setPageComplete(validatePage());
    }

    /**
     * Create the special folder container
     * 
     * @param parent
     * @return
     */
    private Composite createSpecialFolderContent(Composite parent) {
        GridData gData;
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayout(new GridLayout(2, false));

        Label lbl = new Label(composite, SWT.NONE);
        lbl.setLayoutData(new GridData());
        lbl.setText(Messages.AbstractSpecialFolderAssetWizardPage_folder_shortdesc);

        txtFolder = new Text(composite, SWT.BORDER);
        gData = new GridData(GridData.FILL_HORIZONTAL);
        gData.widthHint = TEXT_WIDTH_HINT;
        txtFolder.setLayoutData(gData);
        // Set the default folder name
        txtFolder.setText(folderName != null ? folderName : ""); //$NON-NLS-1$

        txtFolder.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(ModifyEvent e) {
                // Update the folder name
                folderName = txtFolder.getText();

                // validate the page
                setPageComplete(validatePage());
            }
        });

        return composite;
    }

    /**
     * Create the main area of the wizard page. The default implementation
     * returns null. Subclasses can override this to add more content to the
     * wizard page.
     * 
     * @param parent
     * @return main content area.
     */
    protected Composite createMainContent(Composite parent) {
        return null;
    }

    /**
     * Get the default special folder name
     */
    protected abstract String getDefaultSpecialFolderName();

    /**
     * Validate the entry on this page. This method can be extended by
     * subclasses to add validation but should call this super method to
     * validate the special folder entry.
     * 
     * @return <b>true</b> if all entries valid.
     */
    protected boolean validatePage() {
        String errMsg = null;

        if (folderName != null && !folderName.equals("")) { //$NON-NLS-1$
            IWorkspace workspace = ResourcesPlugin.getWorkspace();
            IStatus status = Status.OK_STATUS;
            String[] segments = new Path(folderName).segments();

            // Validate each segment
            for (int x = 0; x < segments.length && status.isOK(); x++) {
                status = workspace.validateName(segments[x], IResource.FOLDER);
            }

            if (status != null && status.getCode() != IStatus.OK) {
                errMsg = status.getMessage();
            }
        } else {
            errMsg =
                    Messages.AbstractSpecialFolderAssetWizardPage_folderNotSpecified_shortdesc;
        }

        // Update the error message
        setErrorMessage(errMsg);

        return errMsg == null;
    }

    /**
     * Sid XPD-2139 Called by asset config page when this asset page is
     * initially loaded or when the user changes the project name after it is
     * loaded.
     * 
     * @see com.tibco.xpd.resources.projectconfig.projectassets.IAssetProjectPropertyChangeListener#projectPropertyChanged(java.beans.PropertyChangeEvent)
     * 
     * @param event
     */
    @Override
    public void projectPropertyChanged(PropertyChangeEvent event) {
    }

    /*
     * SCF-128: We need to add following two methods from AbstractXpdWizardPage
     * here explicitly. We cannot extend this class from AbstractXpdWizardPage
     * since it is in xpd.resources.ui plugin and this plugin is already
     * depended by the former one.
     */

    /**
     * Returns specific Help page context ID. Extending class should implement
     * this properly. Default implementation does nothing.
     * 
     * @return
     */
    protected String getHelpContextId() {
        return null;
    }

    /**
     * @see org.eclipse.jface.dialogs.DialogPage#performHelp() Extednig classes
     */
    @SuppressWarnings("restriction")
    @Override
    public void performHelp() {
        String contextId = getHelpContextId();
        if (contextId == null) {
            contextId = IWorkbenchHelpContextIds.MISSING;
        }

        PlatformUI.getWorkbench().getHelpSystem().displayHelp(contextId);
    }

	/**
	 * Sid ACE-8813 Allows individual resource asset pages to prevent ProjectSelectionPage from allowing finish before
	 * this page is complete.
	 * 
	 * Most pages this is ok for as there is always a default for the initial project asset creation. But sometimes
	 * further user input is required on asset page and hence we shouldnot allow wizard finish directly from the project
	 * name entry page.
	 * 
	 * @return <code>true</code> if the project selection page should not be allowed to complete unless this page is
	 *         complete.
	 */
	public boolean validityRequiredForProjectCreate()
	{
		return false;
	}

}
