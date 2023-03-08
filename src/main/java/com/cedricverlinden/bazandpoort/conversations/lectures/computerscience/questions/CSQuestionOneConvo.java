package com.cedricverlinden.bazandpoort.conversations.lectures.computerscience.questions;

import com.cedricverlinden.bazandpoort.conversations.lectures.computerscience.ComputerScienceConvo;
import com.cedricverlinden.bazandpoort.utils.ChatUtil;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CSQuestionOneConvo implements Prompt {

	String question = (String) ComputerScienceConvo.questions.keySet().toArray()[0];

	@Override
	public @NotNull String getPromptText(@NotNull ConversationContext context) {
		context.getForWhom().sendRawMessage(ChatUtil.color("&r"));
		return ChatUtil.color("&a&lVraag 1: &f" + question);
	}

	@Override
	public boolean blocksForInput(@NotNull ConversationContext context) {
		return true;
	}

	@Override
	public @Nullable Prompt acceptInput(@NotNull ConversationContext context, @Nullable String input) {
		ComputerScienceConvo.handleQuestion((Player) context.getForWhom(), question, input);
		return new CSQuestionTwoConvo();
	}
}
