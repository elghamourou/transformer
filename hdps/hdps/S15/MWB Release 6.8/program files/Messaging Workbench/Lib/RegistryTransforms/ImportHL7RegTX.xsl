<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:strip-space elements="*"/>
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	<!--          Main Transform - Copy all but selected attribute nodes -->
	<xsl:template match="/ | @* | node()">
		<xsl:copy>
			<xsl:apply-templates select="output/@encoding"/>
			<xsl:apply-templates select="@Usage"/>
			<xsl:apply-templates select="Reference"/>
			<xsl:apply-templates select="Predicate"/>
			<xsl:apply-templates select="Description"/>
			<xsl:apply-templates select="@* | node()[not(name()='Reference')][not(name()='Predicate')][not(name()='Description')]"/>
	<xsl:apply-templates select="Message | DataValues | SegGroup | Segment | Field | Component | SubComponent"/> 
		</xsl:copy>
	</xsl:template>
	<!-- Make sure encoding is UTF-8 -->
	<xsl:template match="output/@encoding">
		<xsl:attribute name="encoding">UTF-8</xsl:attribute>
	</xsl:template>
	<xsl:template match="HL7v2xConformanceProfile">
		<xsl:element name="Specification">
			<xsl:attribute name="SpecName"><xsl:value-of select="MetaData/@Name"/></xsl:attribute>
			<xsl:attribute name="OrgName"><xsl:value-of select="MetaData/@OrgName"/></xsl:attribute>
			<xsl:attribute name="HL7Version"><xsl:value-of select="@HL7Version"/></xsl:attribute>
			<xsl:attribute name="SpecVersion"><xsl:value-of select="MetaData/@Version"/></xsl:attribute>
			<xsl:attribute name="Status"><xsl:value-of select="MetaData/@Status"/></xsl:attribute>
			<xsl:apply-templates select="@ProfileType"/>
			<xsl:attribute name="Role"><xsl:value-of select="HL7v2xStaticDef/@Role"/></xsl:attribute>
			<xsl:attribute name="HL7OID"><xsl:value-of select="HL7v2xStaticDef/@Identifier"/></xsl:attribute>
			<xsl:apply-templates select="ImpNote"/>
			<xsl:apply-templates select="UseCase"/>
			<xsl:apply-templates select="DynamicDef "/>
			<xsl:apply-templates select="Encodings"/>
			<xsl:apply-templates select="HL7v2xStaticDef "/>
		</xsl:element>
	</xsl:template>
	<xsl:template match="HL7v2xStaticDef">
		<xsl:attribute name="SpecName"><xsl:value-of select="MetaData/@Name"/></xsl:attribute>
		<xsl:attribute name="OrgName"><xsl:value-of select="MetaData/@OrgName"/></xsl:attribute>
		<xsl:attribute name="HL7Version"><xsl:value-of select="@HL7Version"/></xsl:attribute>
		<xsl:element name="Message">
			<xsl:attribute name="MsgType"><xsl:value-of select="@MsgType"/></xsl:attribute>
			<xsl:attribute name="EventType"><xsl:value-of select="@EventType"/></xsl:attribute>
			<xsl:if test="@OrderControl!=''">
				<xsl:attribute name="OrderControl"><xsl:value-of select="@OrderControl"/></xsl:attribute>
			</xsl:if>
			<xsl:attribute name="EventDesc"><xsl:value-of select="@EventDesc"/></xsl:attribute>
			<xsl:attribute name="Structure">
			</xsl:attribute>
			<xsl:attribute name="MsgStructID"><xsl:value-of select="@MsgStructID"/></xsl:attribute>
			<xsl:apply-templates select="SegGroup | Segment"/>
		</xsl:element>
	</xsl:template>
	<xsl:template match="DynamicDef ">
		<xsl:element name="Conformance">
			<xsl:attribute name="AccAck"><xsl:value-of select="@AccAck"/></xsl:attribute>
			<xsl:attribute name="AppAck"><xsl:value-of select="@AppAck"/></xsl:attribute>
			<xsl:attribute name="StaticID"><xsl:value-of select="/HL7v2xConformanceProfile/HL7v2xStaticDef /MetaData/@Topics"/></xsl:attribute>
			<xsl:attribute name="DynamicD"><xsl:value-of select="/HL7v2xConformanceProfile/MetaData/@Topics"/></xsl:attribute>
			<xsl:attribute name="MsgAckMode"><xsl:value-of select="@MsgAckMode"/></xsl:attribute>
		</xsl:element>
	</xsl:template>
	<!-- Second Transform - selectively change value of all Optionality attributes from 'X' to 'NS' -->
	<xsl:template match="@Usage">
		<xsl:choose>
			<xsl:when test=".='X'">
				<xsl:attribute name="Optionality">NS</xsl:attribute>
			</xsl:when>
			<xsl:otherwise>
				<xsl:attribute name="Optionality"><xsl:value-of select="."/></xsl:attribute>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<!-- transform Conformance type id's -->
	<xsl:template match="@ProfileType">
		<xsl:choose>
			<xsl:when test=".='Implementation'">
				<xsl:attribute name="ConformanceType">Strict</xsl:attribute>
			</xsl:when>
			<xsl:when test=".='Constrainable'">
				<xsl:attribute name="ConformanceType">Tolerant</xsl:attribute>
			</xsl:when>
			<xsl:otherwise>
				<!-- could make=constraiable, so that all types could be registere -->
				<xsl:attribute name="ConformanceType"><xsl:value-of select="."/></xsl:attribute>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template match="SegGroup">
		<xsl:element name="SegGroup">
			<xsl:attribute name="Name"><xsl:value-of select="@Name"/></xsl:attribute>
			<xsl:attribute name="Description"><xsl:value-of select="@LongName"/></xsl:attribute>
			<xsl:apply-templates select="@Usage"/>
			<xsl:attribute name="Sequence"/>
			<xsl:attribute name="Repeatable"/>
			<xsl:attribute name="Min"><xsl:value-of select="@Min"/></xsl:attribute>
			<xsl:attribute name="Max"><xsl:choose><xsl:when test="@Max='0'">*</xsl:when><xsl:otherwise><xsl:value-of select="@Max"/></xsl:otherwise></xsl:choose></xsl:attribute>
			<xsl:apply-templates select="Reference"/>
			<xsl:apply-templates select="Predicate"/>
			<xsl:apply-templates select="ImpNote"/>
			
			<xsl:apply-templates select="Segment|SegGroup"/> <!-- 10/27/05  Change line from listing Segment followed by Seggroup
			<xsl:apply-templates select="SegGroup"/> -->
		</xsl:element>
	</xsl:template>
	<xsl:template match="Segment">
		<xsl:element name="Segment">
			<xsl:attribute name="Name"><xsl:value-of select="@Name"/></xsl:attribute>
			<xsl:attribute name="Description"><xsl:value-of select="@LongName"/></xsl:attribute>
			<xsl:apply-templates select="@Usage"/>
			<xsl:attribute name="Sequence"/>
			<xsl:attribute name="Repeatable"/>
			<xsl:attribute name="Min"><xsl:value-of select="@Min"/></xsl:attribute>
			<xsl:attribute name="Max"><xsl:choose><xsl:when test="@Max='0'">*</xsl:when><xsl:otherwise><xsl:value-of select="@Max"/></xsl:otherwise></xsl:choose></xsl:attribute>
			<xsl:apply-templates select="Reference"/>
			<xsl:apply-templates select="Predicate"/>
			<xsl:apply-templates select="ImpNote"/>
			<xsl:apply-templates select="Field"/>
		</xsl:element>
	</xsl:template>
	<xsl:template match="Field">
				<xsl:element name="Field">
					<xsl:attribute name="Name"><xsl:value-of select="@Name"/></xsl:attribute>
					<xsl:apply-templates select="Description"/>
					<xsl:apply-templates select="@Usage"/>
					<xsl:attribute name="Min"><xsl:value-of select="@Min"/></xsl:attribute>
					<xsl:attribute name="Max"><xsl:choose><xsl:when test="@Max='0'">*</xsl:when><xsl:otherwise><xsl:value-of select="@Max"/></xsl:otherwise></xsl:choose></xsl:attribute>
					<xsl:attribute name="Datatype"><xsl:value-of select="@Datatype"/></xsl:attribute>
					<xsl:if test="@Length!='' and @Length!='0'">
						<xsl:attribute name="Length"><xsl:value-of select="@Length"/></xsl:attribute>
					</xsl:if>
					<xsl:if test="@Table!=''">
						<xsl:attribute name="Table"><xsl:value-of select="@Table"/></xsl:attribute>
					</xsl:if>
					<xsl:if test="@ConstantValue!=''">
						<xsl:attribute name="ConstantValue"><xsl:value-of select="@ConstantValue"/></xsl:attribute>
					</xsl:if>
					<xsl:attribute name="Itemno"><xsl:value-of select="@ItemNo"/></xsl:attribute>
					<xsl:apply-templates select="Reference"/>
					<xsl:apply-templates select="Predicate"/>
					<xsl:apply-templates select="ImpNote"/>
					<xsl:apply-templates select="DataValues"/>
					<xsl:apply-templates select="Component"/>
				</xsl:element>
	</xsl:template>
	<xsl:template match="Component">
		<xsl:element name="Component">
			<xsl:attribute name="Name"><xsl:value-of select="@Name"/></xsl:attribute>
			<xsl:apply-templates select="Description"/>
			<xsl:apply-templates select="@Usage"/>
			<xsl:attribute name="Datatype"><xsl:value-of select="@Datatype"/></xsl:attribute>
			<xsl:if test="@Length!='' and @Length!='0'">
				<xsl:attribute name="Length"><xsl:value-of select="@Length"/></xsl:attribute>
			</xsl:if>
			<xsl:if test="@Table!=''">
				<xsl:attribute name="Table"><xsl:value-of select="@Table"/></xsl:attribute>
			</xsl:if>
			<xsl:if test="@ConstantValue!=''">
				<xsl:attribute name="ConstantValue"><xsl:value-of select="@ConstantValue"/></xsl:attribute>
			</xsl:if>
			<xsl:apply-templates select="Reference"/>
			<xsl:apply-templates select="Predicate"/>
			<xsl:apply-templates select="ImpNote"/>
			<xsl:apply-templates select="DataValues"/>
	  	      <xsl:apply-templates select="SubComponent"/>
		</xsl:element>
	</xsl:template>
	<xsl:template match="SubComponent">
		<xsl:element name="SubComponent">
			<xsl:attribute name="Name"><xsl:value-of select="@Name"/></xsl:attribute>
			<xsl:apply-templates select="Description"/>
			<xsl:apply-templates select="@Usage"/>
			<xsl:attribute name="Datatype"><xsl:value-of select="@Datatype"/></xsl:attribute>
			<xsl:if test="@Length!='' and @Length!='0'">
				<xsl:attribute name="Length"><xsl:value-of select="@Length"/></xsl:attribute>
			</xsl:if>
			<xsl:if test="@Table!=''">
				<xsl:attribute name="Table"><xsl:value-of select="@Table"/></xsl:attribute>
			</xsl:if>
			<xsl:if test="@ConstantValue!=''">
				<xsl:attribute name="ConstantValue"><xsl:value-of select="@ConstantValue"/></xsl:attribute>
			</xsl:if>
			<xsl:apply-templates select="Reference"/>
			<xsl:apply-templates select="Predicate"/>
			<xsl:apply-templates select="ImpNote"/>
			<xsl:apply-templates select="DataValues"/>
		</xsl:element>
	</xsl:template>
	<!-- turn Description node into attribute -->
	<xsl:template match="Description">
		<xsl:attribute name="Description"><xsl:value-of select="."/></xsl:attribute>
	</xsl:template>
	<!-- turn Reference node into attribute -->
	<xsl:template match="Reference">
		<xsl:attribute name="Reference"><xsl:value-of select="."/></xsl:attribute>
	</xsl:template>
	<!-- turn Predicate node into attribute -->
	<xsl:template match="Predicate">
		<xsl:attribute name="Predicate"><xsl:value-of select="."/></xsl:attribute>
	</xsl:template>
	<!-- turn Impnote CDATA into Paragraph -->
	<xsl:template match="ImpNote">
		<xsl:element name="ImpNote">
			<xsl:element name="Paragraph">
				<!-- <xsl:value-of select="."/> -->
				<xsl:value-of select="translate(.,'&#160;&#194;',' ')"/>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!-- Make attributes empty elements -->
	<xsl:template match="UseCase">
		<xsl:element name="UseCase">
			<xsl:apply-templates select="Actor | PreCondition | EventFlow | PostCondition | DerivativeEvent"/>
		</xsl:element>
	</xsl:template>
	<!-- Actor -->
	<xsl:template match="Actor">
		<xsl:element name="Actor">
			<xsl:attribute name="Name"><xsl:value-of select="@Name"/></xsl:attribute>
			<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
		</xsl:element>
	</xsl:template>
	<!-- PreCondition -->
	<xsl:template match="PreCondition">
		<xsl:element name="PreCondition">
			<xsl:attribute name="Name"><xsl:value-of select="@Name"/></xsl:attribute>
			<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
		</xsl:element>
	</xsl:template>
	<!-- PostCondition -->
	<xsl:template match="PostCondition">
		<xsl:element name="PostCondition">
			<xsl:attribute name="Name"><xsl:value-of select="@Name"/></xsl:attribute>
			<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
		</xsl:element>
	</xsl:template>
	<!--DerivativeEvent -->
	<xsl:template match="DerivedEvent">
		<xsl:element name="DerivativeEvent">
			<xsl:attribute name="Name"><xsl:value-of select="@Name"/></xsl:attribute>
			<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
		</xsl:element>
	</xsl:template>
	<!-- EventFlow -->
	<xsl:template match="EventFlow">
		<xsl:element name="EventFlow">
			<xsl:attribute name="Name"><xsl:value-of select="@Name"/></xsl:attribute>
			<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
		</xsl:element>
	</xsl:template>
	<xsl:template match="Encodings">
		<xsl:element name="Encodings">
			<xsl:apply-templates select="Encoding"/>
		</xsl:element>
	</xsl:template>
	<xsl:template match="Encoding">
		<xsl:element name="Encoding">
			<xsl:value-of select="."/>
		</xsl:element>
	</xsl:template>
	<xsl:template match="DataValues">
		<xsl:if test="@ExValue!=''">
			<xsl:element name="DataValues">
				<xsl:attribute name="ExValue"><xsl:value-of select="@ExValue"/></xsl:attribute>
				<xsl:if test="../@ConstantValue!=''">
					<xsl:attribute name="Fixed"><xsl:text>True</xsl:text></xsl:attribute>
				</xsl:if>
			</xsl:element>
		</xsl:if>
	</xsl:template>
</xsl:stylesheet>
