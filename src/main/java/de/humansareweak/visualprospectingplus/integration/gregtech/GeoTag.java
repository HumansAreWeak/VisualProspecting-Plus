package de.humansareweak.visualprospectingplus.integration.gregtech;

public class GeoTag {
    public int dim;
    public int x, z;
    public boolean dead = false;
    public boolean sample;
    public final short ore;

    public GeoTag(int ore, int dim, int x, int z, boolean flower) {
        this.dim = dim;
        this.ore = (short) ore;
        this.x = x;
        this.z = z;
        this.sample = flower;
    }

    public int cx() {return x / 16;}
    public int cz() {return z / 16;}

    public int getFraction(short material) {
        int f = Dwarf.singOf(ore).getOrDefault(material, 0);
        return f == 0 ? 0 : Integer.max(f / Dwarf.weightsMeasure[0] /32 , 1);
    }

    public boolean isInsideIndicator(int x, int z) {
        return this.x >= x - 32 && this.x <= x + 32 && this.z >= z - 32 && this.z <= z + 32;
    }
}
