package ru.brainrtp.bwkits.utils;


import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Kit {

    private String name;
    private String permission;
    private int cost;
    private List<String> description;
    private Map<String, List> equipments = new HashMap<>();
    private Material icon;
    private String id;
    private ItemStack itemStack;

    public Kit(String id, String name, String permission, int cost, List<String> description, Map<String, List> equipments, Material icon, ItemStack itemStack) {
        this.itemStack = itemStack;
        this.id = id;
        this.name = itemStack.getItemMeta().getDisplayName();
        this.permission = permission;
        this.cost = cost;
        this.description = itemStack.getItemMeta().getLore();
        this.equipments = equipments;
        this.icon = itemStack.getType();
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public List<String> getDescription() {
        return description;
    }

    public void setDescription(List<String> description) {
        this.description = description;
    }

    public Map<String, List> getEquipments() {
        return equipments;
    }

    public void setEquipments(Map<String, List> equipments) {
        this.equipments = equipments;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Material getIcon() {
        return icon;
    }

    public void setIcon(Material icon) {
        this.icon = icon;
    }
}