<?xml version="1.0" encoding="UTF-8"?>

<!--
===================================================================================================================
XSLT:		rest2html.xsl	
Author:     kthombar
Since:      TBS 4.0.0 (04-June-2015)				

DESCRIPTION:
	This xslt will create the Procedure Documentation in HTML format from the selected package REST File.

===================================================================================================================
-->

<!-- Define &nbsp; -->
<!DOCTYPE xsl:stylesheet [
<!ENTITY nbsp  '<xsl:text disable-output-escaping="yes">&amp;nbsp;</xsl:text>'>
]>

<xsl:stylesheet version="1.0"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://www.w3.org/1999/xhtml"
	xmlns:html="http://www.w3.org/1999/xhtml" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" xmlns:rsd="http://com.tibco.xpd/rsd"
	exclude-result-prefixes="#default xsl rsd ">

	<!-- Parameters... -->
	<xsl:param name="restFileName" select="' '" />
	<xsl:param name="restFilePath" select="' '" />
	<!-- Parameters END -->


	<xsl:output encoding="UTF-8" indent="yes" method="xml"
		version="1.0" />

	<!-- Java extensions. -->
	<xsl:variable name="restInfo"
		select="java:com.tibco.xpd.rsd.doc.RestServiceToXsltGeneratorHelper.new()" />
	<xsl:variable name="msgL10n"
		select="java:com.tibco.xpd.rsd.doc.xslt.messages.RestDocMsgs.new()" />
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
			<!-- Call template to Cache the Working copy and the Project  -->
			<xsl:call-template name="cacheRestFileEditingDomainAndProject"></xsl:call-template>
			<head>
				<meta http-equiv="Content-Type" content="text/xml; charset=UTF-8" />
				<title>
					TIBCO Business Studio -
					<xsl:value-of select="$restFileName" />
				</title>
				<link rel="stylesheet" href="style/restdoc.css" type="text/css" />
				<link rel="stylesheet" href="style/printrestdoc.css" type="text/css"
					media="print" />
			</head>

			<body>
				<!-- =========================== Create the Main REST Page Header ================================== -->
				<xsl:call-template name="createRestMainHeaderTable" />

				<!-- =========================== Create the REST Resource Overview Table============================ -->
				<xsl:call-template name="createResourceOverviewTable" />
				
				<!-- =========================== Create the REST Resource Detailed Section=========================== -->
				<xsl:call-template name="createResourceDetailsSection" />
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
	<!-- Creates and outputs the REST Resource Detailed Section -->
	<!-- -->
	<!-- ===================================================================== -->
	<xsl:template name="createResourceDetailsSection">
		<xsl:for-each select="rsd:Service/resources">

			<xsl:variable name="resourceName">
				<xsl:value-of select="@name"></xsl:value-of>
			</xsl:variable>

			<div>
				<table>
					<tbody>
						<tr>
							<th class="restResourceTitle" colspan="2">
								<a id="resourceName_{$resourceName}">
									<xsl:value-of select="java:get($msgL10n, 'Type_label')" />
									<xsl:text>: </xsl:text>
									<xsl:value-of select="$resourceName" />
								</a>
							</th>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- Count the path parameters in the resource -->
			<xsl:variable name="pathParamsCount">
				<xsl:value-of select="count(parameters)"></xsl:value-of>
			</xsl:variable>

			<!-- Count the methods in the resource -->
			<xsl:variable name="methodsCount">
				<xsl:value-of select="count(methods)"></xsl:value-of>
			</xsl:variable>

			<table>
				<tbody>
					<tr>
						<th class="heading">
							<xsl:value-of select="java:get($msgL10n, 'PathTemplate_label')" />
						</th>
						<td>
							<xsl:if test="@pathTemplate">
								<xsl:value-of select="@pathTemplate"></xsl:value-of>
							</xsl:if>
						&nbsp;
						</td>
					</tr>
					<tr class="screenOnly">
						<th class="heading">
							<xsl:value-of select="java:get($msgL10n, 'QuickLinks_label')" />
						</th>
						<td>
							<a href="#pathParams_{$resourceName}">
								<xsl:value-of select="java:get($msgL10n, 'PathParams_label')" />
								<xsl:text> (</xsl:text>
								<xsl:value-of select="$pathParamsCount" />
								<xsl:text>), </xsl:text>
											&nbsp;
							</a>
							<a href="#methods_{$resourceName}">
								<xsl:value-of select="java:get($msgL10n, 'Methods_label')" />
								<xsl:text> (</xsl:text>
								<xsl:value-of select="$methodsCount" />
								<xsl:text>)</xsl:text>
											&nbsp;
							</a>
						</td>
					</tr>
				</tbody>
			</table>

			<!-- Call the template to create the Path Param table only if the resource has path params -->
			<xsl:if test="$pathParamsCount > 0">
				<xsl:call-template name="createPathParametersTable">
					<xsl:with-param name="resourceName" select="$resourceName" />
				</xsl:call-template>
			</xsl:if>

			<!-- Call the template to create the methods overview table only if the resource has methods defined -->
			<xsl:if test="$methodsCount > 0">
				<xsl:call-template name="createMethodsOverviewTable">
					<xsl:with-param name="resourceName" select="$resourceName" />
				</xsl:call-template>
				<xsl:call-template name="createMethodsDetailtedSection" />
			</xsl:if>
		</xsl:for-each>
	</xsl:template>

	<!-- ===================================================================== -->
	<!-- -->
	<!-- Creates the Overview table that lists all the methods defined in the Resource -->
	<!-- -->
	<!-- ===================================================================== -->
	<xsl:template name="createMethodsOverviewTable">
		<xsl:param name="resourceName" />

		<div class="section">
			<table>
				<thead>
					<tr>
						<th colspan="2" class="sectionTitle">
							<a id="methods_{$resourceName}">
								<xsl:value-of select="java:get($msgL10n, 'Methods_label')" />
							</a>
						</th>
					</tr>
					<tr>
						<th class="columnWidth">
							<xsl:value-of select="java:get($msgL10n, 'Name_label')" />
						</th>
						<th>
							<xsl:value-of select="java:get($msgL10n, 'HttpMethod_label')" />
						</th>
					</tr>
				</thead>
				<tbody>
					<xsl:for-each select="methods">
						<tr>
							<td>
								<a href="#methods_{@name}">
									<xsl:value-of select="@name" />&nbsp;
								</a>
							</td>
							<td>
								<xsl:choose>
									<xsl:when test="@httpMethod">
										<xsl:value-of select="@httpMethod" />&nbsp;
									</xsl:when>
									<xsl:otherwise>
										<xsl:value-of select="java:get($msgL10n, 'HttpMethodGET_label')" />
									</xsl:otherwise>
								</xsl:choose>
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
	<!-- Creates Detailed Method Section that specifies that outputs all important method related info -->
	<!-- such as, HTTP Type, Request, Response, Payloads, Faults -->
	<!-- -->
	<!-- ===================================================================== -->
	<xsl:template name="createMethodsDetailtedSection">
		<xsl:for-each select="methods">
			<div>
				<table>
					<tbody>
						<tr>
							<th class="restMethodsTitle" colspan="2">
								<a id="methods_{@name}">
									<xsl:value-of select="java:get($msgL10n, 'Method_label')" />
									<xsl:text>: </xsl:text>
									<xsl:value-of select="@name" />
								</a>
							</th>
						</tr>
					</tbody>
				</table>
			</div>
			<xsl:variable name="httpMethodType" select="@httpMethod" />

			<div class="methodSection">
				<table>
					<tbody>
						<tr>
							<th class="heading">
								<xsl:value-of select="java:get($msgL10n, 'HttpMethod_label')" />
							</th>
							<td>
								<xsl:choose>
									<xsl:when test="$httpMethodType">
										<xsl:value-of select="$httpMethodType" />
									</xsl:when>
									<xsl:otherwise>
										<xsl:value-of select="java:get($msgL10n, 'HttpMethodGET_label')" />
									</xsl:otherwise>
								</xsl:choose>&nbsp;
							</td>
						</tr>
						<tr>
							<th class="heading">
								<xsl:value-of select="java:get($msgL10n, 'Request_label')" />
							</th>
							<td>
								<!-- Create the Request Payload Table only if the HTTP type is POST or PUT -->
								<xsl:if test="$httpMethodType = 'POST' or $httpMethodType = 'PUT'">
									<!-- output the request payload info -->
									<xsl:apply-templates select="request" />									
								</xsl:if>
								
								<!-- Create the Request Parameter table(Query and Header) -->
								<xsl:call-template name="createRequestParametersTable" />
							</td>
						</tr>
						<tr>
							<th class="heading">
								<xsl:value-of select="java:get($msgL10n, 'Response_label')" />
							</th>
							<td>
								<!-- Create the Response Payload Table only if the HTTP type is POST or GET -->
								<xsl:choose>
									<xsl:when test="$httpMethodType">
										<xsl:if test="$httpMethodType = 'POST'">		
											<!-- output the response payload info for HTTP type POST -->									
											<xsl:apply-templates select="response" />
										</xsl:if>
									</xsl:when>
									<xsl:otherwise>		
										<!-- output the response payload info for HTTP type GET -->										
										<xsl:apply-templates select="response" />
									</xsl:otherwise>
								</xsl:choose>
								
								<!-- Create the Response Header Parameter table -->
								<xsl:call-template name="createResponseParametersTable" />
							</td>
						</tr>
						<tr>
							<th class="heading">
								<xsl:value-of select="java:get($msgL10n, 'Fault_label')" />
							</th>
							<td>
								<!-- Create the Faults table -->
								<xsl:call-template name="createFaultTable" />
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</xsl:for-each>
	</xsl:template>

	<!-- ===================================================================== -->
	<!-- -->
	<!-- Creates the respective Request/Response Payload Table  -->
	<!-- -->
	<!-- ===================================================================== -->
	<xsl:template match="request|response">
		<div class="innerTable">
			<table>
				<thead>
					<tr>
						<th colspan="2" class="sectionTitle">
							<xsl:value-of select="java:get($msgL10n, 'Payload_label')" />

						</th>
					</tr>
				<!-- 	<tr>
						<th class="columnWidth">
							<xsl:value-of select="java:get($msgL10n, 'Type_label')" />
						</th>
						<th>
							<xsl:value-of select="java:get($msgL10n, 'Value_label')" />
						</th>
					</tr>-->
				</thead>
				<tbody>				
					<tr>
						<th class="heading">
							<xsl:value-of select="java:get($msgL10n, 'JsonType_label')" />
						</th>
						<td>

							<xsl:if test="payloadReference/@ref and payloadReference/@location">
								<xsl:variable name="jsonReferenceName">
									<xsl:value-of
										select="java:getReferencedJsonTypeQualifiedName($restInfo,payloadReference/@ref,payloadReference/@location)" />
								</xsl:variable>

								<xsl:if test="$jsonReferenceName">
									<xsl:value-of select="$jsonReferenceName"></xsl:value-of>
								</xsl:if>

							</xsl:if>								
								&nbsp;
						</td>
					</tr>
					<tr>
						<th class="heading">
							<xsl:value-of select="java:get($msgL10n, 'Content_label')" />
						</th>
						<td>
							<xsl:choose>
								<xsl:when test="@contentType">
									<xsl:value-of select="@contentType" />&nbsp;
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="java:get($msgL10n, 'DefaultContentTypeValue')" />
								</xsl:otherwise>
							</xsl:choose>
								&nbsp;
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</xsl:template>

	<!-- ===================================================================== -->
	<!-- -->
	<!-- Create the Request Parameter table(Query and Header) -->
	<!-- -->
	<!-- ===================================================================== -->
	<xsl:template name="createRequestParametersTable">
	
		<!-- Count of all the params(Query + Header)  -->
		<xsl:variable name="allParamCount">
			<xsl:value-of select="count(request/parameters)"></xsl:value-of>
		</xsl:variable>
	
		<!-- Count of Header parameters -->
		<xsl:variable name="headerParamCount">
			<xsl:value-of select="count(request/parameters/@style)"></xsl:value-of>
		</xsl:variable>

		<div class="innerTable">
			<table>
				<thead>
					<tr>
						<th colspan="4" class="sectionTitle">
							<xsl:value-of select="java:get($msgL10n, 'QueryParameters_label')" />

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
							<xsl:value-of select="java:get($msgL10n, 'DefaultValue_label')" />
						</th>
					</tr>
				</thead>
				<tbody>
					<xsl:choose>
						<xsl:when test="$allParamCount > $headerParamCount">
							<xsl:for-each select="request/parameters">
								<xsl:choose>
									<xsl:when test="@style">
									</xsl:when>
									<xsl:otherwise>
										<tr>
											<td>
												<xsl:value-of select="@name" />&nbsp;										
											</td>
											<td>
											<!-- Output the Data Type of the Parameter -->
												<xsl:call-template name="outputDataType">
													<xsl:with-param name="dataType" select="@dataType" />
												</xsl:call-template>
												&nbsp;
											</td>
											<td>
												<xsl:if test="@mandatory">
													<xsl:if test="@mandatory = 'true'">
														<img alt="Yes" src="images/tick.gif" />
													</xsl:if>
												</xsl:if>
												&nbsp;
											</td>
											<td>
												<xsl:variable name="defaultParamValue">
													<xsl:value-of
														select="java:getParameterDefaultValue($restInfo,@dataType,@defaultValue)" />
												</xsl:variable>
										
												<xsl:if test="$defaultParamValue">								
													<xsl:value-of select="$defaultParamValue"></xsl:value-of>
												</xsl:if>															
												&nbsp;								
											</td>											
										</tr>
									</xsl:otherwise>
								</xsl:choose>
							</xsl:for-each>
						</xsl:when>
						<xsl:otherwise>
						<tr>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>	
							<td>&nbsp;</td>							
						</tr>							
					</xsl:otherwise>
					</xsl:choose>
				</tbody>
			</table>
		</div>

		<div class="innerTable">
			<table>
				<thead>
					<tr>
						<th colspan="4" class="sectionTitle">
							<xsl:value-of select="java:get($msgL10n, 'HeaderParameters_label')" />

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
							<xsl:value-of select="java:get($msgL10n, 'DefaultValue_label')" />
						</th>
					</tr>
				</thead>
				<tbody>
					<xsl:choose>
						<xsl:when test="$headerParamCount > 0">				
							<xsl:for-each select="request/parameters">
								<xsl:if test="@style = 'HEADER'">
									<tr>
										<td>
											<xsl:value-of select="@name" />&nbsp;
										</td>
										<td>
											<!-- Output the Data Type of the Parameter -->
											<xsl:call-template name="outputDataType">
												<xsl:with-param name="dataType" select="@dataType" />
											</xsl:call-template>
										&nbsp;
										</td>
										<td>
											<xsl:if test="@mandatory">
												<xsl:if test="@mandatory = 'true'">
													<img alt="Yes" src="images/tick.gif" />
												</xsl:if>
											</xsl:if>
										&nbsp;
										</td>
										<td>
											<xsl:variable name="defaultParamValue">
												<xsl:value-of
													select="java:getParameterDefaultValue($restInfo,@dataType,@defaultValue)" />
											</xsl:variable>
									
											<xsl:if test="$defaultParamValue">								
												<xsl:value-of select="$defaultParamValue"></xsl:value-of>
											</xsl:if>															
											&nbsp;								
										</td>
									</tr>
								</xsl:if>
							</xsl:for-each>
						</xsl:when>
						<xsl:otherwise>
							<tr>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>	
								<td>&nbsp;</td>							
							</tr>							
						</xsl:otherwise>						
					</xsl:choose>
				</tbody>
			</table>
		</div>
	</xsl:template>

	<!-- ===================================================================== -->
	<!-- -->
	<!-- Creates the Response Header Parameter Table -->
	<!-- -->
	<!-- ===================================================================== -->
	<xsl:template name="createResponseParametersTable">
	
		<xsl:variable name="headerParamCount">
			<xsl:value-of select="count(response/parameters)"></xsl:value-of>
		</xsl:variable>
		
		<div class="innerTable">
			<table>
				<thead>
					<tr>
						<th colspan="4" class="sectionTitle">
							<xsl:value-of select="java:get($msgL10n, 'HeaderParameters_label')" />

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
							<xsl:value-of select="java:get($msgL10n, 'DefaultValue_label')" />
						</th>
					</tr>
				</thead>
				<tbody>
				<xsl:choose>
					<xsl:when test="$headerParamCount > 0">
						<xsl:for-each select="response/parameters">
							<tr>
								<td>
									<xsl:value-of select="@name" />&nbsp;
								</td>
								<td>
									<!-- Output the Data Type of the Parameter -->
									<xsl:call-template name="outputDataType">
										<xsl:with-param name="dataType" select="@dataType" />
									</xsl:call-template>
									&nbsp;
								</td>
								<td>
									<xsl:if test="@mandatory">
										<xsl:if test="@mandatory = 'true'">
											<img alt="Yes" src="images/tick.gif" />
										</xsl:if>
									</xsl:if>
									&nbsp;
								</td>
								<td>
									<xsl:variable name="defaultParamValue">
										<xsl:value-of
											select="java:getParameterDefaultValue($restInfo,@dataType,@defaultValue)" />
									</xsl:variable>
									
									<xsl:if test="$defaultParamValue">								
										<xsl:value-of select="$defaultParamValue"></xsl:value-of>
									</xsl:if>															
									&nbsp;								
								</td>
							</tr>
						</xsl:for-each>
					</xsl:when>
					<xsl:otherwise>
						<tr>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>							
						</tr>							
					</xsl:otherwise>
				</xsl:choose>
				</tbody>
			</table>
		</div>
	</xsl:template>

	<!-- ===================================================================== -->
	<!-- -->
	<!-- Creates the Faults table for the methods -->
	<!-- -->
	<!-- ===================================================================== -->
	<xsl:template name="createFaultTable">

		<xsl:variable name="faultCount">
			<xsl:value-of select="count(faults)"></xsl:value-of>
		</xsl:variable>
					
			<div class="innerTable">
				<table>
					<thead>
						<tr>
							<th class="columnWidth">
								<xsl:value-of select="java:get($msgL10n, 'Name_label')" />
							</th>
							<th>
								<xsl:value-of select="java:get($msgL10n, 'StatusCode_label')" />
							</th>
							<th>
								<xsl:value-of select="java:get($msgL10n, 'JsonType_label')" />
							</th>	
							<th>
								<xsl:value-of select="java:get($msgL10n, 'Content_label')" />
							</th>						
						</tr>
					</thead>
					<tbody>
						
						<xsl:choose>
							<xsl:when test="$faultCount > 0">
								<xsl:for-each select="faults">
									<tr>
										<td>
											<xsl:value-of select="@name" />&nbsp;
										</td>
										<td>
											<xsl:if test="@statusCode">
												<xsl:value-of select="@statusCode"></xsl:value-of>
											</xsl:if>									
											&nbsp;
										</td>
										<td>
											<xsl:if test="payloadReference/@ref and payloadReference/@location">
											<!-- Get the Qualified name of the referenced JSON Schema -->
												<xsl:variable name="jsonReferenceName">
													<xsl:value-of
														select="java:getReferencedJsonTypeQualifiedName($restInfo,payloadReference/@ref,payloadReference/@location)" />
												</xsl:variable>
	
											<xsl:if test="$jsonReferenceName">
												<xsl:value-of select="$jsonReferenceName"></xsl:value-of>
											</xsl:if>
										</xsl:if>																			
										&nbsp;
										</td>
										<td>
											<xsl:if test="@contentType">
												<xsl:value-of select="@contentType"></xsl:value-of>
											</xsl:if>									
											&nbsp;
										</td>								
									</tr>
								</xsl:for-each>
							</xsl:when>
							<xsl:otherwise>
							<tr>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
							</tr>							
							</xsl:otherwise>
						</xsl:choose>					
					</tbody>
				</table>
			</div>		
	</xsl:template>

	<!-- ===================================================================== -->
	<!-- -->
	<!-- Creates the Resource Path Parameter Table -->
	<!-- -->
	<!-- ===================================================================== -->
	<xsl:template name="createPathParametersTable">
		<xsl:param name="resourceName" />
		<div class="section">
			<table>
				<thead>
					<tr>
						<th colspan="3" class="sectionTitle">
							<a id="pathParams_{$resourceName}">
								<xsl:value-of select="java:get($msgL10n, 'PathParams_label')" />
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
							<xsl:value-of select="java:get($msgL10n, 'DefaultValue_label')" />
						</th>
					</tr>
				</thead>
				<tbody>
					<xsl:for-each select="parameters">
						<tr>
							<td>								
									<xsl:value-of select="@name" />&nbsp;
							</td>
							<td>
								<xsl:call-template name="outputDataType">
									<xsl:with-param name="dataType" select="@dataType" />
								</xsl:call-template>							
								&nbsp;
							</td>
							<td>
								<xsl:variable name="defaultParamValue">
									<xsl:value-of
										select="java:getParameterDefaultValue($restInfo,@dataType,@defaultValue)" />
								</xsl:variable>
								
								<xsl:if test="$defaultParamValue">								
									<xsl:value-of select="$defaultParamValue"></xsl:value-of>
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
	<!-- Outputs the Human Readable Data Type based upon the passed data type -->
	<!-- -->
	<!-- ===================================================================== -->
	<xsl:template name="outputDataType">
		<xsl:param name="dataType"></xsl:param>
		<xsl:choose>
			<xsl:when test="$dataType != ''">
				<xsl:choose>
					<xsl:when test="$dataType = 'STRING'">
						<xsl:value-of select="java:get($msgL10n, 'DataTypeString_label')" />
					</xsl:when>
					<xsl:when test="$dataType = 'INTEGER'">
						<xsl:value-of select="java:get($msgL10n, 'DataTypeInteger_label')" />
					</xsl:when>
					<xsl:when test="$dataType = 'FLOAT'">
						<xsl:value-of select="java:get($msgL10n, 'DataTypeFloat_label')" />
					</xsl:when>
					<xsl:when test="$dataType = 'BOOLEAN'">
						<xsl:value-of select="java:get($msgL10n, 'DataTypeBoolean_label')" />
					</xsl:when>
					<xsl:when test="$dataType = 'DATE_TIME'">
						<xsl:value-of select="java:get($msgL10n, 'DataTypeDateTime_label')" />
					</xsl:when>
					<xsl:when test="$dataType = 'DATE'">
						<xsl:value-of select="java:get($msgL10n, 'DataTypeDate_label')" />
					</xsl:when>
					<xsl:when test="$dataType = 'TIME'">
						<xsl:value-of select="java:get($msgL10n, 'DataTypeTime_label')" />
					</xsl:when>
					<xsl:when test="$dataType = 'DECIMAL'">
						<xsl:value-of select="java:get($msgL10n, 'DataTypeFloat_label')" />
					</xsl:when>
				</xsl:choose>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="java:get($msgL10n, 'DataTypeString_label')" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<!-- ===================================================================== -->
	<!-- -->
	<!-- Creates the Resource Overview Table -->
	<!-- -->
	<!-- ===================================================================== -->
	<xsl:template name="createResourceOverviewTable">
		<div class="section">
			<table>
				<thead>
					<tr>
						<th colspan="2" class="sectionTitle">
							<a id="resourcesOveriewTable">
								<xsl:value-of select="java:get($msgL10n, 'Resources_label')" />
							</a>
						</th>
					</tr>
					<tr>
						<th class="columnWidth">
							<xsl:value-of select="java:get($msgL10n, 'Name_label')" />
						</th>
						<th>
							<xsl:value-of select="java:get($msgL10n, 'PathTemplate_label')" />
						</th>
					</tr>
				</thead>
				<tbody>
					<xsl:for-each select="rsd:Service/resources">
						<tr>
							<td>
								<a href="#resourceName_{@name}">
									<xsl:value-of select="@name" />&nbsp;
								</a>
							</td>
							<td>
								<xsl:if test="@pathTemplate">
									<xsl:value-of select="@pathTemplate"></xsl:value-of>
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
	<!-- Creates the Main Header Table for REST Doc -->
	<!-- -->
	<!-- ===================================================================== -->
	<xsl:template name="createRestMainHeaderTable">
		<table class="mainTitle">
			<tbody>
				<tr>
					<td class="noBorder mainTitleCol1">
						<xsl:value-of select="java:get($msgL10n, 'RestServiceDescriptor_label')" />
									&nbsp;
					</td>
					<td class="noBorder mainTitle">
						<xsl:value-of select="$restFileName" />&nbsp;
					</td>
					<td class="noBorder mainTitle" align="right">
						<img src="images/Logo.png" alt="TIBCO Software Inc." />&nbsp;
					</td>
				</tr>
			</tbody>
		</table>
		<xsl:variable name="resourceCount">
			<xsl:value-of select="count(rsd:Service/resources)"></xsl:value-of>
		</xsl:variable>
		<table>
			<tbody>
				<tr>
					<th class="heading">
						<xsl:value-of select="java:get($msgL10n, 'ServiceName_label')" />
					</th>
					<td>

						<xsl:value-of select="rsd:Service/@name" />&nbsp;
					</td>
				</tr>
				<tr>
					<th class="heading">
						<xsl:value-of select="java:get($msgL10n, 'ContextPath_label')" />
					</th>
					<td>
						<xsl:value-of select="rsd:Service/@contextPath" />&nbsp;
					</td>
				</tr>
				<tr>
					<th class="heading">
						<xsl:value-of select="java:get($msgL10n, 'MediaType_label')" />
					</th>
					<td>
						<xsl:value-of select="java:get($msgL10n, 'StandardJsonMediaType_label')" />
					</td>
				</tr>
				<tr class="screenOnly">
					<th class="heading">
						<xsl:value-of select="java:get($msgL10n, 'QuickLinks_label')" />
					</th>
					<td>
						<a href="#resourcesOveriewTable">
							<xsl:value-of select="java:get($msgL10n, 'Resources_label')" />
							<xsl:text> (</xsl:text>
							<xsl:value-of select="$resourceCount"></xsl:value-of>
							<xsl:text>)</xsl:text>
									&nbsp;
						</a>
					</td>
				</tr>
			</tbody>
		</table>
	</xsl:template>

	<!-- ===================================================================== -->
	<!-- -->
	<!-- Caches the Rest File Editing Domain and Parent Project so that 'RestServiceToXsltGeneratorHelper' can use it again and again -->
	<!-- -->
	<!-- ===================================================================== -->
	<xsl:template name="cacheRestFileEditingDomainAndProject">

		<xsl:value-of
			select="java:cacheRestFileEditingDomainAndProject($restInfo,$restFilePath)"></xsl:value-of>

	</xsl:template>

</xsl:stylesheet>