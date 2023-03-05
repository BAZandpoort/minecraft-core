package com.cedricverlinden.bazandpoort.conversations.lectures;

import com.cedricverlinden.bazandpoort.Core;
import org.bukkit.conversations.Conversation;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MathConvo extends Conversation {

	public MathConvo(@NotNull Player forWhom) {
		super(Core.core(), forWhom, new ReadyConvo());
		setLocalEchoEnabled(false);
	}
}
