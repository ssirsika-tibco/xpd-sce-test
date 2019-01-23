package com.tibco.xpd.script.parser.validator;

import java.util.Collections;
import java.util.HashMap;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.Map;

import com.tibco.xpd.script.parser.Messages;

public class ErrorMessage {

    private int lineNumber;

    private int columnNumber;

    private String errorMessage;

    private ErrorType errorType;

    private List<String> additionalAttributes;

    public ErrorMessage(int lineNumber, int columnNumber, String errorMessage) {
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
        this.errorMessage = errorMessage;
    }

    public ErrorMessage(int lineNumber, int columnNumber, String errorMessage,
            ErrorType errorType) {
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
        this.errorMessage = errorMessage;
        this.errorType = errorType;
    }

    public ErrorMessage(int lineNumber, int columnNumber, String errorMessage,
            ErrorType errorType, List<String> additionalAttributes) {
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
        this.errorMessage = errorMessage;
        this.errorType = errorType;
        this.additionalAttributes = additionalAttributes;
    }

    /**
     * @param lineNumber
     *            the lineNumber to set
     */
    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    /**
     * @return the lineNumber
     */
    public int getLineNumber() {
        return lineNumber;
    }

    /**
     * @param columnNumber
     *            the columnNumber to set
     */
    public void setColumnNumber(int columnNumber) {
        this.columnNumber = columnNumber;
    }

    /**
     * @return the columnNumber
     */
    public int getColumnNumber() {
        return columnNumber;
    }

    /**
     * @param errorMessage
     *            the errorMessage to set
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * @return the errorMessage
     */
    public String getErrorMessage() {
        if (additionalAttributes != null) {
            try {
                String text =
                        String.format(errorMessage,
                                additionalAttributes.toArray());
                return text;
            } catch (IllegalFormatException ex) {
                ex.printStackTrace();
            }
        }
        return errorMessage;
    }

    /**
     * @return the errorType
     */
    public ErrorType getErrorType() {
        return errorType;
    }

    /**
     * @param errorType
     *            the errorType to set
     */
    public void setErrorType(ErrorType errorType) {
        this.errorType = errorType;
    }

    @Override
    public String toString() {
        String toReturn =
                Messages.ErrorMessage_LineNumber + lineNumber
                        + Messages.ErrorMessage_ColumnNumber + columnNumber
                        + Messages.ErrorMessage_ErrorMessage
                        + getErrorMessage();
        return toReturn;
    }

    public Map<String, String> getAdditionalInfoMap() {
        Map<String, String> additionalInfoMap = new HashMap<String, String>();
        additionalInfoMap.put("LineNumber", Integer.toString(lineNumber)); //$NON-NLS-1$
        additionalInfoMap.put("ColumnNumber", Integer.toString(columnNumber)); //$NON-NLS-1$
        additionalInfoMap.put("ErrorMessage", getErrorMessage()); //$NON-NLS-1$
        return additionalInfoMap;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (obj instanceof ErrorMessage) {
            ErrorMessage eMessage = (ErrorMessage) obj;

            // Check the simple stuff before going to all the hassle of
            // generating and comparing the error message itself! Remember that
            // equals() method is used for sorting and getting from lists etc so
            // can be called many thousands of times for a single user
            // operation.
            // Even to the point that function call overhead could be
            // significant.
            if (this.lineNumber == eMessage.lineNumber
                    && this.columnNumber == eMessage.columnNumber
                    && this.errorType.equals(eMessage.errorType)
                    && this.errorMessage == eMessage.errorMessage) {
                // Ok that's all the simple variables equal
                // Check the additional info.

                List<String> addAtt1;
                if (this.additionalAttributes != null) {
                    addAtt1 = this.additionalAttributes;
                } else {
                    addAtt1 = Collections.emptyList();
                }

                List<String> addAtt2;
                if (eMessage.additionalAttributes != null) {
                    addAtt2 = eMessage.additionalAttributes;
                } else {
                    addAtt2 = Collections.emptyList();
                }

                if (addAtt1.size() == addAtt2.size()) {
                    // Same number of additional info entries, check the actual
                    // additional info entries.
                    int sz = addAtt1.size();
                    boolean allSame = true;
                    for (int i = 0; i < sz; i++) {
                        if (!addAtt1.get(i).equals(addAtt2.get(i))) {
                            allSame = false;
                            break;
                        }
                    }

                    if (allSame) {
                        return true;
                    }
                }
            }

            return false;

        } else {
            return super.equals(obj);
        }
    }

    @Override
    public int hashCode() {
        String str = toString();
        return str.hashCode();
    }

}
