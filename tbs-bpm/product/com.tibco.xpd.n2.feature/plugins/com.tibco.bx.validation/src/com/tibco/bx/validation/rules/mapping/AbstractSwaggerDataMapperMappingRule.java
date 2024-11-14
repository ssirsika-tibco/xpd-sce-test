/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/
package com.tibco.bx.validation.rules.mapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ITreeContentProvider;

import com.tibco.xpd.analyst.resources.xpdl2.utils.BasicTypeConverterFactory;
import com.tibco.xpd.datamapper.api.DataMapperUtils;
import com.tibco.xpd.datamapper.infoProviders.ContributableDataMapperInfoProvider;
import com.tibco.xpd.datamapper.infoProviders.WrappedContributedContent;
import com.tibco.xpd.datamapper.rules.AbstractDataMapperMappingRule;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.RestMapperTreeItem;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger.SwaggerMapperTreeItem;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger.SwaggerParamTreeItem;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger.SwaggerPropertyType;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger.SwaggerResponseContainerTreeItem;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger.SwaggerTypedTreeItem;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger.UnsupportedSwaggerTreeItem;
import com.tibco.xpd.mapper.MapperContentProvider;
import com.tibco.xpd.mapper.Mapping;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.rsd.ParameterStyle;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.globaldata.CaseUMLScriptRelevantData;
import com.tibco.xpd.validation.bpmn.rules.baserules.BasicTypeHandler;
import com.tibco.xpd.validation.bpmn.rules.baserules.JavaScriptTypeCompatibilityResult;
import com.tibco.xpd.validation.bpmn.rules.baserules.MappingTypeCompatibility;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Length;
import com.tibco.xpd.xpdl2.Xpdl2Factory;

/**
 * Abstract class to provide common functions related to swagger data mapper
 * mapping rules. Jira : ACE-8261
 * 
 * @author ssirsika
 * @since 04 Oct 2024
 */
public abstract class AbstractSwaggerDataMapperMappingRule extends AbstractDataMapperMappingRule {

	private static final String SWAGGER_ARRAY_PARAM_NOT_SUPPORTED_ERR_CODE = "ace.swaggerArrayParamNotSupported"; //$NON-NLS-1$
	private static final String SWAGGER_UNSUPPORTED_REQUEST_PAYLOAD_ERR_CODE = "ace.unsupportedInputPayloadContent"; //$NON-NLS-1$
	private static final String SWAGGER_UNSUPPORTED_RESPONSE_PAYLOAD_ERR_CODE = "ace.unsupportedOutputPayloadContent"; //$NON-NLS-1$
	private ContributableDataMapperInfoProvider sourceInfoProvider;
	private ContributableDataMapperInfoProvider targetInfoProvider;
	
	/**
	 * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractActivityMappingJavaScriptRule#checkJavaScriptTypeCompatibility(java.lang.Object,
	 *      java.lang.Object, java.lang.Object)
	 *
	 * @param sourceObjectInTree
	 * @param targetObjectInTree
	 * @param mapping
	 * @return
	 */
	@Override
	protected JavaScriptTypeCompatibilityResult checkJavaScriptTypeCompatibility(Object sourceObjectInTree,
			Object targetObjectInTree, Object mapping) {

		boolean isLikeMapping = isLikeMapping(mapping);

		if (isLikeMapping) {
			return JavaScriptTypeCompatibilityResult.HANDLED_SCENARIO_CHECK_SUCCEEDED;
		}

		Object unwrappedSource = getUnwrappedSource(sourceObjectInTree);
		Object unwrappedTarget = getUnwrappedTarget(targetObjectInTree);

		JavaScriptTypeCompatibilityResult compatible = super.checkJavaScriptTypeCompatibility(unwrappedSource,
				unwrappedTarget, mapping);

		if (JavaScriptTypeCompatibilityResult.UNHANDLED_SCENARIO.equals(compatible)) {
			SwaggerTypedTreeItem swaggerTypedTreeItem = null;
			ConceptPath conceptPath = null;
			if (unwrappedTarget instanceof SwaggerTypedTreeItem) {
				swaggerTypedTreeItem = (SwaggerTypedTreeItem) unwrappedTarget;
			} else if (unwrappedSource instanceof SwaggerTypedTreeItem) {
				swaggerTypedTreeItem = (SwaggerTypedTreeItem) unwrappedSource;
			}

			if (unwrappedTarget instanceof ConceptPath) {
				conceptPath = (ConceptPath) unwrappedTarget;
			} else if (unwrappedSource instanceof ConceptPath) {
				conceptPath = (ConceptPath) unwrappedSource;
			}

			if (typesAreSupported(conceptPath, swaggerTypedTreeItem)) {

				DirectionType direction = getMappingDirection().equals(MappingDirection.IN) ? DirectionType.IN_LITERAL
						: DirectionType.OUT_LITERAL;

				MappingTypeCompatibility result = checkTypeCompatibility(conceptPath, swaggerTypedTreeItem, direction);

				if (MappingTypeCompatibility.WRONGTYPE.equals(result)) {
					compatible = JavaScriptTypeCompatibilityResult.HANDLED_SCENARIO_CHECK_FAILED;
				} else {
					compatible = JavaScriptTypeCompatibilityResult.HANDLED_SCENARIO_CHECK_SUCCEEDED;
				}

			} else if (unwrappedSource instanceof ScriptInformation) {

				IScriptRelevantData returnType = getCachedScriptReturnType(mapping);
				if (returnType != null && returnType.getType() != null
						&& !returnType.getType().equals(JsConsts.UNDEFINED_DATA_TYPE)) {
					MappingTypeCompatibility result = checkScriptTypeCompatability(swaggerTypedTreeItem, conceptPath,
							returnType);
					if (MappingTypeCompatibility.WRONGTYPE.equals(result)) {
						compatible = JavaScriptTypeCompatibilityResult.HANDLED_SCENARIO_CHECK_FAILED;
					} else {
						compatible = JavaScriptTypeCompatibilityResult.HANDLED_SCENARIO_CHECK_SUCCEEDED;
					}
				}

			}
		}
		return compatible;

	}


	/**
	 * Perform additional validations related to external references
	 */
	@Override
	protected void performAdditionalMappingValidation(Object mapping, Object sourceObjectInTree,
			Object targetObjectInTree)
	{
		super.performAdditionalMappingValidation(mapping, sourceObjectInTree, targetObjectInTree);
		Object unwrappedObj = getMappingDirection().equals(MappingDirection.IN) ? getUnwrappedTarget(targetObjectInTree)
				: getUnwrappedSource(sourceObjectInTree);
		if (unwrappedObj instanceof SwaggerTypedTreeItem)
		{
			SwaggerTypedTreeItem swaggerTypedTreeItem = (SwaggerTypedTreeItem) unwrappedObj;

			/*
			 * Sid ACE-8885 External reference is now handled within SwaggerTypedTreeItem as a
			 * SwaggerProeprtyType.EXTERNALREF. so is handled as general part of checking the Swagger tree items instead
			 * of here as an UnsupportedSwaggerTreeItem.
			 */

			/*
			 * Validated agaisnt array path/query/header param items.
			 */
			if (swaggerTypedTreeItem.isArray() && swaggerTypedTreeItem instanceof SwaggerParamTreeItem)
			{
				SwaggerParamTreeItem treeItem = (SwaggerParamTreeItem) swaggerTypedTreeItem;
				// ACE-8751 : Validate against Array query, path and header Swagger service parameters
				boolean isInvalid = treeItem.getParamStyle().equals(ParameterStyle.PATH)
						|| treeItem.getParamStyle().equals(ParameterStyle.QUERY)
						|| treeItem.getParamStyle().equals(ParameterStyle.HEADER);
				if (isInvalid)
				{
					addIssue(SWAGGER_ARRAY_PARAM_NOT_SUPPORTED_ERR_CODE, getModelObjectForMapping(mapping),
							createMessageList(),
							getDirectionSpecificMappingIssueAdditionalInfo(mapping));
				}
			}
		}
	}


	/**
	 * @param mapping
	 * 
	 * @return Issue additional info dependent which end of a mapping the swagger tree item should be
	 */
	private Map<String, String> getDirectionSpecificMappingIssueAdditionalInfo(Object mapping)
	{
		Map<String, String> additionalInfo;
		if (MappingDirection.IN.equals(getMappingDirection()))
		{
			additionalInfo = createAdditionalInfo(
					MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO,
					getTargetPath(targetInfoProvider, mapping));
		}
		else
		{
			additionalInfo = createAdditionalInfo(MapperContentProvider.DATAMAPPING_SOURCE_URI_ISSUEINFO,
					getSourcePath(sourceInfoProvider, mapping));
		}
		return additionalInfo;
	}

	/**
	 * @param objectPath
	 * 
	 * @return Issue additional info dependent which end of a mapping the swagger tree item should be
	 */
	private Map<String, String> getDirectionSpecificIssueAdditionalInfo(String objectPath)
	{
		Map<String, String> additionalInfo;
		if (MappingDirection.IN.equals(getMappingDirection()))
		{
			additionalInfo = createAdditionalInfo(MapperContentProvider.TARGET_URI_ISSUEINFO,
					objectPath);
		}
		else
		{
			additionalInfo = createAdditionalInfo(MapperContentProvider.SOURCE_URI_ISSUEINFO,
					objectPath);
		}
		return additionalInfo;
	}

	/**
	 * Check for the script type compatibility between script return type and either {@link SwaggerParamTreeItem} or
	 * {@link ConceptPath} as target.
	 * 
	 * @param swaggerTypedTreeItem
	 *            possible target
	 * @param conceptPath
	 *            possible target
	 * @param returnType
	 *            Return type
	 * 
	 * @return the result as to whether the mapping source and target types are compatible or not.
	 */
	private MappingTypeCompatibility checkScriptTypeCompatability(SwaggerTypedTreeItem swaggerTypedTreeItem,
			ConceptPath conceptPath, IScriptRelevantData returnType) {

		/*
		 * First of all, make sure that we aren't mapping a case ref.
		 */
		if (returnType instanceof CaseUMLScriptRelevantData) {

			/*
			 * If that's the case, then move on to check their compatibility.
			 */
			return MappingTypeCompatibility.WRONGTYPE;
		}

		/**
		 * Sid XPD-7925 Should be able to map from user defined scripts that return
		 * "null;"
		 */
		if (JsConsts.NULL.equals(returnType.getType())) {
			return MappingTypeCompatibility.OK;
		}

		// If concept path is not null then treat it as a target , otherwise treat swaggerTypedTreeItem as a target.
		if (conceptPath != null) {
			return N2ProcessDataMappingCompatibilityUtil.checkTypeCompatibility(returnType, conceptPath);
		} else if (swaggerTypedTreeItem != null) {

			Object targetDataType = createBasicType(swaggerTypedTreeItem);

			if (targetDataType instanceof BasicType) {
				return N2ProcessDataMappingCompatibilityUtil.checkTypeCompatibility(returnType,
						(BasicType) targetDataType);
			}
		}

		return MappingTypeCompatibility.WRONGTYPE;
	}

	/**
	 * Check compatibility of the to process data concept path with swagger tree
	 * item.
	 * 
	 * @param conceptPath          {@link ConceptPath}
	 * @param swaggerTypedTreeItem {@link SwaggerTypedTreeItem}
	 * @param directionType
	 * 
	 * @return OK, WRONGTYPE or WRONGSIZE
	 */
	protected MappingTypeCompatibility checkTypeCompatibility(ConceptPath conceptPath,
			SwaggerTypedTreeItem swaggerTypedTreeItem, DirectionType directionType) {

		MappingTypeCompatibility match = MappingTypeCompatibility.WRONGTYPE;

		Object conceptPathType = BasicTypeConverterFactory.INSTANCE.getBaseType(conceptPath.getItem(), true);
		BasicType swaggerBasicType = createBasicType(swaggerTypedTreeItem);

		if (conceptPathType instanceof BasicType && swaggerBasicType instanceof BasicType) {
			BasicType sourceType = DirectionType.IN_LITERAL.equals(directionType) ? (BasicType) conceptPathType : swaggerBasicType;
			BasicType targetType = DirectionType.IN_LITERAL.equals(directionType) ? swaggerBasicType : (BasicType) conceptPathType;
		    
			// Mapping of basic type to String is allowed.
			if(BasicTypeType.STRING_LITERAL.equals(targetType.getType())) {
				return MappingTypeCompatibility.OK;
			}
			
		    return BasicTypeHandler.handleSourceTargetBasicTypes(sourceType, targetType, directionType);
		}

		return match;
	}

	/**
	 * @param wcc The wrapped content.
	 * @return The concept path representing the wrapped object.
	 */
	protected ConceptPath getConceptPath(WrappedContributedContent wcc) {
		ConceptPath cp = null;
		Object contributed = wcc.getContributedObject();
		if (contributed instanceof ConceptPath) {
			cp = (ConceptPath) contributed;
		}
		return cp;
	}
	
	/**
	 *@see AbstractSwaggerDataMapperMappingRule.performAdditionalMappingsValidation
	 */
	@Override
	protected void performAdditionalMappingsValidation(Object objectToValidate, Collection<Object> mappings) {
		super.performAdditionalMappingsValidation(objectToValidate, mappings);
		// Check elements in the source and target mapping
		checkTreeElement(targetInfoProvider.getContentProvider(), objectToValidate);
		checkTreeElement(sourceInfoProvider.getContentProvider(), objectToValidate);
	}
	

	
    /**
     * Check tree elements by using passed contentProvider and base object to validate.
     * @param contentProvider Content provider to retrieve the contents
     * @param objectToValidate object to validate
     */
    private void checkTreeElement(ITreeContentProvider contentProvider, Object objectToValidate) {
    	Object[] elements = contentProvider.getElements(objectToValidate);
    	if (elements == null) {
            return;
        }
    	List<Object> treeItems = new ArrayList<>();
    	for (Object object : elements) {
    		
			if(object instanceof WrappedContributedContent) {
				Object contributedObject = ((WrappedContributedContent) object).getContributedObject();
				treeItems.add(contributedObject);
			}
		}
    	// Recursively check the elements 
    	recursiveCheckItems(treeItems);
	}

	/**
	 * Recursively check passed tree items for compatiblity check.
	 * 
	 * @param treeItems
	 *            List of tree items to check
	 * @return {@link JavaScriptTypeCompatibilityResult}
	 */
	private void recursiveCheckItems(List treeItems)
	{
		/*
		 * Sid ACE-8885 All show each problem (now that we have a single Unsupported mechanism and cover more types of
		 * issue. So just track 'any failed' here and keep going with the rest of the siblings.
		 */

		if (treeItems != null) {
			for (Object item : treeItems) {
				if (item instanceof RestMapperTreeItem) {
					RestMapperTreeItem restMapperTreeItem = (RestMapperTreeItem) item;

					JavaScriptTypeCompatibilityResult itemResult = JavaScriptTypeCompatibilityResult.HANDLED_SCENARIO_CHECK_SUCCEEDED;

					// Check for Unsupported item
					if (restMapperTreeItem instanceof UnsupportedSwaggerTreeItem)
					{
						UnsupportedSwaggerTreeItem unsupportedItem = (UnsupportedSwaggerTreeItem) restMapperTreeItem;

						/*
						 * Sid ACE-8885 We now use tree content to figure out if is response item. Also provide
						 * additional info to get the problem marker placed on the actual tree item.
						 */
						SwaggerResponseContainerTreeItem responseTreeItem = getSwaggerResponseContainerTreeItem(
								unsupportedItem);

						if (responseTreeItem != null)
						{
							// If it's a unsupported response body
							// Add issue with 'response code' and 'issue text'.
							addIssue(SWAGGER_UNSUPPORTED_RESPONSE_PAYLOAD_ERR_CODE, restMapperTreeItem.getActivity(),
									createMessageList(responseTreeItem.getStatusCode(), unsupportedItem.getText()),
									getDirectionSpecificIssueAdditionalInfo(unsupportedItem.getPath()));
							itemResult = JavaScriptTypeCompatibilityResult.HANDLED_SCENARIO_CHECK_FAILED;
						}
						else
						{
							// Add issue with 'issue text'
							addIssue(SWAGGER_UNSUPPORTED_REQUEST_PAYLOAD_ERR_CODE, restMapperTreeItem.getActivity(),
									createMessageList(unsupportedItem.getText()),
									getDirectionSpecificIssueAdditionalInfo(unsupportedItem.getPath()));
							itemResult = JavaScriptTypeCompatibilityResult.HANDLED_SCENARIO_CHECK_FAILED;
						}
					}
					/*
					 * Sid ACE-8885 Some kinds of unsupported content items are now handled within SwaggerTypeTreeItem
					 * itself (OneOf/AllOf/AnyOf/Not, external ref, array of arrays etc). So if we find one then raise
					 * an error for it.
					 */
					else if (restMapperTreeItem instanceof SwaggerTypedTreeItem
							&& ((SwaggerTypedTreeItem) restMapperTreeItem).getType().isUnsupported())
					{
						SwaggerTypedTreeItem swaggerTypedTreeItem = (SwaggerTypedTreeItem) restMapperTreeItem;

						SwaggerResponseContainerTreeItem responseTreeItem = getSwaggerResponseContainerTreeItem(
								swaggerTypedTreeItem);

						if (responseTreeItem != null)
						{
							// If it's a unsupported response body
							// Add issue with 'response code' and 'issue text'.
							addIssue(SWAGGER_UNSUPPORTED_RESPONSE_PAYLOAD_ERR_CODE, restMapperTreeItem.getActivity(),
									createMessageList(responseTreeItem.getStatusCode(),
											swaggerTypedTreeItem.getUnsupportedTypeReason()),
									getDirectionSpecificIssueAdditionalInfo(swaggerTypedTreeItem.getPath()));
							itemResult = JavaScriptTypeCompatibilityResult.HANDLED_SCENARIO_CHECK_FAILED;
						}
						else
						{
							// Add issue with 'issue text'
							addIssue(SWAGGER_UNSUPPORTED_REQUEST_PAYLOAD_ERR_CODE, restMapperTreeItem.getActivity(),
									createMessageList(swaggerTypedTreeItem.getUnsupportedTypeReason()),
									getDirectionSpecificIssueAdditionalInfo(swaggerTypedTreeItem.getPath()));
							itemResult = JavaScriptTypeCompatibilityResult.HANDLED_SCENARIO_CHECK_FAILED;
						}

					}

					// Recursively check the child items (unless we already failed this item
					if (!JavaScriptTypeCompatibilityResult.HANDLED_SCENARIO_CHECK_FAILED.equals(itemResult))
					{
						recursiveCheckItems(restMapperTreeItem.getChildren());
					}
				}
			}
		}
	}

	/**
	 * Recursively traverses up the parent hierarchy to find the first instance of SwaggerResponseContainerTreeItem. If
	 * found, returns the SwaggerResponseContainerTreeItem; otherwise, returns null.
	 *
	 * @param item
	 *            the starting SwaggerMapperTreeItem from which to begin the traversal
	 * @return the found SwaggerResponseContainerTreeItem, or null if not found in the hierarchy
	 */
	private SwaggerResponseContainerTreeItem getSwaggerResponseContainerTreeItem(SwaggerMapperTreeItem item)
	{
		if (item != null)
		{
			if (item instanceof SwaggerResponseContainerTreeItem)
			{
				return (SwaggerResponseContainerTreeItem) item;
			}
			return getSwaggerResponseContainerTreeItem(item.getParent());
		}
		return null;
	}

	/**
	 * @see com.tibco.xpd.datamapper.rules.AbstractDataMapperMappingRule#createSourceInfoProvider(org.eclipse.emf.ecore.EObject)
	 * 
	 * @param objectToValidate
	 * @return
	 */
    @Override
    protected ContributableDataMapperInfoProvider createSourceInfoProvider(EObject objectToValidate) {
        sourceInfoProvider = super.createSourceInfoProvider(objectToValidate);

        return sourceInfoProvider;
    }

    /**
     * @see com.tibco.xpd.datamapper.rules.AbstractDataMapperMappingRule#createTargetInfoProvider(org.eclipse.emf.ecore.EObject)
     * 
     * @param objectToValidate
     * @return
     */
    @Override
    protected ContributableDataMapperInfoProvider createTargetInfoProvider(EObject objectToValidate) {
        targetInfoProvider = super.createTargetInfoProvider(objectToValidate);

        return targetInfoProvider;
    }

	/**
	 * Let the subclass to decide how to unwrap the passed source and return it.
	 * 
	 * @param source
	 * @return
	 */
	abstract Object getUnwrappedSource(Object source);

	/**
	 * Let the subclass to decide how to unwrap the passed target and return it.
	 * 
	 * @param target
	 * @return
	 */
	abstract Object getUnwrappedTarget(Object target);

	/**
	 * Create a {@link BasicType} from passed {@link SwaggerTypedTreeItem}'s type 
	 * @param swaggerTypedTreeItem Swagger item from which basic type need to derived.
	 * @return {@link BasicType}
	 */
	private BasicType createBasicType(SwaggerTypedTreeItem swaggerTypedTreeItem) {
		BasicTypeType basicTypeType = null;
		BasicType resultType = null;
		Integer length = null;

		SwaggerPropertyType swaggerType = swaggerTypedTreeItem.getType();
		if (SwaggerPropertyType.BOOLEAN.equals(swaggerType)) {
			basicTypeType = BasicTypeType.BOOLEAN_LITERAL;
		} else if (SwaggerPropertyType.DATE.equals(swaggerType)) {
			basicTypeType = BasicTypeType.DATE_LITERAL;
		} else if (SwaggerPropertyType.DATETIME.equals(swaggerType)) {
			basicTypeType = BasicTypeType.DATETIME_LITERAL;
		} else if (SwaggerPropertyType.STRING.equals(swaggerType)) {
			basicTypeType = BasicTypeType.STRING_LITERAL;
			length = swaggerTypedTreeItem.getMaxLength();
		} else if (SwaggerPropertyType.INTEGER.equals(swaggerType)) {
			basicTypeType = BasicTypeType.INTEGER_LITERAL;
		} else if (SwaggerPropertyType.NUMBER.equals(swaggerType)) {
			basicTypeType = BasicTypeType.FLOAT_LITERAL;
		}
		
		if (basicTypeType != null) {
			 resultType = Xpdl2Factory.eINSTANCE.createBasicType();
			 resultType.setType(basicTypeType);

            if (length != null && length > 0) {
                Length len = Xpdl2Factory.eINSTANCE.createLength();
                len.setValue(length.toString());

                resultType.setLength(len);
            }
            resultType.setType(basicTypeType);
        }
		return resultType;
	}
	
	/**
	 * @param mapping
	 * @return <code>true</code> if mapping is a 'like mapping'
	 */
	private boolean isLikeMapping(Object mapping) {
		// If it's a 'like' mapping skip type compatibility check.
		boolean isLikeMapping = false;
		if (mapping instanceof Mapping) {
			Mapping m = (Mapping) mapping;
			if (m.isEditable() && DataMapperUtils.isLikeMapping((Mapping) mapping)) {
				isLikeMapping = true;
			}
		}
		return isLikeMapping;
	}

	/**
	 * Check that the data types are set and supported of {@link ConceptPath} and {@link SwaggerTypedTreeItem}.
	 * 
	 * Swagger types may not be supported for a number of reasons. But in general we don't bother type-compatibility
	 * checking them because other parts of the code will complain if they are used in mappings.
	 * 
	 * @param conceptPath
	 *            {@link ConceptPath}
	 * @param treeItem
	 *            {@link SwaggerTypedTreeItem}
	 * @return <code>true</code> if concept path has non-null item as well as has non-null and supported swagger tree
	 *         item.
	 */
	private boolean typesAreSupported(ConceptPath conceptPath, SwaggerTypedTreeItem treeItem)
	{
		if (conceptPath == null || conceptPath.getItem() == null)
		{
			return false;
		}

		if (treeItem == null || treeItem.getType() == null)
		{
			return false;
		}

		/*
		 * Sid ACE-8885 Some Tree items constructs that are now handled as SwaggerTypedTreeItems with type = unsupported
		 * / externalref
		 */
		SwaggerPropertyType type = treeItem.getType();

		if (type.isUnsupported())
		{
			return false;
		}
		return true;
	}
}
