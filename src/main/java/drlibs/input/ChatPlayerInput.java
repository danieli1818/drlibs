package drlibs.input;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

import drlibs.DRLibs;

public class ChatPlayerInput implements PlayerInput {

	private String inputQuery;

	public ChatPlayerInput(String inputQuery) {
		this.inputQuery = inputQuery;
	}

	@Override
	public CompletableFuture<String> getPlayerInput(Player player) {
		CompletableFuture<String> result = new CompletableFuture<String>();
		StringPrompt prompt = new StringPrompt() {

			@Override
			public String getPromptText(ConversationContext context) {
				return inputQuery;
			}

			@Override
			public Prompt acceptInput(ConversationContext context, String input) {
				result.complete(input);
				return END_OF_CONVERSATION;
			}
		};
		new ConversationFactory(DRLibs.getPlugin(DRLibs.class)).withFirstPrompt(prompt).buildConversation(player)
				.begin();
		return result;
	}

	@Override
	public void askPlayerInput(Player player, BiConsumer<Player, String> inputConsumer) {
		StringPrompt prompt = new StringPrompt() {

			@Override
			public String getPromptText(ConversationContext context) {
				return ChatColor.translateAlternateColorCodes('&', inputQuery);
			}

			@Override
			public Prompt acceptInput(ConversationContext context, String input) {
				inputConsumer.accept(player, input);
				return END_OF_CONVERSATION;
			}
		};
		new ConversationFactory(DRLibs.getPlugin(DRLibs.class)).withFirstPrompt(prompt).buildConversation(player)
				.begin();
	}

}
