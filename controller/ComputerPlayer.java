/**
 * 
 * kelas ini didesain untuk dapat menentukan cell mana yang
 * paling banyak keuntungannya dengan menghitung keadaan cell
 * di sekitarnya, sehingga komputer dapat menentukan langkah
 * bermain dengan menaruh bidaknya pada cell tersebut. Langkah
 * yang diprioritaskan adalah langkah untuk menggagalkan pemain
 * untuk menyusun bidak-bidaknya. Jika tidak ada langkah untuk
 * menggagalkan yang berarti maka komputer juga mencoba mencari
 * cell untuk menyusun bidaknya. 
 * 
 * @author Salsabila Nadhifah
 * @version 13.12.2014
 * 
 */
package controller;
import model.Board;
import model.Cell;
import model.GomokuFrame;
import view.BoardComponent;
import view.CellComponent;


import java.util.Random;

public class ComputerPlayer
{

	public static final int DELTA_X[] = { -1, 1, -1, 1, 0, 0, -1, 1 };
	public static final int DELTA_Y[] = { -1, 1, 1, -1, 1, -1, 0, 0 };
	public static final int NUMBER_DIRECTION = 8;
	/*
	 * variabel profit[][] berguna untuk menyimpan keuntungan yang diperoleh
	 * dari masing-masing cell
	 */
	private static int profit[][];
	private static int rowMaxProfit;
	private static int colMaxProfit;
	/*
	 * variabel countProfitWhiteDone berguna untuk menandakan apakah proses
	 * perhitungan keuntungan dari cell yang berisi bidak putih atau bidak
	 * milih komputer sudah dilakukan atau belum
	 */
	private static boolean countProfitWhiteDone;

	/**
	 * 
	 * konstruktor kelas ComputerPlayer
	 * 
	 */
	ComputerPlayer()
	{
		profit = new int[Board.HEIGHT][Board.WIDTH];
		resetProfit();

		rowMaxProfit = 0;
		colMaxProfit = 0;

		countProfitWhiteDone = false;
	}

	/**
	 * 
	 * method ini berfungsi untuk mengembalikan nilai profit pada
	 * setiap cell menjadi nilai awal yaitu 0
	 * 
	 */
	public void resetProfit()
	{
		for (int i = 0; i < Board.HEIGHT; i++) {
			for (int j = 0; j < Board.WIDTH; j++) {
				profit[i][j] = 0;
			}
		}
	}
	
	/**
	 * 
	 * method ini digunakan untuk menghitung keuntungan setiap cell
	 * yang sekitarnya merupakan cell dengan bidak berwarna hitam.
	 * Semakin banyak bidak hitam di sekitar cell tersebut maka semakin
	 * besar keuntungannya untuk menaruh bidak putih pada cell tersebut
	 * untuk mengamankan bidak hitam tersebut agar tidak membentuk
	 * susunan menang.
	 * 
	 * @param cells
	 */
	public void countProfitBlack(CellComponent[][] cells)
	{
		resetProfit();
		rowMaxProfit = 0;
		colMaxProfit = 0;
		countProfitWhiteDone = false;

		for (int i = 0; i < Board.HEIGHT; i++) {
			for (int j = 0; j < Board.WIDTH; j++) {
				if (cells[i][j].getColor() == Cell.BLACK) {
					for (int k = 0; k < NUMBER_DIRECTION; k++) {
						int sum = 1;
						int row = i + DELTA_X[k];
						int col = j + DELTA_Y[k];
						while (GameController.stillOnBoard(row, col) && cells[row][col]
								.getColor() == Cell.BLACK) {
							sum++;
							row += DELTA_X[k];
							col += DELTA_Y[k];
						}
						if (GameController.stillOnBoard(row, col)) {
							profit[row][col] += sum;
						}
					}
				}
			}
		}
	}

	/**
	 * 
	 * method ini digunakan untuk mencari keuntungan terbesar dari semua cell.
	 * apabila keuntungan tidak sesuai dengan ekspetasi padahal permainan
	 * sudah tidak baru mulai maka hitung keuntungan posisi bidak putih untuk
	 * menyusun susunan menang.
	 * 
	 * @param cells
	 */
	public void searchMaxProfit(CellComponent[][] cells)
	{
		int max = -1;
		for (int i = 0; i < Board.HEIGHT; i++) {
			for (int j = 0; j < Board.WIDTH; j++) {
				if (profit[i][j] > max && cells[i][j].getColor() == Cell.EMPTY) {
					max = profit[i][j];
					rowMaxProfit = i;
					colMaxProfit = j;
				}
				/*
				 * jika besar keuntungan sama, maka lakukan random angka, jika dihasilkan
				 * angka 0 maka posisi cell tersebut diambil dan sebaliknya. Random dilakukan
				 * untuk menghasilkan kemungkinan lain dari cara langkah komputer 
				 */
				else if (profit[i][j] == max && cells[i][j].getColor() == Cell.EMPTY) {
					Random rand = new Random();
					int randomNumber = rand.nextInt(2);
					if (randomNumber == 0) {
						rowMaxProfit = i;
						colMaxProfit = j;
					}
				}
			}
		}
		if (max <= 3 && !countProfitWhiteDone && GameController.getNumStep() > 3) {
			countProfitWhite(cells);
			searchMaxProfit(cells);
		}
	}
	
	/**
	 * 
	 * method ini berfungsi untuk menghitung keuntungan setiap cell yang
	 * di sekitarnya terdapat bidak putih. Kemudian akan dicari keuntungan
	 * yang terbesar yang berarti susunan untuk menangnya yang membutuhkan
	 * langkahnya paling sedikit.
	 * 
	 * @param cells
	 */
	public void countProfitWhite(CellComponent[][] cells)
	{
		resetProfit();
		rowMaxProfit = 0;
		colMaxProfit = 0;
		countProfitWhiteDone = true;

		for (int i = 0; i < Board.HEIGHT; i++) {
			for (int j = 0; j < Board.WIDTH; j++) {
				if (cells[i][j].getColor() == Cell.WHITE) {
					for (int k = 0; k < NUMBER_DIRECTION; k++) {
						int sum = 1;
						int row = i + DELTA_X[k];
						int col = j + DELTA_Y[k];
						while (GameController.stillOnBoard(row, col) && cells[row][col]
								.getColor() == Cell.WHITE) {
							sum++;
							row += DELTA_X[k];
							col += DELTA_Y[k];
						}
						if (GameController.stillOnBoard(row, col)) {
							profit[row][col] += sum;
						}
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * method ini berguna untuk mencari cell terakhir yang perlu diisi
	 * untuk membuat susunan menang. Method ini perlu dipanggil terakhir
	 * agar komputer tidak salah langkah.
	 * 
	 * @param cells
	 */
	public void searchOneStepMore(CellComponent[][] cells)
	{
		resetProfit();

		for (int i = 0; i < Board.HEIGHT; i++) {
			for (int j = 0; j < Board.WIDTH; j++) {
				if (cells[i][j].getColor() == Cell.WHITE) {
					for (int k = 0; k < NUMBER_DIRECTION; k++) {
						int sum = 1;
						int row = i + DELTA_X[k];
						int col = j + DELTA_Y[k];
						while (GameController.stillOnBoard(row, col) && cells[row][col]
								.getColor() == Cell.WHITE) {
							sum++;
							row += DELTA_X[k];
							col += DELTA_Y[k];
						}
						if (GameController.stillOnBoard(row, col)) {
							if (sum == 4 && cells[row][col].getColor() == Cell.EMPTY) {
								rowMaxProfit = row;
								colMaxProfit = col;
								return;
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * method ini berfungsi untuk mempresentasikan cara berpikir komputer
	 * ketika bermain, yaitu dengan memanggil method-method untuk
	 * menghitung keuntungan.
	 * 
	 */
	public void play()
	{
		CellComponent[][] cells = BoardComponent.getCells();
		
		countProfitBlack(cells);
		searchMaxProfit(cells);
		searchOneStepMore(cells);
		
		cells[rowMaxProfit][colMaxProfit].setEnabled(false);
		cells[rowMaxProfit][colMaxProfit].setColor(Cell.WHITE);
		GomokuFrame.getBoardComponent().repaint();
		cells[rowMaxProfit][colMaxProfit].setEnabled(true);
	}
	
	public int getRow()
	{
		return rowMaxProfit;
	}
	
	public int getCol()
	{
		return colMaxProfit;
	}

}
