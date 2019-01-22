package com.tibco.bx.composite.core.extensions;

import com.tibco.amf.sca.model.componenttype.ComponentType;
import com.tibco.amf.sca.model.extensionpoints.Implementation;

/**
 * This interface should be implemented to contribute to the component type (e.g. the SCA properties) for the extension activities.
 * To use this, you should add an extension to <code>com.tibco.bx.composite.core.componentTypeContributor</code>,
 * where you specify the corresponding class that implements this interface.
 */
public interface IComponentTypeContributor {

	/**
	 * This method should be implemented to analyze the content of an SCA <code>Implementation</code> to
	 * determine whether certain extension activity is present, and if so, any properties need to be added
	 * to the <code>serviceType</code> object.
	 * @param serviceType the serviceType instance to which to add new contributions, e.g. properties
	 * @param implementation the SCA <code>Implementation</code> instance to be analyzed
	 */
	public void contributeToComponentType(ComponentType serviceType, Implementation implementation);

}
