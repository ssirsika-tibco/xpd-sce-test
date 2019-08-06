
/*
 * This provides an implementation of the BOM class com.example.localsignalprocess.LocalSignalProcess.LocalSignalProcessProcess. 
 */

bpm.data.Loader.classDefiner["com.example.localsignalprocess.LocalSignalProcess.LocalSignalProcessProcess"] = function() {
    /** Constructor. */
    var theClass = function(context) {
        this._init(theClass, context);
    };

    theClass.LOADER = bpm.data.Loader.currentLoader;
    theClass.LOADER.registerClass(theClass, "com.example.localsignalprocess.LocalSignalProcess.LocalSignalProcessProcess");
    bpm.data.Loader.extendClass(bpm.data.BomBase, theClass);

    theClass.ATTR_TEXTFIELD = "TextField";
    theClass.ATTR_TEXTARRAYFIELD = "TextArrayField";
    theClass.ATTR_COMPLEXFIELD = "ComplexField";
    theClass.ATTR_COMPLEXARRAYFIELD = "ComplexArrayField";

    theClass.TYPE_ARRAY = new Object();
    theClass.TYPE_ARRAY[theClass.ATTR_TEXTFIELD] = {
        type: "BomPrimitiveTypes.Text",
        baseType: "BomPrimitiveTypes.Text",
        primitive: true,
        multivalued: false,
        required: true,
        defaultValue: "",
        length: 50
    };
    theClass.TYPE_ARRAY[theClass.ATTR_TEXTARRAYFIELD] = {
        type: "BomPrimitiveTypes.Text",
        baseType: "BomPrimitiveTypes.Text",
        primitive: true,
        multivalued: true,
        required: true,
        defaultValue: "",
        length: 50
    };
    theClass.TYPE_ARRAY[theClass.ATTR_COMPLEXFIELD] = {
        type: "com.example.localsignaldata.Class1",
        baseType: "com.example.localsignaldata.Class1",
        primitive: false,
        multivalued: false,
        required: true,
        defaultValue: ""
    };
    theClass.TYPE_ARRAY[theClass.ATTR_COMPLEXARRAYFIELD] = {
        type: "com.example.localsignaldata.Class1",
        baseType: "com.example.localsignaldata.Class1",
        primitive: false,
        multivalued: true,
        required: true,
        defaultValue: ""
    };

    theClass.PRIMITIVE_ATTRIBUTE_NAMES = [
        theClass.ATTR_TEXTFIELD,
        theClass.ATTR_TEXTARRAYFIELD
    ];
    theClass.COMPOSITE_ATTRIBUTE_NAMES = [
        theClass.ATTR_COMPLEXFIELD,
        theClass.ATTR_COMPLEXARRAYFIELD
    ];
    theClass.ATTRIBUTE_NAMES = [
        theClass.ATTR_TEXTFIELD,
        theClass.ATTR_TEXTARRAYFIELD,
        theClass.ATTR_COMPLEXFIELD,
        theClass.ATTR_COMPLEXARRAYFIELD
    ];

    theClass.getName = function() {
        return "com.example.localsignalprocess.LocalSignalProcess.LocalSignalProcessProcess";
    };


    Object.defineProperty(theClass.prototype, 'TextField', {
        get: function() {
            return this._getPrimitiveAttribute(this.loader.getClass("com.example.localsignalprocess.LocalSignalProcess.LocalSignalProcessProcess").ATTR_TEXTFIELD);
        },
        set: function(TextField) {
            this._setPrimitiveAttribute(this.loader.getClass("com.example.localsignalprocess.LocalSignalProcess.LocalSignalProcessProcess").ATTR_TEXTFIELD, TextField);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'TextArrayField', {
        get: function() {
            return this._getPrimitiveArrayAttribute(this.loader.getClass("com.example.localsignalprocess.LocalSignalProcess.LocalSignalProcessProcess").ATTR_TEXTARRAYFIELD);
        },
        set: function(TextArrayField) {
            this._setPrimitiveAttribute(this.loader.getClass("com.example.localsignalprocess.LocalSignalProcess.LocalSignalProcessProcess").ATTR_TEXTARRAYFIELD, TextArrayField);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'ComplexField', {
        get: function() {
            return this._getComplexAttribute(this.loader.getClass("com.example.localsignalprocess.LocalSignalProcess.LocalSignalProcessProcess").ATTR_COMPLEXFIELD);
        },
        set: function(ComplexField) {
            var classRef = this.loader.getClass("com.example.localsignalprocess.LocalSignalProcess.LocalSignalProcessProcess");
            var attrRef = classRef.ATTR_COMPLEXFIELD;
            var attrType = classRef._getTypeDef(attrRef);
            if (eval("ComplexField instanceof this.loader.getClass(attrType.type)")) {
                this._setComplexAttribute(attrRef, ComplexField);
            } else {
                throw "Wrong input object type.";
            }
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'ComplexArrayField', {
        get: function() {
            return this._getComplexArrayAttribute(this.loader.getClass("com.example.localsignalprocess.LocalSignalProcess.LocalSignalProcessProcess").ATTR_COMPLEXARRAYFIELD);
        },
        set: function(ComplexArrayField) {
            throw "Unsupported complex array attribute: ComplexArrayField";
        },
        enumerable: true
    });

};

bpm.data.Loader.classDefiner["com.example.localsignalprocess.LocalSignalProcess.LocalSignalProcessProcess"]();
