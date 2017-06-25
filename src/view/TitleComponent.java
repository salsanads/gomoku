/**
 * 
 * Kelas ini merupakan kelas yang befungsi untuk membuat
 * dan memodifikasi judul game.
 * 
 * @author Salsabila Nadhifah
 * @version 13.12.2014
 * 
 */
package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class TitleComponent extends JPanel
{

	/**
	 * 
	 * konstruktor kelas TitleComponent()
	 * 
	 */
	public TitleComponent()
	{
		setBackground(Color.BLACK);
	}
	
	/**
	 * 
	 * method paintComponent() ini berfungsi untuk menggambar
	 * gambar untuk judul game yang berasal dari file berformat
	 * .png
	 * 
	 */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		BufferedImage gomokuTitle = null;
		try {
			gomokuTitle = ImageIO.read(new File("gomokuTitle.png"));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		g.drawImage(gomokuTitle, 0, 0, 800, 150, null);
	}
	
}
