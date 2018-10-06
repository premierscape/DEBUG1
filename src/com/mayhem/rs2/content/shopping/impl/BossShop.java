package com.mayhem.rs2.content.shopping.impl;

import com.mayhem.rs2.content.shopping.Shop;
import com.mayhem.rs2.entity.item.Item;
import com.mayhem.rs2.entity.player.Player;
import com.mayhem.rs2.entity.player.net.out.impl.SendMessage;

/**
 * Boss store
 * 
 * @author Dez
 */
public class BossShop extends Shop {

	/**
	 * Id of Boss shop
	 */
	public static final int SHOP_ID = 349;

	/**
	 * Price of items in Boss Skill store
	 * 
	 * @param id
	 * @return
	 */
	public static final int getPrice(int id) {
	switch (id) {
	
	case 4212:
		return 80;
	case 4224:
		return 300;
	case 13091:
		return 350;
	case 13072:
		return 600;
	case 13073:
		return 600;
	case 10547:
		return 50;
	case 10548:
		return 50;
	case 10549:
		return 50;
	case 10550:
		return 50;
	case 10551:
		return 90;
	case 10555:
		return 70;
	case 10553:
		return 50;
	case 10552:
		return 50;
	case 6585:
		return 80;
	case 12954:
		return 60;
	case 11824:
		return 300;
	case 11832:
		return 450;
	case 11834:
		return 750;
	case 11836:
		return 350;
	case 11826:
		return 550;
	case 11828:
		return 600;
	case 11830:
		return 600;
	case 11785:
		return 750;
	case 11802:
		return 600;
	case 11804:
		return 400;
	case 11806:
		return 550;
	case 11808:
		return 350;
	case 12006:
		return 650;
	case 2902:
	case 2904:
	case 2906:
		return 450;
	case 13576:
		return 800;
	case 13271:
		return 550;
	case 13263:
		return 450;
	case 19553:
		return 750;
	case 19547:
		return 500;
	case 19550:
		return 525;
	case 19544:
		return 475;
	case 13197:
	case 13199:
		return 630;
	case 12929:
		return 700;
	case 11942:
		return 50;
		

	}
	return 2147483647;
}

/**
 * All items in hunter
 */
public BossShop() {
	super(SHOP_ID, new Item[] {
			new Item(11942, 100),
			new Item(13576),
			new Item(13271),
			new Item(13263),
			new Item(12006),
			new Item(11832),
			new Item(11834),
			new Item(11836),
			new Item(11826),
			new Item(11828),
			new Item(11830),
			new Item(11785),
			new Item(12954),
			new Item(11824),
			new Item(13072),
			new Item(13073),
			new Item(10547),
			new Item(10548),
			new Item(10549),
			new Item(10550),
			new Item(10551),
			new Item(10555),
			new Item(10552),
			new Item(12929),
			
			}, 
			false, "Boss Point Shop");
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

		if (player.getbossPoints() < amount * getPrice(id)) {
			player.getClient().queueOutgoingPacket(new SendMessage("You do not have enough Boss points to buy that."));
			return;
		}

		player.setbossPoints(player.getbossPoints() - amount * getPrice(id));

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
		return "Boss Points";
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
