/**
 * BpmnTaskAdvancedProperties.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2008
 */
package com.tibco.xpd.processeditor.xpdl2.properties.contributions;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import com.tibco.xpd.processeditor.xpdl2.extensions.AbstractAdvancedModelFeatureProperty;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.No;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * BpmnAdvancedProperties
 * 
 */
public class BpmnAdvancedProperties {

    private abstract static class BaseExpressionAdvancedProperty extends
            AbstractAdvancedModelFeatureProperty {

        @Override
        public PropertyDescriptor createPropertyDescriptor(String descriptorId,
                String displayName) {
            return new TextPropertyDescriptor(descriptorId, displayName);
        }

        @Override
        public Object getValue(EObject input, int sequenceIndex) {
            //
            // Convert the xpdl2 Expression into a String as expected by the
            // TextProeprtyDescriptor.
            Object value = null;

            EObject propertyContainer = getPropertyModelContainer(input);

            if (propertyContainer != null) {
                if (propertyContainer.eIsSet(getPropertyModelFeature())) {
                    value = propertyContainer.eGet(getPropertyModelFeature());

                    if (value instanceof Expression) {
                        value = ((Expression) value).getText();
                    }
                }
            }

            return value == null ? "" : value; //$NON-NLS-1$
        }

        @Override
        public Command getSetValueCommand(EditingDomain editingDomain,
                EObject input, Object value, int sequenceIndex) {
            //
            // Wrap up the nominal TextPropertyDescriptor value (String) in an
            // xpdl2 Expression object).
            if (value instanceof String) {
                Expression expr = Xpdl2Factory.eINSTANCE.createExpression();

                expr.getMixed()
                        .add(XMLTypePackage.eINSTANCE.getXMLTypeDocumentRoot_Text(),
                                value);

                CompoundCommand cmd = new CompoundCommand();
                cmd.setLabel(getSetPropertyCommandLabel());

                EObject container =
                        getOrCreatePropertyContainer(input, editingDomain, cmd);
                if (container != null) {
                    cmd.append(SetCommand.create(editingDomain,
                            container,
                            getPropertyModelFeature(),
                            expr));

                    return cmd;
                }
            }

            return null;
        }

        @Override
        public Object getDefaultValue(EObject input) {
            return null;
        }
    }

    private static class BaseDurationProperty extends
            BaseExpressionAdvancedProperty {
        private String commandLabel;

        private EStructuralFeature feature;

        public BaseDurationProperty(String commandLabel,
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
        protected EObject getOrCreatePropertyContainer(EObject input,
                EditingDomain editingDomain, CompoundCommand cmd) {
            if (input instanceof OtherElementsContainer) {
                return BpmnTaskPropertiesUtils
                        .getOrAddDurationCalculation((OtherElementsContainer) input,
                                editingDomain,
                                cmd);
            }
            return null;
        }

        @Override
        protected EObject getPropertyModelContainer(EObject input) {
            if (input instanceof OtherElementsContainer) {
                return BpmnTaskPropertiesUtils
                        .getDurationCalculation((OtherElementsContainer) input);
            }
            return null;
        }

        @Override
        protected Command getPropertyContainerCleanupCommand(
                EditingDomain editingDomain, EObject input) {
            if (input instanceof OtherElementsContainer) {
                return BpmnTaskPropertiesUtils
                        .getRemoveDurationCalculationCommand(editingDomain,
                                (OtherElementsContainer) input);
            }
            return null;
        }

        @Override
        protected EStructuralFeature getPropertyModelFeature() {
            return feature;
        }

        @Override
        public boolean isApplicable(EObject input) {
            return isApplicableActivityOrProcess(input);
        }

        protected boolean isApplicableActivityOrProcess(EObject input) {
            if (input instanceof Activity) {
                Activity activity = (Activity) input;
                EObject impl = activity.getImplementation();

                if (Xpdl2ModelUtil.isPageflow(Xpdl2ModelUtil
                        .getProcess(activity)) && impl instanceof Task) {
                    Task task = (Task) impl;
                    if (task.getTaskUser() != null) {
                        return false;
                    }
                }
                if (impl instanceof Task || impl instanceof SubFlow
                        || impl instanceof No) {
                    return true;
                }

                if (((Activity) input).getEvent() instanceof IntermediateEvent) {
                    IntermediateEvent event =
                            (IntermediateEvent) ((Activity) input).getEvent();
                    if (!TriggerType.LINK_LITERAL.equals(event.getTrigger())) {
                        if (event.getTarget() == null
                                || event.getTarget().length() == 0) {
                            return true;
                        }
                    }
                }
            } else if (input instanceof Process) {
                Process process = (Process) input;
                if (Xpdl2ModelUtil.isBusinessProcess(process)) {
                    return true;
                }
            }
            return false;
        }
    }

    public static class TaskYearsDurationProperty extends BaseDurationProperty {
        public TaskYearsDurationProperty() {
            super(Messages.BpmnTaskAdvancedProperties_SetYearsDuration_menu,
                    XpdExtensionPackage.eINSTANCE
                            .getDurationCalculation_Years());
        }
    }

    public static class TaskMonthsDurationProperty extends BaseDurationProperty {
        public TaskMonthsDurationProperty() {
            super(Messages.BpmnTaskAdvancedProperties_SetMonthsDuration_menu,
                    XpdExtensionPackage.eINSTANCE
                            .getDurationCalculation_Months());
        }
    }

    public static class TaskWeeksDurationProperty extends BaseDurationProperty {
        public TaskWeeksDurationProperty() {
            super(Messages.BpmnTaskAdvancedProperties_SetWeeksDuration_menu,
                    XpdExtensionPackage.eINSTANCE
                            .getDurationCalculation_Weeks());
        }
    }

    public static class TaskDaysDurationProperty extends BaseDurationProperty {
        public TaskDaysDurationProperty() {
            super(Messages.BpmnTaskAdvancedProperties_SetDaysDuration_menu,
                    XpdExtensionPackage.eINSTANCE.getDurationCalculation_Days());
        }
    }

    public static class TaskHoursDurationProperty extends BaseDurationProperty {
        public TaskHoursDurationProperty() {
            super(Messages.BpmnTaskAdvancedProperties_SetHoursDuration_menu,
                    XpdExtensionPackage.eINSTANCE
                            .getDurationCalculation_Hours());
        }
    }

    public static class TaskMinutesDurationProperty extends
            BaseDurationProperty {
        public TaskMinutesDurationProperty() {
            super(Messages.BpmnTaskAdvancedProperties_SetMinutesDuration_menu,
                    XpdExtensionPackage.eINSTANCE
                            .getDurationCalculation_Minutes());
        }
    }

    public static class TaskSecondsDurationProperty extends
            BaseDurationProperty {
        public TaskSecondsDurationProperty() {
            super(Messages.BpmnTaskAdvancedProperties_SetSecondsDuration_menu,
                    XpdExtensionPackage.eINSTANCE
                            .getDurationCalculation_Seconds());
        }
    }

    public static class TaskMicrosecondsDurationProperty extends
            BaseDurationProperty {
        public TaskMicrosecondsDurationProperty() {
            super(Messages.BpmnTaskAdvancedProperties_SetMicrosDuration_menu,
                    XpdExtensionPackage.eINSTANCE
                            .getDurationCalculation_Microseconds());
        }
    }

}
