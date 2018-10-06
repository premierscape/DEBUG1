package com.mayhem.rs2.content;

import com.mayhem.core.util.ItemNames;
import com.mayhem.core.util.Utility;
import com.mayhem.rs2.content.achievements.AchievementHandler;
import com.mayhem.rs2.content.achievements.AchievementList;
import com.mayhem.rs2.content.dialogue.DialogueManager;
import com.mayhem.rs2.entity.Animation;
import com.mayhem.rs2.entity.item.Item;
import com.mayhem.rs2.entity.player.Player;
import com.mayhem.rs2.entity.player.net.out.impl.SendMessage;

/**
 * Handles crystal chest
 * 
 * @author Daniel
 *
 */
public class CrystalChest {

	/**
	 * Ids of key halves
	 */
	public static final Item[] KEY_HALVES = { new Item(985), new Item(987) };

	/**
	 * Crystal key Id
	 */
	public static final Item KEY = new Item(989);

	/**
	 * Creates the key
	 * 
	 * @param player
	 */
	public static void createKey(final Player player) {
		if (player.getInventory().contains(KEY_HALVES)) {
			player.getInventory().remove(KEY_HALVES[0]);
			player.getInventory().remove(KEY_HALVES[1]);
			player.getInventory().add(KEY);
			DialogueManager.sendItem1(player, "You have combined the two parts to form a key.", KEY.getId());
		}
	}

	public static final Item[] UNCOMMON_CHEST_REWARDS = { new Item(), new Item(12504), new Item(12502), new Item(12500), new Item(12498), 
			new Item(12506), new Item(12508), new Item(12510), new Item(12512), new Item(ItemNames.ZAMORAK_PLATEBODY), new Item(ItemNames.ZAMORAK_PLATELEGS), new Item(ItemNames.SARADOMIN_PLATEBODY), new Item(ItemNames.SARADOMIN_PLATELEGS), new Item(ItemNames.GUTHIX_PLATEBODY), new Item(ItemNames.GUTHIX_PLATELEGS), 
			new Item(ItemNames.ZAMORAK_FULL_HELM), new Item(ItemNames.GUTHIX_FULL_HELM), new Item(ItemNames.SARADOMIN_FULL_HELM), new Item(ItemNames.DRAGON_MED_HELM), new Item(ItemNames.DRAGON_PLATESKIRT), new Item(ItemNames.DRAGON_SQ_SHIELD), new Item(ItemNames.HELM_OF_NEITIZNOT), 
			new Item(ItemNames.WARRIOR_HELM), new Item(ItemNames.ARCHER_HELM), new Item(ItemNames.FARSEER_HELM), new Item(ItemNames.RUNE_SCIMITAR), new Item(ItemNames.RUNE_2H_SWORD), new Item(ItemNames.DRAGON_DAGGER), new Item(ItemNames.DRAGON_SCIMITAR), new Item(ItemNames.DRAGON_MACE), new Item(ItemNames.DRAGON_BATTLEAXE),
			new Item(ItemNames.DRAGON_LONGSWORD), new Item(20002), new Item(4675),  new Item(10368), new Item(10370), new Item(10372), new Item(10374), new Item(10376), new Item(10378), new Item(10380), new Item(10382), new Item(10444), new Item(10450), new Item(ItemNames.TZHAARKETEM), };

	public static final Item[] RARE_CHEST_REWARDS = { new Item(12026), new Item(12027), new Item(12028), new Item(12029), new Item(12030), new Item(12031), new Item(12033), new Item(12034), new Item(12036), new Item(120238), new Item(12040),
			 new Item(12042),  new Item(12044),  new Item(12046),  new Item(12056), new Item(12058), new Item(12057), new Item(12059), new Item(12061),
			 new Item(12063), new Item(12058), new Item(12060),  new Item(12162), new Item(12064), new Item(12066), new Item(12068),  new Item(12070),
			 new Item(12072), new Item(12074), new Item(12076), new Item(12086), new Item(12087), new Item(12089), new Item(12091), new Item(12093),
			 new Item(12088), new Item(12090), new Item(12092), new Item(12094), new Item(12096), new Item(12192),  new Item(12026), new Item(12098),
			 new Item(12100), new Item(12102), new Item(12104), new Item(12106),
 new Item(12026), new Item(12056), new Item(12086), new Item(20161), new Item(20143), new Item(11836), new Item(ItemNames.UNCUT_DRAGONSTONE), 
			 new Item(ItemNames.UNCUT_ONYX), new Item(ItemNames.DRAGON_SPEAR), new Item(ItemNames.DRAGON_2H_SWORD), new Item(11935, Utility.random(100)), new Item(537, Utility.random(100)), new Item(11944, Utility.random(50)),
	  new Item(12526), new Item(20128), new Item(20131), new Item(20134), new Item(20137), new Item(20140), new Item(8887), new Item(8888), new Item(5565) };

	/**
	 * Chest rewards
	 */
	public static final Item[] COMMON_CHEST_REWARDS = {

			/* Armours */
			new Item(ItemNames.RUNE_FULL_HELM), new Item(ItemNames.RUNE_PLATEBODY), new Item(ItemNames.RUNE_KITESHIELD), new Item(ItemNames.RUNE_PLATELEGS), new Item(ItemNames.RUNE_PLATESKIRT), new Item(ItemNames.RUNE_BOOTS), new Item(ItemNames.RUNE_CHAINBODY), new Item(ItemNames.RUNE_CROSSBOW), new Item(ItemNames.RING_OF_RECOIL),

			/* Skilling */
			new Item(392, Utility.random(50)), new Item(372, Utility.random(50)), new Item(2364, Utility.random(5)), new Item(452, Utility.random(10)), new Item(212, Utility.random(10)), new Item(216, Utility.random(10)), new Item(218, Utility.random(10)), new Item(200, Utility.random(20)), new Item(206, Utility.random(20)), new Item(210, Utility.random(10)), new Item(1618, Utility.random(20)), new Item(1622, Utility.random(20)), new Item(1620, Utility.random(25)), new Item(1624, Utility.random(30)),

			/* Random */
			new Item(ItemNames.TAN_CAVALIER),new Item(20166), new Item(4740, Utility.random(500)), new Item(ItemNames.DARK_CAVALIER), new Item(ItemNames.BLACK_CAVALIER), new Item(ItemNames.BLACK_BERET), new Item(ItemNames.RED_HEADBAND), new Item(ItemNames.PIRATES_HAT), new Item(ItemNames.BROWN_HEADBAND), new Item(ItemNames.SHARK), new Item(ItemNames.MONKEY_NUTS), new Item(ItemNames.EYE_PATCH) };

	/**
	 * Searches the chest
	 * 
	 * @param player
	 * @param x
	 * @param y
	 */

	public static void searchChest(final Player player, final int x, final int y) {
		if (player.getInventory().contains(KEY)) {
			player.send(new SendMessage("You unlock the chest with your key."));
			player.getInventory().remove(KEY);
			AchievementHandler.activate(player, AchievementList.OPEN_70_CRYSTAL_CHESTS, 1);
			player.getUpdateFlags().sendAnimation(new Animation(881));
			player.getInventory().add(new Item(995, Utility.random(50000)));
			Item itemReceived;
			switch (Utility.random(200)) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
			case 10:
				itemReceived = Utility.randomElement(UNCOMMON_CHEST_REWARDS);
				break;
			case 25:
				itemReceived = Utility.randomElement(RARE_CHEST_REWARDS);
				break;
			default:
				itemReceived = Utility.randomElement(COMMON_CHEST_REWARDS);
			}

			player.getInventory().addOrCreateGroundItem(itemReceived.getId(), itemReceived.getAmount(), true);
			player.send(new SendMessage("You find " + Utility.determineIndefiniteArticle(itemReceived.getDefinition().getName()) + " " + itemReceived.getDefinition().getName() + " in the chest."));
			if (itemReceived.getDefinition().getGeneralPrice() < 100_000) {
				switch (Utility.random(200)) {
				case 0:
				case 1:
				case 2:
				case 3:
				case 4:
				case 5:
				case 6:
				case 7:
				case 8:
				case 9:
				case 10:
					itemReceived = Utility.randomElement(UNCOMMON_CHEST_REWARDS);
					break;
				case 25:
					itemReceived = Utility.randomElement(RARE_CHEST_REWARDS);
					break;
				default:
					itemReceived = Utility.randomElement(COMMON_CHEST_REWARDS);
				}
				player.getInventory().addOrCreateGroundItem(itemReceived.getId(), itemReceived.getAmount(), true);
				player.send(new SendMessage("You find " + Utility.determineIndefiniteArticle(itemReceived.getDefinition().getName()) + " " + itemReceived.getDefinition().getName() + " in the chest."));
			}
		} else {
			player.send(new SendMessage("You need a key to open this chest."));
		}
	}

}
