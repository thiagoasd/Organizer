package Renamer;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Unrar {

	static String folderPath;
	static String moveFolderPath;
	static String[] acceptedExtensions = new String[] { "mp4", "mkv", "srt" };
	static String[] rejectedExtensions = new String[] { "nfo" };
	static String expression = "(^.*)(S\\d\\dE\\d\\d)(.*)";

	public static void main(String args[]) {

		if (args.length == 0) {
			folderPath = "Z:\\Torrent\\Completo\\Anime";
			moveFolderPath = "Z:\\Torrent\\Completo\\Anime\\";
		} else {
			folderPath = args[0];
			moveFolderPath = args[1];
		}
		File folder = new File(folderPath);

		parser(folder);

	}

	public static void parser(File folder) {
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			System.out.println(listOfFiles[i].getName());
			if (listOfFiles[i].isFile()) {
				renamer(listOfFiles[i]);
			} else if (listOfFiles[i].isDirectory()) {
				parser(listOfFiles[i]);
			}
		}
	}

	public static void renamer(File file) {
		String fileName = file.getName();
		String result = "";
		String extension = "";

		int pos = fileName.lastIndexOf(".");
		if (pos > 0) {
			extension = fileName.substring(pos + 1, fileName.length());
		}

		if (!fileName.contains(".")) {
			result = fileName + ".rar";

			File resultFile = new File(moveFolderPath + result);

			if (file.renameTo(resultFile)) {
				System.out.println("Rename succesful");
			} else {
				System.out.println("Rename failed");
			}
			return;
		}

	}

}
