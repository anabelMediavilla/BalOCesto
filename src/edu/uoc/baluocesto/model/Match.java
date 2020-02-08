package edu.uoc.baluocesto.model;

import java.io.Serializable;

//https://github.com/ElliotJBall/FootballManagerSimulator/tree/master/footballmanager/src/main/java/com/elliot/footballmanager/match
//https://github.com/dreifler/BasketSim-Java-Game/blob/master/src/basketsim/data.txt
//https://repository.genmymodel.com/pablote/Basketball

public class Match implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private final static String MSG_ERR_POINTS_NEGATIVE = "[ERROR] You cannot assign negative points to a team";
	
	Team homeTeam, awayTeam;
	int pointsHomeTeam, pointsAwayTeam;
	
	private enum MatchDecision {
		MOVE,
		PASS,
		SHOOT;
	}
		
	public Match(Team homeTeam, Team awayTeam) throws Exception{
		setHomeTeam(homeTeam);
		setAwayTeam(awayTeam);
		setPointsHomeTeam(0);
		setPointsAwayTeam(0);
	}
	
	public Team getHomeTeam() {
		return homeTeam;
	}
	
	public void setHomeTeam(Team homeTeam) {
		this.homeTeam = homeTeam;
	}
	
	public Team getAwayTeam() {
		return awayTeam;
	}
	
	public void setAwayTeam(Team awayTeam) {
		this.awayTeam = awayTeam;
	}
		
	public int getPointsHomeTeam() {
		return pointsHomeTeam;
	}

	private void setPointsHomeTeam(int pointsHomeTeam) throws Exception{
		if(pointsHomeTeam<0) {
			throw new Exception(MSG_ERR_POINTS_NEGATIVE);
		}else {
			this.pointsHomeTeam = pointsHomeTeam;
		}
	}

	public int getPointsAwayTeam() {
		return pointsAwayTeam;		
	}

	private void setPointsAwayTeam(int pointsAwayTeam) throws Exception{
		if(pointsAwayTeam<0) {
			throw new Exception(MSG_ERR_POINTS_NEGATIVE);
		}else {
			this.pointsAwayTeam = pointsAwayTeam;
		}
	}
	
	public void play() throws Exception{
		final int MAX_ROUNDS = 600, MAX_DISTANCE_BIN = 500, DISTANCE_STEP = 50;
		int currentRound = 0, distance2Bin = MAX_DISTANCE_BIN;
		boolean homePossession = true;
		Player playerWithBall = homeTeam.getLineup().get(0); //PG - Base
		Player playerWithoutBall = awayTeam.getLineup().get(0); //PG - Base
		
		while(currentRound<MAX_ROUNDS) {
			MatchDecision decision = decision(MAX_DISTANCE_BIN,distance2Bin);
			int playerWithBallPerformance, playerWithoutBallPerformance;
			
			switch(decision) {
			
				case PASS:
					playerWithBallPerformance = getRandomIntegerBetweenRange(playerWithBall.getAttributes().getAssists(),100);
					playerWithoutBallPerformance = getRandomIntegerBetweenRange((playerWithBall.getAttributes().getDefense()),100);
					
					if(playerWithBallPerformance>playerWithoutBallPerformance) {
						int playerWithBallIndex = homePossession?homeTeam.getLineup().indexOf(playerWithBall):awayTeam.getLineup().indexOf(playerWithBall);
						int newPlayerWithBallIndex = playerWithBallIndex;
						while(playerWithBallIndex!=newPlayerWithBallIndex) { //Aseguramos que se la pasamos a otro jugador distinto al que tiene el balon
							newPlayerWithBallIndex = getRandomIntegerBetweenRange(0,4);
						}
						//aleatorio
						playerWithBall = homePossession?homeTeam.getLineup().get(newPlayerWithBallIndex):awayTeam.getLineup().get(newPlayerWithBallIndex); 
						//El defensor es el que esta en la misma posicion del que recibe el balon
						playerWithoutBall = homePossession?awayTeam.getLineup().get(newPlayerWithBallIndex):homeTeam.getLineup().get(newPlayerWithBallIndex);
						distance2Bin -= DISTANCE_STEP;
					}else{
						//No es del todo seguro que robe el balon, porque a veces la toca el defensor pero la recoge el atacante
						if(Math.random()>0.6) { //Cambio de posesion
							Player auxPlayer = playerWithBall;
							playerWithBall = playerWithoutBall;
							playerWithoutBall = auxPlayer;
							homePossession = !homePossession;
							distance2Bin = MAX_DISTANCE_BIN;
						}
					}
					
					break;
					
				case SHOOT:
					if(distance2Bin>=MAX_DISTANCE_BIN*0.625) {
						//Triple
						playerWithBallPerformance = getRandomIntegerBetweenRange(playerWithBall.getAttributes().getFg3p(),100);
					}else {
						//2
						playerWithBallPerformance = getRandomIntegerBetweenRange(playerWithBall.getAttributes().getFg2p(),100);						
					}
					
					playerWithoutBallPerformance = getRandomIntegerBetweenRange((playerWithBall.getAttributes().getJump()+playerWithBall.getAttributes().getDefense())/2,100);
				
					if(playerWithoutBall.getAttributes().getHeight()-playerWithBall.getAttributes().getHeight()>8) {
						//Si el defensor es 8 cm mas alto que el atacante, le añadimos un extra de probabilidad de tapon
						playerWithoutBallPerformance += getRandomIntegerBetweenRange(1,8);
					}
					
					if(playerWithBallPerformance>playerWithoutBallPerformance) {
						//Lanzamiento sin tapon... calculamos probabilidad de acierto
						int corrector = 0;
						
						if(distance2Bin>=MAX_DISTANCE_BIN*0.625) {
							
							//Triple
							if(distance2Bin>MAX_DISTANCE_BIN*0.725) {
								//Esta lejos, por lo tanto hay que corregir su probabilidad de acierto
								corrector = 10;
							}
								
							if(Math.random()*100<=playerWithBall.getAttributes().getFg3p()-corrector) { //Si un jugador tiene 62 de Fg3p, entonces acierta el 62% de veces.
								
								if(homePossession) {
									setPointsHomeTeam(getPointsHomeTeam()+3);									
								}else {
									setPointsAwayTeam(getPointsAwayTeam()+3);
								}
								//Cambio de posesion
								Player auxPlayer = playerWithBall;
								playerWithBall = playerWithoutBall;
								playerWithoutBall = auxPlayer;
								homePossession = !homePossession;
								distance2Bin = MAX_DISTANCE_BIN;
							}else {
								//Falla tiro... el defensor tiene ventaja en el rebote... de ahi el +10
								playerWithBallPerformance = getRandomIntegerBetweenRange(playerWithBall.getAttributes().getRebounds(),100);
								playerWithoutBallPerformance = getRandomIntegerBetweenRange(playerWithoutBall.getAttributes().getRebounds()+10,100);
								if(playerWithoutBallPerformance>playerWithBallPerformance) {
									//Cambio de posesion
									Player auxPlayer = playerWithBall;
									playerWithBall = playerWithoutBall;
									playerWithoutBall = auxPlayer;
									homePossession = !homePossession;
									distance2Bin = MAX_DISTANCE_BIN;
								}
							}
						}else {
							//2
							if(distance2Bin>MAX_DISTANCE_BIN*0.4) {
								//Esta lejos, por lo tanto hay que corregir su probabilidad de acierto
								corrector = 7;
							}
							
							if(Math.random()*100<=playerWithBall.getAttributes().getFg2p()-corrector) { //Si un jugador tiene 62 de Fg2p, entonces acierta el 62% de veces.
								 //Marca si la probabilidad aleatoria esta por debajo de su probabilidad de acertar
								
								if(homePossession) {
									setPointsHomeTeam(getPointsHomeTeam()+2);
								}else {
									setPointsAwayTeam(getPointsAwayTeam()+2);
								}
								//Cambio de posesion
								Player auxPlayer = playerWithBall;
								playerWithBall = playerWithoutBall;
								playerWithoutBall = auxPlayer;
								homePossession = !homePossession;
								distance2Bin = MAX_DISTANCE_BIN;
							}else {
								//Falla tiro... el defensor tiene ventaja en el rebote... de ahi el +10
								playerWithBallPerformance = getRandomIntegerBetweenRange(playerWithBall.getAttributes().getRebounds(),100);
								playerWithoutBallPerformance = getRandomIntegerBetweenRange(playerWithoutBall.getAttributes().getRebounds()+10,100);
								if(playerWithoutBallPerformance>playerWithBallPerformance) {
									//Cambio de posesion
									Player auxPlayer = playerWithBall;
									playerWithBall = playerWithoutBall;
									playerWithoutBall = auxPlayer;
									homePossession = !homePossession;
									distance2Bin = MAX_DISTANCE_BIN;
								}
							}
						}
					}else {//Tapon
						playerWithBallPerformance = getRandomIntegerBetweenRange(playerWithBall.getAttributes().getSpeed(),100);
						//El jugador que hace el tapon tiene ventaja porque ve donde va el balon
						playerWithoutBallPerformance = getRandomIntegerBetweenRange(playerWithoutBall.getAttributes().getSpeed()+10,100);
						
						if(playerWithoutBallPerformance>playerWithBallPerformance) {
							//Robo
							Player auxPlayer = playerWithBall;
							playerWithBall = playerWithoutBall;
							playerWithoutBall = auxPlayer;
							homePossession = !homePossession;
							distance2Bin = MAX_DISTANCE_BIN;
						}						
					}
					
					break;
					
				case MOVE:
					playerWithBallPerformance = getRandomIntegerBetweenRange(playerWithBall.getAttributes().getSpeed(),100);
					playerWithoutBallPerformance = getRandomIntegerBetweenRange((playerWithBall.getAttributes().getSpeed()+playerWithBall.getAttributes().getDefense())/2,100);
					
					if(playerWithBallPerformance>playerWithoutBallPerformance) {
						//Si corre mas quien tiene balon que la media de correr y defensa del defensor, entonces el atacante sigue...
						distance2Bin -= DISTANCE_STEP;
					}else {
						//Robo
						Player auxPlayer = playerWithBall;
						playerWithBall = playerWithoutBall;
						playerWithoutBall = auxPlayer;
						homePossession = !homePossession;
						distance2Bin = MAX_DISTANCE_BIN;
					}
					break;
			}			
			currentRound++;	
		}	
		
		//TODO: Falta controlar: personales, tiros libres, etc.
	}

	private MatchDecision decision(int maxDistance, int distance2Bin) {
		
		if(distance2Bin<maxDistance/4) {			
			if(distance2Bin<maxDistance/6) {//Esta cerca: tira
				return MatchDecision.SHOOT;
			}else {
				double choice = Math.random();
				if(choice>0.6) {
					return MatchDecision.PASS;
				}else if(choice>0.3 && choice<=0.6){ 
					return MatchDecision.MOVE;
				}else {
					return MatchDecision.SHOOT;
				}
			}
		}else {
			if(distance2Bin<maxDistance/2) { //Lejos: pasa, mueve o tira
				double choice = Math.random();
				if(choice>0.6) {
					return MatchDecision.PASS;
				}else if(choice>0.4 && choice<=0.6){ 
					return MatchDecision.MOVE;
				}else {
					return MatchDecision.SHOOT;
				}
			}else {//Esta muy lejos: mueves o pasa
				return Math.random()>=0.6?MatchDecision.MOVE:MatchDecision.PASS;
			}
		}
	}

	private int getRandomIntegerBetweenRange(double min, double max){
		int value = (int) ((int) (Math.random()*((max-min)+1))+min);		
		return value;    
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		
		Match fixture = (Match) obj;
		return getHomeTeam().equals(fixture.getHomeTeam()) &&
				getAwayTeam().equals(fixture.getAwayTeam());
	}
	
	@Override
	public String toString() {
		return getHomeTeam().getShortName() + " vs " + getAwayTeam().getShortName();
	}
}