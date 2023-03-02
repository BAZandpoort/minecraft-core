package com.cedricverlinden.bazandpoort.conversations.initial;

import com.cedricverlinden.bazandpoort.Core;
import com.cedricverlinden.bazandpoort.Utils;
import com.mojang.authlib.GameProfile;
import org.bukkit.Bukkit;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.ValidatingPrompt;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AgePrompt extends ValidatingPrompt {

	@Override
	public @NotNull String getPromptText(@NotNull ConversationContext context) {
		return Utils.color("&8[&9SECRETARIAAT&8] &fHoe oud ben je?");
	}

	@Override
	public boolean blocksForInput(@NotNull ConversationContext context) {
		return true;
	}

	@Override
	protected boolean isInputValid(@NotNull ConversationContext context, @NotNull String s) {
		int age;
		try {
			age = Integer.parseInt(s);
		} catch (NumberFormatException exception) {
			context.getForWhom().sendRawMessage(Utils.color("&8[&9SECRETARIAAT&8] &c'" + s + "' is geen nummer. Probeer het opnieuw."));
			return false;
		}

		if (!(age >= 8 && age <= 18)) {
			context.getForWhom().sendRawMessage(Utils.color("&8[&9SECRETARIAAT&8] &c'" + s + "' zit niet binnen de leeftijdscategorie. Probeer het opnieuw."));
			return false;
		}

		return true;
	}

	@Override
	public @Nullable Prompt acceptValidatedInput(@NotNull ConversationContext context, @Nullable String input) {
		context.setSessionData("age", Integer.parseInt(input));
		context.getForWhom().sendRawMessage(Utils.color("&8[&9SECRETARIAAT&8] &fDankjewel &a" + context.getSessionData("name") + "&f, als ik het goed heb gelezen ben je &a" + input + " jaar &fjong."));

		Bukkit.getScheduler().runTaskLater(Core.instance(), () -> Bukkit.getServer().broadcastMessage(Utils.color(Core.getMessages().getEditableFile().getString("join-message").replace("$player", context.getSessionData("name").toString()))), 20);
		return END_OF_CONVERSATION;
	}
}
