package com.crepelandia.controller;

import com.crepelandia.model.Categoria;
import com.crepelandia.model.Producto;
import com.crepelandia.model.ProductoDTO;
import com.crepelandia.repository.CategoriaRepository;
import com.crepelandia.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;


    // Obtener todos los productos
    @GetMapping
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    // Obtener un producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable Integer id) {
        Optional<Producto> producto = productoRepository.findById(id);
        return producto.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear nuevo producto
@PostMapping
public Producto crearProducto(@RequestBody ProductoDTO dto) {
    Categoria categoria = categoriaRepository.findById(dto.idCategoria)
        .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

    Producto producto = new Producto();
    producto.setNombre(dto.nombre);
    producto.setPrecio(dto.precio);
    producto.setStock(dto.stock);
    producto.setCategoria(categoria);
    producto.setDescripcion(dto.descripcion);

    return productoRepository.save(producto);
}




    // Actualizar producto existente
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Integer id, @RequestBody Producto productoDetalles) {
        Optional<Producto> optionalProducto = productoRepository.findById(id);
        if (optionalProducto.isPresent()) {
            Producto producto = optionalProducto.get();
            producto.setNombre(productoDetalles.getNombre());
            producto.setPrecio(productoDetalles.getPrecio());
            producto.setStock(productoDetalles.getStock());
            producto.setCategoria(productoDetalles.getCategoria());
            return ResponseEntity.ok(productoRepository.save(producto));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar producto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Integer id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
