/**
 * RefactorAsEmbeddedSubProcWizard.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */
package com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor;

import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.internal.Messages;

/**
 * RefactorAsEmbeddedSubProcWizard
 * 
 */
public class RefactorAsEmbeddedSubProcWizard extends
		BaseRefactorAsSubProcWizard {

	/**
	 * @param refactorInfo
	 * @param editingDomain
	 * @param wizardTitle
	 * @param wizardDescription
	 */
	public RefactorAsEmbeddedSubProcWizard(
			RefactorAsSubProcWizardInfo refactorInfo,
			EditingDomain editingDomain) {
		super(
				refactorInfo,
				editingDomain,
				Messages.RefactorAsEmbeddedSubProcWizard_RefactorAsEmbSubproc_title,
				Messages.RefactorAsEmbeddedSubProcWizard_RefactorAsEmbSubproc_longdesc);

	}

}
