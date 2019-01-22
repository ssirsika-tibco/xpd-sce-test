/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.gen.api;

import java.util.Collection;
import java.util.HashSet;

import org.eclipse.core.resources.IFile;

/**
 * The data used by the {@link BOMGenerator2} during the generation lifecycle.
 * 
 * @author njpatel
 * 
 * @since 3.3
 * 
 */
public class GeneratorData {
    /**
     * Type of build requested by the BOM generator.
     * 
     * @author njpatel
     * 
     */
    public enum BuildType {
        CLEAN, FULL, INCREMENTAL;
    }

    private BuildType buildType;

    private Collection<IFile> initialSelection;

    /**
     * List of BOMs that have already had their projects created for thus
     * contribution during this build cycle. This is used during a build cycle
     * to decide whether a BOM has already had its target project created for a
     * BOM because that BOM may be a dependency of multiple other BOMs and
     * therefore will get passed to
     * {@link BOMGenerator2#initialize(Collection, GeneratorData, org.eclipse.core.runtime.IProgressMonitor)}
     * multiple times.
     * 
     * @since 3.5
     */
    private Collection<IFile> bomsAlreadyDone;

    public GeneratorData() {
    }

    /**
     * 
     * This is not really an initial selection, it is used to force a build of
     * the listed files.
     * 
     * @param type
     * @param initialSelection
     */
    public GeneratorData(BuildType type, Collection<IFile> initialSelection) {
        setBuildType(type);
        setInitialSelection(initialSelection);
        this.bomsAlreadyDone = new HashSet<IFile>();
    }

    /**
     * Set the type of build required.
     * 
     * @param buildType
     */
    public void setBuildType(BuildType buildType) {
        this.buildType = buildType;
    }

    /**
     * Get the type of build required.
     * 
     * @return
     */
    public BuildType getBuildType() {
        return buildType;
    }

    /**
     * Set the initial BOM resource selection that was a trigger to the
     * generator call.
     * 
     * This is not really an initial selection, it is used to force a build of
     * the listed files.
     * 
     * @param initialSelection
     */
    public void setInitialSelection(Collection<IFile> initialSelection) {
        this.initialSelection = initialSelection;
    }

    /**
     * Get the BOM resources that were initially selected for this generator
     * run. If called from the wizard this will contain the resource the user
     * selected to generate from and if from the builder it will contain the
     * file being built.
     * 
     * @return
     */
    public Collection<IFile> getInitialSelection() {
        return initialSelection;
    }

    /**
     * List of BOMs that have already had their projects created for thus
     * contribution during this build cycle. This is used during a build cycle
     * to decide whether a BOM has already had its target project created for a
     * BOM because that BOM may be a dependency of multiple other BOMs and
     * therefore will get passed to
     * {@link BOMGenerator2#initialize(Collection, GeneratorData, org.eclipse.core.runtime.IProgressMonitor)}
     * multiple times.
     * 
     * @return the bomsTodoList
     * 
     * @since 3.5
     */
    public Collection<IFile> getBomsAlreadyDone() {
        return bomsAlreadyDone;
    }

}
