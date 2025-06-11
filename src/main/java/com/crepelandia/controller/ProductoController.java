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

@CrossOrigin(origins = "https://neon-entremet-b80536.netlify.app")
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
        public ResponseEntity<?> crearProducto(@RequestBody ProductoDTO dto) {
            try {
                Categoria categoria = categoriaRepository.findById(dto.getIdCategoria())
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

                Producto producto = new Producto();
                producto.setNombre(dto.getNombre());
                producto.setPrecio(dto.getPrecio());
                producto.setStock(dto.getStock());
                producto.setDescripcion(dto.getDescripcion());
                producto.setCategoria(categoria);

                return ResponseEntity.ok(productoRepository.save(producto));
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(500).body("Error interno: " + e.getMessage());
            }
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
