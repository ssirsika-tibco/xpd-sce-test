package com.tibco.n2.pfe.services.pageflowType;

import java.util.ArrayList;
import java.util.List;

public class DetailedException {

	protected List<ErrorLine> error;

	/**
	 * Gets the value of the error property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the error property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getError().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link ErrorLine }
	 * 
	 * 
	 */
	public List<ErrorLine> getError() {
		if (error == null) {
			error = new ArrayList<ErrorLine>();
		}
		return this.error;
	}

}
