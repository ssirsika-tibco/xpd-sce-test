/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.n2.process.localsignal.datamapper;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;

import com.tibco.xpd.process.datamapper.common.ProcessDataMapperInfoProvider;
import com.tibco.xpd.validation.bpmn.rules.baserules.MappingRuleContentInfoProviderBase;
import com.tibco.xpd.validation.bpmn.rules.baserules.ProcessDataMappingRuleInfoProvider;

/**
 * Info provider implementation for Local Signal CATCH data mapper.
 *
 * @author sajain
 * @since Jul 16, 2019
 */
public class LocalSignalCatchSourceDataMapperInfoProvider extends ProcessDataMapperInfoProvider {

    /**
     * Info provider implementation for Local Signal CATCH data mapper.
     * 
     * @param contentProvider
     * @param labelProvider
     */
    public LocalSignalCatchSourceDataMapperInfoProvider(ITreeContentProvider contentProvider,
            ILabelProvider labelProvider) {
        super(contentProvider, labelProvider);
    }

    /**
     * @see com.tibco.xpd.process.datamapper.common.ProcessDataMapperInfoProvider#createBaseInfoProvider(org.eclipse.jface.viewers.ITreeContentProvider)
     * 
     * @param contentProvider
     * @return
     */
    @Override
    protected MappingRuleContentInfoProviderBase createBaseInfoProvider(ITreeContentProvider contentProvider) {
        return new ProcessDataMappingRuleInfoProvider(contentProvider);
    }
}