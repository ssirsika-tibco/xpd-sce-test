/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.implementer.script;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.WebServiceOperation;

/**
 * @author nwilson
 */
public final class ActivityMessageUtil {

    /**
     * Private constructor, utility class.
     */
    private ActivityMessageUtil() {
    }

    /**
     * @param mappings
     *            The mappings list.
     * @param target
     *            The formal parameter name.
     * @param direction
     *            The mapping direction.
     * @return The DataMapping or null.
     */
    public static DataMapping findByTargetParameter(List<?> mappings,
            String target, DirectionType direction) {
        DataMapping old = null;
        if (target != null) {
            for (Object next : mappings) {
                DataMapping mapping = (DataMapping) next;
                String param;
                if (DirectionType.IN_LITERAL.equals(direction)) {
                    param = mapping.getFormal();
                } else {
                    param = mapping.getActual().getText();
                }
                if (mapping.getDirection().equals(direction)
                        && target.equals(param)) {
                    old = mapping;
                    break;
                }
            }
        }
        return old;
    }

    /**
     * @param mappings
     *            The mappings list.
     * @param target
     *            The formal parameter name.
     * @param direction
     *            The mapping direction.
     * @return The DataMapping or null.
     */
    public static DataMapping findBySourceAndTargetParameter(List<?> mappings,
            String source, String target, DirectionType direction) {
        DataMapping old = null;
        for (Object next : mappings) {
            DataMapping mapping = (DataMapping) next;
            String sourceParam;
            String targetParam;
            if (DirectionType.IN_LITERAL.equals(direction)) {
                sourceParam = mapping.getActual().getText();
                targetParam = mapping.getFormal();
            } else {
                sourceParam = mapping.getFormal();
                targetParam = mapping.getActual().getText();
            }
            if (mapping.getDirection().equals(direction) && targetParam != null
                    && sourceParam != null && target.equals(targetParam)
                    && source.equals(sourceParam)) {
                old = mapping;
                break;
            }
        }
        return old;
    }

    /**
     * @param activity
     *            The activity to get fields for.
     * @return A collection of available fields for the activity.
     */
    @SuppressWarnings("unchecked")
    public static Collection<ProcessRelevantData> getAvailableParameters(
            Activity activity) {
        Collection<ProcessRelevantData> parameters =
                new ArrayList<ProcessRelevantData>();
        Process process = activity.getProcess();
        Package pckg = process.getPackage();
        parameters.addAll(activity.getDataFields());
        parameters.addAll(process.getDataFields());
        parameters.addAll(ProcessInterfaceUtil.getAllFormalParameters(process));
        parameters.addAll(pckg.getDataFields());
        return parameters;
    }

    /**
     * @param parameters
     *            The parameters.
     * @return A collection of names.
     */
    public static Collection<String> getParameterNames(
            Collection<ProcessRelevantData> parameters) {
        Collection<String> names = new TreeSet<String>();
        for (ProcessRelevantData data : parameters) {
            names.add(getParameterName(data));
        }
        return names;
    }

    /**
     * @param object
     *            The Object to get a name for.
     * @return The name of the Object, or null if the object type is not valid.
     */
    public static String getPath(Object object) {
        String name = null;
        if (object instanceof NamedElement) {
            name = ((NamedElement) object).getName();
        } else if (object instanceof IWsdlPath) {
            name = ((IWsdlPath) object).getPath();
        } else if (object instanceof WebServiceOperation) {
            name = ((WebServiceOperation) object).getOperationName();
        } else if (object instanceof ConceptPath) {
            name = ((ConceptPath) object).getPath();
        }
        return name;
    }

    /**
     * @param target
     *            The traget object.
     * @return The name of the object.
     */
    public static String getParameterName(Object target) {
        String name = null;
        if (target instanceof ConceptPath) {
            target = ((ConceptPath) target).getItem();
        }
        if (target instanceof NamedElement) {
            name = ((NamedElement) target).getName();
        } else if (target instanceof WebServiceOperation) {
            name = ((WebServiceOperation) target).getOperationName();
        }
        return name;
    }

}
