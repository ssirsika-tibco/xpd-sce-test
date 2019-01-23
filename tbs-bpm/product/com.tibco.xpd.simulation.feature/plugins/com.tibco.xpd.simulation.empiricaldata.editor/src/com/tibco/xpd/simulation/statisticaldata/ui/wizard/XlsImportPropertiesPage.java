/* 
 ** 
 **  MODULE:             $RCSfile: XslImportPropertiesPage.java $ 
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
package com.tibco.xpd.simulation.statisticaldata.ui.wizard;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.tibco.xpd.simulation.EmpircalDataEditorPlugin;
import com.tibco.xpd.simulation.empiricaldata.Messages;

public class XlsImportPropertiesPage extends ImportPropertiesPage {

    private final List extensions = new ArrayList();

    private final ActivityStatisticalDataModelImporter importer;

    private String modelLocation;

    public XlsImportPropertiesPage(
            ActivityStatisticalDataModelImporter importer, String pageId) {
        super(pageId);
        this.importer = importer;
        extensions.add("xls"); //$NON-NLS-1$
    }

    protected IStatus doReloadDataTable(IProgressMonitor progressMonitor) throws Exception {
        File f = new File(modelLocation);
        if (f.exists()) {
            DataTable dataTable = new DataTable();
            IStatus errorStatus = importer.importFromExcelFile(dataTable, f, progressMonitor);
            importer.setDataTable(dataTable);
            return errorStatus;
        } else {
            return new Status(IStatus.ERROR, EmpircalDataEditorPlugin.ID, 0,
                    Messages.XlsImportPropertiesPage_FileNotExist, null);
        }
    }

    

    protected boolean isModelLoaded() {
        return importer.getDataTable() != null;
    }

    protected void setModelLocation(String location) {
        modelLocation = location;
    }

    protected String getModelLocation() {
        return modelLocation;
    }

    protected List getImportTypeFileExtensions() {
        return extensions;
    }

}
