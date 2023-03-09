package com.cedricverlinden.bazandpoort.conversations.lectures.math;

import com.cedricverlinden.bazandpoort.Core;
import org.bukkit.conversations.Conversation;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class MathConvo extends Conversation {

	public static final Map<String, String> questions = new HashMap<>() {{
		put("Bereken 5 x 7", "35");
		put("Wat is het verschil tussen 32 en 18", "14");
		put("Als een dozijn gelijk is aan 12 eieren, hoeveel eieren zijn er dan in 3 dozijnen", "36");
		put("Als een pak koekjes 24 koekjes bevat en je wilt ze verdelen onder 4 vrienden, hoeveel koekjes krijgt elke vriend dan?", "6");
		put("Wat is de omtrek van een vierkant met zijden van 8 cm? &7&o(Alleen het getal als antwoord)", "32");
	}};

	public static HashMap<Player, List<String>> playerAnswers = new HashMap<>();

	public static int total = 0;
	public static int score = 0;

	public MathConvo(@NotNull Player forWhom) {
		super(Core.core(), forWhom, new MathReadyConvo());
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
