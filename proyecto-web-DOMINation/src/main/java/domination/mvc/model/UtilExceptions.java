/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domination.mvc.model;

/**
 *
 * @author giann
 */
public class UtilExceptions {
    
    public static void checkNumeroNegativo(double valor, String mensajeDeError) {
        if (valor < 0) {
            throw new RuntimeException(mensajeDeError);
        }
    }
    
    public static void checkObjetoNulo(Object obj, String mensajeDeError) {
        if (obj == null) {
            throw new RuntimeException(mensajeDeError);
        }
    }
}

