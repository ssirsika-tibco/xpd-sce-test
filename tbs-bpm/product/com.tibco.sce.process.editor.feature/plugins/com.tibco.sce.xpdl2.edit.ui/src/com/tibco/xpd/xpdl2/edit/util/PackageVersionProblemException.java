package com.tibco.xpd.xpdl2.edit.util;
/**
 * This class represents an exceptional situation when 
 * we are not able to understand the version mismatch 
 * 
 * @author KamleshU
 *
 */
public class PackageVersionProblemException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public PackageVersionProblemException(String msg) {
		super(msg);
	}

	public PackageVersionProblemException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public PackageVersionProblemException(Throwable throwable) {
		super(throwable);
	}

}
