/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.part;

import java.util.Map;

import org.eclipse.gef.Request;
import org.eclipse.gmf.runtime.diagram.ui.tools.CreationTool;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;

/**
 * Creation tool for a BOM type - this will be used for elements specified in
 * profile extensions.
 * 
 * @author njpatel
 * 
 */
public class BOMTypeCreationTool extends CreationTool {

    private Stereotype stereo;

    private PrimitiveType type;

    private final IElementType elementType;

    public BOMTypeCreationTool(IElementType elementType) {
        super();
        this.elementType = elementType;
    }

    @Override
    public IElementType getElementType() {
        return elementType;
    }

    /**
     * Set the {@link Stereotype} to be applied to the element when created.
     * This method is called through introspection when the parent tool entry's
     * tool property is set.
     * 
     * @param stereo
     */
    public void setStereotype(Stereotype stereo) {
        this.stereo = stereo;
    }

    /**
     * Set the type, if applicable, of the element when created (typically for
     * {@link Property Properties}. This method is called through introspection
     * when the parent tool entry's tool property is set.
     * 
     * @param type
     */
    public void setType(PrimitiveType type) {
        this.type = type;
    }

    @SuppressWarnings("unchecked")//$NON-NLS-1$
    @Override
    protected Request createTargetRequest() {
        Request req = super.createTargetRequest();
        Map extendedData = req.getExtendedData();
        extendedData
                .put(BOMResourcesPlugin.CREATE_ELEMENT_REQUEST_PARAM.STEREOTYPE,
                        stereo);
        if (type != null) {
            extendedData
                    .put(BOMResourcesPlugin.CREATE_ELEMENT_REQUEST_PARAM.TYPE,
                            type); //$NON-NLS-1$
        }

        return req;
    }

}
