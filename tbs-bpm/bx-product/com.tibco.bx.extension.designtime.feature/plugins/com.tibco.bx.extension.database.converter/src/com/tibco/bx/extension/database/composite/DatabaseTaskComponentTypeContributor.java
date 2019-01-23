package com.tibco.bx.extension.database.composite;

import javax.xml.namespace.QName;

import org.eclipse.bpel.model.Process;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.tibco.amf.sca.model.componenttype.ComponentType;
import com.tibco.amf.sca.model.extensionpoints.Implementation;
import com.tibco.amf.sca.model.extensionpoints.Property;
import com.tibco.amf.sca.model.extensionpoints.SCAExtensionsFactory;
import com.tibco.amf.tools.composite.resources.util.UnprotectedWriteOperation;
import com.tibco.bx.amx.model.service.BxServiceImplementation;
import com.tibco.bx.composite.core.extensions.IComponentTypeContributor;
import com.tibco.bx.model.service.ServiceImplementation;
import com.tibco.bx.xpdl2bpel.util.BPELUtils;
import com.tibco.bx.xpdl2bpel.util.XPDLUtils;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

public class DatabaseTaskComponentTypeContributor implements IComponentTypeContributor {

	private static final String DATABASE_LN = "JdbcDataSource";
	
	@Override
	public void contributeToComponentType(ComponentType serviceType, Implementation implementation) {
		try {
			BxServiceImplementation bxImplementation = (BxServiceImplementation) implementation;
			ServiceImplementation serviceModel = bxImplementation
					.getServiceModel();
			if (serviceModel == null) {
				return;
			}

			String moduleName = serviceModel.getModuleName();
			IFile xpdlFile = ResourcesPlugin.getWorkspace().getRoot().getFile(
					new Path(moduleName));
			if (xpdlFile == null || !xpdlFile.exists()) {
				return;
			}
			WorkingCopy workingCopy = XpdResourcesPlugin.getDefault()
					.getWorkingCopy(xpdlFile);
			Package topPackage = (Package) workingCopy.getRootElement();

			EList<com.tibco.bx.core.model.Process> processes = serviceModel
					.getProcesses();
			for (com.tibco.bx.core.model.Process process : processes) {
				IResource file = ResourcesPlugin.getWorkspace().getRoot()
						.findMember(process.getProcessFileName());
				addPropertiesForDatabaseTask((IFile) file, bxImplementation,
						topPackage);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addPropertiesForDatabaseTask(IFile bpelFile,
			BxServiceImplementation implementation, Package topPackage) throws Exception {
		Process process = BPELUtils.getProcess(bpelFile);
		if (process == null) {
			return;
		}
		Element element = process.getElement();
		String processId = BPELUtils.getXpdlId(process);
		com.tibco.xpd.xpdl2.Process xpdlProcess = topPackage.getProcess(processId);
		visit(element, implementation, xpdlProcess);
	}

	private void visit(Element element, BxServiceImplementation implementation,
			com.tibco.xpd.xpdl2.Process process) throws Exception {
		if (BPELUtils.DATABASE_EXTENSION_NAMESPACE_URI.equals(element.getNamespaceURI())) {
			Element parentNode = element;
			String xpdlID = null;
			while ((xpdlID == null || xpdlID.length() == 0) && parentNode != null) {
				parentNode = (Element) parentNode.getParentNode();
				xpdlID = BPELUtils.getXpdlIdFromElement(parentNode);
			}
			Activity activity = Xpdl2ModelUtil.getActivityById(process, xpdlID);
			String databaseSimpleValue = XPDLUtils.resolveDatabaseSimpleValue(activity);
			if(databaseSimpleValue==null){
				return;
			}

			String name = element
					.getAttribute(BPELUtils.CONNECTION_RESOURCE_ATT);
			QName type = new QName(BPELUtils.DATABASE_SR_NS,DATABASE_LN);
			if (name != null && name.length() > 0) {
				addProperty(name, type, databaseSimpleValue, implementation);
			}

		} else {
			NodeList childNodes = element.getChildNodes();
			for (int i = 0; i < childNodes.getLength(); i++) {
				Node item = childNodes.item(i);
				if (item.getNodeType() != Node.ELEMENT_NODE) {
					continue;
				}
				visit((Element) item, implementation, process);
			}
		}
	}

	private void addProperty(final String name, final QName type,
			final String simpleValue,
			final BxServiceImplementation implementation) {
		UnprotectedWriteOperation operation = new UnprotectedWriteOperation(
				TransactionUtil.getEditingDomain(implementation)) {

			@Override
			protected Object doExecute() {
				EList<Property> properties = implementation.getServiceType()
						.getProperties();
				for (Property p : properties) {
					if (name.equals(p.getName()) && type.equals(p.getType())
							&& simpleValue.equals(p.getSimpleValue())) {
						return Status.CANCEL_STATUS;
					}
				}
				Property property = SCAExtensionsFactory.INSTANCE
						.createProperty();
				property.setName(name);
				property.setType(type);
				property.setSimpleValue(simpleValue);
				properties.add(property);
				return Status.OK_STATUS;
			}
		};
		operation.execute();
	}

}
