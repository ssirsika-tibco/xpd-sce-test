package com.tibco.xpd.bom.modeler.diagram.part;

import java.util.List;
import java.util.Map;

import org.eclipse.gef.Request;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateUnspecifiedTypeConnectionRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateUnspecifiedTypeRequest;
import org.eclipse.gmf.runtime.diagram.ui.tools.UnspecifiedTypeCreationTool;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;

public class ConceptTypeCreationTool extends UnspecifiedTypeCreationTool {

    private String stereo;    
    private List elementTypes;
    
    public ConceptTypeCreationTool(List elementTypes) {
        super(elementTypes);
        this.elementTypes = elementTypes;
    }
    
    public void setStereotype(String stereo) {
        this.stereo = stereo;
    }
    
    
    @SuppressWarnings("unchecked")
    @Override
    protected Request createTargetRequest() {
        Request req = super.createTargetRequest();        
        Map extendedData = req.getExtendedData();
        extendedData.put(
                BOMResourcesPlugin.CREATE_ELEMENT_REQUEST_PARAM.STEREOTYPE,
                stereo);
        
        if (req instanceof CreateUnspecifiedTypeRequest){
            CreateUnspecifiedTypeRequest myReq = (CreateUnspecifiedTypeRequest) req;
            
            for (Object relType: elementTypes){
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
