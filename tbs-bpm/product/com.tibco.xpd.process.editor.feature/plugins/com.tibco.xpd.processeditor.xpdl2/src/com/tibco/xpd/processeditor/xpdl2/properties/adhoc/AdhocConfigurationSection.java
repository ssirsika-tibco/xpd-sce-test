/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.adhoc;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.forms.widgets.Section;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDestinationUtil;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.SashContributionSection;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.ui.util.CapabilityUtil;
import com.tibco.xpd.xpdExtension.AdHocExecutionTypeType;
import com.tibco.xpd.xpdExtension.AdHocTaskConfigurationType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Adhoc configuration Section for the Automatic and Manual Adhoc tasks.
 * [Currently we only support Automatic and Manual Adhoc configuration for User
 * tasks and Re-usable sub-process.]
 * 
 * @author kthombar
 * @since 04-Aug-2014
 */
public class AdhocConfigurationSection extends SashContributionSection {

    public static final String TAB_ID =
            "com.tibco.xpd.processeditor.propertyTabs.adhoc"; //$NON-NLS-1$

    private static final String ENABLEMENT_SASH_SECTION_ID =
            "adhocConfigurationEnablementSection"; //$NON-NLS-1$

    /**
     * @param selectionFilterClass
     * @param actionIdPrefix
     */
    public AdhocConfigurationSection() {
        super(Xpdl2Package.eINSTANCE.getActivity(), Xpdl2ProcessEditorPlugin.ID
                + ".AdhocSection"); //$NON-NLS-1$
        setShowInWizard(false);
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.SashContributionSection#doRefresh()
     * 
     */
    @Override
    protected void doRefresh() {

        super.doRefresh();

        Section section = getSection(ENABLEMENT_SASH_SECTION_ID);

        if (section != null) {

            EObject input = getInput();

            if (input instanceof Activity) {

                Object adHocConfig =
                        Xpdl2ModelUtil
                                .getOtherElement((Activity) input,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_AdHocTaskConfiguration());

                if (adHocConfig instanceof AdHocTaskConfigurationType) {

                    AdHocTaskConfigurationType adhocConfigurationType =
                            (AdHocTaskConfigurationType) adHocConfig;

                    /*
                     * Set the Enablement Section Header based on the Adhoc
                     * Activity Execution Type.
                     */
                    if (AdHocExecutionTypeType.AUTOMATIC
                            .equals(adhocConfigurationType
                                    .getAdHocExecutionType())) {

                        section.setText(Messages.AdhocConfigurationSection_AutomaticAdhocEnablementSectionHeader_title);
                    } else {

                        section.setText(Messages.AdhocConfigurationSection_ManualAdhocEnablementSectionHeader_title);
                    }
                }
            }
        }
    }

    /**
     * Filter out resource section for activities in Pageflow processes and
     * decision flow processes. (For filtering out Resource section for pageflow
     * or decision flow processes see ProcessTaskGroupsSection)
     * 
     * @see com.tibco.xpd.processeditor.xpdl2.properties.SashContributionSection#
     *      select(java.lang.Object)
     * 
     * @param toTest
     * @return
     */
    @SuppressWarnings("deprecation")
    @Override
    public boolean select(Object toTest) {

        boolean select = false;

        EObject eo = getBaseSelectObject(toTest);

        if (eo instanceof Activity) {
            /*
             * Section should only be enabled for User OR Sub-Proc tasks
             */
            TaskType taskTypeStrict =
                    TaskObjectUtil.getTaskTypeStrict((Activity) eo);

            select =
                    (TaskType.USER_LITERAL.equals(taskTypeStrict) || TaskType.SUBPROCESS_LITERAL
                            .equals(taskTypeStrict));

            if (select) {
                /*
                 * Section should only be enabled is Developer capability is
                 * enabled and if BPM destination is enabled.
                 */
                select =
                        CapabilityUtil.isDeveloperActivityEnabled()
                                && ProcessDestinationUtil
                                        .isBPMDestinationSelected(Xpdl2ModelUtil
                                                .getProcess(eo));
            }

            if (select) {
                /*
                 * Section should be enabled only if Ad-Hoc Task Configuration
                 * is set [i.e. Ad-Hoc button is checked.]
                 */
                Object adHocConfig =
                        Xpdl2ModelUtil
                                .getOtherElement((Activity) eo,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_AdHocTaskConfiguration());

                select = adHocConfig != null;
            }
        }

        if (select) {
            select = super.select(toTest);
        }
        return select;
    }
}
