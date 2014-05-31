package Renamer;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Series {
	static String folderPath;
	static String moveFolderPath;
	static String[] acceptedExtensions = new String[] {"mp4", "mkv", "srt"};
	static String[] rejectedExtensions = new String[] {"nfo"};
	static String expression = "(^.*)(S\\d\\dE\\d\\d)(.*)";

	public static void main(String[] args) {

		if (args.length == 0) {
			folderPath = "C:\\Users\\Thiago\\Downloads\\Torrent";
			moveFolderPath = "C:\\Users\\Thiago\\Downloads\\Torrent\\";
		} else {
			folderPath = args[0];
			moveFolderPath = args[1];
		}
		File folder = new File(folderPath);

		Parser(folder);

	}

	public static void Parser(File folder) {
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			System.out.println(listOfFiles[i].getName());
			if (listOfFiles[i].isFile()) {
				renamer(listOfFiles[i]);
			} else if (listOfFiles[i].isDirectory()) {
				Parser(listOfFiles[i]);
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
		
		if (!isValidExtension(extension)){
			if(isDeletable(extension))
				file.delete();
			return;
		}
			
		
		Pattern regex = Pattern.compile(expression);
		Matcher m = regex.matcher(fileName);

		if (m.find()) {
			String tvShow = m.group(1);
			String episode = m.group(2);
			String release = m.group(3);

			String[] parts = tvShow.split("\\.| ");

			if (parts.length > 0) {
				for (int i = 0; i < parts.length; i++) {
					result += parts[i] + " ";
				}

				result += episode + " " + release + " ";
				System.out.println(result);

				File resultFile = new File(moveFolderPath + result);

				if (file.renameTo(resultFile)) {
					System.out.println("Rename succesful");
				} else {
					System.out.println("Rename failed");
				}

			}
		}

	}

	public static boolean isValidExtension(String extension) {
		boolean flag = false;
		for (String Vextension : acceptedExtensions) {
			if (Vextension.compareToIgnoreCase(extension) == 0) {
				flag = true;
				break;
			}
		}
		
		return flag;
	}
	
	public static boolean isDeletable(String extension) {
		boolean flag = false;
		for (String Vextension : rejectedExtensions) {
			if (Vextension.compareToIgnoreCase(extension) == 0) {
				flag = true;
				break;
			}
		}
		
		return flag;
	}

	
}
