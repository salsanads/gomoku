/**
 * 
 * Kelas ini merepresentasikan komponen dari papan. Sebuah
 * papan memiliki cell-cell sebanyak tinggi x lebar papan.
 * 
 * @author Salsabila Nadhifah
 * @version 13.12.2014
 * 
 */
package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import model.Board;
import model.GomokuFrame;
import controller.GameController;

public class BoardComponent extends JPanel
{	

	private static CellComponent[][] cells;
	
	/**
	 * 
	 * method paintComponent() ini berfungsi untuk menampilkan gambar
	 * papan.
	 * 
	 */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		BufferedImage boardBackground = null;
		try {
			boardBackground = ImageIO.read(new File("boardBackground.png"));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		g.drawImage(boardBackground, 0, 0, 600, 600, null);
	}
	
	/**
	 * 
	 * konstruktor kelas BoardComponent
	 * 
	 */
	public BoardComponent()
	{
		setPreferredSize(new Dimension(500,500));
		setLayout(new GridLayout(Board.HEIGHT, Board.WIDTH));
		
		cells = new CellComponent[Board.HEIGHT][Board.WIDTH];
		
		for (int i = 0; i < Board.HEIGHT; i++) {
			for (int j = 0; j < Board.WIDTH; j++) {
				cells[i][j] = new CellComponent(i, j);
				cells[i][j].addActionListener(new GameController(cells[i][j]));
				add(cells[i][j]);
			}
		}
	}
	
	public static CellComponent[][] getCells()
	{
		return cells;
	}

	/**
	 * 
	 * method ini berguna untuk menghapus komponen-komponen yang
	 * berada pada papan lalu membentuknya kembali
	 * 
	 */
	public void clearBoard()
	{
		removeAll();
		updateUI();
		setLayout(new GridLayout(Board.HEIGHT, Board.WIDTH));
		cells = new CellComponent[Board.HEIGHT][Board.WIDTH];
		
		for (int i = 0; i < Board.HEIGHT; i++) {
			for (int j = 0; j < Board.WIDTH; j++) {
				cells[i][j] = new CellComponent(i, j);
				cells[i][j].addActionListener(new GameController(cells[i][j]));
				add(cells[i][j]);
			}
		}
	}
	
}
