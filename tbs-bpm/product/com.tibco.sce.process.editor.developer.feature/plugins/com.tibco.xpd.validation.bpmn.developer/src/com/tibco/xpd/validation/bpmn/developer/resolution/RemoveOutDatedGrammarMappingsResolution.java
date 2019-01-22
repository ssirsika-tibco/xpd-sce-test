/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.developer.resolution;

import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.processeditor.xpdl2.util.DataMappingUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 
 * This resolution is applied on Reply activities or Throw error events of
 * not-auto-generated
 * 
 * @author rsomayaj
 * @since 3.3 (24 Feb 2010)
 */
public class RemoveOutDatedGrammarMappingsResolution extends
        AbstractWorkingCopyResolution {

    /**
     * @see com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#getResolutionCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, org.eclipse.core.resources.IMarker)
     * 
     * @param editingDomain
     * @param target
     * @param marker
     * @return
     * @throws ResolutionException
     */
    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {

        CompoundCommand cmd = new CompoundCommand();
        if (target instanceof Activity) {
            Activity activity = (Activity) target;
            // Assuming the activity is only going to be a reply to an request
            // activity that hasn't been generated.
            List<DataMapping> dataMappings =
                    Xpdl2ModelUtil.getDataMappings(activity,
                            DirectionType.IN_LITERAL);
            String activityScriptGrammar =
                    ScriptGrammarFactory.getDefaultScriptGrammar(activity);

            for (DataMapping dataMapping : dataMappings) {
                String mappingGrammar = DataMappingUtil.getGrammar(dataMapping);
                if (null != mappingGrammar
                        && !(mappingGrammar.equals(activityScriptGrammar))) {
                    cmd
                            .append(RemoveCommand.create(editingDomain,
                                    dataMapping));
                }
            }
        }
        return cmd;
    }
}
