/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.n2.ut.resources.dynamicparticipant.mapper;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import com.tibco.xpd.mapper.Mapping;
import com.tibco.xpd.om.core.om.DynamicOrgIdentifier;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.core.om.util.OMUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.DynamicOrgIdentifierRef;

/**
 * Dynamic org identifier path class.
 * <p>
 * Wraps a Dynamic org identifier and can construct equivalent
 * DynamicOrgIdentiferPath objects from either the Dynamic org identifier itself
 * OR the model used to store a reference to the identifier (i.e. the target in
 * a dynamic org identifier mapping).
 * 
 * <p>
 * This is for use by
 * <li>The org identifier mapping section TARGET content provider. Which will
 * construct this using a physical org identifier from the dynamic org models
 * referenced by participants.</li>
 * <li>The org identifier mapping section MAPPING content provider (for the
 * target objects in the {@link Mapping} objects it returns. In this case this
 * is constructed from the DynamicOrgIdentiferRef in the mapping model</li> This
 * should be enough for the mapping content provider and the target content
 * provider to agree on the identifier content objects.
 * <p>
 * <li>The MappingRuleInfoProvider will also deal with DynamicOrgIdentiferPath
 * (and the SAME TARGET content provider) as the objects that are passed to its
 * methods (such as getObjectPath()) BECAUSE it uses the same target contents
 * provider.</li>
 * 
 * @author kthombar
 * @since 2 Oct 2013
 */
public class DynamicOrgIdentiferPath {

    /** The physical referenced object in org model. */
    private DynamicOrgIdentifier orgIdentifier;

    /**
     * Construct from the physical dynamic org identifier object
     * <p>
     * Used by the org identifier mapping section target content provider to
     * represent RHS content.
     * 
     * @param orgIdentifier
     */
    public DynamicOrgIdentiferPath(DynamicOrgIdentifier orgIdentifier) {
        super();
        this.orgIdentifier = orgIdentifier;
    }

    /**
     * Get the referenced Dynamic Org identifier from the org model.
     * 
     * @return
     */
    public DynamicOrgIdentifier getDynamicOrgIdentifier() {
        return orgIdentifier;
    }

    /**
     * Construct from the model object stored for target in the dynamic org
     * identifier mapping mapping.
     * <p>
     * Used by the org identifier mapping section mapping content provider to
     * construct a DynamicOrgIdentiferPath that is equivalent to one constructed
     * with the physical orgIdentifier itself (in the other constructor)
     * 
     * @param orgIdentifier
     */
    public DynamicOrgIdentiferPath(DynamicOrgIdentifierRef target) {
        super();

        /** Deference the org identifier from target object in mapping. */

        /* get orgModel resource for target.orgModelPath */

        /* get dynamicOrg from orgModel resource with target.dynamicOrgId */

        /* get org identifier from dynamicOrg with target.identifierName */

        IFile file =
                SpecialFolderUtil
                        .resolveSpecialFolderRelativePath(WorkingCopyUtil
                                .getProjectFor(target),
                                OMUtil.OM_FILE_EXTENSION,
                                target.getOrgModelPath(),
                                true);

        if (file != null) {

            WorkingCopy workingCopy = WorkingCopyUtil.getWorkingCopy(file);

            if (workingCopy.getRootElement() != null) {

                Resource resource = workingCopy.getRootElement().eResource();
                if (resource != null) {
                    EObject eObject =
                            resource.getEObject(target.getDynamicOrgId());
                    if (eObject != null) {
                        Organization organization = (Organization) eObject;
                        EList<DynamicOrgIdentifier> dynamicOrgIdentifiers =
                                organization.getDynamicOrgIdentifiers();

                        for (DynamicOrgIdentifier eachdynamicOrgIdentifiers : dynamicOrgIdentifiers) {
                            if (eachdynamicOrgIdentifiers.getName()
                                    .equals(target.getIdentifierName())) {
                                this.orgIdentifier = eachdynamicOrgIdentifiers;
                                break;

                            }

                        }

                    }
                }
            }
        }

    }

    public String getLabel() {

        /**
         * Return label for orgIdentifier or 'Unresolved Reference' if
         * orgIdentifier = null
         */

        /*
         * can use this for section label provider and
         * MappingRuleContentInfoProvider.getObjectPathDescription() - then the
         * label will appear in problem marker description
         */

        return orgIdentifier.getLabel();
    }

    public String getName() {

        /**
         * Return label for orgIdentifier or 'Unresolved Reference' if
         * orgIdentifier = null
         */

        /*
         * can use this for section label provider and
         * MappingRuleContentInfoProvider.getObjectPathDescription() - then the
         * label will appear in problem marker description
         */
        if (orgIdentifier != null) {
            return orgIdentifier.getName();
        }
        return null;
    }

    /**
     * Convert orgIdentifier into a programmatic path string (for use in problem
     * marker). NOT a label
     * 
     * USE THIS for the
     * AbstractMappingSection.getProblemMarkerDataMappingTargetPath() AND the
     * target content MappingRuleContentInfoProvider.getObjectPath() then it
     * should means that the 'path' stored in problem marker by
     * AbstractMappingRule will match the 'path' used for the target object in
     * the mapping section.
     * 
     * i.e. this path is ONLY used to match against other paths generated by
     * this method (and should match if the referenced orgIndentifiers are
     * equivalent).
     */
    public String getPath() {
        /**
         * Convert orgIdentifier into a programmatic path string (for use in
         * problem marker). NOT a label
         */
        String path = null;
        if (orgIdentifier != null) {
            path =
                    getOrgModelPath()
                            + "#" //$NON-NLS-1$
                            + ((Organization) orgIdentifier.eContainer())
                                    .getId() + "#" + orgIdentifier.getName(); //$NON-NLS-1$
        }

        return path;
    }

    /**
     * Returns the Special Folder Relative path for the Dynamic Org Identifier.
     * 
     * @return
     */
    public String getOrgModelPath() {

        if (orgIdentifier != null) {
            IPath specialFolderRelativePath =
                    SpecialFolderUtil
                            .getSpecialFolderRelativePath(orgIdentifier,
                                    OMUtil.OM_FILE_EXTENSION);

            if (specialFolderRelativePath != null) {
                return specialFolderRelativePath.toString();
            }

        }
        return null;
    }

    /**
     * This will be used by target content provider when it's
     * ITreeContentProvider.getParent() is called
     * 
     * This method has to return the object that matches whatever class the
     * target content provider returns for the the root dynamic org objects in
     * the tree.
     * 
     * @return Return the parent node of this path (always the parent dynamic)
     *         org)
     */
    public Organization getParent() {
        if (orgIdentifier != null) {
            return (Organization) orgIdentifier.eContainer();
        }
        return null;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DynamicOrgIdentiferPath) {
            DynamicOrgIdentiferPath otherOrgIdentifier =
                    (DynamicOrgIdentiferPath) obj;

            if (this.getDynamicOrgIdentifier() != null) {
                if (otherOrgIdentifier.getDynamicOrgIdentifier() != null) {
                    return this
                            .getDynamicOrgIdentifier()
                            .equals(otherOrgIdentifier.getDynamicOrgIdentifier());
                }
            } else {
                if (otherOrgIdentifier.getDynamicOrgIdentifier() == null) {
                    return true;
                }
            }
        }

        return false;
    }

}
