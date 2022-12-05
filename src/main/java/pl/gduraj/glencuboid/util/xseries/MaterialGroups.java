package pl.gduraj.glencuboid.util.xseries;

import org.bukkit.Material;


public class MaterialGroups {
    public final static Material[] DOORS = new Material[]{
            XMaterial.OAK_DOOR.parseMaterial(),
            XMaterial.BIRCH_DOOR.parseMaterial(),
            XMaterial.DARK_OAK_DOOR.parseMaterial(),
            XMaterial.ACACIA_DOOR.parseMaterial(),
            XMaterial.JUNGLE_DOOR.parseMaterial(),
            XMaterial.MANGROVE_DOOR.parseMaterial(),
            XMaterial.SPRUCE_DOOR.parseMaterial(),
            XMaterial.WARPED_DOOR.parseMaterial(),
            XMaterial.CRIMSON_DOOR.parseMaterial(),
            XMaterial.IRON_DOOR.parseMaterial(),
    };

    public final static Material[] BUTTONS = new Material[]{
            XMaterial.OAK_BUTTON.parseMaterial(),
            XMaterial.BIRCH_BUTTON.parseMaterial(),
            XMaterial.DARK_OAK_BUTTON.parseMaterial(),
            XMaterial.ACACIA_BUTTON.parseMaterial(),
            XMaterial.JUNGLE_BUTTON.parseMaterial(),
            XMaterial.MANGROVE_BUTTON.parseMaterial(),
            XMaterial.SPRUCE_DOOR.parseMaterial(),
            XMaterial.WARPED_BUTTON.parseMaterial(),
            XMaterial.CRIMSON_BUTTON.parseMaterial(),
            XMaterial.STONE_BUTTON.parseMaterial(),
            XMaterial.POLISHED_BLACKSTONE_BUTTON.parseMaterial(),

    };

    public final static Material[] TRAPDORS = new Material[]{
            XMaterial.OAK_BUTTON.parseMaterial(),
            XMaterial.BIRCH_BUTTON.parseMaterial(),
            XMaterial.DARK_OAK_BUTTON.parseMaterial(),
            XMaterial.ACACIA_BUTTON.parseMaterial(),
            XMaterial.JUNGLE_BUTTON.parseMaterial(),
            XMaterial.MANGROVE_BUTTON.parseMaterial(),
            XMaterial.SPRUCE_DOOR.parseMaterial(),
            XMaterial.WARPED_BUTTON.parseMaterial(),
            XMaterial.CRIMSON_BUTTON.parseMaterial(),
            XMaterial.STONE_BUTTON.parseMaterial(),
            XMaterial.POLISHED_BLACKSTONE_BUTTON.parseMaterial(),

    };


    public static boolean isDoors(Material material) {
        for (Material mat : DOORS) {
            if (material.equals(mat)) {
                return true;
            }
        }
        return false;
    }


}
