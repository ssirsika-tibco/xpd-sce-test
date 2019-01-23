/**
 * 
 */
package com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor;

import com.tibco.xpd.processwidget.adapters.DiagramModelImageProvider;
import com.tibco.xpd.xpdl2.NamedElement;

/**
 * Refactor wizard info for refactor tasks as pageflow process wizard.
 * <p>
 * This is everything that the refactor as indi-sub-process wizard needs plus
 * the one participant that is referenced in the selected tasks or the one of
 * many referenced participants selected by user in config page..
 * 
 * @author aallway
 * @since 3.4.2 (20 Sep 2010)
 */
public class RefactorAsPageflowWizardInfo extends
        RefactorAsIndiSubprocWizardInfo {

    /* Field or Participant for invoke pageflow task. */
    public NamedElement pageflowTaskPerformer = null;

    /**
     * @param imageProvider
     */
    public RefactorAsPageflowWizardInfo(
            DiagramModelImageProvider imageProvider, String defaultSubProcName) {
        super(imageProvider, defaultSubProcName);
    }

}
