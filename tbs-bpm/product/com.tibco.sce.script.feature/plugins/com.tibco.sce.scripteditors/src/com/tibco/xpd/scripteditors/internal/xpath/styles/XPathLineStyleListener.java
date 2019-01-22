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
package com.tibco.xpd.scripteditors.internal.xpath.styles;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Vector;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.ITextViewerExtension5;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.custom.LineStyleEvent;
import org.eclipse.swt.custom.LineStyleListener;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.wst.sse.core.internal.parser.ContextRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.xml.core.internal.parser.XMLTokenizer;
import org.eclipse.wst.xml.core.internal.regions.DOMRegionContext;

import com.tibco.xpd.script.sourceviewer.internal.contentassist.TextRange;
import com.tibco.xpd.script.sourceviewer.internal.util.Logger;
import com.tibco.xpd.script.sourceviewer.internal.viewer.listeners.AbstractLineStyleListener;
import com.tibco.xpd.scripteditors.internal.xpath.preferences.ui.LexerCacheForXPath;
import com.tibco.xpd.scripteditors.internal.xpath.preferences.ui.XPathColorPreferences;

;

public class XPathLineStyleListener extends AbstractLineStyleListener {
    private class PropertyChangeListener implements IPropertyChangeListener {
        /* (non-Javadoc)
         * @see org.eclipse.jface.util.IPropertyChangeListener#propertyChange(org.eclipse.jface.util.PropertyChangeEvent)
         */
        public void propertyChange(PropertyChangeEvent event) {
            // have to do it this way so others can override the method
            handlePropertyChange(event);
        }
    }

    private ISourceViewer fSourceViewer = null;

    protected ArrayList cachedStyles = new ArrayList(100);

    protected int offCachedStylesRegion = 0;

    protected String strOldNodeValue = null;

    protected LexerCacheForXPath pcParseCache = null;

    private IDocument fDocument = null;

    private static HashSet<String> boldKeywords = new HashSet<String>();

    private PropertyChangeListener fPreferenceListener =
            new PropertyChangeListener();

    static {
        for (int i = 0; i < LineStyleProviderForXPath.keywords.length; i++) {
            boldKeywords.add(LineStyleProviderForXPath.keywords[i]);
        }

    }

    /**
     * 
     */
    public XPathLineStyleListener() {
        super();
    }

    /* 
     * done: js outline view doesn't appear initially.  Appears after the file is mod'ed
     * done: no icons in js outline view
     * done: after changing colors in pref dialog, the js file is not forced to repaint.
     * todo: look in to javadoc help.
     * todo: test syntax completion.
     * todo: let syntax completion support vars (not just functions) also.
     * todo: cache outline view info better (if needed)
     * todo: on 02/02/28 the performance of typing is bad on Jason's machine.  Check in to it.
     */

    public void init(IDocument document, ISourceViewer sourceViewer) {
        fDocument = document; // setDocument(document);
        if (fDocument != null) {
            // fLexerCache =
            // this method has side effect, even though we don't use returned
            // value
            LexerCacheForXPath.getCache(fDocument, fDocument.get());
            fDocument.addDocumentListener(this);

            XPathColorPreferences
                    .addPropertyChangeListener(fPreferenceListener);
        }
        if (sourceViewer != null) {
            setSourceViewer(sourceViewer);
        }
    }

    // protected void setDocument(IDocument document) {
    // fDocument = document;
    // }
    public IDocument getDocument() {
        return fDocument;
    }

    /**
	 * 
	 */
    public void documentAboutToBeChanged(DocumentEvent event) {
    }

    /**
	 * 
	 */
    public void documentChanged(DocumentEvent event) {
        try {
            if (pcParseCache == null) {
                // hmm. Is this ever called?
                //                Logger.logError("JSLSL: docChanged called before we have a cache\n"); //$NON-NLS-1$
                return;
            }
            String nv = fDocument.get();
            if (nv != null) {
                if (strOldNodeValue == null) {
                    pcParseCache.notifyChange(nv, 0, 0, nv.length());
                    cachedStyles.clear();
                    redrawRegion(0, LexerCacheForXPath.CHANGED_ALL);
                } else {
                    int idx = event.getOffset();
                    // note: idx now is the index to mark the earliest spot
                    // where there
                    // might be a visible character change or token change. In
                    // the
                    // case of a token, it doesn't necessarily have to be at the
                    // beginning of a token. The reason is for example that the
                    // token
                    // is likely to be broken and possibly even split at the
                    // change point.

                    TextRange trRightmostParseChange =
                            pcParseCache.notifyChange(nv,
                                    idx,
                                    event.getLength(),
                                    (event.getText() != null ? event.getText()
                                            .length() : 0));

                    // note: we might need special code if changing of one
                    // character might
                    // affect earlier parsing for example changing "+ " to "++"
                    // changes
                    // the previous token too. For good measure let's move it
                    // back a few
                    // characters. If we discover we need to move back more than
                    // that,
                    // we can code that up after we learn about it.
                    // idx -= 2;

                    if (cachedStyles.size() > 0) {
                        int csize = cachedStyles.size();
                        int offIdx = idx + offCachedStylesRegion;
                        StyleRange srOne = (StyleRange) (cachedStyles.get(0));
                        if (srOne.start > offIdx) {
                            cachedStyles.clear();
                        } else {
                            while ((csize--) > 0) {
                                srOne = (StyleRange) cachedStyles.get(csize);
                                if ((srOne.start + srOne.length) >= offIdx) {
                                    cachedStyles.remove(srOne);
                                } else {
                                    break;
                                }
                            }
                        }
                    }
                    if (trRightmostParseChange.right > 0) {
                        int idxEOL =
                                nv.indexOf('\n',
                                        trRightmostParseChange.offStart);
                        if (idxEOL > trRightmostParseChange.right) {
                            trRightmostParseChange.right = idxEOL;
                        }
                        // defect 219198 - Highlighting wrong for end bracket
                        // without trailing whitespace
                        else if (idxEOL < 0) {
                            trRightmostParseChange.right = nv.length();
                        }
                    }
                    redrawRegion(trRightmostParseChange.offStart,
                            trRightmostParseChange.right);
                }
                this.strOldNodeValue = nv;
            }

        } catch (Exception exc) {
            Logger
                    .logException("Exception in notifyChanged() of LexerCacheForXPath", exc); //$NON-NLS-1$
        }
    }

    /**
     * @see LineStyleListener#lineGetStyle(LineStyleEvent)
     */
    public void lineGetStyle(LineStyleEvent event) {
        String lineText = event.lineText;
        int offset = event.lineOffset;

        event.styles = getStyleRangeArray(offset, lineText);
    }

    protected StyleRange[] getStyleRangeArray(int offset, String text) {
        Vector<StyleRange> styleRangeVector = new Vector<StyleRange>();
        if (text != null) {
            prepareRegions(offset, text, styleRangeVector);
        }
        StyleRange[] styleRangeArray = new StyleRange[styleRangeVector.size()];
        styleRangeVector.copyInto(styleRangeArray);

        return styleRangeArray;
    }

    /**
	 */
    private void prepareRegions(int offStart, String text,
            Collection<StyleRange> holdResults) {
        // Date dt0 = new Date();
        Vector<StyleRange> vecTempResults = new Vector<StyleRange>();
        // prepareRegions2(offStart, length, vecTempResults /*holdResults*/
        // );
        int length = text.length();
        Reader strReader = new StringReader(text);
        ITextRegion textRegion = null;
        XMLTokenizer tokenizer = new XMLTokenizer(strReader);
        if (tokenizer != null) {
            try {
                textRegion = tokenizer.getNextToken();
            } catch (Exception e) {
                // TODO: handle exception
            }
        }

        // iterate through tokens until on screen and off and a bit more for
        // caching
        while (textRegion != null) {
            TextAttribute attr = XPathColorPreferences.taDefault;
            if (textRegion instanceof ContextRegion) {
                ContextRegion contextRegion = (ContextRegion) textRegion;
                if (contextRegion.getType() != null
                        && (contextRegion.getType()
                                .equals(DOMRegionContext.XML_COMMENT_OPEN)
                                || contextRegion
                                        .getType()
                                        .equals(DOMRegionContext.XML_COMMENT_TEXT) || contextRegion
                                .getType()
                                .equals(DOMRegionContext.XML_COMMENT_CLOSE))) {
                    attr = XPathColorPreferences.taComment;
                }
            }

            StyleRange styleRange = null;
            styleRange =
                    new StyleRange(textRegion.getStart() + offStart, textRegion
                            .getTextLength(), attr.getForeground(), attr
                            .getBackground(), attr.getStyle());
            if (textRegion.getStart() < text.length()) {
                holdResults.add(styleRange);
            }
            cachedStyles.add(styleRange);

            try {
                textRegion = tokenizer.getNextToken();
            } catch (Exception e) {
            }

        }
        // now we'll have to trim the ends off because the
        // caller of this doesn't want it to begin before
        // the requested region or extend beyond.
        // I know for a fact that only the first and
        // last elements might extend outside the region.
        int veclen = vecTempResults.size();
        if (veclen > 0) {
            StyleRange sr0 = (StyleRange) vecTempResults.elementAt(0);
            sr0 = returnPrunedStyle(offStart, length, sr0);
            vecTempResults.removeElementAt(0);
            vecTempResults.insertElementAt(sr0, 0);
            StyleRange srE = (StyleRange) vecTempResults.elementAt(veclen - 1);
            srE = returnPrunedStyle(offStart, length, srE);
            vecTempResults.removeElementAt(veclen - 1);
            vecTempResults.insertElementAt(srE, veclen - 1);
        }

        for (int i = 0; i < vecTempResults.size(); i++) {
            StyleRange styleRange = (StyleRange) vecTempResults.elementAt(i);
            if (styleRange.length > 0)
                holdResults.add(styleRange);
        }

    }

    private void handlePropertyChange(PropertyChangeEvent event) {
        // need to check if event.getProperty is one I am concerned with before
        // doing
        // the below steps
        if (event != null) {
            String prefKey = event.getProperty();
            // check if preference changed is a style preference
            if (XPathColorPreferences.isXPathColorPreference(prefKey)) {
                cachedStyles.clear();

                ISourceViewer sourceViewer = getSourceViewer();
                if (sourceViewer != null) {
                    StyledText textWidget = sourceViewer.getTextWidget();
                    if (textWidget != null) {
                        textWidget.redraw();
                    }
                }
            }
        } else {
            // this is around for old deprecated preferencesChanged() method
            // TODO remove when preferencesChanged() is removed
            cachedStyles.clear();

            ISourceViewer sourceViewer = getSourceViewer();
            if (sourceViewer != null) {
                StyledText textWidget = sourceViewer.getTextWidget();
                if (textWidget != null) {
                    textWidget.redraw();
                }
            }
        }
    }

    /**
     * returnPrunedStyle just takes the given style and throws it at the end of
     * the given colleciton. If the style falls outside the specified offsets,
     * then a copy of the provided style is created, trimmed and appended to the
     * collection instead.
     */
    public static StyleRange returnPrunedStyle(int offRegStart, int lenReg,
            StyleRange srIn) {
        int intSRStart = srIn.start;
        int intSREnd = srIn.start + srIn.length;

        // assert: we assume that if we are called *some* part of srIn is in the
        // specified region. If that were not the case there probably isn't a
        // meaningful value we could return besides possibly null
        if (intSRStart < offRegStart) {
            intSRStart = offRegStart;
        }
        if (intSREnd > (offRegStart + lenReg)) {
            intSREnd = offRegStart + lenReg;
        }
        if ((intSRStart == srIn.start)
                && (intSREnd == (srIn.start + srIn.length))) {
            // no changes made so just return
            return srIn;
        }
        StyleRange retval = (StyleRange) srIn.clone();
        retval.start = intSRStart;
        retval.length = (intSREnd - intSRStart);
        return retval;
    }

    protected void setSourceViewer(ISourceViewer sourceViewer) {
        fSourceViewer = sourceViewer;
    }

    protected ISourceViewer getSourceViewer() {
        return fSourceViewer;
    }

    protected void redrawRegion(int start, int idxEnd) {
        int difflen = 0;
        if (idxEnd == LexerCacheForXPath.CHANGED_NONE) {
            return;
        } else if (idxEnd >= 0) {
            difflen = idxEnd - start;
        } else {
            // TODO this needs to be improved to handle projection documents
            // better
            // because there can be several visible regions, not just one
            IRegion visibleRegion = null;
            ITextViewer viewer = getSourceViewer();
            if (viewer instanceof ITextViewerExtension5) {
                ITextViewerExtension5 extension =
                        (ITextViewerExtension5) viewer;
                visibleRegion = extension.getModelCoverage();
            } else {
                visibleRegion = getSourceViewer().getVisibleRegion();
            }
            if (idxEnd == LexerCacheForXPath.CHANGED_ALL) {
                start = visibleRegion.getOffset();
                difflen = visibleRegion.getLength();
            } else if (idxEnd == LexerCacheForXPath.CHANGED_ALL_FORWARD) {
                int start2 = visibleRegion.getOffset();
                difflen = start2 + visibleRegion.getLength() - start;
            } else {
                // shouldn't reach here.
                return;
            }
        }
        // make extra sure not trying to redraw region that does not exist yet
        // (bug 92962)
        int end = start + difflen;
        int actualEnd = getSourceViewer().getTextWidget().getCharCount();
        if (end > actualEnd) {
            difflen = actualEnd - start;
        }
        getSourceViewer().getTextWidget().redrawRange(start, difflen, true);

        // temp workaround until StyledText.redrawRange(int start, int length,
        // boolean clearBackground) is fixed.
        try {
            int lastLineStart =
                    getDocument().getLineOffset(getDocument()
                            .getLineOfOffset(start + difflen));
            int length = start + difflen - lastLineStart;
            getSourceViewer().getTextWidget().redrawRange(lastLineStart,
                    length,
                    true);
        } catch (BadLocationException exception) {
            // skip redrawing last line
        }
    }

    public void dispose() {
        XPathColorPreferences.removePropertyChangeListener(fPreferenceListener);

        if (fDocument != null) {
            fDocument.removeDocumentListener(this);
            LexerCacheForXPath.release(fDocument);
        }
    }
}
