package off.safe.client;

import net.fabricmc.api.ClientModInitializer;

import off.safe.LitematicaPatch;

public class LitematicaPatchClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		LitematicaPatch.LOGGER.info("Patch active: Litematica -> Iris pipeline assignment is skipped");
	}
}