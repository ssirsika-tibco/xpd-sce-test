/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processinterface.properties;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessFeaturesUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.xpdExtension.IntermediateMethod;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author rsomayaj
 * 
 * 
 */
public class IntermediateMethodPropertySection extends BaseMethodSection {

    /**
     * Process properties
     */
    public IntermediateMethodPropertySection() {
        super(XpdExtensionPackage.eINSTANCE.getIntermediateMethod(),
                Xpdl2ResourcesPlugin.PLUGIN_ID
                        + "IntermediateMethodPropertySection"); //$NON-NLS-1$
    }

    @Override
    protected NamedElement getDuplicateMethod(
            ProcessInterface processInterface, NamedElement element,
            String finalName) {
        return Xpdl2ModelUtil.getDuplicateIntermediateMethod(processInterface,
                element,
                finalName);
    }

    @Override
    protected NamedElement getDuplicateDisplayMethod(
            ProcessInterface processInterface, NamedElement element,
            String finalName) {
        return Xpdl2ModelUtil
                .getDuplicateDisplayIntermediateMethod(processInterface,
                        element,
                        finalName);
    }

    @Override
    protected TriggerType getTriggerType() {
        IntermediateMethod method = (IntermediateMethod) getInput();
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
        return "IntermediateMethod"; //$NON-NLS-1$
    }

}
