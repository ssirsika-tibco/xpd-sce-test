/**
 * DO NOT EDIT: This is an automatically generated file.
 * This provides an implementation of the class com.example.firstbizdataproj.Customer. 
 */ 

com.tibco.data.Loader.currentLoader.load("com.example.firstbizdataproj.Address");

com.tibco.data.Loader.classDefiner["com.example.firstbizdataproj.Customer"] = function() {

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
theClass.LOADER.registerClass(theClass, "com.example.firstbizdataproj.Customer");

com.tibco.data.Loader.extendClass(com.tibco.data.BomBase, theClass);

theClass.ATTR_AUTOCASEIDENTIFIER1 = "autoCaseIdentifier1";
theClass.ATTR_NAME = "name";
theClass.ATTR_ADDRESS = "address";
theClass.ATTR_GENDER = "gender";

theClass.TYPE_ARRAY = new Object();
theClass.TYPE_ARRAY[theClass.ATTR_AUTOCASEIDENTIFIER1] = {type:"BomPrimitiveTypes.Integer", baseType:"BomPrimitiveTypes.Integer", primitive:true, multivalued:false, required:false, defaultValue:""};
theClass.TYPE_ARRAY[theClass.ATTR_NAME] = {type:"BomPrimitiveTypes.Text", baseType:"BomPrimitiveTypes.Text", primitive:true, multivalued:false, required:false, defaultValue:""};
theClass.TYPE_ARRAY[theClass.ATTR_ADDRESS] = {type:"com.example.firstbizdataproj.Address", baseType:"com.example.firstbizdataproj.Address", primitive:false, multivalued:false, required:true, defaultValue:""};
theClass.TYPE_ARRAY[theClass.ATTR_GENDER] = {type:"com.example.firstbizdataproj.Gender", baseType:"com.example.firstbizdataproj.Gender", primitive:true, multivalued:false, required:false, defaultValue:""};

theClass.PRIMITIVE_ATTRIBUTE_NAMES = [theClass.ATTR_AUTOCASEIDENTIFIER1, theClass.ATTR_NAME, theClass.ATTR_GENDER];

theClass.COMPOSITE_ATTRIBUTE_NAMES = [theClass.ATTR_ADDRESS];

theClass.ATTRIBUTE_NAMES = [theClass.ATTR_AUTOCASEIDENTIFIER1, theClass.ATTR_NAME, theClass.ATTR_ADDRESS, theClass.ATTR_GENDER];

theClass.getName = function() {
    return "com.example.firstbizdataproj.Customer";
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
		throw("Attribute " + attName + " not recognized for class com.example.firstbizdataproj.Customer");
	return attType;
};

theClass.prototype.getClass  = function() {
    return this.loader.getClass("com.example.firstbizdataproj.Customer");
};

theClass.prototype.getAutoCaseIdentifier1 = function(useInternal) {
    return this._getPrimitiveAttribute(this.loader.getClass("com.example.firstbizdataproj.Customer").ATTR_AUTOCASEIDENTIFIER1, useInternal);
};

theClass.prototype.setAutoCaseIdentifier1 = function(autoCaseIdentifier1) {
    this._setPrimitiveAttribute(this.loader.getClass("com.example.firstbizdataproj.Customer").ATTR_AUTOCASEIDENTIFIER1, autoCaseIdentifier1);
};


theClass.prototype.getName = function(useInternal) {
    return this._getPrimitiveAttribute(this.loader.getClass("com.example.firstbizdataproj.Customer").ATTR_NAME, useInternal);
};

theClass.prototype.setName = function(name) {
    this._setPrimitiveAttribute(this.loader.getClass("com.example.firstbizdataproj.Customer").ATTR_NAME, name);
};


theClass.prototype.getAddress = function() {
    return this._getComplexAttribute(this.loader.getClass("com.example.firstbizdataproj.Customer").ATTR_ADDRESS);
};

theClass.prototype.setAddress = function(address) {
    var classRef = this.loader.getClass("com.example.firstbizdataproj.Customer");
    var attrRef = classRef.ATTR_ADDRESS;
    var attrType = classRef._getTypeDef(attrRef);
    if (eval("address instanceof this.loader.getClass(attrType.type)")) {
        this._setComplexAttribute(attrRef, address);
    } else {
        throw("Wrong input object type.");
    }
};


theClass.prototype.getGender = function(useInternal) {
    return this._getPrimitiveAttribute(this.loader.getClass("com.example.firstbizdataproj.Customer").ATTR_GENDER, useInternal);
};

theClass.prototype.setGender = function(gender) {
    this._setPrimitiveAttribute(this.loader.getClass("com.example.firstbizdataproj.Customer").ATTR_GENDER, gender);
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

com.tibco.data.Loader.classDefiner["com.example.firstbizdataproj.Customer"]();
