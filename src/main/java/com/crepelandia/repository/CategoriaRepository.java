package com.crepelandia.repository;

import com.crepelandia.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    // Buscar categoría por nombre exacto
    Optional<Categoria> findByNombre(String nombre);

    // Verificar si existe una categoría con ese nombre
    boolean existsByNombre(String nombre);

    // Buscar categorías por nombre que contenga el texto (case insensitive)
    @Query("SELECT c FROM Categoria c WHERE LOWER(c.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Categoria> findByNombreContainingIgnoreCase(@Param("nombre") String nombre);

    // Obtener categorías que tienen productos
    @Query("SELECT DISTINCT c FROM Categoria c JOIN c.productos p")
    List<Categoria> findCategoriasConProductos();

    // Obtener categorías que NO tienen productos
    @Query("SELECT c FROM Categoria c WHERE c.productos IS EMPTY")
    List<Categoria> findCategoriasSinProductos();

    // Contar productos por categoría
    @Query("SELECT c.nombre, COUNT(p) FROM Categoria c LEFT JOIN c.productos p GROUP BY c.idCategoria, c.nombre")
    List<Object[]> countProductosPorCategoria();

    // Obtener categorías con stock total mayor a un valor
    @Query("SELECT c FROM Categoria c JOIN c.productos p GROUP BY c.idCategoria HAVING SUM(p.stock) > :stockMinimo")
    List<Categoria> findCategoriasConStockMayorA(@Param("stockMinimo") Integer stockMinimo);

    // Obtener categorías ordenadas por cantidad de productos (descendente)
    @Query("SELECT c FROM Categoria c LEFT JOIN c.productos p GROUP BY c.idCategoria ORDER BY COUNT(p) DESC")
    List<Categoria> findCategoriasOrderByCantidadProductosDesc();

    // Buscar categorías por lista de IDs
    List<Categoria> findByIdCategoriaIn(List<Integer> ids);
}