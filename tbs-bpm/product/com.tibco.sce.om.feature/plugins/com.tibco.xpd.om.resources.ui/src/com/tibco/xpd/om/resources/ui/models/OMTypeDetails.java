package com.tibco.xpd.om.resources.ui.models;


/**
 * Class to hold all the information about an om type - used in the om types picker
 * 
 * @author glewis
 * 
 */
public class OMTypeDetails {

	private Class typeCls = null;
	
	private boolean isSelected = true;	
	
    /**
	 * @param activity
	 * @param taskUser
	 * @param formUrl
	 * @param isSelected
	 * @param isUsed
	 */
	public OMTypeDetails(boolean isSelected, Class typeCls){	    
	    this.isSelected = isSelected;	    
	    this.typeCls = typeCls;
	}

    /**
     * @return
     */
    public Class getTypeCls() {
        return typeCls;
    }

    /**
     * @param typeCls
     */
    public void setTypeCls(Class typeCls) {
        this.typeCls = typeCls;
    }

    /**
     * @return
     */
    public boolean isSelected() {
        return isSelected;
    }

    /**
     * @param isSelected
     */
    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }    
}
