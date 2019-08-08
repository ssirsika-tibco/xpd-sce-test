/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.n2.resources.postimport;

import antlr.Token;

/**
 * Data class to store individual field/param reference (and be able to sort into line in ascending order, then column
 * IN REVERSE ORDER!
 * <p>
 * This helps when doing replacements because we can always guarantee that we don't shuffle text we're interested in
 * away from it's original offset.
 * </p>
 * 
 * @author aallway - shamelessly ripped out of ScriptParserUtil as the use case here will grow to be more than just
 *         simple name reference replacement.
 */
class ScriptItemReplacementRef implements Comparable<ScriptItemReplacementRef> {
    private final int line;

    private final int col;

    private final int len;

    private final String newValue;

    /**
     * Creates a replacement reference with the properties of the given token.
     * 
     * @param aToken
     *            the token that identifies the position and text to be replaced.
     * @param aNewValue
     *            the replacement text.
     */
    public ScriptItemReplacementRef(Token aToken, String aNewValue) {
        this.line = aToken.getLine();
        this.col = aToken.getColumn();
        this.len = aToken.getText().length();
        this.newValue = aNewValue;
    }

    // Sort by line then column.
    @Override
    public int compareTo(ScriptItemReplacementRef o) {
        // Sort by ascending line number
        int ret = line - o.line;
        if (ret == 0) {
            // then descending (reverse) line number.
            ret = o.col - col;
        }
        return ret;
    }

    public int getLine() {
        return line;
    }

    /**
     * Replace the text for this script item with the given replacement text in the given StringBuilder containing the
     * original script.
     * 
     * NOTE that the caller is expected to call this function <b>IN REVERSE ORDER</b> for each line (as this object only
     * stores the location of the reference in the original script).
     * 
     * @param stringBuilder
     */
    public void replaceRef(StringBuilder stringBuilder) {
        int startIdx = col - 1;
        if ((newValue == null) || (newValue.isEmpty())) {
            stringBuilder.delete(startIdx, startIdx + len);
        } else {
            stringBuilder.replace(startIdx, startIdx + len, newValue);
        }
    }

    @SuppressWarnings("nls")
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("replace with \"").append(newValue).append('"');

        return result.toString();
    }
}
