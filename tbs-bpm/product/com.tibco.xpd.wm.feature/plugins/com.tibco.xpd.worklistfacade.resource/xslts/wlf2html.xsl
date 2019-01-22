<?xml version="1.0" encoding="UTF-8"?>

<!--
===================================================================================================================
XSLT:		wlf2html.xsl	
Author:     kthombar
Since:      TBS 4.0.0 (20-May-2015)				

DESCRIPTION:
	This xslt will create the Documentation in HTML format from the selected WLF File.

===================================================================================================================
-->

<!-- Define &nbsp; -->
<!DOCTYPE xsl:stylesheet [
<!ENTITY nbsp  '<xsl:text disable-output-escaping="yes">&amp;nbsp;</xsl:text>'>
]>

<xsl:stylesheet version="1.0"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://www.w3.org/1999/xhtml"
	xmlns:html="http://www.w3.org/1999/xhtml" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xpdExt="http://www.tibco.com/XPD/xpdExtension1.0.0" xmlns:xpdl2="http://www.wfmc.org/2008/XPDL2.1"
	xmlns:java="http://xml.apache.org/xslt/java" xmlns:wlf="http://www.tibco.com/XPD/workListFacade1.0.0"
	exclude-result-prefixes="#default xsl xpdExt xpdl2 wlf ">

	<!-- Parameters... -->
	<xsl:param name="wlfFileName" select="' '" />
	<xsl:param name="wlfFileFullPath" select="' '" />
	<!-- Parameters END -->

	<xsl:output encoding="UTF-8" indent="yes" method="xml"
		version="1.0" />

	<!-- Java extensions. -->
	<xsl:variable name="wlfInfo"
		select="java:com.tibco.xpd.worklistfacade.doc.WlfXsltGeneratorHelper.new()" />
	<xsl:variable name="msgL10n"
		select="java:com.tibco.xpd.worklistfacade.doc.xslt.messages.WlfDocMsgs.new()" />
	<!-- Java extensions END. -->

	<!-- ===================================================================== -->
	<!-- -->
	<!-- XSLT PROCESSING START POINT (PROCESS WHOLE DOCUMENT). -->
	<!-- -->
	<!-- ===================================================================== -->
	<xsl:template match="/">
		<!-- Output the DOCTYPE required or strict xhtml documents. -->
		<xsl:text disable-output-escaping="yes">&lt;</xsl:text><![CDATA[!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"]]><xsl:text
		disable-output-escaping="yes">&gt;</xsl:text>

		<!-- Main <html> header section -->
		<xsl:element name="html" namespace="http://www.w3.org/1999/xhtml">
		
		<!-- XPD-7626: Populate and cache the WorkItemAttribute properties so that we can use it later.-->
		<xsl:value-of select="java:populateWorkitemAttributeProperty($wlfInfo,$wlfFileFullPath)"/>

			<head>
				<meta http-equiv="Content-Type" content="text/xml; charset=UTF-8" />
				<title>
					TIBCO Business Studio -
					<xsl:value-of select="$wlfFileName" />
				</title>
				<link rel="stylesheet" href="style/wlfdoc.css" type="text/css" />
				<link rel="stylesheet" href="style/printwlfdoc.css" type="text/css"
					media="print" />
			</head>

			<body>
			
				<xsl:variable name="workItemAttributeCount"
						select="java:getWorkItemAttributeCount($wlfInfo)" />

				<!-- =========================== Create the Main GSD Page Header ================================== -->
				<xsl:call-template name="createWlfMainHeaderTable"> 
					<xsl:with-param name="workItemAttributeCount" select="$workItemAttributeCount"/>
				</xsl:call-template>
				<!-- =========================== Create the Main GSD Page Header - END 	=========================== -->
				
				<!-- =========================== Create Work Item Attribute Table ================================== -->
				<xsl:call-template name="createWorkItemAttributeTable">
					<xsl:with-param name="workItemAttributeCount" select="$workItemAttributeCount"/>
				</xsl:call-template>
				<!-- =========================== Create Work Item Attribute Table -END============================== -->

			</body>
		</xsl:element>
	</xsl:template>
	<!-- ===================================================================== -->
	<!-- -->
	<!-- END XSLT PROCESSING START POINT (PROCESS WHOLE DOCUMENT). -->
	<!-- -->
	<!-- ===================================================================== -->

	<!-- =================================================================================================================== -->
	<!-- createWorkItemAttributeTable - Template to create WLF Work item attribute table. -->
	<!-- =================================================================================================================== -->
	<xsl:template name="createWorkItemAttributeTable">
		<xsl:param name="workItemAttributeCount"></xsl:param>
		<div class="section">
			<table>
				<thead>
					<tr>
						<th colspan="4" class="sectionTitle">
							<a id="workitemAttributes">
								<xsl:value-of select="java:get($msgL10n, 'WorkItemAttributes_label')" />
							</a>
						</th>
					</tr>
					<tr>
						<th class="wlfAttributeName">
							<xsl:value-of
								select="java:get($msgL10n, 'WorkItemAttributesName_label')" />
						</th>
						<th class="wlfAttributeType">
							<xsl:value-of
								select="java:get($msgL10n, 'WorkItemAttributesType_label')" />
						</th>
						<th class="wlfAttributeLength">
							<xsl:value-of
								select="java:get($msgL10n, 'WorkItemAttributesLength_label')" />
						</th>
						<th class="wlfAttributeDisplayName">
							<xsl:value-of
								select="java:get($msgL10n, 'WorkItemAttributesDisplayName_label')" />
						</th>
					</tr>
				</thead>
				<tbody>
					<!-- call the template to loop through all the 40 work item attributes and output the relavent data. -->
					<xsl:call-template name="loopThroughAndOutputAllWorkItemAttributes">
						<xsl:with-param name="var">0</xsl:with-param>
						<xsl:with-param name="workItemAttributeCount" select="$workItemAttributeCount"/>
					</xsl:call-template>

				</tbody>
			</table>
		</div>
	</xsl:template>

	<!-- =================================================================================================================== -->
	<!-- createWlfMainHeaderTable - Template to create WLF main header table. -->
	<!-- =================================================================================================================== -->
	<xsl:template name="createWlfMainHeaderTable">
		<xsl:param name="workItemAttributeCount"></xsl:param>	
		<table class="mainTitle">
			<tbody>
				<tr>
					<td class="noBorder mainTitleCol1">
						<xsl:value-of select="java:get($msgL10n, 'WlfFile_label')" />
									&nbsp;
					</td>
					<td class="noBorder mainTitle">
						<xsl:value-of select="$wlfFileName" />&nbsp;
					</td>
					<td class="noBorder mainTitle" align="right">
						<img src="images/Logo.png" alt="TIBCO Software Inc." />&nbsp;
					</td>
				</tr>
			</tbody>
		</table>
		<table>
			<tbody>
				<tr class="screenOnly">
					<th class="heading">
						<xsl:value-of select="java:get($msgL10n, 'QuickLinks_label')" />
					</th>
					<td>
						<a href="#workitemAttributes">
							<xsl:value-of select="java:get($msgL10n, 'WorkItemAttributes_label')" />
							<xsl:text> (</xsl:text>
							<xsl:value-of select="$workItemAttributeCount"></xsl:value-of>
							<xsl:text>)</xsl:text>
									&nbsp;
						</a>
					</td>
				</tr>
			</tbody>
		</table>
	</xsl:template>

	<!-- =================================================================================================================== -->
	<!-- loopThroughAndOutputAllWorkItemAttributes - Template that outputs the work item attribute details. -->
	<!-- Reccursively calls itself with values incrementing from 0 to 40 and uses these indexes to get the work item details -->
	<!-- =================================================================================================================== -->
	<xsl:template name="loopThroughAndOutputAllWorkItemAttributes">
		<xsl:param name="var"></xsl:param>
		<xsl:param name="workItemAttributeCount"></xsl:param>
		<xsl:choose>
			<!-- do this only untill var is < 40 -->
			<xsl:when test="$var &lt; $workItemAttributeCount">
				<tr>
					<xsl:variable name="workItemAttributeName"
						select="java:getWorkItemAttributeName($wlfInfo,
				number($var))" />
					<td>
						<xsl:if test="$workItemAttributeName">
							<xsl:value-of select="$workItemAttributeName" />
						</xsl:if>
						&nbsp;
					</td>
					<xsl:variable name="workItemAttributeType"
						select="java:getWorkItemAttributeType($wlfInfo,
				number($var))" />
					<td>
						<xsl:if test="$workItemAttributeType">
							<xsl:value-of select="$workItemAttributeType" />
						</xsl:if>
						&nbsp;
					</td>
					<xsl:variable name="workItemAttributeLength"
						select="java:getWorkItemAttributeLength($wlfInfo,
				number($var))" />
					<td>
						<xsl:if test="$workItemAttributeLength">
							<xsl:value-of select="$workItemAttributeLength" />
						</xsl:if>
						&nbsp;
					</td>
					<xsl:variable name="workItemAttributeDisplayName"
						select="java:getWorkItemAttributeDisplayName($wlfInfo,
				number($var))" />
					<td>
						<xsl:if test="$workItemAttributeDisplayName">
							<xsl:value-of select="$workItemAttributeDisplayName" />
						</xsl:if>
						&nbsp;
					</td>
				</tr>
				<!-- call the template with incremented value -->
				<xsl:call-template name="loopThroughAndOutputAllWorkItemAttributes">
					<xsl:with-param name="var">
						<xsl:number value="number($var)+1" />						
					</xsl:with-param>
					<xsl:with-param name="workItemAttributeCount" select="$workItemAttributeCount"/>
				</xsl:call-template>

			</xsl:when>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>