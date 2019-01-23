/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.deploy.webdav;

import java.io.IOException;

import org.eclipse.webdav.IResponse;

import com.tibco.xpd.deploy.webdav.ui.internal.Messages;

/**
 * @author glewis
 *
 */
public class BaseJmxDeploymentRecord {
	private static String 	postfix = ".deploymentrecord"; //$NON-NLS-1$    
	private WebDAVServer webDavServer;
	
	/*
	 * =====================================================
	 * CONSTRUCTOR : DeploymentRecord
	 * =====================================================
	 */
	/**
	 * Create the object to perform Deployment Record operations
	 *
	 * @param webDavObject		WebDav support object
	 */
	public BaseJmxDeploymentRecord(WebDAVServer webDavServer)
	{
	    this.webDavServer = webDavServer;
	}
	
	/*
	 * =====================================================
	 * METHOD : createRecord
	 * =====================================================
	 */
	/**
	 * Creates a Deployment record for a deployed artifact
	 *
	 * @param component		The componant that this object is deployed to
	 * @param remoteUrl		The WebDav URL that is used to deploy this artifact
	 * @return
	 */
	public String createRecord( String deploymentTarget,
								String           remoteUrl )
	{
		String resultMessage = ""; //$NON-NLS-1$  
		try {
			IResponse response = webDavServer.putTextAsFile(deploymentTarget,
													  getDeploymentRecordName(remoteUrl));

			int statusCode = response.getStatusCode();

			if (statusCode != IResponse.SC_CREATED && statusCode != IResponse.SC_NO_CONTENT)
			{
				String statusMessage = response.getStatusMessage();
				String responseMessage = "\n" + statusCode + ':' + statusMessage; //$NON-NLS-1$ 
				resultMessage += responseMessage + "\n"; //$NON-NLS-1$  
			}
		} catch (IOException e) {
		    WebDAVPlugin.getDefault().getLogger().error(e);
			resultMessage = Messages.BaseJmxDeploymentRecord_FailedToCreateRecord_message; 
		}
		
		return resultMessage;
	}
	
	/*
	 * =====================================================
	 * METHOD : removeRecord
	 * =====================================================
	 */
	/**
	 * Removes a Deployment record for a deployed artifact
	 *
	 * @param remoteUrl		The WebDav URL that was used to deploy this artifact
	 * @return
	 */
	public String removeRecord( String remoteUrl )
	{
		String resultMessage = ""; //$NON-NLS-1$  
		try
		{
			IResponse response = webDavServer.deleteFile(getDeploymentRecordName(remoteUrl));

			int statusCode = response.getStatusCode();
			if ((statusCode != IResponse.SC_NO_CONTENT) && (statusCode == IResponse.SC_OK))
			{
				String statusMessage = response.getStatusMessage();
				String responseMessage = "\n" + statusCode + ':' + statusMessage; //$NON-NLS-1$ 
				resultMessage += responseMessage + "\n"; //$NON-NLS-1$  
			}
		}
		catch (IOException e)
		{
		    WebDAVPlugin.getDefault().getLogger().error(e);
			resultMessage = Messages.BaseJmxDeploymentRecord_FailedToRemoveRecord_message;
		}

		return resultMessage;
	}
	
	
	
	/*
	 * =====================================================
	 * METHOD : isObjectViewable
	 * =====================================================
	 */
	/**
	 * Returns if the supplied object should be a viewable deployment object
	 *
	 * @param remoteURL		True is viewable, otherwise false
	 * @return
	 */
	public static boolean isObjectViewable( String remoteURL )
	{
		boolean retval = true;
		
		if( remoteURL.endsWith(postfix) )
		{
			retval = false;
		}
		
		return retval;
	}
	
	/*
	 * =====================================================
	 * METHOD : getDeploymentRecordName
	 * =====================================================
	 */
	/**
	 * Generates a name for the deployment record
	 *
	 * @param deploymentArtifact	Name of the Artifact being deployed
	 * @return
	 */
	private String getDeploymentRecordName( String deploymentArtifact )
	{
		return deploymentArtifact + postfix;
	}

}
