package com.jack.prisoncells.commands;

import java.math.BigDecimal;
import java.math.MathContext;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;
import com.jack.prisoncells.PrisonCells;
import com.jack.prisoncells.utils.MetaUtils;
import com.jack.prisoncells.utils.Utils;

public class PrisonCellsCommand implements CommandExecutor {

	private PrisonCells plugin;
	private MetaUtils MU;
	private Utils U;
	
	public PrisonCellsCommand(PrisonCells p) {
		plugin = p;
		MU = plugin.getMU();
		U = plugin.getUtils();
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("prisoncells")) {
			if(sender instanceof Player){
				Player player = (Player) sender;
				if(args.length == 0){
					showHelp(player);
				} else {
					if(args.length == 2){
						if(args[0].equals("create") && player.hasPermission("prisoncells.create")){
							if(MU.hasMetaData(player, "pos1") && MU.hasMetaData(player, "pos2")){
								if(args[1].equals("prison")){
									U.createPrison(player);
								} else if(args[1].equals("cell")){
									U.createCell(player);
								} else {
									showHelp(player);
								}
							} else {
								sendMessage(player, "&cYou need to select two locations with a stick");
							}
						} else {
							showHelp(player);
						}
						
						if(args[0].equals("buy")){
							int tmp;
							try {
								tmp = Integer.parseInt(args[1]);
							} catch (NumberFormatException e) {
								tmp = 0;
							}
							if(tmp == 0 || tmp > 50){
								sendMessage(player, "&cThe days you want to buy has to be a number and between 0 51");
							} else {
								if(U.canBuy(player)){
									try {
										double cost = tmp * 25000.0;
										BigDecimal b = new BigDecimal(cost, MathContext.DECIMAL64);
										if(com.earth2me.essentials.api.Economy.hasEnough(player.getName(), b)){
											try {
												com.earth2me.essentials.api.Economy.substract(player.getName(), b);
											} catch (NoLoanPermittedException e) {
												e.printStackTrace();
											} catch (ArithmeticException e) {
												e.printStackTrace();
											}
											sendMessage(player, "&aYou have successfully brought this cell for " + tmp + " days at $" + cost);
											U.buyCell(player, tmp);
										} else {
											sendMessage(player, "&cYou do not have enough money to buy a cell for " + tmp + " days, that would cost you $" + cost);
										}
									} catch (UserDoesNotExistException e) {
										e.printStackTrace();
									}
								}
							}
						}
					} else {
						if(args.length == 1){
							if(args[0].equals("newday") && player.hasPermission("prisoncells.newday")){
								U.newDay();
							} else {
								if(args[0].equals("info") && player.hasPermission("prisoncells.info")){
									U.getCellInfo(player);
								} else {
									showHelp(player);
								}
							}
						} else {
							showHelp(player);
						}
					}
				}
			} else {
				sendMessage(sender, "&cYou must be a player to perform this command");
			}
		}
		return true;
	}

	private void showHelp(Player player) {
		sendMessage(player, "&9-[&6PrisonCells&9]- &6Help");
		sendMessage(player, "&9- &6Creating Prison &9/prisoncells create prison");
		sendMessage(player, "&9- &6Creating Cell &9/prisoncells create cell");
		sendMessage(player, "&9- &6Get Cell Info &9/prisoncells info");
		sendMessage(player, "&9- &6Buy Cell &9/prisoncells buy <days>");
		sendMessage(player, "&9- &6Start New Prison Day &9/prisoncells newday");
	}

	private void sendMessage(CommandSender sender, String string) {
		string = string.replaceAll("&", "§");
		sender.sendMessage(string);
	}
}
