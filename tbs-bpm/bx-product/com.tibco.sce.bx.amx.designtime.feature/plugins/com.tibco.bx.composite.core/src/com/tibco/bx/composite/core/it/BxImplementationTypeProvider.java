package com.tibco.bx.composite.core.it;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;

import com.tibco.amf.sca.componenttype.implementation.ImplementationTypeProvider;
import com.tibco.amf.sca.model.extensionpoints.Implementation;
import com.tibco.bx.amx.model.service.BxServiceImplementation;
import com.tibco.bx.composite.core.BxCompositeCoreActivator;
import com.tibco.bx.composite.core.it.internal.BxImplementationBuilder;
import com.tibco.bx.composite.core.util.BxCompositeCoreConstants;

/**
 * 
 * @author 
 * this class are extended by BPELImplementationTypeProvider and
 * N2PEImplementationTypeProvider now. used for creating implementationType
 */
public abstract class BxImplementationTypeProvider implements
		ImplementationTypeProvider {

	public String getImplementationTypeId() {
		return BxCompositeCoreConstants.N2PE_SCA_IMPLEMENTATION_TYPE;
	}
	
	public abstract boolean isSupported(String path);
	
	/**
	 * this method used to get the httpclient jndi name of an activity with the xpdlId
	 * if the activity has a participant
	 * it does work only for N2PEImplementation
	 * 
	 * @param implementationFile
	 * @param xpdlId: the activity xpdlId
	 * @return
	 */
	public String getHttpclientJNDIName(IFile implementationFile,String xpdlId){
		return null;
	}
	
	/**
	 * this method used to get the endpoint uri of an activity with the xpdlId
	 * if the activity has a participant
	 * it does work only for N2PEImplementation
	 * @param implementationFile
	 * @param xpdlId: the activity xpdlId
	 * @return
	 */
	public String getEndpointUri(IFile implementationFile,String xpdlId){
		return null;
	}
	
	abstract protected IFile[] getBPELFiles(String path, URI compositeLocation, BxServiceImplementation bxImplementation);

	/**
	 * get the implementation
	 * @return
	 */
	protected abstract BxServiceImplementation newImplementation();

	public Implementation createImplementation(String path, URI compositeLocation) {
		if (!isSupported(path)) {
			return null;
		}

		try {
			BxServiceImplementation bxImplementation = newImplementation();
			
			IFile implementationFile = ResourcesPlugin.getWorkspace().getRoot().getFile(
					new Path(path));
			IFile[] bpelFiles = getBPELFiles(path, compositeLocation, bxImplementation);
			if (bpelFiles == null || bpelFiles.length == 0) {
				return null;
			}
			
			BxImplementationBuilder bxImplBuilder = new BxImplementationBuilder(
					bpelFiles,
					compositeLocation, implementationFile,
					bxImplementation, this);
			bxImplBuilder.buildImplementation();
			
			return bxImplementation;
		} catch (Exception e) {
			BxCompositeCoreActivator.logError(e.getMessage(), e);
		}
		
		return null;
	}

}
