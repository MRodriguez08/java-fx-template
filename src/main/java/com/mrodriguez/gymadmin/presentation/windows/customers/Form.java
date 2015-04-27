package com.mrodriguez.gymadmin.presentation.windows.customers;

import java.util.List;

import org.apache.log4j.Logger;

import com.mrodriguez.gymadmin.business.BusinessFactory;
import com.mrodriguez.gymadmin.business.CustomerBusiness;
import com.mrodriguez.gymadmin.persistence.entities.CustomerEntity;
import com.mrodriguez.gymadmin.repository.BusinessException;
import com.mrodriguez.gymadmin.repository.FormMode;
import com.mrodriguez.gymadmin.repository.ModalStage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

public class Form extends ModalStage {

	private static final Logger logger = Logger.getLogger(Form.class);

	private Integer formMode = -1;
	private Integer planIndex = -1;
	private CustomerEntity currentEntity = null;
	
	private TextArea inputAddress;
	private TextField inputName, inputSurname, inputEmail, inputCellphone;
	private CheckBox inputCreateFirstPayment;
	private Label error;
	private HBox errorHBox;

	private ListView<String> inputPlan = new ListView<String>();
	private ObservableList<String> plansList = FXCollections
			.observableArrayList();

	public Form(CustomerEntity entity, Integer mode, MouseEvent event){
		super(entity , mode, event);
		
		//overrides parent defaults
		Group root = new Group();
        scene = new Scene(root);
        scene.getStylesheets().add(this.getClass().getResource("/com/mrodriguez/gymadmin/presentation/styles/main.css").toExternalForm());
        root.getStyleClass().add("form");
        vbox.getStyleClass().add("form");
        
		formMode = mode;
		
		Screen screen = Screen.getPrimary();
	    Rectangle2D bounds = screen.getVisualBounds();
	    //initStyle(StageStyle.UTILITY);
	    
	    initModality(Modality.APPLICATION_MODAL);
	    VBox vboxUsers = new VBox();
	    VBox vboxPayment = new VBox();
	    
	    TabPane tabPane = new TabPane();
	    
	    Tab userInfoTab = new Tab();
	    Tab paymentInfoTab = new Tab();
	    userInfoTab.setText("Datos personales");
	    paymentInfoTab.setText("Datos de pago");
	    
	    userInfoTab.setClosable(false);
	    paymentInfoTab.setClosable(false);
	    
	    vboxUsers.getStyleClass().add("form-tab");
		vboxPayment.getStyleClass().add("form-tab");
        
        Label title = new Label("");  
        title.getStyleClass().add("form-title");
        
        errorHBox = new HBox();
		error = new Label();  
        error.getStyleClass().add("form-error-text");
        errorHBox.getChildren().add(error);
        removeError();
        
        Label labelName = new Label("Nombre:");
        inputName = new TextField ();
        VBox hbName = new VBox();
        hbName.getChildren().addAll(labelName, inputName);
        hbName.setSpacing(10);
        
        Label labelSurname = new Label("Apellido:");
        inputSurname = new TextField ();
        VBox hbSurname = new VBox();
        hbSurname.getChildren().addAll(labelSurname, inputSurname);
        hbSurname.setSpacing(10);
        
        Label labelEmail = new Label("Email:");
        inputEmail = new TextField ();
        inputEmail.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
            	if (ke.getText().length() == 0)
            		return;
                System.out.println("Key Pressed: " + ke.getText());
            }
        });
        VBox hbEmail = new VBox();
        hbEmail.getChildren().addAll(labelEmail, inputEmail);
        hbEmail.setSpacing(10);
        
        
        Label labelCellphone = new Label("Celular:");
        inputCellphone = new TextField ();
        VBox hbCellphone = new VBox();
        hbCellphone.getChildren().addAll(labelCellphone, inputCellphone);
        hbCellphone.setSpacing(10);
        
        Label labelAddress = new Label("Direccion:");
        inputAddress = new TextArea();
        inputAddress.setPrefRowCount(5);
        inputAddress.setWrapText(true);
        VBox vbDescription = new VBox();
        vbDescription.getChildren().addAll(labelAddress, inputAddress);
        vbDescription.setSpacing(10);
        
        Label labelPlan = new Label("Plan:");
        VBox vbPlan = new VBox();
        inputPlan.setPrefHeight(70);
        vbPlan.getChildren().addAll(labelPlan, inputPlan);
        vbPlan.setSpacing(10);
        
        Label labelCreateFirstPayment = new Label("Crear primer pago");
        inputCreateFirstPayment = new CheckBox();
        inputCreateFirstPayment.setSelected(true);
        HBox vbCreateFirstPayment = new HBox();
        vbCreateFirstPayment.getChildren().addAll(inputCreateFirstPayment, labelCreateFirstPayment);
        vbCreateFirstPayment.setSpacing(10);
        
        if (mode == FormMode.CREATE){
        	title.setText("Crear cliente");
        	
        	
        	
        	
        } else if (mode == FormMode.UPDATE){
        	title.setText("Modificar cliente");
        	
        	inputName.setText(entity.getName());
        	inputSurname.setText(entity.getSurname());
        	inputEmail.setText(entity.getEmail());
        	inputCellphone.setText(entity.getCellphone());
        	inputAddress.setText(entity.getAddress());
        	
        	currentEntity = entity;
        } else {        	

        }
        
        HBox hbButtons = new HBox();
        hbButtons.setAlignment(Pos.CENTER_RIGHT); 
        hbButtons.setSpacing(15);
        Button confirm = new Button("Confirmar");
        confirm.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {					
					
					if (inputName.getText().length() == 0){
						inputName.getStyleClass().add("has-error");
						throw new BusinessException("The name is required");
					} else {
						inputName.getStyleClass().remove("has-error");
					}
					
					CustomerBusiness customerBusiness = BusinessFactory.getCustomerBusiness();

					CustomerEntity entity = new CustomerEntity();
					entity.setName(inputName.getText());
					entity.setSurname(inputSurname.getText());
					entity.setEmail(inputEmail.getText());
					
					if (formMode == FormMode.CREATE){						
						entity.setCreateFirstPayment(inputCreateFirstPayment.isSelected());
						customerBusiness.create(entity);
					} else if (formMode == FormMode.UPDATE){
						entity.setRegistrationDate(currentEntity.getRegistrationDate());
						entity.setId(currentEntity.getId());
						customerBusiness.edit(entity);
					}
					closeDialog();
				}  catch (BusinessException e2) {
					setError(e2.getMessage());
				} catch (Exception e2) {
					logger.error(e2.getMessage() , e2);
				}
			}
		});
        Button cancel = new Button("Cancelar");
        cancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				closeDialog();
			}
		});
        hbButtons.getChildren().addAll(cancel , confirm);
        
		vboxUsers.getChildren().add(hbName); 
		vboxUsers.getChildren().add(hbSurname);
		vboxUsers.getChildren().add(hbEmail);
		vboxUsers.getChildren().add(hbCellphone); 
		vboxUsers.getChildren().add(vbDescription); 
		
		userInfoTab.setContent(vboxUsers);
		paymentInfoTab.setContent(vboxPayment);
		
		tabPane.getTabs().addAll(userInfoTab, paymentInfoTab);		
        
        inputPlan.setItems(plansList);
        
		vbox.getChildren().add(title);
		vbox.getChildren().add(errorHBox);
		vbox.getChildren().add(tabPane);
		vbox.getChildren().add(hbButtons);
        root.getChildren().add(vbox);
        
        tabPane.setMaxWidth(300);
        setScene(scene);
	}

	private void closeDialog() {
		this.close();
	}
	
	private void setError(String errorMessage){
		errorHBox.setVisible(true);
		errorHBox.setPrefHeight(12);
        error.setVisible(true);
        error.setText(errorMessage);
	}
	
	private void removeError(){
		errorHBox.setVisible(false);
        error.setVisible(false);
        errorHBox.setPrefHeight(0);
	}
}
