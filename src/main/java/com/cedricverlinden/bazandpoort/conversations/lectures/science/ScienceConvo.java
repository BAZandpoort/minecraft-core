package com.cedricverlinden.bazandpoort.conversations.lectures.science;

import com.cedricverlinden.bazandpoort.Core;
import org.bukkit.conversations.Conversation;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class ScienceConvo extends Conversation {

	public static final Map<String, String> questions = new HashMap<>() {{
		put("Wat is het orgaan in het menselijk lichaam dat zuurstof in het bloed pompt", "Hart");
		put("Welke delen van het menselijk lichaam helpen ons te ademen?", "Longen");
		put("Wat is het proces waarbij een vloeistof verandert in een gas door middel van warmte?", "Verdamping");
		put("Wat is de term voor de natuurkundige kracht die twee objecten aantrekt naar elkaar toe?", "Zwaartekracht");
		put("Welke wetenschap houdt zich bezig met het bestuderen van de eigenschappen en interacties van materie en energie?", "Natuurkunde");
	}};

	public static HashMap<Player, List<String>> playerAnswers = new HashMap<>();

	public static int total = 0;
	public static int score = 0;

	public ScienceConvo(@NotNull Player forWhom) {
		super(Core.core(), forWhom, new ScienceReadyConvo());
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