package com.tibco.bx.validation.rules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.destinations.GlobalDestinationUtil;
import com.tibco.xpd.n2.resources.util.N2Utils;
import com.tibco.xpd.validation.engine.WorkspaceResourceValidator;
import com.tibco.xpd.validation.provider.IValidationScope;

/**
 * Validate against certain character(s) / character sequences in xpdl file name.
 * 
 * Rule initially added to detect -
 *  (i) '#' character (as lead to DAA generation error) 
 *  (ii) ampersands (which could lead to generated wsdls that could cause DAA generation error)   
 *
 * @author patkinso
 * @since 25 Oct 2012
 */
public class XpdlFileNameRestrictionRule implements WorkspaceResourceValidator {

    private static final String ISSUE_ID = "bx.xpdlFileNameRestrictions"; //$NON-NLS-1$

    private static final Pattern pattern = Pattern
            .compile(N2Utils.REGEX_XPDL_FILENAME_INVALID_SUBSEQUENCES);

    public XpdlFileNameRestrictionRule() {
        // TODO Auto-generated constructor stub
    }

    public void validate(IValidationScope scope, IResource resource) {

        // additional code-level checks to ensure is for (a) bpm dest & (b) is
        // an xpdl file

        boolean isN2Destination =
                GlobalDestinationUtil.isGlobalDestinationEnabled(resource
                        .getProject(), N2Utils.N2_GLOBAL_DESTINATION_ID);

        if (isN2Destination
                && (resource instanceof IFile)
                && (resource.getFileExtension() != null)
                && (resource.getFileExtension()
                        .endsWith(Xpdl2ResourcesConsts.XPDL_EXTENSION))) {

            // search for invalid name parts
            List<String> invalidSubSequences = new ArrayList<String>();

            Matcher matcher = pattern.matcher(resource.getName());
            while (matcher.find()) {
                invalidSubSequences.add(matcher.group());
            }

            // report with all invalid parts found
            if (!invalidSubSequences.isEmpty()) {

                String[] args =
                        { collectionToString(invalidSubSequences, " ") }; //$NON-NLS-1$

                scope.createIssue(ISSUE_ID,
                        resource.getName(),
                        resource.getProjectRelativePath().toString(),
                        Arrays.asList(args));
            }
        }
    }

    /**
     * @param invalidSubSequences
     * @return Single string of concatenated collection String elements
     */
    private String collectionToString(Collection<String> col, String delimiter) {

        StringBuilder sb = new StringBuilder();
        for (String str : col) {
            sb.append(str).append(delimiter);
        }
        sb.setLength(sb.length() - delimiter.length());

        return sb.toString();
    }

    public void setProject(IProject project) {
        // TODO Auto-generated method stub

    }

}
