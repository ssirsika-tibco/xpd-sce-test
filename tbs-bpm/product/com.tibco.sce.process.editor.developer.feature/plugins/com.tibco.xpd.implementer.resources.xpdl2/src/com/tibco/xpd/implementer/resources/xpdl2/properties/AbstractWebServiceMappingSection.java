/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.resources.xpdl2.properties;

import com.tibco.xpd.implementer.script.ActivityMessageUtil;
import com.tibco.xpd.implementer.script.IWsdlPath;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.script.ui.internal.BaseScriptSection;

/**
 * @author nwilson
 */
public abstract class AbstractWebServiceMappingSection extends
        AbstractEditableMappingSection {

    /**
     * @param direction
     */
    public AbstractWebServiceMappingSection(MappingDirection direction) {
        super(direction);
    }

    /**
     * @return
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.AbstractEditableMappingSection#getScriptSection()
     */
    @Override
    protected BaseScriptSection getScriptSection() {
        return new WebServiceScriptProperties(getDirection());
    }

    /**
     * Helper method for sub-classes to get the path used for comparison with
     * problem markers whose param is a service parameter data item.
     * 
     * @param target
     * @return path
     */
    protected String getServiceDataPath(Object sourceOrTarget) {
        String path = null;
        if (sourceOrTarget instanceof ConceptPath) {
            path = ((ConceptPath) sourceOrTarget).getPath();
        } else if (sourceOrTarget instanceof IWsdlPath) {
            path = ((IWsdlPath) sourceOrTarget).getPath();
        }
        return path;

    }

    /**
     * Helper method for sub-classes to get the path used for comparison with
     * problem markers whose param is a service parameter data item.
     * 
     * @param target
     * @return path
     */
    protected String getProcessDataPath(Object sourceOrTarget) {
        if (sourceOrTarget != null) {
            if (sourceOrTarget instanceof ConceptPath) {
                return ((ConceptPath) sourceOrTarget).getPath();
            } else {
                return ActivityMessageUtil.getParameterName(sourceOrTarget);
            }
        }
        return null;
    }

}
