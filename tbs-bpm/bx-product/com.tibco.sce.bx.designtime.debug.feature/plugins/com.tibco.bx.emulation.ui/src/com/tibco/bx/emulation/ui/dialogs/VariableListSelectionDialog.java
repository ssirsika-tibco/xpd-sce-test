package com.tibco.bx.emulation.ui.dialogs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import com.tibco.bx.emulation.ui.Messages;
import com.tibco.bx.xpdl2bpel.util.XPDLUtils;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.DeclaredType;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.TypeDeclaration;

public class VariableListSelectionDialog extends SelectionTitleAreaDialog {

	private List<EObject> variableList;

	TableViewer tableViewer;

	public VariableListSelectionDialog(Shell shell, List<EObject> variableList) {
		super(shell);
		this.variableList = variableList;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite dialogArea = (Composite) super.createDialogArea(parent);
		GridData data = new GridData(GridData.FILL_BOTH);

		tableViewer = new TableViewer(dialogArea, SWT.MULTI | SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		Table table = tableViewer.getTable();
		data = new GridData(GridData.FILL_BOTH);
		data.heightHint = 12 * table.getItemHeight();
		table.setLayoutData(data);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		TableLayout tl = new TableLayout();
		table.setLayout(tl);
		tl.addColumnData(new ColumnWeightData(100));
		new TableColumn(table, SWT.NONE).setText(Messages.getString("VariableListSelectionDialog.column0.label")); //$NON-NLS-1$
		tl.addColumnData(new ColumnWeightData(300));
		new TableColumn(table, SWT.NONE).setText(Messages.getString("VariableListSelectionDialog.column1.label")); //$NON-NLS-1$
		table.getColumn(0).setMoveable(true);
		table.getColumn(1).setMoveable(true);

		tableViewer.setContentProvider(new VarContentProvider());
		tableViewer.setLabelProvider(new VarLabelProvider());
		tableViewer.setInput(variableList);

		tableViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent arg0) {
				okPressed();
			}

		});

		setTitle(Messages.getString("VariableListSelectionDialog.title")); //$NON-NLS-1$
		setMessage(Messages.getString("VariableListSelectionDialog.message")); //$NON-NLS-1$
		return dialogArea;
	}

	@Override
	protected void okPressed() {
		Iterator<EObject> iterator = ((IStructuredSelection) tableViewer.getSelection()).iterator();
		List<EObject> vList = new ArrayList<EObject>();
		while (iterator.hasNext()) {
			EObject variable = iterator.next();
			vList.add(variable);
		}
		setResult(vList);
		super.okPressed();
	}

	@Override
	protected Point getInitialSize() {
		return new Point(500, 300);
	}
}

/**
 * @see IStructuredContentProvider
 * 
 */
final class VarContentProvider implements IStructuredContentProvider {

	public Object[] getElements(Object element) {
		if (element instanceof List)
			return ((List) element).toArray();
		else
			return new Object[0];
	}

	public void dispose() {
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

}

/**
 * @see ITableLabelProvider
 * 
 */
final class VarLabelProvider implements ITableLabelProvider {

	public String getColumnText(Object element, int col) {
		EObject variable = (EObject) element;
		if (col == 0)
			return getVariableElementName(variable);
		if (col == 1) {
			String typeName = getVariableElementType(variable);
			if (isArrayVariable(variable)) {
				typeName = typeName + "[]"; //$NON-NLS-1$
			}
			return typeName;
		}
		return "";//$NON-NLS-1$
	}

	public Image getColumnImage(Object element, int columnIndex) {
		EObject variable = (EObject) element;
		if (columnIndex == 0)
			return WorkingCopyUtil.getImage(variable);
		if (columnIndex == 1)
			return null;
		return null;
	}

	public void addListener(ILabelProviderListener listener) {
	}

	public void dispose() {
	}

	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	public void removeListener(ILabelProviderListener listener) {
	}

	private String getVariableElementName(EObject element) {
		String name = null;
		if (element instanceof NamedElement) {
			name = ((NamedElement) element).getName();
		}
		return name;
	}

	private String getVariableElementType(EObject variable) {
		String typeName = null;
		DataType type = ((ProcessRelevantData) variable).getDataType();
		if (type instanceof BasicType) {
			typeName = ((BasicType) type).getType().getName();
		} else if (type instanceof ExternalReference) {
			String bomClassName = XPDLUtils.getBomClassName((ExternalReference) type);
			int lastDot = bomClassName.lastIndexOf("."); //$NON-NLS-1$
			typeName = bomClassName.substring(lastDot + 1) + " - " + bomClassName.substring(0, lastDot); //$NON-NLS-1$
		} else if (type instanceof DeclaredType) {
			String id = ((DeclaredType) type).getTypeDeclarationId();
			EObject object = WorkingCopyUtil.getWorkingCopyFor(type).resolveEObject(id);
			if (object != null && object instanceof TypeDeclaration) {
				typeName = ((TypeDeclaration) object).getName();
			}
			typeName = object.toString();
		} else {
			typeName = type.toString();
		}
		return typeName;
	}

	private boolean isArrayVariable(EObject variable) {
		return ((ProcessRelevantData) variable).isIsArray();
	}

}
