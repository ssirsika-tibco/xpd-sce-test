package com.tibco.bx.composite.xpdl.core.it;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.osgi.framework.Version;

import com.tibco.amf.sca.componenttype.implementation.ImplementationTypeProvider;
import com.tibco.amf.sca.model.extensionpoints.Implementation;
import com.tibco.bx.amx.model.service.BxGlobalSignalImplementation;
import com.tibco.bx.amx.model.service.ServiceFactory;
import com.tibco.bx.composite.core.BxCompositeCoreActivator;
import com.tibco.bx.composite.core.util.BxCompositeCoreConstants;
import com.tibco.bx.core.model.BxGlobalSignalModel;
import com.tibco.bx.core.model.GlobalSignalDataType;
import com.tibco.bx.core.model.GlobalSignalDefinition;
import com.tibco.bx.core.model.GlobalSignalParameter;
import com.tibco.bx.core.model.GlobalSignalType;
import com.tibco.bx.core.model.ModelFactory;
import com.tibco.bx.xpdl2bpel.util.XPDLUtils;
import com.tibco.xpd.globalSignalDefinition.GlobalSignal;
import com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitions;
import com.tibco.xpd.globalSignalDefinition.PayloadDataField;
import com.tibco.xpd.globalSignalDefinition.util.GlobalSignalUtil;
import com.tibco.xpd.resources.projectconfig.ProjectDetails;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.DeclaredType;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Member;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.RecordType;
import com.tibco.xpd.xpdl2.TypeDeclaration;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

public abstract class BxGlobalSignalImplementationTypeProvider implements ImplementationTypeProvider {
	
	/**
	 * Returns all global signal definitions defined in this project
	 * @return global signal definitions
	 */
	public abstract Collection<GlobalSignalDefinitions> getGlobalSignalDefinitions(IProject project);
	
	protected BxGlobalSignalImplementation newImplementation() {
		return ServiceFactory.eINSTANCE.createBxGlobalSignalImplementation();
	}
	
	public String getImplementationTypeId() {
		return BxCompositeCoreConstants.GLOBAL_SIGNAL_SCA_IMPLEMENTATION_TYPE;
	}
	
	/*
	 * GlobalSignalDefinition name = <gsd special folder relative file path>#<signalName>
	 */
	private Collection<GlobalSignalDefinition> convertGlobalSignalDefinitions(Collection<GlobalSignalDefinitions> gsds) {
		Collection<GlobalSignalDefinition> signalDefinitions = new ArrayList<GlobalSignalDefinition>();
		for (GlobalSignalDefinitions globalSignalDefinitions:gsds) {
			for (GlobalSignal globalSignal:globalSignalDefinitions.getGlobalSignals()) {
				GlobalSignalDefinition signal = ModelFactory.eINSTANCE.createGlobalSignalDefinition();
				String qualifiedName = GlobalSignalUtil.getGlobalSignalQualifiedName(globalSignal);
				qualifiedName = qualifiedName.substring(qualifiedName.indexOf('/')+1);
				signal.setSignalName(qualifiedName);
				signal.setSignalTimeout(globalSignal.getCorrelationTimeout()==null?0:globalSignal.getCorrelationTimeout().longValue());
				Package pckg = Xpdl2ModelUtil.getPackage(globalSignal);
				for (PayloadDataField dataField:globalSignal.getPayloadDataFields()) {
					GlobalSignalParameter signalParameter = convertGlobalSignalParameter(pckg, dataField);
					signal.getSignalParameters().add(signalParameter);
				}
				signalDefinitions.add(signal);
			}
		}
		return signalDefinitions;
	}
	
	private GlobalSignalParameter convertGlobalSignalParameter(Package pckg, PayloadDataField dataField) {
		GlobalSignalParameter parameter = ModelFactory.eINSTANCE.createGlobalSignalParameter();
		parameter.setParameterName(dataField.getName());
		parameter.setMandatory(dataField.isMandatory());
		parameter.setCorrelation(dataField.isCorrelation());
		GlobalSignalDataType dataType = convertGlobalSignalType(pckg, dataField.getDataType(), dataField.isIsArray());
		parameter.setParameterType(dataType);
		return parameter;
	}
	
	private GlobalSignalDataType convertGlobalSignalType(Package pckg, DataType dataType, boolean isArray) {
		GlobalSignalDataType globalSignalDataType = ModelFactory.eINSTANCE.createGlobalSignalDataType();
		GlobalSignalType type = null;
		String className = null;
		if (dataType instanceof BasicType) {
			BasicTypeType basicTypeType = ((BasicType) dataType).getType();
            if (basicTypeType != null) {
                switch (basicTypeType.getValue()) {
                    case BasicTypeType.BOOLEAN:
                    	type = GlobalSignalType.BOOLEAN;
                    	break;
                    case BasicTypeType.DATETIME:
                    	type = GlobalSignalType.DATE_TIME;
                    	break;
                    case BasicTypeType.DATE:
                    	type = GlobalSignalType.DATE;
                    	break;
                    case BasicTypeType.TIME:
                    	type = GlobalSignalType.TIME;
                    	break;
                    case BasicTypeType.FLOAT:
                    	type = GlobalSignalType.FLOAT;
                    	break;
                    case BasicTypeType.INTEGER:
                    	type = GlobalSignalType.INTEGER;
                    	break;
                    case BasicTypeType.STRING:
                    case BasicTypeType.PERFORMER:
                    	type = GlobalSignalType.STRING;
                    	break;
                }
            }
		} else if (dataType instanceof RecordType) {
			EList<Member> memberList = ((RecordType) dataType).getMember();
            Member member = memberList.get(0);
            ExternalReference dataTypeExternalReference = null;
            if (null != member.getExternalReference()) {
            	dataTypeExternalReference = member.getExternalReference();
            }
            className = XPDLUtils.getBomClassName((ExternalReference) dataTypeExternalReference);
			type = GlobalSignalType.CASE_REFERENCE;
		} else if (dataType instanceof ExternalReference) {
			className = XPDLUtils.getBomClassName((ExternalReference) dataType);
			type = GlobalSignalType.BOM;
		} else if (dataType instanceof DeclaredType) {
			DeclaredType dt = (DeclaredType) dataType;
    		String typeId = dt.getTypeDeclarationId();
            if (pckg != null && typeId != null) {
                TypeDeclaration typeDecl = pckg.getTypeDeclaration(typeId);
                if (typeDecl != null) {
                    if (typeDecl.getBasicType() != null) {
                    	return convertGlobalSignalType(pckg, typeDecl.getBasicType(), isArray);
                    } else if (typeDecl.getExternalReference() != null) {
                    	return convertGlobalSignalType(pckg, typeDecl.getExternalReference(), isArray);
                    }
                }
            }
		}
		
		globalSignalDataType.setClassName(className);
		globalSignalDataType.setDataType(type);
		globalSignalDataType.setIsArray(isArray);

		return globalSignalDataType;
	}

	/**
	 * @param path path to the special GSD folder
	 * @param compositeLocation location of the composite
	 * @return global signal implementation
	 */
	@Override
	public Implementation createImplementation(String path, URI compositeLocation) {
		if (!isSupported(path)) {
			return null;
		}
		try {
			BxGlobalSignalImplementation implementation = newImplementation();
			BxGlobalSignalModel model = ModelFactory.eINSTANCE.createBxGlobalSignalModel();
			implementation.setGlobalSignalModel(model);
			IFolder folder = ResourcesPlugin.getWorkspace().getRoot().getFolder(new Path(path));
			ProjectDetails details = ProjectUtil.getProjectDetails(folder.getProject());
			model.setName(details.getId()); 		 				//name = project id
			Version version = new Version(details.getVersion());
			model.setVersion(String.valueOf(version.getMajor()));   //version = major project version
			Collection<GlobalSignalDefinitions> gsds = getGlobalSignalDefinitions(folder.getProject());
			Collection<GlobalSignalDefinition> newSignals = convertGlobalSignalDefinitions(gsds);
			model.getGlobalSignalDefinitions().addAll(newSignals);
			return implementation;
		} catch (Exception e) {
			BxCompositeCoreActivator.logError(e.getMessage(), e);
		}
		return null;
	}
}