/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.bx.validation.rules.mapping;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.datamapper.infoProviders.WrappedContributedContent;
import com.tibco.xpd.datamapper.scripts.IScriptDataMapperProvider;
import com.tibco.xpd.implementer.resources.xpdl2.properties.RestServiceTaskAdapter;
import com.tibco.xpd.implementer.resources.xpdl2.properties.RestServiceTaskAdapter.RsoType;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.processeditor.xpdl2.util.RestServiceTaskUtil;
import com.tibco.xpd.rest.datamapper.RestDataMapperConstants;
import com.tibco.xpd.rest.datamapper.RestScriptDataMapperProvider;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Process Data to Swagger Mapping rule. Jira : ACE-8261
 * 
 * @author ssirsika
 * @since 01 Oct 2024
 */
public class ProcessDataToSwaggerJsRule extends AbstractSwaggerDataMapperMappingRule {

	private RestServiceTaskAdapter restServiceTaskAdapter = new RestServiceTaskAdapter();
	/**
	 * @see com.tibco.xpd.datamapper.rules.AbstractDataMapperMappingRule#getScriptDataMapperProvider()
	 *
	 * @return
	 */
	@Override
	protected IScriptDataMapperProvider getScriptDataMapperProvider() {
		return new RestScriptDataMapperProvider(getMappingDirection(), RestDataMapperConstants.PROCESS_TO_SWAGGER);
	}

	/**
	 * @see com.tibco.xpd.datamapper.rules.AbstractDataMapperMappingRule#getDataMapperContext()
	 *
	 * @return
	 */
	@Override
	protected String getDataMapperContext() {
		return RestDataMapperConstants.PROCESS_TO_SWAGGER;
	}

	/**
	 * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#getMappingDirection()
	 *
	 * @return
	 */
	@Override
	protected MappingDirection getMappingDirection() {
		return MappingDirection.IN;
	}

	/**
	 * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#getScriptType()
	 *
	 * @return
	 */
	@Override
	protected String getScriptType() {
		return ProcessScriptContextConstants.DATA_MAPPER_PE_MAPPING_SCRIPTS;
	}

	@Override
	Object getUnwrappedSource(Object source) {
		Object unwrappedSource = source;
		if (source instanceof WrappedContributedContent) {
			unwrappedSource = getConceptPath((WrappedContributedContent) source);
		}

		return unwrappedSource;
	}

	@Override
	Object getUnwrappedTarget(Object target) {
		Object unwrappedTarget = target;
		// Target should be SwaggerTypedTreeItem
		if (target instanceof WrappedContributedContent) {
			unwrappedTarget = ((WrappedContributedContent) target).getContributedObject();
		}
		return unwrappedTarget;
	}
	
    /**
     * ACE-8867: Determines if a given `EObject` is applicable for validation under specific conditions. 
     * This method first checks applicability with the superclass logic. If the object is 
     * applicable, additional filtering is applied to restrict validation to:
     * 
     * 1.Check if it's a SWAGGER activity
     * 
     * @param objectToValidate The `EObject` to validate.
     * @return `true` if the object meets the specific criteria, otherwise `false`.
     */
	@Override
	protected boolean objectIsApplicable(EObject objectToValidate) {
		boolean objectIsApplicable = super.objectIsApplicable(objectToValidate);
		if(!objectIsApplicable) {
			return false;
		}
		if (objectToValidate instanceof Activity)
		{
            Activity activity = (Activity) objectToValidate;
            if (RestServiceTaskUtil.isRESTServiceActivity(activity)) {
				//Add an extra check to ensure activity has an associated Swager service only
				RsoType rsoType = restServiceTaskAdapter.getRsoType((Activity) objectToValidate);

				if (RsoType.SWAGGER.equals(rsoType)
						&& restServiceTaskAdapter.getRSOOperation((Activity) objectToValidate) != null)
				{
					return true;
				}

            }
        }
		return false;
	}
}
