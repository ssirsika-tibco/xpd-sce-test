/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.implementer.nativeservices.databaseservice.properties.table;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.datatools.connectivity.IConnectionProfile;
import org.eclipse.datatools.connectivity.ui.navigator.ConnectionProfileContentProvider;
import org.eclipse.datatools.connectivity.ui.navigator.ConnectionProfileLabelProvider;
import org.eclipse.datatools.modelbase.sql.query.QuerySelectStatement;
import org.eclipse.datatools.modelbase.sql.query.QueryStatement;
import org.eclipse.datatools.modelbase.sql.query.ValueExpressionColumn;
import org.eclipse.datatools.modelbase.sql.query.helper.StatementHelper;
import org.eclipse.datatools.modelbase.sql.query.helper.ValueExpressionHelper;
import org.eclipse.datatools.modelbase.sql.query.util.SQLQuerySourceFormat;
import org.eclipse.datatools.modelbase.sql.schema.Database;
import org.eclipse.datatools.modelbase.sql.tables.Column;
import org.eclipse.datatools.sqltools.core.DatabaseIdentifier;
import org.eclipse.datatools.sqltools.core.profile.ProfileUtil;
import org.eclipse.datatools.sqltools.parsers.sql.SQLParserException;
import org.eclipse.datatools.sqltools.parsers.sql.SQLParserInternalException;
import org.eclipse.datatools.sqltools.parsers.sql.postparse.PostParseProcessor;
import org.eclipse.datatools.sqltools.parsers.sql.query.SQLQueryParseResult;
import org.eclipse.datatools.sqltools.parsers.sql.query.SQLQueryParserManager;
import org.eclipse.datatools.sqltools.parsers.sql.query.SQLQueryParserManagerProvider;
import org.eclipse.datatools.sqltools.parsers.sql.query.postparse.DataTypeResolver;
import org.eclipse.datatools.sqltools.parsers.sql.query.postparse.TableReferenceResolver;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.Transaction;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.impl.InternalTransactionalEditingDomain;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.db.api.DbToBomTransformUtil;
import com.tibco.xpd.bom.db.api.DbToBomUMLPropertyFactory;
import com.tibco.xpd.bom.modeler.diagram.part.UMLDiagramEditorUtil;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.implementer.nativeservices.NativeServicesActivator;
import com.tibco.xpd.implementer.nativeservices.databaseservice.database.SqlType;
import com.tibco.xpd.implementer.nativeservices.databaseservice.properties.utils.DatabaseUtil;
import com.tibco.xpd.implementer.nativeservices.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.UserInfoUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.complexdatatype.ComplexDataTypeExtPointHelper;
import com.tibco.xpd.ui.complexdatatype.ComplexDataTypeReference;
import com.tibco.xpd.ui.complexdatatype.ComplexDataTypesMergedInfo;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.JdbcResource;
import com.tibco.xpd.xpdExtension.ParticipantSharedResource;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ParticipantType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Code to generate a Database return Data Field along with an associated BOM
 * type. If a database connection is available and the return type can be
 * introspected then the BOM type will be populated with the appropriate fields.
 * 
 * @author NWilson
 * 
 */
public final class ReturnTypeGenerator {

    private static final String BOM_FILE_EXTENSION =
            "." + BOMResourcesPlugin.BOM_FILE_EXTENSION; //$NON-NLS-1$

    private static final Logger LOG = XpdResourcesPlugin.getDefault()
            .getLogger();

    /**
     * Generates a Data Field and BOM type for the Result Set that will be
     * returned from the given Database Service activity.
     * 
     * @param cmd
     *            The command to append the Data Field creation command to.
     * @param activity
     *            The database service task activity.
     * @return The ConceptPath for the generated Data Field.
     * @throws IOException
     *             If the BOM file could not be created.
     * @throws CoreException
     *             If the workspace could not be refreshed.
     */
    public ConceptPath generate(CompoundCommand cmd, final Activity activity)
            throws IOException, CoreException {
        IConnectionProfile profile =
                DatabaseUtil.getConnectionProfile(activity);
        if (profile == null) {
            boolean openQuestion =
                    MessageDialog
                            .openQuestion(Display.getCurrent().getActiveShell(),
                                    Messages.ReturnTypeGenerator_ProfileNotConfiguredProperly_shortdesc,
                                    Messages.ReturnTypeGenerator_ActivityIncorrectlyAssocDBProfile_longdesc);

            if (openQuestion) {
                // open the profile dialog and ask the user to select a profile
                String message =
                        Messages.DatabaseServiceSection_AssignProfileCommand;
                profile = pickConnectionProfile(message);
                if (profile != null) {
                    String connectionProfileName = profile.getName();
                    EditingDomain ed =
                            XpdResourcesPlugin.getDefault().getEditingDomain();
                    Participant dbUser = getDbUser(activity);
                    ParticipantSharedResource shared =
                            (ParticipantSharedResource) Xpdl2ModelUtil
                                    .getOtherElement(dbUser,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_ParticipantSharedResource());
                    CompoundCommand setValidProfileToParticipantCmd =
                            new CompoundCommand(message);
                    if (shared == null) {
                        shared =
                                XpdExtensionFactory.eINSTANCE
                                        .createParticipantSharedResource();

                        JdbcResource jdbcResource =
                                XpdExtensionFactory.eINSTANCE
                                        .createJdbcResource();

                        jdbcResource.setJdbcProfileName(connectionProfileName);
                        shared.setSharedResource(jdbcResource);
                        setValidProfileToParticipantCmd
                                .append(Xpdl2ModelUtil
                                        .getSetOtherElementCommand(ed,
                                                dbUser,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_ParticipantSharedResource(),
                                                shared));
                    } else {
                        JdbcResource jdbc = shared.getJdbc();
                        if (jdbc == null) {
                            jdbc =
                                    XpdExtensionFactory.eINSTANCE
                                            .createJdbcResource();
                            setValidProfileToParticipantCmd
                                    .append(SetCommand
                                            .create(ed,
                                                    shared,
                                                    XpdExtensionPackage.eINSTANCE
                                                            .getParticipantSharedResource_Jdbc(),
                                                    jdbc));
                        }
                        setValidProfileToParticipantCmd
                                .append(SetCommand
                                        .create(ed,
                                                jdbc,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getJdbcResource_JdbcProfileName(),
                                                connectionProfileName));
                    }
                    if (setValidProfileToParticipantCmd.canExecute()) {
                        ed.getCommandStack()
                                .execute(setValidProfileToParticipantCmd);
                    }
                }
            } else {
                return null;
            }
        }
        ConceptPath path = null;
        if (activity != null) {
            Implementation impl = activity.getImplementation();
            if (impl instanceof Task) {
                Task task = (Task) impl;
                TaskService service = task.getTaskService();
                if (service != null) {
                    Process process = Xpdl2ModelUtil.getProcess(service);
                    Package pckg = process.getPackage();
                    DataField field = Xpdl2Factory.eINSTANCE.createDataField();
                    String baseName =
                            Messages.ReturnTypeGenerator_DefaultFieldName;
                    String finalName = baseName;
                    int idx = 1;
                    while (Xpdl2ModelUtil.getDuplicateFieldOrParam(pckg,
                            field,
                            finalName) != null
                            || Xpdl2ModelUtil.getDuplicateFieldOrParam(pckg,
                                    field,
                                    NameUtil.getInternalName(finalName, true)) != null) {
                        idx++;
                        finalName = baseName + idx;
                    }
                    final String internalName =
                            NameUtil.getInternalName(finalName, true);
                    field.setName(internalName);
                    field.setIsArray(true);
                    final IProject project =
                            WorkingCopyUtil.getProjectFor(activity);

                    final Classifier[] classifierWraper = new Classifier[1];
                    ResourcesPlugin.getWorkspace()
                            .run(new IWorkspaceRunnable() {
                                @Override
                                public void run(IProgressMonitor monitor)
                                        throws CoreException {
                                    try {
                                        classifierWraper[0] =
                                                generateBomType(XpdResourcesPlugin
                                                        .getDefault()
                                                        .getEditingDomain(),
                                                        project,
                                                        activity,
                                                        activity.getName()
                                                                + internalName);
                                    } catch (IOException e) {
                                        throw new CoreException(
                                                new Status(
                                                        IStatus.ERROR,
                                                        NativeServicesActivator.PLUGIN_ID,
                                                        "IO Excepton.", e)); //$NON-NLS-1$
                                    }

                                }
                            },
                                    new NullProgressMonitor());
                    Classifier classifier = classifierWraper[0];
                    ExternalReference ref =
                            getExternalReference(project, classifier);
                    if (ref != null) {
                        field.setDataType(ref);
                    }
                    Xpdl2ModelUtil.setOtherAttribute(field,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName(),
                            finalName);
                    EditingDomain ed =
                            WorkingCopyUtil.getEditingDomain(process);
                    cmd.append(AddCommand.create(ed,
                            process,
                            Xpdl2Package.eINSTANCE
                                    .getDataFieldsContainer_DataFields(),
                            field));
                    path = new ConceptPath(field, classifier);
                }
            }
        }
        return path;
    }

    private Participant getDbUser(Activity activity) {
        Participant dbUser = null;
        // Get participant
        EObject[] performers = TaskObjectUtil.getActivityPerformers(activity);
        if (performers.length == 1) {
            if (performers[0] instanceof Participant) {
                Participant participant = (Participant) performers[0];
                if (ParticipantType.SYSTEM_LITERAL.equals(participant
                        .getParticipantType().getType())) {
                    dbUser = participant;
                }
            }
        }
        return dbUser;
    }

    private IConnectionProfile pickConnectionProfile(String message) {
        IConnectionProfile profile = null;
        ITreeContentProvider content = new ConnectionProfileContentProvider();
        ILabelProvider label = new ConnectionProfileLabelProvider();
        Object item = null;
        Shell shell = Display.getCurrent().getActiveShell();
        ElementTreeSelectionDialog dialog =
                new ElementTreeSelectionDialog(shell, label, content);
        dialog.setTitle(Messages.AbstractDatabaseSection_ConnectionProfilePickerDialogTitle);
        dialog.setMessage(message);
        dialog.setInput(ResourcesPlugin.getWorkspace().getRoot());
        if (dialog.open() == ElementTreeSelectionDialog.OK) {
            item = dialog.getFirstResult();
        }
        if (item instanceof IConnectionProfile) {
            profile = (IConnectionProfile) item;
        }
        return profile;
    }

    private ExternalReference getExternalReference(IProject project,
            Classifier classifier) {
        ExternalReference ref = null;
        if (classifier != null) {
            ComplexDataTypesMergedInfo complexTypesInfo =
                    ComplexDataTypeExtPointHelper
                            .getAllComplexDataTypesMergedInfo();
            if (complexTypesInfo.isValidComplexDataType(classifier)) {
                ComplexDataTypeReference complex =
                        complexTypesInfo
                                .getComplexDataTypeReference(classifier,
                                        project);
                if (complex != null) {
                    ref = Xpdl2Factory.eINSTANCE.createExternalReference();
                    ref.setLocation(complex.getLocation());
                    ref.setXref(complex.getXRef());
                    ref.setNamespace(complex.getNameSpace());
                }
            }
        }
        return ref;
    }

    /**
     * Generated a BOM file and class to contain the data from the Result Set.
     * 
     * @param ed
     *            transactional editing domain
     * @param project
     *            The project in which to generate the BOM file.
     * @param activity
     *            The database service task activity.
     * @param name
     *            The name of the type to generate.
     * @return The generated UML Class object.
     * @throws IOException
     *             If the BOM file could not be created.
     * @throws CoreException
     *             If the workspace could not be rereshed.
     */
    private Classifier generateBomType(TransactionalEditingDomain ed,
            IProject project, Activity activity, String name)
            throws IOException, CoreException {

        SpecialFolder special =
                SpecialFolderUtil.getCreateSpecialFolderOfKind(project,
                        BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND,
                        Messages.ReturnTypeGenerator_DefaultBomFolderName);
        IFolder folder = special.getFolder();
        if (folder.isDerived()) {
            folder.setDerived(false);
        }
        int idx = 0;
        String umlPackageName =
                String.format("%1$s.%2$s", //$NON-NLS-1$
                        ProjectUtil.getProjectId(WorkingCopyUtil
                                .getProjectFor(activity)),
                        name);
        while (folder.getFile(umlPackageName + BOM_FILE_EXTENSION).exists()) {
            idx++;
            umlPackageName = name + idx;
        }
        String actualFileName = umlPackageName + BOM_FILE_EXTENSION;

        IFile file = folder.getFile(actualFileName);
        URI uri =
                URI.createPlatformResourceURI(file.getFullPath().toString(),
                        true);

        // Start a write transaction as we don't want to execute this on the
        // command stack
        InternalTransactionalEditingDomain internalED =
                ((InternalTransactionalEditingDomain) ed);
        Transaction transaction = internalED.getActiveTransaction();
        Transaction newTransaction = null;
        if (transaction == null || transaction.isReadOnly()) {
            try {
                newTransaction =
                        internalED.startTransaction(false, Collections
                                .singletonMap(Transaction.OPTION_UNPROTECTED,
                                        Boolean.TRUE));
                return generateBomType(ed,
                        uri,
                        activity,
                        umlPackageName.toLowerCase(),
                        actualFileName,
                        project);

            } catch (InterruptedException e) {
                throw new CoreException(
                        new Status(
                                IStatus.ERROR,
                                NativeServicesActivator.PLUGIN_ID,
                                Messages.ReturnTypeGenerator_bomCreationFail_error_message,
                                e));
            } finally {
                if (newTransaction != null) {
                    try {
                        newTransaction.commit();
                    } catch (RollbackException e) {
                        throw new CoreException(
                                new Status(
                                        IStatus.ERROR,
                                        NativeServicesActivator.PLUGIN_ID,
                                        Messages.ReturnTypeGenerator_bomCreationFail_error_message,
                                        e));
                    }
                }
            }
        }

        return null;
    }

    /**
     * Generated a BOM file and class to contain the data from the Result Set.
     * 
     * @param ed
     *            transactional editing domain
     * @param uri
     *            BOM resource URI
     * @param activity
     *            the database service task activity
     * @param name
     *            name of type to generate
     * @param diagramName
     *            name to give the Diagram
     * @param project
     * @return The generated UML Class object.
     * @throws IOException
     *             If the BOM file could not be created.
     * @throws CoreException
     */
    private Classifier generateBomType(TransactionalEditingDomain ed, URI uri,
            Activity activity, String name, String diagramName, IProject project)
            throws IOException, CoreException {

        // Create an empty BOM
        Resource resource =
                UMLDiagramEditorUtil.createDiagram(uri,
                        name,
                        new NullProgressMonitor(),
                        null,
                        null);
        Class classifier = null;
        Model model = null;
        for (EObject eo : resource.getContents()) {
            if (eo instanceof Model) {
                model = (Model) eo;
                break;
            }
        }

        if (model != null) {
            String user =
                    UserInfoUtil.getProjectPreferences(project).getUserName();
            /*
             * Adds the annotation for the badge - includes author name and
             * timestamp
             */
            DbToBomTransformUtil.addAuthorTimestampAnnotationToModel(model,
                    user);
            /*
             * Create an annotation to identify that the BOM created is one for
             * DB.
             */
            DbToBomTransformUtil.addDBGeneratedAnnotationToModel(model);

            PackageableElement element =
                    model.createPackagedElement(activity.getName(),
                            UMLPackage.eINSTANCE.getClass_());
            if (element instanceof Class) {
                classifier = (Class) element;
                introspectResultType(ed, activity, classifier);
            }
        }

        resource.save(UMLDiagramEditorUtil.getSaveOptions());

        return classifier;
    }

    /**
     * Attempts to connect to the database described by the database service
     * task and introspects the result set populating the UML Class with the
     * appropriate fields.
     * 
     * @param activity
     *            The database service task activity.
     * @param classifier
     *            The UML Class to which fields should be added.
     */
    private void introspectResultType(TransactionalEditingDomain ed,
            Activity activity, Class classifier) {
        IConnectionProfile profile =
                DatabaseUtil.getConnectionProfile(activity);
        /*
         * Using the DTP API to retrieve the Database resultset. The resultset
         * will allow us to build a BOM that represents this.
         */
        if (profile == null) {
            return;
        }
        DatabaseIdentifier dbId = new DatabaseIdentifier(profile.getName());
        Database db = ProfileUtil.getDatabase(dbId);

        SqlType sqlType = DatabaseUtil.getSqlType(activity);

        /**
         * For select statement, introspect result type and create BOM with the
         * columns returned by the SQL Query
         */
        if (SqlType.SELECT_LITERAL.equals(sqlType)) {
            SQLQueryParserManager parserManager =
                    SQLQueryParserManagerProvider.getInstance()
                            .getParserManager(db.getVendor(), db.getVersion());
            PostParseProcessor tableRefResolver =
                    new TableReferenceResolver(db, null);
            PostParseProcessor dataTypeResolver = new DataTypeResolver();
            List postParseProcessors = new ArrayList();
            postParseProcessors.add(0, tableRefResolver);
            postParseProcessors.add(1, dataTypeResolver);
            SQLQuerySourceFormat sourceFormat =
                    SQLQuerySourceFormat.copyDefaultFormat();
            parserManager.configParser(sourceFormat, postParseProcessors);

            SQLQueryParseResult parseResult;

            try {
                parseResult =
                        parserManager.parseQuery(DatabaseUtil.getSql(activity));
                QueryStatement queryStatement = parseResult.getQueryStatement();

                if (queryStatement instanceof QuerySelectStatement) {

                    QuerySelectStatement querySelectStatement =
                            (QuerySelectStatement) queryStatement;

                    List<?> effectiveResultColumns =
                            StatementHelper
                                    .getEffectiveResultColumns(querySelectStatement);
                    introspectMetaData(ed, classifier, effectiveResultColumns);
                }
            } catch (SQLParserException e) {
                LOG.error(e);
            } catch (SQLParserInternalException e) {
                LOG.error(e);
            } catch (SQLException e) {
                LOG.error(e);
            }
        } else if (SqlType.STORED_PROC_LITERAL.equals(sqlType)) {
            // ValueExpressionHelper.getColumnsFromValueExpression(valueExpr)
        }
    }

    private void introspectMetaData(TransactionalEditingDomain ed,
            Class classifier, List<?> columns) throws SQLException {
        Iterator<?> columnIt = columns.iterator();

        while (columnIt.hasNext()) {

            ValueExpressionColumn colExpr =
                    (ValueExpressionColumn) columnIt.next();

            Column resolveColumnFromValueExpression =
                    ValueExpressionHelper
                            .resolveColumnFromValueExpression(colExpr);

            ValueExpressionHelper
                    .resolveValueExpressionDatatypeRecursively(colExpr);

            DbToBomUMLPropertyFactory.getInstance().addPropertyToClass(colExpr,
                    classifier);

        }
    }
}
