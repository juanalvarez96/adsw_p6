package es.upm.dit.adsw.ej6;

/**
 * Mapas clave -> valor.
 * @author jose a. manas
 * @version 6.12.2015
 */
public interface Diccionario {

    /**
     * Mete un valor nuevo.
     * Si ya existe uno con misma clave, reemplaza el valor.
     *
     * @param clave
     * @param valor
     * @throws IllegalArgumentException Si clave es null.
     * @throws IllegalArgumentException Si clave es la cadena vacia.
     * @throws RuntimeException Si no cabe la clave.
     */
    void put(String clave, String valor);

    /**
     * Saca el valor asociado a la clave.
     *
     * @param clave
     * @return null si no estÃ¡ la clave.
     * @throws IllegalArgumentException Si clave es null.
     * @throws IllegalArgumentException Si clave es la cadena vacia.
     */
    String get(String clave);

    /**
     * Elimina el objeto asociado a la clave, si estÃ¡.
     *
     * @param clave
     * @return devuelve el valor asociado si estaba la clave; devuelve null si no estÃ¡ la clave
     * @throws IllegalArgumentException Si clave es null.
     * @throws IllegalArgumentException Si clave es la cadena vacia.
     */
    String remove(String clave);

    /**
     * @return nÃºmero de elementos almacenados.
     */
    int size();

    /**
     * Elimina todas las claves.
     */
    void clear();
}