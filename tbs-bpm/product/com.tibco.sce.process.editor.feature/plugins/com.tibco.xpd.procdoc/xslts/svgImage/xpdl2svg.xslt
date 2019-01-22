<?xml version="1.0" encoding="UTF-8"?>
<!--
=================================================================================================
Transform a given process (param processId or first process in package) to SVG...

=================================================================================================
-->
<xsl:stylesheet 
	version="1.0" 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
	xmlns:xlink="http://www.w3.org/1999/xlink"
	xmlns="http://www.w3.org/2000/svg" 
	xmlns:svg="http://www.w3.org/2000/svg" 
	xmlns:xpdExt="http://www.tibco.com/XPD/xpdExtension1.0.0" 
	xmlns:xpdl2="http://www.wfmc.org/2008/XPDL2.1" 
	xmlns:java="http://xml.apache.org/xslt/java" 
	xmlns:email="http://www.tibco.com/XPD/email1.0.0" 
	xmlns:ej="http://www.tibco.com/XPD/EAIJava1.0.0" 
	xmlns:database="http://www.tibco.com/XPD/database1.0.0" 
	xmlns:simulation="http://www.tibco.com/xpd/Simulation1.0.1" 
	exclude-result-prefixes="#default xsi xsl xpdExt xpdl2 email database java ej simulation">

<!-- 
=================================================================================================
Parameters... 
=================================================================================================
-->
<xsl:param name="processId"/>
<xsl:param name="iconsFolder" select="'svgSupport'"/>

<!-- What type of gradient fill / lighting do you want... 
		"BlackAndWhite"			= Plain black and white.
		"Plain"						= Plain colours (no fill effects). 
		"IndividualChrome"		= Overall chrome effect.	(NOTE: Can take a very long time to render! This will therefore switch off mouse over highlighting etc as that re-renders).
		"IndividualChrome_NoShadow" = Same as IndividualChrome without shadowing.
		"Gradient" 					= Standard Studio style lighting.
		"IndividualPoint"			= Point light source highlighting. (doesn't work very well currently).
		"DistantLighting"			= Distant point light source. (not particularly effective currently).
		"AllChrome"					= Overall chrome effect.	(bit of a wash out). (NOTE: Can take a very long time to render! This will therefore switch off mouse over highlighting etc as that re-renders).
-->
<xsl:param name="gradientOrLighting" select="'Gradient'"/>

<!-- Various standard size parameters -->
<xsl:param name="scale" select="1"/>
<xsl:param name="noScaleFont" select="'false'"/> <!-- set to true if you want font to be unscaled (maybe useful if you are scaling because of a non-direct-pixel coord system in xpdl -->
<xsl:param name="fontPtHeight" select="'8'"/>
<xsl:param name="fixedEventSize" select="'27'"/>
<xsl:param name="fixedGatewayWidth" select="'43'"/>
<xsl:param name="fixedGatewayHeight" select="'45'"/>
<xsl:param name="connectionDecoratorSize" select="7"/>

<!-- Standard colors (these can generally also be overridden by adding to <styles/> -->
<xsl:param name="standardArtifactFillColor" select="'175,175,200'"/>
<xsl:param name="standardArtifactBorderColor" select="'128,128,128'"/>
<xsl:param name="standardArtifactTextColor" select="'0,0,0'"/>

<xsl:param name="standardActivityFillColor" select="'255,219,74'"/>
<xsl:param name="standardActivityBorderColor" select="'128,128,128'"/>
<xsl:param name="standardActivityTextColor" select="'0,0,0'"/>

<xsl:param name="standardConnectionColor" select="'128,128,128'"/>

<xsl:param name="cssFile" select="''"/>

<!--
 JavaScripting - Pass a javascript file path (relative or absolute) and then values for on<xxxx> params to specify on<xxxx> attribute on diagram objects.
	Virtually all diagram objects are given the javascript methods (when params are assigned for them); the method can check the "class" attribute
	of the "evt.currentTarget" to filter method on/off for different objects.
-->
<xsl:param name="javaScriptFile" select="''"/>

<xsl:param name="onload" select="''"/> <!-- Only placed on the main <svg> element -->
<xsl:param name="onunload" select="''"/> <!-- Only placed on the main <svg> element -->

<!-- Placed on Pool, Lane, Activity, Artifact and SequenceFlow -->
<xsl:param name="onmouseover" select="''"/>
<xsl:param name="onmouseout" select="''"/>
<xsl:param name="onclick" select="''"/>
<xsl:param name="onmousedown" select="''"/>
<xsl:param name="onmouseup" select="''"/>
<xsl:param name="onkeypress" select="''"/>
<xsl:param name="onkeydown" select="''"/>
<xsl:param name="onkeyup" select="''"/>

<xsl:param name="graphicsInfoToolId" select="'XPD'"/>
<xsl:param name="connectorGraphicsInfoToolId" select="'XPD.ConnectionInfo'"/>

<!-- ========== END OF PARAMS ======== -->

<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>

<!-- Calculate the final font height (taking scale into account) -->
<xsl:variable name="finalFontPtHeight">
	<xsl:choose>
		<xsl:when test="$noScaleFont = 'true'"><xsl:value-of select="$fontPtHeight * (1 div $scale)"/></xsl:when>
		<xsl:otherwise><xsl:value-of select="$fontPtHeight"/></xsl:otherwise>
	</xsl:choose>
</xsl:variable>

<xsl:variable name="poolLaneHeaderWidth" select="$finalFontPtHeight * 3"/>


<!-- 
=================================================================================================
Root entry point...
=================================================================================================
-->
<xsl:template match="/">

<xsl:text disable-output-escaping="yes">
&lt;!DOCTYPE svg PUBLIC "-//W3C//DTD SVG 1.1//EN" "http://www.w3.org/Graphics/SVG/1.1/DTD/svg11-flat-20030114.dtd"&gt;
</xsl:text>

	<xsl:if test="$cssFile != ''">
		<xsl:processing-instruction name="xml-stylesheet"> type="text/css" href="<xsl:value-of select="$cssFile"/>"</xsl:processing-instruction>
<xsl:text>
</xsl:text>
	</xsl:if>
	
	<xsl:variable name="procId">
		<xsl:choose>
			<xsl:when test="$processId != '' and /xpdl2:Package/xpdl2:WorkflowProcesses/xpdl2:WorkflowProcess[@Id = $processId]">
				<xsl:value-of select="$processId"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="/xpdl2:Package/xpdl2:WorkflowProcesses/xpdl2:WorkflowProcess[1]/@Id"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:variable>

	<xsl:variable name="process" select="/xpdl2:Package/xpdl2:WorkflowProcesses/xpdl2:WorkflowProcess[@Id = $procId]"/>
	
	<xsl:variable name="id">PackageProcess_<xsl:value-of select="/xpdl2:Package/@Id"/><xsl:value-of select="$procId"/></xsl:variable>

	<xsl:variable name="width"><xsl:call-template name="getDiagramWidth"><xsl:with-param name="process" select="$process"/></xsl:call-template></xsl:variable>
	<xsl:variable name="height"><xsl:call-template name="getDiagramHeight"><xsl:with-param name="process" select="$process"/></xsl:call-template></xsl:variable>
	
	<svg width="{$width * $scale}" height="{$height * $scale}" version="1.1" id="{$id}">
		<xsl:if test="$onload">
			<xsl:attribute name="onload"><xsl:value-of select="$onload"/></xsl:attribute>
		</xsl:if>
		<xsl:if test="$onunload">
			<xsl:attribute name="onunload"><xsl:value-of select="$onunload"/></xsl:attribute>
		</xsl:if>
		
		<desc><xsl:value-of select="/xpdl2:Package/@xpdExt:DisplayName"/></desc>

		<xsl:call-template name="outputScripts"/>

		<defs>
			<xsl:call-template name="outputStyles"/>
			<xsl:call-template name="outputStandardObjectGradient"/>
			<xsl:call-template name="outputStandardLighting"/>
			<xsl:call-template name="outputStandardShapes"/>
		</defs>
		
		<xsl:choose>
			<xsl:when test="$processId != '' and /xpdl2:Package/xpdl2:WorkflowProcesses/xpdl2:WorkflowProcess[@Id = $processId]">
				<xsl:apply-templates select="/xpdl2:Package/xpdl2:WorkflowProcesses/xpdl2:WorkflowProcess[@Id = $processId]"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:apply-templates select="/xpdl2:Package/xpdl2:WorkflowProcesses/xpdl2:WorkflowProcess[1]"/>
			</xsl:otherwise>
		</xsl:choose>
		
	</svg>

</xsl:template>

<!--
==============================================
Output the given Process
==============================================
-->
<xsl:template match="xpdl2:WorkflowProcess">
	<xsl:variable name="process" select="."/>

	<!-- NOTE: The 5.5 may seem odd, but the way SVG works with line width and rects etc is that it says 'half of the line is outside of the rectangle coords and half is inside,
		and for some reason you get better sharp lines when you use 1.5 pix line and fractional offset. -->

	<xsl:comment>=================================================
		Graphical Elements Process: <xsl:value-of select="$process/@Name"/>...
		=================================================</xsl:comment>
	
	<g id="{$process/@Id}" transform="translate(5.5, 5.5) scale({$scale})" class="ProcessObject DiagramObject">
		<xsl:call-template name="addObjectFillAttributes">
			<xsl:with-param name="object" select="$process"/>
			<xsl:with-param name="noFill" select="true()"/>
		</xsl:call-template>

		<xsl:call-template name="addObjectScripts"><xsl:with-param name="object" select="$process"/></xsl:call-template>
		
		<xsl:variable name="pools" select="/xpdl2:Package/xpdl2:Pools/xpdl2:Pool[@Process = $process/@Id]"/>

		<xsl:choose>
			<xsl:when test="count($pools) > 0">
				<xsl:for-each select="$pools">
					<xsl:variable name="precedingPools" select="./preceding-sibling::xpdl2:Pool[@Process = $process/@Id]"/>
					<xsl:variable name="precedingLanes" select="$precedingPools/xpdl2:Lanes/xpdl2:Lane"/>

					<xsl:variable name="toolId"><xsl:value-of select="$graphicsInfoToolId"/></xsl:variable>
					<xsl:variable name="yoff" select="sum($precedingLanes/xpdl2:NodeGraphicsInfos/xpdl2:NodeGraphicsInfo[not(@ToolId) or @ToolId = '' or @ToolId = $toolId]/@Height) + (count($precedingPools) * 5)"/>
					
					<xsl:call-template name="outputPool">
						<xsl:with-param name="process" select="$process"/>
						<xsl:with-param name="pool" select="."/>
						<xsl:with-param name="yoff" select="$yoff"/>
						<xsl:with-param name="first">
							<xsl:choose>
								<xsl:when test="position() = 1">true</xsl:when>
								<xsl:otherwise>false</xsl:otherwise>
							</xsl:choose>
						</xsl:with-param>
					</xsl:call-template>
					
				</xsl:for-each>
			
			</xsl:when>
			<xsl:otherwise>
				<!-- No pools / lanes just outpout all the activities in process -->
				<xsl:call-template name="outputActivities">
					<xsl:with-param name="activities" select="$process/xpdl2:Activities/xpdl2:Activity"/>
				</xsl:call-template>
				
				<xsl:call-template name="outputArtifacts">
					<xsl:with-param name="parentObject" select="$process"/>
				</xsl:call-template>
				
			</xsl:otherwise>
		</xsl:choose>
	

		<!-- Output the sequence flows. -->
		<xsl:call-template name="outputSequenceFlows">
			<xsl:with-param name="process" select="$process"/>
			<xsl:with-param name="container" select="$process"/>
		</xsl:call-template>
	
	</g>

</xsl:template>

<!--
==============================================
Output the given Pool
==============================================
-->
<xsl:template name="outputPool">
	<xsl:param name="pool"/>
	<xsl:param name="process"/>
	<xsl:param name="yoff"/>
	<xsl:param name="first"/>
	
	<xsl:comment>=================================================
		Graphical Elements Pool: <xsl:value-of select="$pool/@Name"/>...
		=================================================</xsl:comment>

	<xsl:variable name="lanes" select="$pool/xpdl2:Lanes/xpdl2:Lane"/>
	
	<xsl:variable name="height">
		<xsl:choose>
			<xsl:when test="count($lanes) > 0">
				<xsl:variable name="toolId"><xsl:value-of select="$graphicsInfoToolId"/></xsl:variable>
				<xsl:value-of select="sum($lanes/xpdl2:NodeGraphicsInfos/xpdl2:NodeGraphicsInfo[not(@ToolId) or @ToolId = '' or @ToolId = $toolId]/@Height)"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:text>100</xsl:text>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:variable>
	
	<g id="{$pool/@Id}" class="PoolObject DiagramObject">
		<xsl:attribute name="transform">translate(0, <xsl:value-of select="$yoff"/>)</xsl:attribute>

		<xsl:call-template name="addObjectScripts"><xsl:with-param name="object" select="$process"/></xsl:call-template>

		<!-- output gradient for pool object (if one's necessary) -->
		<xsl:call-template name="outputObjectGradient">
			<xsl:with-param name="object" select="$pool"/>
		</xsl:call-template>

		<xsl:variable name="poolWidth"><xsl:call-template name="getPoolWidth"><xsl:with-param name="process" select="$process"/></xsl:call-template> </xsl:variable>
		
		<rect id="Pool_{$pool/@Id}" x="0" y="0" width="{$poolWidth}" height="{$height}" class="Pool" stroke-width="1.5">
			<!-- Add lighting effects to background pool rect JUST in case there is a shadow on the lgithing effect -->
			<xsl:call-template name="addObjectFillAttributes">
				<xsl:with-param name="object" select="$pool"/>
				<xsl:with-param name="noFill" select="true()"/>
			</xsl:call-template>
			<xsl:call-template name="addStrokeColorAttribute"><xsl:with-param name="object" select="$pool"/></xsl:call-template>
		</rect>
		
		<rect id="MainShape_{$pool/@Id}" x="0" y="0" width="{$poolLaneHeaderWidth}" height="{$height}" class="PoolHeader" stroke-width="1.5">
			<xsl:call-template name="addObjectFillAttributes">
				<xsl:with-param name="object" select="$pool"/>
				<xsl:with-param name="noShadow" select="'true'"/>
			</xsl:call-template>
			<xsl:call-template name="addStrokeColorAttribute"><xsl:with-param name="object" select="$pool"/></xsl:call-template>
		</rect>
		
		<xsl:variable name="xLoc" select="($finalFontPtHeight * 2)"/>
		<xsl:variable name="yLoc" select="$height div 2"/>
		
		<text x="{$xLoc}" y="{$yLoc}" transform="rotate(-90, {$xLoc}, {$yLoc})" text-anchor="middle">
			<xsl:call-template name="addTextColorAttribute"><xsl:with-param name="object" select="$pool"/></xsl:call-template>
			<xsl:call-template name="getName"><xsl:with-param name="object" select="$pool"/></xsl:call-template>
		</text>

		<xsl:variable name="toolId"><xsl:value-of select="$graphicsInfoToolId"/></xsl:variable>

		<xsl:for-each select="$lanes">
			<xsl:variable name="laneYoff" select="sum(./preceding-sibling::xpdl2:Lane/xpdl2:NodeGraphicsInfos/xpdl2:NodeGraphicsInfo[not(@ToolId) or @ToolId = '' or @ToolId = $toolId]/@Height)"/>
			
			<xsl:call-template name="outputLane">
				<xsl:with-param name="process" select="$process"/>
				<xsl:with-param name="lane" select="."/>
				<xsl:with-param name="xoff" select="$poolLaneHeaderWidth"/>
				<xsl:with-param name="yoff" select="$laneYoff"/>
				<xsl:with-param name="poolWidth" select="$poolWidth"/>
				<xsl:with-param name="first">
					<xsl:choose>
						<xsl:when test="$first = 'true' and position() = 1">true</xsl:when>
						<xsl:otherwise>false</xsl:otherwise>
					</xsl:choose>
				</xsl:with-param>
			</xsl:call-template>

		</xsl:for-each>
	</g>
	
</xsl:template>


<!--
==============================================
Output the given Lane
==============================================
-->
<xsl:template name="outputLane">
	<xsl:param name="lane"/>
	<xsl:param name="process"/>
	<xsl:param name="xoff"/>
	<xsl:param name="yoff"/>
	<xsl:param name="poolWidth"/>
	<xsl:param name="first"/>
	
	<xsl:comment>=================================================
			Graphical Elements Lane: <xsl:value-of select="$lane/@Name"/>...
			=================================================</xsl:comment>
			
	<g id="{$lane/@Id}" class="LaneObject DiagramObject">
		<xsl:attribute name="transform">translate(<xsl:value-of select="$xoff"/>, <xsl:value-of select="$yoff"/>)</xsl:attribute>
		<xsl:call-template name="addObjectScripts"><xsl:with-param name="object" select="$process"/></xsl:call-template>

		<xsl:variable name="laneWidth" select="$poolWidth - $xoff"/>
		
		<xsl:variable name="toolId"><xsl:value-of select="$graphicsInfoToolId"/></xsl:variable>
		<xsl:variable name="height" select="$lane/xpdl2:NodeGraphicsInfos/xpdl2:NodeGraphicsInfo[not(@ToolId) or @ToolId = '' or @ToolId = $toolId]/@Height"/>

		<rect id="Lane_{$lane/@Id}" x="0" y="0" width="{$laneWidth}" height="{$height}" class="Lane" fill="white" stroke-width="1.5">
			<xsl:call-template name="addStrokeColorAttribute"><xsl:with-param name="object" select="$lane"/></xsl:call-template>
		</rect>

		<xsl:call-template name="outputObjectGradient">
			<xsl:with-param name="object" select="$lane"/>
		</xsl:call-template>
		
		<xsl:variable name="hdrWidth" select="$poolLaneHeaderWidth"/>
		
		<rect id="MainShape_{$lane/@Id}" x="0" y="0" width="{$hdrWidth}" height="{$height}" class="LaneHeader" stroke-width="1.5">
			<xsl:call-template name="addObjectFillAttributes">
				<xsl:with-param name="object" select="$lane"/>
				<xsl:with-param name="noShadow" select="'true'"/>
			</xsl:call-template>
			<xsl:call-template name="addStrokeColorAttribute"><xsl:with-param name="object" select="$lane"/></xsl:call-template>
		</rect>

		<xsl:variable name="xTextLoc" select="($finalFontPtHeight * 2)"/>
		<xsl:variable name="yTextLoc" select="$height div 2"/>
		
		<text x="{$xTextLoc}" y="{$yTextLoc}" transform="rotate(-90, {$xTextLoc}, {$yTextLoc})" text-anchor="middle">
			<xsl:call-template name="addTextColorAttribute"><xsl:with-param name="object" select="$lane"/></xsl:call-template>
			<xsl:call-template name="getName"><xsl:with-param name="object" select="$lane"/></xsl:call-template>
		</text>
		
		
		<!-- output activities. -->
		<g id="LaneNodes_{$lane/@Id}">

			<!-- Everything in lane is relative to top left of lane. -->
			<xsl:attribute name="transform">translate(<xsl:value-of select="$hdrWidth"/>, 0)</xsl:attribute>

			<xsl:if test="$first = 'true'">
				<text id="_DEBUG_" x="5" y="15">Debug</text>
			</xsl:if>

			<xsl:variable name="toolId"><xsl:value-of select="$graphicsInfoToolId"/></xsl:variable>
			<xsl:call-template name="outputActivities">
				<xsl:with-param name="process" select="$process"/>
				<xsl:with-param name="activities" select="$process/xpdl2:Activities/xpdl2:Activity[xpdl2:NodeGraphicsInfos/xpdl2:NodeGraphicsInfo[not(@ToolId) or @ToolId = '' or @ToolId = $toolId]/@LaneId = $lane/@Id]"/>
			</xsl:call-template>

			<xsl:call-template name="outputArtifacts">
				<xsl:with-param name="parentObject" select="$lane"/>
			</xsl:call-template>
			
		</g>
	</g>
	
</xsl:template>


<!--
==============================================
Output the given activities in their container.
==============================================
-->
<xsl:template name="outputActivities">
	<xsl:param name="process"/>
	<xsl:param name="activities"/>
	
	<xsl:for-each select="$activities">
		<!-- Don't do attached events as top level objects. -->
		<xsl:if test="not(xpdl2:Event/xpdl2:IntermediateEvent/@Target) or xpdl2:Event/xpdl2:IntermediateEvent/@Target = ''">
		
			<!-- Some useful generic values. -->
			<xsl:variable name="act" select="."/>
			<xsl:variable name="toolId"><xsl:value-of select="$graphicsInfoToolId"/></xsl:variable>
			<xsl:variable name="gi" select="$act/xpdl2:NodeGraphicsInfos/xpdl2:NodeGraphicsInfo[not(@ToolId) or @ToolId = '' or @ToolId = $toolId]"/>
			<xsl:variable name="CX" select="($gi/xpdl2:Coordinates/@XCoordinate)"/> <!-- Center X relative to top left of activity -->
			<xsl:variable name="CY" select="($gi/xpdl2:Coordinates/@YCoordinate)"/> <!-- Center Y relative to top left of activity -->
			<xsl:variable name="name"><xsl:call-template name="getName"><xsl:with-param name="object" select="$act"/></xsl:call-template></xsl:variable>
	
			<!-- Create a group for all the parts of the activity. 			
				Everything inside activity group shape lane is relative to top left of lane. -->
			<xsl:variable name="X" select="($gi/xpdl2:Coordinates/@XCoordinate) - ($gi/@Width div 2)"/>
			<xsl:variable name="Y" select="($gi/xpdl2:Coordinates/@YCoordinate) - ($gi/@Height div 2)"/>
			
			<g id="{$act/@Id}" class="ActivityObject DiagramObject">
				<xsl:call-template name="addObjectScripts"><xsl:with-param name="object" select="$act"/></xsl:call-template>

				<xsl:variable name="shapeId">MainShape_<xsl:value-of select="$act/@Id"/></xsl:variable>
				
				<xsl:choose>
					<xsl:when test="$act/xpdl2:Event">
						<!-- Event size may include outlying text so place fixed event size circle at top centre. -->
					
						<xsl:choose>
							<!-- Start Event -->
							<xsl:when test="$act/xpdl2:Event/xpdl2:StartEvent">
								<use id="{$shapeId}" xlink:href="#Std_StartEvent" x="{$CX}" y="{$CY}" stroke-width="1.5">
									<xsl:call-template name="addObjectFillAttributes">
										<xsl:with-param name="object" select="$act"/>
									</xsl:call-template>

									<xsl:call-template name="addStrokeColorAttribute"><xsl:with-param name="object" select="$act"/></xsl:call-template>

								</use>
							</xsl:when>
							
							<!-- Intermediate Event -->
							<xsl:when test="$act/xpdl2:Event/xpdl2:IntermediateEvent">
								<use id="{$shapeId}" xlink:href="#Std_IntermediateEvent" x="{$CX}" y="{$CY}" stroke-width="1.5">
									<xsl:call-template name="addObjectFillAttributes">
										<xsl:with-param name="object" select="$act"/>
									</xsl:call-template>
									<xsl:call-template name="addStrokeColorAttribute"><xsl:with-param name="object" select="$act"/></xsl:call-template>
								</use>

								<use id="{$shapeId}_Inner" xlink:href="#Std_InnerIntermediateEvent" x="{$CX}" y="{$CY}" fill="none" stroke-width="1.0">
									<xsl:call-template name="addStrokeColorAttribute"><xsl:with-param name="object" select="$act"/></xsl:call-template>
								</use>
							</xsl:when>
							
							<!-- End Event -->
							<xsl:when test="$act/xpdl2:Event/xpdl2:EndEvent">
								<use id="{$shapeId}" xlink:href="#Std_EndEvent" x="{$CX}" y="{$CY}" stroke-width="3.0">
									<xsl:call-template name="addObjectFillAttributes">
										<xsl:with-param name="object" select="$act"/>
									</xsl:call-template>
									<xsl:call-template name="addStrokeColorAttribute"><xsl:with-param name="object" select="$act"/></xsl:call-template>
								</use>
							</xsl:when>
							
						</xsl:choose>
					</xsl:when>
					
					<!-- Gateways... -->
					<xsl:when test="$act/xpdl2:Route">
						<use id="{$shapeId}" xlink:href="#Std_Gateway" x="{$CX}" y="{$CY}" stroke-width="1.5">
							<xsl:call-template name="addObjectFillAttributes">
								<xsl:with-param name="object" select="$act"/>
							</xsl:call-template>
							<xsl:call-template name="addStrokeColorAttribute"><xsl:with-param name="object" select="$act"/></xsl:call-template>
						</use>
					</xsl:when>
					
					<!-- Tasks -->
					<xsl:otherwise>
						<!-- Tasks -->
						<rect id="{$shapeId}" x="{$X}" y="{$Y}" rx="10" ry="10" width="{$gi/@Width}" height="{$gi/@Height}" class="Task" stroke-width="1.5">
							<xsl:call-template name="addObjectFillAttributes">
								<xsl:with-param name="object" select="$act"/>
							</xsl:call-template>
							<xsl:call-template name="addStrokeColorAttribute"><xsl:with-param name="object" select="$act"/></xsl:call-template>
						</rect>
						
					</xsl:otherwise>
				</xsl:choose>
				
				<!-- Output the gradient fill details for the object if necessary. -->
				<xsl:call-template name="outputObjectGradient">
					<xsl:with-param name="object" select="$act"/>
				</xsl:call-template>

				<!-- Output the activity label text -->
				<xsl:if test="$name != ''">
					<xsl:variable name="labelId">Label_<xsl:value-of select="$act/@Id"/></xsl:variable>
					
						<xsl:choose>
							<xsl:when test="$act/xpdl2:Event">
								<text id="{$labelId}" x="{$CX}" y="{$CY + ($fixedEventSize div 2) + ($finalFontPtHeight * 1.5)}" text-anchor="middle">
									<xsl:call-template name="addTextColorAttribute"><xsl:with-param name="object" select="$act"/></xsl:call-template>
									<xsl:value-of select="$name"/>
								</text>
							</xsl:when>
							<xsl:when test="$act/xpdl2:Route">
								<text id="{$labelId}" x="{$CX}" y="{$CY + ($fixedGatewayHeight div 2) + ($finalFontPtHeight * 1.5)}" text-anchor="middle">
									<xsl:call-template name="addTextColorAttribute"><xsl:with-param name="object" select="$act"/></xsl:call-template>
									<xsl:value-of select="$name"/>
								</text>
							</xsl:when>
							<xsl:when test="$act/xpdl2:BlockActivity">
								<g id="{$shapeId}_GrpLabel">
									<clipPath id="{$shapeId}_GrpLabel_Clip">
										<rect x="{$X + 10}" y="{$Y}" width="{$gi/@Width - 24}" height="{18 + ($finalFontPtHeight * 3)}"/>
									</clipPath>
									<text id="{$labelId}" clip-path="url(#{$shapeId}_GrpLabel_Clip)" x="{$X + 10}" y="{$Y + 18}" text-anchor="start">
										<xsl:call-template name="addTextColorAttribute"><xsl:with-param name="object" select="$act"/></xsl:call-template>
										<xsl:value-of select="$name"/>
									</text>
								</g> 
							</xsl:when>
							<xsl:when test="$act/xpdl2:Implementation/xpdl2:No">
								<g id="{$shapeId}_GrpLabel">
									<clipPath id="{$shapeId}_GrpLabel_Clip">
										<rect x="{$X + 12}" y="{$Y}" width="{$gi/@Width - 24}" height="{$gi/@Height}"/>
									</clipPath>
									<text id="{$labelId}" clip-path="url(#{$shapeId}_GrpLabel_Clip)" x="{$CX}" y="{$CY + ($finalFontPtHeight * 0.5)}" text-anchor="middle">
										<xsl:call-template name="addTextColorAttribute"><xsl:with-param name="object" select="$act"/></xsl:call-template>
										<xsl:value-of select="$name"/>
									</text>
								</g> 
							</xsl:when>
							<xsl:otherwise>
								<!-- Task with icon -->
								<g id="{$shapeId}_GrpLabel">
									<clipPath id="{$shapeId}_GrpLabel_Clip">
										<rect x="{$X + 10}" y="{$Y}" width="{$gi/@Width - 20}" height="{$gi/@Height}"/>
									</clipPath>
									<text id="{$labelId}" clip-path="url(#{$shapeId}_GrpLabel_Clip)" x="{$CX}" y="{$Y + ($gi/@Height) - ($finalFontPtHeight * 1.5)}" text-anchor="middle">
										<xsl:call-template name="addTextColorAttribute"><xsl:with-param name="object" select="$act"/></xsl:call-template>
										<xsl:value-of select="$name"/>
									</text>
								</g> 
							</xsl:otherwise>
						</xsl:choose>
				</xsl:if>
				
				<!-- Output the activity icon -->
				<xsl:choose>
					<xsl:when test="$act/xpdl2:Event or $act/xpdl2:Route">
						<xsl:call-template name="addActivityIcon">
							<xsl:with-param name="object" select="$act"/>
							<xsl:with-param name="CX" select="$CX"/>
							<xsl:with-param name="CY" select="$CY"/>
						</xsl:call-template>
					</xsl:when>
					<xsl:when test="not($act/xpdl2BlockActivity) and not($act/xpdl2:Implementation/xpdl2:No)">
						<xsl:call-template name="addActivityIcon">
							<xsl:with-param name="object" select="$act"/>
							<xsl:with-param name="CX" select="$CX"/>
							<xsl:with-param name="CY" select="$Y + 4 + 16"/> <!-- Assume 32x32 -->
						</xsl:call-template>
					</xsl:when>
				</xsl:choose>
				
				<xsl:if test="$act/xpdl2:BlockActivity">
					<!-- Recurs into embedded sub-proc objects... -->
					<xsl:variable name="contentYoff" select="($finalFontPtHeight * 3)"/>
					<xsl:variable name="contentXoff" select="10"/>
					<g id="EmbeddedSubProcContent_{$act/xpdl2:BlockActivity/@ActivitySetId}" transform="translate({$X + $contentXoff}, {$Y + $contentYoff})">
						<rect id="{$shapeId}_Inner" x="0" y="0" rx="10" ry="10" width="{$gi/@Width - ($contentXoff * 2)}" height="{$gi/@Height - $contentYoff - 10}" stroke-width="1.5" fill="white" stroke="none"/>
						
						<xsl:variable name="actSet" select="$process/xpdl2:ActivitySets/xpdl2:ActivitySet[@Id = $act/xpdl2:BlockActivity/@ActivitySetId]"/>
						<xsl:if test="$actSet">
							<xsl:call-template name="outputActivities">
								<xsl:with-param name="process" select="$process"/>
								<xsl:with-param name="activities" select="$actSet/xpdl2:Activities/xpdl2:Activity"/>
							</xsl:call-template>
							
							<xsl:call-template name="outputArtifacts">
								<xsl:with-param name="parentObject" select="$actSet"/>
							</xsl:call-template>
							
							<!-- Now output the sequence flows. -->
							<xsl:call-template name="outputSequenceFlows">
								<xsl:with-param name="process" select="$process"/>
								<xsl:with-param name="container" select="$actSet"/>
							</xsl:call-template>
						</xsl:if>
					</g>
				
				</xsl:if>
				
			</g>
			
		</xsl:if>
		
	</xsl:for-each>
	
</xsl:template>

<!--
==============================================
Output the sequence flow for given container.
==============================================
-->
<xsl:template name="outputArtifacts">
	<xsl:param name="parentObject"/>
	
	<xsl:if test="count(/xpdl2:Package/xpdl2:Artifacts/xpdl2:Artifact) > 0">

		<g id="Artifacts_{$parentObject/@Id}">
			<xsl:variable name="toolId"><xsl:value-of select="$graphicsInfoToolId"/></xsl:variable>
			<xsl:variable name="arts" select="/xpdl2:Package/xpdl2:Artifacts/xpdl2:Artifact[xpdl2:NodeGraphicsInfos/xpdl2:NodeGraphicsInfo[not(@ToolId) or @ToolId = '' or @ToolId = $toolId]/@LaneId = $parentObject/@Id]"/>
		
			<xsl:for-each select="$arts">
				<xsl:variable name="art" select="."/>
				
				<xsl:variable name="gi" select="$art/xpdl2:NodeGraphicsInfos/xpdl2:NodeGraphicsInfo[not(@ToolId) or @ToolId = '' or @ToolId = $toolId]"/>
		
				<!-- Create a group for all the parts of the activity. 			
					Everything inside activity group shape lane is relative to top left of lane. -->
				<xsl:variable name="width" select="($gi/@Width)"/> <!-- Center X relative to top left of activity -->
				<xsl:variable name="height" select="($gi/@Height)"/> <!-- Center Y relative to top left of activity -->
				
				<g id="{$art/@Id}" class="ArtifactObject DiagramObject">
					<xsl:call-template name="addObjectScripts"><xsl:with-param name="object" select="$art"/></xsl:call-template>
					<xsl:variable name="shapeId">MainShape_<xsl:value-of select="$art/@Id"/></xsl:variable>

					<xsl:choose>
						<xsl:when test="$art/@ArtifactType = 'DataObject'">
							<xsl:variable name="CX" select="($gi/xpdl2:Coordinates/@XCoordinate)"/> <!-- Center X relative to top left of activity -->
							<xsl:variable name="CY" select="($gi/xpdl2:Coordinates/@YCoordinate)"/> <!-- Center Y relative to top left of activity -->
							<xsl:variable name="name"><xsl:call-template name="getName"><xsl:with-param name="object" select="$art"/></xsl:call-template></xsl:variable>
							<xsl:variable name="X" select="($gi/xpdl2:Coordinates/@XCoordinate) - ($gi/@Width div 2)"/>
							<xsl:variable name="Y" select="($gi/xpdl2:Coordinates/@YCoordinate) - ($gi/@Height div 2)"/>

							<xsl:variable name="dataObjectCornerSize" select="10"/>

							<polygon id="{$shapeId}" stroke-width="1.5" class="DataObject">
								<xsl:attribute name="points">
									<xsl:value-of select="$X"/><xsl:text>,</xsl:text><xsl:value-of select="$Y"/><xsl:text> </xsl:text>
									<xsl:value-of select="$X + $width - $dataObjectCornerSize"/><xsl:text>,</xsl:text><xsl:value-of select="$Y"/><xsl:text> </xsl:text>
									<xsl:value-of select="$X + $width"/><xsl:text>,</xsl:text><xsl:value-of select="$Y + $dataObjectCornerSize"/><xsl:text> </xsl:text>
									<xsl:value-of select="$X + $width"/><xsl:text>,</xsl:text><xsl:value-of select="$Y + $height"/><xsl:text> </xsl:text>
									<xsl:value-of select="$X"/><xsl:text>,</xsl:text><xsl:value-of select="$Y + $height"/>
								</xsl:attribute>
								<xsl:call-template name="addObjectFillAttributes">
									<xsl:with-param name="object" select="$art"/>
								</xsl:call-template>
								<xsl:call-template name="addStrokeColorAttribute"><xsl:with-param name="object" select="$art"/></xsl:call-template>
							</polygon>

							<!-- Output the gradient fill details for the object if necessary. -->
							<xsl:call-template name="outputObjectGradient">
								<xsl:with-param name="object" select="$art"/>
							</xsl:call-template>

							
							<polyline id="{$shapeId}_corner" stroke-width="1.5" class="DataObject">
								<xsl:attribute name="points">
									<xsl:value-of select="$X + $width - $dataObjectCornerSize"/><xsl:text>,</xsl:text><xsl:value-of select="$Y"/><xsl:text> </xsl:text>
									<xsl:value-of select="$X + $width - $dataObjectCornerSize"/><xsl:text>,</xsl:text><xsl:value-of select="$Y + $dataObjectCornerSize"/><xsl:text> </xsl:text>
									<xsl:value-of select="$X + $width"/><xsl:text>,</xsl:text><xsl:value-of select="$Y + $dataObjectCornerSize"/>
								</xsl:attribute>
								
								<xsl:call-template name="addObjectFillAttributes">
									<xsl:with-param name="object" select="$art"/>
									<xsl:with-param name="noShadow"/>
								</xsl:call-template>
								<xsl:call-template name="addStrokeColorAttribute"><xsl:with-param name="object" select="$art"/></xsl:call-template>

							</polyline>
							
							<xsl:if test="$name != ''">
								<xsl:variable name="labelId">Label_<xsl:value-of select="$art/@Id"/></xsl:variable>
								
								<text id="{$labelId}" x="{$CX}" y="{$Y + ($height) + ($finalFontPtHeight * 1.5)}" text-anchor="middle">
									<xsl:call-template name="addTextColorAttribute"><xsl:with-param name="object" select="$art"/></xsl:call-template>
									<xsl:value-of select="$name"/>
								</text>
							</xsl:if>

						</xsl:when>

						<xsl:when test="$art/@ArtifactType = 'Annotation'">
							<xsl:variable name="X" select="($gi/xpdl2:Coordinates/@XCoordinate)"/> <!-- Center X relative to top left of activity -->
							<xsl:variable name="Y" select="($gi/xpdl2:Coordinates/@YCoordinate)"/>
						
							<xsl:variable name="textAnnotCornerSize" select="10"/>

							<xsl:variable name="nominalWidth">
								<xsl:choose>
									<xsl:when test="$width != '' and $width > 0"><xsl:value-of select="$width"/></xsl:when>
									<xsl:otherwise><xsl:value-of select="($finalFontPtHeight * 0.5) * 100"/></xsl:otherwise>
								</xsl:choose>
							</xsl:variable>

							<xsl:variable name="dotPerLine"><xsl:call-template name="countLines"><xsl:with-param name="text" select="$art/@TextAnnotation"/><xsl:with-param name="maxLen" select="($nominalWidth div ($finalFontPtHeight * 0.5)) + 1"/></xsl:call-template></xsl:variable>

							<xsl:variable name="actualHeight">
								<xsl:choose>
									<xsl:when test="$dotPerLine != ''"><xsl:value-of select="(string-length($dotPerLine) * ($finalFontPtHeight * 1.5)) + 10"/></xsl:when>
									<xsl:otherwise><xsl:value-of select="($finalFontPtHeight * 1.5) + 10"/></xsl:otherwise>
								</xsl:choose>
							</xsl:variable>
							
							<polyline id="{$shapeId}" stroke-width="1.5" class="Annotation">
								<xsl:attribute name="points">
									<xsl:value-of select="$X + $textAnnotCornerSize"/><xsl:text>,</xsl:text><xsl:value-of select="$Y - ($actualHeight div 2)"/><xsl:text> </xsl:text>
									<xsl:value-of select="$X"/><xsl:text>,</xsl:text><xsl:value-of select="$Y - ($actualHeight div 2)"/><xsl:text> </xsl:text>
									<xsl:value-of select="$X"/><xsl:text>,</xsl:text><xsl:value-of select="$Y + ($actualHeight div 2)"/><xsl:text> </xsl:text>
									<xsl:value-of select="$X + $textAnnotCornerSize"/><xsl:text>,</xsl:text><xsl:value-of select="$Y + ($actualHeight div 2)"/>
								</xsl:attribute>
								<xsl:call-template name="addObjectFillAttributes">
									<xsl:with-param name="object" select="$art"/>
									<xsl:with-param name="noShadow"/>
								</xsl:call-template>
								<xsl:call-template name="addStrokeColorAttribute"><xsl:with-param name="object" select="$art"/></xsl:call-template>
							</polyline>

							<xsl:if test="$art/@TextAnnotation != ''">
								<xsl:variable name="labelId">Label_<xsl:value-of select="$art/@Id"/></xsl:variable>

								<g id="{$labelId}">
									<xsl:call-template name="outputLines">
										<xsl:with-param name="X" select="$X + 3"/>
										<xsl:with-param name="Y" select="($Y - ($actualHeight div 2)) + ($finalFontPtHeight * 1.5)"/>
										<xsl:with-param name="text" select="$art/@TextAnnotation"/>
										<xsl:with-param name="maxLen" select="($nominalWidth div ($finalFontPtHeight * 0.5)) + 1"/>
										<xsl:with-param name="anchor" select="'start'"/>
										<xsl:with-param name="object" select="$art"/>
										<xsl:with-param name="class" select="'Annotation'"/>
									</xsl:call-template>
								</g>
								
							</xsl:if>
						</xsl:when>
					</xsl:choose>
				</g>
					
			</xsl:for-each>
		</g>
		
	</xsl:if>
	
	
</xsl:template>

<!--
==============================================
Output the sequence flow for given container.
==============================================
-->
<xsl:template name="outputLines">
	<xsl:param name="X"/>
	<xsl:param name="Y"/>
	<xsl:param name="text"/>
	<xsl:param name="maxLen" select="0"/>
	<xsl:param name="anchor" select="'middle'"/>
	<xsl:param name="object"/>
	<xsl:param name="class"/>
	
	<xsl:if test="$text != ''">
		<xsl:variable name="thisLine">
			<xsl:choose>
				<xsl:when test="contains($text, '&#10;') and $maxLen >= string-length(substring-before($text, '&#10;'))"><xsl:value-of select="substring-before($text, '&#10;')"/></xsl:when>
				<xsl:when test="$maxLen >= 1 and string-length($text) > $maxLen"><xsl:value-of select="substring($text, 0, $maxLen)"/></xsl:when>
				<xsl:otherwise><xsl:value-of select="$text"/></xsl:otherwise>
			</xsl:choose>
		</xsl:variable>

		<text x="{$X + 2}" y="{$Y + 2}" text-anchor="{$anchor}" class="{$class}">
			<xsl:call-template name="addTextColorAttribute"><xsl:with-param name="object" select="$object"/></xsl:call-template>
			<xsl:value-of select="$thisLine"/>
		</text>

		<xsl:variable name="rest">
			<xsl:choose>
				<xsl:when test="contains($text, '&#10;') and $maxLen >= string-length(substring-before($text, '&#10;'))"><xsl:value-of select="substring-after($text, '&#10;')"/></xsl:when>
				<xsl:when test="$maxLen >= 1 and string-length($text) > $maxLen"><xsl:value-of select="substring($text, $maxLen + 1)"/></xsl:when>
			</xsl:choose>
		</xsl:variable>

		<xsl:if test="$rest != ''">
			<xsl:call-template name="outputLines">
				<xsl:with-param name="X" select="$X"/>
				<xsl:with-param name="Y" select="$Y + ($finalFontPtHeight * 1.5)"/>
				<xsl:with-param name="text" select="$rest"/>
				<xsl:with-param name="maxLen" select="$maxLen"/>
				<xsl:with-param name="anchor" select="$anchor"/>
				<xsl:with-param name="object" select="$object"/>
				<xsl:with-param name="class" select="$class"/>
			</xsl:call-template>
		</xsl:if>
	</xsl:if>
	
</xsl:template>

<!--
==============================================
Output the sequence flow for given container.
==============================================
-->
<xsl:template name="countLines">
	<xsl:param name="text"/>
	<xsl:param name="maxLen" select="0"/>
	
	<xsl:if test="$text != ''">
		<xsl:variable name="thisLine">
			<xsl:choose>
				<xsl:when test="contains($text, '&#10;') and $maxLen >= string-length(substring-before($text, '&#10;'))"><xsl:value-of select="substring-before($text, '&#10;')"/></xsl:when>
				<xsl:when test="$maxLen >= 1 and string-length($text) > $maxLen"><xsl:value-of select="substring($text, 0, $maxLen)"/></xsl:when>
				<xsl:otherwise><xsl:value-of select="$text"/></xsl:otherwise>
			</xsl:choose>
		</xsl:variable>

		<xsl:text>.</xsl:text>

		<xsl:variable name="rest">
			<xsl:choose>
				<xsl:when test="contains($text, '&#10;') and $maxLen >= string-length(substring-before($text, '&#10;'))"><xsl:value-of select="substring-after($text, '&#10;')"/></xsl:when>
				<xsl:when test="$maxLen >= 1 and string-length($text) > $maxLen"><xsl:value-of select="substring($text, $maxLen + 1)"/></xsl:when>
			</xsl:choose>
		</xsl:variable>

		<xsl:if test="$rest != ''">
			<xsl:call-template name="countLines">
				<xsl:with-param name="text" select="$rest"/>
				<xsl:with-param name="maxLen" select="$maxLen"/>
			</xsl:call-template>
		</xsl:if>
	</xsl:if>
	
</xsl:template>


<!--
==============================================
Output the sequence flow for given container.
==============================================
-->
<xsl:template name="outputSequenceFlows">
	<xsl:param name="process"/>
	<xsl:param name="container"/>

	<g id="SequenceFlows_{$container/@Id}">
		<xsl:variable name="activities" select="$container/xpdl2:Activities/xpdl2:Activity"/>
		
		<xsl:for-each select="$container/xpdl2:Transitions/xpdl2:Transition">
			<xsl:variable name="t" select="."/>
			
			<xsl:variable name="from" select="$activities[@Id = $t/@From]"/>
			<xsl:variable name="to" select="$activities[@Id = $t/@To]"/>
	
			<xsl:variable name="toolId"><xsl:value-of select="$graphicsInfoToolId"/></xsl:variable>
			<xsl:variable name="fromGI" select="$from/xpdl2:NodeGraphicsInfos/xpdl2:NodeGraphicsInfo[not(@ToolId) or @ToolId = '' or @ToolId = $toolId]"/>
			<xsl:variable name="toGI" select="$to/xpdl2:NodeGraphicsInfos/xpdl2:NodeGraphicsInfo[not(@ToolId) or @ToolId = '' or @ToolId = $toolId]"/>
	
			<xsl:variable name="srcCX">
				<xsl:choose>
					<xsl:when test="local-name($container) = 'ActivitySet'">
						<!-- For act set want coords relative to act set parent. -->
						<xsl:value-of select="$fromGI/xpdl2:Coordinates/@XCoordinate"/>
					</xsl:when>
					<xsl:otherwise>
						<xsl:call-template name="getObjectAbsX"><xsl:with-param name="process" select="$process"/><xsl:with-param name="object" select="$from"/></xsl:call-template>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>
	
			<xsl:variable name="srcCY">
				<xsl:choose>
					<xsl:when test="local-name($container) = 'ActivitySet'">
						<!-- For act set want coords relative to act set parent. -->
						<xsl:value-of select="$fromGI/xpdl2:Coordinates/@YCoordinate"/>
					</xsl:when>
					<xsl:otherwise>
						<xsl:call-template name="getObjectAbsY"><xsl:with-param name="process" select="$process"/><xsl:with-param name="object" select="$from"/></xsl:call-template>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>
	
			<xsl:variable name="tgtCX">
				<xsl:choose>
					<xsl:when test="local-name($container) = 'ActivitySet'">
						<!-- For act set want coords relative to act set parent. -->
						<xsl:value-of select="$toGI/xpdl2:Coordinates/@XCoordinate"/>
					</xsl:when>
					<xsl:otherwise>
						<xsl:call-template name="getObjectAbsX"><xsl:with-param name="process" select="$process"/><xsl:with-param name="object" select="$to"/></xsl:call-template>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>
	
			<xsl:variable name="tgtCY">
				<xsl:choose>
					<xsl:when test="local-name($container) = 'ActivitySet'">
						<!-- For act set want coords relative to act set parent. -->
						<xsl:value-of select="$toGI/xpdl2:Coordinates/@YCoordinate"/>
					</xsl:when>
					<xsl:otherwise>
						<xsl:call-template name="getObjectAbsY"><xsl:with-param name="process" select="$process"/><xsl:with-param name="object" select="$to"/></xsl:call-template>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>
	
			<g id="{$t/@Id}" class="SequenceFlowObject DiagramObject">
				<xsl:call-template name="addObjectScripts"><xsl:with-param name="object" select="$t"/></xsl:call-template>

				<!-- Calculate best anchor position depending on the target
					For this we will break down into four possible Zones of target relative to source - roughly like...
				
									  \               /
										\  top     /
										.............
							   left     |           | right
										.............
										/  bottom\
									  /               \
				 -->
				<xsl:variable name="srcLeft">
					<xsl:choose>
						<xsl:when test="$from/xpdl2:Event">
							<xsl:value-of select="$srcCX - ($fixedEventSize div 2)"/>
						</xsl:when>
						<xsl:when test="$from/xpdl2:Route">
							<xsl:value-of select="$srcCX - ($fixedGatewayWidth div 2)"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="$srcCX - ($fromGI/@Width div 2)"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				
				<xsl:variable name="srcRight">
					<xsl:choose>
						<xsl:when test="$from/xpdl2:Event">
							<xsl:value-of select="$srcCX + ($fixedEventSize div 2)"/>
						</xsl:when>
						<xsl:when test="$from/xpdl2:Route">
							<xsl:value-of select="$srcCX + ($fixedGatewayWidth div 2)"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="$srcCX + ($fromGI/@Width div 2)"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>

				<xsl:variable name="srcTop">
					<xsl:choose>
						<xsl:when test="$from/xpdl2:Event">
							<xsl:value-of select="$srcCY - ($fixedEventSize div 2)"/>
						</xsl:when>
						<xsl:when test="$from/xpdl2:Route">
							<xsl:value-of select="$srcCY - ($fixedGatewayHeight div 2)"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="$srcCY - ($fromGI/@Height div 2)"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>

				<xsl:variable name="srcBot">
					<xsl:choose>
						<xsl:when test="$from/xpdl2:Event">
							<xsl:value-of select="$srcCY + ($fixedEventSize div 2)"/>
						</xsl:when>
						<xsl:when test="$from/xpdl2:Route">
							<xsl:value-of select="$srcCY + ($fixedGatewayHeight div 2)"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="$srcCY + ($fromGI/@Height div 2)"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				
				<xsl:variable name="tgtLeft">
					<xsl:choose>
						<xsl:when test="$to/xpdl2:Event">
							<xsl:value-of select="$tgtCX - ($fixedEventSize div 2)"/>
						</xsl:when>
						<xsl:when test="$to/xpdl2:Route">
							<xsl:value-of select="$tgtCX - ($fixedGatewayWidth div 2)"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="$tgtCX - ($toGI/@Width div 2)"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				
				<xsl:variable name="tgtRight">
					<xsl:choose>
						<xsl:when test="$to/xpdl2:Event">
							<xsl:value-of select="$tgtCX + ($fixedEventSize div 2)"/>
						</xsl:when>
						<xsl:when test="$to/xpdl2:Route">
							<xsl:value-of select="$tgtCX + ($fixedGatewayWidth div 2)"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="$tgtCX + ($toGI/@Width div 2)"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>

				<xsl:variable name="tgtTop">
					<xsl:choose>
						<xsl:when test="$to/xpdl2:Event">
							<xsl:value-of select="$tgtCY - ($fixedEventSize div 2)"/>
						</xsl:when>
						<xsl:when test="$to/xpdl2:Route">
							<xsl:value-of select="$tgtCY - ($fixedGatewayHeight div 2)"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="$tgtCY - ($toGI/@Height div 2)"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>

				<xsl:variable name="tgtBot">
					<xsl:choose>
						<xsl:when test="$to/xpdl2:Event">
							<xsl:value-of select="$tgtCY + ($fixedEventSize div 2)"/>
						</xsl:when>
						<xsl:when test="$to/xpdl2:Route">
							<xsl:value-of select="$tgtCY + ($fixedGatewayHeight div 2)"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="$tgtCY + ($toGI/@Height div 2)"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
							 
				<xsl:variable name="srcZone">
					<xsl:choose>
						<xsl:when test="($tgtLeft - $srcRight) > 0">
							<!-- target is right of source check if there's a bigger difference down/up than across-->
							<xsl:choose>
								<xsl:when test="$from/xpdl2:Route and $srcTop > $tgtBot">
									<!-- When source is gateway go out of top if target is above at all. -->
									<xsl:text>top</xsl:text>
								</xsl:when>
								<xsl:when test="$from/xpdl2:Route and $tgtTop > $srcBot">
									<!-- When source is gateway go out of bottom if target is below at all. -->
									<xsl:text>bottom</xsl:text>
								</xsl:when>
								<xsl:when test="($tgtTop - $srcBot) > 0 and ($tgtTop - $srcBot) > ($tgtLeft - $srcRight)">
									<!-- Target is further below than it is towards the right so anchor from bottom. -->
									<xsl:text>bottom</xsl:text>
								</xsl:when>
								<xsl:when test="($srcTop - $tgtBot) > 0 and ($srcTop - $tgtBot) > ($tgtLeft - $srcRight)">
									<!-- Target is further above than it is towards the right so anchor from bottom. -->
									<xsl:text>top</xsl:text>
								</xsl:when>
								<xsl:otherwise>right</xsl:otherwise>
							</xsl:choose>
						</xsl:when>
						<xsl:when test="($srcLeft - $tgtRight) > 0">
							<!-- target is left of source check if there's a bigger difference down/up than across-->
							<xsl:choose>
								<xsl:when test="$from/xpdl2:Route and $srcTop > $tgtBot">
									<!-- When source is gateway go out of top if target is above at all. -->
									<xsl:text>top</xsl:text>
								</xsl:when>
								<xsl:when test="$from/xpdl2:Route and $tgtTop > $srcBot">
									<!-- When source is gateway go out of bottom if target is below at all. -->
									<xsl:text>bottom</xsl:text>
								</xsl:when>
								<xsl:when test="($tgtTop - $srcBot) > 0 and ($tgtTop - $srcBot) > ($srcLeft - $tgtRight)">
									<!-- Target is further below than it is towards the right so anchor from bottom. -->
									<xsl:text>bottom</xsl:text>
								</xsl:when>
								<xsl:when test="($srcTop - $tgtBot) > 0 and ($srcTop - $tgtBot) > ($srcLeft - $tgtRight)">
									<!-- Target is further above than it is towards the right so anchor from bottom. -->
									<xsl:text>top</xsl:text>
								</xsl:when>
								<xsl:otherwise>left</xsl:otherwise>
							</xsl:choose>
						</xsl:when>
						<xsl:when test="($tgtTop - $srcBot) > 0">
							<!-- target is below of source check if there's a bigger difference left/right than up/down -->
							<xsl:choose>
								<xsl:when test="($tgtLeft - $srcRight) > 0 and ($tgtLeft - $srcRight) > ($tgtTop - $srcBot)">
									<!-- Target is further right than it is below so anchor from right. -->
									<xsl:text>right</xsl:text>
								</xsl:when>
								<xsl:when test="($srcLeft - $tgtRight) > 0 and ($srcLeft - $tgtRight) > ($tgtTop - $srcBot)">
									<!-- Target is further left than it is below so anchor from left. -->
									<xsl:text>left</xsl:text>
								</xsl:when>
								<xsl:otherwise>bottom</xsl:otherwise>
							</xsl:choose>
						</xsl:when>
						<xsl:when test="($srcTop - $tgtBot) > 0">
							<!-- target is above of source check if there's a bigger difference left/right than up/down -->
							<xsl:choose>
								<xsl:when test="($tgtLeft - $srcRight) > 0 and ($tgtLeft - $srcRight) > ($srcTop - $tgtBot)">
									<!-- Target is further right than it is above so anchor from right. -->
									<xsl:text>right</xsl:text>
								</xsl:when>
								<xsl:when test="($srcLeft - $tgtRight) > 0 and ($srcLeft - $tgtRight) > ($srcTop - $tgtBot)">
									<!-- Target is further left than it is above so anchor from left -->
									<xsl:text>left</xsl:text>
								</xsl:when>
								<xsl:otherwise>top</xsl:otherwise>
							</xsl:choose>
						</xsl:when>
						<xsl:otherwise>unknown</xsl:otherwise> <!-- OVERLAPPING! -->
					</xsl:choose>
				</xsl:variable>

				<xsl:variable name="tgtZone">
					<xsl:choose>
						<xsl:when test="$to/xpdl2:Route and $tgtTop > $srcBot">
							<!-- When target is gateway go into top if target is below at all. -->
							<xsl:text>top</xsl:text>
						</xsl:when>
						<xsl:when test="$to/xpdl2:Route and $srcTop > $tgtBot">
							<!-- When target is gateway go into bottom if target is above at all. -->
							<xsl:text>bottom</xsl:text>
						</xsl:when>
						<xsl:when test="$srcZone = 'right'">
							<xsl:choose>
								<xsl:when test="($tgtTop - $srcCY) > 0 and ($tgtTop - $srcCY) > ($tgtLeft - $srcRight)">
									<xsl:text>top</xsl:text>
								</xsl:when>
								<xsl:when test="($srcCY - $tgtBot) > 0 and ($srcCY - $tgtBot) > ($tgtLeft - $srcRight)">
									<xsl:text>bottom</xsl:text>
								</xsl:when>
								<xsl:otherwise>
									<xsl:text>left</xsl:text>
								</xsl:otherwise>
							</xsl:choose>
						</xsl:when>
						
						<xsl:when test="$srcZone = 'left'">
							<xsl:choose>
								<xsl:when test="($tgtTop - $srcCY) > 0 and ($tgtTop - $srcCY) > ($srcLeft - $tgtRight)">
									<xsl:text>top</xsl:text>
								</xsl:when>
								<xsl:when test="($srcCY - $tgtBot) > 0 and ($srcTop - $tgtCY) > ($srcLeft - $tgtRight)">
									<xsl:text>bottom</xsl:text>
								</xsl:when>
								<xsl:otherwise>
									<xsl:text>right</xsl:text>
								</xsl:otherwise>
							</xsl:choose>
						</xsl:when>

						<xsl:when test="$srcZone = 'bottom'">
							<xsl:choose>
								<xsl:when test="($tgtLeft - $srcCX) > 0 and ($tgtLeft - $srcCX) > ($tgtTop - $srcBot)">
									<xsl:text>left</xsl:text>
								</xsl:when>
								<xsl:when test="($srcCX - $tgtRight) > 0 and ($srcCX - $tgtRight) > ($tgtTop - $srcBot)">
									<xsl:text>right</xsl:text>
								</xsl:when>
								<xsl:otherwise>
									<xsl:text>top</xsl:text>
								</xsl:otherwise>
							</xsl:choose>
						</xsl:when>

						<xsl:when test="$srcZone = 'top'">
							<xsl:choose>
								<xsl:when test="($tgtLeft - $srcCX) > 0 and ($tgtLeft - $srcCX) > ($srcTop - $tgtBot)">
									<xsl:text>left</xsl:text>
								</xsl:when>
								<xsl:when test="($srcCX - $tgtRight) > 0 and ($srcCX - $tgtRight) > ($srcTop - $tgtBot)">
									<xsl:text>right</xsl:text>
								</xsl:when>
								<xsl:otherwise>
									<xsl:text>bottom</xsl:text>
								</xsl:otherwise>
							</xsl:choose>
						</xsl:when>

						<xsl:otherwise>
							<xsl:text>unknown</xsl:text>
						</xsl:otherwise>
						
					</xsl:choose>
				</xsl:variable>

				<polyline id="MainShape_{$t/@Id}" stroke-width="1.5" class="SequenceFlow">
					<xsl:attribute name="fill">none</xsl:attribute>
					
					<xsl:attribute name="points">
						<!-- Output the start anchor point. -->
						<xsl:choose>
							<xsl:when test="$srcZone = 'right'"><xsl:value-of select="$srcRight"/><xsl:text>,</xsl:text><xsl:value-of select="$srcCY"/></xsl:when>
							<xsl:when test="$srcZone = 'left'"><xsl:value-of select="$srcLeft"/><xsl:text>,</xsl:text><xsl:value-of select="$srcCY"/></xsl:when>
							<xsl:when test="$srcZone = 'bottom'"><xsl:value-of select="$srcCX"/><xsl:text>,</xsl:text><xsl:value-of select="$srcBot"/></xsl:when>
							<xsl:when test="$srcZone = 'top'"><xsl:value-of select="$srcCX"/><xsl:text>,</xsl:text><xsl:value-of select="$srcTop"/></xsl:when>
							<xsl:otherwise><xsl:value-of select="$srcRight"/>,<xsl:value-of select="$srcCY"/></xsl:otherwise>
						</xsl:choose>
						
						<xsl:text> </xsl:text>
						
						<!-- Output any point's required between start and end. -->
						<xsl:choose>
							<xsl:when test="$srcZone = 'right'">
								<xsl:choose>
									<xsl:when test="$tgtZone = 'left' and $srcCY != $tgtCY">
										<xsl:value-of select="$srcRight + (($tgtLeft - $srcRight) div 2)"/><xsl:text>,</xsl:text><xsl:value-of select="$srcCY"/>
										<xsl:text> </xsl:text>
										<xsl:value-of select="$srcRight + (($tgtLeft - $srcRight) div 2)"/><xsl:text>,</xsl:text><xsl:value-of select="$tgtCY"/>
										<xsl:text> </xsl:text>
									</xsl:when>
									<xsl:when test="$tgtZone = 'bottom'">
										<xsl:value-of select="$tgtCX"/><xsl:text>,</xsl:text><xsl:value-of select="$srcCY"/>
										<xsl:text> </xsl:text>
									</xsl:when>
									<xsl:when test="$tgtZone = 'top'">
										<xsl:value-of select="$tgtCX"/><xsl:text>,</xsl:text><xsl:value-of select="$srcCY"/>
										<xsl:text> </xsl:text>
									</xsl:when>
								</xsl:choose>
							</xsl:when>

							<xsl:when test="$srcZone = 'left'">
								<xsl:choose>
									<xsl:when test="$tgtZone = 'right' and $srcCY != $tgtCY">
										<xsl:value-of select="$tgtRight + (($srcLeft - $tgtRight) div 2)"/><xsl:text>,</xsl:text><xsl:value-of select="$srcCY"/>
										<xsl:text> </xsl:text>
										<xsl:value-of select="$tgtRight + (($srcLeft - $tgtRight) div 2)"/><xsl:text>,</xsl:text><xsl:value-of select="$tgtCY"/>
										<xsl:text> </xsl:text>
									</xsl:when>
									<xsl:when test="$tgtZone = 'bottom'">
										<xsl:value-of select="$tgtCX"/><xsl:text>,</xsl:text><xsl:value-of select="$srcCY"/>
										<xsl:text> </xsl:text>
									</xsl:when>
									<xsl:when test="$tgtZone = 'top'">
										<xsl:value-of select="$tgtCX"/><xsl:text>,</xsl:text><xsl:value-of select="$srcCY"/>
										<xsl:text> </xsl:text>
									</xsl:when>
								</xsl:choose>
							</xsl:when>

							<xsl:when test="$srcZone = 'bottom'">
								<xsl:choose>
									<xsl:when test="$tgtZone = 'top' and $srcCX != $tgtCX">
										<xsl:value-of select="$srcCX"/><xsl:text>,</xsl:text><xsl:value-of select="$srcBot + (($tgtTop - $srcBot) div 2)"/>
										<xsl:text> </xsl:text>
										<xsl:value-of select="$tgtCX"/><xsl:text>,</xsl:text><xsl:value-of select="$srcBot + (($tgtTop - $srcBot) div 2)"/>
										<xsl:text> </xsl:text>
									</xsl:when>
									<xsl:when test="$tgtZone = 'right'">
										<xsl:value-of select="$srcCX"/><xsl:text>,</xsl:text><xsl:value-of select="$tgtCY"/>
										<xsl:text> </xsl:text>
									</xsl:when>
									<xsl:when test="$tgtZone = 'left'">
										<xsl:value-of select="$srcCX"/><xsl:text>,</xsl:text><xsl:value-of select="$tgtCY"/>
										<xsl:text> </xsl:text>
									</xsl:when>
								</xsl:choose>
							</xsl:when>
							
							<xsl:when test="$srcZone = 'top'">
								<xsl:choose>
									<xsl:when test="$tgtZone = 'bottom' and $srcCX != $tgtCX">
										<xsl:value-of select="$srcCX"/><xsl:text>,</xsl:text><xsl:value-of select="$tgtBot + (($srcTop - $tgtBot) div 2)"/>
										<xsl:text> </xsl:text>
										<xsl:value-of select="$tgtCX"/><xsl:text>,</xsl:text><xsl:value-of select="$tgtBot + (($srcTop - $tgtBot) div 2)"/>
										<xsl:text> </xsl:text>
									</xsl:when>
									<xsl:when test="$tgtZone = 'right'">
										<xsl:value-of select="$srcCX"/><xsl:text>,</xsl:text><xsl:value-of select="$tgtCY"/>
										<xsl:text> </xsl:text>
									</xsl:when>
									<xsl:when test="$tgtZone = 'left'">
										<xsl:value-of select="$srcCX"/><xsl:text>,</xsl:text><xsl:value-of select="$tgtCY"/>
										<xsl:text> </xsl:text>
									</xsl:when>
								</xsl:choose>
							</xsl:when>
							
						</xsl:choose>
						
						
						<!-- Output the End anchor point. -->
						<xsl:choose>
							<xsl:when test="$tgtZone = 'right'"><xsl:value-of select="$tgtRight"/><xsl:text>,</xsl:text><xsl:value-of select="$tgtCY"/></xsl:when>
							<xsl:when test="$tgtZone = 'left'"><xsl:value-of select="$tgtLeft"/><xsl:text>,</xsl:text><xsl:value-of select="$tgtCY"/></xsl:when>
							<xsl:when test="$tgtZone = 'bottom'"><xsl:value-of select="$tgtCX"/><xsl:text>,</xsl:text><xsl:value-of select="$tgtBot"/></xsl:when>
							<xsl:when test="$tgtZone = 'top'"><xsl:value-of select="$tgtCX"/><xsl:text>,</xsl:text><xsl:value-of select="$tgtTop"/></xsl:when>
							<xsl:otherwise><xsl:value-of select="$tgtLeft"/>,<xsl:value-of select="$tgtCY"/></xsl:otherwise>
						</xsl:choose>
						
					</xsl:attribute>

					<xsl:call-template name="addStrokeColorAttribute"><xsl:with-param name="object" select="$t"/></xsl:call-template>
					<xsl:call-template name="addObjectFillAttributes">
						<xsl:with-param name="object" select="$t"/>
						<xsl:with-param name="noFill" select="true()"/>
					</xsl:call-template>

				</polyline>

				<xsl:variable name="lineColor"><xsl:call-template name="getRGBBorderColor"><xsl:with-param name="object" select="$t"/></xsl:call-template></xsl:variable>
				
				<!-- Add Source connection decorations -->
				<xsl:choose>
					<xsl:when test="$t/xpdl2:Condition/@Type = 'CONDITION' and not($from/xpdl2:Route)">
						<xsl:choose>
							<xsl:when test="$srcZone = 'right'">
								<use id="SourceDecoration_{$t/@Id}" xlink:href="#Std_RightDiamond" x="{$srcRight}" y="{$srcCY}">
									<xsl:attribute name="fill"><xsl:value-of select="$lineColor"/></xsl:attribute>
									<xsl:call-template name="addStrokeColorAttribute"><xsl:with-param name="object" select="$t"/></xsl:call-template>
								</use>
							</xsl:when>
							<xsl:when test="$srcZone = 'left'">
								<use id="SourceDecoration_{$t/@Id}" xlink:href="#Std_LeftDiamond" x="{$srcLeft}" y="{$srcCY}">
									<xsl:attribute name="fill"><xsl:value-of select="$lineColor"/></xsl:attribute>
									<xsl:call-template name="addStrokeColorAttribute"><xsl:with-param name="object" select="$t"/></xsl:call-template>
								</use>
							</xsl:when>
							<xsl:when test="$srcZone = 'top'">
								<use id="SourceDecoration_{$t/@Id}" xlink:href="#Std_UpDiamond" x="{$srcCX}" y="{$srcTop}">
									<xsl:attribute name="fill"><xsl:value-of select="$lineColor"/></xsl:attribute>
									<xsl:call-template name="addStrokeColorAttribute"><xsl:with-param name="object" select="$t"/></xsl:call-template>
								</use>
							</xsl:when>
							<xsl:when test="$srcZone = 'bottom'">
								<use id="SourceDecoration_{$t/@Id}" xlink:href="#Std_DownDiamond" x="{$srcCX}" y="{$srcBot}">
									<xsl:attribute name="fill"><xsl:value-of select="$lineColor"/></xsl:attribute>
									<xsl:call-template name="addStrokeColorAttribute"><xsl:with-param name="object" select="$t"/></xsl:call-template>
								</use>
							</xsl:when>
						</xsl:choose>
					</xsl:when>
					<xsl:when test="$t/xpdl2:Condition/@Type = 'OTHERWISE'">
						<xsl:choose>
							<xsl:when test="$srcZone = 'right'">
								<use id="SourceDecoration_{$t/@Id}" xlink:href="#Std_RightSlash" x="{$srcRight}" y="{$srcCY}">
									<xsl:attribute name="fill"><xsl:value-of select="$lineColor"/></xsl:attribute>
									<xsl:call-template name="addStrokeColorAttribute"><xsl:with-param name="object" select="$t"/></xsl:call-template>
								</use>
							</xsl:when>
							<xsl:when test="$srcZone = 'left'">
								<use id="SourceDecoration_{$t/@Id}" xlink:href="#Std_LeftSlash" x="{$srcLeft}" y="{$srcCY}">
									<xsl:attribute name="fill"><xsl:value-of select="$lineColor"/></xsl:attribute>
									<xsl:call-template name="addStrokeColorAttribute"><xsl:with-param name="object" select="$t"/></xsl:call-template>
								</use>
							</xsl:when>
							<xsl:when test="$srcZone = 'top'">
								<use id="SourceDecoration_{$t/@Id}" xlink:href="#Std_UpSlash" x="{$srcCX}" y="{$srcTop}">
									<xsl:attribute name="fill"><xsl:value-of select="$lineColor"/></xsl:attribute>
									<xsl:call-template name="addStrokeColorAttribute"><xsl:with-param name="object" select="$t"/></xsl:call-template>
								</use>
							</xsl:when>
							<xsl:when test="$srcZone = 'bottom'">
								<xsl:variable name="decYOff">
									<xsl:variable name="name"><xsl:call-template name="getName"><xsl:with-param name="object" select="$from"/></xsl:call-template></xsl:variable>
									<xsl:choose>
										<xsl:when test="$name != ''"><xsl:value-of select="$srcBot + ($finalFontPtHeight * 1.5)"/></xsl:when>
										<xsl:otherwise><xsl:value-of select="$srcBot"/></xsl:otherwise>
									</xsl:choose>
								</xsl:variable>
								
								<use id="SourceDecoration_{$t/@Id}" xlink:href="#Std_DownSlash" x="{$srcCX}" y="{$decYOff}">
									<xsl:attribute name="fill"><xsl:value-of select="$lineColor"/></xsl:attribute>
									<xsl:call-template name="addStrokeColorAttribute"><xsl:with-param name="object" select="$t"/></xsl:call-template>
								</use>
							</xsl:when>
						</xsl:choose>
					</xsl:when>
				</xsl:choose>

				<!-- Add Target Source connection decorations -->
				<xsl:choose>
					<xsl:when test="$tgtZone = 'left'">
						<use id="TargetDecoration_{$t/@Id}" xlink:href="#Std_RightArrow" x="{$tgtLeft}" y="{$tgtCY}">
							<xsl:attribute name="fill"><xsl:value-of select="$lineColor"/></xsl:attribute>
							<xsl:call-template name="addStrokeColorAttribute"><xsl:with-param name="object" select="$t"/></xsl:call-template>
						</use>
					</xsl:when>
					<xsl:when test="$tgtZone = 'right'">
						<use id="TargetDecoration_{$t/@Id}" xlink:href="#Std_LeftArrow" x="{$tgtRight}" y="{$tgtCY}">
							<xsl:attribute name="fill"><xsl:value-of select="$lineColor"/></xsl:attribute>
							<xsl:call-template name="addStrokeColorAttribute"><xsl:with-param name="object" select="$t"/></xsl:call-template>
						</use>
					</xsl:when>
					<xsl:when test="$tgtZone = 'top'">
						<use id="TargetDecoration_{$t/@Id}" xlink:href="#Std_DownArrow" x="{$tgtCX}" y="{$tgtTop}">
							<xsl:attribute name="fill"><xsl:value-of select="$lineColor"/></xsl:attribute>
							<xsl:call-template name="addStrokeColorAttribute"><xsl:with-param name="object" select="$t"/></xsl:call-template>
						</use>
					</xsl:when>
					<xsl:when test="$tgtZone = 'bottom'">
						<use id="TargetDecoration_{$t/@Id}" xlink:href="#Std_UpArrow" x="{$tgtCX}" y="{$tgtBot}">
							<xsl:attribute name="fill"><xsl:value-of select="$lineColor"/></xsl:attribute>
							<xsl:call-template name="addStrokeColorAttribute"><xsl:with-param name="object" select="$t"/></xsl:call-template>
						</use>
					</xsl:when>
				</xsl:choose>
				
			</g>
		</xsl:for-each>
	</g>
</xsl:template>

<!--
==============================================
Get Object centre X coordinate for activity/artifact (in absolute).
==============================================
-->
<xsl:template name="getObjectAbsX">
	<xsl:param name="process"/>
	<xsl:param name="object"/>
	<xsl:param name="nestedOffset" select="0"/>

	<xsl:choose>
		<xsl:when test="local-name($object) = 'Lane'">
			<!-- go back as far as lane, add offset to start of lane. -->
			<xsl:value-of select="$nestedOffset + ($poolLaneHeaderWidth * 2)"/>

		</xsl:when>
		
		<xsl:otherwise>
			<!-- Embedded sub-process that we have recursed upto. -->
			<xsl:variable name="toolId"><xsl:value-of select="$graphicsInfoToolId"/></xsl:variable>
			<xsl:variable name="gi" select="$object/xpdl2:NodeGraphicsInfos/xpdl2:NodeGraphicsInfo[not(@ToolId) or @ToolId = '' or @ToolId = $toolId]"/>	
			
			<xsl:variable name="newX">
				<xsl:choose>
					<xsl:when test="local-name($object) = 'Activity' and $object/xpdl2:BlockActivity and $nestedOffset != 0">
						<!-- This is an embedded sub-proc activity that we have recursed into, therefore we are adding offset to OUR top left to an inner activity's offset within ourselves. -->
						<xsl:value-of select="$nestedOffset + ($gi/xpdl2:Coordinates/@XCoordinate - ($gi/@Width div 2))"/>
					</xsl:when>
					<xsl:otherwise>
						<!-- just a normal activity/artifact use our center coord. -->
						<xsl:value-of select="$gi/xpdl2:Coordinates/@XCoordinate"/>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>
			
			<!-- Now recurs up thru my parent! -->
			<xsl:choose>
				<xsl:when test="/xpdl2:Package/xpdl2:Pools/xpdl2:Pool[@Process = $process/@Id]/xpdl2:Lanes/xpdl2:Lane[@Id = $gi/@LaneId]">
					<!-- my parent is a lane. -->
					<xsl:variable name="lane" select="/xpdl2:Package/xpdl2:Pools/xpdl2:Pool[@Process = $process/@Id]/xpdl2:Lanes/xpdl2:Lane[@Id = $gi/@LaneId]"/>
					<xsl:call-template name="getObjectAbsX">
						<xsl:with-param name="process" select="$process"/>
						<xsl:with-param name="object" select="$lane"/>
						<xsl:with-param name="nestedOffset" select="$newX"/>
					</xsl:call-template>
				</xsl:when>
				
				<xsl:when test="$object/ancestor::ActivitySet">
					<!-- My Parent is an activity set. -->
					<xsl:variable name="actSet" select="$object/ancestor::ActivitySet"/>
					<xsl:variable name="embSubProc" select="$process//xpdl2:Activity[xpdl2:BlockActivity/@ActivitySetId = $actSet/@Id]"/>
					
					<xsl:choose>
						<xsl:when test="$embSubProc">
							<xsl:call-template name="getObjectAbsX">
								<xsl:with-param name="process" select="$process"/>
								<xsl:with-param name="object" select="$embSubProc"/>
								<xsl:with-param name="nestedOffset" select="$newX"/>
							</xsl:call-template>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="$newX"/>
						</xsl:otherwise>
					</xsl:choose>
					
				</xsl:when>
				
				<xsl:when test="$process/xpdl2:ActivitySets/xpdl2:ActivitySet[@Id = $gi/@LaneId]">
					<!-- Object with activity set id as lane id should count as 'in the activity set' (i.e. artifacts in embedded sub-procs. -->
					<xsl:variable name="actSet" select="$process/xpdl2:ActivitySets/xpdl2:ActivitySet[@Id = $gi/@LaneId]"/>
					<xsl:variable name="embSubProc" select="$process//xpdl2:Activity[xpdl2:BlockActivity/@ActivitySetId = $actSet/@Id]"/>
					
					<xsl:choose>
						<xsl:when test="$embSubProc">
							<xsl:call-template name="getObjectAbsX">
								<xsl:with-param name="process" select="$process"/>
								<xsl:with-param name="object" select="$embSubProc"/>
								<xsl:with-param name="nestedOffset" select="$newX"/>
							</xsl:call-template>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="$newX"/>
						</xsl:otherwise>
					</xsl:choose>

				</xsl:when>
				
				<xsl:otherwise>
					<!-- My parent is not embedded sub-proc or lane, must be process without lanes. -->
					<xsl:value-of select="$newX"/>
				</xsl:otherwise>
			</xsl:choose>
			
		</xsl:otherwise>
	</xsl:choose>
	
</xsl:template>

<!--
==============================================
Get Object centre Y coordinate for activity/artifact (in absolute diagram coords).
==============================================
-->
<xsl:template name="getObjectAbsY">
	<xsl:param name="process"/>
	<xsl:param name="object"/>
	<xsl:param name="nestedOffset" select="0"/>

	<xsl:choose>
		<xsl:when test="local-name($object) = 'Lane'">
			<!-- go back as far as lane, add offset to start of lane. -->
			<xsl:variable name="lane" select="$object"/>
			<xsl:variable name="pool" select="$lane/ancestor::xpdl2:Pool"/>

			<xsl:variable name="precedingPools" select="$pool/preceding-sibling::xpdl2:Pool[@Process = $process/@Id]"/>

			<xsl:variable name="lanesInPrecedingPools" select="$precedingPools/xpdl2:Lanes/xpdl2:Lane"/>
			<xsl:variable name="precedingLanesInThisPool" select="$lane/preceding-sibling::xpdl2:Lane"/>
			
			<xsl:variable name="toolId"><xsl:value-of select="$graphicsInfoToolId"/></xsl:variable>
			<xsl:variable name="precedingPoolsHeight" select="sum($lanesInPrecedingPools/xpdl2:NodeGraphicsInfos/xpdl2:NodeGraphicsInfo[not(@ToolId) or @ToolId = '' or @ToolId = $toolId]/@Height) + (count($precedingPools) * 5)"/>
			<xsl:variable name="precedingLanesHeight" select="sum($precedingLanesInThisPool/xpdl2:NodeGraphicsInfos/xpdl2:NodeGraphicsInfo[not(@ToolId) or @ToolId = '' or @ToolId = $toolId]/@Height)"/>

			<xsl:value-of select="$nestedOffset + $precedingLanesHeight + $precedingPoolsHeight"/>

		</xsl:when>
		
		<xsl:otherwise>
			<!-- Object inside a lane or embedded sub-proc -->
			<xsl:variable name="toolId"><xsl:value-of select="$graphicsInfoToolId"/></xsl:variable>
			<xsl:variable name="gi" select="$object/xpdl2:NodeGraphicsInfos/xpdl2:NodeGraphicsInfo[not(@ToolId) or @ToolId = '' or @ToolId = $toolId]"/>	
			
			<xsl:variable name="newY">
				<xsl:choose>
					<xsl:when test="local-name($object) = 'Activity' and $object/xpdl2:BlockActivity and $nestedOffset != 0">
						<!-- This is an embedded sub-proc activity that we have recursed into, therefore we are adding offset to OUR top left to an inner activity's offset within ourselves. -->
						<xsl:value-of select="$nestedOffset + ($gi/xpdl2:Coordinates/@YCoordinate - ($gi/@Height div 2))"/>
					</xsl:when>
					<xsl:otherwise>
						<!-- just a normal activity/artifact use our center coord. -->
						<xsl:value-of select="$gi/xpdl2:Coordinates/@YCoordinate"/>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>
			
			<!-- Now recurs up thru my parent! -->
			<xsl:choose>
				<xsl:when test="/xpdl2:Package/xpdl2:Pools/xpdl2:Pool[@Process = $process/@Id]/xpdl2:Lanes/xpdl2:Lane[@Id = $gi/@LaneId]">
					<!-- my parent is a lane. -->
					<xsl:variable name="lane" select="/xpdl2:Package/xpdl2:Pools/xpdl2:Pool[@Process = $process/@Id]/xpdl2:Lanes/xpdl2:Lane[@Id = $gi/@LaneId]"/>
					<xsl:call-template name="getObjectAbsY">
						<xsl:with-param name="process" select="$process"/>
						<xsl:with-param name="object" select="$lane"/>
						<xsl:with-param name="nestedOffset" select="$newY"/>
					</xsl:call-template>
				</xsl:when>
				
				<xsl:when test="$object/ancestor::ActivitySet">
					<!-- My Parent is an activity set. -->
					<xsl:variable name="actSet" select="$object/ancestor::ActivitySet"/>
					<xsl:variable name="embSubProc" select="$process//xpdl2:Activity[xpdl2:BlockActivity/@ActivitySetId = $actSet/@Id]"/>
					
					<xsl:choose>
						<xsl:when test="$embSubProc">
							<xsl:call-template name="getObjectAbsY">
								<xsl:with-param name="process" select="$process"/>
								<xsl:with-param name="object" select="$embSubProc"/>
								<xsl:with-param name="nestedOffset" select="$newY"/>
							</xsl:call-template>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="$newY"/>
						</xsl:otherwise>
					</xsl:choose>
					
				</xsl:when>
				
				<xsl:when test="$process/xpdl2:ActivitySets/xpdl2:ActivitySet[@Id = $gi/@LaneId]">
					<!-- Object with activity set id as lane id should count as 'in the activity set' (i.e. artifacts in embedded sub-procs. -->
					<xsl:variable name="actSet" select="$process/xpdl2:ActivitySets/xpdl2:ActivitySet[@Id = $gi/@LaneId]"/>
					<xsl:variable name="embSubProc" select="$process//xpdl2:Activity[xpdl2:BlockActivity/@ActivitySetId = $actSet/@Id]"/>
					
					<xsl:choose>
						<xsl:when test="$embSubProc">
							<xsl:call-template name="getObjectAbsY">
								<xsl:with-param name="process" select="$process"/>
								<xsl:with-param name="object" select="$embSubProc"/>
								<xsl:with-param name="nestedOffset" select="$newY"/>
							</xsl:call-template>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="$newY"/>
						</xsl:otherwise>
					</xsl:choose>

				</xsl:when>
				
				<xsl:otherwise>
					<!-- My parent is not embedded sub-proc or lane, must be process without lanes. -->
					<xsl:value-of select="$newY"/>
				</xsl:otherwise>
			</xsl:choose>
			
		</xsl:otherwise>
	</xsl:choose>
	
</xsl:template>


<!--
==============================================
Output the total height of the diagram for given process.
==============================================
-->
<xsl:template name="getDiagramWidth">
	<xsl:param name="process"/>

	<xsl:variable name="poolWidth"><xsl:call-template name="getPoolWidth"><xsl:with-param name="process" select="$process"/></xsl:call-template></xsl:variable>
	
	<xsl:value-of select="$poolWidth + 50"/>
</xsl:template>

<!--
==============================================
Output the total height of the diagram for given process.
==============================================
-->
<xsl:template name="getDiagramHeight">
	<xsl:param name="process"/>
	
	<xsl:variable name="pools" select="/xpdl2:Package/xpdl2:Pools/xpdl2:Pool[@Process = $process/@Id]"/>

	<xsl:choose>
		<xsl:when test="count($pools) > 0">
			<xsl:variable name="toolId"><xsl:value-of select="$graphicsInfoToolId"/></xsl:variable>
			<xsl:value-of select="sum($pools/xpdl2:Lanes/xpdl2:Lane/xpdl2:NodeGraphicsInfos/xpdl2:NodeGraphicsInfo[not(@ToolId) or @ToolId = '' or @ToolId = $toolId]/@Height) + (count($pools) * 5) + 50"/>
		</xsl:when>
		<xsl:otherwise>
			<!-- There are no lanes so we can calc the height from lowest (Y * Width/2) object -->
			<xsl:variable name="toolId"><xsl:value-of select="$graphicsInfoToolId"/></xsl:variable>
			<xsl:variable name="coords" select="$process//xpdl2:NodeGraphicsInfo[not(@ToolId) or @ToolId = '' or @ToolId = $toolId]/xpdl2:Coordinates"/>
			
			<xsl:choose>
				<xsl:when test="count($coords) > 0">
					<xsl:for-each select="$coords">
						<xsl:sort data-type="number" select="@YCoordinate + (@Height div 2)"/>
							
						<xsl:if test="position()=1"><xsl:value-of select="@YCoordinate + (../@Height div 2) + 50"/></xsl:if>
						
					</xsl:for-each>
				</xsl:when>
				<xsl:otherwise>
					<xsl:text>100</xsl:text>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:otherwise>
	</xsl:choose>
	
</xsl:template>

<!--
==============================================
Output the width of the pool(s) for given process.
==============================================
-->
<xsl:template name="getPoolWidth">
	<xsl:param name="process"/>

	<!-- TODO - This algorithm WILL NOT WORK if right most object by centre-location is not the object whose right edges is furtherest right 
						unlikely, but worth mentioning (i.e. if end event is furthest right by location there may be, say, an embedded sub-proc 
						whose centre is further left but is big enough for its right edge to be further right. 
			Would help if the Lane had a width (in Studio XPDL, others are probably ok.
		-->
	<xsl:variable name="toolId"><xsl:value-of select="$graphicsInfoToolId"/></xsl:variable>
	<xsl:variable name="coords" select="$process//xpdl2:NodeGraphicsInfo[not(@ToolId) or @ToolId = '' or @ToolId = $toolId]/xpdl2:Coordinates"/>
	
	<xsl:choose>
		<xsl:when test="count($coords) > 0">
			<xsl:for-each select="$coords">
				<xsl:sort data-type="number"  order="descending" select="@XCoordinate"/>
					
				<xsl:if test="position()=1"><xsl:value-of select="@XCoordinate + (../@Width div 2) + 150"/></xsl:if>
				
			</xsl:for-each>
		</xsl:when>
		<xsl:otherwise>
			<xsl:text>100</xsl:text>
		</xsl:otherwise>
	</xsl:choose>
	
</xsl:template>


<!--
==============================================
Output name for given object
==============================================
-->
<xsl:template name="getName">
	<xsl:param name="object"/>
	
	<xsl:choose>
		<xsl:when test="$object/@xpdExt:DisplayName != ''"><xsl:value-of select="$object/@xpdExt:DisplayName"/></xsl:when>
		<xsl:when test="$object/@Name != ''"><xsl:value-of select="$object/@Name"/></xsl:when>
	</xsl:choose>

</xsl:template>

<!--
==============================================
If in gradient fill mode output the linerarGradient section for the given object.
==============================================
-->
<xsl:template name="outputObjectGradient">
	<xsl:param name="object"/>

	<xsl:if test="$gradientOrLighting = 'Gradient'">
		<xsl:variable name="gradientId"><xsl:call-template name="getObjectGradientId"><xsl:with-param name="object" select="$object"/></xsl:call-template></xsl:variable>

		<xsl:choose>
			<xsl:when test="starts-with($gradientId, '__Gradient_Standard')">
				<!-- using a standard object gradient so don't output a specific one. -->
			</xsl:when>
			<xsl:when test="local-name($object) = 'Pool' or local-name($object) = 'Lane'">
				<!-- Left to right gradient for Pool/Lane --> 
				<xsl:variable name="color"><xsl:call-template name="getRGBFillColor"><xsl:with-param name="object" select="$object"/></xsl:call-template></xsl:variable>
				
				<defs>
					<linearGradient id = "{$gradientId}" x1 = "0%" y1 = "50%" x2 = "100%" y2 = "50%">
						<stop stop-color="{$color}" offset = "0%"/>
						<stop stop-color = "white" offset = "100%"/>
					</linearGradient>
				</defs>
			</xsl:when>
			
			<xsl:otherwise>
				<!-- Diagonal gradient for everything else --> 
				<xsl:variable name="color"><xsl:call-template name="getRGBFillColor"><xsl:with-param name="object" select="$object"/></xsl:call-template></xsl:variable>
				
				<defs>
					<linearGradient id = "{$gradientId}" x1 = "0%" y1 = "0%" x2 = "100%" y2 = "100%">
						<stop stop-color="{$color}" offset = "0%"/>
						<stop stop-color = "white" offset = "100%"/>
					</linearGradient>
				</defs>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:if>
	
</xsl:template>

<!--
==============================================
Add the fill / filter attributes to the current graphical output element depending on lighting / gradient settings etc.
==============================================
-->
<xsl:template name="addObjectFillAttributes">
	<xsl:param name="object"/>
	<xsl:param name="noShadow" select="'false'"/>
	<xsl:param name="noFill" select="false()"/>

	<xsl:choose>
		<xsl:when test="(local-name($object) = 'Artifact' and count($object/xpdl2:DataObject) = 0)">
			<xsl:attribute name="fill">none</xsl:attribute>
		</xsl:when>
		<xsl:when test="$gradientOrLighting = 'BlackAndWhite'">
			<xsl:if test="$noFill = false()">
				<xsl:attribute name="fill">white</xsl:attribute>
			</xsl:if>
		</xsl:when>
		
		<xsl:when test="$gradientOrLighting = 'Gradient'">
			<xsl:if test="$noFill = false()">
				<xsl:attribute name="fill">url(#<xsl:call-template name="getObjectGradientId"><xsl:with-param name="object" select="$object"/></xsl:call-template>)</xsl:attribute>
			</xsl:if>
		</xsl:when>

		<xsl:otherwise>
			<!-- Various lighting types and "Plain". (always need a fill) -->
			<xsl:choose>
				<xsl:when test="$noFill = false()">
					<xsl:attribute name="fill"><xsl:call-template name="getRGBFillColor"><xsl:with-param name="object" select="$object"/></xsl:call-template></xsl:attribute>
				</xsl:when>
				<xsl:otherwise>
					<xsl:attribute name="fill">none</xsl:attribute>
				</xsl:otherwise>
			</xsl:choose>
		
			<xsl:choose>
				<xsl:when test="$gradientOrLighting = 'None'">
					<!-- Just a solid fill -->
					
				</xsl:when>

				<xsl:when test="$gradientOrLighting = 'AllChrome' or $gradientOrLighting = 'DistantLighting'">
					<xsl:if test="local-name($object) = 'WorkflowProcess'">
						<!-- For process element only output lighting filter IF the lighting type is an overall diagram one. -->
						<xsl:attribute name="filter">url(#<xsl:value-of select="$gradientOrLighting"/>)</xsl:attribute>
					</xsl:if>
				</xsl:when>

				<xsl:otherwise>
					<xsl:if test="local-name($object) != 'WorkflowProcess'">
						<!-- Then finally there are the lighting effects filter that are specified on EVERY element. -->
						<xsl:choose>
							<xsl:when test="$gradientOrLighting = 'IndividualChrome' and $noShadow = 'true'">
								<!-- Don't use shadow on individually chrome lit pool / laneheader.-->
								<xsl:attribute name="filter">url(#IndividualChrome_NoShadow)</xsl:attribute>
							</xsl:when>
							<xsl:otherwise>
								<!-- For lighting effects placed on every object add the attribute to each one.-->
								<xsl:attribute name="filter">url(#<xsl:value-of select="$gradientOrLighting"/>)</xsl:attribute>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:if>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:otherwise>
	</xsl:choose>
	
</xsl:template>

<!--
==============================================
Output trhe various lighting types.
==============================================
-->
<xsl:template name="outputStandardLighting">

	<xsl:if test="$gradientOrLighting != 'Gradient' and $gradientOrLighting != 'BlackAndWhite'">
		<xsl:comment>=================================================
			Standard Object Lighting Setup...
			=================================================</xsl:comment>

		<!-- Standard lighting / Gradient definitions -->
		<xsl:choose>
			<xsl:when test="$gradientOrLighting = 'IndividualPoint'">
				<!-- point light on each individual object -->
				<filter id="IndividualPoint">
					<feGaussianBlur in="SourceAlpha" stdDeviation="4" result="blur1"/>
					<feSpecularLighting result="specOut" in="blur1" specularExponent="50" lighting-color="#bbbbbb">
						<fePointLight x="1" y ="1" z="35"/>
					</feSpecularLighting>
					<feComposite in="SourceGraphic" in2="specOut" operator="arithmetic" k1="0" k2="1" k3="1" k4="0"/>
				</filter>
			</xsl:when>
			
			<xsl:when test="$gradientOrLighting = 'DistantLighting'">
				<!-- Distant light -->
				<filter id="DistantLighting">
					<feGaussianBlur in="SourceAlpha" stdDeviation="8" result="blur1"/>
					<feSpecularLighting in="blur1" surfaceScale="2" specularConstant=".75"  specularExponent="35" lighting-color="#bbbbbb"  result="specOut">
						<feDistantLight azimuth="90" elevation="55"/>
					</feSpecularLighting>
					<feComposite in="SourceGraphic" in2="specOut" operator="arithmetic" k1="0" k2="1" k3="1" k4="0"/>
				</filter>
			</xsl:when>
			
			<xsl:when test="$gradientOrLighting = 'AllChrome'">
				<!-- Oooo fancy! -->
				<filter id="AllChrome" filterUnits="userSpaceOnUse" x="0" y="0">
					<feGaussianBlur in="SourceAlpha" stdDeviation="4" result="blur"/>
					<feOffset in="blur" dx="4" dy="4" result="offsetBlur"/>
					<feSpecularLighting in="blur" surfaceScale="5" specularConstant=".75"  specularExponent="20" lighting-color="#bbbbbb"  result="specOut">
						<fePointLight x="-5000" y="-10000" z="20000"/>
					</feSpecularLighting>
					<feComposite in="specOut" in2="SourceAlpha" operator="in" result="specOut"/>
					<feComposite in="SourceGraphic" in2="specOut" operator="arithmetic" k1="0" k2="1" k3="1" k4="0" result="litPaint"/>
					<feMerge>
						<feMergeNode in="offsetBlur"/>
						<feMergeNode in="litPaint"/>
					</feMerge>
				</filter>
			</xsl:when>
			
			<xsl:when test="$gradientOrLighting = 'IndividualChrome' or $gradientOrLighting = 'IndividualChrome_NoShadow'">
				<!-- Oooo Double fancy! -->
				<filter id="IndividualChrome" filterUnits="userSpaceOnUse" x="0" y="0">
					<feGaussianBlur in="SourceAlpha" stdDeviation="6" result="blur"/>
					<feOffset in="blur" dx="4" dy="4" result="offsetBlur"/>
					<feSpecularLighting in="blur" surfaceScale="5" specularConstant=".75"  specularExponent="20" lighting-color="#bbbbbb"  result="specOut">
						<fePointLight x="-5000" y="-10000" z="20000"/>
					</feSpecularLighting>
					<feComposite in="specOut" in2="SourceAlpha" operator="in" result="specOut"/>
					<feComposite in="SourceGraphic" in2="specOut" operator="arithmetic" k1="0" k2="1" k3="1" k4="0" result="litPaint"/>
					<feMerge>
						<feMergeNode in="offsetBlur"/>
						<feMergeNode in="litPaint"/>
					</feMerge> 
				</filter>
				
				<!-- Internal use only (for individual chromed objects that don't have shadown like lane/pool headers. -->
				<filter id="IndividualChrome_NoShadow" filterUnits="userSpaceOnUse" x="0" y="0">
					<feGaussianBlur in="SourceAlpha" stdDeviation="6" result="blur"/>
					<feSpecularLighting in="blur" surfaceScale="5" specularConstant=".75"  specularExponent="20" lighting-color="#bbbbbb"  result="specOut">
						<fePointLight x="-5000" y="-10000" z="20000"/>
					</feSpecularLighting>
					<feComposite in="specOut" in2="SourceAlpha" operator="in" result="specOut"/>
					<feComposite in="SourceGraphic" in2="specOut" operator="arithmetic" k1="0" k2="1" k3="1" k4="0" result="litPaint"/>
				</filter>
			</xsl:when>
			
			<xsl:otherwise>
				<xsl:comment>No extra lighting related setup required.</xsl:comment>
			</xsl:otherwise>
		</xsl:choose>


	</xsl:if>
	
</xsl:template>

<!--
==============================================
If in gradient fill mode output the linerarGradient section for the standard activity colour object. 
==============================================
-->
<xsl:template name="outputStandardObjectGradient">
	<xsl:if test="$gradientOrLighting = 'Gradient'">
		<xsl:variable name="color">rgb(<xsl:value-of select="$standardActivityFillColor"/>)</xsl:variable>
		
		<xsl:comment>=================================================
			Standard Reusable Color Gradient...
			(Used when the standard Activity color (param=StandardActivityFillColor) is defined 
			for  an activity (to save specifying smae thing over and over).
			=================================================</xsl:comment>

		<linearGradient id = "__Gradient_StandardActivity__" x1 = "0%" y1 = "0%" x2 = "100%" y2 = "100%">
			<stop stop-color="{$color}" offset = "0%"/>
			<stop stop-color = "white" offset = "100%"/>
		</linearGradient>

		<xsl:comment>=================================================</xsl:comment>

	</xsl:if>
</xsl:template>

<!--
==============================================
Output the id of the linearGradient for the given object.
This will either be the object's very own one or a bos-standard one if it's
a bos-standard formatted object.
==============================================
-->
<xsl:template name="getObjectGradientId">
	<xsl:param name="object"/>
	
	<xsl:variable name="nominal">Gradient_<xsl:value-of select="$object/@Id"/></xsl:variable>

	<xsl:choose>
		<xsl:when test="local-name($object) = 'Activity' or (local-name($object) = 'Artifact' and $object/xpdl2:DataObject)">
			<!-- If this is the box-standard activity fill color then we can use the box-standard linear gradient element (saves repeating ourselves over and over -->
			<xsl:variable name="rgbColor"><xsl:call-template name="getRGBFillColor"><xsl:with-param name="object" select="$object"/></xsl:call-template></xsl:variable>
			<xsl:variable name="stdRgbColor">rgb(<xsl:value-of select="$standardActivityFillColor"/>)</xsl:variable>
			
			<xsl:choose>
				<xsl:when test="$rgbColor = $stdRgbColor">
					<xsl:text>__Gradient_StandardActivity__</xsl:text>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="$nominal"/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:when>
		<xsl:otherwise>
			<xsl:value-of select="$nominal"/>
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>


<!--
==============================================
Output standard reusable shapes
==============================================
-->
<xsl:template name="outputStandardShapes">
	<xsl:comment>=================================================
		Standard Reusable BPMN shapes...
		=================================================</xsl:comment>
	
	<polygon id="Std_Gateway" class="Gateway">
		<xsl:attribute name="points">
			<xsl:text>0,-</xsl:text><xsl:value-of select="$fixedGatewayHeight div 2"/><xsl:text> </xsl:text>
			<xsl:value-of select="$fixedGatewayWidth div 2"/><xsl:text>,0 </xsl:text>
			<xsl:text>0,</xsl:text><xsl:value-of select="$fixedGatewayHeight div 2"/><xsl:text> </xsl:text>
			<xsl:text>-</xsl:text><xsl:value-of select="$fixedGatewayWidth div 2"/><xsl:text>,0</xsl:text>
		</xsl:attribute>
	</polygon>
	
	<circle id="Std_StartEvent" r="{$fixedEventSize div 2}" class="StartEvent"/>

	<circle id="Std_IntermediateEvent" r="{$fixedEventSize div 2}" class="IntermediateEvent"/>

	<circle id="Std_InnerIntermediateEvent" r="{($fixedEventSize div 2) - ($fixedEventSize div 9)}" class="IntermediateEvent"/>

	<circle id="Std_EndEvent" r="{$fixedEventSize div 2}" class="EndEvent"/>

	<polygon id="Std_RightArrow" class="ConnectionDecorator">
		<xsl:attribute name="points">
			<xsl:text>0,0 </xsl:text>
			<xsl:text>-</xsl:text><xsl:value-of select="$connectionDecoratorSize"/><xsl:text>,</xsl:text><xsl:value-of select="$connectionDecoratorSize div 2"/><xsl:text> </xsl:text>
			<xsl:text>-</xsl:text><xsl:value-of select="$connectionDecoratorSize"/><xsl:text>,-</xsl:text><xsl:value-of select="$connectionDecoratorSize div 2"/><xsl:text> </xsl:text>
		</xsl:attribute>
	</polygon>

	<polygon id="Std_LeftArrow" class="ConnectionDecorator">
		<xsl:attribute name="points">
			<xsl:text>0,0 </xsl:text>
			<xsl:value-of select="$connectionDecoratorSize"/><xsl:text>,-</xsl:text><xsl:value-of select="$connectionDecoratorSize div 2"/><xsl:text> </xsl:text>
			<xsl:value-of select="$connectionDecoratorSize"/><xsl:text>,</xsl:text><xsl:value-of select="$connectionDecoratorSize div 2"/><xsl:text> </xsl:text>
		</xsl:attribute>
	</polygon>

	<polygon id="Std_DownArrow" class="ConnectionDecorator">
		<xsl:attribute name="points">
			<xsl:text>0,0 </xsl:text>
			<xsl:text>-</xsl:text><xsl:value-of select="$connectionDecoratorSize div 2"/><xsl:text>,-</xsl:text><xsl:value-of select="$connectionDecoratorSize"/><xsl:text> </xsl:text>
			<xsl:value-of select="$connectionDecoratorSize div 2"/><xsl:text>,-</xsl:text><xsl:value-of select="$connectionDecoratorSize"/><xsl:text> </xsl:text>
		</xsl:attribute>
	</polygon>
				 
	<polygon id="Std_UpArrow" class="ConnectionDecorator">
		<xsl:attribute name="points">
			<xsl:text>0,0 </xsl:text>
			<xsl:value-of select="$connectionDecoratorSize div 2"/><xsl:text>,</xsl:text><xsl:value-of select="$connectionDecoratorSize"/><xsl:text> </xsl:text>
			<xsl:text>-</xsl:text><xsl:value-of select="$connectionDecoratorSize div 2"/><xsl:text>,</xsl:text><xsl:value-of select="$connectionDecoratorSize"/><xsl:text> </xsl:text>
		</xsl:attribute>
	</polygon>

	<polygon id="Std_RightDiamond" class="ConnectionDecorator">
		<xsl:attribute name="points">
			<xsl:text>0,0 </xsl:text>
			<xsl:value-of select="$connectionDecoratorSize"/><xsl:text>,-</xsl:text><xsl:value-of select="$connectionDecoratorSize div 2"/><xsl:text> </xsl:text>
			<xsl:value-of select="$connectionDecoratorSize * 2"/><xsl:text>,0 </xsl:text>
			<xsl:value-of select="$connectionDecoratorSize"/><xsl:text>,</xsl:text><xsl:value-of select="$connectionDecoratorSize div 2"/><xsl:text> </xsl:text>
		</xsl:attribute>
	</polygon>
	
	<polygon id="Std_LeftDiamond" class="ConnectionDecorator">
		<xsl:attribute name="points">
			<xsl:text>0,0 </xsl:text>
			<xsl:text>-</xsl:text><xsl:value-of select="$connectionDecoratorSize"/><xsl:text>,-</xsl:text><xsl:value-of select="$connectionDecoratorSize div 2"/><xsl:text> </xsl:text>
			<xsl:text>-</xsl:text><xsl:value-of select="$connectionDecoratorSize * 2"/><xsl:text>,0 </xsl:text>
			<xsl:text>-</xsl:text><xsl:value-of select="$connectionDecoratorSize"/><xsl:text>,</xsl:text><xsl:value-of select="$connectionDecoratorSize div 2"/><xsl:text> </xsl:text>
		</xsl:attribute>
	</polygon>
	
	<polygon id="Std_DownDiamond" class="ConnectionDecorator">
		<xsl:attribute name="points">
			<xsl:text>0,0 </xsl:text>
			<xsl:text>-</xsl:text><xsl:value-of select="$connectionDecoratorSize div 2"/><xsl:text>,</xsl:text><xsl:value-of select="$connectionDecoratorSize"/><xsl:text> </xsl:text>
			<xsl:text>0,</xsl:text><xsl:value-of select="$connectionDecoratorSize * 2"/><xsl:text> </xsl:text>
			<xsl:value-of select="$connectionDecoratorSize div 2"/><xsl:text>,</xsl:text><xsl:value-of select="$connectionDecoratorSize"/><xsl:text> </xsl:text>
		</xsl:attribute>
	</polygon>
	
	<polygon id="Std_UpDiamond" class="ConnectionDecorator">
		<xsl:attribute name="points">
			<xsl:text>0,0 </xsl:text>
			<xsl:text>-</xsl:text><xsl:value-of select="$connectionDecoratorSize div 2"/><xsl:text>,-</xsl:text><xsl:value-of select="$connectionDecoratorSize"/><xsl:text> </xsl:text>
			<xsl:text>0,-</xsl:text><xsl:value-of select="$connectionDecoratorSize * 2"/><xsl:text> </xsl:text>
			<xsl:value-of select="$connectionDecoratorSize div 2"/><xsl:text>,-</xsl:text><xsl:value-of select="$connectionDecoratorSize"/><xsl:text> </xsl:text>
		</xsl:attribute>
	</polygon>

	<xsl:variable name="slashWidth" select="$connectionDecoratorSize * 1.2"/>
	<xsl:variable name="slashHeight" select="$connectionDecoratorSize * 2"/>
	<xsl:variable name="slashMargin" select="$connectionDecoratorSize div 2"/>
	
	<polygon id="Std_RightSlash" class="ConnectionDecorator">
		<xsl:attribute name="points">
			<xsl:value-of select="$slashMargin"/><xsl:text>,</xsl:text><xsl:value-of select="$slashHeight div 2"/><xsl:text> </xsl:text>
			<xsl:value-of select="$slashMargin + $slashWidth"/><xsl:text>,-</xsl:text><xsl:value-of select="$slashHeight div 2"/><xsl:text> </xsl:text>
		</xsl:attribute>
	</polygon>
	
	<polygon id="Std_LeftSlash" class="ConnectionDecorator">
		<xsl:attribute name="points">
			<xsl:text>-</xsl:text><xsl:value-of select="$slashMargin + $slashWidth"/><xsl:text>,</xsl:text><xsl:value-of select="$slashHeight div 2"/><xsl:text> </xsl:text>
			<xsl:text>-</xsl:text><xsl:value-of select="$slashMargin"/><xsl:text>,-</xsl:text><xsl:value-of select="$slashHeight div 2"/><xsl:text> </xsl:text>
		</xsl:attribute>
	</polygon>

	<polygon id="Std_DownSlash" class="ConnectionDecorator">
		<xsl:attribute name="points">
			<xsl:text>-</xsl:text><xsl:value-of select="$slashWidth div 2"/><xsl:text>,</xsl:text><xsl:value-of select="$slashMargin + $slashHeight"/><xsl:text> </xsl:text>
			<xsl:value-of select="$slashWidth div 2"/><xsl:text>,</xsl:text><xsl:value-of select="$slashMargin"/><xsl:text> </xsl:text>
		</xsl:attribute>
	</polygon>

	<polygon id="Std_UpSlash" class="ConnectionDecorator">
		<xsl:attribute name="points">
			<xsl:text>-</xsl:text><xsl:value-of select="$slashWidth div 2"/><xsl:text>,-</xsl:text><xsl:value-of select="$slashMargin"/><xsl:text> </xsl:text>
			<xsl:value-of select="$slashWidth div 2"/><xsl:text>,-</xsl:text><xsl:value-of select="$slashMargin + $slashHeight"/><xsl:text> </xsl:text>
		</xsl:attribute>
	</polygon>

	<image id="Img_CatchMessage" xlink:href="{$iconsFolder}/eventCatchMessage.png" width="16" height="16"/>

	<image id="Img_SendTask" xlink:href="{$iconsFolder}/taskSend.png" width="32" height="32"/>


</xsl:template>

<!--
====================================================
Add the stroke color attribute.
====================================================
-->
<xsl:template name="addStrokeColorAttribute">
	<xsl:param name="object"/>
	
	<xsl:choose>
		<xsl:when test="$gradientOrLighting = 'BlackAndWhite'">
			<xsl:attribute name="stroke">black</xsl:attribute>
		</xsl:when>
		<xsl:when test="local-name($object) = 'Transition' or local-name($object) = 'MessageFlow' or local-name($object) = 'Association'">
			<xsl:attribute name="stroke"><xsl:call-template name="getRGBConnectionColor"><xsl:with-param name="object" select="$object"/></xsl:call-template></xsl:attribute>
		</xsl:when>
		<xsl:otherwise>
			<xsl:attribute name="stroke"><xsl:call-template name="getRGBBorderColor"><xsl:with-param name="object" select="$object"/></xsl:call-template></xsl:attribute>
		</xsl:otherwise>
	</xsl:choose>
	
</xsl:template>

<!--
====================================================
add a text color attribute to the current output element
====================================================
-->
<xsl:template name="addTextColorAttribute">
	<xsl:param name="object"/>
	<xsl:choose>
		<xsl:when test="$gradientOrLighting = 'BlackAndWhite'">
			<xsl:attribute name="stroke">black</xsl:attribute>
		</xsl:when>
		<xsl:otherwise>
			<xsl:attribute name="stroke"><xsl:call-template name="getRGBTextColor"><xsl:with-param name="object" select="$object"/></xsl:call-template></xsl:attribute>
		</xsl:otherwise>
	</xsl:choose>
	
</xsl:template>

<!--
====================================================
add activity icon.
====================================================
-->
<xsl:template name="addActivityIcon">
	<xsl:param name="object"/>
	<xsl:param name="CX"/>
	<xsl:param name="CY"/>

	<xsl:variable name="size">
		<xsl:choose>
			<xsl:when test="$object/xpdl2:Event">16</xsl:when>
			<xsl:when test="$object/xpdl2:Route">16</xsl:when>
			<xsl:otherwise>32</xsl:otherwise>
		</xsl:choose>
	</xsl:variable>
		
	<xsl:variable name="img">
		<xsl:choose>
			<!-- Events -->
			<xsl:when test="$object/xpdl2:Event//xpdl2:TriggerResultMessage">
				<xsl:choose>
					<xsl:when test="$object/xpdl2:Event//xpdl2:TriggerResultMessage/@CatchThrow = 'THROW'">
						<xsl:text>Std_ThrowMessage</xsl:text>
					</xsl:when>
					<xsl:otherwise>
						<xsl:text>Img_CatchMessage</xsl:text>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:when>

			<!-- Tasks -->
			<xsl:when test="$object/xpdl2:Implementation/xpdl2:Task/xpdl2:TaskSend">
				<xsl:text>Img_SendTask</xsl:text>
			</xsl:when>
			
		</xsl:choose>
	</xsl:variable>

	
	<xsl:if test="$img != ''">
		<!-- Assumes that event icon images are 16x16 pixels. -->
		<use id="Icon_{$object/@Id}" xlink:href="#{$img}" x="{$CX - ($size div 2)}" y="{$CY - ($size div 2)}"/>
	</xsl:if>

</xsl:template>

<!--
====================================================
Add object javascript attributes.
====================================================
-->
<xsl:template name="addObjectScripts">
	<xsl:param name="object"/>

	<xsl:if test="$onmouseover != ''">
		<xsl:attribute name="onmouseover"><xsl:value-of select="$onmouseover"/></xsl:attribute>
	</xsl:if>

	<xsl:if test="$onmouseout != ''">
		<xsl:attribute name="onmouseout"><xsl:value-of select="$onmouseout"/></xsl:attribute>
	</xsl:if>

	<xsl:if test="$onclick != ''">
		<xsl:attribute name="onclick"><xsl:value-of select="$onclick"/></xsl:attribute>
	</xsl:if>

	<xsl:if test="$onmousedown != ''">
		<xsl:attribute name="onmousedown"><xsl:value-of select="$onmousedown"/></xsl:attribute>
	</xsl:if>
	
	<xsl:if test="$onmouseup != ''">
		<xsl:attribute name="onmouseup"><xsl:value-of select="$onmouseup"/></xsl:attribute>
	</xsl:if>

	<xsl:if test="$onkeypress != ''">
		<xsl:attribute name="onkeypress"><xsl:value-of select="$onkeypress"/></xsl:attribute>
	</xsl:if>

	<xsl:if test="$onkeydown != ''">
		<xsl:attribute name="onkeydown"><xsl:value-of select="$onkeydown"/></xsl:attribute>
	</xsl:if>
	
	<xsl:if test="$onkeyup != ''">
		<xsl:attribute name="onkeyup"><xsl:value-of select="$onkeyup"/></xsl:attribute>
	</xsl:if>

</xsl:template>



<!--
====================================================
Get the given object's fill color.
====================================================
-->
<xsl:template name="getRGBFillColor">
	<xsl:param name="object"/>

	<xsl:variable name="toolId"><xsl:value-of select="$graphicsInfoToolId"/></xsl:variable>
	<xsl:variable name="gi" select="$object/xpdl2:NodeGraphicsInfos/xpdl2:NodeGraphicsInfo[not(@ToolId) or @ToolId = '' or @ToolId = $toolId]"/>
	<xsl:variable name="color" select="$gi/@FillColor"/>
	
	<xsl:choose>
		<xsl:when test="$color != ''">
			<xsl:text>rgb(</xsl:text><xsl:value-of select="$color"/><xsl:text>)</xsl:text>
		</xsl:when>
		<xsl:when test="count($object/ancestor::xpdl2:WorkflowProcess) = 1">
			<!-- Things inside processes are activities -->
			<xsl:text>rgb(</xsl:text><xsl:value-of select="$standardActivityFillColor"/><xsl:text>)</xsl:text>
		</xsl:when>
		<xsl:when test="count($object/ancestor::xpdl2:Artifacts) = 1 or count($object/ancestor::xpdl2:Pools) = 1">
			<!-- Artifacts, pools/lanes-->
			<xsl:text>rgb(</xsl:text><xsl:value-of select="$standardArtifactFillColor"/><xsl:text>)</xsl:text>
		</xsl:when>
		<xsl:otherwise>
			<xsl:text>rgb(200,200,200)</xsl:text>
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<!--
====================================================
Get the given object's border color.
====================================================
-->
<xsl:template name="getRGBBorderColor">
	<xsl:param name="object"/>

	<xsl:variable name="toolId"><xsl:value-of select="$graphicsInfoToolId"/></xsl:variable>
	<xsl:variable name="gi" select="$object/xpdl2:NodeGraphicsInfos/xpdl2:NodeGraphicsInfo[not(@ToolId) or @ToolId = '' or @ToolId = $toolId]"/>
	<xsl:variable name="color" select="$gi/@BorderColor"/>
	
	<xsl:choose>
		<xsl:when test="$color != ''">
			<xsl:text>rgb(</xsl:text><xsl:value-of select="$color"/><xsl:text>)</xsl:text>
		</xsl:when>
		<xsl:when test="count($object/ancestor::xpdl2:WorkflowProcess) = 1">
			<!-- Things inside processes are activities -->
			<xsl:text>rgb(</xsl:text><xsl:value-of select="$standardActivityBorderColor"/><xsl:text>)</xsl:text>
		</xsl:when>
		<xsl:when test="count($object/ancestor::xpdl2:Artifacts) = 1 or count($object/ancestor::xpdl2:Pools) = 1">
			<!-- Artifacts, pools/lanes-->
			<xsl:text>rgb(</xsl:text><xsl:value-of select="$standardArtifactBorderColor"/><xsl:text>)</xsl:text>
		</xsl:when>
		<xsl:otherwise>
			<xsl:text>rgb(200,200,200)</xsl:text>
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<!--
====================================================
Get the given object's border color.
====================================================
-->
<xsl:template name="getRGBConnectionColor">
	<xsl:param name="object"/>

	<xsl:variable name="toolId"><xsl:value-of select="$connectorGraphicsInfoToolId"/></xsl:variable>
	<xsl:variable name="gi" select="$object/xpdl2:ConnectorGraphicsInfos/xpdl2:ConnectorGraphicsInfo[not(@ToolId) or @ToolId = '' or @ToolId = $toolId]"/>
	<xsl:variable name="color" select="$gi/@BorderColor"/>
	
	<xsl:choose>
		<xsl:when test="$color != ''">
			<xsl:text>rgb(</xsl:text><xsl:value-of select="$color"/><xsl:text>)</xsl:text>
		</xsl:when>
		<xsl:otherwise>
			<xsl:text>rgb(</xsl:text><xsl:value-of select="$standardConnectionColor"/><xsl:text>)</xsl:text>
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<!--
====================================================
Get the given object's text color.
====================================================
-->
<xsl:template name="getRGBTextColor">
	<xsl:param name="object"/>

	<xsl:variable name="toolId"><xsl:value-of select="$graphicsInfoToolId"/></xsl:variable>
	<xsl:variable name="gi" select="$object/xpdl2:NodeGraphicsInfos/xpdl2:NodeGraphicsInfo[not(@ToolId) or @ToolId = '' or @ToolId = $toolId]"/>
	<xsl:variable name="color" select="$gi/@BorderColor"/>
	
	<xsl:choose>
		<xsl:when test="$color != ''">
			<xsl:text>rgb(</xsl:text><xsl:value-of select="$color"/><xsl:text>)</xsl:text>
		</xsl:when>
		<xsl:when test="count($object/ancestor::xpdl2:WorkflowProcess) = 1">
			<!-- Things inside processes are activities -->
			<xsl:text>rgb(</xsl:text><xsl:value-of select="$standardActivityTextColor"/><xsl:text>)</xsl:text>
		</xsl:when>
		<xsl:when test="count($object/ancestor::xpdl2:Artifacts) = 1 or count($object/ancestor::xpdl2:Pools) = 1">
			<!-- Artifacts, pools/lanes-->
			<xsl:text>rgb(</xsl:text><xsl:value-of select="$standardArtifactTextColor"/><xsl:text>)</xsl:text>
		</xsl:when>
		<xsl:otherwise>
			<xsl:text>rgb(200,200,200)</xsl:text>
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<!--
====================================================
Output the styles...
====================================================
-->
<xsl:template name="outputStyles">
	<xsl:comment>=================================================
		Standard Object Styles...
			Changing these will override the value defined in parameters standardXXXXColor
			AND those given in the XPDL for given classes of objects...
		=================================================</xsl:comment>
	<xsl:choose>
		<xsl:when test="$cssFile = ''">
			<!-- NOTE: PLEASE KEEP THIS IN SYNC WITH xpdl2svg.css -->

			<style type="text/css">
				<xsl:text>
					.Pool
					{
					}
					
					.PoolHeader
					{
					}
					
					.Lane 
					{
					}
					
					.LaneHeader
					{
					}
					
					.StartEvent
					{
					}
					
					.IntermediateEvent
					{
					}
					
					.EndEvent
					{
					}
					
					.Gateway
					{
					}

					.Task
					{
					}
					
					.DataObject
					{
					}
					
					.Annotation
					{
					}

					.SequenceFlow
					{
					}
					
					text
					{
						font-style:	normal;
						font-family:	tahoma;
						font-size:	</xsl:text><xsl:value-of select="$finalFontPtHeight"/><xsl:text>pt;
					}
				</xsl:text>
			</style>
		</xsl:when>
	</xsl:choose>

</xsl:template>


<!--
====================================================
Output the styles...
====================================================
-->
<xsl:template name="outputScripts">

	<xsl:if test="$javaScriptFile != ''">
		<xsl:comment>=================================================
			Scripting...
			=================================================</xsl:comment>
		<script type="text/ecmascript" xlink:href="{$javaScriptFile}"/>
		
	</xsl:if>
	
</xsl:template>


<xsl:template match="@* | *">
	<xsl:apply-templates select="*"/>
</xsl:template>


</xsl:stylesheet>

