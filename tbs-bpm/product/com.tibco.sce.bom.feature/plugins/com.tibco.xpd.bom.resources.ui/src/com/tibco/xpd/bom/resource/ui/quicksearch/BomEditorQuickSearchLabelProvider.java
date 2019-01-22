/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.bom.resource.ui.quicksearch;

import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PrimitiveType;

import com.tibco.xpd.bom.resources.ui.internal.Messages;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchLabelProvider;

/**
 * BOM Editor quick search popup label provider.
 * 
 * @author aallway
 * @since 3.2
 */
public class BomEditorQuickSearchLabelProvider extends
        AbstractQuickSearchLabelProvider {

    public BomEditorQuickSearchLabelProvider(IWorkbenchPartReference partRef) {
        super(partRef);
    }

    /**
     * @see com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchLabelProvider#getText(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    public String getText(Object element) {

        if (element instanceof NamedElement) {
            return PrimitivesUtil.getDisplayLabel((NamedElement) element);
        }

        return ""; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchLabelProvider#getElementTypeName(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    public String getElementTypeName(Object element) {

        if (element instanceof Package) {
            return Messages.BomEditorQuickSearchLabelProvider_TypeName_Package_label;
        } else if (element instanceof Class) {
            return Messages.BomEditorQuickSearchLabelProvider_TypeName_Class_label;
        } else if (element instanceof Enumeration) {
            return Messages.BomEditorQuickSearchLabelProvider_TypeName_Enumeration_label;
        } else if (element instanceof PrimitiveType) {
            return Messages.BomEditorQuickSearchLabelProvider_TypeName_PrimitiveType_label;
        }

        return super.getElementTypeName(element);
    }

}
