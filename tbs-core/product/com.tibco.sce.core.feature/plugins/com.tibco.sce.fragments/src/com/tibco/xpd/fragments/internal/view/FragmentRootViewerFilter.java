/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.fragments.internal.view;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.tibco.xpd.fragments.internal.FragmentsExtensionHelper.FragmentsProvider;
import com.tibco.xpd.fragments.internal.impl.FragmentRootCategory;

/**
 * Fragments viewer filter. When this filter is applied only the fragments from
 * the providers with the given ids will be included.
 * 
 * @author njpatel
 * 
 */
public class FragmentRootViewerFilter extends ViewerFilter {

	private Set<String> providerIds;

	/**
	 * Fragments view filter for the given provider (ids).
	 * 
	 * @param providerIds
	 *            ids of providers to include in the fragments view.
	 */
	public FragmentRootViewerFilter(String[] providerIds) {
		this.providerIds = providerIds != null ? new HashSet<String>(Arrays
				.asList(providerIds)) : null;
	}

	/**
	 * Get the provider ids set in this filter.
	 * 
	 * @return provder ids or <code>null</code> if not set./
	 */
	protected Set<String> getProviderIds() {
		return providerIds;
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {

		if (element instanceof FragmentRootCategory) {
			Set<String> ids = getProviderIds();

			if (ids != null) {
				FragmentsProvider provider = ((FragmentRootCategory) element)
						.getProvider();

				return provider != null && ids.contains(provider.getId());
			}
		}

		return true;
	}

}
