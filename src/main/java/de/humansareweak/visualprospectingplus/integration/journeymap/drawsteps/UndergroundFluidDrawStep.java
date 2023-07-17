package de.humansareweak.visualprospectingplus.integration.journeymap.drawsteps;

import de.humansareweak.visualprospectingplus.VPP;
import de.humansareweak.visualprospectingplus.integration.model.locations.UndergroundFluidLocation;
import journeymap.client.render.draw.DrawStep;
import journeymap.client.render.draw.DrawUtil;
import journeymap.client.render.map.GridRenderer;

import java.awt.geom.Point2D;

public class UndergroundFluidDrawStep implements DrawStep {

    private final UndergroundFluidLocation undergroundFluidLocation;

    public UndergroundFluidDrawStep(UndergroundFluidLocation undergroundFluidLocation) {
        this.undergroundFluidLocation = undergroundFluidLocation;
    }

    @Override
    public void draw(double draggedPixelX, double draggedPixelY, GridRenderer gridRenderer, float drawScale, double fontScale, double rotation) {
        final int maxAmountInField = undergroundFluidLocation.getMaxProduction();
        if (maxAmountInField > 0) {
            final double blockSize = Math.pow(2, gridRenderer.getZoom());
            final Point2D.Double blockAsPixel = gridRenderer.getBlockPixelInGrid(undergroundFluidLocation.getBlockX(), undergroundFluidLocation.getBlockZ());
            final Point2D.Double pixel = new Point2D.Double(blockAsPixel.getX() + draggedPixelX, blockAsPixel.getY() + draggedPixelY);

            final int borderColor = undergroundFluidLocation.getFluid().getColor();
            final int borderAlpha = 204;
            DrawUtil.drawRectangle(pixel.getX(), pixel.getY(), VPP.undergroundFluidSizeChunkX * VPP.chunkWidth * blockSize, 2 * blockSize, borderColor, borderAlpha);
            DrawUtil.drawRectangle(pixel.getX() + VPP.undergroundFluidSizeChunkX * VPP.chunkWidth * blockSize, pixel.getY(), 2 * blockSize, VPP.undergroundFluidSizeChunkZ * VPP.chunkDepth * blockSize, borderColor, borderAlpha);
            DrawUtil.drawRectangle(pixel.getX() + 2 * blockSize, pixel.getY() + VPP.undergroundFluidSizeChunkZ * VPP.chunkDepth * blockSize, VPP.undergroundFluidSizeChunkX * VPP.chunkWidth * blockSize, 2 * blockSize, borderColor, borderAlpha);
            DrawUtil.drawRectangle(pixel.getX(), pixel.getY() + 2 * blockSize, 2 * blockSize, VPP.undergroundFluidSizeChunkZ * VPP.chunkDepth * blockSize, borderColor, borderAlpha);

            final String label = undergroundFluidLocation.getMinProduction() + "L - " + maxAmountInField + "L  " + undergroundFluidLocation.getFluid().getLocalizedName();
            DrawUtil.drawLabel(label, pixel.getX() + VPP.chunkWidth * blockSize, pixel.getY(), DrawUtil.HAlign.Right, DrawUtil.VAlign.Below, 0, 180, 0x00FFFFFF, 255, fontScale, false, rotation);
        }
    }
}
