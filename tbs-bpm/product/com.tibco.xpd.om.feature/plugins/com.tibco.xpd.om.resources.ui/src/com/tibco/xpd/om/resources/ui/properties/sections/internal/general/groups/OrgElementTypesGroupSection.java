/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.properties.sections.internal.general.groups;

import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.jface.viewers.TableViewer;

import com.tibco.xpd.om.core.om.OrgElementType;
import com.tibco.xpd.om.resources.ui.properties.sections.internal.controls.table.columns.LabelColumn;
import com.tibco.xpd.om.resources.ui.properties.sections.internal.controls.table.columns.NameColumn;
import com.tibco.xpd.ui.util.CapabilityUtil;

/**
 * Group section for all {@link OrgElementType}s' general tab.
 * 
 * @author njpatel
 * 
 */
public class OrgElementTypesGroupSection extends AbstractGroupSection {

    @Override
    protected void addColumns(TableViewer viewer) {
        new LabelColumn(getEditingDomain(), viewer);
        if (CapabilityUtil.isDeveloperActivityEnabled()) {
            new NameColumn(getEditingDomain(), viewer);
        }
    }

    @Override
    protected boolean isRequiredChildDescriptor(CommandParameter descriptor) {
        return descriptor != null && descriptor.getValue() != null
                && descriptor.getValue() instanceof OrgElementType;
    }
}
