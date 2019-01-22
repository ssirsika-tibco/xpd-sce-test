package com.tibco.xpd.bom.modeler.diagram.part;

import java.util.List;
import java.util.Map;

import org.eclipse.gef.Request;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateUnspecifiedTypeConnectionRequest;
import org.eclipse.gmf.runtime.diagram.ui.tools.UnspecifiedTypeConnectionTool;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;

public class CustomUnspecifiedTypeConnectionTool extends UnspecifiedTypeConnectionTool {
    
    private String aggKind;    
    private List relationshipTypes;

    public CustomUnspecifiedTypeConnectionTool(List relationshipTypes) {
        super(relationshipTypes);        
        this.relationshipTypes = relationshipTypes;
    }

    public void setAggregationKind(String aggregation) {
        this.aggKind = aggregation;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Request createTargetRequest() {
        Request req = super.createTargetRequest();        
        Map extendedData = req.getExtendedData();
        extendedData.put("AggKind", aggKind); //$NON-NLS-1$
        
        if (req instanceof CreateUnspecifiedTypeConnectionRequest){
            CreateUnspecifiedTypeConnectionRequest myReq = (CreateUnspecifiedTypeConnectionRequest) req;
            
            for (Object relType: relationshipTypes){
                if (relType instanceof IElementType) {
                    IElementType type = (IElementType) relType;                    
                    if (myReq.getRequestForType(type)!=null){
                        CreateRequest crReq = myReq.getRequestForType(type);
                        crReq.setExtendedData(extendedData);
                    }  
                }
            }
     
        }

        return req;
    }
}