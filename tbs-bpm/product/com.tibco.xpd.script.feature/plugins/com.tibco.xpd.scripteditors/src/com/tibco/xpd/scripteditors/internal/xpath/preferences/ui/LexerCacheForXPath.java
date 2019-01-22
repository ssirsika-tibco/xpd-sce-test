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

import java.util.Hashtable;

import org.eclipse.wst.javascript.core.internal.jsparser.node.Token;
import org.eclipse.wst.javascript.core.internal.langlexer.ITokenCache;

import com.tibco.xpd.script.sourceviewer.internal.contentassist.TextRange;
import com.tibco.xpd.scripteditors.internal.xpath.styles.LineStyleProviderForXPath;

/**
 * This is a common class for parsing (actually lexical analyzing) a node.
 * This class also caches results from parses and expects to be told about
 * modifications to the nodes that might affect the validity of the cache.
 *
 * Creation date: (7/6/2001 5:41:19 PM)
 * @author: Jason Crawford n David Williams
 */
public class LexerCacheForXPath implements ITokenCache {

	private static Hashtable htCaches = new Hashtable();
	public static final int CHANGED_NONE = -1;
	public static final int CHANGED_ALL = -2;
	public static final int CHANGED_ALL_FORWARD = -3;

	int lenFuncTreeUnchangedAtFront = 987654321;
	int lenFuncTreeUnchangedAtBack = 987654321;
	int lenBufForOldFNV = 0;


	private final java.util.TreeSet tsTokCache = new java.util.TreeSet(new java.util.Comparator() {
		public boolean equals(Object obj) {
			return (this == obj);
		}

		public int compare(Object obj1, Object obj2) {
			int so1 = ((Token) obj1).getLPOffset();
			int so2 = ((Token) obj2).getLPOffset();
			if (so1 == so2) {
				return 0;
			}
			return (so1 < so2) ? -1 : 1;
		}
	});

	/**
	 * NodeParserForXPath constructor comment.
	 */
	private LexerCacheForXPath() {
		super();
	}

	public void add(Token tk) {
		tsTokCache.add(tk);
	}

	public String[] getKeyKeywordArray() {
		return LineStyleProviderForXPath.keywords;
	}

	public static LexerCacheForXPath getCache(Object key, String strInit) {
		// todo: look into using java.util.SoftReference
		Object val = htCaches.get(key);
		if (val == null) {
			// todo: technically we should sync this to make it thread-safe
			LexerCacheForXPath lc = new LexerCacheForXPath();
			lc.notifyChange(strInit, 0, 0, 0);//$NON-NLS-1$
			htCaches.put(key, lc);
			return lc;
		}
		else {
			return (LexerCacheForXPath) val;
		}
	}

	/**
	 do not call this method unless you're in contact with the author.  It is likely
	 to change since it's semantics don't match those of potential callers.  A more
	 efficient version is also possible.
	 */
	public TextRange notifyChange(String strNew, int posChange, int lenOld, int lenNew) {
	    return new TextRange(posChange, CHANGED_ALL_FORWARD); // all change forward
	}


	public void remove(Token tk) {
		tsTokCache.remove(tk);
	}

	public static void release(Object key) {
		if (key != null) {
			htCaches.remove(key);
		}
	}

	public static void releaseAll() {

		htCaches.clear();

	}

}
