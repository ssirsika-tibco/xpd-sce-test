/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.rql.parser.script;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.om.core.om.Attribute;
import com.tibco.xpd.om.core.om.NamedElement;
import com.tibco.xpd.om.core.om.OrgElementType;
import com.tibco.xpd.om.core.om.Resource;
import com.tibco.xpd.om.core.om.ResourceType;
import com.tibco.xpd.script.model.client.JsAttribute;
/**
 *  
 * @author Miguel Torres
 * 
 */
public class RQLJsClass extends AbstractRQLJsClass {
    
    private EObject eObject;
    
    public RQLJsClass(EObject eObject) {
        this.eObject = eObject;
    }

    public String getName() {
        if(eObject instanceof NamedElement){
            return ((NamedElement)eObject).getName();
        }
        return null;
    }
    
    @Override
    public List<JsAttribute> getAttributeList() {
        if (eObject instanceof Resource) {
            Resource resource = (Resource) eObject;
            OrgElementType type = resource.getType();
            if (type instanceof ResourceType) {
                ResourceType resourceType = (ResourceType) type;
                EList<Attribute> attributes = resourceType.getAttributes();
                List<JsAttribute> attributeList = new ArrayList<JsAttribute>();
                for (Attribute attribute : attributes) {
                    if (attribute != null) {
                        attributeList.add(createJsAttribute(attribute));
                    }
                }
                return attributeList;
            }
        }
        return Collections.emptyList();
    }
    
}
