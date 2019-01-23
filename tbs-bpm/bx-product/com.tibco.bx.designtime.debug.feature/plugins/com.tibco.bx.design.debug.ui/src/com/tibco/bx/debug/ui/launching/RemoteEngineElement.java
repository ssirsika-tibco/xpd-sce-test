package com.tibco.bx.debug.ui.launching;

public class RemoteEngineElement {

	private String type;
	private String hostName;
	private String protocol;
	private String port;
	private String endpointURI;
	private String url;
	private String username;
	private String password;
	private String soapVersion;

	public RemoteEngineElement() {
		this.type = ""; //$NON-NLS-1$
		this.hostName = ""; //$NON-NLS-1$
		this.port = ""; //$NON-NLS-1$
		this.protocol = ""; //$NON-NLS-1$
		this.endpointURI = ""; //$NON-NLS-1$
		this.url = ""; //$NON-NLS-1$
		this.username = ""; //$NON-NLS-1$
		this.password = ""; //$NON-NLS-1$
		this.soapVersion = ""; //$NON-NLS-1$
	}

	public RemoteEngineElement(String type, String hostName, String protocol, String port, String endpointURI, String url, String username, String password,
			String soapVersion) {
		this.type = type;
		this.hostName = hostName;
		this.port = port;
		this.protocol = protocol;
		this.endpointURI = endpointURI;
		this.url = url;
		this.username = username;
		this.password = password;
		this.soapVersion = soapVersion;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEndpointURI() {
		return endpointURI;
	}

	public void setEndpointURI(String endpointURI) {
		this.endpointURI = endpointURI;
	}

	public String getSoapVersion() {
		return soapVersion;
	}

	public void setSoapVersion(String soapVersion) {
		this.soapVersion = soapVersion;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof RemoteEngineElement) {
			RemoteEngineElement remoteEngineElement = (RemoteEngineElement) obj;
			// Asset name !=null && port !=null
			return remoteEngineElement.getType().equals(type) && remoteEngineElement.getHostName().equals(hostName)
					&& remoteEngineElement.getPort().equals(port) && remoteEngineElement.getUrl().equals(url) && remoteEngineElement.getProtocol().equals(protocol)
					&& remoteEngineElement.getSoapVersion().equals(soapVersion);
		}
		return false;
	}
}
