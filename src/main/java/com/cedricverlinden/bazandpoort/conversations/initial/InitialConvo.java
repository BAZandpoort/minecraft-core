package com.cedricverlinden.bazandpoort.conversations.initial;

import com.cedricverlinden.bazandpoort.Core;
import org.bukkit.conversations.Conversation;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class InitialConvo extends Conversation {

	public InitialConvo(@NotNull Player forWhom) {
		super(Core.instance(), forWhom, new NamePrompt());
		setLocalEchoEnabled(false);
	}
}
