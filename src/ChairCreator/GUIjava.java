package ChairCreator;

import java.applet.Applet;
import java.awt.Button;
import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.TextField;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;

public class GUIjava extends Applet implements MouseListener,
		MouseMotionListener, MouseWheelListener, KeyListener {
	private static final long serialVersionUID = 1L;

	static int width, height;
	int mx, my; // the most recently recorded mouse coordinates

	Image backbuffer;
	Graphics backg;

	int azimuth = 35, elevation = 30;
	double near = 3;
	double nearToObj = 1.5f;

	ChairEngine tryChair;

	Point3D vertices[];
	Edge edges[];
	Edge triangleEdges[];
	Triangle triangles[];

	TextField round_txt;

	boolean visible = true;

	boolean mass = false;

	Button New;
	Button Higher;
	Button Lower;

	File testFile;

	static String newContents;

	class Resizer extends ComponentAdapter {
		public void componentResized(ComponentEvent evt) {
			GUIjava.width = getSize().width;
			GUIjava.height = getSize().height;

			backbuffer = createImage(width, height);
			backg = backbuffer.getGraphics();

			New.setBounds((width / 2) - 50, 20, 100, 30);
			Higher.setBounds((width / 2) - 140, height - 50, 120, 30);
			Lower.setBounds((width / 2) + 20, height - 50, 120, 30);

			drawWireframe(backg);
			repaint();
		}
	}

	public void init() {

		setLayout(null);

		resize(500, 500);
		width = getSize().width;
		height = getSize().height;

		backbuffer = createImage(width, height);
		backg = backbuffer.getGraphics();

		setBackground(Color.white);

		New = new Button("New Chair");
		Higher = new Button("Higher");
		Lower = new Button("Lower");

		New.setBounds((width / 2) - 50, 20, 100, 30);
		Higher.setBounds((width / 2) - 140, height - 50, 120, 30);
		Lower.setBounds((width / 2) + 20, height - 50, 120, 30);

		add(New);
		add(Higher);
		add(Lower);

		testFile = new File("blah.txt");

		try {
			testFile.createNewFile();
			setContents(testFile, basis());
		} catch (IOException iox) {
			System.out.println("File read error...");
			iox.printStackTrace();
		}

		createChair();

		drawWireframe(backg);

		addMouseListener(this);
		addMouseMotionListener(this);
		addMouseWheelListener(this);
		addKeyListener(this);

		addComponentListener(new Resizer());
	}

	public String basis() {
		return "SEAT_STABILITY = 0.2\nSEAT_STEEP = 170\nSEAT_MIN_HEIGHT =  0.1\nSEAT_MAX_HEIGHT =  1.2\nBACK_MIN_ANGLE = 70\nBACK_MAX_ANGLE = 150\nLEGS_STABILITY = 0.2\nCHAIR_HEIGHT = 1.8";
	}

	void createChair() {
		tryChair = new ChairEngine();
		Boolean done = false;

		tryChair.mass = this.mass;

		/*
		 * try { getContents(null); } catch(IOException iox) {
		 * System.out.println("File read error..."); iox.printStackTrace(); }
		 */

		while (!done) {
			done = tryChair.go();
			repaint();
		}

		if (mass == true) {
			MassEngine massEngine = new MassEngine();
			massEngine.createMass();
		}

		// for(int i = 0; i < tryChair.messages.length;
		// i++)System.out.println(tryChair.messages[i]);

		vertices = tryChair.points;
		edges = tryChair.edges;
		triangles = tryChair.triangles;

		sortEdges();

	}

	void sortEdges() {

		// TEKEN DE TE TEKENEN LIJNEN VAN DE GEGEVEN DRIEHOEKEN, ZONDER LIJNEN
		// TWEE KEER TE TEKENEN

		Edge[] tempEdgesToDraw = new Edge[triangles.length * 3];
		int x = 0;

		for (int i = 0; i < triangles.length; i++) {
			for (int j = 0; j < 3; j++) {
				boolean edgeTrue = true;
				for (int k = 0; k < x; k++) {
					if (triangles[i].edge[j] == tempEdgesToDraw[k]) {
						edgeTrue = false;
						break;
					}
				}
				if (edgeTrue) {
					tempEdgesToDraw[x] = triangles[i].edge[j];
					x++;
				}
			}
		}

		triangleEdges = new Edge[x];

		for (int i = 0; i < x; i++) {
			triangleEdges[i] = tempEdgesToDraw[i];
		}

	}

	void drawWireframe(Graphics g) {
		Point3D[] realPoints = new Point3D[vertices.length];
		int[] positions = new int[triangles.length];
		Point[] points = new Point[vertices.length];

		projection2d(realPoints);
		sort(realPoints, positions);

		realPointsToPoints(realPoints, points);

		// draw the wireframe
		g.setColor(Color.white);
		g.fillRect(0, 0, width, height);

		// defining the planes of the back and the seat, jezus kanker stijve
		// hark.

		for (int i = 0; i < triangles.length; ++i) {

			g.setColor(Color.LIGHT_GRAY);

			g.fillPolygon(new int[] { points[triangles[i].point[0]].x,
					points[triangles[i].point[1]].x,
					points[triangles[i].point[2]].x }, new int[] {
					points[triangles[i].point[0]].y,
					points[triangles[i].point[1]].y,
					points[triangles[i].point[2]].y }, 3);

		}

		// defining the ribs of the chair
		g.setColor(Color.black);
		if (edges != null) {
			for (int j = 0; j < edges.length; ++j) {
				g.drawLine(points[edges[j].a].x, points[edges[j].a].y,
						points[edges[j].b].x, points[edges[j].b].y);
			}
		}

		for (int j = 0; j < triangleEdges.length; ++j) {
			g.drawLine(points[triangleEdges[j].a].x,
					points[triangleEdges[j].a].y, points[triangleEdges[j].b].x,
					points[triangleEdges[j].b].y);

		}

	}

	void counter(Graphics g, String round) {
		g.setColor(Color.white);
		g.fillRect(0, 0, width, height);

		g.setColor(Color.black);
		g.drawString(round, 10, 10);
	}

	void realPointsToPoints(Point3D[] realPoints, Point[] points) {
		for (int i = 0; i < vertices.length; ++i) {
			if (vertices[i] == null)
				continue;
			points[i] = new Point((int) realPoints[i].x, (int) realPoints[i].y);
		}
	}

	void sort(Point3D[] realPoints, int[] positions) { // gaat er problematisch
														// genoeg nu van uit dat
														// je sowieso werkt met
														// 3 hoeken
		double[] totalDepths = new double[triangles.length]; // maar in alle
																// eerlijkheid
																// zijn
																// vierkanten op
																// dit moment
																// toekomst
																// muziek.

		for (int i = 0; i < triangles.length; ++i) {
			double totalDepth = realPoints[triangles[i].point[0]].z
					+ realPoints[triangles[i].point[1]].z
					+ realPoints[triangles[i].point[2]].z;
			totalDepth = 10000 + ((totalDepth / 3) * 10) + i;
			totalDepths[i] = totalDepth;
		}

		for (int i = 0; i < triangles.length; ++i) {
			double maxDepth = 0;
			int position = 0;
			for (int j = 0; j < triangles.length; ++j) {
				double curDepth = totalDepths[j];

				if (curDepth > maxDepth) {
					maxDepth = curDepth;
					position = j;
				}
			}
			positions[i] = position;
			totalDepths[position] = 0;
		}
	}

	public void projection2d(Point3D[] realPoints) {
		// compute coefficients for the projection
		double theta = Math.PI * azimuth / 180.0;
		double phi = Math.PI * elevation / 180.0;
		double cosT = (double) Math.cos(theta), sinT = (double) Math.sin(theta);
		double cosP = (double) Math.cos(phi), sinP = (double) Math.sin(phi);
		double cosTcosP = cosT * cosP, cosTsinP = cosT * sinP, sinTcosP = sinT
				* cosP, sinTsinP = sinT * sinP;

		// project vertices onto the 2D viewport
		int scaleFactor = width / 4;
		// double near = 3; // distance from eye to near plane
		// double nearToObj = 1.5f; // distance from near plane to center of
		// object
		for (int j = 0; j < vertices.length; ++j) {
			if (vertices[j] == null)
				continue;
			double x0 = vertices[j].x;
			double y0 = vertices[j].y;
			double z0 = vertices[j].z;

			// compute an orthographic projection
			double x1 = cosT * x0 + sinT * z0;
			double y1 = -sinTsinP * x0 + cosP * y0 + cosTsinP * z0;

			// now adjust things to get a perspective projection
			double z1 = cosTcosP * z0 - sinTcosP * x0 - sinP * y0;
			x1 = x1 * near / (z1 + near + nearToObj);
			y1 = y1 * near / (z1 + near + nearToObj);

			// the 0.5 is to round off when converting to int
			realPoints[j] = new Point3D((width / 2 + scaleFactor * x1 + 0.5),
					(height / 2 - scaleFactor * y1 + 0.5), z1);
		}
	}

	public boolean action(Event evt, Object arg) {
		if (arg.equals("New Chair")) {

			mass = false;

			createChair();

			drawWireframe(backg);
			repaint();

		} else if (arg.equals("Higher")) {

			try {

				double collectValue = tryChair.SEAT_MIN_HEIGHT;

				String newValue = Double.toString(collectValue);

				getContents(new String[] { "SEAT_MIN_HEIGHT", newValue });

				setContents(testFile, newContents);

			} catch (IOException iox) {
				System.out.println("File read error...");
				iox.printStackTrace();
			}

		} else if (arg.equals("Lower")) {

			try {
				double collectValue = tryChair.SEAT_MAX_HEIGHT;

				String newValue = Double.toString(collectValue);

				getContents(new String[] { "SEAT_MAX_HEIGHT", newValue });

				setContents(testFile, newContents);
			} catch (IOException iox) {
				System.out.println("File read error...");
				iox.printStackTrace();
			}

		} else
			return false;

		return true;
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

	public final void getContents(String[] newValue)
			throws FileNotFoundException {
		Scanner scanner = new Scanner(testFile);

		try {
			int i = 0;
			while (scanner.hasNextLine()) {
				String[] currentContents = processLine(scanner.nextLine());

				if (newValue == null)
					settings(currentContents);
				else {
					if (i == 0) {
						if (currentContents[0].equals(newValue[0]))
							newContents
									.concat((newValue[0] + " = " + newValue[1]));
						else
							newContents
									.concat((currentContents[0] + " = " + currentContents[1]));

						System.out.println(newContents);

					} else {
						if (currentContents[0].equals(newValue[0]))
							newContents
									.concat(("\n" + newValue[0] + " = " + newValue[1]));
						else
							newContents.concat(("\n" + currentContents[0]
									+ " = " + currentContents[1]));

						System.out.println(newContents);

					}
				}

			}
		} finally {

			scanner.close();
		}
	}

	public String[] processLine(String aLine) {

		Scanner scanner = new Scanner(aLine);
		scanner.useDelimiter("=");
		if (scanner.hasNext()) {
			String name = scanner.next();
			String value = scanner.next();

			return new String[] { name.trim(), value.trim() };
		}

		scanner.close();

		return null;
	}

	public void settings(String[] strings) {

		for (int i = 0; i < strings.length; i++) {
			String setting = strings[0];

			if (setting.equals("SEAT_STABILITY"))
				tryChair.SEAT_STABILITY = Double.parseDouble(strings[1]);
			else if (setting.equals("SEAT_STEEP"))
				tryChair.SEAT_STEEP = Double.parseDouble(strings[1]);
			else if (setting.equals("SEAT_MIN_HEIGHT"))
				tryChair.SEAT_MIN_HEIGHT = Double.parseDouble(strings[1]);
			else if (setting.equals("SEAT_MAX_HEIGHT"))
				tryChair.SEAT_MAX_HEIGHT = Double.parseDouble(strings[1]);
			else if (setting.equals("BACK_MIN_ANGLE"))
				tryChair.BACK_MIN_ANGLE = Double.parseDouble(strings[1]);
			else if (setting.equals("BACK_MAX_ANGLE"))
				tryChair.BACK_MAX_ANGLE = Double.parseDouble(strings[1]);
			else if (setting.equals("LEGS_STABILITY"))
				tryChair.LEGS_STABILITY = Double.parseDouble(strings[1]);
			else if (setting.equals("CHAIR_HEIGHT"))
				tryChair.CHAIR_HEIGHT = Double.parseDouble(strings[1]);

		}
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		mx = e.getX();
		my = e.getY();
		e.consume();
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {
	}

	public void mouseDragged(MouseEvent e) {
		// get the latest mouse position
		int new_mx = e.getX();
		int new_my = e.getY();

		// adjust angles according to the distance travelled by the mouse
		// since the last event
		azimuth -= new_mx - mx;
		elevation += new_my - my;

		// update the backbuffer
		drawWireframe(backg);

		// update our data
		mx = new_mx;
		my = new_my;

		repaint();
		e.consume();
	}

	public void mouseWheelMoved(MouseWheelEvent e) {
		int notches = e.getWheelRotation();
		if (notches < 0) {
			nearToObj = nearToObj * 2;

		} else {
			nearToObj = nearToObj / 2;
		}

		// update the backbuffer
		drawWireframe(backg);

		repaint();
		e.consume();

	}

	public void keyPressed(KeyEvent arg0) {
	}

	public void keyReleased(KeyEvent arg0) {
	}

	public void keyTyped(KeyEvent e) {
		if (e.getKeyChar() == KeyEvent.VK_1) {
			mass = false;

			createChair();

			drawWireframe(backg);
			repaint();
			e.consume();

		} else if (e.getKeyChar() == KeyEvent.VK_2) {
			mass = true;

			createChair();

			drawWireframe(backg);
			repaint();
			e.consume();
		}

	}

	public void update(Graphics g) {
		g.drawImage(backbuffer, 0, 0, this);
	}

	public void paint(Graphics g) {

		update(g);
	}
}