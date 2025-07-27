package lol.godfights.sfxgrabber.mixin;

import lol.godfights.sfxgrabber.SFXGrabber;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({NetHandlerPlayClient.class})
public abstract class MixinNetHandlerPlayClient {
    @Inject(at = {@At("RETURN")}, method = {"handleTimeUpdate(Lnet/minecraft/network/play/server/S03PacketTimeUpdate;)V"})
    private void onTimeUpdate(S03PacketTimeUpdate packetIn, CallbackInfo ci) {
        SFXGrabber.tpsManager.onServerTimeUpdate(packetIn.getTotalWorldTime());
    }
}