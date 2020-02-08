package edu.uoc.baluocesto.view.gui;

import java.io.IOException;
import java.util.Comparator;
import java.util.stream.Collectors;

import edu.uoc.baluocesto.model.Player;
import edu.uoc.baluocesto.model.Team;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

public class TeamViewController extends Controller{

	@FXML
	private ImageView teamLogo;
	
	@FXML
	private VBox teamInfo;
	
	@FXML
	private Label teamName;
	
	@FXML
	private ListView<String> squadList;
	
	/**
	 * Default constructor.
	 */
	public TeamViewController(){
		super();		
	}
	
	/**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {    	
    	displayData(guiApp.getCurrentTeam());
    }
    
    private void displayData(Team team) {
    	teamName.setText(team.getLongName());
    	teamLogo.setImage(new Image("file:"+team.getImageSrc(),128.0, 128.0, false, false));
    	
    	((Label)teamInfo.getChildren().get(0)).setText(team.getPresident());
    	((Label)teamInfo.getChildren().get(1)).setText(team.getFounded()+"");
    	((Label)teamInfo.getChildren().get(2)).setText(team.getStadium().getAddress());
    	((Label)teamInfo.getChildren().get(3)).setText(String.format("%,d",team.getBudget())+" ptas.");
    	((Label)teamInfo.getChildren().get(4)).setText(team.getSponsor());
    	((Label)teamInfo.getChildren().get(5)).setText(team.getStadium().getName());
    	((Label)teamInfo.getChildren().get(6)).setText(String.format("%,d", team.getStadium().getCapacity())+" personas");
    	
    	
		ObservableList<Player> nodeList =  FXCollections.observableArrayList(team.getSquad().stream().sorted(Comparator.comparingInt(Player::getNumber)).collect(Collectors.toList()));
		for(var n : nodeList)
			squadList.getItems().add(n.getNumber()+". "+n.getSurname()+", "+n.getName());
		
		
			squadList.setOnMouseClicked(new EventHandler<MouseEvent>() {
			 public void handle(MouseEvent event) {    					
		        	guiApp.setCurrentPlayer(nodeList.get(squadList.getSelectionModel().getSelectedIndex()));
					guiApp.goToScene("PlayerView.fxml");
		        }
		    });
    	
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

}
