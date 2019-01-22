/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.customer.api.test.iprocess.amxbpm.conversions.test;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EObject;

import com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionScriptComparisonTest;
import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.iprocess.amxbpm.converter.contributions.JavaScriptLineList;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Package;

/**
 * Test that checks that after IPM to BPM conversion all CUSTAUDIT(xxx) method
 * calls are replaced by their Process.auditLog(auditid) method calls.
 * 
 * @author kthombar
 * @since 14-May-2014
 */
public class IpmBpm08_CustAuditToProcesAuditConversionTest extends
        AbstractIProcessConversionScriptComparisonTest {

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest#getConversionType()
     * 
     * @return
     */
    @Override
    protected CONVERSION_TYPE getConversionType() {

        return CONVERSION_TYPE.IPM_TO_BPM_IMPORT_AND_CONVERT;
    }

    private static final String CUSTOM_AUDIT = "custaudit"; //$NON-NLS-1$

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest#doTestConvertedXpdls(java.util.Collection)
     * 
     * @param convertedXpdls
     */
    @Override
    protected void doTestConvertedXpdls(Collection<IFile> convertedXpdls) {

        super.doTestConvertedXpdls(convertedXpdls);
        /*
         * Check if the converted xpdls have any Array which is not converted to
         * its equivalent ScriptUtil method
         */
        checkCustAuditConversion(convertedXpdls);

    }

    /**
     * @param convertedXpdls
     */
    private void checkCustAuditConversion(Collection<IFile> convertedXpdls) {
        for (IFile iFile : convertedXpdls) {

            WorkingCopy workingCopy = WorkingCopyUtil.getWorkingCopy(iFile);

            if (workingCopy == null) {
                fail("Working copy was found to be null"); //$NON-NLS-1$
            }

            EObject rootElement = workingCopy.getRootElement();

            if (rootElement instanceof Package) {
                Package pkg = (Package) rootElement;
                for (Iterator iterator = pkg.eAllContents(); iterator.hasNext();) {
                    EObject eo = (EObject) iterator.next();
                    if (eo instanceof Expression) {
                        Expression expression = (Expression) eo;

                        if (ScriptGrammarFactory.JAVASCRIPT.equals(expression
                                .getScriptGrammar())) {

                            String text = expression.getText();

                            if (text != null && !text.isEmpty()) {
                                JavaScriptLineList javaScriptLines =
                                        new JavaScriptLineList(text);

                                for (String string : javaScriptLines) {
                                    if (doesStringContainCustAudit(string)) {
                                        fail("custaudit(xxx) was found post conversion. Post conversion all custaudit(xxx) should be replaced by its equivalent Process.auditLog() calls"); //$NON-NLS-1$
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
     * 
     * @param string
     * @return <code>true</code> if the passed string has any Custaudit() usage
     *         in it, else return <code>false</code>
     */
    private boolean doesStringContainCustAudit(String string) {

        boolean isDoubleQuotes = false;
        boolean isSingleQuotes = false;

        String removeSemiColonAndComments = removeSemiColonAndComments(string);

        for (int idx = 0; idx < removeSemiColonAndComments.length(); idx++) {

            char charAt = removeSemiColonAndComments.charAt(idx);

            if (charAt == '\\' && isDoubleQuotes) {

                /* Ignore escaped next character in string. */
                idx++;
            } else if (charAt == '\\' && isSingleQuotes) {

                /* Ignore escaped next character in string. */
                idx++;
            } else if (charAt == '"') {

                if (!isDoubleQuotes) {

                    isDoubleQuotes = true;

                } else {

                    isDoubleQuotes = false;
                }
            } else if (charAt == '\'') {

                if (!isSingleQuotes) {

                    isSingleQuotes = true;

                } else {

                    isSingleQuotes = false;
                }
            }

            if (isDoubleQuotes || isSingleQuotes) {
                continue;
            }

            if (idx + CUSTOM_AUDIT.length() < removeSemiColonAndComments
                    .length()) {
                /* check if the text immediately following idx is custaudit */
                String stringAfterIdx =
                        removeSemiColonAndComments.substring(idx, idx
                                + CUSTOM_AUDIT.length());

                if (stringAfterIdx.equalsIgnoreCase(CUSTOM_AUDIT)) {
                    /* check if custaudit is immediately followed by '(' */
                    if (isCustAuditFollowedByOpenBracket(removeSemiColonAndComments,
                            idx + CUSTOM_AUDIT.length())) {

                        return true;

                    }
                }
            }
        }

        return false;
    }

    /**
     * 
     * @param stringToTest
     *            string to test.
     * @param idx
     *            the index after which we want to check the open brcket.
     * @return <code>true</code> if the character immediately followed by idx is
     *         open bracket '(' , else return <code>false</code>
     */
    private boolean isCustAuditFollowedByOpenBracket(String stringToTest,
            int idx) {

        for (; idx < stringToTest.length(); idx++) {
            char charAt = stringToTest.charAt(idx);
            if (Character.isWhitespace(charAt)) {
                continue;
            } else if (charAt == '(') {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * Removes any semiColon ';' or comments '//' found in the input string
     * which are outside single '' or double "" quotes.
     * 
     * @param string
     * @return String which is free from any semicolons and comments which are
     *         outside of quotes.
     */
    private String removeSemiColonAndComments(String string) {

        boolean isDoubleQuotes = false;
        boolean isSingleQuotes = false;

        for (int idx = 0; idx < string.length(); idx++) {

            char charAt = string.charAt(idx);

            if (charAt == '\\' && isDoubleQuotes) {

                /* Ignore escaped next character in string. */
                idx++;
            } else if (charAt == '\\' && isSingleQuotes) {

                /* Ignore escaped next character in string. */
                idx++;
            } else if (charAt == '"') {

                if (!isDoubleQuotes) {

                    isDoubleQuotes = true;

                } else {

                    isDoubleQuotes = false;
                }
            } else if (charAt == '\'') {

                if (!isSingleQuotes) {

                    isSingleQuotes = true;

                } else {

                    isSingleQuotes = false;
                }
            }

            if (isDoubleQuotes || isSingleQuotes) {
                continue;
            }

            if (charAt == ';') {
                string = string.substring(0, idx).trim();
                break;

            } else if (charAt == '/') {
                if (string.charAt(idx + 1) == '/') {
                    string = string.substring(0, idx).trim();
                    break;
                }
            }
        }
        return string;

    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionScriptComparisonTest#getGoldXpdlResourceInfo()
     * 
     * @return
     */
    @Override
    protected TestResourceInfo getGoldXpdlResourceInfo() {
        return new TestResourceInfo(
                "resources/ConversionTests", "IpmBpm08_CustAuditToProcesAuditConversionTest/Process Packages{processes}/CUSTAUDI_GOLD.xpdl"); //$NON-NLS-1$ //$NON-NLS-2$

    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionScriptComparisonTest#getImportIProcessXpdlResourceInfo()
     * 
     * @return
     */
    @Override
    protected TestResourceInfo getImportIProcessXpdlResourceInfo() {

        return new TestResourceInfo(
                "resources/ConversionTests", "IpmBpm08_CustAuditToProcesAuditConversionTest/ImportIpmXpdls/ScriptsHavingCustAudit.ipmxpdl");//$NON-NLS-1$ //$NON-NLS-2$

    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionScriptComparisonTest#getConvertedTestXpdlResourceInfo()
     * 
     * @return
     */
    @Override
    protected TestResourceInfo getConvertedTestXpdlResourceInfo() {
        return new TestResourceInfo(
                "resources/ConversionTests", "IpmBpm08_CustAuditToProcesAuditConversionTest/Process Packages{processes}/CUSTAUDI.xpdl"); //$NON-NLS-1$ //$NON-NLS-2$

    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIpmBpmTest#getMainImportProjectName()
     * 
     * @return
     */
    @Override
    protected String getMainImportProjectName() {

        return "IpmBpm08_CustAuditToProcesAuditConversionTest"; //$NON-NLS-1$
    }

}