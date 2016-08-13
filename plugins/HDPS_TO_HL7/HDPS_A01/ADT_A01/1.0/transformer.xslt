<?xml version="1.0" encoding="utf-8"?><xsl:stylesheet version="2.0" extension-element-prefixes="uuid" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:uuid="xalan://java.util.UUID">
<xsl:output indent="yes" method="xml" encoding="utf-8" omit-xml-declaration="yes"/>
<xsl:template match="/">
<ADT_A01>
	<MSH>
		<MSH.1>
			<xsl:variable name="thecte" select="'|'"/>
<xsl:copy-of select="$thecte"/>
		</MSH.1>
		<MSH.2>
			<xsl:variable name="thecte" select="'^~\&amp;'"/>
<xsl:copy-of select="$thecte"/>
		</MSH.2>
		<MSH.3>
			<HD.1>
				<xsl:value-of select="/hdps/uid">
				</xsl:value-of>
			</HD.1>
			
			
		</MSH.3>
		
		
		
		
		
		<MSH.9>
			<CM_MSG.1>
				<xsl:variable name="thecte" select="'ADT'"/>
<xsl:copy-of select="$thecte"/>
			</CM_MSG.1>
			<CM_MSG.2>
				<xsl:variable name="thecte" select="'A01'"/>
<xsl:copy-of select="$thecte"/>
			</CM_MSG.2>
		</MSH.9>
		<MSH.10>
			<xsl:variable name="uuid" select="uuid:randomUUID()"/>
<xsl:copy-of select="$uuid"/>
			<!-- <map:value xmlns:map="mapping" path='/hdps/sourceUid'> </map:value> -->
		</MSH.10>
		
		<MSH.12>
			<xsl:variable name="thecte" select="'2.3'"/>
<xsl:copy-of select="$thecte"/>
		</MSH.12>
		
		
		
		
		
		
		
		
	</MSH>
	
	<PID>
		<PID.1>
			<xsl:value-of select="/hdps/pid/id">
			</xsl:value-of>
		</PID.1>
		
		<PID.3>
			<CX.1>
				<xsl:value-of select="/hdps/pid/code">
				</xsl:value-of>
			</CX.1>
			
			
			
			
			
		</PID.3>
		
		<PID.5>
			<XPN.1>
				<xsl:value-of select="/hdps/pid/lastName">
				</xsl:value-of>
			</XPN.1>
			<XPN.2>
				<xsl:value-of select="/hdps/pid/firstName">
				</xsl:value-of>
			</XPN.2>
			
			
			
			
			
			
		</PID.5>
		
		<PID.7>
			<TS.1>
				<xsl:value-of select="/hdps/pid/birthDate">
				</xsl:value-of>
			</TS.1>
			
		</PID.7>
		<PID.8>
			<xsl:value-of select="/hdps/pid/gender">
			</xsl:value-of>
		</PID.8>
		
		
		<PID.11>
			<XAD.1>
				<xsl:value-of select="/hdps/pid/address">
				</xsl:value-of>
			</XAD.1>
			
			<XAD.3>
				<xsl:value-of select="/hdps/pid/address">
				</xsl:value-of>
			</XAD.3>
			
			
			
			
			
			
			
		</PID.11>
		
		<PID.13>
			<XTN.1>
				<xsl:value-of select="/hdps/pid/homePhone">
				</xsl:value-of>
			</XTN.1>
			
			
			
			
			
			
			
			
		</PID.13>
		
		
		<PID.16>
			<xsl:value-of select="/hdps/pid/maritalStatus">
			</xsl:value-of>
		</PID.16>
		
		
		
		
		
		
		
		
		
		
		
		
		<PID.29>
			<TS.1>
				<xsl:value-of select="/hdps/pid/deceasedDate">
				</xsl:value-of>
			</TS.1>
			
		</PID.29>
		
		
	</PID>
	
	
	
	
	
	<xsl:for-each select="/hdps/obx">
		<OBX>
			<OBX.1>
				<xsl:value-of select="./id">
				</xsl:value-of>
			</OBX.1>
			<OBX.2>
				<xsl:variable name="thecte" select="'ST'"/>
<xsl:copy-of select="$thecte"/>
			</OBX.2>
			<OBX.3>
				
				<CE.2>
					<xsl:value-of select="./resultUid">
					</xsl:value-of>
				</CE.2>
				
				
				
				
			</OBX.3>
			
			<OBX.5>
				<xsl:value-of select="./valueReference">
				</xsl:value-of>
			</OBX.5>
			<OBX.6>
				
				<CE.2>
					<xsl:value-of select="./unitLabel">
					</xsl:value-of>
				</CE.2>
				
				
				
				
			</OBX.6>
			
			
			
			
			
			
			
			<OBX.14>
				<TS.1>
					<xsl:value-of select="./examDate">
					</xsl:value-of>
				</TS.1>
				
			</OBX.14>
			
			
			
			
		</OBX>
	</xsl:for-each>

	<xsl:for-each select="/hdps/al">
		<AL1>
			<AL1.1>
				<xsl:value-of select="./id">
				</xsl:value-of>
			</AL1.1>
			
			<AL1.3>
				
				<CE.2>
					<xsl:value-of select="./label">
					</xsl:value-of>
				</CE.2>
				
				
				
				
			</AL1.3>
			<AL1.4>
				<xsl:value-of select="./severity">
				</xsl:value-of>
			</AL1.4>
			
			<AL1.6>
				<xsl:value-of select="./diagnosticDate">
				</xsl:value-of>
			</AL1.6>
			
		</AL1>
	</xsl:for-each>
	
	
	
	
	
	
	
	
</ADT_A01>
</xsl:template>
</xsl:stylesheet>
