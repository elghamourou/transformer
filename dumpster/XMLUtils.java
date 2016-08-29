package com.mscc.mapper.utils;

import java.io.IOException;
import java.io.DataOutputStream;

public class XMLUtils {
	public static String escape(String str) {
		String result;
		result = str.replace("&", "&amp;");
		result = result.replace("<", "&lt;");
		result = result.replace(">", "&gt;");
		result = result.replace("\"", "&quot;");
		result = result.replace("'", "&apos;");
		return result;
	}

	public static String unEscape(String str) {
		String result;
		result = str.replace("&amp;", "&");
		result = result.replace("&lt;", "<");
		result = result.replace("&gt;", ">");
		result = result.replace("&quot;", "\"");
		result = result.replace("&apos;", "'");
		return result;
	}

	public static void writeStr(String str, DataOutputStream outStream) {
		try {
			for (int i = 0; i < str.length(); i++) {
				char c = str.charAt(i);
				outStream.writeByte(c);
			}
		} catch (IOException e) {
			System.out.println(e);
			return;
		}
	}
}
