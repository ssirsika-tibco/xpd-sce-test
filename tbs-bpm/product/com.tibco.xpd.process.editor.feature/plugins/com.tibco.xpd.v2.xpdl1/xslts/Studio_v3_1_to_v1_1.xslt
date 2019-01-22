<?xml version="1.0" encoding="UTF-8"?>
<!--
=============================================================================================================

	MODULE:		Studio_v2_0_to_v1_1.xslt

							Author:			Sid Allway
							Created:		2006/11/23
							Copyright:		(c) 2006 - 2008 Tibco Software Inc.

	DESCRIPTION:
		XSLT for transformation of Tibco iProcess Studio v2.0 XPDL(v2) to Tibco v1.1 XPDL(v1)

=============================================================================================================
-->
<xsl:stylesheet version="1.0" 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"

	xmlns:xpdl2="http://www.wfmc.org/2008/XPDL2.1"
    xmlns:xpdExt="http://www.tibco.com/XPD/xpdExtension1.0.0"
	xmlns:iProcessExt="http://www.tibco.com/XPD/iProcessExt1.0.0" 
	
	xmlns:xpdl="http://www.wfmc.org/2002/XPDL1.0" 
	xmlns:extensions="http://www.tibco.com/xpd/XpdlExtensions1.0.0"
	xmlns:simulation="http://www.tibco.com/xpd/Simulation1.0.1"
	
	xmlns:java="http://xml.apache.org/xslt/java"

	exclude-result-prefixes="xpdl2 xpdExt"
	>

<xsl:param name="transformMonitorId"/>
	
<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>

<!-- Characters for upper/lower case conversions... -->
<xsl:variable name="upCaseChars" select="'ABCDEFGHIJKLMNOPQRSTUVWXYZ'"/>
<xsl:variable name="lowCaseChars" select="'abcdefghijklmnopqrstuvwxyz'"/>
<xsl:variable name="numbers" select="'0123456789'"/>

<!-- This is the v2 to v1 java extension util class instance. -->
<xsl:variable name="javaUtil" select="java:com.tibco.xpd.v2.xpdl1.util.V2ToV1Util.new()"/>

<!--
============================================
Root Element:
============================================
-->
<xsl:template match="/">
	<xsl:apply-templates/>
</xsl:template>

<!--
============================================
Package
============================================
-->
<xsl:template match="xpdl2:Package">
<!--<xsl:message>V2 to V1 Package: <xsl:value-of select="@Name"/></xsl:message>-->
<xsl:variable name="start" select="java:com.tibco.xpd.v2.xpdl1.util.V2ToV1Util.startTime()"/>

	<xpdl:Package Id="{@Id}" Name="{@Name}">
		<xsl:attribute name="xsi:schemaLocation">http://www.wfmc.org/2002/XPDL1.0 http://wfmc.org/standards/docs/TC-1025_schema_10_xpdl.xsd</xsl:attribute>
		<xsl:copy-of select="@xpdExt:DisplayName"/>

		<!-- Currently v2 not saving in correct sequence, simple to rectify here.... -->
		<xsl:apply-templates select="xpdl2:PackageHeader"/>
		<xsl:apply-templates select="xpdl2:RedefinableHeader"/>
		<xsl:apply-templates select="xpdl2:ConformanceClass"/>
		<xsl:apply-templates select="xpdl2:Script"/>
		<xsl:apply-templates select="xpdl2:ExternalPackages"/>
		<xsl:apply-templates select="xpdl2:TypeDeclarations"/>
		<xsl:apply-templates select="xpdl2:Participants"/>
		<xsl:apply-templates select="xpdl2:Applications"/>
		<xsl:apply-templates select="xpdl2:DataFields"/>
		<xsl:apply-templates select="xpdl2:WorkflowProcesses"/>
		<xsl:apply-templates select="xpdl2:ExtendedAttributes"/>
	
	</xpdl:Package>
<!--<xsl:message><xsl:value-of select="java:com.tibco.xpd.v2.xpdl1.util.V2ToV1Util.endTime($start, '0', 'V2 To V1 Whole Package', '0')"/></xsl:message>-->
	
</xsl:template>

<!--
============================================
Package Extended Attributes
============================================
-->
<xsl:template match="xpdl2:Package/xpdl2:ExtendedAttributes">

	<xpdl:ExtendedAttributes>
	
		<!-- 
			If there are process interfaces defined then pass them down to v1 xpdl for proc docs / ipm etc. 
		-->
		<xsl:if test="/xpdl2:Package/xpdExt:ProcessInterfaces/xpdExt:ProcessInterface">

			<xsl:call-template name="HashOtherToExtAttr">
				<xsl:with-param name="input" select="/xpdl2:Package/xpdExt:ProcessInterfaces"/>
				<xsl:with-param name="outputXpdl2Nodes" select="'true'"/>
			</xsl:call-template>

			<!-- XPD-214 BEGIN - Need to put extra info into extended attributes for each process interface formal parameter -->
			<xsl:for-each select="/xpdl2:Package/xpdExt:ProcessInterfaces/xpdExt:ProcessInterface">
				<xsl:for-each select="xpdl2:FormalParameters/xpdl2:FormalParameter">
					<xsl:variable name="param" select="."/>
					
					<xpdl:ExtendedAttribute Name="v2XPD_InterfaceFormalParamExtra" Value="{$param/@Id}">
				
						<xsl:call-template name="OutputFormalParamExtraDetails">
							<xsl:with-param name="param" select="$param"/>
						</xsl:call-template>
						
					</xpdl:ExtendedAttribute>

				</xsl:for-each>
			</xsl:for-each>
			<!-- XPD-214 END-->

		</xsl:if>

		<!-- Then deal with the normal attributes. -->
		<xsl:apply-templates select="xpdl2:ExtendedAttribute"/>
		
	</xpdl:ExtendedAttributes>

</xsl:template>

<!--
============================================
Replace some kinds of Package Extended Attributes
============================================
-->
<xsl:template match="xpdl2:Package/xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute">

	<xsl:choose>
		<!-- Override created by and format version and copy the rest -->
		<xsl:when test="@Name = 'CreatedBy'">
			<xpdl:ExtendedAttribute Name="CreatedBy" Value="TIBCO Business Studio"/>
		</xsl:when>
		<xsl:when test="@Name = 'FormatVersion'">
			<xpdl:ExtendedAttribute Name="FormatVersion" Value="2"/>
		</xsl:when>
		<xsl:otherwise>
			<xpdl:ExtendedAttribute>
				<xsl:apply-templates select="@* | node()"/>
			</xpdl:ExtendedAttribute>
		</xsl:otherwise>
	</xsl:choose>

</xsl:template>



<!--
============================================
PackageHeader - Pick up create date from xpdl2
============================================
-->
<xsl:template match="xpdl2:PackageHeader">
  <xpdl:PackageHeader>
    <xpdl:XPDLVersion>1.0</xpdl:XPDLVersion>
    <xpdl:Vendor>TIBCO</xpdl:Vendor>
    <xsl:apply-templates select="xpdl2:Created"/>
  </xpdl:PackageHeader>
</xsl:template>

<!--
============================================
Output a data field to v1 with extra info from v2
============================================
-->
<xsl:template match="xpdl2:DataField">
	<xpdl:DataField Id="{@Id}" Name="{@Name}">
		<xsl:choose>
			<xsl:when test="@IsArray ='true' or @IsArray = 'TRUE'">
				<xsl:attribute name="IsArray">TRUE</xsl:attribute>
			</xsl:when>
			<xsl:when test="@IsArray ='false' or @IsArray = 'FALSE'">
				<xsl:attribute name="IsArray">FALSE</xsl:attribute>
			</xsl:when>
		</xsl:choose>

		<xsl:apply-templates select="@ReadOnly"/>
		<xsl:copy-of select="@xpdExt:DisplayName"/>

		<xsl:apply-templates select="*"/>
		
		<!-- Add extended attrs to carry length, precision and scale from v2 model. -->
		
		
		<!-- get basic type info from either the field itself or the type declaration of a typed field. format: "BasicTypeType;length;precision;scale" -->
		<xsl:variable name="basicTypeInfo" select="java:resolveBasicTypeInfo($javaUtil, /xpdl2:Package/@Id, @Id)"/>
		
		<xsl:variable name="basicType" select="substring-before($basicTypeInfo, ';')"/> <!-- XPD-214 just for consistency with params - not used -->
		
		<xsl:variable name="str1" select="substring-after($basicTypeInfo, ';')"/>
		<xsl:variable name="length" select="substring-before($str1, ';')"/>

		<xsl:variable name="str2" select="substring-after($str1, ';')"/>
		<xsl:variable name="precision" select="substring-before($str2, ';')"/>

		<xsl:variable name="scale" select="substring-after($str2, ';')"/>
		
		<xsl:if test="$length != '' or $precision != '' or $scale != ''">
			<xpdl:ExtendedAttributes>
				<xpdl:ExtendedAttribute Name="v2XPD_DataFieldExtra">
					<details>
						<xsl:attribute name="OriginalName"><xsl:value-of select="@Name"/></xsl:attribute>
						<xsl:attribute name="BaseDataType"><xsl:value-of select="$basicType"/></xsl:attribute>
					
						<xsl:if test="$length != ''">
							<length><xsl:value-of select="$length"/></length>
						</xsl:if>
						<xsl:if test="$precision != ''">
							<precision><xsl:value-of select="$precision"/></precision>
						</xsl:if>
						<xsl:if test="$scale != ''">
							<scale><xsl:value-of select="$scale"/></scale>
						</xsl:if>
					</details>
				</xpdl:ExtendedAttribute>
			</xpdl:ExtendedAttributes>
		</xsl:if>
		
	</xpdl:DataField>
</xsl:template>

<!--
============================================
DataType (resolve declared type / ext reference) down to basic type
i.e. convert dtatype/declared type and ext ref to basic types.
============================================
-->
<xsl:template match="xpdl2:DataType">
	<!-- get basic type info from either the field itself or the type declaration of a typed field. format: "BasicTypeType;length;precision;scale" -->
	<xsl:variable name="fieldOrParamId" select="../@Id"/>
	
	<xsl:variable name="basicTypeInfo" select="java:resolveBasicTypeInfo($javaUtil, ./ancestor::xpdl2:Package/@Id, $fieldOrParamId)"/>
	<xsl:variable name="type" select="substring-before($basicTypeInfo, ';')"/>
	
	<xpdl:DataType>
		<xpdl:BasicType Type="{$type}"/>
	</xpdl:DataType>
	
</xsl:template>


<!--
============================================
Handle WorkflowProcess
============================================
-->
<xsl:template match="xpdl2:WorkflowProcess">

	<!-- Show progress on each Process Interface / Process that is done. -->
	<xsl:variable name="prcName">
		<xsl:choose>
			<xsl:when test="@xpdExt:DisplayName != ''">v1 <xsl:value-of select="@xpdExt:DisplayName"/></xsl:when>
			<xsl:otherwise>v1 <xsl:value-of select="@Name"/></xsl:otherwise>
		</xsl:choose>
	</xsl:variable>
	<xsl:value-of select="java:com.tibco.xpd.ui.importexport.utils.ImportExportTransformer.monitorStartSubTask($transformMonitorId, $prcName)"/>	

	<xpdl:WorkflowProcess Id="{@Id}" Name="{@Name}">
		<xsl:copy-of select="@AccessLevel"/>
		<xsl:copy-of select="@xpdExt:DisplayName"/>
		
		<!-- We can make life A LOT easier for things handling v1 xpdl after us by 'pretending' that any interface parameters we inherit are part of our own. (and for ourselves here too). -->
		<xsl:variable name="procParams" select="xpdl2:FormalParameters/xpdl2:FormalParameter"/>
		<xsl:variable name="interfaceId" select="xpdExt:ImplementedInterface/@xpdExt:ProcessInterfaceId"/>

		<!-- BUT WE HAVE TO COPE WITH INTERFACE FROM A DIFFERENT PACKAGE! -->
		<xsl:variable name="v2IfcPackage" select="java:getInterfacePackage($javaUtil, $interfaceId)"/>
		<xsl:variable name="processInterface" select="$v2IfcPackage/xpdExt:ProcessInterfaces/xpdExt:ProcessInterface[@Id = $interfaceId]"/>
		<xsl:variable name="interfaceParams" select="$processInterface/xpdl2:FormalParameters/xpdl2:FormalParameter"/>
	
		<xsl:variable name="allFormalParams" select="$procParams | $interfaceParams"/>
		
		<!-- Xpdl 2 changed order of participants, datafields and applications -->
		<xsl:apply-templates select="xpdl2:ProcessHeader">
			<xsl:with-param name="allFormalParams" select="$allFormalParams"/>
		</xsl:apply-templates>
		<xsl:apply-templates select="xpdl2:Redefinable">
			<xsl:with-param name="allFormalParams" select="$allFormalParams"/>
		</xsl:apply-templates>
		<xsl:call-template name="OutputFormalParameters">
			<xsl:with-param name="allFormalParams" select="$allFormalParams"/>
		</xsl:call-template>
		<xsl:apply-templates select="xpdl2:DataFields">
			<xsl:with-param name="allFormalParams" select="$allFormalParams"/>
		</xsl:apply-templates>
		<xsl:apply-templates select="xpdl2:Participants">
			<xsl:with-param name="allFormalParams" select="$allFormalParams"/>
		</xsl:apply-templates>
		<xsl:apply-templates select="xpdl2:Applications">
			<xsl:with-param name="allFormalParams" select="$allFormalParams"/>
		</xsl:apply-templates>
		<xsl:apply-templates select="xpdl2:ActivitySets">
			<xsl:with-param name="allFormalParams" select="$allFormalParams"/>
		</xsl:apply-templates>
		<xsl:apply-templates select="xpdl2:Activities">
			<xsl:with-param name="allFormalParams" select="$allFormalParams"/>
		</xsl:apply-templates>
		<xsl:apply-templates select="xpdl2:Transitions">
			<xsl:with-param name="allFormalParams" select="$allFormalParams"/>
		</xsl:apply-templates>
		
		<xpdl:ExtendedAttributes>
			<!--
				Pool and lanes - validation should prevent multi-pools, just in case we'll pick the first.
			-->
			<xsl:variable name="procId" select="@Id"/>
			
			<xsl:variable name="pools" select="/xpdl2:Package/xpdl2:Pools/xpdl2:Pool[@Process = $procId]"/>
			
			<xpdl:ExtendedAttribute Name="XPD">
				<extensions:Diagram>
					<extensions:ZoomLevel>1.0</extensions:ZoomLevel>
					<extensions:Pool Id="{$pools[1]/@Id}">
					
						<extensions:Name><xsl:value-of select="$pools[1]/@Name"/></extensions:Name>
						<extensions:IsVisible>true</extensions:IsVisible>
						<extensions:IsDefault>true</extensions:IsDefault>
	
						<xsl:for-each select="$pools/xpdl2:Lanes/xpdl2:Lane">
							<xsl:variable name="laneId" select="@Id"/>
							<extensions:Lane Id="{$laneId}">
								<extensions:Name><xsl:value-of select="@Name"/></extensions:Name>
								
								<!-- Grab diagram notes... -->
								<xsl:variable name="notes" select="/xpdl2:Package/xpdl2:Artifacts/xpdl2:Artifact[@ArtifactType = 'Annotation']"/>
								
								<xsl:for-each select="$notes">
									<xsl:variable name="gi" select="xpdl2:NodeGraphicsInfos/xpdl2:NodeGraphicsInfo[@ToolId = 'XPD' or @ToolId = '' or not(@ToolId)]"/>
									
									<xsl:if test="$gi/@LaneId = $laneId">
										<extensions:Note Id="{@Id}">
											<extensions:Location>
												<extensions:XOffset><xsl:value-of select="round($gi/xpdl2:Coordinates/@XCoordinate)"/></extensions:XOffset>
												<extensions:YOffset><xsl:value-of select="round($gi/xpdl2:Coordinates/@YCoordinate)"/></extensions:YOffset>
												<extensions:Width>100</extensions:Width>
												<extensions:Height>20</extensions:Height>
											</extensions:Location>
											<extensions:Text><xsl:value-of select="@TextAnnotation"/></extensions:Text>
										</extensions:Note>
									</xsl:if>
								</xsl:for-each>
								
								<extensions:IsDefault>true</extensions:IsDefault>
								<extensions:Size><xsl:value-of select="round(xpdl2:NodeGraphicsInfos/xpdl2:NodeGraphicsInfo[@ToolId = 'XPD' or @ToolId = '' or not(@ToolId)]/@Height)"/></extensions:Size>
							</extensions:Lane>
						</xsl:for-each>

					</extensions:Pool>
				
					<!-- 
						NOTE: we are ditching associations on purpose. This transformation is not intended to be used for 'opening v2 xpdl in v1 studio
									and the main destinations for it (simulation and export to iPM do not use associations.
							And of course because it would be a fair bit of hard work!
					 -->
				
				</extensions:Diagram>
			</xpdl:ExtendedAttribute>						
			
			<!-- Convert destination environments to v1. -->
			<xsl:variable name="destinations" select="xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute[@Name = 'Destination']"/>
			<xsl:if test="count($destinations) > 0">
				<xpdl:ExtendedAttribute Name="DestinationEnvironments">

					<xsl:for-each select="$destinations">
						<extensions:DestinationEnvironments>
							<extensions:DestinationEnvironment Id="{@Value}" Version="{@Value}" Description="{@Value}"/>
						</extensions:DestinationEnvironments>

					</xsl:for-each>

				</xpdl:ExtendedAttribute>
			</xsl:if>
			
			<!-- Then all the other extended attributes that aren't destination environments. -->
			<xsl:apply-templates select="xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute[@Name != 'Destination']"/>

			<!-- Have to add length / precision / scale extended attributes for formal params here (Formal Params have no ext attrs in v1!!! -->
			<xsl:for-each select="$allFormalParams">
				<xsl:variable name="param" select="."/>
				
				<xsl:variable name="paramName"><xsl:call-template name="FormalParameterName"><xsl:with-param name="str" select="@Name"/></xsl:call-template></xsl:variable>

				<xpdl:ExtendedAttribute Name="v2XPD_FormalParamExtra" Value="{$paramName}">
				
					<xsl:call-template name="OutputFormalParamExtraDetails">
						<xsl:with-param name="param" select="$param"/>
					</xsl:call-template>
					
				</xpdl:ExtendedAttribute>
			</xsl:for-each>
			
			<!-- If this process implements an interface then add the interface stuff to an extended attribute... -->
			<xsl:if test="xpdl2:ProcessHeader/xpdl2:Description/@xpdExt:DocumentationURL != ''">
				<xpdl:ExtendedAttribute Name="WorkflowProcess_xpdExt_DocumentationURL" Value="{xpdl2:ProcessHeader/xpdl2:Description/@xpdExt:DocumentationURL}"/>
			</xsl:if>

			<!-- Generically output all other non-xpdl2 prefixed attributes and elements as ext attrs (i.e. ##other's) -->
			<xsl:call-template name="HashOtherToExtAttr">
				<xsl:with-param name="input" select="."/>
				<xsl:with-param name="outputXpdl2Nodes" select="'false'"/>
			</xsl:call-template>
	
		</xpdl:ExtendedAttributes>
	</xpdl:WorkflowProcess>

	<!-- Show progress on each Process Interface / Process that is done. -->
	<xsl:value-of select="java:com.tibco.xpd.ui.importexport.utils.ImportExportTransformer.monitorSubTaskDone($transformMonitorId)"/>	

</xsl:template>

<!--
============================================
Output the v2XPD_FormalParamExtraDetails Extended attribute content for the given parameter
============================================
-->
<xsl:template name="OutputFormalParamExtraDetails">
	<xsl:param name="param"/>
	
	<!-- get basic type info from either the field itself or the type declaration of a typed field. format: "BasicTypeType;length;precision;scale" -->
	<xsl:variable name="basicTypeInfo" select="java:resolveBasicTypeInfo($javaUtil, $param/ancestor::xpdl2:Package/@Id, $param/@Id)"/>

	<xsl:variable name="basicType" select="substring-before($basicTypeInfo, ';')"/>
	
	<xsl:variable name="str1" select="substring-after($basicTypeInfo, ';')"/>
	<xsl:variable name="length" select="substring-before($str1, ';')"/>

	<xsl:variable name="str2" select="substring-after($str1, ';')"/>
	<xsl:variable name="precision" select="substring-before($str2, ';')"/>

	<xsl:variable name="scale" select="substring-after($str2, ';')"/>
	
	<details>
	    <xsl:attribute name="DisplayName"><xsl:value-of select="$param/@xpdExt:DisplayName"/></xsl:attribute>
		<xsl:attribute name="OriginalName"><xsl:value-of select="$param/@Name"/></xsl:attribute>
		<xsl:attribute name="BaseDataType"><xsl:value-of select="$basicType"/></xsl:attribute>
		<xsl:attribute name="IsInterfaceParam">
			<xsl:choose>
				<xsl:when test="local-name($param/../..) = 'ProcessInterface'">true</xsl:when>
				<xsl:otherwise>false</xsl:otherwise>
			</xsl:choose>
		</xsl:attribute>

		<xsl:attribute name="mandatory">
			<xsl:choose>
				<xsl:when test="@Required = 'true'">true</xsl:when>
				<xsl:otherwise>false</xsl:otherwise>
			</xsl:choose> 
		</xsl:attribute>
		
		<xsl:attribute name="SequenceNum"><xsl:value-of select="@iProcessExt:SequenceNum"/></xsl:attribute>

		<xsl:if test="$length != ''">
			<length><xsl:value-of select="$length"/></length>
		</xsl:if>
		
		<xsl:if test="$precision != ''">
			<precision><xsl:value-of select="$precision"/></precision>
		</xsl:if>
		<xsl:if test="$scale != ''">
			<scale><xsl:value-of select="$scale"/></scale>
		</xsl:if>
		<xsl:if test="xpdExt:InitialValues/xpdExt:Value">
			<InitialValues>
				<xsl:for-each select="xpdExt:InitialValues/xpdExt:Value">
					<Value><xsl:value-of select="."/></Value>
				</xsl:for-each>
			</InitialValues>
		</xsl:if>
	</details>
</xsl:template>


<!--
============================================
Handle Activity - need to do quite a bit here so handle it in depth.
============================================
-->
<xsl:template match="xpdl2:Activity">
	<xsl:param name="allFormalParams"/>

	<!-- If this is an event on a task border then ignore it. -->
	<xsl:variable name="eventTaskBorderId"><xsl:call-template name="getBorderTaskIdForEvent"><xsl:with-param name="activity" select="."/></xsl:call-template></xsl:variable>

	<xsl:if test="$eventTaskBorderId = ''">
	
		<xsl:variable name="objType">
			<xsl:call-template name="getV1ActivityType">
				<xsl:with-param name="activity" select="."/>
			</xsl:call-template>
		</xsl:variable>

		<!-- Allow for a reference to a user task. -->
		<xsl:variable name="actualActId">
			<xsl:choose>
				<xsl:when test="xpdl2:Implementation/xpdl2:Reference/@ActivityId != ''">
					<xsl:value-of select="xpdl2:Implementation/xpdl2:Reference/@ActivityId"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="@Id"/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		
		<xsl:variable name="actualActivity" select="../xpdl2:Activity[@Id = $actualActId]"/>
	
		<xpdl:Activity Id="{@Id}" Name="{@Name}">
			<xsl:copy-of select="@xpdExt:DisplayName"/>

			<xsl:apply-templates select="xpdl2:Description"/>
			<xsl:apply-templates select="xpdl2:Limit"/>
			
			<!-- 
				Here's the main tricky bit - converting of activity/gateway/event types.
	
				Some of this has to be handled here and some has to be handled by template for Xpdl2:Activity/xpdl2:ExtendedAttributes below.
				(Because we want to preserve some ext attrs and add some others.
			-->
	
			<!-- 
			Implementation 
			-->
			<xsl:choose>
				<xsl:when test="starts-with($objType, 'EVENT_')">
					<xpdl:Implementation>
						<xpdl:No/>
					</xpdl:Implementation>
				</xsl:when>
				
				<xsl:when test="$objType = 'TASK_SERVICE' or $objType = 'TASK_USER'  or $objType = 'TASK_MANUAL'">
					<xpdl:Implementation>
						<xpdl:Tool Id="0" Type="APPLICATION">
							<xsl:if test="$objType = 'TASK_USER'">

								<!-- For User Tasks output Data Mappings as actual parameters. -->
								<xpdl:ActualParameters>

									<xsl:variable name="dataFields" select="../../xpdl2:DataFields/xpdl2:DataField | /xpdl2:Package/xpdl2:DataFields/xpdl2:DataField"/>
									
									<xsl:for-each select="$actualActivity/xpdExt:AssociatedParameters/xpdExt:AssociatedParameter">
									
										<!-- In v2 these are data field names (?!? most things are Id's but these aren't currently @ 2006/12/18) -->
										<!-- This probably shouldn't stay the same so we'll check for both name and id -->
										<!-- In v1 these are data field id's -->
										<xsl:variable name="actParam" select="@FormalParam"/>
										<xsl:variable name="dataField" select="$dataFields[@Name = $actParam or @Id = $actParam]"/>
										<xsl:variable name="formalParam" select="$allFormalParams[@Name = $actParam or @Id = $actParam]"/>
										
										<xsl:choose>
											<xsl:when test="$dataField">
												<xpdl:ActualParameter><xsl:value-of select="$dataField/@Id"/></xpdl:ActualParameter>
											</xsl:when>
											<xsl:when test="$formalParam">
												<xpdl:ActualParameter>
													<xsl:call-template name="FormalParameterName">
														<xsl:with-param name="str" select="$formalParam/@Name"/>
													</xsl:call-template>	
												</xpdl:ActualParameter>
											</xsl:when>
										</xsl:choose>
									
									</xsl:for-each>
									
								</xpdl:ActualParameters>
								
							</xsl:if>
						</xpdl:Tool>
					</xpdl:Implementation>
				</xsl:when>
				
				<xsl:when test="starts-with($objType, 'GATEWAY_')">
					<xpdl:Route/>
				</xsl:when>
				
				<xsl:when test="$objType = 'TASK_SUBFLOW'">
					<xpdl:Implementation>
						<xpdl:SubFlow Id="{$actualActivity/xpdl2:Implementation/xpdl2:SubFlow/@Id}">
							<xsl:copy-of select="$actualActivity/xpdl2:Implementation/xpdl2:SubFlow/@Execution"/>
							
							<xsl:call-template name="SubFlowParameters">
								<xsl:with-param name="activity" select="$actualActivity"/>
								<xsl:with-param name="allFormalParams" select="$allFormalParams"/>
							</xsl:call-template>
							
						</xpdl:SubFlow>
					</xpdl:Implementation>
				</xsl:when>
				
				<xsl:when test="$objType = 'EMBEDDED_SUBPROCESS'"	>
					<xpdl:BlockActivity BlockId="{$actualActivity/xpdl2:BlockActivity/@ActivitySetId}"/>
				</xsl:when>
				
				
				<xsl:otherwise>
					<xpdl:Implementation>
						<xpdl:Tool Id="0" Type="APPLICATION"/>
					</xpdl:Implementation>
				</xsl:otherwise>
				
			</xsl:choose>
	
			<!-- 
			Performer
				If activity has performer set then use that else if its a reference task used the referenced task performer.
			-->
			<xsl:variable name="thisActPerformers" select="xpdl2:Performer | xpdl2:Performers/xpdl2:Performer"/>
			<xsl:variable name="refdActPerformers" select="$actualActivity/xpdl2:Performer | $actualActivity/xpdl2:Performers/xpdl2:Performer"/>
			
			<xsl:choose>
				<!-- If there are performers for this (potential a reference task ) task then use these by preference. -->
				<xsl:when test="count($thisActPerformers) > 0">
					<!-- There is only allowance for 1 performer in xpdl1 - we'll repeat the lot in ext attrs later -->
					<xpdl:Performer><xsl:value-of select="$thisActPerformers[1]"/></xpdl:Performer>
				</xsl:when>
				<!-- Otherwise use the actual ref'd task (which will be different if we're dealing with a reference taskl -->
				<xsl:when test="count($refdActPerformers) > 0">
					<xpdl:Performer><xsl:value-of select="$refdActPerformers[1]"/></xpdl:Performer>
				</xsl:when>
			</xsl:choose>
	
			<!--
				Start / Finish Mode
			-->
			<xsl:choose>
				<xsl:when test="$objType = 'TASK_SERVICE'">
					<xpdl:StartMode>
						<xpdl:Automatic/>
					</xpdl:StartMode>
					<xpdl:FinishMode>
						<xpdl:Automatic/>
					</xpdl:FinishMode>
				</xsl:when>
	
				<xsl:when test="$objType = 'TASK_USER' or $objType = 'TASK_MANUAL'">
					<xpdl:StartMode>
						<xpdl:Manual/>
					</xpdl:StartMode>
					<xpdl:FinishMode>
						<xpdl:Manual/>
					</xpdl:FinishMode>
				</xsl:when>
			</xsl:choose>
	
			<!--
				Other bits and pieces...
			-->
			<xsl:apply-templates select="xpdl2:Priority"/>
			
			<xsl:for-each select="xpdl2:Deadline">
				<xpdl:Deadline>
					<xsl:copy-of select="@Execution"/>
					<xpdl:DeadlineCondition>
						<xsl:value-of select="xpdl2:DeadlineDuration"/>
					</xpdl:DeadlineCondition>
					<xpdl:ExceptionName>
						<xsl:value-of select="xpdl2:ExceptionName"/>
					</xpdl:ExceptionName>
				</xpdl:Deadline>
			</xsl:for-each>
			
			<xsl:apply-templates select="xpdl2:SimulationInformation"/>
			
			<xsl:if test="xpdl2:Icon">
				<xpdl:Icon>
					<xsl:value-of select="xpdl2:Icon"/>
				</xpdl:Icon>
			</xsl:if>
	
			<xsl:apply-templates select="xpdl2:Documentation"/>
	
			<!-- v1 Studio only bothers with transition restriction for parallel gateways -->
			<xsl:if test="$objType = 'GATEWAY_AND'">
				<xpdl:TransitionRestrictions>
					<xpdl:TransitionRestriction>
						<xpdl:Join Type="AND"/>
						<xpdl:Split Type="AND"/>
					</xpdl:TransitionRestriction>
				</xpdl:TransitionRestrictions>
			</xsl:if>
	
	
			<!-- Now deal with extended  attributes... -->
			<xpdl:ExtendedAttributes>
	
				<xpdl:ExtendedAttribute Name="XPD">
					<extensions:Activity LaneId="{xpdl2:NodeGraphicsInfos/xpdl2:NodeGraphicsInfo[@ToolId = 'XPD' or @ToolId = '' or not(@ToolId)]/@LaneId}">
						<xsl:call-template name="v2_to_v1_Location">
							<xsl:with-param name="objType" select="$objType"/>
						</xsl:call-template>
	
						<!-- Extra activity type info. -->
						<xsl:choose>
							<xsl:when test="$objType = 'EVENT_START'">
								<extensions:ActivityType>EVENT</extensions:ActivityType>
								<extensions:EventFlowType>START</extensions:EventFlowType>
							</xsl:when>
	
							<xsl:when test="starts-with($objType, 'EVENT_END')">
								<extensions:ActivityType>EVENT</extensions:ActivityType>
								<extensions:EventFlowType>END</extensions:EventFlowType>
							</xsl:when>
	
							<xsl:when test="$objType = 'GATEWAY_XOR_EVENT'">
								<extensions:ActivityType>GATEWAY</extensions:ActivityType>
								<extensions:GatewayType>XOR_EVENT</extensions:GatewayType>
							</xsl:when>
	
							<xsl:when test="$objType = 'GATEWAY_XOR_DATA'">
								<extensions:ActivityType>GATEWAY</extensions:ActivityType>
								<extensions:GatewayType>XOR_DATA</extensions:GatewayType>
							</xsl:when>
	
							<xsl:when test="$objType = 'GATEWAY_OR'">
								<extensions:ActivityType>GATEWAY</extensions:ActivityType>
								<extensions:GatewayType>OR</extensions:GatewayType>
							</xsl:when>
							
							<xsl:when test="$objType = 'GATEWAY_AND'">
								<extensions:ActivityType>GATEWAY</extensions:ActivityType>
								<extensions:GatewayType>PARALLEL</extensions:GatewayType>
							</xsl:when>
							
							<xsl:when test="$objType = 'GATEWAY_COMPLEX'">
								<extensions:ActivityType>GATEWAY</extensions:ActivityType>
								<extensions:GatewayType>COMPLEX</extensions:GatewayType>
							</xsl:when>
						</xsl:choose>
					</extensions:Activity>
				</xpdl:ExtendedAttribute>

				<xsl:if test="$objType = 'EVENT_END_CANCEL'">
					<xpdl:ExtendedAttribute Name="v2XPD_EndEventCancel" Value="true"/>
				</xsl:if>

				<!-- 
					Add special directions for export to iPM... 
				-->
				<xsl:variable name="actId" select="@Id"/>
				<xsl:variable name="attachedEvents" select="../xpdl2:Activity/xpdl2:Event/xpdl2:IntermediateEvent[@Target = $actId]"/>

				<xsl:choose>
					<xsl:when test="xpdl2:Event/xpdl2:IntermediateEvent">
						<!-- v1 doesn't have intermediate events but we can add extra attr to say it is and export to other format can use it -->
						<!-- intermedaite not attached to border (we already discounted border events at start of this template).
							 (if it's a link then don't put anything special in so that we just create a empty task in v1 
							 During transition processing we will then create a transition between them). -->
						<xsl:if test="count(xpdl2:Event/xpdl2:IntermediateEvent/xpdl2:TriggerResultLink) = 0">
							<xpdl:ExtendedAttribute Name="v2XPD_IntermediateEvent" Value="true"/>

							<!-- Flag which typ of intermediate event it is. -->
							<xsl:choose>
								<xsl:when test="count(xpdl2:Event/xpdl2:IntermediateEvent/xpdl2:TriggerTimer) > 0">
									<xpdl:ExtendedAttribute Name="v2XPD_IntermediateEventTimer" Value="true"/>

									<xsl:choose>
										<xsl:when test="xpdl2:Deadline[1]/xpdl2:DeadlineDuration/@ScriptGrammar = 'ConstantPeriod'">
											<!-- output constant period direct extended attrs. -->
											<xpdl:ExtendedAttribute Name="v2XPD_TimeoutConstantPeriod">
												<xsl:call-template name="CopyNodeWithoutPrefix">
													<xsl:with-param name="input" select="xpdl2:Deadline[1]/xpdl2:DeadlineDuration/xpdExt:ConstantPeriod"/>
												</xsl:call-template>
											</xpdl:ExtendedAttribute>
										</xsl:when>
										<xsl:otherwise>
											<!-- Output the original source timer event activity (this is where the deadline script can be picked up from in the original v2 model -->
											<xpdl:ExtendedAttribute Name="v2XPD_TimeoutProviderActivity" Value="{@Id}"/>
										</xsl:otherwise>
									</xsl:choose>
									
									<xsl:if test="iProcessExt:TaskProperties">
										<xpdl:ExtendedAttribute Name="Activity_iProcessExt_TimeoutTaskProperties">
											<xsl:call-template name="CopyNodeWithoutPrefix">
												<xsl:with-param name="input" select="iProcessExt:TaskProperties"/>
											</xsl:call-template>
										</xpdl:ExtendedAttribute>
									</xsl:if>
				
								</xsl:when>
								<xsl:when test="count(xpdl2:Event/xpdl2:IntermediateEvent/xpdl2:TriggerResultSignal) > 0">
									<xsl:variable name="signalName" select="xpdl2:Event/xpdl2:IntermediateEvent/xpdl2:TriggerResultSignal/@Name"/>
									<xpdl:ExtendedAttribute Name="v2XPD_ThrowEventSignalName" Value="{$signalName}"/>
								</xsl:when>
							</xsl:choose>
						</xsl:if>
					</xsl:when>
					
					<!-- Preserve signal code for End Events too -->
					<xsl:when test="xpdl2:Event/xpdl2:EndEvent/xpdl2:TriggerResultSignal">
						<xsl:variable name="signalName" select="xpdl2:Event/xpdl2:EndEvent/xpdl2:TriggerResultSignal/@Name"/>
						<xpdl:ExtendedAttribute Name="v2XPD_ThrowEventSignalName" Value="{$signalName}"/>
					</xsl:when>
					
				</xsl:choose>

				<!-- If this task has a timer event attached to border then tag as withdraw on deadline expire or not. -->
				<xsl:variable name="timerEvents" select="$attachedEvents/xpdl2:TriggerTimer"/>
				<xsl:if test="$timerEvents">
					<xsl:choose>
						<!-- may be more than one so just pick one. -->
						<xsl:when test="$timerEvents[1]/@xpdExt:ContinueOnTimeout = 'true'">
							<xpdl:ExtendedAttribute Name="v2XPD_ContinueOnTimeout" Value="true"/>
						</xsl:when>
						<xsl:otherwise>
							<xpdl:ExtendedAttribute Name="v2XPD_ContinueOnTimeout" Value="false"/>
						</xsl:otherwise>
					</xsl:choose>
					
					<!-- Output the original source timer event activity (this is where the deadline script can be picked up from in the original v2 model -->
					<xsl:choose>
						<xsl:when test="$timerEvents[1]/../../../xpdl2:Deadline[1]/xpdl2:DeadlineDuration/@ScriptGrammar = 'ConstantPeriod'">
							<!-- output constant period direct extended attrs. -->
							<xpdl:ExtendedAttribute Name="v2XPD_TimeoutConstantPeriod">
								<xsl:call-template name="CopyNodeWithoutPrefix">
									<xsl:with-param name="input" select="$timerEvents[1]/../../../xpdl2:Deadline[1]/xpdl2:DeadlineDuration/xpdExt:ConstantPeriod"/>
								</xsl:call-template>
							</xpdl:ExtendedAttribute>
						</xsl:when>
						<xsl:otherwise>
							<!-- Output the original source timer event activity (this is where the deadline script can be picked up from in the original v2 model -->
							<xpdl:ExtendedAttribute Name="v2XPD_TimeoutProviderActivity" Value="{$timerEvents[1]/../../../@Id}"/>
						</xsl:otherwise>
					</xsl:choose>

					<!-- Output task properties of the attached timer event. -->
					<xsl:if test="$timerEvents[1]/../../../iProcessExt:TaskProperties">
						<xpdl:ExtendedAttribute Name="Activity_iProcessExt_TimeoutTaskProperties">
							<xsl:call-template name="CopyNodeWithoutPrefix">
								<xsl:with-param name="input" select="$timerEvents[1]/../../../iProcessExt:TaskProperties"/>
							</xsl:call-template>
						</xpdl:ExtendedAttribute>
					</xsl:if>

				</xsl:if>
				
				<!-- If this task has signal event attached to border then preserve the signal name(s). -->
				<xsl:variable name="signalEvents" select="$attachedEvents/xpdl2:TriggerResultSignal"/>
				<xsl:for-each select="$signalEvents">
					<xsl:variable name="signalName" select="@Name"/>
					<xpdl:ExtendedAttribute Name="v2XPD_CatchEventSignalName" Value="{$signalName}"/>
				</xsl:for-each>
				
				
				<!-- Output all performers to extended attribute (xpdl v1 can only deal with 1 as std -->
				<xsl:choose>
					<!-- If there are performers for this (potential a reference task ) task then use these by preference. -->
					<xsl:when test="count($thisActPerformers) > 0">
						<xpdl:ExtendedAttribute Name="v2XPD_Performers">
							<xsl:for-each select="$thisActPerformers">
								<!-- if it's a formal parameter as participant then convert to v1 name -->
								<xsl:variable name="pId"><xsl:value-of select="."/></xsl:variable>
								<xsl:variable name="formalParam" select="$allFormalParams[@Id = $pId]"/>
								<xsl:choose>
									<xsl:when test="$formalParam">
										<v2Performer><xsl:call-template name="FormalParameterName"><xsl:with-param name="str" select="$formalParam/@Name"/></xsl:call-template></v2Performer>
									</xsl:when>
									<xsl:otherwise>
										<v2Performer><xsl:value-of select="."/></v2Performer>
									</xsl:otherwise>
								</xsl:choose>
								
							</xsl:for-each>
						</xpdl:ExtendedAttribute>
					</xsl:when>

					<!-- Otherwise use the actual ref'd task (which will be different if we're dealing with a reference taskl -->
					<xsl:when test="count($refdActPerformers) > 0">
						<xpdl:ExtendedAttribute Name="v2XPD_Performers">
							<xsl:for-each select="$refdActPerformers">
								<!-- if it's a formal parameter as participant then convert to v1 name -->
								<xsl:variable name="pId"><xsl:value-of select="."/></xsl:variable>
								<xsl:variable name="formalParam" select="$allFormalParams[@Id = $pId]"/>
								<xsl:choose>
									<xsl:when test="$formalParam">
										<v2Performer><xsl:call-template name="FormalParameterName"><xsl:with-param name="str" select="$formalParam/@Name"/></xsl:call-template></v2Performer>
									</xsl:when>
									<xsl:otherwise>
										<v2Performer><xsl:value-of select="."/></v2Performer>
									</xsl:otherwise>
								</xsl:choose>
							</xsl:for-each>
						</xpdl:ExtendedAttribute>
					</xsl:when>
				</xsl:choose>

				<!-- Output all the ##other attributes on activity node itself. (for StepIndex) -->
				<xsl:call-template name="HashOtherToExtAttr">
					<xsl:with-param name="input" select="."/> <!-- don't use actualActivity because we want the iProcesExt:StepIndex from the reference task if this is one! -->
					<xsl:with-param name="outputXpdl2Nodes" select="'false'"/>
					<xsl:with-param name="attributesOnly" select="'true'"/>
				</xsl:call-template>

				<!-- If this is a task, add the implementation stuff to extended attributes.
						This can be picked up by "export as ..." and interpretted for the given destination -->
				<xsl:if test="$actualActivity/xpdl2:Implementation/xpdl2:Task">
					<xsl:call-template name="HashOtherToExtAttr">
						<xsl:with-param name="input" select="$actualActivity/xpdl2:Implementation/xpdl2:Task"/>
						<xsl:with-param name="outputXpdl2Nodes" select="'true'"/>
					</xsl:call-template>

					<xsl:if test="$actualActivity/xpdExt:Audit/xpdExt:AuditEvent">
						<xpdl:ExtendedAttribute Name="Activity_xpdExt_Audit">
							<xsl:call-template name="CopyNodeWithoutPrefix">
								<xsl:with-param name="input" select="$actualActivity/xpdExt:Audit"/>
							</xsl:call-template>
						</xpdl:ExtendedAttribute>
					</xsl:if>
				</xsl:if>
				
				<!-- If this is an independent sub-process task then include the original SubFlow definition. -->
				<xsl:if test="$actualActivity/xpdl2:Implementation/xpdl2:SubFlow">
					<xsl:call-template name="HashOtherToExtAttr">
						<xsl:with-param name="input" select="$actualActivity/xpdl2:Implementation"/>
						<xsl:with-param name="outputXpdl2Nodes" select="'true'"/>
					</xsl:call-template>

					<!-- Output Unmapped scripts. -->
					<xsl:for-each select="$actualActivity/xpdExt:ScriptInformation">
						<xpdl:ExtendedAttribute Name="Activity_xpdExt_ScriptInformation">
							<xsl:call-template name="CopyNodeWithoutPrefix">
								<xsl:with-param name="input" select="."/>
							</xsl:call-template>
						</xpdl:ExtendedAttribute>
					</xsl:for-each>
				</xsl:if>

				<xsl:choose>
					<xsl:when test="$actualActivity/@xpdExt:Visibility = 'Public'">
						<xpdl:ExtendedAttribute Name="Activity_xpdExt_Visibility" Value="Public"/>
					</xsl:when>
					<xsl:otherwise>
						<xpdl:ExtendedAttribute Name="Activity_xpdExt_Visibility" Value="Private"/>
					</xsl:otherwise>
				</xsl:choose>

				<xsl:if test="$actualActivity/xpdl2:BlockActivity">
					<xsl:choose>
						<xsl:when test="$actualActivity/@xpdExt:RequireNewTransaction = 'true'">
							<xpdl:ExtendedAttribute Name="Activity_xpdExt_RequireNewTransaction" Value="true"/>
						</xsl:when>
						<xsl:otherwise>
							<xpdl:ExtendedAttribute Name="Activity_xpdExt_RequireNewTransaction" Value="false"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:if>
				
				<!-- If this task has associated parameters element then add it. -->
				<xsl:if test="$actualActivity/xpdExt:AssociatedParameters">
					<xpdl:ExtendedAttribute Name="Activity_xpdExt_AssociatedParameters">
						<xsl:if test="$actualActivity/xpdExt:AssociatedParameters/@DisableImplicitAssociation">
							<xsl:attribute name="DisableImplicitAssociation"><xsl:value-of select="$actualActivity/xpdExt:AssociatedParameters/@DisableImplicitAssociation"/></xsl:attribute>
						</xsl:if>

						<xsl:for-each select="$actualActivity/xpdExt:AssociatedParameters/xpdExt:AssociatedParameter">
							<AssociatedParameter>
								<!-- We will be converting the formal param name to v1 Id (without spaces etc) so should do same here. -->
								<xsl:attribute name="FormalParam">
									<xsl:variable name="v2Name" select="@FormalParam"/>
									<xsl:choose>
										<xsl:when test="$allFormalParams[@Name = $v2Name]">
											<xsl:call-template name="FormalParameterName">
												<xsl:with-param name="str" select="$v2Name"/>
											</xsl:call-template>	
										</xsl:when>
										<xsl:otherwise>
											<xsl:value-of select="$v2Name"/>
										</xsl:otherwise>
									</xsl:choose>
								</xsl:attribute>

								<xsl:apply-templates select="@*[local-name() != 'FormalParam']"/>

								<xsl:apply-templates select="*"/>
							</AssociatedParameter>

						</xsl:for-each>
					</xpdl:ExtendedAttribute>
				</xsl:if>
			
				<xsl:if test="xpdl2:Description/@xpdExt:DocumentationURL != ''">
					<xpdl:ExtendedAttribute Name="Activity_xpdExt_DocumentationURL" Value="{xpdl2:Description/@xpdExt:DocumentationURL}"/>
				</xsl:if>

				<!-- Copy iProcessExt:TaskProperties out to ext attrs. -->
				<xsl:if test="$actualActivity/iProcessExt:TaskProperties">
					<xpdl:ExtendedAttribute Name="Activity_iProcessExt_TaskProperties">
						<xsl:call-template name="CopyNodeWithoutPrefix">
							<xsl:with-param name="input" select="$actualActivity/iProcessExt:TaskProperties"/>
						</xsl:call-template>
					</xpdl:ExtendedAttribute>
				</xsl:if>

				<!-- Copy xpdExt:DurationCalculation out to ext attrs. -->
				<xsl:if test="$actualActivity/xpdExt:DurationCalculation">
					<xpdl:ExtendedAttribute Name="Activity_xpdExt_DurationCalculation">
						<xsl:call-template name="CopyNodeWithoutPrefix">
							<xsl:with-param name="input" select="$actualActivity/xpdExt:DurationCalculation"/>
						</xsl:call-template>
					</xpdl:ExtendedAttribute>
				</xsl:if>

				<!-- Copy iProcessExt:ConditionProperties out to ext attrs. -->
				<xsl:if test="$actualActivity/iProcessExt:ConditionProperties">
					<xpdl:ExtendedAttribute Name="Activity_iProcessExt_ConditionProperties">
						<xsl:call-template name="CopyNodeWithoutPrefix">
							<xsl:with-param name="input" select="$actualActivity/iProcessExt:ConditionProperties"/>
						</xsl:call-template>
					</xpdl:ExtendedAttribute>
				</xsl:if>
			
				<!-- Finally, copy any extra extended attrs from original -->
				<xsl:apply-templates select="$actualActivity/xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute"/>
				
			</xpdl:ExtendedAttributes>
			
		</xpdl:Activity>

	</xsl:if>
	
</xsl:template>
<!--
============================================================================
Subflow parameters (DataMappings -> ActualParameters)
============================================================================
-->
<xsl:template name="SubFlowParameters">
	<xsl:param name="allFormalParams"/>
	<xsl:param name="activity"/>
	
	<!-- Have to create v1 actual parameters by going thru the formal parameters of called sub-proc. This is because in v2 the actual->formal parameter mapping is by reference
			BUT in v1 it is by position. -->

	<xsl:variable name="dataMappings" select="$activity/xpdl2:Implementation/xpdl2:SubFlow/xpdl2:DataMappings/xpdl2:DataMapping"/>

	<xsl:if test="count($dataMappings) > 0">

		<!-- Look up the formal parameters list for subflow in question -->
		<xsl:variable name="subProcId" select="$activity/xpdl2:Implementation/xpdl2:SubFlow/@Id"/>
		<xsl:variable name="subProc" select="/xpdl2:Package/xpdl2:WorkflowProcesses/xpdl2:WorkflowProcess[@Id = $subProcId]"/>
		<xsl:variable name="subProcLocalParams" select="$subProc/xpdl2:FormalParameters/xpdl2:FormalParameter"/>
		
		<xsl:variable name="subProcInterfaceId" select="$subProc/xpdExt:ImplementedInterface/@xpdExt:ProcessInterfaceId"/>
		<xsl:variable name="subProcInterfaceParams" select="/xpdl2:Package/xpdExt:ProcessInterfaces/xpdExt:ProcessInterface[@Id = $subProcInterfaceId]/xpdl2:FormalParameters/xpdl2:FormalParameter"/>
	
		<xsl:variable name="subProcFormalParams" select="$subProcLocalParams | $subProcInterfaceParams"/>

		<xsl:variable name="myFormalParams" select="$allFormalParams"/>
		
		<xsl:variable name="dataFields" select="$activity/../../xpdl2:DataFields/xpdl2:DataField | /xpdl2:Package/xpdl2:DataFields/xpdl2:DataField"/>
			
		<xsl:if test="count($subProcFormalParams) > 0">
			<xpdl:ActualParameters>
	
				<xsl:for-each select="$subProcFormalParams">
					<!-- In v2 there may be a single formal parameter with MULTIPLE actual data mappings. 
						For instance the formal parameter is (currently) always INOUT. In this instance you get up to TWO
						dataMappings (one from input mappings and one from the out mappings).
						This potentially means we could be in trouble if user defines different mapping for in and out. 
						Not much we can do for now so we'll pick the first. Studio should be coded to ensure they're the same. -->

					<xsl:variable name="formalId" select="@Id"/>
					<xsl:variable name="formalName" select="@Name"/>
					
					<xsl:variable name="pMappings" select="$dataMappings[@Formal = $formalId or @Formal = $formalName]"/>

					<!-- if we have a mappoing to the formal param then place it the v1 actual param 
							Otherwise we must insert a dummy one so that position of later ones match up.
						-->
					<xpdl:ActualParameter>
						<xsl:choose>
							<xsl:when test="count($pMappings) > 0">
								<!-- If the mapping is from one of this proc's formal params then we need to output name instead of Id cos we do that when downgrading the formal param itself. -->
								<xsl:variable name="actual" select="$pMappings[1]/xpdl2:Actual"/>
								
								<xsl:variable name="myFormal" select="$myFormalParams[@Id = $actual or @Name = $actual]"/>
								<xsl:variable name="myField" select="$dataFields[@Id = $actual or @Name = $actual]"/>
								
								<xsl:choose>
									<xsl:when test="$myFormal">
										<xsl:call-template name="FormalParameterName">
											<xsl:with-param name="str" select="$myFormal/@Name"/>
										</xsl:call-template>	
									</xsl:when>
									<xsl:when test="$myField">
										<xsl:value-of select="$myField/@Id"/>
									</xsl:when>
									<xsl:otherwise>
										<xsl:value-of select="$actual"/> <!-- No change for data field id's -->
									</xsl:otherwise>
								
								</xsl:choose>
								
							</xsl:when>
							<xsl:otherwise>
								<xsl:text>No_Mapping</xsl:text>
							</xsl:otherwise>
						</xsl:choose>
					</xpdl:ActualParameter>
					
				</xsl:for-each>
	
			</xpdl:ActualParameters>
	
		</xsl:if>
	</xsl:if>
	
</xsl:template>


<!--
============================================================================
Transitions
- Handle special cases for transitions, namely 
	+ convert association from Compensation event on border of task  to v1 transition.
	+ add transition between intermediate link events.
============================================================================
-->
<xsl:template match="xpdl2:Transitions">
	<xsl:param name="allFormalParams"/>

	<xpdl:Transitions>
		<!-- Process all normal transitions -->
		<xsl:for-each select="xpdl2:Transition">
			<xsl:apply-templates select=".">
				<xsl:with-param name="allFormalParams" select="$allFormalParams"/>
			</xsl:apply-templates>
		</xsl:for-each>

		<!-- Look for associations from compensation event on task border and create an exception transition for them -->
		<xsl:variable name="activities" select="../xpdl2:Activities/xpdl2:Activity"/>
		
		<xsl:for-each select="$activities">
			<xsl:variable name="eventTaskBorderId"><xsl:call-template name="getBorderTaskIdForEvent"><xsl:with-param name="activity" select="."/></xsl:call-template></xsl:variable>

			<xsl:choose>
				<!-- Check for compensation event on task border -->
				<xsl:when test="$eventTaskBorderId != ''">
					<xsl:if test="xpdl2:Event/xpdl2:IntermediateEvent/xpdl2:TriggerResultCompensation">
						<!-- Ok, it's a compensation on task border -->
						<xsl:variable name="srcId" select="@Id"/>
						<xsl:variable name="compAssociations" select="/xpdl2:Package/xpdl2:Associations/xpdl2:Association[@Source = $srcId]"/>
	
						<xsl:for-each select="$compAssociations">
							<xpdl:Transition Id="{@Id}" From="{$eventTaskBorderId}" To="{@Target}" Name="{@Name}">
								<xpdl:Condition Type="EXCEPTION"/>
								<xpdl:ExtendedAttributes>
									<xpdl:ExtendedAttribute Name="XPD">
										<extensions:Transition>
											<extensions:ExceptionType>ERROR</extensions:ExceptionType>
										</extensions:Transition>
									</xpdl:ExtendedAttribute>
								</xpdl:ExtendedAttributes>
							</xpdl:Transition>
						</xsl:for-each>
						
					</xsl:if>
				</xsl:when>
				
				<!-- Check for intermediate link event. -->
				<xsl:when test="xpdl2:Event/xpdl2:IntermediateEvent/xpdl2:TriggerResultLink/@Name != ''">
					<xsl:variable name="tgtId" select="xpdl2:Event/xpdl2:IntermediateEvent/xpdl2:TriggerResultLink/@Name"/>
					<xsl:variable name="target" select="$activities[@Id = $tgtId]"/>
					
					<xsl:if test="$target">
						<xpdl:Transition Id="{@Id}_to_{$tgtId}" From="{@Id}" To="{xpdl2:Event/xpdl2:IntermediateEvent/xpdl2:TriggerResultLink/@Name}" Name="Link From '{@Name}' to '{$target/@Name}'"/>
					</xsl:if>
				</xsl:when>
				
			</xsl:choose>

			
		</xsl:for-each>
	
	</xpdl:Transitions>

</xsl:template>


<!--
============================================================================
Transition
============================================================================
-->
<xsl:template match="xpdl2:Transition">
	<xsl:param name="allFormalParams"/>

	<!-- If this transition is from an event on border of task then we need to redirect it's source to task and make it an exception transition -->
	<xsl:variable name="from" select="@From"/>
	
	<xsl:variable name="srcAct" select="../../xpdl2:Activities/xpdl2:Activity[@Id = $from]"/>

	<xsl:variable name="eventTaskBorderId"><xsl:call-template name="getBorderTaskIdForEvent"><xsl:with-param name="activity" select="$srcAct"/></xsl:call-template></xsl:variable>

	<xsl:variable name="realFromId">
		<xsl:choose>
			<xsl:when test="$eventTaskBorderId != ''"><xsl:value-of select="$eventTaskBorderId"/></xsl:when>
			<xsl:otherwise><xsl:value-of select="$from"/></xsl:otherwise>
		</xsl:choose>
	</xsl:variable>
	
	<xpdl:Transition Id="{@Id}" From="{$realFromId}" To="{@To}">
		<xsl:copy-of select="@Name"/>
		<xsl:copy-of select="@xpdExt:DisplayName"/>

		<!-- For event on task border as source, then the transition is an EXCEPTION type in v1 
				Same for in-flow timer intermediate event.
			-->
		<xsl:choose>
			<xsl:when test="$eventTaskBorderId != '' or $srcAct/xpdl2:Event/xpdl2:IntermediateEvent/xpdl2:TriggerTimer">
				<xpdl:Condition Type="EXCEPTION">
					<xsl:if test="xpdl2:Condition/xpdl2:Expression">
						<xpdl:Xpression><xsl:value-of select="xpdl2:Condition/xpdl2:Expression"/></xpdl:Xpression>
					</xsl:if>
				</xpdl:Condition>
			</xsl:when>
			<xsl:otherwise>
				<xsl:if test="xpdl2:Condition">
					<xpdl:Condition>
						<xsl:copy-of select="xpdl2:Condition/@Type"/>
						
						<xsl:choose>
							<xsl:when test="xpdl2:Condition/xpdl2:Expression">
								<xpdl:Xpression><xsl:value-of select="xpdl2:Condition/xpdl2:Expression"/></xpdl:Xpression>
							</xsl:when>
							<xsl:when test="xpdl2:Condition/text() != ''">
								<xpdl:Xpression><xsl:value-of select="xpdl2:Condition/text()"/></xpdl:Xpression>
							</xsl:when>
						</xsl:choose>

					</xpdl:Condition>
				</xsl:if>
			</xsl:otherwise>
		</xsl:choose>

		<xsl:apply-templates select="xpdl:Description"/>

		<xsl:if test="$eventTaskBorderId != '' or count(xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute) > 0 or $srcAct/xpdl2:Event/xpdl2:IntermediateEvent/xpdl2:TriggerTimer or xpdl2:Condition/xpdl2:Expression">
			<xpdl:ExtendedAttributes>
				<xsl:apply-templates select="xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute">
					<xsl:with-param name="allFormalParams" select="$allFormalParams"/>
				</xsl:apply-templates>

				<xsl:choose>
					<xsl:when test="$eventTaskBorderId != ''">
						<xpdl:ExtendedAttribute Name="XPD">
							<extensions:Transition>
								<xsl:choose>
									<xsl:when test="$srcAct/xpdl2:Event/xpdl2:IntermediateEvent/xpdl2:TriggerTimer">
										<extensions:ExceptionType>TIMER</extensions:ExceptionType>
									</xsl:when>
									<xsl:otherwise>
										<extensions:ExceptionType>ERROR</extensions:ExceptionType>
									</xsl:otherwise>
								</xsl:choose>
							</extensions:Transition>
						</xpdl:ExtendedAttribute>

						<!-- If the source object is signal event on task border then flag it -->
						<xsl:if test="$srcAct/xpdl2:Event/xpdl2:IntermediateEvent/xpdl2:TriggerResultSignal">
							<xpdl:ExtendedAttribute Name="v2XPD_FlowFromCatchSignal" Value="true"/>
						</xsl:if>
						
					</xsl:when>
					
					<!-- If source is an in-flow TIMER intermediate event then make it's outgoing transitions TIMER events. -->
					<xsl:when test="$srcAct/xpdl2:Event/xpdl2:IntermediateEvent/xpdl2:TriggerTimer">
						<xpdl:ExtendedAttribute Name="XPD">
							<extensions:Transition>
								<extensions:ExceptionType>TIMER</extensions:ExceptionType>
							</extensions:Transition>
						</xpdl:ExtendedAttribute>
					</xsl:when>
				</xsl:choose>

				<!-- Output the script grammar for conditions. -->
				<xsl:if test="xpdl2:Condition/xpdl2:Expression">
					<xpdl:ExtendedAttribute Name="ScriptGrammar" Value="{xpdl2:Condition/xpdl2:Expression/@ScriptGrammar}"/>
				</xsl:if>

			</xpdl:ExtendedAttributes>
		</xsl:if>
		
	</xpdl:Transition>

</xsl:template>


<!--
============================================================================
If the given activity is an Intermediate Event on the border of a task then return the Id of the task that it is on the border of.
Otherwise returns nothing.
============================================================================
-->
<xsl:template name="getBorderTaskIdForEvent">
	<xsl:param name="activity"/>

	<xsl:value-of select="$activity/xpdl2:Event/xpdl2:IntermediateEvent/@Target"/>

</xsl:template>


<!--
============================================================================
Output v1 location info from current v2 activity
============================================================================
-->
<xsl:template name="v2_to_v1_Location">
	<xsl:param name="objType"/>

	<xsl:variable name="gi" select="xpdl2:NodeGraphicsInfos/xpdl2:NodeGraphicsInfo[@ToolId = 'XPD' or @ToolId = '' or not(@ToolId)]"/>
	
	<xsl:variable name="xPos" select="round($gi/xpdl2:Coordinates/@XCoordinate)"/>
	<xsl:variable name="yPos" select="round($gi/xpdl2:Coordinates/@YCoordinate)"/>
	<xsl:variable name="width" select="round($gi/@Width)"/>
	<xsl:variable name="height" select="round($gi/@Height)"/>
	
	<extensions:Location>

		<!-- In v2 all coords are centred, in v1 only events and gateways are centred -->
		<xsl:choose>
			<xsl:when test="starts-with($objType, 'GATEWAY_')">
				<extensions:XOffset><xsl:value-of select="$xPos"/></extensions:XOffset>
				<extensions:YOffset><xsl:value-of select="$yPos"/></extensions:YOffset>
				<extensions:Width>40</extensions:Width>
				<extensions:Height>44</extensions:Height>
			</xsl:when>
			<xsl:when test="starts-with($objType, 'EVENT_')">
				<extensions:XOffset><xsl:value-of select="$xPos"/></extensions:XOffset>
				<extensions:YOffset><xsl:value-of select="$yPos"/></extensions:YOffset>
				<extensions:Width>29</extensions:Width>
				<extensions:Height>29</extensions:Height>
			</xsl:when>
			
			<xsl:otherwise>
				<!-- adjust x/y to top left -->
				<extensions:XOffset><xsl:value-of select="$xPos - round($width div 2)"/></extensions:XOffset>
				<extensions:YOffset><xsl:value-of select="$yPos - round($height div 2)"/></extensions:YOffset>
				<extensions:Width><xsl:value-of select="$width"/></extensions:Width>
				<extensions:Height><xsl:value-of select="$height"/></extensions:Height>
				
			</xsl:otherwise>
		</xsl:choose>
	
	</extensions:Location>
	
</xsl:template>

<!--
============================================================================
Output a text string indicating the equivalent v1 type for the given v2 activity...
Types...
GATEWAY_XOR_EVENT
GATEWAY_XOR_DATA
GATEWAY_OR
GATEWAY_AND
GATEWAY_COMPLEX
TASK_SERVICE
TASK_USER
TASK_SUBFLOW
EVENT_START
EVENT_END
EVENT_CANCEL

default...
TASK_PLAIN
============================================================================
-->
<xsl:template name="getV1ActivityType">
	<xsl:param name="activity"/>
	
	<xsl:choose>
		<!-- Handle various object types, with plain task as the default for everything. -->
		<xsl:when test="$activity/xpdl2:Route/@GatewayType = 'XOR' or $activity/xpdl2:Route/@GatewayType = 'Exclusive'">
			<xsl:choose>
				<xsl:when test="$activity/xpdl2:Route/@Instantiate = 'true' or $activity/xpdl2:Route/@ExclusiveType = 'Event' or $activity/xpdl2:Route/@XORType = 'Event'">
					<xsl:text>GATEWAY_XOR_EVENT</xsl:text>
				</xsl:when>
				<xsl:otherwise>
					<xsl:text>GATEWAY_XOR_DATA</xsl:text>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:when>

		<xsl:when test="$activity/xpdl2:Route/@GatewayType = 'OR' or $activity/xpdl2:Route/@GatewayType = 'Inclusive'">
			<xsl:text>GATEWAY_OR</xsl:text>
		</xsl:when>

		<xsl:when test="$activity/xpdl2:Route/@GatewayType = 'AND' or $activity/xpdl2:Route/@GatewayType = 'Parallel'">
			<xsl:text>GATEWAY_AND</xsl:text>
		</xsl:when>

		<xsl:when test="$activity/xpdl2:Route/@GatewayType = 'Complex'">
			<xsl:text>GATEWAY_COMPLEX</xsl:text>
		</xsl:when>
		
		<xsl:when test="$activity/xpdl2:BlockActivity">
			<xsl:text>EMBEDDED_SUBPROCESS</xsl:text>
		</xsl:when>

		<xsl:when test="$activity/xpdl2:Implementation/xpdl2:Task/xpdl2:TaskService">
			<xsl:text>TASK_SERVICE</xsl:text>
		</xsl:when>

		<!-- TODO Scripts to eai script  -->
		<xsl:when test="$activity/xpdl2:Implementation/xpdl2:Task/xpdl2:TaskScript">
			<xsl:text>TASK_SERVICE</xsl:text>
		</xsl:when>

		<xsl:when test="$activity/xpdl2:Implementation/xpdl2:Task/xpdl2:TaskManual or $activity/xpdl2:Implementation/xpdl2:Task/xpdl2:TaskUser">
			<xsl:text>TASK_USER</xsl:text>
		</xsl:when>

		<xsl:when test="$activity/xpdl2:Implementation/xpdl2:SubFlow">
			<xsl:text>TASK_SUBFLOW</xsl:text>
		</xsl:when>

		<!-- Return type of referenced task. -->
		<xsl:when test="xpdl2:Implementation/xpdl2:Reference">
			<xsl:variable name="refId" select="xpdl2:Implementation/xpdl2:Reference/@ActivityId"/>
			
			<xsl:variable name="refAct" select="$activity/../xpdl2:Activity[@Id = $refId]"/>
			
			<xsl:choose>
				<xsl:when test="$refAct/xpdl2:Implementation/xpdl2:Task/xpdl2:TaskService">
					<xsl:text>TASK_SERVICE</xsl:text>
				</xsl:when>
		
				<xsl:when test="$refAct/xpdl2:Implementation/xpdl2:Task/xpdl2:TaskScript">
					<xsl:text>TASK_SERVICE</xsl:text>
				</xsl:when>
		
				<xsl:when test="$refAct/xpdl2:Implementation/xpdl2:Task/xpdl2:TaskManual or $refAct/xpdl2:Implementation/xpdl2:Task/xpdl2:TaskUser">
					<xsl:text>TASK_USER</xsl:text>
				</xsl:when>
		
				<xsl:when test="$refAct/xpdl2:Implementation/xpdl2:SubFlow">
					<xsl:text>TASK_SUBFLOW</xsl:text>
				</xsl:when>

				<xsl:otherwise>
					<xsl:text>TASK_PLAIN</xsl:text>
				</xsl:otherwise>
			</xsl:choose>
			
		</xsl:when>
		<!-- End of reference task -->


		<xsl:when test="$activity/xpdl2:Event/xpdl2:StartEvent">
			<xsl:text>EVENT_START</xsl:text>
		</xsl:when>

		<xsl:when test="$activity/xpdl2:Event/xpdl2:EndEvent">
			<xsl:choose>
				<xsl:when test="$activity/xpdl2:Event/xpdl2:EndEvent/@Result = 'Cancel'">
					<xsl:text>EVENT_END_CANCEL</xsl:text>
				</xsl:when>
				<xsl:otherwise>
					<xsl:text>EVENT_END</xsl:text>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:when>

		<xsl:when test="$activity/xpdl2:Event/xpdl2:EndEvent">
			<xsl:text>GATEWAY_AND</xsl:text>
		</xsl:when>

		<xsl:when test="$activity/xpdl2:Event/xpdl2:IntermediateEvent">
			<xsl:text>GATEWAY_OR</xsl:text>
		</xsl:when>

		<!-- Default is plain task. -->
		<xsl:otherwise>
			<xsl:text>TASK_PLAIN</xsl:text>
		</xsl:otherwise>
		
	</xsl:choose>
	
</xsl:template>


<!--
============================================================================
Formal Parameter - Swap Name for Id (no name in v1) - 

Also adds params inherited from interface.
============================================================================
-->
<xsl:template name="OutputFormalParameters">
	<xsl:param name="allFormalParams"/>

	<xsl:if test="count($allFormalParams) > 0">
		<xpdl:FormalParameters>
		
			<xsl:for-each select="$allFormalParams">
				<xsl:variable name="v2Name" select="@Name"/>
				
				<xsl:variable name="tokenName">
					<xsl:call-template name="FormalParameterName">
						<xsl:with-param name="str" select="$v2Name"/>
					</xsl:call-template>	
				</xsl:variable>
				
				<xsl:variable name="v1Id">
					<xsl:choose>
						<!-- v1 has no 'name attr' so put v2 name in v1 Id IF there is a name AND it is unique -->
						<xsl:when test="$v2Name != '' and count(../xpdl2:FormalParameter[@Name = $v2Name]) = 1">
							<xsl:value-of select="$tokenName"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="@Id"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
			
				<!-- Note this also ditches the 'is array' attribute -->
				<xpdl:FormalParameter Id="{$v1Id}" Mode="{@Mode}">
					<xsl:apply-templates select="@ReadOnly"/>
					<xsl:apply-templates select="xpdl2:DataType"/>
					<xpdl:Description><xsl:value-of select="@Name"/></xpdl:Description>
				</xpdl:FormalParameter>
			</xsl:for-each>
			
		</xpdl:FormalParameters>

	</xsl:if>
				
</xsl:template>

<xsl:template name="FormalParameterName">
	<xsl:param name="str"/>

	<xsl:call-template name="AlphaNumOnly">
		<xsl:with-param name="str" select="$str"/>
		<xsl:with-param name="extraAllowedChars" select="'_'"/>
	</xsl:call-template>	
</xsl:template>

<!--
============================================================================
============================================================================
============================================================================
============================================================================

WHOLE BUNCH OF TEMPLATES DESIGNED SIMPLY TO IGNORE GIVEN ATTRIBUTES AND
ELEMENTS THAT ARE NOT SUPPORTED IN XPDL v1

THIS ALLOWS THE ELEMENTS THAT CONTAIN THEM TO DO APPLY-TEMPLATES AND JUST
MISS OUT THE BITS THAT ARE SPECIALLY HANDLED HERE

============================================================================
============================================================================
============================================================================
============================================================================
-->
<xsl:template match="xpdl2:DeclaredType/@Name"/>
<xsl:template match="xpdl2:ExternalPackage/@Id | xpdl2:ExternalPackage/@Name"/>
<xsl:template match="xpdl2:Application/xpdl2:Type"/>
<xsl:template match="xpdl2:FormalParameter/xpdl2:Length"/>

<xsl:template match="xpdl2:PartnerLinkTypes"/>
<xsl:template match="xpdl2:Pools"/>
<xsl:template match="xpdl2:MessageFlows"/>
<xsl:template match="xpdl2:Associations"/>
<xsl:template match="xpdl2:Artifacts"/>

<!-- Need to carry over activity sets for now for iProcess <xsl:template match="xpdl2:ActivitySets"/>-->
<xsl:template match="xpdl2:Assignments"/>
<xsl:template match="xpdl2:PartnerLinks"/>
<xsl:template match="xpdl2:WorkflowProcess/xpdl2:Object"/>
<xsl:template match="xpdl2:WorkflowProcess/xpdl2:Extensions"/>
<xsl:template match="xpdl2:NodeGraphicsInfos"/>
<xsl:template match="xpdl2:ConnectorGraphicsInfos"/>

<!-- Don't output the documentation url extension -->
<xsl:template match="xpdl2:Description">
	<xpdl:Description>
		<xsl:value-of select="."/>
	</xpdl:Description>
</xsl:template>


<!--
============================================================================
For the given input node, look for ##other elements and attributesfrom v2 xml and add them as v1 extended
attributes...
##other Attributes = <xpdl:ExtendedAttribute Name="[input element name]_[##other xmlns prefix]_[attribute name]" Value="[attr value]"/>

##other Elements = <xpdl:ExtendedAttribute Name="[input element name]_[##other xmlns prefix]_[element name]">
								<[ copy of element name with no prefixes] />
							</xpdl:ExtendedAttribute>
============================================================================
-->
<xsl:template name="HashOtherToExtAttr">
	<xsl:param name="input"/>
	<xsl:param name="outputXpdl2Nodes" select="'false'"/>
	<xsl:param name="attributesOnly" select="'false'"/>

	<xsl:if test="count($input) = 1">
	
		<xsl:variable name="inputName" select="local-name($input)"/>

		<!-- Attributes... -->
		<xsl:for-each select="$input/@*">
			<xsl:variable name="ns" select="substring-before(name(), ':')"/>
			<xsl:variable name="name" select="local-name()"/>
	
			<!-- Ignore xpdl2 base attributes -->
			<xsl:if test="$outputXpdl2Nodes = 'true' or ($ns != '' and $ns != 'xpdl2')">
				<xsl:variable name="extName"><xsl:value-of select="$inputName"/>_<xsl:value-of select="$ns"/>_<xsl:value-of select="$name"/></xsl:variable>
				
				<xpdl:ExtendedAttribute Name="{$extName}" Value="{.}"/>
				
			</xsl:if>
		</xsl:for-each>

		<!-- Elements... -->
		<xsl:if test="$attributesOnly != 'true'">
			<xsl:for-each select="$input/*">
				<xsl:variable name="ns" select="substring-before(name(), ':')"/>
				<xsl:variable name="name" select="local-name()"/>
		
				<!-- Ignore xpdl2 base elements -->
				<xsl:if test="$outputXpdl2Nodes = 'true' or ($ns != '' and $ns != 'xpdl2')">
					<xsl:variable name="extName"><xsl:value-of select="$inputName"/>_<xsl:value-of select="$ns"/>_<xsl:value-of select="$name"/></xsl:variable>
					
					<xpdl:ExtendedAttribute Name="{$extName}">
						
						<!-- Copy all attributes and elements without prefix -->
						<xsl:call-template name="CopyNodeWithoutPrefix">
							<xsl:with-param name="input" select="."/>
						</xsl:call-template>
					</xpdl:ExtendedAttribute>
					
				</xsl:if>
			</xsl:for-each>
		</xsl:if>
		
	</xsl:if>

</xsl:template>




<!--
=============================================================================================================
	Template:	AlphaNumOnly

	Params:		str						String to convert
						maxlen					Max length of output [optional]
						stripLeadingNum	Strip Leading numerics.
						extraAllowedChars	Extra characters (other than alpha numeric) to allow.
	
	Outputs str after stripping non-alpha-numeric chars (a-z, A-Z, 0-9) and [optionally] truncating to maxlen characters.
=============================================================================================================
-->
<xsl:template name="AlphaNumOnly">
	<xsl:param name="str"/>
	<xsl:param name="maxlen" select="'0'"/>
	<xsl:param name="stripLeadingNum" select="'0'"/>
	<xsl:param name="extraAllowedChars" select="''"/>
	
	<!-- Strip non alpha's -->
	<xsl:variable name="alphaNumStr">
		<xsl:call-template name="internalAlphaNumOnly"><xsl:with-param name="str" select="$str"/><xsl:with-param name="stripLeadingNum" select="$stripLeadingNum"/><xsl:with-param name="extraAllowedChars" select="$extraAllowedChars"/></xsl:call-template>
	</xsl:variable>
	
	<!-- If maxlen provided then truncate... -->
	<xsl:choose>
		<xsl:when test="$maxlen > 0">
			<xsl:value-of select="substring($alphaNumStr, 1, $maxlen)"/>
		</xsl:when>
		<xsl:otherwise>
			<xsl:value-of select="$alphaNumStr"/>
		</xsl:otherwise>
	</xsl:choose>
	
</xsl:template>

<!--
============================================================================
Copy all attributes and elements under given input without xmlns prefix.
============================================================================
-->
<xsl:template name="CopyNodeWithoutPrefix">
	<xsl:param name="input"/>

	<xsl:if test="count($input) > 0">
		<xsl:variable name="localName" select="local-name($input)"/>
		
		<xsl:element name="{$localName}">
	
			<!-- copy attrs -->
			<xsl:for-each select="$input/@*">
				<xsl:variable name="attrName" select="local-name(.)"/>
				<xsl:attribute name="{$attrName}"><xsl:value-of select="."/></xsl:attribute>
			</xsl:for-each>
	
			<!-- Copy plain text -->
			<xsl:copy-of select="$input/text()"/>
			
			<!-- Recurs for sub-elements. -->
			<xsl:for-each select="$input/*">
				<xsl:call-template name="CopyNodeWithoutPrefix">
					<xsl:with-param name="input" select="."/>
				</xsl:call-template>
			</xsl:for-each>
	
		</xsl:element>
	</xsl:if>
</xsl:template>

<!--
=============================================================================================================
	Template:	internalAlphaNumOnly

	Params:		str						String to convert
						stripLeadingNum	Strip Leading numerics.
						extraAllowedChars	Extra characters (other than alpha numeric) to allow.
	
	Internal (recursive) parts of AlphaNUmOnly
=============================================================================================================
-->
<xsl:template name="internalAlphaNumOnly">
	<xsl:param name="str"/>
	<xsl:param name="stripLeadingNum"/>
	<xsl:param name="extraAllowedChars" select="''"/>

	<!-- Check if the first character in the string is alpha numeric and/or if it is leading numeric. -->
	<xsl:variable name="chr1" select="substring($str,1,1)"/>
	
	<!-- This will cause all alpha-numerics NOT to be transferred into isAlphaNum - therefore if we pass it a valid alpha num then after it will not be there -->
	<xsl:variable name="allowedChars"><xsl:value-of select="$lowCaseChars"/><xsl:value-of select="$upCaseChars"/><xsl:value-of select="$numbers"/><xsl:value-of select="$extraAllowedChars"/></xsl:variable>
	<xsl:variable name="notAlphaNum" select="translate($chr1, $allowedChars, '')"/>

	<!-- Next, work out whether we should stop ignoring leading numerics (i.e. if we have now come to the first non-alpha...) -->
	<xsl:variable name="nowStripLeadNum">
		<xsl:choose>
			<xsl:when test="$notAlphaNum = '' and translate($chr1, $numbers, '') != ''">
				<!-- We have an alphanum character that isn't a numeric, stop ignoring leading numerics -->
				<xsl:text>0</xsl:text>
			</xsl:when>
			<xsl:otherwise>
					<!-- Not switching ignore off this time so just go with whatever the current requirement is. -->
					<xsl:value-of select="$stripLeadingNum"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:variable>


	<!-- If the alpha num was translated to nothing by the above then chr1 was alpha numeric -->
	<xsl:if test="$notAlphaNum = ''">
		<xsl:choose>
			<xsl:when test="$stripLeadingNum > 0 and translate($chr1, $numbers, '') = ''">
				<!-- If we are currently stripping leading numerics and this character is a numberthen Don't output the character -->
			</xsl:when>
			<xsl:otherwise>
				<!-- We aren't ignoring leading numerics or it isn't a numeric so output the character. -->
				<xsl:value-of select="$chr1"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:if>
	
	<!-- now recurs for next character -->
	<xsl:if test="string-length($str) > 1">
		<xsl:call-template name="internalAlphaNumOnly"><xsl:with-param name="str" select="substring($str,2)"/><xsl:with-param name="stripLeadingNum" select="$nowStripLeadNum"/><xsl:with-param name="extraAllowedChars" select="$extraAllowedChars"/></xsl:call-template>
	</xsl:if>
	
</xsl:template>
		
<!-- 
============================================
Remove sub-elements of BasicType in TypeDeclarations.
============================================
-->
<xsl:template match="xpdl2:BasicType">
	<xpdl:BasicType>
		<xsl:apply-templates select="@*"/>
	</xpdl:BasicType>
</xsl:template>


<!--  
====================================================================================================
XPD-7321: Ensure that Participant/ParticipantType is set (which it won't be for Org Model Refs).
otherwise interaction engine will blow up.
===================================================================================================
-->
<xsl:template match="xpdl2:Participant">
	<xpdl:Participant>
		<xsl:apply-templates select="@*"/>

		<xsl:if test="not(xpdl2:ParticipantType/@Type)">
			<xpdl:ParticipantType Type="ORGANIZATIONAL_UNIT"/>
		</xsl:if>	

		<xsl:apply-templates select="node()"/>

	</xpdl:Participant>

</xsl:template>

<!-- 
============================================
By default replace elements with xpdl2 prefix with xpdl 1 prefix
SO BE CAREFUL WHAT YOU DO apply-templates on
============================================
-->
<xsl:template match="xpdl2:*">
	<xsl:param name="allFormalParams"/>

	<xsl:variable name="elementName" select="local-name(.)"/>

	<!-- Here, if we want to we could filter out nodes we definitely don't want but in general it is up to the code above NOT to do <apply-templates> for things it's not certain about. -->
	<xsl:choose>
		<xsl:when test="elementName = 'EXCLUDE DUMMY'"></xsl:when>
		
		<xsl:otherwise>
			<xsl:element name="xpdl:{$elementName}">
				<xsl:apply-templates select="@* | node()">
					<xsl:with-param name="allFormalParams" select="$allFormalParams"/>
				</xsl:apply-templates>
			</xsl:element>
		</xsl:otherwise>
	</xsl:choose>
	
</xsl:template>


<!-- 
============================================
Don't include xpdExt attributes in output
============================================
-->
<xsl:template match="@xpdExt:*">
</xsl:template>

<!-- 
============================================
By default copy all elements not caught elsewhere.
SO BE CAREFUL WHAT YOU DO apply-templates on
============================================
-->
<xsl:template match="@* | node()">
	<xsl:param name="allFormalParams"/>
	
	<xsl:copy>
		<xsl:apply-templates select="@* | node()">
			<xsl:with-param name="allFormalParams" select="$allFormalParams"/>
		</xsl:apply-templates>
	</xsl:copy>

</xsl:template>

</xsl:stylesheet>

