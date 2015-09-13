/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ChairCreator;

import java.applet.Applet;
import java.awt.Button;
import java.awt.Color;
import java.awt.Event;
import java.awt.Font;
import java.awt.Panel;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class NewClass extends Applet {

	File testFile;

	public void init() {
		Panel p = new Panel();
		Font f;
		String osname = System.getProperty("os.name", "");
		if (!osname.startsWith("Windows")) {
			f = new Font("Arial", Font.BOLD, 10);
		} else {
			f = new Font("Verdana", Font.BOLD, 12);
		}
		p.setFont(f);
		p.add(new Button("New"));
		p.add(new Button("read"));
		p.add(new Button("write"));

		p.setBackground(new Color(255, 255, 255));

		add("North", p);

		testFile = new File("/Users/Home/Desktop/blah.txt");

	}

	public boolean action(Event evt, Object arg) {
		if (arg.equals("New")) {

			try {
				testFile.createNewFile();
			} catch (IOException iox) {
				System.out.println("File read error...");
				iox.printStackTrace();
			}

		} else if (arg.equals("read")) {

			System.out.println("New file contents: " + getContents(testFile));

		} else if (arg.equals("write")) {

			try {
				int x = 0;
				setContents(testFile, "" + x);
			} catch (IOException iox) {
				System.out.println("File read error...");
				iox.printStackTrace();
			}

		} else
			return false;

		return true;
	}

	static public String getContents(File aFile) {

		StringBuilder contents = new StringBuilder();

		try {
			BufferedReader input = new BufferedReader(new FileReader(aFile));
			try {
				String line = null;
				while ((line = input.readLine()) != null) {
					contents.append(line);
					contents.append(System.getProperty("line.separator"));
				}
			} finally {
				input.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return contents.toString();
	}

	static public void setContents(File aFile, String aContents)
			throws FileNotFoundException, IOException {
		if (aFile == null) {
			throw new IllegalArgumentException("File should not be null.");
		}
		if (!aFile.exists()) {
			throw new FileNotFoundException("File does not exist: " + aFile);
		}
		if (!aFile.isFile()) {
			throw new IllegalArgumentException("Should not be a directory: "
					+ aFile);
		}
		if (!aFile.canWrite()) {
			throw new IllegalArgumentException("File cannot be written: "
					+ aFile);
		}

		Writer output = new BufferedWriter(new FileWriter(aFile));
		try {
			output.write(aContents);
		} finally {
			output.close();
		}
	}

}
