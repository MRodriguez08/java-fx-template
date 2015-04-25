package com.mrodriguez.gymadmin.presentation.util;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JMenuItem;

public class ComponentsHelper {
	
	public static final String APP_FONT = "DejaVu Sans";

	public static final int BUTTON_HEIGHT = 50;

	public static void setUpButton(JButton btn){
		btn.setPreferredSize(new Dimension(100, 40));
		//btn.setAlignmentX(Component.CENTER_ALIGNMENT);
		btn.setPreferredSize(new Dimension(100,BUTTON_HEIGHT));
	}
	
	public static void setUpMenuEntries(JMenuItem item){
		item.setMargin(new Insets(5, 20, 50, 20));
		item.setFont(new Font(APP_FONT, Font.PLAIN, 20));
	}
	
	
}
