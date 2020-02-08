package edu.uoc.baluocesto.model;

import java.time.LocalDate;

public class CoachFt extends Coach{
	
	private static final long serialVersionUID = 1L;

	public CoachFt(String name, String surname, String nick, LocalDate birthdate, 
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
			 			case C:
			 			case PF:
			 				weeks2Improve = 6;
			 				improvement = 1;
			 				break;						
						case PG:												
						case SF:
						case SG:
							weeks2Improve = 6;
							improvement = 2;
							break;						
			 		}
			 		
			 		try {
			 			if(training.getNumWeeks()%weeks2Improve==0) {
							player.getAttributes().setFt(player.getAttributes().getFt()+improvement);
						}
			 			training.increaseNumWeeks();
			 		} catch (Exception e) {
						e.printStackTrace();
					}

		 });
	}

}
