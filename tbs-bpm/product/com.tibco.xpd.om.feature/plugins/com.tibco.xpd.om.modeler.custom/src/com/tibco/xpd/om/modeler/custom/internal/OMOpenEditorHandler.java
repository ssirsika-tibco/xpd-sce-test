/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.om.modeler.custom.internal;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.PartInitException;

import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.Position;
import com.tibco.xpd.om.modeler.custom.Activator;
import com.tibco.xpd.om.modeler.diagram.part.OrganizationModelDiagramEditorUtil;
import com.tibco.xpd.resources.ui.editorHandler.IOpenEditorHandler;

/**
 * Handler to open the editor for a given OM object.
 * 
 * @author njpatel
 * 
 */
public class OMOpenEditorHandler implements IOpenEditorHandler {

    public OMOpenEditorHandler() {
    }

    /**
     * @see com.tibco.xpd.resources.ui.editorHandler.IOpenEditorHandler#canHandle(java.lang.Object)
     * 
     * @param object
     * @return
     */
    public boolean canHandle(Object object) {
        return object instanceof OrgModel || object instanceof OrgUnit
                || object instanceof Position;
    }

    /**
     * @see com.tibco.xpd.resources.ui.editorHandler.IOpenEditorHandler#openEditor(java.lang.Object)
     * 
     * @param object
     * @return
     */
    public boolean openEditor(Object object) {
        try {
            return OrganizationModelDiagramEditorUtil
                    .openDiagram((EObject) object);
        } catch (PartInitException e) {
            Activator.getDefault().getLogger().error(e,
                    "Failed to open the Organization Model editor");
        }
        return false;
    }

}
