package ljfa.glassshards;

import ljfa.glassshards.compat.ChiselGlassHelper;
import ljfa.glassshards.compat.EnderIOGlassHelper;
import ljfa.glassshards.compat.MFRGlassHelper;
import ljfa.glassshards.compat.ThaumcraftCompat;
import ljfa.glassshards.compat.ThermalExpCompat;
import ljfa.glassshards.compat.TinkersGlassHelper;
import ljfa.glassshards.handlers.HarvestDropsHandler;
import ljfa.glassshards.items.ModItems;
import ljfa.glassshards.render.TransparentItemRenderer;
import ljfa.glassshards.util.GlassHelper;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = Reference.MODID, name = Reference.MODNAME, version = Reference.VERSION, guiFactory = Reference.GUI_FACTORY_CLASS)
public class GlassShards {
    @Mod.Instance(Reference.MODID)
    public static GlassShards instance;
    
    /*@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;*/
    
    public static ToolMaterial toolMatGlass;
    
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Config.loadConfig(event.getSuggestedConfigurationFile());
        toolMatGlass = EnumHelper.addToolMaterial("GLASS", 2, 109, 4.0f, 2.0f, 5);
        ModItems.preInit();
        toolMatGlass.setRepairItem(new ItemStack(ModItems.glass_shards, 1, 16));
    }
    
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        ModRecipes.init();
        MinecraftForge.EVENT_BUS.register(new HarvestDropsHandler());
        
        if(Config.renderTransparent && event.getSide() == Side.CLIENT) {
            MinecraftForgeClient.registerItemRenderer(ModItems.glass_shards, new TransparentItemRenderer());
            MinecraftForgeClient.registerItemRenderer(ModItems.glass_sword, new TransparentItemRenderer());
        }
        
        if(Loader.isModLoaded("EnderIO"))
            EnderIOGlassHelper.addRecipes();
    }
    
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        GlassHelper.registerAll();
        initCompatModules();
    }

    /** Initialize compatibility with other mods */
    private static void initCompatModules() {
        if(Config.chiselEnable && Loader.isModLoaded("chisel"))
            ChiselGlassHelper.init();
        if(Loader.isModLoaded("TConstruct")) {
            if(Config.tinkersEnable)
                TinkersGlassHelper.init();
            if(Config.tinkersMeltShards)
                TinkersGlassHelper.addSmelteryRecipe();
        }
        if(Config.mfrEnable && Loader.isModLoaded("MineFactoryReloaded"))
            MFRGlassHelper.init();
        if(Loader.isModLoaded("EnderIO")) {
            if(Config.eioDropShards)
                EnderIOGlassHelper.init();
            if(Config.eioSagMill)
                EnderIOGlassHelper.setupGrindingBallExcludes();
        }
        if(Config.tePulverizer && Loader.isModLoaded("ThermalExpansion"))
            ThermalExpCompat.addRecipes();
        if(Config.thaumAspects && Loader.isModLoaded("Thaumcraft"))
            ThaumcraftCompat.addAspects();
    }
}
