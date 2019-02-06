/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.n2.daa.test.junit;

/**
 * MultiProjectDAAGenerationWithProgress for junit purposes. This will return an
 * instance of JUnitProjectDAAGenerator which mainly returns the time stamp of
 * gold DAA to be used as a replacement in junit test generated DAA
 * 
 * @author bharge
 * @since 29 Nov 2013
 */
public class JUnitMultiProjectDAAGenerationWithProgress {

    /*
     * SID ACE-122 - commenting out for now - as may be usefil to preserve the
     * test projects at least from this implementation.
     */
    // extends
    // MultiProjectDAAGenerationWithProgress {
    //
    // /**
    // * @param projects
    // * @param preserveDaaAfterGeneration
    // */
    // public JUnitMultiProjectDAAGenerationWithProgress(List<IProject>
    // projects,
    // boolean preserveDaaAfterGeneration) {
    //
    // super(projects, preserveDaaAfterGeneration);
    // }
    //
    // /**
    // * @see
    // com.tibco.xpd.n2.daa.internal.wizards.MultiProjectDAAGenerationWithProgress#getProjectDaaGenerator()
    // *
    // * @return
    // */
    // @Override
    // protected BaseProjectDAAGenerator getProjectDaaGenerator() {
    //
    // return JUnitProjectDAAGenerator.getInstance();
    // }
}
