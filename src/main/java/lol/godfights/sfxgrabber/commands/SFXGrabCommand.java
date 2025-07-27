package lol.godfights.sfxgrabber.commands;

import lol.godfights.sfxgrabber.SFXGrabber;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class SFXGrabCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "sfxgrab";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/sfxgrab <start/save> [filename]";
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, "start", "save");
        }
        return null;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length < 1) {
            SFXGrabber.mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Invalid arguments, usage: " + getCommandUsage(SFXGrabber.mc.thePlayer)));
            return;
        }

        String action = args[0].toLowerCase();
        switch (action) {
            case "start":
                SFXGrabber.tickCount = 0;
                SFXGrabber.currentSequence = "";
                SFXGrabber.grabbing = true;
                sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Started grabbing SFX to specified file path."));
                break;
            case "save":
                if (args.length < 2) {
                    SFXGrabber.mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Please specify a filename to save the SFX sequence."));
                }

                SFXGrabber.grabbing = false;
                SFXGrabber.mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Stopped grabbing SFX, saving..."));
                saveSequenceToFile(args[1]);
                break;
            default:
                SFXGrabber.mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Invalid arguments, usage: " + getCommandUsage(SFXGrabber.mc.thePlayer)));

        }
    }

    public void saveSequenceToFile(String fileName) {
        String filePath = SFXGrabber.mc.mcDataDir.getAbsolutePath() + "/sfx_sequences/" + fileName + ".txt";
        try {
            Files.write(
                    Paths.get(filePath),
                    SFXGrabber.currentSequence.getBytes(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            );
            SFXGrabber.mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "SFX sequence saved to " + EnumChatFormatting.YELLOW + filePath));
        } catch (Exception e) {
            SFXGrabber.mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Failed to save SFX sequence: " + e.getMessage()));
            e.printStackTrace();
        }
    }
}