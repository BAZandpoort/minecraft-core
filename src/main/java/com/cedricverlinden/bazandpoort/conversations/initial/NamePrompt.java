package com.cedricverlinden.bazandpoort.conversations.initial;

import com.cedricverlinden.bazandpoort.utils.ChatUtil;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.ValidatingPrompt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NamePrompt extends ValidatingPrompt {

	@Override
	public @NotNull String getPromptText(@NotNull ConversationContext context) {
		return ChatUtil.color("&9&lM. Seymons: &fHoe mogen wij jou noemen?");
	}

	@Override
	public boolean blocksForInput(@NotNull ConversationContext context) {
		return true;
	}

	@Override
	protected boolean isInputValid(@NotNull ConversationContext context, @NotNull String input) {
		if (!(input.charAt(0) >= 'A' && input.charAt(0) <= 'Z')) {
			context.getForWhom().sendRawMessage(ChatUtil.color("&9&lM. Seymons: &cJe naam moet beginnen met een hoofdletter."));
			return false;
		}

		if (!(input.length() >= 2)) {
			context.getForWhom().sendRawMessage(ChatUtil.color("&9&lM. Seymons: &cJe naam moet minstens 2 letters bevatten."));
			return false;
		}

		if (input.length() > 16) {
			context.getForWhom().sendRawMessage(ChatUtil.color("&9&lM. Seymons: &cJe naam mag eenmaximum 16 letters bevatten."));
			return false;
		}

		return true;
	}

	@Override
	public @Nullable Prompt acceptValidatedInput(@NotNull ConversationContext context, @Nullable String input) {
		context.setSessionData("name", input);
		context.getForWhom().sendRawMessage(ChatUtil.color("&9&lM. Seymons: &fTop, leuk je te ontmoeten &a" + input + "&f!"));
		return new AgePrompt();
	}
}
