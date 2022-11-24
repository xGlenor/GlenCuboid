package pl.gduraj.glencuboid.cuboid.flags;

import org.bukkit.Material;

import java.util.HashSet;
import java.util.Set;

public enum Flag {
    ANVIL(),
    ANIMALS(),
    ANIMALSKILLING(),
    BED(),
    BEACON(),
    BREW(),
    BUILD(),
    BUTTON(),
    CRAFT(),
    DAMAGE(),
    DESTROY(),
    EGG(),
    DOOR(),
    ENCHANT(),
    EXPLODE(),
    ENDERPEARL(),
    FLOW(),
    GROW(),
    ICEFORM(),
    ICEMELT(),
    ITEMDROP(),
    ITEMPICKUP(),
    LAVAFLOW(),
    LEASH(),
    LEVER(),
    MONSTERS(),
    MOBKILLING();


    private Material icon;
    private FlagGroup flagGroup;
    private String name;
    private String desc;
    private boolean change;
    private boolean enabled;
    private Set<String> groups = null;
    public enum FlagGroup{
        USE,
        DAMAGE,
        OTHERS;
    }

    Flag(Material icon, FlagGroup flagGroup, String name, String desc, boolean change, boolean enabled){
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
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

}
