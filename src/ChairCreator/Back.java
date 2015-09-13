/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ChairCreator;

public class Back extends ChairEngine {
	private static final long serialVersionUID = 1L;
	private int[] backPoints;
	private static int[] backToSeat = new int[2];

	public Back() {
	}

	public Back(int[] p) {
		backPoints = p;
	}

	public int getPoint(int num) {
		return backPoints[num];
	}

	public void switchBackPoints() {
		int x = backPoints[0];
		backPoints[0] = backPoints[1];
		backPoints[1] = x;
	}

	public void setLengthBackToSeat(int num) {
		backToSeat = new int[num];
	}

	public int getBackToSeat(int num) {
		return backToSeat[num];
	}

	public void setBackToSeat(int num, int x) {
		this.backToSeat[num] = x;
	}

	public void setBackToSeat(int[] nums) {
		this.backToSeat[0] = nums[0];
		this.backToSeat[1] = nums[1];
	}

	public int[] getPoints() {
		return backPoints;
	}

}