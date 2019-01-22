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
package com.tibco.xpd.simulation.realdata.ui.wizard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.notify.AdapterFactory;
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

import com.tibco.xpd.simulation.realdata.Case;
import com.tibco.xpd.simulation.realdata.Cases;
import com.tibco.xpd.simulation.realdata.DocumentRoot;
import com.tibco.xpd.simulation.realdata.Paramter;
import com.tibco.xpd.simulation.realdata.RealDataFactory;
import com.tibco.xpd.simulation.realdata.RealDataPackage;
import com.tibco.xpd.simulation.realdata.provider.RealDataItemProviderAdapterFactory;


/**
 * Model for importer.
 * 
 * @author jarciuch
 */
public class RealDataModelImporter {

    

    /** Tabular data about case start events */
    private DataTable dataTable = null;

    /** Emf model rootObject */
    private DocumentRoot rootObject;

    /**
     * This caches an instance of the model package.
     */
    protected RealDataPackage modelPackage = RealDataPackage.eINSTANCE;

    /**
     * This caches an instance of the model factory.
     */
    protected RealDataFactory modelFactory = modelPackage
            .getRealDataFactory();

    private ComposedAdapterFactory adapterFactory;

    private AdapterFactoryEditingDomain editingDomain;

    public RealDataModelImporter() {
        super();
        createEditingDomain();
    }

    /**
     * Create a new model.
     */
    protected DocumentRoot createInitialModel() {
        EClass eClass = ExtendedMetaData.INSTANCE
                .getDocumentRoot(modelPackage);
        EStructuralFeature eStructuralFeature = eClass
                .getEStructuralFeature(getInitialObjectName());
        EObject rootObject = modelFactory.create(eClass);
        rootObject.eSet(eStructuralFeature, EcoreUtil
                .create((EClass) eStructuralFeature.getEType()));
        Cases casesElement = modelFactory.createCases();
        ((DocumentRoot) rootObject).setCases(casesElement);
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
                                .getDocumentRoot(modelPackage))
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

    public Cases getCases() {
        return rootObject.getCases();
    }
    
    private void createEditingDomain(){
        // Create an adapter factory that yields item providers.
        //
        List factories = new ArrayList();
        factories.add(new ResourceItemProviderAdapterFactory());
        factories.add(new RealDataItemProviderAdapterFactory());
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
    
    public void createCasesFromTableData() {
    	if (rootObject == null) {
    		rootObject = createInitialModel();
		}
    	Cases cases = modelFactory.createCases();
    	if (dataTable != null) {
    		List selColumnIndexes = dataTable.getSelectedColumnsIndexes();
    		List rows = dataTable.getData();
    		int timeColumnIndex = dataTable.getTimeColumnIndex();
    		for (Iterator iter = rows.iterator(); iter.hasNext();) {
				Object[] row = (Object[]) iter.next();
				Date caseTime = (Date) row[timeColumnIndex];
				Case processCase = modelFactory.createCase();
				processCase.setStartTime(caseTime);
				//parameters
				for (Iterator iter2 = selColumnIndexes.iterator(); iter2.hasNext();) {
					int paramIndex = ((Integer) iter2.next()).intValue();
					Paramter parameter = modelFactory.createParamter();
					parameter.setName(dataTable.getColumn(paramIndex).getParameterName());
					parameter.setValue(row[paramIndex].toString());
					processCase.getParamter().add(parameter);
				}
				cases.getCase().add(processCase);
			}
		}
    	rootObject.setCases(cases);
    }
    
}
