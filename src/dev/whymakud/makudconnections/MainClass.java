package dev.whymakud.makudconnections;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class MainClass extends JavaPlugin implements Listener, PluginMessageListener {
	
   public void onEnable() {
      saveDefaultConfig();
      Bukkit.getPluginManager().registerEvents(this, this);
      Bukkit.getConsoleSender().sendMessage("[§bMakudConnections§r] by §cwhymakud.github.io §renabled!");
      
      getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
      getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);
      getServer().getPluginManager().registerEvents(this, this);
   }

   @EventHandler
   public void onPlayerCommand(PlayerCommandPreprocessEvent e) {
      String cmd = e.getMessage().split(" ")[0].substring(1);
      if (this.getConfig().contains(cmd)) {
         Player player = e.getPlayer();
         ByteArrayDataOutput dataOutput = ByteStreams.newDataOutput();
         dataOutput.writeUTF("Connect");
         dataOutput.writeUTF(getConfig().getString(cmd));
         player.sendPluginMessage(this, "BungeeCord", dataOutput.toByteArray());
         Bukkit.getConsoleSender().sendMessage("[§bMakudConnections§r] §fPlayer: §e" + player.getName() + " §f==> Server: §e" + getConfig().getString(cmd));
         e.setCancelled(true);     
      }
   }
      @EventHandler
      public void onPlayerReloadCommand(PlayerCommandPreprocessEvent e) {
         if (e.getMessage().toLowerCase().startsWith("/makudconnections reload") || e.getMessage().toLowerCase().startsWith("/mconnections reload")) {
            if (e.getPlayer().hasPermission("makudconnections.reload")) {
           	 e.getPlayer().sendMessage("§7[§bMakudConnections§7] §fby §cwhymakud.github.io §freloaded!");
           	 reloadConfig();
			} else {
	          e.getPlayer().sendMessage("§7[§bMakudConnections§7] §fby §cwhymakud.github.io §fnot rebooted, you §cdo not have§f permissions!");
			}
            e.setCancelled(true);
         }
      
   }

@Override
public void onPluginMessageReceived(String arg0, Player arg1, byte[] arg2) {
	// TODO Auto-generated method stub
	
}
}