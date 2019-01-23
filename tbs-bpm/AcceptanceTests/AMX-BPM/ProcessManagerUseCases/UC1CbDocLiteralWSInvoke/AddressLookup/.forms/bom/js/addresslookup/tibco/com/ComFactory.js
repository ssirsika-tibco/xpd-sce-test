/**
 * DO NOT EDIT: This is an automatically generated file.
 * This provides an implementation of the factory class addresslookup.tibco.com.ComFactory. 
 */ 
 
 
 

com.tibco.data.Loader.classDefiner["addresslookup.tibco.com.ComFactory"] = function() {

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
 
com.tibco.data.Loader.currentLoader.registerClass(theClass, "addresslookup.tibco.com.ComFactory");
 
      
theClass.prototype.createZipCode = function() {
    return this.loader.newInstance("addresslookup.tibco.com.zipCode", this.context);
}
      
      
theClass.prototype.createAddress = function() {
    return this.loader.newInstance("addresslookup.tibco.com.address", this.context);
}
      
      
theClass.prototype.createAddressLookupAppDocPort = function() {
    return this.loader.newInstance("addresslookup.tibco.com.AddressLookupAppDocPort", this.context);
}
      
    
};  
com.tibco.data.Loader.classDefiner["addresslookup.tibco.com.ComFactory"]();





