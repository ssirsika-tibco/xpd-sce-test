/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.bom.dbimport;

import java.net.URL;
import java.util.List;

import org.eclipse.datatools.modelbase.sql.accesscontrol.SQLAccessControlPackage;
import org.eclipse.datatools.modelbase.sql.constraints.SQLConstraintsPackage;
import org.eclipse.datatools.modelbase.sql.datatypes.SQLDataTypesPackage;
import org.eclipse.datatools.modelbase.sql.expressions.SQLExpressionsPackage;
import org.eclipse.datatools.modelbase.sql.routines.SQLRoutinesPackage;
import org.eclipse.datatools.modelbase.sql.schema.SQLSchemaPackage;
import org.eclipse.datatools.modelbase.sql.statements.SQLStatementsPackage;
import org.eclipse.datatools.modelbase.sql.tables.SQLTablesPackage;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.uml2.uml.UMLPackage;
import org.openarchitectureware.type.emf.EmfMetaModel;
import org.openarchitectureware.xtend.XtendFacade;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;

/**
 * The transformer file the runs the OAW workflow to convert the Database model
 * to Business Object Model
 * 
 * @author rsomayaj
 * 
 */
public class XtendTransformDB2BOM {

    private static final String DB2BOM_TRANSFORM =
            "com::tibco::xpd::bom::dbimport::template::Db2Bom"; //$NON-NLS-1$

    private static final String TRANSFORM_EXPRESSION = "transform"; //$NON-NLS-1$

    private final static Logger LOG =
            XpdResourcesPlugin.getDefault().getLogger();

    private static XtendTransformDB2BOM INSTANCE;

    private XtendTransformDB2BOM() {
        // Private constructor
    }

    /**
     * Factory method
     * 
     * @return
     */
    public static XtendTransformDB2BOM getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new XtendTransformDB2BOM();
        }
        return INSTANCE;
    }

    /**
     * @param ed
     * @param bomFileToBeCreated
     * 
     */
    public boolean transform(TransactionalEditingDomain ed, List dbObjects,
            EObject modelObj) throws Exception {
        final XtendFacade f = XtendFacade.create(DB2BOM_TRANSFORM);
        configureXpdlToWsdlWorkflow(f);
        boolean result = false;
        if (modelObj != null) {
            result = runWorkflow(ed, f, modelObj, dbObjects);
        }
        return result;
    }

    /**
     * @param ed2
     * @param f
     * @param dbObjects
     */
    private boolean runWorkflow(TransactionalEditingDomain ed, XtendFacade f,
            EObject umlModel, List<EObject> dbObjects) throws Exception {
        try {
            f.call(TRANSFORM_EXPRESSION, dbObjects, umlModel);
        } catch (Exception e) {
            LOG.error(e);
        } finally {
        }
        return true;
    }

    /**
     * @param f
     */
    private void configureXpdlToWsdlWorkflow(final XtendFacade f) {
        URL url = null;
        // add ecore model for uml2
        EmfMetaModel uml2MetaModel =
                new EmfMetaModel((UMLPackage) EPackage.Registry.INSTANCE
                        .get(UMLPackage.eNS_URI));
        f.registerMetaModel(uml2MetaModel);

        // register ecore package
        EmfMetaModel ecoreMetaModel =
                new EmfMetaModel((EcorePackage) EPackage.Registry.INSTANCE
                        .get(EcorePackage.eNS_URI));
        f.registerMetaModel(ecoreMetaModel);

        EmfMetaModel sqlConstraints =
                new EmfMetaModel(
                        (SQLConstraintsPackage) EPackage.Registry.INSTANCE
                                .get(SQLConstraintsPackage.eNS_URI));
        f.registerMetaModel(sqlConstraints);

        EmfMetaModel sqlTables =
                new EmfMetaModel((SQLTablesPackage) EPackage.Registry.INSTANCE
                        .get(SQLTablesPackage.eNS_URI));
        f.registerMetaModel(sqlTables);

        EmfMetaModel sqlSchema =
                new EmfMetaModel((SQLSchemaPackage) EPackage.Registry.INSTANCE
                        .get(SQLSchemaPackage.eNS_URI));
        f.registerMetaModel(sqlSchema);

        EmfMetaModel sqlDatatypes =
                new EmfMetaModel(
                        (SQLDataTypesPackage) EPackage.Registry.INSTANCE
                                .get(SQLDataTypesPackage.eNS_URI));
        f.registerMetaModel(sqlDatatypes);

        EmfMetaModel sqlExpressions =
                new EmfMetaModel(
                        (SQLExpressionsPackage) EPackage.Registry.INSTANCE
                                .get(SQLExpressionsPackage.eNS_URI));
        f.registerMetaModel(sqlExpressions);

        EmfMetaModel sqlRoutines =
                new EmfMetaModel(
                        (SQLRoutinesPackage) EPackage.Registry.INSTANCE
                                .get(SQLRoutinesPackage.eNS_URI));
        f.registerMetaModel(sqlRoutines);

        EmfMetaModel sqlStatements =
                new EmfMetaModel(
                        (SQLStatementsPackage) EPackage.Registry.INSTANCE
                                .get(SQLStatementsPackage.eNS_URI));
        f.registerMetaModel(sqlStatements);

        EmfMetaModel sqlAccessControl =
                new EmfMetaModel(
                        (SQLAccessControlPackage) EPackage.Registry.INSTANCE
                                .get(SQLAccessControlPackage.eNS_URI));
        f.registerMetaModel(sqlAccessControl);
    }
}
