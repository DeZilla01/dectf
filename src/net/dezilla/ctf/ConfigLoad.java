package net.dezilla.ctf;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.Properties;

import org.bukkit.Bukkit;

public class ConfigLoad {
	public static String grabStr(String confFile, String confName) {
		try (FileReader reader = new FileReader(confFile)) {
			Properties config = new Properties();
			config.load(reader);
			
			String conf = config.getProperty(confName);
			return conf;
		} catch (Exception e) {
			;
			e.printStackTrace();
		}
		return "";
	}
	public static double[] grabCoord(String confFile, String confName) {
		double[] coord = new double[4];
		try (FileReader reader = new FileReader(confFile)) {
			Properties config = new Properties();
			config.load(reader);
			String conf = config.getProperty(confName);
			String[] parts = conf.split(",");
			coord[0] = Double.parseDouble(parts[0]);
			coord[1] = Double.parseDouble(parts[1]);
			coord[2] = Double.parseDouble(parts[2]);
			coord[3] = Double.parseDouble(parts[3]);
			return coord;
		} catch (Exception e) {
			;
			e.printStackTrace();
		}
		return coord;
	}
	public static int grabInt(String confFile, String confName) {
		try (FileReader reader = new FileReader(confFile)) {
			Properties config = new Properties();
			config.load(reader);
			
			String conf = config.getProperty(confName);
			return Integer.parseInt(conf);
		} catch (Exception e) {
			;
			e.printStackTrace();
		}
		return 0;
	}
	public static void writeStr(String confFile, String confName, String str){
		try (FileInputStream reader = new FileInputStream(confFile)) {
			Properties config = new Properties();
			config.load(reader);
			
			if(config.contains(confName)){
				config.remove(confName);
			}
			config.setProperty(confName, str);
			config.store(new FileOutputStream(confFile), null);
			reader.close();
		}
		catch(Exception e){
			Bukkit.getServer().broadcastMessage("Error "+e.getMessage());
			;e.printStackTrace();
		}
	}
}
