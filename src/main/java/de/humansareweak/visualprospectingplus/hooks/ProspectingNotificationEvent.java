package de.humansareweak.visualprospectingplus.hooks;

import de.humansareweak.visualprospectingplus.database.OreVeinPosition;
import de.humansareweak.visualprospectingplus.database.UndergroundFluidPosition;

import cpw.mods.fml.common.eventhandler.Event;

public class ProspectingNotificationEvent extends Event {

	@Override
	public boolean isCancelable() {
		return false;
	}

	public static class OreVein extends ProspectingNotificationEvent {

		private final OreVeinPosition position;

		public OreVein(OreVeinPosition position) {
			this.position = position;
		}

		public OreVeinPosition getPosition() {
			return this.position;
		}

	}

	public static class UndergroundFluid extends ProspectingNotificationEvent {

		private final UndergroundFluidPosition position;

		public UndergroundFluid(UndergroundFluidPosition position) {
			this.position = position;
		}

		public UndergroundFluidPosition getPosition() {
			return this.position;
		}

	}

}
