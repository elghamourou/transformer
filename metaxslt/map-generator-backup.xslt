<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:axsl="http://www.w3.org/1999/XSL/TransformAlias" xmlns:map="mapping"
	version="2.0" exclude-result-prefixes="map">
	<xsl:output indent="yes" method="xml" encoding="utf-8"
		omit-xml-declaration="no" />
	<xsl:namespace-alias stylesheet-prefix="axsl"
		result-prefix="xsl" />



	<xsl:template match="/">
<!-- 		xmlns:uuid="java:java.util.UUID-->
		<xsl:variable name="outermostElementName" select="name(/*)" />
		<axsl:stylesheet version="2.0" 
		xmlns:uuid="xalan://java.util.UUID" 
		extension-element-prefixes="uuid">
			<axsl:output indent="yes" method="xml" encoding="utf-8"
				omit-xml-declaration="yes" />
			<axsl:template match="/">
				<xsl:apply-templates />
			</axsl:template>
		</axsl:stylesheet>
	</xsl:template>


	<!-- template to remove any element that does not have a descendent mapped to something -->
 	<xsl:template match="*[not(attribute::map:*) and not(descendant-or-self::map:*)]"/>

	<!-- template to copy elements -->
	<xsl:template match="*">
		<xsl:element name="{local-name()}">
			<xsl:apply-templates select="@* | node()" />
		</xsl:element>
	</xsl:template>

	<!-- template to copy attributes -->
	<xsl:template match="@*">
	<!--
		<xsl:attribute name="{local-name()}">
			<xsl:value-of select="." />
		</xsl:attribute>
	-->
	</xsl:template>
	
	<!-- template to copy the rest of the nodes -->
	<xsl:template match="comment() | text() | processing-instruction()">
		<xsl:copy />
	</xsl:template>


	<xsl:template match="map:value">
		<axsl:value-of select="{@path}">
			<xsl:apply-templates />
		</axsl:value-of>
	</xsl:template>
	
	<xsl:template match="map:cte">
		<axsl:variable name="thecte" select="'{@val}'" />
		<axsl:copy-of select="$thecte" />
	</xsl:template>

	<xsl:template match="map:uuid">
		<axsl:variable name="uuid" select="uuid:randomUUID()"/>
		<axsl:copy-of select="$uuid" />
	</xsl:template>


	<xsl:template match="map:list">
		<axsl:for-each select="{@path}">
			<xsl:apply-templates />
		</axsl:for-each>
	</xsl:template>


	<xsl:template match="map:list2">
		<axsl:for-each select="{@path}">
			<axsl:apply-templates select="." />
		</axsl:for-each>
	</xsl:template>


	<xsl:template match="map:concate">
		<xsl:apply-templates />
	</xsl:template>

	<xsl:template match="map:arg">
		<axsl:value-of select="{@path}" />
	</xsl:template>

	<xsl:template match="map:if-starts-with">
		<axsl:for-each select="{@path}">
			<axsl:variable name="string-value" select="." />
			<axsl:if test="starts-with($string-value, '{@string}')">
				<xsl:apply-templates />
			</axsl:if>
		</axsl:for-each>
	</xsl:template>
	
	
	
	<xsl:template match="map:if-ends-with">
		<axsl:for-each select="{@path}">
			<axsl:variable name="string-value" select="." />
			<axsl:if test="substring($string-value, (string-length($string-value) - string-length(&apos;{@string}&apos;)) + 1) = &apos;{@string}&apos;">
				<xsl:apply-templates />
			</axsl:if>
		</axsl:for-each>
	</xsl:template>
	
	
	<xsl:template match="map:to-attribute">
		<axsl:attribute name="{@name}" >
			<axsl:value-of select="{@path}" />
		</axsl:attribute>
	</xsl:template>
	
	
	
	
	

</xsl:stylesheet>