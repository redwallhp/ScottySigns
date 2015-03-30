package io.github.redwallhp.scottysigns;


import org.bukkit.*;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.lang.Integer;
import java.util.*;

public final class ScottySigns extends JavaPlugin implements Listener {



    YamlConfiguration signStore;
    List<Map<?,?>> signs = new ArrayList<Map<?,?>>();



    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
        this.loadSignStore();
    }



    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {


        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You must be a player to do that.");
            return true;
        }

        Player player = (Player) sender;


        if (!player.hasPermission("scottysigns.admin")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to do that.");
            return true;
        }


        if (cmd.getName().equalsIgnoreCase("scottysign-set")) {

            Block block = player.getTargetBlock((HashSet<Byte>) null, 5);

            if (args.length != 4) {
                sender.sendMessage(ChatColor.RED + "Usage: /scottysign-set <world> <x> <y> <z>");
                return true;
            }

            if (block.getType() != Material.SIGN_POST && block.getType() != Material.WALL_SIGN) {
                sender.sendMessage(ChatColor.RED + "You must be looking at a sign.");
                return true;
            }

            this.createScottySign(block, args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));
            sender.sendMessage(ChatColor.GOLD + "ScottySign registered");
            return true;

        }


        return false;


    }



    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {

        if (e.getAction() != Action.RIGHT_CLICK_BLOCK && e.getAction() != Action.LEFT_CLICK_BLOCK) return;
        Material blockType = e.getClickedBlock().getType();
        if (blockType != Material.SIGN_POST && blockType != Material.WALL_SIGN) return;

        Block block = e.getClickedBlock();
        Map<?,?> scottySign = getScottySign(block.getWorld().getName(), block.getX(), block.getY(), block.getZ());

        if (scottySign != null) {

            // Cancel block break
            e.setCancelled(true);

            // Lock transporter target
            World toWorld = Bukkit.getWorld(scottySign.get("targetWorld").toString());
            Integer x = ((Integer) scottySign.get("targetX"));
            Integer y = ((Integer) scottySign.get("targetY"));
            Integer z = ((Integer) scottySign.get("targetZ"));
            Location loc = new Location(toWorld, x.doubleValue(), y.doubleValue(), z.doubleValue());
            loc.setYaw(e.getPlayer().getLocation().getYaw());

            // Ensure chunk is loaded
            Chunk chunk = loc.getChunk();
            if (!chunk.isLoaded()) {
                chunk.load();
            }

            // Beam up
            e.getPlayer().teleport(loc);

        }

    }



    public Map<?,?> getScottySign(String world, Integer x, Integer y, Integer z) {
        for (Map map : this.signs) {
            if (map.get("signWorld").equals(world)) {
                if (map.get("signX").equals(x) && map.get("signY").equals(y) && map.get("signZ").equals(z)) {
                    return map;
                }
            }
        }
        return null;
    }



    public void createScottySign(Block block, String world, Integer targetX, Integer targetY, Integer targetZ) {

        Map<String, Object> newSign = new HashMap<String, Object>();
        newSign.put("signWorld", block.getWorld().getName());
        newSign.put("targetWorld", world);
        newSign.put("signX", block.getX());
        newSign.put("signY", block.getY());
        newSign.put("signZ", block.getZ());
        newSign.put("targetX", targetX);
        newSign.put("targetY", targetY);
        newSign.put("targetZ", targetZ);

        this.signs.add(newSign);
        this.signStore.set("signs", this.signs);
        this.saveSignStore();

        System.out.println(block.toString());

    }



    public void loadSignStore() {
        File f = new File(getDataFolder() + File.separator + "signs.yml");
        this.signStore = YamlConfiguration.loadConfiguration(f);
        List<Map<?,?>> signs = this.signStore.getMapList("signs");
        this.signs = signs;
    }



    public void saveSignStore() {
        try {
            File f = new File(getDataFolder() + File.separator + "signs.yml");
            this.signStore.save(f);
        } catch(IOException e) {
            Bukkit.getLogger().info("[ScottySigns] Failed to update sign store.");
        }
    }



}
