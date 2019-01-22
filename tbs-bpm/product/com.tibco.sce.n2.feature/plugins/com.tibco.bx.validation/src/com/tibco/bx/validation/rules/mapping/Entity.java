package com.tibco.bx.validation.rules.mapping;

import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;

public class Entity {

    protected final boolean isProperty;

    protected final ConceptPath cp;

    protected WebServiceJavaScriptMappingTypeCompatibility type = null;

    public Entity(ConceptPath cp) {
        this.cp = cp;
        isProperty = cp.getItem() instanceof Property;
    }

    public WebServiceJavaScriptMappingTypeCompatibility getType() {
        if (null == type) {
            Classifier wt = cp.getType();
            type = null == wt ? ProcessType.getType(cp) : BOMType.getType(cp);
        }
        return type;
    }

    public ConceptPath getConceptPath() {
        return cp;
    }

    public WebServiceJavaScriptMappingIssue[] check(Entity target) {
        return getType().check(this, target);
    }

    public int getLength() {
        return type.getLength(this);
    }

    public boolean isArrayItem() {
        return cp.isArrayItem();
    }

    public boolean isArray() {
        return cp.isArray();
    }

    public boolean isArrayHeader() {
        return cp.isArrayHeader();
    }

    public WebServiceJavaScriptMappingIssue[] validate(Entity target) {
        if (isArrayHeader() && !target.isArrayHeader()
                || (!(isArrayHeader()) && target.isArrayHeader())) {
            return arr(WebServiceJavaScriptMappingIssue.ARRAY_ARRAY_CHILDREN_MAPPING);
        }
        return getType().validate(this, target);
    }

    protected WebServiceJavaScriptMappingIssue[] arr(WebServiceJavaScriptMappingIssue... warnings) {
        return warnings;
    }

}
