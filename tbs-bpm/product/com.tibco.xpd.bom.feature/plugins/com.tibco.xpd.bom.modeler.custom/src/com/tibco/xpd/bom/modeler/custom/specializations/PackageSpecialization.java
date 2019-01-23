/**
 * 
 */
package com.tibco.xpd.bom.modeler.custom.specializations;

import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.IEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;

/**
 * @author wzurek
 *
 */
public class PackageSpecialization implements IEditHelperAdvice {

    public boolean approveRequest(IEditCommandRequest request) {
        return true;
    }

    public void configureRequest(IEditCommandRequest request) {
    }

    public ICommand getAfterEditCommand(IEditCommandRequest request) {
        return null;
    }

    public ICommand getBeforeEditCommand(IEditCommandRequest request) {
        return null;
    }
}
