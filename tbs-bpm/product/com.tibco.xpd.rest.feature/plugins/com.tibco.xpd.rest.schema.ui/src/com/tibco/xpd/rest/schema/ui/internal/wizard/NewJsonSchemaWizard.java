package com.tibco.xpd.rest.schema.ui.internal.wizard;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;

import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.rest.schema.ui.JsonSchemaEditorOpener;
import com.tibco.xpd.rest.schema.ui.internal.Messages;
import com.tibco.xpd.rest.schema.ui.internal.RestSchemaImage;
import com.tibco.xpd.rest.schema.ui.internal.RestSchemaUiPlugin;
import com.tibco.xpd.rest.schema.ui.json.JsonSchemaCreator;
import com.tibco.xpd.ui.wizards.newproject.BasicNewXpdResourceWizard;

/**
 * Wizard for creating a new JSON schema file.
 * 
 * @author nwilson
 * @since 28 Jan 2015
 */
public class NewJsonSchemaWizard extends BasicNewXpdResourceWizard {

    /**
     * The page for identifying the target project and file.
     */
    private JsonSchemaFilePage page;

    /**
     * @see com.tibco.xpd.ui.wizards.newproject.BasicNewXpdResourceWizard#init(org.eclipse.ui.IWorkbench,
     *      org.eclipse.jface.viewers.IStructuredSelection)
     * 
     * @param workbench
     *            The workbench.
     * @param selection
     *            The current navigator selection.
     */
    @Override
    public void init(IWorkbench workbench, IStructuredSelection selection) {
        super.init(workbench, selection);
        setWindowTitle(Messages.JsonSchemaFilePage_Title);
        setDefaultPageImageDescriptor(RestSchemaUiPlugin.getDefault()
                .getImageDescriptor(RestSchemaImage.NEW_JSON_FILE_WIZARD));
    }

    /**
     * @see org.eclipse.jface.wizard.Wizard#addPages()
     * 
     */
    @Override
    public void addPages() {
        page = new JsonSchemaFilePage(getSelection());
        page.setTitle(Messages.NewJsonSchemaWizard_filePageTitle);
        page.setDescription(Messages.NewJsonSchemaWizard_filePageDescription);
        addPage(page);
    }

    /**
     * @see com.tibco.xpd.ui.wizards.newproject.BasicNewXpdResourceWizard
     *      #performFinish()
     * 
     * @return true if the JSON schema file was created.
     */
    @Override
    public boolean performFinish() {
        JsonSchemaCreator creator = new JsonSchemaCreator(page.getFilePath());
        IRunnableWithProgress op = creator.getCreateOperation();
        try {
            getContainer().run(false, true, op);
            IFile file = getFile(creator.getResource());
            if (file != null) {
                new JsonSchemaEditorOpener().openEditor(file);
                selectAndReveal(file);
            }
        } catch (InterruptedException | InvocationTargetException e) {
            return false;
        }
        return true;
    }

    /**
     * @param resource
     *            The EMF Resource.
     * @return The associated file.
     */
    private IFile getFile(Resource resource) {
        IFile file = null;
        EObject eObject =
                resource.getContents() != null
                        && !resource.getContents().isEmpty() ? resource
                        .getContents().get(0) : null;
        if (eObject != null) {
            file = WorkingCopyUtil.getFile(eObject);
        }
        return file;

    }

}
