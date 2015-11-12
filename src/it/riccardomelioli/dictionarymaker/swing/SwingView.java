package it.riccardomelioli.dictionarymaker.swing;


import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.BoxLayout;

import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;

import it.riccardomelioli.dictionarymaker.controller.App;
import it.riccardomelioli.dictionarymaker.view.View;

import javax.swing.border.LineBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTextField;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;

import java.awt.Font;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SwingView implements View{
	protected App app;	// comunicazione da vista a controller
	private JFrame frmDictionarymaker;
	private JTextField textFieldKeyLength;
	private JTextField txtNumberOfKey;
	private JTextArea txtareaTextBoard; 			// per permettere la scrittora del controller durante la generate
	private JTextField textFieldPrefix;
	private JTextField textFieldSuffix;
	private JTextField txtM;
	private ImageFrame frmImgASCII;
	private JButton btnGenerateTXT;
	private JProgressBar progressBar;
	private JCheckBox chckbx_09;
	private JCheckBox chckbx_AZ;
	private JCheckBox chckbx_az;
	private JCheckBox chckbxSpecialCharacter;
	private JCheckBox chckbxKeyLowerThanLength;
	/**
	 * Create the application.
	 */
	public SwingView(App appParam) {
		this.app = appParam;
		initialize();
	}
	
	public void setTxtareaTextBoard(String c){
		txtareaTextBoard.setText(txtareaTextBoard.getText() + "\n" + c);
		
	}
	
	public void refreshTxtNumberIfKey(){
		int keyLength = Integer.parseInt(textFieldKeyLength.getText());
	txtNumberOfKey.setText(Long.toString(app.numberOfKey(keyLength,
			chckbx_09.isSelected(),
			chckbx_az.isSelected(),
			chckbx_AZ.isSelected(),
			chckbxSpecialCharacter.isSelected(),
			chckbxKeyLowerThanLength.isSelected()
	)));
	}
	
	@Override
	public void updateProgressBar(int perc) {
		this.progressBar.setValue(perc);
	}

	@Override
	public void enableGenerateButton(boolean val) {
		btnGenerateTXT.setEnabled(val);
	}
	public void initialize() {
		frmDictionarymaker = new JFrame();
		frmDictionarymaker.setTitle("Dictionary Maker");
		frmDictionarymaker.setBounds(100, 100, 690, 524);
		frmDictionarymaker.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setForeground(Color.DARK_GRAY);
		frmDictionarymaker.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(3, 1, 0, 0));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.add(panel_4);
		panel_4.setLayout(new GridLayout(4, 0, 0, 0));
		
		chckbx_09 = new JCheckBox("Character 0-9 (48-57)");
		chckbx_09.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				refreshTxtNumberIfKey();
			}
		});
		panel_4.add(chckbx_09);
		
		chckbx_AZ = new JCheckBox("Character A-Z (65-90)");
		chckbx_AZ.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				refreshTxtNumberIfKey();
			}
		});
		panel_4.add(chckbx_AZ);
		
		chckbx_az = new JCheckBox("Character a-z (97-122)");
		chckbx_az.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				refreshTxtNumberIfKey();
			}
		});
		panel_4.add(chckbx_az);
		
		chckbxSpecialCharacter = new JCheckBox("Character Special (21-47 _ 58-64 _ 91-96 _ 123-126)");
		chckbxSpecialCharacter.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				refreshTxtNumberIfKey();
			}
		});
		panel_4.add(chckbxSpecialCharacter);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.add(panel_3);
		panel_3.setLayout(new GridLayout(0, 1, 5, 5));
		
		// Frame Tabella ASCII
		JButton btnASCIItable = new JButton("Show ASCII table");
		btnASCIItable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frmImgASCII = new ImageFrame("/ASCIITable.gif");
				frmImgASCII.setVisible(true);
			}
		});
		panel_3.add(btnASCIItable);
	
			
		JLabel lblNumberOfKeys = new JLabel("Number of Keys");
		lblNumberOfKeys.setHorizontalAlignment(SwingConstants.CENTER);
		panel_3.add(lblNumberOfKeys);
		
		txtNumberOfKey = new JTextField();
		txtNumberOfKey.setForeground(Color.GREEN);
		txtNumberOfKey.setBackground(Color.BLACK);
		txtNumberOfKey.setEditable(false);
		txtNumberOfKey.setText("0");
		txtNumberOfKey.setHorizontalAlignment(SwingConstants.CENTER);
		panel_3.add(txtNumberOfKey);
		txtNumberOfKey.setColumns(10);
		
		
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.add(panel_5);
		panel_5.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel panel_7 = new JPanel();
		panel_5.add(panel_7);
		panel_7.setLayout(new GridLayout(2, 1, 0, 5));
		
		JPanel panel_10 = new JPanel();
		panel_7.add(panel_10);
		panel_10.setLayout(new GridLayout(2, 0, 0, 0));
		
		JLabel lblPrefix = new JLabel("Prefix");
		lblPrefix.setHorizontalAlignment(SwingConstants.CENTER);
		panel_10.add(lblPrefix);
		
		textFieldPrefix = new JTextField();
		panel_10.add(textFieldPrefix);
		textFieldPrefix.setColumns(10);
		
		JLabel lblSuffix = new JLabel("Suffix");
		lblSuffix.setHorizontalAlignment(SwingConstants.CENTER);
		panel_10.add(lblSuffix);
		
		textFieldSuffix = new JTextField();
		panel_10.add(textFieldSuffix);
		textFieldSuffix.setColumns(10);
		
		JPanel panel_8 = new JPanel();
		panel_7.add(panel_8);
		panel_8.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_8.setLayout(new GridLayout(3, 3, 0, 0));
		
		JLabel lblKeyLength = new JLabel("Key Length");
		panel_8.add(lblKeyLength);
		lblKeyLength.setHorizontalAlignment(SwingConstants.CENTER);
		
		chckbxKeyLowerThanLength = new JCheckBox("Key Lower Than Length");
		panel_8.add(chckbxKeyLowerThanLength);
		chckbxKeyLowerThanLength.setHorizontalAlignment(SwingConstants.CENTER);
		chckbxKeyLowerThanLength.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				refreshTxtNumberIfKey();
			}
		});
		
		textFieldKeyLength = new JTextField();
		textFieldKeyLength.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				refreshTxtNumberIfKey();
			}
		});
		
		
		panel_8.add(textFieldKeyLength);
		textFieldKeyLength.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldKeyLength.setText("4");
		textFieldKeyLength.setColumns(10);
		
		JPanel panel_6 = new JPanel();
		panel_5.add(panel_6);
		panel_6.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel_11 = new JPanel();
		panel_6.add(panel_11);
		panel_11.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel lblNewLabel = new JLabel("Size of TXT file");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel_11.add(lblNewLabel);
		
		
		txtM = new JTextField();
		txtM.setBackground(Color.BLACK);
		txtM.setForeground(Color.GREEN);
		txtM.setHorizontalAlignment(SwingConstants.CENTER);
		txtM.setEditable(false);
		txtM.setText("0 MB");
		panel_11.add(txtM);
		txtM.setColumns(10);
		
		JPanel panel_9 = new JPanel();
		panel_9.setBackground(Color.WHITE);
		panel_9.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_6.add(panel_9);
		panel_9.setLayout(new GridLayout(0, 2, 0, 0));
		
		progressBar = new JProgressBar();
		panel_9.add(progressBar);
		progressBar.setFont(new Font("Tahoma", Font.BOLD, 23));
		progressBar.setBackground(Color.BLACK);
		progressBar.setForeground(new Color(0, 100, 0));
		progressBar.setStringPainted(true);
		progressBar.setValue(0);
		progressBar.setMaximum(100);
		
		btnGenerateTXT = new JButton("Generate TXT");
		panel_9.add(btnGenerateTXT);
		btnGenerateTXT.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int keyLength = Integer.parseInt(textFieldKeyLength.getText());
				app.directTXT(keyLength,
						chckbx_09.isSelected(),
						chckbx_az.isSelected(),
						chckbx_AZ.isSelected(),
						chckbxSpecialCharacter.isSelected(),
						chckbxKeyLowerThanLength.isSelected()
				);
			}
		});
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		txtareaTextBoard = new JTextArea();
		txtareaTextBoard.setEditable(false);
		txtareaTextBoard.setBackground(Color.BLACK);
		txtareaTextBoard.setForeground(Color.GREEN);
		

		JScrollPane scrollPane = new JScrollPane(txtareaTextBoard);
		scrollPane.setWheelScrollingEnabled(true);
		panel_2.add(scrollPane);
		
		JLabel lblNewLabel_4 = new JLabel("TEXT BOARD");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		panel_2.add(lblNewLabel_4, BorderLayout.NORTH);
		
		frmDictionarymaker.setVisible(true);
	}
}
