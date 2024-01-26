/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domination.mvc.model;

/**
 *
 * @author giann
 */
public class Domicilio {
    private int id;
    private String provincia;
    private String localidad;
    private String partido;
    private String calle;
    private String altura;
    private int idSucursal;

    public Domicilio(String provincia, String localidad, String partido, String calle, String altura,int idSucursal) {
        this(0,provincia, localidad, partido,calle,altura,idSucursal);
    }
    
    public Domicilio(int id, String provincia, String localidad, String partido, String calle, String altura, int idSucursal) {
        this.id = id;
        this.provincia = provincia;
        this.localidad = localidad;
        this.partido = partido;
        this.calle = calle;
        this.altura = altura;
        this.idSucursal = idSucursal;
    }

    public int getId() {
        return id;
    }

    public String getProvincia() {
        return provincia;
    }

    public String getLocalidad() {
        return localidad;
    }

    public String getPartido() {
        return partido;
    }

    public String getCalle() {
        return calle;
    }

    public String getAltura() {
        return altura;
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setId(int id) {
        this.id = id;
    }
    
}
