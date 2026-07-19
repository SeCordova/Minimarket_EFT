package com.minimarket.controller;
import com.minimarket.entity.Producto;
import com.minimarket.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
@RestController @RequestMapping("/api/productos")
public class ProductoController {
 private final ProductoService productoService;
 public ProductoController(ProductoService productoService){this.productoService=productoService;}
 private EntityModel<Producto> model(Producto p){return EntityModel.of(p,linkTo(methodOn(ProductoController.class).obtenerProductoPorId(p.getId())).withSelfRel(),linkTo(methodOn(ProductoController.class).listarProductos()).withRel("productos"));}
 @Operation(summary="Lista productos disponibles con enlaces HATEOAS")
 @GetMapping public CollectionModel<EntityModel<Producto>> listarProductos(){List<EntityModel<Producto>> items=productoService.findAll().stream().map(this::model).toList();return CollectionModel.of(items,linkTo(methodOn(ProductoController.class).listarProductos()).withSelfRel());}
 @GetMapping("/{id}") public ResponseEntity<EntityModel<Producto>> obtenerProductoPorId(@PathVariable Long id){Producto p=productoService.findById(id);return p==null?ResponseEntity.notFound().build():ResponseEntity.ok(model(p));}
 @PostMapping @ResponseStatus(HttpStatus.CREATED) public EntityModel<Producto> guardarProducto(@RequestBody Producto p){return model(productoService.save(p));}
 @PutMapping("/{id}") public ResponseEntity<EntityModel<Producto>> actualizarProducto(@PathVariable Long id,@RequestBody Producto p){if(productoService.findById(id)==null)return ResponseEntity.notFound().build();p.setId(id);return ResponseEntity.ok(model(productoService.save(p)));}
 @DeleteMapping("/{id}") public ResponseEntity<Void> eliminarProducto(@PathVariable Long id){if(productoService.findById(id)==null)return ResponseEntity.notFound().build();productoService.deleteById(id);return ResponseEntity.noContent().build();}
}
