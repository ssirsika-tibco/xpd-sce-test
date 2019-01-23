package com.tibco.xpd.bom.validator.rules.xsd;

import org.eclipse.core.resources.IFile;
import org.eclipse.uml2.uml.Operation;

import com.tibco.xpd.bom.validator.XsdIssueIds;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * An N2 validation rule that ensures that Operations are not defined.
 * 
 * @author smorgan
 * 
 */
public class OperationRule implements IValidationRule {

    @Override
    public Class<?> getTargetClass() {
        return Operation.class;
    }

    @Override
    public void validate(IValidationScope scope, Object o) {
        if (o instanceof Operation) {
            Operation op = (Operation) o;
            IFile file = WorkingCopyUtil.getFile(op);
            /*
             * XPD-4941: Saket: This warning marker should not be added to
             * generated BOMs as the user has no choice in the way we generate a
             * BOM from WSDL and include the original operation.
             */
            if (file != null) {
                SpecialFolder sf = SpecialFolderUtil.getRootSpecialFolder(file);

                if (sf == null || sf.getGenerated() == null) {
                    scope.createIssue(XsdIssueIds.OPERATION,
                            op.getQualifiedName(),
                            op.eResource().getURIFragment(op));
                }
            }
        }
    }
}
