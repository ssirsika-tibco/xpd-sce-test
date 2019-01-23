/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.uml2.uml.Classifier;

import com.tibco.xpd.analyst.resources.xpdl2.utils.BasicTypeConverterFactory;
import com.tibco.xpd.mapper.MapperContentProvider;
import com.tibco.xpd.mapper.MapperLabelProvider;
import com.tibco.xpd.mapper.MapperViewerInput;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * @author nwilson
 */
public abstract class AbstractItemProviderMappingSection extends
        AbstractMappingSection {

    private MapperContentProvider mapperContentProvider;

    private MapperLabelProvider mapperLabelProvider;

    /**
     * The Constructor.
     */
    public AbstractItemProviderMappingSection(MappingDirection direction) {
        super(direction);
        // adapterFactory = createMapperItemProviderFactory();
        ITreeContentProvider sourceContentProvider =
                createSourceContentProvider();
        ITreeContentProvider targetContentProvider =
                createTargetContentProvider();
        ILabelProvider sourceLabelProvider =
                new SubProcParameterLabelProvider();
        ILabelProvider targetLabelProvider =
                new SubProcParameterLabelProvider();
        IStructuredContentProvider mappingCP = createMappingContentProvider();
        mapperContentProvider =
                new MapperContentProvider(sourceContentProvider,
                        targetContentProvider, mappingCP);
        mapperLabelProvider =
                new MapperLabelProvider(sourceLabelProvider,
                        targetLabelProvider);
    }

    abstract protected ITreeContentProvider createSourceContentProvider();

    abstract protected ITreeContentProvider createTargetContentProvider();

    abstract protected IStructuredContentProvider createMappingContentProvider();

    // abstract protected MapperItemProviderAdapterFactory
    // createMapperItemProviderFactory();

    /**
     * @param mapperLabelProvider
     *            the mapperLabelProvider to set
     */
    protected void setMapperLabelProvider(
            MapperLabelProvider mapperLabelProvider) {
        this.mapperLabelProvider = mapperLabelProvider;
    }

    /*
     * @see
     * com.tibco.xpd.implementer.resources.xpdl2.properties.AbstractMappingSection
     * #getContentProvider()
     */
    @Override
    final protected MapperContentProvider getContentProvider() {
        return mapperContentProvider;
    }

    /*
     * @see
     * com.tibco.xpd.implementer.resources.xpdl2.properties.AbstractMappingSection
     * #getLabelProvider()
     */
    @Override
    final protected MapperLabelProvider getLabelProvider() {
        return mapperLabelProvider;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.properties.AbstractEObjectSection#setInput(java.util
     * .Collection)
     */
    @Override
    public void setInput(Collection<?> items) {
        super.setInput(items);
        EObject input = getInput();
        if (input != null) {
            EObject eo = input;
            MapperViewerInput mapperInput = new MapperViewerInput(eo, eo, eo);
            setMapperViewerInput(mapperInput);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.mapper.IErrorProvider#getSourceObjectTypeForRecusionComparison
     * (java.lang.Object)
     */
    public Object getSourceObjectTypeForRecursionComparison(
            Object sourceTreeContentObject) {
        return getObjectTypeForRecursionComparison(sourceTreeContentObject);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.mapper.IErrorProvider#getTargetObjectTypeForRecusionComparison
     * (java.lang.Object)
     */
    public Object getTargetObjectTypeForRecursionComparison(
            Object targetTreeContentObject) {
        return getObjectTypeForRecursionComparison(targetTreeContentObject);
    }

    private Object getObjectTypeForRecursionComparison(Object object) {
        if (object instanceof ConceptPath) {
            Classifier type = ((ConceptPath) object).getType();

            if (type != null) {
                return type;
            }

            /*
             * If it's hasn't got a classifier type set then check the item that
             * is set.
             */
            object = ((ConceptPath) object).getItem();

        }

        if (object instanceof ProcessRelevantData) {
            return BasicTypeConverterFactory.INSTANCE
                    .getBaseType(object, false);
        }

        return null;
    }
}
