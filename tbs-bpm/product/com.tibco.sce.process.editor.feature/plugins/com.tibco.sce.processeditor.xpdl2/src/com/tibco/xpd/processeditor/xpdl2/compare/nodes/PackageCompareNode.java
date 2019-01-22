/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.compare.nodes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.compare.ITypedElement;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.compare.Messages;
import com.tibco.xpd.processeditor.xpdl2.compare.nodes.base.NamedElementCompareNode;
import com.tibco.xpd.resources.ui.compare.EMFCompareNodeFactory;
import com.tibco.xpd.resources.ui.compare.nodes.XpdPropertyInfoNode;
import com.tibco.xpd.resources.ui.compare.nodes.emf.EFeatureMapCompareNode;
import com.tibco.xpd.resources.ui.compare.nodes.emf.EMFCompareNode;
import com.tibco.xpd.resources.ui.compare.nodes.emf.EObjectCompareNode;
import com.tibco.xpd.resources.ui.compare.nodes.emf.WrappedEListCompareNode;
import com.tibco.xpd.xpdExtension.ProcessInterfaces;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.RedefinableHeader;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * Compare node for process package.
 * <p>
 * A special compare node is necessary because although pools, aritfacts,
 * message flows and associations are children of Package in the model, as far
 * as the user is concerned they are children of Process.
 * 
 * @author aallway
 * @since 19 Oct 2010
 */
public class PackageCompareNode extends NamedElementCompareNode {

    private Package pkg;

    private EObjectCompareNode processInterfaces;

    private EFeatureMapCompareNode otherElements;

    /**
     * @param object
     * @param listIndex
     * @param feature
     * @param parentCompareNode
     * @param compareNodeFactory
     */
    public PackageCompareNode(Package pkg, int listIndex,
            EStructuralFeature feature, Object parentCompareNode,
            EMFCompareNodeFactory compareNodeFactory) {
        super(pkg, listIndex, feature, parentCompareNode, compareNodeFactory);
        this.pkg = pkg;
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.emf.EObjectCompareNode#createChildren(com.tibco.xpd.resources.ui.compare.EMFCompareNodeFactory)
     * 
     * @param compareNodeFactory
     * @return
     */
    @Override
    protected Object[] createChildren(EMFCompareNodeFactory compareNodeFactory) {
        List<Object> children = new ArrayList<Object>();

        Object[] defaultChildren = super.createChildren(compareNodeFactory);
        if (defaultChildren != null) {
            for (Object child : defaultChildren) {
                children.add(child);
            }
        }

        /*
         * Add process interface list in process interfaces element.
         */
        ProcessInterfaces interfaces =
                ProcessInterfaceUtil.getProcessInterfaces(pkg);
        if (interfaces != null) {
            children.add(new ProcessInterfaceListCompareNode(interfaces, this,
                    compareNodeFactory));
        }

        return children.toArray();
    }

    /**
     * A special compare node is necessary because although pools, aritfacts and
     * associations are children of Package in the model, as far as the user is
     * concerned they are children of Process.
     * 
     * @see com.tibco.xpd.resources.ui.compare.nodes.emf.EMFCompareNode#excludeChildFeature(org.eclipse.emf.ecore.EStructuralFeature,
     *      java.lang.Object)
     * 
     * @param feature
     * @param child
     * @return
     */
    @Override
    protected boolean excludeChildFeature(EStructuralFeature feature,
            Object child) {
        if (feature == Xpdl2Package.eINSTANCE.getPackage_Pools()) {
            return true;
        } else if (feature == Xpdl2Package.eINSTANCE.getPackage_Associations()) {
            return true;
        } else if (feature == Xpdl2Package.eINSTANCE.getPackage_Artifacts()) {
            return true;
        } else if (feature == Xpdl2Package.eINSTANCE.getPackage_MessageFlows()) {
            return true;
        }

        return super.excludeChildFeature(feature, child);
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.compare.nodes.base.NamedElementCompareNode#createInfoPropertyChildren()
     * 
     * @return
     */
    @Override
    protected Collection<XpdPropertyInfoNode> createInfoPropertyChildren() {
        Collection<XpdPropertyInfoNode> props =
                super.createInfoPropertyChildren();

        String author = null;
        String version = null;

        RedefinableHeader packageHeader = pkg.getRedefinableHeader();

        if (packageHeader != null) {
            author = packageHeader.getAuthor();
            version = packageHeader.getVersion();
        }

        props.add(new XpdPropertyInfoNode(
                Messages.PackageCompareNode_Author_label + " " //$NON-NLS-1$
                        + (author != null ? author : ""), 20, //$NON-NLS-1$
                this, this.getType(), "authorInfo")); //$NON-NLS-1$

        props.add(new XpdPropertyInfoNode(
                Messages.PackageCompareNode_Version_label + " " //$NON-NLS-1$
                        + (version != null ? version : ""), 20, //$NON-NLS-1$
                this, this.getType(), "authorInfo")); //$NON-NLS-1$

        return props;
    }

    /**
     * @return the pkg
     */
    public Package getPackage() {
        return pkg;
    }

    /**
     * Special case EListCompareNode for processInterfaces that looks after the
     * parent process interfaces element as well (so that the process interfaces
     * list appears as a first class list under package just like it does in
     * project explorer.
     * 
     * @author aallway
     * @since 29 Nov 2010
     */
    private static class ProcessInterfaceListCompareNode extends
            WrappedEListCompareNode {

        private ProcessInterfaces interfaces;

        /**
         * @param list
         * @param feature
         * @param parentCompareNode
         * @param compareNodeFactory
         */
        public ProcessInterfaceListCompareNode(ProcessInterfaces interfaces,
                Object parentCompareNode,
                EMFCompareNodeFactory compareNodeFactory) {
            super(interfaces.getProcessInterface(),
                    XpdExtensionPackage.eINSTANCE
                            .getProcessInterfaces_ProcessInterface(),
                    parentCompareNode, compareNodeFactory,
                    Messages.PackageCompareNode_ProcessInterfaces_label,
                    Xpdl2ResourcesPlugin.getDefault().getImageRegistry()
                            .get(Xpdl2ResourcesConsts.ICON_PROCESSINTERFACE));

            this.interfaces = interfaces;
        }

        /**
         * @see com.tibco.xpd.resources.ui.compare.nodes.emf.EListCompareNode#doAddYourself(org.eclipse.compare.ITypedElement)
         * 
         * @param targetParent
         * @return
         */
        @Override
        protected Object doAddYourself(ITypedElement targetParent) {
            throw new RuntimeException(
                    "When merging enabled, this class will need to create xpdExt:ProcessInterfaces element to host the list"); //$NON-NLS-1$
        }

        /**
         * @see com.tibco.xpd.resources.ui.compare.nodes.emf.EListCompareNode#doRemoveYourself(org.eclipse.compare.ITypedElement)
         * 
         * @param targetParent
         */
        @Override
        protected void doRemoveYourself(ITypedElement targetParent) {
            throw new RuntimeException(
                    "When merging enabled, this class will need to remove xpdExt:ProcessInterfaces element that host the list"); //$NON-NLS-1$
        }

        /**
         * @see com.tibco.xpd.resources.ui.compare.nodes.emf.EListCompareNode#doReplaceYourself(org.eclipse.compare.ITypedElement,
         *      com.tibco.xpd.resources.ui.compare.nodes.emf.EMFCompareNode)
         * 
         * @param targetParent
         * @param targetNode
         * @return
         */
        @Override
        protected Object doReplaceYourself(ITypedElement targetParent,
                EMFCompareNode targetNode) {
            throw new RuntimeException(
                    "When merging enabled, this class will need to replace xpdExt:ProcessInterfaces element that hosts the list"); //$NON-NLS-1$
        }

    }

}
