package drlibs.services.input;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEditBookEvent;

import drlibs.services.DRLibsPluginService;

public class InputService extends DRLibsPluginService implements Listener {

	private Map<UUID, CompletableFuture<String>> playerFutureInputs;
	
	public static InputService instance;
	
	private InputService() {
		this.playerFutureInputs = new HashMap<>();
	}
	
	public static InputService getInstance() {
		if (instance == null) {
			instance = new InputService();
		}
		return instance;
	}
	
	@EventHandler
	public void onPlayerEditBookEvent(PlayerEditBookEvent event) {
		CompletableFuture<String> futureInput = playerFutureInputs.remove(event.getPlayer().getUniqueId());
		if (futureInput != null) {
			futureInput.complete(String.join("", event.getNewBookMeta().getPages()));
		}
	}
	
	public CompletableFuture<String> createPlayerFuture(UUID uuid) {
		playerFutureInputs.put(uuid, new CompletableFuture<String>());
		return playerFutureInputs.get(uuid);
	}
	
	@Override
	public boolean registerService() {
		if (isRegistered()) {
			return true;
		}
		Bukkit.getPluginManager().registerEvents(this, getPlugin());
		setIsRegistered(true);
		return true;
	}
	
}
