/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.customer.api.test.iprocess.amxbpm.testcontributions;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;

import com.tibco.xpd.customer.api.iprocess.amxbpm.conversion.AbstractIProcessToBPMContribution;
import com.tibco.xpd.customer.api.iprocess.amxbpm.conversion.AbstractIProcessToBPMLifeCycleListener;
import com.tibco.xpd.xpdl2.Package;

/**
 * Dummy extension of AbstractIProcessToBPMLifeCycleListener which copies all
 * the method parameters to its own class attributes which are then evaluated by
 * the JUnit IpmBpm10_LifeCycleListenerTest to make sure that all parameters are
 * being loaded properly.
 * 
 * @author sajain
 * @since Jun 10, 2014
 */
public class IpmBpm10_LifeCycleListenerTest_DummyListener extends
        AbstractIProcessToBPMLifeCycleListener {

    /**
     * This collection will copy the contents of "studioIProcessPackages" which
     * is a parameter of
     * AbstractIProcessToBPMLifeCycleListener.packageSeparationComplete()
     * method. We'll use this collection in IpmBpm10_LifeCycleListenerTest to
     * check if the "studioIProcessPackages" parameter is loaded correctly
     * during the conversion life cycle.
     */
    private static Collection<Package> studioIProcessPackagesAttr;

    /**
     * This collection will copy the contents of "initialFileSet" which is a
     * parameter of
     * AbstractIProcessToBPMLifeCycleListener.importAndMigrateComplete() method.
     * We'll use this collection in IpmBpm10_LifeCycleListenerTest to check if
     * the "initialFileSet" parameter is loaded correctly during the conversion
     * life cycle.
     */
    private static Collection<IFile> initialFileSetAttr;

    /**
     * This collection will copy the contents of "studioBPMPackages" which is a
     * parameter of
     * AbstractIProcessToBPMLifeCycleListener.conversionExtensionsComplete()
     * method. We'll use this collection in IpmBpm10_LifeCycleListenerTest to
     * check if the "studioBPMPackages" parameter is loaded correctly during the
     * conversion life cycle.
     */
    private static Collection<Package> studioBPMPackagesAttr;

    /**
     * This collection will copy the contents of "executedExtensions" which is a
     * parameter of
     * AbstractIProcessToBPMLifeCycleListener.conversionExtensionsComplete()
     * method. We'll use this collection in IpmBpm10_LifeCycleListenerTest to
     * check if the "executedExtensions" parameter is loaded correctly during
     * the conversion life cycle.
     */
    private static Collection<AbstractIProcessToBPMContribution> executedExtensionsAttr;

    /**
     * This collection will copy the contents of "finalFileSet" which is a
     * parameter of AbstractIProcessToBPMLifeCycleListener.conversionComplete()
     * method. We'll use this collection in IpmBpm10_LifeCycleListenerTest to
     * check if the "finalFileSet" parameter is loaded correctly during the
     * conversion life cycle.
     */
    private static Collection<IFile> finalFileSetAttr;

    /**
     * This collection will copy the contents of "finalStudioBPMPackages" which
     * is a parameter of
     * AbstractIProcessToBPMLifeCycleListener.conversionComplete() method. We'll
     * use this collection in IpmBpm10_LifeCycleListenerTest to check if the
     * "finalStudioBPMPackages" parameter is loaded correctly during the
     * conversion life cycle.
     */
    private static Collection<Package> finalStudioBPMPackagesAttr;

    /**
     * This collection will copy the contents of "ignoredDuplicatePackages"
     * which is a parameter of
     * AbstractIProcessToBPMLifeCycleListener.conversionComplete() method. We'll
     * use this collection in IpmBpm10_LifeCycleListenerTest to check if the
     * "ignoredDuplicatePackages" parameter is loaded correctly during the
     * conversion life cycle.
     */
    private static Collection<Package> ignoredDuplicatePackagesAttr;

    /**
     * Default constructor which will re-initialise all the class attributes.
     * <p>
     * If the user wants to define an instance of this class without
     * re-initialising the class attributes, then call
     * IpmBpm10_LifeCycleListenerTest_DummyListener(boolean
     * initialiseClassAttributes) constructor with
     * <code>initialiseClassAttributes</code> parameter as <code>false</code>.
     * 
     */
    public IpmBpm10_LifeCycleListenerTest_DummyListener() {

        initialFileSetAttr = new ArrayList<IFile>();

        studioIProcessPackagesAttr = new ArrayList<Package>();

        studioBPMPackagesAttr = new ArrayList<Package>();

        executedExtensionsAttr =
                new ArrayList<AbstractIProcessToBPMContribution>();

        finalFileSetAttr = new ArrayList<IFile>();

        finalStudioBPMPackagesAttr = new ArrayList<Package>();

        ignoredDuplicatePackagesAttr = new ArrayList<Package>();
    }

    /**
     * There are cases when we do not want to re-initialise the class attributes
     * while defining an instance of this class.
     * <p>
     * For example, in IpmBpm10_LifeCycleListenerTest we don't want the class
     * attributes to be re-initialised because the test is designed to check the
     * contents of the class attributes.
     * <p>
     * So this constructor should be called with initialiseClassAttributes as
     * <code>false</code> if we do not want to re-initialise the attributes.
     * 
     * @param initialiseClassAttributes
     */
    public IpmBpm10_LifeCycleListenerTest_DummyListener(
            boolean initialiseClassAttributes) {

        if (initialiseClassAttributes) {
            initialFileSetAttr = new ArrayList<IFile>();

            studioIProcessPackagesAttr = new ArrayList<Package>();

            studioBPMPackagesAttr = new ArrayList<Package>();

            executedExtensionsAttr =
                    new ArrayList<AbstractIProcessToBPMContribution>();

            finalFileSetAttr = new ArrayList<IFile>();

            finalStudioBPMPackagesAttr = new ArrayList<Package>();

            ignoredDuplicatePackagesAttr = new ArrayList<Package>();
        }
    }

    /**
     * @see com.tibco.xpd.customer.api.iprocess.amxbpm.conversion.AbstractIProcessToBPMLifeCycleListener#importAndMigrationComplete(java.util.Collection,
     *      org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param initialFileSet
     * @param monitor
     * @return
     */
    @Override
    public IStatus importAndMigrationComplete(Collection<IFile> initialFileSet,
            IProgressMonitor monitor) {

        initialFileSetAttr.addAll(initialFileSet);

        return null;
    }

    /**
     * @see com.tibco.xpd.customer.api.iprocess.amxbpm.conversion.AbstractIProcessToBPMLifeCycleListener#packageSeparationComplete(java.util.Collection,
     *      org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param studioIProcessPackages
     * @param monitor
     * @return
     */
    @Override
    public IStatus packageSeparationComplete(
            Collection<Package> studioIProcessPackages, IProgressMonitor monitor) {

        studioIProcessPackagesAttr.addAll(studioIProcessPackages);

        return null;
    }

    /**
     * @see com.tibco.xpd.customer.api.iprocess.amxbpm.conversion.AbstractIProcessToBPMLifeCycleListener#conversionExtensionsComplete(java.util.Collection,
     *      java.util.Collection, org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param studioBPMPackages
     * @param executedExtensions
     * @param monitor
     * @return
     */
    @Override
    public IStatus conversionExtensionsComplete(
            Collection<Package> studioBPMPackages,
            Collection<AbstractIProcessToBPMContribution> executedExtensions,
            IProgressMonitor monitor) {

        studioBPMPackagesAttr.addAll(studioBPMPackages);

        executedExtensionsAttr.addAll(executedExtensions);

        return null;
    }

    /**
     * @see com.tibco.xpd.customer.api.iprocess.amxbpm.conversion.AbstractIProcessToBPMLifeCycleListener#conversionComplete(java.util.Collection,
     *      java.util.Collection, java.util.Collection,
     *      org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param finalFileSet
     * @param studioBPMPackages
     * @param ignoredDuplicatePackages
     * @param monitor
     * @return
     */
    @Override
    public IStatus conversionComplete(Collection<IFile> finalFileSet,
            Collection<Package> studioBPMPackages,
            Collection<Package> ignoredDuplicatePackages,
            IProgressMonitor monitor) {

        finalFileSetAttr.addAll(finalFileSet);

        finalStudioBPMPackagesAttr.addAll(studioBPMPackages);

        ignoredDuplicatePackagesAttr.addAll(ignoredDuplicatePackages);

        return null;
    }

    /**
     * @return the studioIProcessPackagesAttr
     */
    public Collection<Package> getStudioIProcessPackagesAttr() {
        return studioIProcessPackagesAttr;
    }

    /**
     * @return the initialFileSetAttr
     */
    public Collection<IFile> getInitialFileSetAttr() {
        return initialFileSetAttr;
    }

    /**
     * @return the studioBPMPackagesAttr
     */
    public Collection<Package> getStudioBPMPackagesAttr() {
        return studioBPMPackagesAttr;
    }

    /**
     * @return the executedExtensionsAttr
     */
    public Collection<AbstractIProcessToBPMContribution> getExecutedExtensionsAttr() {
        return executedExtensionsAttr;
    }

    /**
     * @return the finalFileSetAttr
     */
    public Collection<IFile> getFinalFileSetAttr() {
        return finalFileSetAttr;
    }

    /**
     * @return the finalStudioBPMPackagesAttr
     */
    public Collection<Package> getFinalStudioBPMPackagesAttr() {
        return finalStudioBPMPackagesAttr;
    }

    /**
     * @return the ignoredDuplicatePackagesAttr
     */
    public Collection<Package> getIgnoredDuplicatePackagesAttr() {
        return ignoredDuplicatePackagesAttr;
    }

}
