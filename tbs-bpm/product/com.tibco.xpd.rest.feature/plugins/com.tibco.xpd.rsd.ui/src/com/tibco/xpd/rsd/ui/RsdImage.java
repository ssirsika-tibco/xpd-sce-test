/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rsd.ui;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

/**
 * Images used by RSD UI plug-in.
 * 
 * To get an image or an image descriptor use: {@link #getImage(RsdImage)} and
 * {@link #getImageDescriptor(RsdImage)} respectively.
 * 
 * @author jarciuch
 * @since 27 Feb 2015
 */
public enum RsdImage {

    CREATE_METHOD("icons/full/ctool16/CreateMethod.png"), //$NON-NLS-1$
    CREATE_RESOURCE("icons/full/ctool16/CreateResource.png"), //$NON-NLS-1$
    CREATE_PARAMS_FROM_PATH("icons/full/ctool16/CreateParamsFromPath.png"), //$NON-NLS-1$

    ERROR("icons/Error.gif"), //$NON-NLS-1$
    FAULT("icons/full/obj16/Fault.png"), //$NON-NLS-1$

    METHOD("icons/full/obj16/Method.png"), //$NON-NLS-1$
    METHOD_GET("icons/full/obj16/MethodGet.png"), //$NON-NLS-1$
    METHOD_POST("icons/full/obj16/MethodPost.png"), //$NON-NLS-1$
    METHOD_PUT("icons/full/obj16/MethodPut.png"), //$NON-NLS-1$
    METHOD_DELETE("icons/full/obj16/MethodDelete.png"), //$NON-NLS-1$

    NEW_RSD_WIZBAN("icons/full/wizban/NewRsd.png"), //$NON-NLS-1$

    PARAMETER("icons/full/obj16/Parameter.png"), //$NON-NLS-1$
    PARAMETER_TEXT("icons/full/obj16/ParameterText.png"), //$NON-NLS-1$
    PARAMETER_BOOLEAN("icons/full/obj16/ParameterBoolean.png"), //$NON-NLS-1$
    PARAMETER_INTEGER("icons/full/obj16/ParameterInteger.png"), //$NON-NLS-1$
    PARAMETER_DECIMAL("icons/full/obj16/ParameterDecimal.png"), //$NON-NLS-1$
    PARAMETER_DATE_TIME("icons/full/obj16/ParameterDateTime.png"), //$NON-NLS-1$

    PAYLOAD_REFERENCE("icons/full/obj16/PayloadReference.png"), //$NON-NLS-1$
    PAYLOAD_REFERENCE_JSON("icons/full/obj16/PayloadReferenceJson.png"), //$NON-NLS-1$

    RSD_FILE("icons/RsdFile.png"), //$NON-NLS-1$
    RESOURCE("icons/full/obj16/Resource.png"), //$NON-NLS-1$

    SERVICE("icons/full/obj16/Service.png"), //$NON-NLS-1$
    IMG_UNPROCESSED_TEXT_TYPE("icons/UnprocessedText.png"); //$NON-NLS-1$

    private String path;

    /**
     * Construct image literal with a specified path.
     * 
     * @param path
     *            the image path relative to the root of this plug-in.
     */
    private RsdImage(String path) {
        this.path = path;
    }

    /**
     * Returns the image path relative to the root of this plug-in.
     * 
     * @return the image path relative to the root of this plug-in.
     */
    public String getPath() {
        return path;
    }

    /**
     * Returns image for the RsdImage enum literal.
     * 
     * @param rsdImage
     *            enum literal
     * @return image for the RsdImage enum literal.
     */
    public static Image getImage(RsdImage rsdImage) {
        return RsdUIPlugin.getPlugin().getImage(rsdImage);
    }

    /**
     * Returns image descriptor for the RsdImage enum literal.
     * 
     * @param rsdImage
     *            enum literal
     * @return image descriptor for the RsdImage enum literal.
     */
    public static ImageDescriptor getImageDescriptor(RsdImage rsdImage) {
        return RsdUIPlugin.getPlugin().getImageDescriptor(rsdImage);
    }
}
