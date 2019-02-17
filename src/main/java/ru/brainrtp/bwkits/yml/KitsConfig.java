package ru.brainrtp.bwkits.yml;

import org.bukkit.plugin.java.JavaPlugin;
import ru.brainrtp.bwkits.utils.ColorUtils;

import java.text.MessageFormat;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class KitsConfig extends YmlConfig {

    private Map<String, String> messageMap;

    public KitsConfig(JavaPlugin plugin) {
        super(plugin, "kits", true);
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

    public String getKit(String path, String... replacements) {
        return MessageFormat.format(messageMap.get(path), (Object[]) replacements);
    }

}