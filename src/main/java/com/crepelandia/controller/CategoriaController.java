package com.crepelandia.controller;

import com.crepelandia.model.Categoria;
import com.crepelandia.repository.CategoriaRepository;
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
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    // Obtener todas las categorías
    @GetMapping
    public ResponseEntity<Map<String, Object>> listarCategorias() {
        try {
            List<Categoria> categorias = categoriaRepository.findAll();
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", categorias);
            response.put("total", categorias.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return handleError("Error al obtener categorías", e);
        }
    }

    // Obtener una categoría por ID
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> obtenerCategoriaPorId(@PathVariable Integer id) {
        try {
            Optional<Categoria> categoria = categoriaRepository.findById(id);
            Map<String, Object> response = new HashMap<>();
            
            if (categoria.isPresent()) {
                response.put("success", true);
                response.put("data", categoria.get());
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Categoría no encontrada");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            return handleError("Error al obtener categoría", e);
        }
    }

    // Crear nueva categoría
    @PostMapping
    public ResponseEntity<Map<String, Object>> crearCategoria(@Valid @RequestBody Categoria categoria) {
        try {
            Map<String, Object> response = new HashMap<>();
            
            // Verificar si ya existe una categoría con ese nombre
            if (categoriaRepository.existsByNombre(categoria.getNombre())) {
                response.put("success", false);
                response.put("message", "Ya existe una categoría con ese nombre");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            }

            Categoria categoriaGuardada = categoriaRepository.save(categoria);
            
            response.put("success", true);
            response.put("message", "Categoría creada exitosamente");
            response.put("data", categoriaGuardada);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
        } catch (Exception e) {
            return handleError("Error al crear categoría", e);
        }
    }

    // Actualizar categoría existente
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> actualizarCategoria(
            @PathVariable Integer id, 
            @Valid @RequestBody Categoria categoriaDetalles) {
        try {
            Optional<Categoria> optionalCategoria = categoriaRepository.findById(id);
            Map<String, Object> response = new HashMap<>();
            
            if (!optionalCategoria.isPresent()) {
                response.put("success", false);
                response.put("message", "Categoría no encontrada");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            // Verificar si el nuevo nombre no está en uso por otra categoría
            Optional<Categoria> categoriaExistente = categoriaRepository.findByNombre(categoriaDetalles.getNombre());
            if (categoriaExistente.isPresent() && !categoriaExistente.get().getIdCategoria().equals(id)) {
                response.put("success", false);
                response.put("message", "Ya existe otra categoría con ese nombre");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            }

            Categoria categoria = optionalCategoria.get();
            categoria.setNombre(categoriaDetalles.getNombre());
            
            Categoria categoriaActualizada = categoriaRepository.save(categoria);
            
            response.put("success", true);
            response.put("message", "Categoría actualizada exitosamente");
            response.put("data", categoriaActualizada);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            return handleError("Error al actualizar categoría", e);
        }
    }

    // Eliminar categoría
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminarCategoria(@PathVariable Integer id) {
        try {
            Map<String, Object> response = new HashMap<>();
            
            if (!categoriaRepository.existsById(id)) {
                response.put("success", false);
                response.put("message", "Categoría no encontrada");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            // Verificar si la categoría tiene productos
            Optional<Categoria> categoria = categoriaRepository.findById(id);
            if (categoria.isPresent() && categoria.get().tieneProductos()) {
                response.put("success", false);
                response.put("message", "No se puede eliminar la categoría porque tiene productos asociados");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            }
            
            categoriaRepository.deleteById(id);
            
            response.put("success", true);
            response.put("message", "Categoría eliminada exitosamente");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            return handleError("Error al eliminar categoría", e);
        }
    }

    // Buscar categorías por nombre
    @GetMapping("/buscar")
    public ResponseEntity<Map<String, Object>> buscarCategorias(@RequestParam String nombre) {
        try {
            List<Categoria> categorias = categoriaRepository.findByNombreContainingIgnoreCase(nombre);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", categorias);
            response.put("total", categorias.size());
            response.put("termino_busqueda", nombre);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return handleError("Error al buscar categorías", e);
        }
    }

    // Obtener categorías con productos
    @GetMapping("/con-productos")
    public ResponseEntity<Map<String, Object>> obtenerCategoriasConProductos() {
        try {
            List<Categoria> categorias = categoriaRepository.findCategoriasConProductos();
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", categorias);
            response.put("total", categorias.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return handleError("Error al obtener categorías con productos", e);
        }
    }

    // Obtener categorías sin productos
    @GetMapping("/sin-productos")
    public ResponseEntity<Map<String, Object>> obtenerCategoriasSinProductos() {
        try {
            List<Categoria> categorias = categoriaRepository.findCategoriasSinProductos();
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", categorias);
            response.put("total", categorias.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return handleError("Error al obtener categorías sin productos", e);
        }
    }

    // Obtener estadísticas de productos por categoría
    @GetMapping("/estadisticas")
    public ResponseEntity<Map<String, Object>> obtenerEstadisticas() {
        try {
            List<Object[]> estadisticas = categoriaRepository.countProductosPorCategoria();
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", estadisticas);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return handleError("Error al obtener estadísticas", e);
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