package com.tibco.bds.designtime.generator.si;

import java.util.Collection;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import com.tibco.bds.designtime.generator.AbstractXpandGenerator;

public class SIGenerator extends AbstractXpandGenerator {

    private static final String BUNDLE_NAME =
            "BDS Scriptable Interfaces (SI) bundle";

    private static final String JAVA_SOURCE_TEMPLATE_PATH =
            "com::tibco::bds::designtime::generator::si::oaw::SIJava::Root";

    private static final String OTHER_ARTIFACTS_TEMPLATE_PATH =
            "com::tibco::bds::designtime::generator::si::oaw::SIOtherArtifacts::Root";

    @Override
    protected String getJavaSourceTemplatePath() {
        return JAVA_SOURCE_TEMPLATE_PATH;
    }

    @Override
    protected String getOtherArtifactsTemplatePath() {
        return OTHER_ARTIFACTS_TEMPLATE_PATH;
    }

    @Override
    protected String getBundleName() {
        return BUNDLE_NAME;
    }

    @Override
    protected String getRootPackageName(String modelName) {
        return String.format("%s.si", modelName);
    }

    @Override
    public boolean supports(Collection<IFile> bomResources,
            IProgressMonitor monitor) throws CoreException {
        return super.supports(bomResources, monitor);
    }
}
