/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.customer.api;

import java.util.Set;

import com.tibco.xpd.customer.api.plugin.BusinessStudioCustomerApiPlugin;
import com.tibco.xpd.n2.resources.script.IProcessScriptToBPMJavaScriptConverter;
import com.tibco.xpd.studioiprocess.scriptconverter.AbstractIProcessToJavaScriptConverter;

/**
 * <p>
 * This class provides a conversion between iProcess grammar scripts to AMX-BPM
 * specific JavaScript.
 * </p>
 * <p>
 * This conversion is not guaranteed to create AMX BPM JavaScript that is 100%
 * executable. The reason for this is that some iProcess script features are not
 * supported in AMX-BPM JavaScript grammar (such as the ability to invoke one
 * script from another). In this case the original iProcess script lines with no
 * equivalent are left in place and a problem marker added in order that the
 * user may resolve these issues manually.
 * </p>
 * <p>
 * All that is required to configure the converter is the set of names of the
 * data (fields and formal parameters) that are in scope of the script(s) to be
 * converted. Usually for scripts in XPDL this will the set of xpdl2:Name
 * attributes for...
 * <li>Process Data-Fields</li>
 * <li>Process Formal-Parameters</li>
 * <li>Package Data-Fields</li>
 * </p>
 * 
 * @author TIBCO Software Inc
 * @since Business Studio v3.6.0 (HF1)
 */
public class IProcessScriptToJavaScriptConverter {

    /**
     * The set of available data names.
     */
    private Set<String> dataNames;

    /**
     * <p>
     * Create a class for conversion of scripts with the given set of data names
     * the scope of the script. Then one or more scripts can be converted usign
     * this same class instance.
     * </p>
     * <p>
     * Usually for scripts in XPDL this will the set of xpdl2:Name attributes
     * for...
     * <li>Process Data-Fields</li>
     * <li>Process Formal-Parameters</li>
     * <li>Package Data-Fields</li>
     * </p>
     * 
     * @param dataNames
     *            The set of names of the process data that is in scope of the
     *            script.
     * 
     * @throws IllegalArgumentException
     *             If the <code>dataNames</code> argument is invalid (
     *             <code>null</code>).
     */
    public IProcessScriptToJavaScriptConverter(Set<String> dataNames)
            throws IllegalArgumentException {
        super();

        /*
         * Check whether the set of data names is null or not. If it is, throw
         * an IllegalArgumentException stating the cause.
         */
        if (dataNames == null) {
            IllegalArgumentException e =
                    new IllegalArgumentException(
                            "Parameter 'dataNames' is null."); //$NON-NLS-1$
            BusinessStudioCustomerApiPlugin.getDefault().getLogger().error(e);
            throw e;
        }

        this.dataNames = dataNames;
    }

    /**
     * <p>
     * Convert the given iProcess grammar script to its equivalent AMX-BPM
     * JavaScript grammar script.
     * </p>
     * <p>
     * This conversion is not guaranteed to create AMX BPM JavaScript that is
     * 100% executable. The reason for this is that some iProcess script
     * features are not supported in AMX-BPM JavaScript grammar (such as the
     * ability to invoke one script from another). In this case the original
     * iProcess script lines with no equivalent are left in place and a problem
     * marker added in order that the user may resolve these issues manually.
     * </p>
     * 
     * @return JavaScript approximation for source IProcess script
     * 
     * @throws IllegalArgumentException
     *             If the <code>sourceIProcessScript</code> argument is invalid
     *             (<code>null</code>).
     */
    public String getJavaScript(String sourceIProcessScript)
            throws IllegalArgumentException {

        /*
         * Check whether sourceIProcessScript is null or not. If it is, throw an
         * IllegalArgumentException stating the cause.
         */
        if (sourceIProcessScript == null) {
            IllegalArgumentException e =
                    new IllegalArgumentException(
                            "Parameter [sourceIProcessScript] is null."); //$NON-NLS-1$
            BusinessStudioCustomerApiPlugin.getDefault().getLogger().error(e);
            throw e;
        }

        AbstractIProcessToJavaScriptConverter scriptConverter =
                new IProcessScriptToBPMJavaScriptConverter(
                        sourceIProcessScript, dataNames);

        return scriptConverter.getJavaScriptApproximation();
    }
}
