package net.coma.ccode;

import lombok.Getter;
import net.coma.ccode.config.Config;
import net.coma.ccode.database.AbstractDatabase;
import net.coma.ccode.database.MySQL;
import net.coma.ccode.language.Language;
import net.coma.ccode.utils.CommandRegister;
import net.coma.ccode.utils.ListenerRegister;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.Objects;

public final class CCode extends JavaPlugin {
    @Getter
    private static CCode instance;
    @Getter
    private static AbstractDatabase databaseManager;
    private static Config config;
    private static Language language;

    @Override
    public void onEnable() {
        instance = this;

        initializeComponents();
        registerListenersAndCommands();
        initializeDatabaseManager();

        MySQL mysql = (MySQL) databaseManager;
        mysql.createTable();
    }

    @Override
    public void onDisable() {
        if (databaseManager != null) databaseManager.disconnect();
    }


    public Config getConfiguration() {
        return config;
    }

    public Language getLanguage() {
        return language;
    }

    private void initializeComponents() {
        language = new Language();
        config = new Config();
    }

    private void registerListenersAndCommands() {
        ListenerRegister.registerEvents();
        CommandRegister.registerCommands();
    }

    private void initializeDatabaseManager() {
        try {
            databaseManager = new MySQL(Objects.requireNonNull(getConfiguration().getSection("database.mysql")));
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }
}
