/**
 * 
 * Kelas ini berfungsi untuk menampilkan 5 score permainan
 * sebelumnya
 * 
 * @author Salsabila Nadhifah
 * @version 13.12.2014
 * 
 */
package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScoreComponent extends JPanel
{
	
	private JPanel highScoreContainer;
	private JLabel title;
	private static JLabel[][] tabel;
	public static final int NUM_ROW = 6;
	public static final int NUM_COL = 2;

	/**
	 * 
	 * konstruktor kelas ScoreComponent
	 * 
	 */
	public ScoreComponent()
	{
		
		highScoreContainer = new JPanel();
		highScoreContainer.setPreferredSize(new Dimension(300,300));
		highScoreContainer.setLayout(new GridLayout(6, 2));
		add(highScoreContainer);
		setBackground(Color.BLACK);
		highScoreContainer.setBackground(Color.BLACK);
		
		tabel = new JLabel[NUM_ROW][NUM_COL];
		for (int i = 0; i < NUM_ROW; i++) {
			for (int j = 0; j < NUM_COL; j++) {
				tabel[i][j] = new JLabel();
				tabel[i][j].setFont(new Font("SansSerif", Font.PLAIN, 18));
				tabel[i][j].setForeground(Color.WHITE);
				highScoreContainer.add(tabel[i][j]);
			}
			tabel[i][0].setText("anonymous");
			tabel[i][1].setText("0");
		}
		tabel[0][0].setText("NAME");
		tabel[0][1].setText("STEP");
	}
	
	/**
	 * 
	 * method ini berfungsi untuk mengupdate score setiap permainan
	 * baru selesai dimainkan
	 * 
	 * @param name
	 * 			nama pemain yang menang
	 * @param step
	 * 			jumlah langkah yang dibutuhkan untuk menang
	 */
	public static void updateScore(String name, int step)
	{
		if (name == null) return;
		for (int i = NUM_ROW - 1; i >= 2; i--) {
			tabel[i][0].setText(tabel[i-1][0].getText());
			tabel[i][1].setText(tabel[i-1][1].getText());
		}
		tabel[1][0].setText(name.toLowerCase());
		tabel[1][1].setText("" + step);
	}
	
}
