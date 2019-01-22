/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.core.test.validations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IMarker;

/**
 * 
 * @author aallway
 * @since 3.2
 */
public class ValidationsTestProblemMarkerInfo implements Comparable<ValidationsTestProblemMarkerInfo>{

    // The problem's issue id.
    private final String problemId;

    // URI of file problem issued for
    private final String resourceURI;

    // URI of location of problem.
    private final String locationURI;

    // Inc' destination name
    private final String problemText;

    // Or null if none.
    private final String quickFixLabel;

    private IMarker sourceMarker;

    /**
     * @param resourceURI
     * @param problemId
     * @param locationURI
     * @param problemText
     * @param quickFixLabel
     */
    public ValidationsTestProblemMarkerInfo(String resourceURI,
            String problemId, String locationURI, String problemText,
            String quickFixLabel) {
        super();
        this.resourceURI = resourceURI != null ? resourceURI : ""; //$NON-NLS-1$
        this.problemId = problemId != null ? problemId : ""; //$NON-NLS-1$
        this.locationURI = locationURI != null ? locationURI : ""; //$NON-NLS-1$
        this.problemText = problemText != null ? problemText : ""; //$NON-NLS-1$
        this.quickFixLabel = quickFixLabel != null ? quickFixLabel : ""; //$NON-NLS-1$
    }

    public ValidationsTestProblemMarkerInfo(String resourceURI,
            String problemId, String locationURI, String problemText,
            String quickFixClassName, IMarker sourceMarker) {
        this(resourceURI, problemId, locationURI, problemText,
                quickFixClassName);

        this.sourceMarker = sourceMarker;
    }

    /**
     * @return the problemId
     */
    public String getProblemId() {
        return problemId;
    }

    /**
     * @return the resourceURI
     */
    public String getResourceURI() {
        return resourceURI;
    }

    /**
     * @return the problemText
     */
    public String getProblemText() {
        return problemText;
    }

    /**
     * @return the quickFixLabel
     */
    public String getQuickFixLabel() {
        return quickFixLabel;
    }

    /**
     * @return the locationURI
     */
    public String getLocationURI() {
        return locationURI;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (obj instanceof ValidationsTestProblemMarkerInfo) {
            ValidationsTestProblemMarkerInfo other =
                    (ValidationsTestProblemMarkerInfo) obj;

            if (problemId.equals(other.problemId)) {
                if (resourceURI.equals(other.resourceURI)) {
                    // May need to ignore the validation destination id (as
                    // this
                    // may change over time).
                    String pt1 = problemText;
                    int idx = pt1.indexOf(":"); //$NON-NLS-1$
                    if (idx >= 0) {
                        pt1 = pt1.substring(idx + 1);
                    }
                    pt1.trim();

                    String pt2 = other.problemText;
                    idx = pt2.indexOf(":"); //$NON-NLS-1$
                    if (idx >= 0) {
                        pt2 = pt2.substring(idx + 1);
                    }
                    pt2.trim();

                    if (pt1.equals(pt2)) {
                        if (locationURI.equals(other.locationURI)) {
                            return true;
                        } else {
                            System.out
                                    .println(this.getClass().getSimpleName()
                                            + ": Test Marker is equivalent but seems to have changed location: " //$NON-NLS-1$
                                            + this.toString() + "\n    " //$NON-NLS-1$
                                            + this.locationURI + "\n  vs.\n" //$NON-NLS-1$
                                            + other.getLocationURI());
                        }
                    }
                }
            }

        }
        return false;
    }

    /**
     * @return the sourceMarker
     */
    public IMarker getSourceMarker() {
        return sourceMarker;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return getProblemText();
    }
    
	public String toComparableString() {
		return this.resourceURI + "::" + this.locationURI + "::" //$NON-NLS-1$ //$NON-NLS-2$
				+ this.problemId + "::" + this.problemText + "::" //$NON-NLS-1$ //$NON-NLS-2$
				+ this.quickFixLabel;
	}

	@Override
	public int compareTo(ValidationsTestProblemMarkerInfo o) {
		String thisComparableString = toComparableString();
		String oComparableString = o.toComparableString();
		if (thisComparableString.equals(oComparableString)) {
			return 0;
		}
		return thisComparableString.compareToIgnoreCase(oComparableString);
	}
}
