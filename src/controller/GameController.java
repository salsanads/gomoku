/**
 * 
 * kelas ini merupakan kelas untuk mengontrol segala peraturan
 * dan kegiatan yang terjadi dalam permainan.
 * 
 * @author Salsabila Nadhifah
 * @version 13.12.2014
 * 
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import model.Board;
import model.Cell;
import model.GomokuFrame;
import view.BoardComponent;
import view.CellComponent;
import view.ScoreComponent;
import view.TimerComponent;

public class GameController implements ActionListener {

	private CellComponent cell;
	private static Cell lastColor;
	private static int numStep = 0;
	
	/**
	 * ArrayList ini berfungsi untuk menyimpan baris serta kolom bidak-
	 * bidak yang menyusun susunan menang
	 */
	private static ArrayList<Integer> rowOfWin = new ArrayList<Integer>();
	private static ArrayList<Integer> colOfWin = new ArrayList<Integer>();

	// array yang menyimpan perubahan baris untuk mengecek urutan warna pada
	// papan
	public final static int[] DELTA_X = { -1, 1, -1, 1, 0, 0, -1, 1 };
	// array yang menyimpan perubahan kolom untuk mengecek urutan warna pada
	// papan
	public final static int[] DELTA_Y = { -1, 1, 1, -1, 1, -1, 0, 0 };
	// variabel ini mempresentasikan jumlah arah yang digunakan untuk mengecek
	// urutan warna
	public final static int NUMBER_DIRECTION = 8;

	public final static ComputerPlayer computerPlayer = new ComputerPlayer();
	private static boolean isPlayWithComputer = false;
	private static boolean winnerFound = false;
	
	/**
	 * 
	 * konstruktor kelas GameController
	 * 
	 * @param cell
	 */
	public GameController(CellComponent cell)
	{
		this.cell = cell;
		lastColor = Cell.EMPTY;
	}

	/**
	 * 
	 * method actionPerformed() berisi langkah-langkah yang
	 * perlu dilakukan jika suatu cell pada papan diklik
	 * 
	 */
	public void actionPerformed(ActionEvent event)
	{
		if (cell.getEnableClick()) {
			if (cell.getColor() == Cell.EMPTY) {
	
				numStep++;
	
				if (lastColor == Cell.BLACK) {
					cell.setColor(Cell.WHITE);
					GomokuFrame.getBoardComponent().repaint();
					lastColor = Cell.WHITE;
					JLabel info = GomokuFrame.getInfoComponent().getCurrentPlayer();
					info.setText("turn: " + GomokuFrame.getFirstName()
							+ " | current color: black | total stone: "
							+ numStep);
				} else {
					cell.setColor(Cell.BLACK);
					GomokuFrame.getBoardComponent().repaint();
					lastColor = Cell.BLACK;
					JLabel info = GomokuFrame.getInfoComponent().getCurrentPlayer();
					info.setText("turn: " + GomokuFrame.getSecondName()
							+ " | current color: white | total stone: "
							+ numStep);
				}

				checkWinner(cell.getRow(), cell.getCol());
				checkCellAvailability();

				if (!winnerFound && isPlayWithComputer) {

					numStep++;

					computerPlayer.play();
					lastColor = Cell.WHITE;

					JLabel info = GomokuFrame.getInfoComponent().getCurrentPlayer();
					info.setText("turn: " + GomokuFrame.getFirstName()
							+ " | current color: black | total stone: "
							+ numStep);
	
					checkWinner(computerPlayer.getRow(), computerPlayer.getCol());
					checkCellAvailability();
				}
	
			} else {
				JOptionPane.showMessageDialog(null,
						"Cell already filled.\nPlease look an empty cell!",
						"Warning!", JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	/**
	 * Method untuk mengecek apakah jumlah di sekitar batu yang diletakan
	 * pemain yang sedang dalam gilirannya sama dengan lima, jika iya maka
	 * pemenang ditemukan sehingga semua cell diset untuk tidak dapat diklik,
	 * lalu pemenang diumumkan dan timer permainan dihentikan.
	 * 
	 * @param row
	 *			indeks baris
	 * @param row
	 *			indeks kolom
	 *
	 */
	public void checkWinner(int row, int col)
	{
		for (int i = 0; i < NUMBER_DIRECTION; i += 2) {
			// pada koordinat itu sendiri sudah terdapat batu
			int counter = 1;
			rowOfWin.clear();
			colOfWin.clear();
			rowOfWin.add(row);
			colOfWin.add(col);

			counter += countColor(row + DELTA_X[i], col + DELTA_Y[i], i);
			counter += countColor(row + DELTA_X[i + 1], col + DELTA_Y[i + 1],
					i + 1);

			if (counter >= 5) {
				counter = 0;
				for (int j = 0; j < rowOfWin.size(); j++) {
					BoardComponent.getCells()[rowOfWin.get(j)]
							[colOfWin.get(j)].setIsCellOfWin(true);
				}
				winnerFound = true;
				cellDisableClick();
				notifyWinner();
				TimerComponent.getTimer().stop();
				return;
			}
		}
	}
	
	/**
	 * Method untuk menghitung banyak batu yang berurutan secara horizontal,
	 * vertikal, ataupun diagonal selama koordinat yang dicek masih berada
	 * dalam papan dan warna batu yang berada pada koordinat tersebut merupakan
	 * warna batu pemain yang sedang dalam gilirannya
	 * 
	 * @param row
	 *			indeks baris
	 * @param col
	 *			indeks kolom
	 * @param index
	 *			indeks untuk array DELTA_X dan DELTA_Y
	 *
	 */
	public int countColor(int row, int col, int index)
	{
		int counter = 0;

		while (stillOnBoard(row, col)
				&& BoardComponent.getCells()[row][col].getColor() == lastColor) {
			counter++;
			rowOfWin.add(row);
			colOfWin.add(col);
			row += DELTA_X[index];
			col += DELTA_Y[index];
		}

		return counter;
	}
	
	/**
	 * Method untuk mengecek apakah indeks yang diberikan
	 * masih terletak di dalam papan
	 * 
	 * @param x
	 *			indeks baris
	 * @param y
	 *			indeks kolom
	 * @return true jika koordinat tersebut masih berada dalam papan
	 * @return false jika sebaliknya
	 *
	 */
	public static boolean stillOnBoard(int x, int y)
	{
		if (x >= 0 && x <= 18 && y >= 0 && y <= 18) {
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * method ini berguna untuk membuat cell-cell tidak dapat diklik
	 * kembali setelah sudah terdapat pemenang pada permainan tersebut
	 * 
	 */
	public void cellDisableClick()
	{
		for (int i = 0; i < Board.HEIGHT; i++) {
			for (int j = 0; j < Board.WIDTH; j++) {
				BoardComponent.getCells()[i][j].setEnableClick(false);
			}
		}
	}
	
	/**
	 * 
	 * method ini berkerja untuk mengumumkan pemenang dan menampilkannya
	 * pada JLabel kelas InfoComponent()
	 * 
	 */
	public void notifyWinner()
	{
		String winner = lastColor == Cell.BLACK ? "black" : "white";
		String name = "";
		
		if (winner.equals("black")) {
			name = GomokuFrame.getFirstName();
			ScoreComponent.updateScore(name, numStep);
		}

		else if (!isPlayWithComputer) {
			name = GomokuFrame.getSecondName();
			ScoreComponent.updateScore(name, numStep);
		}
		JLabel info = GomokuFrame.getInfoComponent().getCurrentPlayer();
		if (winner.equals("white") && isPlayWithComputer) {
			info.setText("ouch! you lose!");
		}
		else {
			info.setText("bravo! " + name + " wins with " + numStep + " step!");
		}
	}

	/**
	 * 
	 * method ini dipanggil ketika perminan dimulai ulang,
	 * method ini mensetting nilai-nilai serta tampilan kembali
	 * ke semula sebelum permainan dimulai
	 * 
	 */
	public static void restartGame()
	{
		numStep = 0;
		winnerFound = false;
		rowOfWin.clear();
		colOfWin.clear();
		TimerComponent.getTimer().stop();
		TimerComponent.resetTimer();
		GomokuFrame.getBoardComponent().clearBoard();
		GomokuFrame.getInfoComponent().clearInfo();
	}

	public static void setIsPlayWithComputer(Boolean value)
	{
		isPlayWithComputer = value;
	}

	public static boolean getIsPlayWithComputer()
	{
		return isPlayWithComputer;
	}

	
	public static int getNumStep()
	{
		return numStep;
	}

	/**
	 * 
	 * method ini mengecek ketersediaan cell kosong pada papan,
	 * jika sudah tidak tersedia maka permainan seri dan berhenti
	 * 
	 */
	public void checkCellAvailability()
	{
		boolean cellAvailable = false;
		for (int i = 0; i < Board.HEIGHT; i++) {
			for (int j = 0; j < Board.WIDTH; j++) {
				if (BoardComponent.getCells()[i][j].getColor() == Cell.EMPTY) {
					cellAvailable = true;
				}
			}
		}
		if (!cellAvailable) {
			cellDisableClick();
			JOptionPane.showMessageDialog(null,
					"Draw, all cells are filled.",
					"Draw", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
}
