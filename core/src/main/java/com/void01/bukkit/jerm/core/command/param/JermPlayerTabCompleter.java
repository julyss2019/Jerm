package com.void01.bukkit.jerm.core.command.param;

import com.void01.bukkit.jerm.api.common.player.JermPlayer;
import com.void01.bukkit.jerm.core.player.JermPlayerImpl;
import com.void01.bukkit.jerm.core.player.JermPlayerImpl;
import com.github.julyss2019.bukkit.voidframework.command.param.tab.completer.BaseParamTabCompleter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;

import java.util.List;
import java.util.stream.Collectors;

public class JermPlayerTabCompleter extends BaseParamTabCompleter {
    public JermPlayerTabCompleter() {
        super(new Class[]{JermPlayer.class});
    }

    @Override
    public List<String> complete(CommandSender commandSender, Class<?> aClass) {
        return Bukkit.getOnlinePlayers()
                .stream()
                .map(HumanEntity::getName)
                .collect(Collectors.toList());
    }
}
