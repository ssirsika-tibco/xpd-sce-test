/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.analyst.resources.xpdl2.utils.BasicTypeConverterFactory;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.processwidget.ProcessWidget.ProcessWidgetType;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.complexdatatype.ComplexDataTypeExtPointHelper;
import com.tibco.xpd.ui.complexdatatype.ComplexDataTypeReference;
import com.tibco.xpd.ui.complexdatatype.ComplexDataTypesMergedInfo;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.Condition;
import com.tibco.xpd.xpdl2.ConditionType;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Member;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.RecordType;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskScript;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.commands.AbstractLateExecuteCommand;
import com.tibco.xpd.xpdl2.edit.ui.ComplexDataUIUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Abstract class that provides common functionality that might be required for
 * all case process creators
 * 
 * @author bharge
 * @since 23 Jan 2014
 */
public abstract class AbstractCaseProcessCreator extends AbstractProcessCreator {

    private Class caseClass;

    /**
     * @param rootCategoryId
     * @param defaultFragmentId
     * @param isBusinessService
     */
    public AbstractCaseProcessCreator(String rootCategoryId,
            String defaultFragmentId, ProcessWidgetType processType) {

        super(rootCategoryId, defaultFragmentId, processType);
    }

    /**
     * @param caseClass
     */
    protected void setCaseClass(Class caseClass) {

        this.caseClass = caseClass;
    }

    /**
     * @return the caseClass
     */
    public Class getCaseClass() {
        return caseClass;
    }

    /**
     * Goes thru the data fields and formal parameters available from the
     * fragment process and sets the bom type or case ref type to the
     * appropriate case class type
     * 
     * @param process
     * @param xpdlPackage
     */
    protected void setBomOrCaseRefType(Process process, Package xpdlPackage) {

        ComplexDataTypeReference complexDataTypeReference =
                ComplexDataUIUtil.resolveSelectionToReference(getCaseClass(),
                        WorkingCopyUtil.getProjectFor(xpdlPackage),
                        getComplexTypesInfo());
        ExternalReference externalReference = null;

        List<ProcessRelevantData> allProcessRelevantData =
                ProcessInterfaceUtil.getAllProcessRelevantData(process);
        for (ProcessRelevantData processRelevantData : allProcessRelevantData) {

            if (processRelevantData.getDataType() instanceof ExternalReference) {

                processRelevantData.setName(caseClass.getName());
                Xpdl2ModelUtil.setOtherAttribute(processRelevantData,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_DisplayName(),
                        caseClass.getLabel());
                externalReference =
                        (ExternalReference) processRelevantData.getDataType();
            }
            if (processRelevantData.getDataType() instanceof RecordType) {

                processRelevantData.setName(caseClass.getName() + "Ref"); //$NON-NLS-1$
                Xpdl2ModelUtil.setOtherAttribute(processRelevantData,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_DisplayName(),
                        caseClass.getLabel() + "Ref"); //$NON-NLS-1$

                RecordType recordType =
                        (RecordType) processRelevantData.getDataType();
                Member member = recordType.getMember().get(0);
                externalReference = member.getExternalReference();

            }
            if (null != externalReference && complexDataTypeReference != null) {

                externalReference.setLocation(complexDataTypeReference
                        .getLocation());
                externalReference.setNamespace(complexDataTypeReference
                        .getNameSpace());
                externalReference.setXref(complexDataTypeReference.getXRef());
            }
        }
    }

    /**
     * 
     * @return ComplexDataTypesMergedInfo
     */
    protected ComplexDataTypesMergedInfo getComplexTypesInfo() {

        ComplexDataTypesMergedInfo _complexTypesInfo = null;
        if (_complexTypesInfo == null) {

            _complexTypesInfo =
                    ComplexDataTypeExtPointHelper
                            .getAllComplexDataTypesMergedInfo();
        }
        return _complexTypesInfo;
    }

    /**
     * return list of basic type data fields
     */
    protected List<DataField> createBasicTypes() {

        List<DataField> dataFieldsList = new ArrayList<DataField>();
        EList<Property> allAttributes = caseClass.getAllAttributes();
        List<Property> autoOrCustomCIdProperties = getCaseIds(allAttributes);

        for (Property property : autoOrCustomCIdProperties) {

            DataField df = Xpdl2Factory.eINSTANCE.createDataField();

            /* Set datafield name */
            String name = property.getName();
            df.setName(name);

            /* Set datafield display name */
            String displayName = property.getLabel();
            if (null != displayName) {
                if (displayName.isEmpty()) {
                    displayName = name;
                }
                Xpdl2ModelUtil.setOtherAttribute(df,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_DisplayName(),
                        displayName);
            }
            /* Set Data type */
            BasicType basicType =
                    BasicTypeConverterFactory.INSTANCE.getBasicType(property
                            .getType());
            df.setDataType(basicType);

            /*
             * we are adding extended attribute for each basic type that are of
             * interest so that later we can remove any other basic types that
             * we may not be interested to have in the interface tab of a page
             * activity
             */
            ExtendedAttribute extendedAttribute =
                    Xpdl2Factory.eINSTANCE.createExtendedAttribute();
            extendedAttribute.setName("PostProcess"); //$NON-NLS-1$
            extendedAttribute.setValue("CaseIdField"); //$NON-NLS-1$
            df.getExtendedAttributes().add(extendedAttribute);

            dataFieldsList.add(df);
        }
        return dataFieldsList;
    }

    /**
     * returns the case ids from the attribute list of a case class
     * 
     * @param allAttributes
     * @return List<Property>
     */
    private List<Property> getCaseIds(EList<Property> allAttributes) {

        List<Property> autoOrCustomCIdProperties = new ArrayList<Property>();
        /*
         * get the composite case ids and auto/custom case ids
         */
        for (Property property : allAttributes) {

            if (BOMGlobalDataUtils.isCompositeCID(property)
                    || BOMGlobalDataUtils.isAutoCID(property)
                    || BOMGlobalDataUtils.isCustomCID(property)) {

                autoOrCustomCIdProperties.add(property);
            }
        }
        return autoOrCustomCIdProperties;
    }

    /**
     * Some of the fragments for Business Service and Case Service have a data
     * field of type integer created to represent the case identifier of a case
     * class to make it handy for the user when a new process is created. But we
     * don't need that data field when business/case services are auto generated
     * from the bom class (because we create the required data fields looking at
     * the bom class selected). So remove that data field from the generated
     * process as it will not be required.
     * 
     * 
     * @author bharge
     * @since 14 Aug 2014
     */
    class DeleteCaseIdDataField extends AbstractLateExecuteCommand {

        Process process;

        /**
         * @param editingDomain
         * @param contextObject
         */
        public DeleteCaseIdDataField(EditingDomain editingDomain,
                Object contextObject) {

            super(editingDomain, contextObject);
            if (contextObject instanceof Process) {

                this.process = (Process) contextObject;
            }
        }

        /**
         * @see com.tibco.xpd.xpdl2.commands.AbstractLateExecuteCommand#createCommand(org.eclipse.emf.edit.domain.EditingDomain,
         *      java.lang.Object)
         * 
         * @param editingDomain
         * @param contextObject
         * @return
         */
        @Override
        protected Command createCommand(EditingDomain editingDomain,
                Object contextObject) {

            if (null != process) {

                CompoundCommand cmd = new CompoundCommand();

                EList<DataField> dataFields = process.getDataFields();
                for (DataField dataField : dataFields) {

                    EList<ExtendedAttribute> extendedAttributes =
                            dataField.getExtendedAttributes();
                    if (!extendedAttributes.isEmpty()) {

                        ExtendedAttribute extendedAttribute =
                                extendedAttributes.get(0);
                        if (null != extendedAttribute) {

                            if ("AutoCaseId".equals(extendedAttribute.getValue())) { //$NON-NLS-1$

                                cmd.append(RemoveCommand.create(editingDomain,
                                        dataField));
                            }
                        }
                    }
                }

                return cmd;
            }
            return null;
        }

    }

    /**
     * Late execute command class to generate script in the script task of the
     * generated business service
     * 
     * @author bharge
     * @since 9 Jan 2014
     */
    class ScriptTaskScriptCommand extends AbstractLateExecuteCommand {

        private Process process;

        /**
         * @param editingDomain
         * @param contextObject
         */
        public ScriptTaskScriptCommand(EditingDomain editingDomain,
                Object contextObject) {

            super(editingDomain, contextObject);
            if (contextObject instanceof Process) {

                this.process = (Process) contextObject;
            }
        }

        /**
         * @see com.tibco.xpd.xpdl2.commands.AbstractLateExecuteCommand#createCommand(org.eclipse.emf.edit.domain.EditingDomain,
         *      java.lang.Object)
         * 
         * @param editingDomain
         * @param contextObject
         * @return
         */
        @Override
        protected Command createCommand(EditingDomain editingDomain,
                Object contextObject) {

            if (null != process) {

                EList<Activity> activities = process.getActivities();
                CompoundCommand cmd = new CompoundCommand();

                for (Activity activity : activities) {

                    Implementation implementation =
                            activity.getImplementation();
                    if (implementation instanceof Task) {

                        Task task = (Task) implementation;
                        TaskScript taskScript = task.getTaskScript();
                        if (null != taskScript) {

                            String newScript = getScriptTaskScript();
                            String scriptGrammar =
                                    taskScript.getScript().getScriptGrammar();
                            Expression newExpr =
                                    Xpdl2Factory.eINSTANCE.createExpression();
                            newExpr.setScriptGrammar(scriptGrammar);
                            newExpr.getMixed()
                                    .add(XMLTypePackage.eINSTANCE.getXMLTypeDocumentRoot_Text(),
                                            newScript);
                            cmd.append(SetCommand.create(editingDomain,
                                    taskScript,
                                    Xpdl2Package.eINSTANCE
                                            .getTaskScript_Script(),
                                    newExpr));
                        }
                    }
                }
                return cmd;
            }
            return null;

        }

        /**
         * 
         * @return String - script for script task
         */
        private String getScriptTaskScript() {

            List<Property> autoOrCustomCaseIds =
                    getAutoOrCustomCaseId(caseClass.getAllAttributes());

            List<Property> compositeCaseIds =
                    getCompositeCaseIds(caseClass.getAllAttributes());

            StringBuilder scriptBuilder = new StringBuilder();

            if (!compositeCaseIds.isEmpty()) {
                /*
                 * custRef =
                 * cac_com_example_customerdata_Customer.findByCompositeIdentifier
                 * (compositeId1, compositeId2);
                 */
                getFirstLineForCompositeCaseIds(compositeCaseIds, scriptBuilder);
            } else if (!autoOrCustomCaseIds.isEmpty()) {
                /*
                 * custRef =
                 * cac_com_example_customerdata_Customer.findByCaseIdentifier1
                 * (caseid);
                 */
                getFirstLineForAutoOrCustomCaseIds(autoOrCustomCaseIds,
                        scriptBuilder);
            }
            /* new line - &#xD; */
            scriptBuilder.append("\n"); //$NON-NLS-1$
            /* if (caseRef != null) { */
            getSecondLine(scriptBuilder);

            scriptBuilder.append("\n"); //$NON-NLS-1$
            /* casebomtype = caseRef.readCase(); */
            getThirdLine(scriptBuilder);

            return scriptBuilder.toString();

        }

        /**
         * custRef =
         * cac_com_example_customerdata_Customer.findByCaseIdentifier1(caseid);
         * 
         * @param autoOrCustomCaseIds
         * @param sb
         * @return string - script string for the first line in the script
         */
        private void getFirstLineForAutoOrCustomCaseIds(
                List<Property> autoOrCustomCaseIds, StringBuilder sb) {

            /* caseClassName + Ref - caseRefType field name */
            String refName = caseClass.getName() + "Ref"; //$NON-NLS-1$
            sb.append(refName);
            sb.append("="); //$NON-NLS-1$
            /* cac factory name - cac_com_example_CustomerBOM */
            StringBuffer cacClassName =
                    new StringBuffer("cac_" //$NON-NLS-1$
                            + caseClass.getQualifiedName().replace('.', '_')
                                    .replace("::", "_")); //$NON-NLS-1$ //$NON-NLS-2$

            sb.append(cacClassName);
            sb.append("."); //$NON-NLS-1$

            Property property = autoOrCustomCaseIds.get(0);
            String methodName = changeCaseInitialChar(property.getName(), true);
            /* method name - findByCaseId1 */
            sb.append("findBy" + methodName); //$NON-NLS-1$
            /* arguments to the findBy method */
            sb.append("("); //$NON-NLS-1$
            sb.append(property.getName());
            sb.append(");"); //$NON-NLS-1$
        }

        /**
         * custRef =
         * cac_com_example_customerdata_Customer.findByCompositeIdentifier
         * (compostiecaseid1, compositecaseid2, ...);
         * 
         * @param compositeCaseIds
         * @param sb
         * @return string - script string for the first line in the script
         */
        private void getFirstLineForCompositeCaseIds(
                List<Property> compositeCaseIds, StringBuilder sb) {

            /* caseClassName + Ref - caseRefType field name */
            StringBuilder tempStr = new StringBuilder(sb.toString());
            String refName = caseClass.getName() + "Ref"; //$NON-NLS-1$
            tempStr.append(refName);
            tempStr.append("="); //$NON-NLS-1$
            /* cac factory name - cac_com_example_CustomerBOM */
            StringBuffer cacClassName =
                    new StringBuffer("cac_" //$NON-NLS-1$
                            + caseClass.getQualifiedName().replace('.', '_')
                                    .replace("::", "_")); //$NON-NLS-1$ //$NON-NLS-2$

            tempStr.append(cacClassName);
            tempStr.append("."); //$NON-NLS-1$

            /* method name - findByCompositeIdentifier */
            String methodName = "findByCompositeIdentifier"; //$NON-NLS-1$
            tempStr.append(methodName);
            tempStr.append("("); //$NON-NLS-1$
            /* arguments to the findBy method */
            for (Property property : compositeCaseIds) {

                tempStr.append(property.getName());
                tempStr.append(","); //$NON-NLS-1$
            }
            String str = tempStr.substring(0, tempStr.lastIndexOf(",")); //$NON-NLS-1$
            sb.append(str);
            sb.append(");"); //$NON-NLS-1$

        }

        /**
         * @param allAttributes
         * @return composite case ids
         */
        private List<Property> getCompositeCaseIds(EList<Property> allAttributes) {

            List<Property> compositeCIdProperties = new ArrayList<Property>();
            /*
             * get the composite case ids and auto/custom case ids
             */
            for (Property property : allAttributes) {

                if (BOMGlobalDataUtils.isCompositeCID(property)) {

                    compositeCIdProperties.add(property);
                }
            }
            return compositeCIdProperties;
        }

        /**
         * @param allAttributes
         * @return auto or custom case ids
         */
        private List<Property> getAutoOrCustomCaseId(
                EList<Property> allAttributes) {

            List<Property> autoOrCustomCIdProperties =
                    new ArrayList<Property>();
            /*
             * get the composite case ids and auto/custom case ids
             */
            for (Property property : allAttributes) {

                if (BOMGlobalDataUtils.isAutoCID(property)
                        || BOMGlobalDataUtils.isCustomCID(property)) {

                    autoOrCustomCIdProperties.add(property);
                }
            }
            return autoOrCustomCIdProperties;

        }

        /**
         * custBomType = custRef.readCustomer();
         * 
         * @param sb
         *            - script string for third line in the script
         */
        private void getThirdLine(StringBuilder sb) {

            String refName = caseClass.getName() + "Ref"; //$NON-NLS-1$
            sb.append(caseClass.getName() + " = "); //$NON-NLS-1$
            sb.append(refName);
            sb.append(".read" + caseClass.getName() + "()"); //$NON-NLS-1$ //$NON-NLS-2$
            sb.append(";"); //$NON-NLS-1$
            sb.append("\n"); //$NON-NLS-1$
            sb.append("}"); //$NON-NLS-1$
        }

        /**
         * if (custRef != null) {
         * 
         * @param sb
         *            - script string for second line in the script
         */
        private void getSecondLine(StringBuilder sb) {

            /* caseClassName + Ref - caseRefType field name */
            String refName = caseClass.getName() + "Ref"; //$NON-NLS-1$
            sb.append("if ("); //$NON-NLS-1$
            sb.append(refName);
            sb.append(" != null) {"); //$NON-NLS-1$
        }

        /**
         * @param propertyName
         * @param uppercase
         *            if set case is upper otherwise lower
         * @return String
         */
        private String changeCaseInitialChar(String propertyName,
                boolean uppercase) {

            if (uppercase) {

                return Character.toString(propertyName.charAt(0)).toUpperCase()
                        + propertyName.substring(1);
            }
            return Character.toString(propertyName.charAt(0)).toLowerCase()
                    + propertyName.substring(1);
        }

    }

    /**
     * Late execute command class to generate script in the conditional
     * transition of the gateway in the generated business service
     * 
     * @author bharge
     * @since 9 Jan 2014
     */
    class TransitionScriptCommand extends AbstractLateExecuteCommand {

        Process process;

        /**
         * @param editingDomain
         * @param contextObject
         */
        public TransitionScriptCommand(EditingDomain editingDomain,
                Object contextObject) {

            super(editingDomain, contextObject);
            if (contextObject instanceof Process) {

                this.process = (Process) contextObject;
            }
        }

        /**
         * @see com.tibco.xpd.xpdl2.commands.AbstractLateExecuteCommand#createCommand(org.eclipse.emf.edit.domain.EditingDomain,
         *      java.lang.Object)
         * 
         * @param editingDomain
         * @param contextObject
         * @return
         */
        @Override
        protected Command createCommand(EditingDomain editingDomain,
                Object contextObject) {

            if (null != process) {
                EList<Transition> transitions = process.getTransitions();
                CompoundCommand cmd = new CompoundCommand();

                Condition oldCondition = null;
                Transition transition = null;
                for (Transition trans : transitions) {

                    EList<ExtendedAttribute> extendedAttributes =
                            trans.getExtendedAttributes();
                    if (!extendedAttributes.isEmpty()) {

                        ExtendedAttribute extendedAttribute =
                                extendedAttributes.get(0);
                        if ("LookupFailConditionalTransition".equals(extendedAttribute.getValue())) { //$NON-NLS-1$

                            if (null != trans.getCondition()
                                    && ConditionType.CONDITION_LITERAL
                                            .equals(trans.getCondition()
                                                    .getType())) {

                                transition = trans;
                                oldCondition = trans.getCondition();
                                break;
                            }
                        }
                    }
                }
                String newScript = getTransitionScript();

                if (null != oldCondition) {

                    String scriptGrammar =
                            oldCondition.getExpression().getScriptGrammar();
                    Condition newCondition =
                            Xpdl2Factory.eINSTANCE.createCondition();
                    newCondition.setType(ConditionType.CONDITION_LITERAL);
                    Expression expression =
                            Xpdl2Factory.eINSTANCE.createExpression();
                    expression.setScriptGrammar(scriptGrammar);
                    expression
                            .getMixed()
                            .add(XMLTypePackage.eINSTANCE.getXMLTypeDocumentRoot_Text(),
                                    newScript);
                    newCondition.setExpression(expression);

                    Command removeCommand =
                            SetCommand.create(editingDomain,
                                    transition,
                                    Xpdl2Package.eINSTANCE
                                            .getTransition_Condition(),
                                    oldCondition);
                    Command setCommand =
                            SetCommand.create(editingDomain,
                                    transition,
                                    Xpdl2Package.eINSTANCE
                                            .getTransition_Condition(),
                                    newCondition);
                    cmd.append(removeCommand);
                    cmd.append(setCommand);
                }

                return cmd;
            }
            return null;

        }

        /**
         * 
         * @return String - script for conditional transition
         */
        private String getTransitionScript() {

            /* custRef == null; */
            StringBuilder sb = new StringBuilder();
            String refName = caseClass.getName() + "Ref"; //$NON-NLS-1$
            sb.append(refName);
            sb.append(" == null;"); //$NON-NLS-1$
            return sb.toString();
        }
    }

}
