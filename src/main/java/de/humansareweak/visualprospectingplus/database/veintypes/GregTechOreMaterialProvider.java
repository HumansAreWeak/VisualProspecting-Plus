package de.humansareweak.visualprospectingplus.database.veintypes;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregapi.oredict.OreDictMaterial;
import net.minecraft.util.IIcon;

public class GregTechOreMaterialProvider implements IOreMaterialProvider {

    private final OreDictMaterial material;

    public GregTechOreMaterialProvider(OreDictMaterial material) {
        this.material = material;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon() {
        return material.mTextureSetsBlock.get(0).getIcon(0);
    }

    @Override
    public int getColor() {
        return
            ((material.mRGBaSolid[0] & 0xFF) << 16) |
            ((material.mRGBaSolid[1] & 0xFF) <<  8) |
            ((material.mRGBaSolid[2] & 0xFF) <<  0);
    }
}
