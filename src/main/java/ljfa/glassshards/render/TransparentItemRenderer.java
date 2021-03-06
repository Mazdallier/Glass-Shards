package ljfa.glassshards.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

public class TransparentItemRenderer implements IItemRenderer {
    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return Minecraft.isFancyGraphicsEnabled()
            && (type == ItemRenderType.ENTITY
            || type == ItemRenderType.EQUIPPED
            || type == ItemRenderType.EQUIPPED_FIRST_PERSON);
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return helper == ItemRendererHelper.ENTITY_BOBBING
            || helper == ItemRendererHelper.ENTITY_ROTATION
            || (helper == ItemRendererHelper.BLOCK_3D && type == ItemRenderType.ENTITY && !RenderItem.renderInFrame);
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        IIcon icon = item.getIconIndex();
        float minU = icon.getMinU();
        float maxU = icon.getMaxU();
        float minV = icon.getMinV();
        float maxV = icon.getMaxV();
        
        if(type == ItemRenderType.ENTITY) {
            if(RenderItem.renderInFrame) {
                GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
                GL11.glTranslatef(0.0f, -0.3f, 0.0f);
            }
            GL11.glTranslatef(-0.5f, 0.0f, 0.03125f);
        }
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_BLEND);
        ItemRenderer.renderItemIn2D(Tessellator.instance, maxU, minV, minU, maxV, icon.getIconWidth(), icon.getIconHeight(), 0.0625f);
        GL11.glDisable(GL11.GL_BLEND);
    }
}
