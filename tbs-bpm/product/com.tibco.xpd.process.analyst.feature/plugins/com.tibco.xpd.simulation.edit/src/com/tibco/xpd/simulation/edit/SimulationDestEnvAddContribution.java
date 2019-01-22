/**
 * 
 */
package com.tibco.xpd.simulation.edit;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.destinations.DestinationsActivator;
import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.simulation.SimulationConstants;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.commands.AddCommandWrapper;

/**
 * Contribution for destination enviroments - it will add simulation data to all
 * activities when simulation data destination enviroment will be added
 * 
 * @author wzurek
 */
public class SimulationDestEnvAddContribution implements AddCommandWrapper {

    public Command createAddCommand(Command oryginalCommand,
            EditingDomain domain, EObject owner, EStructuralFeature feature,
            Collection collection, int index) {
        if (owner instanceof Process
                && feature == Xpdl2Package.eINSTANCE
                        .getExtendedAttributesContainer_ExtendedAttributes()) {

            boolean hasSimDestEnv = false;

            // Check if one of dest env is simulation
            for (Iterator iter = collection.iterator(); iter.hasNext();) {
                ExtendedAttribute extAttr = (ExtendedAttribute) iter.next();
                if (DestinationUtil.DESTINATION_ATTRIBUTE.equals(extAttr
                        .getName())) {
                    
                    String destinationId = extAttr.getValue();

                    if (destinationId != null) {
                        String gdId =
                                DestinationsActivator
                                        .getDefault()
                                        .getDestinationPreferences()
                                        .getGlobalDestinationId(destinationId);
                        if (SimulationConstants.SIMULATION_GLOBAL_DEST
                                .equals(gdId)) {
                            hasSimDestEnv = true;
                            break;
                        }
                    }
                }
            }

            if (hasSimDestEnv) {
                oryginalCommand =
                        new AddSimulationDataCommand(oryginalCommand, owner,
                                domain);
            }
        }
        return oryginalCommand;
    }
}
