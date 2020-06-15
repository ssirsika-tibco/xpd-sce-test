/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.n2.resources.properties;

import java.math.BigInteger;

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
import com.tibco.xpd.processeditor.xpdl2.properties.advanced.BigIntegerPropertyDescriptor;
import com.tibco.xpd.xpdExtension.ConstantPeriod;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 
 * 
 * @author bharge
 * @since 8 Jun 2012
 */
public class AdvancedPropertiesCorrelationTimeout {

    /**
     * 
     * 
     * @author bharge
     * @since 8 Jun 2012
     */
    private static abstract class BaseAdvancedCorrelationTimeoutProperties
            extends AbstractAdvancedModelFeatureProperty {

        private String commandLabel;

        private EStructuralFeature feature;

        /**
         * @param commandLabel
         * @param feature
         */
        public BaseAdvancedCorrelationTimeoutProperties(String commandLabel,
                EStructuralFeature feature) {
            super();
            this.commandLabel = commandLabel;
            this.feature = feature;
        }

        /**
         * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractContributedAdvancedProperty#isApplicable(org.eclipse.emf.ecore.EObject)
         * 
         * @param input
         * @return
         */
        @Override
        public boolean isApplicable(EObject input) {
            /*
             * Sid ACE-3975 Correlation timeout is now a fixed 60 minutes (handled by xpdl2bpel) as correlation is only
             * via API using process instance id (therefore there cannot be any significant delay by a 3rd party app
             * getting a process-instance-id and it actually existing on database).
             * 
             * So until we support some form of business-data based correlation then we'll disable user setting of the
             * correlation timeout.
             */
            if (true) {
                return false;
            }

            /*
             * correlation time out is applicable only to receive task with incoming transition , intermediate catch
             * event and event sub process start message events.
             */
            if (input instanceof Activity) {
                /*
                 * XPD-7042: applicable if the activity is an correlating
                 * activity.
                 */
                return Xpdl2ModelUtil.isCorrelatingActivity((Activity) input);
            }
            return false;
        }

        /**
         * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractContributedAdvancedProperty#getDefaultValue(org.eclipse.emf.ecore.EObject)
         * 
         * @param input
         * @return
         */
        @Override
        public Object getDefaultValue(EObject input) {
            return null;
        }

        /**
         * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractAdvancedModelFeatureProperty#getPropertyModelFeature()
         * 
         * @return
         */
        @Override
        protected EStructuralFeature getPropertyModelFeature() {
            return feature;
        }

        /**
         * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractAdvancedModelFeatureProperty#getSetPropertyCommandLabel()
         * 
         * @return
         */
        @Override
        protected String getSetPropertyCommandLabel() {
            return commandLabel;
        }

        /**
         * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractAdvancedModelFeatureProperty#getPropertyModelContainer(org.eclipse.emf.ecore.EObject)
         * 
         * @param input
         * @return
         */
        @Override
        protected EObject getPropertyModelContainer(EObject input) {

            if (input instanceof Activity) {
                Activity owner = (Activity) input;
                EStructuralFeature feature =
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_CorrelationTimeout();
                Object otherElement =
                        Xpdl2ModelUtil.getOtherElement(owner, feature);

                if (otherElement instanceof ConstantPeriod) {
                    return (ConstantPeriod) otherElement;
                }
            }

            return null;
        }

        /**
         * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractAdvancedModelFeatureProperty#getOrCreatePropertyContainer(org.eclipse.emf.ecore.EObject,
         *      org.eclipse.emf.edit.domain.EditingDomain,
         *      org.eclipse.emf.common.command.CompoundCommand)
         * 
         * @param input
         * @param editingDomain
         * @param cmd
         * @return
         */
        @Override
        protected EObject getOrCreatePropertyContainer(EObject input,
                EditingDomain editingDomain, CompoundCommand cmd) {

            EObject container = null;
            if (input instanceof Activity) {

                Activity activity = (Activity) input;
                container = getPropertyModelContainer(activity);

                if (null == container) {
                    container =
                            XpdExtensionFactory.eINSTANCE
                                    .createConstantPeriod();
                    Command command =
                            Xpdl2ModelUtil
                                    .getSetOtherElementCommand(editingDomain,
                                            activity,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_CorrelationTimeout(),
                                            container);
                    cmd.append(command);
                }

            }
            return container;
        }

        /**
         * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractAdvancedModelFeatureProperty#getPropertyContainerCleanupCommand(org.eclipse.emf.edit.domain.EditingDomain,
         *      org.eclipse.emf.ecore.EObject)
         * 
         * @param editingDomain
         * @param input
         * @return
         */
        @Override
        protected Command getPropertyContainerCleanupCommand(
                EditingDomain editingDomain, EObject input) {

            if (input instanceof Activity) {

                Activity activity = (Activity) input;
                EStructuralFeature feature =
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_CorrelationTimeout();
                EObject container = getPropertyModelContainer(activity);

                if (null != container) {
                    return Xpdl2ModelUtil
                            .getRemoveOtherElementCommand(editingDomain,
                                    activity,
                                    feature,
                                    container);
                }
            }
            return null;
        }

    }

    /**
     * 
     * 
     * @author bharge
     * @since 11 Jun 2012
     */
    public static abstract class BaseAdvancedCorrelationTimeoutBigIntegerProperties
            extends BaseAdvancedCorrelationTimeoutProperties {

        /**
         * @param commandLabel
         * @param feature
         */
        public BaseAdvancedCorrelationTimeoutBigIntegerProperties(
                String commandLabel, EStructuralFeature feature) {
            super(commandLabel, feature);
        }

        /**
         * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractContributedAdvancedProperty#createPropertyDescriptor(java.lang.String,
         *      java.lang.String)
         * 
         * @param descriptorId
         * @param displayName
         * @return
         */
        @Override
        public PropertyDescriptor createPropertyDescriptor(String descriptorId,
                String displayName) {

            BigIntegerPropertyDescriptor descriptor =
                    new BigIntegerPropertyDescriptor(descriptorId, displayName);
            descriptor.setLabelProvider(new LabelProvider() {

                /**
                 * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
                 * 
                 * @param element
                 * @return
                 */
                @Override
                public String getText(Object element) {
                    if (element instanceof BigInteger) {
                        return String.valueOf(element);
                    }
                    return super.getText(element);
                }

            });

            return descriptor;
        }

        /**
         * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractAdvancedModelFeatureProperty#getSetValueCommand(org.eclipse.emf.edit.domain.EditingDomain,
         *      org.eclipse.emf.ecore.EObject, java.lang.Object, int)
         * 
         * @param editingDomain
         * @param input
         * @param value
         * @param sequenceIndex
         * @return
         */
        @Override
        public Command getSetValueCommand(EditingDomain editingDomain,
                EObject input, Object value, int sequenceIndex) {

            if (value instanceof BigInteger) {
                value = new BigInteger(value.toString());
                return super.getSetValueCommand(editingDomain,
                        input,
                        value,
                        sequenceIndex);
            } else if (null == value) {
                CompoundCommand cmd = new CompoundCommand();
                cmd.setLabel(getSetPropertyCommandLabel());

                final EObject container =
                        getOrCreatePropertyContainer(input, editingDomain, cmd);

                cmd.append(SetCommand.create(editingDomain,
                        container,
                        getPropertyModelFeature(),
                        null));
                return cmd;
            }
            return null;
        }
    }

    /**
     * 
     * 
     * @author bharge
     * @since 11 Jun 2012
     */
    public static class CorrelationTimeoutSecondsProperty extends
            BaseAdvancedCorrelationTimeoutBigIntegerProperties {

        public CorrelationTimeoutSecondsProperty() {
            super(Messages.AdvancedCorrelationTimeoutProperties_Seconds,
                    XpdExtensionPackage.eINSTANCE.getConstantPeriod_Seconds());
        }

    }

    /**
     * 
     * 
     * @author bharge
     * @since 11 Jun 2012
     */
    public static class CorrelationTimeoutMinutesProperty extends
            BaseAdvancedCorrelationTimeoutBigIntegerProperties {

        public CorrelationTimeoutMinutesProperty() {
            super(Messages.AdvancedCorrelationTimeoutProperties_Minutes,
                    XpdExtensionPackage.eINSTANCE.getConstantPeriod_Minutes());
        }

    }

    /**
     * 
     * 
     * @author bharge
     * @since 11 Jun 2012
     */
    public static class CorrelationTimeoutHoursProperty extends
            BaseAdvancedCorrelationTimeoutBigIntegerProperties {

        public CorrelationTimeoutHoursProperty() {
            super(Messages.AdvancedCorrelationTimeoutProperties_Hours,
                    XpdExtensionPackage.eINSTANCE.getConstantPeriod_Hours());
        }

    }

    /**
     * 
     * 
     * @author bharge
     * @since 11 Jun 2012
     */
    public static class CorrelationTimeoutDaysProperty extends
            BaseAdvancedCorrelationTimeoutBigIntegerProperties {

        public CorrelationTimeoutDaysProperty() {
            super(Messages.AdvancedCorrelationTimeoutProperties_Days,
                    XpdExtensionPackage.eINSTANCE.getConstantPeriod_Days());
        }

    }

}
