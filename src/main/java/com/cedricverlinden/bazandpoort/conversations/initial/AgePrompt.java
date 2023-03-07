package com.cedricverlinden.bazandpoort.conversations.initial;

import com.cedricverlinden.bazandpoort.Core;
import com.cedricverlinden.bazandpoort.managers.PlayerManager;
import com.cedricverlinden.bazandpoort.utils.ChatUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.ValidatingPrompt;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AgePrompt extends ValidatingPrompt {

	@Override
	public @NotNull String getPromptText(@NotNull ConversationContext context) {
		return ChatUtil.color("&8[&9SECRETARIAAT&8] &fHoe oud ben je?");
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
			context.getForWhom().sendRawMessage(ChatUtil.color("&8[&9SECRETARIAAT&8] &c'" + s + "' is geen nummer. Probeer het opnieuw."));
			return false;
		}

		if (!(age >= 8 && age <= 18)) {
			context.getForWhom().sendRawMessage(ChatUtil.color("&8[&9SECRETARIAAT&8] &c'" + s + "' zit niet binnen de leeftijdscategorie. Probeer het opnieuw."));
			return false;
		}

		return true;
	}

	@Override
	public @Nullable Prompt acceptValidatedInput(@NotNull ConversationContext context, @Nullable String input) {
		Player player = (Player) context.getForWhom();

		String customName = context.getSessionData("name").toString();
		int age = (!(input == null)) ? Integer.parseInt(input) : -1;

		new PlayerManager(player, customName, age);

		context.getForWhom().sendRawMessage(ChatUtil.color("&8[&9SECRETARIAAT&8] &fDankjewel &a" + customName + "&f, " +
				"als ik het goed heb gelezen ben je &a" + input + " jaar &fjong."));

		player.playerListName(Component.text(customName));
		player.displayName(Component.text(customName));

		Bukkit.getScheduler().runTaskLater(Core.core(), () -> Bukkit.getServer().broadcastMessage(ChatUtil.color(Core.instance().getMessages().getEditableFile().getString("join-message").replace("$player", customName))), 20);
		return END_OF_CONVERSATION;
	}
}
