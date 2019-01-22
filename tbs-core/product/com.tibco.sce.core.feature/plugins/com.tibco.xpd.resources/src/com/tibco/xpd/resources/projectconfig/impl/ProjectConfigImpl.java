/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.resources.projectconfig.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.AssetType;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.ProjectConfigFactory;
import com.tibco.xpd.resources.projectconfig.ProjectConfigPackage;
import com.tibco.xpd.resources.projectconfig.ProjectDetails;
import com.tibco.xpd.resources.projectconfig.SpecialFolders;
import com.tibco.xpd.resources.projectconfig.projectassets.IProjectAsset;
import com.tibco.xpd.resources.projectconfig.projectassets.IProjectAssetManager;
import com.tibco.xpd.resources.projectconfig.projectassets.util.ProjectAssetElement;
import com.tibco.xpd.resources.projectconfig.projectassets.util.ProjectAssetManager;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Project Config</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.resources.projectconfig.impl.ProjectConfigImpl#getAssetTypes <em>Asset Types</em>}</li>
 *   <li>{@link com.tibco.xpd.resources.projectconfig.impl.ProjectConfigImpl#getSpecialFolders <em>Special Folders</em>}</li>
 *   <li>{@link com.tibco.xpd.resources.projectconfig.impl.ProjectConfigImpl#getProject <em>Project</em>}</li>
 *   <li>{@link com.tibco.xpd.resources.projectconfig.impl.ProjectConfigImpl#getProjectType <em>Project Type</em>}</li>
 *   <li>{@link com.tibco.xpd.resources.projectconfig.impl.ProjectConfigImpl#getProjectDetails <em>Project Details</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProjectConfigImpl extends EObjectImpl implements ProjectConfig {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getAssetTypes() <em>Asset Types</em>}' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getAssetTypes()
     * @generated
     * @ordered
     */
    protected EList<AssetType> assetTypes;

    /**
     * The cached value of the '{@link #getSpecialFolders() <em>Special Folders</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSpecialFolders()
     * @generated
     * @ordered
     */
    protected SpecialFolders specialFolders;

    /**
     * The default value of the '{@link #getProject() <em>Project</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getProject()
     * @generated
     * @ordered
     */
    protected static final IProject PROJECT_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getProject() <em>Project</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getProject()
     * @generated
     * @ordered
     */
    protected IProject project = PROJECT_EDEFAULT;

    /**
     * The default value of the '{@link #getProjectType() <em>Project Type</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getProjectType()
     * @generated
     * @ordered
     */
    protected static final String PROJECT_TYPE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getProjectType() <em>Project Type</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getProjectType()
     * @generated
     * @ordered
     */
    protected String projectType = PROJECT_TYPE_EDEFAULT;

    /**
     * The cached value of the '{@link #getProjectDetails() <em>Project Details</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getProjectDetails()
     * @generated
     * @ordered
     */
    protected ProjectDetails projectDetails;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected ProjectConfigImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ProjectConfigPackage.Literals.PROJECT_CONFIG;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<AssetType> getAssetTypes() {
        if (assetTypes == null) {
            assetTypes =
                    new EObjectContainmentEList<AssetType>(AssetType.class,
                            this,
                            ProjectConfigPackage.PROJECT_CONFIG__ASSET_TYPES);
        }
        return assetTypes;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public SpecialFolders getSpecialFolders() {
        return specialFolders;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetSpecialFolders(
            SpecialFolders newSpecialFolders, NotificationChain msgs) {
        SpecialFolders oldSpecialFolders = specialFolders;
        specialFolders = newSpecialFolders;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(
                            this,
                            Notification.SET,
                            ProjectConfigPackage.PROJECT_CONFIG__SPECIAL_FOLDERS,
                            oldSpecialFolders, newSpecialFolders);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setSpecialFolders(SpecialFolders newSpecialFolders) {
        if (newSpecialFolders != specialFolders) {
            NotificationChain msgs = null;
            if (specialFolders != null)
                msgs =
                        ((InternalEObject) specialFolders).eInverseRemove(this,
                                ProjectConfigPackage.SPECIAL_FOLDERS__CONFIG,
                                SpecialFolders.class,
                                msgs);
            if (newSpecialFolders != null)
                msgs =
                        ((InternalEObject) newSpecialFolders).eInverseAdd(this,
                                ProjectConfigPackage.SPECIAL_FOLDERS__CONFIG,
                                SpecialFolders.class,
                                msgs);
            msgs = basicSetSpecialFolders(newSpecialFolders, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    ProjectConfigPackage.PROJECT_CONFIG__SPECIAL_FOLDERS,
                    newSpecialFolders, newSpecialFolders));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public IProject getProject() {
        return project;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setProject(IProject newProject) {
        IProject oldProject = project;
        project = newProject;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    ProjectConfigPackage.PROJECT_CONFIG__PROJECT, oldProject,
                    project));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String getProjectType() {
        return projectType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setProjectType(String newProjectType) {
        String oldProjectType = projectType;
        projectType = newProjectType;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    ProjectConfigPackage.PROJECT_CONFIG__PROJECT_TYPE,
                    oldProjectType, projectType));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ProjectDetails getProjectDetails() {
        return projectDetails;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetProjectDetails(
            ProjectDetails newProjectDetails, NotificationChain msgs) {
        ProjectDetails oldProjectDetails = projectDetails;
        projectDetails = newProjectDetails;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(
                            this,
                            Notification.SET,
                            ProjectConfigPackage.PROJECT_CONFIG__PROJECT_DETAILS,
                            oldProjectDetails, newProjectDetails);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setProjectDetails(ProjectDetails newProjectDetails) {
        if (newProjectDetails != projectDetails) {
            NotificationChain msgs = null;
            if (projectDetails != null)
                msgs =
                        ((InternalEObject) projectDetails)
                                .eInverseRemove(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - ProjectConfigPackage.PROJECT_CONFIG__PROJECT_DETAILS,
                                        null,
                                        msgs);
            if (newProjectDetails != null)
                msgs =
                        ((InternalEObject) newProjectDetails)
                                .eInverseAdd(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - ProjectConfigPackage.PROJECT_CONFIG__PROJECT_DETAILS,
                                        null,
                                        msgs);
            msgs = basicSetProjectDetails(newProjectDetails, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    ProjectConfigPackage.PROJECT_CONFIG__PROJECT_DETAILS,
                    newProjectDetails, newProjectDetails));
    }

    /**
     * <!-- begin-user-doc -->
     * 
     * @see ProjectConfig#saveWorkingCopy() <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public void saveWorkingCopy() {

        try {
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(this);

            if (wc != null && wc.isWorkingCopyDirty()) {
                wc.save();
            }
        } catch (IOException e) {
            XpdResourcesPlugin.getDefault().getLogger().error(e);
        }
    }

    /**
     * <!-- begin-user-doc -->
     * 
     * @see ProjectConfig#addAssetTask(String) <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @SuppressWarnings("unchecked")
    public void addAssetTask(String id) {
        if (id != null) {
            EList list = new BasicEList();
            list.add(id);

            addAssetTypes(list);

        } else {
            throw new NullPointerException("id is null."); //$NON-NLS-1$
        }
    }

    /**
     * <!-- begin-user-doc -->
     * 
     * @see ProjectConfig#addAssetTypes(EList) <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @SuppressWarnings("unchecked")
    public void addAssetTypes(EList ids) {
        if (ids != null) {
            EList<AssetType> newAssetTypes = new BasicEList<AssetType>();
            Set<String> addedIds = new HashSet<String>();
            IProjectAssetManager assetManager =
                    ProjectAssetManager.getProjectAssetMenager();

            // Build a list of all asset types already installed so they are not
            // installed again
            EList<AssetType> types = getAssetTypes();
            if (types != null) {
                for (AssetType type : types) {
                    addedIds.add(type.getId());
                }
            }

            for (Iterator<?> iter = ids.iterator(); iter.hasNext();) {
                Object next = iter.next();

                if (next instanceof String) {
                    String id = (String) next;

                    // Check if the asset type has already been
                    // installed/processed
                    if (!addedIds.contains(id)) {

                        // Check if this is a valid asset id
                        ProjectAssetElement assetElement =
                                assetManager.getAssetById(id);
                        if (assetElement == null) {
                            // No asset type with this id is registered
                            throw new IllegalArgumentException(
                                    String.format("Asset with id '%s' not found.", id)); //$NON-NLS-1$
                        }

                        addedIds.add(id);

                        // Asset not installed so do so
                        AssetType aType =
                                ProjectConfigFactory.eINSTANCE
                                        .createAssetType();
                        aType.setId(id);
                        aType.setVersion(assetElement.getVersion(getProject()));
                        newAssetTypes.add(aType);
                    }
                }
            }

            // If there a asset types to add then do so
            if (!newAssetTypes.isEmpty()) {
                EditingDomain ed = WorkingCopyUtil.getEditingDomain(this);

                if (ed != null) {
                    Command cmd =
                            AddCommand.create(ed,
                                    this,
                                    ProjectConfigPackage.eINSTANCE
                                            .getProjectConfig_AssetTypes(),
                                    newAssetTypes);

                    if (cmd.canExecute()) {
                        ed.getCommandStack().execute(cmd);
                        saveWorkingCopy();
                    }

                } else {
                    throw new NullPointerException("Editing domain is null."); //$NON-NLS-1$
                }
            }

        } else {
            throw new NullPointerException("List of id's is null."); //$NON-NLS-1$
        }
    }

    /**
     * <!-- begin-user-doc -->
     * 
     * @see ProjectConfig#getRegisteredAssetTypes() <!-- end-user-doc -->
     * @generated NOT
     */
    @SuppressWarnings("unchecked")
    public EList getRegisteredAssetTypes() {
        EList list = null;

        // Get all registered extension points
        list =
                new BasicEList(Arrays.asList(ProjectAssetManager
                        .getProjectAssetMenager().getAssets()));

        return list;
    }

    /**
     * <!-- begin-user-doc -->
     * 
     * @see ProjectConfig#getAssetById(String) <!-- end-user-doc -->
     * @generated NOT
     */
    public IProjectAsset getAssetById(String id) {
        return ProjectAssetManager.getProjectAssetMenager().getAssetById(id);
    }

    /**
     * <!-- begin-user-doc -->
     * 
     * @see ProjectConfig#hasAssetType(String) <!-- end-user-doc -->
     * @generated NOT
     */
    public boolean hasAssetType(String assetId) {
        boolean ret = false;

        if (assetId != null) {
            EList<?> types = getAssetTypes();

            if (types != null && !types.isEmpty()) {
                for (Iterator<?> iter = types.iterator(); iter.hasNext()
                        && !ret;) {
                    AssetType asset = (AssetType) iter.next();

                    ret = asset.getId().equals(assetId);
                }
            }
        } else {
            throw new NullPointerException("assetId is null."); //$NON-NLS-1$
        }

        return ret;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseAdd(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
        switch (featureID) {
        case ProjectConfigPackage.PROJECT_CONFIG__SPECIAL_FOLDERS:
            if (specialFolders != null)
                msgs =
                        ((InternalEObject) specialFolders)
                                .eInverseRemove(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - ProjectConfigPackage.PROJECT_CONFIG__SPECIAL_FOLDERS,
                                        null,
                                        msgs);
            return basicSetSpecialFolders((SpecialFolders) otherEnd, msgs);
        }
        return super.eInverseAdd(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
        switch (featureID) {
        case ProjectConfigPackage.PROJECT_CONFIG__ASSET_TYPES:
            return ((InternalEList<?>) getAssetTypes()).basicRemove(otherEnd,
                    msgs);
        case ProjectConfigPackage.PROJECT_CONFIG__SPECIAL_FOLDERS:
            return basicSetSpecialFolders(null, msgs);
        case ProjectConfigPackage.PROJECT_CONFIG__PROJECT_DETAILS:
            return basicSetProjectDetails(null, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case ProjectConfigPackage.PROJECT_CONFIG__ASSET_TYPES:
            return getAssetTypes();
        case ProjectConfigPackage.PROJECT_CONFIG__SPECIAL_FOLDERS:
            return getSpecialFolders();
        case ProjectConfigPackage.PROJECT_CONFIG__PROJECT:
            return getProject();
        case ProjectConfigPackage.PROJECT_CONFIG__PROJECT_TYPE:
            return getProjectType();
        case ProjectConfigPackage.PROJECT_CONFIG__PROJECT_DETAILS:
            return getProjectDetails();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
        case ProjectConfigPackage.PROJECT_CONFIG__ASSET_TYPES:
            getAssetTypes().clear();
            getAssetTypes().addAll((Collection<? extends AssetType>) newValue);
            return;
        case ProjectConfigPackage.PROJECT_CONFIG__SPECIAL_FOLDERS:
            setSpecialFolders((SpecialFolders) newValue);
            return;
        case ProjectConfigPackage.PROJECT_CONFIG__PROJECT:
            setProject((IProject) newValue);
            return;
        case ProjectConfigPackage.PROJECT_CONFIG__PROJECT_TYPE:
            setProjectType((String) newValue);
            return;
        case ProjectConfigPackage.PROJECT_CONFIG__PROJECT_DETAILS:
            setProjectDetails((ProjectDetails) newValue);
            return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
        case ProjectConfigPackage.PROJECT_CONFIG__ASSET_TYPES:
            getAssetTypes().clear();
            return;
        case ProjectConfigPackage.PROJECT_CONFIG__SPECIAL_FOLDERS:
            setSpecialFolders((SpecialFolders) null);
            return;
        case ProjectConfigPackage.PROJECT_CONFIG__PROJECT:
            setProject(PROJECT_EDEFAULT);
            return;
        case ProjectConfigPackage.PROJECT_CONFIG__PROJECT_TYPE:
            setProjectType(PROJECT_TYPE_EDEFAULT);
            return;
        case ProjectConfigPackage.PROJECT_CONFIG__PROJECT_DETAILS:
            setProjectDetails((ProjectDetails) null);
            return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
        case ProjectConfigPackage.PROJECT_CONFIG__ASSET_TYPES:
            return assetTypes != null && !assetTypes.isEmpty();
        case ProjectConfigPackage.PROJECT_CONFIG__SPECIAL_FOLDERS:
            return specialFolders != null;
        case ProjectConfigPackage.PROJECT_CONFIG__PROJECT:
            return PROJECT_EDEFAULT == null ? project != null
                    : !PROJECT_EDEFAULT.equals(project);
        case ProjectConfigPackage.PROJECT_CONFIG__PROJECT_TYPE:
            return PROJECT_TYPE_EDEFAULT == null ? projectType != null
                    : !PROJECT_TYPE_EDEFAULT.equals(projectType);
        case ProjectConfigPackage.PROJECT_CONFIG__PROJECT_DETAILS:
            return projectDetails != null;
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String toString() {
        if (eIsProxy())
            return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (project: ");
        result.append(project);
        result.append(", projectType: ");
        result.append(projectType);
        result.append(')');
        return result.toString();
    }

} // ProjectConfigImpl
