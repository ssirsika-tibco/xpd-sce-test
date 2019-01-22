/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.developer.resolution;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.implementer.script.Xpdl2WsdlUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Resolution to set web service activity transport.
 * 
 * @author aallway
 * @since 3.3 (13 May 2010)
 */
public abstract class AbstractSetWebServiceTransportResolution extends
        AbstractWorkingCopyResolution {

    protected abstract String getTransportType();

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        if (target instanceof Activity) {
            WebServiceOperation wso =
                    Xpdl2ModelUtil.getWebServiceOperation((Activity) target);
            if (wso != null) {
                CompoundCommand cmd = new CompoundCommand();

                cmd.append(Xpdl2ModelUtil
                        .getSetOtherAttributeCommand(editingDomain,
                                wso,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_Transport(),
                                getTransportType()));

                return cmd;
            }
        }
        return null;
    }

    public static class SetWebServiceTransportSoapHttpResolution extends
            AbstractSetWebServiceTransportResolution {

        @Override
        protected String getTransportType() {
            return Xpdl2WsdlUtil.SOAP_OVER_HTTP_URL;
        }

    }

    public static class SetWebServiceTransportSoapJmsResolution extends
            AbstractSetWebServiceTransportResolution {

        @Override
        protected String getTransportType() {
            return Xpdl2WsdlUtil.SOAP_OVER_JMS_URL;
        }

    }

    public static class SetWebServiceTransportVirutalisationResolution extends
            AbstractSetWebServiceTransportResolution {

        @Override
        protected String getTransportType() {
            return Xpdl2WsdlUtil.SERVICE_VIRTUALIZATION_URL;
        }

    }
}
