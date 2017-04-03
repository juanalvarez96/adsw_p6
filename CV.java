package es.upm.dit.adsw.ej6;

/**
 * Pareja clave-valor.
 *
 * @author jose a. manas
 * @version 16-Mar-16.
 */
public class CV {
    private final String clave;
    private String valor;

    /**
     * Constructor.
     * @param clave clave para indexar.
     * @param valor valor asociado a la clave.
     */
    public CV(String clave, String valor) {
        this.clave = clave;
        this.valor = valor;
    }

    /**
     * Getter.
     */
    public String getClave() {
        return clave;
    }

    /**
     * Getter.
     */
    public String getValor() {
        return valor;
    }

    /**
     * Setter.
     */
    public void setValor(String valor) {
        this.valor = valor;
    }
}