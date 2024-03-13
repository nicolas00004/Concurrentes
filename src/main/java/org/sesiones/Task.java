package org.sesiones;
import java.util.concurrent.RecursiveAction;
import java.util.List;

/**
 * Esta clase implementa las tareas que van a actualizar  la inforación sobre el precio
 * Si la asignación del valor es menor que 10 incrementa el precio de los productos  asignados
 * En otro caso lo divide la asignación del intervalo en dos y crear dos nuevas tareas y ejecutarlas
 */
public class Task  extends RecursiveAction{

    private static final long serialVerisonUID=1L;

    List<Producto> productos;
    int inicio, fin;
    double margen;

    /**
     * Es el constructor de la clase
     * @param productos Lista de productos
     * @param inicio Representa el inicia de la lista de precios
     * @param fin Representa el fin de la lista de precios
     * @param margen Representa el valor con el que se incrementará el precio
     */
    public Task(List<Producto> productos, int inicio, int fin, double margen) {
        this.productos = productos;
        this.inicio = inicio;
        this.fin = fin;
        this.margen = margen;
    }

    @Override
    protected void compute() {
if(fin-inicio<10){
    updatePrices();
}
else{
    int medio=(fin+inicio)/2;
    System.out.printf("Task: Pending tasks: %s\n",getQueuedTaskCount());
    Task t1=new Task (productos, inicio, medio+1,margen);
    Task t2=new Task(productos,medio+1,fin,margen);
    invokeAll(t1,t2);
}
    }

    /**
     * Metodo que actualiza el precio de los productos asignados a la tarea
     */
    private void updatePrices(){
        for (int i = inicio; i < fin; i++) {
            Producto producto=productos.get(i);
            producto.setPrecio(producto.getPrecio()*(1+margen));
        }
    }

}
