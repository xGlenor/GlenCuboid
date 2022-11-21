package pl.gduraj.glencuboid.managers;

import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.checkerframework.checker.nullness.qual.NonNull;
import pl.gduraj.glencuboid.GlenCuboid;

public class MiniMessageManager {

    private GlenCuboid plugin;
    private MiniMessage miniMessage;
    private BukkitAudiences audiences;

    public MiniMessageManager(){
        plugin = GlenCuboid.getInstance();

    }

    public @NonNull BukkitAudiences adventure() {
        if(this.audiences == null) {
            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
        }
        return this.audiences;
    }
    public void enableMiniMessage(){
        audiences = BukkitAudiences.create(plugin);
        register();
    }

    public void disableMiniMessage(){
        if(audiences != null){
           audiences.close();
           audiences = null;
        }
    }

    private void register(){
        miniMessage = MiniMessage.builder()
                .tags(TagResolver.standard())
                .build();
    }

    public MiniMessage getMiniMessage() {
        return miniMessage;
    }

    public BukkitAudiences getAudiences() {
        return audiences;
    }
}
