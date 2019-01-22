/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.datamapper.api;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.mapper.Mapping;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Data Mapper utility class
 * 
 * @author Ali
 * @since 20 Jan 2015
 */
public class DataMapperUtils {

    /**
     * @param expression
     * @return
     */
    public static ScriptDataMapper getExistingScriptDataMapper(
            Expression expression) {
        if (expression != null && !expression.getMixed().isEmpty()) {

            EList<?> scriptDataMapperList =
                    (EList<?>) expression
                            .getMixed()
                            .get(XpdExtensionPackage.eINSTANCE.getDocumentRoot_ScriptDataMapper(),
                                    true);

            // we expect to have one such element in the expression
            if (scriptDataMapperList.size() == 1
                    && scriptDataMapperList.get(0) != null) {
                return (ScriptDataMapper) scriptDataMapperList.get(0);
            }
        }
        return null;
    }

    /**
     * @param mapping
     *            The mapping to check.
     * @return true if it is a physical 'like' mapping (NOT a child
     *         {@link VirtualLikeMapping}
     */
    public static boolean isLikeMapping(Mapping mapping) {
        boolean isLike = false;
        if (!(mapping instanceof VirtualLikeMapping)) {
            Object model = mapping.getMappingModel();
            if (model instanceof DataMapping) {
                DataMapping dm = (DataMapping) model;
                isLike =
                        Xpdl2ModelUtil.getOtherAttributeAsBoolean(dm,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_LikeMapping());
            }
        }
        return isLike;
    }
}
