package off.safe.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import fi.dy.masa.litematica.compat.iris.IrisCompat;

import off.safe.LitematicaPatch;

/**
 * Skips Litematica's Iris pipeline assignment.
 * <p>
 * Upstream {@code IrisCompat.registerPipelines()} maps Litematica's own
 * {@code litematica:pipeline/legacy/*} pipelines to Iris programs
 * (TERRAIN_SOLID / TERRAIN_CUTOUT / TRANSLUCENT / LINES). That registration is
 * unfinished for the 26.3 renderpearl/Vulkan path, so Iris tries to compile
 * generated {@code sodium_terrain_*} shader variants and fails:
 * <pre>
 * failed to compile sodium_terrain_translucent.vsh:186:
 *   'constructor' : too many arguments
 * </pre>
 * Cancelling the method is equivalent to upstream's
 * {@code ASSIGN_TO_IRIS = false} switch.
 * <p>
 * {@code remap = false} because the target is another mod's class, which is not
 * covered by the Minecraft mappings.
 */
@Mixin(value = IrisCompat.class, remap = false)
public class MixinIrisCompat
{
	@Inject(method = "registerPipelines", at = @At("HEAD"), cancellable = true)
	private static void litematicaPatch$skipIrisPipelineAssignment(CallbackInfo ci)
	{
		LitematicaPatch.LOGGER.info("Skipped Litematica -> Iris pipeline assignment");
		ci.cancel();
	}
}
