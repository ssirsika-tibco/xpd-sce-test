/*
 * Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
 */

package com.tibco.bds.designtime.api.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Property;

import com.tibco.bpm.cdm.libs.dql.exception.DQLException;
import com.tibco.bpm.cdm.libs.dql.exception.UnknownAttributeException;
import com.tibco.bpm.cdm.libs.dql.exception.UnknownDataTypeException;
import com.tibco.bpm.cdm.libs.dql.model.DataFieldProvider;
import com.tibco.bpm.cdm.libs.dql.model.ModelAbstractType;
import com.tibco.bpm.cdm.libs.dql.model.ModelAttribute;
import com.tibco.bpm.cdm.libs.dql.model.ModelStructuredType;

/**
 * DQL parser {@link ModelStructuredType} wrapper for a property of a given BOM Class (or top level case class)
 *
 * @author aallway
 * @since 5 Aug 2024
 */
@SuppressWarnings("nls")
public class DQLClassPropertyWrapper implements ModelStructuredType
{
	/**
	 * The wrapped BOM Class
	 */
	private Class					clazz;

	private DataFieldProvider		dataFieldProvider;

	/**
	 * Child attribute cache (Load on demand)
	 */
	private List<ModelAttribute>	attributesCache			= null;

	/**
	 * The DQL case-class relative path to property with this BOM class (or <code>null</code> if this is a wrapper for
	 * top level class)
	 */
	private String					propertyPath;

	/**
	 * A pattern for extracting index from "attribute[123]" (=123)
	 */
	private static Pattern			ATTRIB_INDEX_PATTERN	= Pattern.compile("\\[(.*?)\\]");

	/**
	 * Create a DQL parser {@link ModelStructuredType} wrapper for BOM Class
	 * 
	 * @param clazz
	 * @param dataFieldProvider
	 */
	public DQLClassPropertyWrapper(Class clazz, DataFieldProvider dataFieldProvider)
	{
		this(clazz, null, dataFieldProvider);
	}

	/**
	 * Create a DQL parser {@link ModelStructuredType} wrapper for BOM Class
	 * 
	 * @param clazz
	 * @param propertyPath
	 *            The DQL case-class relative path to property with this BOM class
	 * @param dataFieldProvider
	 */
	public DQLClassPropertyWrapper(Class clazz, String propertyPath, DataFieldProvider dataFieldProvider)
	{
		super();
		this.clazz = clazz;

		this.propertyPath = propertyPath;
		this.dataFieldProvider = dataFieldProvider;
	}

	/**
	 * @see com.tibco.bpm.cdm.libs.dql.model.ModelStructuredType#getName()
	 *
	 * @return
	 */
	@Override
	public String getName()
	{
		return clazz.getName();
	}

	/**
	 * @see com.tibco.bpm.cdm.libs.dql.model.ModelStructuredType#getAttributes()
	 *
	 * @return
	 */
	@Override
	public List<ModelAttribute> getAttributes()
	{
		/* Cache attribs if not already cached */
		cacheAttributes();

		return attributesCache;
	}

	/**
	 * DQL-interface-Wrap and cache the child attributes of this BOM class if not already cached.
	 */
	private void cacheAttributes()
	{
		if (attributesCache == null)
		{
			attributesCache = new ArrayList<>();

			for (Property property : clazz.getOwnedAttributes())
			{
				attributesCache.add(new DQLPropertyWrapper(property, this, dataFieldProvider));
			}
		}
	}

	/**
	 * @see com.tibco.bpm.cdm.libs.dql.model.ModelStructuredType#getAttribute(java.lang.String)
	 *
	 * @param attributePath
	 * @return
	 * @throws DQLException
	 */
	@Override
	public ModelAttribute getAttribute(String attributePath) throws DQLException
	{
		return recursiveResolveAttribute(attributePath, attributePath, clazz.getQualifiedName());
	}

	/**
	 * Recursively resolve each element of the path. Aray attribute Index specifications are also validated as required.
	 * 
	 * @param wholeAttributePath
	 *            The entire original attribute path
	 * @param remainingAttributePath
	 *            The remaining attribute path to be processed.
	 * @param parentElementName
	 *            The name to use for parent element exceptions.
	 * 
	 * @return DQL {@link ModelAttribute} if attribute found and path specified is valid, else throws appropriate
	 *         exception.
	 * 
	 * @throws DQLException
	 */
	private ModelAttribute recursiveResolveAttribute(String wholeAttributePath, String remainingAttributePath,
			String parentTypeName) throws DQLException
	{
		/*
		 * Grab the first attribute from path, this should be an attribute in THIS structured-type
		 */
		int end1stAttrib = remainingAttributePath.indexOf('.');

		String attributeName = null;
		boolean isLastPathElement;

		// remainingAttributePath.indexOf('.');
		if (end1stAttrib >= 0)
		{
			attributeName = remainingAttributePath.substring(0, end1stAttrib);
			isLastPathElement = false;
		}
		else
		{
			attributeName = remainingAttributePath;
			isLastPathElement = true;
		}

		/*
		 * Find the attribute in our children
		 */

		/* Cache attribs if not already cached */
		cacheAttributes();

		for (ModelAttribute attribute : attributesCache)
		{
			if (attribute.getName().equals(attributeName))
			{
				/*
				 * If we have not reached the leaf-node in the path then, recurs into the attribute's class to find next
				 * component
				 */
				if (!isLastPathElement)
				{
					ModelAbstractType type = attribute.getType();

					String restOfPath = remainingAttributePath.substring(end1stAttrib + 1);

					if (type instanceof DQLClassPropertyWrapper)
					{
						return ((DQLClassPropertyWrapper) type).recursiveResolveAttribute(wholeAttributePath,
								restOfPath, ((DQLClassPropertyWrapper) type).clazz.getQualifiedName());
					}
					else
					{
						/*
						 * There are child attributes on this attribute on path but it is not a child attribute. So must
						 * be a non-existent property
						 */
						UnknownAttributeException exc = new UnknownAttributeException();
						exc.setParentName(type.getName()); // This attribute data type
						exc.setAttributePath(wholeAttributePath);
						exc.setAttributeName(restOfPath); // Does not have these children

						throw exc;
					}
				}
				else
				{
					/*
					 * This is the leaf-node attribute at end of path, so return it back up thru recursions (if any) or
					 * original consumer.
					 */
					return attribute;
				}
			}
		}

		/* Attribute not found. */
		if (isLastPathElement)
		{
			UnknownAttributeException exc = new UnknownAttributeException();
			exc.setParentName(parentTypeName);
			exc.setAttributePath(wholeAttributePath);
			exc.setAttributeName(attributeName);
			throw exc;
		}
		else
		{
			UnknownDataTypeException exc = new UnknownDataTypeException();
			exc.setParentName(parentTypeName);
			exc.setAttributePath(remainingAttributePath);
			exc.setAttributeName(attributeName);
			throw exc;
		}
	}

	/**
	 * The DQL case-class relative path to property with this BOM class
	 * 
	 * @return the propertyPath to this type's context property or <code>null</code> if this is top level class
	 */
	public String getPropertyPath()
	{
		return propertyPath;
	}

	/**
	 * @see java.lang.Object#toString()
	 *
	 * @return
	 */
	@Override
	public String toString()
	{
		return (propertyPath != null ? (propertyPath + "::" + getName()) : getName());
	}
}
