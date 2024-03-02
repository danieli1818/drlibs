package drlibs.events.inventory.moveitemtootherinventory;

import java.util.HashMap;
import java.util.Map;

public class SlotsAmounts {

	private Map<Integer, Integer> fromInventorySlotsAmounts;
	private Map<Integer, Integer> toInventorySlotsAmounts;

	public SlotsAmounts(Map<Integer, Integer> fromInventorySlotsAmounts, Map<Integer, Integer> toInventorySlotsAmounts) {
		this.fromInventorySlotsAmounts = fromInventorySlotsAmounts;
		this.toInventorySlotsAmounts = toInventorySlotsAmounts;
	}
	
	public SlotsAmounts(int fromSlot, int fromSlotAmount, Map<Integer, Integer> toInventorySlotsAmounts) {
		this.fromInventorySlotsAmounts = new HashMap<>();
		this.fromInventorySlotsAmounts.put(fromSlot, fromSlotAmount);
		this.toInventorySlotsAmounts = toInventorySlotsAmounts;
	}

	public Map<Integer, Integer> getFromInventorySlotsAmounts() {
		return fromInventorySlotsAmounts;
	}

	public Map<Integer, Integer> getToInventorySlotsAmounts() {
		return toInventorySlotsAmounts;
	}

}