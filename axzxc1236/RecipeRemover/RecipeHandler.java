package axzxc1236.RecipeRemover;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;

public class RecipeHandler {

    private static final Map<Integer, ArrayList<Integer>> ids = new HashMap<Integer, ArrayList<Integer>>();

    public static void addID(String s) {
        try {
            s = s.trim();
            if (!s.contains(":")) {
                RecipeHandler.addID(Integer.parseInt(s));
                return;
            }

            String[] split = s.split(":");
            int id = Integer.parseInt(split[0]);
            int metadata = Integer.parseInt(split[1]);
            RecipeHandler.addID(id, metadata);
        } catch (NumberFormatException e) {
            e.printStackTrace(System.err);
        }
    }

    public static void addID(int id) {
        addID(id, 0, false);
    }

    public static void addID(int id, int metadata) {
        addID(id, metadata, true);
    }

    private static void addID(int id, int metadata, boolean hasMetadata) {
        if (ids.containsKey(id) && hasMetadata) {
            if (ids.get(id).contains(metadata)) return;
            ids.get(id).add(metadata);
            return;
        }
        if (ids.containsKey(id)) return;
        ids.put(id, new ArrayList<Integer>());
        if (hasMetadata)
            ids.get(id).add(metadata);
    }

    public static void removeRecipes() {
        removeCraftingRecipes();
        removeSmeltingRecipes();
        removeMetaSmeltingRecipes();
    }

    private static void removeCraftingRecipes() {
        List recipes = CraftingManager.getInstance().getRecipeList();
        for (int i = 0; i < recipes.size(); i++) {
            ItemStack stack;
            if ((stack = ((IRecipe) recipes.get(i)).getRecipeOutput()) == null) continue;

            int id = stack.itemID;
            if (!ids.containsKey(id)) continue;
            ArrayList<Integer> metadata = ids.get(id);

            if (ids.get(id).size() == 0) {
                recipes.remove(i--);
            } else {
                if (metadata.contains(stack.getItemDamage()))
                    recipes.remove(i--);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static void removeSmeltingRecipes() {
        Map<Integer, ItemStack> recipes = FurnaceRecipes.smelting().getSmeltingList();
        for (Integer id : new HashMap<Integer, ItemStack>(recipes).keySet()) {
            ItemStack stack;
            if ((stack = recipes.get(id)) == null) return;
            if (!ids.containsKey(stack.itemID)) return;

            ArrayList<Integer> metadata = ids.get(stack.itemID);
            if (ids.get(stack.itemID).size() == 0) {
                recipes.remove(id);
            } else {
                if (metadata.contains(stack.getItemDamage()))
                    recipes.remove(id);
            }
        }
    }

    private static void removeMetaSmeltingRecipes() {
        Map<List<Integer>, ItemStack> recipes = FurnaceRecipes.smelting().getMetaSmeltingList();
        for (List<Integer> list : new HashMap<List<Integer>, ItemStack>(recipes).keySet()) {
            ItemStack stack;
            if ((stack = recipes.get(list)) == null) return;
            if (!ids.containsKey(stack.itemID)) return;

            ArrayList<Integer> metadata = ids.get(stack.itemID);
            if (ids.get(stack.itemID).size() == 0) {
                recipes.remove(list);
            } else {
                if (metadata.contains(stack.getItemDamage()))
                    recipes.remove(list);
            }
        }
    }

}