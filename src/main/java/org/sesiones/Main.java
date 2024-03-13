package org.sesiones;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class Main {
    /**
     * Se raliza una tarea que actualizaŕa el precio de una lista de productos
     * La tarea inicial será responsable de actualizar todos los elementos de la lista
     *
     * EL tamaño de referencia es de 10 productos
     * SI una tarea tiene que actualizar más de 10 productos, dividirá su lista
     *en dos y creará dos subtareas para actualizar los precios de sus respectivas
     * partes.
     *
     */
    public static void main(String[] args) {

       ProductListGenerator generator=new ProductListGenerator();
        List<Producto> productos=generator.generate(100);

        Task task=new Task(productos,0,productos.size(),0.20);
        ForkJoinPool pool=new ForkJoinPool();
        pool.execute(task);

        // Write information about the pool
        do {
            System.out.printf("Main: Thread Count: %d\n",pool.getActiveThreadCount());
            System.out.printf("Main: Thread Steal: %d\n",pool.getStealCount());
            System.out.printf("Main: Paralelism: %d\n",pool.getParallelism());
            try {
                TimeUnit.MILLISECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (!task.isDone());

        // Shutdown the pool
        pool.shutdown();

        // Check if the task has completed normally
        if (task.isCompletedNormally()){
            System.out.printf("Main: The process has completed normally.\n");
        }

        // Expected result: 12. Write products which price is not 12
        for (int i=0; i<productos.size(); i++){
            Producto product=productos.get(i);
            if (product.getPrecio()!=12) {
                System.out.printf("Product %s: %f\n",product.getNombre(),product.getPrecio());
            }
        }

        // End of the program
        System.out.println("Main: End of the program.\n");

    }
}