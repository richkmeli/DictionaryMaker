/*
 *     Copyright 2016 Riccardo Melioli. All Rights Reserved.
 */

package it.riccardomelioli.dictionarymaker.swing;

import java.awt.BorderLayout;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;


@SuppressWarnings("serial")
public class ImageFrame extends JFrame{
	protected ImageIcon imageIcon;
	protected URL URLimage;
	protected ImagePanel panel;
	
	// costruttore con immagine
	public ImageFrame(ImageIcon image){
		this.imageIcon = image;
		buildFrame();
	}
	// costruttore con URL
	public ImageFrame(String stringURLimage){
		this.URLimage = getClass().getResource(stringURLimage);
		if(URLimage != null)
			this.imageIcon = new ImageIcon(URLimage);
		buildFrame();
	}
	
	private void buildFrame(){
		setBounds(0,0,this.imageIcon.getIconWidth(), this.imageIcon.getIconHeight());
		setLayout(new BorderLayout()); // farlo adattare dinamicamente
		
		panel = new ImagePanel(imageIcon.getImage());
		this.add(panel, BorderLayout.CENTER);
	}

}
