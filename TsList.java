package es.upm.dit.adsw.ej6;

import java.util.ArrayList;

/**
 * Lista donde se almacenan los datos CV.
 * 
 * @author juanalvarez
 * @author jmanas
 * @version 22.4.2016
 */
public class TsList {

	private ArrayList<CV> list;
	private LogViewer viewer = LogViewer.getInstance();
	private RW_Monitor monitor = new RW_Monitor();

	public TsList() {
		list = new ArrayList<CV>();
	}

	/**
	 * Asocia una clave y un valor. Si la clave es nueva, se crea una nueva
	 * entrada. Si la clave ya existia, se cambia el valor asociado.
	 * 
	 * @param clave
	 * @param valor
	 *            a asociar a la clave
	 * @return TRUE si se crea una asociación nueva.
	 * @throws InterruptedException
	 */
	public boolean put(String clave, String valor) throws InterruptedException {
		if (clave == null || valor == null || clave.length() == 0)
			throw new IllegalArgumentException();
		monitor.openWriting();
		My.assertEquals(1, monitor.getWriters());
		My.assertEquals(0, monitor.getReaders());
		viewer.dump(this, monitor.getReaders(), monitor.getWriters());
		if (list.size() == 0) {
			list.add(new CV(clave, valor));
			monitor.closeWriting();
			return true;
		}
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getClave().equals(clave)) {
				list.get(i).setValor(valor);
				monitor.closeWriting();
				return false;
			}
		}
		list.add(new CV(clave, valor));
		monitor.closeWriting();
		return true;

	}

	/**
	 * Recupera el valor asociado a una clave. Si la clave no existe, devuelve
	 * NULL:. Si la clave existe, se devuelve el valor asociado.
	 * 
	 * @param clave
	 * @return valor asociado
	 * @throws InterruptedException
	 */
	public String get(String clave) throws InterruptedException {
		if (clave == null || clave.length() == 0)
			throw new IllegalArgumentException();
		monitor.openReading();
		My.assertEquals(0, monitor.getWriters());
		viewer.dump(this, monitor.getReaders(), monitor.getWriters());
		if (list.size() == 0) {
			monitor.closeReading();
			return null;
		}
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getClave().equals(clave)) {
				String s = list.get(i).getValor();
				monitor.closeReading();
				return s;
			}
		}
		monitor.closeReading();
		return null;
	}

	/**
	 * Elimina la asociación de un valor a una clave. Si la clave no existe, no
	 * hace nada y devuelve NULL:. Si la clave existe, la elimina y se devuelve
	 * el valor asociado.
	 * 
	 * @param clave
	 * @return el valor asociado
	 * @throws InterruptedException
	 */
	public String remove(String clave) throws InterruptedException {
		if (clave == null || clave.length() == 0)
			throw new IllegalArgumentException();
		monitor.openWriting();
		My.assertEquals(1, monitor.getWriters());
		My.assertEquals(0, monitor.getReaders());
		viewer.dump(this, monitor.getReaders(), monitor.getWriters());
		if (list.size() == 0) {
			monitor.closeWriting();
			return null;
		}
		CV obj;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getClave().equals(clave)) {
				obj = list.get(i);
				list.remove(i);
				monitor.closeWriting();
				return obj.getValor();
			}
		}
		monitor.closeWriting();
		return null;
	}

	/**
	 * Borra todas las asociaciones. Pone el diccionario a 0.
	 * 
	 * @throws InterruptedException
	 */
	public void clear() throws InterruptedException {
		monitor.openWriting();
		My.assertEquals(1, monitor.getWriters());
		My.assertEquals(0, monitor.getReaders());
		viewer.dump(this, monitor.getReaders(), monitor.getWriters());
		list.clear();
		monitor.closeWriting();
	}
}
