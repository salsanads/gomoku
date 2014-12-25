/**
 * 
 * Kelas ini merupakan kelas panel yang berisi komponen
 * button-button yang ditampilkan pada saat permainan berlangsung.
 * 
 * @author Salsabila Nadhifah
 * @version 13.12.2014
 * 
 */
package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.GomokuFrame;
import controller.GameController;

public class ButtonComponent extends JPanel implements ActionListener
{

	private JButton restartButton;
	private JButton quitButton;
	private JButton mainMenuButton;
	
	/**
	 * 
	 * konstruktor kelas ButtonComponent
	 * 
	 */
	public ButtonComponent()
	{
		
		setBackground(Color.BLACK);
		
		restartButton = new JButton("new game");
		add(restartButton);
		restartButton.addActionListener(this);
		
		mainMenuButton = new JButton("main menu");
		add(mainMenuButton);
		mainMenuButton.addActionListener(this);
		
		quitButton = new JButton("exit");
		add(quitButton);
		quitButton.addActionListener(this);
	}
	
	/**
	 * 
	 * method ini berisi perintah yang akan dilakukan apabila
	 * button-button pada kelas ini diklik.
	 * 
	 */
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == restartButton) {
			int optionValue = JOptionPane.showConfirmDialog(null, "Are you sure want to start new game?", "Confirm New Game", JOptionPane.OK_CANCEL_OPTION);
			if (optionValue == 0) {
				GameController.restartGame();
				TimerComponent.getTimer().start();
			}
		}
		else if (e.getSource() == mainMenuButton) {
			GomokuFrame.getGomokuFrame().mainMenu();
		}
		else if (e.getSource() == quitButton) {
			int optionValue = JOptionPane.showConfirmDialog(null, "Are you sure want to exit?", "Confirm Exit", JOptionPane.OK_CANCEL_OPTION);
			if (optionValue == 0) {
				System.exit(0);
			}
		}
	}
	
}
