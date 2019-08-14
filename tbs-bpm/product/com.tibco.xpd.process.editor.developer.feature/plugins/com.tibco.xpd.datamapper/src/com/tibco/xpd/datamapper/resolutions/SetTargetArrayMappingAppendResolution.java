/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.datamapper.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdExtension.DataMapperArrayInflation;
import com.tibco.xpd.xpdExtension.DataMapperArrayInflationType;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Resolution to to change a target array of a data mapping to APPEND mode.
 * 
 * NOTE that this only functions (currently) where the data mapping IS TO THE TARGET ARRAY (will not work where the
 * mapping is to a child within a complex array object.
 *
 * @author aallway
 * @since 14 Aug 2019
 */
public class SetTargetArrayMappingAppendResolution extends
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
        CompoundCommand cmd = null;

        if (target instanceof DataMapping) {
            DataMapping dataMapping = (DataMapping) target;

            ScriptDataMapper sdm = getScriptDataMapper(dataMapping);

            if (sdm != null) {
                cmd = new CompoundCommand("Comfigure mapping target as 'Append to list'");

                /*
                 * Check if there's an existing configuration for the target array...
                 */
                DataMapperArrayInflation configForTarget = null;

                String targetPath = dataMapping.getFormal();

                EList<DataMapperArrayInflation> arrayInflationType = sdm.getArrayInflationType();

                for (DataMapperArrayInflation dataMapperArrayInflation : arrayInflationType) {
                    if (targetPath.equals(dataMapperArrayInflation.getPath())) {
                        configForTarget = dataMapperArrayInflation;
                        break;
                    }
                }

                if (configForTarget == null) {
                    /* No config for target yet, add one. */
                    configForTarget = XpdExtensionFactory.eINSTANCE.createDataMapperArrayInflation();
                    configForTarget.setPath(targetPath);
                    configForTarget.setContributorId((String) Xpdl2ModelUtil.getOtherAttribute(dataMapping,
                            XpdExtensionPackage.eINSTANCE.getDocumentRoot_TargetContributorId()));
                    configForTarget.setMappingType(DataMapperArrayInflationType.APPEND_LIST);

                    cmd.append(AddCommand.create(editingDomain,
                            sdm,
                            XpdExtensionPackage.eINSTANCE.getScriptDataMapper_ArrayInflationType(),
                            configForTarget));

                } else {
                    /* Already has a config, so can just set the type... */
                    cmd.append(SetCommand.create(editingDomain,
                            configForTarget,
                            XpdExtensionPackage.eINSTANCE.getDataMapperArrayInflation_MappingType(),
                            DataMapperArrayInflationType.APPEND_LIST));
                }
            }
        }
        return cmd;
    }

    /**
     * @param mapping
     * @return The ScriptDataMapper model ancestor of the given mapping.
     */
    private ScriptDataMapper getScriptDataMapper(DataMapping mapping) {
        return (ScriptDataMapper) Xpdl2ModelUtil.getAncestor(mapping, ScriptDataMapper.class);
    }

}
