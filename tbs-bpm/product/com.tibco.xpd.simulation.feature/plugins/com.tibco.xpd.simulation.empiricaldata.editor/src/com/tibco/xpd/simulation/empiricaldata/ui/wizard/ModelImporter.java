/* 
 ** 
 **  MODULE:             $RCSfile: ModelImporter.java $ 
 **                      $Revision: 1.0 $ 
 **                      $Date: 2006-03-21 $ 
 ** 
 **  DESCRIPTION:           
 **                                              
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2006 TIBCO Software Inc, All Rights Reserved.
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: $ 
 ** 
 */
package com.tibco.xpd.simulation.empiricaldata.ui.wizard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.command.CommandStackListener;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.ui.IEditorPart;

import com.tibco.xpd.simulation.empiricaldata.DocumentRoot;
import com.tibco.xpd.simulation.empiricaldata.EmpiricalData;
import com.tibco.xpd.simulation.empiricaldata.EmpiricalDataFactory;
import com.tibco.xpd.simulation.empiricaldata.EmpiricalDataPackage;
import com.tibco.xpd.simulation.empiricaldata.Period;
import com.tibco.xpd.simulation.empiricaldata.PeriodType;
import com.tibco.xpd.simulation.empiricaldata.provider.EmpiricalDataItemProviderAdapterFactory;
import com.tibco.xpd.simulation.empiricaldata.util.Periods;
import com.tibco.xpd.simulation.empiricaldata.util.Periods.TimePeriod;

/**
 * Model for importer.
 * 
 * @author jarciuch
 */
public class ModelImporter {

    private List selectedPeriods = new LinkedList();

    private List availablePeriods = new LinkedList(Periods
            .getSubperiods(selectedPeriods));

    /** Tabular data about case start events */
    DataTable dataTable = null;

    /** Emf model rootObject */
    DocumentRoot rootObject;

    /**
     * This caches an instance of the model package.
     */
    protected EmpiricalDataPackage empiricalDataPackage = EmpiricalDataPackage.eINSTANCE;

    /**
     * This caches an instance of the model factory.
     */
    protected EmpiricalDataFactory empiricalDataFactory = empiricalDataPackage
            .getEmpiricalDataFactory();

    private EmpiricalDataFactory modelFactory = EmpiricalDataFactory.eINSTANCE;

    private ComposedAdapterFactory adapterFactory;

    private AdapterFactoryEditingDomain editingDomain;

    public ModelImporter() {
        super();
        createEditingDomain();
    }

    /**
     * Create a new model.
     */
    protected DocumentRoot createInitialModel() {
        EClass eClass = ExtendedMetaData.INSTANCE
                .getDocumentRoot(empiricalDataPackage);
        EStructuralFeature eStructuralFeature = eClass
                .getEStructuralFeature(getInitialObjectName());
        EObject rootObject = empiricalDataFactory.create(eClass);
        rootObject.eSet(eStructuralFeature, EcoreUtil
                .create((EClass) eStructuralFeature.getEType()));
        EmpiricalData empiricalData = modelFactory.createEmpiricalData();
        ((DocumentRoot) rootObject).setEmpiricalData(empiricalData);
        return (DocumentRoot) rootObject;
    }

    /**
     * Returns the names of the features representing global elements.
     */
    protected String getInitialObjectName() {
        List initialObjectNames = new ArrayList();
        for (Iterator elements = ExtendedMetaData.INSTANCE
                .getAllElements(
                        ExtendedMetaData.INSTANCE
                                .getDocumentRoot(empiricalDataPackage))
                .iterator(); elements.hasNext();) {
            EStructuralFeature eStructuralFeature = (EStructuralFeature) elements
                    .next();
            EClassifier eClassifier = eStructuralFeature.getEType();
            if (eClassifier instanceof EClass) {
                EClass eClass = (EClass) eClassifier;
                if (!eClass.isAbstract()) {
                    initialObjectNames.add(eStructuralFeature.getName());
                }
            }
        }
        Collections.sort(initialObjectNames, java.text.Collator.getInstance());
        // get first object so make sure that there is only one
        return (String) initialObjectNames.iterator().next();
    }

    public DataTable getDataTable() {
        return dataTable;
    }

    public void setDataTable(DataTable dataTable) {
        this.dataTable = dataTable;
    }

    public EObject getRootObject() {
        return rootObject;
    }

    public List getSelectedPeriods() {
        return selectedPeriods;
    }

    public List getAvailablePeriods() {
        return availablePeriods;
    }

    public void addToSelectedPeriods(Periods.TimePeriod period) {
        selectedPeriods.add(period);
        availablePeriods.clear();
        availablePeriods.addAll(Periods.getSubperiods(selectedPeriods));
    }

    public void removeFromSelectedPeriods(Periods.TimePeriod period) {
        selectedPeriods.remove(period);
        availablePeriods.clear();
        availablePeriods.addAll(Periods.getSubperiods(selectedPeriods));
    }

    public void createPeriods() {
        if (rootObject == null) {            
            rootObject = createInitialModel();
        }
        Iterator iter = selectedPeriods.iterator();
        getEmpiricalData().getPeriod().clear();
        createSubperiods(0, getEmpiricalData());
    }

    private void createSubperiods(int periodIndex, EObject parent) {
        if (periodIndex >= selectedPeriods.size())
            return;

        Periods.TimePeriod parentPeriod = (Periods.TimePeriod) selectedPeriods
                .get(periodIndex);
        Enumerator[] periodValues = parentPeriod.getValues();

        List parentPeriodsList = null;
        if (parent instanceof EmpiricalData) {
            parentPeriodsList = ((EmpiricalData) parent).getPeriod();
        } else if (parent instanceof Period) {
            parentPeriodsList = ((Period) parent).getPeriod();
        } else {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < periodValues.length; i++) {
            Period p = modelFactory.createPeriod();
            p.setName(periodValues[i].getName());
            p.setType(parentPeriod.getPeriodType());
            p.setWeightingFactor(0.0D);
            parentPeriodsList.add(p);
            if (periodIndex < selectedPeriods.size() - 1) {
                createSubperiods(periodIndex + 1, p);
            }
        }
    }

    private void addSubperiodsToBuffer(Period period, StringBuffer sb, int level) {
        List subperiods = period.getPeriod();
        for (Iterator iter = subperiods.iterator(); iter.hasNext();) {
            Period p = (Period) iter.next();
            for (int i = 0; i < level; i++) {
                sb.append("\t"); //$NON-NLS-1$
            }
            sb.append(p.getName()).append("\n"); //$NON-NLS-1$
            EList sp = p.getPeriod();
            if (!sp.isEmpty()) {
                addSubperiodsToBuffer(p, sb, level + 1);
            }
        }

    }

    public String periodsToString() {
        StringBuffer sb = new StringBuffer();
        List subperiods = getEmpiricalData().getPeriod();
        for (Iterator iter = subperiods.iterator(); iter.hasNext();) {
            Period p = (Period) iter.next();
            sb.append(p.getName()).append("\n"); //$NON-NLS-1$
            EList sp = p.getPeriod();
            if (!sp.isEmpty()) {
                    addSubperiodsToBuffer(p, sb, 1);
            }
        }
        return sb.toString();
    }

    public EmpiricalData getEmpiricalData() {
        return rootObject.getEmpiricalData();
    }
    
    private void createEditingDomain(){
        // Create an adapter factory that yields item providers.
        //
        List factories = new ArrayList();
        factories.add(new ResourceItemProviderAdapterFactory());
        factories.add(new EmpiricalDataItemProviderAdapterFactory());
        factories.add(new ReflectiveItemProviderAdapterFactory());

        adapterFactory = new ComposedAdapterFactory(factories);

        // Create the command stack that will notify this editor as commands are executed.
        //
        BasicCommandStack commandStack = new BasicCommandStack();

        // Create the editing domain with a special command stack.
        //
        editingDomain = new AdapterFactoryEditingDomain(adapterFactory, commandStack, new HashMap());
    }

    public AdapterFactory getAdapterFactory() {
        return adapterFactory;
    }
    
    public static void main(String[] args) {
        ModelImporter modelImporter = new ModelImporter();
        modelImporter.addToSelectedPeriods(Periods
                .getPeriod(PeriodType.MONTH_OF_YEAR_LITERAL.getName()));
        modelImporter.addToSelectedPeriods(Periods
                .getPeriod(PeriodType.DAY_OF_WEEK_LITERAL.getName()));
        modelImporter.addToSelectedPeriods(Periods
                .getPeriod(PeriodType.HOUR_OF_DAY_LITERAL.getName()));
        modelImporter.createPeriods();
    }


}
