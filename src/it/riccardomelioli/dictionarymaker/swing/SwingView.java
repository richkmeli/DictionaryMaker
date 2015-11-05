package it.riccardomelioli.dictionarymaker.swing;


import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import javax.swing.JTextPane;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;

import it.riccardomelioli.dictionarymaker.App;
import it.riccardomelioli.dictionarymaker.view.View;

import javax.swing.border.LineBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JTextField;
import javax.swing.JScrollBar;
import javax.swing.JProgressBar;
import java.awt.Font;
import java.awt.event.AdjustmentListener;
import java.awt.event.AdjustmentEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.DropMode;

public class SwingView implements View{
	protected App app;	// comunicazione da vista a controller
	private JFrame frmDictionarymaker;
	private JTextField textFieldKeyLength;
	private JTextField textField_1;
	private JTextArea txtareaTextBoard; 			// per permettere la scrittora del controller durante la generate

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
	
	

	/**
	 * Initialize the contents of the frame.
	 */
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
		
		JCheckBox chckbx_09 = new JCheckBox("Character 0-9 (48-57)");
		chckbx_09.setSelected(true);
		panel_4.add(chckbx_09);
		
		JCheckBox chckbx_AZ = new JCheckBox("Character A-Z (65-90)");
		panel_4.add(chckbx_AZ);
		
		JCheckBox chckbx_az = new JCheckBox("Character a-z (97-122)");
		panel_4.add(chckbx_az);
		
		JCheckBox chckbxSpecialCharacter = new JCheckBox("Character Special (21-47 _ 58-64 _ 91-96 _ 123-126)");
		panel_4.add(chckbxSpecialCharacter);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.add(panel_3);
		panel_3.setLayout(new GridLayout(0, 1, 10, 10));
		
		JButton btnASCIItable = new JButton("Show ASCII table");
		panel_3.add(btnASCIItable);
		btnASCIItable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		JLabel lblNumberOfCombination = new JLabel("Number of Combination");
		lblNumberOfCombination.setHorizontalAlignment(SwingConstants.CENTER);
		panel_3.add(lblNumberOfCombination);
		
		textField_1 = new JTextField();
		textField_1.setForeground(Color.GREEN);
		textField_1.setBackground(Color.BLACK);
		textField_1.setEditable(false);
		textField_1.setText("0");
		textField_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_3.add(textField_1);
		textField_1.setColumns(10);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.add(panel_5);
		panel_5.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel panel_7 = new JPanel();
		panel_5.add(panel_7);
		panel_7.setLayout(new GridLayout(3, 2, 0, 5));
		
		JPanel panel_6 = new JPanel();
		panel_5.add(panel_6);
		panel_6.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel_8 = new JPanel();
		panel_8.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_6.add(panel_8);
		panel_8.setLayout(new GridLayout(3, 1, 0, 0));
		
		JLabel lblKeyLength = new JLabel("Key Length");
		panel_8.add(lblKeyLength);
		lblKeyLength.setHorizontalAlignment(SwingConstants.CENTER);
		
		JCheckBox chckbxKeyLowerThanLength = new JCheckBox("Key Lower Than Length");
		panel_8.add(chckbxKeyLowerThanLength);
		chckbxKeyLowerThanLength.setHorizontalAlignment(SwingConstants.CENTER);
		
		textFieldKeyLength = new JTextField();
		panel_8.add(textFieldKeyLength);
		textFieldKeyLength.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldKeyLength.setText("4");
		textFieldKeyLength.setColumns(10);
		
		JPanel panel_9 = new JPanel();
		panel_9.setBackground(Color.WHITE);
		panel_9.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_6.add(panel_9);
		panel_9.setLayout(new GridLayout(0, 2, 10, 10));
		
		JProgressBar progressBar = new JProgressBar();
		panel_9.add(progressBar);
		progressBar.setFont(new Font("Tahoma", Font.BOLD, 23));
		progressBar.setBackground(Color.BLACK);
		progressBar.setForeground(new Color(0, 100, 0));
		progressBar.setStringPainted(true);
		progressBar.setValue(65);
		
		JButton btnGenerateTXT = new JButton("Generate TXT");
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
		panel_2.add(txtareaTextBoard);
		
		JLabel lblNewLabel_4 = new JLabel("TEXT BOARD");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		panel_2.add(lblNewLabel_4, BorderLayout.NORTH);
		
		frmDictionarymaker.setVisible(true);
	}


}
