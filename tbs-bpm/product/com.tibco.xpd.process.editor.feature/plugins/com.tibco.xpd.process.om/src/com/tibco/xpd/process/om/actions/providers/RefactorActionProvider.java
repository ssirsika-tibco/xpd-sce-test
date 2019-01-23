/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.process.om.actions.providers;

import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;

import com.tibco.xpd.process.om.actions.RefactorToOMWizardAction;
import com.tibco.xpd.process.om.internal.Messages;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;

/**
 * <code>CommonActionProvider</code> for providing refactor options for various items.
 * 
 * @author glewis
 * 
 */
public class RefactorActionProvider extends CommonActionProvider {

    private static final String FOLDER_MENU = "xpd.specialfolders.menu"; //$NON-NLS-1$    

    @Override
    public void init(ICommonActionExtensionSite aSite) {    
        super.init(aSite);
    }

    @Override
    public void fillContextMenu(IMenuManager menu) {

        // Create sub menu for folders options
        IMenuManager subMenu = new MenuManager(
                Messages.RefactorActionProvider_refactor_menu,
                FOLDER_MENU);

        // Get the selection
        IStructuredSelection selection = (IStructuredSelection) getContext()
                .getSelection();        

        if (selection != null) {
            RefactorToOMWizardAction exportToOMWizardAction = new RefactorToOMWizardAction();
            exportToOMWizardAction.selectionChanged(selection);

            // Check if all objects are IFolder or SpecialFolder or all IFiles
            IProject projectFromFolders = allObjectsAreFoldersOrSpecialFoldersFromSameProject(selection);
            IProject projectFromFiles = allObjectsAreXpdlFiles(selection);
            if (projectFromFolders != null || projectFromFiles != null){
                ProjectConfig config = null;
                if (projectFromFolders != null){
                    config = XpdResourcesPlugin.getDefault().getProjectConfig(projectFromFolders);
                }else{
                    config = XpdResourcesPlugin.getDefault().getProjectConfig(projectFromFiles);
                }
                if (config != null && projectFromFolders != null && config.getSpecialFolders() != null) {                    
                    if ((allObjectsAreMarkedFoldersOfSameKind(config, selection)) != null) {                     
                        subMenu.add(exportToOMWizardAction);                    
                    }
                }
                else if (config != null && projectFromFiles != null) {                    
                    subMenu.add(exportToOMWizardAction);
                }
            }
        }

        menu.appendToGroup(ICommonMenuConstants.GROUP_REORGANIZE, subMenu);
    }

    /**
     * Check if all objects in the selection are either of type
     * <code>IFolder</code> or <code>SpecialFolder</code>.
     * 
     * @param selection
     * @return the project if all objects are from same project and are either
     *         <code>IFolder</code> or <code>SpecialFolder</code> objects,
     *         <b>null</b> otherwise.
     */
    private IProject allObjectsAreFoldersOrSpecialFoldersFromSameProject(
            IStructuredSelection selection) {
        IProject project = null;
        boolean sameFolderType = true;

        if (selection != null && !selection.isEmpty()) {
            // Determine project
            Object firstElem = selection.getFirstElement();

            if (firstElem instanceof IFolder) {
                project = ((IFolder) firstElem).getProject();
            } else if (firstElem instanceof SpecialFolder) {
                project = ((SpecialFolder) firstElem).getProject();
            }

            if (project != null) {
                /*
                 * Check that each object in the selection is a IFolder or
                 * SpecialFolder
                 */
                for (Iterator<?> iter = selection.iterator(); iter.hasNext()
                        && sameFolderType;) {
                    Object obj = iter.next();

                    if (!(obj instanceof IFolder) && !(obj instanceof SpecialFolder)) {
                        // This object is not an IFolder or SpecialFolder
                        sameFolderType = false;
                    }
                }
            }
        }

        return sameFolderType ? project : null;
    }
    
    /**
     * Check if all objects in the selection are either of type <code>IFile</code>
     * 
     * @param selection
     * @return the project if all objects are from same project and are either
     *         <code>IFile</code> objects,
     *         <b>null</b> otherwise.
     */
    private IProject allObjectsAreXpdlFiles(
            IStructuredSelection selection) {
        IProject project = null;
        boolean sameFileType = true;

        if (selection != null && !selection.isEmpty()) {
            // Determine project
            Object firstElem = selection.getFirstElement();

            if (firstElem instanceof IFile) {
                project = ((IFile) firstElem).getProject();
            }            
            
            if (project != null) {
                /*
                 * Check that each object in the selection is a IFolder or
                 * SpecialFolder and are contained in the same project
                 */
                for (Iterator<?> iter = selection.iterator(); iter.hasNext()
                        && sameFileType;) {
                    Object obj = iter.next();

                    if (!(obj instanceof IFile)) {                        
                        // This object is not an IFile
                        sameFileType = false;
                    }
                }
            }
        }

        return sameFileType ? project : null;
        
    }   

    /**
     * Check if all objects in the <i>selection</i> are marked as special
     * folders and of the same kind.
     * 
     * @param config
     * @param selection
     * 
     * @return if all objects in the <i>selection</i> are special folders and
     *         of the same kind then the kind string will be returned, <b>null</b>
     *         otherwise.
     */
    private String allObjectsAreMarkedFoldersOfSameKind(ProjectConfig config,
            IStructuredSelection selection) {
        boolean ret = false;
        String kindToCompare = null;

        if (config != null && selection != null && !selection.isEmpty()) {
            ret = true;

            for (Iterator<?> iter = selection.iterator(); iter.hasNext() && ret;) {
                Object obj = iter.next();
                String kind = null;

                if (obj instanceof SpecialFolder) {
                    // Marked as special folder
                    kind = ((SpecialFolder) obj).getKind();
                    ret = true;
                } else if (obj instanceof IFolder) {
                    SpecialFolder sf = config.getSpecialFolders().getFolder(
                            (IFolder) obj);

                    if (sf != null) {
                        kind = sf.getKind();
                    } else {
                        // Not a special folder
                        ret = false;
                    }

                } else {
                    // This is not an IFolder or SpecialFolder object
                    ret = false;
                }

                // if we have a special folder then compare the kind
                if (ret && kind != null) {
                    // If there isn't a kind to compare to then set it
                    if (kindToCompare == null) {
                        kindToCompare = kind;
                    } else {
                        ret = kindToCompare.equals(kind);
                    }
                }
            }
        }

        return ret ? kindToCompare : null;
    }
}
