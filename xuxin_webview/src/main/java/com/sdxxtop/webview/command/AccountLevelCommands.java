package com.sdxxtop.webview.command;

import com.sdxxtop.webview.utils.WebConstants;

public class AccountLevelCommands extends Commands {

    public AccountLevelCommands() {
    }

    @Override
    int getCommandLevel() {
        return WebConstants.LEVEL_ACCOUNT;
    }

}
