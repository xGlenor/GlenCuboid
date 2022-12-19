package pl.gduraj.glencuboid.enums;

import java.util.ArrayList;
import java.util.List;

public enum CuboidRole {

    OWNER(1, "roles.OWNER"),
    ADMIN(2, "roles.ADMIN"),
    MEMBER(3, "roles.MEMBER"),
    VISITOR(4, "roles.VISITOR");

    private final int index;
    private final String path;

    CuboidRole(int index, String path) {
        this.index = index;
        this.path = path;
    }

    public static CuboidRole getByIndex(int index) {
        for (CuboidRole role : values())
            if (role.getIndex() == index)
                return role;
        return null;
    }

    public static boolean isRole(String roleStr) {
        for (CuboidRole role : values()) {
            if (role.toString().equalsIgnoreCase(roleStr))
                return true;
        }
        return false;
    }

    public static List<String> getRolesString(){
        List<String> roles = new ArrayList<>();
        for(CuboidRole role: values()){
            roles.add(role.toString());
        }

        return roles;
    }

    public int getIndex() {
        return index;
    }

    public String getPath() {
        return path;
    }

}
