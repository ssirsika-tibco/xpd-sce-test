/**
 * DO NOT EDIT: This is an automatically generated file.
 * This provides an implementation of the class addresslookup.tibco.com.address. 
 */ 



 
 
com.tibco.data.Loader.classDefiner["addresslookup.tibco.com.address"] = function() {

/**
 * Constructor.
 */
 
var theClass = function(context) {
    if (context == null) {
    	this.data = new Object();
    	this.detached = true;
		this.isParameter = false;
    } else  {
    	this.data = new Object();
		this.dataStore = context.dataStore;
    	this.detached = (context.item == null);
		this.isParameter = false;
		this.item = context.item;
		this.logger = context.logger;
		this.id = context.id;
		this.form = context.form;
		this.loader = this.form._loader;
		if (typeof this.form != 'undefined' && this.form != null && this.item != null) {
	    	this.form.registerItem(this.item, this.id);
		}
	}
};

theClass.LOADER = com.tibco.data.Loader.currentLoader;
theClass.LOADER.registerClass(theClass, "addresslookup.tibco.com.address");

com.tibco.data.Loader.extendClass(com.tibco.data.BomBase, theClass);




theClass.TYPE_ARRAY = new Object();
 

theClass.PRIMITIVE_ATTRIBUTE_NAMES = new Array(

);

theClass.COMPOSITE_ATTRIBUTE_NAMES = new Array(

);

theClass.ATTRIBUTE_NAMES = new Array(

);

theClass._hasAttribute = function(attName) {
	return this.TYPE_ARRAY[attName] != null;

};

theClass._getAttributes = function() {
	return this.ATTRIBUTE_NAMES;

};

theClass._getPrimitiveAttributes = function() {
    return this.PRIMITIVE_ATTRIBUTE_NAMES;

}

theClass._isAttributeMultivalued = function(attName) {
	return this._getTypeDef(attName).multivalued;
};

theClass._getTypeDef = function(attName) {
    var attType = this.TYPE_ARRAY[attName];

	if (attType == null)
		throw new Exception("Attribute " + attName + " not recognized for class addresslookup.tibco.com.address");
	return attType;
};

theClass.prototype.getClass  = function() {
    return this.loader.getClass("addresslookup.tibco.com.address");
};




};
com.tibco.data.Loader.classDefiner["addresslookup.tibco.com.address"]();


