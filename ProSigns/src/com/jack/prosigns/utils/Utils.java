package com.jack.prosigns.utils;

import java.math.BigDecimal;

import org.bukkit.block.Sign;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;

public class Utils {
	public void sendMessage(CommandSender player, String str){
		str = str.replaceAll("&", "§");
		player.sendMessage(str);
	}
	
	public boolean hasEnough(Player player, int money){
		try {
			BigDecimal bigmoney = new BigDecimal(money);
			if(com.earth2me.essentials.api.Economy.hasEnough(player.getName(), bigmoney)){
				return true;
			} else {
				return false;
			}
		} catch (UserDoesNotExistException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean subtract(Player player, int money){
		try {
			BigDecimal bigmoney = new BigDecimal(money);
			if(com.earth2me.essentials.api.Economy.hasEnough(player.getName(), bigmoney)){
				try {
					com.earth2me.essentials.api.Economy.substract(player.getName(), bigmoney);
					sendMessage(player, "&c$" + money + " &awas subtracted from your account.");
					return true;
				} catch (NoLoanPermittedException e) {
					e.printStackTrace();
					return false;
				} catch (ArithmeticException e) {
					e.printStackTrace();
					return false;
				}
			} else {
				return false;
			}
		} catch (UserDoesNotExistException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean isProSign(Sign sign){
		if(sign.getLine(0).equals("[-]---------[-]") && sign.getLine(3).equals("[-]---------[-]")){
			if(sign.getLine(1) != "" && sign.getLine(2) != ""){
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}
