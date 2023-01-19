package com.blueteak.csvutil;

import java.io.File;

public class CSVUtil {

	public static String getAbsPath() {
		String path = "src/main/resources/combined";
		File file = new File(path);
		String absolutePath = file.getAbsolutePath();
		System.out.println(absolutePath);
		return absolutePath;
	}
}
