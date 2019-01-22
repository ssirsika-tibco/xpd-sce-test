package com.tibco.xpd.bom.modeler.diagram.edit.commands.custom;

import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.gmf.runtime.emf.type.core.commands.MoveElementsCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.MoveRequest;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Element;

/**
 * BOM specific Move Command
 * 
 * Allows additional objects to be "Auto-Moved" based on another object being
 * moved between packages
 * 
 */
public class BOMMoveElementsCommand extends MoveElementsCommand {

    @SuppressWarnings("unchecked")
    public BOMMoveElementsCommand(MoveRequest request) {
        super(request);

        // Add additional check to see if there are extra associations
        // required in the move with the Class
        Map<?, ?> elemToMove = getElementsToMove();
        for (Object key : elemToMove.keySet()) {
            // Only interested in classes that are being moved between packages
            if (key instanceof Class) {
                Class keyClass = (Class) key;
                // Get the package that "owns the class"
                Element classOwner = keyClass.getOwner();

                // Check to see if this class "owns" and associations, these
                // will be recorded in the same package (or sub-package) as the
                // class itself. This means, if the Class moves then so should
                // the association. (If it is not moved then if the package it
                // was moved FROM is deleted, so will the association)
                EList<Association> associations = keyClass.getAssociations();
                for (Association association : associations) {
                    // Get the "owner or the association"
                    Element associationOwner = association.getOwner();

                    // Check to see if the owners for both class and association
                    // are the same to start with, if they are, then we need to
                    // ensure that we relocate both together
                    if ((classOwner != null) && classOwner == associationOwner) {
                        // Check to see if the association is already in the map
                        if (!elemToMove.containsKey(association)) {
                            // Add this association to the map of elements to
                            // move, we can put the value of the Map to null at
                            // the moment as we are in the constructor, and the
                            // actual values parts will all be processed later
                            // and populated if needed
                            getElementsToMove().put(association, null);
                        }
                    }
                }
            }
        }
    }
}
