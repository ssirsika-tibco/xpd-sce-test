package com.tibco.bx.extension.java.composite;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.bpel.model.Process;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.tibco.amf.dependency.osgi.RequiredBundle;
import com.tibco.amf.sca.model.componenttype.Requirements;
import com.tibco.amf.sca.model.extensionpoints.Implementation;
import com.tibco.bx.amx.model.service.BxServiceImplementation;
import com.tibco.bx.composite.core.extensions.IRequirementsResolver;
import com.tibco.bx.composite.core.util.BxCompositeCoreUtil;
import com.tibco.bx.extensions.java.JavaExtensionModel.FactoryClassType;
import com.tibco.bx.extensions.java.JavaExtensionModel.JavaProjectType;
import com.tibco.bx.model.service.ServiceImplementation;
import com.tibco.bx.xpdl2bpel.util.BPELUtils;

public class JavaTaskRequirementsResolver implements IRequirementsResolver {

	public JavaTaskRequirementsResolver() {
	}

	public void addImplementationRequirements(Requirements requirements, Implementation implementation) {
		try {
			BxServiceImplementation bxImplementation = (BxServiceImplementation) implementation;
			ServiceImplementation serviceModel = bxImplementation.getServiceModel();
			if(serviceModel==null){
				return;
			}

			Set<RequiredBundle> requiredBundles = new HashSet<RequiredBundle>();
			Set<String> referencedPackages = new HashSet<String>();
			EList<com.tibco.bx.core.model.Process> processes = serviceModel.getProcesses();
			for (com.tibco.bx.core.model.Process process : processes) {
				IResource file = ResourcesPlugin.getWorkspace().getRoot().findMember(process.getProcessFileName());
				findRequiredBundles((IFile)file, requirements, requiredBundles,referencedPackages);
			}
			BxCompositeCoreUtil.handlePluginRequirement(requirements,
					bxImplementation, requiredBundles, referencedPackages);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void findRequiredBundles(IFile bpelFile,
			Requirements requirements, Set<RequiredBundle> requiredBundles,Set<String> referencedPackages) throws Exception {
		Process process =  BPELUtils.getProcess(bpelFile);
		if (process == null) {
			return;
		}
		Element element = process.getElement();
		visit(element, requiredBundles,referencedPackages);
	}
	
	private void visit(Element element, Set<RequiredBundle> requiredBundles, Set<String> referencedPackages) throws Exception {
		if (BPELUtils.JAVA_EXTENSION_NAMESPACE_URI.equals(element.getNamespaceURI())) {

			JavaProjectType javaProjectType = convertElement2JavaProject(element);
			if (javaProjectType != null) {
				//create requiredBundle used for custom.feature
				String id = javaProjectType.getPluginId();
				String version = javaProjectType.getVersion();
				RequiredBundle requiredBundle = BxCompositeCoreUtil
						.createRequiredBundle(id, version);
				requiredBundles.add(requiredBundle);
				
				//create importedPackage used for requirements file
				//TODO only export-Packages in referenced plug-in will be considered.
				String qualifyName = javaProjectType.getJavaClass().getQualifyName();
				String classPackageName = getPackageName(qualifyName);
				referencedPackages.add(classPackageName);
				
				String factoryPackageName = null;
				FactoryClassType factoryClass = javaProjectType.getFactoryClass();
				if(factoryClass!=null){
					factoryPackageName = getPackageName(factoryClass.getQualifyName());
					referencedPackages.add(factoryPackageName);
				}
			}
		} else {
			NodeList childNodes = element.getChildNodes();
			for (int i = 0; i < childNodes.getLength(); i++) {
				Node item = childNodes.item(i);
				if (item.getNodeType() != Node.ELEMENT_NODE) {
					continue;
				}
				visit((Element) item, requiredBundles,referencedPackages);
			}
		}
	}
	
	private String getPackageName(String classQualifyName){
		int lastIndex = classQualifyName.lastIndexOf(".");
		if(lastIndex==-1){
			return null;
		}
		return classQualifyName.substring(0,lastIndex);
	}
	
	private JavaProjectType convertElement2JavaProject(Element element) throws Exception{
		DOMSource ds = new DOMSource(element);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		StreamResult sr = new StreamResult(os);
		TransformerFactory.newInstance().newTransformer().transform(ds, sr);
		byte[] byteArray = os.toByteArray();
		Resource res = new XMLResourceImpl();
		ByteArrayInputStream bais = new ByteArrayInputStream(byteArray);
		res.load(bais, null);
		List contents = res.getContents();
		EObject eobject = (!contents.isEmpty()) ? (EObject) contents.get(0)
				: null;

		bais.close();
		os.close();

		return (JavaProjectType) eobject;
	}
}
