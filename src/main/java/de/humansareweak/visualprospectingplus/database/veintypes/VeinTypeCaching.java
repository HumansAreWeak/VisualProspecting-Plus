package de.humansareweak.visualprospectingplus.database.veintypes;

import codechicken.nei.NEIClientConfig;
import codechicken.nei.SearchField;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import de.humansareweak.visualprospectingplus.Tags;
import de.humansareweak.visualprospectingplus.Utils;
import gregapi.data.CS;
import gregapi.data.MT;
import gregapi.oredict.OreDictMaterial;
import gregapi.worldgen.StoneLayer;
import gregapi.worldgen.StoneLayerOres;
import gregapi.worldgen.WorldgenObject;
import net.minecraft.client.resources.I18n;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import static de.humansareweak.visualprospectingplus.Utils.isBartworksInstalled;
import static de.humansareweak.visualprospectingplus.Utils.isNEIInstalled;

public class VeinTypeCaching implements Runnable {

    private static BiMap<Short, VeinType> veinTypeLookupTableForIds = HashBiMap.create();
    private static Map<String, VeinType> veinTypeLookupTableForNames = new HashMap<>();
    private static Map<String, Short> veinTypeStorageInfo;
    public static List<VeinType> veinTypes;
    public static Set<StoneLayerOres> largeVeinOres;
    private static int longesOreName = 0;
    private static final Set<WorldgenObject> worldGenObjects = new HashSet<>();

    static { for(var allGenObjs : CS.GEN_ALL) { worldGenObjects.addAll(allGenObjs); } }

    // BartWorks initializes veins in FML preInit
    // GalacticGreg initializes veins in FML postInit, but only copies all base game veins to make them available on all planets
    // GregTech initializes veins in a thread in FML postInit
    // Therefore, this method must be called after GregTech postInit
    public void run() {
        veinTypes = new ArrayList<>();
        largeVeinOres = new HashSet<>();
        veinTypes.add(VeinType.NO_VEIN);
        veinTypes.addAll(StoneLayer.LAYERS.stream()
            .map(e -> new VeinType("layer." + e.mStone.getUnlocalizedName(), e ))
            .toList()
        );

        loadVeinTypeStorageInfo();
        final Optional<Short> maxVeinTypeIdOptional = veinTypeStorageInfo.values().stream().max(Short::compare);
        short maxVeinTypeId = maxVeinTypeIdOptional.isPresent() ? maxVeinTypeIdOptional.get() : 0;

        for(VeinType veinType : veinTypes) {
            if(veinTypeStorageInfo.containsKey(veinType.name)) {
                veinType.veinId = veinTypeStorageInfo.get(veinType.name);
            }
            else {
                maxVeinTypeId++;
                veinType.veinId = maxVeinTypeId;
                veinTypeStorageInfo.put(veinType.name, veinType.veinId);
            }
            // Build LUT (id <-> object)
            veinTypeLookupTableForIds.put(veinType.veinId, veinType);

            // Build LUT (name -> object)
            veinTypeLookupTableForNames.put(veinType.name, veinType);

            // Build large vein LUT
            if(veinType.canOverlapIntoNeighborOreChunk()) {
                largeVeinOres.addAll(veinType.getOres());
            }
        }
        saveVeinTypeStorageInfo();

        for(VeinType veinType : veinTypes) {
            if(veinType.name.length() > longesOreName) {
                longesOreName = veinType.name.length();
            }
        }
    }

    private OreDictMaterial getGregTechMaterial(short metaId) {
        final OreDictMaterial material = OreDictMaterial.get(metaId);
        if(material == null) {
            // Some materials are not registered in dev when their usage mod is not available.
            return MT.ALL_MATERIALS_REGISTERED_HERE.stream().filter(e -> e.mID == metaId).findFirst().get();
        }
        return material;
    }

    public static int getLongesOreNameLength() {
        return longesOreName;
    }

    public static short getVeinTypeId(VeinType veinType) {
        return veinTypeLookupTableForIds.inverse().get(veinType);
    }

    public static VeinType getVeinType(short veinTypeId) {
        return veinTypeLookupTableForIds.getOrDefault(veinTypeId, VeinType.NO_VEIN);
    }

    public static VeinType getVeinType(String veinTypeName) {
        return veinTypeLookupTableForNames.getOrDefault(veinTypeName, VeinType.NO_VEIN);
    }

    private static File getVeinTypeStorageInfoFile() {
        final File directory = Utils.getSubDirectory(Tags.VISUALPROSPECTING_DIR);
        directory.mkdirs();
        return new File(directory, "veintypesLUT");
    }

    private static void loadVeinTypeStorageInfo() {
        veinTypeStorageInfo = Utils.readFileToMap(getVeinTypeStorageInfoFile());
    }

    private static void saveVeinTypeStorageInfo() {
        Utils.writeMapToFile(getVeinTypeStorageInfoFile(), veinTypeStorageInfo);
    }

    public static void recalculateNEISearch() {
        if(isNEIInstalled()) {
            final boolean isSearchActive = SearchField.searchInventories();
            final String searchString = NEIClientConfig.getSearchExpression().toLowerCase();

            for (VeinType veinType : veinTypes) {
                if (veinType != VeinType.NO_VEIN) {
                    if (isSearchActive) {
                        List<String> searchableStrings = veinType.getOreMaterialNames();
                        searchableStrings.add(I18n.format(veinType.name));
                        searchableStrings = searchableStrings.stream().map(String::toLowerCase).filter(searchableString -> searchableString.contains(searchString)).collect(Collectors.toList());
                        veinType.setNEISearchHeighlight(searchableStrings.isEmpty() == false);
                    } else {
                        veinType.setNEISearchHeighlight(true);
                    }
                }
            }
        }
    }
}
