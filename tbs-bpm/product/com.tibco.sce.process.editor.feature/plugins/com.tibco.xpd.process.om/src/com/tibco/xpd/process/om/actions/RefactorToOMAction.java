/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.process.om.actions;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.actions.BaseSelectionListenerAction;
import org.openarchitectureware.workflow.issues.Issue;
import org.osgi.framework.Bundle;

import com.tibco.xpd.process.om.XtendTransformer;
import com.tibco.xpd.process.om.internal.Messages;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;

/**
 * Refactors Process Models to Organisation Model.
 * <p>
 * <i>Created: 16 July 2008</i>
 * </p>
 * 
 * @author Gary Lewis
 */
public class RefactorToOMAction extends BaseSelectionListenerAction {

    /** Organisation Model Output file extension. */
    public static final String OUTPUT_FILE_EXTENSION = "om"; //$NON-NLS-1$

    private String outputPath;

    private final ArrayList<Exception> errors = new ArrayList<Exception>();

    private static final String REFLECTION_OM_CORE_PLUGIN_ID = "com.tibco.xpd.om.core"; //$NON-NLS-1$

    private static final String REFLECTION_OM_CORE_OMUTIL_CLASS = "com.tibco.xpd.om.core.om.util.OMUtil"; //$NON-NLS-1$

    private static final String REFLECTION_OM_CORE_ORGMODEL_CLASS = "com.tibco.xpd.om.core.om.OrgModel"; //$NON-NLS-1$

    private static final String REFLECTION_OM_GET_URI_BY_ID_METHOD = "getURIByID"; //$NON-NLS-1$

    private static final String REFLECTION_OM_GET_OBJECT_BY_ID_METHOD = "getEObjectByID"; //$NON-NLS-1$

    private Bundle omCoreBundle = null;

    private Class omUtilCls = null;

    private Class orgModelCls = null;

    private Method getURIByIDMeth = null;

    private Method getObjByIDMeth = null;

    /**
     * The constructor
     */
    public RefactorToOMAction() {
        super(Messages.RefactorToOMAction_refactorToOM_menu);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.action.Action#run()
     */
    @Override
    public void run() {
        try {
            omCoreBundle = Platform.getBundle(REFLECTION_OM_CORE_PLUGIN_ID);
            if (omCoreBundle != null) {
                omUtilCls = omCoreBundle
                        .loadClass(REFLECTION_OM_CORE_OMUTIL_CLASS);
                orgModelCls = omCoreBundle
                        .loadClass(REFLECTION_OM_CORE_ORGMODEL_CLASS);

                Class getURIByIDParam = String.class;
                Class getObjByIDParam = String.class;

                getURIByIDMeth = omUtilCls.getMethod(
                        REFLECTION_OM_GET_URI_BY_ID_METHOD, getURIByIDParam);
                getObjByIDMeth = omUtilCls.getMethod(
                        REFLECTION_OM_GET_OBJECT_BY_ID_METHOD, getObjByIDParam);
            }
        } catch (Exception e) {
            errors.add(e);
        }

        IStructuredSelection selection = getStructuredSelection();
        if (selection.isEmpty()
                || !(selection.getFirstElement() instanceof IFile)) {
            errors.add(new Exception(
                    Messages.RefactorToOMAction_NoValidFile_error));
            return;
        }

        ArrayList<IFile> selectedPackages = new ArrayList<IFile>();
        ArrayList<EObject> referencedOrgModels = new ArrayList<EObject>();
        ArrayList<String> referencedURI = new ArrayList<String>();
        Iterator<IFile> iter = selection.iterator();

        // look at each selected xpdl file in turn
        while (iter.hasNext()) {
            // add xpdl file to the array
            IFile modelFile = iter.next();
            selectedPackages.add(modelFile);

            // get the corresponding xpdl2 package and look at all its
            // participants
            WorkingCopy wc = XpdResourcesPlugin.getDefault().getWorkingCopy(
                    modelFile);
            Package xpdl2Package = (Package) wc.getRootElement();
            Iterator<Participant> particIter = xpdl2Package.getParticipants()
                    .iterator();
            while (particIter.hasNext()) {
                Participant participant = particIter.next();
                try {
                    // try and find any participants with external references
                    ExternalReference externalReference = participant
                            .getExternalReference();
                    if (externalReference != null
                            && externalReference.getXref() != null
                            && omCoreBundle != null) {
                        EObject refObject = (EObject) getObjByIDMeth.invoke(
                                omUtilCls, externalReference.getXref());
                        if (refObject != null) {
                            // store the objects container (namely an OrgModel)
                            // along with its uri in the arraylist
                            EObject container = EcoreUtil
                                    .getRootContainer(refObject);
                            URI refURI = (URI) getURIByIDMeth.invoke(omUtilCls,
                                    externalReference.getXref());
                            String strURI = refURI.trimFragment().toString();
                            if (container != null
                                    && orgModelCls.isInstance(container)
                                    && !referencedOrgModels.contains(container)) {
                                referencedOrgModels.add(container);
                                referencedURI.add(strURI);
                            }
                        }
                    }
                } catch (Exception e) {
                    errors.add(e);
                }
            }
        }

        // perform the transformation from xpdl2 packages to OrgModel
        XtendTransformer transformer = new XtendTransformer();
        try {
            boolean result = transformer.transform(selectedPackages,
                    referencedOrgModels, referencedURI, getOutputPath());
        } catch (Exception e) {
            errors.add(e);
        }
        Issue[] issues = transformer.getIssues().getErrors();
        if (issues.length > 0) {
            Exception exception = new Exception(issues[0].getMessage());
            errors.add(exception);
        }
    }

    public ArrayList<Exception> getErrors() {
        return errors;
    }

    /**
     * Gets the output path. If the output path is not set it will return
     * default path which is the same as the source file but with and 'om'
     * extension.
     * 
     * @return The output path. It will be the workspace root relative path.
     */
    public String getOutputPath() {
        if (outputPath == null) {
            return getDefaultOutputPath();
        }
        return outputPath;
    }

    /**
     * Sets the output path.
     * 
     * @param outputPath
     *            the outputPath to set. This must be workspace root relative
     *            path.
     */
    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    /**
     * @return
     */
    private String getDefaultOutputPath() {
        IStructuredSelection selection = getStructuredSelection();
        if (selection.isEmpty()
                || !(selection.getFirstElement() instanceof IFile)) {
            return null;
        }
        final IFile modelFile = (IFile) selection.getFirstElement();
        return modelFile.getFullPath().removeFileExtension().addFileExtension(
                OUTPUT_FILE_EXTENSION).toPortableString();
    }
}
