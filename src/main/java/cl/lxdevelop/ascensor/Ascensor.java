package cl.lxdevelop.ascensor;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class Ascensor extends JavaPlugin implements Listener {

    private static final String PLUGIN_NAME = "[Ascensor Plugin]";
    private final String version = getDescription().getVersion();
    public static final Logger log = Bukkit.getLogger();
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        Component border = Component.text("=============================").color(NamedTextColor.AQUA);
        Component pluginName = Component.text("        [" + PLUGIN_NAME + "]").color(NamedTextColor.GREEN);
        Component mode = Component.text("        Standalone Mode").color(NamedTextColor.BLUE);
        Component versionInfo = Component.text("Version: ").color(NamedTextColor.YELLOW)
                .append(Component.text(version).color(NamedTextColor.RED));
        Component status = Component.text("¡El plugin se ha habilitado!").color(NamedTextColor.GREEN);
        Bukkit.getServer().getConsoleSender().sendMessage(border);
        Bukkit.getServer().getConsoleSender().sendMessage(pluginName);
        Bukkit.getServer().getConsoleSender().sendMessage(mode);
        Bukkit.getServer().getConsoleSender().sendMessage(border);
        Bukkit.getServer().getConsoleSender().sendMessage(versionInfo);
        Bukkit.getServer().getConsoleSender().sendMessage(status);
        Bukkit.getServer().getConsoleSender().sendMessage(border);
    }

    @Override
    public void onDisable() {
        getLogger().info("El plugin se ha deshabilitado.");
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Block block = player.getLocation().getBlock();

        if (block.getType() == Material.STONE_PRESSURE_PLATE) {
            float pitch = player.getLocation().getPitch();
            if (pitch > 70) {
                Block placaAbajo = encontrarPlacaAbajo(block);
                if (placaAbajo != null) {
                    teleportarJugador(player, placaAbajo);
                }
            } else if (pitch < -60) {
                Block placaArriba = encontrarPlacaArriba(block);
                if (placaArriba != null) {
                    teleportarJugador(player, placaArriba);
                }
            }
        }
    }


    private Block encontrarPlacaArriba(Block blockInicial) {
        int x = blockInicial.getX();
        int z = blockInicial.getZ();
        for (int y = blockInicial.getY() + 1; y < blockInicial.getWorld().getMaxHeight(); y++) {
            Block blockActual = blockInicial.getWorld().getBlockAt(x, y, z);
            if (blockActual.getType() == Material.STONE_PRESSURE_PLATE) {
                return blockActual;
            }
        }
        return null;
    }

    private Block encontrarPlacaAbajo(Block blockInicial) {
        int x = blockInicial.getX();
        int z = blockInicial.getZ();

        for (int y = blockInicial.getY() - 1; y > 0; y--) {
            Block blockActual = blockInicial.getWorld().getBlockAt(x, y, z);
            if (blockActual.getType() == Material.STONE_PRESSURE_PLATE) {
                return blockActual;
            }
        }
        return null;
    }

    private void teleportarJugador(Player player, Block destino) {
        player.teleport(destino.getLocation().add(0.5, 0.5, 0.5));
        Component message = Component.text(PLUGIN_NAME)
                .append(Component.text(" ¡Te has teletransportado!")
                        .color(NamedTextColor.YELLOW));

        player.sendMessage(message);
    }
}