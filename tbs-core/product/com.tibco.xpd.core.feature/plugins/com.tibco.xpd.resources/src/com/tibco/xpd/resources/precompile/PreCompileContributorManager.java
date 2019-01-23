/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.resources.precompile;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.expressions.EvaluationContext;
import org.eclipse.core.expressions.EvaluationResult;
import org.eclipse.core.expressions.Expression;
import org.eclipse.core.expressions.ExpressionConverter;
import org.eclipse.core.expressions.ExpressionTagNames;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;

/**
 * Manager for the <code>com.tibco.xpd.n2.resources.precompileContributor</code>
 * extension point. This can be used to get all contributions to this extension
 * point. Use {@link getInstance()} to create an instance of this class.
 * 
 * @author bharge
 * @since 28 Oct 2014
 */
public class PreCompileContributorManager {

    private List<PreCompileContributor> listOfPreCompileContributors;

    /**
     * 
     */
    private static final String CONTRIBUTOR_ID = "contributorId"; //$NON-NLS-1$

    /**
     * Pre-compile folder name that returns the value ".precompiled"
     */
    public final String PRECOMPILE_OUTPUTFOLDER_NAME = ".precompiled";

    /**
     * Properties file name (holding source resource workspace relative path and
     * check sum) that returns the value "SourceFileChecksum.checksum"
     */
    public final String SOURCE_PRECOMPILE_RESOURCES_PROPERTIES =
            "SourceFileChecksum.checksum";

    private static final PreCompileContributorManager INSTANCE =
            new PreCompileContributorManager();

    Logger logger = XpdResourcesPlugin.getDefault().getLogger();

    /**
     * Returns the singleton instance of this class
     * 
     * @return <code>EnablePreCompileContributorHelper</code> singleton instance
     */
    public static PreCompileContributorManager getInstance() {

        return INSTANCE;
    }

    public PreCompileContributorManager() {

        /* Initialises the contribution list */
        try {

            listOfPreCompileContributors = getListOfPrecompileContributors();
        } catch (CoreException e) {

            logger.error(e);
        }
    }

    private final String EXTENSION_POINT_NAME = "precompileContributor"; //$NON-NLS-1$

    /**
     * Returns the list of contributors for this contribution.
     * 
     * @return List of <code>EnablePreCompileContributor</code>
     * @throws CoreException
     */
    public List<AbstractPreCompileContributor> getPreCompileContributors()
            throws CoreException {

        List<AbstractPreCompileContributor> contributionList =
                new ArrayList<>();
        for (PreCompileContributor enableContributor : listOfPreCompileContributors) {

            AbstractPreCompileContributor precompileContributor =
                    enableContributor.getPrecompileContributor();
            contributionList.add(precompileContributor);
        }

        return contributionList;
    }

    /**
     * @return
     * @throws CoreException
     */
    private List<PreCompileContributor> getListOfPrecompileContributors()
            throws CoreException {

        List<PreCompileContributor> precompileContributorList =
                new ArrayList<>();

        IExtensionRegistry registry = Platform.getExtensionRegistry();
        IConfigurationElement[] configurationElements =
                registry.getConfigurationElementsFor(XpdResourcesPlugin.ID_PLUGIN,
                        EXTENSION_POINT_NAME);
        /* Read the contributions */
        for (IConfigurationElement configElement : configurationElements) {

            /*
             * Create an instance of the class that holds the precompile
             * contributors information
             */
            PreCompileContributor enableContributor =
                    new PreCompileContributor();

            /* Set the contributor class */
            Object extension = configElement.createExecutableExtension("class"); //$NON-NLS-1$
            AbstractPreCompileContributor contribution =
                    (AbstractPreCompileContributor) extension;
            /* Set the contributor id */
            String contributorId = configElement.getAttribute(CONTRIBUTOR_ID);
            contribution.setContributorId(contributorId);
            enableContributor.setPrecompileContributor(contribution);

            /* Set the source artifact filters */
            Expression sourceArtifactFilterExpression =
                    getSourceArtifactFilterExpression(configElement);
            if (null != sourceArtifactFilterExpression) {

                enableContributor.setFilter(sourceArtifactFilterExpression);
            }

            /* Add the contributor to the list of contributors */
            precompileContributorList.add(enableContributor);
        }
        return precompileContributorList;
    }

    /**
     * For the given configuration element returns the source artifacts
     * enablement expression
     * 
     * @param configElement
     * @return
     * @throws CoreException
     */
    private Expression getSourceArtifactFilterExpression(
            IConfigurationElement configElement) throws CoreException {

        IConfigurationElement[] children =
                configElement.getChildren("sourceArtifactFilter");
        for (IConfigurationElement sourceArtifactElement : children) {

            IConfigurationElement[] artifactEnablements =
                    sourceArtifactElement
                            .getChildren(ExpressionTagNames.ENABLEMENT);

            if (artifactEnablements.length > 0) {

                Expression enablementExpression =
                        ExpressionConverter.getDefault()
                                .perform(artifactEnablements[0]);
                return enablementExpression;
            }
        }

        return null;
    }

    /**
     * For a given resource returns the enablement expression boolean value that
     * tests if the resource is a file that ends with the extension being tested
     * against in the expression contributed and the resource exists in the
     * special folder
     * 
     * @param resource
     * @return <code>true</code> if the resource matches the given test
     *         expression <code>false</code> otherwise
     */
    public boolean isPrecompiledSourceArtefact(IResource resource) {

        EvaluationContext evaluationContext = createEvaluationContext(resource);

        for (PreCompileContributor enableContributor : listOfPreCompileContributors) {

            try {

                Expression expression = enableContributor.getFilter();
                if (null != expression) {

                    EvaluationResult result =
                            expression.evaluate(evaluationContext);
                    if (EvaluationResult.TRUE.equals(result)) {

                        return true;
                    }
                }
            } catch (CoreException e) {

                logger.error(e,
                        "Error evaluating source artifact filters expression for pre-compile contribution"); //$NON-NLS-1$
            }
        }

        return false;
    }

    /**
     * 
     * @param contextObject
     *            - can be a project or source artefact file / type
     * @return Evaluation context for the project / source artefact resource
     *         type
     */
    private static EvaluationContext createEvaluationContext(
            IResource contextObject) {

        EvaluationContext evaluationContext =
                new EvaluationContext(null, contextObject);

        return evaluationContext;
    }

    /**
     * Class that holds information (id, class and source artifact filter
     * expression - basically the configuration elements defined in the schema)
     * about each enable precompile contributor
     * 
     * @author bharge
     * @since 29 May 2015
     */
    private static class PreCompileContributor {

        private AbstractPreCompileContributor precompileContributor;

        private Expression filter;

        /**
         * @return the precompileContributor
         */
        public AbstractPreCompileContributor getPrecompileContributor() {

            return precompileContributor;
        }

        /**
         * @param precompileContributor
         *            the precompileContributor to set
         */
        public void setPrecompileContributor(
                AbstractPreCompileContributor precompileContributor) {

            this.precompileContributor = precompileContributor;
        }

        /**
         * @return the filters
         */
        public Expression getFilter() {

            return filter;
        }

        /**
         * @param filters
         *            the filters to set
         */
        private void setFilter(Expression filter) {

            this.filter = filter;
        }
    }

}
