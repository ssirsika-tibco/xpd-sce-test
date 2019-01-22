package com.tibco.xpd.script.sourceviewer.client;

import java.util.List;

import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.texteditor.ResourceMarkerAnnotationModel;

import com.tibco.xpd.script.model.client.JsClassDefinitionReader;
import com.tibco.xpd.script.parser.validator.IValidationStrategy;
import com.tibco.xpd.script.sourceviewer.internal.client.IDisposable;

public interface ScriptEditor extends IDisposable {
    /**
     * This method sets the passed string as the input for the script editor.
     * 
     * @param strScript
     */
    void setInputString(String strScript);

    /**
     * This method will install the commandProvider so that updated string can
     * be saved.
     * 
     * @param provider
     */
    void installCommandProvider(CommandProvider provider);

    /**
     * This method will install the siteProvider which the scripteditor needs to
     * register the context menu.
     * 
     * @param siteProvider
     */
    void installSiteProvider(ISiteProvider siteProvider);

    /**
     * This method will install the scriptInfoProvider which the
     * contentAssistProcessor needs to query for showing the relevant and
     * UMLRelevant classes.
     * 
     * @param scriptInfoProvider
     */
    void installScriptInfoProvider(IScriptInfoProvider scriptInfoProvider);

    /**
     * This method will install the scriptCompiler which will compile the
     * script.
     * 
     * @param scriptCompiler
     */
    void installScriptCompiler(IScriptCompiler scriptCompiler);

    /**
     * This method returns the Control underneath the editor
     * 
     * @return
     */
    Control getControl();

    /**
     * This method should be called to configure the script editor
     * 
     */
    void configure();

    /**
     * This method should be called to refresh the editor with the nee input.
     * 
     */
    void doRefresh();

    /**
     * Allows to register definitionReaders with the source viewer.
     * 
     * @param definitionReader
     */
    void installClassDefinitionReaders(
            List<JsClassDefinitionReader> definitionReaderList);

    /**
     * Register Resource Marker Annotation Model.
     * 
     * @param annotationModel
     */
    void installResourceMarkerAnnotationModel(
            ResourceMarkerAnnotationModel annotationModel);

    /**
     * Set the flag to enable the undo and redo actions.
     * 
     * @param enableUndoRedoActions
     *            flag
     */
    public void setEnableUndoRedoActions(boolean enableUndoRedoActions);

    /**
     * Set the flag to enable the copy and paste actions.
     * 
     * @param enableCopyAndPasteActions
     *            flag
     */
    public void setEnableCopyAndPasteActions(boolean enableCopyAndPasteActions);

    /**
     * Set the flag to enable the content assist actions.
     * 
     * @param enableContentAssistActions
     *            flag
     */
    public void setEnableContentAssistActions(boolean enableContentAssistActions);

    public void setIsNewScript(boolean isNewScript);

    public void setScriptSelectionChanged(boolean isScriptSelectionChanged);

    public boolean getScriptSelectionChanged();
    
    public void installValidationStrategies(
            List<IValidationStrategy> validationStrategyList);

}
