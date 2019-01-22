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
package com.tibco.xpd.script.sourceviewer.internal.viewer.listeners;

import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.swt.custom.LineStyleEvent;
import org.eclipse.swt.custom.LineStyleListener;


public abstract class AbstractLineStyleListener implements LineStyleListener, IDocumentListener {
    
    public AbstractLineStyleListener() {
        super();
    }
    /**
     * @see org.eclipse.jface.text.IDocumentListener#documentAboutToBeChanged(org.eclipse.jface.text.DocumentEvent)
     *
     * @param event
     */
    public abstract void documentAboutToBeChanged(DocumentEvent event);
    
    /**
     * @see org.eclipse.jface.text.IDocumentListener#documentChanged(org.eclipse.jface.text.DocumentEvent)
     *
     * @param event
     */
    public abstract void documentChanged(DocumentEvent event);
    
    /**
     * @see org.eclipse.swt.custom.LineStyleListener#lineGetStyle(org.eclipse.swt.custom.LineStyleEvent)
     *
     * @param event
     */
    public abstract void lineGetStyle(LineStyleEvent event);
    
    public abstract void dispose();
    
    public abstract void init(IDocument document, ISourceViewer sourceViewer);
    
}
