package com.tibco.xpd.core.createtestwizards.generators;

import com.tibco.xpd.core.createtestwizards.generatordata.*;

public class AllTestsClassGenerator
{
  protected static String nl;
  public static synchronized AllTestsClassGenerator create(String lineSeparator)
  {
    nl = lineSeparator;
    AllTestsClassGenerator result = new AllTestsClassGenerator();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "/**" + NL + " * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved." + NL + " */";
  protected final String TEXT_2 = NL + "package ";
  protected final String TEXT_3 = ";" + NL + "" + NL + "import junit.framework.Test;" + NL + "import junit.framework.TestSuite;" + NL + "" + NL + "public class AllTests {" + NL + "" + NL + "\tpublic static Test suite() {" + NL + "\t\tTestSuite suite = new TestSuite(" + NL + "\t\t\t\t\"Test for ";
  protected final String TEXT_4 = "\");" + NL + "\t\t// $JUnit-BEGIN$";
  protected final String TEXT_5 = NL + "            suite.addTestSuite(";
  protected final String TEXT_6 = ".class);";
  protected final String TEXT_7 = NL + "\t\t// $JUnit-END$" + NL + "\t\treturn suite;" + NL + "\t}" + NL + "}";
  protected final String TEXT_8 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
     AllTestsClassGeneratorData data = (AllTestsClassGeneratorData)argument; 
    stringBuffer.append(TEXT_2);
    stringBuffer.append(data.testPackageId);
    stringBuffer.append(TEXT_3);
    stringBuffer.append(data.testPackageId);
    stringBuffer.append(TEXT_4);
     for (String testClassName : data.testClassNames) { 
    stringBuffer.append(TEXT_5);
    stringBuffer.append(testClassName);
    stringBuffer.append(TEXT_6);
     } 
    stringBuffer.append(TEXT_7);
    stringBuffer.append(TEXT_8);
    return stringBuffer.toString();
  }
}
