/**
 *
 *  Copyright 2005 The Apache Software Foundation or its licensors, as applicable.
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

import java.util.List;

import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.util.ExtendedMetaData;

public interface SDOExtendedMetaData extends ExtendedMetaData {

  SDOExtendedMetaData INSTANCE = new SDOExtendedMetaDataImpl();
  
  List getAliasNames(EModelElement eModelElement);
  
  void setAliasNames(EModelElement eModelElement, List aliasNames);
  
  void setAliasNames(EModelElement eModelElement, String aliasNames);

}
