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
package com.tibco.xpd.scripteditors.internal.xpath.preferences.ui;



import java.io.PushbackReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.wst.sse.core.internal.ltk.parser.RegionParser;
import org.eclipse.wst.sse.core.internal.parser.ContextRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.sse.core.internal.text.BasicStructuredDocumentRegion;
import org.eclipse.wst.xml.core.internal.parser.XMLTokenizer;
import org.eclipse.wst.xml.core.internal.regions.DOMRegionContext;

import com.tibco.xpd.scripteditors.internal.xpath.styles.IStyleConstantsXPath;
import com.tibco.xpd.scripteditors.internal.xpath.styles.LineStyleProviderForXPath;

/**
 * The XPath Lexer does not conform to the top level IStructuredDocumentRegion model
 * nor its RegionParser interface.  Since the StyledTextColorPicker
 * depends on having a usable parser, wrap the Lexer accordingly.
 */

public class XPathTokenParser implements RegionParser {

	private PushbackReader fPushBackReader = null;
	private List regions = null;

	/**
	 * XPathTokenParser constructor comment.
	 */
	public XPathTokenParser() {
		super();
	}

	/**
	 * Convert the XPath Lexer token list into one large IStructuredDocumentRegion
	 * mapping each token and type to a region and context.
	 */
	public IStructuredDocumentRegion getDocumentRegions() {
	    XMLTokenizer tokenizer = new XMLTokenizer(fPushBackReader);	    
	    ITextRegion textRegion = null;
        ContextRegion region = null;
        IStructuredDocumentRegion node = new BasicStructuredDocumentRegion();
        int start = -1;
        int textLength = 0;
        int length = 0;
        boolean useSame = false;
        this.regions = new ArrayList();
        if(tokenizer != null){
            try{
                textRegion = tokenizer.getNextToken();
            }catch (Exception e) {
                // TODO: handle exception
            }
        }
	    // iterate through tokens until on screen and off and a bit more for caching
        while (textRegion != null) {
            useSame = false;
            start = textRegion.getStart();
            textLength = length = textRegion.getTextLength();
            String type = IStyleConstantsXPath.DEFAULT;
            if (textRegion instanceof ContextRegion) {
                ContextRegion contextRegion = (ContextRegion) textRegion;
                if (contextRegion.getType() != null
                        && (contextRegion.getType().equals(
                                DOMRegionContext.XML_COMMENT_OPEN)
                                || contextRegion.getType().equals(
                                        DOMRegionContext.XML_COMMENT_TEXT) || contextRegion
                                .getType().equals(
                                        DOMRegionContext.XML_COMMENT_CLOSE))) {
                    type = IStyleConstantsXPath.COMMENT;
                }
            }
            // combine same-typed successive Regions
            useSame = useSame || (region != null && region.getType() == type);
            if (useSame && region != null) {
                // extend both the text length and total length
                region.setLength(region.getLength() + length);
                region.setTextLength(region.getLength());
            }
            else {
                if (region != null){
                    regions.add(region);
                }
                region = new ContextRegion(type, start, textLength, length);
                node.addRegion(region);;
            }
            try {
                textRegion = tokenizer.getNextToken();
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

    protected boolean isKeyword(String text) {
        String[] keywords = LineStyleProviderForXPath.keywords;
        for (int i = 0; i < keywords.length; i++)
            if (keywords[i].equals(text))
                return true;
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
		return new XPathTokenParser();
	}

}
