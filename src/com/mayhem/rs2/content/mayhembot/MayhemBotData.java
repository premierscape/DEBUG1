package com.mayhem.rs2.content.mayhembot;

/**
 * Holds the MayhemBot Data
 * @author Daniel
 *
 */
public enum MayhemBotData {	
	DATA_1("Who is the owner of PremierScape?", "Brandon"),
	DATA_2("The more you take the more you leave behind. what am I?", "footsteps", "foot steps"),
	DATA_3("What is the maximum combat level?", "126"),
	DATA_4("How many Hitpoints do Sharks heal?", "20"),
	DATA_5("How many charges can an amulet of glory hold?", "6"),
	DATA_6("What level is Zulrah?", "725"),
	DATA_7("What metal is made from 1 iron ore and 2 coal?", "steel"),
	DATA_8("How many bars do you need to smith a Platebody of any kind?", "5"),
	DATA_9("How many Coal ores do you need to smelt a Mithril bar?", "4"),
	DATA_10("What Ranging Level do you need to be able to wear a Robin Hat?", "40"),
	DATA_11("What level are Jogres?", "53"),
	DATA_12("What is the fishing pet called?", "heron"),
	DATA_13("What am I?", "a question"),
	DATA_14("I have four eyes but I cannot see. What am I?", "Mississippi"),
	DATA_15("I am a 7 letter word containing thousands of letters. What am I?", "mailbox"),
	DATA_16("What farming level do you need to be to farm watermelon?", "47"),
	DATA_17("Which NPC sells skillcapes?", "Wise Old Man", "wise old man", "Wise old man"),
	DATA_18("How many words are in this question?", "7"),
	DATA_19("How many combat styles does Zulrah use?", "3"),
	DATA_20("Which combat style does General Graardor use most often?", "melee"),
	DATA_21("What logs can be cut at level 60 Woodcutting?", "yew", "yew logs"),
	DATA_22("What item is used to make bow strings?", "flax"),
	DATA_23("I am black and white and red all over. What am I?", "newspaper"),
	DATA_24("Forward I am heavy, but backwards I am not. What am I?", "ton"),
	DATA_25("I am tall when young, and short when old. What am I?", "a candle", "candle"),
	DATA_26("What item can be rewarded from the Fight caves activity?", "fire cape"),
	DATA_27("What Magic level is needed to cast Ice Barrage", "94"),
	DATA_28("People need me yet they give me away everyday. What am I?", "money"),
	DATA_29("Which herb is required to make agility potions?", "toadflax"),
	DATA_30("You can catch me but you cannot throw me. What am I?", "a cold"),
	DATA_31("What Cooking Level do you need to be able to cook a Monkfish?", "62"),
	DATA_32("What level are ducks?", "1")
	  
	  
	  ;
	
	private final String question;
	private final String[] answers;
	
	private MayhemBotData(String question, String... answers) {
		this.question = question;
		this.answers = answers;
	}
	
	public String getQuestion() {
		return question;
	}
	
	public String[] getAnswers() {
		return answers;
	}
	
}
