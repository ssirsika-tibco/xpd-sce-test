/**
 * DO NOT EDIT: This is an automatically generated file.
 * This provides an implementation of the class com.example.proctolocalindirect.ProcLocal. 
 */ 

com.tibco.data.Loader.currentLoader.load("com.example.proctolocal.Class1");

com.tibco.data.Loader.classDefiner["com.example.proctolocalindirect.ProcLocal"] = function() {

/**
 * Constructor.
 */
var theClass = function(context) {
	this.data = new Object();
	this.isParameter = false;
    if (context == null) {
    	this.detached = true;
    } else  {
    	this.detached = (context.item == null);
		this.dataStore = context.dataStore;
		this.item = context.item;
		this.logger = context.logger;
		this.id = context.id;
		this.form = context.form;
		this.loader = this.form._loader;
        this.parentWrapper = context.parentWrapper;
	}
};

theClass.LOADER = com.tibco.data.Loader.currentLoader;
theClass.LOADER.registerClass(theClass, "com.example.proctolocalindirect.ProcLocal");

com.tibco.data.Loader.extendClass(com.tibco.data.BomBase, theClass);

theClass.ATTR_ATTRIBUTE1 = "attribute1";
theClass.ATTR_ATTRIBUTE2 = "attribute2";

theClass.TYPE_ARRAY = new Object();
theClass.TYPE_ARRAY[theClass.ATTR_ATTRIBUTE1] = {type:"BomPrimitiveTypes.Text", baseType:"BomPrimitiveTypes.Text", primitive:true, multivalued:false, required:false, defaultValue:""};
theClass.TYPE_ARRAY[theClass.ATTR_ATTRIBUTE2] = {type:"com.example.proctolocal.Class1", baseType:"com.example.proctolocal.Class1", primitive:false, multivalued:false, required:false, defaultValue:""};

theClass.PRIMITIVE_ATTRIBUTE_NAMES = [theClass.ATTR_ATTRIBUTE1];

theClass.COMPOSITE_ATTRIBUTE_NAMES = [theClass.ATTR_ATTRIBUTE2];

theClass.ATTRIBUTE_NAMES = [theClass.ATTR_ATTRIBUTE1, theClass.ATTR_ATTRIBUTE2];

theClass.getName = function() {
    return "com.example.proctolocalindirect.ProcLocal";
};

theClass._hasAttribute = function(attName) {
	return this.TYPE_ARRAY[attName] != null;
};

theClass._getAttributes = function() {
	return this.ATTRIBUTE_NAMES;
};


theClass._getCompositeAttributes = function() {
    return this.COMPOSITE_ATTRIBUTE_NAMES;
};

theClass._getPrimitiveAttributes = function() {
    return this.PRIMITIVE_ATTRIBUTE_NAMES;
};

theClass._isAttributeMultivalued = function(attName) {
	return this._getTypeDef(attName).multivalued;
};

theClass._isAttributeRequired = function(attName) {
    return this._getTypeDef(attName).required;
};

theClass._getTypeDef = function(attName) {
    var attType = this.TYPE_ARRAY[attName];
	if (attType == null)
		throw("Attribute " + attName + " not recognized for class com.example.proctolocalindirect.ProcLocal");
	return attType;
};

theClass.prototype.getClass  = function() {
    return this.loader.getClass("com.example.proctolocalindirect.ProcLocal");
};

theClass.prototype.getAttribute1 = function(useInternal) {
    return this._getPrimitiveAttribute(this.loader.getClass("com.example.proctolocalindirect.ProcLocal").ATTR_ATTRIBUTE1, useInternal);
};

theClass.prototype.setAttribute1 = function(attribute1) {
    this._setPrimitiveAttribute(this.loader.getClass("com.example.proctolocalindirect.ProcLocal").ATTR_ATTRIBUTE1, attribute1);
};


theClass.prototype.getAttribute2 = function() {
    return this._getComplexAttribute(this.loader.getClass("com.example.proctolocalindirect.ProcLocal").ATTR_ATTRIBUTE2);
};

theClass.prototype.setAttribute2 = function(attribute2) {
    var classRef = this.loader.getClass("com.example.proctolocalindirect.ProcLocal");
    var attrRef = classRef.ATTR_ATTRIBUTE2;
    var attrType = classRef._getTypeDef(attrRef);
    if (eval("attribute2 instanceof this.loader.getClass(attrType.type)")) {
        this._setComplexAttribute(attrRef, attribute2);
    } else {
        throw("Wrong input object type.");
    }
};

theClass.getAttributeNames = function() {
    return theClass._getAttributes();
};

theClass.getPrimitiveAttributeNames = function() {
    return theClass._getPrimitiveAttributes();
};

theClass.getComplexAttributeNames = function() {
    return theClass._getCompositeAttributes();
};

theClass.getAttributeType = function(attName) {
    return this._getTypeDef(attName).type;
};

theClass.isAttributeMultivalued = function(attName) {
    return theClass._isAttributeMultivalued(attName);
};

theClass.isAttributePrimitive = function(attName) {
    return this._getTypeDef(attName).primitive;
};

theClass.prototype.getAttributeValue = function(attName) {
    var isSingle = !theClass.isAttributeMultivalued(attName);
    var isPrimitive = theClass.isAttributePrimitive(attName);
    if (isSingle) {
        if (isPrimitive) {
            return this._getPrimitiveAttribute(attName);
        } else {
            return this._getComplexAttribute(attName);
        }
    } else {
        if (isPrimitive) {
            return this._getPrimitiveArrayAttribute(attName);
        } else {
            return this._getComplexArrayAttribute(attName);
        }
    }    
};

theClass.prototype.setAttributeValue = function(attName, value) {
    var isSingle = !theClass.isAttributeMultivalued(attName);
    var isPrimitive = theClass.isAttributePrimitive(attName);
    if (isSingle) {
        if (isPrimitive) {
            this._setPrimitiveAttribute(attName, value);
        } else {
            this._setComplexAttribute(attName, value);
        }
    } else {
        if (isPrimitive) {
            this._setPrimitiveAttribute(attName, value);
        } else {
            throw("Cannot set child List for attribute \"" + attName + "\". You can only modify the existing List");
        }
    }    
};

};

com.tibco.data.Loader.classDefiner["com.example.proctolocalindirect.ProcLocal"]();
