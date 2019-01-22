package com.tibco.xpd.om.modeler.subdiagram.part.custom;

import java.util.List;
import java.util.Map;

import org.eclipse.gef.Request;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateUnspecifiedTypeConnectionRequest;
import org.eclipse.gmf.runtime.diagram.ui.tools.UnspecifiedTypeConnectionTool;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;

import com.tibco.xpd.om.core.om.OrgElementType;

/**
 * 
 * A customised UnspecifiedTypeConnectionTool that facilitates setting extended
 * data in the tool's request.
 * 
 * @author rgreen
 * 
 */
public class OrgUnitRelUnspecifiedTypeConnectionTool extends
        UnspecifiedTypeConnectionTool {

    private List relationshipTypes;
    private Boolean isHierarchical;
    private OrgElementType relType;

    public OrgUnitRelUnspecifiedTypeConnectionTool(List relationshipTypes) {
        super(relationshipTypes);
        this.relationshipTypes = relationshipTypes;
    }

    public OrgUnitRelUnspecifiedTypeConnectionTool(List relationshipTypes,
            OrgElementType relType) {
        super(relationshipTypes);
        this.relationshipTypes = relationshipTypes;
        this.relType = relType;
    }

    public OrgUnitRelUnspecifiedTypeConnectionTool(List relationshipTypes,
            OrgElementType relType, Boolean isHierarchical) {
        super(relationshipTypes);
        this.relationshipTypes = relationshipTypes;
        this.relType = relType;
        this.isHierarchical = isHierarchical;
    }

    public void setIsHierarchical(Boolean isHierarchical) {
        this.isHierarchical = isHierarchical;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Request createTargetRequest() {
        Request req = super.createTargetRequest();
        Map extendedData = req.getExtendedData();
        if (isHierarchical != null) {
            extendedData
                    .put(
                            IOrganizationModelDiagramConstants.OMCreationToolIsHierarchicalRel,
                            isHierarchical);
        }

        if (relType != null) {
            extendedData
                    .put(
                            IOrganizationModelDiagramConstants.OMCreationToolOrgElementType,
                            relType);

        }

        if (req instanceof CreateUnspecifiedTypeConnectionRequest) {
            CreateUnspecifiedTypeConnectionRequest myReq = (CreateUnspecifiedTypeConnectionRequest) req;

            for (Object relType : relationshipTypes) {
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