/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.properties.sections;

import java.util.Collection;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.TableViewer;

import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.resources.ui.properties.sections.internal.controls.table.columns.AllocationMethodColumn;
import com.tibco.xpd.om.resources.ui.properties.sections.internal.general.groups.OrgElementGroupSection;

/**
 * Properties section for the Groups tab.
 * 
 * @author rgreen
 * 
 */
public class GroupsTabSection extends OrgElementGroupSection implements IFilter {

    public GroupsTabSection() {
        super();
    }

    @Override
    protected void addColumns(TableViewer viewer) {
        super.addColumns(viewer);
        new AllocationMethodColumn(getEditingDomain(), viewer);
    }

    @Override
    public boolean select(Object toTest) {

        if (toTest instanceof GraphicalEditPart) {
            GraphicalEditPart gep = (GraphicalEditPart) toTest;

            if (gep.resolveSemanticElement() instanceof OrgModel
                    || gep.resolveSemanticElement() instanceof Organization) {
                return true;
            }
        } else {
            EObject eo = resollveInput(toTest);
            return eo instanceof OrgModel;
        }

        return false;
    }

    @Override
    protected void doRefresh() {
        // TODO Auto-generated method stub
        super.doRefresh();
    }

    @Override
    protected Collection<?> getElements(EObject input) {
        if (input instanceof OrgModel) {
            OrgModel om = (OrgModel) input;
            return (om.getGroups());
        }
        return super.getElements(input);
    }

    @Override
    protected Command doGetCommand(Object obj) {
        // TODO Auto-generated method stub
        return super.doGetCommand(obj);
    }

    @Override
    protected EObject resollveInput(Object object) {

        if (object instanceof GraphicalEditPart) {
            GraphicalEditPart gep = (GraphicalEditPart) object;

            if (gep.resolveSemanticElement() instanceof OrgModel) {
                return gep.resolveSemanticElement();
            }
        } else {
            EObject eo = resollveInput(object);
            return eo;
        }

        return super.resollveInput(object);
    }

}
