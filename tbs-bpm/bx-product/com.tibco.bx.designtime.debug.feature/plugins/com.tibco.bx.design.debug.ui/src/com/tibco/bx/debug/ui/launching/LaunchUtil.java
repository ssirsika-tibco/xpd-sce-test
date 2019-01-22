package com.tibco.bx.debug.ui.launching;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

import com.tibco.bx.debug.core.DebugCoreActivator;
import com.tibco.bx.debug.core.launching.IBxLaunchAttribute;
import com.tibco.bx.debug.ui.Messages;

/**
 * @author yowang
 * 
 */
public class LaunchUtil {

	private static final String PREFERENCE_NODE_NAME = "com.tibco.bx.debug"; //$NON-NLS-1$
	private static final String REMOTEENGINES = "remoteEngines"; //$NON-NLS-1$

	public static final int MAX_PROCESS_NUM = 10;

	/**
	 * read engines from configuration the structure is: <com.tibco.bx.debug>
	 * <remoteEngines> <1 hostName="***"/> <2 port="***"/> <2
	 * debuggerName="***"/> <2 protocol="***"/> ..... </remoteEngines>
	 * </com.tibco.bx.debug>
	 * 
	 * @return
	 * @throws BackingStoreException
	 */
	public static List<RemoteEngineElement> readRemoteEngines() {
		IEclipsePreferences node = new ConfigurationScope().getNode(PREFERENCE_NODE_NAME);
		Preferences preferences = node.node(REMOTEENGINES);

		String[] children;
		List<RemoteEngineElement> list = new ArrayList<RemoteEngineElement>();
		try {
			children = preferences.childrenNames();
			for (String e : children) {
				Preferences engine = preferences.node(e);
				String type = engine.get(IBxLaunchAttribute.MODEL_TYPE, ""); //$NON-NLS-1$
				String protocol = engine.get(IBxLaunchAttribute.CONNECTIONFACTORY_TYPE, ""); //$NON-NLS-1$
				String hostName = engine.get(IBxLaunchAttribute.ENGINE_ADDRESS, ""); //$NON-NLS-1$ 
				String port = engine.get(IBxLaunchAttribute.ENGINE_PORT, ""); //$NON-NLS-1$
				String endpointURI = engine.get(IBxLaunchAttribute.ENDPOINT_URI, ""); //$NON-NLS-1$
				String url = engine.get(IBxLaunchAttribute.URL, ""); //$NON-NLS-1$
				String username = engine.get(IBxLaunchAttribute.USER_NAME, ""); //$NON-NLS-1$
				String password = engine.get(IBxLaunchAttribute.PASSWORD, ""); //$NON-NLS-1$
				String soapVersion = engine.get(IBxLaunchAttribute.SOAP_VERSION, ""); //$NON-NLS-1$
				list.add(0, new RemoteEngineElement(type, hostName, protocol, port, endpointURI, url, username, password, soapVersion));
			}
		} catch (BackingStoreException e1) {
		}
		return list;
	}

	/**
	 * write engines to configuration the structure is: <com.tibco.bx.debug>
	 * <remoteEngines> <1 name="***"/> <2 port="***"/> <2 protocol="***"/> .....
	 * </remoteEngines> </com.tibco.bx.debug>
	 */
	public static void saveRemoteEngines(List<RemoteEngineElement> engines) {
		IEclipsePreferences node = new ConfigurationScope().getNode(PREFERENCE_NODE_NAME);
		try {
			Preferences preferences = node.node(REMOTEENGINES);
			preferences.removeNode();
			preferences = node.node(REMOTEENGINES);
			for (int i = 0; i < MAX_PROCESS_NUM && i < engines.size(); i++) {
				Preferences engine = preferences.node((i + 1) + ""); //$NON-NLS-1$
				engine.put(IBxLaunchAttribute.MODEL_TYPE, engines.get(i).getType());
				engine.put(IBxLaunchAttribute.CONNECTIONFACTORY_TYPE, engines.get(i).getProtocol());
				engine.put(IBxLaunchAttribute.ENGINE_ADDRESS, engines.get(i).getHostName());
				engine.put(IBxLaunchAttribute.ENGINE_PORT, engines.get(i).getPort());
				engine.put(IBxLaunchAttribute.ENDPOINT_URI, engines.get(i).getEndpointURI());
				engine.put(IBxLaunchAttribute.USER_NAME, engines.get(i).getUsername());
				engine.put(IBxLaunchAttribute.PASSWORD, engines.get(i).getPassword());
			}
			node.flush();
		} catch (BackingStoreException e) {
			openErrorDialog(e, Messages.getString("LaunchUtil.cannotSaveSettings")); //$NON-NLS-1$
		}
	}

	public static void openErrorDialog(final Throwable e, final String message) {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				ErrorDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
						Messages.getString("LaunchUtil.errorTitle"), null, //$NON-NLS-1$
						DebugCoreActivator.newStatus(e, message));
			}
		});
	}

	public static String[] getHostNames(List<RemoteEngineElement> engines) {
		String[] names = new String[engines.size()];
		for (int i = 0; i < names.length; i++) {
			names[i] = engines.get(i).getHostName();
		}
		return names;
	}

	public static String[] getUniqueHostNames(List<RemoteEngineElement> engines) {
		List<String> nameList = new ArrayList<String>();
		String name = null;
		for (RemoteEngineElement element : engines) {
			name = element.getHostName();
			if (!nameList.contains(name)) {
				nameList.add(name);
			}
		}
		return nameList.toArray(new String[nameList.size()]);
	}

	public static String[] getPorts(List<RemoteEngineElement> engines) {
		String[] names = new String[engines.size()];
		for (int i = 0; i < names.length; i++) {
			names[i] = engines.get(i).getPort();
		}
		return names;
	}

	/**
	 * 
	 * @param engines
	 * @param host
	 * @param protocol
	 * @return
	 */
	public static String[] getUniquePorts(List<RemoteEngineElement> engines, String host, String protocol) {
		Set<String> portSet = new HashSet<String>();
		String port = null;
		for (RemoteEngineElement element : engines) {
			port = element.getPort();
			if ((element.getHostName().equals(host) || "".equals(host) || host == null)//$NON-NLS-1$
					&& (element.getProtocol().equals(protocol) || "".equals(protocol) || protocol == null)) {//$NON-NLS-1$
				portSet.add(port);
			}
		}
		return portSet.toArray(new String[portSet.size()]);
	}
}
