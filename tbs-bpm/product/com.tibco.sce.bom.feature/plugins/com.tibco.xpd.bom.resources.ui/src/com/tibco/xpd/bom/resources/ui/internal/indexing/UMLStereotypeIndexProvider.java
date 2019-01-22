/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.bom.resources.ui.internal.indexing;

import java.beans.PropertyChangeEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.provider.ComposedImage;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.edit.providers.StereotypeItemProvider;
import org.eclipse.uml2.uml.edit.providers.UMLItemProviderAdapterFactory;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.ui.Activator;
import com.tibco.xpd.bom.resources.ui.BOMImages;
import com.tibco.xpd.bom.resources.ui.providers.BOMItemProviderAdapterFactory;
import com.tibco.xpd.bom.resources.utils.BOMIndexerService;
import com.tibco.xpd.bom.resources.wc.UMLWorkingCopy;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.indexer.WorkingCopyIndexProvider;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Indexer provider for UML stereotypes.
 * 
 * @author njpatel
 * 
 */
public class UMLStereotypeIndexProvider implements WorkingCopyIndexProvider {

    // private final StereotypeItemProvider provider;

    /**
     * Indexer provider for UML stereotypes.
     */
    public UMLStereotypeIndexProvider() {
        // provider = new StereotypeItemProvider(XpdResourcesPlugin.getDefault()
        // .getAdapterFactory());

        // provider = new StereotypeItemProvider(new
        // UMLItemProviderAdapterFactory());

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.indexer.WorkingCopyIndexProvider#getIndexItems
     * (com.tibco.xpd.resources.WorkingCopy)
     */
    public Collection<IndexerItem> getIndexItems(WorkingCopy wc) {
        List<IndexerItem> items = new ArrayList<IndexerItem>();

        if (wc instanceof UMLWorkingCopy && !wc.isInvalidFile()) {
            EObject root = wc.getRootElement();

            if (root != null) {
                if (root instanceof Stereotype) {
                    addStereoType(items, (Stereotype) root);
                }

                TreeIterator<Object> iterator = EcoreUtil.getAllProperContents(
                        root, false);

                while (iterator.hasNext()) {
                    Object next = iterator.next();

                    if (next instanceof Stereotype) {
                        addStereoType(items, (Stereotype) next);
                    }
                }
            }
        }

        return items;
    }

    /**
     * Add <code>Stereotype</code> to the indexer items list.
     * 
     * @param items
     *            indexer items
     * @param type
     *            <code>Stereotype</code> to add to list.
     */
    private void addStereoType(List<IndexerItem> items, Stereotype type) {
        IndexerItem item = getIndexerItem(type);

        // Add item if it has name and URI
        if (item != null && item.getName() != null
                && item.getName().length() > 0 && item.getURI() != null
                && item.getURI().length() > 0) {
            items.add(item);
        }
    }

    public boolean isAffectingEvent(PropertyChangeEvent event) {
        return true;
    }

    private IndexerItem getIndexerItem(Stereotype type) {
        IndexerItemImpl item = null;

        if (type != null) {
            item = new IndexerItemImpl();
            String name = type.getQualifiedName();
            if (name == null) {
                name = type.getName();
            }
            item.setName(name);
            item.setUri(EcoreUtil.getURI(type).toString());

            item.set(BOMIndexerService.INDEXER_ATTR_PROJECT, WorkingCopyUtil
                    .getFile(type).getProject().getName());

            URI imageURI = URI.createPlatformPluginURI(Activator.PLUGIN_ID
                    + "/" //$NON-NLS-1$
                    + BOMImages.STEREOTYPE, true);

            if (imageURI != null) {
                item.set(BOMResourcesPlugin.INDEXER_ATTRIBUTE_IMAGE_URI,
                        imageURI.toString());
            }

            // Determine what type(s) this stereotype can be applied to
            updateTypes(item, type);
        }

        return item;
    }

    private void updateTypes(IndexerItemImpl item, Stereotype type) {
        if (item != null && type != null) {
            EList<Class> metaclasses = type.getAllExtendedMetaclasses();

            if (metaclasses != null) {
                if (canApplyTo(metaclasses, UMLPackage.eINSTANCE.getModel())) {
                    // Stereotype can be applied to model
                    item.set(BOMIndexerService.INDEXER_ATTR_MODEL,
                            BOMIndexerService.VALUE_SET);
                }

                if (canApplyTo(metaclasses, UMLPackage.eINSTANCE.getPackage())) {
                    // Stereotype can be applied to package
                    item.set(BOMIndexerService.INDEXER_ATTR_PACKAGE,
                            BOMIndexerService.VALUE_SET);
                }

                if (canApplyTo(metaclasses, UMLPackage.eINSTANCE.getClass_())) {
                    // Stereotype can be applied to class
                    item.set(BOMIndexerService.INDEXER_ATTR_CLASS,
                            BOMIndexerService.VALUE_SET);
                }

                if (canApplyTo(metaclasses, UMLPackage.eINSTANCE.getProperty())) {
                    // Stereotype can be applied to property
                    item.set(BOMIndexerService.INDEXER_ATTR_PROP,
                            BOMIndexerService.VALUE_SET);
                }

                if (canApplyTo(metaclasses, UMLPackage.eINSTANCE.getOperation())) {
                    // Stereotype can be applied to property
                    item.set(BOMIndexerService.INDEXER_ATTR_OPERATION,
                            BOMIndexerService.VALUE_SET);
                }

                if (canApplyTo(metaclasses, UMLPackage.eINSTANCE
                        .getPrimitiveType())) {
                    // Stereotype can be applied to primitive type
                    item.set(BOMIndexerService.INDEXER_ATTR_PRIMTYPE,
                            BOMIndexerService.VALUE_SET);
                }

                if (canApplyTo(metaclasses, UMLPackage.eINSTANCE
                        .getAssociation())) {
                    // Stereotype can be applied to association
                    item.set(BOMIndexerService.INDEXER_ATTR_ASSOC,
                            BOMIndexerService.VALUE_SET);
                }

                if (canApplyTo(metaclasses, UMLPackage.eINSTANCE
                        .getGeneralization())) {
                    // Stereotype can be applied to generalization
                    item.set(BOMIndexerService.INDEXER_ATTR_GEN,
                            BOMIndexerService.VALUE_SET);
                }

                if (canApplyTo(metaclasses, UMLPackage.eINSTANCE
                        .getEnumeration())) {
                    // Stereotype can be applied to enumeration
                    item.set(BOMIndexerService.INDEXER_ATTR_ENUMERATION,
                            BOMIndexerService.VALUE_SET);
                }

                if (canApplyTo(metaclasses, UMLPackage.eINSTANCE
                        .getEnumerationLiteral())) {
                    // Stereotype can be applied to enumeration literal
                    item.set(BOMIndexerService.INDEXER_ATTR_ENUMLIT,
                            BOMIndexerService.VALUE_SET);
                }

                if (canApplyTo(metaclasses, UMLPackage.eINSTANCE
                        .getAssociationClass())) {
                    // Stereotype can be applied to association class
                    item.set(BOMIndexerService.INDEXER_ATTR_ASSOC_CLASS,
                            BOMIndexerService.VALUE_SET);
                }
            }
        }
    }

    private boolean canApplyTo(EList<Class> metaclasses, EClass eClass) {
        boolean canApply = false;
        List<EClass> types = new ArrayList<EClass>();
        types.add(eClass);
        types.addAll(eClass.getEAllSuperTypes());

        for (EClass type : types) {
            // Check if this is defined in the metaclasses
            for (Class metaclass : metaclasses) {
                if (type.getName().equals(metaclass.getName())) {
                    canApply = true;
                }
            }

            if (canApply) {
                break;
            }
        }

        return canApply;
    }
}
