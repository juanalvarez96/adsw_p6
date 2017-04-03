package es.upm.dit.adsw.ej6;

/**
 * Laxa
 * 
 * @author juanalvarez
 *
 */
public class RW_Monitor {

	private int nWriters;
	private int nReaders;
	private boolean blockReaders;
	private boolean blockWriters;

	public synchronized void openReading() throws InterruptedException {
		if (blockWriters)
			System.out.println("La hebra " + Thread.currentThread().getName() + " ha sido bloqueada");
		while (nWriters != 0 || blockWriters)
			wait();
		System.out.println("La hebra " + Thread.currentThread().getName() + " ha sido desbloqueada, ahora va a leer.");
		nReaders++;
		blockReaders = true;
	}

	public synchronized void openWriting() throws InterruptedException {
		if (nReaders != 0 || blockReaders || blockWriters)
			System.out.println("La hebra " + Thread.currentThread().getName() + " ha sido bloqueada");
		while (nReaders != 0 || blockReaders || blockWriters || nWriters != 0)
			wait();
		System.out.println(
				"La hebra " + Thread.currentThread().getName() + " ha sido desbloqueada, ahora va a escribir.");
		nWriters++;
		blockWriters = true;
	}

	public synchronized void closeReading() {
		System.out.println("La hebra " + Thread.currentThread().getName() + " termina de leer.");
		nReaders--;
		if (nReaders == 0) {
			notifyAll();
			blockReaders = false;
		}

	}

	public synchronized void closeWriting() {
		System.out.println("La hebra " + Thread.currentThread().getName() + " terminade escribir.");
		nWriters--;
		if (nWriters == 0) {
			blockWriters = false;
			notifyAll();
		}
	}

	public synchronized int getWriters() {
		return nWriters;
	}

	public synchronized int getReaders() {
		return nReaders;
	}

}
