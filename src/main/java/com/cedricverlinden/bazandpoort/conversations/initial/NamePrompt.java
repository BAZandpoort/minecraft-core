package com.cedricverlinden.bazandpoort.conversations.initial;

import com.cedricverlinden.bazandpoort.utils.ChatUtils;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.ValidatingPrompt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NamePrompt extends ValidatingPrompt {

	@Override
	public @NotNull String getPromptText(@NotNull ConversationContext context) {
		return ChatUtils.color("&8[&9SECRETARIAAT&8] &fHoe mogen wij jouw noemen?");
	}

	@Override
	public boolean blocksForInput(@NotNull ConversationContext context) {
		return true;
	}

	@Override
	protected boolean isInputValid(@NotNull ConversationContext context, @NotNull String input) {
		if (!(input.charAt(0) >= 'A' && input.charAt(0) <= 'Z')) {
			context.getForWhom().sendRawMessage(ChatUtils.color("&8[&9SECRETARIAAT&8] &cJe naam moet beginnen met een hoofdletter."));
			return false;
		}

		if (!(input.length() >= 2)) {
			context.getForWhom().sendRawMessage(ChatUtils.color("&8[&9SECRETARIAAT&8] &cJe naam moet minstens 2 letters bevatten."));
			return false;
		}

		if (input.length() > 20) {
			context.getForWhom().sendRawMessage(ChatUtils.color("&8[&9SECRETARIAAT&8] &cJe naam mag een maximum 20 letters bevatten."));
			return false;
		}

		return true;
	}

	@Override
	public @Nullable Prompt acceptValidatedInput(@NotNull ConversationContext context, @Nullable String input) {
		context.setSessionData("name", input);
		context.getForWhom().sendRawMessage(ChatUtils.color("&8[&9SECRETARIAAT&8] &fTop, leuk je te ontmoeten &a" + input + "&f!"));
		return new AgePrompt();
	}
}
