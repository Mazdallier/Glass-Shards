package ljfa.glassshards;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class Config {
    public static Configuration conf;
    
    public static final String CATEGORY_GENERAL = "general";
    public static final String CATEGORY_CHISEL = "chisel";
    public static final String CATEGORY_TCONSTRUCT = "tconstruct";
    public static final String CATEGORY_MFR = "mfr";
    public static final String CATEGORY_ENDERIO = "enderio";
    public static final String CATEGORY_THERMALEXP = "thermalexp";
    public static final String CATEGORY_THAUMCRAFT = "thaumcraft";
    
    public static float shardsChance;
    public static float shardsFortuneChance;
    public static boolean recipesRecolor;
    public static boolean recipeUncolor;
    public static boolean renderTransparent;
    public static boolean enableSword;
    
    public static boolean chiselEnable;
    public static boolean chiselFixPaneDrops;
    
    public static boolean tinkersEnable;
    public static boolean tinkersMeltShards;
    
    public static boolean mfrEnable;
    
    public static boolean eioDropShards;
    public static boolean eioSagMill;
    public static boolean eioAlloySmelter;
    
    public static boolean tePulverizer;
    
    public static boolean thaumAspects;
    
    public static void loadConfig(File file) {
        if(conf == null)
            conf = new Configuration(file);
        
        conf.load();
        loadValues();
        
        FMLCommonHandler.instance().bus().register(new ChangeHandler());
    }
    
    public static void loadValues() {
        conf.getCategory(CATEGORY_GENERAL).setComment("General options");
        
        shardsChance = (float)conf.get(CATEGORY_GENERAL, "shardsChance", 0.66, "Base chance that a block of glass drops shards", 0.0, 1.0).getDouble();
        shardsFortuneChance = (float)conf.get(CATEGORY_GENERAL, "shardsFortuneChance", 0.08, "Chance per fortune level that a block of glass drops shards", 0.0, 1.0).getDouble();
        recipesRecolor = conf.get(CATEGORY_GENERAL, "recipesColor", true, "Add recipes for coloring shards").setRequiresMcRestart(true).getBoolean();
        recipeUncolor = conf.get(CATEGORY_GENERAL, "recipeUncolor", true, "Add recipe to remove the color from shards").setRequiresMcRestart(true).getBoolean();
        renderTransparent = conf.get(CATEGORY_GENERAL, "renderTransparent", true, "The shards will be transparent when lying on the ground or held in the hand (fancy graphics only)").setRequiresMcRestart(true).getBoolean();
        enableSword = conf.get(CATEGORY_GENERAL, "enableSword", true, "Enables the glass sword").setRequiresMcRestart(true).getBoolean();
        //----------------
        conf.getCategory(CATEGORY_CHISEL).setComment("Compatibility options for Chisel 2. Tested with version 2.3.5.31.");
        
        chiselEnable = conf.get(CATEGORY_CHISEL, "dropShards", true, "Activates compatibility if Chisel is present.\n"
                + "Note that not activating this even though Chisel is present might lead to unexpected behavior.\n"
                + "Deactivating this doesn't mean Chisel glass will not drop shards. The shards will just not be stained.").setRequiresMcRestart(true).getBoolean();
        chiselFixPaneDrops = conf.get(CATEGORY_CHISEL, "fixStainedPanesDrops", true,
                "By default, Chisel stained glass panes are behaving inconsistently as in they drop themselves\n"
                + "when broken, unlike all the other Chisel glass types.\n"
                + "This option changes this behavior and makes them drop shards instead.").getBoolean();
        //----------------
        conf.getCategory(CATEGORY_TCONSTRUCT).setComment("Compatibility options for Tinkers Construct. Tested with version 1.8.2a.");
        
        tinkersEnable = conf.get(CATEGORY_TCONSTRUCT, "dropShards", false, "Clear Glass and Stained Glass will drop shards rather than themselves").setRequiresMcRestart(true).getBoolean();
        tinkersMeltShards = conf.get(CATEGORY_TCONSTRUCT, "addSmelteryRecipe", true, "Makes shards smeltable in the Smeltery").setRequiresMcRestart(true).getBoolean();
        //----------------
        conf.getCategory(CATEGORY_MFR).setComment("Compatibility options for MineFactory Reloaded. Tested with version 2.8.0RC6-5.");
        
        mfrEnable = conf.get(CATEGORY_MFR, "activate", true, "Activates compatibility if MineFactory Reloaded is present.\n"
                + "When activated, stained glass blocks will drop stained shards.").setRequiresMcRestart(true).getBoolean();
        //----------------
        conf.getCategory(CATEGORY_ENDERIO).setComment("Compatibility options for EnderIO. Tested with version 2.2.7.325.");
        
        eioDropShards = conf.get(CATEGORY_ENDERIO, "dropShards", true, "EnderIO's Quite Clear Glass will drop shards rather than itself.").setRequiresMcRestart(true).getBoolean();
        eioSagMill = conf.get(CATEGORY_ENDERIO, "addSAGMillRecipes", true, "Adds SAG Mill recipes for Glass -> Shards and Shards -> Sand.\n"
                + "This will replace the Glass -> Sand recipe.").setRequiresMcRestart(true).getBoolean();
        eioAlloySmelter = conf.get(CATEGORY_ENDERIO, "addAlloySmelterRecipes", true, "Adds an Alloy Smelter recipe for Shards -> Quite Clear Glass").setRequiresMcRestart(true).getBoolean();
        //----------------
        conf.getCategory(CATEGORY_THERMALEXP).setComment("Compatibility options for Thermal Expansion. Tested with version 4.0.0B8-23.");
        
        tePulverizer = conf.get(CATEGORY_THERMALEXP, "addPulverizerRecipes", true, "Adds Pulverizer recipes for Glass -> Shards and Shards -> Sand.\n"
                + "This will replace the Glass -> Sand recipe.").setRequiresMcRestart(true).getBoolean();
        //----------------
        conf.getCategory(CATEGORY_THAUMCRAFT).setComment("Compatibility options for Thaumcraft. Tested with version 4.2.3.4.");

        thaumAspects = conf.get(CATEGORY_THAUMCRAFT, "addAspects", true, "Adds Thaumcraft aspects to the shards").setRequiresMcRestart(true).getBoolean();
        //----------------
        if(conf.hasChanged())
            conf.save();
    }
    
    /** Reloads the config values upon change */
    public static class ChangeHandler {
        @SubscribeEvent
        public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
            if(event.modID.equals(Reference.MODID))
                loadValues();
        }
    }
}
