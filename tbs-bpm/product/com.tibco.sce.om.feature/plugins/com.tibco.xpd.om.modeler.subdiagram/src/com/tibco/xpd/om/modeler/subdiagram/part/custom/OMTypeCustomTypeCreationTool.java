/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.modeler.subdiagram.part.custom;

import java.util.List;
import java.util.Map;

import org.eclipse.gef.Request;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateUnspecifiedTypeRequest;
import org.eclipse.gmf.runtime.diagram.ui.tools.UnspecifiedTypeCreationTool;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;

import com.tibco.xpd.om.core.om.Feature;
import com.tibco.xpd.om.core.om.OrgElementType;

/**
 * 
 * A customised UnspecifiedTypeCreationTool that facilitates setting extended
 * data in the tool's request relating to which OM type should be applied to the
 * created element.
 * 
 * @author rgreen
 * 
 */
public class OMTypeCustomTypeCreationTool extends UnspecifiedTypeCreationTool {

    private Feature feature;
    private OrgElementType orgElemType;
    private List elementTypes;

    public OMTypeCustomTypeCreationTool(List elementTypes, Feature feature) {
        super(elementTypes);
        this.elementTypes = elementTypes;
        this.feature = feature;
    }

    public OMTypeCustomTypeCreationTool(List elementTypes,
            OrgElementType orgElemType) {
        super(elementTypes);
        this.elementTypes = elementTypes;
        this.orgElemType = orgElemType;
    }

    // public void setFeature(Feature feature) {
    // this.feature = feature;
    // }

    // public void setOrgElemeType(OrgElementType orgElemType) {
    // this.orgElemType = orgElemType;
    // }

    @SuppressWarnings("unchecked")
    @Override
    protected Request createTargetRequest() {
        Request req = super.createTargetRequest();
        Map extendedData = req.getExtendedData();

        if (orgElemType != null) {
            extendedData
                    .put(
                            IOrganizationModelDiagramConstants.OMCreationToolOrgElementType,
                            orgElemType); //$NON-NLS-1$ 
        } else {

            extendedData.put(
                    IOrganizationModelDiagramConstants.OMCreationToolFeature,
                    feature); //$NON-NLS-1$
        }

        if (req instanceof CreateUnspecifiedTypeRequest) {
            CreateUnspecifiedTypeRequest myReq = (CreateUnspecifiedTypeRequest) req;

            for (Object relType : elementTypes) {
                if (relType instanceof IElementType) {
                    IElementType type = (IElementType) relType;
                    if (myReq.getRequestForType(type) != null) {
                        CreateRequest crReq = myReq.getRequestForType(type);
                        crReq.setExtendedData(extendedData);
                    }
                }
            }

        }

        return req;
    }

}
