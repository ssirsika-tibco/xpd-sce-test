package com.tibco.bx.emulation.core;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;

import com.tibco.xpd.resources.projectconfig.projectassets.SpecialFolderAssetConfigurator;
import com.tibco.xpd.resources.util.ProjectUtil;

public class EMNatureAssetConfigurator extends SpecialFolderAssetConfigurator
{

    public EMNatureAssetConfigurator()
    {
    }

    public void configure(IProject iproject, Object obj)
        throws CoreException
    {
        super.configure(iproject, obj);
        ProjectUtil.addNature(iproject, EMNature.EM_NATURE_ID);
    }
    
    
}
