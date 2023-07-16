package de.humansareweak.visualprospectingplus.mixins.xaerosminimap;

import de.humansareweak.visualprospectingplus.integration.xaerominimap.waypoints.WaypointManager;
import de.humansareweak.visualprospectingplus.integration.xaerominimap.XaeroMiniMapState;
import net.minecraft.client.Minecraft;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xaero.common.XaeroMinimapSession;
import xaero.common.minimap.waypoints.render.WaypointsIngameRenderer;

@Mixin(value = WaypointsIngameRenderer.class, remap = false)
public class WaypointsIngameRendererMixin {

    @Inject(method = "render",
            at = @At(value = "INVOKE",
                    target = "Lxaero/common/minimap/waypoints/render/WaypointsIngameRenderer;renderWaypointsList(Ljava/util/Collection;DDDLnet/minecraft/entity/Entity;Lnet/minecraft/client/renderer/Tessellator;DDDDFFDIFFLnet/minecraft/util/Vec3;D)V"
            ),
            slice = @Slice(
                    from = @At(value = "FIELD",
                            target = "Lxaero/common/minimap/waypoints/WaypointsManager;customWaypoints:Ljava/util/Hashtable;",
                            opcode = Opcodes.GETSTATIC
                    )
            )
    )
    private void injectPreRenderCustomWaypoints(XaeroMinimapSession sets, float modCustomWaypoints, CallbackInfo ci) {
        for (WaypointManager manager : XaeroMiniMapState.instance.waypointManagers) {
            if (manager.hasWaypoint()) {
                manager.getXWaypoint().notifyDimension(Minecraft.getMinecraft().renderViewEntity.dimension);
            }
        }
    }
}
