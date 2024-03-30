package com.void01.bukkit.jerm.core.command.param;

import com.void01.bukkit.jerm.api.common.player.JermPlayer;
import com.void01.bukkit.jerm.core.JermPlugin;
import com.void01.bukkit.jerm.api.common.player.JermPlayerManager;
import com.github.julyss2019.bukkit.voidframework.command.param.parser.BaseParamParser;
import com.github.julyss2019.bukkit.voidframework.command.param.parser.Response;
import org.bukkit.command.CommandSender;

public class JermPlayerParamParser extends BaseParamParser {
    private final JermPlayerManager jermPlayerManager;

    public JermPlayerParamParser(JermPlugin plugin) {
        super(new Class[]{JermPlayer.class});

        this.jermPlayerManager = plugin.getJermPlayerManager();
    }

    @Override
    public Response parse(CommandSender commandSender, Class<?> aClass, String text) {
        return Response.success(jermPlayerManager.getPlayer(text));
    }
}
