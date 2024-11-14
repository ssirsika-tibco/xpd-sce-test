/*
 * Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
 */
package com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger;

import java.util.List;

import com.tibco.xpd.xpdl2.Activity;

/**
 * Special tree item to indicate the unsupported objects. 
 * 
 * @author ssirsika
 * @since 14 Oct 2024
 */
public class UnsupportedSwaggerTreeItem extends SwaggerContainerTreeItem {

	private String					label;

	private Activity				activity;

	private SwaggerMapperTreeItem	parent;

	private String					path;

	/**
	 * Public Enum for body type
	 */
	public enum BODY_TYPE {
		REQUEST,
		RESPONSE
	}
	
	/**
	 * Method to create {@link UnsupportedSwaggerTreeItem} for Request body
	 * 
	 * @param activity
	 *            {@link Activity}
	 * @param label
	 *            Label to be shown on mapping UI
	 * @param parent
	 * @param path
	 *            Sid ACE-8885 added parent and path so that error decoration will still work properly.
	 * 
	 * @return {@link UnsupportedSwaggerTreeItem}
	 */
	public static UnsupportedSwaggerTreeItem createUnsupportedRequestItem(Activity activity, String label,
			SwaggerMapperTreeItem parent, String path)
	{
		return new UnsupportedSwaggerTreeItem(activity, label, parent, path);
	}

	/**
	 * @param activity
	 *            {@link Activity}
	 * @param label
	 *            text which can be shown on the mapping UI.
	 * @param parent
	 * @param path
	 */
	private UnsupportedSwaggerTreeItem(Activity activity, String label, SwaggerMapperTreeItem parent, String path)
	{
		this.activity = activity;
		this.label = label;

		/* Sid ACE-8885 added parent and path so that error decoration will still work properly. */
		this.parent = parent;
		this.path = path;

		/*
		 * SId ACE-8885 Remove bodyType and messageParam (because this was only used for status code and all places that
		 * might deal with an unsupported item must be able to check for and use any SwaggerResponseContainerTreeItem
		 * ancestor to get the status-code for errors in specific response tree
		 */
	}
	
	@Override
	public String getPath() {
		return path;
	}

	@Override
	public SwaggerMapperTreeItem getParent() {
		return parent;
	}

	@Override
	protected List<?> createChildren() {
		return null;
	}

	/**
	 * Return test which will be used to show the content on the mapping UI.
	 */
	@Override
	public String getText() {
		return this.label;
	}

	/**
	 * Returns activity
	 */
	@Override
	public Activity getActivity() {
		return activity;
	}

}
