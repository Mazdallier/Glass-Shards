package ljfa.glassshards.compat;

import java.util.List;

import ljfa.glassshards.util.GlassRegistry;
import ljfa.glassshards.util.LogHelper;
import ljfa.glassshards.util.SimpleGlassHandler;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

import org.apache.logging.log4j.Level;

public class BotaniaGlassHelper {
    public static void init() {
        //Fetch Botania's block instance
        Block managlass;
        try {
            Class<?> classModBlocks = Class.forName("vazkii.botania.common.block.ModBlocks");
            managlass = (Block)classModBlocks.getDeclaredField("manaGlass").get(null);
        } catch(Exception ex) {
            LogHelper.log(Level.ERROR, ex, "Failed to load Botania glass compatibility.");
            return;
        }
        
        //Add block to the registry
        GlassRegistry.addHandler(managlass, SimpleGlassHandler.blockInstance);
        
        LogHelper.info("Successfully loaded Botania glass compatibility.");
    }
    
    public static void removeVitreousPick() {
        try {
            //Get the vitreous pickaxe item instance
            Class<?> classModItems = Class.forName("vazkii.botania.common.item.ModItems");
            Item vitreousPick = (Item)classModItems.getDeclaredField("glassPick").get(null);
            
            //Remove recipe
            List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
            for(int i = 0; i < recipes.size(); i++) {
                ItemStack output = recipes.get(i).getRecipeOutput();
                if(output != null && output.getItem() == vitreousPick) {
                    recipes.remove(i);
                    break;
                }
            }
            LogHelper.info("Successfully removed Vitreous Pickaxe recipe.");
        } catch(Exception ex) {
            LogHelper.log(Level.ERROR, ex, "Failed to remove Vitreous Pickaxe recipe.");
            return;
        }
    }
}
