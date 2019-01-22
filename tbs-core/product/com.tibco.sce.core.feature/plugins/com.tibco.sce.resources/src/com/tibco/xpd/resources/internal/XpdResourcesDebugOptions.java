/**
 * 
 */
package com.tibco.xpd.resources.internal;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Debug options available for tracing the XpdResources.
 * 
 * @author wzurek
 */
public final class XpdResourcesDebugOptions {

    public static final String WORKING_COPY = XpdResourcesPlugin.ID_PLUGIN
            + "/workingcopy"; //$NON-NLS-1$

    public static final String INFO = XpdResourcesPlugin.ID_PLUGIN + "/debug"; //$NON-NLS-1$

    public static final String DEBUG = XpdResourcesPlugin.ID_PLUGIN + "/info"; //$NON-NLS-1$

    public static final String INDEXER = "indexer"; //$NON-NLS-1$

    public static final Set<String> INDEXER_IDS = new HashSet<String>(
            Arrays.asList("com.tibco.xpd.bomgen.wsdlBomIndexer", //$NON-NLS-1$
                    "com.tibco.xpd.bom.xsdtransform.wsdlSchemaIndexer", //$NON-NLS-1$
                    "com.tibco.xpd.bom.resources.indexing.umlProfileIndexer")); //$NON-NLS-1$

}
