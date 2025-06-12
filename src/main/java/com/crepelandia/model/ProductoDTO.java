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
    private Integer idCategoria;package com.crepelandia.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.*;

public class ProductoDTO {

    @JsonProperty("nombre")
    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    @JsonProperty("precio")
    @NotNull(message = "El precio es obligatorio")
    @Min(value = 0, message = "El precio debe ser mayor o igual a 0")
    @Max(value = 999999, message = "El precio no puede ser mayor a 999,999")
    private Integer precio;

    @JsonProperty("stock")
    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock debe ser mayor o igual a 0")
    @Max(value = 9999, message = "El stock no puede ser mayor a 9,999")
    private Integer stock;

    @JsonProperty("idCategoria")
    @NotNull(message = "La categoría es obligatoria")
    @Positive(message = "El ID de categoría debe ser un número positivo")
    private Integer idCategoria;

    @JsonProperty("descripcion")
    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
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
