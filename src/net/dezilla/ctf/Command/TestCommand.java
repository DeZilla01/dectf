package net.dezilla.ctf.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TestCommand extends Command implements CommandExecutor{
	
	public TestCommand() {
		super("testy");
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean execute(CommandSender sender, String alias, String[] args) {
		sender.sendMessage("Test good");
		return true;
	}

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		return false;
	}

}
