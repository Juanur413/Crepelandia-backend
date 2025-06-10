package com.crepelandia.model;

import javax.persistence.*;

@Entity
@Table(name = "detalle_pedido")
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "pedidos_id_pedido")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "productos_id_productos")
    private Producto producto;

    private Integer cantidad;

    private Float subtotal;

    // Getters y setters
    public Integer getId() {
    return id;
}

public void setId(Integer id) {
    this.id = id;
}

public Pedido getPedido() {
    return pedido;
}

public void setPedido(Pedido pedido) {
    this.pedido = pedido;
}

public Producto getProducto() {
    return producto;
}

public void setProducto(Producto producto) {
    this.producto = producto;
}

public Integer getCantidad() {
    return cantidad;
}

public void setCantidad(Integer cantidad) {
    this.cantidad = cantidad;
}

public Float getSubtotal() {
    return subtotal;
}

public void setSubtotal(Float subtotal) {
    this.subtotal = subtotal;
}

}
