package edu.uoc.baluocesto.model;

import java.time.LocalDate;

public class CoachRebounds extends Coach{
	
	private static final long serialVersionUID = 1L;

	public CoachRebounds(String name, String surname, String nick, LocalDate birthdate, 
			  Country country, int salary, Team team, int numPlayers) throws Exception{
		
		super(name,surname,nick,birthdate,country,salary,team, numPlayers);
	}

	@Override
	public void train() {
		getTrainings().stream()
		 .forEach(training -> {
			 		Player player = training.getPlayer();
			 		int weeks2Improve = 0;
			 		int improvement = 0;
			 		switch(player.getPosition()) {
			 			case PG:
			 				weeks2Improve = 8;
			 				improvement = 1;
			 				break;
			 			case SG:
			 			case SF:
							weeks2Improve = 6;
							improvement = 1;
							break;							
						case PF:
							weeks2Improve = 5;
							improvement = 2;
							break;
						case C:
							weeks2Improve = 4;
							improvement = 2;
							break;					
			 		}
			 		
			 		try {
			 			if(training.getNumWeeks()%weeks2Improve==0) {
							player.getAttributes().setRebounds(player.getAttributes().getRebounds()+improvement);						
			 			}
			 			training.increaseNumWeeks();
			 		} catch (Exception e) {
						e.printStackTrace();
					}

		 });
	}

}
