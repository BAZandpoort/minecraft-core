package com.cedricverlinden.bazandpoort.conversations.lectures.economy;

import com.cedricverlinden.bazandpoort.Core;
import org.bukkit.conversations.Conversation;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class EconomyConvo extends Conversation {

	public static final Map<String, String> questions = new HashMap<>() {{
		put("Wat is de term voor het geld dat een bedrijf overhoudt nadat alle kosten zijn betaald", "Winst");
		put("Wat is de term voor de situatie waarin de prijzen van goederen en diensten in een land in de loop van de tijd stijgen?", "Inflatie");
		put("Wat is de term voor de kosten die een bedrijf maakt om een product te produceren, inclusief grondstoffen, arbeidskosten en overheadkosten?", "Productiekosten");
		put("Wat is de term voor de prijs die een koper bereid is te betalen voor een product of dienst?", "Vraagprijs");
		put("Wat is de term voor de wetenschap van de productie, distributie en consumptie van goederen en diensten?", "Economie");
	}};

	public static HashMap<Player, List<String>> playerAnswers = new HashMap<>();

	public static int total = 0;
	public static int score = 0;

	public EconomyConvo(@NotNull Player forWhom) {
		super(Core.core(), forWhom, new EconomyReadyConvo());
		setLocalEchoEnabled(false);
	}

	public static void handleQuestion(Player player, String question, String input) {
		if (!(playerAnswers.containsKey(player))) {
			playerAnswers.put(player, new ArrayList<>(Collections.singletonList(input)));
		} else {
			playerAnswers.get(player).add(input);
		}

		total++;

		if (questions.get(question).equalsIgnoreCase(input)) {
			score++;
		}
	}
}
