/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.analyst.resources.xpdl2.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Process;

/**
 * @author kupadhya
 * 
 */
public class ProcessInfoUtil {
    /**
     * @param process
     *            The process.
     * @return a list of ids which are valid performers
     */
    public static List<String> getValidPerformerIdList(
            List<NamedElement> performerList) {
        List<String> performerIdList = new ArrayList<String>();
        if (performerList == null) {
            return performerIdList;
        }
        for (NamedElement namedElement : performerList) {
            performerIdList.add(namedElement.getId());
        }
        return performerIdList;
    }

    /**
     * @param process
     *            The process.
     * @return a list of names which are valid performers
     */
    public static List<String> getValidPerformerNameList(
            List<NamedElement> performerList) {
        List<String> performerIdList = new ArrayList<String>();
        if (performerList == null) {
            return performerIdList;
        }
        for (NamedElement namedElement : performerList) {
            performerIdList.add(namedElement.getName());
        }
        return performerIdList;
    }

    /**
     * @param process
     *            The process.
     * @return a list of valid performers
     */
    public static List<NamedElement> getValidPerformerList(Process process) {

        List<NamedElement> participantList = new ArrayList<NamedElement>();
        if (process == null) {
            return participantList;
        }
        Package xpdlPackage = process.getPackage();

        List<?> packageParticipants = xpdlPackage.getParticipants();
        for (Iterator<?> iter = packageParticipants.iterator(); iter.hasNext();) {
            Participant partic = (Participant) iter.next();
            participantList.add(partic);
        }

        List<?> processParticipants = process.getParticipants();
        for (Iterator<?> iter = processParticipants.iterator(); iter.hasNext();) {
            Participant partic = (Participant) iter.next();
            participantList.add(partic);
        }

        List<?> pkgFields = xpdlPackage.getDataFields();
        for (Iterator<?> iter = pkgFields.iterator(); iter.hasNext();) {
            DataField data = (DataField) iter.next();

            if (data.getDataType() instanceof BasicType
                    && BasicTypeType.PERFORMER_LITERAL.equals(((BasicType) data
                            .getDataType()).getType())) {
                participantList.add(data);
            }
        }

        List<?> procFields = process.getDataFields();
        for (Iterator<?> iter = procFields.iterator(); iter.hasNext();) {
            DataField data = (DataField) iter.next();

            if (data.getDataType() instanceof BasicType
                    && BasicTypeType.PERFORMER_LITERAL.equals(((BasicType) data
                            .getDataType()).getType())) {
                participantList.add(data);
            }
        }

        List procParams = ProcessInterfaceUtil.getAllFormalParameters(process);
        for (Iterator<?> iter = procParams.iterator(); iter.hasNext();) {
            FormalParameter data = (FormalParameter) iter.next();

            if (data.getDataType() instanceof BasicType
                    && BasicTypeType.PERFORMER_LITERAL.equals(((BasicType) data
                            .getDataType()).getType())) {
                participantList.add(data);
            }
        }
        return participantList;
    }
}
