/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.implementer.nativeservices.documentoperationsservice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edit.command.SetCommand;
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
import org.eclipse.ui.part.PageBook;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceData;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceDataUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.util.ContentAssistText;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.CaseDocFindOperations;
import com.tibco.xpd.xpdExtension.CaseDocRefOperations;
import com.tibco.xpd.xpdExtension.DocumentOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.RecordType;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 
 * Abstract Base class for the Case Documents Operations Pages.
 * 
 * @author aprasad
 * @since 12-Aug-2014
 */
public abstract class AbstractDocumentOperationPage<T extends EObject>
        implements Comparable {

    /**
     * Description Label
     */
    private String descLabel;

    /**
     * Parent Detail Property Section of the Case Document Service Task to which
     * this page is contributed.
     */
    private final DocumentOperationsTaskServiceSection parentDetailSection;

    /**
     * Set of child controls which are managed by this section page.
     */
    private Set<Control> managedControls;

    /**
     * Set of child ContentAssistText controls which are managed by this section
     * page.
     */
    private Set<ContentAssistText> managedContentAssists;

    /**
     * Root Container of the Code.
     */
    protected Composite rootContainer;

    /**
     * {@link XpdExtensionFactory} instance.
     */
    protected static final XpdExtensionFactory XPD_EXT_FAC =
            XpdExtensionFactory.eINSTANCE;

    /**
     * {@link XpdExtensionPackage} instance.
     */
    protected static final XpdExtensionPackage XPD_EXT_PKG =
            XpdExtensionPackage.eINSTANCE;

    /**
     * Abstract page for the Case Document Operation implementation properties
     * section.
     * 
     * @param label
     *            Page Description Label.
     * @param section
     *            Parent Details Section to which this page is added.
     */
    public AbstractDocumentOperationPage(String label,
            DocumentOperationsTaskServiceSection section) {

        this.descLabel = label;
        this.parentDetailSection = section;
        this.managedControls = new HashSet<Control>();
        this.managedContentAssists = new HashSet<ContentAssistText>();
    }

    /**
     * Abstract page for the Case Document Operation implementation properties
     * section.
     * 
     * @param label
     *            Page Description Label.
     */
    public AbstractDocumentOperationPage(String label) {

        this.descLabel = label;
        this.parentDetailSection = null;
        this.managedControls = new HashSet<Control>();
    }

    /**
     * Returns the Case Document Operation Feature for the Operation this Page
     * represents.
     * 
     * @return operationFeatureRef Feature Reference of the Case Document
     *         Operation for which this Page is created.
     */
    abstract public EReference getOperationFeatureRef();

    /**
     * Returns the root container of the Page.
     * 
     * @return the rootContainer
     */
    public Composite getRootContainer() {
        return rootContainer;
    }

    /**
     * Update the page with the new details.
     * 
     * @param caseDocumentOperation
     */
    public abstract void update(T caseDocumentOperation);

    /**
     * Get command in response to a change in the given managed control.
     * 
     * @param editingDomain
     * @param caseDocumentOperation
     * @param control
     *            Control to get the Command for.
     * @return Valid Command or null if invalid.
     */
    public abstract Command getCommand(EditingDomain editingDomain,
            T caseDocumentOperation, Object control);

    /**
     * Label of this section (added to the operation selection combo).
     * 
     * @return the label.
     */
    public String getLabel() {
        return descLabel;
    }

    /**
     * Create the controls of the page.
     * 
     * @param parent
     * @param toolkit
     * @return Control for this Page, to be displayed in the PageBook of the
     *         Parent Detail Section.
     */
    public abstract Control createPage(Composite parent, XpdFormToolkit toolkit);

    /**
     * Creates the wrapped Description Control for the page.
     * 
     * @param toolkit
     * @param parent
     * @param text
     *            Description text.
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
     * Adds Control to the the set of managed Controls.
     * 
     * @param control
     */
    protected void manageControl(Control control) {

        if (managedControls == null) {
            managedControls = new HashSet<Control>();
        }
        managedControls.add(control);
    }

    protected void manageControl(final ContentAssistText control) {
        if (managedContentAssists == null) {
            managedContentAssists = new HashSet<ContentAssistText>();
        }
        managedContentAssists.add(control);
    }

    /**
     * @param obj
     *            The control to check.
     * @return true if this
     */
    public boolean containsControl(Control control) {
        boolean contains = false;
        if (control != null) {
            contains = managedControls.contains(control);
            if (!contains) {
                for (ContentAssistText ca : managedContentAssists) {
                    if (control.equals(ca.getText())) {
                        contains = true;
                        break;
                    }
                }
            }
        }
        return contains;
    }

    /**
     * @return
     */
    public Set<ContentAssistText> getContentAssistTexts() {
        return managedContentAssists;
    }

    /**
     * Returns the set of managed controls for this page.
     * 
     * @return the managedControls
     */
    public Set<Control> getManagedControls() {
        return managedControls;
    }

    /**
     * Update text control with given text and preserve caret position.This
     * method handles the null String, by setting empty string value to the text
     * when the passed String is null.
     * 
     * @param text
     *            Text Control to update.
     * @param string
     *            value to update the Text Control with.
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
     * @see java.lang.Comparable#compareTo(java.lang.Object) Compares the Pages
     *      based on the Label, to support sorting in the Operations Drop Down
     *      picker.
     * @param o
     *            AbstractDocumentOperationPage instace to Compare with.
     * @return Comparison result between the Labels of the
     *         AbstractDocumentOperationPage, to sort them alphabetical on their
     *         Label.Returns 0 if o is not of type
     *         AbstractDocumentOperationPage.
     */
    @Override
    public int compareTo(Object o) {

        if (o instanceof AbstractDocumentOperationPage) {
            String label1 = getLabel();
            String label2 = ((AbstractDocumentOperationPage) o).getLabel();
            if (label1 == null) {
                return 0;
            }
            return label1.compareTo(label2);
        }
        return 0;
    }

    /**
     * This method returns a Set Command , only if the value has changed, i.e
     * new value is not same as the old value, thus saving the caller from
     * testing the same and running unwanted commands.
     * 
     * @param domain
     *            the editing domain.
     * @param owner
     *            the owner to which the new value is to be set.
     * @param feature
     *            the feature of the owner to set the value for.
     * @param newValue
     *            the new value of the feature.
     * @param oldValue
     *            the old value of the feature.
     * @param commandLabel
     *            the label for the command.
     * @return a command to set the given value for the designated feature if
     *         the owner, returns null when the old and new values are equal.
     */
    public static Command getSetCommand(EditingDomain domain, Object owner,
            Object feature, Object newValue, Object oldValue,
            String commandLabel) {

        /*
         * Return Command if the new value is either null , to remove the
         * element or not equal to the old value
         */
        if ((newValue == null) || !newValue.equals(oldValue)) {

            CompoundCommand command = new CompoundCommand(commandLabel);
            command.append(SetCommand.create(domain, owner, feature, newValue));
            return command;
        }
        return null;
    }

    /**
     * Returns the Parent Detail Section which contains the {@link PageBook} for
     * this Page.
     * 
     * @return parentDetailSection containing the {@link PageBook} for this
     *         Page.
     */
    public DocumentOperationsTaskServiceSection getParentDetailSection() {
        return parentDetailSection;
    }

    /**
     * Check if the value matches the content assist proposal.
     * 
     * @param value
     * @param contentAssistContents
     * @param contentAssistPosition
     * @return true if the proposal matches the value .
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
     * Proposal Provider Abstract class to provide proposal for the Process data
     * in scope of the Activity, matching the allowed/supported DataType.
     * 
     * @author aprasad
     * @since 04-Sep-2014
     */
    public abstract static class CaseDocumentOperationProposalProvider
            implements IContentProposalProvider {

        /**
         * Page containing the Controls, for which Proposal has to be provided.
         */
        AbstractDocumentOperationPage<EObject> page;

        /**
         * Constructor takes Page containing the Controls, for which Proposal
         * has to be provided, as the argument.
         * 
         * @param page
         *            containing the Controls, for which Proposal has to be
         *            provided.
         */
        public CaseDocumentOperationProposalProvider(
                AbstractDocumentOperationPage page) {

            this.page = page;
        }

        /**
         * @return Input Activity for this Page, uses the input of the Parent
         *         Detail Section.
         */
        protected Activity getInput() {

            EObject input = page.getParentDetailSection().getInput();

            if (input instanceof Activity) {
                return (Activity) input;
            }
            return null;
        }

        /**
         * 
         * Checks if the given dataType is valid for this Proposal provider,
         * which is used to filter the proposal Data Fields.
         * 
         * @return true , if the given DataType is valid for the Proposal
         *         provider.The Implementing classes should validate to allow
         *         only supported DataTypes in the proposal.
         */
        protected abstract boolean isValidDataType(DataType dataType);

        /**
         * Checks if the Proposal should show only Single values Data fields in
         * the proposal, by default it is true, the implementing classes should
         * override this to allow multi valued Data in the proposal.
         * 
         * @return true if the Proposal should support only Single instance
         *         Fields.Default is true, the Implementation of this Abstract
         *         class should override this to allow Multiple instance input
         *         in proposal.
         */
        protected boolean isSingleInstance() {
            return true;
        }

        /**
         * Get all Data Fields that are in scope of the input object fit for
         * proposal.Filters the Data Fields based on isValidDataType() and
         * isSingleInstance().
         * 
         * @return Set of {@link ProcessRelevantData} in scope of the Activity
         *         input of this Page.
         */

        protected Set<ProcessRelevantData> getDataTypesInScopeFilteredForProposal() {
            Set<ProcessRelevantData> items = new HashSet<ProcessRelevantData>();

            Collection<ActivityInterfaceData> activityData =
                    ActivityInterfaceDataUtil
                            .getActivityInterfaceData(getInput());

            for (ActivityInterfaceData data : activityData) {

                if (data.getData() != null) {
                    DataType dataType = data.getData().getDataType();

                    /* Filter on Valid Data Type */
                    if (isValidDataType(dataType)) {

                        /* Filter on Multiplicity */
                        if (isSingleInstance()) {
                            if (!data.getData().isIsArray()) {
                                items.add(data.getData());
                            }
                        } else {
                            items.add(data.getData());
                        }
                    }
                }
            }

            return items;
        }

        /**
         * @see org.eclipse.jface.fieldassist.IContentProposalProvider#getProposals(java.lang.String,
         *      int)
         * 
         * @param contents
         *            to match
         * @param position
         *            current position of the content assist.
         * @return Array of Proposals matching the criteria of DataType ,
         *         Multiplicity and content position.
         */
        @Override
        public IContentProposal[] getProposals(String contents, int position) {
            List<ContentProposal> proposals = new ArrayList<ContentProposal>();
            /*
             * Get all the data fields which match the content of the content
             * assist field
             */
            Set<ProcessRelevantData> dataInScope =
                    getDataTypesInScopeFilteredForProposal();

            for (ProcessRelevantData data : dataInScope) {
                String displayName = Xpdl2ModelUtil.getDisplayName(data);

                if (doesProposalMatch(data.getName(), contents, position)) {

                    proposals.add(new ContentProposal(data.getName(),
                            displayName));
                }
            }

            return proposals.toArray(new IContentProposal[proposals.size()]);
        }

    }

    /**
     * Proposal provider for Case Reference type Data Fields.
     * 
     * @author aprasad
     * @since 04-Sep-2014
     */
    public static class CaseRefTypeProposalProvider extends
            CaseDocumentOperationProposalProvider {

        public CaseRefTypeProposalProvider(AbstractDocumentOperationPage page) {
            super(page);
        }

        /**
         * @see com.tibco.xpd.implementer.nativeservices.documentoperationsservice.AbstractDocumentOperationPage.CaseDocumentOperationProposalProvider#isValidDataType(com.tibco.xpd.xpdl2.DataType)
         * 
         * @param dataType
         *            type to check.
         * @return true if this is a Record Type, which is a Case Reference
         *         type.
         */
        @Override
        protected boolean isValidDataType(DataType dataType) {
            return (dataType instanceof RecordType);
        }

    }

    /**
     * Proposal provider for the Document Reference Data Type. Document
     * Reference for now is supported as Text Field, hence provides proposal of
     * Text Fields.
     * 
     * @author aprasad
     * @since 04-Sep-2014
     */
    public static class DocRefTypeProposalProvider extends
            CaseDocumentOperationProposalProvider {

        public DocRefTypeProposalProvider(AbstractDocumentOperationPage page) {
            super(page);
        }

        /**
         * @see com.tibco.xpd.implementer.nativeservices.documentoperationsservice.AbstractDocumentOperationPage.CaseDocumentOperationProposalProvider#isValidDataType(com.tibco.xpd.xpdl2.DataType)
         * 
         * @param dataType
         *            type to check.
         * @return true only if the Data Type is Text type, for now Document
         *         Reference is supported as Text Type.
         */
        @Override
        protected boolean isValidDataType(DataType dataType) {
            /* Document Reference for now is allowed text fields */
            return ((dataType instanceof BasicType) && (BasicTypeType.STRING_LITERAL
                    .equals(((BasicType) dataType).getType())));
        }

    }

    /**
     * Proposal provider for FileName Content, which is uspported as Text
     * Fields.
     * 
     * @author aprasad
     * @since 04-Sep-2014
     */
    public static class TextTypeProposalProvider extends
            CaseDocumentOperationProposalProvider {

        public TextTypeProposalProvider(AbstractDocumentOperationPage page) {
            super(page);
        }

        /**
         * @see com.tibco.xpd.implementer.nativeservices.documentoperationsservice.AbstractDocumentOperationPage.CaseDocumentOperationProposalProvider#isValidDataType(com.tibco.xpd.xpdl2.DataType)
         * 
         * @param dataType
         *            type to check.
         * @return true only if the Data Type is Text type,
         */
        @Override
        protected boolean isValidDataType(DataType dataType) {
            /* File Name is allowed text fields */
            return ((dataType instanceof BasicType) && (BasicTypeType.STRING_LITERAL
                    .equals(((BasicType) dataType).getType())));
        }

    }

    /**
     * Returns a valid command to set the Operation, if the operationFeatureRef
     * matches the Feature this Page represents otherwise returns null.
     * 
     * @param operationFeatureRef
     *            Operation feature to get the command for.
     * @param docOperation
     *            , Operation's owner.
     * @return a valid command to set the Operation, if the operationFeatureRef
     *         matches the Feature this Page represents otherwise returns null.
     */
    protected Command getResetOperationCommand(EReference operationFeatureRef,
            DocumentOperation docOperation) {

        if (getOperationFeatureRef().equals(operationFeatureRef)) {

            return getSetCommandForOperation(operationFeatureRef, docOperation);
        }
        return null;
    }

    /**
     * Method to create and return command to set new Operation for the given
     * Operation Feature.Implementing classes should provide Commands to create
     * and set the Operation Type it represents.
     * 
     * @param operationFeatureRef
     *            Operation feature to get the command for.
     * @param docOperation
     *            , Operation's owner.
     * @return a valid command to set the Operation, if the operationFeatureRef
     *         matches the Feature this Page represents otherwise returns null.
     */
    abstract protected Command getSetCommandForOperation(
            EReference operationFeatureRef, DocumentOperation docOperation);

    /**
     * Implementors should return the actual operation instance it represents
     * under {@link DocumentOperation}.For Example {@link CaseDocFindOperations}
     * or {@link CaseDocRefOperations}
     * 
     * @return the actual operation instance it represents under
     *         {@link DocumentOperation}.For Example
     *         {@link CaseDocFindOperations} or {@link CaseDocRefOperations}
     */
    abstract protected EObject getOperation(DocumentOperation docOperation);

    /**
     * @return EditingDomain from the Parent Detail Section, null if the Parent
     *         Section is null.
     */
    protected EditingDomain getEditingDomain() {

        return (getParentDetailSection() != null) ? getParentDetailSection()
                .getEditingDomain() : null;
    }

}
