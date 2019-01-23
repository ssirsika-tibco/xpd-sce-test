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
import com.tibco.xpd.iprocess.amxbpm.converter.contributions.ArrayToScriptConverter;
import com.tibco.xpd.iprocess.amxbpm.converter.contributions.JavaScriptLineList;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Package;

/**
 * Test that checks that after IPM to BPM conversion there are no arrays present
 * in the Scripts.
 * 
 * @author kthombar
 * @since 14-May-2014
 */
public class IpmBpm07_ArrayToScriptUtilConversionTest extends
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
        checkArrayConversion(convertedXpdls);

        /*
         * Check is the Converter Class "ArrayToScriptConverter" converts the
         * Arrays as expected.
         */
        doTestArrayToScriptConverterClass();

    }

    /**
     * Tests if the {@link ArrayToScriptConverter} class converts the all the
     * arrays appropriately to their respective ScriptUtil methods.
     */
    @SuppressWarnings("nls")
    private void doTestArrayToScriptConverterClass() {

        String inputString =
                "Array1[a+b]+ '//Not a comment' + MyArray[20] ==c;//Comment";

        String expectedOutput =
                "ScriptUtil.getArrayElement(Array1 , a+b)+ '//Not a comment' + ScriptUtil.getArrayElement(MyArray , 20) ==c;//Comment";

        ArrayToScriptConverter converter =
                new ArrayToScriptConverter(inputString);

        assertEquals("Arrays were not converted corretly to their equivalent ScriptUtil methods",
                expectedOutput,
                converter.getConvertedString());

        /* Test 2 only comments */

        inputString = "//if (LINETYPE [ LOOPCOUNT ] == null)";

        expectedOutput = "//if (LINETYPE [ LOOPCOUNT ] == null)";

        converter = new ArrayToScriptConverter(inputString);

        assertEquals("Arrays were not converted corretly to their equivalent ScriptUtil methods",
                expectedOutput,
                converter.getConvertedString());

        /* Test 3 siemens example */

        inputString =
                "\"LINEALRT[0] from reset timer sub is: \" + IpeScriptUtil.STR(LINEALRT [ 0 ], 0);";

        expectedOutput =
                "\"LINEALRT[0] from reset timer sub is: \" + IpeScriptUtil.STR(ScriptUtil.getArrayElement(LINEALRT , 0), 0);";

        converter = new ArrayToScriptConverter(inputString);

        assertEquals("Arrays were not converted corretly to their equivalent ScriptUtil methods",
                expectedOutput,
                converter.getConvertedString());

        /* Test 4 siemens example */

        inputString =
                "\"INST_TM[0] value:\" + IpeScriptUtil.TIMESTR(INST_TM [ 0 ]) + \" INST_DT[0] value :\" + IpeScriptUtil.DATESTR(INST_DT [ 0 ]);";

        expectedOutput =
                "\"INST_TM[0] value:\" + IpeScriptUtil.TIMESTR(ScriptUtil.getArrayElement(INST_TM , 0)) + \" INST_DT[0] value :\" + IpeScriptUtil.DATESTR(ScriptUtil.getArrayElement(INST_DT , 0));";

        converter = new ArrayToScriptConverter(inputString);

        assertEquals("Arrays were not converted corretly to their equivalent ScriptUtil methods",
                expectedOutput,
                converter.getConvertedString());

        /* Test 5 */

        inputString = "LINETYPE [ LOOPCOUNT ] = null;";

        expectedOutput =
                "ScriptUtil.setArrayElement(LINETYPE , LOOPCOUNT , null);";

        converter = new ArrayToScriptConverter(inputString);

        assertEquals("Arrays were not converted corretly to their equivalent ScriptUtil methods",
                expectedOutput,
                converter.getConvertedString());

        /* Test 6 */
        inputString =
                "if (LINE1TYPE == LINETYPE [ LOOPCOUNT ] && LINE1DATE == INST_DT [ LOOPCOUNT ] && LINE1TIME == INST_TM [ LOOPCOUNT ])";

        expectedOutput =
                "if (LINE1TYPE == ScriptUtil.getArrayElement(LINETYPE , LOOPCOUNT) && LINE1DATE == ScriptUtil.getArrayElement(INST_DT , LOOPCOUNT) && LINE1TIME == ScriptUtil.getArrayElement(INST_TM , LOOPCOUNT))";

        converter = new ArrayToScriptConverter(inputString);

        assertEquals("Arrays were not converted corretly to their equivalent ScriptUtil methods",
                expectedOutput,
                converter.getConvertedString());

        /* Test 7 */

        inputString = "if (LINETYPE [ LOOPCOUNT ] == null)";

        expectedOutput =
                "if (ScriptUtil.getArrayElement(LINETYPE , LOOPCOUNT) == null)";

        converter = new ArrayToScriptConverter(inputString);

        assertEquals("Arrays were not converted corretly to their equivalent ScriptUtil methods",
                expectedOutput,
                converter.getConvertedString());

        /* Test 8 */

        inputString =
                "if (LINE1TYPE == LINETYPE [ LOOPCOUNT ] && LINE1DATE == INST_DT [ LOOPCOUNT ] && LINE1TIME == INST_TM [ LOOPCOUNT ])";

        expectedOutput =
                "if (LINE1TYPE == ScriptUtil.getArrayElement(LINETYPE , LOOPCOUNT) && LINE1DATE == ScriptUtil.getArrayElement(INST_DT , LOOPCOUNT) && LINE1TIME == ScriptUtil.getArrayElement(INST_TM , LOOPCOUNT))";

        converter = new ArrayToScriptConverter(inputString);

        assertEquals("Arrays were not converted corretly to their equivalent ScriptUtil methods",
                expectedOutput,
                converter.getConvertedString());

        /* Test 9 */

        inputString = "IpeScriptUtil.STR(LINEALRT [ 1 ], 0);";

        expectedOutput =
                "IpeScriptUtil.STR(ScriptUtil.getArrayElement(LINEALRT , 1), 0);";

        converter = new ArrayToScriptConverter(inputString);

        assertEquals("Arrays were not converted corretly to their equivalent ScriptUtil methods",
                expectedOutput,
                converter.getConvertedString());

        /* Test 10 */

        inputString =
                "L1_WAIT_DATE = IpeScriptUtil.CALCDATE(INST_DT [ 1 ], 4, 0, 0, 0);";

        expectedOutput =
                "L1_WAIT_DATE = IpeScriptUtil.CALCDATE(ScriptUtil.getArrayElement(INST_DT , 1), 4, 0, 0, 0);";

        converter = new ArrayToScriptConverter(inputString);

        assertEquals("Arrays were not converted corretly to their equivalent ScriptUtil methods",
                expectedOutput,
                converter.getConvertedString());

        /* Test 11 */

        inputString =
                "ARR[ A + B + ETC] = ARR2[ methodThatReturnsInt(x,y,z) + 1];";

        expectedOutput =
                "ScriptUtil.setArrayElement(ARR , A + B + ETC , ScriptUtil.getArrayElement(ARR2 , methodThatReturnsInt(x,y,z) + 1));";

        converter = new ArrayToScriptConverter(inputString);

        assertEquals("Arrays were not converted corretly to their equivalent ScriptUtil methods",
                expectedOutput,
                converter.getConvertedString());

        /* Test 12 */

        inputString = "ARR[ ARR2[ IDX ] ] = ARR3[ ARR2[IDX + 1]];";

        expectedOutput =
                "ScriptUtil.setArrayElement(ARR , ScriptUtil.getArrayElement(ARR2 , IDX) , ScriptUtil.getArrayElement(ARR3 , ScriptUtil.getArrayElement(ARR2 , IDX + 1)));";

        converter = new ArrayToScriptConverter(inputString);

        assertEquals("Arrays were not converted corretly to their equivalent ScriptUtil methods",
                expectedOutput,
                converter.getConvertedString());

        /* Test 13 */

        inputString =
                "ARR[ A + B + ARR2[ IDX + A + B ] ] = ARR3[ ARR2[IDX + 1] + A + B ];";

        expectedOutput =
                "ScriptUtil.setArrayElement(ARR , A + B + ScriptUtil.getArrayElement(ARR2 , IDX + A + B) , ScriptUtil.getArrayElement(ARR3 , ScriptUtil.getArrayElement(ARR2 , IDX + 1) + A + B));";

        converter = new ArrayToScriptConverter(inputString);

        assertEquals("Arrays were not converted corretly to their equivalent ScriptUtil methods",
                expectedOutput,
                converter.getConvertedString());

        /* Test 14 */

        inputString = "Method(ARR3[ ARR2[IDX + 1]], ARR3[ ARR2[IDX + 2]]);";

        expectedOutput =
                "Method(ScriptUtil.getArrayElement(ARR3 , ScriptUtil.getArrayElement(ARR2 , IDX + 1)), ScriptUtil.getArrayElement(ARR3 , ScriptUtil.getArrayElement(ARR2 , IDX + 2)));";

        converter = new ArrayToScriptConverter(inputString);

        assertEquals("Arrays were not converted corretly to their equivalent ScriptUtil methods",
                expectedOutput,
                converter.getConvertedString());

        /*
         * Now some tests with no spacing before the array names.
         */
        /* Test 15 */
        inputString = "ARR3[ARR3[10]] = a;";

        expectedOutput =
                "ScriptUtil.setArrayElement(ARR3 , ScriptUtil.getArrayElement(ARR3 , 10) , a);";

        converter = new ArrayToScriptConverter(inputString);

        assertEquals("Arrays were not converted corretly to their equivalent ScriptUtil methods",
                expectedOutput,
                converter.getConvertedString());

        /* Test 16 */

        inputString = "ARR3[ 20 ] =ARR2[ count ];";

        expectedOutput =
                "ScriptUtil.setArrayElement(ARR3 , 20 , ScriptUtil.getArrayElement(ARR2 , count));";

        converter = new ArrayToScriptConverter(inputString);

        assertEquals("Arrays were not converted corretly to their equivalent ScriptUtil methods",
                expectedOutput,
                converter.getConvertedString());

    }

    /**
     * @param convertedXpdls
     */
    private void checkArrayConversion(Collection<IFile> convertedXpdls) {
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

                                    if (doesStringConatinArray(string)) {
                                        /*
                                         * Fail the test if there is any array
                                         * usage in it!
                                         */
                                        fail("Array usage was found in converted String:- " //$NON-NLS-1$
                                                + string);
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
     * @return <code>true</code> if the passed string has any array usage in it,
     *         else return <code>false</code>
     */
    private boolean doesStringConatinArray(String string) {

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

            if (charAt == '[' || charAt == ']') {

                return true;
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
                "resources/ConversionTests", "IpmBpm07_ArrayToScriptUtilConversionTest/Process Packages{processes}/ARRAYS_GOLD.xpdl"); //$NON-NLS-1$ //$NON-NLS-2$

    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionScriptComparisonTest#getImportIProcessXpdlResourceInfo()
     * 
     * @return
     */
    @Override
    protected TestResourceInfo getImportIProcessXpdlResourceInfo() {

        return new TestResourceInfo(
                "resources/ConversionTests", "IpmBpm07_ArrayToScriptUtilConversionTest/ImportIpmXpdls/arrays.ipmxpdl");//$NON-NLS-1$ //$NON-NLS-2$

    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionScriptComparisonTest#getConvertedTestXpdlResourceInfo()
     * 
     * @return
     */
    @Override
    protected TestResourceInfo getConvertedTestXpdlResourceInfo() {
        return new TestResourceInfo(
                "resources/ConversionTests", "IpmBpm07_ArrayToScriptUtilConversionTest/Process Packages{processes}/ARRAYS.xpdl"); //$NON-NLS-1$ //$NON-NLS-2$

    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIpmBpmTest#getMainImportProjectName()
     * 
     * @return
     */
    @Override
    protected String getMainImportProjectName() {

        return "IpmBpm07_ArrayToScriptUtilConversionTest"; //$NON-NLS-1$
    }

}
