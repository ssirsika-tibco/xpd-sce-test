package com.tibco.xpd.resources.builder.ondemand;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IResource;

/**
 * Represents one unit or sources that are used to build a unit of targets. This
 * allows for a 1-to-many, many-to-1 or many-to-many source/target relationship.
 * <p>
 * This can be overridden to store relevant data over and above the simple
 * underlying set of resources.
 * <p>
 * Note that during clean up phase (removing unused old targets from previous
 * builds) the content of any FOLDER named EXPLICITLY in a target set (rather
 * than implicitly via target resource files) will NOT have it's contents
 * checked and removed - so the builder is responsible for removing any unwanted
 * content from any folder named explicitly in BuildTargetSet (see
 * {@link AbstractOnDemandBuilder#removeOldUnusedTargets(java.util.Map, org.eclipse.core.runtime.IProgressMonitor)}
 * for more details). The builder should also ensure that the folder local
 * timestamp is updated as it will be included in the check that all target
 * resources are later than all the source resources.
 * 
 * @author aallway
 * @since 14 Dec 2011
 */
public class BuildTargetSet {
    private String name;

    private Set<IResource> targetResources = new HashSet<IResource>();

    /**
     * @param mainTargetResource
     *            The main target resource, this is automatically added to the
     *            set.
     */
    public BuildTargetSet(String name) {
        super();
        this.name = name;
    }

    /**
     * Add a resource to the target set.
     * <p>
     * <b>Note that IF a folder/project is explicitly identified as a target
     * resource then it is a statement that the implementation of
     * {@link AbstractOnDemandBuilder#buildSourceSet(BuildSourceSet, BuildTargetSet, org.eclipse.core.runtime.SubProgressMonitor)}
     * will handle the cleanup of any unused (previously built) content of this
     * folder/project.</b>
     * <p>
     * In this situation the
     * {@link AbstractOnDemandBuilder#buildSourceSet(BuildSourceSet, BuildTargetSet, org.eclipse.core.runtime.SubProgressMonitor)}
     * method should also ensure that the folder local timestamp is updated as
     * it will be included in the check that all target resources are later than
     * all the source resources.
     * 
     * @param targetResource
     */
    public void addTargetResource(IResource targetResource) {
        if (!targetResources.contains(targetResource)) {
            targetResources.add(targetResource);
        }
    }

    /**
     * @return the resources in this target set.
     */
    public Set<IResource> getTargetResources() {
        return targetResources;
    }

    /**
     * @return Get the earliest timestamp in the target set.
     */
    public long getEarliestTimeStamp() {
        long timestamp = Long.MAX_VALUE;

        for (IResource resource : targetResources) {
            long ts = resource.getLocalTimeStamp();

            if (ts < timestamp) {
                timestamp = ts;
            }
        }

        return timestamp;
    }

    /**
     * Check if all resources in the target set exist.
     * 
     * @return <code>true</code> if all target resources exist.
     */
    public boolean allTargetsExist() {
        boolean ret = true;
        for (IResource resource : targetResources) {
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
     * @return list of targets
     */
    public String listTargets(String separator) {
        StringBuilder sb = new StringBuilder();
        boolean first = true;

        for (IResource resource : targetResources) {
            sb.append(resource.getFullPath().toString());
            if (!first) {
                sb.append(separator);
            }
            first = false;
        }
        return sb.toString();
    }
}