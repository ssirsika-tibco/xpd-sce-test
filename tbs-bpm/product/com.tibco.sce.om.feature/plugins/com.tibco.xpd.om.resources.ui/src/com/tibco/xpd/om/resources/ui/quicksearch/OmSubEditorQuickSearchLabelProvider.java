/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.om.resources.ui.quicksearch;

import org.eclipse.ui.IWorkbenchPartReference;

import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.Position;
import com.tibco.xpd.om.resources.OMResourceUtil;
import com.tibco.xpd.om.resources.ui.internal.Messages;
import com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchLabelProvider;

/**
 * 
 * 
 * @author patkinso
 * @since 25 Jun 2012
 */
public class OmSubEditorQuickSearchLabelProvider extends
        AbstractQuickSearchLabelProvider {

    /**
     * @param partRef
     */
    public OmSubEditorQuickSearchLabelProvider(IWorkbenchPartReference partRef) {
        super(partRef);
    }

    /**
     * @see com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchLabelProvider#getElementTypeName(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    public String getElementTypeName(Object element) {

        if (element instanceof OrgUnit) {
            return Messages.OmEditorQuickSearchLabelProvider_TypeName_OrganisationUnit_label;
        } else if (element instanceof Position) {
            return Messages.OmEditorQuickSearchLabelProvider_TypeName_Position_label;
        }

        return super.getElementTypeName(element);
    }

    /**
     * @see com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchLabelProvider#getText(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    public String getText(Object element) {
        return OMResourceUtil.getSearchLabelText(element);
    }

}
