package com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups;

import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.processeditorconfiguration.ProcessEditorConfigurationUtil;
import com.tibco.xpd.analyst.resources.xpdl2.processeditorconfiguration.ProcessEditorElementType;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Process (logical) group of the Package in the Project Explorer tree
 * 
 * @author njpatel
 * 
 */
public class ProcessGroup extends AbstractAssetGroup {

    private static final String TITLE = Messages.ProcessGroup_TITLE;

    /**
     * Default constructor
     * 
     * @param parent
     */
    public ProcessGroup(EObject parent) {
        super(parent);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.BpmArtefactGroup
     * #getFeature()
     */
    @Override
    public EStructuralFeature getFeature() {
        return Xpdl2Package.eINSTANCE.getPackage_Processes();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.BpmArtefactGroup
     * #getImage()
     */
    @Override
    public Image getImage() {
        return Xpdl2ResourcesPlugin.getDefault().getImageRegistry()
                .get(Xpdl2ResourcesConsts.ICON_PROCESS);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.BpmArtefactGroup
     * #getText()
     */
    @Override
    public String getText() {
        return TITLE;
    }

    @Override
    public EClass getReferenceType() {
        return Xpdl2Package.eINSTANCE.getProcess();
    }

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.AbstractAssetGroup#isAssetGroupElementTypeExcluded()
     * 
     * @return
     */
    @Override
    public boolean isAssetGroupElementTypeExcluded() {
        /*
         * Process group contains 2 element types (business process and pageflow
         * process, so need to override this method to account for that.
         */

        /*
         * Get the nearest ancestor process/interface/package for checking
         * exclusion
         */
        EObject procOrIfcOrPkg = Xpdl2ModelUtil.getProcess(parent);

        if (procOrIfcOrPkg == null) {
            procOrIfcOrPkg =
                    Xpdl2ModelUtil.getAncestor(parent, ProcessInterface.class);

            if (procOrIfcOrPkg == null) {
                procOrIfcOrPkg =
                        Xpdl2ModelUtil.getAncestor(parent, Package.class);
            }
        }

        if (procOrIfcOrPkg != null) {
            /*
             * Check extension point contributions for exclusion of this
             * particular element type.
             */
            Set<ProcessEditorElementType> excludedElementTypes =
                    ProcessEditorConfigurationUtil
                            .getExcludedElementTypes(procOrIfcOrPkg);

            if (excludedElementTypes
                    .contains(ProcessEditorElementType.business_process)
                    && excludedElementTypes
                            .contains(ProcessEditorElementType.pageflow_process)
                    && excludedElementTypes
                            .contains(ProcessEditorElementType.business_service)
                    && excludedElementTypes
                            .contains(ProcessEditorElementType.case_service)
                    && excludedElementTypes
                            .contains(ProcessEditorElementType.service_process)) {
                return true;
            }
        }

        return false;
    }

}
