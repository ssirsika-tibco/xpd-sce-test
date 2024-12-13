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
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.RestServiceTaskUtil;
import com.tibco.xpd.rest.datamapper.RestDataMapperConstants;
import com.tibco.xpd.rest.datamapper.RestScriptDataMapperProvider;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.IntermediateEvent;

/**
 * Swagger to Process data mapping rule.
 * Jira : ACE-8261 
 * @author ssirsika
 * @since 01 Oct 2024
 */
public class SwaggerToProcessDataJsRule extends AbstractSwaggerDataMapperMappingRule {

	private RestServiceTaskAdapter restServiceTaskAdapter = new RestServiceTaskAdapter();
	
	@Override
	protected IScriptDataMapperProvider getScriptDataMapperProvider() {
		return new RestScriptDataMapperProvider(getMappingDirection(), RestDataMapperConstants.SWAGGER_TO_PROCESS);
	}

	@Override
	protected String getDataMapperContext() {
		return RestDataMapperConstants.SWAGGER_TO_PROCESS;
	}

	@Override
	protected MappingDirection getMappingDirection() {
		return MappingDirection.OUT;
	}

	@Override
	protected String getScriptType() {
		return ProcessScriptContextConstants.DATA_MAPPER_SWAGGER_MAPPING_SCRIPTS;
	}

	@Override
	Object getUnwrappedSource(Object source) {
		Object unwrappedTarget = source;
		// Source should be SwaggerTypedTreeItem
		if (source instanceof WrappedContributedContent) {
			unwrappedTarget = ((WrappedContributedContent) source).getContributedObject();
		}
		return unwrappedTarget;
	}
	
	@Override
	Object getUnwrappedTarget(Object target) {
		Object unwrappedSource = target;
		if (target instanceof WrappedContributedContent) {
			unwrappedSource = getConceptPath((WrappedContributedContent) target);
		}
		return unwrappedSource;
	}
	
    /**
     * ACE-8867: Determines if a given `EObject` is applicable for validation under specific conditions. 
     * This method first checks applicability with the superclass logic. If the object is 
     * applicable, additional filtering is applied to restrict validation to:
     * 
     * 1.Check if it's a SWAGGER activity
     * 2.Check if attached activity to an event is SWAGGER
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

            } else if (activity.getEvent() instanceof IntermediateEvent
                    && EventObjectUtil.isAttachedToTask(activity)) {

                IntermediateEvent ie = (IntermediateEvent) activity.getEvent();

                if (ie.getResultError() != null) {

                    Activity taskAttachedTo =
                            EventObjectUtil.getTaskAttachedTo(activity);

                    if (taskAttachedTo != null
                            && RestServiceTaskUtil
                                    .isRESTServiceActivity(taskAttachedTo)) {
						// Add an extra check to ensure activity has an associated Swagger service only
						if (RsoType.SWAGGER.equals(restServiceTaskAdapter.getRsoType(taskAttachedTo))
								&& restServiceTaskAdapter.getRSOOperation((Activity) objectToValidate) != null)
						{
							return true;
						}
                    }
                }
            }
        }
		return false;
	}
}
