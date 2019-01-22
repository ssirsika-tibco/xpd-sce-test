package com.tibco.xpd.core.createtestwizards.generators;

import com.tibco.xpd.core.createtestwizards.generatordata.*;

public class TestXmlGenerator
{
  protected static String nl;
  public static synchronized TestXmlGenerator create(String lineSeparator)
  {
    nl = lineSeparator;
    TestXmlGenerator result = new TestXmlGenerator();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + NL + "<project name=\"testsuite\" default=\"run\" basedir=\".\">" + NL + "" + NL + "\t<!-- sets the properties eclipse-home, and library-file -->" + NL + "\t<property name=\"plugin-name\" value=\"";
  protected final String TEXT_2 = "\" />" + NL + "\t<property name=\"classname\" value=\"";
  protected final String TEXT_3 = ".AllTests\" />" + NL + "\t" + NL + "\t<!-- The property ${eclipse-home} should be passed into this script -->" + NL + "\t<!-- Set a meaningful default value for when it is not. -->" + NL + "\t<property name=\"eclipse-home\" value=\"${basedir}\\..\\..\" />" + NL + "\t<echo>Eclipse home: ${eclipse-home}</echo>" + NL + "\t" + NL + "\t<property name=\"library-file\"" + NL + "\t\tvalue=\"${eclipse-home}/plugins/org.eclipse.test_3.2.0/library.xml\" />" + NL + "" + NL + "\t<!-- This target holds all initialization code that needs to be done for -->" + NL + "\t<!-- all tests that are to be run. Initialization for individual tests -->" + NL + "\t<!-- should be done within the body of the suite target. -->" + NL + "\t<target name=\"init\">" + NL + "\t\t<property environment=\"env\" />" + NL + "\t\t<tstamp />" + NL + "\t\t<delete>" + NL + "\t\t\t<fileset dir=\"${eclipse-home}\" includes=\"com*.xml\" />" + NL + "\t\t</delete>" + NL + "\t</target>" + NL + "\t" + NL + "\t<target name=\"hudson-output\" if=\"env.WORKSPACE\">" + NL + "\t\t\t<property name=\"results.dir\" value=\"${env.WORKSPACE}/results\"/>" + NL + "\t\t\t<echo>------ SETTING HUDSON OUTPUT --------</echo>" + NL + "\t\t\t<echo>------ ${results.dir}}</echo>" + NL + "\t\t\t<echo>------ --------------------- --------</echo>" + NL + "\t</target>" + NL + "" + NL + "\t<!-- This target defines the tests that need to be run. -->" + NL + "\t<target name=\"suite\" depends=\"hudson-output\">" + NL + "\t\t<property name=\"results.dir\" value=\"${eclipse-home}/results\" />" + NL + "\t\t<property name=\"core-workspace\"" + NL + "\t\t\tvalue=\"${eclipse-home}/ipm-workspace\" />" + NL + "\t\t<delete dir=\"${core-workspace}\" quiet=\"true\" />" + NL + "\t\t<ant target=\"ui-test\" antfile=\"${library-file}\"" + NL + "\t\t\tdir=\"${eclipse-home}\">" + NL + "\t\t\t<property name=\"data-dir\" value=\"${core-workspace}\" />" + NL + "\t\t\t<property name=\"plugin-name\" value=\"${plugin-name}\" />" + NL + "\t\t\t<property name=\"classname\" value=\"${classname}\" />" + NL + "\t\t\t<property name=\"junit-report-output\" value=\"${results.dir}\"/>" + NL + "\t\t</ant>" + NL + "\t</target>" + NL + "" + NL + "\t<!-- This target holds code to cleanup the testing environment after -->" + NL + "\t<!-- after all of the tests have been run. You can use this target to -->" + NL + "\t<!-- delete temporary files that have been created. -->" + NL + "\t<target name=\"cleanup\"></target>" + NL + "" + NL + "\t<!-- This target runs the test suite. Any actions that need to happen -->" + NL + "\t<!-- after all the tests have been run should go here. -->" + NL + "\t<target name=\"run\" depends=\"init,suite,cleanup\">" + NL + "\t\t<ant target=\"collect\" antfile=\"${library-file}\"" + NL + "\t\t\tdir=\"${eclipse-home}\">" + NL + "\t\t\t<property name=\"includes\" value=\"com*.xml\" />" + NL + "\t\t\t<property name=\"output-file\" value=\"${plugin-name}.xml\" />" + NL + "\t\t</ant>" + NL + "\t</target>" + NL + "</project>";
  protected final String TEXT_4 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     TestXmlGeneratorData data = (TestXmlGeneratorData)argument; 
    stringBuffer.append(TEXT_1);
    stringBuffer.append(data.testPluginName);
    stringBuffer.append(TEXT_2);
    stringBuffer.append(data.testPluginName);
    stringBuffer.append(TEXT_3);
    stringBuffer.append(TEXT_4);
    return stringBuffer.toString();
  }
}
