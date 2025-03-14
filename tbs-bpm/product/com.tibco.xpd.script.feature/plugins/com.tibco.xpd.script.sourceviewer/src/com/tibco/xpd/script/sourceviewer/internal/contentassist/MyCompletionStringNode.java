package com.tibco.xpd.script.sourceviewer.internal.contentassist;

import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.script.model.client.JsAttribute;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.model.client.JsMethod;
import com.tibco.xpd.script.model.client.JsReference;

public class MyCompletionStringNode {

    private final String completionstring;

    private final JsClass jsClass;

    private final JsAttribute jsAttribute;

    private final JsReference jsReference;

    private final JsMethod jsMethod;

    private final String comment;

    private final Image icon;

    private final boolean isMultiple;

    public MyCompletionStringNode(JsClass jsClass) {
        this.jsClass = jsClass;
        this.jsAttribute = null;
        this.jsReference = null;
        this.jsMethod = null;
        this.completionstring = jsClass.getContentAssistString();
        this.icon = jsClass.getIcon();
        this.comment = jsClass.getComment();
        this.isMultiple = false;
    }

    /**
     * XPD-5535: Saket: Groups different types of objects in the content assist
     * list by returning an integer according to which the objects are sorted.
     * 
     * @return integer value to sort objects
     */
    public int getSortGroup() {

		// Nikita ACE-7334 For JavaScript editor content assist, sort top level content list appropriately
		/* Sid ACE-8307 sort by user data, then BPM related classes, then finally standard static JS classes. */

		if ("bpm".equals(completionstring) || "bpmScripts".equals(completionstring)
				|| "factory".equals(completionstring) || "pkg".equals(completionstring))
		{
			// Then BPM related "bpm" "bpmScripts" "factory", "pkg"
			return 2;
		}
		else if (jsAttribute != null || jsReference != null || jsMethod != null || jsClass != null)
		{
			// Static JS Classes, sortGroup=2 indicates they will be shown after sortGroup 1
			return 3;
		}
		else
		{

			// Show user-defined wrapper objects "data", before everything else
			// i.e. it has the sortGroup=1 so that these classes are shown on top
			return 1;
		}

    }

    public MyCompletionStringNode(JsAttribute jsAttribute) {
        this.jsAttribute = jsAttribute;
        this.jsReference = null;
        this.jsClass = null;
        this.jsMethod = null;
        this.completionstring = jsAttribute.getContentAssistString();
        this.icon = jsAttribute.getIcon();
        this.comment = jsAttribute.getComment();
        this.isMultiple = jsAttribute.isMultiple();
    }

    public MyCompletionStringNode(JsAttribute jsAttribute, boolean includeAt) {
        this.jsAttribute = jsAttribute;
        this.jsReference = null;
        this.jsClass = null;
        this.jsMethod = null;
        if (includeAt) {
            this.completionstring = "@" + jsAttribute.getContentAssistString();//$NON-NLS-N$
        } else {
            this.completionstring = jsAttribute.getContentAssistString();
        }
        this.icon = jsAttribute.getIcon();
        this.comment = jsAttribute.getComment();
        this.isMultiple = jsAttribute.isMultiple();
    }

    public MyCompletionStringNode(JsReference jsReference) {
        this.jsAttribute = null;
        this.jsReference = jsReference;
        this.jsClass = null;
        this.jsMethod = null;
        this.completionstring = jsReference.getContentAssistString();
        this.icon = jsReference.getIcon();
        this.comment = jsReference.getComment();
        this.isMultiple = jsReference.isMultiple();
    }

    public MyCompletionStringNode(JsMethod jsMethod) {
        this.jsClass = null;
        this.jsAttribute = null;
        this.jsReference = null;
        this.jsMethod = jsMethod;
        this.completionstring = this.jsMethod.getContentAssistString();
        this.comment = jsMethod.getComment();
        this.icon = jsMethod.getIcon();
        this.isMultiple = jsMethod.isMultiple();
    }

    public MyCompletionStringNode(String completionString) {
        this.jsClass = null;
        this.jsAttribute = null;
        this.jsReference = null;
        this.jsMethod = null;
        this.comment = null;
        this.completionstring = completionString;
        this.icon = null;
        this.isMultiple = false;
    }

    public MyCompletionStringNode(String completionString, String comment,
            Image icon, boolean isMultiple) {
        this.jsClass = null;
        this.jsAttribute = null;
        this.jsReference = null;
        this.jsMethod = null;
        this.comment = comment;
        this.completionstring = completionString;
        this.icon = icon;
        this.isMultiple = isMultiple;
    }

    public String getCompletionString() {
        return completionstring;
    }

    public JsClass getJsClass() {
        return jsClass;
    }

    public JsMethod getJsMethod() {
        return jsMethod;
    }

    public JsAttribute getJsAttribute() {
        return jsAttribute;
    }

    public JsReference getJsReference() {
        return jsReference;
    }

    public String getComment() {
        return this.comment;
    }

    public Image getImage() {
        return this.icon;
    }

    public boolean isMultiple() {
        return isMultiple;
    }
}
