/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.codigo_09;

import java.util.Random;

/**
 *
 * @author jcfv3
 */
public class Codigo_09 {
    
    //Gnera valore RGB aleatorios
    private static int[] generarPixel(){
        Random random = new Random(); //Objeto para generar valores aleatorios
        int[] pixel = new int[3]; //Se declara el arreglo de tendra los valores RGB
        
        for(int f = 0; f < 3; f++){
            pixel[f] = random.nextInt(256); //Se genera un valores alteatorios entre 0-255 para R, G, B
        }
        return pixel; //Se retorna el arreglo con los valores RGB
    }
    private static int[][][] generarMatrizPixeles(){ //Se generar una matriz de pixeles
        Random random = new Random(); //Objeto para generar valroes aleatorios
        int largo = random.nextInt(7, 20); //Se genera el largo de la imagen entre 7 y 20 pixeles
        int ancho = random.nextInt(4, 10); //Se genera el ancho de la imagen entre 4 y 10 pixeles
        int[][][] imagen = new int[ancho][largo][3]; //Se declara la matriz de pixeles que conforman la imagen
        
        for(int f = 0; f < ancho; f++){ //Se recorre el largo y ancho de la imagen
            for(int j = 0; j < largo; j++){
                imagen[f][j] = generarPixel(); //Generamos un pixel con valores RGB aleatorios para cada pixel de la imagen
            }
        }
        return imagen; //Se regresa la imagen
    }
    
    //Se recibe un pixel y sus cuatro vecincos para promediar sus vaolores RGB
    private static int[] difuminarPixel(int[] pixel0, int[] pixel1, int[] pixel2, int[] pixel3, int[] pixel4){
        int[] pixelDifuminado = new int[3]; //Se declara un arreglo de enteros que tendra el promedio de los valores RGB
        
        for(int f = 0; f < 3; f++){ //Se recorre los valores R, G, B de cada pixel
            //Se promedia cada valor RGB  de cada pixel y se guarda en la posicion correcta
            //del arreglo que conforma el pixel difuminado.
            pixelDifuminado[f] = (pixel0[f] + pixel1[f] + pixel2[f] + pixel3[f] + pixel4[f])/5;
        }
        
        return pixelDifuminado; //Se regresa el pixel difuminado
    }
    
    
    //Se imprimen los valores RGB de cada pixel de una imagen
    private static void imprimirImagen(int[][][] imagen){
        for(int f = 0; f < imagen.length; f++){ //Se recorre el largo y ancho de la imagen
            for(int i = 0;  i < imagen[0].length; i++){
                //Se ponen entre parentesis
                System.out.print("(");
                //y se imprimen los valores RGB de cada pixel de la imagen
                for(int j =  0; j < 3; j++){
                    System.out.print(imagen[f][i][j]);
                    if(j < 2){ //En el ultimo valor no se pone coma de separacion
                        System.out.print(", ");
                    }
                }
                //Se cierra el parentesis
                System.out.print(")");
                if(i < imagen[0].length - 1){ //No se pone coma de separacion en el ultimo pixel de la fila
                    System.out.print(", ");
                }
            }
            System.out.println(); //Se imprime un salto de linea entre cada fila
        }
    }
    public static void main(String[] args) {
        int[][][] imagen = generarMatrizPixeles(); //Se genera la matriz de pixeles para la imagen original
        //Se declara la imagen difuminada con las mismas dimensiones de la imagen original
        int[][][] imagenDifuminada = new int[imagen.length][imagen[0].length][3]; 
        
        //Se imprimen los valores RGB de la imagen original
        System.out.println("Valores RGB de la imagen original:");
        imprimirImagen(imagen);
        
        //Se recorre el largo y ancho de la imagen original
        for(int f = 0; f < imagen.length; f++){
            for(int j = 0; j < imagen[0].length; j++){
                int[] pixel0 = imagen[f][j]; //Pixel a difuminar
                int[] pixel1 = {0, 0, 0}; //Pixel vecino de arriba
                int[] pixel2 = {0, 0, 0}; //Pixel vecino de la derecha
                int[] pixel3 = {0, 0, 0}; //Pixel vecino de abajo
                int[] pixel4 = {0, 0, 0}; //Pixel vecino de la izquierda
                
                //Evaluamos que los pixeles vecinos no se salgan de las dimensiones de la imagen
                if(f -1 > 0){ //Se evalua el pixel vecino de arriba
                    pixel1 = imagen[f - 1][j];
                }
                
                if(j + 1 < imagen[0].length){ //Se evalua el pixel vecino de la derecha
                    pixel2 = imagen[f][j + 1];
                }
                
                if(f + 1 < imagen.length){ //Se evalua el pixel vecino de abajo
                    pixel3 = imagen[f + 1][j];
                }
                
                if(j - 1 > 0){ //Se evalua el pixel vecino de la izquierda
                    pixel4 = imagen[f][j - 1];
                }
                
                //Se pasa el pixel con todos sus vecinos para sacar su valor difuminado
                //y colocarlo en la posicion correspondiente del arreglo de salida
                imagenDifuminada[f][j] = difuminarPixel(pixel0, pixel1, pixel2, pixel3, pixel4);
            }
        } //Al final, la matriz de salida tiene todos lo valores RGB de los pixeles difuminados,
        //es decir, se tiene la imagen difuminada.
       
        //Se imprime los valores RGB de la imagen difuminada.
        System.out.println("\nValores RGB de la imagen difuminada");
        imprimirImagen(imagenDifuminada);
       
    }
}
