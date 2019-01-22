/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.implementer.nativeservices.documentoperationsservice;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceData;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceDataUtil;
import com.tibco.xpd.implementer.nativeservices.documentoperationsservice.CaseDocumentOperationsHelpUtiliy.CMISQL_PROPERTY;
import com.tibco.xpd.implementer.nativeservices.internal.Messages;
import com.tibco.xpd.implementer.resources.xpdl2.properties.filter.Operator;
import com.tibco.xpd.resources.ui.components.AbstractColumn;
import com.tibco.xpd.resources.ui.components.BaseTableControl;
import com.tibco.xpd.resources.ui.components.XpdToolkit;
import com.tibco.xpd.resources.ui.components.actions.TableAddAction;
import com.tibco.xpd.resources.ui.components.actions.TableDeleteAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerAddAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerDeleteAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerEditAction;
import com.tibco.xpd.xpdExtension.CMISQueryOperator;
import com.tibco.xpd.xpdExtension.CaseDocFindOperations;
import com.tibco.xpd.xpdExtension.CaseDocumentQueryExpression;
import com.tibco.xpd.xpdExtension.DocumentOperation;
import com.tibco.xpd.xpdExtension.FindByQueryOperation;
import com.tibco.xpd.xpdExtension.QueryExpressionJoinType;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * Table Control for the CMISQL Editor Support. The table Contains following 4
 * Columns 'Logical grouping', 'Cmis Property','Operator','Value' .Each Column
 * has its own CellEditor to help user select from the available valid
 * values.For the First Row the 'Logical Grouping' is not applicable hence the
 * cell is not editable.
 * 
 * @author aprasad
 * @since 02-Sep-2014
 */
public class CMISQLTableControl extends BaseTableControl {

    /**
     * Opening Bracket String Constant
     */
    private static final char OPENING_BRACKET_STRING = '(';

    /**
     * Closing Bracket String Constant
     */
    private static final char CLOSING_BRACKET_STRING = ')';

    private EditingDomain editingDomain;

    /**
     * Content provider for the Table.
     */
    private IContentProvider tableContentProvider;

    /**
     * List of Blank Option to be used in the Drop Down Cell Editor for disabled
     * cell.
     */
    private final String[] BLANK_OPTIONS_LIST = new String[] { "" };//$NON-NLS-1$

    /**
     * Constructor.
     * 
     * @param parent
     * @param toolkit
     * @param editingDomain
     */
    public CMISQLTableControl(Composite parent, XpdToolkit toolkit,
            EditingDomain editingDomain) {

        super(parent, toolkit, null, false);
        this.editingDomain = editingDomain;

        if (null != this.editingDomain) {
            createContents(parent, toolkit, null);
        }
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#addColumns(org.eclipse.jface.viewers.ColumnViewer)
     * 
     * @param viewer
     *            TableViewer to add column to.
     */
    @Override
    protected void addColumns(ColumnViewer viewer) {
        if (viewer instanceof TableViewer) {

            TableViewer tableViewer = (TableViewer) viewer;
            /* Logical Join Column */
            new AndOrExpressionColumn(editingDomain, tableViewer);
            /* Opening Bracket Column */
            new BracketColumn(editingDomain, tableViewer,
                    OPENING_BRACKET_STRING, true);
            /* CMS Property column */
            new CmisPropertyColumn(editingDomain, tableViewer);
            /* Condition Operator Column */
            new ConditionOperatorColumn(editingDomain, tableViewer);
            /* Process Data Field Column */
            new ProcessDataFieldColumn(editingDomain, tableViewer);
            /* Closing Bracket Column */
            new BracketColumn(editingDomain, tableViewer,
                    CLOSING_BRACKET_STRING, false);
            // set column width proportions
            setColumnProportions(new float[] { 0.2f, // Logical Grouping
                    0.1f, // Opening Bracket
                    0.2f, // CMIS Property,
                    0.2f, // Operator
                    0.2f,// Process Data Field Value
                    0.1f // Closing Bracket
            });

        }

    }

    /**
     * @see com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#getViewerContentProvider()
     * 
     * @return Content provider for the Viewer.
     */
    @Override
    protected IContentProvider getViewerContentProvider() {
        if (tableContentProvider == null) {

            tableContentProvider = new IStructuredContentProvider() {
                @Override
                public Object[] getElements(Object inputElement) {

                    if (inputElement instanceof Activity) {

                        DocumentOperation documentOperation =
                                CaseDocumentOperationsHelpUtiliy
                                        .getDocumentOperation((Activity) inputElement);

                        if (documentOperation != null) {

                            CaseDocFindOperations caseDocFindOperations =
                                    documentOperation
                                            .getCaseDocFindOperations();

                            if (caseDocFindOperations != null) {

                                FindByQueryOperation findByQueryOperation =
                                        caseDocFindOperations
                                                .getFindByQueryOperation();

                                if (findByQueryOperation != null) {

                                    return findByQueryOperation
                                            .getCaseDocumentQueryExpression()
                                            .toArray();
                                }
                            }
                        }
                    }

                    return new Object[0];
                }

                @Override
                public void dispose() {
                }

                @Override
                public void inputChanged(Viewer viewer, Object oldInput,
                        Object newInput) {
                }
            };
        }
        return tableContentProvider;
    }

    /**
     * 
     * Logical Grouping Column for the CMISQL Editor table.The first cell of the
     * column is non-editable as the first Expression in the Query should not
     * contain a Logical Grouping.The Cell Editor lists the options of supproted
     * Logical Groupings to choose from, at present AND and OR.
     * 
     * @author aprasad
     * @since 03-Sep-2014
     */
    protected class AndOrExpressionColumn extends AbstractColumn {

        /**
         * Cell Editor for the Column cells.
         */
        private ComboBoxCellEditor editor;

        /**
         * Collection of supported Logical Join Operators, to be used by the
         * CellEditor.
         */
        private LinkedHashMap<String, QueryExpressionJoinType> queryExpressionJoinTypes;

        /**
         * Returns null.
         * 
         * @see com.tibco.xpd.resources.ui.components.AbstractColumn#getImage(java.lang.Object)
         * 
         * @param element
         * @return null, not Image is required.
         */
        @Override
        protected Image getImage(Object element) {
            /* Image Not Required */
            return null;
        }

        /**
         * Column constructor, instantiates and initialises the CellEditor for
         * this Column.
         * 
         * @param editingDomain
         * @param viewer
         */
        public AndOrExpressionColumn(EditingDomain editingDomain,
                ColumnViewer viewer) {

            super(editingDomain, viewer, SWT.NONE,
                    Messages.CMISQLTableControl_LogicalGroupingColumnTitle, 90);

            queryExpressionJoinTypes =
                    new LinkedHashMap<String, QueryExpressionJoinType>();
            /* Blank Entry for first Row */
            queryExpressionJoinTypes.put("", null); //$NON-NLS-1$

            queryExpressionJoinTypes.put(QueryExpressionJoinType.AND.getName(),
                    QueryExpressionJoinType.AND);

            queryExpressionJoinTypes.put(QueryExpressionJoinType.OR.getName(),
                    QueryExpressionJoinType.OR);

            editor =
                    new ComboBoxCellEditor(
                            (Composite) viewer.getControl(),
                            queryExpressionJoinTypes
                                    .keySet()
                                    .toArray(new String[queryExpressionJoinTypes
                                            .size()]), SWT.READ_ONLY);

        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getCellEditor
         * (java.lang.Object)
         */
        @Override
        protected CellEditor getCellEditor(Object element) {

            if (element instanceof CaseDocumentQueryExpression) {

                if (isFirstRow(element)) {

                    /* show editor with only blank value to reset */
                    editor.setItems(BLANK_OPTIONS_LIST);
                    return editor;

                }

                /* Initialize the Editor */
                editor.setItems(queryExpressionJoinTypes.keySet()
                        .toArray(new String[queryExpressionJoinTypes.size()]));

                return editor;
            }
            return null;
        }

        /**
         * Returns true if the given Condition Expression is the first
         * Expression in the Query, in which case it should not have Logical
         * Operator defined.
         * 
         * @param element
         * @return true if this is the first condition expression.
         */
        private boolean isFirstRow(Object element) {
            if (element instanceof CaseDocumentQueryExpression) {

                CaseDocumentQueryExpression caseDocumentQueryExpression =
                        (CaseDocumentQueryExpression) element;

                List<CaseDocumentQueryExpression> caseDocQueryExpressionForInput =
                        getCaseDocQueryExpressionForInput();

                if (caseDocQueryExpressionForInput != null
                        && caseDocumentQueryExpression != null) {

                    if (caseDocumentQueryExpression
                            .equals(caseDocQueryExpressionForInput.get(0))) {
                        // this is the first Condition Expression.
                        return true;
                    }
                }
            }
            return false;
        }

        /**
         * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractProcessRelevantDataTable.ExternalReferenceColumn#getSetValueCommand(java.lang.Object,
         *      java.lang.Object)
         * 
         * @param element
         * @param value
         * @return Command to set the Value of the Logical Join for the
         *         {@link CaseDocumentQueryExpression}.
         */
        @Override
        protected Command getSetValueCommand(Object element, Object value) {

            if (element instanceof CaseDocumentQueryExpression) {

                CaseDocumentQueryExpression caseDocumentQueryExpression =
                        (CaseDocumentQueryExpression) element;

                if (value instanceof Integer && ((Integer) value >= 0)
                        && (editor.getItems().length > (Integer) value)) {

                    CompoundCommand cmd = new CompoundCommand();

                    QueryExpressionJoinType newLogicalGrouping =
                            queryExpressionJoinTypes
                                    .get(editor.getItems()[(Integer) value]);

                    QueryExpressionJoinType oldLogicalGrouping =
                            caseDocumentQueryExpression
                                    .getQueryExpressionJoinType();
                    /* boolean to decide if the value has changed */
                    boolean changed = true;
                    /* set only when old and new values are different */
                    if (newLogicalGrouping == null) {

                        if (oldLogicalGrouping == null) {
                            changed = false;
                        }
                    } else if (newLogicalGrouping.equals(oldLogicalGrouping)) {

                        changed = false;

                    }
                    if (changed) {

                        if (oldLogicalGrouping == null) {

                            /*
                             * There is some problem in setting the value to a
                             * valid enum value when the old value is null,
                             * hence it is done here by creating a copy of old
                             * Expression and setting the Logical Join to it,
                             * replacing the old Expression with this new
                             * Expression
                             */
                            int index =
                                    getCaseDocQueryExpressionForInput()
                                            .indexOf(caseDocumentQueryExpression);

                            CaseDocumentQueryExpression newExp =
                                    cloneQueryExpression(caseDocumentQueryExpression);

                            newExp.setQueryExpressionJoinType(newLogicalGrouping);

                            /* Insert New Expression */
                            cmd.append(AddCommand
                                    .create(editingDomain,
                                            caseDocumentQueryExpression
                                                    .eContainer(),
                                            XpdExtensionPackage.eINSTANCE
                                                    .getFindByQueryOperation_CaseDocumentQueryExpression(),
                                            newExp,
                                            index));

                            /* Remove Old Expression */
                            cmd.append(RemoveCommand
                                    .create(editingDomain,
                                            caseDocumentQueryExpression
                                                    .eContainer(),
                                            XpdExtensionPackage.eINSTANCE
                                                    .getFindByQueryOperation_CaseDocumentQueryExpression(),
                                            caseDocumentQueryExpression));

                        } else {
                            cmd.append(SetCommand
                                    .create(editingDomain,
                                            caseDocumentQueryExpression,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getCaseDocumentQueryExpression_QueryExpressionJoinType(),
                                            newLogicalGrouping));
                        }
                        return cmd;
                    }
                }

            }
            return null;
        }

        /**
         * Creates a Copy of given {@link CaseDocumentQueryExpression} instance
         * and returns.
         * 
         * @param caseDocumentQueryExpression
         * @return a copy of {@link CaseDocumentQueryExpression}.
         */
        private CaseDocumentQueryExpression cloneQueryExpression(
                CaseDocumentQueryExpression caseDocumentQueryExpression) {

            CaseDocumentQueryExpression newExp =
                    XpdExtensionFactory.eINSTANCE
                            .createCaseDocumentQueryExpression();

            newExp.setQueryExpressionJoinType(caseDocumentQueryExpression
                    .getQueryExpressionJoinType());

            newExp.setOpenBracketCount(caseDocumentQueryExpression
                    .getCloseBracketCount());

            newExp.setCmisProperty(caseDocumentQueryExpression
                    .getCmisProperty());

            newExp.setCmisDocumentPropertySelected(caseDocumentQueryExpression
                    .isCmisDocumentPropertySelected());

            newExp.setOperator(caseDocumentQueryExpression.getOperator());

            newExp.setProcessDataField(caseDocumentQueryExpression
                    .getProcessDataField());

            newExp.setCloseBracketCount(caseDocumentQueryExpression
                    .getCloseBracketCount());

            return newExp;
        }

        /**
         * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractProcessRelevantDataTable.ExternalReferenceColumn#getText(java.lang.Object)
         * 
         * @param element
         * @return String, name of the Logical Join Operation.
         */
        @Override
        protected String getText(Object element) {

            if (element instanceof CaseDocumentQueryExpression) {

                if (((CaseDocumentQueryExpression) element)
                        .getQueryExpressionJoinType() != null) {

                    /*
                     * Indent Content before returning such the grouping is more
                     * readable
                     */
                    return indentLogicalGroupingText((CaseDocumentQueryExpression) element);
                }
            }
            return ""; //$NON-NLS-1$
        }

        /**
         * Indents the Logical Operator for each opened Bracket which is not
         * closed to show proper nesting of expressions.This method should only
         * be used for display purposes.
         * 
         * @param element
         *            {@link CaseDocumentQueryExpression} for which the indented
         *            Logical Grouping Text should be returned.
         * @return String representing the Logical Grouping , indented to show
         *         nesting of expressions in brackets
         */
        private String indentLogicalGroupingText(
                CaseDocumentQueryExpression element) {

            List<CaseDocumentQueryExpression> caseDocQueryExpressionForInput =
                    getCaseDocQueryExpressionForInput();

            int openBrackets = 0;
            for (CaseDocumentQueryExpression expression : caseDocQueryExpressionForInput) {

                if (expression.equals(element)) {
                    /* need to consider expressions before the current one */
                    break;
                }

                if (expression.getOpenBracketCount() > 0) {
                    /* Opening Bracket */
                    openBrackets += expression.getOpenBracketCount();
                }

                if (expression.getCloseBracketCount() > 0) {
                    /* Bracket Closed */
                    openBrackets -= expression.getCloseBracketCount();
                }
            }
            StringBuffer indentedName =
                    new StringBuffer(element.getQueryExpressionJoinType()
                            .getName());

            for (int i = 0; i < openBrackets; i++) {

                indentedName.insert(0, "  "); //$NON-NLS-1$
            }
            return indentedName.toString();
        }

        /**
         * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractProcessRelevantDataTable.ExternalReferenceColumn#getValueForEditor(java.lang.Object)
         * 
         * @param element
         * @return int, index of the Logical Join Operation.
         */
        @Override
        protected Object getValueForEditor(Object element) {
            int i = 0;

            if (element instanceof CaseDocumentQueryExpression
                    && ((CaseDocumentQueryExpression) element)
                            .getQueryExpressionJoinType() != null) {

                for (String joinType : queryExpressionJoinTypes.keySet()) {

                    if (joinType.equals(((CaseDocumentQueryExpression) element)
                            .getQueryExpressionJoinType().getLiteral())) {

                        return i;
                    }
                    i++;
                }
            }

            return -1;
        }
    }

    /**
     * Column for Bracket for Grouping Expressions.This column can be used for
     * for Opening as well as the Closing Bracket and will behave accordingly.
     * To support this the Constructor takes CMISQueryExpressionBracket as input
     * to customise the behaviour of the column depending on the Bracket it
     * represents.
     * 
     * @author aprasad
     * @since 08-Sep-2014
     */
    protected class BracketColumn extends AbstractColumn {

        /**
         * Cell Editor for the Column cells.
         */
        private TextCellEditor textEditor;

        /**
         * Bracket character
         */
        private char bracket;

        /**
         * Indicates this column is for opening or closing bracket.Set to true
         * for opening bracket false for closing.
         */
        private boolean isOpeningBracket;

        /**
         * Returns null.
         * 
         * @see com.tibco.xpd.resources.ui.components.AbstractColumn#getImage(java.lang.Object)
         * 
         * @param element
         * @return null, not Image is required.
         */
        @Override
        protected Image getImage(Object element) {
            /* Image Not Required */
            return null;
        }

        /**
         * Column constructor, instantiates and initialises the CellEditor for
         * this Column.
         * 
         * @param editingDomain
         * @param viewer
         * @param bracket
         *            Bracket String to be used.
         * @param opening
         *            true indicates this is opening bracket, false indicates
         *            closing.
         */
        public BracketColumn(EditingDomain editingDomain, ColumnViewer viewer,
                char bracket, boolean isOpeningBracket) {

            super(editingDomain, viewer, SWT.NONE, String.valueOf(bracket), 90);

            this.bracket = bracket;
            this.isOpeningBracket = isOpeningBracket;

            textEditor = new TextCellEditor((Composite) viewer.getControl());

            ((Text) textEditor.getControl())
                    .addVerifyListener(new VerifyListener() {

                        @Override
                        public void verifyText(VerifyEvent e) {

                            if (e.text != null) {

                                e.doit = true;
                                for (char ch : e.text.toCharArray()) {

                                    if (BracketColumn.this.bracket != ch) {
                                        /*
                                         * Atleast one invalid character makes
                                         * it invalid
                                         */
                                        e.doit = false;
                                    }
                                }

                            } else {

                                if (BracketColumn.this.bracket == e.character) {
                                    e.doit = true;
                                } else {
                                    e.doit = false;
                                }

                            }
                        }
                    });

        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getCellEditor
         * (java.lang.Object)
         */
        @Override
        protected CellEditor getCellEditor(Object element) {

            return textEditor;
        }

        /**
         * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractProcessRelevantDataTable.ExternalReferenceColumn#getSetValueCommand(java.lang.Object,
         *      java.lang.Object)
         * 
         * @param element
         * @param value
         * @return Command to set the Value of the Logical Join for the
         *         {@link CaseDocumentQueryExpression}.
         */
        @Override
        protected Command getSetValueCommand(Object element, Object value) {

            if (element instanceof CaseDocumentQueryExpression) {

                CaseDocumentQueryExpression caseDocumentQueryExpression =
                        (CaseDocumentQueryExpression) element;

                if (value instanceof String) {

                    String newBracketString = (String) value;

                    CompoundCommand cmd = new CompoundCommand();

                    int oldBracketCount =
                            isOpeningBracket ? caseDocumentQueryExpression
                                    .getOpenBracketCount()
                                    : caseDocumentQueryExpression
                                            .getCloseBracketCount();

                    if (newBracketString != null) {

                        int newBracketCount =
                                getBracketCount(newBracketString, bracket);

                        /* Set only when the values change */
                        if (oldBracketCount != newBracketCount) {

                            if (isOpeningBracket) {

                                cmd.append(SetCommand
                                        .create(editingDomain,
                                                caseDocumentQueryExpression,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getCaseDocumentQueryExpression_OpenBracketCount(),
                                                newBracketCount));
                                return cmd;

                            } else {
                                /* Closing Bracket */
                                cmd.append(SetCommand
                                        .create(editingDomain,
                                                caseDocumentQueryExpression,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getCaseDocumentQueryExpression_CloseBracketCount(),
                                                newBracketCount));
                                return cmd;

                            }
                        }
                    }

                }

            }
            return null;
        }

        /**
         * Counts number of Brackets in the String.
         * 
         * @param newBracketValue
         * @return count of bracket in the String.
         */
        private int getBracketCount(String newBracketString, char bracket) {

            if (newBracketString != null && !newBracketString.isEmpty()) {
                int bracketCount = 0;
                for (char ch : newBracketString.toCharArray()) {

                    if (bracket == ch) {
                        bracketCount++;
                    }
                }
                return bracketCount;
            }
            return 0;
        }

        /**
         * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractProcessRelevantDataTable.ExternalReferenceColumn#getText(java.lang.Object)
         * 
         * @param element
         * @return String, name of the Logical Join Operation.
         */
        @Override
        protected String getText(Object element) {

            if (element instanceof CaseDocumentQueryExpression) {

                CaseDocumentQueryExpression expr =
                        (CaseDocumentQueryExpression) element;
                String bracketStr = null;

                if (this.isOpeningBracket) {

                    bracketStr = getBracketString(expr.getOpenBracketCount());

                } else {

                    bracketStr = getBracketString(expr.getCloseBracketCount());
                }

                return bracketStr;
            }
            return ""; //$NON-NLS-1$
        }

        /**
         * Get String containing openBracketCount number of Brackets.
         * 
         * @param bracketCount
         *            count of brackets in the String.
         * @return String containing openBracketCount number of brackets.
         */
        private String getBracketString(int bracketCount) {

            StringBuffer bracketString = new StringBuffer();

            for (int i = 0; i < bracketCount; i++) {
                bracketString.append(bracket);
            }

            return bracketString.toString();
        }

        /**
         * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractProcessRelevantDataTable.ExternalReferenceColumn#getValueForEditor(java.lang.Object)
         * 
         * @param element
         * @return int, index of Bracket.
         */
        @Override
        protected Object getValueForEditor(Object element) {

            return getText(element);
        }
    }

    /**
     * 
     * Column for the CMIS Property.
     * 
     * @author aprasad
     * @since 04-Sep-2014
     */
    protected class CmisPropertyColumn extends AbstractColumn {

        /**
         * CellEditor for the cell of this Column.The editor lists down the
         * supported CMIS Properties to be used in the Query expressions.
         */
        private ComboBoxCellEditor editor;

        /**
         * Collection of the supported CMIS Properties , to be used by the
         * CellEditor.
         */
        Map<String, CaseDocumentOperationsHelpUtiliy.CMISQL_PROPERTY> cmisProperties;

        /**
         * Returns null.
         * 
         * @see com.tibco.xpd.resources.ui.components.AbstractColumn#getImage(java.lang.Object)
         * 
         * @param element
         * @return null, not Image is required.
         */
        @Override
        protected Image getImage(Object element) {
            /* Image Not required */
            return null;
        }

        /**
         * Constructor for the column , which also instantiates and initialises
         * the CellEditor for the Column.
         * 
         * @param editingDomain
         * @param viewer
         */
        public CmisPropertyColumn(EditingDomain editingDomain,
                ColumnViewer viewer) {

            super(editingDomain, viewer, SWT.NONE,
                    Messages.CMISQLTableControl_CMSPropertyColumnTitle, 90);

            cmisProperties =
                    new LinkedHashMap<String, CaseDocumentOperationsHelpUtiliy.CMISQL_PROPERTY>();

            /*
             * Blank Entry into selection, to allow resetting as this is
             * optional
             */
            cmisProperties.put("", null); //$NON-NLS-1$
            for (CMISQL_PROPERTY cmisProperty : CaseDocumentOperationsHelpUtiliy.SUPPORTED_CMIS_PROPERTIES) {

                cmisProperties
                        .put(cmisProperty.getPropertyName(), cmisProperty);
            }

            editor =
                    new ComboBoxCellEditor(
                            (Composite) viewer.getControl(),
                            cmisProperties.keySet()
                                    .toArray(new String[cmisProperties.size()]),
                            SWT.READ_ONLY);
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getCellEditor
         * (java.lang.Object)
         */
        @Override
        protected CellEditor getCellEditor(Object element) {

            /* Return all Properties and do not restrict */
            return editor;
        }

        /**
         * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractProcessRelevantDataTable.ExternalReferenceColumn#getSetValueCommand(java.lang.Object,
         *      java.lang.Object)
         * 
         * @param element
         * @param value
         * @return Command to set selected the CMIS Property to the
         *         {@link CaseDocumentQueryExpression}.
         */
        @Override
        protected Command getSetValueCommand(Object element, Object value) {
            if (element instanceof CaseDocumentQueryExpression) {

                CaseDocumentQueryExpression caseDocumentQueryExpression =
                        (CaseDocumentQueryExpression) element;

                if (value instanceof Integer && ((Integer) value >= 0)
                        && (editor.getItems().length > (Integer) value)) {

                    CMISQL_PROPERTY cmisProperty =
                            cmisProperties
                                    .get(editor.getItems()[(Integer) value]);

                    boolean set = false;

                    /* set only when old and new values are different */
                    if (caseDocumentQueryExpression.getCmisPropertyForDisplay() == null) {

                        if (cmisProperty != null) {

                            set = true;
                        }

                    } else if (!caseDocumentQueryExpression
                            .getCmisPropertyForDisplay().equals(cmisProperty)) {

                        set = true;
                    }

                    if (set) {
                        /*
                         * Null check is required which might be the case when
                         * blank is selected
                         */
                        String propertyName =
                                (cmisProperty == null) ? null : cmisProperty
                                        .getPropertyName();

                        CompoundCommand cmd = new CompoundCommand();
                        /*
                         * XPD-6848 : Provide cmis:document for [NOT] CONTAINS
                         * operation. Save cmis:document selection.
                         */
                        if (CMISQL_PROPERTY.CMISQL_DOCUMENT
                                .equals(cmisProperty)) {

                            /* Command to remove exists cmis:property */
                            if (caseDocumentQueryExpression
                                    .getCmisPropertyForDisplay() != null) {

                                cmd.append(SetCommand
                                        .create(editingDomain,
                                                caseDocumentQueryExpression,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getCaseDocumentQueryExpression_CmisProperty(),
                                                null));
                            }
                            /* Command to set cmisDocumentPropertySelection */
                            cmd.append(SetCommand
                                    .create(editingDomain,
                                            caseDocumentQueryExpression,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getCaseDocumentQueryExpression_CmisDocumentPropertySelected(),
                                            true));

                            return cmd;
                        }

                        /* Command to set the selected CMIS Property */
                        cmd.append(SetCommand
                                .create(editingDomain,
                                        caseDocumentQueryExpression,
                                        XpdExtensionPackage.eINSTANCE
                                                .getCaseDocumentQueryExpression_CmisProperty(),
                                        propertyName));

                        /*
                         * Command to remove setting of cmis:document property
                         * selection
                         */
                        if (caseDocumentQueryExpression
                                .isCmisDocumentPropertySelected()) {

                            cmd.append(SetCommand
                                    .create(editingDomain,
                                            caseDocumentQueryExpression,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getCaseDocumentQueryExpression_CmisDocumentPropertySelected(),
                                            false));
                        }

                        return cmd;
                    }
                }

            }
            return null;
        }

        /**
         * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractProcessRelevantDataTable.ExternalReferenceColumn#getText(java.lang.Object)
         * 
         * @param element
         * @return String, name of the CMIS Property.
         */
        @Override
        protected String getText(Object element) {

            if (element instanceof CaseDocumentQueryExpression) {

                return ((CaseDocumentQueryExpression) element)
                        .getCmisPropertyForDisplay();
            }
            return ""; //$NON-NLS-1$
        }

        /**
         * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractProcessRelevantDataTable.ExternalReferenceColumn#getValueForEditor(java.lang.Object)
         * 
         * @param element
         * @return Integer index of the CMIS Property to be shown in the editor.
         */
        @Override
        protected Object getValueForEditor(Object element) {
            int i = 0;

            for (String property : cmisProperties.keySet()) {
                if (property.equals(getText(element))) {
                    return i;
                }
                i++;
            }

            return -1;
        }

    }

    /**
     * 
     * Column for the Condition Operator of the Expression.
     * 
     * @author aprasad
     * @since 03-Sep-2014
     */
    protected class ConditionOperatorColumn extends AbstractColumn {

        /**
         * Cell Editor for the cells of this Column.Lists down the supported
         * Operators to choose from.
         */
        private final ComboBoxCellEditor editor;

        /**
         * Collection of supported Operators to be used by the cell editor for
         * listing.
         */
        Map<String, CMISQueryOperator> supportedOperators;

        /**
         * Constructor for the column, which also instantiates and initialises
         * the CellEditor for this Column.
         * 
         * @param editingDomain
         * @param viewer
         */
        public ConditionOperatorColumn(EditingDomain editingDomain,
                ColumnViewer viewer) {

            super(editingDomain, viewer, SWT.NONE,
                    Messages.CMISQLTableControl_OperationColumnTitle, 90);

            supportedOperators =
                    CaseDocumentOperationsHelpUtiliy.getSupportedOperators();
            /*
             * XPD-6789: Saket: This editor should be read only.
             */
            editor =
                    new ComboBoxCellEditor((Composite) viewer.getControl(),
                            supportedOperators.keySet()
                                    .toArray(new String[supportedOperators
                                            .size()]), SWT.READ_ONLY);

        }

        /**
         * 
         * @see com.tibco.xpd.processeditor.xpdl2.properties.
         *      AbstractProcessRelevantDataTable
         *      .ExternalReferenceColumn#getSetValueCommand(java.lang.Object,
         *      java.lang.Object)
         * 
         * @param element
         * 
         * @param value
         * 
         * @return {@link Command}, to set value of the {@link Operator} to the
         *         {@link CaseDocumentQueryExpression}.
         */
        @Override
        protected Command getSetValueCommand(Object element, Object value) {
            if (element instanceof CaseDocumentQueryExpression) {

                CaseDocumentQueryExpression caseDocumentQueryExpression =
                        (CaseDocumentQueryExpression) element;

                if (value instanceof Integer && ((Integer) value >= 0)
                        && (editor.getItems().length > (Integer) value)) {

                    CMISQueryOperator operator =
                            supportedOperators
                                    .get(editor.getItems()[(Integer) value]);

                    if (operator != null
                            && !operator.equals(caseDocumentQueryExpression
                                    .getOperator())) {

                        return SetCommand
                                .create(editingDomain,
                                        caseDocumentQueryExpression,
                                        XpdExtensionPackage.eINSTANCE
                                                .getCaseDocumentQueryExpression_Operator(),
                                        operator);
                    }
                }

            }
            return null;
        }

        /**
         * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractProcessRelevantDataTable.ExternalReferenceColumn#getText(java.lang.Object)
         * 
         * @param element
         * @return String, {@link Operator} for the
         *         {@link CaseDocumentQueryExpression}.
         */
        @Override
        protected String getText(Object element) {

            if (element instanceof CaseDocumentQueryExpression) {

                for (Entry<String, CMISQueryOperator> entry : supportedOperators
                        .entrySet()) {

                    if ((entry.getValue()
                            .equals(((CaseDocumentQueryExpression) element)
                                    .getOperator()))) {

                        return entry.getKey();
                    }
                }
            }
            return ""; //$NON-NLS-1$
        }

        /**
         * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractProcessRelevantDataTable.ExternalReferenceColumn#getValueForEditor(java.lang.Object)
         * 
         * @param element
         * @return index of value to be displayed in the {@link CellEditor}
         * 
         */
        @Override
        protected Object getValueForEditor(Object element) {
            int i = 0;

            for (String joinType : supportedOperators.keySet()) {
                if (joinType.equals(getText(element))) {
                    return i;
                }
                i++;
            }

            return -1;
        }

        /**
         * @see com.tibco.xpd.resources.ui.components.AbstractColumn#getCellEditor(java.lang.Object)
         * 
         * @param element
         * @return Cell Editor for this Column
         */
        @Override
        protected CellEditor getCellEditor(Object element) {

            /* Return all Operator Always and do not restrict */
            return editor;
        }

    }

    /**
     * Column to display the Process Data Field in the Query Editor.
     * 
     * @author aprasad
     * @since 03-Sep-2014
     */
    protected class ProcessDataFieldColumn extends AbstractColumn {

        /**
         * CellEditor for this Column, which lists down the Process Data Fields
         * in scope for the Document Operation Task Activity.
         */
        private ComboBoxCellEditor editor;

        /**
         * Collection of the Process Data Field in scope of the Document
         * Operation Task Activity.
         */
        private Map<String, ProcessRelevantData> processData;

        /**
         * Returns null.
         * 
         * @see com.tibco.xpd.resources.ui.components.AbstractColumn#getImage(java.lang.Object)
         * 
         * @param element
         * @return null, not Image is required.
         */
        @Override
        protected Image getImage(Object element) {
            /* Image Not Required */
            return null;
        }

        /**
         * Conatructor for the Column, which also instantiates and initialises
         * the Cell Editor for the Column cells.
         * 
         * @param editingDomain
         * @param viewer
         */
        public ProcessDataFieldColumn(EditingDomain editingDomain,
                ColumnViewer viewer) {

            super(editingDomain, viewer, SWT.NONE,
                    Messages.CMISQLTableControl_ValueColumnTitle, 90);

            processData = getAllActivityInterfaceData();

            editor =
                    new ComboBoxCellEditor((Composite) viewer.getControl(),
                            processData.keySet().toArray(new String[processData
                                    .size()]),
                            SWT.READ_ONLY);
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getCellEditor
         * (java.lang.Object)
         */
        @Override
        protected CellEditor getCellEditor(Object element) {

            if (isNULLOperatorExpression(element)) {

                /* show editor with only blank value to reset */
                editor.setItems(BLANK_OPTIONS_LIST);
                return editor;
            }

            /*
             * Should always re-fetch the data to show updated list in the cell
             * editor.
             */
            processData = new HashMap<String, ProcessRelevantData>();
            /*
             * Blank Entry into selection, to allow resetting as this is
             * optional
             */
            processData.put("", null); //$NON-NLS-1$
            processData.putAll(getAllActivityInterfaceData());
            /* Sort them always */
            String[] procDataNames =
                    processData.keySet()
                            .toArray(new String[processData.size()]);
            Arrays.sort(procDataNames);
            editor.setItems(procDataNames);
            return editor;
        }

        /**
         * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractProcessRelevantDataTable.ExternalReferenceColumn#getSetValueCommand(java.lang.Object,
         *      java.lang.Object)
         * 
         * @param element
         * @param value
         * @return {@link Command} to set Process Data Field name to the
         *         {@link CaseDocumentQueryExpression}.
         */
        @Override
        protected Command getSetValueCommand(Object element, Object value) {

            if (element instanceof CaseDocumentQueryExpression) {

                CaseDocumentQueryExpression caseDocumentQueryExpression =
                        (CaseDocumentQueryExpression) element;

                if (value instanceof Integer && ((Integer) value >= 0)
                        && (editor.getItems().length > (Integer) value)) {

                    String processData = editor.getItems()[(Integer) value];

                    boolean set = false;
                    /*
                     * XPD-6844 CMIS Query Editor : Set Process Data to null
                     * when reset to blank
                     */
                    if (processData == null || processData.isEmpty()) {
                        if (caseDocumentQueryExpression.getProcessDataField() != null) {

                            processData = null;
                            set = true;
                        }
                    }
                    if (processData != null
                            && !processData.equals(caseDocumentQueryExpression
                                    .getProcessDataField())) {

                        set = true;
                    }

                    if (set) {
                        return SetCommand
                                .create(editingDomain,
                                        caseDocumentQueryExpression,
                                        XpdExtensionPackage.eINSTANCE
                                                .getCaseDocumentQueryExpression_ProcessDataField(),
                                        processData);
                    }
                }

            }
            return null;
        }

        /**
         * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractProcessRelevantDataTable.ExternalReferenceColumn#getText(java.lang.Object)
         * 
         * @param element
         * @return String, name of the Process Data Field.
         */
        @Override
        protected String getText(Object element) {

            if (element instanceof CaseDocumentQueryExpression) {

                return ((CaseDocumentQueryExpression) element)
                        .getProcessDataField();
            }
            return ""; //$NON-NLS-1$
        }

        /**
         * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractProcessRelevantDataTable.ExternalReferenceColumn#getValueForEditor(java.lang.Object)
         * 
         * @param element
         * @return index of the Process Data Field.
         */
        @Override
        protected Object getValueForEditor(Object element) {
            int i = 0;
            // Use Sorted List as, the Cell Editor Contains Sorted list.
            String[] procDataNames =
                    processData.keySet()
                            .toArray(new String[processData.size()]);
            Arrays.sort(procDataNames);

            for (String property : procDataNames) {
                if (property.equals(getText(element))) {
                    return i;
                }
                i++;
            }

            return -1;
        }
    }

    /**
     * Returns the input of this table.
     * 
     * @return
     */
    private EObject getInput() {
        return (EObject) (getViewer() != null ? getViewer().getInput() : null);
    }

    /**
     * Returns Map<process data name, {@link ProcessRelevantData}> of associated
     * with the given Activity input.
     * 
     * @return Map<process data name, {@link ProcessRelevantData}> of associated
     *         with the given Activity input.
     */
    private Map<String, ProcessRelevantData> getAllActivityInterfaceData() {
        Map<String, ProcessRelevantData> allData =
                new LinkedHashMap<String, ProcessRelevantData>();

        EObject input = getInput();

        if (input instanceof Activity) {

            Collection<ActivityInterfaceData> activityInterfaceDataList =
                    ActivityInterfaceDataUtil
                            .getActivityInterfaceData((Activity) input);

            for (ActivityInterfaceData activityInterfaceData : activityInterfaceDataList) {

                allData.put(activityInterfaceData.getName(),
                        activityInterfaceData.getData());
            }
        }
        return allData;
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#createAddAction(org.eclipse.jface.viewers.ColumnViewer)
     * 
     * @param viewer
     * @return Action to add a row to this Table Editor, each row represents a
     *         {@link CaseDocumentQueryExpression}.
     */
    @Override
    protected ViewerAddAction createAddAction(ColumnViewer viewer) {

        return new TableAddAction(viewer,
                Messages.CMISQLTableControl_AddActionLabel,
                Messages.CMISQLTableControl_AddActionDesc) {

            @Override
            protected Object addRow(StructuredViewer viewer) {

                FindByQueryOperation findByQueryOperation =
                        getFindByQueryOperationForInput();

                if (findByQueryOperation != null) {

                    CaseDocumentQueryExpression expression =
                            XpdExtensionFactory.eINSTANCE
                                    .createCaseDocumentQueryExpression();

                    /* For first expression Join Type is not required */
                    if (findByQueryOperation.getCaseDocumentQueryExpression()
                            .isEmpty()) {

                        expression.setQueryExpressionJoinType(null);

                    } else {

                        expression
                                .setQueryExpressionJoinType(QueryExpressionJoinType.AND);

                    }
                    expression.setCmisProperty(CMISQL_PROPERTY.CMISQL_NAME
                            .getPropertyName());

                    expression.setOperator(CMISQueryOperator.EQUAL);

                    /*
                     * By Default add at the end if no row is selected,
                     * otherwise insert after the selected row.
                     */
                    List<CaseDocumentQueryExpression> caseDocQueryExpressionForInput =
                            getCaseDocQueryExpressionForInput();

                    int index = caseDocQueryExpressionForInput.size();

                    /* Get current Selection Index to insert new Row after that */
                    IStructuredSelection selection =
                            (IStructuredSelection) getTableViewer()
                                    .getSelection();

                    if (selection != null && !selection.isEmpty()) {

                        Object object =
                                selection.toList().get(selection.toList()
                                        .size() - 1);

                        if (object instanceof CaseDocumentQueryExpression) {
                            index =
                                    caseDocQueryExpressionForInput
                                            .indexOf(object) + 1;
                        }
                    }

                    Command createCmd =
                            AddCommand
                                    .create(editingDomain,
                                            findByQueryOperation,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getFindByQueryOperation_CaseDocumentQueryExpression(),
                                            expression,
                                            index);

                    if (createCmd.canExecute()) {

                        editingDomain.getCommandStack().execute(createCmd);
                    }
                }
                return null;
            }

        };
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#createEditAction(org.eclipse.jface.viewers.ColumnViewer)
     * 
     * @param viewer
     * @return null, edit Action is not supported for this Query Editor.
     */
    @Override
    protected ViewerEditAction createEditAction(ColumnViewer viewer) {
        /* Edit Action Not Required */
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.processeditor.xpdl2.properties.dataFields.DataFieldTable
     * #getMovableFeatures()
     */
    @Override
    protected Set<EStructuralFeature> getMovableFeatures() {

        Set<EStructuralFeature> features = super.getMovableFeatures();
        features.add(XpdExtensionPackage.eINSTANCE
                .getFindByQueryOperation_CaseDocumentQueryExpression());

        return features;
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#createDeleteAction(org.eclipse.jface.viewers.ColumnViewer)
     * 
     * @param viewer
     * @return Action to Delete selected rows from the Query Editor Table,where
     *         each row represents a {@link CaseDocumentQueryExpression}.
     */
    @Override
    protected ViewerDeleteAction createDeleteAction(ColumnViewer viewer) {
        return new TableDeleteAction(viewer,
                Messages.CMISQLTableControl_DeleteActionLable,
                Messages.CMISQLTableControl_DeleteActionDesc) {

            @Override
            protected void deleteRows(IStructuredSelection selection) {

                if (!selection.isEmpty()) {

                    Iterator iterator = selection.iterator();

                    while (iterator.hasNext()) {

                        Object next = iterator.next();

                        if (next instanceof CaseDocumentQueryExpression) {

                            Command removeCmd =
                                    RemoveCommand
                                            .create(editingDomain,
                                                    getFindByQueryOperationForInput(),
                                                    XpdExtensionPackage.eINSTANCE
                                                            .getFindByQueryOperation_CaseDocumentQueryExpression(),
                                                    next);

                            if (removeCmd.canExecute()) {
                                editingDomain.getCommandStack()
                                        .execute(removeCmd);
                            }

                        }
                    }
                }

            }
        };
    }

    /**
     * @return Collection of {@link CaseDocumentQueryExpression} for the input
     *         Document Operation Task Activity.Returns null if the input
     *         Activity is not a Case Document Operation Task Activity , or is
     *         not a Case Document Operation Task Activity for Find By Query
     *         Operation.
     */
    private List<CaseDocumentQueryExpression> getCaseDocQueryExpressionForInput() {

        FindByQueryOperation findByQueryOperation =
                getFindByQueryOperationForInput();

        if (findByQueryOperation != null) {

            return findByQueryOperation.getCaseDocumentQueryExpression();
        }

        return null;
    }

    /**
     * @return {@link FindByQueryOperation} for the input Document Operation
     *         Task Activity. Returns null if the input Activity is not a Case
     *         Document Operation Task Activity , or is not a Case Document
     *         Operation Task Activity for Find By Query Operation.
     */
    private FindByQueryOperation getFindByQueryOperationForInput() {

        EObject input = getInput();
        if (input instanceof Activity) {

            DocumentOperation documentOperation =
                    CaseDocumentOperationsHelpUtiliy
                            .getDocumentOperation((Activity) input);

            if (documentOperation != null) {

                CaseDocFindOperations caseDocFindOperations =
                        documentOperation.getCaseDocFindOperations();
                if (caseDocFindOperations != null) {

                    FindByQueryOperation findByQueryOperation =
                            caseDocFindOperations.getFindByQueryOperation();

                    if (findByQueryOperation != null) {

                        return findByQueryOperation;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Returns true if this is a [NOT] CONTAINS Expression.
     * 
     * @param element
     * @return true if this is a [NOT] CONTAINS Expression, false otherwise.
     */
    private boolean isContainsOperatorExpression(Object element) {

        if (element instanceof CaseDocumentQueryExpression) {

            CaseDocumentQueryExpression exp =
                    (CaseDocumentQueryExpression) element;

            if (CMISQueryOperator.CONTAINS.equals(exp.getOperator())
                    || CMISQueryOperator.NOT_CONTAINS.equals(exp.getOperator())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if this is a [NOT] NULL Expression.
     * 
     * @param element
     * @return true if this is a [NOT] NULL Expression, false otherwise.
     */
    private boolean isNULLOperatorExpression(Object element) {

        if (element instanceof CaseDocumentQueryExpression) {

            CaseDocumentQueryExpression exp =
                    (CaseDocumentQueryExpression) element;

            if (CMISQueryOperator.NULL.equals(exp.getOperator())
                    || CMISQueryOperator.NOT_NULL.equals(exp.getOperator())) {
                return true;
            }
        }
        return false;
    }

}
