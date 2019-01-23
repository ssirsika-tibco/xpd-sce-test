package com.tibco.xpd.resources.builder.ondemand;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IResource;

/**
 * Represents one unit of sources that are used to build a unit of targets. This
 * allows for a 1-to-many, many-to-1 or many-to-many source/target relationship.
 * <p>
 * This can be overridden to store relevant data over and above the simple
 * underlying set of resources.
 * 
 * @author aallway
 * @since 14 Dec 2011
 */
public class BuildSourceSet {

    private Set<IResource> sourceResources = new HashSet<IResource>();

    private String name;

    /**
     * @param mainSourceResource
     *            The main source resource, this is automatically added to the
     *            set.
     */
    public BuildSourceSet(String name) {
        super();
        this.name = name;
    }

    /**
     * Add a resource to the build source set.
     * 
     * @param sourceResource
     */
    public void addSourceResource(IResource sourceResource) {
        if (!sourceResources.contains(sourceResource)) {
            sourceResources.add(sourceResource);
        }
    }

    /**
     * @return the resources in this build source set.
     */
    public Set<IResource> getSourceResources() {
        return sourceResources;
    }

    /**
     * @return Get the latest timestamp in the source set.
     */
    public long getLatestTimeStamp() {
        long timestamp = -1;

        for (IResource resource : sourceResources) {
            long ts = resource.getLocalTimeStamp();

            if (ts > timestamp) {
                timestamp = ts;
            }
        }

        return timestamp;
    }

    /**
     * Check if all resources in the source set exist.
     * 
     * @return <code>true</code> if all target resources exist.
     */
    public boolean allSourcesExist() {
        boolean ret = true;
        for (IResource resource : sourceResources) {
            if (!resource.exists()) {
                ret = false;
                break;
            }
        }

        return ret;
    }

    /**
     * 
     * @return The name of this target set (defaults to main resource file
     *         name).
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return list of sources
     */
    public String listSources(String separator) {
        StringBuilder sb = new StringBuilder();
        boolean first = true;

        for (IResource resource : sourceResources) {
            sb.append(resource.getFullPath().toString());
            if (!first) {
                sb.append(separator);
            }
            first = false;
        }
        return sb.toString();
    }
}