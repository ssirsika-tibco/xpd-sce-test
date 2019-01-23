/**
 * DO NOT EDIT: This is an automatically generated file.
 * This provides an implementation of the factory class com.example.businessobjectmodel.BusinessobjectmodelFactory. 
 */ 

com.tibco.data.Loader.classDefiner["com.example.businessobjectmodel.BusinessobjectmodelFactory"] = function() {

/**
 * Constructor.
 */
var theClass = function(form) {
    this.context = new Object();
    this.context.form = form;
    this.context.dataStore = form.getDataStore();
    this.context.logger = form.getLogger();
    this.context.item = null;
    this.context.id = null;
    this.loader = form._loader;
};

com.tibco.data.Loader.currentLoader.registerClass(theClass, "com.example.businessobjectmodel.BusinessobjectmodelFactory");

theClass.prototype.SUPPORTED_CLASSES = ["com.example.businessobjectmodel.Class1", "com.example.businessobjectmodel.Class2"];

theClass.prototype.checkClassName = function(className) {
    for(i = 0; i < this.SUPPORTED_CLASSES.length; i++) {
        if (className == this.SUPPORTED_CLASSES[i]) return;
    }
    throw("Factory com.example.businessobjectmodel.BusinessobjectmodelFactory does not support class \"" + className + "\"");
};

theClass.prototype.create = function(className, jsonData) {
    this.checkClassName(className);
    var classObject = this.loader.newInstance(className, this.context);
    if(jsonData != undefined)
        classObject = classObject._setValue(classObject, jsonData, this.context);
    return classObject;
};

theClass.prototype.createClass1 = function(jsonData) {
    var classObject = this.loader.newInstance("com.example.businessobjectmodel.Class1", this.context);
    if(jsonData != undefined)
        classObject = classObject._setValue(classObject, jsonData);
    return classObject;
};

theClass.prototype.listCreateClass1 = function(jsonData) {
    var classObject = this.loader.newInstance("com.example.businessobjectmodel.Class1", this.context);
    var classList;
    classList = classObject._setValueList(classObject, jsonData);
    return classList;
};
    
theClass.prototype.createClass2 = function(jsonData) {
    var classObject = this.loader.newInstance("com.example.businessobjectmodel.Class2", this.context);
    if(jsonData != undefined)
        classObject = classObject._setValue(classObject, jsonData);
    return classObject;
};

theClass.prototype.listCreateClass2 = function(jsonData) {
    var classObject = this.loader.newInstance("com.example.businessobjectmodel.Class2", this.context);
    var classList;
    classList = classObject._setValueList(classObject, jsonData);
    return classList;
};
    
theClass.prototype.getClass = function(className) {
    this.checkClassName(className);
    return this.loader.getClass(className);
};
};

com.tibco.data.Loader.classDefiner["com.example.businessobjectmodel.BusinessobjectmodelFactory"]();
