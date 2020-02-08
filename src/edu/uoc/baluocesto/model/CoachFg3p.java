package edu.uoc.baluocesto.model;

import java.time.LocalDate;

public class CoachFg3p extends Coach{

	private static final long serialVersionUID = 1L;

	public CoachFg3p(String name, String surname, String nick, LocalDate birthdate, 
			  Country country, int salary, Team team, int numPlayers) throws Exception{
		
		super(name,surname,nick,birthdate,country,salary,team, numPlayers);
	}

	@Override
	public void train() {
		/*
		 *  Los pivots mejoran en el lanzamiento de 3 muy poco, i.e. +1 cada 6 semanas conitnuadas de entrenamiento, etc.
		 */
		getTrainings().stream()
							 .forEach(training -> {
								 		Player player = training.getPlayer();
								 		int weeks2Improve = 0;
								 		int improvement = 0;
								 		switch(player.getPosition()) {
								 			case C:
								 				weeks2Improve = 8;
								 				improvement = 1;
								 				break;
											case PF:
												weeks2Improve = 6;
												improvement = 1;
												break;
											case PG:												
											case SG:
												weeks2Improve = 5;
												improvement = 2;
												break;
											case SF:
												weeks2Improve = 4;
												improvement = 2;
												break;
								 		}
								 		
								 		try {
								 			if(training.getNumWeeks()%weeks2Improve==0) {
								 				player.getAttributes().setFg3p(player.getAttributes().getFg3p()+improvement);
											}
								 			training.increaseNumWeeks();
								 		} catch (Exception e) {
											e.printStackTrace();
										}
		
							 });		
	}

}
