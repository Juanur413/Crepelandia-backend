package com.crepelandia.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductoDTO {

    @JsonProperty("nombre")
    public String nombre;

    @JsonProperty("precio")
    public Integer precio;

    @JsonProperty("stock")
    public Integer stock;

    @JsonProperty("idCategoria")
    public Integer idCategoria;

    @JsonProperty("descripcion")
    public String descripcion;
}
