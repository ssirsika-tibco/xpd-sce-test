<?xml version="1.0" encoding="UTF-8"?>

<!--
===================================================================================================================
XSLT:		gsd2html.xsl	
Author:     kthombar
Since:      TBS 4.0.0 (04-May-2015)				

DESCRIPTION:
	This xslt will create the Procedure Documentation in HTML format from the selected package GSD File.

===================================================================================================================
-->

<!-- Define &nbsp;  -->
<!DOCTYPE xsl:stylesheet [
<!ENTITY nbsp  '<xsl:text disable-output-escaping="yes">&amp;nbsp;</xsl:text>'>
]>

<xsl:stylesheet version="1.0"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://www.w3.org/1999/xhtml"
	xmlns:html="http://www.w3.org/1999/xhtml" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xpdExt="http://www.tibco.com/XPD/xpdExtension1.0.0" xmlns:xpdl2="http://www.wfmc.org/2008/XPDL2.1"
	xmlns:java="http://xml.apache.org/xslt/java" xmlns:gsd="http://www.tibco.com/XPD/GlobalSignalDefinition1.0.0"
	exclude-result-prefixes="#default xsl xpdExt xpdl2 gsd ">

	<!-- Parameters... -->
	<xsl:param name="gsdSpecialFolderRelativePath" select="' '" />
	<xsl:param name="gsdProjectId" select="' '" />
	<xsl:param name="gsdFileName" select="' '" />
	<!-- Parameters END -->

	<xsl:output encoding="UTF-8" indent="yes" method="xml"
		version="1.0" />

	<!-- Java extensions. -->
	<xsl:variable name="globalSignalInfo"
		select="java:com.tibco.xpd.n2.globalsignal.doc.GlobalSignalXsltGeneratorHelper.new()" />
	<xsl:variable name="msgL10n"
		select="java:com.tibco.xpd.n2.globalsignal.doc.xslt.messages.GsdDocMsgs.new()" />
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

			<head>
				<meta http-equiv="Content-Type" content="text/xml; charset=UTF-8" />
				<title>
					TIBCO Business Studio -
					<xsl:value-of select="$gsdFileName" />
				</title>
				<link rel="stylesheet" href="style/gsddoc.css" type="text/css" />
				<link rel="stylesheet" href="style/printgsddoc.css" type="text/css"
					media="print" />
			</head>

			<body>
				<!-- get the count of the number of global signals in the gsd file -->
				<xsl:variable name="globalSignalCount">
					<xsl:value-of
						select="count(gsd:GlobalSignalDefinitions/gsd:GlobalSignals/gsd:GlobalSignal)"></xsl:value-of>
				</xsl:variable>
				
				<!-- =========================== Create the Main GSD Page Header ================================== -->
								
				<xsl:call-template name="createGsdMainHeaderTable">
					<xsl:with-param name="globalSignalCount" select="$globalSignalCount" />
				</xsl:call-template>
				<!-- ================================= Main GSD Page Header - END =========================== -->

				<xsl:if test="$globalSignalCount > 0">

					<!-- ============================= GLOBAL SIGNAL OVERVIEW TABLE =============================== -->
					<!-- Create the Global Signal table that has the Global Signal names -->
					<!-- and their descriptions resp. -->
					<!-- ========================================================================================== -->
					
					<xsl:call-template name="createGlobalSignalOverviewTable"></xsl:call-template>

					<!-- ======================= GLOBAL SIGNAL OVERVIEW TABLE- END ==================================== -->
					
					
					
					<!-- ======================== GLOBAL SIGNAL AND PAYLOAD DETAILED TABLE =========================================-->
					<!-- For each Global Signal create a table that details its correlation -->
					<!-- value, its description and quick links to the payload in the global -->
					<!-- signal. -->
					<!-- ============================================================================================== -->
					<xsl:call-template name="createGlobalSignalAndPayloadDetailsTable"></xsl:call-template>
					
					<!-- ================== GLOBAL SIGNAL AND PAYLOAD DETAILED TABLE - END =========================== -->
					
				</xsl:if>
			</body>
		</xsl:element>
	</xsl:template>

	<!-- =================================================================================================================== -->
	<!-- createGlobalSignalOverviewTable - Template to Create Global Signal Overview table. -->
	<!-- =================================================================================================================== -->
	<xsl:template name="createGlobalSignalOverviewTable">
		<div class="section">
			<table>
				<thead>
					<tr>
						<th colspan="2" class="sectionTitle">
							<a id="globalSignals">
								<xsl:value-of select="java:get($msgL10n, 'GlobalSignals_label')" />
							</a>
						</th>
					</tr>
					<tr>
						<th class="columnWidth">
							<xsl:value-of select="java:get($msgL10n, 'Name_label')" />
						</th>
						<th>
							<xsl:value-of select="java:get($msgL10n, 'Description_label')" />
						</th>
					</tr>
				</thead>
				<tbody>
					<xsl:for-each
						select="gsd:GlobalSignalDefinitions/gsd:GlobalSignals/gsd:GlobalSignal">
						<tr>
							<td>
								<a href="#signalName_{@Name}">
									<xsl:value-of select="@Name" />&nbsp;
								</a>
							</td>
							<td>
								<xsl:value-of select="gsd:Description" />&nbsp;
							</td>
						</tr>
					</xsl:for-each>
				</tbody>
			</table>
		</div>
	</xsl:template>
	<!-- =================================================================================================================== -->
	<!-- createGlobalSignalOverviewTable - End of template. -->
	<!-- =================================================================================================================== -->


	<!-- =================================================================================================================== -->
	<!-- createGsdMainHeaderTable - Template to Create the Main GSD Page Header. -->
	<!-- =================================================================================================================== -->
	<xsl:template name="createGsdMainHeaderTable">
		<xsl:param name="globalSignalCount"></xsl:param>
		<table class="mainTitle">
			<tbody>
				<tr>
					<td class="noBorder mainTitleCol1">
						<xsl:value-of select="java:get($msgL10n, 'GsdFile_label')" />
									&nbsp;
					</td>
					<td class="noBorder mainTitle">
						<xsl:value-of select="$gsdFileName" />&nbsp;
					</td>
					<td class="noBorder mainTitle" align="right">
						<img src="images/Logo.png" alt="TIBCO Software Inc." />&nbsp;
					</td>
				</tr>
			</tbody>
		</table>
		<!-- Table that displays the gsd file description and the quick links to 
			global signals table -->
		<table>
			<tbody>
				<tr>
					<th class="heading">
						<xsl:value-of select="java:get($msgL10n, 'Description_label')" />
					</th>
					<td>
						<xsl:value-of select="gsd:GlobalSignalDefinitions/gsd:Description" />&nbsp;
					</td>
				</tr>
				<tr class="screenOnly">
					<th class="heading">
						<xsl:value-of select="java:get($msgL10n, 'QuickLinks_label')" />
					</th>
					<td>
						<a href="#globalSignals">
							<xsl:value-of select="java:get($msgL10n, 'GlobalSignals_label')" />
							<xsl:text> (</xsl:text>
							<xsl:value-of select="$globalSignalCount" />
							<xsl:text>)</xsl:text>&nbsp;
						</a>
					</td>
				</tr>
			</tbody>
		</table>
	</xsl:template>
	<!-- =================================================================================================================== -->
	<!-- createGsdMainHeaderTable - End of template. -->
	<!-- =================================================================================================================== -->

	<!-- =================================================================================================================== -->
	<!-- createGlobalSignalAndPayloadDetailsTable - Template to create the global signal and payload data details table -->
	<!-- =================================================================================================================== -->
	<xsl:template name="createGlobalSignalAndPayloadDetailsTable">
		<xsl:for-each
			select="gsd:GlobalSignalDefinitions/gsd:GlobalSignals/gsd:GlobalSignal">

			<xsl:variable name="globalSignalName">
				<xsl:value-of select="@Name"></xsl:value-of>
			</xsl:variable>

			<div>
				<table>
					<tbody>
						<tr>
							<th class="globalSignalTitle" colspan="2">
								<a id="signalName_{$globalSignalName}">
									<xsl:value-of select="java:get($msgL10n, 'GlobalSignal_label')" />
									<xsl:text>: </xsl:text>
									<xsl:value-of select="$globalSignalName" />
								</a>
							</th>
						</tr>
					</tbody>
				</table>
			</div>

			<xsl:variable name="payloadDataCount">
				<xsl:value-of select="count(gsd:PayloadDataFields/gsd:PayloadDataField)"></xsl:value-of>
			</xsl:variable>

			<table>
				<tbody>
					<tr>
						<th class="heading">
							<xsl:value-of select="java:get($msgL10n, 'Description_label')" />
						</th>
						<td>
							<xsl:value-of select="gsd:Description" />&nbsp;
						</td>
					</tr>
					<tr>
						<th class="heading">
							<xsl:value-of select="java:get($msgL10n, 'Correlation_label')" />
						</th>
						<td>
							<xsl:choose>
								<xsl:when test="@CorrelationTimeout != ''">
									<xsl:value-of select="java:get($msgL10n, 'TimeoutSignalAfter_label')" />
									<xsl:text> </xsl:text>
									<xsl:value-of select="@CorrelationTimeout" />
									<xsl:text> </xsl:text>
									<xsl:value-of select="java:get($msgL10n, 'Seconds_label')" />&nbsp;
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of
										select="java:get($msgL10n, 'CorrelationImmediately_label')" />
								</xsl:otherwise>
							</xsl:choose>
						</td>
					</tr>
					<tr class="screenOnly">
						<th class="heading">
							<xsl:value-of select="java:get($msgL10n, 'QuickLinks_label')" />
						</th>
						<td>
							<a href="#payload_{@Name}">
								<xsl:value-of select="java:get($msgL10n, 'PayloadData_label')" />
								<xsl:text> (</xsl:text>
								<xsl:value-of select="$payloadDataCount" />
								<xsl:text>)</xsl:text>
											&nbsp;
							</a>
						</td>
					</tr>
				</tbody>
			</table>

			<!-- ======================== PAYLOAD DATA DETAILED TABLE ===================================== -->
			<!-- For each Global Signal create a table for all the payload data -->
			<!-- in the global signal which provides all the info of the payload -->
			<!-- ========================================================================================== -->

			<xsl:if test="$payloadDataCount > 0">
				<div class="section">
					<table>
						<thead>
							<tr>
								<th colspan="6" class="sectionTitle">
									<a id="payload_{@Name}">
										<xsl:value-of select="java:get($msgL10n, 'PayloadData_label')" />
									</a>
								</th>
							</tr>
							<tr>
								<th class="payloadName">
									<xsl:value-of select="java:get($msgL10n, 'Name_label')" />
								</th>
								<th class="payloadType">
									<xsl:value-of select="java:get($msgL10n, 'PayloadDataType_label')" />
								</th>
								<th class="payloadUseForCorrelationColumn">
									<xsl:value-of
										select="java:get($msgL10n, 'UseForSignalSorrelation_label')" />
								</th>
								<th class="payloadMandatoryColumn">
									<xsl:value-of select="java:get($msgL10n, 'Mandatory_label')" />
								</th>
								<th class="payloadLength">
									<xsl:value-of select="java:get($msgL10n, 'PayloadLength_label')" />
								</th>
								<th class="payloadDescription">
									<xsl:value-of select="java:get($msgL10n, 'Description_label')" />
								</th>
							</tr>
						</thead>
						<tbody>
							<xsl:for-each select="gsd:PayloadDataFields/gsd:PayloadDataField">
								<tr>
									<td>
										<xsl:value-of select="@Name" />&nbsp;
									</td>
									<!-- Call the template to display the data type of the payload -->
									<xsl:call-template name="outputDataType">
										<xsl:with-param name="payloadDataId" select="@Id" />
										<xsl:with-param name="globalSignalName" select="$globalSignalName" />
									</xsl:call-template>
									<td>
										<xsl:if test="@Correlation = 'true'">
											<img alt="Yes" src="images/tick.gif" />
										</xsl:if>&nbsp;
									</td>
									<td>
										<xsl:if test="@Mandatory = 'true'">
											<img alt="Yes" src="images/tick.gif" />
										</xsl:if>&nbsp;
									</td>
									<td>
										<xsl:value-of select="xpdl2:DataType/xpdl2:BasicType/xpdl2:Length" />&nbsp;
									</td>
									<td>
										<xsl:value-of select="xpdl2:Description" />&nbsp;
									</td>
								</tr>
							</xsl:for-each>
						</tbody>
					</table>
				</div>
			</xsl:if>
			<!-- ======================= PAYLOAD DATA DETAILED TABLE -END ===================================== -->
		</xsl:for-each>
	</xsl:template>
	<!-- =================================================================================================================== -->
	<!-- createGlobalSignalAndPayloadDetailsTable - End of template. -->
	<!-- =================================================================================================================== -->

	<!-- =================================================================================================================== -->
	<!-- outputDataType - Template to output the Data Types of the Payload Data. -->
	<!-- =================================================================================================================== -->
	<xsl:template name="outputDataType">
		<xsl:param name="payloadDataId" />
		<xsl:param name="globalSignalName" />

		<td style="white-space: nowrap;">
			<xsl:variable name="basicType"
				select="java:getPayloadDataBasicType($globalSignalInfo, $gsdProjectId, $gsdFileName, $gsdSpecialFolderRelativePath, $globalSignalName, $payloadDataId)" />

			<xsl:variable name="typeName">
				<xsl:choose>
					<xsl:when test="$basicType != ''">
						<xsl:choose>
							<xsl:when test="$basicType = 'STRING'">
								<xsl:value-of select="java:get($msgL10n, 'DataTypeString_label')" />
							</xsl:when>
							<xsl:when test="$basicType = 'INTEGER'">
								<xsl:value-of select="java:get($msgL10n, 'DataTypeInteger_label')" />
							</xsl:when>
							<xsl:when test="$basicType = 'FLOAT'">
								<xsl:value-of select="java:get($msgL10n, 'DataTypeFloat_label')" />
							</xsl:when>
							<xsl:when test="$basicType = 'BOOLEAN'">
								<xsl:value-of select="java:get($msgL10n, 'DataTypeBoolean_label')" />
							</xsl:when>
							<xsl:when test="$basicType = 'PERFORMER'">
								<xsl:value-of select="java:get($msgL10n, 'DataTypePerformer_label')" />
							</xsl:when>
							<xsl:when test="$basicType = 'DATETIME'">
								<xsl:value-of select="java:get($msgL10n, 'DataTypeDateTime_label')" />
							</xsl:when>
							<xsl:when test="$basicType = 'DATE'">
								<xsl:value-of select="java:get($msgL10n, 'DataTypeDate_label')" />
							</xsl:when>
							<xsl:when test="$basicType = 'TIME'">
								<xsl:value-of select="java:get($msgL10n, 'DataTypeTime_label')" />
							</xsl:when>
							<xsl:otherwise>
								<xsl:value-of select="$basicType" />
							</xsl:otherwise>
						</xsl:choose>
					</xsl:when>
					<xsl:otherwise>
						<xsl:variable name="tName"
							select="java:getPayloadDataExternalRefName($globalSignalInfo, $gsdProjectId, $gsdFileName, $gsdSpecialFolderRelativePath, $globalSignalName, $payloadDataId)" />
						<xsl:if test="$tName != ''">
							<xsl:value-of select="$tName" />
						</xsl:if>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>

			<xsl:choose>
				<xsl:when test="$typeName != ''">
					<xsl:copy-of select="$typeName" />
				</xsl:when>
				<xsl:otherwise>
					<i>
						<xsl:value-of select="java:get($msgL10n, 'Unspecified_label')" />
					</i>
				</xsl:otherwise>
			</xsl:choose>&nbsp;
		</td>
	</xsl:template>
	<!-- =================================================================================================================== -->
	<!-- outputDataType - End of template. -->
	<!-- =================================================================================================================== -->
</xsl:stylesheet>