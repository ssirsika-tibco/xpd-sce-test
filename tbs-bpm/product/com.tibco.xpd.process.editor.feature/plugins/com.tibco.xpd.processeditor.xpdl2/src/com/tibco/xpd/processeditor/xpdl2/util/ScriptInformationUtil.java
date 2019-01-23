/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.util;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author nwilson
 */
public final class ScriptInformationUtil {
    /**  */
    private static final String SCRIPT_PREFIX = "Script"; //$NON-NLS-1$

    private ScriptInformationUtil() {
    }

    /**
     * @param act
     * @return
     */
    public static String getNextScriptName(Activity act, DirectionType direction) {
        int max = 0;
        max = getMaxEoIndex(act, direction, max);

        return SCRIPT_PREFIX + (max + 1);
    }

    /**
     * @param scriptDataMapper
     * @return
     */
    public static String getNextScriptName(ScriptDataMapper scriptDataMapper) {
        int max = 0;
        max = getMaxIndex(scriptDataMapper, max);

        return SCRIPT_PREFIX + (max + 1);
    }

    /**
     * @param scriptDataMapper
     * @param max
     * @return
     */
    private static int getMaxIndex(ScriptDataMapper scriptDataMapper, int max) {

        // get unmapped scripts
        Collection<ScriptInformation> scripts =
                EcoreUtil.copyAll(scriptDataMapper.getUnmappedScripts());
        // also get mapped scripts
        for (DataMapping mapping : scriptDataMapper.getDataMappings()) {
            List<?> others =
                    Xpdl2ModelUtil.getOtherElementList(mapping,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_Script());
            for (Object other : others) {
                if (other instanceof ScriptInformation) {
                    ScriptInformation information = (ScriptInformation) other;
                    scripts.add(information);
                }
            }
        }

        for (ScriptInformation script : scripts) {
            String name = script.getName();
            if (name != null && name.startsWith(SCRIPT_PREFIX)) {
                String number = name.substring(SCRIPT_PREFIX.length());
                if (number.length() > 0) {
                    try {
                        int index = Integer.parseInt(number);
                        max = Math.max(max, index);
                    } catch (NumberFormatException e) {
                        // Ignore
                    }
                }
            }
        }

        return max;
    }

    /**
     * @param act
     * @param direction
     * @return
     */
    private static int getMaxEoIndex(EObject eo, DirectionType direction,
            int max) {
        if (eo instanceof OtherElementsContainer) {
            max = getMaxIndex((OtherElementsContainer) eo, direction, max);
        }
        for (Object next : eo.eContents()) {
            EObject nextEo = (EObject) next;
            max = getMaxEoIndex(nextEo, direction, max);
        }
        return max;
    }

    /**
     * @param act
     * @param direction
     * @param max
     * @return
     */
    private static int getMaxIndex(OtherElementsContainer container,
            DirectionType direction, int max) {
        List<?> others =
                Xpdl2ModelUtil.getOtherElementList(container,
                        XpdExtensionPackage.eINSTANCE.getDocumentRoot_Script());
        for (Object other : others) {
            if (other instanceof ScriptInformation) {
                ScriptInformation information = (ScriptInformation) other;
                if (direction.equals(information.getDirection())) {
                    String name = information.getName();
                    if (name != null && name.startsWith(SCRIPT_PREFIX)) {
                        String number = name.substring(SCRIPT_PREFIX.length());
                        if (number.length() > 0) {
                            try {
                                int index = Integer.parseInt(number);
                                max = Math.max(max, index);
                            } catch (NumberFormatException e) {
                                // Ignore
                            }
                        }
                    }
                }
            }
        }
        return max;
    }

    /**
     * @param act
     * @param dir
     * @param name
     * @return
     */
    public static boolean scriptNameExists(Activity act, DirectionType dir,
            String name) {
        // TODO Auto-generated method stub
        return internalScriptNameExists(act, dir, name);
    }

    /**
     * @param act
     * @param dir
     * @param name
     * @return
     */
    private static boolean internalScriptNameExists(EObject eo,
            DirectionType dir, String name) {
        boolean exists = false;
        if (eo instanceof OtherElementsContainer) {
            exists = internalNameExists((OtherElementsContainer) eo, dir, name);
        }
        if (!exists) {
            for (Object next : eo.eContents()) {
                EObject nextEo = (EObject) next;
                exists = internalScriptNameExists(nextEo, dir, name);
                if (exists) {
                    break;
                }
            }
        }
        return exists;
    }

    /**
     * @param eo
     * @param dir
     * @param name
     * @return
     */
    private static boolean internalNameExists(OtherElementsContainer eo,
            DirectionType dir, String name) {
        boolean exists = false;
        List<?> others =
                Xpdl2ModelUtil.getOtherElementList(eo,
                        XpdExtensionPackage.eINSTANCE.getDocumentRoot_Script());
        for (Object other : others) {
            if (other instanceof ScriptInformation) {
                ScriptInformation information = (ScriptInformation) other;
                if (dir.equals(information.getDirection())) {
                    String otherName = information.getName();
                    if (name.equals(otherName)) {
                        exists = true;
                        break;
                    }
                }
            }
        }
        return exists;
    }

}
