/**
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.resources.ui.api.quicksearch;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IWorkbenchPartReference;

import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * AbstractQuickSearchLabelProvider
 * <p>
 * Note that for EObject's held within WorkingCopy this label provider should
 * mostly work (except for getElementTypeName()).
 * <p>
 * It also has some default handling for IndexerItem elements (for
 * getElementPath(), getElementName).
 * 
 * @author aallway
 * @since 3.1
 */
public abstract class AbstractQuickSearchLabelProvider extends LabelProvider {

    protected IWorkbenchPartReference workbenchPartRef;

    /**
     * 
     */
    public AbstractQuickSearchLabelProvider(IWorkbenchPartReference partRef) {
        workbenchPartRef = partRef;
    }

    /**
     * @return the workbenchPart
     */
    public IWorkbenchPartReference getWorkbenchPartRef() {
        return workbenchPartRef;
    }

    /**
     * Get the path to the quick search element provided via the
     * {@link AbstractQuickSearchContentProvider}
     * <p>
     * This is used in the bottom of the matching items list.
     * 
     * @param element
     */
    public String getElementPath(Object element) {
        if (element instanceof EObject) {
            IFile file = WorkingCopyUtil.getFile((EObject) element);

            if (file != null) {
                return file.getFullPath().toString();
            }

        } else if (element instanceof IndexerItem) {
            String strUri = ((IndexerItem) element).getURI();

            if (strUri != null) {
                URI uri = URI.createURI(strUri);
                if (uri != null) {

                    String path = uri.toPlatformString(true);

                    return path;
                }
            }
        }

        return ""; //$NON-NLS-1$
    }

    @Override
    public String getText(Object element) {
        if (element instanceof EObject) {
            return WorkingCopyUtil.getText((EObject) element);
        } else if (element instanceof IndexerItem) {
            return ((IndexerItem) element).getName();
        }

        return super.getText(element);
    }

    @Override
    public Image getImage(Object element) {
        if (element instanceof EObject) {
            EObject eo = (EObject) element;

            return WorkingCopyUtil.getImage(eo);
        }
        return null;
    }

    /**
     * Element Type Name for the quick search element provided via the
     * {@link AbstractQuickSearchContentProvider}
     * <p>
     * The element type name is used to display in the quick search matching
     * items list IF the normal LabelProvider.getText() returns null or "".
     * <p>
     * The BIG difference between getElementTypeName() and
     * LabelProvider.getText() is that the element type name is NEVER used to
     * match search patterns. It is used only to display <i>something</i> when
     * an entry with a blank name happens to match the search pattern (i.e. "*"
     * will match objects even when they have no name).
     * 
     * @param element
     * @return
     */
    public String getElementTypeName(Object element) {
        return ""; //$NON-NLS-1$
    }

}
