/**
 * DO NOT EDIT: This is an automatically generated file.
 * This provides an implementation of the factory class com.example.localbomproj.LocalbomprojFactory. 
 */ 

com.tibco.data.Loader.classDefiner["com.example.localbomproj.LocalbomprojFactory"] = function() {

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

com.tibco.data.Loader.currentLoader.registerClass(theClass, "com.example.localbomproj.LocalbomprojFactory");

theClass.prototype.SUPPORTED_CLASSES = ["com.example.localbomproj.Person"];

theClass.prototype.checkClassName = function(className) {
    for(i = 0; i < this.SUPPORTED_CLASSES.length; i++) {
        if (className == this.SUPPORTED_CLASSES[i]) return;
    }
    throw("Factory com.example.localbomproj.LocalbomprojFactory does not support class \"" + className + "\"");
};

theClass.prototype.create = function(className) {
    this.checkClassName(className);
    return this.loader.newInstance(className, this.context);
};

theClass.prototype.createPerson = function() {
    return this.loader.newInstance("com.example.localbomproj.Person", this.context);
};
    
theClass.prototype.getClass = function(className) {
    this.checkClassName(className);
    return this.loader.getClass(className);
};
};

com.tibco.data.Loader.classDefiner["com.example.localbomproj.LocalbomprojFactory"]();
