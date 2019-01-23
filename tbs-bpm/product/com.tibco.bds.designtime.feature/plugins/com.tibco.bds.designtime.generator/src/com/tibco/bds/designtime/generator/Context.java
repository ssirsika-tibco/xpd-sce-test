package com.tibco.bds.designtime.generator;

import java.util.Collection;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;

public class Context {

    private IFile bomFile;

    private IProject outputProject;

    private Collection<IProject> foreignProjects;

    private String bundleName;
    
    private String bundleVersion;

    public String getBundleVersion() {
        return bundleVersion;
    }

    public void setBundleVersion(String bundleVersion) {
        this.bundleVersion = bundleVersion;
    }

    public String getBundleName() {
        return bundleName;
    }

    public void setBundleName(String bundleName) {
        this.bundleName = bundleName;
    }

    public void setBOMFile(IFile bomFile) {
        this.bomFile = bomFile;
    }

    public IFile getBOMFile() {
        return bomFile;
    }

    public void setOutputProject(IProject project) {
        this.outputProject = project;
    }

    public IProject getOutputProject() {
        return outputProject;
    }

    public void setForeignProjects(Collection<IProject> projects) {
        this.foreignProjects = projects;
    }

    public Collection<IProject> getForeignProjects() {
        return foreignProjects;
    }

}
