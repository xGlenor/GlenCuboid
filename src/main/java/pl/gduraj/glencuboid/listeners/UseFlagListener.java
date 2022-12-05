package pl.gduraj.glencuboid.listeners;

import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantInventory;
import org.bukkit.inventory.meta.SpawnEggMeta;
import pl.gduraj.glencuboid.GlenCuboid;
import pl.gduraj.glencuboid.cuboid.Cuboid;
import pl.gduraj.glencuboid.enums.Flag;
import pl.gduraj.glencuboid.util.ChatUtil;
import pl.gduraj.glencuboid.util.xseries.XMaterial;

public class UseFlagListener implements Listener {

    private final GlenCuboid plugin;

    public UseFlagListener() {
        this.plugin = GlenCuboid.getInstance();
    }


    @EventHandler
    public void onInterract(PlayerInteractEvent event) {


        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        ItemStack ismain = player.getInventory().getItemInMainHand();
        ItemStack isoff = player.getInventory().getItemInOffHand();

        if (block == null || block.getType() == null || player == null) return;


        if (!player.hasPermission("glencuboid.bypass.use")) {
            Cuboid cub = plugin.getCuboidManager().getEnabledSource(block.getLocation(), Flag.USE);
            if (cub != null) {
                if (cub.getFlags().isPreventMaterial(block.getType())) {
                    if (!plugin.getCuboidManager().checkPerms(cub, player.getName(), Flag.USE)) {
                        event.setCancelled(true);
                        ChatUtil.sendMSGColor(player, "<rainbow>nie masz dostępu USE");
                        return;
                    }
                }
            }
        }

        if (block.getType().equals(XMaterial.CRAFTING_TABLE.parseMaterial())) {
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

        if (Tag.DOORS.isTagged(block.getType())) {
            if (!player.hasPermission("glencuboid.bypass.use")) {
                Cuboid cuboid = plugin.getCuboidManager().getEnabledSource(block.getLocation(), Flag.DOORS);
                if (cuboid != null) {
                    if (!plugin.getCuboidManager().checkPerms(cuboid, player.getName(), Flag.DOORS)) {
                        event.setCancelled(true);
                        ChatUtil.sendMSGColor(player, "<rainbow>nie masz dostępu DOORS");
                        return;
                    }
                }
            }
        }

        if (block.getType().equals(XMaterial.BEACON.parseMaterial())) {
            if (!player.hasPermission("glencuboid.bypass.use")) {
                Cuboid cuboid = plugin.getCuboidManager().getEnabledSource(block.getLocation(), Flag.BEACON);
                if (cuboid != null) {
                    if (!plugin.getCuboidManager().checkPerms(cuboid, player.getName(), Flag.BEACON)) {
                        event.setCancelled(true);
                        ChatUtil.sendMSGColor(player, "<rainbow>nie masz dostępu BEACON");
                        return;
                    }
                }
            }
        }

        if (block.getType().equals(XMaterial.ENCHANTING_TABLE.parseMaterial())) {
            if (!player.hasPermission("glencuboid.bypass.use")) {
                Cuboid cuboid = plugin.getCuboidManager().getEnabledSource(block.getLocation(), Flag.ENCHANT);
                if (cuboid != null) {
                    if (!plugin.getCuboidManager().checkPerms(cuboid, player.getName(), Flag.ENCHANT)) {
                        event.setCancelled(true);
                        ChatUtil.sendMSGColor(player, "<rainbow>nie masz dostępu ENCHANT");
                        return;
                    }
                }
            }
        }

        if (block.getType().equals(XMaterial.SHEARS.parseMaterial())) {
            if (!player.hasPermission("glencuboid.bypass.use")) {
                Cuboid cuboid = plugin.getCuboidManager().getEnabledSource(block.getLocation(), Flag.SHEARS);
                if (cuboid != null) {
                    if (!plugin.getCuboidManager().checkPerms(cuboid, player.getName(), Flag.SHEARS)) {
                        event.setCancelled(true);
                        ChatUtil.sendMSGColor(player, "<rainbow>nie masz dostępu SHEARS");
                        return;
                    }
                }
            }
        }

        if (block.getType().equals(XMaterial.LEVER.parseMaterial())) {
            if (!player.hasPermission("glencuboid.bypass.use")) {
                Cuboid cuboid = plugin.getCuboidManager().getEnabledSource(block.getLocation(), Flag.LEVER);
                if (cuboid != null) {
                    if (!plugin.getCuboidManager().checkPerms(cuboid, player.getName(), Flag.LEVER)) {
                        event.setCancelled(true);
                        ChatUtil.sendMSGColor(player, "<rainbow>nie masz dostępu LEVER");
                        return;
                    }
                }
            }
        }

        if (Tag.BUTTONS.isTagged(block.getType())) {
            if (!player.hasPermission("glencuboid.bypass.use")) {
                Cuboid cuboid = plugin.getCuboidManager().getEnabledSource(block.getLocation(), Flag.BUTTONS);
                if (cuboid != null) {
                    if (!plugin.getCuboidManager().checkPerms(cuboid, player.getName(), Flag.BUTTONS)) {
                        event.setCancelled(true);
                        ChatUtil.sendMSGColor(player, "<rainbow>nie masz dostępu BUTTONS");
                        return;
                    }
                }
            }
        }

        if (Tag.PRESSURE_PLATES.isTagged(block.getType())) {
            if (!player.hasPermission("glencuboid.bypass.use")) {
                Cuboid cuboid = plugin.getCuboidManager().getEnabledSource(block.getLocation(), Flag.PRESSUREPLATES);
                if (cuboid != null) {
                    if (!plugin.getCuboidManager().checkPerms(cuboid, player.getName(), Flag.PRESSUREPLATES)) {
                        event.setCancelled(true);
                        ChatUtil.sendMSGColor(player, "<rainbow>nie masz dostępu PRESSURE PLATES");
                        return;
                    }
                }
            }
        }

        if (Tag.BEDS.isTagged(block.getType())) {
            if (!player.hasPermission("glencuboid.bypass.use")) {
                Cuboid cuboid = plugin.getCuboidManager().getEnabledSource(block.getLocation(), Flag.BED);
                if (cuboid != null) {
                    if (!plugin.getCuboidManager().checkPerms(cuboid, player.getName(), Flag.BED)) {
                        event.setCancelled(true);
                        ChatUtil.sendMSGColor(player, "<rainbow>nie masz dostępu BEDS");
                        return;
                    }
                }
            }
        }

        if (Tag.ANVIL.isTagged(block.getType())) {
            if (!player.hasPermission("glencuboid.bypass.use")) {
                Cuboid cuboid = plugin.getCuboidManager().getEnabledSource(block.getLocation(), Flag.ANVIL);
                if (cuboid != null) {
                    if (!plugin.getCuboidManager().checkPerms(cuboid, player.getName(), Flag.ANVIL)) {
                        event.setCancelled(true);
                        ChatUtil.sendMSGColor(player, "<rainbow>nie masz dostępu ANVIL");
                        return;
                    }
                }
            }
        }

        if ((ismain.getItemMeta() instanceof SpawnEggMeta) || (isoff.getItemMeta() instanceof SpawnEggMeta)) {
            if (!player.hasPermission("glencuboid.bypass.use")) {
                Cuboid cuboid = plugin.getCuboidManager().getEnabledSource(block.getLocation(), Flag.SPAWNEGG);
                if (cuboid != null) {
                    if (!plugin.getCuboidManager().checkPerms(cuboid, player.getName(), Flag.SPAWNEGG)) {
                        event.setCancelled(true);
                        ChatUtil.sendMSGColor(player, "<rainbow>nie masz dostępu SPAWNEGG");
                    }
                }
            }
        }

    }

    @EventHandler
    public void onTradeVillager(PlayerInteractAtEntityEvent ev) {
        if (ev.isCancelled()) return;

        Player player = ev.getPlayer();
        Entity entity = ev.getRightClicked();
        ItemStack ismain = player.getInventory().getItemInOffHand();
        ItemStack isoff = player.getInventory().getItemInOffHand();

        if (entity instanceof Villager) {
            if (((Villager) entity).getInventory() instanceof MerchantInventory) {
                if (!player.hasPermission("glencuboid.bypass.use")) {
                    Cuboid cuboid = plugin.getCuboidManager().getEnabledSource(entity.getLocation(), Flag.TRADE);

                    if (cuboid != null) {
                        if (!plugin.getCuboidManager().checkPerms(cuboid, player.getName(), Flag.TRADE)) {
                            ev.setCancelled(true);
                            ChatUtil.sendMSGColor(player, "<rainbow>nie masz dostepu TRADE");
                            return;
                        }
                    }
                }
            }
        }

        if ((ismain != null) || (isoff != null)) {
            if (ismain.getType().equals(XMaterial.NAME_TAG.parseMaterial()) ||
                    isoff.getType().equals(XMaterial.NAME_TAG.parseMaterial())) {
                if (!player.hasPermission("glencuboid.bypass.use")) {
                    Cuboid cuboid = plugin.getCuboidManager().getEnabledSource(entity.getLocation(), Flag.NAMETAG);

                    if (cuboid != null) {
                        if (!plugin.getCuboidManager().checkPerms(cuboid, player.getName(), Flag.NAMETAG)) {
                            ev.setCancelled(true);
                            ChatUtil.sendMSGColor(player, "<rainbow>nie masz dostepu NAME TAG");
                        }
                    }
                }
            }
        }


    }

}
