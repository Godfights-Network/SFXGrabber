package lol.godfights.sfxgrabber;

import lol.godfights.sfxgrabber.commands.SFXChatCommand;
import lol.godfights.sfxgrabber.commands.SFXGrabCommand;
import lol.godfights.sfxgrabber.listener.SoundListener;
import lol.godfights.sfxgrabber.managers.TPSManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventBus;

@Mod(name = SFXGrabber.NAME, version = SFXGrabber.VERSION, modid = SFXGrabber.MODID, useMetadata = true)
public class SFXGrabber {
    public static final String NAME = "@NAME@";
    public static final String VERSION = "@VER@";
    public static final String MODID = "@ID@";

    public static SFXGrabber instance;
    public static final Minecraft mc = Minecraft.getMinecraft();

    public static TPSManager tpsManager;

    public static boolean toggleSFXChat = false;

    public static long tickCount = 0;
    public static boolean grabbing = false;
    public static String currentSequence = "";

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        instance = this;
        tpsManager = new TPSManager();

        registerListeners();
        registerCommands();
    }

    public void registerListeners() {
        EventBus bus = MinecraftForge.EVENT_BUS;

        bus.register(new SoundListener());
    }

    public void registerCommands() {
        ClientCommandHandler cch = ClientCommandHandler.instance;

        cch.registerCommand(new SFXChatCommand());
        cch.registerCommand(new SFXGrabCommand());
    }
}
