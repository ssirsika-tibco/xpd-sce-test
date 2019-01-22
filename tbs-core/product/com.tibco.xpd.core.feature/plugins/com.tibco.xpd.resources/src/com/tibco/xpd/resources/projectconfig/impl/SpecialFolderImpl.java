/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.resources.projectconfig.impl;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.tibco.xpd.resources.projectconfig.DocumentRoot;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.ProjectConfigPackage;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.projectconfig.SpecialFolders;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Special Folder</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>
 * {@link com.tibco.xpd.resources.projectconfig.impl.SpecialFolderImpl#getKind
 * <em>Kind</em>}</li>
 * <li>
 * {@link com.tibco.xpd.resources.projectconfig.impl.SpecialFolderImpl#getLocation
 * <em>Location</em>}</li>
 * <li>
 * {@link com.tibco.xpd.resources.projectconfig.impl.SpecialFolderImpl#getGenerated
 * <em>Generated</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class SpecialFolderImpl extends UniqueIdContainerImpl implements
        SpecialFolder {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved."; //$NON-NLS-1$

    /**
     * The default value of the '{@link #getKind() <em>Kind</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getKind()
     * @generated
     * @ordered
     */
    protected static final String KIND_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getKind() <em>Kind</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getKind()
     * @generated
     * @ordered
     */
    protected String kind = KIND_EDEFAULT;

    /**
     * The default value of the '{@link #getLocation() <em>Location</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getLocation()
     * @generated
     * @ordered
     */
    protected static final String LOCATION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getLocation() <em>Location</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getLocation()
     * @generated
     * @ordered
     */
    protected String location = LOCATION_EDEFAULT;

    /**
     * The default value of the '{@link #getGenerated() <em>Generated</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getGenerated()
     * @generated
     * @ordered
     */
    protected static final String GENERATED_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getGenerated() <em>Generated</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getGenerated()
     * @generated
     * @ordered
     */
    protected String generated = GENERATED_EDEFAULT;

    /**
     * The project that this Special Folder belongs to
     */
    private IProject project = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected SpecialFolderImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ProjectConfigPackage.Literals.SPECIAL_FOLDER;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public String getKind() {
        return kind;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setKind(String newKind) {
        String oldKind = kind;
        kind = newKind;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    ProjectConfigPackage.SPECIAL_FOLDER__KIND, oldKind, kind));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public String getLocation() {
        return location;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public void setLocation(String newLocation) {
        String oldLocation = location;
        location = newLocation;

        // Location has changes so clear cache
        project = null;

        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    ProjectConfigPackage.SPECIAL_FOLDER__LOCATION, oldLocation,
                    location));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public String getGenerated() {
        return generated;
    }

    /**
     * <!-- begin-user-doc --> If this value is set then it indicates that this
     * folder is a generated special folder. The newGenerated string is an
     * arbitrary value which can be used to indicate the purpose of this special
     * folder.
     * <p>
     * Note that setting this value will only update the model. Use
     * {@link SpecialFolders#markAsGenerated(SpecialFolder, String)} to set and
     * persist the value to the .config file.
     * </p>
     * folder. <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setGenerated(String newGenerated) {
        String oldGenerated = generated;
        generated = newGenerated;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    ProjectConfigPackage.SPECIAL_FOLDER__GENERATED,
                    oldGenerated, generated));
    }

    /**
     * <!-- begin-user-doc -->
     * 
     * @see SpecialFolder#getProject()
     * 
     *      <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public IProject getProject() {
        if (project == null) {
            DocumentRoot root = (DocumentRoot) EcoreUtil.getRootContainer(this);

            if (root != null) {
                ProjectConfig config = root.getProjectConfig();

                if (config != null && config.getProject() != null) {
                    project = config.getProject();
                }
            }
        }

        return project;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public IFolder getFolder() {
        return getFolder(true); // Folder must exist.
    }

    /**
     * Get the eclipse folder that this special folder represents.
     * 
     * @param mustExist
     *            if <code>true</code> then the folder must exist, otherwise the
     *            handle to the folder will be returned.
     * 
     * @return Handle to the eclipse folder or
     *         <code>null<code> if <code>mustExist</code> is true and folder
     *         doesn't exist.
     * @generated NOT
     */
    private IFolder getFolder(boolean mustExist) {
        IProject prj = getProject();
        if (prj != null) {
            if (mustExist) {
                IResource resource = prj.findMember(getLocation());
                if (resource instanceof IFolder) {
                    return (IFolder) resource;
                }
            } else {
                return prj.getFolder(getLocation());
            }

        }
        return null;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case ProjectConfigPackage.SPECIAL_FOLDER__KIND:
            return getKind();
        case ProjectConfigPackage.SPECIAL_FOLDER__LOCATION:
            return getLocation();
        case ProjectConfigPackage.SPECIAL_FOLDER__GENERATED:
            return getGenerated();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
        case ProjectConfigPackage.SPECIAL_FOLDER__KIND:
            setKind((String) newValue);
            return;
        case ProjectConfigPackage.SPECIAL_FOLDER__LOCATION:
            setLocation((String) newValue);
            return;
        case ProjectConfigPackage.SPECIAL_FOLDER__GENERATED:
            setGenerated((String) newValue);
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
        case ProjectConfigPackage.SPECIAL_FOLDER__KIND:
            setKind(KIND_EDEFAULT);
            return;
        case ProjectConfigPackage.SPECIAL_FOLDER__LOCATION:
            setLocation(LOCATION_EDEFAULT);
            return;
        case ProjectConfigPackage.SPECIAL_FOLDER__GENERATED:
            setGenerated(GENERATED_EDEFAULT);
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
        case ProjectConfigPackage.SPECIAL_FOLDER__KIND:
            return KIND_EDEFAULT == null ? kind != null : !KIND_EDEFAULT
                    .equals(kind);
        case ProjectConfigPackage.SPECIAL_FOLDER__LOCATION:
            return LOCATION_EDEFAULT == null ? location != null
                    : !LOCATION_EDEFAULT.equals(location);
        case ProjectConfigPackage.SPECIAL_FOLDER__GENERATED:
            return GENERATED_EDEFAULT == null ? generated != null
                    : !GENERATED_EDEFAULT.equals(generated);
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public String toString() {
        if (eIsProxy())
            return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (kind: ");
        result.append(kind);
        result.append(", location: ");
        result.append(location);
        result.append(", generated: ");
        result.append(generated);
        result.append(')');
        return result.toString();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
     */
    @Override
    public Object getAdapter(Class adapter) {
        Object adapted = null;
        IFolder resource = getFolder(false);

        if (resource != null) {
            adapted = resource.getAdapter(adapter);
        }

        return adapted;
    }

} // SpecialFolderImpl
