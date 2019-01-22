/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.bom.resource.ui.quicksearch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PrimitiveType;

import com.tibco.xpd.bom.resources.ui.internal.Messages;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchContentProvider;
import com.tibco.xpd.resources.ui.api.quicksearch.QuickSearchPopupCategory;

/**
 * BOM Editor quick search popup content provider.
 * 
 * @author aallway
 * @since 3.2
 */
public class BomEditorQuickSearchContentProvider extends
        AbstractQuickSearchContentProvider {

    private static final String BOM_PACKAGES_QS_CATEGORY_ID =
            "editor.bom.packages.category"; //$NON-NLS-1$

    private static final String BOM_CLASSES_QS_CATEGORY_ID =
            "editor.bom.classes.category"; //$NON-NLS-1$

    private static final String BOM_PRIMATIVE_TYPES_QS_CATEGORY_ID =
            "editor.bom.primitivetypes.category"; //$NON-NLS-1$

    private static final String BOM_ENUMERATIONS_QS_CATEGORY_ID =
            "editor.bom.enumerations.category"; //$NON-NLS-1$

    private List<Package> availablePackages = new ArrayList<Package>();

    private List<Class> availableClasses = new ArrayList<Class>();

    private List<Enumeration> availableEnumerations =
            new ArrayList<Enumeration>();

    private List<PrimitiveType> availablePrimitiveTypes =
            new ArrayList<PrimitiveType>();

    private List<QuickSearchPopupCategory> searchCategories;

    public BomEditorQuickSearchContentProvider(IWorkbenchPartReference partRef) {
        super(partRef);

        EditorPart editor = (EditorPart) partRef.getPart(false);

        WorkingCopy wc = (WorkingCopy) editor.getAdapter(WorkingCopy.class);
        if (wc != null) {
            EObject root = wc.getRootElement();
            searchAndStoreRelevantElements(root);
        }

        initSearchCategories();
    }

    /**
     * @param parent
     */
    private void searchAndStoreRelevantElements(EObject parent) {
        for (EObject element : parent.eContents()) {

            if (element instanceof Package) {
                availablePackages.add((Package) element);
            } else if (element instanceof Class) {
                availableClasses.add((Class) element);
            } else if (element instanceof Enumeration) {
                availableEnumerations.add((Enumeration) element);
            } else if (element instanceof PrimitiveType) {
                availablePrimitiveTypes.add((PrimitiveType) element);
            }

            searchAndStoreRelevantElements(element);
        }
    }

    @Override
    public void dispose() {

    }

    @Override
    public Collection<QuickSearchPopupCategory> getCategories() {
        return searchCategories;
    }

    @Override
    public Collection<?> getElements() {
        return getElements(Collections.EMPTY_LIST);
    }

    @Override
    public Collection<?> getElements(
            Collection<QuickSearchPopupCategory> categories) {

        List<Object> searchSet = new ArrayList<Object>();

        if (isCategoryEnabled(categories, BOM_PACKAGES_QS_CATEGORY_ID)) {
            searchSet.addAll(availablePackages);
        }
        if (isCategoryEnabled(categories, BOM_CLASSES_QS_CATEGORY_ID)) {
            searchSet.addAll(availableClasses);
        }
        if (isCategoryEnabled(categories, BOM_PRIMATIVE_TYPES_QS_CATEGORY_ID)) {
            searchSet.addAll(availablePrimitiveTypes);
        }
        if (isCategoryEnabled(categories, BOM_ENUMERATIONS_QS_CATEGORY_ID)) {
            searchSet.addAll(availableEnumerations);
        }

        return searchSet;
    }

    /**
     * Initialise the search categories list.
     */
    private void initSearchCategories() {
        searchCategories = new ArrayList<QuickSearchPopupCategory>();

        searchCategories.add(new QuickSearchPopupCategory(
                BOM_PACKAGES_QS_CATEGORY_ID,
                Messages.BomEditorQuickSearchContentProvider_packages_menu));

        searchCategories.add(new QuickSearchPopupCategory(
                BOM_CLASSES_QS_CATEGORY_ID,
                Messages.BomEditorQuickSearchContentProvider_classes_menu));

        searchCategories.add(new QuickSearchPopupCategory(
                BOM_PRIMATIVE_TYPES_QS_CATEGORY_ID,
                Messages.BomEditorQuickSearchContentProvider_primitives_menu));

        searchCategories
                .add(new QuickSearchPopupCategory(
                        BOM_ENUMERATIONS_QS_CATEGORY_ID,
                        Messages.BomEditorQuickSearchContentProvider_enumerations_menu));

    }

}
