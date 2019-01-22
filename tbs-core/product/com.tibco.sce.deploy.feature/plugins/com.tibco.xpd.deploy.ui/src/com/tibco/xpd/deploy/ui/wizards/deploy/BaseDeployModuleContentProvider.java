/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.wizards.deploy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.ui.model.WorkbenchContentProvider;

import com.tibco.xpd.resources.util.SpecialFolderUtil;

/**
 * Provide context for deployable modules
 * <p>
 * <i>Created: 23 September 2008</i>
 * 
 * @author glewis
 * 
 */
public class BaseDeployModuleContentProvider extends WorkbenchContentProvider {

    private static final Object[] EMPTY_TABLE = new Object[0];
    
    ArrayList<String> filterKinds = null;

    /**
     * 
     */
    public BaseDeployModuleContentProvider() {
        super();        
    }
    
    /**
     * @param kind
     */
    public BaseDeployModuleContentProvider(ArrayList<String> filterKinds) {
        super();        
        this.filterKinds = filterKinds;
    }

    @Override
    public Object[] getChildren(Object element) {

        if (element instanceof IProject) {            
            return EMPTY_TABLE;            
        }
        return super.getChildren(element);
    }

    @Override
    public Object[] getElements(Object element) {
        Object[] elements = super.getElements(element);
        List<IProject> modules = new ArrayList<IProject>();
        for (int i = 0; i < elements.length; i++) {
            if (elements[i] instanceof IProject) {
                IProject project = (IProject) elements[i];
                if (project.exists() && project.isOpen() && filterKinds != null && filterKinds.size() > 0 ) {
                    boolean allKindsExistInProj = true;
                    Iterator<String> kindsIter = filterKinds.iterator();
                    while(kindsIter.hasNext()){
                        String kind = kindsIter.next();
                        if (!SpecialFolderUtil.containsSpecialFolderOfKind(project, kind, true)){
                            allKindsExistInProj = false;
                            break;
                        }
                    }
                    if (allKindsExistInProj){
                        modules.add(project);
                    }
                }
            }
        }
        return modules.toArray();
    }

    @Override
    public boolean hasChildren(Object element) {
        if (element instanceof IProject) {           
            return false;
        }
        return super.hasChildren(element);
    }

    @Override
    public Object getParent(Object element) {
        Object parent = super.getParent(element);
        if (element instanceof IProject) {
            return null;
        }
        return parent;
    }   
}
