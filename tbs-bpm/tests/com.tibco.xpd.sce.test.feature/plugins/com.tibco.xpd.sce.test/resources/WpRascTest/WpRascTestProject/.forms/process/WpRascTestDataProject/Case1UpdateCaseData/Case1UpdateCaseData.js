/*
 * This provides an implementation of the BOM class com.example.process.WpRascTestDataProject.Case1UpdateCaseData. 
 */
bpm.data.Loader.classDefiner["com.example.process.WpRascTestDataProject.Case1UpdateCaseData"] = function() {
    /** Constructor. */
    var theClass = function(context) {
        this._init(theClass, context);
    };

    theClass.LOADER = bpm.data.Loader.currentLoader;
    theClass.LOADER.registerClass(theClass, "com.example.process.WpRascTestDataProject.Case1UpdateCaseData");
    bpm.data.Loader.extendClass(bpm.data.BomBase, theClass);
    theClass.ATTR_CASE1REF = "Case1Ref";
    theClass.ATTR_ERRORCODE = "ErrorCode";
    theClass.ATTR_ERRORDETAIL = "ErrorDetail";
    theClass.ATTR_CASEOUTOFSYNCHERROR = "CaseOutOfSynchError";
    theClass.ATTR_CASE1 = "Case1";
    theClass.ATTR_CASENOTFOUND = "CaseNotFound";

    theClass.TYPE_ARRAY = {};
    theClass.TYPE_ARRAY[theClass.ATTR_CASE1REF] = {
        type: "BomPrimitiveTypes.Text",
        baseType: "BomPrimitiveTypes.Text",
        primitive: true,
        multivalued: false,
        required: true,
        defaultValue: "",
        length: -1
    };
    theClass.TYPE_ARRAY[theClass.ATTR_ERRORCODE] = {
        type: "BomPrimitiveTypes.Text",
        baseType: "BomPrimitiveTypes.Text",
        primitive: true,
        multivalued: false,
        required: true,
        defaultValue: "",
        length: 50
    };
    theClass.TYPE_ARRAY[theClass.ATTR_ERRORDETAIL] = {
        type: "BomPrimitiveTypes.Text",
        baseType: "BomPrimitiveTypes.Text",
        primitive: true,
        multivalued: false,
        required: true,
        defaultValue: "",
        length: 250
    };
    theClass.TYPE_ARRAY[theClass.ATTR_CASEOUTOFSYNCHERROR] = {
        type: "BomPrimitiveTypes.Text",
        baseType: "BomPrimitiveTypes.Text",
        primitive: true,
        multivalued: false,
        required: true,
        defaultValue: "",
        length: 150
    };
    theClass.TYPE_ARRAY[theClass.ATTR_CASE1] = {
        type: "com.example.wprasctestdataproject.Case1",
        baseType: "com.example.wprasctestdataproject.Case1",
        primitive: false,
        multivalued: false,
        required: true,
        defaultValue: ""
    };
    theClass.TYPE_ARRAY[theClass.ATTR_CASENOTFOUND] = {
        type: "BomPrimitiveTypes.Text",
        baseType: "BomPrimitiveTypes.Text",
        primitive: true,
        multivalued: false,
        required: true,
        defaultValue: "",
        length: 100
    };

    theClass.PRIMITIVE_ATTRIBUTE_NAMES = [
        theClass.ATTR_CASE1REF,
        theClass.ATTR_ERRORCODE,
        theClass.ATTR_ERRORDETAIL,
        theClass.ATTR_CASEOUTOFSYNCHERROR,
        theClass.ATTR_CASENOTFOUND
    ];
    theClass.COMPOSITE_ATTRIBUTE_NAMES = [
        theClass.ATTR_CASE1
    ];
    theClass.ATTRIBUTE_NAMES = [
        theClass.ATTR_CASE1REF,
        theClass.ATTR_ERRORCODE,
        theClass.ATTR_ERRORDETAIL,
        theClass.ATTR_CASEOUTOFSYNCHERROR,
        theClass.ATTR_CASE1,
        theClass.ATTR_CASENOTFOUND
    ];

    theClass.getName = function() {
        return "com.example.process.WpRascTestDataProject.Case1UpdateCaseData";
    };


    Object.defineProperty(theClass.prototype, 'Case1Ref', {
        get: function() {
            return this._getPrimitiveAttribute(this.$loader.getClass("com.example.process.WpRascTestDataProject.Case1UpdateCaseData").ATTR_CASE1REF);
        },
        set: function(Case1Ref) {
            this._setPrimitiveAttribute(this.$loader.getClass("com.example.process.WpRascTestDataProject.Case1UpdateCaseData").ATTR_CASE1REF, Case1Ref);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'ErrorCode', {
        get: function() {
            return this._getPrimitiveAttribute(this.$loader.getClass("com.example.process.WpRascTestDataProject.Case1UpdateCaseData").ATTR_ERRORCODE);
        },
        set: function(ErrorCode) {
            this._setPrimitiveAttribute(this.$loader.getClass("com.example.process.WpRascTestDataProject.Case1UpdateCaseData").ATTR_ERRORCODE, ErrorCode);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'ErrorDetail', {
        get: function() {
            return this._getPrimitiveAttribute(this.$loader.getClass("com.example.process.WpRascTestDataProject.Case1UpdateCaseData").ATTR_ERRORDETAIL);
        },
        set: function(ErrorDetail) {
            this._setPrimitiveAttribute(this.$loader.getClass("com.example.process.WpRascTestDataProject.Case1UpdateCaseData").ATTR_ERRORDETAIL, ErrorDetail);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'CaseOutOfSynchError', {
        get: function() {
            return this._getPrimitiveAttribute(this.$loader.getClass("com.example.process.WpRascTestDataProject.Case1UpdateCaseData").ATTR_CASEOUTOFSYNCHERROR);
        },
        set: function(CaseOutOfSynchError) {
            this._setPrimitiveAttribute(this.$loader.getClass("com.example.process.WpRascTestDataProject.Case1UpdateCaseData").ATTR_CASEOUTOFSYNCHERROR, CaseOutOfSynchError);
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'Case1', {
        get: function() {
            return this._getComplexAttribute(this.$loader.getClass("com.example.process.WpRascTestDataProject.Case1UpdateCaseData").ATTR_CASE1);
        },
        set: function(Case1) {
            var classRef = this.$loader.getClass("com.example.process.WpRascTestDataProject.Case1UpdateCaseData");
            var attrRef = classRef.ATTR_CASE1;
            var attrType = classRef._getTypeDef(attrRef);
            if (eval("(Case1 == null) || Case1 instanceof this.$loader.getClass(attrType.type)")) {
                this._setComplexAttribute(attrRef, Case1);
            } else {
                throw "Wrong input object type for 'Case1' in '" + classRef.getName() + "', expected an instance of '" + attrType.type+ "' but found '" + bpm.scriptUtil.getType(Case1) + "'.";
            }
        },
        enumerable: true
    });


    Object.defineProperty(theClass.prototype, 'CaseNotFound', {
        get: function() {
            return this._getPrimitiveAttribute(this.$loader.getClass("com.example.process.WpRascTestDataProject.Case1UpdateCaseData").ATTR_CASENOTFOUND);
        },
        set: function(CaseNotFound) {
            this._setPrimitiveAttribute(this.$loader.getClass("com.example.process.WpRascTestDataProject.Case1UpdateCaseData").ATTR_CASENOTFOUND, CaseNotFound);
        },
        enumerable: true
    });

};

bpm.data.Loader.classDefiner["com.example.process.WpRascTestDataProject.Case1UpdateCaseData"]();
