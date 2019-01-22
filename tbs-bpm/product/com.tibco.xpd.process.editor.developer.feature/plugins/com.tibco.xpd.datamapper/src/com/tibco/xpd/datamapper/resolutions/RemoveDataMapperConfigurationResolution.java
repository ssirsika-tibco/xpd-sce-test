package com.tibco.xpd.datamapper.resolutions;

import java.util.Properties;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.validation.rules.ValidationUtil;

/**
 * Resolution to remove unused data mapper array inflation configuration from an
 * activity. The mapping marker must contain an additional info entry with
 * CONFIG_ID as the key and the DataMapperArrayInflation element URI as the
 * value.
 * 
 * @author nwilson
 * @since 22 Apr 2015
 */
public class RemoveDataMapperConfigurationResolution extends
        AbstractWorkingCopyResolution {

    public static final String CONFIG_ID = "arrayInflationConfig"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#getResolutionCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, org.eclipse.core.resources.IMarker)
     * 
     * @param editingDomain
     * @param target
     * @param marker
     * @return
     * @throws ResolutionException
     */
    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        Command cmd = null;
        Properties props = ValidationUtil.getAdditionalInfo(marker);
        if (null != props) {
            String uri = props.getProperty(CONFIG_ID);
            if (uri != null) {
                WorkingCopy wc =
                        WorkingCopyUtil.getWorkingCopy(marker.getResource());
                if (wc != null) {
                    EObject eo = wc.resolveEObject(uri);
                    cmd = RemoveCommand.create(editingDomain, eo);
                }
            }
        }
        return cmd;
    }

}
