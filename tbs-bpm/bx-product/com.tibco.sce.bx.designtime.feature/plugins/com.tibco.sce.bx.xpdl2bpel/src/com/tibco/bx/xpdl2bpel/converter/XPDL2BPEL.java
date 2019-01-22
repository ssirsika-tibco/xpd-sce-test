package com.tibco.bx.xpdl2bpel.converter;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.wst.wsdl.Definition;
import org.osgi.framework.Bundle;
import org.osgi.framework.Version;

import com.tibco.bx.xpdl2bpel.ConverterActivator;
import com.tibco.bx.xpdl2bpel.Messages;
import com.tibco.bx.xpdl2bpel.N2PEConstants;
import com.tibco.bx.xpdl2bpel.analyzer.Analyzer;
import com.tibco.bx.xpdl2bpel.analyzer.AnalyzerProcess;
import com.tibco.bx.xpdl2bpel.converter.internal.ConvertDataField;
import com.tibco.bx.xpdl2bpel.converter.internal.ConvertPartnerLinkType;
import com.tibco.bx.xpdl2bpel.converter.internal.ConvertProcess;
import com.tibco.bx.xpdl2bpel.converter.internal.ConverterContext;
import com.tibco.bx.xpdl2bpel.util.BPELUtils;
import com.tibco.bx.xpdl2bpel.util.WSDLUtils;
import com.tibco.bx.xpdl2bpel.util.XPDLUtils;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;


/**
 * A class that performs conversion of an entire XPDL package to a BPEL package.
 */
public class XPDL2BPEL {

    private static String fileSep = System.getProperty("file.separator"); //$NON-NLS-1$

    private ConverterContext context;
    protected int defaultId = 1;

    /**
     * Initiate the converter from an XPDL file
     * @param xpdlFile The XPDL file to convert.
     */
    public XPDL2BPEL(IFile xpdlFile) {
		context = new ConverterContext(xpdlFile);
	}

    /**
     * Initiate the converter from an XPDL instance
     * @param xpdlModelRoot the XPDL root instance
     */
    public XPDL2BPEL(EObject xpdlModelRoot) {
		context = new ConverterContext(xpdlModelRoot);
	}
    
    public ConverterContext getContext() {
    	return context;
    }

	/** Convert the XPDL in to BPEL.
     * @param bpelDirectory The directory where the result BPEL file will be saved.
     */
    public IStatus convertToBPEL(final String bpelDirectory) {
        Package topPackage = context.getXpdlPackage();
		if (topPackage == null) {
        	return ConverterActivator.createErrorStatus(Messages.getString("XPDL2BPEL.ErrorMsg.failedToLoadXPDL"), null); //$NON-NLS-1$
        }

        context.setOutputFilePath(bpelDirectory);
        
        ConvertDataField.convertDataFieldsToBPEL(context, topPackage);
        ConvertPartnerLinkType.convertPartnerLinkTypesToBPEL(context, topPackage.getPartnerLinkTypes());

        List<Process> processes = topPackage.getProcesses();
        for (Process process : processes) {
            if(XPDLUtils.hasN2Destination(process)){
                try {
					convertProcess(process);
				} catch (ConversionException e) {
					context.logError("Error occurred while converting the process " + process.getName(), e);
				}
            }
        }

        if (!context.getConversionStatus().isOK()) {
        	ConverterActivator.log(context.getConversionStatus());
        }
        return context.getConversionStatus();
    }
    
	/** Convert the XPDL in to BPEL.
     * @param bpelDirectory The directory where the result BPEL file will be saved.
     * @param isPageFlowEngineTarget True when deploying to page flow engine.
     */
    public IStatus convertProcessToBPEL(Process xpdProcess, final String bpelDirectory, boolean isPageFlowEngineTarget) {
        Package topPackage = context.getXpdlPackage();
		if (topPackage == null) {
        	return ConverterActivator.createErrorStatus(Messages.getString("XPDL2BPEL.ErrorMsg.failedToLoadXPDL"), null); //$NON-NLS-1$
        }

        if(!XPDLUtils.hasN2Destination(xpdProcess)){
            return Status.CANCEL_STATUS;
        }

        context.setOutputFilePath(bpelDirectory);
        context.setPageFlowEngineTarget(isPageFlowEngineTarget);
        
        ConvertDataField.convertDataFieldsToBPEL(context, topPackage);
        ConvertPartnerLinkType.convertPartnerLinkTypesToBPEL(context, topPackage.getPartnerLinkTypes());

        try {
			convertProcess(xpdProcess);
		} catch (ConversionException e) {
			context.logError("Error occurred while converting the process " + xpdProcess.getName(), e);
		}

        if (!context.getConversionStatus().isOK()) {
        	ConverterActivator.log(context.getConversionStatus());
        }
        return context.getConversionStatus();
    }

	private void convertProcess(Process xpdProcess) throws ConversionException {
		context.setAnalyzer(new Analyzer(xpdProcess));
        AnalyzerProcess analyzerProcess = context.getAnalyzer().getProcesses().get(0);
        org.eclipse.bpel.model.Process bpelProcess = ConvertProcess.convertProcessToBPELProcess(context, analyzerProcess);
        
        //add the "bxVersion" attribute to the process to note which version of converter produced this process
		Bundle bundle = Platform.getBundle(ConverterActivator.PLUGIN_ID);
		Version version = new Version((String) bundle.getHeaders().get("Bundle-Version")); //$NON-NLS-1$
		String versionString = String.format("%d.%d.%d", new Object[] {version.getMajor(), version.getMinor(), version.getMicro()});//$NON-NLS-1$
		BPELUtils.addExtensionAttribute(bpelProcess, "bxVersion", versionString); //$NON-NLS-1$
		if (context.getAnalyzer().getOrgModelVersion()!=null) {
			BPELUtils.addExtensionAttribute(bpelProcess, "orgModelVersion", context.getAnalyzer().getOrgModelVersion().toString()); //$NON-NLS-1$
		}

        try {
    		IFolder destFolder = ResourcesPlugin.getWorkspace().getRoot().getFolder(new Path(context.getOutputFilePath()));
        	String[] processIds = context.getProcessIdsWithFaultDefinition();
        	for (String processId : processIds) {
        		NamedElement processOrInterface = Xpdl2WorkingCopyImpl.locateProcess(processId);
        		if (processOrInterface == null) {
        			processOrInterface = Xpdl2WorkingCopyImpl.locateProcessInterface(processId);
        		}
        		if (processOrInterface == null) {
        			continue;
        		}
            	IFile wsdlFile = destFolder.getFile(processOrInterface.getName() + "-fault.wsdl"); //$NON-NLS-1$
        		if (wsdlFile.exists()) {
        			wsdlFile.delete(true, null);
        		}

        		Definition definition = context.getFaultDefinition(processOrInterface);
        		String wsdlFilePath = wsdlFile.getFullPath().toString();
    			URI wsdlURI = URI.createPlatformResourceURI(wsdlFilePath, false);
    			WSDLUtils.saveWSDLFile(wsdlURI, definition);
			}

            String fileName = context.getOutputFilePath() + fileSep + bpelProcess.getName() + N2PEConstants.BPEL_EXTENSION;
            BPELUtils.saveBPELFile(fileName, bpelProcess, context.getPrefixToNamespaceMap());
        } catch (Exception e) {
            context.logError(Messages.getString("XPDL2BPEL.ErrorMsg.failedToSaveBPEL") + e.getMessage(), e); //$NON-NLS-1$
        }
	}

}

