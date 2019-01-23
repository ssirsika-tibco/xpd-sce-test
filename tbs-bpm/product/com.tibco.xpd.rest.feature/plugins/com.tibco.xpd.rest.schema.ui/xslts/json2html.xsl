<?xml version="1.0" encoding="UTF-8"?>
<!--
===================================================================================================================
XSLT:		json2html.xsl	
Author:     kthombar
Since:      TBS 4.0.0 (04-June-2015)				

DESCRIPTION:
	This xslt will create the Procedure Documentation in HTML format from the selected package JSON File.

===================================================================================================================
-->
<!-- Define &nbsp; -->
<!DOCTYPE xsl:stylesheet [
<!ENTITY nbsp  '<xsl:text disable-output-escaping="yes">&amp;nbsp;</xsl:text>'>
]>

<xsl:stylesheet version="1.0"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://www.w3.org/1999/xhtml"
	xmlns:html="http://www.w3.org/1999/xhtml" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" xmlns:uml="http://www.eclipse.org/uml2/5.0.0/UML"
	exclude-result-prefixes="#default xsl uml ">

	<!-- Parameters... -->
	<xsl:param name="jsonFileName" select="' '" />
	<xsl:param name="jsdFilePath" select="' '" />
	<!-- Parameters END -->

	<xsl:output encoding="UTF-8" indent="yes" method="xml"
		version="1.0" />

	<!-- Java extensions. -->
	<xsl:variable name="jsdInfo"
		select="java:com.tibco.xpd.rest.json.schema.doc.JsonToXsltGeneratorHelper.new()" />
	<xsl:variable name="msgL10n"
		select="java:com.tibco.xpd.rest.json.schema.doc.xslt.messages.JsonDocMsgs.new()" />
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
		<!-- =========================== Cache the Schema Properties  ================================== -->
			<xsl:call-template name="cacheSchemaProperties"></xsl:call-template>
			<head>
				<meta http-equiv="Content-Type" content="text/xml; charset=UTF-8" />
				<title>
					TIBCO Business Studio -
					<xsl:value-of select="$jsonFileName" />
				</title>
				<link rel="stylesheet" href="style/jsondoc.css" type="text/css" />
				<link rel="stylesheet" href="style/printjsondoc.css" type="text/css"
					media="print" />
			</head>
			<body>
				<!-- =========================== Create the Main JSON Page Header ============================ -->
				<xsl:call-template name="createJsonMainHeaderTable" />
				
				<!-- =========================== Create the JSON Type Table ================================== -->
				<xsl:call-template name="createJsonTypesTable" />
				
				<!-- =========================== Create the JSON Type Detailed Section========================= -->
				<xsl:call-template name="createJsonTypeSection" />
			</body>
		</xsl:element>
	</xsl:template>
	<!-- ===================================================================== -->
	<!-- -->
	<!-- END XSLT PROCESSING START POINT (PROCESS WHOLE DOCUMENT). -->
	<!-- -->
	<!-- ===================================================================== -->

	<!-- ===================================================================== -->
	<!-- -->
	<!-- Caches the Schema Properties in the Helper class 'JsonToXsltGeneratorHelper' -->
	<!-- -->
	<!-- ===================================================================== -->
	<xsl:template name="cacheSchemaProperties">

		<xsl:value-of select="java:cacheSchemaProperties($jsdInfo,$jsdFilePath)"></xsl:value-of>

	</xsl:template>
	
	<!-- ===================================================================== -->
	<!-- -->
	<!-- Creates the Detailed JSON Type Section -->
	<!-- -->
	<!-- ===================================================================== -->
	<xsl:template name="createJsonTypeSection">
		<xsl:for-each select="uml:Package/packagedElement">

			<xsl:variable name="jsonTypeName">
				<xsl:value-of select="@name"></xsl:value-of>
			</xsl:variable>

			<div>
				<table>
					<tbody>
						<tr>
							<th class="jsonTypeTitle" colspan="2">
								<a id="jsonType_{$jsonTypeName}">
									<xsl:value-of select="java:get($msgL10n, 'Type_label')" />
									<xsl:text>: </xsl:text>
									<xsl:value-of select="$jsonTypeName" />
								</a>
							</th>
						</tr>
					</tbody>
				</table>
			</div>

			<xsl:variable name="childPropertyCount">
				<xsl:value-of select="count(ownedAttribute)"></xsl:value-of>
			</xsl:variable>

			<table>
				<tbody>
					<tr class="screenOnly">
						<th class="heading">
							<xsl:value-of select="java:get($msgL10n, 'QuickLinks_label')" />
						</th>
						<td>
							<a href="#property_{@name}">
								<xsl:value-of select="java:get($msgL10n, 'SchemaProperties_label')" />
								<xsl:text> (</xsl:text>
								<xsl:value-of select="$childPropertyCount" />
								<xsl:text>)</xsl:text>
											&nbsp;
							</a>
						</td>
					</tr>
				</tbody>
			</table>

			<xsl:if test="$childPropertyCount > 0">
				<xsl:call-template name="createJsonPropertiesTable">
					<xsl:with-param name="jsonTypeName" select="$jsonTypeName" />
				</xsl:call-template>
			</xsl:if>
		</xsl:for-each>
	</xsl:template>

	<!-- ===================================================================== -->
	<!-- -->
	<!-- Create the JSON Properties Table -->
	<!-- -->
	<!-- ===================================================================== -->
	<xsl:template name="createJsonPropertiesTable">
		<xsl:param name="jsonTypeName"></xsl:param>
		<div class="section">
			<table>
				<thead>
					<tr>
						<th colspan="4" class="sectionTitle">
							<a id="property_{$jsonTypeName}">
								<xsl:value-of select="java:get($msgL10n, 'SchemaProperties_label')" />
							</a>
						</th>
					</tr>
					<tr>
						<th class="columnWidth">
							<xsl:value-of select="java:get($msgL10n, 'Name_label')" />
						</th>
						<th>
							<xsl:value-of select="java:get($msgL10n, 'Type_label')" />
						</th>
						<th>
							<xsl:value-of select="java:get($msgL10n, 'Mandatory_label')" />
						</th>
						<th>
							<xsl:value-of select="java:get($msgL10n, 'Array_label')" />
						</th>
					</tr>
				</thead>
				<tbody>
					<xsl:for-each select="ownedAttribute">
						<tr>
							<td>
								<xsl:value-of select="@name" />&nbsp;
							</td>
							<td>
								<xsl:variable name="basicType"
									select="java:getSchemaPropertyDataType($jsdInfo,$jsonTypeName,@name)" />

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
												<xsl:when test="$basicType = 'DATETIME'">
													<xsl:value-of
														select="java:get($msgL10n, 'DataTypeDateTime_label')" />
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
								</xsl:choose>
								&nbsp;
							</td>
							<td>
								<xsl:choose>
									<xsl:when test="lowerValue">

										<xsl:if test="lowerValue/@value = '1'">
											<img alt="Yes" src="images/tick.gif" />
										</xsl:if>

									</xsl:when>
									<xsl:otherwise>
										<img alt="Yes" src="images/tick.gif" />
									</xsl:otherwise>
								</xsl:choose>
								
								&nbsp;
							</td>
							<td>
								<xsl:if test="upperValue">
									<xsl:if test="upperValue/@value = '*'">
										<img alt="Yes" src="images/tick.gif" />
									</xsl:if>
								</xsl:if>								
								&nbsp;
							</td>
						</tr>
					</xsl:for-each>
				</tbody>
			</table>
		</div>

	</xsl:template>

	<!-- ===================================================================== -->
	<!-- -->
	<!-- Creates the JSON Type Overview Table -->
	<!-- -->
	<!-- ===================================================================== -->
	<xsl:template name="createJsonTypesTable">
		<div class="section">
			<table>
				<thead>
					<tr>
						<th colspan="2" class="sectionTitle">
							<a id="jsonSchemaTypes">
								<xsl:value-of select="java:get($msgL10n, 'JSONType_label')" />
							</a>
						</th>
					</tr>
					<tr>
						<th class="columnWidth">
							<xsl:value-of select="java:get($msgL10n, 'Name_label')" />
						</th>
						<th>
							<xsl:value-of select="java:get($msgL10n, 'RootJsonSchema_label')" />
						</th>
					</tr>
				</thead>
				<tbody>
					<xsl:for-each select="uml:Package/packagedElement">
						<tr>
							<td>
								<a href="#jsonType_{@name}">
									<xsl:value-of select="@name" />&nbsp;
								</a>
							</td>
							<td>
								<xsl:if test="eAnnotations/@source ='root'">
									<img alt="Yes" src="images/tick.gif" />
								</xsl:if>								
								&nbsp;
							</td>
						</tr>
					</xsl:for-each>
				</tbody>
			</table>
		</div>
	</xsl:template>

	<!-- ===================================================================== -->
	<!-- -->
	<!-- Creates the Main header table for JSON Documentation -->
	<!-- -->
	<!-- ===================================================================== -->
	<xsl:template name="createJsonMainHeaderTable">
		<table class="mainTitle">
			<tbody>
				<tr>
					<td class="noBorder mainTitleCol1">
						<xsl:value-of select="java:get($msgL10n, 'JsonSchema_label')" />
									&nbsp;
					</td>
					<td class="noBorder mainTitle">
						<xsl:value-of select="$jsonFileName" />&nbsp;
					</td>
					<td class="noBorder mainTitle" align="right">
						<img src="images/Logo.png" alt="TIBCO Software Inc." />&nbsp;
					</td>
				</tr>
			</tbody>
		</table>
		<xsl:variable name="jsonTypesCount">
			<xsl:value-of select="count(uml:Package/packagedElement)"></xsl:value-of>
		</xsl:variable>
		<table>
			<tbody>
				<tr class="screenOnly">
					<th class="heading">
						<xsl:value-of select="java:get($msgL10n, 'QuickLinks_label')" />
					</th>
					<td>
						<a href="#jsonSchemaTypes">
							<xsl:value-of select="java:get($msgL10n, 'JSONType_label')" />
							<xsl:text> (</xsl:text>
							<xsl:value-of select="$jsonTypesCount"></xsl:value-of>
							<xsl:text>)</xsl:text>
									&nbsp;
						</a>
					</td>
				</tr>
			</tbody>
		</table>
	</xsl:template>
</xsl:stylesheet>