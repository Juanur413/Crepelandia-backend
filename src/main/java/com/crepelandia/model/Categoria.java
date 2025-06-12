package com.crepelandia.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Integer idCategoria;

    @NotBlank(message = "El nombre de la categoría es obligatorio")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    @Column(name = "nombre", nullable = false, length = 50, unique = true)
    private String nombre;

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"categoria", "hibernateLazyInitializer", "handler"})
    private List<Producto> productos = new ArrayList<>();

    // Constructor por defecto
    public Categoria() {}

    // Constructor con nombre
    public Categoria(String nombre) {
        this.nombre = nombre;
    }

    // Getters y setters
    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    // Métodos útiles
    public void addProducto(Producto producto) {
        productos.add(producto);
        producto.setCategoria(this);
    }

    public void removeProducto(Producto producto) {
        productos.remove(producto);
        producto.setCategoria(null);
    }

    public int getCantidadProductos() {
        return productos != null ? productos.size() : 0;
    }

    // Método para obtener el stock total de la categoría
    public int getStockTotal() {
        if (productos == null) return 0;
        return productos.stream()
                .mapToInt(p -> p.getStock() != null ? p.getStock() : 0)
                .sum();
    }

    // Método para verificar si tiene productos
    public boolean tieneProductos() {
        return productos != null && !productos.isEmpty();
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "idCategoria=" + idCategoria +
                ", nombre='" + nombre + '\'' +
                ", cantidadProductos=" + getCantidadProductos() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Categoria)) return false;
        Categoria categoria = (Categoria) o;
        return idCategoria != null && idCategoria.equals(categoria.idCategoria);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}