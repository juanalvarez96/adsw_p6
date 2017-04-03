package es.upm.dit.adsw.ej6;

/**
 * Cache donde se va a guardar la información. El diccionario será de tipo
 * hashlistas.
 * 
 * @author juanalvarez
 * @author jmanas
 * @version 22.4.2016
 */
public class TsCache implements Diccionario {

	private TsList[] cache;
	private int nDatos;

	public TsCache(int nSlots) {
		cache = new TsList[nSlots];
		nDatos = 0;
		for (int i = 0; i < nSlots; i++) {
			cache[i] = new TsList();
		}

	}

	@Override
	public void put(String clave, String valor) {
		if (clave == null || valor == null || clave.length() == 0)
			throw new IllegalArgumentException();
		int pos = Math.abs(clave.hashCode() % cache.length);
		boolean ind = false;
		try {
			ind = cache[pos].put(clave, valor);
			if (ind) {
				nDatos++;
				return;
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public String get(String clave) {
		if (clave == null || clave.length() == 0)
			throw new IllegalArgumentException();
		int pos = Math.abs(clave.hashCode() % cache.length);
		String s = null;
		try {
			s = cache[pos].get(clave);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}

	@Override
	public String remove(String clave) {
		if (clave == null || clave.length() == 0)
			throw new IllegalArgumentException();
		int pos = Math.abs(clave.hashCode() % cache.length);

		String s = null;
		try {
			s = cache[pos].remove(clave);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (s != null)
			nDatos--;
		return s;
	}

	@Override
	public int size() {
		// El método no funciona con múltiples hebras, ya que no se garantiza la
		// exclusión mutua.
		return nDatos;
	}

	@Override
	public void clear() {
		for (int i = 0; i < cache.length; i++) {
			try {
				cache[i].clear();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		nDatos = 0;

	}

}
