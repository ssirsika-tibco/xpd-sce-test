/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.internal;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.ui.PartInitException;

import com.tibco.xpd.bom.modeler.custom.Activator;
import com.tibco.xpd.bom.modeler.diagram.part.UMLDiagramEditorUtil;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.resources.ui.editorHandler.IOpenEditorHandler;

/**
 * Open editor handler for the BOM.
 * 
 * @author njpatel
 * 
 */
public class BOMOpenEditorHandler implements IOpenEditorHandler {

    public BOMOpenEditorHandler() {
    }

    @Override
    public boolean canHandle(Object object) {
        if (object instanceof EObject) {
            EClass eClass = ((EObject) object).eClass();
            Resource resource = ((EObject) object).eResource();
            if (resource != null && eClass != null
                    && "uml".equals(eClass.getEPackage().getName())) { //$NON-NLS-1$
                URI uri = resource.getURI();
                return uri != null
                        && BOMResourcesPlugin.BOM_FILE_EXTENSION.equals(uri
                                .fileExtension());
            }
        }
        return false;
    }

    @Override
    public boolean openEditor(Object object) {
        EObject eo = (EObject) object;

        try {
            return UMLDiagramEditorUtil.openDiagram(eo);
        } catch (PartInitException e) {
            Activator.getDefault().getLogger().error(e,
                    "Problem opening BOM editor");
        }

        return false;
    }

}
