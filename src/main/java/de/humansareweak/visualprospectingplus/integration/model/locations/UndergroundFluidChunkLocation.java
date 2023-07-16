package de.humansareweak.visualprospectingplus.integration.model.locations;

import de.humansareweak.visualprospectingplus.Utils;
import net.minecraftforge.fluids.Fluid;

public class UndergroundFluidChunkLocation implements ILocationProvider {

    private final int blockX;
    private final int blockZ;
    private final int dimensionId;
    private final Fluid fluid;
    private final int fluidAmount;
    private final int maxAmountInField;
    private final int minAmountInField;

    public UndergroundFluidChunkLocation(int chunkX, int chunkZ, int dimensionId, Fluid fluid, int fluidAmount, int minAmountInField, int maxAmountInField) {
        blockX = Utils.coordChunkToBlock(chunkX);
        blockZ = Utils.coordChunkToBlock(chunkZ);
        this.dimensionId = dimensionId;
        this.fluid = fluid;
        this.fluidAmount = fluidAmount;
        this.maxAmountInField = maxAmountInField;
        this.minAmountInField = minAmountInField;
    }

    public double getBlockX() {
        return blockX + 0.5;
    }

    public double getBlockZ() {
        return blockZ + 0.5;
    }

    public int getDimensionId() {
        return dimensionId;
    }

    public String getFluidAmountFormatted() {
        if(fluidAmount >= 1000) {
            return (fluidAmount / 1000) + "kL";
        }
        return fluidAmount + "L";
    }

    public int getFluidAmount() {
        return fluidAmount;
    }

    public Fluid getFluid() {
        return fluid;
    }

    public int getMaxAmountInField() {
        return maxAmountInField;
    }

    public int getMinAmountInField() {
        return minAmountInField;
    }
}
