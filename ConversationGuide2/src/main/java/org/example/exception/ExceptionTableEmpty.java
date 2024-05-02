/*
Vedem o clasa de exceptii personalizata pentru a gestiona cazurile in care tabelul este gol sau
elementul este deja prezent.

Se incapsuleaza logica de gestionare a exceptiilor(această clasa contine codul care se ocupa de
gestionarea si tratarea exceptiilor specifice cazului cand tabela este goala).
 */

package org.example.exception;

public class ExceptionTableEmpty extends Exception{
    public ExceptionTableEmpty(String errormessage){
        super(errormessage);
    }

}
