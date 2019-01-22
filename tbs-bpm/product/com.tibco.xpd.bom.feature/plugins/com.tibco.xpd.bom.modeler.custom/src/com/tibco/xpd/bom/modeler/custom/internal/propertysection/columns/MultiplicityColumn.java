/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.internal.propertysection.columns;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.workspace.EMFOperationCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserService;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.uml2.uml.MultiplicityElement;

import com.tibco.xpd.bom.modeler.custom.internal.Messages;
import com.tibco.xpd.bom.resources.ui.providers.OperationItemProvider.OperationHintIDs;
import com.tibco.xpd.resources.ui.components.AbstractColumn;

/**
 * Multiplicity column for the BOM tables.
 * 
 * @author njpatel
 * 
 */
public abstract class MultiplicityColumn extends AbstractColumn {

    private final TextCellEditor editor;

    private final ParserService ps;

    /**
     * Multiplicity column.
     * 
     * @param editingDomain
     * @param viewer
     *            table viewer
     */
    public MultiplicityColumn(EditingDomain editingDomain, ColumnViewer viewer) {
        this(editingDomain, viewer, SWT.NONE,
                Messages.MultiplicityColumn_column_title);
    }

    /**
     * Multiplicity column.
     * 
     * @param editingDomain
     * @param viewer
     *            table viewer
     * @param style
     *            column style
     * @param heading
     *            column title
     */
    public MultiplicityColumn(EditingDomain editingDomain, ColumnViewer viewer,
            int style, String heading) {
        super(editingDomain, viewer, style, heading, 60);
        editor = new TextCellEditor((Composite) viewer.getControl());
        ps = ParserService.getInstance();
    }

    @Override
    protected CellEditor getCellEditor(Object element) {
        element = getMultiplicityElement(element);
        return element != null ? editor : null;
    }

    @Override
    protected Command getSetValueCommand(Object element, Object value) {
        Command cmd = null;
        IParser parser = getParser(ps, element);
        MultiplicityElement mElem = getMultiplicityElement(element);
        if (parser != null && mElem != null) {
            ICommand icmd =
                    parser.getParseCommand(new EObjectAdapter(mElem),
                            (String) value,
                            0);
            if (icmd != null) {
                cmd =
                        new EMFOperationCommand(
                                (TransactionalEditingDomain) getEditingDomain(),
                                icmd);
            }
        }
        return cmd;
    }

    @Override
    protected String getText(Object element) {
        String txt = null;
        MultiplicityElement mElem = getMultiplicityElement(element);
        if (mElem != null) {
            IParser parser = getParser(ps, element);
            if (parser != null) {
                txt = parser.getPrintString(new EObjectAdapter(mElem), 0);
            }
        }
        return txt != null ? txt : ""; //$NON-NLS-1$
    }

    @Override
    protected Object getValueForEditor(Object element) {
        return getText(element);
    }

    /**
     * Get the parser for the given element from the service.
     * 
     * @param service
     * @param element
     * @return
     */
    private IParser getParser(ParserService service, Object element) {
        IParser parser = null;
        MultiplicityElement elem = getMultiplicityElement(element);
        if (elem != null) {
            // Get the parser
            parser =
                    service.getParser(OperationHintIDs.MULTIPLICITY.hint(elem));
        }
        return parser;
    }

    /**
     * Get the {@link MultiplicityElement} from the given table row element.
     * 
     * @param element
     * @return
     */
    protected abstract MultiplicityElement getMultiplicityElement(Object element);
}
