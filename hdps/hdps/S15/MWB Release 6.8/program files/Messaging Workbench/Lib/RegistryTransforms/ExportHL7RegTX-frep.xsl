<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:strip-space elements="*"/>
	<!--	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes" cdata-section-elements="ImpNote Reference Predicate Actor PreCondition PostCondition DerivativeEvent EventFlow"/>-->

<!-- 10/27/5 This is the original version of the Export XSL for SpecXML that permits field repetitions principally identified by fields with name suffix ending in '_rep'. This now an alternate XSL. -->
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	<!-- This version converts ImpNotes, References and predicates into CDATA sections. This allows them to contain markup -->
	<!--          Main Transform - Copy all but selected attribute nodes -->
	<xsl:template match="/ | @* | node()">
		<xsl:copy>
			<!-- set document encoding UTF-8 -->
			<xsl:apply-templates select="output/@encoding"/>
			<!-- <xsl:apply-templates select="Paragraph[contains(.,'&#160;')]"/> -->
			<xsl:apply-templates select="@Optionality"/>
			<xsl:apply-templates select="@*[not(name()='Repeatable')][not(name()='Sequence')][not(name()='Structure')][not(name()='Optionality')][not(name()='Predicate')][not(name()='Reference')][not(name()='Description')][not(name()='ProcRule')][not(name()='QueryStatus')][not(name()='QueryMode')]  | node()[not(name()='DataValues')][not(name()='SegGroup')][not(name()='Segment')][not(name()='Field')][not(name()='Component')][not(name()='SubComponent')]"/>
			<!-- Need this Element order to satisfy Normative DTD -->
			<xsl:apply-templates select="@Reference"/>
			<xsl:apply-templates select="@Predicate"/>
			<!-- Need this Element order to satisfy Normative DTD -->
			<xsl:apply-templates select="Message | DataValues | SegGroup | Segment | Field | Component | SubComponent"/>
			<xsl:apply-templates select="HL7Definition | HL7Definition/Paragraph"/>
		</xsl:copy>
	</xsl:template>
	<!-- +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ -->
	<!-- Make sure encoding is UTF-8 Really has no effect here, since TransformNode still returns an XML file with encoding="UTF-16"  -->
	<xsl:template match="output/@encoding">
		<xsl:attribute name="encoding">UTF-8</xsl:attribute>
	</xsl:template>
	<!--	
	<xsl:template match="Specification">
	  <xsl:if test=".!=''">
		<xsl:element name="HL7v2xConformanceProfile">
			<xsl:value-of select="."/>
		</xsl:element>
	  </xsl:if>
	</xsl:template>
-->
	<xsl:template match="Specification">
		<xsl:element name="HL7v2xConformanceProfile">
			<xsl:attribute name="HL7Version">
				<xsl:value-of select="@HL7Version"/>
			</xsl:attribute>
			<xsl:apply-templates select="@ConformanceType"/>
			<xsl:if test="@OID!=''">
				<xsl:attribute name="Identifier">
					<xsl:value-of select="@OID"/>
				</xsl:attribute>
			</xsl:if>
			<xsl:element name="MetaData">
				<xsl:attribute name="Name">
					<xsl:value-of select="@SpecName"/>
				</xsl:attribute>
				<xsl:attribute name="OrgName">
					<xsl:value-of select="@OrgName"/>
				</xsl:attribute>
				<xsl:attribute name="Version">
					<xsl:value-of select="@SpecVersion"/>
				</xsl:attribute>
				<xsl:attribute name="Status">
					<xsl:value-of select="@Status"/>
				</xsl:attribute>
				<xsl:attribute name="Topics">
					<xsl:text>confsig-</xsl:text>
					<xsl:value-of select="/Specification/@OrgName"/>
					<xsl:text>-</xsl:text>
					<xsl:value-of select="@HL7Version"/>
					<xsl:text>-profile-acc</xsl:text>
					<xsl:value-of select="Conformance/@AccAck"/>
					<xsl:text>_acc</xsl:text>
					<xsl:value-of select="Conformance/@AppAck"/>
					<xsl:text>-</xsl:text>
					<xsl:value-of select="Conformance/@MsgAckMode"/>
				</xsl:attribute>
			</xsl:element>
			<xsl:apply-templates select="ImpNote"/>
			<xsl:apply-templates select="UseCase"/>
			<xsl:apply-templates select="Encodings"/>
			<xsl:apply-templates select="Conformance"/>
			<xsl:apply-templates select="Message"/>
		</xsl:element>
	</xsl:template>
	<xsl:template match="Message">
		<xsl:element name="HL7v2xStaticDef">
			<xsl:attribute name="MsgType">
				<xsl:value-of select="@MsgType"/>
			</xsl:attribute>
			<xsl:attribute name="EventType">
				<xsl:value-of select="@EventType"/>
			</xsl:attribute>
			<xsl:attribute name="MsgStructID">
				<xsl:value-of select="@MsgStructID"/>
			</xsl:attribute>
			<xsl:if test="@OrderControl!=''">
				<xsl:attribute name="OrderControl">
					<xsl:value-of select="@OrderControl"/>
				</xsl:attribute>
			</xsl:if>
			<xsl:attribute name="EventDesc">
				<xsl:value-of select="@EventDesc"/>
			</xsl:attribute>
			<xsl:if test="/Specification/@HL7OID!=''">
				<xsl:attribute name="Identifier">
					<xsl:value-of select="/Specification/@HL7OID"/>
				</xsl:attribute>
			</xsl:if>
			<xsl:attribute name="Role">
				<xsl:value-of select="/Specification/@Role"/>
			</xsl:attribute>
			<xsl:element name="MetaData">
				<xsl:attribute name="Name">
					<xsl:value-of select="/Specification/@SpecName"/>
				</xsl:attribute>
				<xsl:attribute name="OrgName">
					<xsl:value-of select="/Specification/@OrgName"/>
				</xsl:attribute>
				<xsl:attribute name="Version">
					<xsl:value-of select="/Specification/@SpecVersion"/>
				</xsl:attribute>
				<xsl:attribute name="Status">
					<xsl:value-of select="/Specification/@Status"/>
				</xsl:attribute>
				<xsl:attribute name="Topics">
					<xsl:text>confsig-</xsl:text>
					<xsl:value-of select="/Specification/@OrgName"/>
					<xsl:text>-</xsl:text>
					<xsl:value-of select="/Specification/@HL7Version"/>
					<xsl:text>-static-</xsl:text>
					<xsl:value-of select="@MsgType"/>
					<xsl:text>-</xsl:text>
					<xsl:value-of select="@EventType"/>
					<xsl:text>-</xsl:text>
					<xsl:choose>
						<xsl:when test="@OrderControl=''">
							<xsl:value-of select="@OrderControl"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:text>null</xsl:text>
						</xsl:otherwise>
					</xsl:choose>
					<xsl:text>-</xsl:text>
					<xsl:value-of select="@MsgStructID"/>
					<xsl:text>-</xsl:text>
					<xsl:value-of select="/Specification/@SpecVersion"/>
					<xsl:text>-</xsl:text>
					<xsl:value-of select="/Specification/@Status"/>
					<xsl:text>-</xsl:text>
					<xsl:value-of select="/Specification/@Role"/>
				</xsl:attribute>
			</xsl:element>
			<xsl:apply-templates select="SegGroup | Segment"/>
		</xsl:element>
	</xsl:template>
	<xsl:template match="SegGroup">
		<xsl:element name="SegGroup">
			<xsl:attribute name="Name">
				<xsl:value-of select="@Name"/>
			</xsl:attribute>
			<xsl:attribute name="LongName">
				<xsl:value-of select="@Description"/>
			</xsl:attribute>
			<xsl:apply-templates select="@Optionality"/>
			<xsl:attribute name="Min">
				<xsl:value-of select="@Min"/>
			</xsl:attribute>
			<xsl:attribute name="Max">
				<xsl:choose>
					<xsl:when test="@Max='0'">*</xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="@Max"/>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:attribute>
			<xsl:apply-templates select="Field"/>
			<xsl:apply-templates select="ImpNote"/>
			<xsl:apply-templates select="@Reference"/>
			<xsl:apply-templates select="@Predicate"/>
			<xsl:apply-templates select="SegGroup | Segment"/>
		</xsl:element>
	</xsl:template>
	<xsl:template match="Segment">
		<xsl:element name="Segment">
			<xsl:attribute name="Name">
				<xsl:value-of select="@Name"/>
			</xsl:attribute>
			<xsl:attribute name="LongName">
				<xsl:value-of select="@Description"/>
			</xsl:attribute>
			<xsl:apply-templates select="@Optionality"/>
			<xsl:attribute name="Min">
				<xsl:value-of select="@Min"/>
			</xsl:attribute>
			<xsl:attribute name="Max">
				<xsl:choose>
					<xsl:when test="@Max='0'">*</xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="@Max"/>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:attribute>
			<xsl:apply-templates select="ImpNote"/>
			<xsl:apply-templates select="@Reference"/>
			<xsl:apply-templates select="@Predicate"/>
			<xsl:apply-templates select="Field"/>
		</xsl:element>
	</xsl:template>
	<xsl:template match="Field">
		<xsl:element name="Field">
			<xsl:attribute name="Name">
				<xsl:value-of select="@Name"/>
			</xsl:attribute>
			<xsl:apply-templates select="@Optionality"/>
			<xsl:attribute name="Min">
				<xsl:value-of select="@Min"/>
			</xsl:attribute>
			<xsl:attribute name="Max">
				<xsl:choose>
					<xsl:when test="@Max='0'">*</xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="@Max"/>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:attribute>
			<xsl:attribute name="Datatype">
				<xsl:value-of select="@Datatype"/>
			</xsl:attribute>
			<xsl:if test="@Length!='' and @Length!='0'">
				<xsl:attribute name="Length">
					<xsl:value-of select="@Length"/>
				</xsl:attribute>
			</xsl:if>
			<xsl:if test="@Table!=''">
				<xsl:attribute name="Table">
					<xsl:value-of select="@Table"/>
				</xsl:attribute>
			</xsl:if>
			<xsl:if test="@ConstantValue!=''">
				<xsl:attribute name="ConstantValue">
					<xsl:value-of select="@ConstantValue"/>
				</xsl:attribute>
			</xsl:if>
			<xsl:if test="@ItemNo!=''">
				<xsl:attribute name="ItemNo">
					<xsl:value-of select="@ItemNo"/>
				</xsl:attribute>
			</xsl:if>
			<xsl:apply-templates select="ImpNote"/>
			<xsl:apply-templates select="@Reference"/>
			<xsl:apply-templates select="@Predicate"/>
			<xsl:apply-templates select="DataValues"/>
			<xsl:apply-templates select="Component"/>
		</xsl:element>
	</xsl:template>
	<xsl:template match="Component">
		<xsl:element name="Component">
			<xsl:attribute name="Name">
				<xsl:value-of select="@Name"/>
			</xsl:attribute>
			<xsl:apply-templates select="@Optionality"/>
			<xsl:attribute name="Datatype">
				<xsl:value-of select="@Datatype"/>
			</xsl:attribute>
			<xsl:if test="@Length!='' and @Length!='0'">
				<xsl:attribute name="Length">
					<xsl:value-of select="@Length"/>
				</xsl:attribute>
			</xsl:if>
			<xsl:if test="@Table!=''">
				<xsl:attribute name="Table">
					<xsl:value-of select="@Table"/>
				</xsl:attribute>
			</xsl:if>
			<xsl:if test="@ConstantValue!=''">
				<xsl:attribute name="ConstantValue">
					<xsl:value-of select="@ConstantValue"/>
				</xsl:attribute>
			</xsl:if>
			<xsl:apply-templates select="ImpNote"/>
			<xsl:apply-templates select="@Reference"/>
			<xsl:apply-templates select="@Predicate"/>
			<xsl:apply-templates select="DataValues"/>
			<xsl:apply-templates select="SubComponent"/>
		</xsl:element>
	</xsl:template>
	<xsl:template match="SubComponent">
		<xsl:element name="SubComponent">
			<xsl:attribute name="Name">
				<xsl:value-of select="@Name"/>
			</xsl:attribute>
			<xsl:apply-templates select="@Optionality"/>
			<xsl:attribute name="Datatype">
				<xsl:value-of select="@Datatype"/>
			</xsl:attribute>
			<xsl:if test="@Length!='' and @Length!='0'">
				<xsl:attribute name="Length">
					<xsl:value-of select="@Length"/>
				</xsl:attribute>
			</xsl:if>
			<xsl:if test="@Table!=''">
				<xsl:attribute name="Table">
					<xsl:value-of select="@Table"/>
				</xsl:attribute>
			</xsl:if>
			<xsl:if test="@ConstantValue!=''">
				<xsl:attribute name="ConstantValue">
					<xsl:value-of select="@ConstantValue"/>
				</xsl:attribute>
			</xsl:if>
			<xsl:apply-templates select="ImpNote"/>
			<xsl:apply-templates select="@Reference"/>
			<xsl:apply-templates select="@Predicate"/>
			<xsl:apply-templates select="DataValues"/>
		</xsl:element>
	</xsl:template>
	<xsl:template match="UseCase">
		<xsl:element name="UseCase">
			<xsl:value-of select="."/>
			<xsl:apply-templates select="Purpose | Description | Actor | PreCondition | EventFlow | PostCondition | DerivativeEvent"/>
		</xsl:element>
	</xsl:template>
	<!-- transform profiles type id's -->
	<xsl:template match="@ConformanceType">
		<xsl:choose>
			<xsl:when test=".='Implementable'"> <!-- 8/28/8 change 'Strict' to 'Implementable' -->
				<xsl:attribute name="ProfileType">Implementation</xsl:attribute>
			</xsl:when>
			<xsl:when test=".='Constrainable'"> <!-- 8/28/8 change 'Tolerant'  to'Constrainable'-->
				<xsl:attribute name="ProfileType">Constrainable</xsl:attribute>
			</xsl:when>
			<xsl:otherwise>
				<xsl:attribute name="ProfileType">
					<xsl:value-of select="."/>
				</xsl:attribute>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<!-- Second Transform - selectively change value of all Optionality attributes from 'NS' to 'X'  (change to Usage)-->
	<xsl:template match="@Optionality">
		<xsl:choose>
			<xsl:when test=".='NS'">
				<xsl:attribute name="Usage">X</xsl:attribute>
			</xsl:when>
			<xsl:otherwise>
				<xsl:attribute name="Usage">
					<xsl:value-of select="."/>
				</xsl:attribute>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<!-- Transforms 3,4,5 - turn an attribute into an element -->
	<!-- This template turns ImpNote/Paralgraph content into a single ImpNote element with a CDATA section. -->
	<xsl:template match="ImpNote">
		<xsl:element name="ImpNote">
			<xsl:for-each select="Paragraph">
				<xsl:value-of select="concat(translate(.,'&#160;',' '),'&#10;')"/>
			</xsl:for-each>
		</xsl:element>
	</xsl:template>
	<xsl:template match="@Description">
		<xsl:if test=".!=''">
			<xsl:element name="Description">
				<xsl:value-of select="."/>
			</xsl:element>
		</xsl:if>
	</xsl:template>
	<xsl:template match=" @Reference ">
		<xsl:if test=".!=''">
			<xsl:element name="Reference">
				<xsl:value-of select="."/>
			</xsl:element>
		</xsl:if>
	</xsl:template>
	<xsl:template match="@Predicate">
		<xsl:if test=".!=''">
			<xsl:element name="Predicate">
				<xsl:value-of select="."/>
			</xsl:element>
		</xsl:if>
	</xsl:template>
	<xsl:template match="Actor">
		<xsl:element name="Actor">
			<xsl:attribute name="Name">
				<xsl:value-of select="@Name"/>
			</xsl:attribute>
			<xsl:value-of select="@Value"/>
		</xsl:element>
	</xsl:template>
	<xsl:template match="PreCondition">
		<xsl:element name="PreCondition">
			<xsl:attribute name="Name">
				<xsl:value-of select="@Name"/>
			</xsl:attribute>
			<xsl:value-of select="@Value"/>
		</xsl:element>
	</xsl:template>
	<xsl:template match="PostCondition">
		<xsl:element name="PostCondition">
			<xsl:attribute name="Name">
				<xsl:value-of select="@Name"/>
			</xsl:attribute>
			<xsl:value-of select="@Value"/>
		</xsl:element>
	</xsl:template>
	<xsl:template match="DerivativeEvent">
		<xsl:element name="DerivedEvent">
			<xsl:attribute name="Name">
				<xsl:value-of select="@Name"/>
			</xsl:attribute>
			<xsl:value-of select="@Value"/>
		</xsl:element>
	</xsl:template>
	<xsl:template match="EventFlow">
		<xsl:element name="EventFlow">
			<xsl:attribute name="Name">
				<xsl:value-of select="@Name"/>
			</xsl:attribute>
			<xsl:value-of select="@Value"/>
		</xsl:element>
	</xsl:template>
	<xsl:template match="DataValues">
		<xsl:if test="@ExValue!=''">
			<xsl:element name="DataValues">
				<xsl:attribute name="ExValue">
					<xsl:value-of select="@ExValue"/>
				</xsl:attribute>
			</xsl:element>
		</xsl:if>
	</xsl:template>
	<!-- empty template removes the paragraphs for the HL7Definition element -->
	<xsl:template match="HL7Definition | HL7Definition/Paragraph"/>
	<!-- Shouldn't have to reiterate Conformance Element, but without it, the xml output has:
	        <Conformance [atributes]>
	        </Conformance>
	 The MSXML parser won't validate, claiming that the element contains illegal text. If
	 I edit the output file (removing the eol chr) so that it looks like:
	        <Conformance [atributes]></Conformance>
	  The document validates fine. In XML Spy, both species validate. Diddling with MSXML
	  preservewhitespace property has no effect. -->
	<xsl:template match="Conformance">
		<xsl:element name="DynamicDef">
			<xsl:attribute name="AccAck">
				<xsl:value-of select="@AccAck"/>
			</xsl:attribute>
			<xsl:attribute name="AppAck">
				<xsl:value-of select="@AppAck"/>
			</xsl:attribute>
			<xsl:attribute name="MsgAckMode">
				<xsl:value-of select="@MsgAckMode"/>
			</xsl:attribute>
		</xsl:element>
	</xsl:template>
</xsl:stylesheet>
