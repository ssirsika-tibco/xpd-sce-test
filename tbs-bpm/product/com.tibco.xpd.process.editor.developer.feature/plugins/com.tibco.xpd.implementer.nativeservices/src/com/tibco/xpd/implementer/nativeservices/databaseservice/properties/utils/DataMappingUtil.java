/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.implementer.nativeservices.databaseservice.properties.utils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tibco.xpd.implementer.nativeservices.internal.Messages;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.TaskService;

/**
 * Utility class to help getting and identifying unique data base parameter name
 * (used by database parameter mapping).
 * <p>
 * Use:
 * <ul>
 * <li><code>{@link #getUniqueParameterName(TaskService, String)}</code> - to
 * get a unique parameter name from the <code>TaskService</code> object given
 * based on the name pattern provided.</li>
 * <li><code>{@link #isParameterNameUnique(TaskService, String)}</code> - to
 * check if the given parameter name is unique in the given
 * <code>TaskService</code> object.</li>
 * </ul>
 * </p>
 * 
 * @author njpatel
 */
public final class DataMappingUtil {

    // The formal parameter name pattern
    private static final String ID_PATTERN =
            Messages.DataMappingUtil_DefaultParameterNamePattern;

    /**
     * Get a unique parameter name for the <code>TaskService</code> object based
     * on pattern {@link #ID_PATTERN}. If the pattern provided is not a pattern
     * then a pattern will be created; this will be the pattern name with the
     * string ' {0}' concated to it.
     * 
     * @param taskService
     * @return Unique parameter name
     */
    public static String getUniqueParameterName(TaskService taskService,
            DirectionType directionType) {
        String id = ""; //$NON-NLS-1$
        String pattern = ID_PATTERN;

        if (taskService != null && pattern != null) {
            if (!isPattern(pattern)) {
                // Create a pattern
                pattern += " {0}"; //$NON-NLS-1$
            }

            // Get default id
            int idx = 1;
            id = MessageFormat.format(pattern, new Object[] { idx });

            List<String> parameters = getParameters(taskService, directionType);

            if (parameters != null) {
                // Find the parameter name that hasn't been used yet based on
                // the pattern given
                while (parameters.contains(id)) {
                    id = MessageFormat.format(pattern, new Object[] { ++idx });
                }
            }
        } else {
            throw new NullPointerException(
                    "Null parameter passed to getUniqueParameterName()."); //$NON-NLS-1$
        }

        return id;
    }

    /**
     * Check whether the given parameter name is unique within the given
     * <code>TaskService</code> object.
     * 
     * @param taskService
     * @param name
     * @param directionType
     * @return <code>true</code> if the given name is unique, <code>false</code>
     *         otherwise.
     */
    public static boolean isParameterNameUnique(TaskService taskService,
            String name, DirectionType directionType) {
        boolean isUnique = true;

        if (taskService != null && name != null) {
            List<String> parameters = getParameters(taskService, directionType);

            if (parameters != null) {
                // Find the first occurrence of the name and the last, if the
                // two values are equal then this name only appears once, thus
                // unique
                int firstIndex = parameters.indexOf(name);
                int lastIndex = parameters.lastIndexOf(name);

                isUnique = firstIndex == lastIndex;
            }

        } else {
            throw new NullPointerException(
                    "Null parameter passed to isParameterNameUnique."); //$NON-NLS-1$
        }

        return isUnique;
    }

    /**
     * Get the formal parameter names from the <code>DataMapping</code> of the
     * MessageIn and MessageOut sections of the <code>TaskService</code>.
     * 
     * @param taskService
     * @return
     */
    private static List<String> getParameters(TaskService taskService,
            DirectionType directionType) {
        List<String> parameters = null;
        if (taskService != null) {
            parameters = new ArrayList<String>();
            // Get parameter names from both the MessageIn and MessageOut
            // sections
            if (DirectionType.IN_LITERAL.equals(directionType)
                    || DirectionType.INOUT_LITERAL.equals(directionType)) {
                parameters.addAll(getParameters(taskService.getMessageIn()));
            }
            if (DirectionType.OUT_LITERAL.equals(directionType)
                    || DirectionType.INOUT_LITERAL.equals(directionType)) {
                parameters.addAll(getParameters(taskService.getMessageOut()));
            }
        }

        return parameters;
    }

    /**
     * Get the formal parameter names from the Message's
     * <code>DataMapping</code> section.
     * 
     * @param message
     * @return List of parameter names found.
     */
    private static List<String> getParameters(Message message) {
        List<String> parameters = new ArrayList<String>();
        if (message != null) {
            for (Iterator iter = message.getDataMappings().iterator(); iter
                    .hasNext();) {
                DataMapping mapping = (DataMapping) iter.next();

                if (mapping != null) {
                    parameters.add(mapping.getFormal());
                }
            }
        }

        return parameters;
    }

    /**
     * Check if the given string is a message format pattern.
     * 
     * @param pattern
     * @return
     */
    private static boolean isPattern(String pattern) {
        String value = MessageFormat.format(pattern, new Object[] { 1 });

        // If no change to pattern string then it's not a pattern
        return !value.equals(pattern);
    }

}
