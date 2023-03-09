package com.cedricverlinden.bazandpoort.conversations.lectures.computerscience;

import com.cedricverlinden.bazandpoort.conversations.lectures.computerscience.questions.CSQuestionOneConvo;
import com.cedricverlinden.bazandpoort.managers.PlayerManager;
import com.cedricverlinden.bazandpoort.utils.ChatUtil;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.ValidatingPrompt;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CSReadyConvo extends ValidatingPrompt {

	PlayerManager playerManager;

	@Override
	public @NotNull String getPromptText(@NotNull ConversationContext context) {
		String customName = PlayerManager.getPlayer((Player) context.getForWhom()).getCustomName();
		return ChatUtil.color("&a&lM. Vermeulen: &fHey " + customName + ", ben je klaar om te oefenen? " +
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
				PlayerManager.getPlayer((Player) context.getForWhom()).setCurrentLecture("computerscience");
				return new CSQuestionOneConvo();
			}

			case "nee" -> {
				context.getForWhom().sendRawMessage(ChatUtil.color("&r"));
				context.getForWhom().sendRawMessage(ChatUtil.color("&a&lM. Vermeulen: &fSpijtig, tot de volgende keer!"));
				return END_OF_CONVERSATION;
			}
		};

		return null;
	}
}
