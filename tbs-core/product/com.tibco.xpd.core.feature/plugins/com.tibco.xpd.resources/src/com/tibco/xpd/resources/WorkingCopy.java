/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.resources;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.Saveable;

import com.tibco.xpd.resources.wc.NotificationPropertyChangeEvent;

/**
 * WorkingCopy - XPD way of accessing resources.
 * <p>
 * Responsibility:
 * <ol>
 * <li>Load and save model to/from file(s)</li>
 * <li>Ensure that there is only on instance of the model in memory</li>
 * <li>Ensure that all viewers use the same EditingDomain and AdpaterFactory</li>
 * <li>Provide notifications about changes in resources caused by external
 * editors</li>
 * <li>Provide dependency graph</li>
 * <li>Can manage invalid files</li>
 * </ol>
 * </p>
 * 
 * @author wzurek
 */
public interface WorkingCopy extends IEditingDomainProvider {

    /**
     * Read Only property of WorkingCopy that clients may listen to.
     */
    String PROP_READONLY = "READ_ONLY"; //$NON-NLS-1$

    /**
     * Changed property of WorkingCopy that clients may listen to. Working Copy
     * has changed and model has been replaced, the client should unregister
     * itself from model and request new one from working copy.
     */
    String PROP_RELOADED = "RELOADED"; //$NON-NLS-1$

    /**
     * 'Removed' property of WorkingCopy that clients may listen to Working copy
     * has been removed. Client should destroy itself.
     */
    String PROP_REMOVED = "REMOVED"; //$NON-NLS-1$

    /**
     * Dirty property of WorkingCopy that clients may listen to Dirtyness flag
     * of the working copy has changed. The client should request new dirty
     * status from WorkingCopy.
     */
    String PROP_DIRTY = "DIRTY"; //$NON-NLS-1$

    /**
     * Property to indicate that the model has changed. The new value of this
     * event may contain the trigger event object (since 3.0, in case of
     * resources that belong to the single transactional
     * <code>EditingDomain</code> this will be the
     * {@link ResourceSetChangeEvent}).
     * <p>
     * Since 3.1 the resource change <code>Notification</code>s can also be
     * accessed using {@link NotificationPropertyChangeEvent} that will be
     * passed in place of {@link PropertyChangeEvent}.
     * </p>
     */
    String CHANGED = "CHANGED"; //$NON-NLS-1$

    /**
     * notification property that is fired when any of resource on wchich this
     * working copy depends has been changed.
     */
    String PROP_DEPENDENCY_CHANGED = "DEPENDENCY_CHANGED"; //$NON-NLS-1$

    /**
     * List of eclipse resources that contain the model(s). It contains at least
     * one resource there.
     * 
     * the list should not be modified by the client.
     * 
     * @return eclipse resources
     */
    List<IResource> getEclipseResources();

    /**
     * List of eclipse resources on which that working copy depends. The
     * listeners to this working copy will receive notifications about resource
     * changes in these resources (see PROP_DEPENDENCY_CHANGED).
     * 
     * It is also used by validation engine.
     * 
     * the list should not be modified by the client.
     * 
     * @return eclipse resources
     */
    List<IResource> getDependency();

    /**
     * Editing domain for editing the model. The user should execute all
     * commands through this editing domain. The domain can be shared across
     * many models, so the client should not modify or alter it.
     * 
     * @return editing domain
     */
    EditingDomain getEditingDomain();

    /**
     * Adapter factory that can produce ItemProvaiderAdapters for the model
     * objects.
     * 
     * @return adapter factory
     */
    AdapterFactory getAdapterFactory();

    /**
     * Returns status of 'dirtiness' of the model.
     * 
     * @return true, if the model need to be saved. false otherwise.
     */
    boolean isWorkingCopyDirty();

    /**
     * Save changes on editable version of the model to the read only varsion
     * (and the file).
     * 
     * @throws IOException
     *             on IO problem
     */
    void save() throws IOException;

    /**
     * Register given model for notifications of dirtiness flag and changes to
     * the file done outside the editors. The editors have to register before
     * they obey EditingDomain and editable model, in other case the model could
     * be cleaned or rebuilt.<br>
     * Also the editors have to unregister themselves from the working copy in
     * order to be able to recover memory of editable model.
     * 
     * Listeners do not need synchronisation with UI thread.
     * 
     * @param listener
     *            listener to register
     */

    void addListener(PropertyChangeListener listener);

    /**
     * Unregister the listener.
     * 
     * @see #addListener(PropertyChangeListener)
     * @param listener
     *            listener to remove
     */
    void removeListener(PropertyChangeListener listener);

    /**
     * Set WorkingCopy property.
     * 
     * @param key
     *            one of the constants of this interface
     * @param value
     *            value to set
     */
    void setProperty(String key, Object value);

    /**
     * Get property stored in WorkingCopy.
     * 
     * @param key
     *            one of the constants of this interface
     * @return value of the property
     */
    Object getProperty(String key);

    /**
     * Attributes defined by the client. Attributes are not stored managed by
     * default by WorkingCopy.
     * 
     * @return map of client defined attributes.
     */
    Map<Object, Object> getAttributes();

    /**
     * This will return whether the resource exist or not. true, if it exists.
     * 
     * @return true when the file exists
     */
    boolean isExist();

    /**
     * Returns the root Element of the file. Returns the editable element.
     * RootElement is an first element form first resource for WorkingCopies
     * that manage more then one resource or have more then one model per
     * resource.
     * 
     * It can returns null when file is invalid.
     * 
     * @return EObject the root object
     */
    EObject getRootElement();

    /**
     * Returns the name, usually name of the resource.
     * 
     * @return name of the working copy that could be displayed to the user
     */
    String getName();

    /**
     * Returns the image of the WC, usually image of the resource.
     * 
     * @return image of the working copy.
     */
    Image getImage();

    /**
     * Returns whether the working copy is loaded.
     * 
     * @return true, when the model is already loaded into memory
     */
    boolean isLoaded();

    /**
     * Return true when the file is invalid and it cannot be loaded.
     * 
     * isLoaded and isInvalidFile cannot be both true.
     * 
     * @return true when the file is invalid.
     */
    boolean isInvalidFile();

    /**
     * Removes the reference to the rootElement and informs all the listeners
     * about it. It will also remove all invalid file markers, so the working
     * copy will try to load the model again when requested.
     */
    void reLoad();

    /**
     * Check is the working copy is read-only. The flag can be set from outside
     * by calling {@link #setReadOnly(boolean) setReadOnly}. The the working copy is read only no
     * command can be executed on its editing domain
     * 
     * @return read only flag
     */
    boolean isReadOnly();

    /**
     * Set read only flag for editing domain.
     * 
     * @param readOnly
     *            flag to set
     */
    void setReadOnly(boolean readOnly);

    /**
     * Return type of main element of the working copy, it should not be
     * required to load the file to determine the type of WorkingCopy.
     * 
     * @return EPackage of the main model (the one returned by getRootElement)
     */
    EPackage getWorkingCopyEPackage();

    /**
     * Return the saveable for this working copy.
     * 
     * @return saveable for this working copy
     */
    Saveable getSaveable();

    /**
     * Get meta-data name of the EObject, it takes translatable description of
     * the EClass from the model.edit plugin.
     * <p>
     * Example: for EObject of type Activity it will return 'Activity'
     * 
     * 
     * @param eo
     *            object to test
     * @return meta-data description
     */
    String getMetaText(EObject eo);

    /**
     * Returns attribute of the working copy that is accessible even if the file
     * is not loaded to memory.
     * 
     * @param key
     *            Specific to working copy key of the attribute
     * @return value of the key, null if not found
     */
    String getCachedAttribute(String key);

    /**
     * @param uri
     *            The uri to resolve to an EObject.
     * @return The resolved EObject, or null if it is not found.
     */
    EObject resolveEObject(String uri);

    /**
     * Clears the URI to EObject cache.
     */
    void clearUriCache();

    /**
     * @param eo
     *            The EObject.
     * @param er
     *            The resource.
     * @return The problem severity.
     * @throws CoreException
     *             If there was a problem finding the markers.
     */
    int getSeverity(EObject eo, IResource er) throws CoreException;

    /**
     * Clears the severity cache.
     */
    void clearSeverityCache();
}
