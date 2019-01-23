package com.tibco.xpd.simulation.realdata.ui.wizard;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;

import com.tibco.xpd.simulation.EmpircalDataEditorPlugin;

/**
 * This is the one page of the wizard.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class NewFileCreationPage extends WizardNewFileCreationPage {
    /**
     * Pass in the selection.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NewFileCreationPage(String pageId, IStructuredSelection selection) {
        super(pageId, selection);
    }

    /**
     * The framework calls this to see if the file is correct.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected boolean validatePage() {
        if (super.validatePage()) {
            // Make sure the file ends in ".realdata".
            //
            String requiredExt = EmpircalDataEditorPlugin.INSTANCE.getString("_UI_RealDataEditorFilenameExtension");
            String enteredExt = new Path(getFileName()).getFileExtension();
            if (enteredExt == null || !enteredExt.equals(requiredExt)) {
                setErrorMessage(EmpircalDataEditorPlugin.INSTANCE.getString("_WARN_FilenameExtension", new Object [] { requiredExt }));
                return false;
            }
            else {
                return true;
            }
        }
        else {
            return false;
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public IFile getModelFile() {
        return ResourcesPlugin.getWorkspace().getRoot().getFile(getContainerFullPath().append(getFileName()));
    }
}