/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.datamapper.api;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;

import com.tibco.xpd.validation.bpmn.rules.baserules.MappingRuleContentInfoProviderBase;

/**
 * This class provide the content information needed for the Data Mapper. It
 * extends MappingRuleContentInfoProviderBase to provide additional info
 * required for the Data Mapper feature (e.g., like mapping etc) and also allows
 * to provide a label provider for the content.
 * 
 * @author Ali
 * @since 12 Jan 2015
 */
public abstract class AbstractDataMapperInfoProvider extends
        MappingRuleContentInfoProviderBase {

    private ILabelProvider labelProvider;

    /**
     * 
     * Note: caching can be used for validation rules and the script generator.
     * It should be turned off when used for mapper content or model changes
     * won't be reflected in the UI.
     * 
     * @param contentProvider
     *            The content provider.
     * @param labelProvider
     *            The label provider.
     * @param caching
     *            true to cache child content.
     */
    public AbstractDataMapperInfoProvider(ITreeContentProvider contentProvider,
            ILabelProvider labelProvider, boolean caching) {
        super(contentProvider, caching);
        this.labelProvider = labelProvider;
    }

    /**
     * @param contentProvider
     */
    public AbstractDataMapperInfoProvider(ITreeContentProvider contentProvider,
            ILabelProvider labelProvider) {
        this(contentProvider, labelProvider, true);
    }

    /**
     * @return the labelProvider
     */
    public ILabelProvider getLabelProvider() {
        return labelProvider;
    }

    /**
     * 
     * @param objectPath
     * @param mapperInput
     * @return Object for the given path string(e.g., given DataMapping formal
     *         string should return corresponding concept path in case of
     *         process data mapper)
     */
    public abstract Object getObjectForPath(String objectPath,
            Object mapperInput);

    /**
     * @param child
     * @return The name of the object (complying with JAvaScript object naming
     *         restrictions so that it can be used to build JavaScript variable
     *         nams etc)
     */
    public abstract String getObjectName(Object object);

    /**
     * Get the string that specifies the object type, this is used by the data
     * mapper framework to determine the type-equivalence for like-mappings
     * 
     * @param object
     * @return type string of the given object
     */
    public abstract String getObjectType(Object object);

}
