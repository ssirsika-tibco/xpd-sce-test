/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.projectexplorer.actions.providers;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;
import org.eclipse.ui.navigator.INavigatorContentService;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.AssetType;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.projectconfig.specialfolders.ISpecialFolderModel;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.internal.Messages;
import com.tibco.xpd.resources.util.GovernanceStateService;
import com.tibco.xpd.ui.projectexplorer.actions.specialfolder.AbstractSpecialFolderAction;
import com.tibco.xpd.ui.projectexplorer.actions.specialfolder.AssetConfigAction;
import com.tibco.xpd.ui.projectexplorer.actions.specialfolder.SetSpecialFolderAction;
import com.tibco.xpd.ui.projectexplorer.actions.specialfolder.UnsetSpecialFolderAction;

/**
 * <code>CommonActionProvider</code> for setting/unsetting special folders.
 * 
 * @author njpatel
 * 
 */
public class SpecialFoldersActionProvider extends CommonActionProvider {

    private static final String FOLDER_MENU = "xpd.specialfolders.menu"; //$NON-NLS-1$

    private static final String OTHER_MENU = "xpd.specialfolders.other"; //$NON-NLS-1$

    private static final String SET_SPECIALFOLDER_TITLE = Messages.SpecialFoldersActionProvider_useAs_menu;

    private static final String SET_SPECIALFOLDER_TOOLTIP = Messages.SpecialFoldersActionProvider_useFolderAs_menu;

    private static final String UNSET_SPECIALFOLDER_TITLE = Messages.SpecialFoldersActionProvider_doNotUseAs_menu;

    private static final String UNSET_SPECIALFOLDER_TOOLTIP = Messages.SpecialFoldersActionProvider_doNotUseAsFolder_menu;

    private Shell shell;

    private INavigatorContentService contentService;

    @Override
    public void init(ICommonActionExtensionSite aSite) {
        shell = aSite.getViewSite().getShell();
        contentService = aSite.getContentService();
        super.init(aSite);
    }

    @Override
    public void fillContextMenu(IMenuManager menu) {

        // Create sub menu for folders options
        IMenuManager subMenu = new MenuManager(
                Messages.SpecialFoldersActionProvider_specialFolder_menu,
                FOLDER_MENU);

        // Get the selection
        IStructuredSelection selection = (IStructuredSelection) getContext()
                .getSelection();

        if (selection != null) {

            // Check if all objects are IFolder or SpecialFolder and are from
            // the same project
            IProject project;
            if ((project = allObjectsAreFoldersOrSpecialFoldersFromSameProject(selection)) != null) {
                ProjectConfig config = XpdResourcesPlugin.getDefault()
                        .getProjectConfig(project);

                if (config != null && config.getSpecialFolders() != null) {
                    String kind;
                    if ((kind = allObjectsAreMarkedFoldersOfSameKind(config,
                            selection)) != null) {
                        /*
                         * All special folders in the selection are of the same
                         * kind so show action to unset them
                         */
                        ISpecialFolderModel ext = config.getSpecialFolders()
                                .getFolderKindInfo(kind);

                        // Special folder kind should be settable
                        if (ext != null && !ext.isUnsettable()) {
                            boolean show = true;

                            /*
                             * If a navigator content ID is provided then check
                             * that it is active in the navigator. If it isn't
                             * then do not show the action
                             */
                            String navigatorId = ext.getNavigatorContentId();

                            if (contentService != null && navigatorId != null) {
                                // Check if navigator id is valid
                                if (contentService
                                        .getContentDescriptorById(navigatorId) != null) {
                                    show = contentService.isActive(navigatorId);
                                } else {
                                    show = false;
                                    XpdResourcesUIActivator
                                            .getDefault()
                                            .getLogger()
                                            .warn(
                                                    String
                                                            .format(
                                                                    "Navigator content extension %1$s not found (Special folder : %2$s).", //$NON-NLS-1$
                                                                    navigatorId,
                                                                    ext
                                                                            .getName()));
                                }
                            }

                            if (show) {
                                UnsetSpecialFolderAction action = new UnsetSpecialFolderAction(
                                        MessageFormat.format(
                                                UNSET_SPECIALFOLDER_TITLE,
                                                new Object[] { ext.getName() }),
                                        MessageFormat.format(
                                                UNSET_SPECIALFOLDER_TOOLTIP,
                                                new Object[] { ext.getName() }),
                                        ext);

                                if (action.updateSelection(selection)) {
                                    /*
                                     * ACE-2473: Saket: Action should be
                                     * disabled for locked application.
                                     */
                                    if (selection.getFirstElement() instanceof EObject) {
                                        boolean isLocked = (new GovernanceStateService())
                                                .isLockedForProduction((EObject) (selection.getFirstElement()));
                                        action.setEnabled(!isLocked);
                                    }
                                    subMenu.add(action);
                                }
                            }
                        }

                    } else if (allObjectsAreUnmarkedFolders(config, selection)) {
                        /*
                         * All objects in the selection are not marked as
                         * special folders
                         */
                        // All objects in the selection will be IFolder objects
                        IFolder[] folders = convertToIFolderArray(selection);

                        /*
                         * Check that no sibling folders are contained in the
                         * selection and also that none of the folders are
                         * already contained in a special folder
                         */
                        if (!anyFoldersContainedInOtherFolder(folders)
                                && !anyFoldersContainedInSpecialFolder(config,
                                        folders)) {
                            /*
                             * Check if the folders contain special folders - if
                             * they do then the folder cannot be set as a
                             * special folder
                             */
                            boolean containSpecialFolder = doFoldersContainSpecialFolder(
                                    config, folders);

                            /*
                             * Show menu option if: 1. Selected folder(s)
                             * contain no special folders at any level, 2.
                             * Single selected folder contains special folder at
                             * any level.
                             */
                            if ((folders.length > 0 && !containSpecialFolder)
                                    || (folders.length == 1 && containSpecialFolder)) {

                                // Create the Other menu
                                IMenuManager otherMenu = new MenuManager(
                                        Messages.SpecialFoldersActionProvider_other_menu,
                                        OTHER_MENU);

                                List<String> assetIds = getConfiguredAssetTypes(config);

                                /*
                                 * Show set special folder actions for each
                                 * registered special folder
                                 */
                                EList<ISpecialFolderModel> kindInfoList = config
                                        .getSpecialFolders()
                                        .getFolderKindInfo();
                                for (Iterator<ISpecialFolderModel> iter = kindInfoList
                                        .iterator(); iter.hasNext();) {
                                    ISpecialFolderModel info = iter.next();

                                    // Special folder kind should be settable
                                    if (info != null && !info.isUnsettable()) {
                                        boolean add = true;
                                        /*
                                         * If a navigator content ID is provided
                                         * then check that it is active in the
                                         * navigator. If it isn't then do not
                                         * show the action
                                         */
                                        String navigatorId = info
                                                .getNavigatorContentId();

                                        if (contentService != null
                                                && navigatorId != null) {
                                            // Check if the navigator id is
                                            // valid
                                            if (contentService
                                                    .getContentDescriptorById(navigatorId) != null) {
                                                add = contentService
                                                        .isActive(navigatorId);
                                            } else {
                                                add = false;
                                                XpdResourcesUIActivator
                                                        .getDefault()
                                                        .getLogger()
                                                        .warn(
                                                                String
                                                                        .format(
                                                                                "Navigator content extension %1$s not found (Special folder : %2$s).", //$NON-NLS-1$
                                                                                navigatorId,
                                                                                info
                                                                                        .getName()));
                                            }
                                        }

                                        if (add) {
                                            // Get the appropriate action
                                            AbstractSpecialFolderAction action = getAction(
                                                    config, assetIds, info);

                                            if (action != null
                                                    && action
                                                            .updateSelection(selection)) {
                                                // Add action to the right
                                                // submenu

                                                /*
                                                 * ACE-2473: Saket: Action
                                                 * should be disabled for locked
                                                 * application.
                                                 */
                                                if (selection.getFirstElement() instanceof EObject) {
                                                    boolean isLocked =
                                                            (new GovernanceStateService()).isLockedForProduction(
                                                                    (EObject) (selection.getFirstElement()));
                                                    action.setEnabled(!isLocked);
                                                }

                                                if (action instanceof SetSpecialFolderAction) {
                                                    subMenu.add(action);
                                                } else {
                                                    otherMenu.add(action);
                                                }
                                            }
                                        }
                                    }
                                }
                                // Add Other submenu
                                subMenu.add(new Separator());
                                subMenu.add(otherMenu);
                            }
                        }
                    }
                }
            }
        }

        menu.appendToGroup(ICommonMenuConstants.GROUP_REORGANIZE, subMenu);
    }

    /**
     * Get the appropriate action. If the special folder has an associated asset
     * type and this asset type is not configured for this project then create
     * an <code>{@link AssetConfigAction}</code>.
     * 
     * @param config
     * @param assetIds
     * @param info
     * @return
     */
    private AbstractSpecialFolderAction getAction(ProjectConfig config,
            List<String> assetIds, ISpecialFolderModel info) {
        AbstractSpecialFolderAction action = null;
        /*
         * Check if this special folder needs to go in the Other submenu - this
         * will be the case if an associated asset type is not configured for
         * this project
         */
        if (info.getProjectAssetId() != null
                && !assetIds.contains(info.getProjectAssetId())) {

            action = new AssetConfigAction(shell, MessageFormat.format(
                    SET_SPECIALFOLDER_TITLE, new Object[] { info.getName() }),
                    MessageFormat.format(SET_SPECIALFOLDER_TOOLTIP,
                            new Object[] { info.getName() }), info, config
                            .getAssetById(info.getProjectAssetId()));
        } else {
            action = new SetSpecialFolderAction(shell, MessageFormat.format(
                    SET_SPECIALFOLDER_TITLE, new Object[] { info.getName() }),
                    MessageFormat.format(SET_SPECIALFOLDER_TOOLTIP,
                            new Object[] { info.getName() }), info);
        }

        return action;
    }

    /**
     * Get a list of ids of assets configured for the given
     * <code>ProjectConfig</code> object.
     * 
     * @param config
     * @return List of strings, empty list if no assets are configured.
     */
    private List<String> getConfiguredAssetTypes(ProjectConfig config) {
        List<String> assetIds = new ArrayList<String>();

        if (config != null) {
            EList<AssetType> assetTypes = config.getAssetTypes();

            if (assetTypes != null) {
                for (AssetType type : assetTypes) {
                    if (type != null) {
                        assetIds.add(type.getId());
                    }
                }
            }
        }

        return assetIds;
    }

    /**
     * Check if all objects in the selection are either of type
     * <code>IFolder</code> or <code>SpecialFolder</code> and come from the same
     * project.
     * 
     * @param selection
     * @return the project if all objects are from same project and are either
     *         <code>IFolder</code> or <code>SpecialFolder</code> objects,
     *         <b>null</b> otherwise.
     */
    private IProject allObjectsAreFoldersOrSpecialFoldersFromSameProject(
            IStructuredSelection selection) {
        IProject project = null;
        boolean sameProject = true;

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
                 * SpecialFolder and are contained in the same project
                 */
                for (Iterator<?> iter = selection.iterator(); iter.hasNext()
                        && sameProject;) {
                    Object obj = iter.next();

                    if (obj instanceof IFolder) {
                        sameProject = project.equals(((IFolder) obj)
                                .getProject());
                    } else if (obj instanceof SpecialFolder) {
                        sameProject = project.equals(((SpecialFolder) obj)
                                .getProject());
                    } else {
                        // This object is not an IFolder or a SpecialFolder
                        sameProject = false;
                    }
                }
            }
        }

        return sameProject ? project : null;
    }

    /**
     * Check if any object in the <i>selection</i> is already marked as a
     * <code>SpecialFolder</code>.
     * 
     * @param config
     * @param selection
     * 
     * @return <b>true</b> if any object in the <i>selection</i> is marked as a
     *         <code>SpecialFolder</code>.
     */
    private boolean allObjectsAreUnmarkedFolders(ProjectConfig config,
            IStructuredSelection selection) {
        boolean ret = true;

        if (config != null && selection != null && !selection.isEmpty()) {
            for (Iterator<?> iter = selection.iterator(); iter.hasNext() && ret;) {
                Object obj = iter.next();

                if (obj instanceof SpecialFolder) {
                    // This is a special folder
                    ret = false;
                } else if (obj instanceof IFolder) {
                    IFolder folder = (IFolder) obj;

                    if (config.getSpecialFolders() != null) {
                        ret = (config.getSpecialFolders().getFolder(folder) == null);
                    }
                }
            }
        }

        return ret;
    }

    /**
     * Check if all objects in the <i>selection</i> are marked as special
     * folders and of the same kind.
     * 
     * @param config
     * @param selection
     * 
     * @return if all objects in the <i>selection</i> are special folders and of
     *         the same kind then the kind string will be returned, <b>null</b>
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

    /**
     * Check if any of the folders in the list are contained in a special
     * folder.
     * 
     * @param config
     * @param folders
     * 
     * @return <b>true</b> if a folder in the <i>folders</i> list is contained
     *         in a <code>SpecialFolder</code>.
     */
    private boolean anyFoldersContainedInSpecialFolder(ProjectConfig config,
            IFolder[] folders) {
        boolean ret = false;

        if (config != null && folders != null && folders.length > 0) {
            for (IFolder folder : folders) {
                ret = (config.getSpecialFolders().getFolderContainer(folder) != null);

                // If folder contained in special folder then break
                if (ret) {
                    break;
                }
            }
        }

        return ret;
    }

    /**
     * Check if any of the selected folders is contained within another of the
     * selected folder. Special folders cannot be defined inside other special
     * folders so this will not be allowed in the selection.
     * 
     * @param folder
     * @return <b>true</b> if a folder is contained (at any level) within
     *         another folder in the selection.
     */
    private boolean anyFoldersContainedInOtherFolder(IFolder[] folders) {
        boolean ret = false;

        if (folders != null && folders.length > 0) {
            for (IFolder folder : folders) {
                if (ret = folderContainedinOtherFolder(folder, folders)) {
                    break;
                }
            }
        }

        return ret;
    }

    /**
     * Check if any of the folders in the array is a root folder of the given
     * <i>folder</i>.
     * 
     * @param folder
     * @param folders
     * @return <b>true</b> if one of the folder's parent (at any level) folder
     *         is contained in the given array.
     */
    private boolean folderContainedinOtherFolder(IFolder folder,
            IFolder[] folders) {
        boolean ret = false;

        if (folder != null && folders != null && folders.length > 0) {
            for (IFolder f : folders) {

                if (!f.equals(folder)) {
                    if (ret = (f.getLocation().isPrefixOf(folder.getLocation()))) {
                        // Test passed
                        break;
                    }
                }
            }
        }

        return ret;
    }

    /**
     * Check if the given <i>folders</i> contain a special folder at any level.
     * Assuming that all folders come from the same project; the project will be
     * determined from the first folder in the array.
     * 
     * @param config
     * @param folders
     * @return <b>true</b> if any folder in the array contains a special folder
     *         at any level.
     */
    private boolean doFoldersContainSpecialFolder(ProjectConfig config,
            IFolder[] folders) {
        boolean ret = false;

        if (config != null && folders != null && folders.length > 0) {
            EList<SpecialFolder> sFolders = config.getSpecialFolders()
                    .getFolders();

            if (sFolders != null) {
                for (int fIdx = 0; fIdx < folders.length && !ret; fIdx++) {
                    // Get the folder's project relative path
                    String projRelativePath = folders[fIdx]
                            .getProjectRelativePath().toString();

                    /*
                     * Compare the location of each special folder with the
                     * project relative path of the folder, if they are
                     * contained in the same path suffix then the folder
                     * contains a special folder.
                     */
                    for (Iterator<SpecialFolder> iter = sFolders.iterator(); iter
                            .hasNext()
                            && !ret;) {
                        SpecialFolder sf = iter.next();

                        ret = (sf != null && sf.getLocation().startsWith(
                                projRelativePath));
                    }
                }
            }
        }
        return ret;
    }

    /**
     * Convert the given <i>selection</i> to an <code>IFolder</code> array.
     * <p>
     * ASSUMING ALL OBJECTS IN THE SELECTION ARE OF TYPE <code>IFolder</code>.
     * </p>
     * 
     * @param selection
     * @return
     */
    private IFolder[] convertToIFolderArray(IStructuredSelection selection) {
        if (selection != null) {
            List<?> list = selection.toList();

            if (list != null) {
                return list.toArray(new IFolder[list.size()]);
            }
        }

        return null;
    }
}
