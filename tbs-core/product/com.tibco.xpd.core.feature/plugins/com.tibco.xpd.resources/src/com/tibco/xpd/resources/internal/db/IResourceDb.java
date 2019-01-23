/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.resources.internal.db;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.indexer.IndexerItem;

/**
 * The interface to the resource database. It should always be
 * used by the application instead of the implementation.
 *  
 * @author ramin
 *
 */
public interface IResourceDb {

	/**
	 * Name of the database where the resource infos are stored.
	 */
	public static final String RESOURCE_DB_NAME = "TibcoResources"; //$NON-NLS-1$

	/**
	 * Name of the database where the resource infos are stored.
	 */
	public static final String RESOURCE_DB_RESOURCE_TABLE_NAME = "Resources"; //$NON-NLS-1$

	/**
	 * Update resource information in database.
	 * 
	 * @param path
	 *            old resource path if path was changed, or just current path if
	 *            it was not
	 * @param newResource
	 *            new information for resource
	 * @throws SQLException
	 *             if SQL error ocurred
	 */
	public void update(String indexerId, Collection<IndexerItem> items, WorkingCopy wc) throws ResourceDbException;

	   /**
     * Remove resource information in database.
     * 
     * @param path
     *            old resource path if path was changed, or just current path if
     *            it was not
     * @param newResource
     *            new information for resource
     * @throws SQLException
     *             if SQL error ocurred
     */
    public void cleanPath(String indexerId, String path) throws ResourceDbException;


	/**
	 * Query all resource from database which are specified by type and returns
	 * it.
	 * 
	 * @param searchString
	 *            string which should be included in resource name
	 * @return List of ResourceEntity
	 * @throws SQLException
	 *             if SQL error ocurred
	 */
	public Collection<IndexerItem> query(String indexerId, Map<String, String> criteria) throws ResourceDbException;

    /**
     * opens the connection to the resource database
     */
    public void open() throws ResourceDbException;
    
    /**
     * closes the conncetion to the resource database
     */
    public void close() throws ResourceDbException;
    
	/**
	 * Clears all entries.
	 * 
	 * @throws ResourceDbException
	 */
	public void cleanProject(String projectName) throws ResourceDbException;

	/**
	 * @param indexerId
     * @param columns
	 * @throws ResourceDbException
	 */
	public void initTable(String indexerId, String version, Column[] columns) throws ResourceDbException;

	   /**
     * Drops one table.
     *  
     * @throws ResourceDbException
     */
    public void drop(String tableName) throws ResourceDbException;

	/**
	 * This method is intended for debugging purposes.
	 * 
	 * @return
	 * @throws ResourceDbException
	 */
	public Collection<IndexerItem> printAllEntries() throws ResourceDbException;
	
}
