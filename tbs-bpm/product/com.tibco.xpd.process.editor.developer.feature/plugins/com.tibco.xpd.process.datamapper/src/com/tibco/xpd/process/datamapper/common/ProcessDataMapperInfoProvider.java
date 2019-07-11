/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.process.datamapper.common;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.uml2.uml.Enumeration;

import com.tibco.xpd.analyst.resources.xpdl2.utils.BasicTypeConverterFactory;
import com.tibco.xpd.datamapper.api.AbstractDataMapperInfoProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.validation.bpmn.rules.baserules.MappingRuleContentInfoProviderBase;
import com.tibco.xpd.validation.bpmn.rules.baserules.ProcessDataMappingRuleInfoProvider;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.RecordType;

/**
 * This class implements ProcessDataMapperInfoProvider to provide content
 * information needed for the Process Data Mapper. It asks
 * ProcessDataMappingRuleInfoProvider to provide content apart from
 * getObjectForPath(), which is a new method here. Sub-classes may override this
 * for scenario-specific implementation of getObjectForPath() or any other
 * method (if necessary).
 * 
 * @author Ali
 * @since 12 Jan 2015
 */
public class ProcessDataMapperInfoProvider extends
        AbstractDataMapperInfoProvider {

    private ILabelProvider labelProvider;

    /**
     * We need to implement the {@link AbstractDataMapperInfoProvider} BUT it
     * would also be good to leverage the stuff already done in
     * {@link ProcessDataMappingRuleInfoProvider}. So we'll create one to
     * delegate to.
     */
    private MappingRuleContentInfoProviderBase baseInfoProvider;

    /**
     * @param contentProvider
     */
    public ProcessDataMapperInfoProvider(ITreeContentProvider contentProvider,
            ILabelProvider labelProvider) {
        super(contentProvider, labelProvider, false);
        this.labelProvider = labelProvider;
        this.baseInfoProvider = createBaseInfoProvider(contentProvider);
    }

    /**
     * Override this if we need a specifically different behaviour from the base
     * info provider.
     * <p>
     * This would be called while constructing the
     * <code>ProcessDataMapperInfoProvider</code> instance.
     * <p>
     * 
     * @param contentProvider
     * 
     * @return <code>ProcessDataMappingRuleInfoProvider</code> instance for the
     *         specified tree content provider.
     */
    protected MappingRuleContentInfoProviderBase createBaseInfoProvider(
            ITreeContentProvider contentProvider) {

        return new ProcessDataMappingRuleInfoProvider(contentProvider);
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.MappingRuleContentInfoProviderBase#getObjectPath(java.lang.Object)
     * 
     * @param objectFromMappingOrContent
     * @return
     */
    @Override
    public String getObjectPath(Object objectFromMappingOrContent) {
        return baseInfoProvider.getObjectPath(objectFromMappingOrContent);
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.MappingRuleContentInfoProviderBase#getObjectPathDescription(java.lang.Object)
     * 
     * @param objectFromMappingOrContent
     * @return
     */
    @Override
    public String getObjectPathDescription(Object objectFromMappingOrContent) {
        return baseInfoProvider
                .getObjectPathDescription(objectFromMappingOrContent);
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.MappingRuleContentInfoProviderBase#isReadOnlyTarget(java.lang.Object)
     * 
     * @param targetObjectInTree
     * @return
     */
    @Override
    public boolean isReadOnlyTarget(Object targetObjectInTree) {
        return baseInfoProvider.isReadOnlyTarget(targetObjectInTree);
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.MappingRuleContentInfoProviderBase#isMultiInstance(java.lang.Object)
     * 
     * @param objectInTree
     * @return
     */
    @Override
    public boolean isMultiInstance(Object objectInTree) {
        return baseInfoProvider.isMultiInstance(objectInTree);
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.MappingRuleContentInfoProviderBase#getMinimumInstances(java.lang.Object)
     * 
     * @param objectInTree
     * @return
     */
    @Override
    public int getMinimumInstances(Object objectInTree) {
        return baseInfoProvider.getMinimumInstances(objectInTree);
    }

    /**
     * @param objectInTree
     * @return
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.ProcessDataMappingRuleInfoProvider#getMaximumInstances(java.lang.Object)
     */
    @Override
    public int getMaximumInstances(Object objectInTree) {
        return baseInfoProvider.getMaximumInstances(objectInTree);
    }

    /**
     * @param objectInTree
     * @return
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.ProcessDataMappingRuleInfoProvider#getInstanceIndex(java.lang.Object)
     */
    @Override
    public int getInstanceIndex(Object objectInTree) {
        return baseInfoProvider.getInstanceIndex(objectInTree);
    }

    /**
     * @param objectInTree
     * @return
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.ProcessDataMappingRuleInfoProvider#isSimpleTypeContent(java.lang.Object)
     */
    @Override
    public boolean isSimpleTypeContent(Object objectInTree) {

        /*
         * overriding this as the super class method doesn't consider simple
         * type arrays as simple type content
         */

        if (objectInTree != null) {
            if (objectInTree instanceof ConceptPath) {
                ConceptPath cp = (ConceptPath) objectInTree;

                if (cp.getItem() != null) {
                    /*
                     * Any process data of primitive type should be counted as
                     * simple content (If parent of simple type content is
                     * Complex data then its not a simple type content)
                     */
                    BasicType basicType =
                            BasicTypeConverterFactory.INSTANCE.getBasicType(cp
                                    .getItem());
                    if (basicType != null) {
                        return true;

                    }
                    /*
                     * Sid XPD-7674: Enumerations can also be treated as (so
                     * that framework deosn't try to create them etc.
                     */
                    else if (cp.getType() instanceof Enumeration) {
                        return true;
                    }
                    /*
                     * Sid XPD-7723 - Case Reference should be treated as simple
                     * type as far as we're concerned.
                     */
                    else if (cp.getItem() instanceof ProcessRelevantData
                            && ((ProcessRelevantData) cp.getItem())
                                    .getDataType() instanceof RecordType) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * @param objectInTree
     * @return
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.ProcessDataMappingRuleInfoProvider#isNullSimpleContentAllowed(java.lang.Object)
     */
    @Override
    public boolean isNullSimpleContentAllowed(Object objectInTree) {
        return baseInfoProvider.isNullSimpleContentAllowed(objectInTree);
    }

    /**
     * @param objectInTree
     * @return
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.ProcessDataMappingRuleInfoProvider#isArtificialObject(java.lang.Object)
     */
    @Override
    public boolean isArtificialObject(Object objectInTree) {
        return baseInfoProvider.isArtificialObject(objectInTree);
    }

    /**
     * @param objectFromMappingOrContent
     * @return
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.ProcessDataMappingRuleInfoProvider#isChoiceObject(java.lang.Object)
     */
    @Override
    public boolean isChoiceObject(Object objectFromMappingOrContent) {
        return baseInfoProvider.isChoiceObject(objectFromMappingOrContent);
    }

    /**
     * @return the labelProvider
     */
    @Override
    public ILabelProvider getLabelProvider() {
        return labelProvider;
    }

    /**
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperInfoProvider#getObjectForPath(java.lang.Object,
     *      java.lang.Object)
     * 
     * @param objectPath
     * @param mapperInput
     * @return
     */
    @Override
    public Object getObjectForPath(String objectPath, Object mapperInput) {
        /*
         * this only deals with conceptPaths and sub-classes may override this
         * to provide different implementation if the object for the path is not
         * a ConceptPath
         */

        if (mapperInput instanceof Activity) {
            ConceptPath formalConcept =
                    ConceptUtil.resolveConceptPath((Activity) mapperInput,
                            objectPath,
                            true);
            return formalConcept;
        }

        return null;
    }

    /**
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperInfoProvider#getObjectType(java.lang.Object)
     * 
     * @param object
     * @return
     */
    @Override
    public String getObjectType(Object object) {
        if (object instanceof ConceptPath) {
            ConceptPath cp = (ConceptPath) object;

            Object baseType =
                    BasicTypeConverterFactory.INSTANCE
                            .getBaseType(cp.getItem(), false);

            if (baseType != null) {
                return baseType.toString();
            }

        }
        return null;
    }

    /**
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperInfoProvider#getObjectName(java.lang.Object)
     * 
     * @param object
     * @return
     */
    @Override
    public String getObjectName(Object object) {
        if (object instanceof ConceptPath) {
            ConceptPath cp = (ConceptPath) object;
            return cp.getName();
        }
        return null;
    }

}
