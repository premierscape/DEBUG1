package com.mayhem.rs2.content.wogw;

import java.io.*;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import com.mayhem.Constants;
import com.mayhem.core.util.Utility;
import com.mayhem.rs2.entity.World;
import com.mayhem.rs2.entity.player.Player;
import com.mayhem.rs2.entity.player.net.out.impl.SendInterface;
import com.mayhem.rs2.entity.player.net.out.impl.SendMessage;
import com.mayhem.rs2.entity.player.net.out.impl.SendString;


public class Wogw {

	public static String[] lastDonators = new String[5];
	private static int slot = 0;
	
	private static final int LEAST_ACCEPTED_AMOUNT = 1000000; //1m 

	public static long EXPERIENCE_TIMER = 0, PC_POINTS_TIMER = 0, DOUBLE_DROPS_TIMER = 0;
	public static int MONEY_TOWARDS_EXPERIENCE = 0, MONEY_TOWARDS_PC_POINTS = 0, MONEY_TOWARDS_DOUBLE_DROPS = 0;

	@SuppressWarnings("resource")
	public static void init() {
        try {
            File f = new File("./data/wogw.txt");
            Scanner sc = new Scanner(f);
            int i = 0;
            while(sc.hasNextLine()){
            	i++;
                String line = sc.nextLine();
                String[] details = line.split("=");
                String amount = details[1];
                
                switch (i) {
                case 1:
                	MONEY_TOWARDS_EXPERIENCE = (int) Long.parseLong(amount);
                	break;
                case 2:
                	EXPERIENCE_TIMER = (int) Long.parseLong(amount);
                	break;
                case 3:
                	MONEY_TOWARDS_PC_POINTS = (int) Long.parseLong(amount);
                	break;
                case 4:
                	PC_POINTS_TIMER = (int) Long.parseLong(amount);
                	break;
                case 5:
                	MONEY_TOWARDS_DOUBLE_DROPS = (int) Long.parseLong(amount);
                	break;
                case 6:
                	DOUBLE_DROPS_TIMER = (int) Long.parseLong(amount);
                	break;
                }
            }

        } catch (FileNotFoundException e) {         
            e.printStackTrace();
        }
	}
	
	public static void save() {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter("./data/wogw.txt"));
			out.write("experience-amount=" + MONEY_TOWARDS_EXPERIENCE);
			out.newLine();
			out.write("experience-timer=" + EXPERIENCE_TIMER);
			out.newLine();
			out.write("pc-amount=" + MONEY_TOWARDS_PC_POINTS);
			out.newLine();
			out.write("pc-timer=" + PC_POINTS_TIMER);
			out.newLine();
			out.write("drops-amount=" + MONEY_TOWARDS_DOUBLE_DROPS);
			out.newLine();
			out.write("drops-timer=" + DOUBLE_DROPS_TIMER);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void donate(Player player, int amount) {
		if (amount < LEAST_ACCEPTED_AMOUNT) {
			player.send(new SendMessage("You must donate at least 1 million coins."));
			return;
		}
		if (!player.getInventory().hasItemAmount(995, amount)) {
			player.send(new SendMessage("You do not have " + Utility.getValueWithoutRepresentation(amount) + "."));
			return;
		}
		player.getInventory().remove(995, amount);
		player.send(new SendInterface(38020));
		
		/**
		 * Updating latest donators
		 */
		String towards =Objects.equals(player.wogwOption, "pc") ? "+5 bonus PC Points!" : Objects.equals(player.wogwOption, "experience") ? "double experience!" : Objects.equals(player.wogwOption, "drops") ? "double drops!" : "";
		player.send(new SendMessage("You successfully donated " + Utility.format((int) player.wogwDonationAmount) + "gp to the well of goodwill towards"));
		player.send(new SendMessage(towards));
		Wogw.lastDonators[Wogw.slot] = "" + Utility.formatPlayerName(player.getUsername()) + " donated " + Utility.getValueWithoutRepresentation(player.wogwDonationAmount) + " towards " + towards;
		player.send(new SendString(Wogw.lastDonators[Wogw.slot], 38033 + Wogw.slot));
		
		/**
		 * Announcing donations over 10m
		 */
		if (player.wogwDonationAmount >= 10_000_000) {
			World.sendGlobalMessage("[@blu@WOGW@bla@]@blu@" + Utility.formatPlayerName(player.getUsername()) + "@bla@ donated @blu@" + Utility.getValueWithoutRepresentation(player.wogwDonationAmount) + "@bla@ to the well of goodwill!");
		}
		/**
		 * Setting the amounts and enabling bonus if the amount reaches above required.
		 */
		switch (player.wogwOption) {
		case "experience":
			handleMoneyToExperience(amount);
			break;
			
		case "pc":
			if (MONEY_TOWARDS_PC_POINTS + amount >= 50000000 && PC_POINTS_TIMER == 0) {
				MONEY_TOWARDS_PC_POINTS = MONEY_TOWARDS_PC_POINTS + amount - 50000000;
				World.sendGlobalMessage("[@blu@WOGW@bla@] <col=6666FF>The Well of Goodwill has been filled!");
				World.sendGlobalMessage("[@blu@WOGW@bla@] <col=6666FF>It is now granting everyone 1 hour of +5 bonus pc points.");
				PC_POINTS_TIMER += TimeUnit.HOURS.toMillis(1) / 600;
				Constants.BONUS_PC_WOGW = true;
			} else {
				MONEY_TOWARDS_PC_POINTS += amount;
			}
			break;
			
		case "drops":
			if (MONEY_TOWARDS_DOUBLE_DROPS + amount >= 100000000 && DOUBLE_DROPS_TIMER == 0) {
				MONEY_TOWARDS_DOUBLE_DROPS = MONEY_TOWARDS_DOUBLE_DROPS + amount - 100000000;
				World.sendGlobalMessage("[@blu@WOGW@bla@] <col=6666FF>The Well of Goodwill has been filled!");
				World.sendGlobalMessage("[@blu@WOGW@bla@] <col=6666FF>It is now granting everyone 1 hour of double drop rate.");
				DOUBLE_DROPS_TIMER += TimeUnit.HOURS.toMillis(1) / 600;
				Constants.DOUBLE_DROPS = true;
			} else {
				MONEY_TOWARDS_DOUBLE_DROPS += amount;
			}
			break;
		}
		player.send(new SendString("" + Utility.getValueWithoutRepresentation(Wogw.MONEY_TOWARDS_EXPERIENCE) + "/50M", 38012));
		player.send(new SendString("" + Utility.getValueWithoutRepresentation(Wogw.MONEY_TOWARDS_PC_POINTS) + "/50M", 38013));
		player.send(new SendString("" + Utility.getValueWithoutRepresentation(Wogw.MONEY_TOWARDS_DOUBLE_DROPS) + "/100M", 38014));
		Wogw.slot++;
		if (Wogw.slot == 5) {
			Wogw.slot = 0;
		}
		player.wogwOption = "";
		player.wogwDonationAmount = 0;
	}
	

	private static void handleMoneyToExperience(int amount) {

		if (MONEY_TOWARDS_EXPERIENCE + amount >= 50000000 && EXPERIENCE_TIMER == 0) {
			MONEY_TOWARDS_EXPERIENCE = MONEY_TOWARDS_EXPERIENCE + amount - 50000000;
			World.sendGlobalMessage("[@blu@WOGW@bla@] <col=6666FF>The Well of Goodwill has been filled!");
			World.sendGlobalMessage("[@blu@WOGW@bla@] <col=6666FF>It is now granting everyone 1 hour of double experience.");
			EXPERIENCE_TIMER += TimeUnit.HOURS.toMillis(1) / 600;
			Constants.BONUS_XP_WOGW = true;
		} else {
			MONEY_TOWARDS_EXPERIENCE += amount;
		}
	}

	public static void appendBonus() {
			if (MONEY_TOWARDS_EXPERIENCE >= 50000000) {
				World.sendGlobalMessage("[@blu@WOGW@bla@] <col=6666FF>The Well of Goodwill was filled above the top!");
				World.sendGlobalMessage("[@blu@WOGW@bla@] <col=6666FF>It is now granting everyone 1 more hour of double experience.");
				EXPERIENCE_TIMER += TimeUnit.HOURS.toMillis(1) / 600;
				MONEY_TOWARDS_EXPERIENCE -= 50000000;
				World.nonNullStream().forEach(player -> {
					player.send(new SendString("" + Utility.getValueWithoutRepresentation(Wogw.MONEY_TOWARDS_EXPERIENCE) + "/50M", 38012));
					player.send(new SendString("" + Utility.getValueWithoutRepresentation(Wogw.MONEY_TOWARDS_PC_POINTS) + "/50M", 38013));
					player.send(new SendString("" + Utility.getValueWithoutRepresentation(Wogw.MONEY_TOWARDS_DOUBLE_DROPS) + "/100M", 38014));
				});
				Constants.BONUS_XP_WOGW = true;
				return;
			}
			if (MONEY_TOWARDS_PC_POINTS >= 50000000) {
				World.sendGlobalMessage("[@blu@WOGW@bla@] <col=6666FF>The Well of Goodwill was filled above the top!");
				World.sendGlobalMessage("[@blu@WOGW@bla@] <col=6666FF>It is now granting everyone 1 more hour of +5 bonus pc points.");
				PC_POINTS_TIMER += TimeUnit.HOURS.toMillis(1) / 600;
				MONEY_TOWARDS_PC_POINTS -= 50000000;
				World.nonNullStream().forEach(player -> {
					player.send(new SendString("" + Utility.getValueWithoutRepresentation(Wogw.MONEY_TOWARDS_EXPERIENCE) + "/50M", 38012));
					player.send(new SendString("" + Utility.getValueWithoutRepresentation(Wogw.MONEY_TOWARDS_PC_POINTS) + "/50M", 38013));
					player.send(new SendString("" + Utility.getValueWithoutRepresentation(Wogw.MONEY_TOWARDS_DOUBLE_DROPS) + "/100M", 38014));
				});
				Constants.BONUS_PC_WOGW = true;
				return;
			}
			if (MONEY_TOWARDS_DOUBLE_DROPS >= 100000000) {
				World.sendGlobalMessage("[@blu@WOGW@bla@] <col=6666FF>The Well of Goodwill was filled above the top!");
				World.sendGlobalMessage("[@blu@WOGW@bla@] <col=6666FF>It is now granting everyone 1 more hour of double drop rate");
				DOUBLE_DROPS_TIMER += TimeUnit.HOURS.toMillis(1) / 600;
				MONEY_TOWARDS_DOUBLE_DROPS -= 100000000;
				World.nonNullStream().forEach(player -> {
					player.send(new SendString("" + Utility.getValueWithoutRepresentation(Wogw.MONEY_TOWARDS_EXPERIENCE) + "/50M", 38012));
					player.send(new SendString("" + Utility.getValueWithoutRepresentation(Wogw.MONEY_TOWARDS_PC_POINTS) + "/50M", 38013));
					player.send(new SendString("" + Utility.getValueWithoutRepresentation(Wogw.MONEY_TOWARDS_DOUBLE_DROPS) + "/100M", 38014));
				});
				Constants.DOUBLE_DROPS = true;
			}
	}

}
