package com.void01.bukkit.jerm.core.command.param;

import com.void01.bukkit.jerm.core.JermPlugin;
import com.void01.bukkit.jerm.api.common.gui.Gui;
import com.void01.bukkit.jerm.api.common.gui.GuiManager;
import com.github.julyss2019.bukkit.voidframework.command.param.tab.completer.BaseParamTabCompleter;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.stream.Collectors;

public class GuiParamTabCompleter extends BaseParamTabCompleter {
    private final GuiManager guiManager;

    public GuiParamTabCompleter(JermPlugin jermPlugin) {
        super(new Class[]{Gui.class});

        this.guiManager = jermPlugin.getGuiManager();
    }

    @Override
    public List<String> complete(CommandSender sender, Class<?> clazz) {
        return guiManager.getGuis()
                .stream()
                .map(Gui::getId)
                .collect(Collectors.toList());
    }
}
