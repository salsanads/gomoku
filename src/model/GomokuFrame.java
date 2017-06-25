/**
 * 
 * Kelas ini merupakan kelas frame serta berisi main method.
 * Kelas ini berisi segala komponen yang ditampilkan pada frame. 
 * 
 * @author Salsabila Nadhifah
 * @version 13.12.2014
 * 
 */

package model;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import view.BoardComponent;
import view.ButtonComponent;
import view.InfoComponent;
import view.ScoreComponent;
import view.TimerComponent;
import view.TitleComponent;
import controller.GameController;

public class GomokuFrame extends JFrame implements ActionListener
{
	private static GomokuFrame gomokuFrame;

	private static JPanel menu;
	private static TitleComponent title;
	private static JButton play2PlayerButton;
	private static JButton playWithComputerButton;
	private static JButton exitButton;
	private static JPanel panel;
	private static BoardComponent boardComponent;
	private static InfoComponent infoComponent;
	private static ScoreComponent scoreComponent;
	private static ButtonComponent buttonComponent;
	private static TimerComponent timerComponent;
	private static String firstName;
	private static String secondName;
	
	/**
	 * konstruktor kelas GomokuFrame
	 */
	public GomokuFrame()
	{

		setTitle("Gomoku");
		setUndecorated(true);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		getContentPane().setBackground(Color.BLACK);
		
		/*
		 * membuat tampilan menu utama
		 */
		constructMainDisplay();
		
		add(menu, BorderLayout.CENTER);
		add(panel, BorderLayout.SOUTH);
		
		setVisible(true);
	}
	
	/**
	 * 
	 * method ini membuat tampilan menu utama pada game Gomoku
	 * 
	 */
	public void constructMainDisplay()
	{
		Dimension size = new Dimension(800, 600);
		
		/* 
		 * variabel menu merupakan panel untuk menyimpan panel container yang
		 * berisi button-button pada menu utama
		 */
		menu = new JPanel();
		menu.setPreferredSize(size);
		menu.setLayout(null);
		menu.setBackground(Color.BLACK);
		
		/*
		 * variabel container merupakan panel yang menampung button-button
		 * pada menu utama
		 */
		JPanel container = new JPanel();
		container.setLayout(new GridLayout(4, 1));
		container.setBackground(Color.BLACK);
		menu.add(container);
		container.setPreferredSize(size);
		
		/*
		 * variabel title merupakan panel yang berisi judul game 
		 */
		title = new TitleComponent();
		
		/*
		 * variable player2PlayerButton merupakan button yang dipilih
		 * jika pemain ingin bermain berdua
		 */
		play2PlayerButton = new JButton("Play 2 Players");
		play2PlayerButton.setForeground(Color.WHITE);
		play2PlayerButton.setFont(new Font("SansSerif", Font.PLAIN, 48));
		play2PlayerButton.setOpaque(false);
		play2PlayerButton.setContentAreaFilled(false);
		play2PlayerButton.addActionListener(this);
		play2PlayerButton.setFocusPainted(false);
		
		/*
		 * variabel playWithComputerButton merupakan button yang dipilih
		 * jika pemain ingin bermain melawan komputer
		 */
		playWithComputerButton = new JButton("Play vs Computer");
		playWithComputerButton.setForeground(Color.WHITE);
		playWithComputerButton.setFont(new Font("SansSerif", Font.PLAIN, 48));
		playWithComputerButton.setOpaque(false);
		playWithComputerButton.setContentAreaFilled(false);
		playWithComputerButton.addActionListener(this);
		playWithComputerButton.setFocusPainted(false);
		
		/*
		 * variabel exitButton merupakan button yang dipilih jika pemain
		 * ingin keluar dari permainan
		 */
		exitButton = new JButton("Exit");
		exitButton.setForeground(Color.WHITE);
		exitButton.setFont(new Font("SansSerif", Font.PLAIN, 48));
		exitButton.setOpaque(false);
		exitButton.setContentAreaFilled(false);
		exitButton.addActionListener(this);
		exitButton.setFocusPainted(false);

		container.add(title);
		container.add(play2PlayerButton);
		container.add(playWithComputerButton);
		container.add(exitButton);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		container.setBounds((screenSize.width-size.width)/2,
				(screenSize.height-size.height)/2, size.width, size.height);
		
		/*
		 * variabel panel merupakan panel yang menyimpan JLabel tentang informasi
		 * pembuat game
		 */
		panel = new JPanel();
		panel.setLayout(new GridLayout(2, 1));
		panel.setBackground(Color.BLACK);
		JLabel about1 = new JLabel("Created by Salsabila Nadhifah 2014");
		about1.setForeground(Color.WHITE);
		about1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel.add(about1);
		JLabel about2 = new JLabel("Made with <3");
		about2.setForeground(Color.WHITE);
		about2.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel.add(about2);
	}
	
	/**
	 * 
	 * method ini merupakan actionPerformed() untuk button-button
	 * yang berada pada kelas GomokuFrame, yaitu button-button yang
	 * berada pada menu utama yang terletak pada JPanel container
	 * 
	 */
	public void actionPerformed(ActionEvent e)
	{
		/*
		 * jika exitButton dipilih maka muncul dialog untuk konfirmasi keluar
		 */
		if (e.getSource() == exitButton) {
			int optionValue = JOptionPane.showConfirmDialog(null,
					"Are you sure want to exit?", "Confirm Exit",
					JOptionPane.OK_CANCEL_OPTION);
			if (optionValue == 0) {
				System.exit(0);
			}
		}
		/*
		 * selain tombol exitButton yang dipilih maka komponen-komponen pada
		 * frame dihapus lalu dibuat komponen-komponen pada arena permainan
		 * kemudian ditampilkan pada frame
		 */
		else {
			remove(menu);
			remove(panel);

			Dimension boardSize = new Dimension(600, 600);

			panel = new JPanel();
			add(panel, BorderLayout.CENTER);
			panel.setPreferredSize(boardSize);
			panel.setBackground(Color.BLACK);
			panel.setLayout(null);
			
			boardComponent = new BoardComponent();
			panel.add(boardComponent);
			boardComponent.setPreferredSize(boardSize);
			
			firstName = firstName == null ? "first player" : firstName;
			secondName = secondName == null ? "second player" : secondName;
			
			infoComponent = new InfoComponent();
			scoreComponent = new ScoreComponent();
			buttonComponent = new ButtonComponent();
			timerComponent = new TimerComponent();
			
			add(infoComponent, BorderLayout.NORTH);
			add(scoreComponent, BorderLayout.EAST);
			add(buttonComponent, BorderLayout.SOUTH);
			add(timerComponent, BorderLayout.WEST);
			
			revalidate();
			boardComponent.setBounds((panel.getWidth()-boardSize.width)/2,
					(panel.getHeight()-boardSize.height)/2, boardSize.width,
					boardSize.height);
			revalidate();
			
			if (e.getSource() == play2PlayerButton) {
				GameController.setIsPlayWithComputer(false);
			}
			else if (e.getSource() == playWithComputerButton) {
				GameController.setIsPlayWithComputer(true);
			}
			welcomeScreen();
		}
	}

	/**
	 * 
	 * method main
	 * 
	 * @param args
	 */
	
	public static void main(String[] args)
	{
		gomokuFrame = new GomokuFrame();
	}

	public static BoardComponent getBoardComponent()
	{
		return boardComponent;
	}

	public static InfoComponent getInfoComponent()
	{
		return infoComponent;
	}

	public static String getFirstName()
	{
		return firstName;
	}

	public static String getSecondName()
	{
		return secondName;
	}

	/**
	 * 
	 * method ini dipanggil setelah pemain memilih mode bermain dan tepat sebelum
	 * permainan dimulai. Method ini dialog tempat pemain untuk memasukkan nama serta
	 * mengatur kapan timer permainan berjalan 
	 * 
	 */
	public static void welcomeScreen()
	{	
		firstName = JOptionPane.showInputDialog(null,
				"Welcome to Gomoku Game!", "Enter First Player Name");
		firstName = firstName == null ? "first player" : firstName;
		firstName = firstName.equals("Enter First Player Name") ? "first player"
				: firstName;

		infoComponent.clearInfo();

		/*
		 * jika pemain memilih bermain melawan komputer, maka setelah dia, yaitu
		 * pemain pertama memasukkan nama maka timer permainan dimulai
		 */
		if (GameController.getIsPlayWithComputer()) {
			TimerComponent.getTimer().start();
		}
		
		/*
		 * jika tidak bermain melawan komputer, maka pemain kedua memasukkan namanya
		 * dan setelah namanya dimasukkan timer permainan dimulai
		 */
		if (!GameController.getIsPlayWithComputer()) {
			secondName = JOptionPane.showInputDialog(null,
					"Welcome to Gomoku Game!", "Enter Second Player Name");
			secondName = secondName == null ? "second player" : secondName;
			secondName = secondName.equals("Enter Second Player Name") ? "second player"
					: secondName;
			TimerComponent.getTimer().start();
		}
	}
	
	/**
	 * 
	 * method ini dipanggil ketika pemain berada dalam arena permainan dan
	 * ingin kembali ke menu utama. method ini menghapus komponen-komponen yang
	 * berada pada frame ketika permainan berlangsung dan mengubah tampilan
	 * menjadi menu utama dengan memanggil method constructMainDisplay() untuk
	 * membuat komponen pada menu utama
	 * 
	 */
	public void mainMenu()
	{
		gomokuFrame.remove(panel);
		gomokuFrame.remove(infoComponent);
		gomokuFrame.remove(scoreComponent);
		gomokuFrame.remove(buttonComponent);
		gomokuFrame.remove(timerComponent);

		constructMainDisplay();

		gomokuFrame.add(menu, BorderLayout.CENTER);
		gomokuFrame.add(panel, BorderLayout.SOUTH);
		
		gomokuFrame.revalidate();
		GameController.restartGame();
		
	}
	
	public static GomokuFrame getGomokuFrame()
	{
		return gomokuFrame;
	}
}
