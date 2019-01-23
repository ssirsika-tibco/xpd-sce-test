/*
 * Copyright (c) TIBCO Software Inc. 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.properties.table;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.activities.ActivityManagerEvent;
import org.eclipse.ui.activities.IActivityManagerListener;
import org.eclipse.ui.activities.WorkbenchActivityHelper;

/**
 * Manages single table column.
 * This manager is able to listen to capabilities and hide/show column depending on changes.
 * 
 * @author mmaciuki
 */
public class ColumnManager implements IPluginContribution, IActivityManagerListener {
	
	private final TableColumn colum;
	private int columnWidthMemo;
	private final String localId;
	private final String pluginId;
	private final List<RefreshListener> refreshListeners;
	private boolean isVisible;

	public ColumnManager(TableColumn colum, String pluginId, String localId){
		if(colum==null){
			throw new NullPointerException("Column cannot be null"); //$NON-NLS-1$
		}
		if(pluginId==null){
			throw new NullPointerException("pluginId cannot be null"); //$NON-NLS-1$
		}
		if(localId==null){
			throw new NullPointerException("localId cannot be null"); //$NON-NLS-1$
		}
		this.pluginId = pluginId;
		this.localId = localId;
		this.colum = colum;
		this.columnWidthMemo=colum.getWidth();
		this.isVisible=(this.columnWidthMemo!=0);
		this.refreshListeners=new ArrayList<RefreshListener>();
	}
	
	public synchronized void add(RefreshListener o) {
		this.refreshListeners.add(o);
	}

	public synchronized void remove(Object o) {
		this.refreshListeners.remove(o);
	}

	private synchronized void notifyListeners(){
		for (RefreshListener listener : this.refreshListeners) {
			listener.doRefresh();
		}
	}
	
	public void syncVisiblitity() {
    	boolean isColumnVisible = WorkbenchActivityHelper.filterItem(this);
    	setVisible(isColumnVisible);
	}

	private synchronized void setVisible(boolean visibility){
		if(this.isVisible==visibility){
			return;
		}
		
        if(visibility){
        	// show column
        	this.colum.setWidth(this.columnWidthMemo);
        } else {
        	// hide column
        	this.columnWidthMemo=colum.getWidth();
        	this.colum.setWidth(0);
        }
        this.isVisible=!this.isVisible;
        notifyListeners();
	}

	public String getLocalId() {
		return this.localId;
	}

	public String getPluginId() {
		return this.pluginId;
	}

	public void activityManagerChanged(
			ActivityManagerEvent activityManagerEvent) {
        if (activityManagerEvent.haveEnabledActivityIdsChanged()) {
        	syncVisiblitity();
        }
	}
	
	public interface RefreshListener {
		void doRefresh();
	}
}