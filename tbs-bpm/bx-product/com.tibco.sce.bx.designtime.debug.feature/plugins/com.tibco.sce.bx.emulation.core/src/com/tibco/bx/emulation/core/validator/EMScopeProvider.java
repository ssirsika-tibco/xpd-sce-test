package com.tibco.bx.emulation.core.validator;

import java.util.Collection;
import java.util.HashSet;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.bx.emulation.model.EmulationData;
import com.tibco.bx.emulation.model.Input;
import com.tibco.bx.emulation.model.ProcessNode;
import com.tibco.xpd.validation.destinations.Destination;
import com.tibco.xpd.validation.provider.IScopeProvider;
import com.tibco.xpd.validation.provider.IValidationItem;

public class EMScopeProvider implements IScopeProvider{

	
	public EMScopeProvider() {
	}

	public Collection<EObject> getAffectedObjects(Destination validationDestinationEnd,
			String providerId, IValidationItem validationitem) {
		 Collection<EObject> collection = validationitem.getObjects();
		 HashSet<EObject> result = new HashSet<EObject>();
		 if(collection.size() == 1){
			 EObject object = collection.iterator().next();
			 if( object instanceof EmulationData){
				 // adding ProcessNodes or saving the emulation file or dependency changed
				 EList<ProcessNode> list = ((EmulationData)object).getProcessNodes();
				 for(ProcessNode node: list){
					 result.add(node);
					 result.addAll(node.getTestpoints());
					 result.addAll(node.getAssertions());
					 Input input = node.getInput();
					 if(input != null){
						 result.add(input);
					 }
				 }
				 
			 }else if(object instanceof ProcessNode && object.eContainer() != null){
				 // get Testpoints and Assertions and input from a ProcessNode
				 result.addAll(((ProcessNode)object).getTestpoints());
				 result.addAll(((ProcessNode)object).getAssertions());
				 Input input = ((ProcessNode)object).getInput();
				 if(input != null){
					 result.add(input);
				 }
			 }
		 }
	    	 return result;
	}

}