/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.wm.validator;

import java.net.URL;

import org.eclipse.emf.common.util.URI;

import com.tibco.xpd.n2.cds.reader.CdsExtendedJScriptProcessDefinitionReader;

/**
 * Reader for PSL Function Scripts when targetEnv is set to Work Manager. (i.e. "useIn" Property of PSL Function Script
 * Activity)
 *
 * @author cbabar
 * @since Feb 27, 2024
 */
public class WMJScriptProcessDefinitionReader extends CdsExtendedJScriptProcessDefinitionReader
{

	public static final String WM_MODEL_FILE_NAME = "/model/WorkManagerBpmJsClass.bom";

	@Override
	protected URI getURI()
	{
		URL entry = Activator.getDefault().getBundle().getEntry(WMJScriptProcessDefinitionReader.WM_MODEL_FILE_NAME);
		return URI.createURI(entry.toExternalForm());
	}

}
