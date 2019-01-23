package com.tibco.xpd.script.ui.internal.extension;

public class CertifiedContributions {

	private static final CertifiedTaskImpl TASK_IMPL = new CertifiedTaskImpl();
	private static final CertifiedScriptGramm SCRIPT_GRAMMAR = new CertifiedScriptGramm();

	private CertifiedContributions() {}

	public static CertifiedTaskImpl getCertifiedTaskImpl() {
		return TASK_IMPL;
	}

	public static CertifiedScriptGramm getCertifiedScriptGramm() {
		return SCRIPT_GRAMMAR;
	}
}
