/**
 * 
 * Kelas ini merupakan kelas panel yang berisi JLabel untuk
 * menginformasikan kondisi yang sedang terjadi pada arena
 * permainan.
 * 
 * @author Salsabila Nadhifah
 * @version 13.12.2014
 * 
 */
package view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.GomokuFrame;

public class InfoComponent extends JPanel
{

	private JLabel currentPlayer;
	
	/**
	 * 
	 * konstruktor kelas InfoComponent
	 * 
	 */
	public InfoComponent()
	{
		currentPlayer = new JLabel("turn: " + GomokuFrame.getFirstName()
				+ " | current color: black | total stone: 0");
		currentPlayer.setFont(new Font("SansSerif", Font.PLAIN, 20));
		currentPlayer.setForeground(Color.WHITE);
		add(currentPlayer);
		setBackground(Color.BLACK);
	}

	public JLabel getCurrentPlayer()
	{
		return currentPlayer;
	}

	/**
	 *
	 * method ini digunakan untuk mensetting info kembali ke
	 * nilai semua sebelum permainan dimainkan
	 *
	 */
	public void clearInfo()
	{
		currentPlayer.setText("turn: " + GomokuFrame.getFirstName()
				+ " | current color: black | total stone: 0");
	}
}
