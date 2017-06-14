/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.software2.examples.pharmacysapp;
import com.software2.examples.pharmacysapp.Producto;
import com.software2.examples.pharmacysapp.DetallePedido;
import com.software2.examples.pharmacysapp.Pago;  
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author Stephany
 */
public class PedidoTest {
    
    private static ArrayList<Producto> catalogo;
    private static ArrayList<DetallePedido> carrito;
    
    public static void initCatalogo() {
        
       
        catalogo = new ArrayList<Producto>();
        carrito = new ArrayList<DetallePedido>();
        
        Producto producto1 = new Producto("Dicloflenaco","Medicina",4,1,10.5);
        Producto producto2 = new Producto("Buscapina","Medicina",20,5,5.6);
        Producto producto3 = new Producto("Analgan","Medicina",10,2,2.50);
        Producto producto4 = new Producto("Redoxon","Medicina",30,5,7.80);
        
        catalogo.add(producto1);
        catalogo.add(producto2);
        catalogo.add(producto3);
        catalogo.add(producto4);
    }
    
    
    public Producto obtener_producto_catalogo(ArrayList<Producto> productos, String clave){
        Producto encontrado = null;
        
        for (Producto producto : productos) {
             if(producto.getNombre().equals(clave))
                 encontrado = producto;
            }
        return encontrado;
    
    }
    
    @Before
    public void beforeEachTest() {
        initCatalogo();
    }

    @After
    public void afterEachTest() {
      
    }

    @Test
    public void testIntegracion_producto_catalogo() {
        System.out.println("----Test 1----"); 
        System.out.println("\n(INICIAL) El cat�logo de la farmacia es: " + catalogo.size());
        System.out.println(catalogo.toString());
        Producto producto5 = new Producto("Ciprofloxacina","Medicina",50,15,11.42);
        String resultado = producto5.crear_producto();
        catalogo.add(producto5);
        System.out.println("\n(FIN) El cat�logo de la farmacia es: " + catalogo.size());
        System.out.println(catalogo.toString()); 
        
        assertEquals("Se creo el producto exitosamente!", resultado); //experado,obtenido
        
        System.out.println("----Test 1----\n"); 
    }
    
    @Test  
    public void testIntegracion_catalogo_carrito() {
        System.out.println("----Test 2----");        
        Producto p = obtener_producto_catalogo(catalogo,"Redoxon"); //simulo que lo agrego al carrito
        DetallePedido detalle1 = new DetallePedido(p,5); //Simulo que selecciono 5
        carrito.add(detalle1);
        //visualizar el pedido con los productos seleccionados.
        System.out.println("*******Carrito*****");
        System.out.println(carrito.toString());
        assertEquals(1, carrito.size()); //experado,obtenido
        System.out.println("----Test 2----\n"); 
    }
    
   
    
    @Test  
    public void testIntegracion_PagoSubtotal() {
        System.out.println("----Test 3----");      
        Producto p1 = obtener_producto_catalogo(catalogo,"Analgan");
        Producto p2 = obtener_producto_catalogo(catalogo,"Buscapina");
        DetallePedido detalle1 = new DetallePedido(p1,1);
        DetallePedido detalle2 = new DetallePedido(p2,1);
        carrito.add(detalle1);
        carrito.add(detalle2);
        //visualizar el pedido con los productos seleccionados.
        System.out.println(carrito.toString());
        Pago pa=new Pago();
        //Ingresa el tipo de pago
        pa.crear_pago(true,"");
        Cliente client=new Cliente("Juan", 1, pa);
        System.out.println(client.InfoPer());
        System.out.println( pa.validar_pago(pa));
        double subt=detalle1.subtotal +detalle2.subtotal;
        System.out.println("El subtotal a pagar es: " + subt);
        assertEquals(true, pa.getTipo());//experado,obtenido
        System.out.println("----Test 3----\n");      
    }
    
    @Test  
    public void testIntegracion_ConfirmarPedido() {
        System.out.println("----Test 4----");      
        Producto p1 = obtener_producto_catalogo(catalogo,"Analgan");
        Producto p2 = obtener_producto_catalogo(catalogo,"Diclofenaco");
        DetallePedido detalle1 = new DetallePedido(p1,1);
        DetallePedido detalle2 = new DetallePedido(p2,1);
        carrito.add(detalle1);
        carrito.add(detalle2);
        //visualizar el pedido con los productos seleccionados.
        System.out.println(carrito.toString());
        Pago pa=new Pago();
        //Ingresa el tipo de pago
        pa.crear_pago(true,"");
        Cliente client=new Cliente("Kerly", 2, pa);
        System.out.println(client.InfoPer());
        System.out.println( pa.validar_pago(pa));
        double subt=detalle1.subtotal +detalle2.subtotal;
        System.out.println("El subtotal a pagar es: " + subt);
        Date dt = new Date();  // current time
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(dt);   // assigns calendar to given date 
        int hour = calendar.get(Calendar.HOUR_OF_DAY); // gets hour in 24h format
        ArrayList<DetallePedido> detped = new ArrayList<DetallePedido>();
        detped.add(detalle1);
        detped.add(detalle2);
        Pedido pe = new Pedido(detped, dt, client);
        String hcomp = pe.ValidHora();
        System.out.println(hcomp.toString());
        assertEquals("Horario disponible", hcomp.toString());
        System.out.println("----Test 4----\n");      
    }
    
    /*
    Nombre                      Revisar la funcionalidad de obtener el recargo y total a pagar
    Funcionalidades/Modulos	F1. Obtener el Recargo      F2. Total a Pagar
    Descripcion                 El usuario ingresa al sistema, agrega los productos al carrito, visualiza el pedido. Despues da clic en siguiente, donde ingresa nombre, sector, tipo de pago y da click en confirmar pedido. Luego se obtiene el recargo del pedido y se muestra el total a pagar.
    Datos de Prueba             productos seleccionados = analgan, diclofenaco. Cantidad = 1,1. Pago = Efectivo. Nombre = Kerly. Sector = Centro. Horario = hora actual. 	
    Resultados Esperados	El usuario da click en confirmar pedido y se muestra el recargo del pedido y el total a pagar.	
    Resultados Obtenidos        El usuario dio click en confirmar pedido y se muestro el recargo del pedido y el total a pagar.
    */

    @Test  
    public void testIntegracion_TotalAPagar() {
        System.out.println("----Test 5----");      
        Producto p1 = obtener_producto_catalogo(catalogo,"Analgan");
        Producto p2 = obtener_producto_catalogo(catalogo,"Dicloflenaco");
        DetallePedido detalle1 = new DetallePedido(p1,1);
        DetallePedido detalle2 = new DetallePedido(p2,1);
        carrito.add(detalle1);
        carrito.add(detalle2);
        //visualizar el pedido con los productos seleccionados.
        System.out.println(carrito.toString());
        Pago pa=new Pago();
        //Ingresa el tipo de pago
        pa.crear_pago(true,"");
        Cliente client=new Cliente("Kerly", 2, pa);
        System.out.println(client.InfoPer());
        System.out.println( pa.validar_pago(pa));
        double subt=detalle1.subtotal +detalle2.subtotal;
        System.out.println("El subtotal a pagar es: " + subt);
        Date dt = new Date();  // current time
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(dt);   // assigns calendar to given date 
        int hour = calendar.get(Calendar.HOUR_OF_DAY); // gets hour in 24h format
        ArrayList<DetallePedido> detped = new ArrayList<DetallePedido>();
        detped.add(detalle1);
        detped.add(detalle2);
        Pedido pe = new Pedido(detped, dt, client);
        
        double recargo = pe.GetRecargo(client, subt);        
        System.out.println("El recargo del pedido es: " + recargo);  
        double tot = pe.TotalPedido();    
        System.out.println("El Total del pedido es: " + tot); 
        assertEquals(13, tot);//experado,obtenido
        System.out.println("----Test 5----\n");      
    }
    
}
