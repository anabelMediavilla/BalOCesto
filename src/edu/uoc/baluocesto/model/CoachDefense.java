package edu.uoc.baluocesto.model;

import java.time.LocalDate;

public class CoachDefense extends Coach{
	private static final long serialVersionUID = 1L;

	public CoachDefense(String name, String surname, String nick, LocalDate birthdate, 
			  Country country, int salary, Team team, int numPlayers) throws Exception{
		
		super(name,surname,nick,birthdate,country,salary,team, numPlayers);
	}

	@Override
	public void train() {
		getTrainings().stream()
		 .forEach(training -> {
			 		Player player = training.getPlayer();
			 		int weeks2Improve = 6;
			 		int improvement = 1;
			 		try {
			 			if(training.getNumWeeks()%weeks2Improve==0) {
				 			player.getAttributes().setFg2p(player.getAttributes().getFg2p()+improvement);
						}			 		
				 		training.increaseNumWeeks();
					} catch (Exception e) {						
						e.printStackTrace();
					}

		 });
	}
}
