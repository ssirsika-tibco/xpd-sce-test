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
public class ClassSpecialization implements IEditHelperAdvice {

    public boolean approveRequest(IEditCommandRequest request) {
        return true;
    }

    public void configureRequest(IEditCommandRequest request) {
        // do nothing
    }

    public ICommand getBeforeEditCommand(IEditCommandRequest request) {
        return null;
    }
    
    public ICommand getAfterEditCommand(IEditCommandRequest request) {
        return null;
    }

}
