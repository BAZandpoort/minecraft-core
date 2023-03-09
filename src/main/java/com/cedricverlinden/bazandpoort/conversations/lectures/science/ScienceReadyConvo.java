package com.cedricverlinden.bazandpoort.conversations.lectures.science;

import com.cedricverlinden.bazandpoort.conversations.lectures.science.questions.ScienceQuestionOneConvo;
import com.cedricverlinden.bazandpoort.managers.PlayerManager;
import com.cedricverlinden.bazandpoort.utils.ChatUtil;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.ValidatingPrompt;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ScienceReadyConvo extends ValidatingPrompt {

	@Override
	public @NotNull String getPromptText(@NotNull ConversationContext context) {
		String customName = PlayerManager.getPlayer((Player) context.getForWhom()).getCustomName();
		return ChatUtil.color("&a&lM. ???: &fHey " + customName + ", ben je klaar om te oefenen? " +
				"&7&o(antwoord met \"Ja\" of \"Nee\")");
	}

	@Override
	public boolean blocksForInput(@NotNull ConversationContext context) {
		return true;
	}

	@Override
	protected boolean isInputValid(@NotNull ConversationContext context, @NotNull String input) {
		return (input.trim().equalsIgnoreCase("ja") || input.trim().equalsIgnoreCase("nee"));
	}

	@Override
	protected @Nullable Prompt acceptValidatedInput(@NotNull ConversationContext context, @NotNull String input) {
		switch (input.toLowerCase()) {
			case "ja" -> {
				PlayerManager.getPlayer((Player) context.getForWhom()).setCurrentLecture("sciences");
				return new ScienceQuestionOneConvo();
			}

			case "nee" -> {
				context.getForWhom().sendRawMessage(ChatUtil.color("&r"));
				context.getForWhom().sendRawMessage(ChatUtil.color("&a&lM. ???: &fSpijtig, tot de volgende keer!"));
				return END_OF_CONVERSATION;
			}
		}
		;

		return null;
	}
}