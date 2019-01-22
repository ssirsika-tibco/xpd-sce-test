/**
 * Copyright (c) TIBCO Software Inc 2004-2012. All rights reserved.
 */
package com.tibco.xpd.bom.resources.internal.refactor;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.NullChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.internal.Messages;
import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.SpecialFolderUtil;

/**
 * Update reference change that will refactor any URI references to the BOM
 * being renamed/moved.
 * 
 * @author njpatel
 */
public class UpdateReferenceChange extends Change {

    private final BOMWorkingCopy wc;

    private final IFile[] oldFiles;

    private final IFile[] newFiles;

    /**
     * Update reference refactor.
     */
    public UpdateReferenceChange(BOMWorkingCopy wc, IFile[] oldFiles,
            IFile[] newFiles) {
        this.wc = wc;
        this.oldFiles = oldFiles;
        this.newFiles = newFiles;
    }

    /**
     * @see org.eclipse.ltk.core.refactoring.Change#getName()
     * 
     * @return
     */
    @Override
    public String getName() {
        return String
                .format(Messages.BOMRefactorHelper_updateReference_change_shortdesc,
                        wc.getName());
    }

    /**
     * @see org.eclipse.ltk.core.refactoring.Change#initializeValidationData(org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param pm
     */
    @Override
    public void initializeValidationData(IProgressMonitor pm) {
        if (pm != null) {
            pm.done();
        }
    }

    /**
     * @see org.eclipse.ltk.core.refactoring.Change#isValid(org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param pm
     * @return
     * @throws CoreException
     * @throws OperationCanceledException
     */
    @Override
    public RefactoringStatus isValid(IProgressMonitor pm) throws CoreException,
            OperationCanceledException {
        try {
            /*
             * Working copy cannot be in a dirty state
             */
            if (wc.isWorkingCopyDirty()) {
                return RefactoringStatus
                        .createErrorStatus(String
                                .format(Messages.BOMRefactorHelper_fileNeedsSaving_error_shortdesc,
                                        wc.getName()));
            }
        } finally {
            if (pm != null) {
                pm.done();
            }
        }
        return RefactoringStatus.create(Status.OK_STATUS);
    }

    /**
     * @see org.eclipse.ltk.core.refactoring.Change#perform(org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param pm
     * @return
     * @throws CoreException
     */
    @Override
    public Change perform(IProgressMonitor pm) throws CoreException {

        if (pm != null) {
            pm.beginTask(String
                    .format(Messages.BOMRefactorHelper_updatingReference_progress_shortdesc,
                            wc.getName()),
                    oldFiles.length);
        }
        try {
            TransactionalEditingDomain domain =
                    XpdResourcesPlugin.getDefault().getEditingDomain();
            CompoundCommand cc = new CompoundCommand();

            final Resource res = wc.getRootElement().eResource();

            for (int idx = 0; idx < oldFiles.length; idx++) {
                IFile oldFile = oldFiles[idx];
                IFile newFile = newFiles[idx];

                // Replace all reference URIs from this...
                URI oldURI =
                        URI.createPlatformResourceURI(oldFile.getFullPath()
                                .toString(), true);
                // ...to this
                URI newURI =
                        URI.createURI(SpecialFolderUtil
                                .getSpecialFolderRelativePath(newFile)
                                .toString());

                /*
                 * If the relative URIs are the same then do nothing
                 */
                if (BOMRefactorHelper
                        .isRelativePathsIdentical(oldFile, newFile)) {
                    return new NullChange();
                }

                // Find all references in the model being refactored
                Map<EObject, Collection<Setting>> refs =
                        EcoreUtil.CrossReferencer.find(res.getContents());

                for (Entry<EObject, Collection<Setting>> entry : refs
                        .entrySet()) {
                    EObject ref = entry.getKey();
                    Collection<Setting> settings = entry.getValue();

                    URI uri = EcoreUtil.getURI(ref);
                    String fragment = uri.fragment();
                    uri = uri.trimFragment();

                    // Find a reference to the BOM being renamed
                    if (uri.equals(oldURI)) {
                        URI proxyURI = newURI;
                        if (fragment != null) {
                            proxyURI = proxyURI.appendFragment(fragment);
                        }

                        /*
                         * Update all references by creating a proxy to the new
                         * URI
                         */
                        for (Setting setting : settings) {
                            EStructuralFeature feature =
                                    setting.getEStructuralFeature();
                            if (!feature.isDerived() && feature.isChangeable()) {
                                int index;
                                if (feature.isMany()) {
                                    List<?> list = (List<?>) setting.get(false);
                                    index = list.indexOf(ref);
                                } else {
                                    index = CommandParameter.NO_INDEX;
                                }

                                // Create a proxy object with the new URI
                                EObject proxy =
                                        createProxy(ref.eClass(), proxyURI);

                                /*
                                 * Set the new URI, thus ensuring that the
                                 * object is a proxy that will be re-resolved
                                 * next time it is accessed.
                                 */
                                cc.append(new SetCommand(domain, setting
                                        .getEObject(), feature, proxy, index));
                            }
                        }
                    }
                }

                if (pm != null) {
                    pm.worked(1);
                }
            }
            if (!cc.isEmpty()) {
                domain.getCommandStack().execute(cc);
                try {
                    wc.save();

                    return new UpdateReferenceChange(wc, newFiles, oldFiles);
                } catch (IOException e) {
                    BOMResourcesPlugin
                            .getDefault()
                            .getLogger()
                            .error(e,
                                    String.format(Messages.UpdateReferenceChange_workingCopySaveFail_error_shortdesc,
                                            wc.getName()));
                }
            }
        } finally {
            if (pm != null) {
                pm.done();
            }
        }

        return new NullChange();
    }

    /**
     * @param eClass
     * @param uri
     * @return
     */
    private EObject createProxy(EClass eClass, URI uri) {
        EObject proxy =
                eClass.getEPackage().getEFactoryInstance().create(eClass);
        ((InternalEObject) proxy).eSetProxyURI(uri);
        return proxy;
    }

    /**
     * @see org.eclipse.ltk.core.refactoring.Change#getModifiedElement()
     * 
     * @return
     */
    @Override
    public Object getModifiedElement() {
        return wc;
    }
}