package com.tibco.xpd.om.modeler.diagram.part;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import com.tibco.xpd.om.core.om.util.OMUtil;
import com.tibco.xpd.om.resources.OMResourcesActivator;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.ui.dialogs.WizardNewFileInSpecialFolderCreationPage;

/**
 * @generated NOT
 */
public class OrganizationModelCreationWizardPage extends
        WizardNewFileInSpecialFolderCreationPage {

    /**
     * @generated
     */
    private final String fileExtension;

    private Button createDefaultMMCheckbox;

    private Button createDefaultMMandApplyCheckBox;

    /**
     * @generated
     */
    public OrganizationModelCreationWizardPage(String pageName,
            IStructuredSelection selection, String fileExtension) {
        super(pageName, selection);
        this.fileExtension = fileExtension;

        // Add selection validator to warn user if file not created in BOM
        // special folder
        setSpecialFolderSelectionValidator(OMResourcesActivator.OM_SPECIAL_FOLDER_KIND,
                new Status(
                        IStatus.ERROR,
                        OrganizationModelDiagramEditorPlugin.ID,
                        Messages.OrganizationModelCreationWizardPage_NotSpecialFolder));

        // XPD-4448: Only show OM special folders in the tree viewer
        addFilter(new ViewerFilter() {

            @Override
            public boolean select(Viewer viewer, Object parentElement,
                    Object element) {
                if (element instanceof SpecialFolder) {
                    return OMResourcesActivator.OM_SPECIAL_FOLDER_KIND
                            .equals(((SpecialFolder) element).getKind());
                }
                return true;
            }
        });
    }

    /**
     * Override to create files with this extension.
     * 
     * @generated
     */
    protected String getExtension() {
        return fileExtension;
    }

    /**
     * @generated NOT
     */
    public URI getURI() {
        // This is set to NOT generated as we need to encode the URI
        return URI.createPlatformResourceURI(getFilePath().toString(), true);
    }

    /**
     * @generated
     */
    protected IPath getFilePath() {
        IPath path = getContainerFullPath();
        if (path == null) {
            path = new Path(""); //$NON-NLS-1$
        }
        String fileName = getFileName();
        if (fileName != null) {
            path = path.append(fileName);
        }

        // If the path does not have the correct file extension then append the
        // correct file extension
        if (path != null
                && (path.getFileExtension() == null || !path.getFileExtension()
                        .equalsIgnoreCase(OMUtil.OM_FILE_EXTENSION))) {
            path = path.addFileExtension(OMUtil.OM_FILE_EXTENSION);
        }

        return path;
    }

    /**
     * @generated
     */
    @Override
    public void createControl(Composite parent) {
        super.createControl(parent);
        setFileName(OrganizationModelDiagramEditorUtil
                .getUniqueFileName(getContainerFullPath(),
                        getFileName(),
                        getExtension()));
        setPageComplete(validatePage());
    }

    @Override
    protected Composite createAdvancedControls(Composite topLevel) {
        Composite advancedComp = new Composite(topLevel, SWT.NONE);
        advancedComp.setLayout(new GridLayout());

        // Check box to create model with default schema
        createDefaultMMCheckbox = new Button(advancedComp, SWT.CHECK);
        createDefaultMMCheckbox.setSelection(true);
        createDefaultMMCheckbox
                .setText(Messages.OrganizationModelCreationWizardPage_createDefaultSchema_check_label);
        createDefaultMMCheckbox.setLayoutData(new GridData(
                GridData.FILL_HORIZONTAL));

        // Checkbox to apply Standard Organization Type. Will be enabled only if
        // the createDefaultMMCheckBox is enabled.
        createDefaultMMandApplyCheckBox = new Button(advancedComp, SWT.CHECK);
        createDefaultMMandApplyCheckBox.setSelection(false);
        createDefaultMMandApplyCheckBox
                .setText(Messages.OrganizationModelCreationWizardPage_applyType_check_label);
        createDefaultMMandApplyCheckBox.setLayoutData(new GridData(
                GridData.FILL_HORIZONTAL));

        createDefaultMMCheckbox.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                super.widgetSelected(e);
                if (e.getSource() == createDefaultMMCheckbox) {
                    Button source = (Button) e.getSource();

                    if (createDefaultMMandApplyCheckBox != null) {
                        if (!source.getSelection()) {
                            createDefaultMMandApplyCheckBox.setEnabled(false);
                        } else {
                            createDefaultMMandApplyCheckBox.setEnabled(true);
                        }
                    }
                }
            }
        });

        return advancedComp;

    }

    public boolean createDefaultSchema() {
        if (createDefaultMMCheckbox != null) {
            return createDefaultMMCheckbox.getSelection();
        } else {
            return false;
        }
    }

    public boolean createStandardType() {
        if (createDefaultMMandApplyCheckBox != null) {
            return createDefaultMMandApplyCheckBox.getSelection();
        } else {
            return false;
        }
    }
}
