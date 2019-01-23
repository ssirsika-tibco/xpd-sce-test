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
import com.tibco.xpd.implementer.nativeservices.java.internal.Messages;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper.JavaMethodParameter;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper.JavaParameterLabelProvider;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper.JavaServiceMappingCommandFactory;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper.JavaServiceMappingUtil;
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
 * Java (pojo) IN mapper section.
 * 
 * @author njpatel
 */
public class JavaServiceMapperInSection extends AbstractEditableMappingSection
        implements IFilter, IMappingTransferValidator, IPluginContribution {

    private JavaServiceMappingCommandFactory commandFactory;

    /**
     * Constructor
     */
    public JavaServiceMapperInSection() {
        super(MappingDirection.IN);
        commandFactory =
                new JavaServiceMappingCommandFactory(MappingDirection.IN);
        setMapperLabelProvider(new MapperLabelProvider(
                new ScriptableLabelProvider(new JavaParameterLabelProvider()),
                new JavaParameterLabelProvider()));
    }

    @Override
    protected void addViewerFilters(MapperViewer mapperViewer) {
        ViewerFilter associatedFilter = new AssociatedParameterFilter(this);
        mapperViewer.sourceViewerAddFilter(associatedFilter);
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
    protected String getTitle() {
        return Messages.JavaServiceMapperInSection_JavaMappingInHeader;
    }

    @Override
    protected BaseScriptSection getScriptSection() {
        return new JavaMapperScriptProperties(getDirection());
    }

    @Override
    protected IMappingTransferValidator getTransferValidator() {
        return this;
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
            DataMapping mapping = findDataMapping(target);
            if ((source instanceof ProcessRelevantData
                    || source instanceof ConceptPath || source instanceof ScriptInformation)
                    && target instanceof JavaMethodParameter) {
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
     * @param source
     * @param target
     * @return
     */
    private boolean isValidSingleTransfer(Object source, Object target) {
        boolean valid;
        JavaMethodParameter tgtParam = (JavaMethodParameter) target;

        // The data field name should be valid javascript identifier
        valid = true;

        /*
         * SID XPD-225 - there was a request to be able to map certain data
         * types to certain class types (such as DATETIME -> GregorianCalender,
         * so we should allow, but validate against later.
         */
        // Can't map to a Class type parameter
        // if (valid) {
        // valid = tgtParam.getType() != ParameterTypeEnum.CLASS;
        // }
        // Can't map to an array parameter type that is already mapped to an
        // array data field
        if (valid) {
            valid = !tgtParam.isMappedToArrayDataField();
        }

        /*
         * SID XPD-225 - allow user to create any mapping and validate against
         * it afterwards.
         */
        // If the source data field is an array type then it should only be
        // able to map to an array parameter
        // if (source instanceof ConceptPath) {
        // source = ((ConceptPath) source).getItem();
        // }
        // if (source instanceof ProcessRelevantData) {
        // ProcessRelevantData srcData = (ProcessRelevantData) source;
        //
        // if (valid && srcData.isIsArray()) {
        // valid = canMapArrayField(tgtParam);
        // }
        // }
        return valid;
    }

    /**
     * @param path
     * @return
     */
    private DataMapping findDataMapping(Object target) {
        DataMapping mapping = null;
        Object input = getInput();
        if (input instanceof Activity) {
            Activity activity = (Activity) input;
            Implementation implementation = activity.getImplementation();
            if (implementation instanceof Task) {
                Task task = (Task) implementation;
                TaskService service = task.getTaskService();
                if (service != null) {
                    if (ImplementationType.OTHER_LITERAL.equals(service
                            .getImplementation())) {
                        Message message = service.getMessageIn();
                        List<?> mappings = message.getDataMappings();
                        for (Object next : mappings) {
                            DataMapping dataMapping = (DataMapping) next;
                            String name =
                                    JavaServiceMappingUtil
                                            .getScriptName(target,
                                                    getDirection());
                            if (name != null
                                    && name.equals(dataMapping.getFormal())) {
                                mapping = dataMapping;
                            }
                        }
                    }
                }
            }
        }
        return mapping;
    }

    /**
     * Check if an array field can be mapped to the given parameter. The
     * parameter can only be mapped from an array field if this is not an
     * element of an array and it's not already mapped to array field.
     * 
     * @return <code>true</code> if an array field can be mapped
     */
    private boolean canMapArrayField(JavaMethodParameter tgtParam) {
        boolean canMap = false;

        if (tgtParam != null) {

            canMap = tgtParam.isArray() && !tgtParam.isMappedToArrayDataField();

            // if (canMap) {
            // canMap = tgtParam.isHighestIndex() && tgtParam.getIndex() == 0;
            // }
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
        return "pojoMapperOut"; //$NON-NLS-1$
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
        if (source != null) {
            if (source instanceof ConceptPath) {
                return ((ConceptPath) source).getPath();
            } else {
                return ActivityMessageUtil.getParameterName(source);
            }
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
        if (target instanceof JavaMethodParameter) {
            JavaMethodParameter param = (JavaMethodParameter) target;
            return param.getPath();
        }

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.implementer.resources.xpdl2.properties.
     * AbstractEditableMappingSection
     * #getTargetObjectTypeForRecursionComparison(java.lang.Object)
     */
    @Override
    public Object getTargetObjectTypeForRecursionComparison(
            Object targetTreeContentObject) {
        Object typeForComparison = null;

        if (targetTreeContentObject instanceof JavaMethodParameter) {
            JavaMethodParameter javaMethodParameter =
                    (JavaMethodParameter) targetTreeContentObject;
            ParameterTypeEnum type = javaMethodParameter.getType();

            if (ParameterTypeEnum.CLASS.equals(type)) {
                typeForComparison = javaMethodParameter.getClass();
            }
        }

        if (typeForComparison == null) {
            typeForComparison =
                    super.getTargetObjectTypeForRecursionComparison(targetTreeContentObject);
        }

        return typeForComparison;
    }

    /**
     * @return
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractActivityMappingProblemMarkerHandlingSection#createMappingContentProvider()
     */
    @Override
    protected IStructuredContentProvider createMappingContentProvider() {
        return new PojoMappingItemProvider(MappingDirection.IN);
    }

    /**
     * @return
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractActivityMappingProblemMarkerHandlingSection#createSourceContentProvider()
     */
    @Override
    protected ITreeContentProvider createSourceContentProvider() {
        ITreeContentProvider provider =
                new CompositeTreeContentProvider(
                        new ActivityDataFieldItemProvider(MappingDirection.IN),
                        new ActivityScriptContentProvider(MappingDirection.IN));
        return provider;
    }

    /**
     * @return
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractActivityMappingProblemMarkerHandlingSection#createTargetContentProvider()
     */
    @Override
    protected ITreeContentProvider createTargetContentProvider() {
        return new PojoItemProvider(MappingDirection.IN);
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
