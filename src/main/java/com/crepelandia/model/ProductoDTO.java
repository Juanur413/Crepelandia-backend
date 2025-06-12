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

    // Constructor por defecto
    public ProductoDTO() {}

    // Constructor con parámetros
    public ProductoDTO(String nombre, Integer precio, Integer stock, Integer idCategoria, String descripcion) {
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.idCategoria = idCategoria;
        this.descripcion = descripcion;
    }

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

    // Setters
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

    // Método toString para debugging
    @Override
    public String toString() {
        return "ProductoDTO{" +
                "nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", stock=" + stock +
                ", idCategoria=" + idCategoria +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }

    // Método para validar los datos básicos
    public boolean isValid() {
        return nombre != null && !nombre.trim().isEmpty() &&
               precio != null && precio >= 0 &&
               stock != null && stock >= 0 &&
               idCategoria != null && idCategoria > 0;
    }
}