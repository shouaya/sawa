
package com.jialu.sawa.utility;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesUtil.class);
	private static final Properties pros_property = new Properties();

	/**
	 * read message.properties
	 */
	static {
		try {
			InputStreamReader propertieReader = new InputStreamReader(
					PropertiesUtil.class.getClassLoader().getResourceAsStream("property.properties"), "UTF-8");
			pros_property.load(propertieReader);
			propertieReader.close();
		} catch (IOException e) {
			LOGGER.error("property.properties error", e);
		}
	}
	public static HashMap<String, String> getPropertyProperty(String key){
		HashMap<String, String> properties = new HashMap<String, String>();
		for (final String name : pros_property.stringPropertyNames()) {
			if (name.startsWith(key)) {
				properties.put(name.replace(key + ".", ""), pros_property.getProperty(name));
			}
		}
		return properties;
	}
}
