package com.tibco.xpd.bom.resources.ui.internal.properties;

import java.util.Arrays;

import org.eclipse.emf.common.ui.celleditor.ExtendedComboBoxCellEditor;
import org.eclipse.emf.common.ui.celleditor.ExtendedDialogCellEditor;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.gmf.runtime.common.ui.contentassist.ContentAssistantHelper;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.emf.ui.properties.descriptors.EMFCompositeSourcePropertyDescriptor;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.bom.resources.ui.internal.calendar.DateTimeCellEditor;
import com.tibco.xpd.resources.ui.components.calendar.DateType;

/**
 * Extend the behaviour of the base class in order to handle feature that is a
 * parser.
 * 
 * @author wzurek
 */
public final class EMFCompositeSourcePropertyDescriptorEx extends
        EMFCompositeSourcePropertyDescriptor {
    /**
     * @param object
     * @param itemPropertyDescriptor
     * @param category
     */
    EMFCompositeSourcePropertyDescriptorEx(Object object,
            IItemPropertyDescriptor itemPropertyDescriptor, String category) {
        super(object, itemPropertyDescriptor, category);
    }

    /**
     * Extend the behaviour in order to handle feature that is a parser.
     */
    @Override
    protected CellEditor doCreateEditor(Composite composite) {
        Object feature = getFeature();
        if (feature instanceof IParser) {
            return createCellEditorForParser(composite, (IParser) feature);
        } else if (feature instanceof PropertyModifier) {
            return createModifierDialogEditor(composite,
                    (PropertyModifier) feature);
        } else if (feature instanceof Property) {
            // return new CheckboxCellEditor(composite, SWT.None);
            return new ExtendedComboBoxCellEditor(composite, Arrays
                    .asList(new Object[] { "", Boolean.FALSE, Boolean.TRUE }), //$NON-NLS-1$
                    getLabelProvider(), false);
        } else if (feature instanceof Enumeration) {
            return createComboBoxCellEditor(composite);
        } else if (feature instanceof DateType) {
            return new DateTimeCellEditor(composite, (DateType) feature);
        }
        return super.doCreateEditor(composite);
    }

    /**
     * @param composite
     * @param modifier
     * @return
     */
    private CellEditor createModifierDialogEditor(Composite composite,
            final PropertyModifier modifier) {
        CellEditor result =
                new ExtendedDialogCellEditor(composite, getLabelProvider()) {
                    @Override
                    protected Object openDialogBox(Control cellEditorWindow) {
                        return modifier.performAction(cellEditorWindow,
                                getItemDescriptor(),
                                getObject());
                    }

                    @Override
                    protected Button createButton(Composite parent) {
                        // Assign a key listener to the browse button to listen
                        // for
                        // delete key - this will be used to clear the value
                        Button browseBtn = super.createButton(parent);

                        if (browseBtn != null) {
                            browseBtn.addKeyListener(new KeyListener() {

                                public void keyPressed(KeyEvent e) {
                                    // Do nothing
                                }

                                public void keyReleased(KeyEvent e) {
                                    if (e.keyCode == SWT.DEL) {
                                        // Delete key pressed so clear value
                                        modifier
                                                .resetPropertyValue(getObject());
                                    }
                                }

                            });
                        }
                        return browseBtn;
                    }
                };
        return result;
    }

    /**
     * Create cell editor for parser with (optional) content assist processor.
     * 
     * @param composite
     * @param parser
     * @return
     */
    private CellEditor createCellEditorForParser(Composite composite,
            IParser parser) {
        TextCellEditor textCellEditor = new TextCellEditor(composite);
        IContentAssistProcessor processor = parser.getCompletionProcessor(null);
        if (processor != null) {
            ContentAssistantHelper
                    .createTextContentAssistant((Text) textCellEditor
                            .getControl(), processor);

        }
        return textCellEditor;
    }

}