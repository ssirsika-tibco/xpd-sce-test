package com.tibco.xpd.bom.modeler.diagram.part;

import java.util.Map;

import org.eclipse.gef.Request;
import org.eclipse.gmf.runtime.diagram.ui.tools.ConnectionCreationTool;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.uml2.uml.Stereotype;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;

public class AssociationConnectionTool extends ConnectionCreationTool {

    private String aggKind;

    private Stereotype stereotype;

    public AssociationConnectionTool(IElementType type) {
        super(type);
    }

    public void setAggregationKind(String aggregation) {
        this.aggKind = aggregation;
    }

    @Override
    public IElementType getElementType() {
        // TODO Auto-generated method stub
        return super.getElementType();
    }

    public void setStereotype(Stereotype stereotype) {
        this.stereotype = stereotype;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Request createTargetRequest() {
        Request req = super.createTargetRequest();
        Map extendedData = req.getExtendedData();
        extendedData.put("AggKind", aggKind); //$NON-NLS-1$
        extendedData
                .put(BOMResourcesPlugin.CREATE_ELEMENT_REQUEST_PARAM.STEREOTYPE,
                        stereotype);

        // if (req instanceof CreateUnspecifiedTypeConnectionRequest) {
        // CreateUnspecifiedTypeConnectionRequest myReq =
        // (CreateUnspecifiedTypeConnectionRequest) req;
        //
        // for (Object relType : relationshipTypes) {
        // if (relType instanceof IElementType) {
        // IElementType type = (IElementType) relType;
        // if (myReq.getRequestForType(type) != null) {
        // CreateRequest crReq = myReq.getRequestForType(type);
        // crReq.setExtendedData(extendedData);
        // }
        // }
        // }
        //
        // }

        return req;
    }
}