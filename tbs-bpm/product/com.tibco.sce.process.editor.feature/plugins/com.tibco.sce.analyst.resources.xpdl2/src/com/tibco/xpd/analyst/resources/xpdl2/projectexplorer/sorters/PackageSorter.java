/*
 * Copyright (c) TIBCO Software Inc. 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.sorters;

import org.eclipse.jface.viewers.ViewerSorter;

import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.DataFieldGroup;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.EndErrorGroup;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.FormalParameterGroup;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.IntermediateMethodGroup;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.ParticipantGroup;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.ProcessGroup;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.ProcessInterfaceGroup;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.StartMethodGroup;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.TypeDeclarationGroup;

/**
 * Project Explorer uses this to sort items in the package tree view
 * 
 * @author njpatel
 * 
 */
public class PackageSorter extends ViewerSorter {

    // Order of appearance in the tree - the lower the number
    // the higher it appears in the tree

    private static final int PARAMETERS = 1;

    private static final int DATAFIELDS = 2;

    private static final int PARTICIPANTS = 3;

    private static final int TYPEDECLARATIONS = 4;

    private static final int ERROREVENTS = 5;

    private static final int STARTEVENTS = 6;

    private static final int INTERMEDIATEEVENTS = 7;

    private static final int PROCESSINTERFACES = 8;

    private static final int PROCESSES = 9;

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.viewers.ViewerComparator#category(java.lang.Object)
     */
    public int category(Object element) {
        if (element instanceof FormalParameterGroup) {
            return PARAMETERS;
        } else if (element instanceof DataFieldGroup) {
            return DATAFIELDS;
        } else if (element instanceof ParticipantGroup) {
            return PARTICIPANTS;
        } else if (element instanceof TypeDeclarationGroup) {
            return TYPEDECLARATIONS;
        } else if (element instanceof EndErrorGroup) {
            return ERROREVENTS;
        } else if (element instanceof StartMethodGroup) {
            return STARTEVENTS;
        } else if (element instanceof IntermediateMethodGroup) {
            return INTERMEDIATEEVENTS;
        } else if (element instanceof ProcessInterfaceGroup) {
            return PROCESSINTERFACES;
        } else if (element instanceof ProcessGroup) {
            return PROCESSES;
        } else {
            return super.category(element);
        }
    }

}
