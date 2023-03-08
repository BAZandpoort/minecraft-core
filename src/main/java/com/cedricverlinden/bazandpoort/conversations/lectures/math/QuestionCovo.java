package com.cedricverlinden.bazandpoort.conversations.lectures.math;

import com.cedricverlinden.bazandpoort.Core;
import com.cedricverlinden.bazandpoort.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.ValidatingPrompt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class QuestionCovo extends ValidatingPrompt {

	@Override
	public @NotNull String getPromptText(@NotNull ConversationContext context) {
		return ChatUtil.color("&8[&5OEFENING&8] &a5 + 5 = ?");
	}

	@Override
	protected boolean isInputValid(@NotNull ConversationContext context, @NotNull String input) {
		return input.equals("10");
	}

	@Override
	protected @Nullable Prompt acceptValidatedInput(@NotNull ConversationContext context, @NotNull String input) {
		context.getForWhom().sendRawMessage(ChatUtil.color("&8[&5WISKUDE LEERKRACHT&8] &fGefeliciteerd &a{name}&f, je hebt de vraag &ajuist&f!"));
		Bukkit.getScheduler().runTaskLater(Core.core(), () -> {
			context.getForWhom().sendRawMessage(ChatUtil.color("&7&oDat was het voor nu, er zullen binnenkort meer vragen worden toegevoegd."));
		}, 20);
		return END_OF_CONVERSATION;
	}
}
