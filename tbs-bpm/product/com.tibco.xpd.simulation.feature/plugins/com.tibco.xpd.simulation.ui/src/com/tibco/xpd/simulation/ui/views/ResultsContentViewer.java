package com.tibco.xpd.simulation.ui.views;

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.IChangeNotifier;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.WrapperItemProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

import com.tibco.xpd.simulation.ui.Messages;

/**
 * A manager for the contents of the simulation results table.
 * 
 * @author NWilson
 */
public class ResultsContentViewer extends Composite {
    private TableViewer tableViewer;
    private TableViewer propertiesViewer;
    private Table table;
    private PageBook pageBook;
    private Table properties;
    private AdapterFactory factory;
    private TableSorter sorter;
    private Locale DEFAULT_LOCALE = Locale.getDefault();
    private String DEFAULT_TIME_FORMAT = "#######0.0000";   //$NON-NLS-1$    

    public ResultsContentViewer(Composite parent, AdapterFactory factory) {
        super(parent, SWT.NONE);
        this.factory = factory;

        GridLayout layout = new GridLayout();
        layout.numColumns = 1;
        layout.marginWidth = 0;
        layout.horizontalSpacing = 0;
        layout.verticalSpacing = 0;
        layout.marginHeight = 0;
        setLayout(layout);

        pageBook = new PageBook(this, SWT.NONE);
        GridData pageBookGridData = new GridData();
        pageBookGridData.grabExcessHorizontalSpace = true;
        pageBookGridData.grabExcessVerticalSpace = true;
        pageBookGridData.horizontalAlignment = SWT.FILL;
        pageBookGridData.verticalAlignment = SWT.FILL;
        pageBook.setLayoutData(pageBookGridData);

        sorter = new TableSorter();
        table = new Table(pageBook, SWT.FULL_SELECTION | SWT.MULTI);
        table.setHeaderVisible(true);
        GridData tableGridData = new GridData();
        tableGridData.grabExcessHorizontalSpace = true;
        tableGridData.grabExcessVerticalSpace = true;
        tableGridData.horizontalAlignment = SWT.FILL;
        tableGridData.verticalAlignment = SWT.FILL;
        table.setLayoutData(tableGridData);
        tableViewer = new TableViewer(table);
        table.setHeaderVisible(true);
        tableViewer.setContentProvider(new AdapterFactoryContentProvider(
                factory));
        tableViewer.setLabelProvider(new AdapterFactoryLabelProvider(factory));
        tableViewer.setSorter(sorter);

        properties = new Table(pageBook, SWT.FULL_SELECTION | SWT.MULTI);
        properties.setHeaderVisible(true);
        properties.setLayoutData(tableGridData);
        TableColumn column = new TableColumn(properties, SWT.NONE);
        column.setText(Messages.ResultsContentViewer_Property);
        column.setWidth(150);
        column = new TableColumn(properties, SWT.NONE);
        column.setText(Messages.ResultsContentViewer_Value);
        column.setWidth(250);
        propertiesViewer = new TableViewer(properties);
        propertiesViewer.setContentProvider(new PropertyContentProvider(
                new AdapterFactoryContentProvider(factory)));
        propertiesViewer.setLabelProvider(new PropertyLabelProvider(
                new AdapterFactoryLabelProvider(factory)));
    }

    private void propertyView() {
        pageBook.showPage(properties);
    }

    private void tableView() {
        pageBook.showPage(table);
    }

    /**
     * Updates the columns and data content for the table to match the data type
     * and contents of the object passed in.
     * 
     * @param modelObject
     *            The object model to display data for.
     */
    public void changeContent(Object input) {
        reset();
        if (input instanceof EObject) {
            EObject eObject = (EObject) input;
            IStructuredItemContentProvider provider = (IStructuredItemContentProvider) factory
                    .adapt(eObject, IStructuredItemContentProvider.class);
            if (provider instanceof ItemProviderAdapter) {
                ItemProviderAdapter adapter = (ItemProviderAdapter) provider;
                if (adapter instanceof IItemPropertySource) {
                    propertyView();
                    propertiesViewer.setInput(input);
                } else {
                    tableView();
                    List descriptors = adapter.getPropertyDescriptors(input);
                    for (Iterator i = descriptors.iterator(); i.hasNext();) {
                        IItemPropertyDescriptor descriptor = (IItemPropertyDescriptor) i
                                .next();
                        TableColumn column = new TableColumn(table, SWT.NONE);
                        column.setText(descriptor.getDisplayName(input));
                        column.setWidth(100);
                        column.setData(((EStructuralFeature) descriptor
                                .getFeature(input)).getEType()
                                .getInstanceClass());
                        column.addSelectionListener(sorter);
                    }
                    tableViewer.setInput(input);
                }
            }
        } else if (input instanceof ItemProviderAdapter) {
            tableView();
            ItemProviderAdapter adapter = (ItemProviderAdapter) input;
            List descriptors = adapter.getPropertyDescriptors(input);
            for (Iterator i = descriptors.iterator(); i.hasNext();) {
                IItemPropertyDescriptor descriptor = (IItemPropertyDescriptor) i
                        .next();
                TableColumn column = new TableColumn(table, SWT.NONE);
                column.setText(descriptor.getDisplayName(input));
                column.setWidth(100);
                column.setData(((EStructuralFeature) descriptor
                        .getFeature(input)).getEType().getInstanceClass());
                column.addSelectionListener(sorter);
                tableViewer.setInput(input);
            }
        } else if (input instanceof WrapperItemProvider) {
            tableView();
            WrapperItemProvider adapter = (WrapperItemProvider) input;
            List descriptors = adapter.getPropertyDescriptors(input);
            for (Iterator i = descriptors.iterator(); i.hasNext();) {
                IItemPropertyDescriptor descriptor = (IItemPropertyDescriptor) i
                        .next();
                TableColumn column = new TableColumn(table, SWT.NONE);
                column.setText(descriptor.getDisplayName(input));
                column.setWidth(100);
                column.setData(((EStructuralFeature) descriptor
                        .getFeature(input)).getEType().getInstanceClass());
                column.addSelectionListener(sorter);
                tableViewer.setInput(input);
            }
        } else {
            tableView();
            tableViewer.setInput(input);
        }
    }

    public void reset() {
        tableViewer.setInput(null);
        propertiesViewer.setInput(null);
        TableColumn[] columns = table.getColumns();
        for (int i = 0; i < columns.length; i++) {
            columns[i].dispose();
        }
    }

    class PropertyContentProvider implements ITreeContentProvider,
            INotifyChangedListener {
        private AdapterFactoryContentProvider provider;
        private Viewer viewer;
        private IChangeNotifier notifier;

        public PropertyContentProvider(AdapterFactoryContentProvider provider) {
            this.provider = provider;
            AdapterFactory factory = provider.getAdapterFactory();
            if (factory instanceof IChangeNotifier) {
                notifier = (IChangeNotifier) factory;
                notifier.addListener(this);
            }
        }

        public Object getParent(Object element) {
            return null;
        }

        public boolean hasChildren(Object element) {
            return provider.getPropertySource(element).getPropertyDescriptors().length > 0;
        }

        public Object[] getChildren(Object parentElement) {
            IPropertyDescriptor[] descriptors = provider.getPropertySource(
                    parentElement).getPropertyDescriptors();
            Property[] properties = new Property[descriptors.length];
            for (int i = 0; i < descriptors.length; i++) {
                properties[i] = new Property();
                properties[i].provider = provider;
                properties[i].parentElement = parentElement;
                properties[i].descriptor = descriptors[i];
            }
            return properties;
        }

        public Object[] getElements(Object inputElement) {
            return getChildren(inputElement);
        }

        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
            this.viewer = viewer;
            provider.inputChanged(viewer, oldInput, newInput);
        }

        public void dispose() {
            if (notifier != null) {
                notifier.removeListener(this);
                notifier = null;
            }
            if (provider != null) {
                provider.dispose();
            }
        }

        public void notifyChanged(Notification notification) {
            if (viewer != null) {
                viewer.refresh();
            }
        }
    }

    class Property {
        AdapterFactoryContentProvider provider;
        Object parentElement;
        IPropertyDescriptor descriptor;
    }

    class PropertyLabelProvider implements ITableLabelProvider {
        private AdapterFactoryLabelProvider provider;

        public PropertyLabelProvider(AdapterFactoryLabelProvider provider) {
            this.provider = provider;
        }

        public Image getColumnImage(Object element, int columnIndex) {
            return null;
        }

        public String getColumnText(Object element, int columnIndex) {
            String label = ""; //$NON-NLS-1$
            Property property = (Property) element;
            if (columnIndex == 0) {
                label = property.descriptor.getDisplayName();
            } else if (columnIndex == 1) {
                IItemLabelProvider labelProvider = (IItemLabelProvider) property.provider
                        .getPropertySource(property.parentElement)
                        .getPropertyValue(property.descriptor.getId());
                label = labelProvider.getText(property.descriptor);
                
                if (label.indexOf(".") != -1){  //$NON-NLS-1$
                    try {
                        DecimalFormat simulationTimeFormatter = (DecimalFormat)DecimalFormat.getInstance(DEFAULT_LOCALE);
                        simulationTimeFormatter.applyPattern(DEFAULT_TIME_FORMAT);
                        double doubleLabel = Double.parseDouble(label);
                        label = simulationTimeFormatter.format(doubleLabel);
                    }catch(NumberFormatException e){                       
                    }
                }
            }
            if (columnIndex == 4) {
                int h=0;
            }
            return label;
        }

        public void addListener(ILabelProviderListener listener) {
            provider.addListener(listener);
        }

        public void dispose() {
            provider.dispose();
        }

        public boolean isLabelProperty(Object element, String property) {
            return false;
        }

        public void removeListener(ILabelProviderListener listener) {
            provider.removeListener(listener);
        }
    }

    class TableSorter extends ViewerSorter implements SelectionListener {
        int column;
        Class type;

        public int compare(Viewer viewer, Object e1, Object e2) {
            if (e1 instanceof EObject && e2 instanceof EObject) {
                String s1 = ((AdapterFactoryLabelProvider) tableViewer
                        .getLabelProvider()).getColumnText(e1, column);
                String s2 = ((AdapterFactoryLabelProvider) tableViewer
                        .getLabelProvider()).getColumnText(e2, column);
                Object o1;
                Object o2;
                if (type == double.class) {
                    o1 = new Double(s1);
                    o2 = new Double(s2);
                } else if (type == int.class) {
                    o1 = new Integer(s1);
                    o2 = new Integer(s2);
                } else {
                    o1 = s1;
                    o2 = s2;
                }
                if (o1 instanceof Comparable) {
                    return ((Comparable) o1).compareTo(o2);
                }
                return super.compare(viewer, o1, o2);
            }
            return super.compare(viewer, e1, e2);
        }

        public void widgetSelected(SelectionEvent e) {
            Object source = e.getSource();
            if (source instanceof TableColumn) {
                TableColumn tableColumn = (TableColumn) source;
                column = table.indexOf(tableColumn);
                type = (Class) tableColumn.getData();
                tableViewer.refresh();
            }
        }

        public void widgetDefaultSelected(SelectionEvent e) {
        }

    }
}
