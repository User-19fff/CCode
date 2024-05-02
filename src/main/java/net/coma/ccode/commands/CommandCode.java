package net.coma.ccode.commands;

import net.coma.ccode.CCode;
import net.coma.ccode.enums.MessageKeys;
import net.coma.ccode.managers.Code;
import net.coma.ccode.menu.menus.CodeMenu;
import net.coma.ccode.utils.MenuUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Subcommand;

import java.util.Objects;

@Command({"code", "ccode", "dvoucher"})
public class CommandCode {
    @Subcommand("reload")
    public void reload(CommandSender sender) {
        if (!sender.hasPermission("ccode.reload")) {
            sender.sendMessage(MessageKeys.NO_PERMISSION.getMessage());
            return;
        }

        CCode.getInstance().getLanguage().reload();
        CCode.getInstance().getConfiguration().reload();
        CCode.getDatabaseManager().reconnect(Objects.requireNonNull(CCode.getInstance().getConfiguration().getSection("database.mysql")));
        sender.sendMessage(MessageKeys.RELOAD.getMessage());
    }

    @Subcommand("menu")
    public void menu(CommandSender sender) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(MessageKeys.PLAYER_REQUIRED.getMessage());
            return;
        }

        new CodeMenu(MenuUtils.getMenuUtils(player)).open();
    }

    @Subcommand("create")
    public void create(CommandSender sender, @NotNull String name, int uses, @NotNull String command) {
        if (!sender.hasPermission("ccode.create")) {
            sender.sendMessage(MessageKeys.NO_PERMISSION.getMessage());
            return;
        }

        if (CCode.getDatabaseManager().exists(name)) {
            sender.sendMessage(MessageKeys.ALREADY_EXISTS.getMessage());
            return;
        }

        if (uses < 0) {
            sender.sendMessage(MessageKeys.CANT_BE_NEGATIVE.getMessage());
            return;
        }

        Code code = new Code(name, (command + " ").trim(), uses);
        CCode.getDatabaseManager().createCode(code.codeName(), code.command(), code.uses());
        sender.sendMessage(MessageKeys.CREATED.getMessage());
    }

    @Subcommand("delete")
    public void delete(CommandSender sender, @NotNull String name) {
        if (!sender.hasPermission("ccode.delete")) {
            sender.sendMessage(MessageKeys.NO_PERMISSION.getMessage());
            return;
        }

        if (!CCode.getDatabaseManager().exists(name)) {
            sender.sendMessage(MessageKeys.NOT_EXISTS.getMessage());
            return;
        }

        CCode.getDatabaseManager().deleteCode(name);
        sender.sendMessage(MessageKeys.DELETED.getMessage());
    }

    @Subcommand("edituse")
    public void edituse(CommandSender sender, @NotNull String name, int newUse) {
        if (!sender.hasPermission("ccode.edituse")) {
            sender.sendMessage(MessageKeys.NO_PERMISSION.getMessage());
            return;
        }

        if (!CCode.getDatabaseManager().exists(name)) {
            sender.sendMessage(MessageKeys.NOT_EXISTS.getMessage());
            return;
        }

        if (newUse < 0) {
            sender.sendMessage(MessageKeys.CANT_BE_NEGATIVE.getMessage());
            return;
        }

        CCode.getDatabaseManager().changeUses(name, newUse);
        sender.sendMessage(MessageKeys.EDIT_USES.getMessage());
    }

    @Subcommand("editname")
    public void editname(CommandSender sender, @NotNull String name, String newName) {
        if (!sender.hasPermission("ccode.editname")) {
            sender.sendMessage(MessageKeys.NO_PERMISSION.getMessage());
            return;
        }

        if (!CCode.getDatabaseManager().exists(name)) {
            sender.sendMessage(MessageKeys.NOT_EXISTS.getMessage());
            return;
        }

        CCode.getDatabaseManager().changeName(name, newName);
        sender.sendMessage(MessageKeys.EDIT_NAME.getMessage());
    }

    @Subcommand("editcommand")
    public void editcommand(CommandSender sender, @NotNull String name, String newCommand) {
        if (!sender.hasPermission("ccode.editcommand")) {
            sender.sendMessage(MessageKeys.NO_PERMISSION.getMessage());
            return;
        }

        if (!CCode.getDatabaseManager().exists(name)) {
            sender.sendMessage(MessageKeys.NOT_EXISTS.getMessage());
            return;
        }

        CCode.getDatabaseManager().changeCommand(name, (newCommand + " ").trim());
        sender.sendMessage(MessageKeys.EDIT_NAME.getMessage());
    }

    @Subcommand("add")
    public void add(CommandSender sender, @NotNull String name, @NotNull String target) {
        OfflinePlayer targetPlayer = Bukkit.getOfflinePlayer(target);

        if (!sender.hasPermission("ccode.give")) {
            sender.sendMessage(MessageKeys.NO_PERMISSION.getMessage());
            return;
        }

        if (!CCode.getDatabaseManager().exists(name)) {
            sender.sendMessage(MessageKeys.NOT_EXISTS.getMessage());
            return;
        }

        CCode.getDatabaseManager().giveCode(name, targetPlayer);
        sender.sendMessage(MessageKeys.SUCCESSFUL_ADD.getMessage());
    }

    @Subcommand("redeem")
    public void redeem(Player player, @NotNull String name) {
        if (!CCode.getDatabaseManager().exists(name)) {
            player.sendMessage(MessageKeys.NOT_EXISTS.getMessage());
            return;
        }

        if (CCode.getDatabaseManager().isUsesZero(name)) {
            player.sendMessage(MessageKeys.USES_ZERO.getMessage());
            return;
        }

        if (!CCode.getDatabaseManager().isOwned(name, player)) {
            player.sendMessage(MessageKeys.NOT_AN_OWNER.getMessage());
            return;
        }

        CCode.getDatabaseManager().redeemCode(name, player);
        player.sendMessage(MessageKeys.REDEEMED.getMessage());
    }
}
