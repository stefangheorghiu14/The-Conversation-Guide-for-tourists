/*Clasa este responsabila pentru logica aplicatiei, interactioneaza cu utilizatorul si
 apeleaza metodele din clasa ConversationImplementation.

 Se utilizeaza incapsularea pentru a proteja membrii si metodele clasei("final private")
Se abstractizeaza operatiile CRUD (create, read, update, delete) prin metodele:
saveNewElement(), readTranslationForOption(), updateElement(), deleteElement()(continuare mai jos)
Se utilizeaza polimorfismul prin apelarea metodelor din interfata ConversationAPI, fara a sti
implementarea concreta din clasa ConversationImplementation.
*/
package org.example.application;
import org.example.dao.ConversationAPI;
import org.example.dao.ConversationImplementation;
import org.example.dao.HibernateUtil;
import org.example.exception.ExceptionTableEmpty;
import org.example.model.Conversation;
import org.hibernate.SessionFactory;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Scanner;

public class Application {
    final private SessionFactory sessionFactory= HibernateUtil.provideSessionFactory();
    final private ConversationAPI<Conversation> conversation=new ConversationImplementation();
    //abstractizarea este prezenta in utilizarea interfetei ConversationAPI pentru manipularea operatiilor
    // pe obiectele de tip Conversation

    /*
    Astfel, clasa Application utilizeaza operatiile definite in interfata fara a sti detaliile concrete
    de implementare ale metodelor, contribuind astfel la abstractizarea functionalitatii.
     */
    final private Scanner scanner=new Scanner(System.in);
    //citeste datele introduse de noi de la tastatura in timpul rularii aplicatiei
    public void startApplication() throws ExceptionTableEmpty, SQLException {
        Boolean flag=true;
        Scanner scanner=new Scanner(System.in);
        while (flag) {
            int option;
            System.out.println("Alege una dintre variante:");
            System.out.println("1-create");
            System.out.println("2-read");
            System.out.println("3-update");
            System.out.println("4-delete");
            System.out.println("5-close");
            option=scanner.nextInt();//userul introduce un numar de tip int, de la 1 la 5
            scanner.nextLine();//citeste urmatoarea linie de text, pana la urmatorul separator de linie
            switch (option)
            {
                case 1:
                    saveNewElement();
                    break;
                case 2:
                    readTranslationForOption();
                    break;
                case 3:
                    updateElement();
                    break;
                case 4:
                    deleteElement();
                    break;
                case 5:
                    flag=false;//se inchide programul
                    break;
                default:
                    System.out.println("optiune invalida");
            }

        }

    }

    private void deleteElement() throws ExceptionTableEmpty {               // CAZUL 4
        System.out.println("Se incearca stergerea ultimului element");
        conversation.delete();
        /*functia delete este apelata pe obiectul conversation, care este o instanta a clasei
                ConversationImplementation */
    }

    private void updateElement() throws SQLException, ExceptionTableEmpty {     // CAZUL 3

        System.out.println("Introdu index-ul elementului pe care vrei sa-l modifici:");
        Optional<Conversation> c=conversation.get(scanner.nextInt());
        /*
        Aceasta metoda este apelata pe obiectul conversation, care este o instanta a interfetei ConversationAPI.
        Linia de cod in cauza incearca sa obtina(pe baza metodei apelate get din ConversationAPI) un obiect
        conversation din baza de date(cu option_name, language,translation) folosind ID-ul introdus de utilizator.
         */
        scanner.nextLine();

        if(c.isEmpty())

        //Metoda utilizata pentru a verifica daca obiectul Optional(parte a API-ului Java) contine sau nu o valoare
        {
            System.out.println("Index inexistent");
            return;
        }
        Conversation c2= c.get();
        /*
        Aceasta linie de cod atribuie valoarea din interiorul obiectului Optional(cu ajutorul lui c.get())
        variabilei c2. In esenta, obiectul Optional este un ambalaj care poate contine sau nu o valoare.
        Cu get(), il deschidem si extragem valoarea (in acest caz, un obiect de tip Conversation) pentru a
        putea lucra cu el mai departe.
         */

        String updateParam;
        System.out.println("Noul Option (gol neschimbat)");
        updateParam=scanner.nextLine();
        /*
        Acest parametru updateParam va conține textul introdus de utilizator de la tastatură,
        care este folosit ulterior pentru a actualiza câmpurile obiectului conversation(linia cu Optional..nextI)
         */
        if(!updateParam.isEmpty())
        /*
        Se verifica daca sirul updateParam NU este gol folosind metoda isEmpty si "!".Daca este gol, atunci
        codul dintre acolade nu va fi executat.
         */
        {
            c2.setOption_name(updateParam);
            /*
            atribuie valoarea variabilei "updateParam" in proprietatea option_name a obiectului c2, unde
            option_name este unul din campurile declarate in clasa Conversation
             */
        }
        System.out.println("Noul language (gol neschimbat)");
        updateParam=scanner.nextLine();
        if(!updateParam.isEmpty())
        {
            c2.setLanguage(updateParam);
        }System.out.println("Noul translation (gol neschimbat)");
        updateParam=scanner.nextLine();
        if(!updateParam.isEmpty())
        {
            c2.setTranslation(updateParam);
        }

        conversation.update(c2.getId(),c2.getOption_name(),c2.getLanguage(),c2.getTranslation());
        /*
        functia update este apelata pe obiectul conversation, care este o instanta a clasei
        ConversationImplementation
         */
    }

    private Integer setOption(LinkedHashSet<String> opt,String type)
    //Aceasta metoda este responsabila pentru afisarea optiunilor disponibile si selectarea unei optiuni
    //valide de catre utilizator
    {
        int counter=1;
        System.out.println("Alege "+type+" valida");//in loc de type, va scrie la rulare "optiune"
        for(var i:opt)
        {
            System.out.println((counter++)+"-> " + i);
            /*
            Counterul va creste de la 1 la 5, iar cu ajutorul lui i, se vor afisa toate option_name o singura
            data.
             */
        }

        Integer choice=scanner.nextInt();//citim de la tastatura un numar intreg de la 1 la 5
        scanner.nextLine();//citim urmatoarea linie de text introdusa de user in consola
        if(choice>counter||choice<1)//choice trebuie sa apartina, in cazul nostru, intervalului [1,5]
        {
            System.out.println(type+" invalida");
            return -1;
        }
        choice--;
        /*
        Deoarece in Java indexarea incepe de la 0, iar optiunile noastre incep de la 1, se decrementeaza
        choice pentru ca indexul sa fie in concordanta cu alegerea utilizatorului.
         */
        return choice;
    }

    private void readTranslationForOption() {                       // CAZUL 2
         LinkedHashSet<String> optiuni =conversation.getOptionVersion().get();
         /*     getOptionVersion - declarat in ConversationAPI (returneaza optiunile unice)
         Acest obiect "optiuni" LinkedHashSet de tip String contine toate optiunile disponibile.Prin apelul
         getOptionVersion() a obiectului "Conversation" si apoi a metodei get(), se obtine valoarea din
         "Optional" .Utilizam LinkedHashSet in clasa noastra deoarece vrem sa inseram elementele unice cu
         conditia de a pastra ordinea lor de inserare.
          */
         Integer optionChosed = null;//inca nu avem o optiune aleasa de user

        optionChosed=setOption(optiuni,"optiune");
        if(optionChosed==-1)
            return;
        printTranslationForOption(optiuni.toArray()[optionChosed].toString());
        /*
        converteste optiunea selectata de utilizator printr-un set intr-un sir. Apoi, o trimite catre metoda
        printTranslationForOption() pentru a fi afisata
        -optiuni.toArray(): se converteste obiectul de tip LinkedHashset  <String> intr-un array
        - [optionChosed]:Acceseaza elementul de la pozitia specificata din array-ul obtinut.
        Valoarea variabilei optionChosed reprezinta indicele optiunii selectate de utilizator.
        - toString():Convertim obiectul din array la o reprezentare de șir de caractere.
        -printTranslationForOption(...):Aceasta metoda primeste un sir de caractere ca argument. În acest caz,
        argumentul este reprezentarea sub forma de sir a elementului selectat din setul de optiuni.

         */
    }

    private void printTranslationForOption(String o) {
        //Aceasta metoda afiseaza traducerile disponibile pentru o anumita optiune selectata de utilizator
        LinkedHashSet<String> limbi =conversation.getLanguageVersion(o).get();
        /* getLanguageVersion - declarat in ConversationAPI (returneaza limbile disponibile pentru versiunea
        pe care o avem), "o" reprezentand "option_name" */
        Integer optionChosed = null;
        optionChosed=setOption(limbi,"limba");
        //va fi la fel ca la option_name, numai ca acum trebuie aleasa limba
        if(optionChosed==-1)
            return;
        System.out.println(conversation.getByOptionAndLanguage(o,limbi.toArray()[optionChosed].toString()));
        /*
        Se apeleaza metoda getByOptionAndLanguage corespunzatoare obiectului conversation, trimitându-i ca
    argumente numele opțiunii("o" de la option_name) și limba selectată (limbi.toArray()[optionChosed].toString())
    (din nou, se converteste setul de limbi intr-un array de stringuri, apoi rezultatul obtinut se converteste in
    sir de caractere)
         */
    }

    private void saveNewElement() throws ExceptionTableEmpty {              // CAZUL 1
        String option;
        String lang;
        String translation;
        System.out.println("Introdu optiunea:");
        option=scanner.next();
        System.out.println("Introdu limba:");
        lang=scanner.next();
        System.out.println("Introdu traducerea:");
        translation=scanner.next();//option_name, language,translation - citite de la tastatura
        conversation.save(new Conversation(option,lang,translation));
        /*
        conversation.save isi are originea in clasa ConversationImplementation, unde se implementeaza
        interfata ConversationAPI
         */
    }
}
