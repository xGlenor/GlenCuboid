package pl.gduraj.glencuboid.cuboid;

import org.bukkit.Material;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import pl.gduraj.glencuboid.enums.Dirty;
import pl.gduraj.glencuboid.enums.Flag;
import pl.gduraj.glencuboid.util.xseries.XMaterial;

import java.util.ArrayList;
import java.util.List;

public class CuboidFlag {

    private final Cuboid cuboid;
    private List<Flag> flags = new ArrayList<>();
    private List<Material> preventUse = new ArrayList<>();
    private List<Flag> disabledFlags = new ArrayList<>();

    public CuboidFlag(Cuboid cuboid) {
        this.cuboid = cuboid;
    }

    public void addFlag(Flag flag) {
        flags.add(flag);
    }

    public void removeFlag(Flag flag) {
        flags.remove(flag);
    }

    public void addDisabledFlag(Flag flag) {
        flags.remove(flag);
        disabledFlags.add(flag);
        cuboid.addDirty(Dirty.CHANGE_FLAGS);
    }

    public void removeDisabledFlag(Flag flag) {
        disabledFlags.remove(flag);
        flags.add(flag);
        cuboid.addDirty(Dirty.CHANGE_FLAGS);
    }

    public boolean hasFlag(Flag flag) {
        boolean ret = flags.contains(flag);
        System.out.println("FLAGS: " + ret);

        if (disabledFlags.contains(flag)) {
            ret = false;
            System.out.println("FLAGS D: " + ret);
        }
        System.out.println("FLAGS F: " + ret);

        return ret;
    }


    public String getFlagsAsString() {
        JSONObject obj = new JSONObject();

        JSONArray flagsJSON = new JSONArray();
        for (Flag f : flags)
            flagsJSON.add(f.toString());

        JSONArray disabledFlagsJSON = new JSONArray();
        for (Flag f : disabledFlags)
            disabledFlagsJSON.add(f.toString());

        if (!flagsJSON.isEmpty()) {
            obj.put("flags", flagsJSON);
        }

        if (!disabledFlagsJSON.isEmpty()) {
            obj.put("disabledFlags", disabledFlagsJSON);
        }

        System.out.println(obj.toJSONString());
        return obj.toJSONString();
    }

    public String getPreventUseAsString() {
        JSONArray array = new JSONArray();
        for (Material m : preventUse) {
            array.add(m.name());
        }
        return array.toJSONString();
    }

    public boolean isPreventMaterial(Material material) {
        if (material != null) {
            return preventUse.contains(material);
        }
        return false;
    }

    public List<Flag> getFlags() {
        return flags;
    }

    public void setFlags(String jsonSTR) {

        JSONObject obj = (JSONObject) JSONValue.parse(jsonSTR);

        if (obj != null) {
            JSONArray flag = (JSONArray) obj.get("flags");
            JSONArray dflag = (JSONArray) obj.get("disabledFlags");

            if (flag != null) {
                for (Object fl : flag)
                    this.flags.add(Flag.getFlag((String) fl));
            }
            if (dflag != null) {
                for (Object fl : dflag)
                    this.disabledFlags.add(Flag.getFlag((String) fl));
            }

        }

    }

    public void setFlags(List<Flag> flags) {
        this.flags = flags;
    }

    public List<Material> getPreventUse() {
        return preventUse;
    }

    public void setPreventUse(String jsonSTR) {
        if (jsonSTR == null) return;
        if (JSONValue.parse(jsonSTR) instanceof JSONArray) {
            JSONArray array = (JSONArray) JSONValue.parse(jsonSTR);
            if (array != null) {
                for (Object arr : array) {
                    preventUse.add(XMaterial.valueOf((String) arr).parseMaterial());
                }
            }
            System.out.println(preventUse);
        }

    }

    public void setPreventUse(List<Material> preventUse) {
        this.preventUse = preventUse;
    }

    public List<Flag> getDisabledFlags() {
        return disabledFlags;
    }

    public void setDisabledFlags(List<Flag> disabledFlags) {
        this.disabledFlags = disabledFlags;
    }
}
