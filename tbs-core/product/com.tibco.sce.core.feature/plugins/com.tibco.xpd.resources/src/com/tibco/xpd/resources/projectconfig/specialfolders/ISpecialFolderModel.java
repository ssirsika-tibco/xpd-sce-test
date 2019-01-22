/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.resources.projectconfig.specialfolders;

import org.eclipse.jface.resource.ImageDescriptor;

/**
 * Interface defining the special folder kind information acquired from the
 * special folders extensions.
 * 
 * @author njpatel
 */
public interface ISpecialFolderModel extends Comparable<ISpecialFolderModel> {

    /**
     * Multiplicity type of the Special Folder kind.
     * <ul>
     * <li><i>SINGLE</i> - Only one SpecialFolder of this kind can be added to
     * a project,</li>
     * <li><i>MANY</i> - more than one SpecialFolder of this kind can be added
     * to the project.</li>
     * </ul>
     * 
     * @author njpatel
     */
    public enum MultiplicityType {
        SINGLE, MANY
    };

    /**
     * Get unique <i>kind</i> of <code>SpecialFolder</code>.
     * 
     * @return The <i>kind</i> set in the extension
     */
    public String getKind();

    /**
     * Get the user readable <i>name</i> of this <code>SpecialFolder</code>
     * kind.
     * 
     * @return The user readable <i>name</i> set in the extesion
     */
    public String getName();

    /**
     * Get the <i>icon</i> for this <code>SpecialFolder</code> kind.
     * 
     * @return <code>ImageDescriptor</code> of the icon set in the extension.
     *         If no icon is set then <b>null</b> will be returned.
     */
    public ImageDescriptor getIcon();

    /**
     * Get the <i>unsettable</i> value of this <code>SpecialFolder</code>
     * kind. This indicates whether this kind of special folder can be set
     * through the user interface.
     * 
     * @return <b>true</b> if this kind of SpecialFolder cannot be set,
     *         <b>false</b> returned otherwise (default value).
     */
    public boolean isUnsettable();

    /**
     * Get the Navigator content ID that will service this
     * <code>SpecialFolder</code> kind.
     * 
     * @return The navigator ID string if one is set, <b>null</b> otherwise.
     */
    public String getNavigatorContentId();

    /**
     * Get the <i>multiple</i> value set in the extension for the
     * <code>SpecialFolder</code> kind.
     * 
     * @return
     * <ul>
     * <li><code>{@link MultiplicityType#SINGLE}</code> - if only a single
     * <code>SpecialFolder</code> of this kind can be set in a project.</li>
     * <li><code>{@link MultiplicityType#MANY}</code> - if more than one
     * <code>SpecialFolder</code> of this kind can be set in a project
     * (default).</li>
     */
    public MultiplicityType getMultiplicity();

    /**
     * Get the ID of the project asset that is associated with this special
     * folder.
     * 
     * @return Project Asset ID, <code>null</code> if no asset is associated
     *         with this special folder.
     */
    public String getProjectAssetId();

    /**
     * Indicate whether duplicate resources are allowed in this kind of special
     * folder. If a project has more than one special folder of this kind then
     * this value will determine if a resource with the same name (and relative
     * path) can exist in the special folders.
     * 
     * @return <code>true</code> if duplicate resources are allowed,
     *         <code>false</code> otherwise.
     */
    public boolean isDuplicateResourcesAllowed();

    /**
     * Get the issue id of the issue to add to the problems view when duplicate
     * resources are found (when duplicateResourcesAllowed is set to false).
     * 
     * @return issue id
     */
    public String getIssueId();
}
