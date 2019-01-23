/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.validation.pageflow;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.validation.xpdl2.provider.Xpdl2ScopeProvider;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * PageflowXpdl2ScopeProvider
 * <p>
 * Scope provider for Pageflow processes in XPDL v2 files.
 * 
 * @author aallway
 * @since 3.3 (4 Nov 2009)
 */
public class PageflowXpdl2ScopeProvider extends Xpdl2ScopeProvider {

    @Override
    protected boolean filterAffectedObject(EObject eObject) {
        boolean include = false;

        if (super.filterAffectedObject(eObject)) {

            /* Attempt to ascertain the parent process */
            Process process = null;

            if (eObject instanceof Process) {
                process = (Process) eObject;
            } else {
                process = Xpdl2ModelUtil.getProcess(eObject);
            }

            /*
             * If it's a process or an object under a process then it needs to
             * be validated if it's a pageflow process.
             */
            if (process != null) {
                include =
                        Xpdl2ModelUtil.isPageflow(process)
                                || ProcessInterfaceUtil
                                        .isPageflowEngineServiceProcess(process);
            }
            /*
             * We don't want to validate process interface or anything below it.
             */
            else if (!(eObject instanceof ProcessInterface)
                    && ProcessInterfaceUtil.getProcessInterface(eObject) == null) {
                /*
                 * If it's not under a process, and not under a process
                 * interface then it must be under package and therefore we
                 * should only validate it if there is at least one pageflow
                 * process in package.
                 */
                Package pkg = Xpdl2ModelUtil.getPackage(eObject);
                if (pkg != null) {

                    for (Process proc : pkg.getProcesses()) {

                        if (Xpdl2ModelUtil.isPageflow(proc)
                                || ProcessInterfaceUtil
                                        .isPageflowEngineServiceProcess(proc)) {

                            include = true;
                            break;
                        }
                    }
                }
            }
        }

        return include;
    }
}
