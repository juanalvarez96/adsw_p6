package es.upm.dit.adsw.ej6;

import java.util.Random;
/**
 * La clase donde decimos como se comportarán las hebras
 * @author jmanas
 *
 */
public class Agente implements Runnable {
	private TsCache cache;
	private int id;

	public Agente(int id, TsCache cache2) {
		this.id = id;
		this.cache = cache2;
	}

	@Override
	public void run() {
		Random random = new Random();
		while (true) {
			try {
				String key = String.valueOf(random.nextInt(1000));
				if (cache.get(key) == null) {
					String val = "{" + key + "}";
					Nap.random(10, 20);
					cache.put(key, val);
				}
				Nap.sleep(10);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
