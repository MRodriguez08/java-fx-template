package com.mrodriguez.gymadmin.presentation;

import org.apache.log4j.Logger;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainWindow extends Application {

	private static Logger logger = Logger.getLogger(MainWindow.class);

	private BorderPane layout;
	
	private Scene scene;

	@Override
	public void start(Stage primaryStage) {

		MenuBar menuBar = new MenuBar();

		// --- Menu File
		Menu menuFile = new Menu("File");
		MenuItem menuExit = new MenuItem("Salir");
		menuExit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				exitApp();
			}
		});
		menuFile.getItems().add(menuExit);

		// --- Menu Edit
		Menu menuAdministration = new Menu("Administracion");
		MenuItem itemPlans = new MenuItem("Planes");
		menuAdministration.getItems().add(itemPlans);
		MenuItem itemCustomers = new MenuItem("Clientes");
		menuAdministration.getItems().add(itemCustomers);

		// --- Menu View
		Menu menuHelp = new Menu("Ayuda");
		MenuItem itemAbout = new MenuItem("Sobre GymAdmin...");
		menuHelp.getItems().add(itemAbout);

		menuBar.getMenus().addAll(menuFile, menuAdministration, menuHelp);
		
		layout = new BorderPane();

		scene = new Scene(new VBox());
		scene.getStylesheets().add(this.getClass().getResource("/com/mrodriguez/gymadmin/presentation/styles/main.css").toExternalForm());
		
		((VBox) scene.getRoot()).getChildren().addAll(menuBar , layout);		
		
		layout.setLeft(prepateSideMenu());		
		
		layout.setTop(prepateTop());
		layout.setRight(null);		
		
		Screen screen = Screen.getPrimary();
	    Rectangle2D bounds = screen.getVisualBounds();
		primaryStage.setTitle("GymAdmin v1.0");
		primaryStage.setScene(scene);
		primaryStage.setWidth(bounds.getWidth());
		primaryStage.setHeight(bounds.getHeight());
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		    @Override
		    public void handle(WindowEvent event) {
		        exitApp();
		    }
		});
		openCustomersAdministration();
		primaryStage.show(); 
	}
	
	private void exitApp(){
		logger.debug("closing application");
		System.exit(0);
	}
	
	private VBox prepateSideMenu(){
		VBox sideMenu = new VBox();
		sideMenu.getStyleClass().add("layout-left");
		
		Label generalLabel = new Label("General");		
		sideMenu.getChildren().add(generalLabel);
		
		Hyperlink paymentsLink = new Hyperlink("Pagos");
		paymentsLink.getStyleClass().add("side-menu-link");
		paymentsLink.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				//layout.setCenter(null);
				//layout.setCenter(new com.mrodriguez.gymadmin.presentation.windows.payments.Admin());
			}
		});
		sideMenu.getChildren().add(paymentsLink);
		
		Label adminLabel = new Label("Administracion");		
		sideMenu.getChildren().add(adminLabel);
		
		Hyperlink plansLink = new Hyperlink("Planes");		
		plansLink.getStyleClass().add("side-menu-link");
		plansLink.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				openPlansAdministration();				
			}
		});
		sideMenu.getChildren().add(plansLink);
		
		Hyperlink paymentMethodLink = new Hyperlink("Metodos de pago");		
		paymentMethodLink.getStyleClass().add("side-menu-link");
		paymentMethodLink.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				openPaymentMethodsAdministration();				
			}
		});
		sideMenu.getChildren().add(paymentMethodLink);
		
		
		Hyperlink customersLink = new Hyperlink("Clientes");
		customersLink.getStyleClass().add("side-menu-link");
		customersLink.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				layout.setCenter(null);
				layout.setCenter(new com.mrodriguez.gymadmin.presentation.windows.customers.Admin());
			}
		});
		sideMenu.getChildren().add(customersLink);
		
		return sideMenu;
	}
	
	private void openPlansAdministration(){
		//layout.setCenter(null);
		//layout.setCenter(new com.mrodriguez.gymadmin.presentation.windows.plans.Admin());
	}
	
	private void openPaymentMethodsAdministration(){
		//layout.setCenter(null);
		//layout.setCenter(new com.mrodriguez.gymadmin.presentation.windows.paymentmethods.Admin());
	}
	
	private void openCustomersAdministration(){
		layout.setCenter(null);
		layout.setCenter(new com.mrodriguez.gymadmin.presentation.windows.customers.Admin());
	}
	
	private HBox prepateTop(){
		HBox top = new HBox();
		
		top.getStyleClass().add("layout-top");
		Label appNameLabel = new Label("Panther Gym");
		top.getChildren().add(appNameLabel);
		
		return top;
	}

	public static void main(String[] args) {
		launch(args);
	}

	

}
