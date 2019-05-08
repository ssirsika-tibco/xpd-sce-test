/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.packagetemplates;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.swt.graphics.ImageData;

import com.tibco.xpd.analyst.resources.xpdl2.utils.Xpdl2ProcessorUtil;
import com.tibco.xpd.fragments.IFragment;
import com.tibco.xpd.fragments.IFragmentCategory;
import com.tibco.xpd.fragments.IFragmentElement;
import com.tibco.xpd.processeditor.fragments.BaseXpdlTemplatesContributor;
import com.tibco.xpd.processeditor.fragments.PropertiesAction;
import com.tibco.xpd.processeditor.fragments.PropertiesAction.LocalizeType;
import com.tibco.xpd.processeditor.fragments.internal.Messages;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Artifact;
import com.tibco.xpd.xpdl2.ArtifactType;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.resources.XpdlMigrate;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Contributes the package fragments that get displayed in the Package and
 * Project creation wizard.
 * 
 * @author rsomayaj
 * 
 */
public class PackageTemplatesContributor extends BaseXpdlTemplatesContributor {

    private static final String PACKAGE_TEMPLATES_LOCATION =
            "Package Templates";

    private static final String PACKAGE_TEMPLATES_CONTRIBUTOR_ID =
            "com.tibco.xpd.pkgtemplates";

    private static final String FRAGMENT_FILE_NAME = "package_template.xpdl";

    private static final String SAMPLE_DESCRIPTION =
            Messages.ProcessFragmentContributor_NewFragmentDesc_shortdesc;

    /**
     * FRAGMENT_VERSION 4 equals Studio version 3.4 FRAGMENT_VERSION 5 equals
     * Studio version 3.5
     * 
     * Factor in the migration format version so that when format version
     * changes, so does the fragment version - thus forcing fragments to be
     * re-loaded and therefore migrated.
     */
    private static final String FRAGMENT_VERSION =
            "6" + "." + XpdlMigrate.FORMAT_VERSION_ATT_VALUE; //$NON-NLS-1$

    private static final String SYSTEM_FRAGMENTS_LOCATION = "Package Templates";

    public PackageTemplatesContributor() {
    }

    @Override
    public void copyToClipboard(IFragment fragment) {
        // do nothing
    }

    @Override
    public String getFragmentVersion() {
        return FRAGMENT_VERSION;
    }

    @Override
    public ClipboardFragmentData getFromClipboard(IFragmentCategory cat) {
        // No drag drop or copy-paste for package template
        return null;
    }

    @Override
    public String getLocalizedData(IFragment fragment, String NL) {
        Package fragmentPackage =
                Xpdl2ProcessorUtil.getFragmentPackage(fragment.getData());

        localizeName(fragmentPackage,
                PropertiesAction.getLabel(getPluginBundle(),
                        fragmentPackage,
                        fragment.getKey(),
                        LocalizeType.PACKAGE_TEMPLATE));
        TreeIterator<Object> allContents =
                EcoreUtil.getAllContents(fragmentPackage, false);
        while (allContents.hasNext()) {
            Object object = allContents.next();
            if (object instanceof NamedElement) {
                NamedElement namedElement = (NamedElement) object;

                localizeName(namedElement,
                        PropertiesAction.getLabel(getPluginBundle(),
                                namedElement,
                                fragment.getKey(),
                                LocalizeType.PACKAGE_TEMPLATE));
            }

        }

        String resourceString =
                Xpdl2ProcessorUtil.getResourceString(fragmentPackage);
        return resourceString;
    }

    /**
     * @param eobj
     * @param label
     */
    private void localizeName(EObject eobj, String label) {
        if (eobj instanceof NamedElement) {
            if (eobj instanceof Artifact) {
                Artifact artifact = (Artifact) eobj;
                if (ArtifactType.ANNOTATION_LITERAL.equals(artifact
                        .getArtifactType())) {
                    artifact.setTextAnnotation(label);
                    return;
                }

            }
            // ((NamedElement) eobj).setName(label);
            Xpdl2ModelUtil
                    .setOtherAttribute((NamedElement) eobj,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName(),
                            label);
        }
    }

    @Override
    public String getLocalizedDescription(IFragmentElement element) {
        return element.getDescription();
    }

    @Override
    public ImageData getLocalizedImageData(IFragment fragment, String NL) {
        return null;
    }

    @Override
    public String getLocalizedName(IFragmentElement element) {
        return element.getName();
    }

    /**
     * @see com.tibco.xpd.processeditor.fragments.BaseXpdlTemplatesContributor#getContributorId()
     * 
     * @return
     */
    @Override
    protected String getContributorId() {
        return PACKAGE_TEMPLATES_CONTRIBUTOR_ID;
    }

    /**
     * @see com.tibco.xpd.processeditor.fragments.BaseXpdlTemplatesContributor#getFragmentFileName()
     * 
     * @return
     */
    @Override
    protected String getFragmentFileName() {
        return FRAGMENT_FILE_NAME;
    }

    /**
     * @see com.tibco.xpd.processeditor.fragments.BaseXpdlTemplatesContributor#getSampleDescription()
     * 
     * @return
     */
    @Override
    protected String getSampleDescription() {
        return SAMPLE_DESCRIPTION;
    }

    /**
     * @see com.tibco.xpd.processeditor.fragments.BaseXpdlTemplatesContributor#getSystemFragmentsLocation()
     * 
     * @return
     */
    @Override
    protected String getSystemFragmentsLocation() {
        return SYSTEM_FRAGMENTS_LOCATION;
    }

    /**
     * @see com.tibco.xpd.processeditor.fragments.BaseXpdlTemplatesContributor#getTemplatesLocation()
     * 
     * @return
     */
    @Override
    protected String getTemplatesLocation() {
        return PACKAGE_TEMPLATES_LOCATION;
    }

}
