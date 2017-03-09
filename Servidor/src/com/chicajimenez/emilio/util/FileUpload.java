package com.chicajimenez.emilio.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.Part;

public class FileUpload {
	

	public static void writeToFileUsingFileOutputStream(InputStream filecontent,

			String filePath) throws IOException {

		OutputStream out = null;

		try {

			out = new FileOutputStream(new File(filePath));

			int read = 0;

			final byte[] bytes = new byte[1024];

			while ((read = filecontent.read(bytes)) != -1) {

				out.write(bytes, 0, read);

			}

		} finally {

			if (out != null) {

				out.close();

			}

			if (filecontent != null) {

				filecontent.close();

			}

		}

	}

	public static String getFileName(final Part part) {

		final String partHeader = part.getHeader("content-disposition");

		String[] sections = partHeader.split(";");

		for (String content : sections) {

			if (content.trim().startsWith("filename")) {

				return content.substring(content.indexOf('=') + 1).trim()

						.replace("\"", "");

			}

		}

		return null;

	}
}
