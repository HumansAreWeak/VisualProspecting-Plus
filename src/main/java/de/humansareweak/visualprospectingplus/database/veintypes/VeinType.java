package de.humansareweak.visualprospectingplus.database.veintypes;

import de.humansareweak.visualprospectingplus.Tags;
import gregapi.data.MT;
import gregapi.worldgen.StoneLayer;
import gregapi.worldgen.StoneLayerOres;
import net.minecraft.util.EnumChatFormatting;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class VeinType {
    private final StoneLayer stoneLayer;
    public static final int veinHeight = 9;

    public final String name;
    public short veinId;
    private final Set<StoneLayerOres> ores;
    private boolean isHighlighted = true;

    // Available after VisualProspecting post GT initialization
    public static final VeinType NO_VEIN = new VeinType(Tags.ORE_MIX_NONE_NAME, null);

    public VeinType(String name, StoneLayer stoneLayer)
    {
        this.name = name;
        this.stoneLayer = stoneLayer;
        ores = new HashSet<>();

        if(stoneLayer != null) ores.addAll(stoneLayer.mOres);
    }

    public Set<StoneLayerOres> getOres() {
        return ores;
    }

    public boolean matches(Set<StoneLayerOres> foundOres) {
        return foundOres.containsAll(ores);
    }

    public boolean matchesWithSpecificPrimaryOrSecondary(Set<Short> foundOres, short specificMeta) {
        return (primaryOreMeta == specificMeta || secondaryOreMeta == specificMeta) && foundOres.containsAll(oresAsSet);
    }

    public boolean canOverlapIntoNeighborOreChunk() {
        return blockSize > 24;
    }

    // Ore chunks on coordinates -1 and 1 are one chunk less apart
    public boolean canOverlapIntoNeighborOreChunkAtCoordinateAxis() {
        return blockSize > 16;
    }

    public boolean containsOre(short oreMetaData) {
        return primaryOreMeta == oreMetaData
                || secondaryOreMeta == oreMetaData
                || inBetweenOreMeta == oreMetaData
                || sporadicOreMeta == oreMetaData;
    }

    public List<String> getOreMaterialNames() {
        return ores.stream().map(e -> e.mMaterial.getLocal()).collect(Collectors.toList());
    }

    public Set<Short> getOresAtLayer(int layerBlockY) {
        final Set<Short> result = new HashSet<>();
        switch (layerBlockY) {
            case 0, 1, 2 -> {
                result.add(secondaryOreMeta);
                result.add(sporadicOreMeta);
                return result;
            }
            case 3 -> {
                result.add(secondaryOreMeta);
                result.add(inBetweenOreMeta);
                result.add(sporadicOreMeta);
                return result;
            }
            case 4 -> {
                result.add(inBetweenOreMeta);
                result.add(sporadicOreMeta);
                return result;
            }
            case 5, 6 -> {
                result.add(primaryOreMeta);
                result.add(inBetweenOreMeta);
                result.add(sporadicOreMeta);
                return result;
            }
            case 7, 8 -> {
                result.add(primaryOreMeta);
                result.add(sporadicOreMeta);
                return result;
            }
            default -> {
                return result;
            }
        }
    }

    public boolean isHighlighted() {
        return isHighlighted;
    }

    public void setNEISearchHeighlight(boolean isHighlighted) {
        this.isHighlighted = isHighlighted;
    }
}
