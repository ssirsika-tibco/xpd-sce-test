/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.om.core.om.provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.command.CreateChildCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;

import com.tibco.xpd.om.core.om.NamedElement;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.projectexplorer.decorator.XPDDecorationStatusProvider;
import com.tibco.xpd.ui.projectexplorer.decorator.XPDProblemDecorator;

/**
 * Base class for transient item providers. Transient item providers represent
 * elements which does not exist in the model but are displayed in viewers as
 * grouping elements. The parent of the groups should have statefull item
 * provider which creates the groups (singletons) and exposes them as children.
 * 
 * <p>
 * <i>Created: 4 Jan 2008</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
public class TransientItemProvider extends ItemProviderAdapter implements
        IEditingDomainItemProvider, IStructuredItemContentProvider,
        ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource,
        IEditingDomainProvider, XPDDecorationStatusProvider {

    /**
     * 
     * <p>
     * <i>Created: 29 Jan 2009</i>
     * </p>
     * 
     * @author Jan Arciuchiewicz
     */
    protected final class CreateChildCommandWithLabel extends
            CreateChildCommand {
        private String text;
        private String toolTipText;

        /** */
        private final EObject objectTarget;

        /**
         * @param domain
         * @param owner
         * @param feature
         * @param child
         * @param index
         * @param selection
         * @param helper
         * @param objectTarget
         */
        protected CreateChildCommandWithLabel(EditingDomain domain,
                EObject owner, EStructuralFeature feature, Object child,
                int index, Collection<?> selection, Helper helper,
                EObject objectTarget) {
            super(domain, owner, feature, child, index, selection, helper);
            this.objectTarget = objectTarget;
        }

        @Override
        public Collection<?> getAffectedObjects() {
            Collection<?> affected = super.getAffectedObjects();
            if (affected.contains(objectTarget))
                affected = Collections.singleton(TransientItemProvider.this);
            return affected;
        }

        /** {@inheritDoc} */
        @Override
        public String getText() {
            if (text != null) {
                return text;
            }
            return super.getText();
        }

        /** {@inheritDoc} */
        @Override
        public String getToolTipText() {
            if (toolTipText != null) {
                return toolTipText;
            }
            return super.getToolTipText();
        }

        /**
         * @param text
         *            the text to set
         */
        public void setText(String text) {
            this.text = text;
        }

        /**
         * @param toolTipText
         *            the toolTipText to set
         */
        public void setToolTipText(String toolTipText) {
            this.toolTipText = toolTipText;
        }

    }

    /**
     * The constructor.
     * 
     * @param adapterFactory
     *            the adapter factory for the model.
     * @param object
     *            parent for the group.
     */
    public TransientItemProvider(AdapterFactory adapterFactory, EObject eObject) {
        super(adapterFactory);
        eObject.eAdapters().add(this);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.emf.edit.provider.ItemProviderAdapter#getChildren(java.lang
     * .Object)
     */
    @Override
    public Collection<?> getChildren(Object object) {
        return super.getChildren(target);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.emf.edit.provider.ItemProviderAdapter#getParent(java.lang
     * .Object)
     */
    @Override
    public Object getParent(Object object) {
        return target;
    }

    /**
     * Returns true if provider is a parent for the feature. This method can be
     * overwritten in subtypes. If the same feature should appear under two
     * groups this method must be overwritten.
     * 
     * @param feature
     *            the feature to be checked.
     * @return true if the provider is a parent for the feature.
     */
    protected boolean isParentForFeature(Object feature) {
        Collection<? extends EStructuralFeature> features = getChildrenFeatures(target);
        return features.contains(feature);
    }

    /**
     * Return the resource locator for this item provider's resources.
     */
    @Override
    public ResourceLocator getResourceLocator() {
        return OrganisationModelEditPlugin.INSTANCE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.emf.edit.domain.IEditingDomainProvider#getEditingDomain()
     */
    public EditingDomain getEditingDomain() {
        return AdapterFactoryEditingDomain.getEditingDomainFor(target);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.emf.edit.provider.ItemProviderAdapter#factorRemoveCommand
     * (org.eclipse.emf.edit.domain.EditingDomain,
     * org.eclipse.emf.edit.command.CommandParameter)
     */
    @Override
    protected Command factorRemoveCommand(EditingDomain domain,
            CommandParameter commandParameter) {
        if (commandParameter.getOwner() instanceof TransientItemProvider) {
            commandParameter.setOwner(target);
        }
        return super.factorRemoveCommand(domain, commandParameter);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.emf.edit.provider.ItemProviderAdapter#getNewChildDescriptors
     * (java.lang.Object, org.eclipse.emf.edit.domain.EditingDomain,
     * java.lang.Object)
     */
    @Override
    public Collection<?> getNewChildDescriptors(Object object,
            EditingDomain editingDomain, Object sibling) {
        // Build the collection of new child descriptors.
        Collection<Object> newChildDescriptors = new ArrayList<Object>();
        collectNewChildDescriptors(newChildDescriptors, object);
        return newChildDescriptors;
    }

    @Override
    protected Command createCreateChildCommand(EditingDomain domain,
            EObject owner, EStructuralFeature feature, Object value, int index,
            Collection<?> collection) {
        final EObject eObjectTarget = (EObject) target;
        return new CreateChildCommandWithLabel(domain, eObjectTarget, feature,
                value, index, collection, this, eObjectTarget);
    }

    /**
     * @param elements
     * @return
     */
    protected Collection<String> getNamesArray(Collection<?> elements) {
        Iterator<?> it = elements.iterator();
        ArrayList<String> names = new ArrayList<String>();
        while (it.hasNext()) {
            Object eObject = it.next();
            if (eObject instanceof NamedElement) {
                names.add(((NamedElement) eObject).getName());
            }
        }
        return names;
    }

    /**
     * @param elements
     * @return
     */
    protected Collection<String> getDisplayNamesArray(Collection<?> elements) {
        Iterator<?> it = elements.iterator();
        ArrayList<String> displayNames = new ArrayList<String>();
        while (it.hasNext()) {
            Object eObject = it.next();
            if (eObject instanceof NamedElement) {
                displayNames.add(((NamedElement) eObject).getDisplayName());
            }
        }
        return displayNames;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.projectexplorer.decorator.XPDDecorationStatusProvider
     * #getDecorationMarker()
     */
    public int getDecorationMarker() {
        int result = 0;
        Collection<?> children = getChildren(target);
        if (children != null && !children.isEmpty()) {
            IFile file = WorkingCopyUtil.getFile((EObject) target);

            if (file != null) {
                for (Object child : children) {
                    if (child instanceof EObject) {
                        try {
                            int severity = XPDProblemDecorator.getSeverity(
                                    (EObject) child, file);
                            if (severity == IMarker.SEVERITY_ERROR) {
                                return severity;
                            } else if (severity == IMarker.SEVERITY_WARNING) {
                                result = severity;
                            }
                        } catch (CoreException e) {
                            // Ignore error
                        }
                    }
                }
            }
        }
        return result;
    }

}
