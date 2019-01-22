package com.tibco.bds.designtime.generator.da;

import com.tibco.bds.designtime.generator.AbstractXpandGenerator;

/**
 * BDS Database Adaptor (DA) bundle generator. An Xpand-based generator.
 * 
 * @author smorgan
 * 
 */
public class DAGenerator extends AbstractXpandGenerator {

    private static final String BUNDLE_NAME =
            "BDS Database Adaptor (DA) bundle";

    private static final String JAVA_SOURCE_TEMPLATE_PATH =
            "com::tibco::bds::designtime::generator::da::oaw::DAJava::Root";

    private static final String OTHER_ARTIFACTS_TEMPLATE_PATH =
            "com::tibco::bds::designtime::generator::da::oaw::DAOtherArtifacts::Root";

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
        return String.format("%s.da", modelName);
    }
}
