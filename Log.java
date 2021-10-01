import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

/**
 * The Class download and save Log.
 * created junaedi harmiansyah
 * date 10/02/2021 12:38 AM
 */
public class Log {

	/** The scanner1. */
	private static Scanner scanner1 = new Scanner(System.in);
	
	/** The scanner2. */
	private static Scanner scanner2 = new Scanner(System.in);
	
	/** The scanner3. */
	private static Scanner scanner3 = new Scanner(System.in);
	
	/** The download and save. */
	private static String downloadAndSave = null;
	
	/** The h. */
	private static String h = null;
	
	/** The option. */
	private static int option = 0;
	
	/** The start. */
	private static int start = 0;
	
	/** The length one. */
	private static int lengthOne = 0;
	
	/** The length two. */
	private static int lengthTwo = 0;
	
	/** The path url destination. */
	private static String pathUrlDestination = "";
	
	/** The path source. */
	private static String pathSource = "";
	
	/** The type source. */
	private static String typeSource = "";
	
	/** The path source final. */
	private static String pathSourceFinal = "";

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {

		System.out.println("Ketik -h untuk melihat panduan pengunaan");
		h = scanner1.nextLine();

		guide(h);

	}

	/**
	 * Guide function for user run program step by step.
	 *
	 * @param h the h
	 */
	private static void guide(String h) {
		if (h != null && h.equalsIgnoreCase("-h")) {
			System.out.println("Petunjuk Download");
			System.out
					.println("Download file log: /var/log/nginx/error.log -t");
			System.out.println("atau");
			System.out
					.println("Dwnload file log type json:  /var/log/nginx/error.log -t json");
			System.out
					.println("Download file log type text: /var/log/nginx/error.log -t text");
			System.out.println();
			System.out.println("Petunjuk Penyimpanan");
			System.out
					.println("Menyimpan file download: /var/log/nginx/error.log -o /User/johnmayer/Desktop/nginxlog.txt");
			System.out.println("atau");
			System.out
					.println("Menyimpan file download: /var/log/nginx/error.log -t json -o /User/johnmayer/Desktop/nginxlog.json");

		}
		;
		System.out.println();
		System.out.println("Masukan format download dan penyimpanan anda! ");
		downloadAndSave = scanner2.nextLine();
		save(downloadAndSave);

	}

	/**
	 * General Save function for move file log on server directory  to local directory .
	 *
	 * @param save the save
	 */
	private static void save(String save) {
		if (save.contains("/var/log/nginx/error.log")) {
			String destination = "";
			lengthOne = save.length();

			for (int i = 0; i < lengthOne; i++) {
				if (save.charAt(i) == '-' && save.charAt(i + 1) == 'o') {
					start = i;
					break;
				}
			}
			pathSource = save.substring(0, start);
			destination = save.substring(start, lengthOne);
			if (save.equalsIgnoreCase(destination)) {
				System.out
						.println("Format download dan penyimpanan anda tidak sesuai! ");
				System.out
						.println("Masukan format download dan penyimpanan anda kembali! ");
				downloadAndSave = "";
				downloadAndSave = scanner2.nextLine();
				save(downloadAndSave);
			}
			lengthTwo = destination.length();
			for (int i = 0; i < lengthTwo; i++) {
				if (i > 1) {
					pathUrlDestination = pathUrlDestination
							+ destination.charAt(i);
				}

			}

			createPathSource(pathSource.trim());

			boolean isCek = downloadAndSaveLog(pathUrlDestination,
					pathSourceFinal, typeSource);
			if (isCek) {
				System.out.println("Sukses download file log");

			} else {
				System.out.println("Gagal download file log");

			}

		} else {
			System.out.println("Format download tidak sesuai!");
			System.out.println();
			System.out
					.println("Masukan 1 untuk kembali ke petunjuk download dan penyimpana!");
			System.out.println("Masukan 2 untuk kembali mendownload file!");
			option = scanner3.nextInt();
			switch (option) {
			case 1:
				guide(h);
				break;
			case 2:
				System.out
						.println("Masukan format download dan penyimpanan anda kembali! ");
				downloadAndSave = "";
				downloadAndSave = scanner2.nextLine();
				save(downloadAndSave);
				break;

			}

		}
		return;
	}

	/**
	 * Download and save log.
	 *
	 * @param pathUrlDestination2 the path url destination2
	 * @param pathSource2 the path source2
	 * @param typeSource2 the type source2
	 * @return true, if successful
	 */
	private static boolean downloadAndSaveLog(String pathUrlDestination2,
			String pathSource2, String typeSource2) {

		try (InputStream in = new FileInputStream(pathSource2.trim());
				OutputStream out = new FileOutputStream(
						pathUrlDestination2.trim())) {
			byte[] buf = new byte[1024];
			int length;
			while ((length = in.read(buf)) > 0) {
				out.write(buf, 0, length);
			}

			out.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	/**
	 * Creates the path source.
	 *
	 * @param pathSource the path source
	 */
	private static void createPathSource(String pathSource) {
		int lengthTree = pathSource.length();
		pathSourceFinal = "";
		if (!pathSource.contains("-")) {
			pathSourceFinal = pathSource;
			typeSource = "text";
		} else {
			for (int i = 0; i < lengthTree; i++) {
				pathSourceFinal = pathSourceFinal + pathSource.charAt(i);
				if (pathSource.charAt(i + 1) == '-'
						&& pathSource.charAt(i + 2) == 't') {
					break;
				}

			}
			typeSource = pathSource.substring(lengthTree - 4, lengthTree);
		}

	}
}
