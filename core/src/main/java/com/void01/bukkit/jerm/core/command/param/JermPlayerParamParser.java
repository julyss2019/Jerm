package com.void01.bukkit.jerm.core.command.param;

import com.github.julyss2019.bukkit.voidframework.command.param.parser.BaseParamParser;
import com.github.julyss2019.bukkit.voidframework.command.param.parser.Response;
import com.void01.bukkit.jerm.api.common.player.JermPlayer;
import com.void01.bukkit.jerm.api.common.player.JermPlayerManager;
import com.void01.bukkit.jerm.core.JermPlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JermPlayerParamParser extends BaseParamParser {
    private final JermPlayerManager jermPlayerManager;

    public JermPlayerParamParser(JermPlugin plugin) {
        super(new Class[]{JermPlayer.class});

        this.jermPlayerManager = plugin.getJermPlayerManager();
    }

    @Override
    public Response parse(CommandSender commandSender, Class<?> aClass, String text) {
        @SuppressWarnings("deprecation") Player player = Bukkit.getPlayer(text);

        if (player == null) {
            return Response.failure("玩家不在线");
        }

        return Response.success(jermPlayerManager.getJermPlayer(player));
    }
}
