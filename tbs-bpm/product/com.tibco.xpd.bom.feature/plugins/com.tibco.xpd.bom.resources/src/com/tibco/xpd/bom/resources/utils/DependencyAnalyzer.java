/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.resources.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;

import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.DependencySorter;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.util.DependencySorter.Arc;

/**
 * Dependency analyzer that will build a dependency list from a given BOM file.
 * 
 * @author njpatel
 * 
 */
public class DependencyAnalyzer {

    private final DependencySorter<IFile> sorter;

    private List<IFile> processedFiles = new ArrayList<IFile>();

    private List<Arc<IFile>> arcs = new ArrayList<Arc<IFile>>();

    public DependencyAnalyzer(Collection<IFile> files) {
        sorter = runDependencyAnalysis(files);
    }

    /**
     * Get the ordered list of dependencies.
     * 
     * @return
     */
    public List<IFile> getResult() {
        return sorter != null ? sorter.getOrderedList() : null;
    }

    /**
     * Get all direct and indirect BOM resources that the given BOM resource
     * file depends on.
     * 
     * @param file
     * @return
     */
    public List<IFile> getAllDependencies(IFile file) {
        HashSet<IFile> alreadyProcessed = new HashSet<IFile>();
        alreadyProcessed.add(file);
        return internalGetDependencies(file, alreadyProcessed);
    }

    /**
     * @param file
     * @param alreadyProcessed
     * @return dependencies (recursive)
     */
    private List<IFile> internalGetDependencies(IFile file,
            HashSet<IFile> alreadyProcessed) {
        List<IFile> dependencies = new ArrayList<IFile>();

        List<IFile> fileDependencies = getDependencies(file);
        for (IFile dependency : fileDependencies) {
            if (!alreadyProcessed.contains(dependency)) {
                alreadyProcessed.add(dependency);
                dependencies.addAll(internalGetDependencies(dependency,
                        alreadyProcessed));
                dependencies.add(dependency);
            }
        }

        return dependencies;
    }

    /**
     * Get all direct BOM resources that the given BOM resource file depends on.
     * 
     * @param file
     * @return
     */
    public List<IFile> getDependencies(IFile file) {
        List<IFile> dependencies = new ArrayList<IFile>();
        if (file != null) {
            for (Arc<IFile> arc : arcs) {
                if (arc.getFrom().equals(file)
                        && !dependencies.contains(arc.getTo())) {
                    dependencies.add(arc.getTo());
                }
            }
        }
        return dependencies;
    }

    /**
     * Calculate the dependencies for the selected resources.
     * 
     * @param selectedResources
     */
    private DependencySorter<IFile> runDependencyAnalysis(
            Collection<IFile> selectedResources) {
        DependencySorter<IFile> sorter = null;
        if (selectedResources != null && !selectedResources.isEmpty()) {
            List<IFile> nodes = new ArrayList<IFile>();
            for (IFile file : selectedResources) {
                arcs.addAll(calculateArcs(file, nodes));
            }

            sorter = new DependencySorter<IFile>(arcs, nodes);

        }
        return sorter;
    }

    /**
     * Calculate the dependency arcs of the given resource. This will drill down
     * all the dependent files and calcuate the full dependency tree.
     * 
     * @param file
     *            file to calculate dependecies of
     * @param nodes
     *            all nodes collected
     * @return
     */
    private List<Arc<IFile>> calculateArcs(IFile file, List<IFile> nodes) {
        List<Arc<IFile>> arcs = new ArrayList<Arc<IFile>>();

        if (!processedFiles.contains(file)) {
            // Keep track of which files have been processed - to avoid cyclic
            // processing
            processedFiles.add(file);
            if (!nodes.contains(file)) {
                nodes.add(file);
            }
            List<IFile> dependencies = calculateDependencies(file);
            if (dependencies != null && !dependencies.isEmpty()) {
                for (IFile dependency : dependencies) {
                    arcs.add(new Arc<IFile>(file, dependency));
                    if (!nodes.contains(dependency)) {
                        nodes.add(dependency);
                    }

                    arcs.addAll(calculateArcs(dependency, nodes));
                }
            }
        }

        return arcs;
    }

    /**
     * Get all BOM resources that the given model depends on.
     * 
     * @param modelFile
     * 
     * @return
     */
    private List<IFile> calculateDependencies(IFile modelFile) {
        if (modelFile != null) {

            WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(modelFile);
            if (wc != null) {
                List<IResource> dependency = wc.getDependency();
                if (dependency != null) {
                    List<IFile> dependencies = new ArrayList<IFile>();
                    for (IResource resource : dependency) {
                        WorkingCopy workingCopy =
                                WorkingCopyUtil.getWorkingCopy(resource);
                        if (workingCopy instanceof BOMWorkingCopy) {
                            dependencies.add((IFile) resource);
                        }
                    }
                    return dependencies;
                }
            }
        }
        return null;
    }
}