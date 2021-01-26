package core;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Objects;
import java.util.Properties;

public class AppConfig {

	private static AppConfig _instance;
	private final Properties properties;

	private AppConfig() {
		this.properties = Objects.requireNonNull(readPropertiesFile(), "Properties file can't be readed");
	}

	public static AppConfig getInstance() {
		if ( _instance == null ) {
			_instance = new AppConfig();
		}
		return _instance;
	}


	private Properties readPropertiesFile() {
		final String propFileName = "app.properties";
		InputStream is = Objects.requireNonNull(getClass()
				.getClassLoader()
				.getResourceAsStream(propFileName));

		try (InputStreamReader in = new InputStreamReader(is, Charset.defaultCharset()) ) {
			Properties prop = new Properties();
			prop.load(in);
			return prop;
		} catch (IOException ignored) {}

		return null;
	}

	public String getStringProperty(String name) {
		return replaceVariables(properties.getProperty(name));
	}

	public Integer getIntegerProperty(String name) {
		return Integer.valueOf(getStringProperty(name));
	}

	public Boolean getBooleanProperty(String name) {
		return Boolean.valueOf(getStringProperty(name));
	}

	private String replaceVariables(String content) {
		if ( content == null ) {
			return null;
		}
		if ( content.contains("$") ) {
			String[] vars = content.split("\\$");
			for ( String var : vars ) {
				if ( var.contains("{") && var.contains("}") ) {
					String cleanVar = var.replaceAll(".*\\{", "").replaceAll("\\}.*", "");
					content = content.replace("$"+var, getStringProperty(cleanVar));
				}
			}
			String resultAsAnotherVariable = getStringProperty(content);
			if ( resultAsAnotherVariable != null ) {
				return resultAsAnotherVariable;
			}
		}
		return content;
	}

}
