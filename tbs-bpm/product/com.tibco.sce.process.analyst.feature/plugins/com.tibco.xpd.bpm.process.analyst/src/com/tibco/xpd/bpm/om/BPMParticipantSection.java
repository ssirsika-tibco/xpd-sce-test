package com.tibco.xpd.bpm.om;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.tibco.xpd.analyst.resources.xpdl2.processeditorconfiguration.ProcessEditorConfigurationUtil;
import com.tibco.xpd.analyst.resources.xpdl2.processeditorconfiguration.ProcessEditorElementType;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.ParticipantGroup;
import com.tibco.xpd.ui.properties.AbstractTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.DecisionFlowUtil;

public class BPMParticipantSection extends AbstractTransactionalSection {

    BPMParticipantTable participantTable;

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = toolkit.createComposite(parent);
        root.setLayout(new FillLayout());

        participantTable =
                new BPMParticipantTable(root, toolkit, getEditingDomain());

        return root;
    }

    @Override
    public void setInput(Collection<?> items) {
        EObject input = null;

        if (items.size() == 1 && participantTable != null) {
            participantTable.setSubsiduaryProcessInput(null);

            Object selectedObject = items.iterator().next();

            /*
             * When selected object is process then set input to package so that
             * new partics are aded at package level BUT we will still show
             * partics from process by setting it as the subsiduary input.
             * 
             * But only if it really was the process selected NOT if the
             * ParticipantGroup was selected - in that case only show the actual
             * participants in that group.
             */
            if (selectedObject instanceof ParticipantGroup) {
                /*
                 * For the project explorer Participant Group folder show only
                 * teh items in that folder.
                 */
                selectedObject =
                        ((ParticipantGroup) selectedObject).getParent();
                if (selectedObject instanceof EObject) {
                    input = (EObject) selectedObject;
                }

            } else {
                /*
                 * If it's not an EObvject but is adaptable to such then do so.
                 */
                if (!(selectedObject instanceof EObject)
                        && selectedObject instanceof IAdaptable) {
                    selectedObject =
                            ((IAdaptable) selectedObject)
                                    .getAdapter(EObject.class);
                }

                if (selectedObject instanceof Process) {
                    /*
                     * Set input to package (so that table creates of new
                     * participants are added to package level.
                     */
                    input = ((Process) selectedObject).getPackage();

                    /*
                     * Tell table about subsiduary process so it includes it's
                     * participants in list.
                     */
                    participantTable
                            .setSubsiduaryProcessInput((Process) selectedObject);

                } else if (selectedObject instanceof Package) {
                    /*
                     * Package is fine as is.
                     */
                    input = (Package) selectedObject;
                }
            }

        }

        if (input == null) {
            super.setInput(Collections.emptyList());
        } else {
            super.setInput(Collections.singletonList(input));
        }
        return;

    }

    @Override
    protected void doRefresh() {
        EObject input = getInput();
        if (participantTable != null && participantTable.getViewer() != null) {
            participantTable.getViewer().cancelEditing();
            participantTable.getViewer().setInput(input);
        }
    }

    @Override
    protected Command doGetCommand(Object obj) {
        return null;
    }

    /**
     * Process/Package Participant section visibility filter.
     * <p>
     * Visible when Project Explorer participant group, Package/Process or
     * anything adaptable to package/process is selected.
     * 
     * 
     * @author aallway
     * @since 3.4.2 (26 Jul 2010)
     */
    public static class ParticipantSectionFilter implements IFilter {

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
         */
        @Override
        public boolean select(Object toTest) {
            EObject eo = null;

            if (toTest instanceof ParticipantGroup) {
                ParticipantGroup participantGroup = (ParticipantGroup) toTest;
                toTest = participantGroup.getParent();
            }

            if (toTest instanceof EObject) {
                eo = (EObject) toTest;
            } else if (toTest instanceof IAdaptable) {
                eo = (EObject) ((IAdaptable) toTest).getAdapter(EObject.class);
            }

            /*
             * Don't contribute section if it's decisions process or package.
             */
            if (eo != null && DecisionFlowUtil.isDecisionsContent(eo)) {
                return false;
            }

            if (eo instanceof Process || eo instanceof Package) {
                /*
                 * Sid XPD-2516: Don't show participants table section if
                 * processEditorConfiguration section has an active exclusion
                 * for participants.
                 */
                if (!ProcessEditorConfigurationUtil.getExcludedElementTypes(eo)
                        .contains(ProcessEditorElementType.participant)) {
                    /*
                     * DOn't show for wizard (in which case object won't have
                     * been added to resource yet.
                     */
                    /*
                     * XPD-1140: File may not be there in case of remote
                     * repository. eResource will be but won't be for wizard.
                     */
                    if (eo.eResource() != null) {
                        return true;
                    }
                }
            }

            return false;
        }
    }

}
