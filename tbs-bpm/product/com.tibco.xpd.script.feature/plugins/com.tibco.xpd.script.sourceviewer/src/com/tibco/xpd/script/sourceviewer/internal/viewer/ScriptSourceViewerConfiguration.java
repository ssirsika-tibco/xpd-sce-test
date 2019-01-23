/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.script.sourceviewer.internal.viewer;

import java.util.Collections;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.source.IAnnotationHover;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditorPreferenceConstants;

import com.tibco.xpd.script.model.client.JsClassDefinitionReader;
import com.tibco.xpd.script.parser.validator.IValidationStrategy;
import com.tibco.xpd.script.sourceviewer.client.IScriptInfoProvider;
import com.tibco.xpd.script.sourceviewer.internal.client.IDisposable;
import com.tibco.xpd.script.sourceviewer.internal.contentassist.AbstractTibcoContentAssistProcessor;
import com.tibco.xpd.script.sourceviewer.internal.util.PreferenceUtils;

/**
 * @author rsomayaj
 * 
 */
public class ScriptSourceViewerConfiguration extends SourceViewerConfiguration
        implements IDisposable {

    private IAnnotationHover hover = null;

    private IPreferenceStore fPreferenceStore =
            PreferenceUtils.getPreferenceStore();

    private AbstractTibcoContentAssistProcessor contentAssistantProcessor;

    private String grammarType;

    /**
     * Returns the content assistant ready to be used with the given source
     * viewer. This implementation always returns <code>null</code>.
     * 
     * @param sourceViewer
     *            the source viewer to be configured by this configuration
     * @return a content assistant or <code>null</code> if content assist should
     *         not be supported
     */
    public IContentAssistant getContentAssistant(ISourceViewer sourceViewer) {

        // Ensure that only one assistant is ever returned. Creating a
        // second assistant
        // that is added to a viewer can cause odd key-eating by the wrong
        // one.
        ContentAssistant contentAssistant = new ContentAssistant();
        contentAssistant
                .setDocumentPartitioning(getConfiguredDocumentPartitioning(sourceViewer));
        // IContentAssistProcessor contentAssistantProcessor = new
        // JavaScriptContentAssistProcessor();

        // TODO - Ravi - Don't read Content Assist processor from this plugin
        // contentAssistantProcessor =
        // Activator.getDefault()
        // .getContentAssistProcessor(getGrammarType());
        contentAssistantProcessor = getContentAssistProcessor();
        if (contentAssistantProcessor != null) {
            contentAssistantProcessor
                    .setScriptInfoProvider(getScriptInfoProvider());
            contentAssistantProcessor
                    .setClassDefinitionReaders(getClassDefinitionReaders());
            contentAssistantProcessor
                    .setValidationStrategyList(getValidationStrategyList());
            contentAssistant
                    .setContentAssistProcessor(contentAssistantProcessor,
                            IDocument.DEFAULT_CONTENT_TYPE);
            // passing on the process destinations to the ContentAssistProcessor
            contentAssistant.enableAutoActivation(true);
            contentAssistant.setAutoActivationDelay(500);
            contentAssistant
                    .setProposalPopupOrientation(IContentAssistant.PROPOSAL_OVERLAY);
            contentAssistant
                    .setContextInformationPopupOrientation(IContentAssistant.CONTEXT_INFO_ABOVE);
            contentAssistant
                    .setInformationControlCreator(getInformationControlCreator(sourceViewer));
        }
        return contentAssistant;
    }

    @Override
    public IAnnotationHover getAnnotationHover(ISourceViewer sourceViewer) {
        if (hover == null) {
            hover = createAnnotationHover();
        }
        return hover;
    }

    @Override
    public IAnnotationHover getOverviewRulerAnnotationHover(
            ISourceViewer sourceViewer) {
        if (hover == null) {
            hover = createAnnotationHover();
        }
        return hover;
    }

    @Override
    public int getTabWidth(ISourceViewer sourceViewer) {
        return fPreferenceStore
                .getInt(AbstractDecoratedTextEditorPreferenceConstants.EDITOR_TAB_WIDTH);
    }

    private IAnnotationHover createAnnotationHover() {
        ScriptAnnotationHover hover = new ScriptAnnotationHover();
        return hover;
    }

    public void dispose() {
        if (contentAssistantProcessor != null
                && contentAssistantProcessor instanceof IDisposable) {
            ((IDisposable) contentAssistantProcessor).dispose();
        }
    }

    private IScriptInfoProvider scriptInfoProvider = null;

    public void setScriptInfoProvider(IScriptInfoProvider scriptInfoProvider) {
        this.scriptInfoProvider = scriptInfoProvider;
    }

    public IScriptInfoProvider getScriptInfoProvider() {
        return this.scriptInfoProvider;
    }

    private List<JsClassDefinitionReader> definitionReaderList =
            Collections.EMPTY_LIST;

    public void setClassDefinitionReaders(
            List<JsClassDefinitionReader> definitionReaderList) {
        this.definitionReaderList = definitionReaderList;
    }

    public List<JsClassDefinitionReader> getClassDefinitionReaders() {
        return this.definitionReaderList;
    }
    
    private List<IValidationStrategy> validationStrategyList =
        Collections.EMPTY_LIST;

    public void setValidationStrategyList(
            List<IValidationStrategy> validationStrategyList) {
        this.validationStrategyList = validationStrategyList;
    }

    public List<IValidationStrategy> getValidationStrategyList() {
        return this.validationStrategyList;
    }

    public String getGrammarType() {
        return grammarType;
    }

    public void setGrammarType(String grammarType) {
        this.grammarType = grammarType;
    }

    /**
     * @param contentAssistProcessor
     */
    public void setContentAssistProcessor(
            AbstractTibcoContentAssistProcessor contentAssistProcessor) {
        this.contentAssistantProcessor = contentAssistProcessor;
    }

    /**
     * 
     * @return
     */
    public AbstractTibcoContentAssistProcessor getContentAssistProcessor() {
        return this.contentAssistantProcessor;
    }

}
