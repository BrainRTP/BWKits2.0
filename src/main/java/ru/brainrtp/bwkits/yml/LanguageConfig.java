package ru.brainrtp.bwkits.yml;

import org.bukkit.plugin.java.JavaPlugin;
import ru.brainrtp.bwkits.utils.ColorUtils;

import java.text.MessageFormat;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LanguageConfig extends YmlConfig {

    private Map<String, String> messageMap;

    public LanguageConfig(JavaPlugin plugin) {
        super(plugin, "lang", true);
        this.update();
    }

    @Override
    public void reload() {
        super.reload();
        this.update();
    }

    private void update() {
        messageMap = yml.getKeys(false).stream().filter(yml::isString).collect(Collectors.toMap(Function.identity(), s -> ColorUtils.color(yml.getString(s))));
    }

    public String getMsg(String path, boolean needPrefix, String... replacements) {
        String msg = MessageFormat.format(messageMap.get(path), (Object[]) replacements);
        if (needPrefix) {
            msg = messageMap.get("prefix") + msg;
        }
        return msg;
    }

}