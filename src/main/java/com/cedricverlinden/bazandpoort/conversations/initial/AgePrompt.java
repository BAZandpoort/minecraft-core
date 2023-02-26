package com.cedricverlinden.bazandpoort.conversations.initial;

import com.cedricverlinden.bazandpoort.Utils;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.NumericPrompt;
import org.bukkit.conversations.Prompt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AgePrompt extends NumericPrompt {

	@Override
	public @NotNull String getPromptText(@NotNull ConversationContext context) {
		return Utils.color("&8[&2DIRECTEUR&8] &fHoe jong ben je?");
	}

	@Override
	public boolean blocksForInput(@NotNull ConversationContext context) {
		return true;
	}

	@Override
	protected @Nullable String getInputNotNumericText(@NotNull ConversationContext context, @NotNull String invalidInput) {
		return Utils.color("&8[&2DIRECTEUR&8] &fHelaas is '" + invalidInput + "' geen getal.");
	}

	@Override
	public @Nullable Prompt acceptValidatedInput(@NotNull ConversationContext context, @Nullable Number input) {
		context.setSessionData("age", input.intValue());
		context.getForWhom().sendRawMessage(Utils.color("&8[&2DIRECTEUR&8] &fDankjewel &a" + context.getSessionData("name") + "&f, als ik het goed heb gelezen ben je &a" + input + " jaar &fjong."));
		return END_OF_CONVERSATION;
	}
}
