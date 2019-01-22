/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.mapper;

import org.eclipse.jface.viewers.ILabelProvider;

/**
 * Label providers for the mapper viewer.  The source is the tree content on the
 * LHS, the target on the RHS.  There is currently no label provider for the mappings, 
 * but this may change!
 * 
 * @author scrossle
 *
 */
public final class MapperLabelProvider {
	/**
	 * Labels for source.
	 */
	private ILabelProvider sourceLabelProvider;
	/**
	 * Labels for target.
	 */
	private ILabelProvider targetLabelProvider;
	/**
	 * @param sourceLabelProvider Labels for source.
	 * @param targetLabelProvider Labels for target.
	 */
	public MapperLabelProvider(ILabelProvider sourceLabelProvider, 
			ILabelProvider targetLabelProvider) {
		super();
		this.sourceLabelProvider = sourceLabelProvider;
		this.targetLabelProvider = targetLabelProvider;
	}
	/**
	 * @return the sourceLabelProvider
	 */
	public ILabelProvider getSourceLabelProvider() {
		return sourceLabelProvider;
	}
	/**
	 * @return the targetLabelProvider
	 */
	public ILabelProvider getTargetLabelProvider() {
		return targetLabelProvider;
	}
	
	

}
