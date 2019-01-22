/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.processeditorconfiguration.ProcessEditorConfigurationUtil;
import com.tibco.xpd.analyst.resources.xpdl2.processeditorconfiguration.ProcessEditorElementType;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.projectexplorer.decorator.XPDProblemDecorator;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ProcessInterfaces;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author rsomayaj
 * 
 * 
 */
public class ProcessInterfaceGroup extends AbstractAssetGroup {

    private static final String TITLE =
            Messages.ProcessInterfaceGroup_Group_title;

    /**
     * Default constructor
     * 
     * @param parent
     */
    public ProcessInterfaceGroup(EObject parent) {
        super(parent, ProcessEditorElementType.process_interface);
        AdapterFactoryEditingDomain ed =
                (AdapterFactoryEditingDomain) getEditingDomain(parent);
        AdapterFactory af = ed.getAdapterFactory();

        Package pkg = (Package) parent;
        ProcessInterfaces pi =
                (ProcessInterfaces) pkg
                        .getOtherElement(XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ProcessInterfaces().getName());
        if (pi != null) {
            ItemProviderAdapter adapter =
                    (ItemProviderAdapter) af.adapt(pi,
                            IEditingDomainItemProvider.class);
            if (adapter != null) {
                adapter.addListener(this);
            }
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.BpmArtefactGroup
     * #getFeature()
     */
    @Override
    public EStructuralFeature getFeature() {
        return XpdExtensionPackage.eINSTANCE
                .getDocumentRoot_ProcessInterfaces();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.BpmArtefactGroup
     * #getImage()
     */
    @Override
    public Image getImage() {
        return Xpdl2ResourcesPlugin.getDefault().getImageRegistry()
                .get(Xpdl2ResourcesConsts.ICON_PROCESSINTERFACE);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.BpmArtefactGroup
     * #getText()
     */
    @Override
    public String getText() {
        return TITLE;
    }

    @Override
    public EClass getReferenceType() {
        return XpdExtensionPackage.eINSTANCE.getProcessInterfaces();
    }

    @Override
    public List getChildren() {
        // If children list not created then create it
        if (children == null) {
            children = new ArrayList<Object>();

            if (getParent() != null && getFeature() != null) {
                Package parentPackage = (Package) getParent();
                if (parentPackage.getOtherElement(XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_ProcessInterfaces().getName()) != null) {
                    ProcessInterfaces processInterfaces =
                            (ProcessInterfaces) parentPackage
                                    .getOtherElement(XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_ProcessInterfaces()
                                            .getName());
                    children.addAll(processInterfaces.getProcessInterface());
                }
            }
        }
        return children;
    }

    @Override
    public void notifyChanged(Notification notification) {
        super.notifyChanged(notification);
        AdapterFactoryEditingDomain ed =
                (AdapterFactoryEditingDomain) getEditingDomain(parent);
        if (ed != null) {
            AdapterFactory af = ed.getAdapterFactory();
            Package pkg = (Package) parent;
            ProcessInterfaces pi =
                    (ProcessInterfaces) pkg
                            .getOtherElement(XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_ProcessInterfaces()
                                    .getName());
            if (pi != null) {
                ItemProviderAdapter adapter =
                        (ItemProviderAdapter) af.adapt(pi,
                                IEditingDomainItemProvider.class);
                if (adapter != null) {
                    adapter.removeListener(this);
                    adapter.addListener(this);
                }
            } else {
                // Ravi- look into
                /*
                 * ItemProviderAdapter adapter = (ItemProviderAdapter)
                 * af.adapt(pi, IEditingDomainItemProvider.class);
                 * adapter.removeListener(this);
                 */
            }
        }
    }

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.AbstractAssetGroup#getDecorationMarker()
     *      Overided this method to ensure that process interface errors are not
     *      dependent on the process errors. We do our own callcations here to
     *      see if the process interface shows a warning, error or nothing at
     *      all!
     * @return
     */
    @Override
    public int getDecorationMarker() {
        WorkingCopy wc =
                WorkingCopyUtil.getWorkingCopyFor((EObject) getParent());
        if (wc != null) {
            IResource res = wc.getEclipseResources().get(0);
            try {
                Object values = ((EObject) getParent()).eGet(getFeature());
                if (values instanceof ProcessInterfaces) {
                    values = ((ProcessInterfaces) values).getProcessInterface();
                    if (values instanceof Collection) {
                        int result = 0;
                        int cr;
                        for (Iterator iter = ((Collection) values).iterator(); iter
                                .hasNext();) {
                            Object obj = iter.next();
                            if (obj instanceof EObject) {
                                cr =
                                        XPDProblemDecorator
                                                .getSeverity((EObject) obj, res);
                                if (cr == IMarker.SEVERITY_ERROR) {
                                    return cr;
                                } else if (cr == IMarker.SEVERITY_WARNING) {
                                    result = cr;
                                }
                            }
                        }
                        return result;
                    }
                }
            } catch (CoreException e) {
                // ignore
            }
        }
        return 0;
    }

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.AbstractAssetGroup#isAssetGroupElementTypeExcluded()
     * 
     * @return
     */
    @Override
    public boolean isAssetGroupElementTypeExcluded() {

        EObject eObject =
                Xpdl2ModelUtil.getAncestor(parent, ProcessInterface.class);
        if (eObject instanceof ProcessInterface) {

            ProcessInterface processInterface = (ProcessInterface) eObject;
            /*
             * Check extension point contributions for exclusion of this
             * particular element type.
             */
            Set<ProcessEditorElementType> excludedElementTypes =
                    ProcessEditorConfigurationUtil
                            .getExcludedElementTypes(processInterface);

            if (excludedElementTypes
                    .contains(ProcessEditorElementType.process_interface)
                    && excludedElementTypes
                            .contains(ProcessEditorElementType.service_process_interface)) {
                return true;
            }
        }

        return false;
    }
}