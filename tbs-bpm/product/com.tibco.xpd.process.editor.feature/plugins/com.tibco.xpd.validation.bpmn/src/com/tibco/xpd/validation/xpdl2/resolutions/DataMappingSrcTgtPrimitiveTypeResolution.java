/**
 * 
 */
package com.tibco.xpd.validation.xpdl2.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.preCommit.MappingsProcessDataPrimitiveTypeCommand;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;

/**
 * 
 * 
 * @author rsomayaj
 * @since 3.3 (18 Nov 2010)
 */
public class DataMappingSrcTgtPrimitiveTypeResolution extends
        AbstractWorkingCopyResolution {

    /**
     * 
     */
    public DataMappingSrcTgtPrimitiveTypeResolution() {

    }

    private static final Logger LOG =
            XpdResourcesPlugin.getDefault().getLogger();

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#
     * getResolutionCommand(org.eclipse.emf.edit.domain.EditingDomain,
     * org.eclipse.emf.ecore.EObject, org.eclipse.core.resources.IMarker)
     */
    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        if (target instanceof com.tibco.xpd.xpdl2.Process) {
            com.tibco.xpd.xpdl2.Process process =
                    (com.tibco.xpd.xpdl2.Process) target;

            return MappingsProcessDataPrimitiveTypeCommand
                    .create(editingDomain, process);

        }
        return null;
    }

}
