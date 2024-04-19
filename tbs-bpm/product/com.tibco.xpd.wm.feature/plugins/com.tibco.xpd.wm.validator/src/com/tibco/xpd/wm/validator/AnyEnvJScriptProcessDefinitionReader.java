/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.wm.validator;

import java.net.URL;

import org.eclipse.emf.common.util.URI;

import com.tibco.xpd.n2.cds.reader.CdsExtendedJScriptProcessDefinitionReader;

/**
 * Reader for PSL Function Scripts when targetEnv is set to Any/All. (i.e. "useIn" Property of PSL Function Script
 * Activity)
 *
 * @author cbabar
 * @since Feb 28, 2024
 */
public class AnyEnvJScriptProcessDefinitionReader extends CdsExtendedJScriptProcessDefinitionReader
{

	public static final String ANY_TARGET_ENV_MODEL_FILE_NAME = "/model/AnyTargetEnvBpmJsClass.bom";

	@Override
	protected URI getURI()
	{
		URL entry = Activator.getDefault().getBundle()
				.getEntry(AnyEnvJScriptProcessDefinitionReader.ANY_TARGET_ENV_MODEL_FILE_NAME);
		return URI.createURI(entry.toExternalForm());
	}

}
