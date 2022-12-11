package pl.gduraj.glencuboid.enums;

import org.bukkit.Material;
import pl.gduraj.glencuboid.util.xseries.XMaterial;

import java.util.HashSet;
import java.util.Set;

public enum Flag {

    /*
        USING FLAGS
     */
    ANVIL(XMaterial.ANVIL.parseMaterial(), FlagGroup.USE, true, false),
    BED(XMaterial.WHITE_BED.parseMaterial(), FlagGroup.USE, true, false),
    BEACON(XMaterial.BEACON.parseMaterial(), FlagGroup.USE, true, false),
    BUTTONS(XMaterial.STONE_BUTTON.parseMaterial(), FlagGroup.USE, true, false),
    CRAFTING(XMaterial.CRAFTING_TABLE.parseMaterial(), FlagGroup.USE, true, false),
    REDSTONE(XMaterial.REDSTONE_TORCH.parseMaterial(), FlagGroup.USE, true, false),
    DOORS(XMaterial.OAK_DOOR.parseMaterial(), FlagGroup.USE, true, false),
    ENCHANT(XMaterial.ENCHANTING_TABLE.parseMaterial(), FlagGroup.USE, true, false),
    LEVER(XMaterial.LEVER.parseMaterial(), FlagGroup.USE, true, false),
    NAMETAG(XMaterial.NAME_TAG.parseMaterial(), FlagGroup.USE, true, false),
    PRESSUREPLATES(XMaterial.LIGHT_WEIGHTED_PRESSURE_PLATE.parseMaterial(), FlagGroup.USE, true, false),
    SHEARS(XMaterial.SHEARS.parseMaterial(), FlagGroup.USE, true, false),
    TRADE(XMaterial.EMERALD.parseMaterial(), FlagGroup.USE, true, false),
    SPAWNEGG(XMaterial.SHEEP_SPAWN_EGG.parseMaterial(), FlagGroup.USE, true, false),


    USE(XMaterial.SHEEP_SPAWN_EGG.parseMaterial(), FlagGroup.USE, true, false),
    INVENTORY(XMaterial.SHEEP_SPAWN_EGG.parseMaterial(), FlagGroup.USE, true, false),

    ANIMALSKILLING(XMaterial.PORKCHOP.parseMaterial(), FlagGroup.DAMAGE, true, false),
    ANIMALSSPAWN(XMaterial.SHEEP_SPAWN_EGG.parseMaterial(), FlagGroup.DAMAGE, true, false),
    MONSTERKILLING(XMaterial.BONE.parseMaterial(), FlagGroup.DAMAGE, true, false),
    MONSTERSPAWN(XMaterial.ZOMBIE_SPAWN_EGG.parseMaterial(), FlagGroup.DAMAGE, true, false),
    MONSTERDAMAGE(XMaterial.ROTTEN_FLESH.parseMaterial(), FlagGroup.DAMAGE, true, false),
    VILLAGERKILLING(XMaterial.VILLAGER_SPAWN_EGG.parseMaterial(), FlagGroup.DAMAGE, true, false),
    WATERMOBSKILLING(XMaterial.PUFFERFISH.parseMaterial(), FlagGroup.DAMAGE, true, false),
    WATERMOBSSPAWN(XMaterial.SHEEP_SPAWN_EGG.parseMaterial(), FlagGroup.DAMAGE, true, false),


    ALL(null, FlagGroup.OTHERS, false, true);

    private Material icon;
    private FlagGroup flagGroup;
    private boolean change;
    private boolean enabled;
    private Set<String> groups = null;

    public enum FlagGroup {
        USE,
        DAMAGE,
        OTHERS
    }

    Flag(Material icon, FlagGroup flagGroup, boolean change, boolean enabled) {
        this.icon = icon;
        this.flagGroup = flagGroup;
        this.change = change;
        this.enabled = enabled;
    }


    public Material getIcon() {
        return icon;
    }

    public void setIcon(Material icon) {
        this.icon = icon;
    }

    public FlagGroup getFlagGroup() {
        return flagGroup;
    }

    public void setFlagGroup(FlagGroup flagGroup) {
        this.flagGroup = flagGroup;
    }

    public boolean isChange() {
        return change;
    }

    public void setChange(boolean change) {
        this.change = change;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void addGroup(String group) {
        if (this.groups == null)
            this.groups = new HashSet<>();
        this.groups.add(group.toLowerCase());
    }

    public boolean isInGroup(String group) {
        if (this.groups == null)
            return false;
        return this.groups.contains(group.toLowerCase());
    }

    public Set<String> getGroups() {
        return groups;
    }

    public void setGroups(Set<String> groups) {
        this.groups = groups;
    }

    public void resetGroups() {
        this.groups = null;
    }

    public static Flag getFlag(String flag) {
        byte b;
        int i;
        Flag[] arrayOfFlags;
        for (i = (arrayOfFlags = values()).length, b = 0; b < i; ) {
            Flag f = arrayOfFlags[b];
            if (f.toString().equalsIgnoreCase(flag))
                return f;
            b++;
        }
        return null;
    }


}
