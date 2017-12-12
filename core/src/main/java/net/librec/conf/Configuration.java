
package net.librec.conf;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.librec.util.StringUtil;

public class Configuration implements Iterable<Map.Entry<String, String>> {

	private static final Log LOG = LogFactory.getLog(Configuration.class);

	private static final ConcurrentMap<ClassLoader, Map<String, Class<?>>> CACHE_CLASSES = new ConcurrentHashMap<ClassLoader, Map<String, Class<?>>>();
	private Properties properties;
	private ClassLoader classLoader;
	{
		classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader == null) {
			classLoader = Configuration.class.getClassLoader();
		}
	}

	private boolean loadDefaults = true;

	private ArrayList<Resource> resources = new ArrayList<Resource>();

	private static final CopyOnWriteArrayList<String> defaultResources = new CopyOnWriteArrayList<String>();

	static {
		 ClassLoader cL = Thread.currentThread().getContextClassLoader();

		if (cL == null) {
			cL = Configuration.class.getClassLoader();
		}
		if (cL.getResource("librec-default.properties") != null) {
			addDefaultResource("librec-default.properties");
		}
		// if (cL.getResource("driver.classes.props") != null) {
		// LOG.warn("DEPRECATED: driver.classes.props found in the classpath.");
		// }
		if (cL.getResource("librec.properties") != null) {
			addDefaultResource("librec.properties");
		}
		// addDefaultResource("driver.classes.props");
	}

	public static class Resource {
		private final Object resource;
		private final String name;

		public Resource(Object resource) {
			this(resource, resource.toString());
		}

		public Resource(Object resource, String name) {
			this.resource = resource;
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public Object getResource() {
			return resource;
		}

		@Override
		public String toString() {
			return name;
		}
	}

	public static void addDefaultResource(String name) {
		synchronized (Configuration.class) {
			if (defaultResources.contains(name)) {
				return;
			}
			defaultResources.add(name);
		}
	}

	public Iterator<Entry<String, String>> iterator() {
		Map<String, String> result = new HashMap<String, String>();
		for (Map.Entry<Object, Object> item : getProps().entrySet()) {
			if (item.getKey() instanceof String && item.getValue() instanceof String) {
				result.put((String) item.getKey(), (String) item.getValue());
			}
		}
		return result.entrySet().iterator();
	}

	public synchronized void addResource(Resource resource) {
		loadProperty(getProps(), resource);
		resources.add(resource);
	}

	private void overlay(Properties to, Properties from) {
		for (Entry<Object, Object> entry : from.entrySet()) {
			to.put(entry.getKey(), entry.getValue());
		}
	}

	public void set(String name, String value) {
		getProps().setProperty(name, value);
	}

	public String get(String name) {
		return getProps().getProperty(name);
	}

	public void setStrings(String name, String... values) {
		set(name, StringUtil.arrayToString(values));
	}

	public String[] getStrings(String name) {
		String valueString = get(name);
		return StringUtil.getStrings(valueString);
	}

	public Float getFloat(String name, Float defaultValue) {
		String value = get(name);
		if (StringUtils.isNotBlank(value)) {
			return Float.valueOf(value);
		} else {
			return defaultValue;
		}
	}

	public void setFloat(String name, float value) {
		set(name, Float.toString(value));
	}

	public Float getFloat(String name) {
		String value = get(name);
		if (StringUtils.isNotBlank(value)) {
			return Float.valueOf(value);
		} else {
			return null;
		}
	}

	public void setDouble(String name, double value) {
		set(name, Double.toString(value));
	}

	public Double getDouble(String name, Double defaultValue) {
		String value = get(name);
		if (StringUtils.isNotBlank(value)) {
			return Double.valueOf(value);
		} else {
			return defaultValue;
		}
	}

	public Double getDouble(String name) {
		String value = get(name);
		if (StringUtils.isNotBlank(value)) {
			return Double.valueOf(value);
		} else {
			return null;
		}
	}

	public String get(String name, String defaultValue) {
		String value = get(name);
		return StringUtils.isNotBlank(value) ? value : defaultValue;
	}

	public void setLong(String name, long value) {
		set(name, Long.toString(value));
	}

	public Long getLong(String name, Long defaultValue) {
		String value = get(name);
		if (StringUtils.isNotBlank(value)) {
			return Long.valueOf(value);
		} else {
			return defaultValue;
		}
	}

	public Long getLong(String name) {
		String value = get(name);
		if (StringUtils.isNotBlank(value)) {
			return Long.valueOf(value);
		} else {
			return null;
		}
	}

	public void setInt(String name, int value) {
		set(name, Integer.toString(value));
	}

	public Integer getInt(String name, Integer defaultValue) {
		String value = get(name);
		if (StringUtils.isNotBlank(value)) {
			return Integer.valueOf(value);
		} else {
			return defaultValue;
		}
	}

	public Integer getInt(String name) {
		String value = get(name);
		if (StringUtils.isNotBlank(value)) {
			return Integer.valueOf(value);
		} else {
			return null;
		}
	}

	public void setInts(String name, int[] values) {
		set(name, StringUtil.arrayToString(values));
	}

	public int[] getInts(String name) {
		String[] strings = getTrimmedStrings(name);
		int[] ints = new int[strings.length];
		for (int i = 0; i < strings.length; i++) {
			ints[i] = Integer.parseInt(strings[i]);
		}
		return ints;
	}

	public String[] getTrimmedStrings(String name) {
		String valueString = get(name);
		return StringUtil.getTrimmedStrings(valueString);
	}

	public void setBoolean(String name, boolean value) {
		set(name, Boolean.toString(value));
	}

	public boolean getBoolean(String name) {
		String value = get(name);
		return StringUtils.isNotBlank(value) ? Boolean.valueOf(value) : false;
	}

	public boolean getBoolean(String name, boolean defaultValue) {
		String value = get(name);
		if (StringUtils.isNotBlank(value)) {
			return Boolean.valueOf(value);
		} else {
			return defaultValue;
		}
	}

	private synchronized Properties getProps() {
		if (properties == null) {
			properties = new Properties();
			loadResources(properties, resources);
		}
		return properties;
	}

	private void loadResources(Properties properties, ArrayList<Resource> resources) {
		if (loadDefaults) {
			for (String resource : defaultResources) {
				loadProperty(properties, new Resource(resource));
			}
		}
		for (Resource resource : resources) {
			loadProperty(properties, resource);
		}
	}

	private void loadProperty(Properties properties, Resource wrapper) {
		Object resource = wrapper.getResource();
		try {
			InputStream fis;
			if (resource instanceof URL) { // an URL resource
				fis = ((URL) resource).openStream();
				properties.load(fis);
			} else if (resource instanceof String) { // a CLASSPATH resource
				URL url = getResource((String) resource);
				if (url != null) {
					URLConnection connection = url.openConnection();
					if (connection instanceof JarURLConnection) {
						// Disable caching for JarURLConnection to avoid sharing
						// JarFile
						// with other users.
						connection.setUseCaches(false);
					}
					fis = connection.getInputStream();
					properties.load(fis);
				}
			} else if (resource instanceof Path) { // a file resource
				fis = new FileInputStream(new File(((Path) resource).toUri().getPath()));
				properties.load(fis);
			} else if (resource instanceof InputStream) {
				fis = (InputStream) resource;
				properties.load(fis);
			} else if (resource instanceof Properties) {
				overlay(properties, (Properties) resource);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public URL getResource(String name) {
		return classLoader.getResource(name);
	}

	public Class<?> getClassByName(String name) throws ClassNotFoundException {
		Map<String, Class<?>> map = CACHE_CLASSES.get(classLoader);
		if (map == null) {
			Map<String, Class<?>> newMap = new ConcurrentHashMap<String, Class<?>>();
			map = CACHE_CLASSES.putIfAbsent(classLoader, newMap);
			if (map == null) {
				map = newMap;
			}
		}
		Class<?> clazz = map.get(name);
		if (clazz == null) {
			clazz = Class.forName(name, true, classLoader);
			if (clazz != null) {
				map.put(name, clazz);
			}
		}

		return clazz;
	}

	public Class<?> getClassByName(String name, String defaultName) throws ClassNotFoundException {
		try {
			return getClassByName(name);
		} catch (ClassNotFoundException e) {
			LOG.error(e);
			return getClassByName(defaultName);
		}
	}
}
