/*
 * This provides an implementation of the BOM class com.example.bomlinksprocess.BomLinksProcess.BomLinksProcessProcess. 
 */
bpm.data.Loader.classDefiner["com.example.bomlinksprocess.BomLinksProcess.BomLinksProcessProcess"] = function() {
    /** Constructor. */
    var theClass = function(context) {
        this._init(theClass, context);
    };

    theClass.LOADER = bpm.data.Loader.currentLoader;
    theClass.LOADER.registerClass(theClass, "com.example.bomlinksprocess.BomLinksProcess.BomLinksProcessProcess");
    bpm.data.Loader.extendClass(bpm.data.BomBase, theClass);
    theClass.ATTR_ORDERCASEREF = "OrderCaseRef";
    theClass.ATTR_PRODUCTCASEREF = "ProductCaseRef";

    theClass.TYPE_ARRAY = {};
    theClass.TYPE_ARRAY[theClass.ATTR_ORDERCASEREF] = {
        type: "BomPrimitiveTypes.Text",
        baseType: "BomPrimitiveTypes.Text",
        primitive: true,
        multivalued: false,
        required: true,
        defaultValue: "",
        length: -1
    };
    theClass.TYPE_ARRAY[theClass.ATTR_PRODUCTCASEREF] = {
        type: "BomPrimitiveTypes.Text",
        baseType: "BomPrimitiveTypes.Text",
        primitive: true,
        multivalued: false,
        required: true,
        defaultValue: "",
        length: -1
    };

    theClass.PRIMITIVE_ATTRIBUTE_NAMES = [
        theClass.ATTR_ORDERCASEREF,
        theClass.ATTR_PRODUCTCASEREF
    ];
    theClass.COMPOSITE_ATTRIBUTE_NAMES = [

    ];
    theClass.ATTRIBUTE_NAMES = [
        theClass.ATTR_ORDERCASEREF,
        theClass.ATTR_PRODUCTCASEREF
    ];

    theClass.getName = function() {
        return "com.example.bomlinksprocess.BomLinksProcess.BomLinksProcessProcess";
    };


    Object.defineProperty(theClass.prototype, 'OrderCaseRef', {
        get: function() {
            return this._getPrimitiveAttribute(this.$loader.getClass("com.example.bomlinksprocess.BomLinksProcess.BomLinksProcessProcess").ATTR_ORDERCASEREF);
        },
        set: function(OrderCaseRef) {
            this._setPrimitiveAttribute(this.$loader.getClass("com.example.bomlinksprocess.BomLinksProcess.BomLinksProcessProcess").ATTR_ORDERCASEREF, OrderCaseRef);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'ProductCaseRef', {
        get: function() {
            return this._getPrimitiveAttribute(this.$loader.getClass("com.example.bomlinksprocess.BomLinksProcess.BomLinksProcessProcess").ATTR_PRODUCTCASEREF);
        },
        set: function(ProductCaseRef) {
            this._setPrimitiveAttribute(this.$loader.getClass("com.example.bomlinksprocess.BomLinksProcess.BomLinksProcessProcess").ATTR_PRODUCTCASEREF, ProductCaseRef);
        },
        enumerable: true
    });

};

bpm.data.Loader.classDefiner["com.example.bomlinksprocess.BomLinksProcess.BomLinksProcessProcess"]();
