package com.tibco.xpd.script.sourceviewer.internal.viewer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.text.edits.TextEdit;
import org.eclipse.wst.javascript.core.internal.contenttype.ContentTypeIdForJavaScript;
import org.eclipse.wst.sse.core.internal.encoding.CommonEncodingPreferenceNames;
import org.eclipse.wst.sse.core.internal.encoding.ContentBasedPreferenceGateway;

import com.tibco.xpd.script.sourceviewer.client.CommandProvider;
import com.tibco.xpd.script.sourceviewer.client.IScriptCompiler;
import com.tibco.xpd.script.sourceviewer.internal.util.LineDelimeterUtil;
import com.tibco.xpd.script.sourceviewer.internal.util.Logger;
import com.tibco.xpd.script.sourceviewer.viewer.ScriptSourceViewer;

public class SaveUtil {

    public synchronized static void compileScript(
            ScriptSourceViewer jScriptSourceViewer) {
        SourceViewer sourceViewer = jScriptSourceViewer.getSourceViewer();
        if (sourceViewer == null) {
            return;
        }
        IDocument scriptDoc = sourceViewer.getDocument();
        try {
            LineDelimeterUtil.convertLineDelimiters(scriptDoc);
            IScriptCompiler scriptCompiler = jScriptSourceViewer
                    .getScriptCompiler();
            if (scriptCompiler != null) {
                scriptCompiler.compileScript(scriptDoc.get());
                // compileScript(scriptDoc, inputDetails);
            }
        } catch (CoreException e1) {
            e1.printStackTrace();
        }
    }

    public synchronized static void saveScript(
            ScriptSourceViewer jScriptSourceViewer) {
        SourceViewer sourceViewer = jScriptSourceViewer.getSourceViewer();
        if (sourceViewer == null) {
            return;
        }
        IDocument scriptDoc = sourceViewer.getDocument();
        CommandProvider commandProvider = jScriptSourceViewer
                .getCommandProvider();
        if (commandProvider != null) {
            commandProvider.executeSaveCommand(scriptDoc);
        }
    }

    public synchronized static void compileScript(IDocument document) {
        // try {
        // String modifiedScript = document.get();
        // EObject eObject = inputDetails.getEObject();
        // WorkingCopy xpdlWorkingCopyFor = WorkingCopyUtil
        // .getWorkingCopyFor(eObject);
        // IResource eclipseResource = xpdlWorkingCopyFor
        // .getEclipseResources().get(0);
        // String activityId = inputDetails.getScriptContainerId();
        // deleteValidationMarker(eclipseResource, activityId);
        // convertLineDelimiters(document);
        // isScriptValid(modifiedScript, inputDetails, eclipseResource);
        //
        // } catch (CoreException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
    }

    private static void convertLineDelimiters(IDocument document)
            throws CoreException {
        String contentTypeId = ContentTypeIdForJavaScript.ContentTypeID_JAVASCRIPT;
        String endOfLineCode = ContentBasedPreferenceGateway
                .getPreferencesString(contentTypeId,
                        CommonEncodingPreferenceNames.END_OF_LINE_CODE);
        // endOfLineCode == null means the content type does not support this
        // function (e.g. DTD)
        // endOfLineCode == "" means no translation
        if (endOfLineCode != null && endOfLineCode.length() > 0) {
            String lineDelimiterToUse = System.getProperty("line.separator"); //$NON-NLS-1$
            if (endOfLineCode.equals(CommonEncodingPreferenceNames.CR))
                lineDelimiterToUse = CommonEncodingPreferenceNames.STRING_CR;
            else if (endOfLineCode.equals(CommonEncodingPreferenceNames.LF))
                lineDelimiterToUse = CommonEncodingPreferenceNames.STRING_LF;
            else if (endOfLineCode.equals(CommonEncodingPreferenceNames.CRLF))
                lineDelimiterToUse = CommonEncodingPreferenceNames.STRING_CRLF;

            TextEdit multiTextEdit = new MultiTextEdit();
            int lineCount = document.getNumberOfLines();
            try {
                for (int i = 0; i < lineCount; i++) {
                    IRegion lineInfo = document.getLineInformation(i);
                    int lineStartOffset = lineInfo.getOffset();
                    int lineLength = lineInfo.getLength();
                    int lineEndOffset = lineStartOffset + lineLength;

                    if (i < lineCount - 1) {
                        String currentLineDelimiter = document
                                .getLineDelimiter(i);
                        if (currentLineDelimiter != null
                                && currentLineDelimiter
                                        .compareTo(lineDelimiterToUse) != 0)
                            multiTextEdit.addChild(new ReplaceEdit(
                                    lineEndOffset, currentLineDelimiter
                                            .length(), lineDelimiterToUse));
                    }
                }

                if (multiTextEdit.getChildrenSize() > 0)
                    multiTextEdit.apply(document);
            } catch (BadLocationException e) {
                // log or now, unless we find reason not to
                Logger.log(Logger.INFO, e.getMessage());
            }
        }
    }

    private static void createValidationMarker(IResource resource,
            String genericErrorMsg, String antlrErrorMsg, String activityId,
            int lineNumber) {
        // try {
        // String errorMsg;
        // if (antlrErrorMsg != null) {
        // errorMsg = genericErrorMsg + ", " + antlrErrorMsg; //$NON-NLS-1$
        // } else {
        // errorMsg = genericErrorMsg;
        // }
        // IMarker marker = resource.createMarker(Consts.MARKER_TYPE);
        // marker.setAttribute(IMarker.MESSAGE, errorMsg);
        // marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
        // marker.setAttribute(IMarker.LOCATION, resource
        // .getProjectRelativePath().toString());
        // String projName = resource.getProject().getName();
        // marker.setAttribute(Consts.PROJECT_NAME_ATTRIB, projName);
        // marker.setAttribute(Consts.SCRIPT_CONTAINER_ID, activityId);
        // marker.setAttribute(Consts.LINE_NUMBER, lineNumber);
        // marker.setAttribute(Consts.ERROR_MESSAGE, antlrErrorMsg);
        // } catch (CoreException e1) {
        // e1.printStackTrace();
        // System.out.println(Messages.SaveUtil_ExceptionWhileCreatingMarker
        // + resource.getFullPath());
        // }
    }

    private static void deleteValidationMarker(IResource resource,
            String activityId) {
        // ArrayList<IMarker> markerList = getValidationMarker(resource,
        // activityId);
        // for (IMarker marker : markerList) {
        // try {
        // marker.delete();
        // } catch (CoreException e) {
        // e.printStackTrace();
        // }
        // }
    }

    public static ArrayList<IMarker> getValidationMarker(IResource resource,
            String activityId) {
        IMarker[] markerArr;
        ArrayList<IMarker> markerList = new ArrayList<IMarker>();
        // try {
        // markerArr = resource.findMarkers(Consts.MARKER_TYPE, true,
        // IResource.DEPTH_INFINITE);
        // if (markerArr != null || markerArr.length > 0) {
        // for (int index = 0; index < markerArr.length; index++) {
        // IMarker tempMarker = markerArr[index];
        // if (tempMarker != null && tempMarker.exists()) {
        // String markerActivityId = (String) tempMarker
        // .getAttribute(Consts.SCRIPT_CONTAINER_ID);
        // if (activityId.equals(markerActivityId)) {
        // markerList.add(tempMarker);
        // }
        // }
        // }
        // }
        // } catch (CoreException e) {
        // e.printStackTrace();
        // }
        return markerList;
    }

    private static boolean isScriptValid(String scriptText,
            IResource eclipseResource) {
        // String packageName = input.getPackageName();
        // String processName = input.getProcessName();
        // String activityName = input.getScriptContainerName();
        // String activityId = input.getScriptContainerId();
        // String genericMsg;
        // String antlrErrorMsg;
        // int line;
        // int column;
        // try {
        // StringReader reader = new StringReader(scriptText);
        // // Create a scanner that reads from the input stream passed to us
        // JScriptLexer lexer = new JScriptLexer(reader);
        // // Create a parser that reads from the scanner
        // JScriptParser parser = new JScriptParser(lexer);
        // // start parsing at the compilationUnit rule
        // SymbolTable symbolTable = getSymbolTable();
        // parser.setSymbolTable(symbolTable);
        // parser.compilationUnit();
        // List<ErrorMessage> errorList = parser.getErrorList();
        // for (ErrorMessage message : errorList) {
        // line = message.getLineNumber();
        // column = message.getColumnNumber();
        // genericMsg = packageName + "-->" + processName + "-->"
        // + activityName + "-->" + " At line:" + line
        // + " and column:" + column;
        // antlrErrorMsg = message.getErrorMessage();
        // createValidationMarker(eclipseResource, genericMsg,
        // antlrErrorMsg, activityId, line);
        // System.out.println("The error message is "+message.toString());
        // }
        // Set<String> identifierNames =
        // parser.getSymbolTable().getLocalVariableNames();
        // for (String identifier : identifierNames) {
        // System.out.println("The name of identifier is "+identifier);
        // }
        // JScriptTreeParser treeParser = new JScriptTreeParser();
        // treeParser.compilationUnit(parser.getAST());
        // } catch (Exception e) {
        // if (e instanceof RecognitionException) {
        // RecognitionException mte = (RecognitionException) e;
        // line = mte.getLine();
        // column = mte.getColumn();
        // antlrErrorMsg = mte.getMessage();
        // genericMsg = packageName + "-->" + processName + "-->"
        // + activityName + "-->" + " At line:" + line
        // + " and column:" + column;
        // /*
        // * if (msg != null) { specificErroMsg = ", the problem is " +
        // * msg; } else { specificErroMsg = ", there is a problem"; }
        // */
        // createValidationMarker(eclipseResource, genericMsg,
        // antlrErrorMsg, activityId, line);
        // } else {
        // e.printStackTrace(); // so we can get stack trace
        // }
        // return false;
        // }
        return true;
    }

    private static Map<String, String> getProcessFields() {
        Map<String, String> processDataFields = new HashMap<String, String>();
        processDataFields.put("data1", "String"); //$NON-NLS-1$ //$NON-NLS-2$
        processDataFields.put("data2", "String"); //$NON-NLS-1$ //$NON-NLS-2$
        processDataFields.put("data3", "String"); //$NON-NLS-1$ //$NON-NLS-2$
        return processDataFields;
    }

    // private static SymbolTable getSymbolTable(){
    // SymbolTable table = new SymbolTable();
    // table.setProcessData(getProcessFields());
    // return table;
    // }

}
