package pl.gduraj.glencuboid.cuboid;

import com.google.gson.JsonObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import pl.gduraj.glencuboid.GlenCuboid;
import pl.gduraj.glencuboid.cuboid.Cuboid;
import pl.gduraj.glencuboid.cuboid.Flag;

import java.util.ArrayList;
import java.util.List;

public class CuboidFlag {

    private Cuboid cuboid;
    private List<Flag> flags = new ArrayList<>();
    private List<Flag> disabledFlags = new ArrayList<>();

    public CuboidFlag(Cuboid cuboid){
        this.cuboid = cuboid;
    }

    public void addFlag(Flag flag){
        flags.add(flag);
    }

    public void removeFlag(Flag flag){
        flags.remove(flag);
    }

    public void addDisabledFlag(Flag flag){
        flags.remove(flag);
        disabledFlags.add(flag);
    }

    public void removeDisabledFlag(Flag flag){
        disabledFlags.remove(flag);
        flags.add(flag);
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




    public String getFlagsAsString(){
        JSONObject obj = new JSONObject();

        JSONArray flagsJSON = new JSONArray();
        for(Flag f : flags)
            flagsJSON.add(f.toString());

        JSONArray disabledFlagsJSON = new JSONArray();
        for(Flag f : disabledFlags)
            disabledFlagsJSON.add(f.toString());

        if(!flagsJSON.isEmpty()){
            obj.put("flags", flagsJSON);
        }

        if(!disabledFlagsJSON.isEmpty()){
            obj.put("disabledFlags", disabledFlagsJSON);
        }

        System.out.println(obj.toJSONString());
        return obj.toJSONString();
    }

    public void setFlags(String jsonSTR) {

        JSONObject obj = (JSONObject) JSONValue.parse(jsonSTR);

        if(obj != null){
            JSONArray flag = (JSONArray) obj.get("flags");
            JSONArray dflag = (JSONArray) obj.get("disabledFlags");

            if(flag != null){
                for(Object fl : flag)
                    this.flags.add(Flag.getFlag((String) fl));
            }
            if(dflag != null){
                for(Object fl : dflag)
                    this.disabledFlags.add(Flag.getFlag((String) fl));
            }

        }





/*        if(JSONString != null && !JSONString.isEmpty()){
            JSONObject flags = (JSONObject) JSONValue.parse(JSONString);
            System.out.println("FLAGS JSON: " + flags);
            if(flags != null){
                for(Object flag : flags.keySet()){
                    System.out.println("SETFLAG 1: "+ flag);
                    try {
                        System.out.println("SETFLAG 2: " + flag);
                        if(flag.equals("disabledFlags")){
                            JSONArray disabledFlagsJSON = (JSONArray) flags.get(flag);

                            System.out.println("disabledFlags: " + disabledFlagsJSON);
                            System.out.println(disabledFlags);
                            this.disabledFlags.addAll(disabledFlagsJSON);
                        } else if (flag.equals("flags")) {
                            JSONArray flagsJSON = (JSONArray) flags.get(flag);

                            System.out.println("flags: " + flagsJSON);
                            System.out.println(flags);
                            this.flags.addAll(flagsJSON);
                        }
                    }catch (Exception ex){
                        GlenCuboid.getMessageLoaded().add("Blad podczas odczytania flagi: " + flag + "\n Values: " +flags.get(flag));
                    }

                }
            }

        }*/

    }

    public List<Flag> getFlags() {
        return flags;
    }

    public void setFlags(List<Flag> flags) {
        this.flags = flags;
    }

    public List<Flag> getDisabledFlags() {
        return disabledFlags;
    }

    public void setDisabledFlags(List<Flag> disabledFlags) {
        this.disabledFlags = disabledFlags;
    }
}
