/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.process.om;

import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.core.services.ViewService;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.swt.widgets.Display;
import org.eclipse.uml2.uml.UMLPackage;
import org.openarchitectureware.type.emf.EmfMetaModel;
import org.openarchitectureware.workflow.issues.IssuesImpl;
import org.openarchitectureware.xtend.XtendFacade;
import org.osgi.framework.Bundle;

import com.tibco.xpd.process.om.internal.Messages;
import com.tibco.xpd.process.om.template.TransformHelper;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Transform XPDL Models to Organisation Model.
 * <p>
 * <i>Created: 16 July 2008</i>
 * </p>
 * 
 * @author Gary Lewis
 */
public class XtendTransformer {

    /** */
    private static final String EXTENSION_FILE = "com::tibco::xpd::process::om::template::Proc2org"; //$NON-NLS-1$
    /** */
    private static final String TRANSFORM_EXPRESSION = "transform"; //$NON-NLS-1$

    protected static boolean initialised = false;

    private IssuesImpl issues = null;

    public IssuesImpl getIssues() {
        return issues;
    }

    private final String xpdl2MetaModelEcoreURL = "/model/xpdl2.ecore";

    private final String xpdExtensionEcoreURL = "/model/XpdExtension.ecore";

    private final String omMetaModelURL = "model/om.ecore"; //$NON-NLS-1$

    private static final String REFLECTION_OM_CORE_PLUGIN_ID = "com.tibco.xpd.om.core"; //$NON-NLS-1$

    private static final String REFLECTION_XPDL_PLUGIN_ID = "com.tibco.xpd.xpdl2"; //$NON-NLS-1$

    private static Bundle omCoreBundle = null;

    private static Bundle xpdl2Bundle;

    public final static String ORG_MODEL_ID = "Organization Model"; //$NON-NLS-1$    

    public static final String ID = "com.tibco.xpd.om.modeler.diagram"; //$NON-NLS-1$

    public static final PreferencesHint DIAGRAM_PREFERENCES_HINT = new PreferencesHint(
            ID);

    private void initialise() {
        if (!initialised) {
            EPackage.Registry.INSTANCE.put(EcorePackage.eNS_URI,
                    EcorePackage.eINSTANCE);
            EPackage.Registry.INSTANCE.put(UMLPackage.eNS_URI,
                    UMLPackage.eINSTANCE);
            EPackage.Registry.INSTANCE.put(NotationPackage.eNS_URI,
                    NotationPackage.eINSTANCE);
        }
        initialised = true;
    }

    private void initialiseMetaModels(final XtendFacade f) {
        // add ecore model for xpdl2
        URL url = xpdl2Bundle.getEntry(xpdl2MetaModelEcoreURL);
        EmfMetaModel xpdl2MetaModel = new EmfMetaModel();
        xpdl2MetaModel.setMetaModelFile(url.toString());
        // add xsd meta model for
        url = xpdl2Bundle.getEntry(xpdExtensionEcoreURL);
        EmfMetaModel xpdExtMetaModel = new EmfMetaModel();
        xpdExtMetaModel.setMetaModelFile(url.toString());

        // add meta model for Organisation Unit
        url = omCoreBundle.getResource(omMetaModelURL);
        EmfMetaModel omMetaModel = new EmfMetaModel();
        omMetaModel.setMetaModelFile(url.toString());

        // Meta models registration.
        EmfMetaModel ecoreMetaModel = new EmfMetaModel(
                (EcorePackage) EPackage.Registry.INSTANCE
                        .get(EcorePackage.eNS_URI));

        f.registerMetaModel(ecoreMetaModel);
        f.registerMetaModel(omMetaModel);
        f.registerMetaModel(xpdl2MetaModel);
        f.registerMetaModel(xpdExtMetaModel);
    }

    /**
     * Performs the actual transformation
     * 
     * @param source
     * @param omFilePath
     * @return
     */
    public boolean transform(final Collection<IFile> source,
            final Collection<EObject> references,
            final Collection<String> refURI, final String omFilePath)
            throws Exception {

        issues = new IssuesImpl();
        omCoreBundle = Platform.getBundle(REFLECTION_OM_CORE_PLUGIN_ID);
        if (omCoreBundle == null) {
            return false;
        }
        xpdl2Bundle = Platform.getBundle(REFLECTION_XPDL_PLUGIN_ID);
        if (xpdl2Bundle == null) {
            return false;
        }

        final Collection<com.tibco.xpd.xpdl2.Package> packages = TransformHelper
                .getPackages(source);

        final XtendFacade f = XtendFacade.create(EXTENSION_FILE);

        initialise();

        initialiseMetaModels(f);

        Display.getDefault().syncExec(new Runnable() {
            public void run() {
                try {
                    TransformHelper.setOMReflection();
                    TransformHelper.setMembers(source);
                    final EObject tempModel = (EObject) f.call(
                            TRANSFORM_EXPRESSION, packages, references);
                    AbstractTransactionalCommand command = new AbstractTransactionalCommand(
                            XpdResourcesPlugin.getDefault().getEditingDomain(),
                            Messages.XtendTransformer_CreateDiagramCommand_label,
                            Collections.EMPTY_LIST) {
                        @Override
                        protected CommandResult doExecuteWithResult(
                                IProgressMonitor monitor, IAdaptable info)
                                throws ExecutionException {
                            try {
                                URI uri = URI.createPlatformResourceURI(
                                        omFilePath, true);

                                TransactionalEditingDomain editingDomain = XpdResourcesPlugin
                                        .getDefault().getEditingDomain();
                                Resource resource = editingDomain
                                        .getResourceSet().createResource(uri);
                                resource.getContents().add(tempModel);

                                final String diagramName = uri.lastSegment();

                                Map saveOptions = new HashMap();
                                saveOptions.put(XMLResource.OPTION_ENCODING,
                                        "UTF-8"); //$NON-NLS-1$
                                saveOptions
                                        .put(
                                                Resource.OPTION_SAVE_ONLY_IF_CHANGED,
                                                Resource.OPTION_SAVE_ONLY_IF_CHANGED_MEMORY_BUFFER);

                                // save the new changes we have made to resource
                                resource.save(saveOptions);
                                resource.unload(); // ( not sure why I need to
                                // do this but if I don't the
                                // wc.getRootElement below
                                // returns null )

                                IFile omFile = WorkspaceSynchronizer
                                        .getFile(resource);
                                omFile.getParent().refreshLocal(
                                        IFile.DEPTH_INFINITE, null);
                                WorkingCopy wc = XpdResourcesPlugin
                                        .getDefault().getWorkingCopy(omFile);
                                EObject tempOrgModel = wc.getRootElement();

                                TransformHelper.setOMGeneralProperties(omFile,
                                        tempOrgModel);

                                Diagram diagram = ViewService.createDiagram(
                                        tempOrgModel, ORG_MODEL_ID,
                                        DIAGRAM_PREFERENCES_HINT);

                                diagram.setName(diagramName);
                                diagram.setElement(tempOrgModel);
                                resource.getContents().add(diagram);

                                resource.save(saveOptions);

                                TransformHelper.fixCommentSpaces(resource);
                                TransformHelper
                                        .setXPDLExternalReferences(
                                                WorkspaceSynchronizer
                                                        .getFile(resource),
                                                source);
                            } catch (Exception e) {
                                Activator
                                        .getDefault()
                                        .getLogger()
                                        .error(
                                                Messages.XtendTransformer_unableToStore_message);
                            }
                            return CommandResult.newOKCommandResult();
                        }

                        /**
                         * @generated
                         */
                        @Override
                        public boolean canUndo() {
                            // Don't want this command to be
                            // undo-able
                            return false;
                        }
                    };
                    try {
                        OperationHistoryFactory.getOperationHistory().execute(
                                command, null, null);
                    } catch (ExecutionException e) {
                        Activator
                                .getDefault()
                                .getLogger()
                                .error(
                                        Messages.XtendTransformer_unableToCreate_message);
                    }
                } catch (final Exception e) {
                    Activator.getDefault().getLogger().error(e,
                            Messages.XtendTransformer_transformError_message);
                }
            }
        });

        return true;
    }

}
