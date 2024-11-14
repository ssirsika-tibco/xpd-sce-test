package com.tibco.xpd.validation.bpmn.developer.resolution;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.implementer.resources.xpdl2.properties.RestServiceTaskAdapter;
import com.tibco.xpd.resources.ui.picker.PickerItem;
import com.tibco.xpd.rsd.ui.components.RestMethodPicker;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Resolution for selecting a REST service operation.
 * 
 * @author nwilson
 * @since 25 Jun 2015
 */
public class SelectRestOperationResolution extends
        AbstractWorkingCopyResolution {

    /**
     * @see com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#getResolutionCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, org.eclipse.core.resources.IMarker)
     * 
     * @param editingDomain
     *            the editing domain.
     * @param target
     *            The target object.
     * @param marker
     *            The problem marker.
     * @return The command to resolve the problem.
     * @throws ResolutionException
     *             If an exception occurred creating the resolution.
     */
    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        Command cmd = null;
        if (target instanceof Activity) {
            Activity activity = (Activity) target;
            RestMethodPicker picker = new RestMethodPicker();
			// Nikita-8267 The picker dialog will now return a PickerItem instead of the specific types
			PickerItem pickerItem = picker.pickRestMethod(null);
			if (pickerItem != null)
			{
                RestServiceTaskAdapter rsta = new RestServiceTaskAdapter();
				cmd = rsta.getSetMethodCommand(editingDomain, activity, pickerItem);
            }
        }
        return cmd;
    }
}
