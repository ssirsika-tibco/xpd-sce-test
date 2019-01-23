/*******************************************************************************
 * Copyright 2006 by TIBCO, Inc.
 * ALL RIGHTS RESERVED
 */
package com.tibco.bx.xpdl2bpel.util.internal;

import java.util.List;

import javax.wsdl.extensions.ExtensibilityElement;
import javax.xml.namespace.QName;

import org.eclipse.bpel.model.Activity;
import org.eclipse.bpel.model.extensions.BPELActivitySerializer;
import org.eclipse.bpel.model.resource.BPELWriter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.tibco.bx.bpelExtension.extensions.misc.BxBPELWriter;

/**
 * Created by IntelliJ IDEA.
 * User: goldberg
 * Date: Aug 1, 2007
 * Time: 3:52:19 PM
 */
public class EObjectActivitySerializer implements BPELActivitySerializer {

	public void marshall(QName elementType, Activity activity, Node parentNode,
			org.eclipse.bpel.model.Process process, BPELWriter bpelWriter) {
		Document document = parentNode.getOwnerDocument();
		Element element = activity.getElement();
		if (element != null) {
          Node extensionActivityElement = document.importNode(element, true);
          List xx = activity.getExtensibilityElements();
          for (Object eee: xx) {
              extensionActivityElement.appendChild(((BxBPELWriter) bpelWriter).extensibilityElement2XML((ExtensibilityElement) eee));
          }
          parentNode.appendChild(extensionActivityElement);
		}
    }

}
