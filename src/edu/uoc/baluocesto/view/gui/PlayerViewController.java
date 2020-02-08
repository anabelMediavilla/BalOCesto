package edu.uoc.baluocesto.view.gui;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.stream.Collectors;

import edu.uoc.baluocesto.model.Player;
import edu.uoc.baluocesto.model.Team;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

public class PlayerViewController extends Controller{

	@FXML
	private ImageView playerImage;
	
	@FXML
	private VBox playerInfo;
	
	@FXML
	private Label playerName, playerNumber;
	
	@FXML
	private TableView playerAttributes;

	
	/**
	 * Default constructor.
	 */
	public PlayerViewController(){
		super();		
	}
	
	/**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {    	
    	displayData(guiApp.getCurrentPlayer());
    }
    
    private void displayData(Player player) {
    	playerName.setText(player.getNick());
    	playerImage.setImage(new Image("file:"+player.getImageSrc(), 1000, 1500, false, false));
    	playerNumber.setText(player.getNumber()+"");
    	
    	((Label)playerInfo.getChildren().get(0)).setText(player.getName()+" "+player.getSurname());
    	((Label)playerInfo.getChildren().get(1)).setText((player.getAttributes().getHeight()/(double)100)+" m.");
    	((Label)playerInfo.getChildren().get(2)).setText(player.getAttributes().getWeight()+" kg.");
    	((Label)playerInfo.getChildren().get(3)).setText(player.getBirthdate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))+"");
    	((Label)playerInfo.getChildren().get(4)).setText(player.getCountry()+"");
    	((Label)playerInfo.getChildren().get(5)).setText(player.getPosition()+"");    	
    	((Label)playerInfo.getChildren().get(6)).setText(player.getTeam().getLongName());
    	
    	
    	((TableColumn<PlayerAttributesInfo, String>)playerAttributes.getColumns().get(0)).setCellValueFactory( data -> data.getValue().ftProperty());
    	((TableColumn<PlayerAttributesInfo, String>)playerAttributes.getColumns().get(1)).setCellValueFactory( data -> data.getValue().fg2pProperty());
    	((TableColumn<PlayerAttributesInfo, String>)playerAttributes.getColumns().get(2)).setCellValueFactory( data -> data.getValue().fg3pProperty());
    	((TableColumn<PlayerAttributesInfo, String>)playerAttributes.getColumns().get(3)).setCellValueFactory( data -> data.getValue().speedProperty());
    	((TableColumn<PlayerAttributesInfo, String>)playerAttributes.getColumns().get(4)).setCellValueFactory( data -> data.getValue().defenseProperty());
    	((TableColumn<PlayerAttributesInfo, String>)playerAttributes.getColumns().get(5)).setCellValueFactory( data -> data.getValue().jumpProperty());
    	((TableColumn<PlayerAttributesInfo, String>)playerAttributes.getColumns().get(6)).setCellValueFactory( data -> data.getValue().reboundsProperty());
    	((TableColumn<PlayerAttributesInfo, String>)playerAttributes.getColumns().get(7)).setCellValueFactory( data -> data.getValue().assistsProperty());
    	((TableColumn<PlayerAttributesInfo, String>)playerAttributes.getColumns().get(8)).setCellValueFactory( data -> data.getValue().energyProperty());
    	((TableColumn<PlayerAttributesInfo, String>)playerAttributes.getColumns().get(9)).setCellValueFactory( data -> data.getValue().averageProperty());
    	
    	((TableColumn<PlayerAttributesInfo, String>)playerAttributes.getColumns().get(9)).getStyleClass().add("player-average");
    	
    	final ObservableList<PlayerAttributesInfo> data = FXCollections.observableArrayList(
    		    new PlayerAttributesInfo(player.getAttributes().getFt(), player.getAttributes().getFg2p(), player.getAttributes().getFg3p(), 
    		    		player.getAttributes().getSpeed(), player.getAttributes().getDefense(), player.getAttributes().getJump(),
    		    		player.getAttributes().getRebounds(), player.getAttributes().getAssists(), player.getAttributes().getEnergy(), player.getAttributes().getAverage())
    		);
    	
    	
    	playerAttributes.setItems(data);
    }
    
    /**
   	 * Called when the user clicks on the return button.
   	 * @throws IOException 
   	 */
   	@FXML
	private void handleReturn() throws IOException{
    	guiApp.setCurrentView(View.MAIN);
    	guiApp.goToScene("MainMenuView.fxml");
    }
   	
   	private class PlayerAttributesInfo{
   		
   		private final SimpleStringProperty  speed, defense, jump,  energy,
		 ft,  fg2p,  fg3p,  rebounds,  assists, average;

   	 
   	    private PlayerAttributesInfo(int ft, int fg2p, int fg3p, int speed, int defense, 
   	    		int jump,  int rebounds, int assists, int energy, int average) {
   	    	
   	        this.speed = new SimpleStringProperty(speed+"");
   	        this.defense = new SimpleStringProperty(defense+"");
   	        this.jump = new SimpleStringProperty(jump+"");
   	        this.energy = new SimpleStringProperty(energy+"");
	        this.ft = new SimpleStringProperty(ft+"");
	        this.fg2p = new SimpleStringProperty(fg2p+"");
	        this.fg3p = new SimpleStringProperty(fg3p+"");
	        this.rebounds = new SimpleStringProperty(rebounds+"");
	        this.assists = new SimpleStringProperty(assists+"");
	        this.average = new SimpleStringProperty(average+"");   	        
   	    }


   	    public SimpleStringProperty speedProperty() { return this.speed; }

		public SimpleStringProperty defenseProperty() { return this.defense; }


		public SimpleStringProperty jumpProperty() { return this.jump; }


		public SimpleStringProperty energyProperty() { return this.energy; }


		public SimpleStringProperty ftProperty() { return this.ft; }
		
		public SimpleStringProperty fg2pProperty() { return this.fg2p; }
		
		public SimpleStringProperty fg3pProperty() { return this.fg3p; }

		public SimpleStringProperty reboundsProperty() { return this.rebounds; }


		public SimpleStringProperty assistsProperty() { return this.assists; }

		public SimpleStringProperty averageProperty() { return this.average; }
   	    
   	    
   	}
}
