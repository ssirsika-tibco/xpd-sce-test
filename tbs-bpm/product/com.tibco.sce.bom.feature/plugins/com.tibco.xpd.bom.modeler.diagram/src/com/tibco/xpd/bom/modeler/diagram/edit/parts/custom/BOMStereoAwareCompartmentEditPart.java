/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.edit.parts.custom;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.CompartmentEditPart;
import org.eclipse.uml2.uml.Element;

import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationClassEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.ClassEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.EnumerationEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.PackageEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.PrimitiveTypeEditPart;
import com.tibco.xpd.bom.resources.firstclassprofiles.FirstClassProfileManager;

/**
 * 
 * An extension of CompartmentEditPart that includes a resource listener for
 * stereotype changes. When a resource change event is received the listener
 * delegates to a method to test whether the notification relates to an addition
 * or removal of a stereotype. If it does then the edit part refresh method is
 * invoked.
 * 
 * 
 * @author rgreen
 * 
 */
public class BOMStereoAwareCompartmentEditPart extends CompartmentEditPart {

    public BOMStereoAwareCompartmentEditPart(EObject model) {
        super(model);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void refresh() {
        super.refresh();
    }

    public void refreshStereotype() {
        Boolean showLabel = false;
        EObject elem = resolveSemanticElement();

        if ((elem != null) && (elem instanceof Element)) {
            Element clsr = (Element) elem;

            // Check if 1st class profile is set, because we won't want to
            // show a label if there
            if (FirstClassProfileManager.getInstance()
                    .isFirstClassProfileApplied(clsr.getModel())) {
                showLabel = false;
            } else if (!clsr.getAppliedStereotypes().isEmpty()) {
                showLabel = true;
            }

            // Delegate rebuilding the figure to the label editpart
            EditPart parentEP = getParent();

            if (parentEP instanceof ClassEditPart) {
                if (showLabel) {
                    ((ClassEditPart) parentEP).getCustFigure()
                            .rebuildFigureWithStereo(true);
                } else {
                    ((ClassEditPart) parentEP).getCustFigure()
                            .rebuildFigureWithStereo(false);
                }
            }

            if (parentEP instanceof PackageEditPart) {
                if (showLabel) {
                    ((PackageEditPart) parentEP).getCustFigure()
                            .rebuildFigureWithStereo(true);
                } else {
                    ((PackageEditPart) parentEP).getCustFigure()
                            .rebuildFigureWithStereo(false);
                }
            }

            if (parentEP instanceof PrimitiveTypeEditPart) {
                if (showLabel) {
                    ((PrimitiveTypeEditPart) parentEP).getCustFigure()
                            .rebuildFigureWithStereo(true);
                } else {
                    ((PrimitiveTypeEditPart) parentEP).getCustFigure()
                            .rebuildFigureWithStereo(false);
                }
            }

            if (parentEP instanceof EnumerationEditPart) {
                if (showLabel) {
                    ((EnumerationEditPart) parentEP).getCustFigure()
                            .rebuildFigureWithStereo(true);
                } else {
                    ((EnumerationEditPart) parentEP).getCustFigure()
                            .rebuildFigureWithStereo(false);
                }
            }

            if (parentEP instanceof AssociationClassEditPart) {
                if (showLabel) {
                    ((AssociationClassEditPart) parentEP).getCustFigure()
                            .rebuildFigureWithStereo(true);
                } else {
                    ((AssociationClassEditPart) parentEP).getCustFigure()
                            .rebuildFigureWithStereo(false);
                }
            }

        }

        super.refresh();
    }

}
