package pl.gduraj.glencuboid.listeners;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import pl.gduraj.glencuboid.GlenCuboid;
import pl.gduraj.glencuboid.cuboid.Cuboid;

public class BlockListener implements Listener {

    private GlenCuboid plugin;

    public BlockListener(){
        this.plugin = GlenCuboid.getInstance();
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBreak(BlockBreakEvent event){
        if(event.isCancelled()) return;

        Block block = event.getBlock();
        Player player = event.getPlayer();

        if(block == null || player == null){
            return;
        }

        if(!player.hasPermission("glencuboid.bypass.destroy")){
            boolean isAllowBlock = false;

            Cuboid cuboid = plugin.getCuboidManager().getByLoc(block.getLocation());

            if(cuboid != null){

            }


        }


    }



}
