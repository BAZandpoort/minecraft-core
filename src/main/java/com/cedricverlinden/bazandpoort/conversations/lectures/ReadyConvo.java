package com.cedricverlinden.bazandpoort.conversations.lectures;

import com.cedricverlinden.bazandpoort.utils.ChatUtil;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.ValidatingPrompt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ReadyConvo extends ValidatingPrompt {

	@Override
	public @NotNull String getPromptText(@NotNull ConversationContext context) {
		context.getForWhom().sendRawMessage(ChatUtil.color("&8[&5WISKUNDE LEERKRACHT&8] &fHey &a{name}&f, ben je klaar om te oefenen?"));
		return ChatUtil.color("&7&o(antwoord met \"Ja\" of \"Nee\")");
	}

	@Override
	public boolean blocksForInput(@NotNull ConversationContext context) {
		return true;
	}

	@Override
	protected boolean isInputValid(@NotNull ConversationContext context, @NotNull String input) {
		input = input.toLowerCase();
		return (input.equals("ja") || input.equals("nee"));
	}

	@Override
	protected @Nullable Prompt acceptValidatedInput(@NotNull ConversationContext context, @NotNull String input) {
		switch (input) {
			case "ja" -> {
				return new QuestionCovo();
			}

			case "nee" -> {
				context.getForWhom().sendRawMessage(ChatUtil.color("&8[&5WISKUNDE LEERKRACHT&8] &fJe hebt de oefening beëindig."));
				return END_OF_CONVERSATION;
			}
		};
		return null;
	}
}
