/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.rql.parser.script;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.om.core.om.Capability;
import com.tibco.xpd.om.core.om.Group;
import com.tibco.xpd.om.core.om.Location;
import com.tibco.xpd.om.core.om.NamedElement;
import com.tibco.xpd.om.core.om.OrgTypedElement;
import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.core.om.Position;
import com.tibco.xpd.om.core.om.Privilege;
import com.tibco.xpd.om.core.om.QualifiedOrgElement;
import com.tibco.xpd.om.core.om.Resource;
import com.tibco.xpd.rql.model.RQLConsts;
import com.tibco.xpd.rql.parser.eval.EntityType;
import com.tibco.xpd.script.model.client.DefaultScriptRelevantData;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.model.internal.client.IModelScriptRelevantData;

/**
 * 
 * 
 * @author Miguel Torres
 * 
 */
public class RQLScriptRelevantData extends DefaultScriptRelevantData implements IModelScriptRelevantData{
    
    private String className = null;
    
    private EObject model;
    
    RQLJsClass jsClass = null;
    
    public RQLScriptRelevantData(EObject model) {
        this.model = model;
        createJsClass();
    }

    public EObject getModel() {
        return model;
    }
    
    public JsClass getJsClass() {
        if(jsClass == null){
            createJsClass();
        }
        return jsClass;
    }
    
    protected JsClass createJsClass() {
        jsClass = new RQLJsClass(model);
        jsClass.setIcon(RQLConsts.getOmIcon(model)); 
        jsClass.setContentAssistIconProvider(RQLConsts.getRQLContentAssistIconProvider());
        className = jsClass.getName();
        return jsClass;
    }

    private String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
    
    @Override
    public String getName() {        
        return getClassName();
    }
    
    @Override
    public String getType() {
        EObject type = getModel();
        if (type instanceof Privilege) {
            return EntityType.PRIVILEGE.toString();
        } else if (type instanceof Capability) {
            return EntityType.CAPABILITY.toString();
        } else if (type instanceof Organization) {
            return EntityType.ORGANIZATION.toString();
        } else if (type instanceof OrgUnit) {
            return EntityType.ORGANIZATIONAL_UNIT.toString();
        } else if (type instanceof Position) {
            return EntityType.POSITION.toString();
        } else if (type instanceof Resource) {
            return EntityType.RESOURCE.toString();
        } else if (type instanceof Group) {
            return EntityType.GROUP.toString();
        } else if (type instanceof Location) {
            return EntityType.LOCATION.toString();
        }
        return null;
    }
    
    public EObject getModelType() {
        if(getModel() instanceof OrgTypedElement){
            return ((OrgTypedElement)getModel()).getType();
        }
        return null;
    }
    
    public String getModelTypeName() {
        if (getModelType() instanceof NamedElement) {
            return ((NamedElement) getModelType()).getName();
        }
        return null;
    }
    
    public EObject getQualifier() {
        if (getModel() instanceof QualifiedOrgElement) {
            return ((QualifiedOrgElement) getModel()).getQualifierAttribute();
        }
        return null;
    }
    
    @Override
    public Image getIcon() {
        return RQLConsts.getOmIcon(getModel());
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RQLScriptRelevantData) {
            RQLScriptRelevantData compare = (RQLScriptRelevantData) obj;
            if (compare.getModel() != null && getModel() != null
                    && compare.getModel().equals(getModel())) {
                return true;
            }
        }
        return false;
    }

}
