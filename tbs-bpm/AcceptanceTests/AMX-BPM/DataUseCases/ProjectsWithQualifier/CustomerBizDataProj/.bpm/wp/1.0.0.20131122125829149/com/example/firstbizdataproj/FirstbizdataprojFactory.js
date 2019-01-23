/**
 * DO NOT EDIT: This is an automatically generated file.
 * This provides an implementation of the factory class com.example.firstbizdataproj.FirstbizdataprojFactory. 
 */ 

com.tibco.data.Loader.classDefiner["com.example.firstbizdataproj.FirstbizdataprojFactory"] = function() {

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

com.tibco.data.Loader.currentLoader.registerClass(theClass, "com.example.firstbizdataproj.FirstbizdataprojFactory");

theClass.prototype.SUPPORTED_CLASSES = ["com.example.firstbizdataproj.Customer", "com.example.firstbizdataproj.Address"];

theClass.prototype.checkClassName = function(className) {
    for(i = 0; i < this.SUPPORTED_CLASSES.length; i++) {
        if (className == this.SUPPORTED_CLASSES[i]) return;
    }
    throw("Factory com.example.firstbizdataproj.FirstbizdataprojFactory does not support class \"" + className + "\"");
};

theClass.prototype.create = function(className) {
    this.checkClassName(className);
    return this.loader.newInstance(className, this.context);
};

theClass.prototype.createCustomer = function() {
    return this.loader.newInstance("com.example.firstbizdataproj.Customer", this.context);
};
    
theClass.prototype.createAddress = function() {
    return this.loader.newInstance("com.example.firstbizdataproj.Address", this.context);
};
    
theClass.prototype.getClass = function(className) {
    this.checkClassName(className);
    return this.loader.getClass(className);
};
};

com.tibco.data.Loader.classDefiner["com.example.firstbizdataproj.FirstbizdataprojFactory"]();
