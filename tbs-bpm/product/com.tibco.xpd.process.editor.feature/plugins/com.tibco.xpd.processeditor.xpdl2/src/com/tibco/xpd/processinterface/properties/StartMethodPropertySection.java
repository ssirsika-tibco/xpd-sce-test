/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processinterface.properties;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessFeaturesUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.StartMethod;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author rsomayaj
 * 
 * 
 */
public class StartMethodPropertySection extends BaseMethodSection {

    /**
     * Process properties
     */
    public StartMethodPropertySection() {
        super(XpdExtensionPackage.eINSTANCE.getStartMethod(),
                Xpdl2ResourcesPlugin.PLUGIN_ID + "StartMethodPropertySection"); //$NON-NLS-1$
    }

    @Override
    protected NamedElement getDuplicateMethod(
            ProcessInterface processInterface, NamedElement element,
            String finalName) {
        return Xpdl2ModelUtil.getDuplicateStartMethod(processInterface,
                element,
                finalName);
    }

    @Override
    protected NamedElement getDuplicateDisplayMethod(
            ProcessInterface processInterface, NamedElement element,
            String finalName) {
        return Xpdl2ModelUtil.getDuplicateDisplayStartMethod(processInterface,
                element,
                finalName);
    }

    @Override
    protected TriggerType getTriggerType() {
        StartMethod method = (StartMethod) getInput();
        if (method != null) {
            return method.getTrigger();
        }
        return null;
    }

    @Override
    protected boolean shouldShowSolutionDesignForm() {
        if (!XpdResourcesPlugin.isRCP()
                && ProcessFeaturesUtil.isProcessDeveloperFeatureInstalled()) {
            TriggerType triggerType = getTriggerType();
            if (triggerType != null
                    && triggerType.equals(TriggerType.MESSAGE_LITERAL)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected String getMethodTypeName() {
        return "StartMethod"; //$NON-NLS-1$
    }

}