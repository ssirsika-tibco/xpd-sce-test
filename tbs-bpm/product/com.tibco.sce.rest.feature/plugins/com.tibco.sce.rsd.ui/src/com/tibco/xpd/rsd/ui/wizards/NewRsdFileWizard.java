package com.tibco.xpd.rsd.ui.wizards;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.Transaction;
import org.eclipse.emf.transaction.TransactionalCommandStack;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.rsd.Method;
import com.tibco.xpd.rsd.RsdFactory;
import com.tibco.xpd.rsd.RsdPackage;
import com.tibco.xpd.rsd.Service;
import com.tibco.xpd.rsd.ui.RsdImage;
import com.tibco.xpd.rsd.ui.RsdUIPlugin;
import com.tibco.xpd.rsd.ui.components.RsdEditingUtil;
import com.tibco.xpd.rsd.ui.editor.RsdEditorPart;
import com.tibco.xpd.rsd.ui.internal.Messages;
import com.tibco.xpd.ui.wizards.newproject.BasicNewXpdResourceWizard;

/**
 * Wizard for creating a new REST service descriptor file.
 * 
 * @author jarciuch
 */
public class NewRsdFileWizard extends BasicNewXpdResourceWizard {

    /**
     * The page for identifying the target project and file.
     */
    private RsdFilePage page;

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(IWorkbench workbench, IStructuredSelection selection) {
        super.init(workbench, selection);
        setWindowTitle(Messages.NewRsdFileWizard_NewRsd_title);
        setDefaultPageImageDescriptor(RsdImage
                .getImageDescriptor(RsdImage.NEW_RSD_WIZBAN));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addPages() {
        page = new RsdFilePage(getSelection());
        page.setTitle(Messages.NewRsdFileWizard_NewRsd_message);
        page.setDescription(Messages.NewRsdFileWizard_NewRsd_desc);
        addPage(page);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean performFinish() {
        IPath filePath = page.getFilePath();
        IRunnableWithProgress op = getCreateOperation(filePath);
        try {
            getContainer().run(false, true, op);
            IFile file =
                    ResourcesPlugin.getWorkspace().getRoot().getFile(filePath);
            if (file != null) {
                openEditor(file);
                selectAndReveal(file);
            }
        } catch (InterruptedException | InvocationTargetException e) {
            return false;
        }
        return true;
    }

    public void openEditor(IFile file) {
        IEditorInput editorInput;
        editorInput = new FileEditorInput(file);

        IWorkbenchPage page =
                PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                        .getActivePage();
        try {
            page.openEditor(editorInput, RsdEditorPart.EDITOR_ID);
        } catch (PartInitException e) {
            RsdUIPlugin.getLogger().error(e);
        }

    }

    /**
     * Gets a operation to create a new RSD file.
     * 
     * @return A runnable operation to create a new RSD file.
     */
    public IRunnableWithProgress getCreateOperation(final IPath path) {
        IRunnableWithProgress op = new WorkspaceModifyOperation(null) {

            @Override
            protected void execute(IProgressMonitor monitor)
                    throws CoreException, InterruptedException {
                TransactionalEditingDomain ed =
                        XpdResourcesPlugin.getDefault().getEditingDomain();
                URI uri = URI.createPlatformResourceURI(path.toString(), true);
                final ResourceSet rs = ed.getResourceSet();
                final Resource resource = rs.createResource(uri);
                RecordingCommand cmd = new RecordingCommand(ed) {
                    @Override
                    protected void doExecute() {
                        EObject root = createRootElement();
                        resource.getContents().add(root);
                    }

                    @Override
                    public boolean canUndo() {
                        // Should not be able to undo the creation of a new
                        // model.
                        return false;
                    }

                    private EObject createRootElement() {
                        Service service = RsdFactory.eINSTANCE.createService();
                        String fileNameBase =
                                path.removeFileExtension().lastSegment().trim();
                        service.setName(fileNameBase
                                + "-" + Messages.NewRsdFileWizard_DefaultServiceName); //$NON-NLS-1$

                        /*
                         * Create default resource.
                         */
                        com.tibco.xpd.rsd.Resource resource =
                                com.tibco.xpd.rsd.RsdFactory.eINSTANCE
                                        .createResource();

                        String resourceName =
                                RsdEditingUtil
                                        .getDefaultName(RsdEditingUtil.NEW_RESOURCE_NAME_PREFIX,
                                                service,
                                                RsdPackage.eINSTANCE
                                                        .getResource());

                        resource.setName(resourceName);

                        /*
                         * Create default method.
                         */
                        Method method =
                                com.tibco.xpd.rsd.RsdFactory.eINSTANCE
                                        .createMethod();

                        String methodName =
                                RsdEditingUtil
                                        .getDefaultName(RsdEditingUtil.NEW_METHOD_NAME_PREFIX,
                                                service,
                                                RsdPackage.eINSTANCE
                                                        .getMethod());

                        method.setName(methodName);

                        resource.getMethods().add(method);
                        service.getResources().add(resource);

                        return service;
                    }
                };
                if (cmd.canExecute()) {
                    HashMap<String, Boolean> params = new HashMap<>();
                    params.put(Transaction.OPTION_NO_UNDO, Boolean.TRUE);
                    try {
                        ((TransactionalCommandStack) ed.getCommandStack())
                                .execute(cmd, params);
                    } catch (RollbackException e) {
                        throw new CoreException(new Status(IStatus.ERROR,
                                RsdUIPlugin.PLUGIN_ID,
                                "Probelm with .rsd resource creation.", e)); //$NON-NLS-1$
                    }
                }
                Map<String, String> saveOptions = new HashMap<>();
                saveOptions.put(XMLResource.OPTION_ENCODING, "utf-8"); //$NON-NLS-1$
                saveOptions.put(Resource.OPTION_SAVE_ONLY_IF_CHANGED,
                        Resource.OPTION_SAVE_ONLY_IF_CHANGED_MEMORY_BUFFER);
                try {
                    resource.save(saveOptions);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        return op;
    }
}
