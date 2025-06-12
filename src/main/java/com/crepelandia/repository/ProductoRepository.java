package com.crepelandia.repository;

import com.crepelandia.model.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    // Buscar productos por categoría
    List<Producto> findByCategoriaIdCategoria(Integer idCategoria);

    // Buscar productos disponibles (stock > 0)
    @Query("SELECT p FROM Producto p WHERE p.stock > 0")
    List<Producto> findProductosDisponibles();

    // Buscar productos sin stock
    @Query("SELECT p FROM Producto p WHERE p.stock = 0 OR p.stock IS NULL")
    List<Producto> findProductosSinStock();

    // Buscar productos por nombre (case insensitive)
    @Query("SELECT p FROM Producto p WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Producto> findByNombreContainingIgnoreCase(@Param("nombre") String nombre);

    // Buscar productos por rango de precio
    @Query("SELECT p FROM Producto p WHERE p.precio BETWEEN :precioMin AND :precioMax")
    List<Producto> findByPrecioBetween(@Param("precioMin") Integer precioMin, @Param("precioMax") Integer precioMax);

    // Buscar productos con stock mayor a un valor
    List<Producto> findByStockGreaterThan(Integer stock);

    // Buscar productos con stock menor a un valor (productos con poco stock)
    List<Producto> findByStockLessThan(Integer stock);

    // Buscar productos por categoría y disponibilidad
    @Query("SELECT p FROM Producto p WHERE p.categoria.idCategoria = :idCategoria AND p.stock > 0")
    List<Producto> findProductosDisponiblesPorCategoria(@Param("idCategoria") Integer idCategoria);

    // Obtener productos más caros
    @Query("SELECT p FROM Producto p ORDER BY p.precio DESC")
    Page<Producto> findProductosOrderByPrecioDesc(Pageable pageable);

    // Obtener productos más baratos
    @Query("SELECT p FROM Producto p ORDER BY p.precio ASC")
    Page<Producto> findProductosOrderByPrecioAsc(Pageable pageable);

    // Buscar por múltiples criterios
    @Query("SELECT p FROM Producto p WHERE " +
           "(:nombre IS NULL OR LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))) AND " +
           "(:idCategoria IS NULL OR p.categoria.idCategoria = :idCategoria) AND " +
           "(:precioMin IS NULL OR p.precio >= :precioMin) AND " +
           "(:precioMax IS NULL OR p.precio <= :precioMax) AND " +
           "(:soloDisponibles = false OR p.stock > 0)")
    Page<Producto> findProductosConFiltros(
        @Param("nombre") String nombre,
        @Param("idCategoria") Integer idCategoria,
        @Param("precioMin") Integer precioMin,
        @Param("precioMax") Integer precioMax,
        @Param("soloDisponibles") boolean soloDisponibles,
        Pageable pageable
    );

    // Contar productos por categoría
    @Query("SELECT COUNT(p) FROM Producto p WHERE p.categoria.idCategoria = :idCategoria")
    Long countProductosPorCategoria(@Param("idCategoria") Integer idCategoria);

    // Obtener el producto más caro de una categoría
    @Query("SELECT p FROM Producto p WHERE p.categoria.idCategoria = :idCategoria ORDER BY p.precio DESC")
    Optional<Producto> findProductoMasCaroPorCategoria(@Param("idCategoria") Integer idCategoria);

    // Obtener el producto más barato de una categoría
    @Query("SELECT p FROM Producto p WHERE p.categoria.idCategoria = :idCategoria ORDER BY p.precio ASC")
    Optional<Producto> findProductoMasBaratoPorCategoria(@Param("idCategoria") Integer idCategoria);

    // Verificar si existe producto con ese nombre
    boolean existsByNombre(String nombre);

    // Buscar productos por lista de IDs
    List<Producto> findByIdIn(List<Integer> ids);

    // Obtener estadísticas de precio por categoría
    @Query("SELECT c.nombre, MIN(p.precio), MAX(p.precio), AVG(p.precio), COUNT(p) " +
           "FROM Producto p JOIN p.categoria c " +
           "GROUP BY c.idCategoria, c.nombre")
    List<Object[]> getEstadisticasPrecioPorCategoria();

    // Buscar productos con descripción
    @Query("SELECT p FROM Producto p WHERE p.descripcion IS NOT NULL AND p.descripcion != ''")
    List<Producto> findProductosConDescripcion();

    // Buscar productos sin descripción
    @Query("SELECT p FROM Producto p WHERE p.descripcion IS NULL OR p.descripcion = ''")
    List<Producto> findProductosSinDescripcion();
}