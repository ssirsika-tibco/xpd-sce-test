/**
 * DO NOT EDIT: This is an automatically generated file.
 * This provides an implementation of the package class com.example.firstbizdataproj.FirstbizdataprojPackage.
 */

com.tibco.data.Loader.classDefiner["com.example.firstbizdataproj.FirstbizdataprojPackage"] = function() {

/**
 * Constructor.
 */
var theClass = function() {
};

com.tibco.data.Loader.currentLoader.registerClass(theClass, "com.example.firstbizdataproj.FirstbizdataprojPackage");

// Define the enumerations declared by this package.
theClass.Gender = ["MALE", "FEMALE"];
theClass.Gender.MALE = "MALE";
theClass.Gender.FEMALE = "FEMALE";
theClass.getGender = function() {
    return this.Gender;
};

// Load the classes defined by this package.
com.tibco.data.Loader.currentLoader.load("com.example.firstbizdataproj.Customer");
com.tibco.data.Loader.currentLoader.load("com.example.firstbizdataproj.Address");

// Load the associated factory.
com.tibco.data.Loader.currentLoader.load("com.example.firstbizdataproj.FirstbizdataprojFactory");

};

com.tibco.data.Loader.classDefiner["com.example.firstbizdataproj.FirstbizdataprojPackage"]();
