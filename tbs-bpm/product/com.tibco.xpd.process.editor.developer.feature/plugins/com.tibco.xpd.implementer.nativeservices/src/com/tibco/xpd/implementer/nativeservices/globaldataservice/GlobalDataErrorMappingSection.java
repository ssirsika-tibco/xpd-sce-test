/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */
package com.tibco.xpd.implementer.nativeservices.globaldataservice;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.ui.IPluginContribution;

import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.BpmnCatchableErrorUtil;
import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.BpmnCatchableErrorUtil.ErrorCatchType;
import com.tibco.xpd.implementer.nativeservices.NativeServicesActivator;
import com.tibco.xpd.implementer.resources.xpdl2.errorEvents.CatchBpmnErrorMapperSection;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.xpdExtension.GlobalDataOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Mapping section for the Global Data specific errors.
 * 
 * @author njpatel
 */
public class GlobalDataErrorMappingSection extends CatchBpmnErrorMapperSection
        implements IPluginContribution {

    public GlobalDataErrorMappingSection() {
        super();
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.errorEvents.CatchBpmnErrorMapperSection#createSourceContentProvider()
     * 
     * @return
     */
    @Override
    protected ITreeContentProvider createSourceContentProvider() {
        return new GlobalDataErrorSourceContentProvider();
    }

    // XPD-5871: Moved GlobalDataErrorSourceContentProvider to its own file to
    // be accessed from Validation rules.
    /**
     * @see org.eclipse.ui.IPluginContribution#getLocalId()
     * 
     * @return
     */
    @Override
    public String getLocalId() {
        return "GlobalDataTaskCatchErrorMapperOut"; //$NON-NLS-1$
    }

    /**
     * @see org.eclipse.ui.IPluginContribution#getPluginId()
     * 
     * @return
     */
    @Override
    public String getPluginId() {
        return NativeServicesActivator.PLUGIN_ID;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection#getPluginContributon()
     * 
     * @return
     */
    @Override
    public IPluginContribution getPluginContributon() {
        return this;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection#select(java.lang.Object)
     * 
     * @param toTest
     * @return
     */
    @Override
    public boolean select(Object toTest) {
        EObject eo = getBaseSelectObject(toTest);

        // Check it's a catch Error intermediate event.
        if (eo instanceof Activity) {
            Activity activity = (Activity) eo;

            if (activity.getEvent() instanceof IntermediateEvent) {
                IntermediateEvent event =
                        (IntermediateEvent) activity.getEvent();

                if (TriggerType.ERROR_LITERAL.equals(event.getTrigger())) {

                    ErrorCatchType catchType =
                            BpmnCatchableErrorUtil.getCatchTypeStrict(activity);

                    /*
                     * Check that this is a specific error for a Global Data
                     * task.
                     */
                    if (catchType == ErrorCatchType.CATCH_SPECIFIC) {
                        if (ScriptGrammarFactory.JAVASCRIPT
                                .equals(ScriptGrammarFactory
                                        .getGrammarToUse(activity,
                                                DirectionType.OUT_LITERAL))
                        /*
                         * || ScriptGrammarFactory.XPATH
                         * .equals(ScriptGrammarFactory
                         * .getGrammarToUse(activity,
                         * DirectionType.OUT_LITERAL))
                         */) {
                            Object thrower =
                                    BpmnCatchableErrorUtil
                                            .getErrorThrower(activity);
                            if (thrower instanceof Activity) {
                                Implementation implementation =
                                        ((Activity) thrower)
                                                .getImplementation();

                                if (implementation instanceof Task) {
                                    TaskService taskService =
                                            ((Task) implementation)
                                                    .getTaskService();
                                    if (taskService != null) {
                                        GlobalDataOperation globalDataOp =
                                                (GlobalDataOperation) Xpdl2ModelUtil
                                                        .getOtherElement(taskService,
                                                                XpdExtensionPackage.eINSTANCE
                                                                        .getDocumentRoot_GlobalDataOperation());

                                        return globalDataOp != null;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

}
