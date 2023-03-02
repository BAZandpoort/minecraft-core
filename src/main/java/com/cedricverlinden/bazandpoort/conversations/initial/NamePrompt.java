package com.cedricverlinden.bazandpoort.conversations.initial;

import com.cedricverlinden.bazandpoort.Utils;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.ValidatingPrompt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NamePrompt extends ValidatingPrompt {


	@Override
	public @NotNull String getPromptText(@NotNull ConversationContext context) {
		return Utils.color("&8[&9SECRETARIAAT&8] &fHoe mogen wij jouw noemen?");
	}

	@Override
	public boolean blocksForInput(@NotNull ConversationContext context) {
		return true;
	}

	@Override
	protected boolean isInputValid(@NotNull ConversationContext context, @NotNull String input) {
		return (input.charAt(0) >= 'A' && input.charAt(0) <= 'Z') && input.length() >= 2;
	}

	@Override
	protected @Nullable String getFailedValidationText(@NotNull ConversationContext context, @NotNull String invalidInput) {
		return Utils.color("&8[&9SECRETARIAAT&8] &fVergeet niet dat je naam moet beginnen met een hoofdletter!");
	}

	@Override
	public @Nullable Prompt acceptValidatedInput(@NotNull ConversationContext context, @Nullable String input) {
		context.setSessionData("name", input);
		context.getForWhom().sendRawMessage(Utils.color("&8[&9SECRETARIAAT&8] &fTop, leuk je te ontmoeten &a" + input + "&f!"));
		return new AgePrompt();
	}
}
