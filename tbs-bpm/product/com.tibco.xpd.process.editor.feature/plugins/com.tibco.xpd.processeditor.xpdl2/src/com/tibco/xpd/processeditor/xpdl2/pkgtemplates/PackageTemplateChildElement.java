/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.pkgtemplates;

import java.util.Collection;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.graphics.ImageData;

import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.xpdl2.Pool;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author rsomayaj
 * 
 */
public class PackageTemplateChildElement implements IAdaptable {

    private final Process process;

    private final PackageTemplate packageTemplate;

    /**
     * @param packageTemplate
     * 
     */
    public PackageTemplateChildElement(PackageTemplate packageTemplate,
            Process proc) {
        this.packageTemplate = packageTemplate;
        this.process = proc;
    }

    /**
     * 
     */
    public PackageTemplate getParent() {
        return packageTemplate;
    }

    public Process getProcess() {
        return process;
    }

    /**
     * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
     * 
     * @param adapter
     * @return
     */
    public Object getAdapter(Class adapter) {
        if (adapter.equals(EObject.class)) {
            return this.process;
        }
        return null;
    }

    public String getName() {
        return Xpdl2ModelUtil.getDisplayName(process);
    }

    public ImageData getImageData() {
        com.tibco.xpd.xpdl2.Package xpdlPkg =
                (com.tibco.xpd.xpdl2.Package) packageTemplate
                        .getAdapter(EObject.class);
        Collection<EObject> procElements = getProcessElements(xpdlPkg);
        return Xpdl2ProcessEditorPlugin.getProcessDiagramImage(xpdlPkg,
                process.getId(),
                procElements).getImageData();
    }

    private Collection<EObject> getProcessElements(
            com.tibco.xpd.xpdl2.Package xpdlPkg) {
        Pool procPool = getProcPool(xpdlPkg);

        Collection<EObject> allNodesInPool =
                Xpdl2ModelUtil.getAllNodesInPool(procPool);
        allNodesInPool.add(procPool);

        return allNodesInPool;
    }

    private Pool getProcPool(com.tibco.xpd.xpdl2.Package xpdlPkg) {
        for (Pool pool : xpdlPkg.getPools()) {
            if (pool.getProcessId().equals(process.getId())) {
                return pool;
            }
        }
        return null;
    }
}
