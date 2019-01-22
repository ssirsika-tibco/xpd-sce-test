/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.n2.ut.resources.processdatamapper;

import java.util.Comparator;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IResource;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.worklistfacade.model.WorkItemAttribute;
import com.tibco.xpd.worklistfacade.model.WorkListFacade;
import com.tibco.xpd.worklistfacade.resource.mapper.WorkListFacadeMapperUtil;
import com.tibco.xpd.worklistfacade.resource.workingcopy.WorkListFacadeWorkingCopy;

/**
 * Sort comparator for WM Data Mapper
 * {@link WMDataMapperWorkItemAttributeElement} objects.
 * <p>
 * Sorts by 'objects that have WLF override labels' first followed by
 * numerically sequenced attribtue names.
 * 
 * 
 * @author aallway
 * @since 18 May 2015
 */
final class WorkItemAttributeSortComparator implements
        Comparator<WMDataMapperWorkItemAttributeElement> {

    private WorkListFacade workListFacade = null;

    final Pattern lastIntPattern = Pattern.compile("[0-9]$");

    /**
     * 
     */
    public WorkItemAttributeSortComparator() {
        IResource wlfFile =
                WorkListFacadeMapperUtil
                        .getFirstWorkListFacadeFileInWorkspace();

        if (wlfFile != null) {
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(wlfFile);

            if (wc instanceof WorkListFacadeWorkingCopy) {
                workListFacade =
                        ((WorkListFacadeWorkingCopy) wc).getWorkListFacade();
            }
        }
    }

    public int compare(WMDataMapperWorkItemAttributeElement o1,
            WMDataMapperWorkItemAttributeElement o2) {
        String o1BaseLabel = o1.getWorkItemAttrProperty().getName();
        String o2BaseLabel = o2.getWorkItemAttrProperty().getName();

        String o1WLFLabel = getWLFLabel(o1);
        String o2WLFLabel = getWLFLabel(o2);

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
             * Both are default attribute1 - 40 so do a compare taking number
             * into account (so attribute10 does not appear after attribute1 but
             * before attribute2
             */

            String[] nameAndNum = lastIntPattern.split(o1BaseLabel);

            if (nameAndNum != null && nameAndNum.length == 1) {
                String o1name = nameAndNum[0];
                int o1num =
                        Integer.parseInt(o1BaseLabel.substring(o1name.length()));

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

    /**
     * @param wmDataMapperWorkItemAttributeElement
     * 
     * @return the Work List Facade override label for given element or
     *         <code>null</code> if none.
     */
    private String getWLFLabel(
            WMDataMapperWorkItemAttributeElement wmDataMapperWorkItemAttributeElement) {
        String wlfLabel = null;

        if (workListFacade != null) {
            String name =
                    wmDataMapperWorkItemAttributeElement
                            .getWorkItemAttrProperty().getName();

            if (workListFacade.getWorkItemAttributes() != null) {

                for (WorkItemAttribute workItemAttribute : workListFacade
                        .getWorkItemAttributes().getWorkItemAttribute()) {

                    if (name.equals(workItemAttribute.getName())) {
                        String label = workItemAttribute.getDisplayLabel();

                        if (label != null && label.length() > 0) {
                            wlfLabel = label;
                        }

                        break;
                    }
                }
            }
        }

        return wlfLabel;
    }
}