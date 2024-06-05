/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.script.sourceviewer.internal.viewer;

import java.util.Collections;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.DefaultInformationControl;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IInformationControl;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.source.IAnnotationHover;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
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
		implements IDisposable 
{


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
    @Override
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
            /* Sid ACE-8097 Use our own cusom info (popup)control creator */ 
            contentAssistant
                    .setInformationControlCreator(new XpdInformationControlCreator());
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

    @Override
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


	/**
	 * Studio-custom info control (for popup's), that adds wrap to the styling for the unfocused control
	 *
	 * Sid ACE-8097
	 * 
	 * @author aallway
	 * @since 29 May 2024
	 */
	private final class XpdInformationControl extends DefaultInformationControl
	{
		private Shell parent;

		/**
		 * @param parent
		 * @param textStyles
		 * @param presenter
		 * @param statusFieldText
		 */
		private XpdInformationControl(Shell parent)
		{
			super(parent, SWT.WRAP, null, null);
			this.parent = parent;

			setInfoControlSize(this);
		}

		/**
		 * Addition of SWT.WRAP messed up sizing of popup help. for some reason the underlying system then doesn't set a
		 * minimum width/height on the info control within the popup shell. This cause the popup help to be shrunk to
		 * the size of the help text rather than having minimum size.
		 * 
		 * As it is the underlying system's task to setup the layout data on the popup help contents, the only thing we
		 * can do is overwrite it here. It is understood that this isn't particularly good approach, as the layout could
		 * change from GridLayout at any point in the future (although unlikely). So we must be very careful not to make
		 * any assumptions about the popup shell's children or their layout.
		 */
		private void setInfoControlSize(DefaultInformationControl infoControl)
		{
			Shell infoShell = infoControl.getShell();

			if (infoShell != null)
			{
				Control[] children = infoShell.getChildren();
				if (children != null && children.length > 0)
				{
					Object layoutData = children[0].getLayoutData();

					if (layoutData instanceof GridData)
					{
						Point size = parent.getSize();

						GridData gd = new GridData();

						// Set same size as the popup itself
						gd.widthHint = size.x;
						gd.heightHint = size.y;

						children[0].setLayoutData(gd);
						infoShell.layout(true, true);
					}
				}
			}
		}

		/**
		 * @see org.eclipse.jface.text.DefaultInformationControl#getInformationPresenterControlCreator()
		 *
		 * @return
		 */
		@Override
		public IInformationControlCreator getInformationPresenterControlCreator()
		{
			/*
			 * Have to return our main class as the IInformationControlCreator from our implementation of
			 * DefaultInformationControl because when the popup help is first presented the underlying class asks our
			 * IInformationControlCreator (the main class here) to do createInformationControl().
			 * 
			 * However, when the popup help gains focus THEN it asks the currently displayed information control (i.e.
			 * this impl' of it) for the creator of info controls. And the DefaultInformationControl will return the
			 * original creator one from underlying system.
			 * 
			 * So if we don't override here to say 'use the ScriptSourceViewerConfiguration which created THIS info
			 * control, then we'd get the default original DefaultInformationControl with the default config, which
			 * doesn't have text wrapping.
			 */
			return parent -> {
				DefaultInformationControl defaultInformationControl = new DefaultInformationControl(parent,
						SWT.WRAP | SWT.V_SCROLL, null, null);

				return defaultInformationControl;
			};

		}

	}

	/**
	 * Info control creator that uses our custom {@link XpdInformationControl}
	 * 
	 * Sid ACE-8097
	 *
	 * @author aallway
	 * @since 29 May 2024
	 */
	private class XpdInformationControlCreator implements IInformationControlCreator
	{

		/**
		 * @see org.eclipse.jface.text.IInformationControlCreator#createInformationControl(org.eclipse.swt.widgets.Shell)
		 *
		 * @param parent
		 * @return
		 */
		@Override
		public IInformationControl createInformationControl(Shell parent)
		{
			XpdInformationControl infoControl = new XpdInformationControl(parent);

			return infoControl;
		}
	}

}
