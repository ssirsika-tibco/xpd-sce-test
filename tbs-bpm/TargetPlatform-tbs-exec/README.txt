Summary
======
Exec target platform is used as a target platform for a running studio (for example during plugin junit tests).

It is build from: 
	- the constant base in c:\builder\eclipse\base-exec-tp.zip
	- the set of features defined in requestedReleaseUnits.xml

Base exec TP:
=============
base-exec-tp.zip - is a base amx target platform. (currently platform 3.4-V42). To create one please:
- download and install SDS V build: http://reldist.na.tibco.com/package/amsg/3.4.0/ (or V build of studio that adopts a amx platform - in this case the base will already contain almost everything needed (see: requestedReleaseUnits.xml))
- zip the content of ${tibco.home}\components\shared\1.0.0 into base-exec-tp.zip (zip should contain directly features and plugins folders)

 
Test running instructions:
==========================
Please: 
1.) download latest tp, 
2.) modify provided template 'devTargetPlatform.target.template' to point to unzipped content (also remove .template) and 
3.) run development studio with the following property for every new workspace to set up a correct AMX target platform: -Dcom.tibco.target.platform.location=C:\DevTargetPlatform\devTargetPlatform.target 