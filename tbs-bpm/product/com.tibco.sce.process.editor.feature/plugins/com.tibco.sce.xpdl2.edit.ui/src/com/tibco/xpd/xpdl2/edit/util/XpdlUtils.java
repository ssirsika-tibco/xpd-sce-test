package com.tibco.xpd.xpdl2.edit.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;

/**
 * Utility class to process an xpdl file. Various methods to empty tags, remove
 * carriage returns and so on
 * 
 * @author glewis
 * 
 */
public class XpdlUtils {
    
    public static final String XPDL_FILE_EXTENSION = "xpdl"; //$NON-NLS-1$

    /**
     * Gets the contents of the xpdl file
     * 
     * @param file
     * @return
     */
    public static String getXPDLContents(File file, String charEncoding)
            throws IOException {
        String stringContents = ""; //$NON-NLS-1$

        StringBuffer sb = new StringBuffer();
        char[] buffer = new char[4000];
        int len;

        InputStreamReader reader = null;
        if (charEncoding == null) {
            reader = new InputStreamReader(new FileInputStream(file));
        } else {
            reader =
                    new InputStreamReader(new FileInputStream(file), Charset
                            .forName(charEncoding));
        }

        try {
            while ((len = reader.read(buffer)) >= 0) {
                sb.append(buffer, 0, len);
            }
        } finally {
            reader.close();
        }
        stringContents = sb.toString();

        return stringContents;
    }

    /**
     * Gets the contents of the xpdl file
     * 
     * @param file
     * @return
     */
    public static String getXPDLContents(IFile file, String charEncoding)
            throws IOException {
        String stringContents = ""; //$NON-NLS-1$

        StringBuffer sb = new StringBuffer();
        char[] buffer = new char[4000];
        int len;

        InputStreamReader reader = null;
        try {
            if (charEncoding == null) {
                reader =
                        new InputStreamReader(file.getContents(), Charset
                                .forName(charEncoding));
            } else {
                reader =
                        new InputStreamReader(file.getContents(), Charset
                                .forName(charEncoding));
            }

            while ((len = reader.read(buffer)) >= 0) {
                sb.append(buffer, 0, len);
            }
        } catch (CoreException e) {

        } finally {
            reader.close();
        }
        stringContents = sb.toString();

        return stringContents;
    }

    /**
     * Copy new contents back to the xpdl file
     * 
     * @param file
     * @param xpdlContents
     */
    public static void setFileContents(File file, String xpdlContents,
            String charEncoding) {
        try {
            OutputStreamWriter writer = null;
            if (charEncoding == null) {
                writer = new OutputStreamWriter(new FileOutputStream(file));
            } else {
                writer =
                        new OutputStreamWriter(new FileOutputStream(file),
                                Charset.forName(charEncoding));
            }

            writer.write(xpdlContents);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            // ignore
        }
    }

    public static void setXPDLContents(IFile file, String xpdlContents,
            String charEncoding) {
        try {

            byte[] bytes;

            if (charEncoding == null) {
                bytes = xpdlContents.getBytes();

            } else {
                bytes = xpdlContents.getBytes(charEncoding);
            }

            try {
                file.setContents(new ByteArrayInputStream(bytes),
                        IFile.FORCE,
                        new NullProgressMonitor());

            } catch (CoreException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } catch (IOException e) {
            // ignore
        }
    }

    /**
     * Actually removes the runtime alias tag if it is empty.
     * 
     * @param xpdlContents
     * @return
     */
    public static String removeEmptyRunTimeAlias(String xpdlContents) {
        CharSequence inputStr = xpdlContents;
        String patternStr = "&lt;RunTimeAlias&gt;&lt;/RunTimeAlias&gt;"; //$NON-NLS-1$       

        // Compile regular expression
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(inputStr);

        // Replace all occurrences of pattern in input
        StringBuffer buf = new StringBuffer();
        boolean found = false;
        while ((found = matcher.find())) {
            // Get the match result
            String replaceStr = matcher.group();

            replaceStr = replaceStr.replace(patternStr, ""); //$NON-NLS-1$

            // Insert replacement
            matcher.appendReplacement(buf, replaceStr);
        }
        matcher.appendTail(buf);

        // Get result
        xpdlContents = buf.toString();

        return xpdlContents;
    }

    /**
     * Removes all carriage returns in the web service xpdl section of xpdl file
     * 
     * @param xpdlContents
     * @return
     */
    public static String removeWSCarriageReturns(String xpdlContents) {
        String startPatternStr = "<sw:EAIText>"; //$NON-NLS-1$
        String endPatternStr = "</sw:EAIText>"; //$NON-NLS-1$        

        int index = xpdlContents.indexOf(startPatternStr);
        while (index != -1) {
            int endIndex = xpdlContents.indexOf(endPatternStr, index);
            if (endIndex != -1) {
                String originalSubstring =
                        xpdlContents.substring(index, endIndex
                                + endPatternStr.length());
                int wsIndex =
                        originalSubstring.indexOf("Web_Services_EAI_Plugin"); //$NON-NLS-1$        
                if (wsIndex != -1) {
                    String parsedSubstring = originalSubstring;
                    parsedSubstring = parsedSubstring.replace("\r", ""); //$NON-NLS-1$ //$NON-NLS-2$    
                    parsedSubstring = parsedSubstring.replace("\n", ""); //$NON-NLS-1$ //$NON-NLS-2$
                    parsedSubstring = parsedSubstring.replace("&#13;", ""); //$NON-NLS-1$ //$NON-NLS-2$
                    xpdlContents =
                            xpdlContents.replace(originalSubstring,
                                    parsedSubstring);
                    endIndex = index + 1;
                }
            }
            index = xpdlContents.indexOf(startPatternStr, endIndex + 1);
        }

        return xpdlContents;
    }

    /**
     * Removes all carriage returns in the web service xpdl section of xpdl file
     * 
     * @param xpdlContents
     * @return
     */
    public static String removeBWCarriageReturns(String xpdlContents) {
        String startPatternStr = "<sw:EAIText>"; //$NON-NLS-1$
        String endPatternStr = "/File_Contents"; //$NON-NLS-1$        

        int index = xpdlContents.indexOf(startPatternStr);
        if (index != -1) {
            index = xpdlContents.indexOf("lt;File_Contents&gt", index); //$NON-NLS-1$
            while (index != -1) {
                int endIndex = xpdlContents.indexOf(endPatternStr, index);
                if (endIndex != -1) {
                    String originalSubstring =
                            xpdlContents.substring(index, endIndex
                                    + endPatternStr.length());
                    String parsedSubstring = originalSubstring;
                    parsedSubstring = parsedSubstring.replace("\r", ""); //$NON-NLS-1$ //$NON-NLS-2$    
                    parsedSubstring = parsedSubstring.replace("\n", ""); //$NON-NLS-1$ //$NON-NLS-2$
                    parsedSubstring = parsedSubstring.replace("&#13;", ""); //$NON-NLS-1$ //$NON-NLS-2$
                    xpdlContents =
                            xpdlContents.replace(originalSubstring,
                                    parsedSubstring);
                    endIndex = index + 1;
                }
                index = xpdlContents.indexOf(startPatternStr, endIndex + 1);
                if (index != -1) {
                    index = xpdlContents.indexOf("lt;File_Contents&gt", index); //$NON-NLS-1$
                }
            }
        }

        return xpdlContents;
    }

    /**
     * Actually removes the operation names (with its children attributes) from
     * the xpdl file if there is no port defined for it (basically an interface)
     * 
     * @param xpdlContents
     * @return
     */
    public static String removeInterfaceOperationSelections(String xpdlContents) {
        String startPatternStr = "&lt;Selected_Operation&gt;"; //$NON-NLS-1$
        String endPatternStr = "&lt;/Selected_Operation&gt;"; //$NON-NLS-1$
        String interfacePortPattern = "&lt;Port_Name&gt;&lt;/Port_Name&gt;"; //$NON-NLS-1$

        int index = xpdlContents.indexOf(startPatternStr);
        while (index != -1) {
            int endIndex = xpdlContents.indexOf(endPatternStr, index);
            if (endIndex != -1) {
                String operationContents =
                        xpdlContents.substring(index, endIndex
                                + endPatternStr.length());
                if (operationContents.indexOf(interfacePortPattern) != -1) {
                    xpdlContents = xpdlContents.replace(operationContents, ""); //$NON-NLS-1$
                    index = xpdlContents.indexOf(startPatternStr);
                } else {
                    index = xpdlContents.indexOf(startPatternStr, endIndex);
                }
            } else {
                index = -1;
            }
        }

        return xpdlContents;
    }

    /**
     * Actually removes the security profile tag if it is empty.
     * 
     * @param xpdlContents
     * @return
     */
    public static String changeEmptySecurityProfile(String xpdlContents) {
        CharSequence inputStr = xpdlContents;
        String patternStr = "&lt;SecurityProfile&gt;&lt;/SecurityProfile&gt;"; //$NON-NLS-1$       

        // Compile regular expression
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(inputStr);

        // Replace all occurrences of pattern in input
        StringBuffer buf = new StringBuffer();
        boolean found = false;
        while ((found = matcher.find())) {
            // Get the match result
            String replaceStr = matcher.group();

            replaceStr = replaceStr.replace(patternStr, ""); //$NON-NLS-1$
            //replaceStr = replaceStr.replace(patternStr, "&lt;SecurityProfile&gt;NONE&lt;/SecurityProfile&gt;"); //$NON-NLS-1$            

            // Insert replacement
            matcher.appendReplacement(buf, replaceStr);
        }
        matcher.appendTail(buf);

        // Get result
        xpdlContents = buf.toString();
        return xpdlContents;
    }
    
    public static boolean isSupportedXPDLFile(IResource resource) {
        if (resource instanceof IFile && resource.getName() != null
                && resource.getFileExtension() != null
                && resource.getFileExtension().equals(XPDL_FILE_EXTENSION)) {
            return true;
        }
        return false;
    }
    
}
