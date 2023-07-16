package de.humansareweak.visualprospectingplus.integration.model.locations;

import de.humansareweak.visualprospectingplus.database.UndergroundFluidPosition;
import net.minecraftforge.fluids.Fluid;

public class UndergroundFluidLocation implements ILocationProvider {

    private final UndergroundFluidPosition undergroundFluidPosition;
    private final int minProduction;
    private final int maxProduction;

    public UndergroundFluidLocation(UndergroundFluidPosition undergroundFluidPosition) {
        this.undergroundFluidPosition = undergroundFluidPosition;
        minProduction = undergroundFluidPosition.getMinProduction();
        maxProduction = undergroundFluidPosition.getMaxProduction();
    }

    @Override
    public int getDimensionId() {
        return undergroundFluidPosition.dimensionId;
    }

    @Override
    public double getBlockX() {
        return undergroundFluidPosition.getBlockX() + 0.5;
    }

    @Override
    public double getBlockZ() {
        return undergroundFluidPosition.getBlockZ() + 0.5;
    }

    public int getMinProduction() {
        return minProduction;
    }

    public int getMaxProduction() {
        return maxProduction;
    }

    public Fluid getFluid() {
        return undergroundFluidPosition.fluid;
    }
}
