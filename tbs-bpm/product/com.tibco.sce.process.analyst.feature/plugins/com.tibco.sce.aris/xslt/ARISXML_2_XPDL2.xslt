<?xml version="1.0" encoding="UTF-8"?>
<!--
	===================================================================================================================
	XSLT:		ARISXML_2_iPS_xpdl.xslt
	
	DESCRIPTION:
	This xslt will convert ARIS XML to iPS XPDL2
	
	===================================================================================================================
-->

<!-- Define constants -->
<!DOCTYPE xsl:stylesheet [
	<!ENTITY crlf							'&#xA;'>
	<!ENTITY arisParticROLE		'OT_POS'>
	<!ENTITY particROLE				'ROLE'>
	<!ENTITY arisParticHUMAN		'OT_PERS OT_PERS_TYPE OT_GRP OT_EMPL_INST'>
	<!ENTITY particHUMAN			'HUMAN'>
	<!ENTITY arisParticOU			'OT_ORG_UNIT OT_ORG_UNIT_TYPE'>
	<!ENTITY particOU					'ORGANIZATIONAL_UNIT'>
	<!ENTITY arisParticSYSTEM	'OT_SYS_ORG_UNIT OT_SYS_ORG_UNIT_TYPE OT_APPL_SYS OT_APPL_SYS_CLS OT_APPL_SYS_TYPE OT_MOD_TYPE OT_MOD'>
	<!ENTITY particSYSTEM			'SYSTEM'>
	<!ENTITY arisParticipants		'&arisParticROLE; &arisParticHUMAN; &arisParticOU; &arisParticSYSTEM;'>
	<!ENTITY arisDataFields			'OT_ERM_ATTR OT_TECH_TRM OT_ATTR_TYPE_GRP'>
	<!ENTITY arisFunctions			'OT_FUNC'>
	<!ENTITY arisRules					'OT_RULE'>
	<!ENTITY arisRisks					'OT_RISK'>
	<!ENTITY arisEvents				'OT_EVT'>
	<!ENTITY arisKnownObjs		'&arisParticipants; &arisDataFields; &arisFunctions; &arisRules; &arisEvents;'>
	<!ENTITY true							'TRUE'>
	<!ENTITY false						'FALSE'>
	<!ENTITY arisTextDefAttrText						'TEXT'>
	<!ENTITY arisTextDefAttrModel					'MODELATTR'>
	<!ENTITY symbolFileLocation	'/images/'>
	<!ENTITY symbolFileExt			'.png'>
	<!ENTITY activityHeight			'68'>
	<!ENTITY activityWidth			'90'>
	<!ENTITY dataObjHeight			'34'>
	<!ENTITY dataObjWidth			'28'>
]>

<xsl:stylesheet version="1.0"
	xmlns:xpdl="http://www.wfmc.org/2002/XPDL1.0"
	xmlns:xpdl2="http://www.wfmc.org/2008/XPDL2.1"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xpdExt="http://www.tibco.com/XPD/xpdExtension1.0.0"
	exclude-result-prefixes="xpdl">

	

	<xsl:output method="xml" version="1.0" encoding="UTF-8"
		indent="yes" />

	<!-- This factor is applied to all X Y coordinates to scale the ARIS model to better fit in the XPD -->
	<xsl:variable name="factor">0.4</xsl:variable>

	<!--
		===================================================================================================================
		XSLT processing start point (process whole document)
		===================================================================================================================
	-->
	<xsl:template match="/">

		<xsl:apply-templates />

	</xsl:template>

	<!--
		===================================================================================================================
		AML = head element of the ARIS XML document.  This will add the xpdl2:Package element
		===================================================================================================================
	-->
	<xsl:template match="AML">

		<xpdl2:Package Id="{Group/@Group.ID}"
			Name="{Header-Info/@DatabaseName}">
			<!-- Output the package header -->
			<xsl:apply-templates select="Header-Info" />
			<!-- Output redefinable header -->
			<xsl:apply-templates select="Database" />
			<!-- Output main section -->
			<xsl:call-template name="outputMain">
				<xsl:with-param name="groups" select="//Group" />
			</xsl:call-template> 

			<!-- Add the package extended attributes -->
			<xpdl2:ExtendedAttributes>
				<xpdl2:ExtendedAttribute Name="CreatedBy"
					Value="TIBCO Business Studio" />
				<!-- Format Version 3 = Studio 2.1, but Aris wizard will chain migration to this automatically -->
				<!-- Jump to format version 5 to update the new XPDL namespace in this XSLT -->
				<xpdl2:ExtendedAttribute Name="FormatVersion" Value="5"/>
			</xpdl2:ExtendedAttributes>

		</xpdl2:Package>

	</xsl:template>

	<!--
		===================================================================================================================
		Header-Info = this will add the xpdl:PackageHeader
		===================================================================================================================
	-->
	<xsl:template match="Header-Info">

		<xpdl2:PackageHeader>
			<!-- Set language -->
			<xsl:choose>
				<xsl:when test="../Language/@LocaleId='1031'">
					<xsl:attribute name="xpdExt:Language">de</xsl:attribute>
				</xsl:when>
				<xsl:when test="../Language/@LocaleId='1033'">
					<xsl:attribute name="xpdExt:Language">en_US</xsl:attribute>
				</xsl:when>
				<xsl:when test="../Language/@LocaleId='1040'">
					<xsl:attribute name="xpdExt:Language">it</xsl:attribute>
				</xsl:when>
			</xsl:choose>
			<xpdl2:XPDLVersion>2.0</xpdl2:XPDLVersion>
			<xpdl2:Vendor>TIBCO</xpdl2:Vendor>
			<xpdl2:Created>
				<xsl:value-of select="@CreateTime" /><xsl:text> </xsl:text><xsl:value-of select="@CreateDate" />
			</xpdl2:Created>
			<xpdl2:Description />
			<xpdl2:Documentation />
			<!-- Set currency, to some degree a guess based on the locale -->
			<xsl:choose>
				<xsl:when test="../Language/@LocaleId='1031'">
					<xpdl2:CostUnit>EUR</xpdl2:CostUnit>
				</xsl:when>
				<xsl:when test="../Language/@LocaleId='1033'">
					<xpdl2:CostUnit>USD</xpdl2:CostUnit>
				</xsl:when>
				<xsl:when test="../Language/@LocaleId='1040'">
					<xpdl2:CostUnit>EUR</xpdl2:CostUnit>
				</xsl:when>
			</xsl:choose>			
		</xpdl2:PackageHeader>

	</xsl:template>

	<!--
		===================================================================================================================
		Database = this will add the xpdl:RedefinableHeader
		===================================================================================================================
	-->
	<xsl:template match="Database">

		<xpdl2:RedefinableHeader PublicationStatus="UNDER_TEST">
			<xpdl2:Author>
				<xsl:value-of
					select="AttrDef[@AttrDef.Type = 'AT_CREATOR']/AttrValue" />
			</xpdl2:Author>
			<!-- Business Version -->
			<xpdl2:Version>1.0</xpdl2:Version>
		</xpdl2:RedefinableHeader>

	</xsl:template>

	<!--
		===================================================================================================================
		outputMain = This will output the main section of the xpdl including participants, datafields, artifacts and the processes
		===================================================================================================================
	-->
	<xsl:template name="outputMain">
		<xsl:param name="groups" />
		
		<xsl:variable name="objDef" select="$groups/ObjDef" />
		<xsl:variable name="textDef" select="FFTextDef" />		
		<xsl:variable name="models" select="$groups/Model[starts-with (@Model.Type, 'MT_EEPC')]" />
		
		<!-- Add any participants -->
		<xpdl2:Participants>
			<xsl:apply-templates select="$objDef" mode="participants" />
		</xpdl2:Participants>

		<!-- Add any data fields -->
		<xpdl2:DataFields>
			<xsl:apply-templates select="$objDef" mode="dataFields" />
		</xpdl2:DataFields>
		
		<!--
			Pools, associations and artifacts appear only once in a package and contain all processes relevant data
		-->
		
		<!-- Add pools -->
		<xpdl2:Pools>
			<!-- For each model there should be a pool -->
			<xsl:for-each select="$models">
				<!-- calculate any X or Y shift required in the model to stop it being drawn off the left or top border of the editor -->
				<xsl:variable name="shiftX">
					<xsl:call-template name="calculateXShift">
						<xsl:with-param name="model" select="." />
					</xsl:call-template>
				</xsl:variable>
		
				<xsl:variable name="shiftY">
					<xsl:call-template name="calculateYShift">
						<xsl:with-param name="model" select="." />
					</xsl:call-template>
				</xsl:variable>
			
				<xsl:apply-templates select="." mode="pool">
					<xsl:with-param name="objDefinitions" select="$objDef" />
					<xsl:with-param name="shiftX" select="$shiftX" />
					<xsl:with-param name="shiftY" select="$shiftY" />
				</xsl:apply-templates>
			</xsl:for-each>
		</xpdl2:Pools>
	
		<!-- Add associations -->
		<xpdl2:Associations>
			<!-- Add associations for each model -->
			<xsl:for-each select="$models">
				<!-- calculate any X or Y shift required in the model to stop it being drawn off the left or top border of the editor -->
				<xsl:variable name="shiftX">
					<xsl:call-template name="calculateXShift">
						<xsl:with-param name="model" select="." />
					</xsl:call-template>
				</xsl:variable>
		
				<xsl:variable name="shiftY">
					<xsl:call-template name="calculateYShift">
						<xsl:with-param name="model" select="." />
					</xsl:call-template>
				</xsl:variable>
				
				<xsl:apply-templates select="ObjOcc" mode="associations">
					<xsl:with-param name="objDefinitions" select="$objDef" />		
					<xsl:with-param name="shiftX" select="$shiftX" />
					<xsl:with-param name="shiftY" select="$shiftY" />		
				</xsl:apply-templates>
			</xsl:for-each>
		</xpdl2:Associations>
		
		<!-- Add artifacts -->
		<xpdl2:Artifacts>
			<!-- Add artifacts for each model -->
			<xsl:for-each select="$models">
				<!-- calculate any X or Y shift required in the model to stop it being drawn off the left or top border of the editor -->
				<xsl:variable name="shiftX">
					<xsl:call-template name="calculateXShift">
						<xsl:with-param name="model" select="." />
					</xsl:call-template>
				</xsl:variable>
		
				<xsl:variable name="shiftY">
					<xsl:call-template name="calculateYShift">
						<xsl:with-param name="model" select="." />
					</xsl:call-template>
				</xsl:variable>
			
				<xsl:apply-templates select="ObjOcc" mode="artifacts">
					<xsl:with-param name="models" select="$models" />
					<xsl:with-param name="objDefinitions" select="$objDef" />
					<xsl:with-param name="shiftX" select="$shiftX" />
					<xsl:with-param name="shiftY" select="$shiftY" />
				</xsl:apply-templates>
				<xsl:apply-templates select="FFTextOcc">
					<xsl:with-param name="textDefinitions" select="$textDef" />
					<xsl:with-param name="shiftX" select="$shiftX" />
					<xsl:with-param name="shiftY" select="$shiftY" />
				</xsl:apply-templates>
			</xsl:for-each>
		</xpdl2:Artifacts>
		
		<!-- Add each process -->
		<xpdl2:WorkflowProcesses>
			<xsl:for-each select="$models">
				<!-- calculate any X or Y shift required in the model to stop it being drawn off the left or top border of the editor -->
				<xsl:variable name="shiftX">
					<xsl:call-template name="calculateXShift">
						<xsl:with-param name="model" select="." />
					</xsl:call-template>
				</xsl:variable>
		
				<xsl:variable name="shiftY">
					<xsl:call-template name="calculateYShift">
						<xsl:with-param name="model" select="." />
					</xsl:call-template>
				</xsl:variable>
				
				<!-- Add processes for each EPC model -->
					<xsl:apply-templates select=".">
					<xsl:with-param name="objDefinitions" select="$objDef" />
						<xsl:with-param name="shiftX" select="$shiftX" />
						<xsl:with-param name="shiftY" select="$shiftY" />
					</xsl:apply-templates>
			</xsl:for-each>
		</xpdl2:WorkflowProcesses>
		
	</xsl:template>

	<!--
		===================================================================================================================
		ObjDef (participants) - Identify participants and add them to the xpdl
		===================================================================================================================
	-->
	<xsl:template match="ObjDef" mode="participants">

		<!-- Get ID -->
		<xsl:variable name="objDefID" select="@ObjDef.ID" />
		 
		<!-- Only interested in participant type objects -->
		<xsl:if test="contains('&arisParticipants;', @TypeNum)">
			<!-- Only interested with objects that have an occurrence within one of the models -->
			<!--<xsl:if 
				test="count (..//Model/ObjOcc[@ObjDef.IdRef = $objDefID]) &gt; 0">-->
				<!-- Identify participants -->
				
				<xsl:variable name="type">
					<xsl:choose>
						<xsl:when
							test="contains('&arisParticROLE;', @TypeNum)">
							<xsl:text>&particROLE;</xsl:text>
						</xsl:when>
						<xsl:when
							test="contains('&arisParticHUMAN;', @TypeNum)">
							<xsl:text>&particHUMAN;</xsl:text>
						</xsl:when>
						<xsl:when
							test="contains('&arisParticOU;', @TypeNum)">
							<xsl:text>&particOU;</xsl:text>
						</xsl:when>
						<xsl:when
							test="contains('&arisParticSYSTEM;', @TypeNum)">
							<xsl:text>&particSYSTEM;</xsl:text>
						</xsl:when>
					</xsl:choose>
				</xsl:variable>

				<!-- If participant then add it -->
				<xsl:if test="$type != ''">
					<xpdl2:Participant Id="{@ObjDef.ID}">
						<xsl:attribute name="Name">
							<xsl:apply-templates select="AttrDef[@AttrDef.Type = 'AT_NAME']" mode="getvalue" />
						</xsl:attribute>
						<xpdl2:ParticipantType Type="{$type}" />

						<!-- If there is a description then add it -->
						<xsl:variable name="AttrDesc">
							<xsl:apply-templates select="AttrDef[@AttrDef.Type = 'AT_DESC']" mode="getvalue" />
						</xsl:variable>
				
						<xsl:if test="$AttrDesc">
							<xpdl2:Description>
								<xsl:value-of select="normalize-space($AttrDesc)"/>
							</xpdl2:Description>
						</xsl:if>
						
						<xpdl2:ExtendedAttributes>
		                    <xsl:element name="xpdl2:ExtendedAttribute">
		                        <xsl:attribute name="Name">ARIS_GUID</xsl:attribute>
		                        <xsl:attribute name="Value"><xsl:value-of select="GUID"/></xsl:attribute>
		                    </xsl:element> 
							<xsl:element name="xpdl2:ExtendedAttribute">
		                        <xsl:attribute name="Name">ARIS_DEFID</xsl:attribute>
		                        <xsl:attribute name="Value"><xsl:value-of select="@ObjDef.ID"/></xsl:attribute>
		                    </xsl:element>                           
		                    <xsl:apply-templates select="AttrDef"/>
                		</xpdl2:ExtendedAttributes>
					</xpdl2:Participant>
				</xsl:if>

			<!--</xsl:if> -->
		</xsl:if>

	</xsl:template>

	<!--
		===================================================================================================================
		ObjDef (dataFields) - Identify data fields and add them to the xpdl
		===================================================================================================================
	-->
	<xsl:template match="ObjDef" mode="dataFields">

		<!-- Get object ID -->
		<xsl:variable name="objDefID" select="@ObjDef.ID" />
		<!--  Only interested in data types -->
		<xsl:if test="contains ('&arisDataFields;', @TypeNum)">
			<!-- Only interested with objects that have an occurrence within one of the models -->
			<xsl:if 			test="count (..//Model/ObjOcc[@ObjDef.IdRef = $objDefID]) &gt; 0">
				<xpdl2:DataField Id="{$objDefID}">
				
				<xsl:variable name="FieldName">
					<xsl:apply-templates select="AttrDef[@AttrDef.Type = 'AT_NAME']" mode="getvalue" />
				</xsl:variable>	
						
				<xsl:attribute name="Name">
					<xsl:call-template name="StripSpaces" >
						<xsl:with-param name="InputString" select="$FieldName"/>
					</xsl:call-template> 
				</xsl:attribute>
				
				<xsl:attribute name="xpdExt:DisplayName">
					<xsl:value-of select="$FieldName" />
				</xsl:attribute>

     		 	<xpdl2:DataType>
        			<xpdl2:BasicType Type="STRING">
          			<xpdl2:Length>50</xpdl2:Length>
        			</xpdl2:BasicType>
      			</xpdl2:DataType>
				
				<!--
					<xpdl2:DataType>
						<xpdl2:BasicType Type="REFERENCE" />
					</xpdl2:DataType>
					
					-->
					<!-- If there is a description then add it -->
					
					<xsl:variable name="AttrDesc">
					 	<xsl:if test = "AttrDef[@AttrDef.Type = 'AT_DESC']">
							<xsl:apply-templates select="AttrDef[@AttrDef.Type = 'AT_DESC']" mode="getvalue" />
					 	</xsl:if>
					</xsl:variable>
					<xsl:if test="$AttrDesc">
						<xpdl2:Description>
								<xsl:value-of select="normalize-space($AttrDesc)"/>
						</xpdl2:Description>
					</xsl:if>
	                <xpdl2:ExtendedAttributes>
	                    <xsl:element name="xpdl2:ExtendedAttribute">
	                        <xsl:attribute name="Name">ARIS_GUID</xsl:attribute>
	                        <xsl:attribute name="Value"><xsl:value-of select="GUID"/></xsl:attribute>
	    		        </xsl:element>                            
				    </xpdl2:ExtendedAttributes>

				</xpdl2:DataField>
			</xsl:if>
		</xsl:if>

	</xsl:template>

	<!--
		===================================================================================================================
		Model (pool) = add the default pool and lane to the workflow process.  The max Y offset is calculated by sorting the Y offsets
		of the activities and any text objects in the process and getting the highest value - this will make the size value of the lane.
		===================================================================================================================
	-->
	<xsl:template match="Model" mode="pool">
		<xsl:param name="objDefinitions" />
		<xsl:param name="shiftX" />
		<xsl:param name="shiftY" />
		
		<xpdl2:Pool Id="pool{generate-id()}" Name="Pool"
			BoundaryVisible="true" Process="{@Model.ID}">
			<xpdl2:Lanes>
				<!-- Work out the max Y offset to adjust the size of the default lane -->
						<xsl:variable name="maxYOffset">
							<xsl:choose>
								<xsl:when test="ObjOcc | FFTextOcc">
									<xsl:for-each select="ObjOcc | FFTextOcc">
										<xsl:sort data-type="number" order="descending"
											select="Position/@Pos.Y" />
										<!-- Get the first item in the sorted list - this should be the max Y offset -->
										<xsl:if test="position() = 1">
											<xsl:value-of
												select="round (Position/@Pos.Y * $factor)" />
										</xsl:if>
									</xsl:for-each>
								</xsl:when>
								<xsl:otherwise>
									<!-- No model so set lane size to 0 -->
									<xsl:text>0</xsl:text>
								</xsl:otherwise>
							</xsl:choose>
						</xsl:variable>						
						
						<xpdl2:Lane Id="lane{generate-id()}" Name="Lane">
						<xpdl2:NodeGraphicsInfos>
							<!-- Add arbitrary value to show the lowest activity in the list without it overlapping the lane boundary -->
							<xpdl2:NodeGraphicsInfo Height="{$maxYOffset + $shiftY + 100}" ToolId="XPD" />
						</xpdl2:NodeGraphicsInfos>
					</xpdl2:Lane>
			</xpdl2:Lanes>
		</xpdl2:Pool>

	</xsl:template>
	
<!--
		===================================================================================================================
		ObjOcc (associations) = Add all associations to data objects
		===================================================================================================================
	-->
	<xsl:template match="ObjOcc" mode="associations">
		<xsl:param name="objDefinitions"/>
		<xsl:param name="shiftX" />
		<xsl:param name="shiftY" />
		
		<xsl:variable name="objOccId" select="@ObjOcc.ID"/>
		
		<xsl:variable name="objDefId" select="@ObjDef.IdRef" />
		<xsl:variable name="objDef" select="$objDefinitions[@ObjDef.ID = $objDefId]"/>

		<!-- Check if this object will be drawn as an activity -->
		<xsl:variable name="activity">
			<xsl:call-template name="isActivity">
				<xsl:with-param name="objOcc" select="." />
				<xsl:with-param name="objDef" select="$objDef" />
				<xsl:with-param name="allObjOccs" select="../ObjOcc" />
			</xsl:call-template>
		</xsl:variable>
		
		<!-- If this object is not drawn as an activity then it will be a data object, participant or data field -->
		<xsl:if test="$activity = '&false;'">
			<!-- If this is a participant or data type then ignore -->
			<xsl:if test="not (contains('&arisParticipants; &arisDataFields;', $objDef/@TypeNum))">
				<!-- Get source occurrence ID -->
				<xsl:variable name="sourceOccId">
					<xsl:choose>
						<xsl:when test="CxnOcc">
							<!-- This object is the source object -->
							<xsl:value-of select="$objOccId"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="../ObjOcc[CxnOcc[@ToObjOcc.IdRef = $objOccId]]/@ObjOcc.ID"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				
				<!-- Get the target occurrence ID -->
				<xsl:variable name="targetOccId">
					<xsl:choose>
						<xsl:when test="CxnOcc">
							<xsl:variable name="toObjOccId" select="CxnOcc/@ToObjOcc.IdRef" />
							<xsl:value-of select="../ObjOcc[@ObjOcc.ID = $toObjOccId]/@ObjOcc.ID" />
						</xsl:when>
						<xsl:otherwise>
							<xsl:if test="../ObjOcc/CxnOcc[@ToObjOcc.IdRef = $objOccId]">
								<!-- This object is the target -->
								<xsl:value-of select="$objOccId" />
							</xsl:if>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				
				<!-- If there is a source and target then add association -->
				<xsl:if test="$sourceOccId != '' and $targetOccId != ''">
					
					<!-- Get the source and target object occurrences -->
					<xsl:variable name="sourceOcc" select="../ObjOcc[@ObjOcc.ID = $sourceOccId]" />
					<xsl:variable name="targetOcc" select="../ObjOcc[@ObjOcc.ID = $targetOccId]" />
					
					<xsl:variable name="cxn" select="$sourceOcc/CxnOcc[@ToObjOcc.IdRef = $targetOccId]" />
					<xsl:variable name="cxnDef" select="$objDefinitions[@ObjDef.ID = $sourceOcc/@ObjDef.IdRef]/CxnDef[@CxnDef.ID = $cxn/@CxnDef.IdRef]" />
					
					<xpdl2:Association Id="{$cxn/@CxnOcc.ID}" Name="{$cxnDef/AttrDef[@AttrDef.Type = 'AT_DESC']/AttrValue}" AssociationDirection="None" 
							Source="{$sourceOcc/@ObjOcc.ID}" Target="{$targetOcc/@ObjOcc.ID}">
						<xpdl2:Object Id="{generate-id($cxn)}"/>
						<xpdl2:ConnectorGraphicsInfos>
							<xpdl2:ConnectorGraphicsInfo ToolId="XPD">
								<!-- Add any bendpoints if present -->
								<xsl:call-template name="insertBendPoints">
									<xsl:with-param name="positions" select="$cxn/Position" />
									<xsl:with-param name="fromObjOcc" select="$sourceOcc" />
									<xsl:with-param name="toObjOcc" select="$targetOcc" />
									<xsl:with-param name="shiftX" select="$shiftX" />
									<xsl:with-param name="shiftY" select="$shiftY" />
								</xsl:call-template>
							</xpdl2:ConnectorGraphicsInfo>
						</xpdl2:ConnectorGraphicsInfos>
					</xpdl2:Association>
				</xsl:if>
			</xsl:if>
		</xsl:if>
		
	</xsl:template>

	<!--
		===================================================================================================================
		ObjOcc (artifacts) = Add all data object artifacts.  Each object that will not be mapped as an
		activity, has only incoming or only outgoing connections, or no connections, will be a data object.
		===================================================================================================================
	-->
	<xsl:template match="ObjOcc" mode="artifacts">
		<xsl:param name="models" />
		<xsl:param name="objDefinitions" />
		<xsl:param name="shiftX" />
		<xsl:param name="shiftY" />
		
		<xsl:variable name="objDefId" select="@ObjDef.IdRef" />
		<xsl:variable name="objDef" select="$objDefinitions[@ObjDef.ID=$objDefId]"/>

		<!-- Check if this object will be drawn as an activity -->
		<xsl:variable name="activity">
			<xsl:call-template name="isActivity">
				<xsl:with-param name="objOcc" select="." />
				<xsl:with-param name="objDef" select="$objDef" />
				<xsl:with-param name="allObjOccs" select="../ObjOcc" />
			</xsl:call-template>
		</xsl:variable>

		<!-- If this object is not drawn as an activity then it will be a data object, participant or data field -->
		<xsl:if test="$activity = '&false;'">
			<!-- If this is a participant or data type then ignore -->
			<xsl:if test="not (contains('&arisParticipants; &arisDataFields;', $objDef/@TypeNum))">
			
				<!-- 
					If this object is linked to another process then get the name of the linked process so that it can be entered in the name and
					description of this data object to indicate the fact that it was linked.
				-->
				
				<xsl:variable name="linkedModel" select = "$models[@Model.ID = normalize-space($objDef/@LinkedModels.IdRefs)]" />
				
				
				<xsl:variable name="linkedModelName">				
						<xsl:if test = "count($linkedModel) > 0 " >
									<xsl:apply-templates select="$linkedModel/AttrDef[@AttrDef.Type = 'AT_NAME']" mode="getvalue" />
						</xsl:if>	
				</xsl:variable>				
							
				<xsl:variable name="linkedModelDesc">
						<xsl:if test="$objDef/@LinkedModels.IdRefs and $linkedModelName">
							Linked to process '<xsl:value-of select="$linkedModelName" />'
						</xsl:if>
				</xsl:variable>
			
				
				<!-- If there is a linked model then indicate this in the name -->

				<xsl:variable name="name">
					<xsl:choose>
						<xsl:when test="$linkedModelDesc != ''">
							 <xsl:apply-templates select="$objDef/AttrDef[@AttrDef.Type = 'AT_NAME']" mode="getvalue" />	 (<xsl:value-of select="$linkedModelDesc" />)
						</xsl:when>
						<xsl:otherwise>
							 <xsl:apply-templates select="$objDef/AttrDef[@AttrDef.Type = 'AT_NAME']" mode ="getvalue"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
		

				
				<xpdl2:Artifact Id="{@ObjOcc.ID}"
					Name="{normalize-space($name)}"
					ArtifactType="DataObject">
					
					<xsl:variable name="objOccId" select="@ObjOcc.ID" />
					
					<!-- Determine ProducedAtCompletion value - if this object is connected from another object then set to true -->
					<xsl:variable name="producedAtCompletion">
						<xsl:choose>
							<xsl:when test="../ObjOcc[CxnOcc/@ToObjOcc.IdRef = $objOccId]">
								<xsl:text>true</xsl:text>
							</xsl:when>
							<xsl:otherwise>
								<xsl:text>false</xsl:text>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:variable>
					
					<!-- Determine RequiredForStart value - if this object connects to another object then set to true -->
					<xsl:variable name="requiredForStart">
						<xsl:choose>
							<xsl:when test="CxnOcc">
								<xsl:text>true</xsl:text>
							</xsl:when>
							<xsl:otherwise>
								<xsl:text>false</xsl:text>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:variable>
					


				<xsl:variable name="AttrDesc">
					 <xsl:if test = "$objDef/AttrDef[@AttrDef.Type = 'AT_DESC']">
							<xsl:apply-templates select="$objDef/AttrDef[@AttrDef.Type = 'AT_DESC']" mode="getvalue" />
					 </xsl:if>
				</xsl:variable>
					
	 				<xpdl2:DataObject Id="{generate-id()}" ProducedAtCompletion="{$producedAtCompletion}" RequiredForStart="{$requiredForStart}">
						<!-- If there is a description then add it -->
						<xsl:variable name="description">
							<xsl:choose>
								<xsl:when test="$AttrDesc and $linkedModelDesc != ''">
									<xsl:value-of select="$AttrDesc"/>
									<xsl:text> </xsl:text>
									(<xsl:value-of select="$linkedModelDesc" />)
								</xsl:when>
								<xsl:when test="$AttrDesc">
									<xsl:value-of select="$AttrDesc"/>
								</xsl:when>
								<xsl:when test="$linkedModelDesc != ''">
									<xsl:value-of select="$linkedModelDesc" />
								</xsl:when>
							</xsl:choose>
						</xsl:variable>
						
						<xsl:if test="$description != '' or $objDef/SymbolGUID">
							<xsl:element name="xpdExt:DataObjectAttributes">
								<!-- If there is a symbol GUID then add external reference -->
								<xsl:if test="$objDef/SymbolGUID">
									<xsl:attribute name="xpdExt:ExternalReference">
										<xsl:text>&symbolFileLocation;</xsl:text>
										<xsl:value-of select="$objDef/SymbolGUID" />
										<xsl:text>&symbolFileExt;</xsl:text>
									</xsl:attribute>
								</xsl:if>
								<!-- Add description if there is one -->
								<xsl:if test="$description != ''">
									<xpdExt:Description>
										<xsl:value-of select="normalize-space($description)" />
									</xpdExt:Description>
								</xsl:if>
							</xsl:element>
						</xsl:if>
					  </xpdl2:DataObject>
					
					<xpdl2:NodeGraphicsInfos>
						<xpdl2:NodeGraphicsInfo Height="{&dataObjHeight;}"
							Width="{&dataObjWidth;}" LaneId="lane{generate-id(..)}"
							ToolId="XPD">

							<xsl:if test="contains('&arisRisks;', $objDef/@TypeNum)">

								<xsl:attribute name="FillColor">
									<xsl:text>255,128,128</xsl:text>
								</xsl:attribute>	
								
							</xsl:if>
							
							<!-- Add the coordinates -->
							<xsl:element name="xpdl2:Coordinates">
								<xsl:attribute name="XCoordinate">
									<xsl:call-template name="XPos">
										<xsl:with-param name="posX" select="Position/@Pos.X" />
										<xsl:with-param name="shiftX" select="$shiftX" />
									</xsl:call-template>
								</xsl:attribute>
								<xsl:attribute name="YCoordinate">
									<xsl:call-template name="YPos">
										<xsl:with-param name="posY" select="Position/@Pos.Y" />
										<xsl:with-param name="shiftY" select="$shiftY" />
									</xsl:call-template>
								</xsl:attribute>
							</xsl:element>
						</xpdl2:NodeGraphicsInfo>
					</xpdl2:NodeGraphicsInfos>
				</xpdl2:Artifact>
			</xsl:if>
		</xsl:if>

	</xsl:template>
	
	<!--
		===================================================================================================================
		FFTextOcc = Add all text artifacts.  Each object that will not be mapped as an
		activity will be a data object
		===================================================================================================================
	-->
	<xsl:template match="FFTextOcc">
		<xsl:param name="textDefinitions" />
		<xsl:param name="shiftX" />
		<xsl:param name="shiftY" />
		
		<xsl:variable name="textDefId" select="@FFTextDef.IdRef" />
		<xsl:variable name="textDef" select="$textDefinitions[@FFTextDef.ID = $textDefId]" />
		
		<!--
			Get the text value.  If this is a text object then get it's name, otherwise if it's a model attribute annotation then get 
			the model attribute.
		-->
		<xsl:variable name="text">
			<xsl:choose>
				<xsl:when test="$textDef/@IsModelAttr = '&arisTextDefAttrText;'">
					<!-- Free text -->
					<xsl:apply-templates select="$textDef/AttrDef[@AttrDef.Type = 'AT_NAME']"  />
					
				</xsl:when>
				<xsl:when test="$textDef/@IsModelAttr = '&arisTextDefAttrModel;'">
					<!-- Model attribute -->
					<xsl:variable name="attr">
						<xsl:value-of select="$textDef/AttrDef[@AttrDef.Type = 'AT_MODEL_AT']/AttrValue" />
					</xsl:variable>
					
					<xsl:if test="$attr != ''">
						<!-- Get the model attribute -->
						<xsl:value-of select="normalize-space(../AttrDef[@AttrDef.Type = $attr]/AttrValue)" />
					</xsl:if>
				</xsl:when>
			</xsl:choose>
		</xsl:variable>
		
		<!-- If there is text then add as an annotation -->
		<xsl:if test="$text != ''">
			<xpdl2:Artifact Id="{@FFTextOcc.ID}" ArtifactType="Annotation" TextAnnotation="{$text}">
			  <xpdl2:NodeGraphicsInfos>
				<xpdl2:NodeGraphicsInfo Height="20.0" LaneId="lane{generate-id(..)}" ToolId="XPD" Width="0.0">
				  <!-- Add the coordinates -->
							<xsl:element name="xpdl2:Coordinates">
								<xsl:attribute name="XCoordinate">
									<xsl:call-template name="XPos">
										<xsl:with-param name="posX" select="Position/@Pos.X" />
										<xsl:with-param name="shiftX"
											select="$shiftX" />
									</xsl:call-template>
								</xsl:attribute>
								<xsl:attribute name="YCoordinate">
									<xsl:call-template name="YPos">
										<xsl:with-param name="posY" select="Position/@Pos.Y" />
										<xsl:with-param name="shiftY" select="$shiftY" />
									</xsl:call-template>
								</xsl:attribute>
							</xsl:element>
				</xpdl2:NodeGraphicsInfo>
			  </xpdl2:NodeGraphicsInfos>
			</xpdl2:Artifact>
		</xsl:if>
		
		</xsl:template>
	
	<!--
		===================================================================================================================
		Model = Add WorkflowProcess for each model in this group
		===================================================================================================================
	-->
	<xsl:template match="Model">
		<xsl:param name="objDefinitions"/>
		<xsl:param name="shiftX" />
		<xsl:param name="shiftY" />
		
		<xsl:variable name="modelName">
			<xsl:apply-templates select="AttrDef[@AttrDef.Type = 'AT_NAME']" mode="getvalue" />
		</xsl:variable>	
		
		<xpdl2:WorkflowProcess Id="{@Model.ID}">
			<xsl:attribute name="Name">
				<xsl:call-template name="StripSpaces" >
					<xsl:with-param name="InputString" select="$modelName"/>
				</xsl:call-template> 
			</xsl:attribute>
			
			<xsl:attribute name="xpdExt:DisplayName">
				<xsl:value-of select="$modelName" />
			</xsl:attribute>

			<xpdl2:ProcessHeader>
				<xpdl2:Created><xsl:value-of select="AttrDef[@AttrDef.Type = 'AT_CREAT_TIME_STMP']/AttrValue" /></xpdl2:Created>
			</xpdl2:ProcessHeader>
			<xpdl2:RedefinableHeader />
			
			<!-- Add the activities -->
			<xpdl2:Activities>
				<xsl:apply-templates select="ObjOcc">
					<xsl:with-param name="objDefinitions" select="$objDefinitions" />
					<xsl:with-param name="shiftX" select="$shiftX" />
					<xsl:with-param name="shiftY" select="$shiftY" />
				</xsl:apply-templates>
			</xpdl2:Activities>
			
			<!-- Add the transitions -->
			<xpdl2:Transitions>
				<xsl:apply-templates  select="ObjOcc/CxnOcc">
					<xsl:with-param name="objDefinitions" select="$objDefinitions" />
					<xsl:with-param name="shiftX" select="$shiftX" />
					<xsl:with-param name="shiftY" select="$shiftY" />
				</xsl:apply-templates>

			</xpdl2:Transitions>
		</xpdl2:WorkflowProcess>
	</xsl:template>
	
<!--
===================================================================================================================
	ObjOcc = this will add the Activity entries
===================================================================================================================
-->	
	<xsl:template match="ObjOcc">
		<xsl:param name="objDefinitions" />
		<xsl:param name="shiftX" />
		<xsl:param name="shiftY" />
		
		<xsl:variable name="objOccId" select="@ObjOcc.ID" />
		
		<!-- Get the object definition of this occurrence -->
		<xsl:variable name="objDefId" select="@ObjDef.IdRef" />
		<xsl:variable name="objDef" select="$objDefinitions[@ObjDef.ID = $objDefId]" />
		
		<!-- Check if this object will be drawn as an activity -->
		<xsl:variable name="isActivity">
			<xsl:call-template name="isActivity">
				<xsl:with-param name="objOcc" select="." />
				<xsl:with-param name="objDef" select="$objDef" />
				<xsl:with-param name="allObjOccs" select="../ObjOcc" />
			</xsl:call-template>
		</xsl:variable>
		
		<!-- If this is an activity then draw it -->
		<xsl:if test="$isActivity = '&true;'">
	
		                      
			<xpdl2:Activity Id="{@ObjOcc.ID}" >

				<xsl:variable name="activityName">
					<xsl:apply-templates select="$objDef/AttrDef[@AttrDef.Type = 'AT_NAME']" mode="getvalue" />
				</xsl:variable>	
				
				<xsl:attribute name="Name">
					<xsl:call-template name="StripSpaces" >
						<xsl:with-param name="InputString" select="$activityName"/>
					</xsl:call-template>
				</xsl:attribute>

				<xsl:attribute name="xpdExt:DisplayName">
					<xsl:value-of select="$activityName" />
				</xsl:attribute>
				
				<!-- If there is a description then add it -->
				<xsl:variable name="AttrDesc">
					<xsl:apply-templates select="$objDef/AttrDef[@AttrDef.Type = 'AT_DESC']" mode="getvalue" />
				</xsl:variable>
				
				<xsl:if test="$AttrDesc">
					<xpdl2:Description>
						<xsl:value-of select="normalize-space($AttrDesc)"/>
					</xpdl2:Description>
				</xsl:if>
				
				<xsl:variable name="ActivityType">
					<xsl:choose>	
						<xsl:when test="contains('&arisEvents;', $objDef/@TypeNum)">
								<xsl:text>Event</xsl:text>
						</xsl:when>
						<xsl:when test="contains('&arisRules;', $objDef/@TypeNum)">
							<xsl:text>Route</xsl:text>
						</xsl:when>
						<xsl:otherwise>
							<xsl:text>Task</xsl:text>
						</xsl:otherwise>
					</xsl:choose>				
				</xsl:variable>
				
				
				<xsl:variable name="subFlow">
					<xsl:if test="$ActivityType='Task'">
								<xsl:if test="$objDef/@LinkedModels.IdRefs">
									<xsl:call-template name="getFirstLinkedModel">
										<xsl:with-param name="linkedRef" select="$objDef/@LinkedModels.IdRefs" />
										<xsl:with-param name="models" select="../../Model" />
									</xsl:call-template>
								</xsl:if>
					</xsl:if>			
				</xsl:variable>
							
				<xsl:variable name="TaskType">
					<xsl:if test="$ActivityType='Task'">
								<xsl:choose>	
									<xsl:when test="$subFlow != ''">
											<xsl:text>SubFlow</xsl:text>
									</xsl:when>
									<xsl:when test="starts-with ($objDef/@SymbolNum, 'ST_SYS_FUNC')">
										<xsl:text>SystemTask</xsl:text>
									</xsl:when>
									<xsl:when test="not($objDef/@SymbolNum) or starts-with ($objDef/@SymbolNum, 'ST_FUNC')">
										<xsl:text>UserTask</xsl:text>
									</xsl:when>
									<xsl:otherwise>
										<xsl:text>Task</xsl:text>
									</xsl:otherwise>
								</xsl:choose>
					</xsl:if>			
				</xsl:variable>
				
				<!-- Check for type of activity -->
				<xsl:choose>
					<!-- Event -->
					<xsl:when test="contains('&arisEvents;', $objDef/@TypeNum)">
						<xsl:variable name="incomingConnections" select="../ObjOcc/CxnOcc[@ToObjOcc.IdRef=$objOccId]" />
						<xsl:variable name="outgoingConnections" select="./CxnOcc" />
						<xsl:variable name="inCxnDef" select="$objDefinitions/CxnDef[@CxnDef.ID = $incomingConnections/@CxnDef.IdRef]" />
						<xsl:variable name="outObjDefID" select="../ObjOcc[@ObjOcc.ID = $outgoingConnections/@ToObjOcc.IdRef]/@ObjDef.IdRef" />
						<xsl:variable name="isNotMappedAsEvent" >
							<xsl:if test= "(count($incomingConnections) = 1) and (count($outgoingConnections) = 1)">
									
									<xsl:if test="contains('&arisRules;',$objDefinitions[CxnDef/@CxnDef.ID = $inCxnDef/@CxnDef.ID]/@TypeNum) and contains('OT_FUNC',$objDefinitions[@ObjDef.ID = $outObjDefID]/@TypeNum )" >
										<xsl:text>&true;</xsl:text>
									</xsl:if>
							</xsl:if>				
						</xsl:variable>	
						<xsl:choose>
							<xsl:when test = "$isNotMappedAsEvent='&true;'">
								<xsl:element name="xpdl2:Route">
									<xsl:attribute name="GatewayType"  >
										<xsl:value-of select="'Parallel'" />
									</xsl:attribute>
								</xsl:element> 
							</xsl:when>
							<xsl:otherwise>
									
										<!-- Determine the kind of event.  If the object has only out-going connections then it's a start event, if it has only in-coming connections
												 then it's an end event.  Otherwise, it will be an intermediate event
										-->
										<xsl:choose>
											<!-- Start event, only out-going connections -->
											<xsl:when test="CxnOcc and count(../ObjOcc/CxnOcc[@ToObjOcc.IdRef=$objOccId]) = 0">
												<xpdl2:Event>
													<xpdl2:StartEvent Trigger="None" />
												</xpdl2:Event>
											</xsl:when>
											<!-- End event, only in-coming connections -->
											<xsl:when test="count(CxnOcc) = 0 and count(../ObjOcc/CxnOcc[@ToObjOcc.IdRef=$objOccId]) &gt; 0">
												<xpdl2:Event>
													<xpdl2:EndEvent />
												</xpdl2:Event>
											</xsl:when>
											<!-- Otherwise, this is an intermediate event -->
											<xsl:otherwise>
												<xpdl2:IntermediateEvent Trigger="None" />
											</xsl:otherwise>
										</xsl:choose>
									
							</xsl:otherwise>		
						</xsl:choose>	
					</xsl:when>
					
					<!-- Route -->
					<xsl:when test="contains('&arisRules;', $objDef/@TypeNum)">
						<!-- Determine type of route -->
						<xsl:variable name="type">
							<xsl:choose>
								<xsl:when test="starts-with($objDef/@SymbolNum, 'ST_OPR_OR') or starts-with($objDef/@SymbolNum, 'ST_OR')"> 
									<xsl:text>Inclusive</xsl:text>
								</xsl:when>
								<xsl:when test="starts-with($objDef/@SymbolNum, 'ST_OPR_AND') or starts-with($objDef/@SymbolNum, 'ST_AND')"> 
									<xsl:text>Parallel</xsl:text>
								</xsl:when>
								<xsl:otherwise>
									<!-- Otherwise assume to be XOR route -->
									<xsl:text>Exclusive</xsl:text>
								</xsl:otherwise>
							</xsl:choose>
							
						</xsl:variable>
						<xsl:element name="xpdl2:Route">
							<xsl:attribute name="GatewayType" >
								<xsl:value-of select="$type" />
							</xsl:attribute>	
							<xsl:if test="$type='Exclusive'" >
								<xsl:attribute name="ExclusiveType" >
									<xsl:value-of select = "'Data'" />
								</xsl:attribute>
							</xsl:if>	
						</xsl:element> 
					</xsl:when>
					
					<!-- Task -->
					<xsl:otherwise>
						<xpdl2:Implementation>
							<!-- Check if this is a subflow call -->

							
							<xsl:choose>
								<!-- Subflow -->
								<xsl:when test="$TaskType='SubFlow'">
									<xpdl2:SubFlow Id="{$subFlow}" />
								</xsl:when>
								

								
								<!-- Service task -->
								<xsl:when test="$TaskType='SystemTask'">
									<xpdl2:Task>
										<xpdl2:TaskService Implementation="Unspecified">
											<!-- Add parameters -->
											<xsl:apply-templates select="." mode="parameters">
												<xsl:with-param name="objDefinitions" select="$objDefinitions" />
											</xsl:apply-templates>
										</xpdl2:TaskService>
									</xpdl2:Task>
								</xsl:when>
								
								<!-- User task -->
								<xsl:when test="$TaskType='UserTask'">
								<xpdl2:Task>
              						<xpdl2:TaskUser Implementation="Unspecified">
										<xpdl2:Performers>
											<xpdl2:Performer>-defined-in-Activity-Performer-</xpdl2:Performer>
										</xpdl2:Performers>
										<!-- Add parameters -->
										<xsl:apply-templates select="." mode="parameters">
											<xsl:with-param name="objDefinitions" select="$objDefinitions" />
										</xsl:apply-templates>
              						</xpdl2:TaskUser>
              					</xpdl2:Task>
								</xsl:when>
								
								<!-- Otherwise, this is only a task -->
								<xsl:otherwise>
									<xpdl2:No/>
								</xsl:otherwise>
								
							</xsl:choose>
							
						</xpdl2:Implementation>
					</xsl:otherwise>
					
				</xsl:choose> <!-- type of activity -->
				
                <!-- Add any performers to the activity - only for task types -->
				<xsl:if test="contains ('&arisFunctions;', $objDef/@TypeNum)">
					<xsl:apply-templates select="." mode="performers">
						<xsl:with-param name="objDefinitions" select="$objDefinitions" />
						<xsl:with-param name="taskType" select="$TaskType" />
	
					</xsl:apply-templates>
				</xsl:if>
				
                <xpdl2:ExtendedAttributes>
                    <xsl:element name="xpdl2:ExtendedAttribute">
                        <xsl:attribute name="Name">ARIS_GUID</xsl:attribute>
                        <xsl:attribute name="Value"><xsl:value-of select="$objDef/GUID"/></xsl:attribute>
                    </xsl:element>                            
					<xsl:element name="xpdl2:ExtendedAttribute">
                        <xsl:attribute name="Name">ARIS_OCCURRENCEID</xsl:attribute>
                        <xsl:attribute name="Value"><xsl:value-of select="$objOccId"/></xsl:attribute>
                    </xsl:element>                            
                    <xsl:apply-templates select="$objDef/AttrDef"/>
                </xpdl2:ExtendedAttributes>

				<xpdl2:NodeGraphicsInfos>
		        	<xpdl2:NodeGraphicsInfo Height="&activityHeight;" LaneId="lane{generate-id(..)}" ToolId="XPD" Width="&activityWidth;">
		        		<!-- Add the X and Y position of this object -->
		        		<xsl:element name="xpdl2:Coordinates">
		        			<xsl:attribute name="XCoordinate">
		        				<xsl:call-template name="XPos">
		        					<xsl:with-param name="posX" select="Position/@Pos.X"/>
		        					<xsl:with-param name="sizeX" select="Size/@Size.dX" />
		        					<xsl:with-param name="shiftX" select="$shiftX"/>
		        				</xsl:call-template>
		        			</xsl:attribute>
		        			<xsl:attribute name="YCoordinate">
								<xsl:call-template name="YPos">
									<xsl:with-param name="posY" select="Position/@Pos.Y"/>
									<xsl:with-param name="sizeY" select="Size/@Size.dY" />
									<xsl:with-param name="shiftY" select="$shiftY"/>
								</xsl:call-template>
		        			</xsl:attribute>
		        		</xsl:element>
		            </xpdl2:NodeGraphicsInfo>
				</xpdl2:NodeGraphicsInfos>
			</xpdl2:Activity>
		</xsl:if>
		
	</xsl:template>
	
	<!--
===================================================================================================================
	ObjOcc (performers) = Identify any connected participants - if there is only one then add as a performer.  If multiple
		participants are found then add as a performers group
===================================================================================================================
-->	
	<xsl:template match="ObjOcc" mode="performers">
		<xsl:param name="objDefinitions" />
		<xsl:param name="taskType" />
		
		<!-- Get this occurence ID -->
		<xsl:variable name="objOccID" select="@ObjOcc.ID" />
	
		<!-- Get all object occs connected to this object -->
		<xsl:variable name="connectedOccs" select="../ObjOcc[CxnOcc/@ToObjOcc.IdRef = $objOccID]" />
		
		<!-- Get object definitions of connected objects -->
		<xsl:variable name="connectedObjs" select="$objDefinitions[@ObjDef.ID = $connectedOccs/@ObjDef.IdRef]" />
		
		<!-- Get all participant objects from the connected list -->
		<xsl:variable name="connParticipantObjs" select="$connectedObjs[contains('&arisParticipants;', @TypeNum)]" />

		<xsl:variable name="numNonSystemParticipants" select="$connectedObjs[contains('&arisParticROLE; &arisParticHUMAN; &arisParticOU;', @TypeNum)]" />
		
		<!-- If there is only one participant then add it as the performer, otherwise add as performers group -->
		<xsl:choose>
				<xsl:when test="$taskType='UserTask'" >
						<xsl:choose>
							<xsl:when test="count ($numNonSystemParticipants) = 1">
								<xpdl2:Performer>
									<xsl:value-of select="$numNonSystemParticipants[1]/@ObjDef.ID" />
								</xpdl2:Performer>
							</xsl:when>
							<xsl:when test="count ($numNonSystemParticipants) &gt; 1">
								<xpdl2:Performers>
									<xsl:for-each select="$numNonSystemParticipants">
										<xpdl2:Performer>
											<xsl:value-of select="@ObjDef.ID" />
										</xpdl2:Performer>
									</xsl:for-each>
								</xpdl2:Performers>
							</xsl:when>
						</xsl:choose>
				</xsl:when>
				<xsl:otherwise>
						<xsl:choose>
							<xsl:when test="count ($connParticipantObjs) = 1">
								<xpdl2:Performer>
									<xsl:value-of select="$connParticipantObjs[1]/@ObjDef.ID" />
								</xpdl2:Performer>
							</xsl:when>
							<xsl:when test="count ($connParticipantObjs) &gt; 1">
								<xpdl2:Performers>
									<xsl:for-each select="$connParticipantObjs">
										<xpdl2:Performer>
											<xsl:value-of select="@ObjDef.ID" />
										</xpdl2:Performer>
									</xsl:for-each>
								</xpdl2:Performers>
							</xsl:when>
							
						</xsl:choose>

				</xsl:otherwise>
		</xsl:choose>
		
	</xsl:template>
	
	<!--
===================================================================================================================
	ObjOcc (parameters) = Identify any connected technical terms or ERM attributes - this will become parameters of this activity
===================================================================================================================
-->	
	<xsl:template match="ObjOcc" mode="parameters">
		<xsl:param name="objDefinitions" />

		<!-- Get the ID of this object -->
		<xsl:variable name="objOccID" select="@ObjOcc.ID" />
		<!-- Get all connections -->
		<xsl:variable name="cxn" select="CxnOcc" />
		
		<!-- Get all connected (both income and outgoing) object occurences  to this object -->
		<xsl:variable name="inputParamOccs" select="../ObjOcc[CxnOcc/@ToObjOcc.IdRef = $objOccID]" />
		<xsl:variable name="outputParamOccs" select="../ObjOcc[@ObjOcc.ID = $cxn/@ToObjOcc.IdRef]" />
		<!-- Get the corresponding object definitions that are data type objects -->
		<xsl:variable name="inputParamObjs" select="$objDefinitions[@ObjDef.ID = $inputParamOccs/@ObjDef.IdRef and contains ('&arisDataFields;', @TypeNum)]" />
		<xsl:variable name="outputParamObjs" select="$objDefinitions[@ObjDef.ID = $outputParamOccs/@ObjDef.IdRef and contains ('&arisDataFields;', @TypeNum)]" />
		

		
		
		
		<!-- Define input parameters -->
		<xpdl2:MessageIn Id="in{generate-id()}">
			<xsl:if test="$inputParamObjs">
				<xpdl2:ActualParameters>
					<xsl:for-each select="$inputParamObjs">
							<xsl:variable name="ParamName">
								<xsl:apply-templates select="AttrDef[@AttrDef.Type = 'AT_NAME']" mode="getvalue" />
							</xsl:variable>
							<xsl:variable name="RefParamName">
								<xsl:call-template name="StripSpaces" >
									<xsl:with-param name="InputString" select="$ParamName"/>
								</xsl:call-template>
							</xsl:variable>
						<xpdl2:ActualParameter><xsl:value-of select="normalize-space($RefParamName)" /></xpdl2:ActualParameter>
					</xsl:for-each>
				</xpdl2:ActualParameters>
			</xsl:if>
		</xpdl2:MessageIn>
		
		<!-- Define output parameters -->
		<xpdl2:MessageOut Id="out{generate-id()}">
			<xsl:if test="$outputParamObjs">
				<xpdl2:ActualParameters>
					<xsl:for-each select="$outputParamObjs">
							<xsl:variable name="ParamName">
								<xsl:apply-templates select="AttrDef[@AttrDef.Type = 'AT_NAME']" mode="getvalue" />
							</xsl:variable>	
							<xsl:variable name="RefParamName">
								<xsl:call-template name="StripSpaces" >
									<xsl:with-param name="InputString" select="$ParamName"/>
								</xsl:call-template>
							</xsl:variable>				
						<xpdl2:ActualParameter><xsl:value-of select="normalize-space($RefParamName)" /></xpdl2:ActualParameter>
					</xsl:for-each>
				</xpdl2:ActualParameters>
			</xsl:if>
		</xpdl2:MessageOut>
		
	</xsl:template>
	
	<!--
		===================================================================================================================
		CxnOcc = Add the transitions
		===================================================================================================================
	-->
	<xsl:template match="CxnOcc">
		<xsl:param name="objDefinitions" />
		<xsl:param name="shiftX" />
		<xsl:param name="shiftY" />
		
		<!-- Get this object occurrence -->
		<xsl:variable name="thisObjOcc" select="../." />
		<!-- Get the object definition of this occurrence -->
		<xsl:variable name="objDef" select="$objDefinitions[@ObjDef.ID = $thisObjOcc/@ObjDef.IdRef]" />
		<!-- Get all object occurrence of this model -->
		<xsl:variable name="objOccs" select="../../ObjOcc" />
		
		<!-- Check if this object will be drawn as an activity.  If not then don't draw it's transitions -->
		<xsl:variable name="isActivity">
			<xsl:call-template name="isActivity">
				<xsl:with-param name="objOcc" select="$thisObjOcc" />
				<xsl:with-param name="objDef" select="$objDef" />
				<xsl:with-param name="allObjOccs" select="$objOccs" />
			</xsl:call-template>
		</xsl:variable>
		
		<!-- If this is an activity then draw it's transitions -->
		<xsl:if test="$isActivity = '&true;'">	
			
			<!-- Get the target object occurrence ID -->
			<xsl:variable name="toObjOccId" select="@ToObjOcc.IdRef" />
			<!-- Get the to object occurrence -->
			<xsl:variable name="toObjOcc" select="$objOccs[@ObjOcc.ID = $toObjOccId]" />
			<!-- Get the to object definition -->
			<xsl:variable name="toObjDef" select="$objDefinitions[@ObjDef.ID = $toObjOcc/@ObjDef.IdRef]" />
			
			<!-- 
				If the object connected to will not be drawn as an activity then this transition should not be drawn.
			-->
			<xsl:variable name="isConnectedOccActivity">
				<xsl:call-template name="isActivity">
					<xsl:with-param name="objOcc" select="$toObjOcc" />
					<xsl:with-param name="objDef" select="$toObjDef" />
					<xsl:with-param name="allObjOccs" select="$objOccs" />
				</xsl:call-template>
			</xsl:variable>		
		
			<xsl:if test="$isConnectedOccActivity = '&true;'">
				<xsl:variable name="cxnDefId" select="@CxnDef.IdRef" />

				<xsl:variable name="connectionName">
					<xsl:choose>
						<xsl:when test="$objDef/@TypeNum = 'OT_RULE' and not (starts-with($objDef/@SymbolNum, 'ST_OPR_AND') or starts-with($objDef/@SymbolNum, 'ST_AND'))"> 
								<xsl:apply-templates select="$toObjDef/AttrDef[@AttrDef.Type = 'AT_NAME']" mode="getvalue" />
						</xsl:when>
						<xsl:otherwise>
								<xsl:apply-templates select="$objDef/CxnDef[@CxnDef.ID = $cxnDefId]/AttrDef[@AttrDef.Type = 'AT_DESC']" mode="getvalue" />
						</xsl:otherwise>
					</xsl:choose>						
				</xsl:variable>
				
				<xpdl2:Transition Id="{@CxnOcc.ID}"  
						From="{$thisObjOcc/@ObjOcc.ID}" To="{$toObjOccId}">
					<xsl:attribute name="xpdExt:DisplayName" >
						<xsl:value-of  select="$connectionName" />	
					</xsl:attribute>
					<!-- If this the source of this transition is an OT_RULE and not of operation AND (ie, it is OR or XOR) then this transition should be conditional -->
					<xsl:if test="$objDef/@TypeNum = 'OT_RULE' and not (starts-with($objDef/@SymbolNum, 'ST_OPR_AND') or starts-with($objDef/@SymbolNum, 'ST_AND'))">
						<xsl:if test="count($objDef/CxnDef) > 1" >
							<xsl:choose>
								<xsl:when test="count($thisObjOcc/CxnOcc) > 1 and @CxnOcc.ID=$thisObjOcc/CxnOcc[last()]/@CxnOcc.ID">
									     <xpdl2:Condition Type="OTHERWISE" />
								</xsl:when>
								<xsl:otherwise>
									<xpdl2:Condition Type="CONDITION" />		
								</xsl:otherwise>
							</xsl:choose>
					
							
						</xsl:if>
						  <xpdl2:ExtendedAttributes>
						  	<xpdl2:ExtendedAttribute Name="ARIS_OCCURRENCEID"><xsl:value-of select="@CxnOcc.ID" />
							</xpdl2:ExtendedAttribute>
				        </xpdl2:ExtendedAttributes>
					</xsl:if>
						
					<!-- If we have more than two position elements then there are bend points -->
					<xsl:if test="count(Position) &gt; 2">
						<xpdl2:ConnectorGraphicsInfos>
							<xpdl2:ConnectorGraphicsInfo BorderColor="0,0,128" ToolId="XPD.ConnectionInfo">
								<xsl:call-template name="insertBendPoints">
									<xsl:with-param name="positions" select="Position" />
									<xsl:with-param name="fromObjOcc" select="$thisObjOcc" />
									<xsl:with-param name="toObjOcc" select="$toObjOcc" />
									<xsl:with-param name="shiftX" select="$shiftX" />
									<xsl:with-param name="shiftY" select="$shiftY" />
								</xsl:call-template>
							</xpdl2:ConnectorGraphicsInfo>
						</xpdl2:ConnectorGraphicsInfos>
					</xsl:if>
				</xpdl2:Transition>
			</xsl:if>
		</xsl:if>
		
	</xsl:template>
	
	<!--
		===================================================================================================================
		getFirstLinkedModel = Get any linked model of type EPC in the given linked ref.  A function object can reference multiple models of various types. 
		This template will recover the first model reference that is of an EPC model.  
		===================================================================================================================
	-->
	<xsl:template name="getFirstLinkedModel">
		<xsl:param name="linkedRef" />
		<xsl:param name="models" />
		
		<!-- If there is a space in the linked ref then search for the first ref to a model -->
		<xsl:choose>
			<xsl:when test="contains ($linkedRef, ' ')">
				<xsl:variable name="link" select="substring-before ($linkedRef, ' ')" />
				<xsl:variable name="linkedModel" select="$models[@Model.ID = $link and starts-with (@Model.Type, 'MT_EEPC')]/@Model.ID" />
				
				<xsl:choose>
					<xsl:when test="$linkedModel">
						<xsl:value-of select="$linkedModel/@Model.ID" />
					</xsl:when>
					<xsl:otherwise>
						<xsl:call-template name="getFirstLinkedModel">
							<xsl:with-param name="linkedRef" select="substring-after($linkedRef, ' ')" />
							<xsl:with-param name="models" select="$models" />
						</xsl:call-template>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$models[@Model.ID = $linkedRef and starts-with (@Model.Type, 'MT_EEPC')]/@Model.ID" />
			</xsl:otherwise>
		</xsl:choose>
		
	</xsl:template> 
	
	<!--
		===================================================================================================================
		insertBendPoints = Add bendpoints on transitions and associations.
		===================================================================================================================
	-->	
	<xsl:template name="insertBendPoints">
		<xsl:param name="positions" />
		<xsl:param name="fromObjOcc" />
		<xsl:param name="toObjOcc" />
		<xsl:param name="shiftX" />
		<xsl:param name="shiftY" />
		
		<!-- Get X,Y mid-points of the from and to objects -->
		<xsl:variable name="fromObjX" select="$fromObjOcc/Position/@Pos.X + ($fromObjOcc/Size/@Size.dX div 2)" />
		<xsl:variable name="fromObjY" select="$fromObjOcc/Position/@Pos.Y + ($fromObjOcc/Size/@Size.dY div 2)" />
		
		<xsl:variable name="toObjX" select="$toObjOcc/Position/@Pos.X + ($toObjOcc/Size/@Size.dX div 2)" />
		<xsl:variable name="toObjY" select="$toObjOcc/Position/@Pos.Y + ($toObjOcc/Size/@Size.dY div 2)" />
		
		<!-- 
			Insert bendpoints if any.  ARIS has atleast 2 points for each connection - the start and end points of the connection.
			Any other points are the bend points
		-->
		<xsl:if test="count ($positions) &gt; 2">
			<!-- Insert bendpoints for every bend coordinate in the connection -->
			<xsl:for-each select="$positions">
				<xsl:if test="position() != 1 and position() != last()">
					<!--
						Calculate any deviation of this bend point from the previous and next bend point.  This is to identify if this point deviates slightly from a straight line 
						path, so as to avoid having unnecessary bend points in the transformed model.
					-->
					<xsl:variable name="diffX">
						<xsl:variable name="value" select="round (((@Pos.X - preceding-sibling::Position/@Pos.X) + (following-sibling::Position/@Pos.X - @Pos.X)) div 2)" />
						<!-- Return absolute value -->
						<xsl:choose>
							<xsl:when test="$value &lt; 0"><xsl:value-of select="-$value" /></xsl:when>
							<xsl:otherwise><xsl:value-of select="$value" /></xsl:otherwise>
						</xsl:choose>
					</xsl:variable>
					<xsl:variable name="diffY">
						<xsl:variable name="value" select="round (((@Pos.Y - preceding-sibling::Position/@Pos.Y) + (following-sibling::Position/@Pos.Y - @Pos.Y)) div 2)" />
						<!-- Return absolute value -->
						<xsl:choose>
							<xsl:when test="$value &lt; 0"><xsl:value-of select="-$value" /></xsl:when>
							<xsl:otherwise><xsl:value-of select="$value" /></xsl:otherwise>
						</xsl:choose>
					</xsl:variable>
					
					<!-- If no deviation identified then add the bendpoint - 50 units is an arbitrary value -->
					<xsl:if test="$diffX &gt; 50 and $diffY &gt; 50">
					<!-- If the X value or the Y value of the bendpoint deviates slightly from the mid point of the from/to object then change value to the mid-point -->
					<xsl:variable name="xVal">
						<xsl:choose>
							<xsl:when test="@Pos.X &gt; $fromObjOcc/Position/@Pos.X and @Pos.X &lt; ($fromObjOcc/Position/@Pos.X + $fromObjOcc/Size/@Size.dX)">
								<xsl:value-of select="$fromObjX" />
							</xsl:when>
							<xsl:when test="@Pos.X &gt; $toObjOcc/Position/@Pos.X and @Pos.X &lt; ($toObjOcc/Position/@Pos.X + $toObjOcc/Size/@Size.dX)">
								<xsl:value-of select="$toObjX" />
							</xsl:when>
							<xsl:otherwise>
								<xsl:value-of select="@Pos.X" />
							</xsl:otherwise>
						</xsl:choose>
					</xsl:variable>
				
					<xsl:variable name="yVal">
						<xsl:choose>
							<xsl:when test="@Pos.Y &gt; $fromObjOcc/Position/@Pos.Y and @Pos.Y &lt; ($fromObjOcc/Position/@Pos.Y + $fromObjOcc/Size/@Size.dY)">
								<xsl:value-of select="$fromObjY" />
							</xsl:when>
							<xsl:when test="@Pos.Y &gt; $toObjOcc/Position/@Pos.Y and @Pos.Y &lt; ($toObjOcc/Position/@Pos.Y + $toObjOcc/Size/@Size.dY)">
								<xsl:value-of select="$toObjY" />
							</xsl:when>
							<xsl:otherwise>
								<xsl:value-of select="@Pos.Y" />
							</xsl:otherwise>
						</xsl:choose>
					</xsl:variable>					
					
						<xpdl2:Coordinates XCoordinate="{(round (($xVal - $fromObjOcc/Position/@Pos.X - ($fromObjOcc/Size/@Size.dX div 2)) + $shiftX) * $factor)}" 
								YCoordinate="{(round (($yVal - $fromObjOcc/Position/@Pos.Y - ($fromObjOcc/Size/@Size.dY div 2)) + $shiftY) * $factor)}"/>
						<xpdl2:Coordinates XCoordinate="{(round (($xVal - $toObjOcc/Position/@Pos.X - ($toObjOcc/Size/@Size.dX div 2)) + $shiftX) * $factor)}" 
								 YCoordinate="{(round (($yVal - $toObjOcc/Position/@Pos.Y - ($toObjOcc/Size/@Size.dY div 2)) + $shiftY) * $factor)}"/>
					</xsl:if>
				</xsl:if>
			</xsl:for-each>
		</xsl:if>
	</xsl:template>
	
	<!--
		===================================================================================================================
		XPos = Calculate the X position of the given obj (ObjOcc or FFTextOcc)
		===================================================================================================================
	-->
	<xsl:template name="XPos">
		<xsl:param name="posX" />
		<xsl:param name="sizeX" />
		<xsl:param name="shiftX" />
		<!-- 
			Get the center position of object and scale using factor given
			Add the shift to the position to make sure the model is not off the page 
		-->
		<xsl:choose>
			<xsl:when test="$sizeX">
				<!-- Has size element so probably an ObjOcc -->
				<xsl:value-of select="round (($posX + round ($sizeX div 2) + $shiftX) * $factor)" />
			</xsl:when>
			<xsl:otherwise>
				<!-- This is probably a text object so no size defined -->
				<xsl:value-of select="($posX + $shiftX) * $factor" />
			</xsl:otherwise>
		</xsl:choose>

	</xsl:template>

	<!--
		===================================================================================================================
		YPos = Calculate the Y position of the given obj (ObjOcc or FFTextOcc). 
		===================================================================================================================
	-->
	<xsl:template name="YPos">
		<xsl:param name="posY" />
		<xsl:param name="sizeY" />
		<xsl:param name="shiftY" />
		
		<!-- 
			Get the center position of object and scale using factor given
			Add the shift to the position to make sure the model is not off the page 
		-->
		<xsl:choose>
			<xsl:when test="$sizeY">
				<!-- Has size element so probably an ObjOcc -->
				<xsl:value-of select="round (($posY + round ($sizeY div 2) +  $shiftY) * $factor)" />
			</xsl:when>
			<xsl:otherwise>
				<!-- This is probably a text object so no size defined -->
				<xsl:value-of select="($posY + $shiftY) * $factor" />
			</xsl:otherwise>
		</xsl:choose>

	</xsl:template>

	<!--
		===================================================================================================================
		isActivity = this template will return 'true' if the given object will be drawn as an activity in the mode, else 'false' will be returned
		===================================================================================================================
	-->
	<xsl:template name="isActivity">
		<xsl:param name="objOcc" />
		<xsl:param name="objDef" />
		<xsl:param name="allObjOccs" />

		<!-- 
			Determine whether this object will be drawn as an activity .  All the following objects will not be drawn as activities as they
			will either be converted to associated notes, participants or data fields:
			
			1. Participant type objects.
			2. Data type objects - OT_ERM _ATTR and OT_TECH_TRM
			3. Non-function objects that have either one in-coming connection or one out-going connection (but not both)
			4. Non-function objects that have no connections
		-->

		<!-- If this is a function, rule or event type then it will be placed as an activity -->
		<xsl:choose>
			<xsl:when test="contains('&arisFunctions; &arisEvents; &arisRules;',$objDef/@TypeNum)">
				<xsl:text>&true;</xsl:text>
			</xsl:when>
			<xsl:otherwise>

				<xsl:choose>
					<xsl:when test="contains ('&arisParticipants;', $objDef/@TypeNum)">
						<xsl:text>&false;</xsl:text>
					</xsl:when>
					<xsl:when test="contains ('&arisDataFields;', $objDef/@TypeNum)">
						<xsl:text>&false;</xsl:text>
					</xsl:when>
					<!-- only 1 out-going connection -->
					<xsl:when test="count ($objOcc/CxnOcc) = 1 and count ($allObjOccs/CxnOcc[@ToObjOcc.ID = $objOcc/@ObjOcc.ID]) = 0">
							<xsl:text>&false;</xsl:text>
					</xsl:when>
					<!-- Only 1 in-coming connection -->
					<xsl:when test="count ($objOcc/CxnOcc) = 0 and count ($allObjOccs/CxnOcc[@ToObjOcc.ID = $objOcc/@ObjOcc.ID]) = 1">
							<xsl:text>&false;</xsl:text>
					</xsl:when>
					<!-- no connections -->
					<xsl:when test="count ($objOcc/CxnOcc) = 0 and count ($allObjOccs/CxnOcc[@ToObjOcc.ID = $objOcc/@ObjOcc.ID]) = 0">
							<xsl:text>&false;</xsl:text>
					</xsl:when>
					<xsl:otherwise>
							<xsl:text>&true;</xsl:text>
					</xsl:otherwise>
				</xsl:choose>

			</xsl:otherwise>
		</xsl:choose>

	</xsl:template>

	<!--
		===================================================================================================================
		calculateXShift = Calculates the X shift required to reposition the model so that no part of the model appears outside the left-side of the lane
		===================================================================================================================
	-->
	<xsl:template name="calculateXShift">
		<xsl:param name="model" />
		
		<!--
			Function objects will have to be centered (in XPD terms) so calculate the min X position after
			centering the objects (x + (width/2) - (XPD activity height / 2))
		-->
		<!-- Get the min x position of the objects -->
		<xsl:variable name="objMinX">
			<xsl:for-each select="$model/ObjOcc">
				<xsl:sort data-type="number" order="ascending"
					select="Position/@Pos.X + round (Size/@Size.dX div 2) - round (&activityWidth; div 2)" />
				<!--  Return the first item as this will be the min value -->
				<xsl:if test="position () = 1">
					<xsl:value-of
						select="Position/@Pos.X + round (Size/@Size.dX div 2) - round (&activityWidth; div 2)" />
				</xsl:if>
			</xsl:for-each>
		</xsl:variable>
		
		<!-- Get the min x position of any text objects -->
		<xsl:variable name="textMinX">
			<xsl:for-each select="$model/FFTextOcc">
				<xsl:sort data-type="number" order="ascending"
					select="Position/@Pos.X" />
				<!--  Return the first item as this will be the min value -->
				<xsl:if test="position () = 1">
					<xsl:value-of
						select="Position/@Pos.X" />
				</xsl:if>
			</xsl:for-each>
		</xsl:variable>
		
		<!-- Calculate the min X position from the min X position of the object and text occurrences -->
		<xsl:variable name="minX">
			<xsl:choose>
				<xsl:when test="$textMinX != ''">
					<xsl:choose>
						<xsl:when test="$textMinX &lt; $objMinX">
							<!-- Text occurrence is at min X -->
							<xsl:value-of select="$textMinX" />
						</xsl:when>
						<xsl:otherwise>
							<!-- Object occurrence is at min X -->
							<xsl:value-of select="$objMinX" />
						</xsl:otherwise>
					</xsl:choose>
				</xsl:when>
				<xsl:otherwise>
					<!-- No text occurrences in the model -->
					<xsl:value-of select="$objMinX" />
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		
		<!-- If the min value is less than 0 units then calculate the difference - this will be the shift required -->
		<xsl:choose>
			<xsl:when test="$minX &lt; 0">
				<xsl:value-of select="-$minX" />
			</xsl:when>
			<xsl:otherwise>
				<xsl:text>0</xsl:text>
			</xsl:otherwise>
		</xsl:choose>
		
	</xsl:template>

	<!--
		===================================================================================================================
		calculateYShift = Calculates the Y shift required to reposition the model so that no part of the model appears outside the top-edge of the lane
		===================================================================================================================
	-->
	<xsl:template name="calculateYShift">
		<xsl:param name="model"/>
		
		<!--
			Function objects will have to be centered so calculate the min Y position after
			centering the objects (y - (height/2) - (XPD Activity height / 2))
		-->
		<!-- Get the min Y of the object occurrences -->
		<xsl:variable name="objMinY">
			<xsl:for-each select="$model/ObjOcc">
				<xsl:sort data-type="number" order="ascending"
					select="Position/@Pos.Y + round (Size/@Size.dY div 2) - round (&activityHeight; div 2)" />
				 <!-- Return the first item as this will be the min value -->
				<xsl:if test="position () = 1">
					<xsl:value-of
						select="Position/@Pos.Y + round (Size/@Size.dY div 2) - round (&activityHeight; div 2)" />
				</xsl:if>
			</xsl:for-each>
		</xsl:variable>
		
		<!-- Get the min Y of the text occurrences -->
		<xsl:variable name="textMinY">
			<xsl:for-each select="$model/FFTextOcc">
				<xsl:sort data-type="number" order="ascending"
					select="Position/@Pos.Y" />
				 <!-- Return the first item as this will be the min value -->
				<xsl:if test="position () = 1">
					<xsl:value-of
						select="Position/@Pos.Y" />
				</xsl:if>
			</xsl:for-each>
		</xsl:variable>
		
		<!-- Calculate the min Y position from the min Y position of the object and text occurrences -->
		<xsl:variable name="minY">
			<xsl:choose>
				<xsl:when test="$textMinY != ''">
					<xsl:choose>
						<xsl:when test="$textMinY &lt; $objMinY">
							<!-- Text occurrence is at min Y -->
							<xsl:value-of select="$textMinY" />
						</xsl:when>
						<xsl:otherwise>
							<!-- Object occurrence is at min Y -->
							<xsl:value-of select="$objMinY" />
						</xsl:otherwise>
					</xsl:choose>
				</xsl:when>
				<xsl:otherwise>
					<!-- No text occurrences in the model -->
					<xsl:value-of select="$objMinY" />
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		
		<!--  If the min value is less than 0 units then calculate the difference - this will be the shift required -->
		<xsl:choose>
			<xsl:when test="$minY &lt; 0">
				<xsl:value-of select="-$minY" />
			</xsl:when>
			<xsl:otherwise>
				<xsl:text>0</xsl:text>
			</xsl:otherwise>
		</xsl:choose>
		
	</xsl:template>

	<!-- ===================================================================================================================
		General template to preserve all unrecognised attributes as XPDL Extended Attributes
		===================================================================================================================
	-->
	<xsl:template match="AttrDef">
	      	     <xsl:element name="xpdl2:ExtendedAttribute" >
		        	<xsl:attribute name="Name"><xsl:value-of select="@AttrDef.Type"/></xsl:attribute>
		            <xsl:choose>
		            	<xsl:when test="AttrValue/StyledElement">
		            		<xsl:attribute name="Value"><xsl:apply-templates select="AttrValue/StyledElement" /></xsl:attribute>
		            	</xsl:when>
		     		<xsl:otherwise>
		           			 <xsl:attribute name="Value"><xsl:value-of select="AttrValue"/></xsl:attribute>
					</xsl:otherwise>       		
		            </xsl:choose>	
				</xsl:element>
   
		         	


	</xsl:template>
	<!-- ===================================================================================================================
		Test Template to get the value of an StyledElement
		===================================================================================================================
	-->
		<xsl:template match="StyledElement">

            <xsl:choose>
           		 <xsl:when test="PlainText">
            		<xsl:value-of select="normalize-space(PlainText/@TextValue)"/>
            	</xsl:when>
            	 <xsl:when test="LineBreak">
            		<xsl:text> </xsl:text>
            	</xsl:when>
            	<xsl:when test="StyledElement">
            		<xsl:apply-templates select="StyledElement" />
            	</xsl:when>
     		
            </xsl:choose>	

	</xsl:template>

	<!-- ===================================================================================================================
		Test Template to get the value of an StyledElement
		===================================================================================================================
-->
		<xsl:template match="StyledElement" mode="getvalue">

            <xsl:choose>
           		 <xsl:when test="PlainText">
            		<xsl:value-of select="normalize-space(PlainText/@TextValue)"/>
            	</xsl:when>
            	 <xsl:when test="LineBreak">
            		<xsl:text> </xsl:text>
            	</xsl:when>
            	<xsl:when test="StyledElement">
            		<xsl:apply-templates select="StyledElement" mode="getvalue"/>
            	</xsl:when>
     		
            </xsl:choose>	

	</xsl:template>

	

<!--
		===================================================================================================================
		Test Template to get the value of an attribute
		===================================================================================================================
	-->
		<xsl:template match="AttrDef" mode="getvalue">
				<xsl:variable name="empty_string"/>
			<xsl:variable name="attributevalue" >
            	<xsl:if test="AttrValue/StyledElement">
            		<xsl:apply-templates select="AttrValue/StyledElement" mode="getvalue" />
            	</xsl:if>
   	
         </xsl:variable>   	
	
         <xsl:choose>
         	<xsl:when test="$attributevalue != $empty_string">   
         	     <xsl:value-of select="$attributevalue"/>	
         	</xsl:when>
         	<xsl:otherwise>
         		<xsl:value-of select="normalize-space(AttrValue)"/>	
			</xsl:otherwise>       		
            </xsl:choose>	

	</xsl:template>


	<!--
		===================================================================================================================
		General template to copy all attributes and text of an element
		===================================================================================================================
	-->
	<xsl:template match="@* | text()">

		<!-- Copy input attribute to output. -->
		<xsl:copy>
			<xsl:apply-templates select="@* | text()" />
		</xsl:copy>

	</xsl:template>

	<!--
		===================================================================================================================
		General template for Stripping the spaces out of a string
		===================================================================================================================
	-->

	<xsl:template name="StripSpaces" >
		<xsl:param name="InputString"/>

		<xsl:if test="string-length($InputString) > 0">
			<xsl:variable name="nextchar" select="substring($InputString,1,1)" />
			<xsl:variable name="stringremainder" select="substring-after($InputString,$nextchar)" />
		
			<xsl:if test="not($nextchar=' ') and contains('abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_0123456789', $nextchar)">
				<xsl:value-of select="$nextchar" />
			</xsl:if>
					
			<xsl:if test="string-length($stringremainder) > 0">
				<xsl:call-template name="StripSpaces">
					<xsl:with-param name="InputString" select="$stringremainder" />
				</xsl:call-template>
			</xsl:if >
		</xsl:if>				
	</xsl:template>

</xsl:stylesheet>

