package edu.uoc.baluocesto.model;

import java.time.LocalDate;

public class CoachAssists extends Coach{

	private static final long serialVersionUID = 1L;

	public CoachAssists(String name, String surname, String nick, LocalDate birthdate, 
			  Country country, int salary, Team team, int maxNumPlayers2Train) throws Exception{
		
		super(name,surname,nick,birthdate,country,salary,team, maxNumPlayers2Train);
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
			 				weeks2Improve = 8;
			 				improvement = 1;
			 				break;
						case SF:
						case SG:
							weeks2Improve = 6;
							improvement = 2;
							break;
						case PG:
							weeks2Improve = 4;
							improvement = 2;
			 		}
			 		try {
				 		if(training.getNumWeeks()%weeks2Improve==0) {				 			
							player.getAttributes().setAssists(player.getAttributes().getAssists()+improvement);							
				 		}			 			 		
						training.increaseNumWeeks();
					} catch (Exception e) {						
						e.printStackTrace();
					}

		 });
	}
}
