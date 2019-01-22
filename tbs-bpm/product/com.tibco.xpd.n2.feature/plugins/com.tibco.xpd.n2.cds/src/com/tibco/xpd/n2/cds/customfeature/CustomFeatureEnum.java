/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.n2.cds.customfeature;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

import com.tibco.xpd.n2.cds.utils.CDSCustomFeatureUtils.BomPluginEnum;

/**
 * Declares attributes for custom features.
 * 
 * @author patkinso
 * @since 19 Sep 2013
 */
public enum CustomFeatureEnum {

    CDS("model.bds", EnumSet.of(BomPluginEnum.MODEL), //$NON-NLS-1$
            CDSFeatureManagerImpl.class),

    SI("model.bds", EnumSet.of(BomPluginEnum.SCRIPTABLE_INTERFACE), //$NON-NLS-1$
            SIFeatureManagerImpl.class),

    DA("da.bds", EnumSet.of(BomPluginEnum.DB_ADAPTOR), //$NON-NLS-1$
            DAFeatureManagerImpl.class);

    private Set<BomPluginEnum> bomPlugins;

    private String featureSuffix;

    private Class<? extends CustomFeatureManager> managerClazz;

    private CustomFeatureManager manager;

    private CustomFeatureEnum(String featureSuffix,
            Set<BomPluginEnum> bomPlugins,
            Class<? extends CustomFeatureManager> managerClazz) {
        this.bomPlugins = bomPlugins;
        this.featureSuffix = "." + featureSuffix; //$NON-NLS-1$
        this.managerClazz = managerClazz;
    }

    public Set<BomPluginEnum> getBomPlugins() {
        return bomPlugins;
    }

    public Set<String> getGeneratorIds() {
        return BomPluginEnum.getGeneratorIds(getBomPlugins());
    }

    /**
     * @return the featureSuffix
     */
    public String getFeatureSuffix() {
        return featureSuffix;
    }

    public CustomFeatureManager getManager() {
        if (manager == null) {
            manager = getManagerInstance();
        }
        return manager;
    }

    /**
     * @return
     */
    private CustomFeatureManager getManagerInstance() {
        try {
            Method method = managerClazz.getMethod("getInstance"); //$NON-NLS-1$
            Object o = method.invoke(null, new Object[0]);
            return (CustomFeatureManager) o;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return
     */
    public static Set<String> getAllGeneratorIDs() {
        Set<String> allGeneratorIDs = new HashSet<String>();

        for (CustomFeatureEnum value : values()) {
            allGeneratorIDs.addAll(value.getGeneratorIds());
        }
        return allGeneratorIDs;
    }

    public static Set<String> getAllFeatureSuffixes() {
        Set<String> allFeatureSuffixes = new HashSet<String>();

        for (CustomFeatureEnum value : values()) {
            allFeatureSuffixes.add(value.getFeatureSuffix());
        }
        return allFeatureSuffixes;
    }

    public static Set<BomPluginEnum> getAllBomPlugins() {
        Set<BomPluginEnum> allBomPlugins = new HashSet<BomPluginEnum>();

        for (CustomFeatureEnum value : values()) {
            allBomPlugins.addAll(value.getBomPlugins());
        }
        return allBomPlugins;
    }

}
