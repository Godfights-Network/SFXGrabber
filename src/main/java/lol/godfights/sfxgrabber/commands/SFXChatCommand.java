package lol.godfights.sfxgrabber.commands;

import lol.godfights.sfxgrabber.SFXGrabber;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class SFXChatCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "sfxchat";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/sfxchat";
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        SFXGrabber.toggleSFXChat = !SFXGrabber.toggleSFXChat;
        EnumChatFormatting color = SFXGrabber.toggleSFXChat ? EnumChatFormatting.GREEN : EnumChatFormatting.RED;
        String status = SFXGrabber.toggleSFXChat ? "now" : "no longer";
        sender.addChatMessage(new ChatComponentText(color + "SFXGrabber will " + status + " print sound effects to chat."));
    }
}
