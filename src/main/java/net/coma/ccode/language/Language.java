package net.coma.ccode.language;

import net.coma.ccode.CCode;
import net.coma.ccode.utils.ConfigUtils;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class Language extends ConfigUtils {
    public Language() {
        super(CCode.getInstance().getDataFolder().getPath() + File.separator + "locales", "messages_en");

        YamlConfiguration yml = getYml();

        yml.addDefault("prefix", "&b&lCODE &8| ");
        yml.addDefault("messages.no-permission", "&cYou do not have permission to do this!");
        yml.addDefault("messages.reload", "&aI have successfully reloaded the files!");
        yml.addDefault("messages.player-required", "&cPlayer is required!");
        yml.addDefault("messages.create-right-usage", "&cUsage: /codecreate [name] [uses] [cmd]");
        yml.addDefault("messages.edit-right-usage", "&cUsage: /codeedit [name] name|uses|cmd [new cmd|uses|name]");
        yml.addDefault("messages.give-right-usage", "&cUsage: /codegive [name] [player]");
        yml.addDefault("messages.already-created", "&cThis code is already exists!");
        yml.addDefault("messages.usage-redeem", "&cUsage: /redeem [name]");
        yml.addDefault("messages.usage-delete", "&cUsage: /deletecode [name]");
        yml.addDefault("messages.already-redeemed", "&cYou have already redeemed this code!");
        yml.addDefault("messages.redeemed", "&aYou have successfully redeemed this code!");
        yml.addDefault("messages.deleted", "&aYou have successfully deleted this code!");
        yml.addDefault("messages.need-number", "&cYou need to type in a number!");
        yml.addDefault("messages.uses-zero", "&cYou can't redeem this code anymore!");
        yml.addDefault("messages.cant-be-negative", "&cThe number can't be negative!");
        yml.addDefault("messages.created", "&aYou have successfully created a new code!");
        yml.addDefault("messages.not-exists", "&cThere is no such a code in the database!");
        yml.addDefault("messages.edit-cmd", "&aYou have successfully changed the command of the code!");
        yml.addDefault("messages.edit-uses", "&aYou have successfully changed the uses of the code!");
        yml.addDefault("messages.edit-name", "&aYou have successfully changed the name of the code!");
        yml.addDefault("messages.not-an-owner", "&cYou are not an owner of this code!");
        yml.addDefault("messages.already-owner", "&cThe player is already an owner!");
        yml.addDefault("messages.successful-give", "&aYou have successfully gave the player permission to the code!");
        yml.addDefault("messages.first-page", "&cYou are already on the first page!");
        yml.addDefault("messages.last-page", "&cYou are already on the last page!");


        yml.options().copyDefaults(true);
        save();
    }
}
