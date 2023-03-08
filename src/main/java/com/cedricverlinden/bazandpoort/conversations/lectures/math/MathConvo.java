package com.cedricverlinden.bazandpoort.conversations.lectures.math;

import com.cedricverlinden.bazandpoort.Core;
import org.bukkit.conversations.Conversation;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MathConvo extends Conversation {

	public MathConvo(@NotNull Player forWhom) {
		super(Core.core(), forWhom, new MathReadyConvo());
		setLocalEchoEnabled(false);
	}
}
