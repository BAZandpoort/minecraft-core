package com.cedricverlinden.bazandpoort.conversations.lectures.science.questions;

import com.cedricverlinden.bazandpoort.conversations.lectures.science.ScienceConvo;
import com.cedricverlinden.bazandpoort.managers.PlayerManager;
import com.cedricverlinden.bazandpoort.utils.ChatUtil;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ScienceQuestionFiveConvo implements Prompt {

	String question = (String) ScienceConvo.questions.keySet().toArray()[4];

	@Override
	public @NotNull String getPromptText(@NotNull ConversationContext context) {
		context.getForWhom().sendRawMessage(ChatUtil.color("&r"));
		return ChatUtil.color("&a&lVraag 5: &f" + question);
	}

	@Override
	public boolean blocksForInput(@NotNull ConversationContext context) {
		return true;
	}

	@Override
	public @Nullable Prompt acceptInput(@NotNull ConversationContext context, @Nullable String input) {
		ScienceConvo.handleQuestion((Player) context.getForWhom(), question, input);

		context.getForWhom().sendRawMessage(ChatUtil.color("&r"));
		List<String> answers = ScienceConvo.playerAnswers.get((Player) context.getForWhom());
		for (int i = 0; i < answers.size(); i++) {
			String question = (String) ScienceConvo.questions.keySet().toArray()[i];
			String correctAnswer = ScienceConvo.questions.get(question);
			String playerAnswer = answers.get(i);

			if (playerAnswer.equalsIgnoreCase(correctAnswer)) {
				context.getForWhom().sendRawMessage(ChatUtil.color("&a&lVraag " + (i+1) + " &fis juist!"));
			} else {
				context.getForWhom().sendRawMessage(ChatUtil.color("&a&lVraag " + (i+1) + " &fis helaas niet juist. Het juiste antwoord is &2" + correctAnswer + "&a."));
			}
		}

		int score = ScienceConvo.score;
		int total = ScienceConvo.total;

		context.getForWhom().sendRawMessage(ChatUtil.color("&r"));
		context.getForWhom().sendRawMessage(ChatUtil.color("&a&lM. ???: &fJe hebt een score van " + score + "/" + total + ". " + ((score > 2) ? "Goed gedaan!" : "Nog een beetje oefenen!")));
		ScienceConvo.playerAnswers.remove((Player) context.getForWhom());

		PlayerManager.getPlayer((Player) context.getForWhom()).setCurrentLecture("Exploring");
		ScienceConvo.score = 0;
		ScienceConvo.total = 0;
		return END_OF_CONVERSATION;
	}
}