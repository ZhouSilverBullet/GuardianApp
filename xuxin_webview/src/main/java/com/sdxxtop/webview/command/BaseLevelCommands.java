package com.sdxxtop.webview.command;

import com.sdxxtop.webview.utils.WebConstants;

public class BaseLevelCommands extends Commands {

    public BaseLevelCommands() {
    }

    @Override
    int getCommandLevel() {
        return WebConstants.LEVEL_BASE;
    }
}
