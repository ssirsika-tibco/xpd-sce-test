/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.bx.validation.rules.mapping;

import org.eclipse.emf.ecore.EObject;

import com.tibco.bx.validation.internal.Messages;
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
 * Process to REST service mapping rule.
 * 
 * @author jarciuch
 * @since 16 Apr 2015
 */
public class ProcessDataToRestServiceJsRule extends AbstractRestDataMapperMappingRule {

	private RestServiceTaskAdapter restServiceTaskAdapter = new RestServiceTaskAdapter();
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getScriptType() {
		return ProcessScriptContextConstants.DATA_MAPPER_PE_MAPPING_SCRIPTS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected MappingDirection getMappingDirection() {
		return MappingDirection.IN;
	}

	/**
	 * @see com.tibco.xpd.datamapper.rules.AbstractDataMapperMappingRule#getScriptDataMapperProvider()
	 * 
	 * @return
	 */
	@Override
	protected IScriptDataMapperProvider getScriptDataMapperProvider() {
		/* Sid ACE-8864 Use new constructor for RestScriptDataMapperProvider */
		return new RestScriptDataMapperProvider(getMappingDirection(), getDataMapperContext());
	}

	/**
	 * @see com.tibco.xpd.datamapper.rules.AbstractDataMapperMappingRule#getDataMapperContext()
	 * 
	 * @return
	 */
	@Override
	protected String getDataMapperContext() {
		return RestDataMapperConstants.PROCESS_TO_REST_SERVICE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getMappingTypeDescription(EObject objectToValidate) {
		return Messages.ProcessDataToRestServiceJsRule_Mapping_desc;
	}

	/**
	 * @see com.tibco.xpd.datamapper.rules.AbstractDataMapperMappingRule#isPartialMappingCompletionSupported(java.lang.Object)
	 * 
	 * @param targetObjectInTree
	 * @return
	 */
	@Override
	protected boolean isPartialMappingCompletionSupported(Object targetObjectInTree) {
		/* Mandatory target child content mapping is required. */
		return false;
	}

	 /**
     * ACE-8867: Determines if a given `EObject` is applicable for validation under specific conditions. 
     * This method first checks applicability with the superclass logic. If the object is 
     * applicable, additional filtering is applied to restrict validation to:
     * 
     * 1.Check if it's a RSD activity
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

		/*
		 * XPD-7922: Added filtering so that only Rest Activities
		 */
		if (objectToValidate instanceof Activity) {
			Activity activity = (Activity) objectToValidate;
			if (RestServiceTaskUtil.isRESTServiceActivity(activity)) {
				// ACE-8269 Add an extra check to ensure activity has an associated RSD service
				// only
				RsoType rsoType = restServiceTaskAdapter.getRsoType((Activity) objectToValidate);
				if (RsoType.RSD.equals(rsoType)) {
					return true;
				}

			}
		}
		return false;
	}
}
