/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.rcp.internal.models;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.resources.ui.Activator;
import com.tibco.xpd.bom.resources.ui.BOMImages;
import com.tibco.xpd.bom.resources.ui.providers.DiagramGroupTransientItemProvider;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * BOM content label provider.
 * 
 * @author mtorres
 * 
 */
/* public */class BOMOverviewLabelProvider extends AbstractLabelProvider {

    @Override
    public String getText(Object element) {

        if (element instanceof DiagramGroupTransientItemProvider
                || (element instanceof EObject && (isFromUMLPackage((EObject) element)))) {
            return getFactoryLabelProvider().getText(element);
        } else if (element instanceof Diagram) {
            /*
             * For diagrams get the name directly from the model as we don't
             * control its item provider
             */
            return ((Diagram) element).getName();
        }

        return super.getText(element);
    }

    @Override
    public Image getImage(Object element) {

        if (element instanceof StructuredSelection) {
            element = ((StructuredSelection) element).getFirstElement();
        }

        if (element instanceof DiagramGroupTransientItemProvider
                || (element instanceof EObject && isFromUMLPackage((EObject) element))) {

            Image image = getFactoryLabelProvider().getImage(element);
            /*
             * If this is the top-level model then apply the file image overlays
             */
            if (element instanceof Model) {
                IFile file = WorkingCopyUtil.getFile((Model) element);
                if (file != null) {
                    image = getDecorator().decorateImage(image, file);
                }
            }

            return image;
        } else if (element instanceof Diagram) {
            /*
             * For diagrams get custom icon as we don't control its item
             * provider
             */
            return Activator.getDefault().getImageRegistry()
                    .get(BOMImages.DIAGRAM);
        }

        return super.getImage(element);
    }

    @Override
    public String getDescription(Object anElement) {
        if (anElement instanceof DiagramGroupTransientItemProvider) {
            return ((DiagramGroupTransientItemProvider) anElement)
                    .getText(anElement);
        }
        return super.getDescription(anElement);
    }

    /**
     * Check if the given <code>EObject</code> is from the
     * <code>UMLPackage</code>.
     * 
     * @param eo
     *            <code>EObject</code> to test.
     * @return <code>true</code> if from the <code>UMLPackage</code>,
     *         <code>false</code> otherwise.
     */
    private boolean isFromUMLPackage(EObject eo) {
        boolean fromUMLPackage = false;

        if (eo != null && eo.eClass() != null) {
            EPackage pkg = eo.eClass().getEPackage();

            fromUMLPackage = pkg != null && pkg == UMLPackage.eINSTANCE;
        }

        return fromUMLPackage;
    }

}
