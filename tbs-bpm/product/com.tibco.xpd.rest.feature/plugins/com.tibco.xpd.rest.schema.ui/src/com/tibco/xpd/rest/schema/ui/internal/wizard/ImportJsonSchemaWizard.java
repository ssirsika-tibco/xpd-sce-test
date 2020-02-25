package com.tibco.xpd.rest.schema.ui.internal.wizard;

import java.io.IOException;
import java.io.Reader;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;

import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.rest.schema.ui.JsonSchemaEditorOpener;
import com.tibco.xpd.rest.schema.ui.internal.Messages;
import com.tibco.xpd.rest.schema.ui.internal.RestSchemaImage;
import com.tibco.xpd.rest.schema.ui.internal.RestSchemaUiPlugin;
import com.tibco.xpd.rest.schema.ui.json.JsonSampleImport;
import com.tibco.xpd.ui.wizards.newproject.BasicNewXpdResourceWizard;

/**
 * Wizard for creating JSON Schemas by importing external resources.
 * 
 * @author nwilson
 * @since 30 Jan 2015
 */
public class ImportJsonSchemaWizard extends BasicNewXpdResourceWizard implements
        IImportWizard {

    /**
     * The schema import wizard page.
     */
    private JsonSchemaImportPage filePage;

    @Override
    public void init(IWorkbench workbench, IStructuredSelection selection) {
        super.init(workbench, selection);
        setWindowTitle(Messages.ImportJsonSchemaWizard_windowTitle);
        setDefaultPageImageDescriptor(RestSchemaUiPlugin.getDefault()
                .getImageDescriptor(RestSchemaImage.IMPORT_JSON_WIZARD));
    }

    /**
     * @see org.eclipse.jface.wizard.Wizard#addPages()
     * 
     */
    @Override
    public void addPages() {
        filePage = new JsonSchemaImportPage(getSelection());
        filePage.setTitle(Messages.ImportJsonSchemaWizard_pageTitle);
        filePage.setDescription(Messages.ImportJsonSchemaWizard_pageDescription);
        addPage(filePage);
    }

    @Override
    public boolean performFinish() {
        JsonSchemaImportMethod method = filePage.getImportMethod();
        IPath target = filePage.getFilePath();
        if (method != null) {
            try (Reader reader = filePage.getSource()) {
                JsonSampleImport im = new JsonSampleImport();
                Resource resource =
                        im.schemaImport(method, reader, target, getContainer());
                IFile file = getFile(resource);
                if (file != null) {
                    new JsonSchemaEditorOpener().openEditor(file);
                    selectAndReveal(file);
                }
                return true;
            } catch (IOException e) {
                filePage.setErrorMessage(e.getMessage());
            }
        }
        return false;
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
