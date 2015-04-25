package com.mrodriguez.gymadmin.repository;

import com.mrodriguez.gymadmin.persistence.entities.CustomerEntity;

import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ModalStage extends Stage {
	
	protected VBox vbox;
	protected Scene scene;
	
	public ModalStage(CustomerEntity entity, Integer mode, MouseEvent event){
		vbox = new VBox();
		
	}

}
