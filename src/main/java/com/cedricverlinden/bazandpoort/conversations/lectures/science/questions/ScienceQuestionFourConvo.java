package com.cedricverlinden.bazandpoort.conversations.lectures.science.questions;

import com.cedricverlinden.bazandpoort.conversations.lectures.science.ScienceConvo;
import com.cedricverlinden.bazandpoort.utils.ChatUtil;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ScienceQuestionFourConvo implements Prompt {

	String question = (String) ScienceConvo.questions.keySet().toArray()[3];

	@Override
	public @NotNull String getPromptText(@NotNull ConversationContext context) {
		context.getForWhom().sendRawMessage(ChatUtil.color("&r"));
		return ChatUtil.color("&a&lVraag 4: &f" + question);
	}

	@Override
	public boolean blocksForInput(@NotNull ConversationContext context) {
		return true;
	}

	@Override
	public @Nullable Prompt acceptInput(@NotNull ConversationContext context, @Nullable String input) {
		ScienceConvo.handleQuestion((Player) context.getForWhom(), question, input);
		return new ScienceQuestionFiveConvo();
	}
}