/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.worklistfacade.doc;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.worklistfacade.model.WorkItemAttribute;
import com.tibco.xpd.worklistfacade.model.WorkItemAttributes;
import com.tibco.xpd.worklistfacade.model.WorkListFacade;
import com.tibco.xpd.worklistfacade.resource.editor.util.WorkListFacadeEditorUtil;
import com.tibco.xpd.worklistfacade.resource.util.PhysicalWorkItemAttributesProvider;
import com.tibco.xpd.worklistfacade.resource.workingcopy.WorkListFacadeWorkingCopy;

/**
 * Helper class that provides helper methods to wlf2html.xsl which are required
 * for the wlf to html conversion.
 * 
 * @author kthombar
 * @since May 20, 2015
 */
public class WlfXsltGeneratorHelper {

    private WorkIteamTableAttributeWrapper workitemAttributeProperty[];

    private PhysicalWorkItemAttributesProvider wIAProvider;

    /**
     * Default Constructor
     */
    public WlfXsltGeneratorHelper() {
        wIAProvider = PhysicalWorkItemAttributesProvider.INSTANCE;
    }

    /**
     * Populates the work item attribute property, this is done once per wlf
     * file.
     */
    public String populateWorkitemAttributeProperty(String wlfFileFullPath) {

        workitemAttributeProperty =
                new WorkIteamTableAttributeWrapper[getWorkItemAttributeCount()];

        /*
         * XPD-7626: Get the actual display names by accessing the working copy
         * rather than displaying same entries for all WLF files
         */
        Map<String, String> displayNameFromFile =
                getAttrNameToDisplayNameMap(wlfFileFullPath);

        int incrementer = 0;

        for (Property property : wIAProvider.getWorkItemAttributes()) {
            /*
             * get the property name, type , length and display name
             */
            String propName = property.getName();

            String propType = null;
            if (property.getType() != null) {
                propType = property.getType().getName();
            }

            String propDisplayName = displayNameFromFile.get(propName);

            String propLength =
                    WorkListFacadeEditorUtil.getLengthRestriction(property);

            /*
             * wrap the data together.
             */
            WorkIteamTableAttributeWrapper workIteamTableAttributeWrapper =
                    new WorkIteamTableAttributeWrapper(propName, propType,
                            propLength, propDisplayName);

            workitemAttributeProperty[incrementer++] =
                    workIteamTableAttributeWrapper;
        }
        /*
         * we would want to show the attributes with display names first, hence
         * sort them.
         */
        Arrays.sort(workitemAttributeProperty, new WorkItemAttributeSorter());

        return null;
    }

    /**
     * @param wlfFileFullPath
     *            the path of the file that is currently exported.
     * @return map of work item attribute names to their display names (if any)
     */
    private Map<String, String> getAttrNameToDisplayNameMap(
            String wlfFileFullPath) {

        Map<String, String> attributeNameToDisplayNameMap =
                new HashMap<String, String>();
        IFile file =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getFile(new Path(wlfFileFullPath));

        if (file != null) {

            WorkingCopy workingCopy = WorkingCopyUtil.getWorkingCopy(file);

            if (workingCopy instanceof WorkListFacadeWorkingCopy) {

                WorkListFacade wlf =
                        ((WorkListFacadeWorkingCopy) workingCopy)
                                .getWorkListFacade();

                if (wlf != null) {

                    WorkItemAttributes workItemAttributes =
                            wlf.getWorkItemAttributes();

                    if (workItemAttributes != null) {

                        EList<WorkItemAttribute> workItemAttribute =
                                workItemAttributes.getWorkItemAttribute();

                        for (WorkItemAttribute attributes : workItemAttribute) {

                            String displayLabel = attributes.getDisplayLabel();
                            String name = attributes.getName();

                            if (name != null && !name.isEmpty()
                                    && displayLabel != null
                                    && !displayLabel.isEmpty()) {

                                attributeNameToDisplayNameMap.put(name,
                                        displayLabel);
                            }
                        }
                    }
                }
            }
        }
        return attributeNameToDisplayNameMap;
    }

    /**
     * 
     * @return the number of Work Item attributes, if none found then return -1.
     */
    public int getWorkItemAttributeCount() {

        if (wIAProvider.getWorkItemAttributes() != null
                && !wIAProvider.getWorkItemAttributes().isEmpty()) {
            return wIAProvider.getWorkItemAttributes().size();
        }

        return -1;
    }

    /**
     * 
     * @param attributeIndex
     *            the index of the wlf work item attribute.
     * @return the wlf work item attribute name.
     */
    public String getWorkItemAttributeName(int attributeIndex) {

        return workitemAttributeProperty[attributeIndex].getAttributeName();
    }

    /**
     * 
     * @param attributeIndex
     *            the index of the wlf work item attribute.
     * @return the wlf work item attribute type.
     */
    public String getWorkItemAttributeType(int attributeIndex) {

        return workitemAttributeProperty[attributeIndex].getAttributeType();
    }

    /**
     * 
     * @param attributeIndex
     *            the index of the wlf work item attribute.
     * @return the wlf work item attribute display name.
     */
    public String getWorkItemAttributeDisplayName(int attributeIndex) {

        return workitemAttributeProperty[attributeIndex]
                .getAttributeDisplayName();
    }

    /**
     * 
     * @param attributeIndex
     *            the index of the wlf work item attribute.
     * @return the wlf work item attribute length.
     */
    public String getWorkItemAttributeLength(int attributeIndex) {

        return workitemAttributeProperty[attributeIndex].getAttributeLength();
    }

    /**
     * Sorts the work item attributes based on the siaply names.
     * 
     * 
     * @author kthombar
     * @since May 21, 2015
     */
    private class WorkItemAttributeSorter implements
            Comparator<WorkIteamTableAttributeWrapper> {
        /*
         * Code taken from WorkItemAttributeSortComparator
         */
        final Pattern lastIntPattern = Pattern.compile("[0-9]$"); //$NON-NLS-1$

        @Override
        public int compare(WorkIteamTableAttributeWrapper attribute1,
                WorkIteamTableAttributeWrapper attribute2) {

            String o1BaseLabel = attribute1.getAttributeName();
            String o2BaseLabel = attribute2.getAttributeName();

            String o1WLFLabel = attribute1.getAttributeDisplayName();
            String o2WLFLabel = attribute2.getAttributeDisplayName();

            if (o1WLFLabel != null && o2WLFLabel == null) {
                /* WLF labels above normal labels. */
                return -1;
            } else if (o2WLFLabel != null && o1WLFLabel == null) {
                /* Normal Labels below WLF labels. */
                return 1;

            } else if (o2WLFLabel != null && o1WLFLabel != null) {
                /*
                 * If both are WLF labels then do a straight compare.
                 */
                return o1WLFLabel.compareToIgnoreCase(o2WLFLabel);

            } else {
                /*
                 * Both are default attribute1 - 40 so do a compare taking
                 * number into account (so attribute10 does not appear after
                 * attribute1 but before attribute2
                 */

                String[] nameAndNum = lastIntPattern.split(o1BaseLabel);

                if (nameAndNum != null && nameAndNum.length == 1) {
                    String o1name = nameAndNum[0];
                    int o1num =
                            Integer.parseInt(o1BaseLabel.substring(o1name
                                    .length()));

                    String[] nameAndNum2 = lastIntPattern.split(o2BaseLabel);

                    if (nameAndNum2 != null && nameAndNum2.length == 1) {
                        String o2name = nameAndNum2[0];
                        int o2num =
                                Integer.parseInt(o2BaseLabel.substring(o2name
                                        .length()));

                        int res = o1name.compareToIgnoreCase(o2name);
                        if (res != 0) {
                            return res;
                        } else {
                            return o1num - o1num;
                        }
                    }
                }
            }

            return o1BaseLabel.compareToIgnoreCase(o2BaseLabel);
        }
    }

    /**
     * Wraps all the work item attribute required for doc generation together.
     * 
     * 
     * @author kthombar
     * @since May 21, 2015
     */
    private class WorkIteamTableAttributeWrapper {

        private String attributeName;

        private String attributeType;

        private String attributeLength;

        private String attributeDisplayName;

        /**
         * 
         * @param attributeName
         * @param attributeType
         * @param attributeLength
         * @param attributeDisplayName
         */
        public WorkIteamTableAttributeWrapper(String attributeName,
                String attributeType, String attributeLength,
                String attributeDisplayName) {
            this.attributeName = attributeName;
            this.attributeType = attributeType;
            this.attributeLength = attributeLength;
            this.attributeDisplayName = attributeDisplayName;

        }

        /**
         * @return the attributeName
         */
        public String getAttributeName() {
            return attributeName;
        }

        /**
         * @return the attributeType
         */
        public String getAttributeType() {
            return attributeType;
        }

        /**
         * @return the attributeLength
         */
        public String getAttributeLength() {
            return attributeLength;
        }

        /**
         * @return the attributeDisplayName
         */
        public String getAttributeDisplayName() {
            return attributeDisplayName;
        }
    }
}
