/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.properties.filter;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.CorrelationDataFolder;
import com.tibco.xpd.xpdExtension.AssociatedCorrelationField;
import com.tibco.xpd.xpdExtension.AssociatedCorrelationFields;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author NWilson
 * 
 */
public class AssociatedCorrelationDataFilter extends ChainingViewerFilter {

    private AbstractMappingSection abstractMappingSection;

    /**
     * @param abstractMappingSection
     */
    public AssociatedCorrelationDataFilter(
            AbstractMappingSection abstractMappingSection) {
        this.abstractMappingSection = abstractMappingSection;
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.filter.ChainingViewerFilter#doSelect(org.eclipse.jface.viewers.Viewer,
     *      java.lang.Object, java.lang.Object)
     * 
     * @param viewer
     * @param parentElement
     * @param element
     * @return
     */
    @Override
    protected boolean doSelect(Viewer viewer, Object parentElement,
            Object element) {
        boolean ok = false;
        if (element instanceof ConceptPath) {
            element = ((ConceptPath) element).getItem();
        }
        if (element instanceof DataField) {
            DataField field = (DataField) element;
            if (field.isCorrelation()) {
                String name = field.getName();
                EObject input = abstractMappingSection.getInput();
                if (name != null && input instanceof Activity) {
                    Activity activity = (Activity) input;
                    Object other =
                            Xpdl2ModelUtil
                                    .getOtherElement(activity,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_AssociatedCorrelationFields());
                    if (other instanceof AssociatedCorrelationFields) {
                        AssociatedCorrelationFields fields =
                                (AssociatedCorrelationFields) other;
                        EList<AssociatedCorrelationField> associatedFields =
                                fields.getAssociatedCorrelationField();
                        if (associatedFields.size() == 0) {
                            ok = true;
                        } else {
                            for (AssociatedCorrelationField associated : associatedFields) {
                                if (name.equals(associated.getName())) {
                                    ok = true;
                                    break;
                                }
                            }
                        }
                    } else if (other == null) {
                        ok = true;
                    }
                }
            }
        } else if (element instanceof CorrelationDataFolder) {
            ok = true;
        }
        return ok;
    }

}
