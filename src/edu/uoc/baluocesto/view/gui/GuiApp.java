package edu.uoc.baluocesto.view.gui;

import java.io.IOException;

import edu.uoc.baluocesto.controller.Database;
import edu.uoc.baluocesto.model.Player;
import edu.uoc.baluocesto.model.Team;
import edu.uoc.baluocesto.view.gui.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Region;

/**
 * This class is the entry point of the Kiosk's graphical interface.
 * 
 * @author David García Solórzano
 * @version 1.0
 */
public class GuiApp extends Application{
 
	private Stage primaryStage;
    private Region rootLayout;
    private Database controller;
    private View currentView;
    
    private Team team;
 	private Player player;
    
    /**
     * Method that is called first when the app is launched.
     * @throws Exception when something wrong happens.
     */
    @Override
	public void start(Stage primaryStage) throws Exception {
    	// Give the controller access to the main app.
        Controller.guiApp = this;
    	this.primaryStage = primaryStage;
	    this.primaryStage.setTitle("PC BalUOCesto's Database");
	    this.primaryStage.setResizable(false);      
	    controller = new Database("./files/"); 
	    setCurrentView(View.MAIN);
	    goToScene("MainMenuView.fxml");	  
	 }
       
    
    /**
     * Method that is called first when the app is closed/stopped.
     * @throws Exception when something wrong happens. 
     */
    @Override
	public void stop() throws Exception {
		super.stop();
	}


	/**
	 * Program entry point.
	 * 
	 * @param args arguments to the program (they are not needed).
	 */    
	 public static void main(String[] args) {
	    launch(args);
	 }
    
	 /**
	  * Returns the current Database object
	  * @return Database object that is currently used. 
	  */
	 public Database getController(){
		return controller;
	 }
	 
	 /**
	  * Sets the a new Database object.
	  * @param database New Database object to save.
	  */
	 public void setDatabase(Database database){
		this.controller = database;
	 }  
	  
	 /**
	  *  Returns the current view object.
	  * @return View Object that is currently shown to the user.
	  */
	 public View getCurrentView() {
		return currentView;
	 }
	
	 /**
	  * Sets the view that is being shown at this moment.
	  * @param currentView View object.
	  */
	 public void setCurrentView(View currentView) {
		this.currentView = currentView;
	 }
	 
	 public void setCurrentTeam(Team team) {
		 this.team = team;
	 }
	 
	 
	 public Team getCurrentTeam() {
		 return team;
	 }
	 
	 public void setCurrentPlayer(Player player) {
		 this.player = player;
	 }
	 
	 
	 public Player getCurrentPlayer() {
		 return player;
	 }
	 	
	/**
	 * It sets the layout/scene that should be shown currently.
	 * @param layout Name of the FXML file, e.g. WelcomeView.fxml.
	 */	
	public void goToScene(String layout) {
      try {
          // Load root layout from fxml file.
          FXMLLoader loader = new FXMLLoader();         
          loader.setLocation(GuiApp.class.getResource(layout));
          rootLayout = (Region) loader.load();         
          // Show the scene containing the root layout.
          Scene scene = new Scene(rootLayout);
          primaryStage.setScene(scene);
          primaryStage.show();
          
      }catch(IOException e) {
          e.printStackTrace();
      }
	}  
}