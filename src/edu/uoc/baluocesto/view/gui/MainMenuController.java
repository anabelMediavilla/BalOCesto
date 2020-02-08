package edu.uoc.baluocesto.view.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import edu.uoc.baluocesto.model.Player;
import edu.uoc.baluocesto.model.Team;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class MainMenuController extends Controller{

	@FXML
	private TilePane teamsPane;
	
	@FXML
	private ListView<String> playersPane;
	
	
	/**
	 * Default constructor.
	 */
	public MainMenuController(){
		super();		
	}
	
	/**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {    	
    	displayTeams(Controller.guiApp.getController().getTeamsByLeague("ACB"));
    	
    	List<Team> teams = guiApp.getController().getTeamsByLeague("ACB");
    	
    	List<Player> players = new ArrayList<Player>(teams.size()*12);
		
		for(Team team : teams){   
			players.addAll(team.getSquad());		
		}
		
		
    	
		displayPlayers(players);	
    }
    
   /**
	 * Given a list of items (i.e. mains, beverages, etc.), this method creates a visual element for each item of the list. 
	 * In addition, it adds a mouse released method to each element.  
	 * @param currentList List of items whose items should be displayed.
	 */
	private void displayTeams(List<Team> teams){
		
		ObservableList<Node> nodeList =  FXCollections.observableArrayList();
		
	   	for(Team team : teams){    		
	   		try {	   			
				Pane n = (Pane)FXMLLoader.load(getClass().getResource("TeamLogo.fxml"));
				
				Image iv = new Image ("file:"+team.getImageSrc(),
		    			300, 300, false, false);
				
				((ImageView) n.getChildren().get(0)).setImage(iv);
				
				((Label)((HBox) n.getChildren().get(1)).getChildren().get(0)).setText(team.getShortName());
				
				nodeList.add(n);
				
				n.setOnMouseClicked(new EventHandler<Event>() {
					@Override
					public void handle(Event arg0) {
						guiApp.setCurrentTeam(team);
						guiApp.goToScene("TeamView.fxml");
						
					}
				});
				
			} catch (IOException e) {				
				e.printStackTrace();
			}
	   		
	   		teamsPane.getChildren().clear();
	   		teamsPane.getChildren().addAll(nodeList);  	
	   	}	
	}
	
	/**
	 * Given a list of items (i.e. mains, beverages, etc.), this method creates a visual element for each item of the list. 
	 * In addition, it adds a mouse released method to each element.  
	 * @param currentList List of items whose items should be displayed.
	 */
	private void displayPlayers(List<Player> players){		
		players = players.stream().sorted(Comparator.comparing(Player::getNick)).collect(Collectors.toList());
		
		ObservableList<Player> nodeList =  FXCollections.observableArrayList(players);
		
		playersPane.getItems().clear();
		
		for(var player : nodeList) {
			playersPane.getItems().add(player.getNick().toUpperCase()+" | "+player.getSurname()+", "+player.getName()+" ("+player.getTeam().getShortName()+")");
				
			playersPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
		        @Override
		        public void handle(MouseEvent event) {
		        	guiApp.setCurrentPlayer(nodeList.get(playersPane.getSelectionModel().getSelectedIndex()));
					guiApp.goToScene("PlayerView.fxml");
		        }
		    });			
		}
	}	
	
}