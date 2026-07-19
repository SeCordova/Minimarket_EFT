package com.minimarket;
import com.minimarket.entity.Producto;
import com.minimarket.repository.ProductoRepository;
import com.minimarket.service.impl.ProductoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class ProductoServiceImplTest {
 @Mock ProductoRepository repository; ProductoServiceImpl service;
 @BeforeEach void setUp(){service=new ProductoServiceImpl(); try{var f=ProductoServiceImpl.class.getDeclaredField("productoRepository");f.setAccessible(true);f.set(service,repository);}catch(Exception e){throw new RuntimeException(e);}}
 @Test void debeBuscarProductoPorId(){Producto p=new Producto();p.setId(1L);p.setNombre("Arroz");when(repository.findById(1L)).thenReturn(Optional.of(p));assertEquals("Arroz",service.findById(1L).getNombre());verify(repository).findById(1L);}
 @Test void debeRetornarNullCuandoNoExiste(){when(repository.findById(99L)).thenReturn(Optional.empty());assertNull(service.findById(99L));}
 @Test void debeGuardarProducto(){Producto p=new Producto();p.setNombre("Leche");when(repository.save(p)).thenReturn(p);assertSame(p,service.save(p));verify(repository).save(p);}
}
