This is the ant script to quickly build or clean a set of features (usually all features for this project) using TIBCO Builder. 

The "product-features.txt" contains an ordered list of product features to build (and similarly "test-features.txt" contains test futures list).  

An example command to build all scf product and test features would be: ant build-all

Targets:

 build-all      Builds all product and test features.
 build-product  Builds all product features.
 build-tests    Builds all test features.
 clean-all      Builds all product and test features.
 clean-product  Cleans all product features.
 clean-tests    Cleans all test features.
 default        Default task: builds and then clean all product features.
Default target: default