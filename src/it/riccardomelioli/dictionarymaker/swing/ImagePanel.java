package it.riccardomelioli.dictionarymaker.swing;

import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

import javax.swing.JComponent;


@SuppressWarnings("serial")
public class ImagePanel extends JComponent{
	protected Image image;
	protected URL URLimage;
	
	// costruttore con immagine
	public ImagePanel(Image image){
		this.image = image;
	}
	
	@Override
	protected void paintComponent(Graphics painter){	// è chiamato da swing
		super.paintComponent(painter);
		painter.drawImage(image, 0, 0, getWidth(), getHeight(), this);
	}
	
	
}
