package org.plugin.tovymsg;

import org.bukkit.plugin.java.JavaPlugin;

public final class TovyMsg extends JavaPlugin {

    @Override
    public void onEnable() {
        MsgSystem msgSystem = new MsgSystem();
        System.out.println("MsgSystem started up!");
        getCommand("reply").setExecutor(msgSystem.getReplyCommand());
        getCommand("message").setExecutor(this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
