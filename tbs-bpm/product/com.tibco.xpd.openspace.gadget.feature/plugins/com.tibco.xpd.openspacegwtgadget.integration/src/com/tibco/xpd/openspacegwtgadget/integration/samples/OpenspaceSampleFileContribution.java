/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.openspacegwtgadget.integration.samples;

import org.eclipse.core.runtime.IConfigurationElement;

import com.tibco.xpd.openspacegwtgadget.integration.samples.SampleSourceFileContribution.SourceFileContribution;
import com.tibco.xpd.openspacegwtgadget.integration.samples.SampleSourceFileContribution.SourceJetEmitterContribution;
import com.tibco.xpd.openspacegwtgadget.integration.samples.SampleTargetFileContribution.TargetFileContribution;
import com.tibco.xpd.openspacegwtgadget.integration.samples.SampleTargetFileContribution.TargetPackageFileContribution;

/**
 * Class representing a SampleFile element in the
 * com.tibco.xpd.openspacegwtgadget.integration.OpenspaceSampleCreator extension
 * point.
 * <p>
 * Each sample file has exactly 1 source file definition (
 * {@link SampleSourceFileContribution}) and 1 target file definition (
 * {@link SampleTargetFileContribution})
 * <li>
 * A source file definition is either a static resource ( (wrapped in a
 * {@link SourceFileContribution}) or a JET emitter class (wrappe in a
 * {@link SourceJetEmitterContribution})
 * <p>
 * Each target file definition is either a vanilla fold/file definition (wrapped
 * in a {@link TargetFileContribution}) or a java source package (wrapped in a
 * {@link TargetPackageFileContribution}).
 * 
 * 
 * @author aallway
 * @since 21 Jan 2013
 */
public class OpenspaceSampleFileContribution {

    private SampleSourceFileContribution sourceFileContribution;

    private SampleTargetFileContribution targetFileContribution;

    public OpenspaceSampleFileContribution(
            IConfigurationElement configurationElement) throws Exception {

        IConfigurationElement[] children = configurationElement.getChildren();

        if (children != null && children.length == 2) {

            /* Must have one source file and one target file. */
            sourceFileContribution = createSourceFileContribution(children[0]);
            if (sourceFileContribution != null) {
                targetFileContribution =
                        createTargetFileContribution(children[1]);

            } else {
                sourceFileContribution =
                        createSourceFileContribution(children[1]);
                targetFileContribution =
                        createTargetFileContribution(children[0]);
            }

            if (sourceFileContribution != null
                    && targetFileContribution != null) {
                return;
            }
        }

        throw new Exception(
                String.format("SampleFile contribution by plugin'%1$s' is expected to have exactly 1*(SourceFile OR SourceJetEmitter element) AND exactly 1 (TargetFile or TargetPackageFile element)", //$NON-NLS-1$
                        configurationElement.getContributor().getName()));
    }

    /**
     * @return the sourceFileContribution
     */
    public SampleSourceFileContribution getSourceFileContribution() {
        return sourceFileContribution;
    }

    /**
     * @return the targetFileContribution
     */
    public SampleTargetFileContribution getTargetFileContribution() {
        return targetFileContribution;
    }

    /**
     * Creates sample source file contribution class IF the element indicates
     * that it is that type.
     * 
     * @param element
     * @return sample source file contribution class or <code>null</code> if
     *         element does not represent a source file contribution.
     * @throws Exception
     */
    private SampleSourceFileContribution createSourceFileContribution(
            IConfigurationElement configurationElement) throws Exception {
        if ("SourceFile".equals(configurationElement.getName())) { //$NON-NLS-1$
            return new SourceFileContribution(configurationElement);

        } else if ("SourceJetEmitter".equals(configurationElement.getName())) { //$NON-NLS-1$
            return new SourceJetEmitterContribution(configurationElement);
        }

        return null;
    }

    /**
     * Creates sample target file contribution class IF the element indicates
     * that it is that type.
     * 
     * @param element
     * @return sample target file contribution class or <code>null</code> if
     *         element does not represent a source file contribution.
     * @throws Exception
     */
    private SampleTargetFileContribution createTargetFileContribution(
            IConfigurationElement configurationElement) throws Exception {
        if ("TargetFile".equals(configurationElement.getName())) { //$NON-NLS-1$
            return new TargetFileContribution(configurationElement);

        } else if ("TargetPackageFile".equals(configurationElement.getName())) { //$NON-NLS-1$
            return new TargetPackageFileContribution(configurationElement);
        }

        return null;
    }

}
