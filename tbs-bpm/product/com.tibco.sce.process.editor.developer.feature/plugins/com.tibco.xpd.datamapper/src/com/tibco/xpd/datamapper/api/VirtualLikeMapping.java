/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.datamapper.api;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.datamapper.DataMapperConstants;
import com.tibco.xpd.datamapper.DataMapperPlugin;
import com.tibco.xpd.mapper.Mapping;

/**
 * This is a specialisation of {@link Mapping} specifically for the virtual
 * child content mappings that are implied by a dta mapper Like Mapping.
 * <p>
 * Note that the {@link #getMappingModel()} <b>will be the mapping model for the
 * physical Like Mapping itself.
 * 
 * @author aallway
 * @since 16 Jul 2015
 */
public class VirtualLikeMapping extends Mapping {

    private static Color lineColour = null;

    /**
     * @param source
     * @param target
     * @param likeMappingModel
     *            The model object for the parent like mapping.
     */
    public VirtualLikeMapping(Object source, Object target,
            Object likeMappingModel) {
        super(source, target, likeMappingModel);

        super.setEditable(false);
        super.setVirtual(true);

        if (PlatformUI.isWorkbenchRunning()) {
            if (lineColour == null) {
                lineColour = new Color(null, 240, 240, 240);
            }
            super.setColor(lineColour);

            Image img = null;

            img =
                    DataMapperPlugin.getDefault().getImageRegistry()
                            .get(DataMapperConstants.IMG_VIRTUAL_LIKE_MAPPING);
            setStartLineAnnotation(img);
            setEndLineAnnotation(img);

        }

    }

}
