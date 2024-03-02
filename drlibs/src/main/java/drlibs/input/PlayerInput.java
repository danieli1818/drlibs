package drlibs.input;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

import org.bukkit.entity.Player;

public interface PlayerInput {

	public CompletableFuture<String> getPlayerInput(Player player);
	
	public void askPlayerInput(Player player, BiConsumer<Player, String> inputConsumer);
	
}
