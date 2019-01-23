/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.destinations.preferences;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.tibco.xpd.resources.ui.internal.destination.GlobalDestinationUtil;

/**
 * Utility class for encoding and decoding a DestinationSetting List to and from
 * String format so that it can be stored in the preference store.
 * 
 * @author nwilson
 * 
 */
public final class DestinationPreferencesCodec {

    private static Pattern escapePattern = Pattern.compile("(?=[/,;:\\|])"); //$NON-NLS-1$

    private static Pattern splitComponents = Pattern.compile("(?<=[^/]):"); //$NON-NLS-1$

    private static Pattern splitComponentParams = Pattern
            .compile("(?<=[^/])\\|"); //$NON-NLS-1$

    /**
     * Private constructor.
     */
    private DestinationPreferencesCodec() {
    }

    /**
     * Encodes a DestinationSetting List as a String.
     * 
     * @param settings
     *            The list of destination settings.
     * @return The encoded string.
     */
    public static String encode(List<DestinationSetting> settings) {
        StringBuilder builder = new StringBuilder();
        for (DestinationSetting setting : settings) {
            if (builder.length() != 0) {
                builder.append(',');
            }
            builder.append(getString(setting));
        }
        return builder.toString();
    }

    /**
     * @param setting
     *            A destination setting.
     * @return an encoded String for the setting.
     */
    private static String getString(DestinationSetting setting) {
        StringBuilder builder = new StringBuilder();
        builder.append(escape(setting.getName()));
        builder.append(';');
        builder.append(setting.getGlobalDestinationId());
        builder.append(';');
        boolean first = true;
        for (DestinationComponentSetting component : setting.getComponents()) {
            if (first) {
                first = false;
            } else {
                builder.append(':');
            }
            builder.append(component.getComponentId());
            builder.append('|');
            builder.append(component.getValidationDestinationId());
        }
        return builder.toString();
    }

    /**
     * Decodes a String into a list of DestinationSetting objects. The string
     * must be correctly formatted as per the strings produced by the encode
     * method in this class.
     * 
     * @param string
     *            The string to decode.
     * @return The list of destination settings.
     */
    public static List<DestinationSetting> decode(String string) {
        List<DestinationSetting> settings = new ArrayList<DestinationSetting>();
        String[] settingStrings = GlobalDestinationUtil
                .getDestinationSettings(string);
        for (String settingString : settingStrings) {
            String[] dests = GlobalDestinationUtil
                    .getDestinationDetails(settingString);
            if (dests.length == 2 || dests.length == 3) {
                DestinationSetting setting = new DestinationSetting(
                        unescape(dests[0]));
                setting.setGlobalDestinationId(dests[1]);
                if (dests.length == 3) {
                    String[] componentStrings = splitComponents.split(dests[2]);
                    for (String componentString : componentStrings) {
                        String[] componentIds = splitComponentParams
                                .split(componentString);
                        if (componentIds.length == 2) {
                            DestinationComponentSetting component = new DestinationComponentSetting(
                                    componentIds[0]);
                            component
                                    .setValidationDestinationId(componentIds[1]);
                            setting.addComponent(component);
                        }
                    }
                }
                settings.add(setting);
            }
        }
        return settings;
    }

    /**
     * @param s
     *            The unescaped string.
     * @return The escaped string.
     */
    private static String escape(String s) {
        return escapePattern.matcher(s).replaceAll("/"); //$NON-NLS-1$
    }

    /**
     * @param s
     *            The escaped string.
     * @return The unescaped string.
     */
    private static String unescape(String s) {
        return GlobalDestinationUtil.unescape(s);
    }
}
