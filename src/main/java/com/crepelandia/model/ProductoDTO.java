package com.crepelandia.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductoDTO {

    @JsonProperty("nombre")
    private String nombre;

    @JsonProperty("precio")
    private Integer precio;

    @JsonProperty("stock")
    private Integer stock;

    @JsonProperty("idCategoria")
    private Integer idCategoria;

    @JsonProperty("descripcion")
    private String descripcion;

    // Getters
    public String getNombre() {
        return nombre;
    }

    public Integer getPrecio() {
        return precio;
    }

    public Integer getStock() {
        return stock;
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    // Setters (opcional, solo si piensas modificar los datos desde fuera)
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
