package com.tibco.bx.composite.core.extensions;

import com.tibco.amf.sca.model.componenttype.Requirements;
import com.tibco.amf.sca.model.extensionpoints.Implementation;

/**
 * This interface should be implemented to contribute the requirements for the extension activities.
 * To use this, you should add an extension to <code>com.tibco.bx.composite.core.requirementsResolver</code>,
 * where you specify the corresponding class that implements this interface.
 */
public interface IRequirementsResolver {

	/**
	 * This method should be implemented to analyze the content of an SCA <code>Implementation</code> to
	 * determine whether certain extension activity is present, and if so, which new requirements need to be added
	 * to the <code>requirements</code> object.
	 * @param requirements the requirements instance to which to add new contributions
	 * @param implementation the SCA <code>Implementation</code> instance to be analyzed
	 */
	public void addImplementationRequirements(Requirements requirements, Implementation implementation);
	
}
