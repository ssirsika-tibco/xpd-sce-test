/*
 * Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
 */

package com.tibco.bds.designtime.api.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Property;

import com.tibco.bpm.cdm.libs.dql.exception.ALLIndexNotSupportedException;
import com.tibco.bpm.cdm.libs.dql.exception.BadlyFormattedPathExcpetion;
import com.tibco.bpm.cdm.libs.dql.exception.DQLException;
import com.tibco.bpm.cdm.libs.dql.exception.IndexNotSupportedForSizeFunctionException;
import com.tibco.bpm.cdm.libs.dql.exception.InvalidIndexException;
import com.tibco.bpm.cdm.libs.dql.exception.MultiValuedAttributeMissingIndexException;
import com.tibco.bpm.cdm.libs.dql.exception.SingleValuedAttributeIndexException;
import com.tibco.bpm.cdm.libs.dql.exception.SpuriousTextAfterBracketException;
import com.tibco.bpm.cdm.libs.dql.exception.TagNotSupportedException;
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
		return getAttribute(attributePath, true);
	}

	/**
	 * @see com.tibco.bpm.cdm.libs.dql.model.ModelStructuredType#getAttribute(java.lang.String, boolean)
	 *
	 * @param attributePath
	 * @param checkIndex
	 *            <code>true</code> means ensure that index is valid (must be integer number), but as we are validating
	 *            equery at design-time it is assumed that we shoul ALWAYS do this regardless
	 * 
	 * @return
	 * @throws MultiValuedAttributeMissingIndexException
	 * @throws SingleValuedAttributeIndexException
	 */
	@Override
	public ModelAttribute getAttribute(String attributePath, boolean checkIndex) throws DQLException
	{

		return recursiveResolveAttribute(attributePath, attributePath, checkIndex, clazz.getQualifiedName());
	}

	/*
	 * Recursively resolve each element of the path. Aray attribute Index specifications are also validated as required.
	 * 
	 * @param wholeAttributePath The entire original attribute path
	 * 
	 * @param remainingAttributePath The remaining attribute path to be processed.
	 * 
	 * @param checkIndex Whether Index is expected for array attribute
	 * 
	 * @param parentElementName The name to use for parent element exceptions.
	 * 
	 * @return DQL {@link ModelAttribute} if attribute found and path specified is valid, else throws appropriate
	 * exception.
	 * 
	 * @throws DQLException
	 */
	private ModelAttribute recursiveResolveAttribute(String wholeAttributePath, String remainingAttributePath,
			boolean checkIndex, String parentTypeName) throws DQLException
	{
		/*
		 * Grab the first attribute from path, this should be an attribute in THIS structured-type
		 */
		int end1stAttrib;

		/*
		 * Need to also take "arrayAttrib[${data.field.number}].attrib" into account. So get first element in path
		 * discounting any "." character between [ ] IF that index specification is part of the first path element.
		 */
		int endArrayBracket = -1;
		int firstArrayBracket = remainingAttributePath.indexOf('[');

		if (firstArrayBracket >= 0)
		{
			endArrayBracket = remainingAttributePath.indexOf(']');

			if (endArrayBracket <= firstArrayBracket)
			{
				BadlyFormattedPathExcpetion exc = new BadlyFormattedPathExcpetion(); // Urk, no terminating ]
				exc.setAttributePath(wholeAttributePath);
				exc.setAttributeName(remainingAttributePath);
				throw exc;
			}
		}

		/*
		 * If the first [ is before the first '.', then array index is part of 1st path element. So end of first path
		 * element is first '.' after the first ]
		 */
		int firstPeriod = remainingAttributePath.indexOf(".");

		if (firstArrayBracket > 0 && firstArrayBracket < firstPeriod)
		{
			// End of first path element is after [index]
			end1stAttrib = endArrayBracket + 1 + remainingAttributePath.substring(endArrayBracket + 1).indexOf('.');
		}
		else
		{
			// End of 1st attribute is before first index [
			end1stAttrib = remainingAttributePath.indexOf('.');
		}

		String firstPathComponent = null;
		boolean isLastPathElement;

		// remainingAttributePath.indexOf('.');
		if (end1stAttrib >= 0)
		{
			firstPathComponent = remainingAttributePath.substring(0, end1stAttrib);
			isLastPathElement = false;
		}
		else
		{
			firstPathComponent = remainingAttributePath;
			isLastPathElement = true;
		}

		/*
		 * Extract the attribute name and [index] value if there is one.
		 */
		String attributeName = null;

		boolean hasAttributeIndex = false;

		Matcher attribIndexMatcher = ATTRIB_INDEX_PATTERN.matcher(firstPathComponent);

		if (attribIndexMatcher.find())
		{
			attributeName = firstPathComponent.substring(0, firstPathComponent.indexOf('['));

			if (checkIndex == false && isLastPathElement)
			{
				/*
				 * If this is a "size(arrayAttribute)" type expression, then checkExists will be false, and the last
				 * element should NOT have index specified.
				 */
				IndexNotSupportedForSizeFunctionException exc = new IndexNotSupportedForSizeFunctionException();
				exc.setAttributePath(wholeAttributePath);
				exc.setAttributeName(attributeName);
				throw exc;
			}

			String indexString = attribIndexMatcher.group(1);

			int end = attribIndexMatcher.end();

			if (end != (firstPathComponent.length()))
			{
				/*
				 * There was a match for [xxx] but it was not at end of path component (i.e. "abc[123]garbage" -
				 * therefore invalid)
				 */
				SpuriousTextAfterBracketException exc = new SpuriousTextAfterBracketException();
				exc.setAttributePath(remainingAttributePath);
				exc.setAttributeName(attributeName);
				throw exc;
			}
			else
			{
				try
				{
					hasAttributeIndex = true;

					Integer attributeIndex = Integer.valueOf(indexString);
				}
				catch (NumberFormatException e)
				{
					/* There was [xxx] and end of path component, but it was not an integer number. */
					if ("ALL".equals(indexString))
					{
						ALLIndexNotSupportedException exc = new ALLIndexNotSupportedException();
						exc.setAttributePath(remainingAttributePath);
						exc.setAttributeName(attributeName);
						throw exc;
					}
					else if (indexString.startsWith("$"))
					{
						/*
						 * Check for index specified as ${data.field} reference - ignore if so, because DQLParser will
						 * validate field type on return.
						 */
						if (!indexString.startsWith("${"))
						{
							TagNotSupportedException exc = new TagNotSupportedException();
							exc.setAttributePath(remainingAttributePath);
							exc.setAttributeName(attributeName);
							throw exc;
						}
					}
					else
					{
						InvalidIndexException exc = new InvalidIndexException();
						exc.setAttributePath(remainingAttributePath);
						exc.setAttributeName(attributeName);
						throw exc;
					}
				}
			}
		}
		else
		{
			/* There was no attribute index specification */
			attributeName = firstPathComponent;
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
				/* Validate index-is-provided with whether the attribute is an array or not. */
				if (attribute.isArray())
				{
					if (!hasAttributeIndex)
					{
						/*
						 * Array attributes MUST have index specified UNLESS this is a "size(arrayAttribute)" type
						 * expression, indicated by checkExists=false. In that case the LAST element should NOT have
						 * index specified.
						 * 
						 * So if we have an array without index specified and we are checking all arrays have index
						 * (checkIndex=true) then complain.
						 * 
						 * OR if we've been told last element should be an array without index (checkIndex=false), then
						 * only complain if not last element in path.
						 */
						if (checkIndex || (!checkIndex && !isLastPathElement))
						{
							MultiValuedAttributeMissingIndexException exc = new MultiValuedAttributeMissingIndexException();
							exc.setAttributePath(remainingAttributePath);
							exc.setAttributeName(attributeName);
							throw exc;
						}
					}
				}
				else
				{
					/* Non-array attribute MUST NOT have index specified. */
					if (hasAttributeIndex)
					{
						SingleValuedAttributeIndexException exc = new SingleValuedAttributeIndexException();
						exc.setAttributePath(remainingAttributePath);
						exc.setAttributeName(attributeName);
						throw exc;
					}
				}

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
								restOfPath, checkIndex, ((DQLClassPropertyWrapper) type).clazz.getQualifiedName());
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
