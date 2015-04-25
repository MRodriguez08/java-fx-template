package com.mrodriguez.gymadmin.presentation.windows.customers;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.mrodriguez.gymadmin.business.BusinessFactory;
import com.mrodriguez.gymadmin.business.CustomerBusiness;
import com.mrodriguez.gymadmin.persistence.entities.CustomerEntity;
import com.mrodriguez.gymadmin.repository.FormMode;
import com.mrodriguez.gymadmin.repository.ImageClickCellFactory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;

public class Admin extends GridPane {
	
	private static final Logger logger = Logger.getLogger(Admin.class);
	
	private CustomerBusiness customerBusiness;
	
	private TextField nameFilter, emailFilter, surnameFilter;
	
	private TableView<CustomerEntity> table = new TableView<CustomerEntity>();
	
	private final ObservableList<CustomerEntity> data =
	        FXCollections.observableArrayList();
	
	private Map<String , String> filters = new HashMap<String, String>();
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Admin(){
		
		customerBusiness = BusinessFactory.getCustomerBusiness();
		
		VBox wrapper = new VBox();
		setHgrow(wrapper, Priority.ALWAYS);
		wrapper.setFillWidth(true);
		wrapper.setMinWidth(this.getWidth());
		wrapper.getStyleClass().add("administration-pane-container");
		
		Label label = new Label("Gestion de clientes");
		label.getStyleClass().add("administration-title");
		wrapper.getChildren().add(label);
		
		HBox titleSubLine = new HBox();
		titleSubLine.getStyleClass().add("administration-title-line");
		wrapper.getChildren().add(titleSubLine);
		
		//filters panel
		TitledPane tp = new TitledPane();
		tp.setText("Filtros");		
		HBox filtersPane = new HBox();
		filtersPane.getStyleClass().add("administration-filter-pane");
		
		Label labelNameFilter = new Label("Nombre:");
		nameFilter = new TextField();
		labelNameFilter.setLabelFor(nameFilter);
		
		Label labelDescriptionFilter = new Label("Apellido:");
		surnameFilter = new TextField();
		labelDescriptionFilter.setLabelFor(surnameFilter);
		
		Label labelEmailFilter = new Label("Email:");
		emailFilter = new TextField();
		labelEmailFilter.setLabelFor(emailFilter);
		
		Button filterButton = new Button("Filtrar");
		filterButton.getStyleClass().add("administration-filter-button");
		filterButton.setOnAction(new EventHandler<ActionEvent>() {			
			@Override
			public void handle(ActionEvent arg0) {
				filters.clear();
				filters.put("name", nameFilter.getText() == null ? "" : nameFilter.getText());
				filters.put("surname", surnameFilter.getText() == null ? "" : surnameFilter.getText());
				filters.put("email", emailFilter.getText() == null ? "" : emailFilter.getText());
				filter();
			}
		});
		
		HBox filterInputsPane = new HBox();
		filterInputsPane.getChildren().addAll(labelNameFilter, nameFilter, labelDescriptionFilter, surnameFilter, labelEmailFilter, emailFilter);
		HBox filterButonPane = new HBox();
		filterButonPane.getChildren().addAll(filterButton);
		filtersPane.getChildren().addAll(filterInputsPane,filterButonPane);
		tp.setContent(filtersPane);
		wrapper.getChildren().add(tp);
		
		//create button
		HBox createButtonPane = new HBox();
		Button createButton = new Button("+");
		createButton.setTooltip(new Tooltip("Crear nuevo cliente"));
		createButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

		    @Override
		    public void handle(MouseEvent event) {
		        if (event.getButton() == MouseButton.PRIMARY) {
		        	Form dialog = new Form(null, FormMode.CREATE, event);
                    dialog.showAndWait();
                    filter();
		        }
		    }
		});
		createButtonPane.setAlignment(Pos.CENTER_RIGHT); 
		createButtonPane.getChildren().add(createButton);
		wrapper.getChildren().add(createButtonPane);
		
		//administration panel
		table.setEditable(false);

		TableColumn idColumn = new TableColumn("Id");
		idColumn.setMinWidth(50);
		idColumn.setMaxWidth(50);
		idColumn.setCellValueFactory(new PropertyValueFactory<CustomerEntity, Integer>(
						"id"));
		
		TableColumn nameColumn = new TableColumn("Nombre");
		nameColumn.setMinWidth(200);
		nameColumn.setMaxWidth(400);
		nameColumn.setCellValueFactory(new PropertyValueFactory<CustomerEntity, String>(
						"name"));
		
		TableColumn surnameColumn = new TableColumn("Apellido");
		surnameColumn.setMinWidth(200);
		surnameColumn.setCellValueFactory(new PropertyValueFactory<CustomerEntity, String>(
						"surname"));
		
		TableColumn emailColumn = new TableColumn("Email");
		emailColumn.setMinWidth(200);
		emailColumn.setCellValueFactory(new PropertyValueFactory<CustomerEntity, String>(
						"email"));
		
		TableColumn cellphoneColumn = new TableColumn("Celular");
		cellphoneColumn.setMinWidth(200);
		cellphoneColumn.setCellValueFactory(new PropertyValueFactory<CustomerEntity, String>(
						"cellphone"));
		
		TableColumn editColumn = new TableColumn("");
		editColumn.setMinWidth(50);
		editColumn.setMaxWidth(50);
		editColumn.setCellValueFactory(
                new PropertyValueFactory<CustomerEntity, File>("editImage"));
		ImageClickCellFactory cellFactory = new ImageClickCellFactory(
                new UpdateEventHandler());
		editColumn.setCellFactory(cellFactory);
		
		TableColumn deleteColumn = new TableColumn("");
		deleteColumn.setMinWidth(50);
		deleteColumn.setMaxWidth(50);
		deleteColumn.setCellValueFactory(
                new PropertyValueFactory<CustomerEntity, File>("deleteImage"));
		ImageClickCellFactory deleteCellFactory = new ImageClickCellFactory(
                new DeleteEventHandler());
		deleteColumn.setCellFactory(deleteCellFactory);
		
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		table.setItems(data);
        table.getColumns().addAll(idColumn, nameColumn, surnameColumn, emailColumn,cellphoneColumn, editColumn, deleteColumn);       
        
        wrapper.setSpacing(5);        
       
        wrapper.setPadding(new Insets(10, 0, 0, 10));
        wrapper.getChildren().add(table);
		
        this.getChildren().add(wrapper);
        
        filters.put("name","");
		filters.put("surname", "");
		filters.put("email", "");
        filter();
	}
	
	private void filter(){
		data.clear();
		
		List<CustomerEntity> entities = customerBusiness.findByFilters(filters);
		for (CustomerEntity e : entities){
			data.add(e);
		}
		
	}
	
	// This class implement the mouse event handler
    private class UpdateEventHandler implements EventHandler<MouseEvent> {
        @SuppressWarnings("rawtypes")
		@Override
        public void handle(MouseEvent event) {
            if (event.getClickCount() == 1 && event.getButton() == MouseButton.PRIMARY) {
                try {
                	CustomerEntity entity = data.get(
                            ((TableCell) event.getSource()).getIndex());

                    Form dialog = new Form(entity, FormMode.UPDATE, event);
                    dialog.showAndWait();
                    
                    filter();
                } catch (Exception e) {
                	logger.error(e.getMessage() , e);
                }
            }
        }
    }   
    
    // This class implement the mouse event handler
    private class DeleteEventHandler implements EventHandler<MouseEvent> {
        @SuppressWarnings("rawtypes")
		@Override
        public void handle(MouseEvent event) {
        	if (event.getClickCount() == 1 && event.getButton() == MouseButton.PRIMARY) {
                try {
                	CustomerEntity entity = data.get(
                            ((TableCell) event.getSource()).getIndex());

                    Form dialog = new Form(entity, FormMode.CREATE, event);                    
                    dialog.show();
                    
                } catch (IndexOutOfBoundsException e) {
                	logger.error(e.getMessage() , e);
                }
            }
        }
    }
}
