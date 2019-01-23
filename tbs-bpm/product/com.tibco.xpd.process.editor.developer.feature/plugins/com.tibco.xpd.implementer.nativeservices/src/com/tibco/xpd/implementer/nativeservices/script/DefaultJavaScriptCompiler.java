package com.tibco.xpd.implementer.nativeservices.script;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;

import antlr.RecognitionException;

import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.process.js.parser.antlr.JScriptTreeParser;
import com.tibco.xpd.process.js.parser.validator.ProcessValidationUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.script.model.client.DefaultScriptRelevantData;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.parser.Messages;
import com.tibco.xpd.script.parser.antlr.JScriptLexer;
import com.tibco.xpd.script.parser.antlr.JScriptParser;
import com.tibco.xpd.script.parser.validator.ErrorMessage;
import com.tibco.xpd.script.parser.validator.ISymbolTable;
import com.tibco.xpd.script.parser.validator.IValidationStrategy;
import com.tibco.xpd.script.parser.validator.SymbolTable;
import com.tibco.xpd.script.sourceviewer.client.IScriptCompiler;
import com.tibco.xpd.script.ui.ScriptGrammarContributionsUtil;

public class DefaultJavaScriptCompiler implements IScriptCompiler {

    public static final String CONTEXT_ID =
            "com.tibco.xpd.js.sourceviewer.jsSourceViewer"; //$NON-NLS-1$

    public final static String MARKER_TYPE =
            "com.tibco.xpd.js.sourceviewer.jsValidationProblem"; //$NON-NLS-1$

    public final static String PROJECT_NAME_ATTRIB =
            "com.tibco.xpd.js.sourceviewer.jsProjectName"; //$NON-NLS-1$

    public final static String SCRIPT_CONTAINER_ID =
            "com.tibco.xpd.js.sourceviewer.jsScriptContainerId"; //$NON-NLS-1$

    public static final String LINE_NUMBER =
            "com.tibco.xpd.js.sourceviewer.jsSourceViewerLineNumber"; //$NON-NLS-1$

    public static final String ERROR_MESSAGE =
            "com.tibco.xpd.js.sourceviewer.jsSourceViewerErrorMessage"; //$NON-NLS-1$

    private InputDetails inputDetails;

    public void compileScript(String modifiedScript) {
        try {
            InputDetails inputDetails = getInputDetails();
            if (inputDetails == null) {
                throw new IllegalStateException(
                        "Instance of InputDetails has not been set"); //$NON-NLS-1$
            }
            EObject eObject = inputDetails.getEObject();
            WorkingCopy xpdlWorkingCopyFor =
                    WorkingCopyUtil.getWorkingCopyFor(eObject);
            IResource eclipseResource =
                    xpdlWorkingCopyFor.getEclipseResources().get(0);
            String activityId = inputDetails.getScriptContainerId();
            deleteValidationMarker(eclipseResource, activityId);
            // convertLineDelimiters(document);
            isScriptValid(modifiedScript, inputDetails, eclipseResource);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static void createValidationMarker(IResource resource,
            String genericErrorMsg, String antlrErrorMsg, String activityId,
            int lineNumber) {
        try {
            String errorMsg;
            if (antlrErrorMsg != null) {
                errorMsg = genericErrorMsg + ", " + antlrErrorMsg; //$NON-NLS-1$
            } else {
                errorMsg = genericErrorMsg;
            }
            IMarker marker = resource.createMarker(MARKER_TYPE);
            marker.setAttribute(IMarker.MESSAGE, errorMsg);
            marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
            marker.setAttribute(IMarker.LOCATION, resource
                    .getProjectRelativePath().toString());
            String projName = resource.getProject().getName();
            marker.setAttribute(PROJECT_NAME_ATTRIB, projName);
            marker.setAttribute(SCRIPT_CONTAINER_ID, activityId);
            marker.setAttribute(LINE_NUMBER, lineNumber);
            marker.setAttribute(ERROR_MESSAGE, antlrErrorMsg);
        } catch (CoreException e1) {
            e1.printStackTrace();

        }
    }

    private static void deleteValidationMarker(IResource resource,
            String activityId) {
        ArrayList<IMarker> markerList =
                getValidationMarker(resource, activityId);
        for (IMarker marker : markerList) {
            try {
                marker.delete();
            } catch (CoreException e) {
                e.printStackTrace();
            }
        }
    }

    public static ArrayList<IMarker> getValidationMarker(IResource resource,
            String activityId) {
        IMarker[] markerArr;
        ArrayList<IMarker> markerList = new ArrayList<IMarker>();
        try {
            markerArr =
                    resource.findMarkers(MARKER_TYPE,
                            true,
                            IResource.DEPTH_INFINITE);
            if (markerArr != null || markerArr.length > 0) {
                for (int index = 0; index < markerArr.length; index++) {
                    IMarker tempMarker = markerArr[index];
                    if (tempMarker != null && tempMarker.exists()) {
                        String markerActivityId =
                                (String) tempMarker
                                        .getAttribute(SCRIPT_CONTAINER_ID);
                        if (activityId.equals(markerActivityId)) {
                            markerList.add(tempMarker);
                        }
                    }
                }
            }
        } catch (CoreException e) {
            e.printStackTrace();
        }
        return markerList;
    }

    private static boolean isScriptValid(String scriptText, InputDetails input,
            IResource eclipseResource) {
        String packageName = input.getPackageName();
        String processName = input.getProcessName();
        String activityName = input.getScriptContainerName();
        String activityId = input.getScriptContainerId();
        String genericMsg;
        String antlrErrorMsg;
        int line;
        int column;
        try {
            StringReader reader = new StringReader(scriptText);
            // Create a scanner that reads from the input stream passed to us
            JScriptLexer lexer = new JScriptLexer(reader);
            // Create a parser that reads from the scanner
            JScriptParser parser = new JScriptParser(lexer);
            // start parsing at the compilationUnit rule
            ISymbolTable symbolTable = getSymbolTable();
            parser.setSymbolTable(symbolTable);

            List<String> processDestinationList =
                    input.getProcessDestinationList();
            if (processDestinationList == null) {
                processDestinationList = new ArrayList<String>();
                processDestinationList.add(ProcessJsConsts.JSCRIPT_DESTINATION);
            }
            List<IValidationStrategy> validationStrategy =
                    ScriptGrammarContributionsUtil.INSTANCE
                            .getValidationStrategy(processDestinationList,
                                    ProcessJsConsts.SCRIPT_TASK,
                                    ProcessJsConsts.JAVASCRIPT_GRAMMAR,
                                    ProcessJsConsts.JSCRIPT_DESTINATION);
            parser.setValidationStrategyList(validationStrategy);
            parser.compilationUnit();
            Map<String, List<ErrorMessage>> errorMap =
                    ProcessValidationUtil.getErrorMap(parser);
            Collection<List<ErrorMessage>> errorListCollection =
                    errorMap.values();
            for (List<ErrorMessage> errorList : errorListCollection) {
                for (ErrorMessage message : errorList) {
                    line = message.getLineNumber();
                    column = message.getColumnNumber();
                    genericMsg =
                            Messages.DefaultJavaScriptCompiler_GenericMessage;
                    List<String> additionalAttributes = new ArrayList<String>();
                    additionalAttributes.add(processName);
                    additionalAttributes.add(activityName);
                    additionalAttributes.add(String.valueOf(line));
                    additionalAttributes.add(String.valueOf(column));
                    ErrorMessage newMessage =
                            new ErrorMessage(line, column, genericMsg, null,
                                    additionalAttributes);

                    antlrErrorMsg = message.getErrorMessage();
                    createValidationMarker(eclipseResource, newMessage
                            .getErrorMessage(), antlrErrorMsg, activityId, line);
                }
            }
            Set<String> identifierNames =
                    parser.getSymbolTable().getLocalVariableNames();
            for (String identifier : identifierNames) {
                System.out.println("The name of identifier is " + identifier); //$NON-NLS-1$
            }
            JScriptTreeParser treeParser = new JScriptTreeParser();
            treeParser.compilationUnit(parser.getAST());
            if (symbolTable instanceof SymbolTable) {
    			((SymbolTable) symbolTable).dispose();
    		}
            symbolTable = null;
            parser = null;
        } catch (Exception e) {
            if (e instanceof RecognitionException) {
                RecognitionException mte = (RecognitionException) e;
                line = mte.getLine();
                column = mte.getColumn();
                antlrErrorMsg = mte.getMessage();
                genericMsg = Messages.DefaultJavaScriptCompiler_GenericMessage;
                List<String> additionalAttributes = new ArrayList<String>();
                additionalAttributes.add(processName);
                additionalAttributes.add(activityName);
                additionalAttributes.add(String.valueOf(line));
                additionalAttributes.add(String.valueOf(column));
                ErrorMessage newMessage =
                        new ErrorMessage(line, column, genericMsg, null,
                                additionalAttributes);
                createValidationMarker(eclipseResource, newMessage
                        .getErrorMessage(), antlrErrorMsg, activityId, line);
            } else {
                e.printStackTrace(); // so we can get stack trace
            }
            return false;
        } 
        return true;
    }

    private static Map<String, IScriptRelevantData> getProcessFields() {
        Map<String, IScriptRelevantData> processDataFields =
                new HashMap<String, IScriptRelevantData>();
        IScriptRelevantData scriptRelevantData1 =
                new DefaultScriptRelevantData();
        scriptRelevantData1.setName("data1"); //$NON-NLS-1$
        scriptRelevantData1.setType("String"); //$NON-NLS-1$
        processDataFields.put("data1", scriptRelevantData1); //$NON-NLS-1$
        IScriptRelevantData scriptRelevantData2 =
                new DefaultScriptRelevantData();
        scriptRelevantData2.setName("data2"); //$NON-NLS-1$
        scriptRelevantData2.setType("String"); //$NON-NLS-1$
        processDataFields.put("data2", scriptRelevantData2); //$NON-NLS-1$
        IScriptRelevantData scriptRelevantData3 =
                new DefaultScriptRelevantData();
        scriptRelevantData3.setName("data3"); //$NON-NLS-1$
        scriptRelevantData3.setType("String"); //$NON-NLS-1$
        processDataFields.put("data3", scriptRelevantData3); //$NON-NLS-1$
        return processDataFields;
    }

    private static ISymbolTable getSymbolTable() {
        ISymbolTable table = new SymbolTable();
        table.setScriptRelevantDataTypeMap(getProcessFields());
        return table;
    }

    public InputDetails getInputDetails() {
        return inputDetails;
    }

    public void setInputDetails(InputDetails inputDetails) {
        this.inputDetails = inputDetails;
    }

}
