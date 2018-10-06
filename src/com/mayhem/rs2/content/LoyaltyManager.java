package com.mayhem.rs2.content;

import java.util.Timer;
import java.util.TimerTask;

import com.mayhem.rs2.entity.item.Item;
import com.mayhem.rs2.entity.player.Player;
import com.mayhem.rs2.entity.player.net.out.impl.SendMessage;
import com.mayhem.rs2.entity.player.net.out.impl.SendString;

/**
 * @author Andy || ReverendDread Mar 2, 2017
 */
public class LoyaltyManager {
	
	/**
	 * 
	 * Constructs a new object.
	 * @param player {@link Player} The player.
	 * @param delay The delay until execute fires.
	 */
	public LoyaltyManager(Player player) {
		this.player = player;
		appendTimer();
	}

	//The player
	private Player player;
	private Timer timer = new Timer();
	
	public void appendTimer() {
		timer.scheduleAtFixedRate(new TimerTask() {
			  @Override
			  public void run() {				  		
						player.setMoneyPouch(player.getMoneyPouch() + 100000);
						player.send(new SendString(player.getMoneyPouch() + "", 8135));
						player.send(new SendMessage("[LoyaltyManager]: You've been awarded for playing for 1 hour! 100k coins added to your pouch."));

			  }
		}, 60 * 60 * 1000, 60 * 60 * 1000); //Execute every 1 min.
	}
}
