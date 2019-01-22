/**
 *
 * Copyright 2005 The Apache Software Foundation
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.tibco.bx.xpdl2bpel.util.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.util.BasicExtendedMetaData;

public class SDOExtendedMetaDataImpl 
  extends BasicExtendedMetaData 
  implements SDOExtendedMetaData {

  
  
  public SDOExtendedMetaDataImpl() {
    super();
  }

  public SDOExtendedMetaDataImpl(Registry registry) {
    super(registry);
  }

  public SDOExtendedMetaDataImpl(String annotationURI, Registry registry, Map annotationMap) {
    super(annotationURI, registry, annotationMap);
  }

  public SDOExtendedMetaDataImpl(String annotationURI, Registry registry) {
    super(annotationURI, registry);
  }

  /**
   * Returns the listing of alias names as specified by the sdo:aliasNames
   * property.
   */
  public List getAliasNames(EModelElement modelElement) {
    EAnnotation eAnnotation = getAnnotation(modelElement, false);
    List list = null;
    if (eAnnotation != null) {
      String aliasNames = (String)eAnnotation.getDetails().get("aliasNames"); //$NON-NLS-1$
      if (aliasNames != null) {
        list = new ArrayList();
        StringTokenizer st = new StringTokenizer(aliasNames, " "); //$NON-NLS-1$
        while (st.hasMoreTokens()) {
          String t = st.nextToken();
          list.add(t);
        }
      }
    }
    return list;
  }

  
  public void setAliasNames(EModelElement modelElement, List aliasNames) {
    if (aliasNames == null || aliasNames.isEmpty()) {
      setAliasNames(modelElement, (String)null);
    } else {
      StringBuffer buf = new StringBuffer();
      for (int n = 0; n < aliasNames.size(); n++) {
        String name = (String) aliasNames.get(n);
        buf.append(name);
        buf.append(" "); //$NON-NLS-1$
      }
      setAliasNames(modelElement, buf.toString());
    }
  }
  
  /**
   * Adds an alias name per sdo:aliasName
   */
  public void setAliasNames(EModelElement modelElement, String aliasNames) {
    EAnnotation eAnnotation = getAnnotation(modelElement, true);
    eAnnotation.getDetails().put("aliasNames", aliasNames); //$NON-NLS-1$
  }
  
}
