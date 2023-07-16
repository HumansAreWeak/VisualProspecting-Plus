package de.humansareweak.visualprospectingplus.database.veintypes;

import de.humansareweak.visualprospectingplus.Tags;
import gregapi.data.MT;
import net.minecraft.util.EnumChatFormatting;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class VeinType {

    public static final int veinHeight = 9;

    public final String name;
    public short veinId;
    public final IOreMaterialProvider oreMaterialProvider;
    public final int blockSize;
    public final short primaryOreMeta;
    public final short secondaryOreMeta;
    public final short inBetweenOreMeta;
    public final short sporadicOreMeta;
    public final int minBlockY;
    public final int maxBlockY;
    public final Set<Short> oresAsSet;
    private boolean isHighlighted = true;

    // Available after VisualProspecting post GT initialization
    public static final VeinType NO_VEIN = new VeinType(Tags.ORE_MIX_NONE_NAME, null, 0, (short)-1, (short)-1, (short)-1, (short)-1, 0, 0);

    public VeinType(String name, IOreMaterialProvider oreMaterialProvider, int blockSize, short primaryOreMeta, short secondaryOreMeta, short inBetweenOreMeta, short sporadicOreMeta, int minBlockY, int maxBlockY)
    {
        this.name = name;
        this.oreMaterialProvider = oreMaterialProvider;
        this.blockSize = blockSize;
        this.primaryOreMeta = primaryOreMeta;
        this.secondaryOreMeta = secondaryOreMeta;
        this.inBetweenOreMeta = inBetweenOreMeta;
        this.sporadicOreMeta = sporadicOreMeta;
        this.minBlockY = minBlockY;
        this.maxBlockY = maxBlockY;
        oresAsSet = new HashSet<>();
        oresAsSet.add(primaryOreMeta);
        oresAsSet.add(secondaryOreMeta);
        oresAsSet.add(inBetweenOreMeta);
        oresAsSet.add(sporadicOreMeta);
    }

    public boolean matches(Set<Short> foundOres) {
        return foundOres.containsAll(oresAsSet);
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
        return MT.ALL_MATERIALS_REGISTERED_HERE.stream()
                .filter(e -> oresAsSet.contains(e.mID))
                .map(e -> EnumChatFormatting.GRAY + e.getLocal())
                .collect(Collectors.toList());
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
