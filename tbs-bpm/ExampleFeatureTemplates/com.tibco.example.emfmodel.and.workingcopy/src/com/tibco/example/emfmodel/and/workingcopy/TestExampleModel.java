/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */
package com.tibco.example.emfmodel.and.workingcopy;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.junit.Test;

import com.tibco.example.model.ChildType;
import com.tibco.example.model.DocumentRoot;
import com.tibco.example.model.MainElementType;
import com.tibco.example.model.ModelFactory;
import com.tibco.example.workingcopy.ExampleWorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.util.XpdConsts;

/**
 * Junit Test that shows Creation and Manipulation of com.tibco.example.model
 * ECore Model via it's working copy.
 * 
 * @author aallway
 * @since 6 Feb 2015
 */
public class TestExampleModel {

    @Test
    public void test() {

        IProject project = createTestProject();

        IPath path = project.getFullPath();
        String testFile = "TestExampleModel.exmp"; //$NON-NLS-1$

        path = path.append(testFile);
        URI uri = URI.createPlatformResourceURI(path.toString(), true);

        Resource emfresource = createSidTstFile(uri, new NullProgressMonitor());

        final ExampleWorkingCopy workingCopy =
                (ExampleWorkingCopy) WorkingCopyUtil.getWorkingCopy(project
                        .getFile(testFile));

        modifyResource(workingCopy);

        try {
            workingCopy.save();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return;

    }

    /**
     * @param workingCopy
     */
    private void modifyResource(final ExampleWorkingCopy workingCopy) {
        RecordingCommand cmd =
                new RecordingCommand(
                        (TransactionalEditingDomain) workingCopy
                                .getEditingDomain()) {

                    @Override
                    protected void doExecute() {
                        MainElementType mainElement =
                                workingCopy.getMainElement();

                        mainElement.setName(mainElement.getName() + "changed"); //$NON-NLS-1$

                        ChildType childType =
                                ModelFactory.eINSTANCE.createChildType();
                        childType.setChildAttribute("New attribtue"); //$NON-NLS-1$
                        childType.setTestElement("New element"); //$NON-NLS-1$
                        mainElement.getChildElements().add(childType);

                    }
                };

        workingCopy.getEditingDomain().getCommandStack().execute(cmd);
    }

    /**
     * @return
     */
    private IProject createTestProject() {
        IProject project =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getProject("ExampleTextProject"); //$NON-NLS-1$
        try {
            project.create(null);
            project.open(null);
            ProjectUtil.addNature(project, XpdConsts.PROJECT_NATURE_ID);

        } catch (CoreException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return project;
    }

    Resource createSidTstFile(final URI uri, IProgressMonitor monitor) {
        final Resource resource;
        // if this is a valid platform URI
        if (uri != null && uri.isPlatformResource()) {
            // if the monitor is not provided create one.
            if (monitor == null) {
                monitor = new NullProgressMonitor();
            }
            // Make sure its our editing domain
            TransactionalEditingDomain editingDomain =
                    XpdResourcesPlugin.getDefault().getEditingDomain();

            // create Facade Resource
            resource = editingDomain.getResourceSet().createResource(uri);

            AbstractTransactionalCommand command =
                    new AbstractTransactionalCommand(editingDomain,
                            "Create SidTst", //$NON-NLS-1$
                            Collections.EMPTY_LIST) {
                        @Override
                        protected CommandResult doExecuteWithResult(
                                IProgressMonitor monitor, IAdaptable info)
                                throws ExecutionException {

                            String fileName = uri.lastSegment();

                            DocumentRoot documentRoot = createInitialModel();

                            resource.getContents().add(documentRoot);

                            try {
                                Map saveOptions = new HashMap();
                                saveOptions.put(XMLResource.OPTION_ENCODING,
                                        "UTF-8"); //$NON-NLS-1$
                                saveOptions
                                        .put(Resource.OPTION_SAVE_ONLY_IF_CHANGED,
                                                Resource.OPTION_SAVE_ONLY_IF_CHANGED_MEMORY_BUFFER);

                                // Save File
                                resource.save(saveOptions);

                            } catch (IOException e) {
                                System.out.println("IOEExeption: " //$NON-NLS-1$
                                        + e.toString());
                            }
                            return CommandResult.newOKCommandResult();
                        }

                    };
            // Run the command
            try {
                OperationHistoryFactory.getOperationHistory().execute(command,
                        new SubProgressMonitor(monitor, 1),
                        null);
            } catch (ExecutionException e) {
                System.out.println("ExecutionException: " + e.toString()); //$NON-NLS-1$
            }
            // return the create Facade Resource.
            return resource;
        }
        return null;
    }

    /**
     * @return
     */
    private DocumentRoot createInitialModel() {
        DocumentRoot documentRoot = ModelFactory.eINSTANCE.createDocumentRoot();

        MainElementType mainElement =
                ModelFactory.eINSTANCE.createMainElementType();
        documentRoot.setMainElement(mainElement);

        mainElement.setId(EcoreUtil.generateUUID());
        mainElement.setName("Sids EMF Test"); //$NON-NLS-1$

        ChildType childType = ModelFactory.eINSTANCE.createChildType();
        childType.setChildAttribute("TEst child attr"); //$NON-NLS-1$
        childType.setTestElement("Test child element"); //$NON-NLS-1$
        mainElement.getChildElements().add(childType);

        ChildType childType2 = ModelFactory.eINSTANCE.createChildType();
        childType2.setChildAttribute("TEst child attr2"); //$NON-NLS-1$
        childType2.setTestElement("Test child element2"); //$NON-NLS-1$
        mainElement.getChildElements().add(childType2);
        return documentRoot;
    }

}
