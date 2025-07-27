package lol.godfights.sfxgrabber.listener;

import lol.godfights.sfxgrabber.SFXGrabber;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class SoundListener {
    public String soundFormat = "addSound(tick, \"%name%\", %volume%, %pitch%);"; // Example: addSound(tick, "note.harp", 1, 0.571428);
    public boolean listening = false;

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (SFXGrabber.mc.thePlayer == null || SFXGrabber.mc.theWorld == null) return;
        if (event.phase != TickEvent.Phase.START) return; // Only process at the start of the tick

        if (SFXGrabber.grabbing && listening) {
            SFXGrabber.tickCount += (long) (1L / (SFXGrabber.tpsManager.getTps() / 20.0)); // Increment tick count based on TPS
        }
    }

    public long lastTick = 0;

    @SubscribeEvent
    public void onSound(PlaySoundEvent event) {
        if (SFXGrabber.mc.thePlayer == null || SFXGrabber.mc.theWorld == null) return;

        if (SFXGrabber.toggleSFXChat) {
            String soundName = event.name;
            if (soundName != null && !soundName.isEmpty()) {
                SFXGrabber.mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Sound: " + EnumChatFormatting.YELLOW + soundName + EnumChatFormatting.GREEN + ", Volume: " + EnumChatFormatting.YELLOW + event.sound.getVolume() + EnumChatFormatting.GREEN + ", Pitch: " + EnumChatFormatting.YELLOW + event.sound.getPitch()));
            }
        }

        if (SFXGrabber.grabbing) {
            if (!listening) listening = true;
            if (SFXGrabber.tickCount - lastTick != 0) SFXGrabber.currentSequence += "tick += " + (SFXGrabber.tickCount - lastTick) + ";\n";
            lastTick = SFXGrabber.tickCount;
            String soundName = event.name;
            if (soundName != null && !soundName.isEmpty()) {
                SFXGrabber.currentSequence += soundFormat
                        .replace("%name%", soundName)
                        .replace("%volume%", String.valueOf(event.sound.getVolume()))
                        .replace("%pitch%", String.valueOf(event.sound.getPitch())) + "\n";
            }
        } else {
            listening = false;
        }
    }
}
