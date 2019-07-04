/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.n2.ut.resources.processdatamapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.uml2.uml.Property;

import com.tibco.n2.ut.resources.internal.Messages;
import com.tibco.n2.ut.resources.ui.N2UTResourcesPlugin;
import com.tibco.xpd.datamapper.staticcontent.AbstractStaticDataMapperContentProvider;
import com.tibco.xpd.datamapper.staticcontent.StaticContentDataMapperElement;
import com.tibco.xpd.datamapper.staticcontent.VirtualDataMapperFolder;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.worklistfacade.resource.util.PhysicalWorkItemAttributesProvider;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataField;

/**
 * Content provider for {@link WMDataMapperContentContributor} - Data mapper
 * Content Contributor that supports the use of the content of the JavaScript
 * class "bpm.workManager" within User Task Work manager Scripts.
 * <p>
 * Contributes the "Work Item" folder and it's content.
 * <p>
 * <b>Note</b> that all content provided is derived from {@link ConceptPath}
 * (including the folders). For WorkManagerFactory properties, a
 * {@link DataField} is created to provide process data equivalent type
 * information etc
 * 
 */
class WMDataMapperContentProvider extends
        AbstractStaticDataMapperContentProvider {

    /*
     * Sid ACE-1720 WorkManagerFactory JavaScript object is now wrapped up in
     * the bpm.workManager property.
     */
    public static final String WORK_MANAGER_FACTORY_SCRIPT_OBJECT = "bpm.workManager"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.datamapper.staticcontent.AbstractStaticDataMapperContentProvider#createContentCache()
     * 
     *      Create the content cache (if not already created) Work Item class
     *      content is 'fairly' static (except fo WLF labels) so we load on
     *      demand and keep it.
     * @return
     */
    @Override
    protected ConceptPath[] createContentCache() {
        VirtualDataMapperFolder workItemFolder =
                new VirtualDataMapperFolder(
                        "WMDataMapper.WorkItemFolder", //$NON-NLS-1$
                        Messages.WMDataMapperContentProvider_WorkItemFolder_label,
                        N2UTResourcesPlugin.getDefault(),
                        N2UTResourcesPlugin.IMG_TASKUSER_ICON);

        addWorkItemFolderChildren(workItemFolder);

        addWorkItemAttributeFolder(workItemFolder);

        return new ConceptPath[] { workItemFolder };
    }

    /**
     * Add the direct child content of Work Item Folder.
     * 
     * @param workItemFolder
     */
    private void addWorkItemFolderChildren(
            VirtualDataMapperFolder workItemFolder) {
        List<StaticContentDataMapperElement> children =
                new ArrayList<StaticContentDataMapperElement>();

        children.add(new StaticContentDataMapperElement(
                "description", //$NON-NLS-1$
                "WorkItem_Description$", WORK_MANAGER_FACTORY_SCRIPT_OBJECT + ".getWorkItem().description", //$NON-NLS-1$//$NON-NLS-2$
                BasicTypeType.STRING_LITERAL, false, workItemFolder));

        children.add(new StaticContentDataMapperElement(
                "priority", //$NON-NLS-1$
                "WorkItem_Priority$", WORK_MANAGER_FACTORY_SCRIPT_OBJECT + ".getWorkItem().priority", //$NON-NLS-1$//$NON-NLS-2$
                BasicTypeType.INTEGER_LITERAL, false, workItemFolder));

        children.add(new StaticContentDataMapperElement("cancel", //$NON-NLS-1$
                "WorkItem_Cancel$", WORK_MANAGER_FACTORY_SCRIPT_OBJECT + ".getWorkItem().cancel", //$NON-NLS-1$//$NON-NLS-2$
                BasicTypeType.BOOLEAN_LITERAL, false, workItemFolder));

        Collections.sort(children,
                new Comparator<StaticContentDataMapperElement>() {

                    public int compare(StaticContentDataMapperElement o1,
                            StaticContentDataMapperElement o2) {
                        return o1.getLabel().compareToIgnoreCase(o2.getLabel());
                    }
                });

        workItemFolder.getChildren().addAll(children);
    }

    /**
     * Add the work item attribute folder and it's children.
     * 
     * @param workItemFolder
     */
    private void addWorkItemAttributeFolder(
            VirtualDataMapperFolder workItemFolder) {
        VirtualDataMapperFolder workItemAttributesFolder =
                new VirtualDataMapperFolder(
                        "WMDataMapper.WorkItemAttributesFolder", //$NON-NLS-1$
                        Messages.WMDataMapperContentProvider_WorkItemAttributesFolder_label,
                        N2UTResourcesPlugin.getDefault(),
                        N2UTResourcesPlugin.WORK_ITEM_ATTRIBUTES_GROUP_ICON,
                        workItemFolder);

        workItemFolder.getChildren().add(workItemAttributesFolder);

        /*
         * Add the work item attributes
         */

        PhysicalWorkItemAttributesProvider provider =
                PhysicalWorkItemAttributesProvider.INSTANCE;

        List<WMDataMapperWorkItemAttributeElement> workItemAttrElements =
                new ArrayList<WMDataMapperWorkItemAttributeElement>();

        // get all the attributes
        for (Property property : provider.getWorkItemAttributes()) {
            WMDataMapperWorkItemAttributeElement attributeElement =
                    new WMDataMapperWorkItemAttributeElement(property,
                            workItemAttributesFolder);
            workItemAttrElements.add(attributeElement);
        }

        /*
         * If attribute label is defined in wlf project in the workspace then
         * these should appear at the top of the list and sorted alphabetically
         * followed by the rest
         */
        Collections.sort(workItemAttrElements,
                new WorkItemAttributeSortComparator());

        /*
         * Add them to the folder.
         */
        workItemAttributesFolder.getChildren().addAll(workItemAttrElements);
    }

    /**
     * @see org.eclipse.jface.viewers.IContentProvider#dispose()
     * 
     */
    @Override
    public void dispose() {
    }
}