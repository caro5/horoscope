package gui;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import java.applet.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HoroView extends Applet{
	public static JFrame theFrame;
	public static int frameW = 800;
	public static int frameH = 800;
	public static JPanel panel;
	public static JPanel cosmoPanel;
	public static JPanel dailyPanel;
	public static JPanel huffPanel;
	public static JTextArea cosmo;
	public static JTextArea daily;
	public static JTextArea huff;
	public static String sign = "";
	public final static Object lock = new Object();
	public static String[] allSigns = {"aries", "taurus", "gemini", "cancer", "leo", "virgo", "libra", "scorpio", "sagittarius", "capricorn", "aquarius", "pisces"};

	private static void createFrame() {
		
		Font theFont = new Font("Open Sans", Font.PLAIN, 16);
		Font labelFont = new Font("Open Sans", Font.PLAIN, 18);
		theFrame = new JFrame();
		theFrame.setLayout(new GridLayout(4,1));
		theFrame.setBounds(0, 0, frameW, frameH);
		theFrame.setVisible(true);
		theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel = new JPanel();
		panel.setVisible(true);
		cosmoPanel = new JPanel();
		cosmoPanel.setVisible(true);
		dailyPanel = new JPanel();
		dailyPanel.setVisible(true);
		huffPanel = new JPanel();
		huffPanel.setVisible(true);

		cosmo = new JTextArea();
		cosmo.setBounds(100, 500, 500, 300);
		cosmo.setLineWrap(true);
		cosmo.setWrapStyleWord(true);
		cosmo.setFont(theFont);
		cosmo.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
		Color colo = new Color(0x404041);
		cosmo.setBackground(colo);
		Color backC = Color.WHITE;
		cosmo.setForeground(backC);
		
		daily = new JTextArea();
		daily.setBounds(300, 300, 500, 300);
		daily.setLineWrap(true);
		daily.setWrapStyleWord(true);
		daily.setFont(theFont);
		daily.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
		daily.setBackground(colo);
		daily.setForeground(backC);
		
		huff = new JTextArea();
		huff.setBounds(500, 300, 500, 300);
		huff.setLineWrap(true);
		huff.setWrapStyleWord(true);
		huff.setFont(theFont);
		huff.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
		huff.setBackground(colo);
		huff.setForeground(backC);

		JComboBox signList = new JComboBox(allSigns);
		signList.setFont(theFont);
		signList.setPreferredSize(new Dimension(120, 40));
		JButton submit = new JButton("submit");
		submit.setFont(theFont);
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				synchronized(lock) {
					sign = signList.getSelectedItem().toString();
					lock.notifyAll();
					loadText();
				}
			}
		});

		panel.add(signList);
		panel.add(submit);
		theFrame.add(panel);
		theFrame.add(cosmoPanel);
		theFrame.add(dailyPanel);
		theFrame.add(huffPanel);

		
		JLabel cosmoLabel = new JLabel("Cosmopolitan UK");
		cosmoLabel.setFont(labelFont);
		Color nameC = new Color(0xD2E8F8);
		cosmoLabel.setForeground(nameC);
		JLabel dailyLabel = new JLabel("NY Daily Post     ");
		dailyLabel.setFont(labelFont);
		dailyLabel.setForeground(nameC);
		JLabel huffLabel = new JLabel("Huffington Post  ");
		huffLabel.setFont(labelFont);
		huffLabel.setForeground(nameC);
		cosmoPanel.add(cosmoLabel);
		cosmoPanel.add(cosmo);
		dailyPanel.add(dailyLabel);
		dailyPanel.add(daily);
		huffPanel.add(huffLabel);
		huffPanel.add(huff);
		Color c = new Color(0x404041);
		panel.setBackground(c);
		cosmoPanel.setBackground(c);
		dailyPanel.setBackground(c);
		huffPanel.setBackground(c);
		
		
		theFrame.add(cosmoPanel);
		theFrame.add(dailyPanel);
		theFrame.add(huffPanel);
	}

	/**
	 * creates a submit button that takes in the sign and sets the String sign
	 * to the text.
	 */
	public void enterSign() {
		
		JButton submit = new JButton("enter sign");
		final TextField signField = new TextField(1);
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				synchronized(lock) {
					sign = signField.getText();
					lock.notifyAll();
				}
			}
		});
		panel.add(signField);
		panel.add(submit);
		theFrame.add(panel);
		theFrame.setVisible(true);
	}

	public static void loadText() {
		Scraper sc = new Scraper();
		sc.siteUrl.clear();
		sc.domainNameMaker(sign);
		
		cosmo.setText(sc.crawlCosmo());
		daily.setText(sc.crawlDailyNews());
		huff.setText(sc.crawlHuffPost());
		
		
	
	}

	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				HoroView frame = new HoroView();
				frame.createFrame();
			}
		});
	}
}
