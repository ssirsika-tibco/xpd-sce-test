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
package com.tibco.xpd.simulation.realdata.ui.wizard;


public class TxtImportPropertiesPage extends ImportPropertiesPage {

    private final RealDataModelImporter importer;

    public TxtImportPropertiesPage(RealDataModelImporter importer, String pageId) {
        super(pageId);
        this.importer = importer;
    }

}
