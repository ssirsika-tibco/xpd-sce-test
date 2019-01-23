/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.util.GatewayObjectUtil;
import com.tibco.xpd.processwidget.adapters.GatewayType;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdl2.Activity;

/**
 * SetGatewayTypeResolution
 * 
 * 
 * @author aallway
 * @since 3.3 (10 Nov 2009)
 */
public abstract class SetGatewayTypeResolution extends
        AbstractWorkingCopyResolution {
    private GatewayType gatewayType;

    SetGatewayTypeResolution(GatewayType gatewayType) {
        super();
        this.gatewayType = gatewayType;
    }

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        if (target instanceof Activity) {
            Activity activity = (Activity) target;

            return GatewayObjectUtil.getSetGatewayTypeCommand(editingDomain,
                    activity,
                    gatewayType);
        }
        return null;
    }

    @Override
    protected String getResolutionLabel(String propertiesLabel, IMarker marker) {
        return String.format(propertiesLabel, gatewayType.toString());
    }

    @Override
    protected String getResolutionDescription(String propertiesLabel,
            IMarker marker) {
        return String.format(propertiesLabel, gatewayType.toString());
    }

    /**
     * SetGatewayTypeExclusiveDataResolution
     * 
     * @author aallway
     * @since 3.3 (10 Nov 2009)
     */
    public static class SetGatewayTypeExclusiveDataResolution extends
            SetGatewayTypeResolution {
        public SetGatewayTypeExclusiveDataResolution() {
            super(GatewayType.EXCLUSIVE_DATA_LITERAL);
        }
    }

    /**
     * SetGatewayTypeExclusiveEventResolution
     * 
     * @author aallway
     * @since 3.3 (10 Nov 2009)
     */
    public static class SetGatewayTypeExclusiveEventResolution extends
            SetGatewayTypeResolution {
        public SetGatewayTypeExclusiveEventResolution() {
            super(GatewayType.EXLCUSIVE_EVENT_LITERAL);
        }
    }

    /**
     * SetGatewayTypeParallelResolution
     * 
     * @author aallway
     * @since 3.3 (10 Nov 2009)
     */
    public static class SetGatewayTypeParallelResolution extends
            SetGatewayTypeResolution {
        public SetGatewayTypeParallelResolution() {
            super(GatewayType.PARALLEL_LITERAL);
        }
    }

    /**
     * SetGatewayTypeComplexResolution
     * 
     * @author aallway
     * @since 3.3 (10 Nov 2009)
     */
    public static class SetGatewayTypeComplexResolution extends
            SetGatewayTypeResolution {
        public SetGatewayTypeComplexResolution() {
            super(GatewayType.COMPLEX_LITERAL);
        }
    }

    /**
     * SetGatewayTypeInclusiveResolution
     * 
     * @author aallway
     * @since 3.3 (10 Nov 2009)
     */
    public static class SetGatewayTypeInclusiveResolution extends
            SetGatewayTypeResolution {
        public SetGatewayTypeInclusiveResolution() {
            super(GatewayType.INCLUSIVE_LITERAL);
        }
    }
}
