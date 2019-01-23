/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.processeditor.fragments;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.utils.Xpdl2ProcessorUtil;
import com.tibco.xpd.fragments.IFragment;
import com.tibco.xpd.fragments.IFragmentCategory;
import com.tibco.xpd.fragments.IFragmentElement;
import com.tibco.xpd.fragments.dnd.FragmentLocalSelectionTransfer;
import com.tibco.xpd.processeditor.fragments.PropertiesAction.LocalizeType;
import com.tibco.xpd.processeditor.fragments.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Artifact;
import com.tibco.xpd.xpdl2.ArtifactType;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Pool;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.resources.XpdlMigrate;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Contributes the process fragments to the {@link FragmentViewPart}. Migrates
 * fragments from the older versions of Studio if the user opens the latest
 * version using an older workspace.
 * 
 * @author rsomayaj
 * 
 */
public class ProcessFragmentContributor extends BaseXpdlTemplatesContributor {

    private static final String SAMPLE_DESCRIPTION =
            Messages.ProcessFragmentContributor_NewFragmentDesc_shortdesc;

    /**
     * 
     */
    private static final String DESCRIPTION_KEY = "Description"; //$NON-NLS-1$

    /**
     * 
     */
    private static final String NAME_KEY = "Name"; //$NON-NLS-1$

    /**
	 * 
	 */
    private static final String PROC_FRAGMENTS_CONTRIBUTOR_ID =
            "com.tibco.xpd.processeditor.fragments"; //$NON-NLS-1$

    /**
     * FRAGMENT_VERSION 2 equals Studio version 3.2
     * 
     * FRAGMENT_VERSION 6 equals Studio version 3.5.4
     * 
     * FRAGMENT_VERSION 7 equals Studio version 3.5.10
     * 
     * FRAGMENT_VERSION 8 is for changes in fragments for XPD-3892
     * 
     * FRAGMENT_VERSION 10 is for changes in fragments for ABPM-897
     * 
     * Factor in the migration format version so that when format version
     * changes, so does the fragment version - thus forcing fragments to be
     * re-loaded and therefore migrated.
     */
    private static final String FRAGMENT_VERSION =
            "10" + "." + XpdlMigrate.FORMAT_VERSION_ATT_VALUE; //$NON-NLS-1$

    private static final String SYSTEM_FRAGMENTS_LOCATION = "Bpmn Fragments"; //$NON-NLS-1$

    private static final String FRAGMENT_FILE_NAME = "fragment.xpdl"; //$NON-NLS-1$

    public ProcessFragmentContributor() {
    }

    @Override
    public String getFragmentVersion() {
        return FRAGMENT_VERSION;
    }

    @Override
    public ClipboardFragmentData getFromClipboard(IFragmentCategory cat) {
        Clipboard clp = new Clipboard(PlatformUI.getWorkbench().getDisplay());
        try {
            Object contents =
                    clp.getContents(FragmentLocalSelectionTransfer
                            .getTransfer());
            if (contents instanceof IStructuredSelection) {
                FragmentDataObject frgDO =
                        ProcessFragmentTransferHelper.INSTANCE
                                .getFragmentData((IStructuredSelection) contents);
                if (frgDO != null) {
                    return new ClipboardFragmentData(frgDO.getFragmentData(),
                            frgDO.getFragmentImageData());
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            clp.dispose();
        }
        return null;
    }

    @Override
    public String getLocalizedData(IFragment fragment, String NL) {

        Package fragmentPackage =
                Xpdl2ProcessorUtil.getFragmentPackage(fragment.getData());
        List<EObject> fragmentObjects =
                getElementsToExternalize(Xpdl2ProcessorUtil
                        .getFragmentProcess(fragmentPackage));
        for (EObject eobj : fragmentObjects) {
            String label =
                    PropertiesAction.getLabel(getPluginBundle(),
                            eobj,
                            fragment.getKey(),
                            LocalizeType.PROCESS_TEMPLATE);
            localizeName(eobj, label);
        }

        String resourceString =
                Xpdl2ProcessorUtil.getResourceString(fragmentPackage);
        return resourceString;
    }

    /**
     * @param fragmentProcess
     * @return
     */
    private List<EObject> getElementsToExternalize(Process fragmentProcess) {
        if (fragmentProcess != null) {
            List<EObject> elementsToExternalize = new ArrayList<EObject>();

            elementsToExternalize.addAll(Xpdl2ModelUtil
                    .getAllActivitiesInProc(fragmentProcess));
            elementsToExternalize.addAll(Xpdl2ModelUtil
                    .getAllArtifactsInProcess(fragmentProcess));
            elementsToExternalize.addAll(Xpdl2ModelUtil
                    .getAllMessageFlowsInProc(fragmentProcess));
            elementsToExternalize.addAll(Xpdl2ModelUtil
                    .getAllTransitionsInProc(fragmentProcess));
            elementsToExternalize.addAll(Xpdl2ModelUtil
                    .getAllAssociationsInProc(fragmentProcess));
            EList<Pool> pools = fragmentProcess.getPackage().getPools();
            for (Pool pool : pools) {
                elementsToExternalize.add(pool);
                elementsToExternalize.addAll(pool.getLanes());
            }
            return elementsToExternalize;
        }
        return Collections.emptyList();
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
        return PropertiesAction.getLabel(getPluginBundle(),
                DESCRIPTION_KEY,
                element.getKey(),
                LocalizeType.PROCESS_TEMPLATE);
    }

    @Override
    public ImageData getLocalizedImageData(IFragment fragment, String NL) {
        String localizedData = fragment.getLocalizedData();
        Package fragmentPackage =
                Xpdl2ProcessorUtil.getFragmentPackage(localizedData);
        Process fragmentProcess =
                Xpdl2ProcessorUtil.getFragmentProcess(fragmentPackage);

        List<EObject> fragmentElements =
                Xpdl2ProcessorUtil.getProcessElements(fragmentProcess);
        ImageData imageData =
                Xpdl2ProcessEditorPlugin
                        .getProcessDiagramImage(fragmentPackage,
                                fragmentProcess.getId(),
                                fragmentElements).getImageData();

        return imageData;
    }

    @Override
    public String getLocalizedName(IFragmentElement element) {
        return PropertiesAction.getLabel(getPluginBundle(),
                NAME_KEY,
                element.getKey(),
                LocalizeType.PROCESS_TEMPLATE);
    }

    /**
     * @see com.tibco.xpd.processeditor.fragments.BaseXpdlTemplatesContributor#getContributorId()
     * 
     * @return
     */
    @Override
    protected String getContributorId() {
        return PROC_FRAGMENTS_CONTRIBUTOR_ID;
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
        return SYSTEM_FRAGMENTS_LOCATION;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.fragments.FragmentsContributor#getIcon(com.tibco.xpd.fragments
     * .IFragmentElement)
     */
    @Override
    public Image getIcon(IFragmentElement fragment) {
        if (!(fragment instanceof IFragmentCategory)) {
            return Xpdl2ResourcesPlugin.getDefault().getImageRegistry()
                    .get(Xpdl2ResourcesConsts.ICON_PROCESS);
        }
        return null;
    }
}
