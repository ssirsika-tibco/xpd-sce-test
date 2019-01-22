/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.wsdlgen.wsdl.rules;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.wst.wsdl.Definition;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.projectconfig.SpecialFolders;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;
import com.tibco.xpd.wsdl.ui.WsdlUIPlugin;
import com.tibco.xpd.wsdlgen.WsdlgenPlugin;

/**
 * This rule raises a warning level marker on all WSDLs that have the generated
 * flags on its definition.
 * 
 * The rule is accompanied by a quick-fix which will remove the generated flag
 * from the WSDL.
 * 
 * @author rsomayaj
 * @since 3.3 (17 Sep 2010)
 */
public class WsdlIsGeneratedRule implements IValidationRule {

    /**
     * 
     */
    private static final String WSDL_GENERATED_INFO = "wsdlgen.wsdlIsGenerated";

    /**
     * @see com.tibco.xpd.validation.rules.IValidationRule#getTargetClass()
     * 
     * @return
     */
    @Override
    public Class<?> getTargetClass() {
        return Definition.class;
    }

    /**
     * @see com.tibco.xpd.validation.rules.IValidationRule#validate(com.tibco.xpd.validation.provider.IValidationScope,
     *      java.lang.Object)
     * 
     * @param scope
     * @param o
     */
    @Override
    public void validate(IValidationScope scope, Object o) {
        if (o instanceof Definition) {
            Definition definition = (Definition) o;
            if (definition.getElement() != null) {
                String serviceTaskAttrib =
                        definition.getElement()
                                .getAttribute(WsdlUIPlugin.TIBEX_SERVICE_TASK);
                if (serviceTaskAttrib != null && serviceTaskAttrib.length() > 0) {
                    addWarning(scope, definition);
                } else {
                    // If the definition element has XPDL attribute and the WSDL
                    // is not in the "Generated Services" folder - add the
                    // warning.
                    String xpdlPlaceHolder =
                            definition
                                    .getElement()
                                    .getAttribute(WsdlUIPlugin.TIBEX_XPDL_PLACEHOLDER);
                    if (xpdlPlaceHolder != null && xpdlPlaceHolder.length() > 0) {
                        IFile wsdlFile = WorkingCopyUtil.getFile(definition);
                        if (wsdlFile != null && wsdlFile.exists()) {

                            IProject project = wsdlFile.getProject();
                            if (project != null) {
                                ProjectConfig projectConfig =
                                        XpdResourcesPlugin.getDefault()
                                                .getProjectConfig(project);
                                if (projectConfig != null) {
                                    SpecialFolders specialFolders =
                                            projectConfig.getSpecialFolders();
                                    if (specialFolders != null) {
                                        SpecialFolder folderContainer =
                                                specialFolders
                                                        .getFolderContainer(wsdlFile);
                                        if (folderContainer != null
                                                && !(WsdlgenPlugin.GENERATED_SERVICES_FOLDER
                                                        .equals(folderContainer
                                                                .getFolder()
                                                                .getName()))) {
                                            addWarning(scope, definition);
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
            }

        }
    }

    /**
     * @param scope
     * @param definition
     */
    private void addWarning(IValidationScope scope, Definition definition) {
        String uri = definition.eResource().getURIFragment(definition);
        scope.createIssue(WSDL_GENERATED_INFO,
                definition.getTargetNamespace(),
                uri);
    }
}
