package edu.uoc.baluocesto.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import edu.uoc.baluocesto.model.Coach;
import edu.uoc.baluocesto.model.Game;
import edu.uoc.baluocesto.model.Match;
import edu.uoc.baluocesto.model.MatchDay;
import edu.uoc.baluocesto.model.Offer;
import edu.uoc.baluocesto.model.Player;
import edu.uoc.baluocesto.model.Position;
import edu.uoc.baluocesto.model.Standing;
import edu.uoc.baluocesto.model.Team;
import edu.uoc.baluocesto.model.Training;
import edu.uoc.baluocesto.model.TrainingType;
import edu.uoc.baluocesto.model.WeeklyCash;


//https://www.youtube.com/watch?v=ge3tWwEMCSw

/**
 * The Manager class handles all the information required to successfully play the Basketball Manager.

 * @author David García Solórzano 
 */
public class Manager implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private final String MSG_PLAYER_ALREADY_TRAINED = "This player is already being trained!!";
	private final String MSG_ERR_OFFER_TRANSFERED_PLAYER = "This player is in the transfer list!! You cannot offer a renovation!!";
	
	Game game;
		
	public Manager() {
		game = null;
	}
	
	public Manager(String managerName, List<Team> teams, Team managerTeam) throws Exception{
		game = new Game(managerName,teams,managerTeam);
	}
	
	private Game getGame() {
		return game;
	}
	
	public boolean saveGame(){
		//TODO
	}
	
	public void loadGame() throws IOException{
		//TODO
	}
	
	public String getManagerName() {
		return game.getManagerName();
	}
	
	public String getTeamName(boolean shortName) {
		return shortName? game.getTeam().getShortName(): game.getTeam().getLongName();
	}
	
	private Team getMyTeam() {
		return game.getTeam();
	}
	
	public List<Player> getMySquad() {
		return getMyTeam().getSquad();
	}
	
	public List<Player> getMySquadByPosition(Position position){
		return getMySquad().stream().filter(player -> player.getPosition().getAbbreviation().equals(position.getAbbreviation())).
				sorted((p1,p2) -> p1.getNumber()-p2.getNumber()).collect(Collectors.toList());
	}
	
	public Player getPlayerFromMySquadByNumber(int number) throws NullPointerException,NoSuchElementException{
		return getMySquad().stream().filter(player -> player.getNumber()==number).findFirst().get();
	}
	
	private int getIndexPlayerFromMySquadByNumber(int number) throws NoSuchElementException{
		return IntStream.range(0, getMySquad().size())
														.filter(i -> getMySquad().get(i).getNumber() == number)
														.findFirst().getAsInt();
	}
	
	public void swapPlayers(int firstPlayerNumber, int secondPlayerNumber) throws NoSuchElementException{
		int firstPlayerIndex = getIndexPlayerFromMySquadByNumber(firstPlayerNumber);
		int secondPlayerIndex = getIndexPlayerFromMySquadByNumber(secondPlayerNumber);
			
		Collections.swap(getMySquad(), firstPlayerIndex, secondPlayerIndex);
	}
	
	public List<Coach> getCoaches(){
		return getMyTeam().getCoaches();
	}
	
	public void assignTraining2Player(int playerNumber, TrainingType trainingType) throws Exception {
		Player player = getPlayerFromMySquadByNumber(playerNumber);
		
		
		 Coach coach = getCoaches().stream()                        // Convert to steam
	                .filter(c -> c.isPlayerTrained(player))        // we want trained only
	                .findAny()                                      // If 'findAny' then return found
	                .orElse(null);                                  // If not found, return null
		
				
		if(coach!=null) {
			throw new Exception(MSG_PLAYER_ALREADY_TRAINED);
		}else {
			//Buscamos el coach			
			coach = getCoaches().stream().filter(c -> {
				try {
					return c.getClass().equals(Class.forName("edu.uoc.baluocesto.model.Coach"+trainingType));
				} catch (ClassNotFoundException e) {					
					e.printStackTrace();
				}
				return false;
			}).findFirst().get();

			getMyTeam().assignPlayer2Coach(player,coach);
		}
	}
	
	public void removeTraining2Player(int playerNumber) {
		System.out.println(playerNumber);
		getCoaches().stream().forEach(coach -> {
			Iterator<Training> itr = coach.getTrainings().iterator();
			
			while(itr.hasNext()) {
				Training training = itr.next();				
				if(training.getPlayer().getNumber()==playerNumber) {	
					itr.remove();
				}
			}			
			
		});
	}
	
	public boolean isPlayerTrainedByType(int playerNumber, TrainingType trainingType) {
		Player player = getPlayerFromMySquadByNumber(playerNumber);
		
		//Buscamos el coach
		Coach coach = getCoaches().stream().filter(c -> {
			try {
				return c.getClass().equals(Class.forName("edu.uoc.baluocesto.model.Coach"+trainingType));
			} catch (ClassNotFoundException e) {					
				e.printStackTrace();
			}
			return false;
		}).findFirst().get();
		
		//Miramos si el coach esta entrenando al player
		return coach.getTrainings().stream().filter(t -> t.getPlayer().equals(player)).collect(Collectors.toList()).size()>0;		
	}
	
	public TrainingType trainingTypeByPlayer(int playerNumber) {
		Player player = getPlayerFromMySquadByNumber(playerNumber);
		List<Coach> coaches = getCoaches();
		
		for(var coach : coaches) {
			if(coach.getTrainings().stream().filter(t -> t.getPlayer().equals(player)).collect(Collectors.toList()).size()>0) {
				return TrainingType.valueOf(coach.getClass().toString().substring(36).toUpperCase());
			}			
		}
		
		return null;		
	}
	
	public int trainingTypeVacancies(TrainingType trainingType) {
		
		//Buscamos el coach
		Coach coach = getCoaches().stream().filter(c -> {
			try {
				return c.getClass().equals(Class.forName("edu.uoc.baluocesto.model.Coach"+trainingType));
			} catch (ClassNotFoundException e) {					
				e.printStackTrace();
			}
			return false;
		}).findFirst().get();
		
		if(coach!=null) {
			return coach.getMaxNumPlayers2Train()-coach.getNumTrainings();
		}else {
			return 0;
		}	
	}
	
	public void createOffer(Player player, int offerTeam, int cancellationClause, int salary, int contractYears) throws Exception{
		
		//Si el jugador es de mi equipo y esta en transferible, entonces no se puede hacer oferta... no tiene sentido
		//Tampoco se puede hacer mas de una oferta a la vez, al mismo jugador
		if((getMyTeam().getSquad().contains(player) && isPlayerInTransferList(player)) || isPlayerInOfferList(player)) {
			throw new Exception(MSG_ERR_OFFER_TRANSFERED_PLAYER);
		}else {
			Offer offer = new Offer(player, offerTeam, cancellationClause, salary, contractYears, getMyTeam());
			game.addOffer(offer);
		}
	}
	
	
	/**
	 * 
	 * @param playerNumber Player's number.
	 * @return true if player is added to transfer list; false if the player is removed from the transfer list.
	 * @throws NoSuchElementException when the player does not exist in the squad (e.g. wrong player's number).
	 */
	public boolean transferPlayer(int playerNumber) throws NoSuchElementException{
		Player player = getPlayerFromMySquadByNumber(playerNumber);
		return transferPlayer(player);
		
		/* Otra forma
		 *
		 * int playerIndex = getIndexPlayerFromMySquadByNumber(playerNumber);		
		 * return transferPlayer(getMySquad().get(playerIndex));
		 
		 */
	}
	
	/**
	 * 
	 * @param playerNumber Player's object.
	 * @return true if player is added to transfer list; false if the player is removed from the transfer list.	
	 */
	private boolean transferPlayer(Player player) {
		return game.putPlayer2TransferList(player);
	}
	
	public boolean firePlayer(int playerNumber) throws NoSuchElementException{
		return firePlayer(getPlayerFromMySquadByNumber(playerNumber));
	}
	
	private boolean firePlayer(Player player) throws NoSuchElementException{
		try{
			/*
			 * Quitamos el jugador del equipo, si con el despido nos quedamos por debajo de MIN_PLAYERS, 
			 * entonces lanza excepcion y no se despide al jugador
			 */
			getMyTeam().removePlayer(player);
			player.getContract().setCancellationClause(0); //al despedirlo no tiene clausula de rescision
			player.getContract().setContractYears(0); //al despedirlo se va con cero años de contrato
			
			//Quitamos el equipo al jugador
			player.setTeam(null);
			
			//Añadimos el coste de despedirlo: salario semanal * semanas_que_quedan_hasta_el_final_de_temporada
			int playerCost = (player.getContract().getSalary()/game.getCalendar().size())*(game.getCalendar().size()-(game.getCurrentMatchDayIndex()+1));
			game.getFinance().setFireExpenditure(game.getFinance().getFireExpenditure()+playerCost);			
			
			//Asignamos a la lista de transferibles
			return transferPlayer(player);			
		}catch(Exception e) {
			System.err.println(e.getMessage());
			return false;
		}		
	}	
	
	public List<Standing> getLeagueTable(){
		return game.getLeagueTable();
	}
	
	public int getNumSeasonDays() {
		return game.getCalendar().size();
	}
	
	public void increaseTicketPrice() {
		game.getFinance().increaseTicketPrice();
	}
	
	public void decreaseTicketPrice() throws Exception {
		game.getFinance().decreaseTicketPrice();
	}
	
	public void increaseBillboardPrice() {
		game.getFinance().increaseBillboardPrice();
	}
	
	public void decreaseBillboardPrice() throws Exception {
		game.getFinance().decreaseBillboardPrice();
	}
	
	public int getTicketPrice() {
		return game.getFinance().getTicketPrice();
	}
	
	public int getBillboardPrice() {
		return game.getFinance().getBillboardPrice();
	}
	
	public boolean isPlayingAtHome() {
		return game.getCurrentMatch().getHomeTeam().equals(game.getTeam());
	}
	
	public MatchDay getMatchDay(int day) {
		return game.getCalendar().get(day);
	}
	
	public MatchDay getCurrentMatchDay() {
		return getMatchDay(getCurrentMatchDayIndex());
	}
	
	public int getCurrentMatchDayIndex() {
		return game.getCurrentMatchDayIndex();
	}
	
	public Match getMyMatch(int day) {
		return getMatchDay(day).findMatchByTeam(game.getTeam());
	}
	
	public Match getCurrentMyMatch() {
		return getCurrentMatchDay().findMatchByTeam(game.getTeam());
	}
	
	public WeeklyCash getWeeklyCash(int day) {
		return game.getFinance().getWeeklyCash(day);
	}
	
	public List<Player> getTransferList(){
		return game.getTransferList();
	}
	
	public boolean isPlayerInTransferList(Player player) {
		return game.getTransferList().contains(player);
	}
	
	public boolean isPlayerInOfferList(Player player) {
		return game.getOfferList().stream().filter(offer -> offer.getPlayer().equals(player)).collect(Collectors.toList()).size()>0;
	}
	
	public int getBudget() {
		return getMyTeam().getBudget();
	}
	
	public void advance() throws Exception{
		game.advance();	
	}	
}
