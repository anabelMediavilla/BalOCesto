package edu.uoc.baluocesto.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import edu.uoc.baluocesto.controller.Database;
/**
* This class has information which represents the person playing the game and stores information about
* the current status of the game, i.e. manager's team, season's information, finance, database according to the decisions of the game, etc.
*/
public class Game implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private final int MILLION = 1000000;
	
	private final String MSG_ERR_OFFER_FOR_PLAYER_ALREADY_EXISTS = "[ERROR] There is already an offer for this player!!";
	private final String MSG_ERR_NO_MORE_MATCH_DAYS = "[ERROR] There are no more match days!!";
	private final String MSG_TEAM_REJECT_TRANSFER = " has rejected your offer!!";
	private final String MSG_PLAYER_REJECT_TRANSFER = " has rejected your offer!!";
	private final String MSG_YOU_REJECT_TRANSFER = "You have just reject an offer for ";
	private final String MSG_YOU_ACCEPT_TRANSFER = " has just been transfered to ";
	private final String MSG_ACCEPTED_YOUR_OFFER = "You have just bought  ";
	//private final String MSG_ACCEPTED_OTHER_OFFER =" has accepted an offer from ";
	private final String MSG_RECEIVE_TV_INCOME = "You have just earned TV incomes by value of ";
	
	private String managerName;
	private Team team;
	private int currentMatchDayIndex;
	private Season season;
	private Finance finance;
	private Database database;
	private List<Player> transferList;
	private List<Offer> offerList;
			
	public Game(String managerName, List<Team>teams, Team managerTeam) throws Exception {
		setManagerName(managerName);
		setTeam(managerTeam);
		setSeason(new Season(LocalDate.of(1997, 9, 6), teams));
		setFinance(new Finance(getTeam(), getSeason().getCalendar().size()));
		//Miramos si recibimos dinero de la TV
		calculateTvIncomes();
		getFinance().addWeeklyCash(); //Creamos la primera semana de caja		
		createTransferList();
		offerList = new ArrayList<Offer>();
	}
	
	public Game(String managerName, Team managerTeam, Finance finance, Season season, int currentMatchDayIndex) throws Exception{
		setManagerName(managerName);
		setTeam(managerTeam);
		setFinance(finance);
		setSeason(season);
		setCurrentMatchDayIndex(currentMatchDayIndex);
	}
	
	public String getManagerName() {
		return managerName;
	}
	
	private void setManagerName(String managerName) {
		this.managerName = managerName;
	}	
	
	public Team getTeam() {
		return team;
	}
	
	private void setTeam(Team team) {
		this.team = team;		
	}
	
	public int getCurrentMatchDayIndex() {
		return currentMatchDayIndex;
	}
	
	private void setCurrentMatchDayIndex(int currentMatchDayIndex) throws Exception{
		if(currentMatchDayIndex>=getSeason().getCalendar().size()) {
			throw new Exception(MSG_ERR_NO_MORE_MATCH_DAYS);
		}
			
		this.currentMatchDayIndex = currentMatchDayIndex;
	}
	
	public LocalDate getCurrentMatchDayDate() {
		return getSeason().getCalendar().get(currentMatchDayIndex).getDate();
	}
	
	public Match getCurrentMatch() {
		return getSeason().getCalendar().get(getCurrentMatchDayIndex()).findMatchByTeam(getTeam());
	}
	
	public MatchDay getCurrentMachDay() {
		return getSeason().getCalendar().get(getCurrentMatchDayIndex());
	}
	
	public Season getSeason() {
		return season;
	}
	
	public void setSeason(Season season) {
		this.season = season;
	}
		
	public Database getDatabase() {
		return database;
	}
	
	public void setDatabase(Database database) {
		this.database = database;
	}	
		
	public List<Standing> getLeagueTable(){
		return getSeason().getLeagueTable();
	}
	
	public List<MatchDay> getCalendar(){
		return getSeason().getCalendar();
	}
	
	public Finance getFinance() {
		return finance;
	}
	
	private void setFinance(Finance finance) {
		this.finance = finance;
	}
	
	private void createTransferList() {
		transferList = new ArrayList<Player>();
	}
	
	public boolean putPlayer2TransferList(Player player) {
		if(transferList.contains(player)) {
			return !transferList.remove(player); //return false when the player is removed.
		}else{
			return transferList.add(player); //return true when the player is added.
		}
	}
	
	public List<Player> getTransferList(){
		return transferList;
	}
	
	public List<Offer> getOfferList(){
		return offerList;
	}
	
	public void addOffer(Offer offer) throws Exception{
		if(offerList.contains(offer)) {
			throw new Exception(MSG_ERR_OFFER_FOR_PLAYER_ALREADY_EXISTS);
		}
		offerList.add(offer);
	}
	
	public void advance() throws Exception{
		try{
			
			//Jugamos los partidos
			playMatches();			
		
			//TODO: Generamos ofertas
			
			//Miramos las ofertas
			//simulateTransfers();			
		
			//Calculamos las finanzas
			calculateFinance();
			
			//Avanzamos de jornada
			try{
				setCurrentMatchDayIndex(getCurrentMatchDayIndex()+1);
			}catch(Exception e) {
				System.out.println(e.getMessage());
				int position = 1;
				
				for(var s : getLeagueTable()){
					if(s.getTeam().equals(team)) break;
					position++;
				}
				
				System.out.println("Your position has been: "+position);
				System.exit(0);
			}
			getFinance().reset();
			getFinance().addWeeklyCash();
			
			//Miramos si recibimos dinero de la TV
			calculateTvIncomes();
			
		}catch(Exception e) {
			System.out.println(e.getMessage());		
		}
	}
	
	private void playMatches() {
		getCurrentMachDay().getMatches().stream().forEach(match -> {
			try{
				match.play();
				//Actualizamos la clasificacion
				//Equipo casa
				getLeagueTable().stream().filter(standing -> standing.getTeam().equals(match.getHomeTeam())).forEach(s -> {
					s.setPointsFor(s.getPointsFor()+match.getPointsHomeTeam());
					s.setPointsAgainst(s.getPointsAgainst()+match.getPointsAwayTeam());
					if(match.getPointsHomeTeam()>match.getPointsAwayTeam()) {
						s.setWins(s.getWins()+1);
					}else {
						s.setLosses(s.getLosses()+1);
					}
				});;
				
				//Equipo visitante
				getLeagueTable().stream().filter(standing -> standing.getTeam().equals(match.getAwayTeam())).forEach(s -> {
					s.setPointsFor(s.getPointsFor()+match.getPointsAwayTeam());
					s.setPointsAgainst(s.getPointsAgainst()+match.getPointsHomeTeam());
					if(match.getPointsAwayTeam()>match.getPointsHomeTeam()) {
						s.setWins(s.getWins()+1);
					}else {
						s.setLosses(s.getLosses()+1);
					}
				});;
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}
		});
	}
	
	private void simulateTransfers() throws Exception {
		//TODO: Generamos ofertas de otros equipos por cualquier jugador
				
		//VENDEMOS
		sellPlayers();
		
		//COMPRAMOS
		buyPlayers();		
	}
		
	private void sellPlayers() throws Exception{
		List<Player> myTransferPlayers = getTransferList().stream().filter(player -> 
											player.getTeam().equals(getTeam()) //jugadores de mi equipo que estan en traspaso
										).collect(Collectors.toList());
		
		List<Offer> offersForMyPlayers = getOfferList().stream().filter(offer -> 
				offer.getPlayer().getTeam().equals(getTeam()) //ofertas para jugadores de mi equipo
			 ).collect(Collectors.toList());
		
		Iterator<Player> itr = myTransferPlayers.iterator();
		
		while(itr.hasNext()) {
			Player player = itr.next();
		
			//Buscamos todas las ofertas para un jugador ordenadas de mayor a menor oferta
			List<Offer> offersForOnePlayer = offersForMyPlayers.stream().filter(offer -> player.equals(offer.getPlayer())).sorted().collect(Collectors.toList());
		
			if(offersForOnePlayer.size()>0) {
				Offer offer = offersForOnePlayer.get(0);
				
				if(offer.getOfferTeam() == player.getContract().getCancellationClause()) {
					//Si la oferta es igual a la clausula de rescision, entonces por nuestra parte el jugador es vendido automaticamente
					//Falta que el jugador decida si le intersa las condiciones del contrato
				
					if(offer.getContract().getCancellationClause()/player.getContract().getCancellationClause()<=1.2) { //Es menor a un incremento del 20%
						if(offer.getContract().getSalary()/player.getContract().getSalary()>=0.8) {	//El salario es del 80% minimo al actual				
							if(Math.abs(offer.getContract().getContractYears()-player.getContract().getContractYears())<=2) { //si los años ofrecidos es como maximo de 2 años mas o menos	
								//Jugador acepta la oferta. Asignamos al jugador su nuevo contrato y equipo 
								player.getContract().setCancellationClause(offer.getContract().getCancellationClause());
								player.getContract().setSalary(offer.getContract().getSalary());
								player.getContract().setContractYears(offer.getContract().getContractYears());
								player.setTeam(offer.getTeam()); 
				
								//Incrementamos nuestras ganancias por traspasos
								getFinance().setTransferIncome(getFinance().getTransferIncome()+offer.getOfferTeam()); 
				
				
								System.out.println(player.getNick()+MSG_YOU_ACCEPT_TRANSFER+offer.getTeam().getLongName());
				
								//Quitamos al jugador de la lista de transferibles
								itr.remove();
							}else {
								System.out.println(player.getNick()+MSG_PLAYER_REJECT_TRANSFER);
							}								
						}else {
							System.out.println(player.getNick()+MSG_PLAYER_REJECT_TRANSFER);
						}
					}else {
						System.out.println(player.getNick()+MSG_PLAYER_REJECT_TRANSFER);
					}
				}else {
					System.out.println(MSG_YOU_REJECT_TRANSFER+player.getNick());
				
					/*
					* TODO: Aqui podriamos preguntar si queremos vender por menor precio...
					* entonces habria que hacer diferente este metodo y su relacion con la vista... 
					*/					
				}
			
				//Quitamos todas las ofertas para este jugador
				for(var offerForOnePlayer : offersForOnePlayer) {
					getOfferList().remove(offerForOnePlayer);
				}
			
				offersForOnePlayer = null;
			}
		}
	//TODO: Aqui podriamos gestionar jugadores por los que tenemos ofertas pero no los hemos traspasado
	}

	private void buyPlayers() throws Exception{
	
		List<Offer> myOffers = getOfferList().stream().filter(offer -> 
								offer.getTeam().equals(getTeam()) //mis ofertas
								).collect(Collectors.toList());	
		
		List<Player> otherTransferPlayers = getTransferList().stream().filter(player -> 
						!player.getTeam().equals(getTeam()) //jugadores que no sean de mi equipo que estan en traspaso
				).collect(Collectors.toList());
		
		
		Iterator<Player> itr = otherTransferPlayers.iterator();
		
		//TODO: Solo gestionamos nuestras ofertas, no miramos si otros equipos se han interesado por este mismo jugador...
		while(itr.hasNext()) {
			Player player = itr.next();
		
			//Buscamos mi ofertas para el jugador
			Offer offer = myOffers.stream().filter(o -> player.equals(o.getPlayer())).findFirst().get(); 
						
			if(offer.getPlayer().equals(player)) {
				boolean teamAccept = false;		
		
				if(offer.getOfferTeam() == player.getContract().getCancellationClause()) {
					//Si la oferta es igual a la clausula de rescision, entonces el jugador es vendido automaticamente
					//Falta que el jugador decida si le intersa las condiciones del contrato
					teamAccept = true;
				}else {						
					//Si nuestra oferta es como minimo mas del 85% de la clausula entonces es aleatoria la aceptacion
					if(offer.getOfferTeam()/player.getContract().getCancellationClause()>0.85) {
						teamAccept = Math.random()>0.5?true:false;
					}else {
						teamAccept = false;
					}
		
					if(teamAccept) {
						//Falta que decida el jugador
						if(offer.getContract().getCancellationClause()/player.getContract().getCancellationClause()<=1.2) { //Es menor a un incremento del 20%
							if(offer.getContract().getSalary()/player.getContract().getSalary()>=0.9) {	//El salario es del 9% minimo al actual				
								if(Math.abs(offer.getContract().getContractYears()-player.getContract().getContractYears())<=2) { //si los años ofrecidos es como maximo de 2 años mas o menos	
									//Jugador acepta la oferta. Asignamos al jugador su nuevo contrato y equipo 
									player.getContract().setCancellationClause(offer.getContract().getCancellationClause());
									player.getContract().setSalary(offer.getContract().getSalary());
									player.getContract().setContractYears(offer.getContract().getContractYears());
									player.setTeam(offer.getTeam()); 
		
									//Incrementamos nuestros gastos por traspasos
									getFinance().setTransferExpenditure(getFinance().getTransferExpenditure()+offer.getOfferTeam()); 
											
									//if(offer.getTeam().equals(game.getTeam())) {
									//Lo hemos comprado nosotros
									System.out.println(MSG_ACCEPTED_YOUR_OFFER+player.getNick());
									//}else {
									//Lo ha comprado otro equipo que ha hecho mejor oferta
									//	System.out.println(player.getNick()+MSG_ACCEPTED_OTHER_OFFER+offer.getTeam().getLongName());
									//	}
		
									//Quitamos al jugador de la lista de transferibles
									itr.remove();
								}else {
									System.out.println(player.getNick()+MSG_PLAYER_REJECT_TRANSFER);
								}								
							}else {
								System.out.println(player.getNick()+MSG_PLAYER_REJECT_TRANSFER);
							}
						}else {
							System.out.println(player.getNick()+MSG_PLAYER_REJECT_TRANSFER);
						}
					}else {
						System.out.println(player.getTeam().getLongName()+MSG_TEAM_REJECT_TRANSFER);
					}
				}
			}
		}
	}

	private void calculateFinance() throws Exception{
		int stadiumCapacity = getTeam().getStadium().getCapacity(), matchCapacity = stadiumCapacity/2, matchBillboards; 
		final int NUM_BILLBOARDS = 50;
		//Si somos equipo local, calculamos las ganancias por entradas y vallas
		if(getCurrentMatch().getHomeTeam().equals(getTeam())) {
			//Calculamos cuantos espectadores han ido al partido en funcion de los equipos (su posicion, calidad, etc.) y precios
			int homeTeamPosition = getSeason().getTeamPosition(getTeam());
			int awayTeamPosition = getSeason().getTeamPosition(getCurrentMatch().getAwayTeam());
			
			
			if(Math.abs(homeTeamPosition-awayTeamPosition)<6) {
				//Si entre los dos equipos hay menos de 6 posiciones de diferencia, 
				//entonces el estadio se llena como minimo la mitad
				matchCapacity = getRandomIntegerBetweenRange(stadiumCapacity/2,stadiumCapacity);
			}
			
			if(awayTeamPosition<6 || homeTeamPosition<6) {
				//si el equipo que visita o nuestro equipo es de los 5 primeros, 
				//entonces podemos mejorar el aforo hasta llenar
				matchCapacity += getRandomIntegerBetweenRange(0,stadiumCapacity*0.33);
			}
			
			if(getFinance().getTicketPrice()>getFinance().getTicketPriceStep()*6) {
				//Si el precio de las entradas es desorbitado,
				//penaliza al aforo
				matchCapacity = getRandomIntegerBetweenRange(matchCapacity*0.6,matchCapacity*0.8);
			}
			
			//El numero de vallas depende del aforo final del estadio
			if((matchCapacity/stadiumCapacity)>0.7) {
				matchBillboards =  getRandomIntegerBetweenRange(NUM_BILLBOARDS*0.6,NUM_BILLBOARDS);
			}else {
				matchBillboards = getRandomIntegerBetweenRange(NUM_BILLBOARDS*0.4,NUM_BILLBOARDS*0.6);
			}
			
			//Tambien del precio de las vallas
			if(getFinance().getBillboardPrice()>getFinance().getBillboardPriceStep()*4){
				matchBillboards -= getRandomIntegerBetweenRange(NUM_BILLBOARDS*0.2,NUM_BILLBOARDS*0.3);
			}
							
			getFinance().setTicketIncome(matchCapacity*getFinance().getTicketPrice());		 
			getFinance().setBillboardIncome(matchBillboards*getFinance().getBillboardPrice());
		}						
		getFinance().updateWeeklyCash();		
	}

	private int getRandomIntegerBetweenRange(double min, double max){
		return (int)((Math.random()*((max-min)+1))+min);	    
	}
	
	private void calculateTvIncomes() throws Exception{
		Match match = getCurrentMatch();
		int tvIncome = 0;
		if(match.getHomeTeam().equals(getTeam())) { //Solo recibo ingresos de TV si soy el equipo local
			int homeTeamPosition = getSeason().getTeamPosition(match.getHomeTeam());
			int awayTeamPosition = getSeason().getTeamPosition(match.getHomeTeam());
			
			if(Math.abs(homeTeamPosition-awayTeamPosition)<6 || awayTeamPosition<6) {
				tvIncome = (int) Math.random()*25000000+MILLION; //Minimo 10 mill y max 35 mill.
			}else {
				if(Math.random()>0.8) {
					tvIncome = MILLION;
				}
			}
			
			if(tvIncome!=0) {
				System.out.println(MSG_RECEIVE_TV_INCOME+tvIncome);
			}
		}		
		
		getFinance().setTvIncome(tvIncome);
	}
}
