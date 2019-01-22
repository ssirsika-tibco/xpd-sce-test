/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.mappings.validation.test.datamapper;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.datamapper.scripts.DataMapperJavascriptGenerator;
import com.tibco.xpd.datamapper.scripts.DataMapperMappingScriptsToJavaScriptContribution;
import com.tibco.xpd.n2.test.core.AbstractN2BaseResourceTest;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Process data mapper script generation test, looks thru test resource set for
 * XPDL files.
 * <p>
 * For each one of these iterates thru process looking for DataMapper scripts.
 * <p>
 * COnverts each in turn to JavaScript.
 * <p>
 * Compares with the 'GOLD standard' generated script store in the description
 * of the same activity.
 * <p>
 * So all you have to do to create new tests is to create a process with an
 * activity with the datamapper script you want, then manually convert it to
 * JavaScript, copy the result, undo the conversion and the paste the result
 * into description. Then inspect it manually to ensure it's ok as the GOld
 * standard.
 * <li><b>Important !!</b> Note that incase the Process data mapper test fails
 * and we need to update the gold script stored in the activity description we
 * can use
 * {@link DataMapperMappingScriptsToJavaScriptContribution#convertScriptForGrammar(String, String, String, com.tibco.xpd.xpdl2.Expression, String)}
 * which takes the data mapper script abd return the relavent java script.
 * 
 * @author aallway
 * @since 7 May 2015
 */
public abstract class AbstractProcessDataMapperScriptGenerationTest extends
        AbstractN2BaseResourceTest {

    /**
     * ProcessDataMapperScriptGenerationTestsTest
     * 
     * @throws Exception
     */
    public void testProcessDataMapperScriptGenerationTestsTest()
            throws Exception {
        cleanProjectAtEnd = false;
        // Check all files created correctly.
        checkTestFilesCreated();

        TestResourceInfo[] testResources = getTestResources();

        StringBuilder result = new StringBuilder();

        for (TestResourceInfo testResourceInfo : testResources) {
            IFile testFile = testResourceInfo.getTestFile();

            if (testFile.getFileExtension().equalsIgnoreCase("xpdl")) { //$NON-NLS-1$
                Package pkg =
                        (Package) WorkingCopyUtil.getWorkingCopy(testFile)
                                .getRootElement();

                doTestPackage(pkg, result);

            }

        }

        if (result.length() > 0) {
            fail(result.toString());
        }

        return;
    }

    /**
     * Test the scripts in the process in the package.
     * 
     * @param pkg
     * @param result
     *            <code>empty</code> on success, else error string on error.
     */
    private void doTestPackage(Package pkg, StringBuilder result) {

        Set<Activity> alreadyDoneActivity = new HashSet<Activity>();

        TreeIterator<EObject> eAllContents = pkg.eAllContents();

        for (Iterator iterator = eAllContents; iterator.hasNext();) {
            Object eo = iterator.next();

            if (eo instanceof ScriptDataMapper) {
                ScriptDataMapper scriptDataMapper = (ScriptDataMapper) eo;

                DataMapperJavascriptGenerator dataMapperJavascriptGenerator =
                        new DataMapperJavascriptGenerator();

                /**
                 * Get the script context for the data mapper element
                 */

                /**
                 * Get the expected javaScript result text from activity
                 * description
                 */
                Activity activity =
                        (Activity) Xpdl2ModelUtil.getAncestor(scriptDataMapper,
                                Activity.class);
                if (activity == null) {
                    logError(result,
                            scriptDataMapper,
                            "- ScriptDataMapper element is not in an xpdl2:Activity"); //$NON-NLS-1$

                } else if (alreadyDoneActivity.contains(activity)) {
                    logError(result,
                            scriptDataMapper,
                            "- Activity has more than one Data Mapper script which is not permitted for this test (%s)", Xpdl2ModelUtil.getDisplayNameOrName(activity)); //$NON-NLS-1$

                } else {
                    alreadyDoneActivity.add(activity);

                    if (activity.getDescription() != null) {
                        String goldScript =
                                activity.getDescription().getValue();
                        String originalGoldScript =
                                (goldScript == null ? "" : goldScript); //$NON-NLS-1$

                        /* Replace whitespace - don't care so much about that. */
                        goldScript = originalGoldScript.replaceAll("\\s", ""); //$NON-NLS-1$ //$NON-NLS-2$

                        if (goldScript.length() == 0) {
                            logError(result,
                                    activity,
                                    "- No 'gold script' defined int description for host activity"); //$NON-NLS-1$

                        } else {
                            /**
                             * Convert to javascript
                             */
                            String genScript =
                                    dataMapperJavascriptGenerator
                                            .convertMappingsToJavascript(scriptDataMapper);

                            String originalGenScript =
                                    (genScript == null ? "" : genScript); //$NON-NLS-1$

                            /* Ignore whitespace. */
                            genScript = originalGenScript.replaceAll("\\s", ""); //$NON-NLS-1$ //$NON-NLS-2$

                            if (!genScript.equals(goldScript)) {
                                String differences =
                                        differences(originalGoldScript,
                                                originalGenScript);

                                logError(result,
                                        scriptDataMapper,
                                        "Generated script does not match gold standard script...\n%s\nGenerated Script:\n~~~~~~~~~~~~~~~~~~\n%s\n~~~~~~~~~~~~~~~~~~\nGold Script:\n~~~~~~~~~~~~~~~~~~\n%s\n~~~~~~~~~~~~~~~~~~\n\n", //$NON-NLS-1$
                                        differences,
                                        originalGenScript,
                                        originalGoldScript);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 
     * @param gold
     * @param test
     * @return String detailing the diffs between 2 lines.
     */
    private String differences(String gold, String test) {
        StringBuilder result = new StringBuilder();

        String[] goldLines = gold.split("\n"); //$NON-NLS-1$

        String[] testLines = test.split("\n"); //$NON-NLS-1$

        int i = 0;
        for (; i < goldLines.length; i++) {

            if (i >= testLines.length) {
                if (!goldLines[i].trim().isEmpty()) {
                    result.append(String
                            .format("Test File has not content on or after gold line %d: %s\n", //$NON-NLS-1$
                                    i + 1,
                                    goldLines[i]));
                    break;
                }

            } else {
                if (!goldLines[i]
                        .replaceAll("\\s", "").equals(testLines[i].replaceAll("\\s", ""))) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$//$NON-NLS-4$
                    result.append(String
                            .format("Gold and Test File Line %d Differ:\n  Gold: %s\n  Test: %s\n", //$NON-NLS-1$
                                    i + 1,
                                    goldLines[i],
                                    testLines[i]));
                    break;
                }
            }

        }

        if (result.length() == 0) {
            for (; i < testLines.length; i++) {
                if (!testLines[i].trim().isEmpty()) {
                    result.append(String
                            .format("Test File has additional content from test line line %d: %s\n", //$NON-NLS-1$
                                    i + 1,
                                    testLines[i]));
                    break;
                }
            }
        }

        return result.toString();
    }

    /**
     * Add error message to result string
     * 
     * @param result
     * @param contextObject
     * @param message
     * @param args
     */
    void logError(StringBuilder result, EObject contextObject, String message,
            Object... args) {
        result.append(String
                .format("  [%s:%s - %s]\n-----------------------------------------------------------\n", //$NON-NLS-1$
                        Xpdl2ModelUtil
                                .getDisplayNameOrName((NamedElement) Xpdl2ModelUtil
                                        .getAncestor(contextObject,
                                                Process.class)),
                        Xpdl2ModelUtil
                                .getDisplayNameOrName((NamedElement) Xpdl2ModelUtil
                                        .getAncestor(contextObject,
                                                Activity.class)),
                        EcoreUtil.getURI(contextObject).toString()));
        result.append(String.format(message, args));
    }
}
