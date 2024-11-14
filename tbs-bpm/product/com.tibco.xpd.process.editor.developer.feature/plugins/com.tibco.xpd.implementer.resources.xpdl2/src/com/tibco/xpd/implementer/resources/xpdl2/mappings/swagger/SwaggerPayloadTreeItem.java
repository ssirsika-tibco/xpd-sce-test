/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import com.tibco.xpd.implementer.resources.xpdl2.internal.Messages;
import com.tibco.xpd.resources.util.XpdUtil;
import com.tibco.xpd.xpdl2.Activity;

import io.swagger.v3.oas.models.media.Schema;

/**
 *
 *
 * @author nkelkar
 * @since Sep 17, 2024
 */
@SuppressWarnings("nls")
public class SwaggerPayloadTreeItem extends SwaggerTypedTreeItem
{
	private SwaggerMapperTreeItem		parent;

	private String						path;

	private List<SwaggerMapperTreeItem>	children;

	private boolean						isRequired;

	/**
	 * 
	 * @param parent
	 * @param activity
	 * @param path
	 * @param name
	 * @param propertySchema
	 *            The schema associated with the property (not necessarily the type schema referenced by it.
	 * @param isArray
	 * @param isRequired
	 */
	public SwaggerPayloadTreeItem(SwaggerMapperTreeItem parent, Activity activity, String path, String name,
			Schema propertySchema, boolean isRequired)
	{
		super(activity, propertySchema, name);
		this.parent = parent;
		this.path = path;
		this.isRequired = isRequired;
		/* Sid ACE-8885 'name' pulled up into SwaggerTypedTreeItem (so that it can be used during construction). */

		this.children = new ArrayList<SwaggerMapperTreeItem>();
	}

	/**
	 * 
	 * @see com.tibco.xpd.implementer.resources.xpdl2.mappings.RestMapperTreeItem#createChildren()
	 *
	 * @return
	 */
	@Override
	public List<SwaggerMapperTreeItem> createChildren()
	{
		List<SwaggerMapperTreeItem> children = new ArrayList<>();

		Schema typeSchema = getTypeSchema();

		if (typeSchema != null && typeSchema.getProperties() != null)
		{
			/*
			 * Prevent recursive schemas going bananas.
			 */
			if (hasAncestorOfType(typeSchema))
			{
				children.add(UnsupportedSwaggerTreeItem.createUnsupportedRequestItem(getActivity(), String
						.format(Messages.SwaggerPayloadTreeItem_RecursiveSchemaNotSupportedValidationMsg, getLabel()),
						this, getPath()));
				return children;
			}
			
			List<String> requiredProps = typeSchema.getRequired() != null ? typeSchema.getRequired()
					: new ArrayList<>();

			String parentPath = getPath();

			Map< ? , ? > properties = typeSchema.getProperties();

			if (properties != null)
			{
				for (Entry entry : properties.entrySet())
				{
					if (entry.getKey() instanceof String && entry.getValue() instanceof Schema)
					{
						String propertyName = (String) entry.getKey();
						Schema propSchema = (Schema) entry.getValue();

						/*
						 * Sid ACE-8885 Handling of unsupported constructs moved into SwaggerTypedTreeItem, because they
						 * could be hidden in nested type indirections. So we no longer handle ComposedSchema here, but
						 * do so in SwaggerTypedTreeitem type handling directly.
						 */
						
						boolean required = requiredProps.contains(propertyName);

						SwaggerPayloadTreeItem childItem = new SwaggerPayloadTreeItem(this, getActivity(),
								parentPath + "." + propertyName, propertyName, propSchema, required); //$NON-NLS-1$

						children.add(childItem);
					}
				}
			}
		}
		/*
		 * Sid ACE-8885 Handling of unsupported constructs moved into SwaggerTypedTreeItem, because they
		 * could be hidden in nested type indirections. So we no longer handle root level ComposedSchema here, but
		 * do so in SwaggerTypedTreeitem type handling directly.
		 */

		return children;
	}


	/**
	 * Recursively traverses up the parent hierarchy to find the first instance of
	 * SwaggerResponseContainerTreeItem. If found, returns the SwaggerResponseContainerTreeItem;
	 * otherwise, returns null.
	 *
	 * @param item the starting SwaggerMapperTreeItem from which to begin the traversal
	 * @return the found SwaggerResponseContainerTreeItem, or null if not found in the hierarchy
	 */
	private SwaggerResponseContainerTreeItem getSwaggerResponseContainerTreeItem(SwaggerMapperTreeItem item) {
		if (item != null) {
			if (item instanceof SwaggerResponseContainerTreeItem) {
				return (SwaggerResponseContainerTreeItem) item;
			}
			return getSwaggerResponseContainerTreeItem(item.getParent());
		}
		return null;
	}

	/**
	 * Check if any ancestor of this tree item has the same schema type as this tree
	 * Item.
	 * 
	 * @param schema
	 * 
	 * @return <code>true</code> if parent of any ancestor is same type.
	 */
	private boolean hasAncestorOfType(Schema schema) {
		if (getParent() instanceof SwaggerPayloadTreeItem) {
			SwaggerPayloadTreeItem parentTreeItem = (SwaggerPayloadTreeItem) getParent();

			Schema parentSchema = parentTreeItem.getTypeSchema();

			if (Objects.equals(schema, parentSchema)) {
				return true;
			} else {
				return parentTreeItem.hasAncestorOfType(schema);
			}
		}

		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SwaggerMapperTreeItem getParent()
	{
		return parent;
	}

	@Override
	public String getPath()
	{
		return path;
	}

	public boolean isRequired()
	{
		return isRequired;
	}

	public void addChild(SwaggerMapperTreeItem child)
	{
		this.children.add(child);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getActivity() == null) ? 0 : getActivity().hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		result = prime * result + ((getText() == null) ? 0 : getText().hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		SwaggerPayloadTreeItem other = (SwaggerPayloadTreeItem) obj;

		if (!XpdUtil.safeEquals(getActivity(), other.getActivity())) return false;
		if (!XpdUtil.safeEquals(parent, other.parent)) return false;
		if (!XpdUtil.safeEquals(getText(), other.getText())) return false;
		if (!XpdUtil.safeEquals(path, other.path)) return false;
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString()
	{
		return path;
	}
}
