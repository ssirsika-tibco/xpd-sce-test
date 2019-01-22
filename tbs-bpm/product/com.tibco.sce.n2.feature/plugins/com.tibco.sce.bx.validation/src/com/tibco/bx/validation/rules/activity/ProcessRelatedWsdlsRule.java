/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.bx.validation.rules.activity;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.xsd.XSDSchema;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityWebServiceReference;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.bom.wsdltransform.api.WSDLTransformUtil;
import com.tibco.xpd.implementer.script.ActivityMessageProvider;
import com.tibco.xpd.implementer.script.ActivityMessageProviderFactory;
import com.tibco.xpd.implementer.script.Xpdl2WsdlUtil;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;

/**
 * Rule for checking aspects of WSDL configuration for WSDLs that are referenced
 * by processes.
 * <p>
 * Rule 1: that checks if a web service task invokes an operation from a wsdl
 * that references schemas (by imports or includes) has any relative path
 * references ("../" reference) outside wsdl folder tree.
 * 
 * @author bharge
 * @since 4 Jun 2014
 */
public class ProcessRelatedWsdlsRule extends ProcessValidationRule {

    private static final String ACTIVITY_INVOKES_WSDL_WITH_UNSUPPORTED_SCHEMA_REFERENCE =
            "bx.activityInvokesWsdlWithUnsupportedSchemaRef"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#validate(com.tibco.xpd.xpdl2.Process)
     * 
     * @param process
     */
    @Override
    public void validate(Process process) {

        Package pkg = process.getPackage();
        Map<String, ActivityWebServiceReference> activityWebServiceReferenceMap =
                null;

        EList<Activity> activities = process.getActivities();

        for (Activity activity : activities) {

            ActivityMessageProvider messageProvider =
                    ActivityMessageProviderFactory.INSTANCE
                            .getMessageProvider(activity);

            if (messageProvider != null
                    && messageProvider.isActualWebServiceActivity()
                    && !ReplyActivityUtil.isReplyActivity(activity)) {

                if (null == activityWebServiceReferenceMap) {

                    activityWebServiceReferenceMap =
                            ProcessUIUtil
                                    .getActivityWebServiceReferenceMap(pkg);
                }

                ActivityWebServiceReference activityWebServiceReference =
                        activityWebServiceReferenceMap.get(activity.getId());
                if (null != activityWebServiceReference) {

                    validateServiceTask(activity, activityWebServiceReference);
                }
            }
        }
    }

    /**
     * From the given activity web service reference gets the wsdl file invoked
     * from the activity and calls to validate the referenced schema locations
     * 
     * @param activity
     * @param activityWebServiceReference
     */
    private void validateServiceTask(Activity activity,
            ActivityWebServiceReference activityWebServiceReference) {

        IFile wsdlFile = null;

        String wsdlFileLocation =
                activityWebServiceReference.getWsdlFileLocation();

        if (null != wsdlFileLocation) {

            IProject project = WorkingCopyUtil.getProjectFor(activity);
            wsdlFile =
                    SpecialFolderUtil.resolveSpecialFolderRelativePath(project,
                            "wsdl", wsdlFileLocation, true); //$NON-NLS-1$
            if (null != wsdlFile) {

                validateReferencedSchemaLocation(activity, wsdlFile);
            }
        }

    }

    /**
     * gets the wsdl definition and all the imported and included schemas from
     * it. checks if the imported or included schema location starts with ../
     * and falls outside wsdl folder tree.
     * 
     * @param activity
     * @param wsdlFile
     */
    private void validateReferencedSchemaLocation(Activity activity,
            IFile wsdlFile) {

        org.eclipse.wst.wsdl.Definition definition =
                (org.eclipse.wst.wsdl.Definition) Xpdl2WsdlUtil
                        .getDefinition(wsdlFile);

        IPath wsdlFullPath = wsdlFile.getParent().getFullPath();
        /* get all imported and included schemas */
        Set<XSDSchema> referencedSchemas =
                WSDLTransformUtil.getReferencedSchemas(definition);

        URI wsdlFileUri =
                URI.createPlatformResourceURI(wsdlFile.getFullPath().toString(),
                        true);

        for (XSDSchema xsdSchema : referencedSchemas) {

            IFile referencingFile = WorkingCopyUtil.getFile(xsdSchema);
            String schemaLocation = xsdSchema.getSchemaLocation();
            if (null != schemaLocation) {

                URI importFileUri = URI.createURI(schemaLocation);
                URI absoluteToWsdlFileUri = importFileUri.resolve(wsdlFileUri);
                URI relativeToWsdlFileUri =
                        absoluteToWsdlFileUri.deresolve(wsdlFileUri,
                                false,
                                true,
                                false);
                if (relativeToWsdlFileUri.toString().startsWith("../")) { //$NON-NLS-1$

                    IFile referencedFile =
                            referencingFile.getParent().getFile(new Path(
                                    schemaLocation));
                    /*
                     * if any wsdl invoke references a type using ../ reference
                     * which is outside wsdl folder, it is a problem with bpel
                     * generation.
                     */
                    if (!wsdlFullPath.isPrefixOf(referencedFile.getFullPath())) {

                        addIssue(ACTIVITY_INVOKES_WSDL_WITH_UNSUPPORTED_SCHEMA_REFERENCE,
                                activity,
                                Arrays.asList(new String[] { schemaLocation,
                                        referencingFile.getName() }));
                    }
                }
            }
        }

    }

}
