/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.openspacegwtgadget.integration.samples;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

/**
 * Abstract base class and its implementations representing the SourceFile and
 * SourceJetEmitter elements in a contribution to the
 * com.tibco.xpd.openspacegwtgadget.integration.OpenspaceSampleCreator extension
 * point.
 * 
 * 
 * @author aallway
 * @since 21 Jan 2013
 */
abstract class SampleSourceFileContribution {
    protected final String contributingPluginId;

    /**
     * 
     * @param contributionElement
     * @throws Exception
     *             Error loading detail from plugin contribution.
     */
    SampleSourceFileContribution(IConfigurationElement contributionElement)
            throws Exception {
        contributingPluginId = contributionElement.getContributor().getName();
    }

    /**
     * @param variableProperties
     *            There are currently two variable properties that can be used
     *            in source/target paths by placing them with { } delimiters.
     *            The currently available variable properties are are
     * 
     *            <li>
     *            {@link #PROPERTY_SAMPLE_NAME} ( {@value #PROPERTY_SAMPLE_NAME}
     *            ): The gadget tokenised name (valid for java names etc set by
     *            user during creation (initially set by ext point contribution.
     *            </li>
     * 
     *            <li>
     *            {@link #PROPERTY_SAMPLE_LABEL} (
     *            {@value #PROPERTY_SAMPLE_LABEL} ): The gadget label set by
     *            user during creation (initially set by ext point contribution.
     *            </li>
     * 
     *            <li>
     *            {@link #PROPERTY_TARGET_PACKAGE_NAME} (
     *            {@value #PROPERTY_TARGET_PACKAGE_NAME} ): The GWT gadget
     *            source package selected as a destination for new sample</li>
     * 
     * @return InputStream from which source file content is read.
     * 
     * @throws Exception
     */
    abstract InputStream createInputStream(
            Map<String, String> variableProperties) throws Exception;

    /**
     * @return Descriptive name of source.
     */
    abstract String getSourceName();

    /**
     * Class representing the SourceFile element in a contribution to the
     * com.tibco.xpd.openspacegwtgadget.integration.OpenspaceSampleCreator
     * extension point.
     * <p>
     * The SourceFile element represents a static resource stored in th esource
     * plugin.
     * 
     * 
     * @author aallway
     * @since 21 Jan 2013
     */
    static class SourceFileContribution extends SampleSourceFileContribution {

        private String sourceLocation;

        /**
         * @param contributionElement
         * @throws Exception
         *             Error loading detail from plugin contribution.
         */
        SourceFileContribution(IConfigurationElement contributionElement)
                throws Exception {
            super(contributionElement);

            sourceLocation = contributionElement.getAttribute("sourceLocation"); //$NON-NLS-1$

            if (sourceLocation == null || sourceLocation.length() == 0) {
                throw new Exception(
                        String.format("TargetPackageFile sourceLocation attribute unspecified in contribution by plugin: %1$s", //$NON-NLS-1$
                                contributingPluginId));
            }
        }

        /**
         * @see com.tibco.xpd.openspacegwtgadget.integration.samples.SampleSourceFileContribution#createInputStream()
         * 
         * @return
         * @throws Exception
         *             Error accessing source file.
         */
        @Override
        InputStream createInputStream(Map<String, String> variableProperties)
                throws Exception {
            Bundle bundle = Platform.getBundle(contributingPluginId);

            return FileLocator.openStream(bundle,
                    new Path(sourceLocation),
                    false);
        }

        /**
         * @see com.tibco.xpd.openspacegwtgadget.integration.samples.SampleSourceFileContribution#getSourceName()
         * 
         * @return
         */
        @Override
        String getSourceName() {
            return sourceLocation;
        }

    }

    /**
     * Class representing the SoureceJetEmitter element in a contribution to the
     * com.tibco.xpd.openspacegwtgadget.integration.OpenspaceSampleCreator
     * extension point.
     * <p>
     * A SourceJetEmitter element represents a a dynamic source file created by
     * a JET template emitter class.
     * 
     * @author aallway
     * @since 21 Jan 2013
     */
    static class SourceJetEmitterContribution extends
            SampleSourceFileContribution {

        private Method generateMethod;

        private Object emitterClass;

        /**
         * 
         * 
         * @param contributionElement
         * @throws Exception
         *             Error loading detail from plugin contribution.
         */
        SourceJetEmitterContribution(IConfigurationElement contributionElement)
                throws Exception {
            super(contributionElement);

            try {
                emitterClass =
                        contributionElement
                                .createExecutableExtension("jetEmitterClass"); //$NON-NLS-1$

                try {
                    generateMethod =
                            emitterClass.getClass()
                                    .getMethod("generate", Object.class); //$NON-NLS-1$

                    if (generateMethod.getReturnType() != String.class) {
                        throw new Exception(
                                String.format("Error loading method \"public String generate(Object argument)\" from SourceJetEmitter class (%1$s) contributed by plugin: %2$s", //$NON-NLS-1$
                                        contributionElement
                                                .getAttribute("jetEmitterClass"), //$NON-NLS-1$
                                        contributingPluginId));
                    }

                } catch (Exception e) {
                    throw new Exception(
                            String.format("Error loading method \"public String generate(Object argument)\" from SourceJetEmitter class (%1$s) contributed by plugin: %2$s", //$NON-NLS-1$
                                    contributionElement
                                            .getAttribute("jetEmitterClass"), //$NON-NLS-1$
                                    contributingPluginId), e);
                }

            } catch (CoreException e) {
                throw new Exception(
                        String.format("Error loading SourceJetEmitter class (%1$s) in contribution by plugin: %2$s", //$NON-NLS-1$
                                contributionElement
                                        .getAttribute("jetEmitterClass"), //$NON-NLS-1$
                                contributingPluginId), e);
            }
        }

        /**
         * @see com.tibco.xpd.openspacegwtgadget.integration.samples.SampleSourceFileContribution#createInputStream()
         * 
         * @return
         * @throws Exception
         */
        @Override
        InputStream createInputStream(Map<String, String> variableProperties)
                throws Exception {
            try {
                String emittedString =
                        (String) generateMethod.invoke(emitterClass,
                                variableProperties);

                return new BufferedInputStream(new ByteArrayInputStream(
                        emittedString.getBytes()));

            } catch (Exception e) {
                throw new Exception(
                        String.format("Error invoking method \"public String generate(Object argument)\" from SourceJetEmitter class (%1$s) contributed by plugin: %2$s", //$NON-NLS-1$
                                emitterClass.getClass().getName(),
                                contributingPluginId), e);
            }
        }

        /**
         * @see com.tibco.xpd.openspacegwtgadget.integration.samples.SampleSourceFileContribution#getSourceName()
         * 
         * @return
         */
        @Override
        String getSourceName() {
            return emitterClass.getClass().getName();
        }
    }

}
