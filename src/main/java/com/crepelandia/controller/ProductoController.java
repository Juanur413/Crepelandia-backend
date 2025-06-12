package com.crepelandia.controller;

import com.crepelandia.model.Categoria;
import com.crepelandia.model.Producto;
import com.crepelandia.model.ProductoDTO;
import com.crepelandia.repository.CategoriaRepository;
import com.crepelandia.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = {"https://neon-entremet-b80536.netlify.app", "http://localhost:3000"})
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;
    
    @Autowired
    private CategoriaRepository categoriaRepository;

    // Obtener todos los productos
    @GetMapping
    public ResponseEntity<Map<String, Object>> listarProductos() {
        try {
            List<Producto> productos = productoRepository.findAll();
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", productos);
            response.put("total", productos.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return handleError("Error al obtener productos", e);
        }
    }

    // Obtener un producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> obtenerProductoPorId(@PathVariable Integer id) {
        try {
            Optional<Producto> producto = productoRepository.findById(id);
            Map<String, Object> response = new HashMap<>();
            
            if (producto.isPresent()) {
                response.put("success", true);
                response.put("data", producto.get());
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Producto no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            return handleError("Error al obtener producto", e);
        }
    }

    // Crear nuevo producto
    @PostMapping
    public ResponseEntity<Map<String, Object>> crearProducto(@Valid @RequestBody ProductoDTO dto) {
        try {
            // Validar que la categoría existe
            Optional<Categoria> categoriaOpt = categoriaRepository.findById(dto.getIdCategoria());
            if (!categoriaOpt.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Categoría no encontrada");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            Categoria categoria = categoriaOpt.get();
            
            // Crear nuevo producto
            Producto producto = new Producto();
            producto.setNombre(dto.getNombre());
            producto.setPrecio(dto.getPrecio());
            producto.setStock(dto.getStock());
            producto.setDescripcion(dto.getDescripcion());
            producto.setCategoria(categoria);

            Producto productoGuardado = productoRepository.save(producto);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Producto creado exitosamente");
            response.put("data", productoGuardado);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
        } catch (Exception e) {
            return handleError("Error al crear producto", e);
        }
    }

    // Actualizar producto existente
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> actualizarProducto(
            @PathVariable Integer id, 
            @Valid @RequestBody ProductoDTO dto) {
        try {
            Optional<Producto> optionalProducto = productoRepository.findById(id);
            Map<String, Object> response = new HashMap<>();
            
            if (!optionalProducto.isPresent()) {
                response.put("success", false);
                response.put("message", "Producto no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            // Validar que la categoría existe
            Optional<Categoria> categoriaOpt = categoriaRepository.findById(dto.getIdCategoria());
            if (!categoriaOpt.isPresent()) {
                response.put("success", false);
                response.put("message", "Categoría no encontrada");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            Producto producto = optionalProducto.get();
            producto.setNombre(dto.getNombre());
            producto.setPrecio(dto.getPrecio());
            producto.setStock(dto.getStock());
            producto.setDescripcion(dto.getDescripcion());
            producto.setCategoria(categoriaOpt.get());
            
            Producto productoActualizado = productoRepository.save(producto);
            
            response.put("success", true);
            response.put("message", "Producto actualizado exitosamente");
            response.put("data", productoActualizado);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            return handleError("Error al actualizar producto", e);
        }
    }

    // Eliminar producto
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminarProducto(@PathVariable Integer id) {
        try {
            Map<String, Object> response = new HashMap<>();
            
            if (!productoRepository.existsById(id)) {
                response.put("success", false);
                response.put("message", "Producto no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            
            productoRepository.deleteById(id);
            
            response.put("success", true);
            response.put("message", "Producto eliminado exitosamente");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            return handleError("Error al eliminar producto", e);
        }
    }

    // Método auxiliar para manejo de errores
    private ResponseEntity<Map<String, Object>> handleError(String message, Exception e) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", message);
        response.put("error", e.getMessage());
        
        System.err.println(message + ": " + e.getMessage());
        e.printStackTrace();
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}