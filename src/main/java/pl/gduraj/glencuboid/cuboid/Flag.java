package pl.gduraj.glencuboid.cuboid;

import org.bukkit.Material;
import pl.gduraj.glencuboid.util.xseries.XMaterial;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public enum Flag {

    /*
        USING FLAGS
     */
    ANVIL(XMaterial.ANVIL.parseMaterial(), FlagGroup.USE, getConfigName("ANVIL"), getConfigDesc("ANVIL"), true, false),
    BED(XMaterial.WHITE_BED.parseMaterial(), FlagGroup.USE, getConfigName("BED"), getConfigDesc("BED"), true, false),
    BEACON(XMaterial.BEACON.parseMaterial(), FlagGroup.USE, getConfigName("BEACON"), getConfigDesc("BEACON"), true, false),
    BUTTONS(XMaterial.STONE_BUTTON.parseMaterial(), FlagGroup.USE, getConfigName("BUTTONS"), getConfigDesc("BUTTONS"), true, false),
    CRAFTING(XMaterial.CRAFTING_TABLE.parseMaterial(), FlagGroup.USE, getConfigName("CRAFTING"), getConfigDesc("CRAFTING"), true, false),
    REDSTONE(XMaterial.REDSTONE_TORCH.parseMaterial(), FlagGroup.USE, getConfigName("REDSTONE"), getConfigDesc("REDSTONE"), true, false),
    DOORS(XMaterial.OAK_DOOR.parseMaterial(), FlagGroup.USE, getConfigName("DOORS"), getConfigDesc("DOORS"), true, false),
    ENCHANT(XMaterial.ENCHANTING_TABLE.parseMaterial(), FlagGroup.USE, getConfigName("ENCHANT"), getConfigDesc("ENCHANT"), true, false),
    LEVER(XMaterial.LEVER.parseMaterial(), FlagGroup.USE, getConfigName("LEVER"), getConfigDesc("LEVER"), true, false),
    NAMETAG(XMaterial.NAME_TAG.parseMaterial(), FlagGroup.USE, getConfigName("NAMETAG"), getConfigDesc("NAMETAG"), true, false),
    PRESSUREPLATES(XMaterial.LIGHT_WEIGHTED_PRESSURE_PLATE.parseMaterial(), FlagGroup.USE, getConfigName("PRESSUREPLATES"), getConfigDesc("PRESSUREPLATES"), true, false),
    SHEARS(XMaterial.SHEARS.parseMaterial(), FlagGroup.USE, getConfigName("SHEARS"), getConfigDesc("SHEARS"), true, false),
    TRADE(XMaterial.EMERALD.parseMaterial(), FlagGroup.USE, getConfigName("TRADE"), getConfigDesc("TRADE"), true, false),
    SPAWNEGG(XMaterial.SHEEP_SPAWN_EGG.parseMaterial(), FlagGroup.USE, getConfigName("SPAWNEGG"), getConfigDesc("SPAWNEGG"), true, false),


    USE(XMaterial.SHEEP_SPAWN_EGG.parseMaterial(), FlagGroup.USE, getConfigName("USE"), getConfigDesc("USE"),true, false),
    INVENTORY(XMaterial.SHEEP_SPAWN_EGG.parseMaterial(), FlagGroup.USE, getConfigName("INVENTORY"), getConfigDesc("INVENTORY"),true, false),

    ANIMALSKILLING(XMaterial.PORKCHOP.parseMaterial(), FlagGroup.DAMAGE, getConfigName("ANIMALKILLING"), getConfigDesc("ANIMALKILLING"), true, false),
    ANIMALSSPAWN(XMaterial.SHEEP_SPAWN_EGG.parseMaterial(), FlagGroup.DAMAGE, getConfigName("ANIMALSPAWN"), getConfigDesc("ANIMALSPAWN"), true, false),
    MONSTERKILLING(XMaterial.BONE.parseMaterial(), FlagGroup.DAMAGE, getConfigName("MONSTERKILLING"), getConfigDesc("MONSTERKILLING"), true, false),
    MONSTERSPAWN(XMaterial.ZOMBIE_SPAWN_EGG.parseMaterial(), FlagGroup.DAMAGE, getConfigName("MONSTERSPAWN"), getConfigDesc("MONSTERSPAWN"), true, false),
    MONSTERDAMAGE(XMaterial.ROTTEN_FLESH.parseMaterial(), FlagGroup.DAMAGE, getConfigName("MONSTERDAMAGE"), getConfigDesc("MONSTERDAMAGE"), true, false),
    VILLAGERKILLING(XMaterial.VILLAGER_SPAWN_EGG.parseMaterial(), FlagGroup.DAMAGE, getConfigName("VILLAGERKILLING"), getConfigDesc("VILLAGERKILLING"), true, false),
    WATERMOBSKILLING(XMaterial.PUFFERFISH.parseMaterial(), FlagGroup.DAMAGE, getConfigName("WATERMOBSKILLING"), getConfigDesc("WATERMOBSKILLING"), true, false),
    WATERMOBSSPAWN(XMaterial.SHEEP_SPAWN_EGG.parseMaterial(), FlagGroup.DAMAGE, getConfigName("WATERMOBSPAWN"), getConfigDesc("WATERMOBSPAWN"), true, false);





    private Material icon;
    private FlagGroup flagGroup;
    private String name;
    private List<String> desc;
    private boolean change;
    private boolean enabled;
    private Set<String> groups = null;
    public enum FlagGroup{
        USE,
        DAMAGE,
        OTHERS;
    }

    Flag(Material icon, FlagGroup flagGroup, String name, List<String> desc, boolean change, boolean enabled){
        this.icon = icon;
        this.flagGroup = flagGroup;
        this.name = name;
        this.desc = desc;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getDesc() {
        return desc;
    }

    public void setDesc(List<String> desc) {
        this.desc = desc;
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

    public void addGroup(String group){
        if(this.groups == null)
            this.groups = new HashSet<>();
        this.groups.add(group.toLowerCase());
    }

    public boolean isInGroup(String group){
        if(this.groups == null)
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

    public static String getConfigName(String flag){
        return flag;
        //return ChatUtil.strToColor(GlenCuboid.getInstance().getConfigManager().getConfig("flags").getString("flags." + flag + ".name"));
    }

    public static List<String> getConfigDesc(String flag){
        return Arrays.asList(flag);
        //return ChatUtil.listToColor(GlenCuboid.getInstance().getConfigManager().getConfig("flags").getStringList("flags." + flag + ".lores"));
    }
}
