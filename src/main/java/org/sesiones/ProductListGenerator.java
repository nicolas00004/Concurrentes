package org.sesiones;
import java.util.List;
import java.util.ArrayList;
public class ProductListGenerator {


    public List<Producto>generate(int num) {
        List<Producto> ret=new ArrayList<Producto>();

        for (int i = 0; i < num; i++) {
            Producto producto=new Producto();
            producto.setNombre("Producto "+i);
            producto.setPrecio(10);
            ret.add(producto);
        }
        return ret;
    }
}
