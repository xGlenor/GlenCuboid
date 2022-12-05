package pl.gduraj.glencuboid.util;

import net.kyori.adventure.platform.bukkit.BukkitComponentSerializer;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.gduraj.glencuboid.GlenCuboid;

import java.util.List;

public class ItemBuilder {

    private final LegacyComponentSerializer serializer = BukkitComponentSerializer.legacy();
    private final MiniMessage mm = GlenCuboid.getInstance().getMiniMessage();
    private final ItemStack itemStack;

    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemBuilder() {
        this.itemStack = null;
    }

    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
    }

    public ItemBuilder setName(String name) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(serializer.serialize(mm.deserialize(name)));
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder setLore(List<String> lores) {
        for (int i = 0; i < lores.size(); i++) {
            lores.set(i, serializer.serialize(mm.deserialize(lores.get(i))));
        }

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(lores);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder addLore(String lore) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lores = itemMeta.getLore();

        lores.add(serializer.serialize(mm.deserialize(lore)));
        itemMeta.setLore(lores);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder addLores(List<String> lores) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> itemLores = itemMeta.getLore();

        for (int i = 0; i < lores.size(); i++)
            lores.set(i, serializer.serialize(mm.deserialize(lores.get(i))));

        itemLores.addAll(lores);
        itemMeta.setLore(itemLores);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setEnchant(Enchantment enchant, int level) {
        itemStack.addUnsafeEnchantment(enchant, level);
        return this;
    }

    public ItemBuilder delEnchant(Enchantment enchantment) {
        itemStack.removeEnchantment(enchantment);
        return this;
    }

    public ItemBuilder addFlag(ItemFlag... flag) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addItemFlags(flag);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder delFlag(ItemFlag... flag) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.removeItemFlags(flag);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder glow() {
        setEnchant(Enchantment.DURABILITY, 1);
        addFlag(ItemFlag.HIDE_ATTRIBUTES);
        return this;
    }

    public ItemStack build() {
        return getItem();
    }


    public ItemStack getItem() {
        return itemStack;
    }

}
