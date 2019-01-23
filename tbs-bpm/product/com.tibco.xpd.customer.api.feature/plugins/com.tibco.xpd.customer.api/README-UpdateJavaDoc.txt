If there are any changes in api or javadoc comment of 'com.tibco.xpd.customer.api' then javadoc 
should be regenerated in the accompanying plug-in 'com.tibco.xpd.customer.api.doc'.

See also: XPD-5671

To regenerate javadoc api:

1. Build com.tibco.xpd.customer.api.feature etc (go to root of release unit (the parent of features and plugins source folders) RU using tibco builder. 
	(ant command: ant clean conf-clean build)

2. Go to candidates jar/release folder 
	(location will be provided at the end of console build:
	for example: D:\src\build\candidates\xpd-repository\com.tibco.xpd.customer.api.feature\2014-01-15-1747.jarciuch\jar\release)

3. In plug-ins folder unzip  docs.com.tibco.xpd.customer.api_1.0.0.XXXX.jar into a folder and copy/replace content of: 
	html/reference/javadoc folder 
	into com.tibco.xpd.customer.api.doc/html/reference/javadoc in your workspace.

4. Commit changes to com.tibco.xpd.customer.api.doc plug-in.


ADDENDUM - EXCLUDING PACKAGES FROM JavaDoc  
If new packages are added that should be excluded from javadoc then go to the properties file such as...
   https://emea-swi-svn.emea.tibco.com/svn/technical/xpd/trunk/tbs-bpm/product/com.tibco.xpd.customer.api.feature/javadoc.properties
   
Add add an entry to the excluded packages entry:
   # packages to exclude 
   attribute.excludepackagenames=com.tibco.xpd.customer.api.plugin, com.tibco.xpd.customer.api.internal, com.tibco.xpd.customer.api.iprocess.internal

**Note: there is also something that claims to be "packages to include* but I (Sid 27/05/2014) could not get this to function.

