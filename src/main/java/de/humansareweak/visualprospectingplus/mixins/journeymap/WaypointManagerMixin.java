package de.humansareweak.visualprospectingplus.mixins.journeymap;

import de.humansareweak.visualprospectingplus.integration.model.MapState;
import de.humansareweak.visualprospectingplus.integration.model.layers.LayerManager;
import de.humansareweak.visualprospectingplus.integration.model.layers.WaypointProviderManager;
import journeymap.client.ui.waypoint.WaypointManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(WaypointManager.class)
public class WaypointManagerMixin {

    @Inject(method = "toggleItems", at = @At("HEAD"), remap = false, require = 1, locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    private void onToggleAllWaypoints(boolean enable, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if(enable == false) {
            for(LayerManager layer : MapState.instance.layers) {
                if(layer instanceof WaypointProviderManager) {
                    ((WaypointProviderManager) layer).clearActiveWaypoint();
                }
            }
        }
    }
}
