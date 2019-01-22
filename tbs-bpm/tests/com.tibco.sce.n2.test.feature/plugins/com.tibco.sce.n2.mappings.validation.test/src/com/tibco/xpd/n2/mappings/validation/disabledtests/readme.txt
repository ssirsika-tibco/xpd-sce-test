Sid 02/08/11
These tests are moved into a package which won't be executed in overnight build.
For the following reasons:

DecisionServiceMappingValidationTest:
  Currently junit workspace eclipse startup for amxdecisions platform produces a 
  dialog on startup "Capability Status Changed" which of course will hang up the 
  whole test suite. 
