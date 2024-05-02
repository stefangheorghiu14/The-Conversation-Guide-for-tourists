/*
Aceasta clasa contine metoda main si este punctul de intrare în aplicatie.
 Aici se instantiaza obiectul Application si se apeleaza metoda startApplication() pentru
 a incepe executia aplicatiei.

Se utilizeaza incapsularea pentru a crea un obiect Application si a
apela metoda startApplication() (se foloseste "public")
Se utilizeaza polimorfismul prin apelul metodei startApplication() din clasa Application.(nu stim implemen-
tarea concreta din clasa
 */

package org.example;
import org.example.application.Application;
import org.example.exception.ExceptionTableEmpty;
import java.sql.SQLException;


public class Main { //declaram clasa main
    public static void main(String[] args) throws ExceptionTableEmpty, SQLException {//metoda main

        Application app=new Application();
        /*
        Desi tipul variabilei app este Application, de fapt instanta este a unei subclase a clasei Application.
        Asta se intampla deoarece compilatorul stie ca app este de tip Application, dar in timpul rularii, se va
        apela metoda specifica a subclasei Application.
         */
        app.startApplication();

    }
}

