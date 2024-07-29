package cl.lxdevelop.ascensor;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Ascensor extends JavaPlugin implements Listener {

    public static final String PLUGIN_NAME = ChatColor.RED + "[Ascensor Plugin]";
    private final String version = getDescription().getVersion();

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(PLUGIN_NAME + ChatColor.GREEN + " Activado, version: " + version);
        getLogger().info(PLUGIN_NAME + " ¡El plugin se ha habilitado!");
        Bukkit.getPluginManager().registerEvents(this, this);
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
            Block placaArriba = encontrarPlacaArriba(block);
            if (placaArriba != null) {
                teleportarJugador(player, placaArriba);
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

    private void teleportarJugador(Player player, Block destino) {
        player.teleport(destino.getLocation().add(0.5, 0.5, 0.5));
        player.sendMessage(PLUGIN_NAME + " Te has teletransportado hacia arriba.");
    }
}