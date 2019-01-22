package com.tibco.bx.xpdl2bpel.converter.internal;

import javax.xml.namespace.QName;

import com.tibco.bx.xpdl2bpel.N2PEConstants;
import com.tibco.bx.xpdl2bpel.converter.ConversionException;
import com.tibco.bx.xpdl2bpel.util.BPELUtils;
import com.tibco.bx.xpdl2bpel.util.XPDLUtils;

public class ConvertWebService {

    /**
     * Convert the XPDL Message and Operation to a BPEL Receive Activity.
     * @return A BPEL Receive converted from the input Message and Operation.
     * @throws ConversionException
     */
    public static org.eclipse.bpel.model.Receive convertWebServiceOperationToBPELReceive(
            final ConverterContext context, final WebServiceOperationInfo wsoInfo,
            boolean instantiate, boolean correlateImmediate, com.tibco.xpd.xpdl2.Activity xpdlActivity, com.tibco.xpd.xpdl2.Message message) throws ConversionException {
        if (wsoInfo == null) {
            return null;
        }

        org.eclipse.bpel.model.Receive receive = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createReceive();
        receive.setCreateInstance(instantiate);
        receive.setName(context.genUniqueActivityName("receive")); //$NON-NLS-1$
        Long seconds = XPDLUtils.getMessageTimeout(xpdlActivity);
        if (seconds>0) {
            BPELUtils.addExtensionAttribute(receive, "messageTimeout", seconds.toString());
        }
        
        // Correlate immediate
        if (correlateImmediate) {
        	BPELUtils.addExtensionAttribute(receive, N2PEConstants.CORRELATE_IMMEDIATE, "yes");
        }

        configurePartnerActivity(context, receive, wsoInfo);
        org.eclipse.bpel.model.Variable inputVar = wsoInfo.createInputVariable();
        if (inputVar != null) {
            context.addVariable(inputVar);
            receive.setVariable(inputVar);
        }

        ConvertCorrelations.convert(context, xpdlActivity, receive, wsoInfo, message);
        return receive;
    }

    /**
     * Convert the XPDL Message and Operation to a BPEL Reply Activity.
     * @return A BPEL Reply
     * @throws ConversionException
     */
    public static org.eclipse.bpel.model.Reply convertWebServiceOperationToBPELReply(
            final ConverterContext context, final WebServiceOperationInfo wsoInfo
            , com.tibco.xpd.xpdl2.Activity xpdlActivity, com.tibco.xpd.xpdl2.Message message) throws ConversionException {
        if (wsoInfo == null) {
            return null;
        }

        org.eclipse.bpel.model.Reply reply = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createReply();
        reply.setName(context.genUniqueActivityName("reply")); //$NON-NLS-1$
        configurePartnerActivity(context, reply, wsoInfo);

        org.eclipse.bpel.model.Variable outputVar = wsoInfo.createOutputVariable();
        if (outputVar != null) {
            context.addVariable(outputVar);
            reply.setVariable(outputVar);
        }

        ConvertCorrelations.convert(context, xpdlActivity, reply, wsoInfo, message);
        return reply;
    }

    public static org.eclipse.bpel.model.Reply convertWebServiceOperationToBPELReplyWithFault(
            final ConverterContext context, final WebServiceOperationInfo wsoInfo, 
            com.tibco.xpd.xpdl2.Activity xpdlActivity, com.tibco.xpd.xpdl2.Message faultMessage) throws ConversionException {
        if (wsoInfo == null) {
            return null;
        }

        String faultName = faultMessage.getFaultName();

        org.eclipse.bpel.model.Reply reply = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createReply();
        reply.setName(context.genUniqueActivityName("reply")); //$NON-NLS-1$
        reply.setFaultName(new QName(wsoInfo.getWsdlDefinition().getTargetNamespace(), faultName));
        configurePartnerActivity(context, reply, wsoInfo);

		org.eclipse.bpel.model.Variable faultVar = wsoInfo.createFaultVariable(faultName);
        if (faultVar != null) {
            context.addVariable(faultVar);
            reply.setVariable(faultVar);
        }

        ConvertCorrelations.convert(context, xpdlActivity, reply, wsoInfo, faultMessage);
        return reply;
    }

    public static org.eclipse.bpel.model.Invoke convertWebServiceOperationToBPELInvoke(
            final ConverterContext context, final WebServiceOperationInfo wsoInfo
            , com.tibco.xpd.xpdl2.Activity xpdlActivity, com.tibco.xpd.xpdl2.Message message) {
        if (wsoInfo == null) {
            return null;
        }

        org.eclipse.bpel.model.Invoke invoke = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createInvoke();
        invoke.setName(context.genUniqueActivityName("invoke")); //$NON-NLS-1$
        configurePartnerActivity(context, invoke, wsoInfo);

        org.eclipse.bpel.model.Variable inputVar = wsoInfo.createInputVariable();
        if (inputVar != null) {
            context.addVariable(inputVar);
            invoke.setInputVariable(inputVar);
        }

        org.eclipse.bpel.model.Variable outputVar = wsoInfo.createOutputVariable();
        if (outputVar != null) {
            context.addVariable(outputVar);
            invoke.setOutputVariable(outputVar);
        }

        ConvertCorrelations.convert(context, xpdlActivity, invoke, wsoInfo, message);	// Not support in Studio        
        return invoke;
    }

    private static org.eclipse.bpel.model.PartnerLink configurePartnerActivity(final ConverterContext context,
                                                 org.eclipse.bpel.model.PartnerActivity partnerActivity, WebServiceOperationInfo wsoInfo) {
        partnerActivity.setOperation(wsoInfo.createOperation());

        org.eclipse.bpel.model.PartnerLink partnerLink = wsoInfo.createPartnerLinkForWebService();
        context.addPartnerLink(partnerLink);
        partnerActivity.setPartnerLink(partnerLink);

        partnerActivity.setPortType(wsoInfo.createPortType());
        return partnerLink;
    }

}
