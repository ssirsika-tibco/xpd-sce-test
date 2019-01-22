/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.resources.projectconfig.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.resources.internal.Messages;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.ProjectConfigFactory;
import com.tibco.xpd.resources.projectconfig.ProjectConfigPackage;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.projectconfig.SpecialFolders;
import com.tibco.xpd.resources.projectconfig.specialfolders.ISpecialFolderModel;
import com.tibco.xpd.resources.projectconfig.specialfolders.ISpecialFolderModel.MultiplicityType;
import com.tibco.xpd.resources.projectconfig.specialfolders.extpoint.SpecialFoldersExtensionPoint;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Special Folders</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>
 * {@link com.tibco.xpd.resources.projectconfig.impl.SpecialFoldersImpl#getFolders
 * <em>Folders</em>}</li>
 * <li>
 * {@link com.tibco.xpd.resources.projectconfig.impl.SpecialFoldersImpl#getConfig
 * <em>Config</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class SpecialFoldersImpl extends EObjectImpl implements SpecialFolders {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getFolders() <em>Folders</em>}'
     * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getFolders()
     * @generated
     * @ordered
     */
    protected EList<SpecialFolder> folders;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected SpecialFoldersImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ProjectConfigPackage.Literals.SPECIAL_FOLDERS;
    }

    /**
     * <!-- begin-user-doc --><!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public EList getFolders() {
        if (folders == null) {
            folders =
                    new EObjectContainmentEList(SpecialFolder.class, this,
                            ProjectConfigPackage.SPECIAL_FOLDERS__FOLDERS);
        }
        return folders;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public ProjectConfig getConfig() {
        if (eContainerFeatureID() != ProjectConfigPackage.SPECIAL_FOLDERS__CONFIG)
            return null;
        return (ProjectConfig) eContainer();
    }

    /**
     * <!-- begin-user-doc -->
     * 
     * @see SpecialFolders#getFoldersOfKind(String) <!--end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    @SuppressWarnings("unchecked")
    public EList getFoldersOfKind(String kind) {
        EList folders = new BasicEList();

        if (kind != null) {
            // Check that the kind is value
            if (getFolderKindInfo(kind) != null) {
                EList allFolders = getFolders();

                if (allFolders != null && !allFolders.isEmpty()) {
                    // Filter the list for the given kind
                    for (Iterator<?> iter = allFolders.iterator(); iter
                            .hasNext();) {
                        Object obj = iter.next();
                        if (obj instanceof SpecialFolder
                                && ((SpecialFolder) obj).getKind().equals(kind)) {
                            folders.add(obj);
                        }
                    }
                }
            }
        }

        return folders;
    }

    /**
     * <!-- begin-user-doc -->
     * <p>
     * 
     * @see SpecialFolders#getFolder(IFolder) </p>
     *      <!-- end-user-doc -->
     * @generated NOT
     */
    @Override
    public SpecialFolder getFolder(IFolder folder) {
        SpecialFolder ret = null;

        if (folder != null) {
            // Check if the folder is from the same project as this object
            if (folder.getProject().equals(getConfig().getProject())) {
                if (getFolders() != null) {
                    String projRelativePath =
                            folder.getProjectRelativePath().toString();
                    EList sFolders = getFolders();

                    for (Iterator<?> iter = sFolders.iterator(); iter.hasNext()
                            && ret == null;) {
                        SpecialFolder sf = (SpecialFolder) iter.next();

                        if (sf != null
                                && sf.getLocation().equals(projRelativePath)) {
                            ret = sf;
                        }
                    }
                }
            } else {
                // Folder is not from the same project as this config
                throw new IllegalArgumentException(
                        "Folder does not belong to this project."); //$NON-NLS-1$
            }

        } else {
            throw new NullPointerException("folder is null."); //$NON-NLS-1$
        }

        return ret;
    }

    /**
     * <!-- begin-user-doc -->
     * 
     * @see SpecialFolders#getFolder(IFolder, String) <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public SpecialFolder getFolder(IFolder folder, String kind) {
        SpecialFolder ret = getFolder(folder);

        if (ret != null && kind != null) {
            // Check that the kind is valid
            if (getFolderKindInfo(kind) != null) {
                // If the kind of the folder doesn't match then return null
                if (!ret.getKind().equals(kind)) {
                    ret = null;
                }
            }
        }

        return ret;
    }

    /**
     * <!-- begin-user-doc -->
     * 
     * @see SpecialFolders#getFolderContainer(IResource) <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public SpecialFolder getFolderContainer(IResource resource) {
        SpecialFolder ret = null;

        if (resource != null) {

            // Check that the resource is from the same project as the config
            if (resource.getProject().equals(getConfig().getProject())) {
                EList sFolders = getFolders();

                if (sFolders != null && !sFolders.isEmpty()) {
                    IPath projRelativePath = resource.getProjectRelativePath();

                    for (Iterator<?> iter = sFolders.iterator(); iter.hasNext()
                            && ret == null;) {
                        SpecialFolder sf = (SpecialFolder) iter.next();

                        if (sf != null) {
                            IPath sfPath = new Path(sf.getLocation());
                            /*
                             * Match the prefix of the path to determine if the
                             * resource is contained within a special folder
                             */
                            if (sfPath.isPrefixOf(projRelativePath)) {
                                ret = sf;
                            }
                        }
                    }
                }
            } else {
                throw new IllegalArgumentException(
                        "Resource does not belong to this project."); //$NON-NLS-1$
            }
        } else {
            throw new NullPointerException("resource is null"); //$NON-NLS-1$
        }

        return ret;
    }

    /**
     * <!-- begin-user-doc -->
     * 
     * @see SpecialFolders#addFolder(IFolder, String) <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public SpecialFolder addFolder(IFolder folder, String kind) {
        return addFolder(folder, kind, null);
    }

    /**
     * <!-- begin-user-doc -->
     * 
     * @see SpecialFolders#addFolder(EList, String) <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public void addFolder(EList folders, String kind) {
        addFolder(folders, kind, null);
    }

    /**
     * Add the given folders and mark them as special folders of the given kind.
     * 
     * @param folders
     *            folder to add
     * @param kind
     *            kind of special folder
     * @param generated
     *            (optional) set these folders as generated folders.
     */
    private void addFolder(EList<?> folders, String kind, String generated) {

        // Check input
        if (folders == null) {
            throw new NullPointerException("Folders is null."); //$NON-NLS-1$
        }

        if (kind == null) {
            throw new NullPointerException("Kind is null."); //$NON-NLS-1$
        }

        // Get the kind info
        ISpecialFolderModel kindInfo = getFolderKindInfo(kind);

        // Validate the input
        if (kindInfo != null) {
            if (!folders.isEmpty()) {
                /*
                 * Check the multiplicity setting for the special folder kind
                 */
                if (kindInfo.getMultiplicity() == MultiplicityType.SINGLE) {

                    // If the list contains more than 1 folder then throw
                    // exception

                    if (folders.size() > 1) {
                        String foldersLst =
                                getDisplayLstOfSpecialfolders(folders);

                        throw new IllegalArgumentException(
                                String.format(Messages.SpecialFoldersImpl_onlyOneSpecialFolder_longdesc1,
                                        new Object[] { kind, foldersLst }));
                    }

                    /*
                     * This is a singleton special folder so check if there is
                     * already a special folder of this kind defined, if it is
                     * then throw exception
                     */
                    EList<?> foldersOfKind = getFoldersOfKind(kind);

                    if (foldersOfKind.size() > 0) {
                        String foldersLst =
                                getDisplayLstOfSpecialfolders(foldersOfKind);

                        throw new IllegalArgumentException(
                                String.format(Messages.SpecialFoldersImpl_onlyOneSpecialFolder_longdesc1,
                                        new Object[] { kind, foldersLst }));
                    }
                }

                EditingDomain ed = WorkingCopyUtil.getEditingDomain(this);

                if (ed != null) {
                    List<SpecialFolder> sFoldersToAdd =
                            new ArrayList<SpecialFolder>();

                    /*
                     * Process each folder in the list - if a SpecialFolder
                     * already exists for the folder then check it's kind - if
                     * the kind match then ignore the folder, if the kind are
                     * different then throw exception. If the SpecialFolder
                     * doesn not exist for the folder then add it to the add
                     * list
                     */
                    for (Iterator<?> iter = folders.iterator(); iter.hasNext();) {
                        Object obj = iter.next();

                        if (obj instanceof IFolder) {
                            IFolder folder = (IFolder) obj;

                            /*
                             * Validate the folder - if validation fails then an
                             * IllegalArgumentException will be thrown
                             */
                            validateFolder(folder);

                            SpecialFolder sf = getFolder(folder);

                            if (sf == null) {
                                /*
                                 * SpecialFolder doesn't exist for the folder so
                                 * create the SpecialFolder and add it to list
                                 */
                                sf =
                                        ProjectConfigFactory.eINSTANCE
                                                .createSpecialFolder();
                                sf.setKind(kind);
                                sf.setLocation(folder.getProjectRelativePath()
                                        .toString());
                                sf.setGenerated(generated);
                                sFoldersToAdd.add(sf);

                            } else {
                                if (!sf.getKind().equals(kind)) {
                                    throw new IllegalArgumentException(
                                            MessageFormat
                                                    .format("Special folder with location ''{0}'' already exists.", //$NON-NLS-1$
                                                            new Object[] { folder
                                                                    .getProject()
                                                                    .toString() }));
                                }
                            }

                        } else {
                            throw new IllegalArgumentException(
                                    "Expected IFolder objects in the list"); //$NON-NLS-1$
                        }
                    }

                    /*
                     * If there is a special folders list to add then add it
                     */
                    if (!sFoldersToAdd.isEmpty()) {
                        // Add all create special folder objects to the
                        // config
                        Command cmd =
                                AddCommand.create(ed,
                                        this,
                                        ProjectConfigPackage.eINSTANCE
                                                .getSpecialFolders_Folders(),
                                        sFoldersToAdd);

                        if (cmd.canExecute()) {
                            ed.getCommandStack().execute(cmd);
                            if (getConfig() != null) {
                                // Save the working copy
                                getConfig().saveWorkingCopy();
                            }
                        }
                    }
                } else {
                    throw new NullPointerException("editing domain is null."); //$NON-NLS-1$
                }
            }
        } else {
            throw new IllegalArgumentException(
                    String.format("Special folder of kind '%s' not found.", kind)); //$NON-NLS-1$
        }
    }

    /**
     * Get a displayable list of specified special folders.
     * 
     * @param foldersOfKind
     * 
     * @return A displayable list of specified special folders.
     */
    private String getDisplayLstOfSpecialfolders(EList<?> foldersOfKind) {

        StringBuilder sb = new StringBuilder();
        final String separator = ", "; //$NON-NLS-1$
        for (Object folder : foldersOfKind) {
            if (folder instanceof SpecialFolder) {
                sb.append(((SpecialFolder) folder).getFolder())
                        .append(separator);
            }
        }
        int newLength = sb.length() - separator.length();
        if (newLength > 0) {
            sb.setLength(newLength);
        }

        return sb.toString();
    }

    /**
     * <!-- begin-user-doc -->
     * 
     * @see SpecialFolders#removeFolder(SpecialFolder) <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    @SuppressWarnings("unchecked")
    public void removeFolder(SpecialFolder specialFolder) {
        if (specialFolder != null) {
            EList list = new BasicEList();
            list.add(specialFolder);

            removeFolder(list);
        }
    }

    /**
     * <!-- begin-user-doc -->
     * 
     * @see SpecialFolders#removeFolder(EList) <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public void removeFolder(EList specialFolders) {

        if (specialFolders != null) {
            if (specialFolders != null && !specialFolders.isEmpty()) {
                EditingDomain ed = WorkingCopyUtil.getEditingDomain(this);

                if (ed != null) {
                    Command cmd =
                            RemoveCommand.create(ed,
                                    this,
                                    ProjectConfigPackage.eINSTANCE
                                            .getSpecialFolders_Folders(),
                                    specialFolders);

                    if (cmd != null && cmd.canExecute()) {
                        ed.getCommandStack().execute(cmd);
                        if (getConfig() != null) {
                            // Save the working copy
                            getConfig().saveWorkingCopy();
                        }
                    }
                } else {
                    throw new NullPointerException("Editing domain is null."); //$NON-NLS-1$
                }
            }
        } else {
            throw new NullPointerException("specialFolders is null."); //$NON-NLS-1$
        }
    }

    /**
     * <!-- begin-user-doc -->
     * 
     * @see SpecialFolders#changeFolder(SpecialFolder, IFolder) <!--
     *      end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public SpecialFolder changeFolder(SpecialFolder specialFolder,
            IFolder folder) {
        SpecialFolder ret = null;

        if (specialFolder != null && folder != null) {

            /*
             * Validate the folder - if validation fails then an
             * IllegalArgumentException will be thrown
             */
            validateFolder(folder);

            if (!specialFolder.getLocation().equals(folder
                    .getProjectRelativePath().toString())) {
                // Update the location of this special folder
                EditingDomain ed = WorkingCopyUtil.getEditingDomain(this);

                if (ed != null) {
                    // Run command to update the location
                    Command cmd =
                            SetCommand.create(ed,
                                    specialFolder,
                                    ProjectConfigPackage.eINSTANCE
                                            .getSpecialFolder_Location(),
                                    folder.getProjectRelativePath().toString());

                    if (cmd.canExecute()) {
                        ed.getCommandStack().execute(cmd);
                        if (getConfig() != null) {
                            // Save the working copy
                            getConfig().saveWorkingCopy();
                        }
                        ret = specialFolder;
                    }
                } else {
                    throw new NullPointerException("Editing domain is null."); //$NON-NLS-1$
                }
            } else {
                // The path is the same as the one already set in the
                // specialFolder so just return it
                ret = specialFolder;
            }
        } else {
            throw new NullPointerException("specialFolder or folder is null."); //$NON-NLS-1$
        }

        return ret;
    }

    /**
     * <!-- begin-user-doc -->
     * 
     * @see SpecialFolders#getFolderKindInfo() <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    @SuppressWarnings("unchecked")
    public EList getFolderKindInfo() {
        EList ret = new BasicEList();

        ISpecialFolderModel[] extensions =
                SpecialFoldersExtensionPoint.getInstance().getExtensions();

        if (extensions.length > 0) {
            ret.addAll(Arrays.asList(extensions));
        }

        return ret;
    }

    /**
     * <!-- begin-user-doc -->
     * 
     * @see SpecialFolders#getFolderKindInfo(String) <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public ISpecialFolderModel getFolderKindInfo(String kind) {
        ISpecialFolderModel info = null;

        if (kind != null) {
            info =
                    SpecialFoldersExtensionPoint.getInstance()
                            .getExtensionByKind(kind);
        } else {
            throw new NullPointerException("kind is null."); //$NON-NLS-1$
        }

        return info;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    @SuppressWarnings("unchecked")
    public EList getEclipseIFoldersOfKind(String kind) {
        EList<IFolder> folders = new BasicEList<IFolder>();
        if (kind != null) {
            // Check that the kind is value
            if (getFolderKindInfo(kind) != null) {
                List<SpecialFolder> allFolders = getFolders();
                if (allFolders != null) {
                    for (SpecialFolder specialFolder : allFolders) {
                        // Filter the list for the given kind
                        if (specialFolder.getKind().equals(kind)
                                && specialFolder.getFolder() != null) {
                            folders.add(specialFolder.getFolder());
                        }
                    }
                }
            }
        }
        return folders;
    }

    /**
     * <!-- begin-user-doc --> Add a generated special folder. The generated
     * value is an arbitrary string and can be used to indicate the purpose of
     * this special folder. Also see
     * {@link #markAsGenerated(SpecialFolder, String)}.<!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public SpecialFolder addFolder(IFolder folder, String kind, String generated) {
        SpecialFolder ret = null;

        if (folder != null) {
            // Check that the folder exists
            if (folder.isAccessible()) {
                EList<IFolder> list = new BasicEList<IFolder>();
                list.add(folder);

                addFolder(list, kind, generated);
                // Find the folder just added
                ret = getFolder(folder);
            } else {
                throw new IllegalArgumentException("folder is not accessible"); //$NON-NLS-1$
            }
        } else {
            throw new NullPointerException("folder is null."); //$NON-NLS-1$
        }

        return ret;
    }

    /**
     * <!-- begin-user-doc --> Set the given special folder as a generated
     * folder. The generated value is an arbitrary string and can be used to
     * indicate the purpose of this special folder. <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public void markAsGenerated(SpecialFolder specialFolder, String generated) {
        if (specialFolder != null && generated != null) {
            EditingDomain ed = WorkingCopyUtil.getEditingDomain(this);
            if (ed != null) {
                // Execute command to update the generated attribute and save
                // working copy
                ed.getCommandStack().execute(SetCommand.create(ed,
                        specialFolder,
                        ProjectConfigPackage.eINSTANCE
                                .getSpecialFolder_Generated(),
                        generated));
                getConfig().saveWorkingCopy();
            }
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public NotificationChain eInverseAdd(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
        switch (featureID) {
        case ProjectConfigPackage.SPECIAL_FOLDERS__CONFIG:
            if (eInternalContainer() != null)
                msgs = eBasicRemoveFromContainer(msgs);
            return eBasicSetContainer(otherEnd,
                    ProjectConfigPackage.SPECIAL_FOLDERS__CONFIG,
                    msgs);
        }
        return super.eInverseAdd(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
        switch (featureID) {
        case ProjectConfigPackage.SPECIAL_FOLDERS__FOLDERS:
            return ((InternalEList<?>) getFolders())
                    .basicRemove(otherEnd, msgs);
        case ProjectConfigPackage.SPECIAL_FOLDERS__CONFIG:
            return eBasicSetContainer(null,
                    ProjectConfigPackage.SPECIAL_FOLDERS__CONFIG,
                    msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public NotificationChain eBasicRemoveFromContainerFeature(
            NotificationChain msgs) {
        switch (eContainerFeatureID()) {
        case ProjectConfigPackage.SPECIAL_FOLDERS__CONFIG:
            return eInternalContainer().eInverseRemove(this,
                    ProjectConfigPackage.PROJECT_CONFIG__SPECIAL_FOLDERS,
                    ProjectConfig.class,
                    msgs);
        }
        return super.eBasicRemoveFromContainerFeature(msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case ProjectConfigPackage.SPECIAL_FOLDERS__FOLDERS:
            return getFolders();
        case ProjectConfigPackage.SPECIAL_FOLDERS__CONFIG:
            return getConfig();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
        case ProjectConfigPackage.SPECIAL_FOLDERS__FOLDERS:
            getFolders().clear();
            getFolders().addAll((Collection<? extends SpecialFolder>) newValue);
            return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
        case ProjectConfigPackage.SPECIAL_FOLDERS__FOLDERS:
            getFolders().clear();
            return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
        case ProjectConfigPackage.SPECIAL_FOLDERS__FOLDERS:
            return folders != null && !folders.isEmpty();
        case ProjectConfigPackage.SPECIAL_FOLDERS__CONFIG:
            return getConfig() != null;
        }
        return super.eIsSet(featureID);
    }

    /**
     * Validate the given folder. This will check for:
     * <ul>
     * <li>the folder belongs to the same project as this config,</li>
     * <li>the folder is already marked as a special folder,</li>
     * <li>the folder is accessible.</li>
     * </ul>
     * It will also enforce the following conditions:
     * <ul>
     * <li>A special folder cannot contain other special folders,</li>
     * <li>A folder that contains special folder(s) cannot be set as a special
     * folder.</li>
     * </ul>
     * 
     * @param folder
     * 
     * @exception IllegalArgumentException
     *                - If any of the tests mentioned above fails.
     */
    private void validateFolder(IFolder folder) {

        if (folder != null) {
            String msg = null;

            // Check if the folder is from the same project as this config
            if (folder.getProject().equals(getConfig().getProject())) {
                // Check if the folder is already marked as a special folder
                if (getFolder(folder) == null) {
                    // Check if the folder is accessible
                    if (folder.isAccessible()) {
                        // Check if the folder is contained in a special folders
                        if (getFolderContainer(folder) == null) {
                            // Check if the folder contains special folders
                            EList sFolders = getFolders();

                            if (sFolders != null) {
                                IPath projRelPath =
                                        folder.getProjectRelativePath();

                                for (Iterator<?> iter = sFolders.iterator(); iter
                                        .hasNext() && msg == null;) {
                                    SpecialFolder sf =
                                            (SpecialFolder) iter.next();

                                    if (sf != null
                                            && sf.getFolder() != null
                                            && projRelPath.isPrefixOf(sf
                                                    .getFolder()
                                                    .getProjectRelativePath())) {
                                        // The folder contains a special folder
                                        msg =
                                                Messages.SpecialFoldersImpl_containsSpecialFolder_longdesc;
                                    }
                                }
                            }
                        } else {
                            msg =
                                    Messages.SpecialFoldersImpl_isContainedInSpecialFolder_longdesc;
                        }
                    } else {
                        msg =
                                Messages.SpecialFoldersImpl_isNotAccessible_longdesc;
                    }
                } else {
                    msg =
                            Messages.SpecialFoldersImpl_alreadyMarkedAsSpecialFolder_longdesc;
                }
            } else {
                msg =
                        Messages.SpecialFoldersImpl_doesNotBelongTpProject_longdesc;
            }

            // If there is a message created then throw exception
            if (msg != null) {
                throw new IllegalArgumentException(String.format(msg,
                        folder.getProjectRelativePath()));
            }

        }
    }

} // SpecialFoldersImpl
