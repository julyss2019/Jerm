package com.void01.bukkit.jerm.core.command.param;

import com.github.julyss2019.bukkit.voidframework.command.param.parser.BaseParamParser;
import com.github.julyss2019.bukkit.voidframework.command.param.parser.Response;
import com.void01.bukkit.jerm.core.JermPlugin;
import com.void01.bukkit.jerm.api.common.gui.Gui;
import com.void01.bukkit.jerm.api.common.gui.GuiManager;
import org.bukkit.command.CommandSender;

public class GuiParamParser extends BaseParamParser {
    private final GuiManager guiManager;

    public GuiParamParser(JermPlugin jermPlugin) {
        super(new Class[]{Gui.class});

        this.guiManager = jermPlugin.getGuiManager();
    }

    @Override
    public Response parse(CommandSender sender, Class<?> clazz, String text) {
        Gui gui = guiManager.getGui(text);

        if (gui == null) {
            return Response.failure("界面不存在");
        }

        return Response.success(gui);
    }
}
