package com.cedricverlinden.bazandpoort.conversations.lectures.economy.questions;

import com.cedricverlinden.bazandpoort.conversations.lectures.economy.EconomyConvo;
import com.cedricverlinden.bazandpoort.managers.PlayerManager;
import com.cedricverlinden.bazandpoort.utils.ChatUtil;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EconomyQuestionFiveConvo implements Prompt {

	String question = (String) EconomyConvo.questions.keySet().toArray()[4];

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
		EconomyConvo.handleQuestion((Player) context.getForWhom(), question, input);

		context.getForWhom().sendRawMessage(ChatUtil.color("&r"));
		List<String> answers = EconomyConvo.playerAnswers.get((Player) context.getForWhom());
		for (int i = 0; i < answers.size(); i++) {
			String question = (String) EconomyConvo.questions.keySet().toArray()[i];
			String correctAnswer = EconomyConvo.questions.get(question);
			String playerAnswer = answers.get(i);

			if (playerAnswer.equalsIgnoreCase(correctAnswer)) {
				context.getForWhom().sendRawMessage(ChatUtil.color("&a&lVraag " + (i+1) + " &fis juist!"));
			} else {
				context.getForWhom().sendRawMessage(ChatUtil.color("&a&lVraag " + (i+1) + " &fis helaas niet juist. Het juiste antwoord is &2" + correctAnswer + "&a."));
			}
		}

		int score = EconomyConvo.score;
		int total = EconomyConvo.total;

		context.getForWhom().sendRawMessage(ChatUtil.color("&r"));
		context.getForWhom().sendRawMessage(ChatUtil.color("&a&lM. ???: &fJe hebt een score van " + score + "/" + total + ". " + ((score > 2) ? "Goed gedaan!" : "Nog een beetje oefenen!")));
		EconomyConvo.playerAnswers.remove((Player) context.getForWhom());

		PlayerManager.getPlayer((Player) context.getForWhom()).setCurrentLecture("Exploring");
		EconomyConvo.score = 0;
		EconomyConvo.total = 0;
		return END_OF_CONVERSATION;
	}
}