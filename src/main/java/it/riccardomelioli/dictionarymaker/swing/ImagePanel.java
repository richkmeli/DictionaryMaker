/*
 *     Copyright 2016 Riccardo Melioli. All Rights Reserved.
 */

package it.riccardomelioli.dictionarymaker.swing;

import javax.swing.*;
import java.awt.*;
import java.net.URL;


@SuppressWarnings("serial")
public class ImagePanel extends JComponent {
    protected Image image;
    protected URL URLimage;

    // costruttore con immagine
    public ImagePanel(Image image) {
        this.image = image;
    }

    @Override
    protected void paintComponent(Graphics painter) {    // � chiamato da swing
        super.paintComponent(painter);
        painter.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }


}
