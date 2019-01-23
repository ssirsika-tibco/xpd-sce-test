/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.resources;

import java.net.URI;
import java.util.Map;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IPathVariableManager;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceProxy;
import org.eclipse.core.resources.IResourceProxyVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourceAttributes;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.jobs.ISchedulingRule;

/**
 * A proxy dependency resource that represents a referenced resource that is not
 * found in the workspace. This will be passed back by the dependency provider
 * to be stored in the cache as a PROXY resource.
 * <p>
 * The dependency cache will then try to resolve this resource when dependencies
 * are asked for from the respective working copy (as this resource may be in
 * the workspace by that time).
 * </p>
 * <p>
 * NOTE: Use {@link #getFullPath()} to get the relative path of this proxy
 * (typically relative to the special folder).
 * </p>
 * 
 * @author njpatel
 * @since 3.5.5
 */
public class DependencyProxyResource implements IResource {

    private final String specialFolderKind;

    private final IPath relativePath;

    /**
     * 
     */
    public DependencyProxyResource(IPath relativePath, String specialFolderKind) {
        super();
        this.relativePath = relativePath;
        this.specialFolderKind = specialFolderKind;

    }

    /**
     * @return the specialFolderKind
     */
    public String getSpecialFolderKind() {
        return specialFolderKind;
    }

    /**
     * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
     * 
     * @param adapter
     * @return
     */
    @Override
    public Object getAdapter(Class adapter) {
        return null;
    }

    /**
     * @see org.eclipse.core.runtime.jobs.ISchedulingRule#contains(org.eclipse.core.runtime.jobs.ISchedulingRule)
     * 
     * @param rule
     * @return
     */
    @Override
    public boolean contains(ISchedulingRule rule) {
        return false;
    }

    /**
     * @see org.eclipse.core.runtime.jobs.ISchedulingRule#isConflicting(org.eclipse.core.runtime.jobs.ISchedulingRule)
     * 
     * @param rule
     * @return
     */
    @Override
    public boolean isConflicting(ISchedulingRule rule) {
        return false;
    }

    /**
     * @see org.eclipse.core.resources.IResource#accept(org.eclipse.core.resources.IResourceProxyVisitor,
     *      int)
     * 
     * @param visitor
     * @param memberFlags
     * @throws CoreException
     */
    @Override
    public void accept(IResourceProxyVisitor visitor, int memberFlags)
            throws CoreException {
    }

    /**
     * @see org.eclipse.core.resources.IResource#accept(org.eclipse.core.resources.IResourceVisitor)
     * 
     * @param visitor
     * @throws CoreException
     */
    @Override
    public void accept(IResourceVisitor visitor) throws CoreException {

    }

    /**
     * @see org.eclipse.core.resources.IResource#accept(org.eclipse.core.resources.IResourceVisitor,
     *      int, boolean)
     * 
     * @param visitor
     * @param depth
     * @param includePhantoms
     * @throws CoreException
     */
    @Override
    public void accept(IResourceVisitor visitor, int depth,
            boolean includePhantoms) throws CoreException {
    }

    /**
     * @see org.eclipse.core.resources.IResource#accept(org.eclipse.core.resources.IResourceVisitor,
     *      int, int)
     * 
     * @param visitor
     * @param depth
     * @param memberFlags
     * @throws CoreException
     */
    @Override
    public void accept(IResourceVisitor visitor, int depth, int memberFlags)
            throws CoreException {
    }

    /**
     * @see org.eclipse.core.resources.IResource#clearHistory(org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param monitor
     * @throws CoreException
     */
    @Override
    public void clearHistory(IProgressMonitor monitor) throws CoreException {
    }

    /**
     * @see org.eclipse.core.resources.IResource#copy(org.eclipse.core.runtime.IPath,
     *      boolean, org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param destination
     * @param force
     * @param monitor
     * @throws CoreException
     */
    @Override
    public void copy(IPath destination, boolean force, IProgressMonitor monitor)
            throws CoreException {
    }

    /**
     * @see org.eclipse.core.resources.IResource#copy(org.eclipse.core.runtime.IPath,
     *      int, org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param destination
     * @param updateFlags
     * @param monitor
     * @throws CoreException
     */
    @Override
    public void copy(IPath destination, int updateFlags,
            IProgressMonitor monitor) throws CoreException {
    }

    /**
     * @see org.eclipse.core.resources.IResource#copy(org.eclipse.core.resources.IProjectDescription,
     *      boolean, org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param description
     * @param force
     * @param monitor
     * @throws CoreException
     */
    @Override
    public void copy(IProjectDescription description, boolean force,
            IProgressMonitor monitor) throws CoreException {
    }

    /**
     * @see org.eclipse.core.resources.IResource#copy(org.eclipse.core.resources.IProjectDescription,
     *      int, org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param description
     * @param updateFlags
     * @param monitor
     * @throws CoreException
     */
    @Override
    public void copy(IProjectDescription description, int updateFlags,
            IProgressMonitor monitor) throws CoreException {
    }

    /**
     * @see org.eclipse.core.resources.IResource#createMarker(java.lang.String)
     * 
     * @param type
     * @return
     * @throws CoreException
     */
    @Override
    public IMarker createMarker(String type) throws CoreException {
        return null;
    }

    /**
     * @see org.eclipse.core.resources.IResource#createProxy()
     * 
     * @return
     */
    @Override
    public IResourceProxy createProxy() {
        return null;
    }

    /**
     * @see org.eclipse.core.resources.IResource#delete(boolean,
     *      org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param force
     * @param monitor
     * @throws CoreException
     */
    @Override
    public void delete(boolean force, IProgressMonitor monitor)
            throws CoreException {
    }

    /**
     * @see org.eclipse.core.resources.IResource#delete(int,
     *      org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param updateFlags
     * @param monitor
     * @throws CoreException
     */
    @Override
    public void delete(int updateFlags, IProgressMonitor monitor)
            throws CoreException {
    }

    /**
     * @see org.eclipse.core.resources.IResource#deleteMarkers(java.lang.String,
     *      boolean, int)
     * 
     * @param type
     * @param includeSubtypes
     * @param depth
     * @throws CoreException
     */
    @Override
    public void deleteMarkers(String type, boolean includeSubtypes, int depth)
            throws CoreException {
    }

    /**
     * @see org.eclipse.core.resources.IResource#exists()
     * 
     * @return
     */
    @Override
    public boolean exists() {
        return false;
    }

    /**
     * @see org.eclipse.core.resources.IResource#findMarker(long)
     * 
     * @param id
     * @return
     * @throws CoreException
     */
    @Override
    public IMarker findMarker(long id) throws CoreException {
        return null;
    }

    /**
     * @see org.eclipse.core.resources.IResource#findMarkers(java.lang.String,
     *      boolean, int)
     * 
     * @param type
     * @param includeSubtypes
     * @param depth
     * @return
     * @throws CoreException
     */
    @Override
    public IMarker[] findMarkers(String type, boolean includeSubtypes, int depth)
            throws CoreException {
        return null;
    }

    /**
     * @see org.eclipse.core.resources.IResource#findMaxProblemSeverity(java.lang.String,
     *      boolean, int)
     * 
     * @param type
     * @param includeSubtypes
     * @param depth
     * @return
     * @throws CoreException
     */
    @Override
    public int findMaxProblemSeverity(String type, boolean includeSubtypes,
            int depth) throws CoreException {
        return 0;
    }

    /**
     * @see org.eclipse.core.resources.IResource#getFileExtension()
     * 
     * @return
     */
    @Override
    public String getFileExtension() {
        return relativePath.getFileExtension();
    }

    /**
     * @see org.eclipse.core.resources.IResource#getFullPath()
     * 
     * @return
     */
    @Override
    public IPath getFullPath() {
        return relativePath;
    }

    /**
     * @see org.eclipse.core.resources.IResource#getLocalTimeStamp()
     * 
     * @return
     */
    @Override
    public long getLocalTimeStamp() {
        return 0;
    }

    /**
     * @see org.eclipse.core.resources.IResource#getLocation()
     * 
     * @return
     */
    @Override
    public IPath getLocation() {
        return relativePath;
    }

    /**
     * @see org.eclipse.core.resources.IResource#getLocationURI()
     * 
     * @return
     */
    @Override
    public URI getLocationURI() {
        return relativePath.toFile() != null ? relativePath.toFile().toURI()
                : null;
    }

    /**
     * @see org.eclipse.core.resources.IResource#getMarker(long)
     * 
     * @param id
     * @return
     */
    @Override
    public IMarker getMarker(long id) {
        return null;
    }

    /**
     * @see org.eclipse.core.resources.IResource#getModificationStamp()
     * 
     * @return
     */
    @Override
    public long getModificationStamp() {
        return 0;
    }

    /**
     * @see org.eclipse.core.resources.IResource#getName()
     * 
     * @return
     */
    @Override
    public String getName() {
        return relativePath.toString();
    }

    /**
     * @see org.eclipse.core.resources.IResource#getParent()
     * 
     * @return
     */
    @Override
    public IContainer getParent() {
        return null;
    }

    /**
     * @see org.eclipse.core.resources.IResource#getPersistentProperties()
     * 
     * @return
     * @throws CoreException
     */
    @Override
    public Map getPersistentProperties() throws CoreException {
        return null;
    }

    /**
     * @see org.eclipse.core.resources.IResource#getPersistentProperty(org.eclipse.core.runtime.QualifiedName)
     * 
     * @param key
     * @return
     * @throws CoreException
     */
    @Override
    public String getPersistentProperty(QualifiedName key) throws CoreException {
        return null;
    }

    /**
     * @see org.eclipse.core.resources.IResource#getProject()
     * 
     * @return
     */
    @Override
    public IProject getProject() {
        return null;
    }

    /**
     * @see org.eclipse.core.resources.IResource#getProjectRelativePath()
     * 
     * @return
     */
    @Override
    public IPath getProjectRelativePath() {
        return null;
    }

    /**
     * @see org.eclipse.core.resources.IResource#getRawLocation()
     * 
     * @return
     */
    @Override
    public IPath getRawLocation() {
        return getLocation();
    }

    /**
     * @see org.eclipse.core.resources.IResource#getRawLocationURI()
     * 
     * @return
     */
    @Override
    public URI getRawLocationURI() {
        return getLocationURI();
    }

    /**
     * @see org.eclipse.core.resources.IResource#getResourceAttributes()
     * 
     * @return
     */
    @Override
    public ResourceAttributes getResourceAttributes() {
        return null;
    }

    /**
     * @see org.eclipse.core.resources.IResource#getSessionProperties()
     * 
     * @return
     * @throws CoreException
     */
    @Override
    public Map getSessionProperties() throws CoreException {
        return null;
    }

    /**
     * @see org.eclipse.core.resources.IResource#getSessionProperty(org.eclipse.core.runtime.QualifiedName)
     * 
     * @param key
     * @return
     * @throws CoreException
     */
    @Override
    public Object getSessionProperty(QualifiedName key) throws CoreException {
        return null;
    }

    /**
     * @see org.eclipse.core.resources.IResource#getType()
     * 
     * @return
     */
    @Override
    public int getType() {
        return IResource.FILE;
    }

    /**
     * @see org.eclipse.core.resources.IResource#getWorkspace()
     * 
     * @return
     */
    @Override
    public IWorkspace getWorkspace() {
        return ResourcesPlugin.getWorkspace();
    }

    /**
     * @see org.eclipse.core.resources.IResource#isAccessible()
     * 
     * @return
     */
    @Override
    public boolean isAccessible() {
        return false;
    }

    /**
     * @see org.eclipse.core.resources.IResource#isDerived()
     * 
     * @return
     */
    @Override
    public boolean isDerived() {
        return false;
    }

    /**
     * @see org.eclipse.core.resources.IResource#isDerived(int)
     * 
     * @param options
     * @return
     */
    @Override
    public boolean isDerived(int options) {
        return false;
    }

    /**
     * @see org.eclipse.core.resources.IResource#isHidden()
     * 
     * @return
     */
    @Override
    public boolean isHidden() {
        return false;
    }

    /**
     * @see org.eclipse.core.resources.IResource#isLinked()
     * 
     * @return
     */
    @Override
    public boolean isLinked() {
        return false;
    }

    /**
     * @see org.eclipse.core.resources.IResource#isLinked(int)
     * 
     * @param options
     * @return
     */
    @Override
    public boolean isLinked(int options) {
        return false;
    }

    /**
     * @see org.eclipse.core.resources.IResource#isLocal(int)
     * 
     * @param depth
     * @return
     * @deprecated
     */
    @Deprecated
    @Override
    public boolean isLocal(int depth) {
        return false;
    }

    /**
     * @see org.eclipse.core.resources.IResource#isPhantom()
     * 
     * @return
     */
    @Override
    public boolean isPhantom() {
        return false;
    }

    /**
     * @see org.eclipse.core.resources.IResource#isReadOnly()
     * 
     * @return
     * @deprecated
     */
    @Deprecated
    @Override
    public boolean isReadOnly() {
        return false;
    }

    /**
     * @see org.eclipse.core.resources.IResource#isSynchronized(int)
     * 
     * @param depth
     * @return
     */
    @Override
    public boolean isSynchronized(int depth) {
        return false;
    }

    /**
     * @see org.eclipse.core.resources.IResource#isTeamPrivateMember()
     * 
     * @return
     */
    @Override
    public boolean isTeamPrivateMember() {
        return false;
    }

    /**
     * @see org.eclipse.core.resources.IResource#move(org.eclipse.core.runtime.IPath,
     *      boolean, org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param destination
     * @param force
     * @param monitor
     * @throws CoreException
     */
    @Override
    public void move(IPath destination, boolean force, IProgressMonitor monitor)
            throws CoreException {

    }

    /**
     * @see org.eclipse.core.resources.IResource#move(org.eclipse.core.runtime.IPath,
     *      int, org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param destination
     * @param updateFlags
     * @param monitor
     * @throws CoreException
     */
    @Override
    public void move(IPath destination, int updateFlags,
            IProgressMonitor monitor) throws CoreException {

    }

    /**
     * @see org.eclipse.core.resources.IResource#move(org.eclipse.core.resources.IProjectDescription,
     *      boolean, boolean, org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param description
     * @param force
     * @param keepHistory
     * @param monitor
     * @throws CoreException
     */
    @Override
    public void move(IProjectDescription description, boolean force,
            boolean keepHistory, IProgressMonitor monitor) throws CoreException {

    }

    /**
     * @see org.eclipse.core.resources.IResource#move(org.eclipse.core.resources.IProjectDescription,
     *      int, org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param description
     * @param updateFlags
     * @param monitor
     * @throws CoreException
     */
    @Override
    public void move(IProjectDescription description, int updateFlags,
            IProgressMonitor monitor) throws CoreException {

    }

    /**
     * @see org.eclipse.core.resources.IResource#refreshLocal(int,
     *      org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param depth
     * @param monitor
     * @throws CoreException
     */
    @Override
    public void refreshLocal(int depth, IProgressMonitor monitor)
            throws CoreException {

    }

    /**
     * @see org.eclipse.core.resources.IResource#revertModificationStamp(long)
     * 
     * @param value
     * @throws CoreException
     */
    @Override
    public void revertModificationStamp(long value) throws CoreException {

    }

    /**
     * @see org.eclipse.core.resources.IResource#setDerived(boolean)
     * 
     * @param isDerived
     * @throws CoreException
     */
    @Override
    public void setDerived(boolean isDerived) throws CoreException {

    }

    /**
     * @see org.eclipse.core.resources.IResource#setHidden(boolean)
     * 
     * @param isHidden
     * @throws CoreException
     */
    @Override
    public void setHidden(boolean isHidden) throws CoreException {

    }

    /**
     * @see org.eclipse.core.resources.IResource#setLocal(boolean, int,
     *      org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param flag
     * @param depth
     * @param monitor
     * @throws CoreException
     * @deprecated
     */
    @Deprecated
    @Override
    public void setLocal(boolean flag, int depth, IProgressMonitor monitor)
            throws CoreException {

    }

    /**
     * @see org.eclipse.core.resources.IResource#setLocalTimeStamp(long)
     * 
     * @param value
     * @return
     * @throws CoreException
     */
    @Override
    public long setLocalTimeStamp(long value) throws CoreException {
        return 0;
    }

    /**
     * @see org.eclipse.core.resources.IResource#setPersistentProperty(org.eclipse.core.runtime.QualifiedName,
     *      java.lang.String)
     * 
     * @param key
     * @param value
     * @throws CoreException
     */
    @Override
    public void setPersistentProperty(QualifiedName key, String value)
            throws CoreException {

    }

    /**
     * @see org.eclipse.core.resources.IResource#setReadOnly(boolean)
     * 
     * @param readOnly
     * @deprecated
     */
    @Deprecated
    @Override
    public void setReadOnly(boolean readOnly) {

    }

    /**
     * @see org.eclipse.core.resources.IResource#setResourceAttributes(org.eclipse.core.resources.ResourceAttributes)
     * 
     * @param attributes
     * @throws CoreException
     */
    @Override
    public void setResourceAttributes(ResourceAttributes attributes)
            throws CoreException {

    }

    /**
     * @see org.eclipse.core.resources.IResource#setSessionProperty(org.eclipse.core.runtime.QualifiedName,
     *      java.lang.Object)
     * 
     * @param key
     * @param value
     * @throws CoreException
     */
    @Override
    public void setSessionProperty(QualifiedName key, Object value)
            throws CoreException {

    }

    /**
     * @see org.eclipse.core.resources.IResource#setTeamPrivateMember(boolean)
     * 
     * @param isTeamPrivate
     * @throws CoreException
     */
    @Override
    public void setTeamPrivateMember(boolean isTeamPrivate)
            throws CoreException {

    }

    /**
     * @see org.eclipse.core.resources.IResource#touch(org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param monitor
     * @throws CoreException
     */
    @Override
    public void touch(IProgressMonitor monitor) throws CoreException {

    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj instanceof DependencyProxyResource) {
            DependencyProxyResource other = (DependencyProxyResource) obj;

            // Check if all values match
            return ((relativePath != null && relativePath
                    .equals(other.relativePath)) || relativePath == other.relativePath)
                    && ((specialFolderKind != null && specialFolderKind
                            .equals(other.specialFolderKind)) || specialFolderKind == other.specialFolderKind);

        }
        return false;
    }

    /**
     * @see java.lang.Object#hashCode()
     * 
     * @return
     */
    @Override
    public int hashCode() {
        if (specialFolderKind != null) {
            return relativePath.hashCode() + specialFolderKind.hashCode();
        }

        return relativePath.hashCode();
    }

    /**
     * @see java.lang.Object#toString()
     * 
     * @return
     */
    @Override
    public String toString() {
        return String.format("Proxy '%s' (special folder: '%s')", //$NON-NLS-1$
                relativePath,
                specialFolderKind);
    }

    /**
     * @see org.eclipse.core.resources.IResource#getPathVariableManager()
     * 
     * @return
     */
    @Override
    public IPathVariableManager getPathVariableManager() {
        return null;
    }

    /**
     * @see org.eclipse.core.resources.IResource#isHidden(int)
     * 
     * @param options
     * @return
     */
    @Override
    public boolean isHidden(int options) {
        return false;
    }

    /**
     * @see org.eclipse.core.resources.IResource#isVirtual()
     * 
     * @return
     */
    @Override
    public boolean isVirtual() {
        return false;
    }

    /**
     * @see org.eclipse.core.resources.IResource#isTeamPrivateMember(int)
     * 
     * @param options
     * @return
     */
    @Override
    public boolean isTeamPrivateMember(int options) {
        return false;
    }

    /**
     * @see org.eclipse.core.resources.IResource#setDerived(boolean,
     *      org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param isDerived
     * @param monitor
     * @throws CoreException
     */
    @Override
    public void setDerived(boolean isDerived, IProgressMonitor monitor)
            throws CoreException {
    }

    /**
     * @see org.eclipse.core.resources.IResource#accept(org.eclipse.core.resources.IResourceProxyVisitor,
     *      int, int)
     *
     * @param visitor
     * @param depth
     * @param memberFlags
     * @throws CoreException
     */
    @Override
    public void accept(IResourceProxyVisitor visitor, int depth, int memberFlags)
            throws CoreException {
    }
}
