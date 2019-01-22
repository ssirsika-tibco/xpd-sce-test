/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.nativeservices.java.javaservice;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.ui.IPluginContribution;

import com.tibco.xpd.implementer.nativeservices.NativeServicesConsts;
import com.tibco.xpd.implementer.nativeservices.java.JavaActivator;
import com.tibco.xpd.implementer.nativeservices.java.JavaConstants;
import com.tibco.xpd.implementer.nativeservices.java.internal.Messages;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper.JavaMethodParameter;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper.JavaParameterLabelProvider;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper.JavaServiceMappingCommandFactory;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper.ParameterTypeEnum;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper.PojoItemProvider;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper.PojoMappingItemProvider;
import com.tibco.xpd.implementer.resources.xpdl2.properties.AbstractEditableMappingSection;
import com.tibco.xpd.implementer.script.ActivityMessageUtil;
import com.tibco.xpd.mapper.IMappingTransferValidator;
import com.tibco.xpd.mapper.MapperLabelProvider;
import com.tibco.xpd.mapper.MapperViewer;
import com.tibco.xpd.mapper.MapperViewerInput;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.ActivityDataFieldItemProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.AssociatedParameterFilter;
import com.tibco.xpd.processeditor.xpdl2.properties.ChoiceConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.IMappingCommandFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.IMappingCommandFactory2;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ActivityScriptContentProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.script.CompositeTreeContentProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptableLabelProvider;
import com.tibco.xpd.processeditor.xpdl2.util.DataMappingUtil;
import com.tibco.xpd.processwidget.adapters.TaskAdapter;
import com.tibco.xpd.processwidget.parts.TaskEditPart;
import com.tibco.xpd.script.ui.internal.BaseScriptSection;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.ImplementationType;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Java (pojo) OUT mapper section.
 * 
 * @author njpatel
 * 
 */
public class JavaServiceMapperOutSection extends AbstractEditableMappingSection
        implements IFilter, IMappingTransferValidator, IPluginContribution {

    private JavaServiceMappingCommandFactory commandFactory;

    /**
     * Constructor.
     */
    public JavaServiceMapperOutSection() {
        super(MappingDirection.OUT);
        commandFactory =
                new JavaServiceMappingCommandFactory(MappingDirection.OUT);
        setMapperLabelProvider(new MapperLabelProvider(
                new ScriptableLabelProvider(new JavaParameterLabelProvider()),
                new JavaParameterLabelProvider()));
    }

    @Override
    protected IMappingCommandFactory getMappingCommandFactory() {
        /*
         * This method is deprecated, the new factory via
         * getMappingCommandFactory2() will be used instead. This is done to use
         * a base remove mapping functionality using selected mapping and not
         * the source and target of the mapping, which was ineffective for
         * broken mappings.
         */
        return null;
    }

    @Override
    protected void addViewerFilters(MapperViewer mapperViewer) {
        ViewerFilter associatedFilter = new AssociatedParameterFilter(this);
        mapperViewer.targetViewerAddFilter(associatedFilter);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.properties.AbstractEObjectSection#setInput(java.util
     * .Collection)
     */
    @Override
    public void setInput(Collection items) {
        super.setInput(items);
        EObject input = getInput();
        if (input != null) {
            Activity activity = (Activity) input;
            MapperViewerInput mapperInput =
                    new MapperViewerInput(activity, activity, activity);
            setMapperViewerInput(mapperInput);
        }
    }

    @Override
    protected String getTitle() {
        return Messages.JavaServiceMapperOutSection_JavaMappingOutHeader;
    }

    @Override
    protected IMappingTransferValidator getTransferValidator() {
        return this;
    }

    @Override
    protected BaseScriptSection getScriptSection() {
        return new JavaMapperScriptProperties(getDirection());
    }

    @Override
    public boolean canAcceptMultipleInputs(Object target) {
        return true;
    }

    @Override
    public boolean isValidTransfer(Object source, Object target) {
        boolean valid = false;
        // Not a valid transfer if either the source or the target is a
        // ChoiceConceptPath
        if (source instanceof ChoiceConceptPath
                || target instanceof ChoiceConceptPath) {
            return false;
        }
        if (source instanceof Collection) {
            boolean allValid = true;
            for (Object next : (Collection<?>) source) {
                if (next instanceof ScriptInformation
                        || !isValidTransfer(next, target)) {
                    allValid = false;
                    break;
                }
            }
            if (allValid) {
                valid = true;
            }
        } else {
            if ((source instanceof JavaMethodParameter || source instanceof ScriptInformation)
                    && (target instanceof ProcessRelevantData || target instanceof ConceptPath)) {

                DataMapping mapping = findDataMapping(target);
                if (mapping == null) {
                    valid = isValidSingleTransfer(source, target);
                } else {
                    Object other =
                            Xpdl2ModelUtil.getOtherElement(mapping,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_Script());
                    if (other == null && !(source instanceof ScriptInformation)) {
                        valid = isValidSingleTransfer(source, target);
                    }
                }
            }
        }
        return valid;
    }

    /**
     * @param target
     * @return
     */
    private DataMapping findDataMapping(Object target) {
        DataMapping found = null;
        Activity activity = (Activity) getInput();
        if (target != null && activity != null) {
            Implementation implementation = activity.getImplementation();
            if (implementation instanceof Task) {
                Task task = (Task) implementation;
                TaskService service = task.getTaskService();
                if (service != null) {
                    if (ImplementationType.OTHER_LITERAL.equals(service
                            .getImplementation())) {
                        Message message = service.getMessageOut();
                        if (message != null) {
                            List<?> mappings = message.getDataMappings();
                            for (Object next : mappings) {
                                DataMapping mapping = (DataMapping) next;
                                String targetName =
                                        DataMappingUtil.getTarget(mapping);
                                if (targetName
                                        .startsWith(JavaConstants.PROCESS_PREFIX
                                                + ".")) { //$NON-NLS-1$
                                    targetName = targetName.substring(8);
                                }
                                String name =
                                        ActivityMessageUtil.getPath(target);
                                if (name != null && name.equals(targetName)) {
                                    found = mapping;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        return found;
    }

    /**
     * @param source
     * @param target
     * @return
     */
    private boolean isValidSingleTransfer(Object source, Object target) {
        boolean valid;
        // Target name should be valid javascript identifier
        valid = true;

        if (source instanceof JavaMethodParameter) {
            JavaMethodParameter srcParam = (JavaMethodParameter) source;

            // Cannot map to Class type parameter
            if (valid) {
                /*
                 * allow map from class ype an dlet valdiation sort out any
                 * problems.
                 */
                // valid = srcParam.getType() != ParameterTypeEnum.CLASS;
            }

            /*
             * Cannot map an element of the source parameter to an array data
             * field. Also, cannot map a source array parameter that is already
             * mapped to an array to a non-array data field
             */
            if (target instanceof ConceptPath) {
                target = ((ConceptPath) target).getItem();
            }
            if (target instanceof ProcessRelevantData) {
                ProcessRelevantData tgtData = (ProcessRelevantData) target;
                if (valid && srcParam.isArray()) {
                    if (tgtData.isIsArray()) {
                        valid = true;
                    } else {
                        valid = !srcParam.isMappedToArrayDataField();
                    }
                }

                /*
                 * Cannot map an array type parameter to an array type data
                 * field that has already got a mapping. Also, cannot map a
                 * parameter to an array data field that is already mapped.
                 */
                if (valid && tgtData.isIsArray()) {
                    // valid = canMapToArrayDataField(srcParam, tgtData);
                }
            }
            // // Can't map to an array parameter type that is already
            // mapped to
            // an
            // // array data field
            // if (valid) {
            // valid = !srcParam.isMappedToArrayDataField();
            // }

            // // If the source data field is an array type then it should
            // only
            // be
            // // able to map to an array parameter
            // if (valid && tgtData.isIsArray()) {
            // valid = srcParam.canMapArrayField();
            // }
        }
        return valid;
    }

    /**
     * Check if an array type data field can be mapped. The data field can only
     * be mapped to a parameter if the parameter is an array type and the data
     * field is not already mapped.
     * 
     * @param srcParam
     * @param tgtData
     * @return
     */
    private boolean canMapToArrayDataField(JavaMethodParameter srcParam,
            ProcessRelevantData tgtData) {
        boolean canMap = false;

        if (srcParam != null && tgtData != null && srcParam.isArray()
                && tgtData.isIsArray()) {
            Activity activity = srcParam.getActivity();

            if (activity != null) {
                Implementation implementation = activity.getImplementation();
                if (implementation instanceof Task) {
                    Task task = (Task) implementation;
                    TaskService service = task.getTaskService();
                    if (service != null) {
                        if (ImplementationType.OTHER_LITERAL.equals(service
                                .getImplementation())) {
                            Message message = service.getMessageOut();
                            List<?> mappings = message.getDataMappings();
                            String fieldName =
                                    JavaConstants.PROCESS_PREFIX
                                            + JavaConstants.PARAMETER_SEPARATOR
                                            + tgtData.getName();

                            if (mappings != null) {
                                // Set can map to true
                                canMap = true;

                                for (Object next : mappings) {
                                    DataMapping mapping = (DataMapping) next;
                                    String target =
                                            DataMappingUtil.getTarget(mapping);
                                    if (target != null
                                            && target.equals(fieldName)) {
                                        // Data field already mapped so cannot
                                        // be mapped
                                        // again
                                        canMap = false;
                                        break;
                                    }
                                }

                            }
                        }
                    }
                }
            } else {
                throw new NullPointerException(
                        String.format("Activity from parameter %s is null.", srcParam //$NON-NLS-1$
                                        .getQualifiedElementName()));
            }

        }

        return canMap;
    }

    @Override
    public boolean select(Object toTest) {
        boolean ret = super.select(toTest);

        if (ret) {
            if (toTest instanceof TaskEditPart) {
                TaskEditPart editPart = (TaskEditPart) toTest;
                TaskAdapter taskAdapter =
                        (TaskAdapter) editPart.getModelAdapter();

                if (taskAdapter != null) {
                    String extensionName =
                            taskAdapter.getTaskImplementationExtensionId();

                    ret =
                            (extensionName != null && extensionName
                                    .equals(NativeServicesConsts.JAVA_SERVICE_ID));
                } else {
                    ret = false;
                }
            } else {
                ret = false;
            }
        }

        return ret;
    }

    /**
     * Contribution local identifier.
     * 
     * @see org.eclipse.ui.IPluginContribution#getLocalId()
     */
    @Override
    public String getLocalId() {
        return "pojoMapperIn"; //$NON-NLS-1$
    }

    /**
     * Contributing plug-in identifier.
     * 
     * @see org.eclipse.ui.IPluginContribution#getPluginId()
     */
    @Override
    public String getPluginId() {
        return JavaActivator.PLUGIN_ID;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractEObjectSection#getPluginContributon()
     */
    @Override
    public IPluginContribution getPluginContributon() {
        return this;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection#
     * getProblemMarkerDataMappingSourcePath(java.lang.Object)
     */
    @Override
    protected String getProblemMarkerDataMappingSourcePath(Object source) {
        if (source instanceof JavaMethodParameter) {
            JavaMethodParameter param = (JavaMethodParameter) source;
            return param.getPath();
        } else {
            /*
             * Saket XPD-4087: If we don't recognise source object type, the
             * superclass might!
             */
            return super.getProblemMarkerDataMappingSourcePath(source);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection#
     * getProblemMarkerDataMappingTargetPath(java.lang.Object)
     */
    @Override
    protected String getProblemMarkerDataMappingTargetPath(Object target) {
        if (target != null) {
            if (target instanceof ConceptPath) {
                return ((ConceptPath) target).getPath();
            } else {
                return ActivityMessageUtil.getParameterName(target);
            }
        }
        return null;
    }

    /**
     * @return
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractActivityMappingProblemMarkerHandlingSection#createMappingContentProvider()
     */
    @Override
    protected IStructuredContentProvider createMappingContentProvider() {
        return new PojoMappingItemProvider(MappingDirection.OUT);
    }

    /**
     * @return
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractActivityMappingProblemMarkerHandlingSection#createSourceContentProvider()
     */
    @Override
    protected ITreeContentProvider createSourceContentProvider() {
        ITreeContentProvider provider =
                new CompositeTreeContentProvider(new PojoItemProvider(
                        MappingDirection.OUT),
                        new ActivityScriptContentProvider(MappingDirection.OUT));
        return provider;
    }

    /**
     * @return
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractActivityMappingProblemMarkerHandlingSection#createTargetContentProvider()
     */
    @Override
    protected ITreeContentProvider createTargetContentProvider() {
        return new ActivityDataFieldItemProvider(MappingDirection.OUT);
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.implementer.resources.xpdl2.properties.
     * AbstractEditableMappingSection
     * #getTargetObjectTypeForRecursionComparison(java.lang.Object)
     */
    @Override
    public Object getSourceObjectTypeForRecursionComparison(
            Object sourceTreeContentObject) {
        Object typeForComparison = null;

        if (sourceTreeContentObject instanceof JavaMethodParameter) {
            JavaMethodParameter javaMethodParameter =
                    (JavaMethodParameter) sourceTreeContentObject;
            ParameterTypeEnum type = javaMethodParameter.getType();

            if (ParameterTypeEnum.CLASS.equals(type)) {
                typeForComparison = javaMethodParameter.getClass();
            }
        }

        if (typeForComparison == null) {
            typeForComparison =
                    super.getTargetObjectTypeForRecursionComparison(sourceTreeContentObject);
        }

        return typeForComparison;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection#getMappingCommandFactory2()
     * 
     * @return
     */
    @Override
    protected IMappingCommandFactory2 getMappingCommandFactory2() {
        return commandFactory;
    }
}
