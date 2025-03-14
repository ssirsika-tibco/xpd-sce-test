/*******************************************************************************
 * Copyright (c) 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.tibco.xpd.rql.script.preferences.ui;



import java.io.PushbackReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.eclipse.wst.javascript.core.internal.jsparser.lexer.Lexer;
import org.eclipse.wst.javascript.core.internal.jsparser.node.EOF;
import org.eclipse.wst.javascript.core.internal.jsparser.node.TBlank;
import org.eclipse.wst.javascript.core.internal.jsparser.node.Token;
import org.eclipse.wst.sse.core.internal.ltk.parser.RegionParser;
import org.eclipse.wst.sse.core.internal.parser.ContextRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.text.BasicStructuredDocumentRegion;

import com.tibco.xpd.rql.script.styles.IStyleConstantsRQL;
import com.tibco.xpd.rql.script.styles.LineStyleProviderForRQL;

/**
 * The JavaScript Lexer does not conform to the top level IStructuredDocumentRegion model
 * nor its RegionParser interface.  Since the StyledTextColorPicker
 * depends on having a usable parser, wrap the Lexer accordingly.
 */

public class RQLTokenParser implements RegionParser {

	private PushbackReader fPushBackReader = null;
	private List regions = null;

	/**
	 * TokenParser constructor comment.
	 */
	public RQLTokenParser() {
		super();
	}

	/**
	 * Convert the JavaScript Lexer token list into one large IStructuredDocumentRegion
	 * mapping each token and type to a region and context.
	 */
	public IStructuredDocumentRegion getDocumentRegions() {

		Lexer lexer = new Lexer(fPushBackReader);
		Token token = null;
		ContextRegion region = null;
		IStructuredDocumentRegion node = new BasicStructuredDocumentRegion();
		int start = -1;
		int textLength = 0;
		int length = 0;
		boolean useSame = false;
		this.regions = new ArrayList();

		try {
			token = lexer.next();
		}
		catch (Exception e) {
		}
		// iterate through tokens until on screen and off and a bit more for caching
		while (token != null && !(token instanceof EOF)) {
			useSame = false;
			start = token.getLPOffset();
			textLength = length = token.toString().length();
			String type = IStyleConstantsRQL.DEFAULT;
			if (token instanceof TBlank) {
				//			useSame = true;
				// append as whitespace
			}            
            else if (isEntityKeyword(token.getText())) {
                type = IStyleConstantsRQL.KEYWORDENTITIES;
            }           
            else if (isAttributeKeyword(token.getText())) {
                type = IStyleConstantsRQL.KEYWORDATTRIBUTES;
            }           
            else if (isOperatorKeyword(token.getText())) {
                type = IStyleConstantsRQL.KEYWORDOPERATORS;
            }
			// combine same-typed successive tokens
			//		if ((styleRange != null)
			//			&& (previousAttr != null)
			//			&& (previousAttr.equals(attr))) {
			//			styleRange.length += (strTkThis.length());
			//		}

			// combine same-typed successive Regions
			useSame = useSame || (region != null && region.getType() == type);
			if (useSame && region != null) {
				// extend both the text length and total length
				region.setLength(region.getLength() + length);
				region.setTextLength(region.getLength());
			}
			else {
				if (region != null)
					regions.add(region);
				region = new ContextRegion(type, start, textLength, length);
				node.addRegion(region);
				// DW, 4/16/2003 .. regions no longer have parents
				// region.setParent(node);
			}
			try {
				token = lexer.next();
			}
			catch (Exception e) {
			}

		}
		regions.add(region);
		node.setStart(0);
		node.setLength(region.getStart() + region.getLength());
		node.setEnded(true);
		return node;
	}

	/**
	 * getRegions method comment.
	 */
	public List getRegions() {
		if (regions == null)
			getDocumentRegions();
		return regions;
	}

    protected boolean isEntityKeyword(String text) {
        HashSet<String> keywords = LineStyleProviderForRQL.entityKeywords;
        if (keywords.contains(text)) {
            return true;
        }
        return false;
    }

    protected boolean isAttributeKeyword(String text) {
        HashSet<String> keywords = LineStyleProviderForRQL.attributeKeywords;
        if (keywords.contains(text)) {
            return true;
        }
        return false;
    }

    protected boolean isOperatorKeyword(String text) {
        HashSet<String> keywords = LineStyleProviderForRQL.operatorKeywords;
        if (keywords.contains(text)) {
            return true;
        }
        return false;
    }

	/**
	 * reset method comment.
	 */
	public void reset(Reader reader) {
		reset(reader, 0);
	}

	/**
	 * An additional offset for use with any position-dependant parsing rules
	 */
	public void reset(Reader reader, int offset) {
		fPushBackReader = new PushbackReader(reader);
	}

	/**
	 * reset method comment.
	 */
	public void reset(String s) {
		reset(new StringReader(s));
	}

	/**
	 * An additional offset for use with any position-dependant parsing rules
	 */
	public void reset(String input, int offset) {
		reset(new StringReader(input), offset);
	}

	public RegionParser newInstance() {
		return new RQLTokenParser();
	}

}
