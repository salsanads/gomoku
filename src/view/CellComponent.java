/**
 * 
 * Kelas ini merupakan kelas untuk setiap cell pada papan.
 * Setiap cell pada papan memiliki informasi baris, kolom,
 * dan apakah cell tersebut merupakan salah satu cell penyusun
 * susunan menang. Cell juga dapat diatur apakah dalam
 * kondisi bisa atau tidak bisa diklik.
 * 
 * @author Salsabila Nadhifah
 * @version 13.12.2014
 * 
 */
package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JButton;

import model.Cell;

public class CellComponent extends JButton
{

	private int row;
	private int col;
	private Cell color;
	private boolean isCellOfWin;
	private boolean enableClick;

	/**
	 * 
	 * konstruktor kelas CellComponent
	 * 
	 * @param row
	 * 			baris cell yang dibentuk
	 * @param col
	 * 			kolom cell yang dibentuk
	 */
	public CellComponent(int row, int col)
	{
		this.row = row;
		this.col = col;
		this.color = Cell.EMPTY;
		this.isCellOfWin = false;
		this.enableClick = true;
		if ((row + col) % 2 == 0) {
			setBackground(Color.GRAY);
		} else {
			setBackground(Color.LIGHT_GRAY);
		}
		setOpaque(false);
		setContentAreaFilled(false);
		setBorderPainted(false);
	}

	/**
	 * 
	 * getter row pada cell
	 * 
	 * @return baris cell
	 */
	public int getRow()
	{
		return row;
	}
	
	/**
	 * 
	 * getter col pada cell
	 * 
	 * @return kolom cell
	 */
	public int getCol()
	{
		return col;
	}
	
	/**
	 * 
	 * setter color pada cell
	 * 
	 * @param color
	 * 			warna baru, yaitu warna bidak yang terletak pada cell
	 */
	public void setColor(Cell color)
	{
		this.color = color;
	}
	
	/**
	 * 
	 * getter color pada cell
	 * 
	 * @return warna bidak yang terletak pada cell
	 */
	public Cell getColor()
	{
		return color;
	}
	
	/**
	 * 
	 * setter isCellOfWin untuk menandakan bahwa cell
	 * merupakan salah satu penyusun susunan menang atau bukan
	 * 
	 * @param value
	 * 				nilai baru
	 */
	public void setIsCellOfWin(boolean value)
	{
		isCellOfWin = value;
	}
	
	/**
	 * 
	 * getter enableClick untuk mendapatkan informasi
	 * apakah cell dapat diklik atau tidak
	 * 
	 * @return true
	 * 			jika cell dapat diklik
	 * @return false
	 * 			jika cell tidak dapat diklik
	 */
	public boolean getEnableClick()
	{
		return enableClick;
	}
	
	/**
	 * 
	 * setter enableClick, untuk mengubah apakah cell
	 * dapat diklik atau tidak
	 * 
	 * @param value
	 * 			nilai baru untuk menandakan dapat atau tidak
	 * 			dapat diklik
	 */
	public void setEnableClick(boolean value)
	{
		enableClick = value;
	}
	
	/**								
	 * Method untuk menggambar bidak pada cell apabila cell
	 * tersebut dipilih
	 */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		
		/*
		 * set Anti Alias agar tepi gambar pada bidak terlihat halus
		*/
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		if (color == Cell.BLACK) {
			g2.setColor(Color.BLACK);
			g2.fillOval(4, 4, getWidth() - 8, getHeight() - 8);
		}
		else if (color == Cell.WHITE) {
			g2.setColor(Color.WHITE);
			g2.fillOval(4, 4, getWidth() - 8, getHeight() - 8);
		}
		/*
		 * jika cell merupakan bagian dari penyusun susunan menang
		 * maka cell digambar ulang serta diberikan garis tepi
		 * untuk menampilkan cell mana saja yang merupakan penyusun
		 * susunan menang
		 */
		if (isCellOfWin) {
			if (color == Cell.BLACK) {
				g2.setColor(Color.BLACK);
				g2.fillOval(4, 4, getWidth() - 8, getHeight() - 8);
			}
			else if (color == Cell.WHITE) {
				g2.setColor(Color.WHITE);
				g2.fillOval(4, 4, getWidth() - 8, getHeight() - 8);
			}
			g2.setStroke(new BasicStroke(3));
			g2.setColor(Color.RED);
			g2.drawOval(4, 4, getWidth() - 8, getHeight() - 8);
		}
	}
}
