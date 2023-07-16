package de.humansareweak.visualprospectingplus.integration.gregtech;

public class RockMatter extends GeoTag {
    public short y, multiple = 1;

    public RockMatter(int ore, int dim, int x, int y, int z, boolean rock) {
        super(ore, dim, x, z, rock);
        this.y = (short) y;
    }

    @Override
    public int getFraction(short material) {
        return Dwarf.getFractionIn(ore, material);
    }
}
