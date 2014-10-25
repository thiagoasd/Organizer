package SubtitleSearcher;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Searcher {

	static String[] excludedFolders = new String[] { "" };
	static ArrayList<String> excludedSuffixes = new ArrayList<String>();
	static ArrayList<String> NotSubtitled = new ArrayList<String>();

	public static void main(String[] args) {
		startExcludedSuffixes();
		String path = args[0];
		File dir = new File(path);
		File[] list = dir.listFiles();
		for (File folder : list) {
			if (folder.isDirectory())
				if (!folder.getName().startsWith("."))
					search(folder);
		}
		
		for (String file : NotSubtitled) {
			System.out.println(file);
		}

	}

	public static ArrayList<File> search(File folder) {
		ArrayList<File> files = new ArrayList<File>();
		for (File file : folder.listFiles()) {
			if (file.getName().startsWith(".")) // if its a metadata folder,
												// skip
				continue;

			if (file.isDirectory()) { // if folder, search inside
				search(file);

			}

			else {
				if (file.getName().endsWith("mkv")
						|| file.getName().endsWith("srt")
						|| file.getName().endsWith("avi")
						|| file.getName().endsWith("mp4")
						|| file.getName().endsWith("rmvb")) {
					files.add(file);
				} else
					addExcludedSuffixes(file);
			}
		}

		for (File file : files) {
			boolean found = false;
			for (int i = 0; i < files.size(); i++) {
				String name1 = getFileName(file);
				String name2 = getFileName(files.get(i));
				if (!name1.equals(name2))
					continue;
				else {
					String extension1 = getFileExtension(file);
					String extension2 = getFileExtension(files.get(i));
					if (!extension1.equals(extension2)
							&& (extension1.equals("srt") || extension2
									.equals("srt"))) // if its different but one
														// of them is a subtitle
					{
						found = true;
						break;
					}
				}

			}
			if (found == false) {
				NotSubtitled.add(file.getName());
			}
		}

		return files;
	}

	public static void addExcludedSuffixes(File file) {
		String suffix = file.getName().substring(
				file.getName().lastIndexOf(".") + 1);
		if (!excludedSuffixes.contains(suffix))
			excludedSuffixes.add(suffix);
	}

	public static void startExcludedSuffixes() {

		excludedSuffixes.add("png");
		excludedSuffixes.add("jpg");
		excludedSuffixes.add("tbn");

	}

	private static String getFileName(File file) {
		String name = file.getName().substring(0,
				file.getName().lastIndexOf("."));
		return name;
	}

	private static String getFileExtension(File file) {
		String extension = file.getName().substring(
				file.getName().lastIndexOf(".") + 1);
		return extension;
	}
}
