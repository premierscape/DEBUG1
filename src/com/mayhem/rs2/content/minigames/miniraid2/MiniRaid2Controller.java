package com.mayhem.rs2.content.minigames.miniraid2;

import com.mayhem.rs2.content.combat.Combat.CombatTypes;
import com.mayhem.rs2.content.minigames.miniraid2.MiniRaid2Game;
import com.mayhem.rs2.entity.Attributes;
import com.mayhem.rs2.entity.Location;
import com.mayhem.rs2.entity.World;
import com.mayhem.rs2.entity.player.Player;
import com.mayhem.rs2.entity.player.controllers.GenericMinigameController;
import com.mayhem.rs2.entity.player.net.out.impl.SendMessage;

/**
 * MainMonster Controller 
 * @author Mod Divine
 *
 */
public class MiniRaid2Controller extends GenericMinigameController {

	@Override
	public boolean allowMultiSpells() {
		return false;
	}

	@Override
	public boolean allowPvPCombat() {
		return false;
	}

	@Override
	public boolean canAttackNPC() {
		return true;
	}

	@Override
	public boolean canAttackPlayer(Player paramPlayer1, Player paramPlayer2) {
		return false;
	}

	@Override
	public boolean canDrink(Player paramPlayer) {
		return true;
	}

	@Override
	public boolean canEat(Player paramPlayer) {
		return true;
	}

	@Override
	public boolean canEquip(Player paramPlayer, int paramInt1, int paramInt2) {
		return true;
	}
	
	@Override
	public boolean canUnequip(Player player) {		
		return true;
	}

	@Override
	public boolean canDrop(Player player) {
		return true;
	}

	@Override
	public boolean canUseCombatType(Player paramPlayer, CombatTypes paramCombatTypes) {
		return true;
	}

	@Override
	public boolean canUsePrayer(Player paramPlayer, int id) {
		return true;
	}

	@Override
	public boolean canUseSpecialAttack(Player paramPlayer) {
		return true;
	}

	@Override
	public Location getRespawnLocation(Player p) {
		if (p.getAttributes().get(MiniRaid2Game.MINIRAID2_GAME_KEY) != null) {
			if (((MiniRaid2Game) p.getAttributes().get(MiniRaid2Game.MINIRAID2_GAME_KEY)).hasEnded()) {
				return new Location(1643, 3674, 0);
			}
			
		}

		((MiniRaid2Game) p.getAttributes().get(MiniRaid2Game.MINIRAID2_GAME_KEY)).remove(p);
		return new Location(1643, 3674, 0);
	}

	@Override
	public boolean isSafe(Player player) {
		return true;
	}

	@Override
	public void onControllerInit(Player paramPlayer) {
	}

	@Override
	public void onDeath(Player p) {
		if (p.getAttributes().get(MiniRaid2Game.MINIRAID2_GAME_KEY) != null) {
			if (((MiniRaid2Game) p.getAttributes().get(MiniRaid2Game.MINIRAID2_GAME_KEY)).hasEnded()) {
				p.teleport(new Location(1643, 3674, 0));
			}
			
		}
		p.teleport(new Location(1643, 3674, 0));
		((MiniRaid2Game) p.getAttributes().get(MiniRaid2Game.MINIRAID2_GAME_KEY)).remove(p);
		}

	@Override
	public void onDisconnect(Player p) {
		p.teleport(new Location(1643, 3674, 0));
		((MiniRaid2Game) p.getAttributes().get(MiniRaid2Game.MINIRAID2_GAME_KEY)).remove(p);
	}

	@Override
	public void tick(Player paramPlayer) {
	}

	@Override
	public String toString() {
		return "Mini Raid2";
	}
	
	@Override
	public boolean canLogOut() {
		return false;
	}

	@Override
	public boolean canMove(Player p) {
		return true;
	}

	@Override
	public boolean canSave() {
		return true;
	}

	@Override
	public boolean canTalk() {
		return true;
	}

	@Override
	public boolean canTeleport() {
		return false;
	}

	@Override
	public boolean canTrade() {
		return false;
	}

	@Override
	public void onTeleport(Player p) {
	}


}
