package pl.gduraj.glencuboid.cuboid;

import org.bukkit.configuration.file.FileConfiguration;

import pl.gduraj.glencuboid.cuboid.flags.Flag;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class CuboidSettings {

    protected String name;
    protected List<String> lores;
    protected int amount;
    protected boolean glow;
    protected boolean deleteIfNoPermission;
    protected boolean requiredPermission;
    protected List<Integer> limits;
    protected ArrayList<Flag> flags;
    protected ArrayList<Flag> disabledFlags;
    protected List<String> allowedPlayers;

    public CuboidSettings(LinkedHashMap<String, Object> configs){
    }

    public CuboidSettings(){
        this.flags = new ArrayList<>();
        this.disabledFlags = new ArrayList<>();
        this.allowedPlayers = new ArrayList<>();

        this.name = "";
        this.lores = new ArrayList<>();
        this.amount = 1;
        this.glow = true;
        this.deleteIfNoPermission = false;
        this.requiredPermission = false;
        this.limits = new ArrayList<>();
    }

    public void loadGroups(FileConfiguration config){

    }

    public void addPlayer(){

    }

    public void removePlayer(){

    }

    public void isDisabledFlag(Flag flag, Cuboid cuboid){

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getLores() {
        return lores;
    }

    public void setLores(List<String> lores) {
        this.lores = lores;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isGlow() {
        return glow;
    }

    public void setGlow(boolean glow) {
        this.glow = glow;
    }

    public List<Integer> getLimits() {
        return limits;
    }

    public void setLimits(List<Integer> limits) {
        this.limits = limits;
    }

    public ArrayList<Flag> getFlags() {
        return flags;
    }

    public void setFlags(ArrayList<Flag> flags) {
        this.flags = flags;
    }

    public ArrayList<Flag> getDisabledFlags() {
        return disabledFlags;
    }

    public void setDisabledFlags(ArrayList<Flag> disabledFlags) {
        this.disabledFlags = disabledFlags;
    }

    public List<String> getAllowedPlayers() {
        return allowedPlayers;
    }

    public void setAllowedPlayers(List<String> allowedPlayers) {
        this.allowedPlayers = allowedPlayers;
    }

    public boolean isDeleteIfNoPermission() {
        return deleteIfNoPermission;
    }

    public void setDeleteIfNoPermission(boolean deleteIfNoPermission) {
        this.deleteIfNoPermission = deleteIfNoPermission;
    }

    public boolean isRequiredPermission() {
        return requiredPermission;
    }

    public void setRequiredPermission(boolean requiredPermission) {
        this.requiredPermission = requiredPermission;
    }
}
