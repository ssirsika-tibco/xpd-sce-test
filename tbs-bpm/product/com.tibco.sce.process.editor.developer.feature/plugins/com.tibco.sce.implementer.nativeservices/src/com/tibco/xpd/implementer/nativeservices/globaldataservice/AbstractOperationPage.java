/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.implementer.nativeservices.globaldataservice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.fieldassist.ContentProposal;
import org.eclipse.jface.fieldassist.IContentProposal;
import org.eclipse.jface.fieldassist.IContentProposalProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Text;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceData;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceDataUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDataUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.util.CommandContentAssistTextHandler;
import com.tibco.xpd.processeditor.xpdl2.properties.util.ContentAssistText;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.CaseAccessOperationsType;
import com.tibco.xpd.xpdExtension.CaseReferenceOperationsType;
import com.tibco.xpd.xpdExtension.GlobalDataOperation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.RecordType;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Abstract pagebook page for the controls of a Global Data operation in the
 * Global Data Task implementation properties section.
 * 
 * @author njpatel
 * @param <T>
 *            operations type {@link CaseAccessOperationsType} or
 *            {@link CaseReferenceOperationsType}
 */
public abstract class AbstractOperationPage<T extends EObject> {

    private String label;

    private final List<ContentAssistText> contentAssistTexts;

    private final List<Text> managedTexts;

    private final GlobalDataTaskServiceSection section;

    /**
     * Abstract page for the Global Data Operation implementation properties
     * section.
     */
    public AbstractOperationPage(String label,
            GlobalDataTaskServiceSection section) {
        this.label = label;
        this.section = section;
        contentAssistTexts = new ArrayList<ContentAssistText>();
        managedTexts = new ArrayList<Text>();
    }

    /**
     * Clear any cached values in this page. This implementation is empty.
     * Subclasses may extend.
     */
    public void reset() {

    }

    /**
     * Check if the value matches the content assist proposal.
     * 
     * @param value
     * @param contentAssistContents
     * @param contentAssistPosition
     * @return
     */
    public static boolean doesProposalMatch(String value,
            String contentAssistContents, int contentAssistPosition) {

        if (value != null && !value.isEmpty() && contentAssistContents != null
                && !contentAssistContents.isEmpty()) {
            String toMatch = null;

            if (!contentAssistContents.isEmpty()) {
                toMatch =
                        contentAssistPosition > 0 ? contentAssistContents
                                .substring(0, contentAssistPosition)
                                : contentAssistContents;
            }

            return toMatch == null || value.startsWith(toMatch);
        }

        return true;
    }

    /**
     * Get the {@link GlobalDataTaskServiceSection} parent.
     * 
     * @return the section
     */
    protected GlobalDataTaskServiceSection getSection() {
        return section;
    }

    /**
     * Update the page with the new details.
     * 
     * @param opType
     */
    public abstract void update(T opType);

    /**
     * Get command in response to a change in the given managed control.
     * 
     * @param editingDomain
     * @param opType
     * @param control
     * @return
     */
    public abstract Command getCommand(EditingDomain editingDomain, T opType,
            Object control);

    /**
     * Update text control with given text and preserve caret position
     * 
     * @param text
     * @param string
     */
    protected void updateText(Text text, String string) {
        if (text == null || text.isDisposed()) {
            return;
        }
        string = (string != null ? string : ""); //$NON-NLS-1$
        int position = text.getCaretPosition();
        text.setText(string);
        position = Math.min(position, string.length() + 1);
        text.setSelection(position);
    }

    /**
     * Get the class referenced in the given external reference.
     * 
     * @param project
     * @param extRef
     * @return
     */
    protected Class getReferencedClass(IProject project,
            ExternalReference extRef) {
        return (Class) ProcessUIUtil.getReferencedClassifier(extRef, project);
    }

    /**
     * Label of this section (added to the operation selection combo).
     * 
     * @return the label.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Create the controls of the page.
     * 
     * @param parent
     * @param toolkit
     * @return
     */
    public abstract Control createPage(Composite parent, XpdFormToolkit toolkit);

    /**
     * Create a content assist text control.
     * 
     * @param parent
     * @param toolkit
     * @param proposalProvider
     * @return
     */
    protected ContentAssistText createContentAssistText(Composite parent,
            XpdFormToolkit toolkit, IContentProposalProvider proposalProvider) {
        ContentAssistText text =
                new ContentAssistText(parent, toolkit, proposalProvider);
        return text;
    }

    /**
     * Manage the given text control.
     * 
     * @param txt
     */
    protected void manageTextControl(Text txt) {
        managedTexts.add(txt);
    }

    /**
     * Provides the same functionality for ContentAssistText fields as the
     * manageControl methods in AbstractXpdSection do for SWT Controls.
     * 
     * @param txt
     *            The content assist control to manage.
     */
    protected void manageControl(ContentAssistText txt) {
        new CommandContentAssistTextHandler(txt, section);
        contentAssistTexts.add(txt);
    }

    /**
     * @param control
     *            The control to check.
     * @return True if the page contains the control.
     */
    public boolean containsControl(Control control) {
        boolean contains = false;
        if (control != null) {
            contains = managedTexts.contains(control);
            if (!contains) {
                for (ContentAssistText txt : contentAssistTexts) {
                    if (Objects.equals(control, txt.getText())) {
                        contains = true;
                        break;
                    }
                }
            }
        }
        return contains;
    }

    /**
     * Get all the managed controls registered in this page.
     * 
     * @return
     */
    public List<Text> getManagedTextControls() {
        return managedTexts;
    }

    /**
     * @param toolkit
     * @param parent
     * @return control with some description text in that is wrapped with
     *         nominally enough space for a couple of lines
     */
    protected Control createWrappedDescriptionText(XpdFormToolkit toolkit,
            Composite parent, String text) {
        Composite c = toolkit.createComposite(parent);

        final Label description = toolkit.createLabel(c, text, SWT.WRAP);

        /*
         * Simple fill layout that once we have a definitive width to work on we
         * use the wrapped description label to calculate our required size
         * (because we're in a scrolled container any wrapped control needs to
         * have it's width constricted some how)
         */
        c.setLayout(new Layout() {
            Point cacheSize = new Point(1, 1);

            @Override
            protected void layout(Composite composite, boolean flushCache) {
                Point size = composite.getSize();

                description.setLocation(0, 0);
                description.setSize(size);
            }

            @Override
            protected Point computeSize(Composite composite, int wHint,
                    int hHint, boolean flushCache) {
                if (wHint > 0) {
                    cacheSize = description.computeSize(wHint, SWT.DEFAULT);
                }

                return cacheSize;
            }
        });
        return c;
    }

    /**
     * Content proposal for case ref associations.
     * 
     * @author njpatel
     */
    protected class CaseReferenceOperationAssociationProposalProvider implements
            IContentProposalProvider {

        /**
         * @see org.eclipse.jface.fieldassist.IContentProposalProvider#getProposals(java.lang.String,
         *      int)
         * 
         * @param contents
         * @param position
         * @return
         */
        @Override
        public IContentProposal[] getProposals(String contents, int position) {
            GlobalDataOperation dataOp = section.getGlobalDataOperation();
            if (dataOp != null) {
                CaseReferenceOperationsType referenceOperations =
                        dataOp.getCaseReferenceOperations();
                if (referenceOperations != null) {
                    String caseRefField = referenceOperations.getCaseRefField();
                    if (caseRefField != null && !caseRefField.isEmpty()) {
                        Class caseClassRef = getCaseClassRef(caseRefField);

                        if (caseClassRef != null) {
                            List<IContentProposal> proposals =
                                    new ArrayList<IContentProposal>();
                            /*
                             * XPD-6049: Saket: Need to consider attributes from
                             * super classes as well.
                             */
                            for (Property prop : caseClassRef
                                    .getAllAttributes()) {
                                /*
                                 * if case ref association
                                 */
                                if (prop.getAssociation() != null
                                        && prop.getType() instanceof Class
                                        && BOMGlobalDataUtils
                                                .isCaseClass((Class) prop
                                                        .getType())) {

                                    String displayName =
                                            PrimitivesUtil
                                                    .getDisplayLabel(prop);

                                    if (doesProposalMatch(prop.getName(),
                                            contents,
                                            position)) {
                                        proposals.add(new ContentProposal(prop
                                                .getName(), displayName));
                                    }
                                }
                            }

                            return proposals
                                    .toArray(new IContentProposal[proposals
                                            .size()]);
                        }
                    }
                }
            }
            return new IContentProposal[0];
        }

        /**
         * Get the case class referenced by the process data field with the
         * given name.
         * 
         * @param fieldName
         *            process data field name
         * @return
         */
        private Class getCaseClassRef(String fieldName) {
            ProcessRelevantData data = findProcessRelevantData(fieldName);
            if (data != null && data.getDataType() instanceof RecordType) {
                RecordType type = (RecordType) data.getDataType();
                if (type.getMember() != null && !type.getMember().isEmpty()) {
                    ExternalReference reference =
                            type.getMember().get(0).getExternalReference();
                    if (reference != null) {
                        return getReferencedClass(WorkingCopyUtil.getProjectFor(data),
                                reference);
                    }
                }
            }

            return null;
        }

        /**
         * Find the process data field with the given name within the scope if
         * this activity.
         * 
         * @param name
         * @return
         */
        private ProcessRelevantData findProcessRelevantData(String name) {
            if (name != null) {
                List<ProcessRelevantData> relevantData =
                        ProcessDataUtil.getProcessRelevantData(section
                                .getInput());
                for (ProcessRelevantData data : relevantData) {
                    if (name.equals(data.getName())) {
                        return data;
                    }
                }
            }
            return null;
        }

    }

    /**
     * Content proposal for case reference data types.
     * 
     * @author njpatel
     */
    public static abstract class CaseRefDataTypeProposalProvider implements
            IContentProposalProvider {

        private final boolean allowDrillDown;

        /**
         * Content proposal for case reference data types. This will also allow
         * drill down to the properties (same as calling
         * <code>CaseRefDataTypeProposalProvider(true)</code>).
         */
        public CaseRefDataTypeProposalProvider() {
            this(true);
        }

        /**
         * Content proposal for case reference data types.
         * 
         * @param allowDrillDown
         *            <code>true</code> to allow drilling down into properties
         *            (e.g. CustomerRef.addressRef).
         */
        public CaseRefDataTypeProposalProvider(boolean allowDrillDown) {
            this.allowDrillDown = allowDrillDown;
        }

        @Override
        public IContentProposal[] getProposals(String contents, int position) {
            List<ContentProposal> proposals = new ArrayList<ContentProposal>();

            if (allowDrillDown
                    && (position > 0 && contents.substring(0, position)
                            .contains(ConceptPath.CONCEPTPATH_SEPARATOR))) {
                /*
                 * User has entered a path to an attribute so use concept path
                 * to resolve it
                 */
                String contentAssistText = contents.substring(0, position);
                // Concept path
                ConceptPath cPath = null;
                cPath =
                        ConceptUtil
                                .resolveConceptPath(getInput(),
                                        contentAssistText.substring(0,
                                                contentAssistText
                                                        .lastIndexOf(ConceptPath.CONCEPTPATH_SEPARATOR)));

                if (cPath != null) {
                    int lastIdx =
                            contentAssistText
                                    .lastIndexOf(ConceptPath.CONCEPTPATH_SEPARATOR) + 1;
                    String attrPartText = ""; //$NON-NLS-1$

                    if (lastIdx < position) {
                        attrPartText = contentAssistText.substring(lastIdx);
                    }
                    for (ConceptPath child : cPath.getChildren()) {
                        if (attrPartText.isEmpty()
                                || child.getName().startsWith(attrPartText)) {

                            // Only allow case class types
                            if (child.getType() instanceof Class
                                    && BOMGlobalDataUtils
                                            .isCaseClass((Class) child
                                                    .getType())) {
                                proposals.add(new ContentProposal(child
                                        .getPath()));
                            }
                        }
                    }
                }

            } else {
                /*
                 * Get all the data fields which match the content of the
                 * content assist field
                 */
                Set<ProcessRelevantData> dataInScope =
                        getCaseRefDataTypesInScope();

                for (ProcessRelevantData data : dataInScope) {
                    String displayName = Xpdl2ModelUtil.getDisplayName(data);

                    if (doesProposalMatch(data.getName(), contents, position)) {
                        proposals.add(new ContentProposal(data.getName(),
                                displayName));
                    }
                }
            }

            return proposals.toArray(new IContentProposal[proposals.size()]);
        }

        /**
         * Get the input of the section.
         * 
         * @return
         */
        protected abstract Activity getInput();

        /**
         * Get all Case class reference data types that are in scope of the
         * input object.
         * 
         * @return
         */
        private Set<ProcessRelevantData> getCaseRefDataTypesInScope() {
            Set<ProcessRelevantData> items = new HashSet<ProcessRelevantData>();

            Collection<ActivityInterfaceData> activityData =
                    ActivityInterfaceDataUtil
                            .getActivityInterfaceData(getInput());

            for (ActivityInterfaceData data : activityData) {
                if (data.getData() != null) {
                    DataType dataType = data.getData().getDataType();
                    if (dataType instanceof RecordType) {
                        items.add(data.getData());
                    }
                }
            }

            return items;
        }

    }

    /**
     * Proposal provider for the 'local' process data (i.e. doesn't include case
     * reference fields).
     * 
     * @author njpatel
     */
    protected final class LocalDataProposalProvider implements
            IContentProposalProvider {

        private final boolean includeSimpleTypes;

        /**
         * Proposal provider for the 'local' process data.
         */
        public LocalDataProposalProvider(boolean includeSimpleTypes) {
            this.includeSimpleTypes = includeSimpleTypes;
        }

        @Override
        public IContentProposal[] getProposals(String contents, int position) {
            Set<ProcessRelevantData> localDataTypesInScope =
                    getDataTypesInScope(includeSimpleTypes);

            List<ContentProposal> proposals = new ArrayList<ContentProposal>();

            if (position > 0
                    && contents.substring(0, position)
                            .contains(ConceptPath.CONCEPTPATH_SEPARATOR)) {
                String contentAssistText = contents.substring(0, position);
                // Concept path
                ConceptPath cPath =
                        ConceptUtil
                                .resolveConceptPath((Activity) section
                                        .getInput(),
                                        contentAssistText.substring(0,
                                                contentAssistText
                                                        .lastIndexOf(ConceptPath.CONCEPTPATH_SEPARATOR)));

                if (cPath != null) {
                    int lastIdx =
                            contentAssistText
                                    .lastIndexOf(ConceptPath.CONCEPTPATH_SEPARATOR) + 1;
                    String attrPartText = ""; //$NON-NLS-1$

                    if (lastIdx < position) {
                        attrPartText = contentAssistText.substring(lastIdx);
                    }
                    for (ConceptPath child : cPath.getChildren()) {
                        if (attrPartText.isEmpty()
                                || child.getName().startsWith(attrPartText)) {
                            proposals.add(new ContentProposal(child.getPath()));
                        }
                    }
                }

            } else {

                for (ProcessRelevantData data : localDataTypesInScope) {
                    String displayName = Xpdl2ModelUtil.getDisplayName(data);

                    if (doesProposalMatch(data.getName(), contents, position)) {
                        proposals.add(new ContentProposal(data.getName(),
                                displayName));
                    }
                }
            }

            return proposals.toArray(new IContentProposal[proposals.size()]);
        }

        /**
         * Get all Local class reference data types that are in scope of this
         * section's input object.
         * 
         * @param includeSimpleTypes
         *            <code>true</code> to include simple types.
         * @return
         */
        private Set<ProcessRelevantData> getDataTypesInScope(
                boolean includeSimpleTypes) {
            Set<ProcessRelevantData> items = new HashSet<ProcessRelevantData>();

            Collection<ActivityInterfaceData> activityData =
                    ActivityInterfaceDataUtil
                            .getActivityInterfaceData((Activity) section
                                    .getInput());

            for (ActivityInterfaceData data : activityData) {
                if (data.getData() != null) {
                    DataType dataType = data.getData().getDataType();
                    if (dataType instanceof BasicType) {
                        if (includeSimpleTypes) {
                            items.add(data.getData());
                        }
                    } else if (dataType instanceof ExternalReference) {
                        items.add(data.getData());
                    }
                }
            }

            return items;
        }
    }
}
