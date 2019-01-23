/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.n2.resources.properties;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import com.tibco.xpd.n2.resources.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.extensions.AbstractAdvancedModelFeatureProperty;
import com.tibco.xpd.processeditor.xpdl2.properties.advanced.DropDownPropertyDescriptor;
import com.tibco.xpd.processeditor.xpdl2.properties.advanced.IntegerPropertyDescriptor;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.xpdExtension.MaxRetryActionType;
import com.tibco.xpd.xpdExtension.Retry;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Container class for properties that apply to certain Task types with a parent
 * process having a BPM destination. These advanced properties describe activity
 * retry behaviour for the task's failed activities.
 * 
 * @author patkinso
 * @since 4 May 2012
 */
public class AdvancedTaskRetryProperties {

    /**
     * All Task Retry advanced properties descend from this base class
     */
    private abstract static class BaseAdvancedTaskRetryProperties
            extends AbstractAdvancedModelFeatureProperty {

        private String commandLabel;

        private EStructuralFeature feature;

        public BaseAdvancedTaskRetryProperties(String commandLabel,
                EStructuralFeature feature) {
            super();
            this.commandLabel = commandLabel;
            this.feature = feature;
        }

        @Override
        protected String getSetPropertyCommandLabel() {
            return commandLabel;
        }

        @Override
        protected EStructuralFeature getPropertyModelFeature() {
            return feature;
        }

        @Override
        protected EObject getPropertyModelContainer(EObject input) {

            if (input instanceof Activity) {
                Activity owner = (Activity) input;
                EStructuralFeature feature =
                        XpdExtensionPackage.eINSTANCE.getDocumentRoot_Retry();
                return (Retry) Xpdl2ModelUtil.getOtherElement(owner, feature);
            }

            return null;
        }

        @Override
        protected EObject getOrCreatePropertyContainer(EObject input,
                EditingDomain editingDomain, CompoundCommand cmd) {

            EObject container = null;

            if (input instanceof Activity) {
                Activity activity = (Activity) input;
                EStructuralFeature feature =
                        XpdExtensionPackage.eINSTANCE.getDocumentRoot_Retry();
                container = getPropertyModelContainer(activity);
                if (container == null) {
                    container = XpdExtensionFactory.eINSTANCE.createRetry();
                    Command command = Xpdl2ModelUtil.getSetOtherElementCommand(
                            editingDomain,
                            activity,
                            feature,
                            container);
                    cmd.append(command);
                }
            }

            return container;
        }

        @Override
        protected Command getPropertyContainerCleanupCommand(
                EditingDomain editingDomain, EObject input) {

            if (input instanceof Activity) {
                Activity activity = (Activity) input;
                EStructuralFeature feature =
                        XpdExtensionPackage.eINSTANCE.getDocumentRoot_Retry();
                EObject container = getPropertyModelContainer(activity);

                if (container != null) {
                    return Xpdl2ModelUtil.getRemoveOtherElementCommand(
                            editingDomain,
                            activity,
                            feature,
                            container);
                }
            }

            return null;
        }

        /**
         * @param retry
         * @return true if all passed in Retry attributes are considered
         *         empty/nulled
         */
        protected boolean isRetryElementEmpty(Retry retry) {

            boolean otherFeaturesSet = false;

            if (retry != null) {
                if (this.getPropertyModelFeature() != XpdExtensionPackage.eINSTANCE
                        .getRetry_Max()) {
                    if (retry.eIsSet(
                            XpdExtensionPackage.eINSTANCE.getRetry_Max())) {
                        otherFeaturesSet = true;
                    }
                }

                if (this.getPropertyModelFeature() != XpdExtensionPackage.eINSTANCE
                        .getRetry_InitialPeriod()) {
                    if (retry.eIsSet(XpdExtensionPackage.eINSTANCE
                            .getRetry_InitialPeriod())) {
                        otherFeaturesSet = true;
                    }
                }

                if (this.getPropertyModelFeature() != XpdExtensionPackage.eINSTANCE
                        .getRetry_PeriodIncrement()) {
                    if (retry.eIsSet(XpdExtensionPackage.eINSTANCE
                            .getRetry_PeriodIncrement())) {
                        otherFeaturesSet = true;
                    }
                }

                if (this.getPropertyModelFeature() != XpdExtensionPackage.eINSTANCE
                        .getRetry_MaxRetryAction()) {
                    if (retry.eIsSet(XpdExtensionPackage.eINSTANCE
                            .getRetry_MaxRetryAction())) {
                        otherFeaturesSet = true;
                    }
                }

            }

            return otherFeaturesSet;
        }

        @Override
        public Object getDefaultValue(EObject input) {
            return null;
        }

        @Override
        public boolean isApplicable(EObject input) {

            if ((input instanceof Activity)) {
                Activity activity = (Activity) input;

                /*
                 * Be selective about the Activities that the advanced retry
                 * properties are applicable to.
                 */

                TaskType taskType = TaskObjectUtil.getTaskTypeStrict(activity);
                /*
                 * XPD-3857 not applicable to pageflows and service process
                 * (XPD-8276).
                 */
                if (Xpdl2ModelUtil.isPageflowProcess((activity.getProcess()))
                        || Xpdl2ModelUtil
                                .isServiceProcess(activity.getProcess())) {
                    return false;
                }
                // Allow 'Service Task'
                if (TaskType.SERVICE_LITERAL.equals(taskType)) {
                    return true;
                    // Allow 'Send Task' that aren't replies
                } else if (TaskType.SEND_LITERAL.equals(taskType)) {
                    if (!ReplyActivityUtil.isReplyActivity(activity)) {
                        return true;
                    }
                    /*
                     * Allow throw message events that aren't replies to
                     * incoming messages.
                     */
                } else if (EventTriggerType.EVENT_MESSAGE_THROW_LITERAL.equals(
                        EventObjectUtil.getEventTriggerType(activity))) {
                    if (!ReplyActivityUtil.isReplyActivity(activity)) {
                        return true;
                    }
                }

            }

            return false;
        }

        @Override
        public Command getSetValueCommand(EditingDomain editingDomain,
                EObject input, Object value, int sequenceIndex) {

            Command cmd = null;

            if (isValueEligibleForUnsetting(value)) {
                /*
                 * If this is last attribute that was set, then remove container
                 * instead.
                 */
                Retry retry = (Retry) getPropertyModelContainer(input);
                if (!isRetryElementEmpty(retry)) {
                    cmd = getPropertyContainerCleanupCommand(editingDomain,
                            input);
                } else {
                    cmd = super.getSetValueCommand(editingDomain,
                            input,
                            SetCommand.UNSET_VALUE,
                            sequenceIndex);
                }
            } else {
                cmd = super.getSetValueCommand(editingDomain,
                        input,
                        value,
                        sequenceIndex);
            }

            return cmd;
        }

        /**
         * @param value
         * @return true if passed in value is considered eligible for resetting
         *         (through the use of SetCommand.UNSET_VALUE)
         */
        protected abstract boolean isValueEligibleForUnsetting(Object value);

    }

    /**
     * All Task Retry advanced properties descend from this base class
     */
    public abstract static class BaseAdvancedTaskRetryIntegerProperties
            extends BaseAdvancedTaskRetryProperties {

        /**
         * @param commandLabel
         * @param feature
         */
        public BaseAdvancedTaskRetryIntegerProperties(String commandLabel,
                EStructuralFeature feature) {
            super(commandLabel, feature);
        }

        /**
         * @see com.tibco.xpd.n2.resources.properties.AdvancedTaskRetryProperties.BaseAdvancedTaskRetryProperties#isValueEligibleForUnsetting(java.lang.Object)
         * 
         * @param value
         * @return
         */
        @Override
        protected boolean isValueEligibleForUnsetting(Object value) {
            return value == null;
        }

        @Override
        public PropertyDescriptor createPropertyDescriptor(String descriptorId,
                String displayName) {

            IntegerPropertyDescriptor descriptor =
                    new IntegerPropertyDescriptor(descriptorId, displayName);
            descriptor.setLabelProvider(new LabelProvider() {

                @Override
                public String getText(Object element) {
                    /*
                     * For properties with non-numeric values assume the
                     * property will be assigned a default value at runtime. In
                     * these cases hide the value and display some standard text
                     * indicating a default value
                     */
                    String strValue =
                            Messages.AdvancedTaskRetryProperties_RetryDefaultPropertyValue;
                    if ((element != null) && (element instanceof Integer)) {
                        strValue = Integer.toString((Integer) element);
                    }

                    return strValue;
                }
            });

            return descriptor;
        }

    }

    public static class RetryMaxProperty
            extends BaseAdvancedTaskRetryIntegerProperties {

        public RetryMaxProperty() {
            super(Messages.AdvancedTaskRetryProperties_RetryMax,
                    XpdExtensionPackage.eINSTANCE.getRetry_Max());
        }
    }

    public static class RetryInitialPeriodProperty
            extends BaseAdvancedTaskRetryIntegerProperties {

        public RetryInitialPeriodProperty() {
            super(Messages.AdvancedTaskRetryProperties_RetryInitialPeriod,
                    XpdExtensionPackage.eINSTANCE.getRetry_InitialPeriod());
        }
    }

    public static class RetryPeriodIncrementProperty
            extends BaseAdvancedTaskRetryIntegerProperties {

        public RetryPeriodIncrementProperty() {
            super(Messages.AdvancedTaskRetryProperties_RetryPeriodIncrement,
                    XpdExtensionPackage.eINSTANCE.getRetry_PeriodIncrement());
        }
    }

    public abstract static class BaseAdvancedTaskRetryDropDownProperties
            extends BaseAdvancedTaskRetryProperties {

        protected static final String DEFAULT_DROPDOWN_VALUE =
                Messages.AdvancedTaskRetryProperties_RetryDefaultPropertyValue;

        protected static String[] labels;

        protected static Object[] values;

        /**
         * @param commandLabel
         * @param feature
         */
        public BaseAdvancedTaskRetryDropDownProperties(String commandLabel,
                EStructuralFeature feature) {
            super(commandLabel, feature);
        }

        /**
         * @see com.tibco.xpd.n2.resources.properties.AdvancedTaskRetryProperties.BaseAdvancedTaskRetryProperties#createPropertyDescriptor(java.lang.String,
         *      java.lang.String)
         * 
         * @param descriptorId
         * @param displayName
         * @return
         */
        @Override
        public PropertyDescriptor createPropertyDescriptor(String descriptorId,
                String displayName) {

            return new DropDownPropertyDescriptor(descriptorId, displayName,
                    labels, values);
        }

        /**
         * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractAdvancedModelFeatureProperty#getValue(org.eclipse.emf.ecore.EObject,
         *      int)
         * 
         * @param input
         * @param sequenceIndex
         * @return
         */
        @Override
        public Object getValue(EObject input, int sequenceIndex) {

            Object value = super.getValue(input, sequenceIndex);
            if (value == null) {
                return DEFAULT_DROPDOWN_VALUE;
            }

            return value;
        }

        /**
         * @see com.tibco.xpd.n2.resources.properties.AdvancedTaskRetryProperties.BaseAdvancedTaskRetryProperties#isValueEligibleForUnsetting(java.lang.Object)
         * 
         * @param value
         * @return
         */
        @Override
        protected boolean isValueEligibleForUnsetting(Object value) {
            return (value == null) || (value.equals(DEFAULT_DROPDOWN_VALUE));
        }

    }

    public static class RetryMaxActionProperty
            extends BaseAdvancedTaskRetryDropDownProperties {

        static {
            Map<String, Object> labelValueMap =
                    new LinkedHashMap<String, Object>();

            labelValueMap.put(DEFAULT_DROPDOWN_VALUE, DEFAULT_DROPDOWN_VALUE);
            labelValueMap.put(
                    Messages.AdvancedTaskRetryProperties_HaltDropDownValue,
                    MaxRetryActionType.HALT);
            labelValueMap.put(
                    Messages.AdvancedTaskRetryProperties_ErrorDropDownValue,
                    MaxRetryActionType.ERROR);

            labels = labelValueMap.keySet()
                    .toArray(new String[labelValueMap.size()]);
            values = labelValueMap.values()
                    .toArray(new Object[labelValueMap.size()]);
        }

        public RetryMaxActionProperty() {
            super(Messages.AdvancedTaskRetryProperties_MaximumRetryAction,
                    XpdExtensionPackage.eINSTANCE.getRetry_MaxRetryAction());
        }

    }

}
