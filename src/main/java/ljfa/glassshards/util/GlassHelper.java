package ljfa.glassshards.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.BlockPane;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.BlockStainedGlassPane;
import net.minecraft.block.material.Material;

public class GlassHelper {
    public static enum GlassType {
        block(1.0f), pane(0.375f);
        
        public final float multiplier;
        
        private GlassType(float mult) { multiplier = mult; }
    }
    
    public static GlassType getType(Block block, int meta) {
        if(block instanceof BlockGlass || block instanceof BlockStainedGlass)
            return GlassType.block;
        else if(block instanceof BlockStainedGlassPane
                || block instanceof BlockPane && block.getMaterial() == Material.glass)
            return GlassType.pane;
        else
            return null;
    }
    
    public static boolean isGlassStained(Block block, int meta) {
        return block instanceof BlockStainedGlass || block instanceof BlockStainedGlassPane;
    }
    
    public static int getGlassColor(Block block, int meta) {
        return meta;
    }
}