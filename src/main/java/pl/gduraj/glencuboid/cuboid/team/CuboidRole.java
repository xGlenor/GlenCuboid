package pl.gduraj.glencuboid.cuboid.team;

public enum CuboidRole {

    OWNER(1, "roles.OWNER"),
    ADMIN(2, "roles.ADMIN"),
    MEMBER(3, "roles.MEMBER"),
    VISITOR(4, "roles.VISITOR");

    private int index;
    private String path;

    CuboidRole(int index, String path){
        this.index = index;
        this.path = path;
    }

    public int getIndex() {
        return index;
    }

    public String getPath() {
        return path;
    }

    public static CuboidRole getByIndex(int index){
        for(CuboidRole role : values())
            if(role.getIndex() == index)
                return role;
        return null;
    }

    public static boolean isRole(String roleStr){
        for(CuboidRole role : values()){
            if(role.toString().equalsIgnoreCase(roleStr))
                return true;
        }
        return false;
    }

}
