package pl.gduraj.glencuboid.listeners;

import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import pl.gduraj.glencuboid.GlenCuboid;
import pl.gduraj.glencuboid.cuboid.Cuboid;
import pl.gduraj.glencuboid.cuboid.Flag;
import pl.gduraj.glencuboid.util.ChatUtil;
import pl.gduraj.glencuboid.util.xseries.MaterialGroups;
import pl.gduraj.glencuboid.util.xseries.XMaterial;

public class UseFlagListener implements Listener {

    private GlenCuboid plugin;

    public UseFlagListener(){
        this.plugin = GlenCuboid.getInstance();
    }


    @EventHandler
    public void onInterract(PlayerInteractEvent event) {


        Player player = event.getPlayer();
        Block block = event.getClickedBlock();

        if(block == null || block.getType() == null || player == null) return;


        if(!player.hasPermission("glencuboid.bypass.use")){
            Cuboid cub = plugin.getCuboidManager().getEnabledSource(block.getLocation(), Flag.USE);
            if(cub != null){

                if(!plugin.getCuboidManager().checkPerms(cub, player.getName(), Flag.USE)){
                    event.setCancelled(true);
                    ChatUtil.sendMSGColor(player, "<rainbow>nie masz dostępu USE");
                    return;
                }
            }
        }

        if (block.getType().equals(XMaterial.CRAFTING_TABLE.parseMaterial())){
            if (!player.hasPermission("glencuboid.bypass.use")) {
                Cuboid cuboid = plugin.getCuboidManager().getEnabledSource(block.getLocation(), Flag.CRAFTING);
                if (cuboid != null) {
                    if (!plugin.getCuboidManager().checkPerms(cuboid, player.getName(), Flag.CRAFTING)) {
                        event.setCancelled(true);
                        ChatUtil.sendMSGColor(player, "<rainbow>nie masz dostępu CRAFTING");
                        return;
                    }
                }

            }
        }

        if(Tag.DOORS.isTagged(block.getType())){
            if(!player.hasPermission("glencuboid.bypass.use")) {
                Cuboid cuboid = plugin.getCuboidManager().getEnabledSource(block.getLocation(), Flag.DOORS);
                if(cuboid != null){
                    if(!plugin.getCuboidManager().checkPerms(cuboid, player.getName(), Flag.DOORS)){
                        event.setCancelled(true);
                        ChatUtil.sendMSGColor(player, "<rainbow>nie masz dostępu DOORS");
                        return;
                    }
                }
            }
        }

        if(block.getType().equals(XMaterial.BEACON.parseMaterial())){
            if(!player.hasPermission("glencuboid.bypass.use")){
                Cuboid cuboid = plugin.getCuboidManager().getEnabledSource(block.getLocation(), Flag.BEACON);
                if(cuboid != null){
                    if(!plugin.getCuboidManager().checkPerms(cuboid, player.getName(), Flag.BEACON)){
                        event.setCancelled(true);
                        ChatUtil.sendMSGColor(player, "<rainbow>nie masz dostępu BEACON");
                        return;
                    }
                }
            }
        }

        if(block.getType().equals(XMaterial.ENCHANTING_TABLE.parseMaterial())){
            if(!player.hasPermission("glencuboid.bypass.use")){
                Cuboid cuboid = plugin.getCuboidManager().getEnabledSource(block.getLocation(), Flag.ENCHANT);
                if(cuboid != null){
                    if(!plugin.getCuboidManager().checkPerms(cuboid, player.getName(), Flag.ENCHANT)){
                        event.setCancelled(true);
                        ChatUtil.sendMSGColor(player, "<rainbow>nie masz dostępu ENCHANT");
                        return;
                    }
                }
            }
        }

        if(block.getType().equals(XMaterial.SHEARS.parseMaterial())){
            if(!player.hasPermission("glencuboid.bypass.use")){
                Cuboid cuboid = plugin.getCuboidManager().getEnabledSource(block.getLocation(), Flag.SHEARS);
                if(cuboid != null){
                    if(!plugin.getCuboidManager().checkPerms(cuboid, player.getName(), Flag.SHEARS)){
                        event.setCancelled(true);
                        ChatUtil.sendMSGColor(player, "<rainbow>nie masz dostępu SHEARS");
                        return;
                    }
                }
            }
        }

        if(block.getType().equals(XMaterial.ANVIL.parseMaterial())){
            if(!player.hasPermission("glencuboid.bypass.use")){
                Cuboid cuboid = plugin.getCuboidManager().getEnabledSource(block.getLocation(), Flag.SHEARS);
                if(cuboid != null){
                    if(!plugin.getCuboidManager().checkPerms(cuboid, player.getName(), Flag.SHEARS)){
                        event.setCancelled(true);
                        ChatUtil.sendMSGColor(player, "<rainbow>nie masz dostępu SHEARS");
                        return;
                    }
                }
            }
        }

    }

}
