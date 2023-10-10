package com.pack1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class YamlReading {

	public static Map<String, String> Data() {
		Yaml yaml = new Yaml();
		Map<String, String> detailsfromsalesforce = null;

		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			File file = new File(classLoader.getResource("Credentials.yaml").getFile());
			BufferedReader reader = new BufferedReader(new FileReader(file));
			detailsfromsalesforce = yaml.load(reader);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return detailsfromsalesforce;
	}
}
