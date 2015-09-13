package ChairCreator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class readSettings {

	public static void main(String... aArgs) throws FileNotFoundException {
		readSettings parser = new readSettings("/Users/Home/Desktop/blah.txt");
		parser.processLineByLine();
		log("Done.");
	}

	public readSettings(String aFileName) {
		fFile = new File(aFileName);
	}

	public final void processLineByLine() throws FileNotFoundException {
		Scanner scanner = new Scanner(fFile);
		try {

			while (scanner.hasNextLine()) {
				processLine(scanner.nextLine(), "blabla ");
			}
		} finally {

			scanner.close();
		}
	}

	protected void processLine(String aLine, String str) {

		Scanner scanner = new Scanner(aLine);
		scanner.useDelimiter("=");
		if (scanner.hasNext()) {
			String name = scanner.next();
			String value = scanner.next();

			if (name.equals(str))
				return;

			log("Name is : " + quote(name.trim()) + ", and Value is : "
					+ quote(value.trim()));
		} else {
			log("Empty or invalid line. Unable to process.");
		}

		scanner.close();
	}

	// PRIVATE //
	private final File fFile;

	private static void log(Object aObject) {
		System.out.println(String.valueOf(aObject));
	}

	private String quote(String aText) {
		String QUOTE = "'";
		return QUOTE + aText + QUOTE;
	}
}
