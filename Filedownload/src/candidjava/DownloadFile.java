package candidjava;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DownloadFile extends javax.servlet.http.HttpServlet implements
		javax.servlet.Servlet {
	static final long serialVersionUID = 1L;
	private static final int BUFSIZE = 4096;
	String directory = "D:\\Development pool\\FiletoDownload";
	String filePath;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String filePath = directory + File.separator + "FileTest.txt";
		/**
		 * create a new file
		 */
		File file = new File(filePath);

		file.createNewFile();
		/**
		 * generate data for the new file
		 */
		FileWriter writer = new FileWriter(file);

		writer.write("Hello data here");
		writer.flush();
		writer.close();

		/**
		 * download file from server
		 */
		int length = 0;
		ServletOutputStream outStream = response.getOutputStream();
		response.setContentType("text/html");
		response.setContentLength((int) file.length());

		String fileName = (new File(filePath)).getName();
		response.setHeader("Content-Disposition", "attachment; filename=\""+ fileName + "\"");

		byte[] byteBuffer = new byte[BUFSIZE];
		DataInputStream in = new DataInputStream(new FileInputStream(file));

		while ((in != null) && ((length = in.read(byteBuffer)) != -1)) {
			outStream.write(byteBuffer, 0, length);
		}

		in.close();
		outStream.close();
	}
}
