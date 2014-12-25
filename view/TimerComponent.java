/**
 * 
 * Kelas ini merupakan kelas timer permainan. Sehingga
 * dalam arena permainan ditampilkan timer permainan untuk
 * menghitung berapa lama permainan sudah berlangsung.
 * 
 * @author Salsabila Nadhifah
 * @version 13.12.2014
 * 
 */
package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class TimerComponent extends JPanel
{
	private static Timer timer;
	public static final int TENTH_SEC = 100;
	private static JLabel timeLabel;
	/*
	 * variabel millisecond berguna untuk menyimpan nilai dari milisekon
	 * waktu yang sedang berlangsung
	 */
	private static int millisecond;
	/*
	 * variabel second berguna untuk menyimpan nilai dari detik waktu yang
	 * sedang berlangsung
	 */
	private static int second;
	/*
	 * variabel minute berguna untuk menyimpan nilai dari menit waktu yang
	 * sedang berlangsung
	 */
	private static int minute;
	/*
	 * variabel secondString menyimpan nilai variabel second dalam bentuk String
	 */
	private static String secondString;
	/*
	 * variabel minuteString menyimpan nilai variabel minute dalam bentuk String
	 */
	private static String minuteString;

	/**
	 * 
	 * konstruktor kelas TimerComponent
	 * 
	 */
	public TimerComponent()
	{
		setPreferredSize(new Dimension(300, 300));
		setBackground(Color.BLACK);
		setLayout(new FlowLayout(FlowLayout.RIGHT));
		millisecond = 0;
		second = millisecond / TENTH_SEC;
		minute = 0;

		secondString = "" + second;
		minuteString = "" + minute;
		if (second < 10) {
			secondString = "0" + secondString;
		}
		if (minute < 10) {
			minuteString = "0" + minuteString;
		}

		timeLabel = new JLabel();
		timeLabel.setFont(new Font("SansSerif", Font.PLAIN, 80));
		timeLabel.setForeground(Color.WHITE);
		timeLabel.setText(minuteString + ":" + secondString);
		add(timeLabel);

		timer = new Timer(TENTH_SEC, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				millisecond++;
				second = millisecond / 10;
				if (second == 60) {
					minute++;
					millisecond = 0;
					second = 0;
				}
				secondString = "" + second;
				minuteString = "" + minute;
				if (second < 10) {
					secondString = "0" + secondString;
				}
				if (minute < 10) {
					minuteString = "0" + minuteString;
				}
				timeLabel.setText(minuteString + ":" + secondString);
			}
		});
	}
	
	/**
	 * getter timer
	 * 
	 * @return Timer timer
	 */
	public static Timer getTimer()
	{
		return timer;
	}
	
	/**
	 * 
	 * method ini berfungsi untuk mereset timer kembali
	 * ke angka nol
	 * 
	 */
	public static void resetTimer()
	{
		millisecond = 0;
        second = millisecond / 10;
        minute = 0;
        secondString = "" + second;
        minuteString = "" + minute;
        if (second < 10) {
          secondString = "0" + secondString;
        }
        if (minute < 10) {
          minuteString = "0" + minuteString;
        }
        timeLabel.setText(minuteString + ":" + secondString);
	}
}
