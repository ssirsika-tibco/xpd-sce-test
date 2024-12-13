/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rest.datamapper;

import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.datamapper.DataMapperMappingContentProvider;
import com.tibco.xpd.datamapper.api.AbstractDataMapperSection;
import com.tibco.xpd.datamapper.scripts.AbstractScriptDataMapperEditorProvider;
import com.tibco.xpd.implementer.resources.xpdl2.properties.RestServiceTaskAdapter;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.resources.util.XpdUtil;
import com.tibco.xpd.xpdl2.Activity;

/**
 * This section provides the base Data Mapper for use in REST Invocation
 * activities.
 * 
 * @author nwilson
 * @since 29 Apr 2015
 */
public abstract class AbstractRestDataMapperSection extends
        AbstractDataMapperSection {

	/**
	 * Sid ACE-8742 Keeps track of the currently selected service so that we can force a refresh of mapping content
	 * providers.
	 */
	private String cachedServiceRef = null;

    /**
     * @param direction
     *            The mapping direction for this section.
     */
    public AbstractRestDataMapperSection(MappingDirection direction) {
        super(direction);
    }

    /**
     * 
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperSection#getScriptDataMapperProvider()
     * 
     * @return
     */
    @Override
    protected AbstractScriptDataMapperEditorProvider getScriptDataMapperProvider() {
		return new RestScriptDataMapperProvider(getDirection(), getDataMapperContext());
	}

	/**
	 * @see com.tibco.xpd.datamapper.api.AbstractDataMapperSection#shouldRefresh(java.util.List)
	 *
	 * @param notifications
	 * @return
	 */
	@Override
	protected boolean shouldRefresh(List<Notification> notifications)
	{
		/*
		 * Sid ACE-8742 If service reference has changed then need to refresh
		 */
		EObject input = getInput();

		if (input instanceof Activity)
		{
			Activity activity = (Activity) input;

			RestServiceTaskAdapter rsta = new RestServiceTaskAdapter();

			/*
			 * Check if input activity or selected service has changed and if so, then we should refresh
			 */
			String currentServiceRef = rsta.getServiceRefKey(activity);

			if (!XpdUtil.safeEquals(currentServiceRef, cachedServiceRef))
			{
				cachedServiceRef = currentServiceRef;

				/* Also ensure that any cached mappings content is reset */
				DataMapperMappingContentProvider mappingContentProvider = getMappingContentProvider();
				if (mappingContentProvider != null)
				{
					mappingContentProvider.resetCache();
				}

				return true;
			}
		}

		return super.shouldRefresh(notifications);
	}
}
