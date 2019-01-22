package com.tibco.bx.debug.bpm.core;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;

import com.tibco.bx.debug.api.ConditionLanguage;
import com.tibco.bx.debug.core.DebugCoreActivator;
import com.tibco.bx.debug.core.Tracing;
import com.tibco.bx.debug.core.runtime.IBxTester;
import com.tibco.bx.debug.core.runtime.ITestDelegate;
import com.tibco.bx.emulation.core.EmulationCoreActivator;
import com.tibco.bx.emulation.model.Assertion;
import com.tibco.bx.emulation.model.EmulationData;
import com.tibco.bx.emulation.model.EmulationFactory;
import com.tibco.bx.emulation.model.ProcessNode;
import com.tibco.bx.emulation.model.Testpoint;
import com.tibco.bx.emulation.model.util.EmulationResourceFactoryImpl;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.XpdConsts;

public class BxTestDelegate implements ITestDelegate, PropertyChangeListener {

	IBxTester tester;
	private EmulationData emulationData;

	private Map<Testpoint, com.tibco.bx.tester.Testpoint> testpointMap;
	private Map<Assertion, com.tibco.bx.tester.Assertion> assertionMap;

	public BxTestDelegate(IBxTester tester) {
		this.tester = tester;
		init();
	}

	private void init() {
		testpointMap = new HashMap<Testpoint, com.tibco.bx.tester.Testpoint>();
		assertionMap = new HashMap<Assertion, com.tibco.bx.tester.Assertion>();
		try {
			if (tester != null) {
				// create temporary emulation file for this launch.
				IFile file = createEmulationFileForLaunch();
				WorkingCopy workingCopy = XpdResourcesPlugin.getDefault().getXpdProjectResourceFactory(file.getProject()).getWorkingCopy(file);
				if (workingCopy != null) {
					emulationData = (EmulationData) workingCopy.getRootElement();
					workingCopy.addListener(this);
				}
			}
		} catch (Exception e) {
			DebugCoreActivator.log(e);
		}
	}

	private IFile createEmulationFileForLaunch() throws CoreException {
		String projectName = ".debug"; //$NON-NLS-1$
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IProject project = workspace.getRoot().getProject(projectName);
		if (!project.exists()) {
			IProjectDescription description = workspace.newProjectDescription(projectName);
			description.setNatureIds(new String[] { XpdConsts.PROJECT_NATURE_ID });
			project.create(description, null);
		}
		project.open(null);
		IFile file = project.getFile(tester.hashCode() + "." + EmulationCoreActivator.EMULATION_FILE_EXTENSION); //$NON-NLS-1$
		Resource resource = new EmulationResourceFactoryImpl().createResource(URI.createURI(file.getLocationURI().toString()));
		resource.getContents().add(EmulationFactory.eINSTANCE.createEmulationData());
		try {
			resource.save(null);
			file.refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (IOException e) {
			DebugCoreActivator.log(e);
		}

		return file;
	}

	@Override
	public void dispose() {
		try {
			removeEmulationFile();
			testpointMap.clear();
			assertionMap.clear();
		} catch (CoreException e) {
			DebugCoreActivator.log(e);
		}
	}

	private void removeEmulationFile() throws CoreException {
		if (emulationData != null) {
			String path = emulationData.eResource().getURI().toPlatformString(true);
			IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(path));
			if (file.exists()) {
				emulationData = null;
				file.delete(true, null);
			}
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		String propName = event.getPropertyName();
		if (propName.equals(WorkingCopy.PROP_REMOVED)) {
			// TODO launch terminated
			return;
		} else if (propName.equals(WorkingCopy.CHANGED)) {
			if (Tracing.ENABLED) {
				Tracing.trace("Call Debugger to add TestPoints"); //$NON-NLS-1$
			}
			if (event.getNewValue() instanceof ResourceSetChangeEvent) {
				ResourceSetChangeEvent resEvent = (ResourceSetChangeEvent) event.getNewValue();
				for (Notification notification : resEvent.getNotifications()) {
					if (notification.getEventType() == Notification.ADD) {
						if (notification.getNewValue() instanceof Testpoint) {
							addTestpoint((Testpoint) notification.getNewValue());
						} else if (notification.getNewValue() instanceof Assertion) {
							addAssertion((Assertion) notification.getNewValue());
						} else if (notification.getNewValue() instanceof ProcessNode) {
							ProcessNode processNode = (ProcessNode) notification.getNewValue();
							addAll(processNode);
						}
					} else if (notification.getEventType() == Notification.SET) {
						if (notification.getNewValue() != null) {
							if (notification.getNotifier() instanceof Testpoint) {
								setTestpoint((Testpoint) notification.getNotifier());
							} else if (notification.getNotifier() instanceof Assertion) {
								setAssertion((Assertion) notification.getNotifier());
							}
						}
					} else if (notification.getEventType() == Notification.REMOVE) {
						if (notification.getOldValue() instanceof ProcessNode) {
							removeAll((ProcessNode) notification.getOldValue());
						} else if (notification.getOldValue() instanceof Testpoint) {
							removeTestpoint((Testpoint) notification.getOldValue());
						} else if (notification.getOldValue() instanceof Assertion) {
							removeAssertion((Assertion) notification.getOldValue());
						}
					} else if (notification.getEventType() == Notification.ADD_MANY) {
						if (notification.getNotifier() instanceof EmulationData) {
							EmulationData emulationData = (EmulationData) notification.getNotifier();
							for (ProcessNode processNode : emulationData.getProcessNodes()) {
								addAll(processNode);
							}
						}
					}
				}
			}
		}

	}
	
	private void addAll(ProcessNode processNode) {
		EList<Assertion> assertions = processNode.getAssertions();
		for (Assertion assertion : assertions) {
			addAssertion(assertion);
		}
		EList<Testpoint> testpoints = processNode.getTestpoints();
		for (Testpoint tp : testpoints) {
			addTestpoint(tp);
		}
	}
	
	private void removeAll(ProcessNode processNode) {
		EList<Assertion> assertions = processNode.getAssertions();
		for (Assertion assertion : assertions) {
			removeAssertion(assertion);
		}
		EList<Testpoint> testpoints = processNode.getTestpoints();
		for (Testpoint testpoint : testpoints) {
			removeTestpoint(testpoint);
		}
	}
	
	public void addTestpoint(Testpoint testpoint) {
		try {
			if (tester == null) {
				return;
			}
			ProcessNode pNode = testpoint.getProcessNode();
			com.tibco.bx.tester.Testpoint testerPoint = new com.tibco.bx.tester.Testpoint();
			testerPoint.setProcessId(pNode.getId());
			testerPoint.setLocation(testpoint.getId());
			testerPoint.setExpression(testpoint.getExpression());
			testerPoint.setLang(ConditionLanguage.JSCRIPT);
			tester.addTestpoint(testerPoint);
			testpointMap.put(testpoint, testerPoint);
		} catch (CoreException e) {
			DebugCoreActivator.log(e);
		}
	}

	private void setTestpoint(Testpoint testpoint) {
		if (testpointMap.get(testpoint) != null) {
			testpointMap.remove(testpoint);
			addTestpoint(testpoint);
		}
	}

	public void removeTestpoint(Testpoint testpoint) {
		try {
			if (testpointMap.get(testpoint) != null) {
				tester.removeTestpoint(testpointMap.get(testpoint));
			}
		} catch (CoreException e) {
			e.printStackTrace();
		} finally {
			testpointMap.remove(testpoint);
		}
	}

	private void addAssertion(Assertion assertion) {
		try {
			if (tester == null) {
				return;
			}
			ProcessNode pNode = assertion.getProcessNode();
			com.tibco.bx.tester.Assertion testerAssertion = new com.tibco.bx.tester.Assertion();
			testerAssertion.setProcessId(pNode.getId());
			testerAssertion.setLocation(assertion.getId());
			testerAssertion.setAccessible(assertion.isAccessible());
			tester.addAssertion(testerAssertion);
			assertionMap.put(assertion, testerAssertion);
		} catch (CoreException e) {
			DebugCoreActivator.log(e);
		}
	}

	private void setAssertion(Assertion assertion) {
		if (assertionMap.get(assertion) != null) {
			assertionMap.remove(assertion);
			addAssertion(assertion);
		}
	}

	private void removeAssertion(Assertion assertion) {
		try {
			if (assertionMap.get(assertion) != null) {
				tester.removeAssertion(assertionMap.get(assertion));
			}
		} catch (CoreException e) {
			DebugCoreActivator.log(e);
		} finally {
			assertionMap.remove(assertion);
		}
	}

	public EmulationData getEmulationData() {
		return emulationData;
	}

	@Override
	public Object getAdapter(Class cl) {
		if (cl == EmulationData.class)
			return emulationData;
		return null;
	}
}
