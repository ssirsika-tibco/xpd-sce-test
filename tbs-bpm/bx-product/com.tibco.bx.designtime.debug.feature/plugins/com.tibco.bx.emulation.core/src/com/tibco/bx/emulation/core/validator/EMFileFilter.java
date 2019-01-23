package com.tibco.bx.emulation.core.validator;

import org.eclipse.core.resources.IFile;

import com.tibco.bx.emulation.core.EmulationCoreActivator;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.projectconfig.SpecialFolders;
import com.tibco.xpd.validation.provider.IFileFilter;

public class EMFileFilter implements IFileFilter{

	public EMFileFilter() {
	}

	 public boolean accept(IFile ifile)
	    {
	        boolean flag = false;
	        if(EmulationCoreActivator.EMULATION_FILE_EXTENSION.equalsIgnoreCase(ifile.getFileExtension()))
	        {
	            org.eclipse.core.resources.IProject iproject = ifile.getProject();
	            ProjectConfig projectconfig = XpdResourcesPlugin.getDefault().getProjectConfig(iproject);
	            if(projectconfig != null)
	            {
	                SpecialFolders specialfolders = projectconfig.getSpecialFolders();
	                if(specialfolders != null)
	                {
	                    SpecialFolder specialfolder = specialfolders.getFolderContainer(ifile);
	                    if(specialfolder != null && EmulationCoreActivator.EMULATION_SPECIAL_FOLDER_KIND.equals(specialfolder.getKind()))
	                        flag = true;
	                }
	            }
	        }
	        return flag;
	    }
}
