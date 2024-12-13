/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rest.datamapper.rules;

import com.tibco.xpd.datamapper.rules.AbstractDataMapperExpressionRule;
import com.tibco.xpd.datamapper.rules.DataMapperScriptTool;
import com.tibco.xpd.implementer.resources.xpdl2.properties.RestServiceTaskAdapter;
import com.tibco.xpd.implementer.resources.xpdl2.properties.RestServiceTaskAdapter.RsoType;
import com.tibco.xpd.js.validation.tools.ScriptTool;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.RestServiceTaskUtil;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.ResultError;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Expression validation rule for REST invocation response mapping scripts.
 * 
 * @author nwilson
 * @since 5 May 2015
 */
public class RestResponseDataMapperExpressionRule extends
        AbstractDataMapperExpressionRule {

	private RestServiceTaskAdapter restServiceTaskAdapter = new RestServiceTaskAdapter();
    /**
     * @see com.tibco.xpd.js.validation.rules.AbstractExpressionRule#getScriptContext()
     * 
     * @return
     */
    @Override
    protected String getScriptContext() {
        return ProcessScriptContextConstants.DATA_MAPPER_REST_MAPPING_SCRIPTS;
    }

    /**
     * @see com.tibco.xpd.datamapper.rules.AbstractDataMapperExpressionRule#createScriptToolIfInterested(com.tibco.xpd.xpdExtension.ScriptDataMapper)
     * 
     * @param si
     *            The ScriptInformation element.
     * @param sdm
     *            The script data mapper.
     * @return A tool if this is a REST response script.
     */
    @Override
    protected ScriptTool createScriptToolIfInterested(ScriptInformation si,
            ScriptDataMapper sdm) {
        ScriptTool tool = null;

        /*
         * Sid XPD-7359 Improved discovery of whether is rest service activity
         * (to account for events and send task as well as service task.
         */
        if (DirectionType.OUT_LITERAL.equals(sdm.getMappingDirection())) {
            Activity activity =
                    (Activity) Xpdl2ModelUtil.getAncestor(sdm, Activity.class);

            if (RestServiceTaskUtil.isRESTServiceActivity(activity)) {
            	//ACE-8641: Perform an extra check to ensure activity has an associated RSD service only
				RsoType rsoType = restServiceTaskAdapter.getRsoType(activity);

				if (!RsoType.RSD.equals(rsoType)) {
					return null;
				}
						
                tool = new DataMapperScriptTool(si, getScriptContext());

            } else if (sdm.eContainer() instanceof ResultError) {
                /*
                 * Only deal for catch errors that are attached to REST service
                 * tasks.
                 */
                Activity catchError =
                        (Activity) Xpdl2ModelUtil.getAncestor(sdm,
                                Activity.class);

                Activity taskAttachedTo =
                        EventObjectUtil.getTaskAttachedTo(catchError);
                if (taskAttachedTo != null
                        && RestServiceTaskUtil
                                .isRESTServiceActivity(taskAttachedTo)) {

                    tool = new DataMapperScriptTool(si, getScriptContext());
                }
            }
        }

        return tool;
    }

}
