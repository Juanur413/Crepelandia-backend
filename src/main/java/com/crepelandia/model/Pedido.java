package com.crepelandia.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "clientes_id_cliente")
    private Cliente cliente;

    private LocalDateTime fecha;

    private String estado;

    @OneToMany(mappedBy = "pedido")
    private List<DetallePedido> detalles;

    // Getters y setters

    public Integer getId() {
    return id;
}

public void setId(Integer id) {
    this.id = id;
}

public Cliente getCliente() {
    return cliente;
}

public void setCliente(Cliente cliente) {
    this.cliente = cliente;
}

public LocalDateTime getFecha() {
    return fecha;
}

public void setFecha(LocalDateTime fecha) {
    this.fecha = fecha;
}

public String getEstado() {
    return estado;
}

public void setEstado(String estado) {
    this.estado = estado;
}

public List<DetallePedido> getDetalles() {
    return detalles;
}

public void setDetalles(List<DetallePedido> detalles) {
    this.detalles = detalles;
}

}
