package com.cedricverlinden.bazandpoort.conversations.lectures.computerscience;

import com.cedricverlinden.bazandpoort.Core;
import org.bukkit.conversations.Conversation;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class ComputerScienceConvo extends Conversation {

	public static final Map<String, String> questions = new HashMap<>() {{
		put("Wat is de naam van 's werelds grootste zoekmachine?", "Google");
		put("Wat is de naam van het besturingssysteem dat door Apple is ontwikkeld voor hun computers?", "MacOS");
		put("Wat is de term voor computerprogramma's die specifieke taken uitvoeren op een computer?", "Software");
		put("Wat is de naam van het kleine apparaat dat digitale bestanden opslaat en overdraagt?", "USB");
		put("Wat is de naam van de code die wordt gebruikt om webpagina's te maken?", "HTML");
	}};

	public static HashMap<Player, List<String>> playerAnswers = new HashMap<>();

	public static int total = 0;
	public static int score = 0;

	public ComputerScienceConvo(@NotNull Player forWhom) {
		super(Core.core(), forWhom, new CSReadyConvo());
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
