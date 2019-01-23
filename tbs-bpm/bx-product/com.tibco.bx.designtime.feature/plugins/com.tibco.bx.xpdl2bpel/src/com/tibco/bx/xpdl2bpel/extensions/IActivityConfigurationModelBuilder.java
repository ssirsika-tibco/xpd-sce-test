package com.tibco.bx.xpdl2bpel.extensions;

import org.eclipse.emf.ecore.EObject;

import com.tibco.bx.xpdl2bpel.converter.ConversionException;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Participant;

import java.util.Map;

/**
 * This interface defines an Eclipse extension point invoked at design time when generating a .bpel file
 * The activity developer should implement this interface when the default EMF model used to configure the
 * activity at runtime is not suitable.
 */
public interface IActivityConfigurationModelBuilder {

	static final String EXT_ACTIVITY_CONFIG_MODEL_BUILDER = "activityConfigurationModelBuilder"; //$NON-NLS-1$
	
    /**
     * This method allows the activity developer to convert to a more suitable configuration model.
     * This model will be presented to the activity in its eval, postEval and cancel methods.
     * Full access to the XPDL process is available via the xpdlActivity parameter. The XPDL models may be inspected, but
     * SHOULD NOT be modified.
     * @param xpdlActivity design time model of the activity
     * @param participantMap maps performer XPDLid to Participant object
     * @return EMF model used at runtime to contain the activities configuration
     */
    EObject transformConfigModel(Activity xpdlActivity, Map<String, Participant> participantMap) throws ConversionException;

}
