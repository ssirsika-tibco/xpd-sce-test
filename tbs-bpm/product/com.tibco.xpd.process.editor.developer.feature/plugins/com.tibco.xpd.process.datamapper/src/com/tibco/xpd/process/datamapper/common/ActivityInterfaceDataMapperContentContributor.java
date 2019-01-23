package com.tibco.xpd.process.datamapper.common;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;

import com.tibco.xpd.datamapper.api.AbstractDataMapperContentContributor;
import com.tibco.xpd.datamapper.api.AbstractDataMapperInfoProvider;
import com.tibco.xpd.implementer.resources.xpdl2.properties.ParameterLabelProvider;

/**
 * Contributes the normal process data content for Worm Manager Data Mapper
 * scripts (which is distinct for Work Manager scripts as these only allow
 * access to data that is implicitly/explicitly associated in activity
 * interface).
 * 
 * @author aallway
 * @since 27 Apr 2015
 */
public class ActivityInterfaceDataMapperContentContributor extends
        AbstractDataMapperContentContributor {

    /**
     * The data mapper contributor id for activity interface associated process
     * data content provider.
     */
    public static final String ACTIVITY_INTERFACE_CONTRIBUTOR_ID =
            "ActivityInterface.DataMapperContent"; //$NON-NLS-1$

    public ActivityInterfaceDataMapperContentContributor() {
    }

    /**
     * 
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperContentContributor#createInfoProvider()
     * 
     * @return
     */
    @Override
    public AbstractDataMapperInfoProvider createInfoProvider() {

        ITreeContentProvider contentProvider =
                new ActivityInterfaceDataMapperConceptPathProvider();

        ILabelProvider labelProvider = new ParameterLabelProvider();

        AbstractDataMapperInfoProvider infoProvider =
                new ProcessDataMapperInfoProvider(contentProvider,
                        labelProvider);
        return infoProvider;
    }

    /**
     * 
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperContentContributor#getContributorId()
     * 
     * @return
     */
    @Override
    public String getContributorId() {
        return ACTIVITY_INTERFACE_CONTRIBUTOR_ID;
    }

}
