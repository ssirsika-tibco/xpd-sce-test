/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.datamapper.resolutions;

import java.util.Properties;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.datamapper.internal.Messages;
import com.tibco.xpd.datamapper.rules.AbstractDataMapperMappingRule;
import com.tibco.xpd.mapper.MapperContentProvider;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.validation.rules.ValidationUtil;
import com.tibco.xpd.xpdExtension.LikeMappingExclusion;
import com.tibco.xpd.xpdExtension.LikeMappingExclusions;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Resolution to add a data mapper target element to the exclusion list of it's
 * ancetors target object's like mapping.
 * 
 * @author aallway
 * @since 24 Jul 2015
 */
public class ExcludeLikeMappedChildResolution extends
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

        Properties addInfo = ValidationUtil.getAdditionalInfo(marker);

        if (addInfo == null) {
            return null;
        }

        if (target instanceof Activity) {
            /*
             * In certain cases (where problem markers need to be placed on
             * source/target content) the target object may be activity.
             * 
             * In this case we can try to resolve the data mapping from marker
             * additional into.
             */
            String likeMappingURI =
                    (String) addInfo
                            .get(AbstractDataMapperMappingRule.MARKER_INFO_LIKE_MAPPING_URI);

            if (likeMappingURI != null && likeMappingURI.length() > 0) {
                EObject eObject =
                        ((Activity) target).eResource()
                                .getEObject(likeMappingURI);
                if (eObject instanceof DataMapping) {
                    target = eObject;
                }
            }
        }

        if (target instanceof DataMapping) {
            DataMapping dataMapping = (DataMapping) target;

            /* Get the target path in question. */

            String targetPath =
                    (String) addInfo
                            .get(MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO);

            if (targetPath == null || targetPath.isEmpty()) {
                targetPath =
                        (String) addInfo
                                .get(MapperContentProvider.TARGET_URI_ISSUEINFO);
            }

            if (targetPath != null && !targetPath.isEmpty()) {
                CompoundCommand cmd =
                        new CompoundCommand(
                                Messages.AbstractDataMapperSection_AddToLikeMappingsExclusions_menu);

                LikeMappingExclusions likeMappingExclusions =
                        (LikeMappingExclusions) Xpdl2ModelUtil
                                .getOtherElement(dataMapping,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_LikeMappingExclusions());

                if (likeMappingExclusions == null) {
                    likeMappingExclusions =
                            XpdExtensionFactory.eINSTANCE
                                    .createLikeMappingExclusions();

                    cmd.append(Xpdl2ModelUtil
                            .getSetOtherElementCommand(editingDomain,
                                    dataMapping,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_LikeMappingExclusions(),
                                    likeMappingExclusions));
                }

                for (LikeMappingExclusion likeMappingExclusion : likeMappingExclusions
                        .getExclusions()) {
                    if (targetPath.equals(likeMappingExclusion.getPath())) {
                        /* Can't add it if it's already there - do nothing. */
                        return null;
                    }
                }

                LikeMappingExclusion likeMappingExclusion =
                        XpdExtensionFactory.eINSTANCE
                                .createLikeMappingExclusion();
                likeMappingExclusion.setPath(targetPath);

                cmd.append(new AddCommand(editingDomain, likeMappingExclusions
                        .getExclusions(), likeMappingExclusion));

                return cmd;
            }
        }

        return null;
    }

}
