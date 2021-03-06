package ljfa.glassshards.compat;

import ljfa.glassshards.Config;
import ljfa.glassshards.api.GlassType;
import ljfa.glassshards.util.GlassRegistry;
import ljfa.glassshards.util.LogHelper;
import ljfa.glassshards.util.ModGlassHandler;
import net.minecraft.block.Block;

import org.apache.logging.log4j.Level;

public class ChiselGlassHelper {
    public static void init() {
        Block[] chiselStainedGlass = com.cricketcraft.chisel.init.ChiselBlocks.stainedGlass;
        Block[] chiselStainedPane = com.cricketcraft.chisel.init.ChiselBlocks.stainedGlassPane;
        
        //Add blocks to the registry
        for(int i = 0; i < chiselStainedGlass.length; i++)
            GlassRegistry.addHandler(chiselStainedGlass[i], new ChiselStainedGlassHandler(i));
        
        for(int i = 0; i < chiselStainedPane.length; i++)
            GlassRegistry.addHandler(chiselStainedPane[i], new ChiselStainedPaneHandler(i));
        
        LogHelper.info("Successfully loaded Chisel compatibility.");
    }
    
    // How stained glass works in Chisel:
    // array index = color >> 2
    // metadata = (color & 3) << 2
    // Blame Chisel for using such hardcoded values
    public static class ChiselStainedGlassHandler extends ModGlassHandler {
        protected int arrayIndex;
        
        public ChiselStainedGlassHandler(int arrayIndex) {
            this.arrayIndex = arrayIndex;
        }
        
        @Override
        public GlassType getType(Block block, int meta) {
            int color = (arrayIndex << 2) | (meta >> 2);
            return new GlassType(GlassType.mult_block, true, color);
        }
    }

    // How stained panes work in Chisel:
    // array index = color >> 1
    // metadata = (color & 1) << 3
    public static class ChiselStainedPaneHandler extends ModGlassHandler {
        protected int arrayIndex;
        
        public ChiselStainedPaneHandler(int arrayIndex) {
            this.arrayIndex = arrayIndex;
        }
        
        @Override
        public GlassType getType(Block block, int meta) {
            int color = (arrayIndex << 1) | (meta >> 3);
            return new GlassType(GlassType.mult_pane, true, color);
        }
        
        @Override
        public boolean shouldRemoveDrop(Block block, int meta) {
            return Config.chiselFixPaneDrops;
        }
    }
}
