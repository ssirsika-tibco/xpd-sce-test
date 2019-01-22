package com.tibco.xpd.bom.validator.resolution;

import org.eclipse.jface.dialogs.IInputValidator;

import com.tibco.xpd.bom.validator.internal.Messages;

public class RenamePackageResolution extends AbstractRenameResolution {

    public RenamePackageResolution() {
        super(Messages.RenamePackageResolution_rename_package_message);
    }

    @Override
    protected IInputValidator getValidator() {
        return new IInputValidator() {

            public String isValid(String newText) {
                String err = null;

                if (newText.length() == 0) {
                    err = Messages.RenamePackageResolution_name_required_message;
                    // TODO
// } else if (!PackageNameRule.PACKAGE_NAME_PATTERN.matcher(
//                        newText).matches()) {
                    err = Messages.RenamePackageResolution_package_name_wrong_format_message;
                }

                return err;
            }

        };
    }

}
