package com.mayhem.rs2.content.shopping.impl;

import com.mayhem.rs2.content.shopping.Shop;
import com.mayhem.rs2.entity.item.Item;
import com.mayhem.rs2.entity.player.Player;
import com.mayhem.rs2.entity.player.net.out.impl.SendMessage;

/**
 * Skill point store
 * 
 * @author Divine
 */
public class SkillPointShop2 extends Shop {

	/**
	 * Id of Skill point shop
	 */
	public static final int SHOP_ID = 121;

	/**
	 * Price of items in Skill point store
	 * 
	 * @param id
	 * @return
	 */
	public static final int getPrice(int id) {
	switch (id) {
	
		case 10941:
			return 20000;
		case 10939:
			return 25000;
		case 10940:
			return 25000;
		case 10933:
			return 15000;
		case 20708:
			return 20000;
		case 20704:
			return 25000;
		case 20706:
			return 25000;
		case 20710:
			return 15000;
		case 20712:
			return 15000;
		case 12013:
			return 20000;
		case 12014:
			return 25000;
		case 12015:
			return 25000;
		case 12016:
			return 15000;
		case 5554:
			return 20000;
		case 5553:
			return 25000;
		case 5555:
			return 25000;
		case 5556:
			return 15000;
		case 5557:
			return 15000;
		case 13258:
			return 20000;
		case 13259:
			return 25000;
		case 13260:
			return 25000;
		case 13261:
			return 15000;

	}
	return 2147483647;
}

/**
 * All items in skill point shop
 */
public SkillPointShop2() {
	super(SHOP_ID, new Item[] { 
			new Item(10941, 1),
			new Item(10939, 1),
			new Item(10940, 1),
			new Item(10933, 1),
			new Item(20708, 1),
			new Item(20704, 1),
			new Item(20706, 1),
			new Item(20710, 1),
			new Item(20712, 1),
			new Item(13258, 1),
			new Item(13259, 1),
			new Item(13260, 1),
			new Item(13261, 1),
			new Item(12013, 1),
			new Item(12014, 1),
			new Item(12015, 1),
			new Item(12016, 1),
			new Item(5554, 1),
			new Item(5553, 1),
			new Item(5555, 1),
			new Item(5556, 1),
			new Item(5557, 1),
			}, 
			false, "Skill point Shop");
}

	@Override
	public void buy(Player player, int slot, int id, int amount) {
		if (!hasItem(slot, id))
			return;
		if (get(slot).getAmount() == 0)
			return;
		if (amount > get(slot).getAmount()) {
			amount = get(slot).getAmount();
		}

		Item buying = new Item(id, amount);

		if (!player.getInventory().hasSpaceFor(buying)) {
			if (!buying.getDefinition().isStackable()) {
				int slots = player.getInventory().getFreeSlots();
				if (slots > 0) {
					buying.setAmount(slots);
					amount = slots;
				} else {
					player.getClient().queueOutgoingPacket(new SendMessage("You do not have enough inventory space to buy this item."));
				}
			} else {
				player.getClient().queueOutgoingPacket(new SendMessage("You do not have enough inventory space to buy this item."));
				return;
			}
		}

		if (player.getskillPoints() < amount * getPrice(id)) {
			player.getClient().queueOutgoingPacket(new SendMessage("You do not have enough Skill points to buy that."));
			return;
		}

		player.setskillPoints(player.getskillPoints() - amount * getPrice(id));

		//InterfaceHandler.writeText(new QuestTab(player));

		player.getInventory().add(buying);
		update();
	}

	@Override
	public int getBuyPrice(int id) {
		return 0;
	}

	@Override
	public String getCurrencyName() {
		return "Skill Points";
	}

	@Override
	public int getSellPrice(int id) {
		return getPrice(id);
	}

	@Override
	public boolean sell(Player player, int id, int amount) {
		player.getClient().queueOutgoingPacket(new SendMessage("You cannot sell items to this shop."));
		return false;
	}
}
