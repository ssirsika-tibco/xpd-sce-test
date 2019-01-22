package com.tibco.bx.debug.bpm.core.models;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EPackage;

import com.tibco.bx.debug.api.IDebugConstants;
import com.tibco.bx.debug.common.model.VariableType;
import com.tibco.bx.debug.core.DebugCoreActivator;
import com.tibco.bx.debug.core.Messages;
import com.tibco.bx.debug.core.models.variable.IVariableHandler;
import com.tibco.bx.emulation.bpm.core.common.BomProjectManager;

public class BxEnumType extends VariableType{

	 private final static String NULL_ATTR = IDebugConstants.NULL_ATTR;
	 
	 private IVariableHandler variableHandler;
	 public BxEnumType(IVariableHandler variableHandler, String typeString){
			super(typeString);
			this.variableHandler = variableHandler;
		}
		
		@Override
		public Object getValue(String valueString) throws CoreException {
			if (NULL_ATTR.equals(valueString)) {
				return null;
			}
			try {
				String pName = getTypeString().substring(0, getTypeString().lastIndexOf("."));
				String enumName= getTypeString().substring(getTypeString().lastIndexOf(".")+1, getTypeString().length());
				EPackage ePackage = BomProjectManager.getDefault().getEPackage(pName);
				EFactory factory = ePackage.getEFactoryInstance();
				return factory.createFromString((EDataType)ePackage.getEClassifier(enumName), valueString);
			} catch (Exception e) {
				DebugCoreActivator.log(e);
				throw new CoreException(DebugCoreActivator.newStatus(e, Messages.getString("BxComplexType.deserializeError"))); //$NON-NLS-1$
			}
		}
}
