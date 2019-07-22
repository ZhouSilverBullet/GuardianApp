package com.sdxxtop.webview.command;

import android.content.Context;

import java.util.Map;

public interface Command {
    String COMMAND_UPDATE_TITLE = "xuxin_webview_update_title";
    String COMMAND_UPDATE_TITLE_PARAMS_TITLE = "xuxin_webview_update_title_params_title";

    String name();

    void exec(Context context, Map params, ResultBack resultBack);
}
