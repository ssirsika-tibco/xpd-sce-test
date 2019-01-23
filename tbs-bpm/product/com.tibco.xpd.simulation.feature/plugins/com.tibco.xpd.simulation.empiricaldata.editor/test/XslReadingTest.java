import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;

import junit.framework.TestCase;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
/* 
 ** 
 **  MODULE:             $RCSfile: XslReadingTest.java $ 
 **                      $Revision: 1.0 $ 
 **                      $Date: 2006-03-16 $ 
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

public class XslReadingTest extends TestCase {

    private static final String TEST_FILE = "test-files/WorkbookBook1.xls"; //$NON-NLS-1$
    
    private SimpleDateFormat displayDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");  
    
    protected void setUp() throws Exception {
        super.setUp();
        
    }
    
    protected void tearDown() throws Exception {
        super.tearDown();
        
    }
    
    public void testExistTestFile() {
        File f = new File(TEST_FILE); //$NON-NLS-1$
        assertTrue("Cannot find test file", f.exists());
    }
    
    public void testXslReading() throws FileNotFoundException, IOException {
        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(TEST_FILE));
        HSSFWorkbook wb = new HSSFWorkbook(fs);
        int numOfSheets = wb.getNumberOfSheets();
        System.out.println("Sheets:");
        for (int i = 0; i < numOfSheets; i++) {
            System.out.println(wb.getSheetName(i));
        }
        HSSFSheet sheet = wb.getSheetAt(0);
        int noOfRows = sheet.getPhysicalNumberOfRows();
        for (int i = 0; i < noOfRows; i++) {
            HSSFRow row = sheet.getRow(i);
            if (row == null) continue;
            System.out.print("Row " + i + ":\t");
            //process row
            short firstColNo = row.getFirstCellNum();
            short lastColNo = row.getLastCellNum();
            for (int j = firstColNo; j < lastColNo; j++) {
                HSSFCell cell = row.getCell((short)j);
                printCellValue(cell);
            }
            System.out.println();
        }
    }

    private void printCellValue(HSSFCell cell) {
        if (cell != null) {
            int cellType = cell.getCellType();
            switch (cellType) {
            case HSSFCell.CELL_TYPE_STRING:
                System.out.print(cell.getStringCellValue());
                System.out.print("\t");
                break;
            case HSSFCell.CELL_TYPE_NUMERIC:
                short formatId = cell.getCellStyle().getDataFormat();
                String format = HSSFDataFormat.getBuiltinFormat(formatId);
                if(isDateTimeFormat(format)) {
                    System.out.print(displayDateFormat.format(cell.getDateCellValue()));                    
                } else {
                    System.out.print(cell.getNumericCellValue());
                }
                System.out.print("\t");
                break;
            case HSSFCell.CELL_TYPE_BLANK:
                System.out.print("-blank-");
                System.out.print("\t");
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN:
                System.out.print(cell.getBooleanCellValue());
                System.out.print("\t");
                break;
            case HSSFCell.CELL_TYPE_ERROR:
                System.out.print("-error-");
                System.out.print("\t");
                break;
            case HSSFCell.CELL_TYPE_FORMULA:
                System.out.print(cell.getCellFormula());
                System.out.print("\t");
                break;
            default:
                System.out.print("-unknown type-");
                System.out.print("\t");
                break;
            }
        } else {
            System.out.print("=empty=");
        }
    }

    private boolean isDateTimeFormat(String format) {
        boolean containsDataChars = 
            (format.indexOf('y') != -1) &&
            (format.indexOf('m') != -1) &&
            (format.indexOf('d') != -1) &&
            (format.indexOf('h') != -1);
        return containsDataChars;
    }

}
