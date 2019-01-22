/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.n2.resources.script;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;

import com.tibco.xpd.n2.resources.internal.Messages;
import com.tibco.xpd.studioiprocess.scriptconverter.AbstractIProcessToJavaScriptConverter;
import com.tibco.xpd.studioiprocess.scriptconverter.IProcessExpressionChunk;
import com.tibco.xpd.studioiprocess.scriptconverter.IProcessExpressionChunkFactory;
import com.tibco.xpd.studioiprocess.scriptconverter.expressionchunks.ChunkDateConstant;
import com.tibco.xpd.studioiprocess.scriptconverter.expressionchunks.ChunkDateTimeOffsetConstant;
import com.tibco.xpd.studioiprocess.scriptconverter.expressionchunks.ChunkTimeConstant;
import com.tibco.xpd.xpdl2.Expression;

/**
 * iProcessScript grammar script to JavaScript converter specialised (if
 * required later) for AMX BPM (N2) destination
 * 
 * @author aallway
 * @since 8 Jun 2011
 */
public class IProcessScriptToBPMJavaScriptConverter extends
        AbstractIProcessToJavaScriptConverter {

    /**
     * Function name map (iProcess function name to JavaScript utility class and
     * method).
     */
    static final Map<String, String> functionNameMap =
            new HashMap<String, String>();
    static {
        /*
         * FUNCTIONS with KNOWN EQUIVALENCE...
         */
        functionNameMap.put("CALCDATE", "IpeScriptUtil.CALCDATE"); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("CALCTIME", "IpeScriptUtil.CALCTIME"); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("DATE", "IpeScriptUtil.DATE"); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("DATESTR", "IpeScriptUtil.DATESTR"); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("DAYNUM", "IpeScriptUtil.DAYNUM"); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("DAYSTR", "IpeScriptUtil.DAYNUM"); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("HOURNUM", "IpeScriptUtil.HOURNUM"); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("MINSNUM", "IpeScriptUtil.MINSNUM"); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("MONTHNUM", "IpeScriptUtil.MONTHNUM"); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("MONTHSTR", "IpeScriptUtil.MONTHSTR"); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("NUM", "IpeScriptUtil.NUM"); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("RSEARCH", "IpeScriptUtil.RSEARCH"); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("SEARCH", "IpeScriptUtil.SEARCH"); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("SPECIALCHARS", "IPEConversionUtil.SPECIALCHARS"); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("STR", "IpeScriptUtil.STR"); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("STRCONVERT", "IpeScriptUtil."); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("STRLEN", "IpeScriptUtil.STRLEN"); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("STRTOLOWER", "IpeScriptUtil.STRTOLOWER"); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("STRTOUPPER", "IpeScriptUtil.STRTOUPPER"); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("SUBSTR", "IpeScriptUtil.SUBSTR"); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("TIME", "IpeScriptUtil.TIME"); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("TIMESTR", "IpeScriptUtil.TIMESTR"); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("WEEKNUM", "IpeScriptUtil.WEEKNUM"); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("YEARNUM", "IpeScriptUtil.YEARNUM"); //$NON-NLS-1$ //$NON-NLS-2$
        /*
         * NO EQUIVALENCE...
         */
        functionNameMap.put("FINDARRELEMENT", "");//$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("NEXTARRELEMENT", ""); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("DBWRITEFIELDS", ""); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("DDEEXECUTE", ""); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("DDEGETNAME", ""); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("DDEGETTOPIC", ""); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("DDEINITIATE", ""); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("DDEPOKE", ""); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("DDEREQUEST", ""); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("DDETERMALL", ""); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("DDETERMINATE", ""); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("CANDO", ""); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("CUSTAUDIT", "CUSTAUDIT"); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("ENQUIRE", ""); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("FORMCONTROL", ""); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("FORMMAXIMIZE", ""); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("FORMMINIMIZE", ""); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("FORMMOVE", ""); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("FORMRESTORE", ""); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("FORMSIZE", ""); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("GETHANDLE", ""); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("ISWINDOWS", ""); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("MARKFIELDCHANGED", ""); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("MEMOFILE", ""); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("MESSAGEBOX", ""); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("READFIELDS", ""); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("SENDKEYS", ""); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("SETSTEPSTATUS", ""); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("USERATTRIBUTE", ""); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("WINACTION", ""); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("WINACTIVATE", ""); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("WINCLOSE", ""); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("WINEXIST", ""); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("WINFIND", ""); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("WINMAXIMIZE", ""); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("WINMESSAGE", ""); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("WINMINIMIZE", ""); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("WINMOVE", ""); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("WINRESTORE", ""); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("WINSIZE", ""); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("WRITEFIELDS", ""); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("SERVEREXEC", ""); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("SERVERRUN", ""); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("UNIXEXEC", ""); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("UNIXRUN", ""); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("WINRUN", ""); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("FILECOPY", ""); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("FILEDELETE", ""); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("FILEEXISTS", ""); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("FILERENAME", ""); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("FILEREQUEST", ""); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("SELECTVAL", ""); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("SWITCHVAL", ""); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("GETPROCESSNAME", ""); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("CASECLOSE", ""); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("CASESTART", ""); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("GOTOSTEP", ""); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("TRIGGEREVENT", ""); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("GETTASKNAME", ""); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("VLDFILE", ""); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("VLDFILEX", ""); //$NON-NLS-1$ //$NON-NLS-2$
        functionNameMap.put("VLDQUERY", ""); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * iPocess System fields map (SW_xxx to equivalent JavaScript utility class
     * and method name.
     */
    static final Map<String, String> systemFieldsNameMap =
            new HashMap<String, String>();
    static {
        systemFieldsNameMap.put("SW_NA", "null"); //$NON-NLS-1$ //$NON-NLS-2$

        systemFieldsNameMap.put("SW_DATE", "DateTimeUtil.createDate()"); //$NON-NLS-1$ //$NON-NLS-2$
        systemFieldsNameMap.put("SW_TIME", "DateTimeUtil.createTime()"); //$NON-NLS-1$ //$NON-NLS-2$ 

        systemFieldsNameMap.put("SW_BLANK", ""); //$NON-NLS-1$ //$NON-NLS-2$
        systemFieldsNameMap.put("SW_CASENUM", "Process.getId()"); //$NON-NLS-1$ //$NON-NLS-2$
        systemFieldsNameMap.put("SW_CASEREF", ""); //$NON-NLS-1$ //$NON-NLS-2$
        systemFieldsNameMap.put("SW_HOSTNAME", ""); //$NON-NLS-1$ //$NON-NLS-2$
        systemFieldsNameMap.put("SW_NODENAME", ""); //$NON-NLS-1$ //$NON-NLS-2$
        systemFieldsNameMap.put("SW_PRODESC", ""); //$NON-NLS-1$ //$NON-NLS-2$
        systemFieldsNameMap.put("SW_PRONAME", "Process.getName()"); //$NON-NLS-1$ //$NON-NLS-2$
        systemFieldsNameMap.put("SW_CP_VALUE", "Process.priority"); //$NON-NLS-1$ //$NON-NLS-2$
        systemFieldsNameMap.put("SW_QRETRYCOUNT", ""); //$NON-NLS-1$ //$NON-NLS-2$
        systemFieldsNameMap.put("SW_STEPDESC", ""); //$NON-NLS-1$ //$NON-NLS-2$
        systemFieldsNameMap.put("SW_STEPNAME", ""); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * @param sourceIProcessScript
     */
    public IProcessScriptToBPMJavaScriptConverter(String sourceIProcessScript,
            Expression expression) {
        super(sourceIProcessScript, expression);
    }

    /**
     * ABPM-675: Modify the existing internal API to be passed the list of
     * available-data-names rather than an expression and therefore not require
     * or use the EMF XPDL model at all.
     * 
     * @param sourceIProcessScript
     * @param allData
     */
    public IProcessScriptToBPMJavaScriptConverter(String sourceIProcessScript,
            Set<String> allData) {
        super(sourceIProcessScript, allData);
    }

    /**
     * @see com.tibco.xpd.studioiprocess.scriptconverter.AbstractIProcessToJavaScriptConverter#createChunkFactory()
     * 
     * @return
     */
    @Override
    protected IProcessExpressionChunkFactory createChunkFactory() {
        return new BPMDestSpecialisedChunkFactory();
    }

    /**
     * @see com.tibco.xpd.studioiprocess.scriptconverter.AbstractIProcessToJavaScriptConverter#translateFunctionName(java.lang.String)
     * 
     * @param functionName
     * @return
     */
    @Override
    protected String translateFunctionName(String functionName) {
        for (Entry<String, String> entry : functionNameMap.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(functionName)) {
                String value = entry.getValue();

                if (value != null && value.length() > 0) {
                    return value;
                }

                /* No equivalence so just return default. */
                break;
            }
        }
        /*
         * XPD-6105 - Kapil : commenting out the call to super class as we no
         * longer want to add comments when we have CALL or SCRIPT methods in
         * our Java Script
         */
        // return super.translateFunctionName(functionName);
        return functionName;
    }

    /**
     * @see com.tibco.xpd.studioiprocess.scriptconverter.AbstractIProcessToJavaScriptConverter#translateKeywordOrFieldName(java.lang.String)
     * 
     * @param chunkText
     * @return
     */
    @Override
    protected String translateKeywordOrFieldName(String chunkText) {

        /*
         * Handle conversion of special system fields like SW_NA to equivalents
         */
        for (Entry<String, String> entry : systemFieldsNameMap.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(chunkText)) {
                String value = entry.getValue();

                if (value != null && value.length() > 0) {
                    return value;
                }

                /* No equivalence so just return default. */
                break;
            }
        }

        /*
         * Fall back on basic handling.
         */
        return super.translateKeywordOrFieldName(chunkText);
    }

    /**
     * Chunk factory specialised for when the target is JavaScript for AMX BPM
     * destination
     * 
     * @author aallway
     * @since 10 Jun 2011
     */
    private static class BPMDestSpecialisedChunkFactory extends
            IProcessExpressionChunkFactory {
        /**
         * @see com.tibco.xpd.studioiprocess.scriptconverter.IProcessExpressionChunkFactory#createDateConstantChunk(java.lang.String,
         *      int)
         * 
         * @param line
         * @param startOfChunkIdx
         * @return
         */
        @Override
        protected IProcessExpressionChunk createDateConstantChunk(String line,
                int startOfChunkIdx) {
            return new ChunkDateConstant(line, startOfChunkIdx) {
                @Override
                public String getChunkText() {
                    String dateConstant = super.getChunkText();

                    List<String> tokens =
                            tokeniseConstant(dateConstant, "/", 3); //$NON-NLS-1$

                    return String
                            .format("/* %s */ DateTimeUtil.createDate(?%s, ?%s, ?%s)", //$NON-NLS-1$
                                    String.format(Messages.IProcessScriptToBPMJavaScriptConverter_OrderOfDateConstantFields_message,
                                            dateConstant),
                                    tokens.get(0),
                                    tokens.get(1),
                                    tokens.get(2));
                }
            };
        }

        /**
         * @see com.tibco.xpd.studioiprocess.scriptconverter.IProcessExpressionChunkFactory#createTimeConstantChunk(java.lang.String,
         *      int)
         * 
         * @param line
         * @param startOfChunkIdx
         * @return
         */
        @Override
        protected IProcessExpressionChunk createTimeConstantChunk(String line,
                int startOfChunkIdx) {
            return new ChunkTimeConstant(line, startOfChunkIdx) {
                @Override
                public String getChunkText() {
                    String timeConstant = super.getChunkText();

                    List<String> tokens =
                            tokeniseConstant(timeConstant, ":", 2); //$NON-NLS-1$

                    return String
                            .format("DateTimeUtil.createTime(%s, %s, 0, 0)", //$NON-NLS-1$
                                    tokens.get(0),
                                    tokens.get(1));
                }
            };
        }

        /**
         * @see com.tibco.xpd.studioiprocess.scriptconverter.IProcessExpressionChunkFactory#createDateTimeOffsetConstantChunk(java.lang.String,
         *      int)
         * 
         * @param line
         * @param startOfChunkIdx
         * @return
         */
        @Override
        protected IProcessExpressionChunk createDateTimeOffsetConstantChunk(
                String line, int startOfChunkIdx) {
            // TODO Auto-generated method stub
            return new ChunkDateTimeOffsetConstant(line, startOfChunkIdx) {
                @Override
                public String getChunkText() {
                    String offsetConstant = super.getChunkText();

                    List<String> tokens =
                            tokeniseConstant(offsetConstant, "/", 4); //$NON-NLS-1$
                    return "/* " //$NON-NLS-1$
                            + String.format(Messages.IProcessScriptToBPMJavaScriptConverter_ConvertDateTimeOffsetConstant_message,
                                    offsetConstant)
                            + "*/ " //$NON-NLS-1$
                            + String.format("IpeScriptUtil.CALCDATE(?DateField?, %s, %s, %s, %s)", //$NON-NLS-1$
                                    tokens.get(0),
                                    tokens.get(1),
                                    tokens.get(2),
                                    tokens.get(3));
                }
            };
        }

        /**
         * @param constant
         * @param delimiter
         * @param expectedTokenCount
         * @return List of elements extracted from the given constant string
         *         definition that are separated by the given delimiter. At
         *         least expecetdTokenCount are guaranteed to be returned - if
         *         there are less tokens actually defined then the token in list
         *         will have value ""
         */
        private List<String> tokeniseConstant(String constant,
                String delimiter, int expectedTokenCount) {
            List<String> tokens = new ArrayList<String>();

            int tokenIdx = 0;

            if (constant != null) {
                constant = constant.trim();
                /* Strip leading / trailing "'s */
                if (constant.startsWith("\"")) { //$NON-NLS-1$
                    constant = constant.substring(1);
                }
                if (constant.endsWith("\"")) { //$NON-NLS-1$
                    constant = constant.substring(0, constant.length() - 1);
                }

                StringTokenizer tokenizer =
                        new StringTokenizer(constant, delimiter); //$NON-NLS-1$

                for (; tokenizer.hasMoreTokens(); tokenIdx++) {
                    String tok = tokenizer.nextToken();

                    tokens.add(tok != null ? tok : ""); //$NON-NLS-1$
                }
            }

            for (; tokenIdx < expectedTokenCount; tokenIdx++) {
                tokens.add(""); //$NON-NLS-1$
            }

            return tokens;
        }
    }

}
