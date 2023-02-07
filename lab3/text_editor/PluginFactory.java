package hr.fer.ooup.lab3;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;


public class PluginFactory {
	private static final String packagePath = "hr/fer/ooup/lab3/plugins";
	private static final String packageName = "hr.fer.ooup.lab3.plugins";
	private static final URLClassLoader classLoader = getLoader("plugins/");
	
	private static URLClassLoader getLoader(String pluginPath) {
		URL url = null;
		try {
			url = Path.of(pluginPath).toUri().toURL();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		URLClassLoader classLoader = new URLClassLoader(new URL[] {url}, PluginFactory.class.getClassLoader());
		return classLoader;
	}
	
	public static List<Plugin> getPlugins(String pluginPath){
		File f  = new File(pluginPath + packagePath);
		String[] files = f.list();
		List<Plugin> allPlugins = new LinkedList<>();
		for(String file: files) {
			if(file.endsWith(".class")) {
				try {
					String className = file.split("\\.")[0];
					Class<?> pluginClass = classLoader.loadClass(packageName + "." + className);
					Constructor<?> pluginConstructor = pluginClass.getConstructor();
					Plugin plugin = (Plugin)pluginConstructor.newInstance();
					allPlugins.add(plugin);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}
		return allPlugins;
	}
	
}
