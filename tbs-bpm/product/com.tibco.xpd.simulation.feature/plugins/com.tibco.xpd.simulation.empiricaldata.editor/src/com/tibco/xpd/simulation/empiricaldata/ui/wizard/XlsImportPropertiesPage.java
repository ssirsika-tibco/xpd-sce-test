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
package com.tibco.xpd.simulation.empiricaldata.ui.wizard;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
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

import com.tibco.xpd.simulation.empiricaldata.Messages;

public class XlsImportPropertiesPage extends ImportPropertiesPage {

    private final List extensions = new ArrayList();

    private final ModelImporter importer;

    private String modelLocation;

    public XlsImportPropertiesPage(ModelImporter importer, String pageId) {
        super(pageId);
        this.importer = importer;
        extensions.add("xls"); //$NON-NLS-1$
    }

    protected void doReloadDataTable(IProgressMonitor progressMonitor)
            throws Exception {
        File f = new File(modelLocation);
        if (f.exists()) {
            DataTable dataTable = new DataTable();
            importFromExcelFile(dataTable, f, progressMonitor);
            importer.setDataTable(dataTable);
        }
    }

    private void importFromExcelFile(DataTable dataTable, File f,
            IProgressMonitor progressMonitor) throws Exception {
        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(f));
        HSSFWorkbook wb = new HSSFWorkbook(fs);
        int numOfSheets = wb.getNumberOfSheets();
        HSSFSheet sheet = wb.getSheetAt(0);
        int firstRowNumber = sheet.getFirstRowNum();
        int lastRowNumber = sheet.getLastRowNum();
        int noOfRows = sheet.getPhysicalNumberOfRows();
        progressMonitor.beginTask(Messages.XlsImportPropertiesPage_ReadingFile, noOfRows);
        int worked = 0;
        if (firstRowNumber < lastRowNumber) {// process first row-col. header
            HSSFRow row = sheet.getRow(firstRowNumber);
            HSSFRow row2 = sheet.getRow(firstRowNumber+1);
            if (row != null) {
                progressMonitor.worked(1);
                // process row
                short firstColNo = row.getFirstCellNum();
                short lastColNo = row.getLastCellNum();
                String columnName = null;
                Class columnType = Object.class;
                int columnNo = 0;
                for (int j = firstColNo; j < lastColNo; j++) {
                    HSSFCell cell = row.getCell((short) j);
                    columnName = getCellValue(cell).toString().trim();
                    if ( row2 != null) {
                        HSSFCell cell2 = row2.getCell((short) j);
                        columnType = getCellType(cell2);
                    } else {
                        columnType = Object.class;
                    }
                    dataTable.addColumn(columnNo, columnName, columnType);
                    columnNo++;
                }
            }
        }
        int rowNo = 0;
        for (int i = firstRowNumber + 1; i <= lastRowNumber; i++) {
            HSSFRow row = sheet.getRow(i);
            if (row == null)
                continue;
            // process row
            Object[] tableDataRow = new Object[dataTable.getNumberOfColumns()];
            short firstColNo = row.getFirstCellNum();
            short lastColNo = row.getLastCellNum();
            short tableLength = (short)tableDataRow.length;
            if ((lastColNo - firstColNo) > tableLength) {
                //lastColNo = firstColNo + tableLength;
            }
            int columnNo = 0;
            for (int j = firstColNo; j < lastColNo; j++) {
                HSSFCell cell = row.getCell((short) j);
                tableDataRow[columnNo] = getCellValue(cell);
                columnNo++;
            }
            dataTable.addRow(tableDataRow);
            worked++;
            progressMonitor.worked(worked);
            rowNo++;
        }
    }


    protected void setModelLocation(String location) {
        modelLocation = location;
    }

    protected String getModelLocation() {
        return modelLocation;
    }

    protected void setModelLocationText(String location) {
        super.setModelLocationText(location);
    }

    protected List getImportTypeFileExtensions() {
        return extensions;
    }

    private Class getCellType(HSSFCell cell) {
        if (cell != null) {
            int cellType = cell.getCellType();
            switch (cellType) {
            case HSSFCell.CELL_TYPE_STRING:
                return String.class;
            case HSSFCell.CELL_TYPE_NUMERIC:
                short formatId = cell.getCellStyle().getDataFormat();
                String format = HSSFDataFormat.getBuiltinFormat(formatId);
                if (isDateTimeFormat(format)) {
                   return Date.class;
                } else {
                    return Double.class;
                }
            case HSSFCell.CELL_TYPE_BLANK:
                return Object.class;
            case HSSFCell.CELL_TYPE_BOOLEAN:
                return Boolean.class;
            case HSSFCell.CELL_TYPE_ERROR:
                return Object.class;
            case HSSFCell.CELL_TYPE_FORMULA:
                return Object.class;
            default:
                return Object.class;
            }
        } else {
            return Object.class;
        }
    }
    
    private Object getCellValue(HSSFCell cell) {
        if (cell != null) {
            int cellType = cell.getCellType();
            switch (cellType) {
            case HSSFCell.CELL_TYPE_STRING:
                return cell.getStringCellValue();
            case HSSFCell.CELL_TYPE_NUMERIC:
                short formatId = cell.getCellStyle().getDataFormat();
                String format = HSSFDataFormat.getBuiltinFormat(formatId);
                if (isDateTimeFormat(format)) {
                    return cell.getDateCellValue();
                } else {
                    return new Double(cell.getNumericCellValue());
                }
            case HSSFCell.CELL_TYPE_BLANK:
                return ""; //$NON-NLS-1$
            case HSSFCell.CELL_TYPE_BOOLEAN:
                return Boolean.valueOf(cell.getBooleanCellValue());
            case HSSFCell.CELL_TYPE_ERROR:
                return "!error!"; //$NON-NLS-1$
            case HSSFCell.CELL_TYPE_FORMULA:
                return "Formula: "+ cell.getCellFormula(); //$NON-NLS-1$
            default:
                return ""; //$NON-NLS-1$
            }
        } else {
            return ""; //$NON-NLS-1$
        }
    }

    private boolean isDateTimeFormat(String format) {
        boolean containsDataChars = (format.indexOf('y') != -1)
                && (format.indexOf('m') != -1) && (format.indexOf('d') != -1)
                && (format.indexOf('h') != -1);
        return containsDataChars;
    }

}
