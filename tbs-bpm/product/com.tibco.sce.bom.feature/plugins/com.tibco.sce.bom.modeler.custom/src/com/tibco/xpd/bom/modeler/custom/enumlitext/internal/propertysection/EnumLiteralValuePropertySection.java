package com.tibco.xpd.bom.modeler.custom.enumlitext.internal.propertysection;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.part.PageBook;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.PrimitiveType;

import com.tibco.xpd.bom.modeler.custom.enumlitext.util.EnumLitValueUtil;
import com.tibco.xpd.bom.modeler.custom.internal.Messages;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.resources.ui.components.calendar.DateType;
import com.tibco.xpd.ui.properties.AbstractTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

public class EnumLiteralValuePropertySection extends
        AbstractTransactionalSection implements IFilter {

    private PageBook valueBook;

    private ValuePage currentPage;

    private StringValuePage stringPage;

    private BooleanValuePage booleanPage;

    private DateTimeValuePage dateTimePage;

    private NumberValuePage numberpage;

    public EnumLiteralValuePropertySection() {
        // TODO Auto-generated constructor stub
    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = toolkit.createComposite(parent);
        root.setLayout(new GridLayout(2, false));

        createLabel(root,
                toolkit,
                Messages.EnumLiteralValuePropertySection_value_label);

        valueBook = new PageBook(root, SWT.NONE);
        valueBook.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
        toolkit.adapt(valueBook);

        stringPage = new StringValuePage(toolkit, getEditingDomain());
        stringPage.createControl(valueBook);

        booleanPage = new BooleanValuePage(toolkit, getEditingDomain());
        booleanPage.createControl(valueBook);

        dateTimePage = new DateTimeValuePage(toolkit, getEditingDomain());
        dateTimePage.createControl(valueBook);

        numberpage = new NumberValuePage(toolkit, getEditingDomain());
        numberpage.createControl(valueBook);

        currentPage = stringPage;
        valueBook.showPage(currentPage.getControl());

        return root;
    }

    @Override
    protected Command doGetCommand(Object obj) {
        return null;
    }

    @Override
    protected void doRefresh() {
        ValuePage page = getPage();
        if (page != null && page != currentPage) {
            currentPage = page;
        }

        if (currentPage != null) {
            currentPage.setInput(getInput());
            currentPage.doRefresh();
            valueBook.showPage(currentPage.getControl());
        }
    }

    @Override
    public boolean select(Object toTest) {
        EObject eo = resollveInput(toTest);

        if (eo instanceof EnumerationLiteral) {
            EnumerationLiteral literal = (EnumerationLiteral) eo;

            if (literal.getEnumeration() != null) {
                PrimitiveType type =
                        EnumLitValueUtil.getBaseType(literal.getEnumeration());
                /*
                 * Don't show this section for String type Enumeration. The
                 * VALUE will be set the same as the Name of the literal.
                 */
                return !PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME.equals(type
                        .getName());
            }
        }

        return false;
    }

    @Override
    protected EObject resollveInput(Object object) {
        EObject eo = null;
        if (object instanceof IAdaptable) {
            eo = (EObject) ((IAdaptable) object).getAdapter(EObject.class);
        }
        return eo != null ? eo : super.resollveInput(object);
    }

    private ValuePage getPage() {
        ValuePage page = stringPage;

        EObject input = getInput();
        if (input instanceof EnumerationLiteral) {
            PrimitiveType type =
                    EnumLitValueUtil.getBaseType(((EnumerationLiteral) input)
                            .getEnumeration());

            if (PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME
                    .equals(type.getName())) {
                page = booleanPage;
            } else if (PrimitivesUtil.BOM_PRIMITIVE_DATETIME_NAME.equals(type
                    .getName())) {
                page = dateTimePage;
                dateTimePage.setDateType(DateType.DATETIME);
            } else if (PrimitivesUtil.BOM_PRIMITIVE_DATETIMETZ_NAME.equals(type
                    .getName())) {
                page = dateTimePage;
                dateTimePage.setDateType(DateType.DATETIMETZ);
            } else if (PrimitivesUtil.BOM_PRIMITIVE_DATE_NAME.equals(type
                    .getName())) {
                page = dateTimePage;
                dateTimePage.setDateType(DateType.DATE);
            } else if (PrimitivesUtil.BOM_PRIMITIVE_TIME_NAME.equals(type
                    .getName())) {
                page = dateTimePage;
                dateTimePage.setDateType(DateType.TIME);
            } else if (PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME.equals(type
                    .getName())
                    || PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME.equals(type
                            .getName())) {
                page = numberpage;
            }
        }

        return page;
    }

}
