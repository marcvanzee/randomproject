/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ChairCreator;

import java.awt.Graphics;
import java.awt.Image;

public class MassEngine extends ChairEngine {

	int width, height;
	Image backbuffer;
	Graphics backg;

	public void createMass() {

		int[] bottomPoints = new int[] { 0, 1, 2, 3 };
		int[] midPoints = new int[] { 4, 5, 6, 7 };
		int[] topPoints = new int[] { 8, 9 };

		Vector nextPerp = new Vector();
		Vector[][] normVectors = new Vector[bottomPoints.length
				+ midPoints.length - 2][6];
		Vector[][] backSeatVectors = new Vector[2][10];
		Vector[][] backVectors = new Vector[2][3];

		int j;
		int x;

		// HIER HEB IK EIGENLIJK EEN POTJE VAN GEMAAKT. KAN VEEL DUIDELIJKER.
		for (int i = 0; i < points.length; i++) {

			Edge edge1 = new Edge(i, points[i].attachedTo[0]);
			Edge edge2 = new Edge(i, points[i].attachedTo[1]);

			if (i == topPoints[0] || i == topPoints[1]) {
				j = i - bottomPoints.length - midPoints.length;

				Vector tempVec = new Vector();

				if (backMode == 0) {
					backVectors[j][0] = Calculator
							.perpVector(
									edge1,
									edge2,
									(thicknessMode == 0 || thicknessMode == 2) ? Math
											.abs(((edge1.length() + edge2
													.length()) / 2))
											: THICKNESS);

					Edge diagonalEdge = new Edge(points[i].attachedTo[0],
							points[i].attachedTo[1]);
					Edge tempEdge = new Edge(
							points[points[i].attachedTo[0]].attachedTo[1],
							points[i].attachedTo[0]);

					if (i == topPoints[0]) {
						tempVec = new Vector(backSeatVectors[1][5]);
					} else {
						tempVec = new Vector(backSeatVectors[0][3]);
					}

					backVectors[j][0] = Calculator.correctPerpVector(
							diagonalEdge, tempEdge, edge1, tempVec,
							backVectors[j][0]);

				} else {
					edge1 = new Edge(i, points[i].attachedTo[2]);

					backVectors[j][0] = Calculator
							.perpVector(
									edge1,
									edge2,
									(thicknessMode == 0 || thicknessMode == 2) ? Math
											.abs(((edge1.length() + edge2
													.length()) / 2))
											: THICKNESS);

					Edge diagonalEdge = new Edge(
							points[points[i].attachedTo[0]].attachedTo[1],
							points[i].attachedTo[1]);
					Edge tempEdge = new Edge(
							points[points[i].attachedTo[1]].attachedTo[1],
							points[i].attachedTo[0]);

					if (i == topPoints[0]) {
						tempVec = new Vector(backSeatVectors[0][2]);
					} else {
						tempVec = new Vector(backSeatVectors[1][1]);
					}

					backVectors[j][0] = Calculator.correctPerpVector(
							diagonalEdge, tempEdge, edge1, tempVec,
							backVectors[j][0]);
				}

			} else {

				x = (i < midPoints.length) ? midPoints.length
						: bottomPoints.length;

				Edge edge3 = new Edge(i, points[i].attachedTo[2]);

				if (i == midPoints[0] || i == midPoints[1]) {
					j = i - bottomPoints.length;

					Edge edge4 = new Edge(i, points[i].attachedTo[3]);

					backSeatVectors[j][0] = Calculator
							.perpVector(
									edge1,
									edge3,
									(thicknessMode == 0 || thicknessMode == 2) ? Math
											.abs(((edge1.length() + edge3
													.length()) / 2))
											: THICKNESS);
					backSeatVectors[j][1] = new Vector(nextPerp);
					backSeatVectors[j][2] = Calculator
							.perpVector(
									edge1,
									edge2,
									(thicknessMode == 0 || thicknessMode == 2) ? Math
											.abs(((edge1.length() + edge2
													.length()) / 2))
											: THICKNESS);
					backSeatVectors[j][3] = Calculator
							.perpVector(
									edge1,
									edge4,
									(thicknessMode == 0 || thicknessMode == 2) ? Math
											.abs(((edge1.length() + edge4
													.length()) / 2))
											: THICKNESS);
					backSeatVectors[j][4] = Calculator
							.perpVector(
									edge2,
									edge4,
									(thicknessMode == 0 || thicknessMode == 2) ? Math
											.abs(((edge2.length() + edge4
													.length()) / 2))
											: THICKNESS);
					backSeatVectors[j][5] = Calculator
							.perpVector(
									edge3,
									edge4,
									(thicknessMode == 0 || thicknessMode == 2) ? Math
											.abs(((edge3.length() + edge4
													.length()) / 2))
											: THICKNESS);

					backSeatVectors[j][0] = Calculator.correctPerpVector(edge3,
							edge2, edge1, backSeatVectors[j][1],
							backSeatVectors[j][0]);
					backSeatVectors[j][2] = Calculator.correctPerpVector(edge1,
							edge3, edge2, backSeatVectors[j][0],
							backSeatVectors[j][2]);
					backSeatVectors[j][3] = Calculator.correctPerpVector(edge1,
							edge2, edge4, backSeatVectors[j][2],
							backSeatVectors[j][3]);
					backSeatVectors[j][4] = Calculator.correctPerpVector(edge4,
							edge1, edge2, backSeatVectors[j][3],
							backSeatVectors[j][4]);
					backSeatVectors[j][5] = Calculator.correctPerpVector(edge3,
							edge2, edge4, backSeatVectors[j][1],
							backSeatVectors[j][5]);

					Edge diagonalEdge = new Edge(i, points[i + 1].attachedTo[1]);

					nextPerp = Calculator
							.perpVector(
									edge1,
									diagonalEdge,
									(thicknessMode == 0 || thicknessMode == 2) ? Math
											.abs(((edge1.length() + Calculator
													.lineLength(
															points[i + 1],
															points[points[i + 1].attachedTo[1]])) / 2))
											: THICKNESS);
					nextPerp = Calculator.correctPerpVector(edge1, edge3,
							diagonalEdge, backSeatVectors[j][0], nextPerp);

				} else {
					if (i < bottomPoints.length)
						j = i;
					else
						j = i - 2;

					normVectors[j][0] = Calculator
							.perpVector(
									edge1,
									edge3,
									(thicknessMode == 0 || thicknessMode == 2) ? ((edge1
											.length() + edge3.length()) / 2)
											: THICKNESS);
					if (i == 0)
						normVectors[j][1] = Calculator
								.perpVector(
										edge2,
										edge3,
										(thicknessMode == 0 || thicknessMode == 2) ? Math
												.abs(((edge2.length() + edge3
														.length()) / 2))
												: THICKNESS);
					else
						normVectors[j][1] = new Vector(nextPerp);
					normVectors[j][2] = Calculator
							.perpVector(
									edge1,
									edge2,
									(thicknessMode == 0 || thicknessMode == 2) ? Math
											.abs(((edge1.length() + edge2
													.length()) / 2))
											: THICKNESS);

					normVectors[j][0] = Calculator.correctPerpVector(edge3,
							edge2, edge1, normVectors[j][1], normVectors[j][0]);
					normVectors[j][2] = Calculator.correctPerpVector(edge1,
							edge3, edge2, normVectors[j][0], normVectors[j][2]);

					if (i != bottomPoints.length - 1) {
						Edge diagonalEdge = new Edge(i,
								points[i + 1].attachedTo[1]);

						nextPerp = Calculator
								.perpVector(
										edge1,
										diagonalEdge,
										(thicknessMode == 0 || thicknessMode == 2) ? Math
												.abs(((edge1.length() + Calculator
														.lineLength(
																points[i + 1],
																points[points[i + 1].attachedTo[1]])) / 2))
												: THICKNESS);
						nextPerp = Calculator.correctPerpVector(edge1, edge3,
								diagonalEdge, normVectors[j][0], nextPerp);
					} else {
						Edge diagonalEdge = new Edge(points[i].attachedTo[1],
								points[i + 1].attachedTo[1]);

						nextPerp = Calculator
								.perpVector(
										new Edge((i + 1),
												points[i].attachedTo[1]),
										diagonalEdge,
										(thicknessMode == 0 || thicknessMode == 2) ? Math
												.abs(((Calculator
														.lineLength(
																points[i + 1],
																points[points[i + 1].attachedTo[0]]) + Calculator
														.lineLength(
																points[i + 1],
																points[points[i + 1].attachedTo[1]])) / 2))
												: THICKNESS);
						nextPerp = Calculator.correctPerpVector(diagonalEdge,
								i, (i + 1), normVectors[j][2], nextPerp);
					}
				}
			}
		}

		x = bottomPoints.length;

		Vector[] offsetVectors = new Vector[x];
		Vector[] invOffsetVectors = new Vector[x];

		for (int i = 0; i < x; i++) {
			offsetVectors[i] = new Vector(new Vector[] { normVectors[i][0],
					normVectors[i][1], normVectors[i][2] });
			invOffsetVectors[i] = new Vector(offsetVectors[i], -1);
		}

		double distance = 0;
		double invDistance = 0;

		for (int i = 0; i < x; i++) {
			distance = distance
					+ Calculator.lineLength(new Point3D(points[i],
							offsetVectors[i]), new Point3D(
							points[((i + 1) % x)], offsetVectors[(i + 1) % x]));
			invDistance = invDistance
					+ Calculator.lineLength(new Point3D(points[i],
							invOffsetVectors[i]), new Point3D(
							points[((i + 1) % x)],
							invOffsetVectors[(i + 1) % x]));
		}
		if (invDistance > distance) {
			for (int i = 0; i < points.length; i++) {
				if (i == x || i == x + 1) {
					backSeatVectors[i - x][0].scale(-1);
					backSeatVectors[i - x][1].scale(-1);
					backSeatVectors[i - x][2].scale(-1);
					backSeatVectors[i - x][3].scale(-1);
					backSeatVectors[i - x][4].scale(-1);
					backSeatVectors[i - x][5].scale(-1);
				} else if (i >= x + midPoints.length) {
					backVectors[i - (x + midPoints.length)][0].scale(-1);
				} else {
					if (i > x)
						j = i - 2;
					else
						j = i;

					normVectors[j][0].scale(-1);
					normVectors[j][1].scale(-1);
					normVectors[j][2].scale(-1);
				}
			}
		}

		Vector perp;
		Vector oppPerp;
		// HIER GA IK EVENTJES KIJKEN OF IK DE NAAR BENEDEN RICHTING ANDERS OM
		// WIL DOEN. (Potentieel een probleem...)
		for (int i = 0; i < points.length - 2; i++) {
			if (i == x || i == x + 1) {
				perp = backSeatVectors[i - x][0];
				oppPerp = new Vector(backSeatVectors[i - x][0], -1);
			} else {
				if (i > x)
					perp = normVectors[i - 2][0];
				else
					perp = normVectors[i][0];
				oppPerp = new Vector(perp, -1);
			}
			if (Calculator.lineLength(points[i], new Point3D(points[i], perp)) < Calculator
					.lineLength(points[i], new Point3D(points[i], oppPerp))) {
				if (i == x || i == x + 1)
					backSeatVectors[i - x][0].scale(-1);
				else {
					if (i > x)
						normVectors[i - 2][0].scale(-1);
					else
						normVectors[i][0].scale(-1);
				}
			}
		}

		// HET AANMAKEN VAN DE RICHTING VAN DE ZIJDEN.
		for (int i = 0; i < points.length; i++) {
			if (i == midPoints[0] || i == midPoints[1]) {
				j = i - bottomPoints.length;

				backSeatVectors[j][6] = new Vector(points[i].attachedTo[0], i);
				if (thicknessMode == 1)
					backSeatVectors[j][6].resize(THICKNESS);

				backSeatVectors[j][7] = new Vector(points[i].attachedTo[2], i);
				if (thicknessMode == 1)
					backSeatVectors[j][7].resize(THICKNESS);

				backSeatVectors[j][8] = new Vector(points[i].attachedTo[1], i);
				if (thicknessMode == 1)
					backSeatVectors[j][8].resize(THICKNESS);

				backSeatVectors[j][9] = new Vector(points[i].attachedTo[3], i);
				if (thicknessMode == 1)
					backSeatVectors[j][9].resize(THICKNESS);

				Vector offset = new Vector(backSeatVectors[j][1],
						backSeatVectors[j][2]);

				Vector direction = new Vector(backSeatVectors[j][6],
						backSeatVectors[j][7]);
				Vector oppDirection = new Vector(direction, -1);

				Vector oppOffset = new Vector(offset, oppDirection);
				offset.addVector(direction);

				if (offset.length() < oppOffset.length()) {
					// backSeatVectors[j][6].scale(-1);
					// backSeatVectors[j][7].scale(-1);

					// backSeatVectors[j][6] = new
					// Vector(backSeatVectors[j][7]);
					// backSeatVectors[j][7] = new Vector(tempvector);
				}

			} else if (i == topPoints[0] || i == topPoints[1]) {
				j = i - bottomPoints.length - midPoints.length;

				backVectors[j][1] = new Vector(points[i].attachedTo[0], i);
				if (thicknessMode == 1)
					backVectors[j][1].resize(THICKNESS);

				backVectors[j][2] = new Vector(points[i].attachedTo[1], i);
				if (thicknessMode == 1)
					backVectors[j][2].resize(THICKNESS);

			} else {
				if (i < bottomPoints.length)
					j = i;
				else
					j = i - 2;

				normVectors[j][3] = new Vector(points[i].attachedTo[0], i);
				if (thicknessMode == 1)
					normVectors[j][3].resize(THICKNESS);

				normVectors[j][4] = new Vector(points[i].attachedTo[2], i);
				if (thicknessMode == 1)
					normVectors[j][4].resize(THICKNESS);

				normVectors[j][5] = new Vector(points[i].attachedTo[1], i);
				if (thicknessMode == 1)
					normVectors[j][5].resize(THICKNESS);
			}
		}

		Point3D[][] outerBottomPoints = new Point3D[points.length][6];

		// nu vectoren toedelen aan de juiste punten, op de juiste wijzen...

		for (int i = 0; i < points.length; i++) {

			if (i == midPoints[0] || i == midPoints[1]) {
				j = i - bottomPoints.length;

				backSeatVectors[j][0].scale(-1);
				backSeatVectors[j][8].scale(-1);

				Vector firstCorner = new Vector(new Vector[] {
						backSeatVectors[j][0], backSeatVectors[j][1],
						backSeatVectors[j][2], backSeatVectors[j][6],
						backSeatVectors[j][6], backSeatVectors[j][7],
						backSeatVectors[j][7], backSeatVectors[j][8],
						backSeatVectors[j][8] });

				backSeatVectors[j][1].scale(-1);
				backSeatVectors[j][6].scale(-1);

				Vector secondCorner = new Vector(new Vector[] {
						backSeatVectors[j][0], backSeatVectors[j][1],
						backSeatVectors[j][2], backSeatVectors[j][6],
						backSeatVectors[j][6], backSeatVectors[j][7],
						backSeatVectors[j][7], backSeatVectors[j][8],
						backSeatVectors[j][8] });

				backSeatVectors[j][2].scale(-1);
				backSeatVectors[j][7].scale(-1);

				Vector thirdCorner = new Vector(new Vector[] {
						backSeatVectors[j][0], backSeatVectors[j][1],
						backSeatVectors[j][2], backSeatVectors[j][6],
						backSeatVectors[j][6], backSeatVectors[j][7],
						backSeatVectors[j][7], backSeatVectors[j][8],
						backSeatVectors[j][8] });

				backSeatVectors[j][1].scale(-1);
				backSeatVectors[j][6].scale(-1);

				Vector fourthCorner = new Vector(new Vector[] {
						backSeatVectors[j][0], backSeatVectors[j][1],
						backSeatVectors[j][2], backSeatVectors[j][6],
						backSeatVectors[j][6], backSeatVectors[j][7],
						backSeatVectors[j][7], backSeatVectors[j][8],
						backSeatVectors[j][8] });

				backSeatVectors[j][0].scale(-1);
				backSeatVectors[j][7].scale(-1);
				backSeatVectors[j][9].scale(-1);

				Vector fifthCorner = new Vector(new Vector[] {
						backSeatVectors[j][0], backSeatVectors[j][3],
						backSeatVectors[j][5], backSeatVectors[j][6],
						backSeatVectors[j][6], backSeatVectors[j][7],
						backSeatVectors[j][7], backSeatVectors[j][9],
						backSeatVectors[j][9] });

				if (i == midPoints[1]) {
					backSeatVectors[j][3].scale(-1);
					backSeatVectors[j][6].scale(-1);
				} else {
					backSeatVectors[j][5].scale(-1);
					backSeatVectors[j][7].scale(-1);

				}

				Vector sixthCorner = new Vector(new Vector[] {
						backSeatVectors[j][0], backSeatVectors[j][3],
						backSeatVectors[j][5], backSeatVectors[j][6],
						backSeatVectors[j][6], backSeatVectors[j][7],
						backSeatVectors[j][7], backSeatVectors[j][9],
						backSeatVectors[j][9] });

				if (backSeatMode == 1) {

					if (i == midPoints[1]) {
						backSeatVectors[j][3].scale(-1);
						backSeatVectors[j][6].scale(-1);
					} else {
						backSeatVectors[j][5].scale(-1);
						backSeatVectors[j][7].scale(-1);

					}

					backSeatVectors[j][0].scale(-1);
					backSeatVectors[j][9].scale(-1);

					firstCorner.addVectors(new Vector[] {
							backSeatVectors[j][0], backSeatVectors[j][3],
							backSeatVectors[j][5], backSeatVectors[j][6],
							backSeatVectors[j][6], backSeatVectors[j][7],
							backSeatVectors[j][7], backSeatVectors[j][9],
							backSeatVectors[j][9] });

					backSeatVectors[j][5].scale(-1);
					backSeatVectors[j][6].scale(-1);

					secondCorner.addVectors(new Vector[] {
							backSeatVectors[j][0], backSeatVectors[j][3],
							backSeatVectors[j][5], backSeatVectors[j][6],
							backSeatVectors[j][6], backSeatVectors[j][7],
							backSeatVectors[j][7], backSeatVectors[j][9],
							backSeatVectors[j][9] });

					backSeatVectors[j][3].scale(-1);
					backSeatVectors[j][7].scale(-1);

					thirdCorner.addVectors(new Vector[] {
							backSeatVectors[j][0], backSeatVectors[j][3],
							backSeatVectors[j][5], backSeatVectors[j][6],
							backSeatVectors[j][6], backSeatVectors[j][7],
							backSeatVectors[j][7], backSeatVectors[j][9],
							backSeatVectors[j][9] });

					backSeatVectors[j][5].scale(-1);
					backSeatVectors[j][6].scale(-1);

					fourthCorner.addVectors(new Vector[] {
							backSeatVectors[j][0], backSeatVectors[j][3],
							backSeatVectors[j][5], backSeatVectors[j][6],
							backSeatVectors[j][6], backSeatVectors[j][7],
							backSeatVectors[j][7], backSeatVectors[j][9],
							backSeatVectors[j][9] });

					backSeatVectors[j][0].scale(-1);
					backSeatVectors[j][7].scale(-1);
					backSeatVectors[j][8].scale(-1);

					fifthCorner.addVectors(new Vector[] {
							backSeatVectors[j][0], backSeatVectors[j][1],
							backSeatVectors[j][2], backSeatVectors[j][6],
							backSeatVectors[j][6], backSeatVectors[j][7],
							backSeatVectors[j][7], backSeatVectors[j][8],
							backSeatVectors[j][8] });

					if (i == midPoints[1]) {
						backSeatVectors[j][2].scale(-1);
						backSeatVectors[j][6].scale(-1);
					} else {
						backSeatVectors[j][1].scale(-1);
						backSeatVectors[j][7].scale(-1);

					}

					sixthCorner.addVectors(new Vector[] {
							backSeatVectors[j][0], backSeatVectors[j][1],
							backSeatVectors[j][2], backSeatVectors[j][6],
							backSeatVectors[j][6], backSeatVectors[j][7],
							backSeatVectors[j][7], backSeatVectors[j][8],
							backSeatVectors[j][8] });

				}

				outerBottomPoints[i][0] = cornerPoint(firstCorner,
						thicknessMode, i, 9);
				outerBottomPoints[i][1] = cornerPoint(secondCorner,
						thicknessMode, i, 9);
				outerBottomPoints[i][2] = cornerPoint(thirdCorner,
						thicknessMode, i, 9);
				outerBottomPoints[i][3] = cornerPoint(fourthCorner,
						thicknessMode, i, 9);
				outerBottomPoints[i][4] = cornerPoint(fifthCorner,
						thicknessMode, i, 9);
				outerBottomPoints[i][5] = cornerPoint(sixthCorner,
						thicknessMode, i, 9);

			} else if (i < bottomPoints.length) {

				j = i;

				Vector firstCorner = new Vector(
						new Vector[] { normVectors[j][0], normVectors[j][1],
								normVectors[j][2], normVectors[j][3],
								normVectors[j][3], normVectors[j][4],
								normVectors[j][4], normVectors[j][5],
								normVectors[j][5] });

				normVectors[j][1].scale(-1);
				normVectors[j][3].scale(-1);

				Vector secondCorner = new Vector(
						new Vector[] { normVectors[j][0], normVectors[j][1],
								normVectors[j][2], normVectors[j][3],
								normVectors[j][3], normVectors[j][4],
								normVectors[j][4], normVectors[j][5],
								normVectors[j][5] });

				normVectors[j][2].scale(-1);
				normVectors[j][4].scale(-1);

				Vector thirdCorner = new Vector(
						new Vector[] { normVectors[j][0], normVectors[j][1],
								normVectors[j][2], normVectors[j][3],
								normVectors[j][3], normVectors[j][4],
								normVectors[j][4], normVectors[j][5],
								normVectors[j][5] });

				normVectors[j][1].scale(-1);
				normVectors[j][3].scale(-1);

				Vector fourthCorner = new Vector(
						new Vector[] { normVectors[j][0], normVectors[j][1],
								normVectors[j][2], normVectors[j][3],
								normVectors[j][3], normVectors[j][4],
								normVectors[j][4], normVectors[j][5],
								normVectors[j][5] });

				outerBottomPoints[i][0] = cornerPoint(firstCorner,
						thicknessMode, i, 9);
				outerBottomPoints[i][1] = cornerPoint(secondCorner,
						thicknessMode, i, 9);
				outerBottomPoints[i][2] = cornerPoint(thirdCorner,
						thicknessMode, i, 9);
				outerBottomPoints[i][3] = cornerPoint(fourthCorner,
						thicknessMode, i, 9);

			} else if (i == topPoints[0] || i == topPoints[1]) {
				j = i - bottomPoints.length - midPoints.length;

				Vector firstCorner = new Vector(
						new Vector[] { backVectors[j][0], backVectors[j][1],
								backVectors[j][2] });

				backVectors[j][0].scale(-1);

				Vector secondCorner = new Vector(
						new Vector[] { backVectors[j][0], backVectors[j][1],
								backVectors[j][2] });

				outerBottomPoints[i][0] = cornerPoint(firstCorner,
						thicknessMode, i, 3);
				outerBottomPoints[i][1] = cornerPoint(secondCorner,
						thicknessMode, i, 3);

			} else {

				j = i - 2;

				Vector firstCorner = new Vector(
						new Vector[] { normVectors[j][0], normVectors[j][1],
								normVectors[j][2], normVectors[j][3],
								normVectors[j][3], normVectors[j][4],
								normVectors[j][4], normVectors[j][5],
								normVectors[j][5] });

				normVectors[j][0].scale(-1);
				normVectors[j][1].scale(-1);
				normVectors[j][3].scale(-1);
				normVectors[j][5].scale(-1);

				Vector secondCorner = new Vector(
						new Vector[] { normVectors[j][0], normVectors[j][1],
								normVectors[j][2], normVectors[j][3],
								normVectors[j][3], normVectors[j][4],
								normVectors[j][4], normVectors[j][5],
								normVectors[j][5] });

				normVectors[j][2].scale(-1);
				normVectors[j][4].scale(-1);

				Vector thirdCorner = new Vector(
						new Vector[] { normVectors[j][0], normVectors[j][1],
								normVectors[j][2], normVectors[j][3],
								normVectors[j][3], normVectors[j][4],
								normVectors[j][4], normVectors[j][5],
								normVectors[j][5] });

				normVectors[j][1].scale(-1);
				normVectors[j][3].scale(-1);

				Vector fourthCorner = new Vector(
						new Vector[] { normVectors[j][0], normVectors[j][1],
								normVectors[j][2], normVectors[j][3],
								normVectors[j][3], normVectors[j][4],
								normVectors[j][4], normVectors[j][5],
								normVectors[j][5] });

				outerBottomPoints[i][0] = cornerPoint(firstCorner,
						thicknessMode, i, 9);
				outerBottomPoints[i][1] = cornerPoint(secondCorner,
						thicknessMode, i, 9);
				outerBottomPoints[i][2] = cornerPoint(thirdCorner,
						thicknessMode, i, 9);
				outerBottomPoints[i][3] = cornerPoint(fourthCorner,
						thicknessMode, i, 9);
			}
		}

		x = points.length;
		int y = 0;

		points = new Point3D[(4 * bottomPoints.length) + (4 * midPoints.length)
				+ 4 + (2 * topPoints.length)];

		for (int i = 0; i < x; i++) {

			if (i < bottomPoints.length) {
				for (int k = 0; k < 4; k++) {
					points[y] = outerBottomPoints[i][k];
					y = y + 1;
				}
			} else if (i < bottomPoints.length + midPoints.length) {
				for (int k = 0; k < 4; k++) {
					points[y] = outerBottomPoints[i][k];
					y = y + 1;
				}

				if (i == (bottomPoints.length + midPoints.length) - 1) {
					points[y] = outerBottomPoints[bottomPoints.length][4];
					y = y + 1;
					points[y] = outerBottomPoints[bottomPoints.length][5];
					y = y + 1;
					points[y] = outerBottomPoints[bottomPoints.length + 1][4];
					y = y + 1;
					points[y] = outerBottomPoints[bottomPoints.length + 1][5];
					y = y + 1;
				}
			} else {
				for (int k = 0; k < 2; k++) {
					points[y] = outerBottomPoints[i][k];
					y = y + 1;
				}
			}
		}

		y = 0;

		triangles = new Triangle[76];

		for (int i = 0; i < bottomPoints.length; i++) {
			x = i * bottomPoints.length;

			triangles[y] = new Triangle((0 + x), (1 + x), (3 + x));
			triangles[y + 1] = new Triangle((1 + x), (2 + x), (3 + x));
			triangles[y + 2] = new Triangle((0 + x), (1 + x), (16 + x));
			triangles[y + 3] = new Triangle((0 + x), (3 + x), (16 + x));
			triangles[y + 4] = new Triangle((2 + x), (1 + x), (18 + x));
			triangles[y + 5] = new Triangle((2 + x), (3 + x), (18 + x));
			triangles[y + 6] = new Triangle((1 + x), (16 + x), (17 + x));
			triangles[y + 7] = new Triangle((1 + x), (18 + x), (17 + x));
			triangles[y + 8] = new Triangle((3 + x), (16 + x), (19 + x));
			triangles[y + 9] = new Triangle((3 + x), (18 + x), (19 + x));

			y = y + 10;
		}

		// onderkant zitvlak
		for (int i = 0; i < midPoints.length; i++) {
			x = i * midPoints.length;

			triangles[y] = new Triangle((17 + x), (18 + x),
					((i < midPoints.length - 1) ? 23 + x : 19));
			triangles[y + 1] = new Triangle((18 + x),
					((i < midPoints.length - 1) ? 22 + x : 18),
					((i < midPoints.length - 1) ? 23 + x : 19));

			y = y + 2;
		}
		triangles[y] = new Triangle(18, 30, 26);
		y = y + 1;
		triangles[y] = new Triangle(22, 18, 26);
		y = y + 1;

		// zijkanten zitvlak
		triangles[y] = new Triangle(16, 19, 33);
		y = y + 1;
		triangles[y] = new Triangle(16, 32, 33);
		y = y + 1;
		triangles[y] = new Triangle(16, 17, 32);
		y = y + 1;
		triangles[y] = new Triangle(20, 23, 34);
		y = y + 1;
		triangles[y] = new Triangle(32, 17, 23);
		y = y + 1;
		triangles[y] = new Triangle(23, 34, 32);
		y = y + 1;
		triangles[y] = new Triangle(20, 21, 35);
		y = y + 1;
		triangles[y] = new Triangle(20, 34, 35);
		y = y + 1;
		triangles[y] = new Triangle(21, 35, 27);
		y = y + 1;
		triangles[y] = new Triangle(35, 24, 27);
		y = y + 1;
		triangles[y] = new Triangle(24, 25, 31);
		y = y + 1;
		triangles[y] = new Triangle(24, 31, 28);
		y = y + 1;
		triangles[y] = new Triangle(28, 29, 19);
		y = y + 1;
		triangles[y] = new Triangle(28, 19, 33);
		y = y + 1;

		// zitvlak
		triangles[y] = new Triangle(35, 33, 24);
		y = y + 1;
		triangles[y] = new Triangle(24, 33, 28);
		y = y + 1;

		// zijkant van rug
		triangles[y] = new Triangle(32, 33, 37);
		y = y + 1;
		triangles[y] = new Triangle(32, 36, 37);
		y = y + 1;
		triangles[y] = new Triangle(34, 35, 39);
		y = y + 1;
		triangles[y] = new Triangle(38, 39, 34);
		y = y + 1;

		// bovenkant van rug
		triangles[y] = new Triangle(39, 38, 36);
		y = y + 1;
		triangles[y] = new Triangle(36, 37, 39);
		y = y + 1;

		// hier komt de waarde van de schuine lijn in het spel... Moet ik
		// tijdens het verweven naar kijken.

		x = 39;
		int z = 33;
		// voorkant van rug
		triangles[y] = new Triangle(35, 33, x);
		y = y + 1;
		triangles[y] = new Triangle(37, 39, z);
		y = y + 1;

		// achterkant van rug
		triangles[y] = new Triangle(32, 34, (x - 1));
		y = y + 1;
		triangles[y] = new Triangle(36, 38, (z - 1));
	}

	public static Point3D cornerPoint(Vector vec, int mode, int i, double num) {

		if (mode == 0)
			vec.scale(FACTOR);
		else if (mode == 1)
			vec.resize(THICKNESS);
		else
			vec.scale(FACTOR * (1 / num));
		Point3D point = new Point3D(points[i], vec);

		return point;
	}
}