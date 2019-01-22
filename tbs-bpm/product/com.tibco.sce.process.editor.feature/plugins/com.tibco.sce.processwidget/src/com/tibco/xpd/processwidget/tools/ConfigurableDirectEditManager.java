/**
 * ConfigurableDirectEditManager.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */
package com.tibco.xpd.processwidget.tools;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.processwidget.adapters.BaseGraphicalNodeAdapter;
import com.tibco.xpd.processwidget.adapters.BaseProcessAdapter;
import com.tibco.xpd.processwidget.parts.BaseGraphicalEditPart;
import com.tibco.xpd.processwidget.parts.ModelAdapterEditPart;

/**
 * ConfigurableDirectEditManager
 * <p>
 * Direct edit manager that allows style to be set for edit box.
 * </p>
 */
public abstract class ConfigurableDirectEditManager extends DirectEditManager {
    int style = SWT.NONE;

    /**
     * @param source
     * @param type
     * @param locator
     */
    public ConfigurableDirectEditManager(GraphicalEditPart source,
            CellEditorLocator locator, int style) {
        super(source, TextCellEditor.class, locator);
        this.style = style;
    }

    @Override
    protected CellEditor createCellEditorOn(Composite composite) {
        // Only allow direct edit if edit parts process widget adapter says we
        // can.
        GraphicalEditPart editPart = getEditPart();
        if (editPart instanceof BaseGraphicalEditPart) {
            BaseProcessAdapter adapter =
                    ((BaseGraphicalEditPart) editPart).getModelAdapter();

            if (adapter instanceof BaseGraphicalNodeAdapter) {
                if (!((BaseGraphicalNodeAdapter) adapter).canSetName()) {
                    return null;
                }
            }
        }

        TextCellEditor cell = new TextCellEditor(composite, style);
        Text textControl = (Text) cell.getControl();

        textControl.setBackground(ColorConstants.listBackground);

        //
        // By default, text control with SWT.WRAP style behaves just like
        // SWT.MULTI (in that it allows you to enter <CR> even though
        // SWT.SINGLE style is set). So we have to set a verify listener
        // that ignores newlines when SWT.SINGLE is set)
        textControl.addVerifyListener(new VerifyListener() {
            public void verifyText(VerifyEvent e) {
                if (((style & SWT.MULTI) == 0 && e.character == '\r')
                        || e.character == '\t') {
                    e.doit = false;

                    ConfigurableDirectEditManager.this.commit();
                }
            }
        });
        return cell;
    }

    /**
     * XPD-1140: Override to only show direct edit feedback if editing domain is
     * not read only.
     */
    @Override
    public void show() {
        boolean showIt = true;

        if (getEditPart() instanceof ModelAdapterEditPart) {
            ModelAdapterEditPart editPart =
                    (ModelAdapterEditPart) getEditPart();

            EditingDomain editingDomain = editPart.getEditingDomain();
            Object model = editPart.getModel();
            if (editingDomain != null && model instanceof EObject) {

                if (editingDomain.isReadOnly(((EObject) model).eResource())) {
                    showIt = false;
                }
            }
        }

        if (showIt) {
            super.show();
        }
        return;
    }

}
