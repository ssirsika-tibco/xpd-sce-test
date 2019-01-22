/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.resources.internal.db.impl.derby;

import static com.tibco.xpd.resources.XpdResourcesPlugin.isTraceINDEX;
import static com.tibco.xpd.resources.XpdResourcesPlugin.traceINDEX;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdConfigOption;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.internal.db.Column;
import com.tibco.xpd.resources.internal.db.IResourceDb;
import com.tibco.xpd.resources.internal.db.ResourceDbException;
import com.tibco.xpd.resources.internal.indexer.IndexerServiceImpl;
import com.tibco.xpd.resources.logger.Logger;

/**
 * Derby Resource DB implementation
 * 
 * @author ramin
 * 
 */
public class ResourceDbDerby implements IResourceDb {

    private static final String DERBY_SYSTEM_HOME_PROP = "derby.system.home"; //$NON-NLS-1$

    private static final Logger LOG = XpdResourcesPlugin.getDefault()
            .getLogger();

    final static public int COLUMN_NAME = 0;

    final static public int COLUMN_TYPE = 1;

    final static public int COLUMN_URI = 2;

    final static public int COLUMN_PATH = 3;

    final static public int COLUMN_PROJECT = 4;

    final static public int COLUMN_UPDATETIMESTAMP = 5;

    final static public int COLUMN_NAME_LENGTH = 4096;

    final static public int COLUMN_TYPE_LENGTH = 255;

    final static public int COLUMN_URI_LENGTH = 4096;

    final static public int COLUMN_PATH_LENGTH = 4096;

    final static public int COLUMN_PROJECT_LENGTH = 255;

    public static final int COLUMN_UPDATETIMESTAMP_LENGTH = 255;

    private Properties dbProperties;

    private String systemDir;

    private String dbName;

    private static final String SQL_STATE_COLUMN_NOT_DEFINED = "42X14"; //$NON-NLS-1$

    //private static final String SQL_SCHEMA_DOES_NOT_EXIST = "42Y07"; //$NON-NLS-1$

    //private static final String SQL_TABLE_ALREADY_EXISTS = "X0Y32"; //$NON-NLS-1$

    private static final Pattern TABLE_PARTS_SEPARATOR = Pattern.compile("[.]"); //$NON-NLS-1$

    /**
     * This is needed because all queries do not define the columns in the
     * SELECT clause.
     */
    private final Map<String, Column[]> tableRegistry;

    private boolean doesVersionTableExist;

    /**
     * This flag is set when the close() method is called. No new connections
     * should be created after this flag is set.
     */
    private volatile boolean closing = false;

    /**
     * Lock used to cleanly shut down database connection.
     */
    private ReadWriteLock lock;

    /**
     * The accessLock lock should be used for all general access to the
     * database. The lock should be acquired just before the try-with-resource
     * statement containing the getConnection() call and should be released in
     * the finally block. This lock allows entry by multiple threads, but cannot
     * be entered at the same time as the closeLock.
     */
    private Lock accessLock;

    /**
     * This should only be used by the close() method to prevent database access
     * whilst the database is shutting down.
     */
    private Lock closeLock;

    /**
     * Version as it is defined in the Configuration.properties
     */
    private Map<String, Boolean> validationTable =
            new Hashtable<String, Boolean>();

    /**
     * Creates an instance of ResourceDbDerby with the default database name.
     */
    public ResourceDbDerby() {
        this(IResourceDb.RESOURCE_DB_NAME);
    }

    /**
     * Creates an instance of ResourceDbDerby with a custom database name.
     */
    public ResourceDbDerby(String dbName) {
        super();
        lock = new ReentrantReadWriteLock();
        accessLock = lock.readLock();
        closeLock = lock.writeLock();
        this.dbName = dbName;
        tableRegistry = new HashMap<String, Column[]>();
        setDBSystemDir();
        try {
            dbProperties = loadDBProperties();
        } catch (ResourceDbException e1) {
            XpdResourcesPlugin.getDefault().getLogger().error(e1);
            return;
        }
        String driverName = dbProperties.getProperty("derby.driver"); //$NON-NLS-1$

        try {
            loadDatabaseDriver(driverName);

            // Run configuration before accessing the database
            preStartup();

        } catch (Exception e1) {
            XpdResourcesPlugin.getDefault().getLogger().error(e1);
            return;
        }

        try {
            if (!dbExists()) {
                createDatabase();
            }
        } catch (ResourceDbException e) {
            throw new AssertionError(
                    "Creation of the derby db failed: " + e.getLocalizedMessage()); //$NON-NLS-1$
        }

        /*
         * Initialize the internal version table
         */
        try {
            doesVersionTableExist = initializeVersionTable();
        } catch (ResourceDbException e) {
            XpdResourcesPlugin
                    .getDefault()
                    .getLogger()
                    .error(e,
                            "Failed to create the VERSION table in the indexer."); //$NON-NLS-1$
        }

        try {
            printAllEntries();
        } catch (ResourceDbException e) {
            XpdResourcesPlugin.getDefault().getLogger().error(e);
        }
    }

    /**
     * Run pre-configuration before accessing the database. This method does
     * nothing here but subclasses may extend.
     * 
     * @throws CoreException
     */
    protected void preStartup() throws CoreException {
        // Do nothing
    }

    /**
     * Try to reconnect to the database if the connection has dropped. This
     * method does nothing here as this is running an embedded database but
     * subclasses may extend to try to reconnect to database if required.
     * 
     * @return
     * @throws Exception
     */
    protected boolean reconnect() throws Exception {
        return true;
    }

    /**
     * Checks if the db location dir exists
     * 
     * @return
     */
    private boolean dbExists() {
        boolean bExists = false;
        String dbLocation = getDatabaseLocation();
        File dbFileDir = new File(dbLocation);
        if (dbFileDir.exists()) {
            bExists = true;
        }
        return bExists;
    }

    /**
     * Create the database.
     * 
     * @throws ResourceDbException
     */
    private void createDatabase() throws ResourceDbException {
        Properties properties = new Properties(dbProperties);
        properties.put("create", "true"); //$NON-NLS-1$ //$NON-NLS-2$
        try (Connection c = getConnection(properties)) {
        } catch (SQLException e) {
            throw new ResourceDbException(e);
        }
    }

    /**
     * Get a new connection using the default properties.
     * 
     * @return a new connection.
     * @throws SQLException
     *             If the connection could not be created.
     */
    private Connection getConnection() throws SQLException {
        if (closing) {
            return null;
        }
        return DriverManager.getConnection(getDatabaseUrl(), dbProperties);
    }

    /**
     * Get a new connection using the provided properties.
     * 
     * @param properties
     *            The connection properties.
     * @return a new connection.
     * @throws SQLException
     *             If the connection could not be created.
     */
    private Connection getConnection(Properties properties) throws SQLException {
        return DriverManager.getConnection(getDatabaseUrl(), properties);
    }

    /**
     * Set the DB system directory.
     */
    private void setDBSystemDir() {
        IPath stateLocation =
                XpdResourcesPlugin.getDefault().getStateLocation();
        systemDir = stateLocation.toFile().getAbsolutePath() + "/db/"; //$NON-NLS-1$
        System.setProperty(DERBY_SYSTEM_HOME_PROP, systemDir);

        // create the db system directory

        File fileSystemDir = new File(systemDir);
        fileSystemDir.mkdir();
        XpdResourcesPlugin.getDefault().getLogger().debug("DB SYSTEM DIR = " //$NON-NLS-1$
                + systemDir);
    }

    /**
     * @param driverName
     * @throws ClassNotFoundException
     */
    private void loadDatabaseDriver(String driverName)
            throws ClassNotFoundException {
        // load Derby driver
        Class.forName(driverName);
    }

    /**
     * @return
     * @throws ResourceDbException
     */
    protected Properties loadDBProperties() throws ResourceDbException {
        InputStream dbPropInputStream = null;

        //select config properties for specific db mode.
        String configProperties = "Configuration.properties";   //$NON-NLS-1$
        
        // select in-memory mode for derby if memory option is set.
        if(XpdConfigOption.IN_MEMORY_INDEX_DB.isOn()){
        	configProperties = "Configuration_InMemory.properties";  //$NON-NLS-1$
        }
        
        dbPropInputStream =
                ResourceDbDerby.class
                        .getResourceAsStream(configProperties); //$NON-NLS-1$
        
        Properties props = new Properties();
        Properties config = new Properties();
        try {
            config.load(dbPropInputStream);

            String user = (String) config.get("user_notrans"); //$NON-NLS-1$
            String password = (String) config.get("password_notrans"); //$NON-NLS-1$
            String driverName = (String) config.get("derby.driver_notrans"); //$NON-NLS-1$
            String urlString = (String) config.get("derby.url_notrans"); //$NON-NLS-1$
            String tableName = (String) config.get("db.table_notrans"); //$NON-NLS-1$
            String schema = (String) config.get("db.schema_notrans"); //$NON-NLS-1$
            String pageSize = (String) config.get("derby.pageSize_notrans"); //$NON-NLS-1$
            String pageCacheSize =
                    (String) config.get("derby.pageCacheSize_notrans"); //$NON-NLS-1$
            String statementCacheSize =
                    (String) config.get("derby.statementCacheSize_notrans"); //$NON-NLS-1$

            props.put("user", user); //$NON-NLS-1$
            props.put("password", password); //$NON-NLS-1$
            props.put("derby.driver", driverName); //$NON-NLS-1$
            props.put("derby.url", urlString); //$NON-NLS-1$
            props.put("db.table", tableName); //$NON-NLS-1$
            props.put("db.schema", schema); //$NON-NLS-1$
            // SCF-173: Increased the memory for the database
            props.put("derby.storage.pageSize", pageSize); //$NON-NLS-1$ 
            props.put("derby.storage.pageCacheSize", pageCacheSize); //$NON-NLS-1$
            props.put("derby.language.statementCacheSize", statementCacheSize); //$NON-NLS-1$
        } catch (IOException ex) {
            XpdResourcesPlugin.getDefault().getLogger().error(ex);
            throw new ResourceDbException(ex);
        } finally {
            try {
                dbPropInputStream.close();
            } catch (IOException e) {
                XpdResourcesPlugin
                        .getDefault()
                        .getLogger()
                        .error(e,
                                "Problem closing the configuration file of the indexer database."); //$NON-NLS-1$
            }
        }
        return props;
    }

    /**
     * @return
     */
    public String getDatabaseLocation() {
        String dbLocation = System.getProperty(DERBY_SYSTEM_HOME_PROP) + dbName;
        XpdResourcesPlugin.getDefault().getLogger().debug("DB LOCATION = " //$NON-NLS-1$
                + dbLocation);
        return dbLocation;
    }

    /**
     * @return
     */
    public String getDatabaseUrl() {
    	String protocolName = "jdbc:derby:"; //$NON-NLS-1$
        
        // Override the protocolName in case of in-memory indexing.
        if (XpdConfigOption.IN_MEMORY_INDEX_DB.isOn()) {
            protocolName = "jdbc:derby:memory:"; //$NON-NLS-1$
         }
        String dbUrl = protocolName + IResourceDb.RESOURCE_DB_NAME; //$NON-NLS-1$
        XpdResourcesPlugin.getDefault().getLogger().debug("DB URL = " //$NON-NLS-1$
                + dbUrl);
        
        return dbUrl;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.resources.db.IResourceDb#showAllEntries()
     */
    @Override
    @Deprecated
    public Collection<IndexerItem> printAllEntries() throws ResourceDbException {

        if (!isTraceINDEX()) {
            return null;
        } else {
            ArrayList<IndexerItem> result = new ArrayList<IndexerItem>();

            traceINDEX("=== <TIBCO RESOURCES DB ALL ENTRIES> ==="); //$NON-NLS-1$

            String newLn = System.getProperty("line.separator"); //$NON-NLS-1$
            Iterator<String> indexerIds = tableRegistry.keySet().iterator();
            while (indexerIds.hasNext()) {
                String indexerId = indexerIds.next();

                HashMap<String, String> criteria =
                        new HashMap<String, String>();
                ArrayList<IndexerItem> result2 =
                        (ArrayList<IndexerItem>) query(indexerId, criteria);

                result.addAll(result2);

                Iterator<IndexerItem> it = result2.iterator();
                while (it.hasNext()) {
                    IndexerItem item = it.next();
                    StringBuffer buf = new StringBuffer();
                    buf.append(newLn + " table = " + indexerId); //$NON-NLS-1$
                    buf.append(newLn + " name = " + item.getName()); //$NON-NLS-1$
                    //                    buf.append("\n    type                 = " + item.getType()); //$NON-NLS-1$
                    buf.append(newLn + " URI = " + item.getURI()); //$NON-NLS-1$

                    Iterator<String> it2 = item.keys().iterator();
                    while (it2.hasNext()) {
                        String key = it2.next();
                        if (key.contains("file") || key.contains("path")) { //$NON-NLS-1$ //$NON-NLS-2$
                            String displayKey = normalize(key, 20);
                            buf.append(newLn
                                    + " " + displayKey + " = " + item.get(key)); //$NON-NLS-1$ //$NON-NLS-2$
                        }
                    }

                    traceINDEX(indexerId, buf.toString());
                }
            }

            traceINDEX("=== </TIBCO RESOURCES DB ALL ENTRIES> ==="); //$NON-NLS-1$

            return result;
        }
    }

    /**
     * @param s
     * @param length
     * @return
     */
    private String normalize(String s, int length) {
        StringBuffer buf = new StringBuffer(s);
        while (buf.length() < length) {
            buf.append(" "); //$NON-NLS-1$
        }
        buf.setLength(length);
        return buf.toString();
    }

    /**
     * Closes datasource.
     * 
     * @throws ResourceDbException
     */
    @Override
    public void close() throws ResourceDbException {
        closing = true;

        Properties properties = new Properties(dbProperties);
        properties.put("shutdown", "true"); //$NON-NLS-1$ //$NON-NLS-2$
        closeLock.lock();
        try (Connection c = getConnection(properties)) {
        } catch (SQLException e) {
            // Derby always throws an exception on shutdown, this is expected.
            XpdResourcesPlugin.getDefault().getLogger()
                    .info("Derby database closed."); //$NON-NLS-1$
        } finally {
            closeLock.unlock();
        }
    }

    /**
     * Query all resource from database which are specified by type and returns
     * it.
     * 
     * @param searchString
     *            depending on the match flag: match set: all resources where
     *            the searchString is part of there name match false: all
     *            resources with the name of the searchString, this can be used
     *            to detect duplicate names.
     * @param type
     *            filters the type of the resource item
     * 
     * @return List of ResourceEntity
     * @throws SQLException
     *             if SQL error ocurred
     */
    @Override
    public Collection<IndexerItem> query(final String indexerId,
            final Map<String, String> criteria) throws ResourceDbException {
        UninterruptibleJob<Collection<IndexerItem>> job =
                new UninterruptibleJob<Collection<IndexerItem>>() {

                    @Override
                    protected Collection<IndexerItem> run()
                            throws ResourceDbException {
                        return doQuery(indexerId, criteria);
                    }
                };

        return job.start();
    }

    private Collection<IndexerItem> doQuery(String indexerId,
            Map<String, String> criteria) throws ResourceDbException {
        try {

            List<IndexerItem> result = new ArrayList<IndexerItem>();
            if (!isValid(indexerId)) {
                return result;
            }
            StringBuffer whereClause = new StringBuffer();

            if (criteria != null && !criteria.isEmpty()) {
                whereClause.append(" WHERE "); //$NON-NLS-1$
                Iterator<String> it = criteria.keySet().iterator();
                for (boolean firstTime = true; it.hasNext();) {
                    String attribute = it.next();
                    if (firstTime) {
                        firstTime = false;
                    } else {
                        whereClause.append(" and "); //$NON-NLS-1$
                    }
                    whereClause.append(attribute);
                    whereClause.append(" = ?"); //$NON-NLS-1$
                }
            }

            // Get results from all indexers
            if (indexerId.equals("*")) { //$NON-NLS-1$
                for (String id : tableRegistry.keySet()) {
                    Collection<IndexerItem> query = query(id, criteria);

                    if (query != null) {
                        result.addAll(query);
                    }
                }

                return result;
            }

            accessLock.lock();
            try (Connection connection = getConnection()) {

                if (connection != null) {
                    StringBuffer sql = new StringBuffer("SELECT * FROM "); //$NON-NLS-1$
                    sql.append(getTableName(indexerId));
                    sql.append(whereClause);

                    String sqlString = sql.toString();
                    /*
                     * SCF-178: Not caching these prepared statements as it
                     * causes concurrent issues as this method can get called
                     * from multiple threads.
                     */
                    PreparedStatement s =
                            connection.prepareStatement(sqlString);

                    // set the criteria values
                    if (criteria != null) {
                        int pos = 1;
                        for (Entry<String, String> entry : criteria.entrySet()) {
                            s.setString(pos++, entry.getValue());
                        }
                    }

                    ResultSet rs = null;
                    try {
                        rs = s.executeQuery();

                        String name = ""; //$NON-NLS-1$
                        String type = ""; //$NON-NLS-1$
                        String uri = ""; //$NON-NLS-1$

                        final Column[] columns = tableRegistry.get(indexerId);
                        while (rs.next()) {
                            Map<String, String> map =
                                    new HashMap<String, String>(
                                            columns.length - 3);
                            for (int columnIndex = 0; columnIndex < columns.length; columnIndex++) {

                                String columnName =
                                        columns[columnIndex].getName();
                                String value = rs.getString(columnIndex + 1);
                                if (columnName
                                        .equals(IndexerServiceImpl.ATTRIBUTE_NAME)) {
                                    name = value;
                                } else if (columnName
                                        .equals(IndexerServiceImpl.ATTRIBUTE_TYPE)) {
                                    type = value;
                                } else if (columnName
                                        .equals(IndexerServiceImpl.ATTRIBUTE_URI)) {
                                    uri = value;
                                } else {
                                    map.put(columnName, value);
                                }
                            }

                            result.add(new IndexerItemImpl(name, type, uri, map));
                        }

                    } finally {
                        if (rs != null) {
                            rs.close();
                        }
                        s.close();
                    }
                }
            } catch (SQLException ex) {
                XpdResourcesPlugin.getDefault().getLogger().error(ex);
                throw new ResourceDbException(ex);
            } finally {
                accessLock.unlock();
            }
            return result;
        } catch (Exception ex) {
            XpdResourcesPlugin.getDefault().getLogger().error(ex);
            throw new ResourceDbException(ex);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.resources.db.IResourceDb#clean()
     */
    @Override
    public void cleanProject(final String projectName)
            throws ResourceDbException {
        UninterruptibleJob<Void> job = new UninterruptibleJob<Void>() {

            @Override
            protected Void run() throws ResourceDbException {
                return doCleanProject(projectName);
            }
        };

        job.start();
    }

    private Void doCleanProject(String projectName) throws ResourceDbException {
        Iterator<String> indexerIds = tableRegistry.keySet().iterator();
        while (indexerIds.hasNext()) {
            String indexerId = indexerIds.next();
            cleanProject(indexerId, projectName);
        }
        return null;
    }

    /**
     * @param indexerId
     * @param projectName
     * @throws ResourceDbException
     */
    private void cleanProject(String indexerId, String projectName)
            throws ResourceDbException {
        if (!isValid(indexerId)) {
            return;
        }
        accessLock.lock();
        try (Connection connection = getConnection()) {

            if (connection != null) {
                Statement statement = connection.createStatement();
                try {
                    statement.executeUpdate(String
                            .format("DELETE FROM %1$s WHERE %2$s = '%3$s'", //$NON-NLS-1$
                                    getTableName(indexerId),
                                    IndexerServiceImpl.ATTRIBUTE_PROJECT,
                                    projectName));
                } finally {
                    statement.close();
                }
            }
        } catch (SQLException ex) {
            XpdResourcesPlugin.getDefault().getLogger().error(ex);
            throw new ResourceDbException(ex);
        } finally {
            accessLock.unlock();
        }

    }

    /**
     * Cleans out entries for a specific path in the given indexer.
     * 
     * @see com.tibco.xpd.resources.db.IResourceDb#clean()
     */
    @Override
    public void cleanPath(final String indexerId, final String path) {
        UninterruptibleJob<Void> job = new UninterruptibleJob<Void>() {

            @Override
            protected Void run() throws ResourceDbException {
                return doCleanPath(indexerId, path);
            }
        };

        try {
            job.start();
        } catch (ResourceDbException e) {
            XpdResourcesPlugin.getDefault().getLogger().error(e);
        }
    }

    private Void doCleanPath(String indexerId, String path) {
        if (isValid(indexerId)) {
            accessLock.lock();
            try (Connection connection = getConnection()) {
                if (connection != null) {

                    cleanPathInternal(indexerId, path, connection);
                }
            } catch (SQLException ex) {
                XpdResourcesPlugin.getDefault().getLogger().error(ex);
            } finally {
                accessLock.unlock();
            }
        }
        return null;
    }

    /**
     * @param indexerId
     * @param path
     * @param connection
     * @throws SQLException
     */
    private void cleanPathInternal(String indexerId, String path,
            Connection connection) throws SQLException {
        PreparedStatement s =
                connection.prepareStatement(String
                        .format("DELETE FROM %1$s WHERE %2$s = ?", //$NON-NLS-1$
                                getTableName(indexerId),
                                IndexerServiceImpl.ATTRIBUTE_PATH));

        s.setString(1, path);
        s.execute();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.resources.db.IResourceDb#drop(String)
     */
    @Override
    public void drop(final String indexerId) throws ResourceDbException {
        UninterruptibleJob<Void> job = new UninterruptibleJob<Void>() {

            @Override
            protected Void run() throws ResourceDbException {
                return doDrop(indexerId);
            }
        };

        job.start();
    }

    private Void doDrop(String indexerId) throws ResourceDbException {
        accessLock.lock();
        try (Connection connection = getConnection()) {

            if (connection != null) {
                Statement statement = connection.createStatement();
                try {
                    statement.execute(String.format("DROP TABLE %1$s", //$NON-NLS-1$
                            getTableName(indexerId)));
                } finally {
                    statement.close();
                }
            }
        } catch (SQLException ex) {
            XpdResourcesPlugin.getDefault().getLogger().error(ex);
            throw new ResourceDbException(ex);
        } finally {
            accessLock.unlock();
        }
        return null;
    }

    /**
     * Check if the table for the given indexer id exists.
     * 
     * @param indexerId
     * @return <code>true</code> if the table exists.
     * @throws ResourceDbException
     * 
     * @since 3.5
     */
    public boolean doesTableExist(final String indexerId)
            throws ResourceDbException {
        UninterruptibleJob<Boolean> job = new UninterruptibleJob<Boolean>() {

            @Override
            protected Boolean run() throws ResourceDbException {
                accessLock.lock();
                try (Connection c = getConnection()) {
                    return doesTableExist(c, indexerId);
                } catch (SQLException e) {
                    throw new ResourceDbException(e);
                } finally {
                    accessLock.unlock();
                }
            }
        };

        return job.start();
    }

    /**
     * Check if the table for the given indexer id exists.
     * 
     * @param indexerId
     * @return <code>true</code> if the table exists.
     * @throws ResourceDbException
     * 
     * @since 3.5
     */
    private boolean doesTableExist(Connection connection, String indexerId)
            throws SQLException {

        if (indexerId != null) {
            ResultSet rSet = null;
            if (connection != null) {
                String tableName = getTableName(indexerId).toUpperCase();
                rSet =
                        connection.getMetaData().getTables(null,
                                null,
                                tableName,
                                new String[] { "TABLE" }); //$NON-NLS-1$
                while (rSet.next()) {
                    if (tableName.equals(rSet.getString("TABLE_NAME"))) { //$NON-NLS-1$
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Execute the given SQL statement
     * 
     * @param cols
     *            number of columns to return in the result
     * @param sql
     *            SQL statement
     * @param parameters
     *            parameters to pass to the SQL statement
     * @return Result in a 2-dimensional array. This will return all results of
     *         the SQL statement, each row containing data from the number of
     *         columns specified (i.e., if <i>cols</i> is set to 2 then result
     *         set will be String[<i>x</i>][2]).
     * @throws ResourceDbException
     * 
     * @since 3.5
     */
    public String[][] execute(final int cols, final String sql,
            final String... parameters) throws ResourceDbException {
        UninterruptibleJob<String[][]> job =
                new UninterruptibleJob<String[][]>() {

                    @Override
                    protected String[][] run() throws ResourceDbException {
                        return doExecute(cols, sql, parameters);
                    }
                };

        return job.start();
    }

    private String[][] doExecute(int cols, String sql, String... parameters)
            throws ResourceDbException {
        String[][] res = null;
        PreparedStatement statement = null;
        if (sql != null) {
            accessLock.lock();
            try (Connection connection = getConnection()) {
                if (connection != null) {
                    statement = connection.prepareStatement(sql);
                    for (int idx = 0; idx < parameters.length; idx++) {
                        statement.setString(idx + 1, parameters[idx]);
                    }

                    if (cols > 0) {
                        ResultSet rSet = statement.executeQuery();
                        List<String[]> result = new ArrayList<String[]>();
                        try {
                            while (rSet.next()) {
                                String[] row = new String[cols];
                                for (int x = 0; x < cols; x++) {
                                    row[x] = rSet.getString(x + 1);
                                }
                                result.add(row);
                            }
                        } finally {
                            rSet.close();
                        }
                        return result.toArray(new String[result.size()][cols]);
                    } else {
                        statement.execute();
                    }
                }
            } catch (SQLException e) {
                throw new ResourceDbException(e);
            } finally {
                accessLock.unlock();
            }
        }

        return res;
    }

    /**
     * Run the Update SQL statement.
     * 
     * @param sql
     *            update SQL statement
     * @param parameters
     *            parameters for the SQL statement
     * @throws ResourceDbException
     * 
     * @since 3.5
     */
    public void update(String sql, String... parameters)
            throws ResourceDbException {
        execute(0, sql, parameters);
    }

    /**
     * Insert the given data into the given index.
     * 
     * @param indexerId
     *            index to update
     * @param parameters
     *            data to insert into the index
     * @throws ResourceDbException
     * 
     * @since 3.5
     */
    public void insert(final String indexerId, final String[][] parameters)
            throws ResourceDbException {
        UninterruptibleJob<Void> job = new UninterruptibleJob<Void>() {

            @Override
            protected Void run() throws ResourceDbException {
                return doInsert(indexerId, parameters);
            }
        };

        job.start();
    }

    private Void doInsert(String indexerId, String[][] parameters)
            throws ResourceDbException {

        if (indexerId != null && parameters != null && parameters.length > 0
                && parameters[0] != null && parameters[0].length > 0) {

            StringBuffer sql = new StringBuffer("INSERT INTO "); //$NON-NLS-1$
            sql.append(getTableName(indexerId));
            sql.append(" VALUES("); //$NON-NLS-1$

            for (int x = 0; x < parameters[0].length; x++) {
                sql.append(x > 0 ? ",?" : "?"); //$NON-NLS-1$ //$NON-NLS-2$
            }
            sql.append(")"); //$NON-NLS-1$

            accessLock.lock();
            try (Connection connection = getConnection()) {
                if (connection != null) {
                    try {
                        connection.setAutoCommit(false);
                        String sqlStr = sql.toString();
                        PreparedStatement statement =
                                connection.prepareStatement(sqlStr);
                        if (parameters.length > 1) {
                            // Batch process
                            for (String[] param : parameters) {
                                for (int x = 0; x < param.length; x++) {
                                    statement.setString(x + 1, param[x]);
                                }
                                statement.addBatch();
                            }
                            statement.executeBatch();

                        } else {
                            // Single statement to execute
                            for (int x = 0; x < parameters[0].length; x++) {
                                statement.setString(x + 1, parameters[0][x]);
                            }

                            statement.execute();
                        }
                        connection.commit();
                    } catch (SQLException e) {
                        connection.rollback();
                        throw e;
                    }
                }
            } catch (SQLException e) {
                throw new ResourceDbException(e);
            } finally {
                accessLock.unlock();
            }
        }
        return null;
    }

    /**
     * @param statement
     * @throws ResourceDbException
     */
    @Override
    public void initTable(String indexerId, String bundleVersion,
            Column[] columns) throws ResourceDbException {

        validationTable.put(indexerId, false);

        // Add the default columns
        Column[] newColumns = new Column[columns.length + 6];
        System.arraycopy(columns, 0, newColumns, 6, columns.clone().length);

        newColumns[COLUMN_NAME] =
                new Column(IndexerServiceImpl.ATTRIBUTE_NAME, "" //$NON-NLS-1$
                        + COLUMN_NAME_LENGTH);
        newColumns[COLUMN_TYPE] =
                new Column(IndexerServiceImpl.ATTRIBUTE_TYPE, "" //$NON-NLS-1$
                        + COLUMN_TYPE_LENGTH);
        newColumns[COLUMN_URI] =
                new Column(IndexerServiceImpl.ATTRIBUTE_URI, "" //$NON-NLS-1$
                        + COLUMN_URI_LENGTH);
        /*
         * SCF-173: Index the path column as this is heavily used during
         * delete/update
         */
        newColumns[COLUMN_PATH] =
                new Column(IndexerServiceImpl.ATTRIBUTE_PATH, "" //$NON-NLS-1$
                        + COLUMN_PATH_LENGTH, true);
        newColumns[COLUMN_PROJECT] =
                new Column(IndexerServiceImpl.ATTRIBUTE_PROJECT, "" //$NON-NLS-1$
                        + COLUMN_PROJECT_LENGTH);
        newColumns[COLUMN_UPDATETIMESTAMP] =
                new Column(IndexerServiceImpl.ATTRIBUTE_UPDATETIMESTAMP, "" //$NON-NLS-1$
                        + COLUMN_UPDATETIMESTAMP_LENGTH);

        tableRegistry.put(indexerId, newColumns);

        /*
         * get primary key element for the indexer if they are defined
         */
        String[] keyAttributes = IndexerServiceImpl.getIndexerKeyAttributes();

        /*
         * If two wsdls use the same xsd and when the second wsdl is imported,
         * it results in duplicate key in the indexer. This is because URI would
         * be the same for both wsdls. To avoid getting duplicate key in the
         * indexer, we have decided to i)allow the indexers to define their own
         * primary key(s) ii)or not to have any primary key.
         * 
         * 1. if indexer key element is not defined then the primary key would
         * be the default primary key i.e. URI
         * 
         * 2. if indexer key element is defined and the indexer has defined its
         * own key(s) to be used as primary key then those key(s) would be the
         * primary key for the indexer
         * 
         * 3. if indexer key element is defined but is empty (no keys defined)
         * then the indexer will not have any primary key. (eg.
         * wsdlSchemaIndexer)
         */
        String[] primaryKey = null;
        if (null == keyAttributes) {
            primaryKey = new String[1];
            primaryKey[0] = IndexerServiceImpl.ATTRIBUTE_URI;
        } else {
            if (null != keyAttributes && keyAttributes.length > 0) {
                primaryKey = new String[keyAttributes.length];
                for (int i = 0; i < keyAttributes.length; i++) {
                    primaryKey[i] = keyAttributes[i];
                }
            } else {
                primaryKey = null;
            }
        }

        initializeTable(indexerId, bundleVersion, newColumns, primaryKey);

        // If it's got this far then the table has been initialized successfully
        validationTable.put(indexerId, true);
    }

    /**
     * Initialize the given indexer table. If the table does not exist then this
     * will create it.
     * 
     * @param indexerId
     *            indexer table
     * @param bundleVersion
     * @param columns
     * @param primaryKey
     *            primary key to set (could be more than one if indexers define
     *            so), <code>null</code> if none should be set.
     * @throws ResourceDbException
     */
    public void initializeTable(final String indexerId,
            final String bundleVersion, final Column[] columns,
            final String[] primaryKey) throws ResourceDbException {
        UninterruptibleJob<Void> job = new UninterruptibleJob<Void>() {

            @Override
            protected Void run() throws ResourceDbException {
                return doInitializeTable(indexerId,
                        bundleVersion,
                        columns,
                        primaryKey);
            }
        };

        job.start();
    }

    private Void doInitializeTable(String indexerId, String bundleVersion,
            Column[] columns, String[] primaryKey) throws ResourceDbException {
        accessLock.lock();
        try (Connection connection = getConnection()) {

            if (connection != null && indexerId != null && columns != null
                    && columns.length > 0) {
                boolean doCreateTable = false;
                if (doesTableExist(connection, indexerId)) {
                    /*
                     * Table exists so check if the bundle version match - if
                     * not then the table needs to be recreated as this is a
                     * different version of Studio.
                     */
                    String versionFromDb = null;
                    try {
                        versionFromDb =
                                getVersionFromDb(indexerId, bundleVersion);
                    } catch (SQLException e) {
                        XpdResourcesPlugin
                                .getDefault()
                                .getLogger()
                                .error(e,
                                        String.format("Failed to read the bundle version of indexer '%s' from the database.", //$NON-NLS-1$
                                                indexerId));
                        throw new ResourceDbException(e);
                    }
                    if (versionFromDb == null) {
                        return null;
                    }

                    if (!bundleVersion.equals(versionFromDb)) {
                        doCreateTable = true;
                        doDrop(indexerId);
                    }
                } else {
                    // Indexer table does not exist
                    doCreateTable = true;
                }

                if (doCreateTable) {
                    String tableName = getTableName(indexerId);
                    StringBuffer sqlStringBuffer =
                            new StringBuffer("CREATE TABLE " + tableName + "("); //$NON-NLS-1$ //$NON-NLS-2$
                    for (Column column : columns) {
                        sqlStringBuffer.append(column.getName()
                                + " varchar(" + column.getLength() + "),"); //$NON-NLS-1$ //$NON-NLS-2$
                    }

                    if (primaryKey != null) {
                        /*
                         * update the sql string for primary key
                         */
                        if (primaryKey.length > 0) {
                            sqlStringBuffer.append("PRIMARY KEY("); //$NON-NLS-1$
                            for (int i = 0; i < primaryKey.length; i++) {
                                if (null != primaryKey[i]
                                        && !primaryKey[i].isEmpty()) {
                                    sqlStringBuffer.append(primaryKey[i] + ","); //$NON-NLS-1$
                                }
                            }
                            /* Remove the last comma */
                            sqlStringBuffer.deleteCharAt(sqlStringBuffer
                                    .length() - 1);
                            /* add the closing parenthesis for primary key */
                            sqlStringBuffer.append(")"); //$NON-NLS-1$
                        }
                    } else {
                        /* Remove the last comma */
                        sqlStringBuffer
                                .deleteCharAt(sqlStringBuffer.length() - 1);
                    }

                    sqlStringBuffer.append(")"); //$NON-NLS-1$

                    Statement s = connection.createStatement();
                    s.execute(sqlStringBuffer.toString());

                    // SCF-173: Create indexes, if defined
                    for (Column col : columns) {
                        if (col.isIndexed()) {
                            String sql =
                                    String.format("CREATE INDEX %1$s_%2$s ON %1$s(%2$s)", //$NON-NLS-1$
                                            tableName,
                                            col.getName());
                            s.execute(sql);
                        }
                    }

                }
            }
        } catch (SQLException ex) {
            throw new ResourceDbException(ex);
        } finally {
            accessLock.unlock();
        }
        return null;
    }

    /**
     * Initialize the version table. If this does not exist then it will be
     * created.
     * 
     * @return true if the version table exists or was created successfully.
     * @throws ResourceDbException
     * @since 3.5.5
     */
    private boolean initializeVersionTable() throws ResourceDbException {
        accessLock.lock();
        try (Connection connection = getConnection()) {
            if (connection != null
                    && !doesTableExist(connection,
                            IndexerServiceImpl.VERSION_TABLE_NAME)) {
                String sqlString =
                        "CREATE TABLE " //$NON-NLS-1$
                                + IndexerServiceImpl.VERSION_TABLE_NAME
                                + "(" //$NON-NLS-1$
                                + IndexerServiceImpl.ATTRIBUTE_VERSION_TABLE_NAME
                                + " varchar(255), " //$NON-NLS-1$
                                + IndexerServiceImpl.ATTRIBUTE_VERSION
                                + " varchar(255), PRIMARY KEY (" //$NON-NLS-1$
                                + IndexerServiceImpl.ATTRIBUTE_VERSION_TABLE_NAME
                                + "))"; //$NON-NLS-1$

                Statement statement = connection.createStatement();
                statement.execute(sqlString);
            }
        } catch (SQLException ex) {
            throw new ResourceDbException(ex);
        } finally {
            accessLock.unlock();
        }
        return true;
    }

    /**
     * @param indexerId
     * @return
     * @throws SQLException
     */
    private String getVersionFromDb(String indexerId, String bundleVersion)
            throws SQLException {
        String version = ""; //$NON-NLS-1$
        if (indexerId != null && doesVersionTableExist) {
            accessLock.lock();
            try (Connection connection = getConnection()) {

                if (connection != null) {
                    final String tableName = getTableName(indexerId);
                    Statement statement = connection.createStatement();

                    String sqlString =
                            String.format("SELECT * FROM %1$s WHERE %2$s = '%3$s'", //$NON-NLS-1$
                                    IndexerServiceImpl.VERSION_TABLE_NAME,
                                    IndexerServiceImpl.ATTRIBUTE_VERSION_TABLE_NAME,
                                    tableName);

                    ResultSet rs = statement.executeQuery(sqlString);
                    boolean isNewEntry = false;
                    if (rs.next()) {
                        version =
                                rs.getString(IndexerServiceImpl.ATTRIBUTE_VERSION);
                    } else {
                        // Entry not found in the table
                        isNewEntry = true;
                    }

                    sqlString = null;
                    if (isNewEntry) {
                        // Insert the version number into the table
                        sqlString =
                                String.format("INSERT INTO %1$s (%2$s,%3$s) VALUES('%4$s','%5$s')", //$NON-NLS-1$
                                        IndexerServiceImpl.VERSION_TABLE_NAME,
                                        IndexerServiceImpl.ATTRIBUTE_VERSION_TABLE_NAME,
                                        IndexerServiceImpl.ATTRIBUTE_VERSION,
                                        tableName,
                                        bundleVersion);
                    } else {
                        // Update the version number in the table if required
                        if (!version.equals(bundleVersion)) {
                            sqlString =
                                    String.format("UPDATE %1$s SET %2$s = '%3$s' WHERE %4$s = '%5$s'", //$NON-NLS-1$
                                            IndexerServiceImpl.VERSION_TABLE_NAME,
                                            IndexerServiceImpl.ATTRIBUTE_VERSION,
                                            bundleVersion,
                                            IndexerServiceImpl.ATTRIBUTE_VERSION_TABLE_NAME,
                                            tableName);
                        }
                    }

                    if (sqlString != null) {
                        statement.executeUpdate(sqlString);
                    }
                }
            } finally {
                accessLock.unlock();
            }
        }
        return version;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.resources.internal.db.IResourceDb#open()
     */
    @Override
    public void open() throws ResourceDbException {
    }

    /**
     * @param indexerId
     * @param items
     * @throws ResourceDbException
     */
    @Override
    public void update(final String indexerId,
            final Collection<IndexerItem> items, final WorkingCopy wc)
            throws ResourceDbException {
        UninterruptibleJob<Void> job = new UninterruptibleJob<Void>() {

            @Override
            protected Void run() throws ResourceDbException {
                return doUpdate(indexerId, items, wc);
            }
        };

        job.start();
    }

    private Void doUpdate(String indexerId, Collection<IndexerItem> items,
            WorkingCopy wc) throws ResourceDbException {

        if (!isValid(indexerId)) {
            return null;
        }

        /*
         * SID XPD-29: MUST clean old entries out BEFORE quitting because there
         * are no items to index (else the last remaining one will never get
         * removed!).
         */
        IResource resource = wc.getEclipseResources().get(0);
        final String projectName = resource.getProject().getName();
        final String path = resource.getFullPath().toPortableString();

        accessLock.lock();
        try (Connection connection = getConnection()) {
            if (connection != null) {
                try {
                    cleanPathInternal(indexerId, path, connection);
                    connection.setAutoCommit(false);

                    if (items == null || items.isEmpty()) {
                        return null;
                    }

                    final Column[] columns = tableRegistry.get(indexerId);

                    StringBuffer sqlString = new StringBuffer();
                    sqlString.append("INSERT INTO "); //$NON-NLS-1$
                    sqlString.append(getTableName(indexerId));
                    sqlString.append("("); //$NON-NLS-1$

                    for (Column column : columns) {
                        sqlString.append(column.getName() + ","); //$NON-NLS-1$
                    }
                    sqlString.setLength(sqlString.length() - 1);
                    sqlString.append(")"); //$NON-NLS-1$

                    sqlString.append(" VALUES("); //$NON-NLS-1$
                    for (int i = 0; i < columns.length; i++) {
                        sqlString.append("?,"); //$NON-NLS-1$
                    }
                    sqlString.setLength(sqlString.length() - 1);
                    sqlString.append(")"); //$NON-NLS-1$

                    PreparedStatement s =
                            connection.prepareStatement(sqlString.toString());

                    for (IndexerItem item : items) {
                        s.setString(COLUMN_NAME + 1, item.getName());
                        s.setString(COLUMN_TYPE + 1, item.getType());
                        s.setString(COLUMN_URI + 1, item.getURI());
                        s.setString(COLUMN_PATH + 1, path);
                        s.setString(COLUMN_PROJECT + 1, projectName);

                        for (int colNum = 5; colNum < columns.length; colNum++) {
                            String columnName = columns[colNum].getName();
                            String value = item.get(columnName);
                            s.setString(colNum + 1, value);
                        }
                        s.addBatch();
                    }
                    s.executeBatch();
                    connection.commit();
                } catch (SQLException e) {
                    connection.rollback();
                    throw e;
                }
            }
        } catch (BatchUpdateException e) {
            logIndexerURIs(items, e);
        } catch (SQLException e) {
            if ("23505".equals(e.getSQLState())) { //$NON-NLS-1$
                logIndexerURIs(items, e);
            } else {
                if (e.getSQLState().equals(SQL_STATE_COLUMN_NOT_DEFINED)) {
                    // dropResourceTable();
                    // update(newResource);
                    XpdResourcesPlugin.getDefault().getLogger().error(e);
                }
            }
            throw new ResourceDbException(e);
        } finally {
            accessLock.unlock();
        }
        printAllEntries();

        return null;
    }

    /**
     * @param items
     * @param e
     */
    private void logIndexerURIs(Collection<IndexerItem> items, SQLException e) {
        // Duplicate URI (SQL Integrity Constraint Violation) -
        // print the duplicates in the error message in the log

        Map<String, Integer> uriCount = new HashMap<String, Integer>();

        for (IndexerItem ind : items) {
            String uri = ind.getURI();
            Integer count = uriCount.get(uri);
            if (count == null) {
                count = new Integer(0);
            }
            uriCount.put(uri, ++count);
        }

        List<IStatus> result = new ArrayList<IStatus>();
        for (Entry<String, Integer> entry : uriCount.entrySet()) {
            if (entry.getValue() > 1) {
                result.add(new Status(IStatus.ERROR,
                        XpdResourcesPlugin.ID_PLUGIN, String
                                .format("Duplicate URI: %s", //$NON-NLS-1$
                                        entry.getKey())));
            }
            if (entry.getValue() > 0) {
                result.add(new Status(IStatus.ERROR,
                        XpdResourcesPlugin.ID_PLUGIN, entry.getKey()));
            }
        }

        LOG.log(new MultiStatus(XpdResourcesPlugin.ID_PLUGIN, 0, result
                .toArray(new IStatus[result.size()]), String.format("[%s] %s", //$NON-NLS-1$
                Thread.currentThread().getId(),
                e.getLocalizedMessage()), e));
    }

    /**
     * @param indexerId
     * @param items
     * @throws ResourceDbException
     */
    public void remove(final String indexerId,
            final Collection<IndexerItem> items) throws ResourceDbException {
        UninterruptibleJob<Void> job = new UninterruptibleJob<Void>() {

            @Override
            protected Void run() throws ResourceDbException {
                return doRemove(indexerId, items);
            }
        };

        job.start();
    }

    private Void doRemove(String indexerId, Collection<IndexerItem> items)
            throws ResourceDbException {
        if (!isValid(indexerId)) {
            return null;
        }
        if (items != null && items.size() > 0) {
            for (IndexerItem item : items) {

                StringBuffer sqlString = new StringBuffer();
                String primaryKeyName = IndexerServiceImpl.ATTRIBUTE_URI;
                sqlString.append("DELETE FROM " + getTableName(indexerId) //$NON-NLS-1$
                        + " WHERE " + primaryKeyName + " = ?"); //$NON-NLS-1$ //$NON-NLS-2$

                accessLock.lock();
                try (Connection connection = getConnection()) {

                    if (connection != null) {
                        PreparedStatement s =
                                connection.prepareStatement(sqlString
                                        .toString());
                        String value = item.get(primaryKeyName);
                        s.setString(1, value);
                        s.execute();
                    }
                } catch (SQLException e) {
                    if (e.getSQLState().equals(SQL_STATE_COLUMN_NOT_DEFINED)) {
                        // dropResourceTable();
                        // update(newResource);
                    }
                    XpdResourcesPlugin.getDefault().getLogger().error(e);
                    throw new ResourceDbException(e);
                } finally {
                    accessLock.unlock();
                }
            }
        }
        return null;
    }

    /**
     * @param indexerId
     * @return
     */
    public String getTableName(String indexerId) {
        return TABLE_PARTS_SEPARATOR.matcher(indexerId).replaceAll("_"); //$NON-NLS-1$
    }

    /**
     * Indicates if the indexer with the given id is valid for it the methods of
     * this class can be called.
     * 
     * @param indexerId
     * @return
     */
    boolean isValid(String indexerId) {
        Boolean val = validationTable.get(indexerId);
        return indexerId.equals("*") || (val != null && val); //$NON-NLS-1$
    }

}
