package com.tibco.xpd.script.sourceviewer.internal.util;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.text.edits.TextEdit;
import org.eclipse.wst.javascript.core.internal.contenttype.ContentTypeIdForJavaScript;
import org.eclipse.wst.sse.core.internal.encoding.CommonEncodingPreferenceNames;
import org.eclipse.wst.sse.core.internal.encoding.ContentBasedPreferenceGateway;

public class LineDelimeterUtil {

    public static void convertLineDelimiters(IDocument document)
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

}
