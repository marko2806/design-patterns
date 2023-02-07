#include <stdio.h>
#include <stdlib.h>

typedef char const* (*PTRFUN)();

/*tablica koja sadrzi pokazivace na virtualne metode razreda. 
Pokazivaci na metode se nalaze u samom razredu kako velicina razreda ne bi ovisila
o broju virtualnih metoda. Dodavanjem metode povecava se samo velicina tablice, ali
ne i "razreda" */
struct TablicaFunkcija{
    PTRFUN animalPrintGreeting;
    PTRFUN animalPrintMenu;
};

/* "Razred" sadrži atribute i pokazivac na tablicu virtualnih funkcija. 
Za razliku od klasicnih objektno orijetiranih jezika, ovdje nisu zastupljeni
modifikatori viljivosti
*/
struct Animal{
    char *name;
    struct TablicaFunkcija* tablica;
};

/*
metode za razrede Dog i Cat. Pokazivaci na ove funkcije se zapisuju u 
tablicu virtualnih funkcija prilikom stvaranja objekta
*/
char const* dogGreet(void){
  return "vau!";
}
char const* dogMenu(void){
  return "kuhanu govedinu";
}
char const* catGreet(void){
  return "mijau!";
}
char const* catMenu(void){
  return "konzerviranu tunjevinu";
}

/* Instanciranje objekta Dog u za to predvideno mjesto na memoriji.
Dodatno se dodijeljuju vrijednosti atributima i dodijeljuju se prikladne 
funckije u tablicu virtualnih funkcija*/
void constructDog(struct Animal* animal, char* name){
    animal -> name = name;
    animal -> tablica = (struct TablicaFunkcija*)malloc(sizeof(struct TablicaFunkcija));
    animal -> tablica ->animalPrintGreeting = dogGreet;
    animal -> tablica -> animalPrintMenu = dogMenu;
}

/* Instanciranje objekta Cat u za to predvideno mjesto na memoriji.
Dodatno se dodijeljuju vrijednosti atributima i dodijeljuju se prikladne 
funckije u tablicu virtualnih funkcija*/
void constructCat(struct Animal* animal, char* name){
    animal -> name = name;
    animal -> tablica = (struct TablicaFunkcija*)malloc(sizeof(struct TablicaFunkcija));
    animal -> tablica ->animalPrintGreeting = catGreet;
    animal -> tablica -> animalPrintMenu = catMenu;
}

//Zauzimanje mjesta za stvaranje objekta tipa Dog i pozivanje "konstruktora" za objekt
struct Animal* createDog(char* name){
    struct Animal* result = (struct Animal*)malloc(sizeof(struct Animal));
    constructDog(result,name);
    return result;
}
//Zauzimanje mjesta za stvaranje objekta tipa Cat i pozivanje "konstruktora" za objekt
struct Animal* createCat(char* name){
   struct Animal* result = (struct Animal*)malloc(sizeof(struct Animal));
   constructCat(result,name);
   return result;
}
//Ispis pozdrava za objekt tipa Animal. Pozdrav se dohvaca pozivom metode dodijeljene 
// objektu prilikom instanciranja. Ukoliko je objekt tipa Dog poziva se metoda dogGreeting,
// a ukoliko je objekt tipa Cat, poziva se metoda catGreeting
void animalPrintGreeting(struct Animal* animal){
    printf("%s kaze:%s\n", animal -> name, animal -> tablica -> animalPrintGreeting());
}
//Ispis menija za objekt tipa Animal. Meni se dohvaca pozivom metode dodijeljene 
// objektu prilikom instanciranja. Ukoliko je objekt tipa Dog poziva se metoda dogMenu,
// a ukoliko je objekt tipa Cat, poziva se metoda catMenu
void animalPrintMenu(struct Animal* animal){
    printf("%s voli:%s\n", animal -> name, animal -> tablica -> animalPrintMenu());
}


void testAnimals(void){
  struct Animal* p1=createDog("Hamlet");
  struct Animal* p2=createCat("Ofelija");
  struct Animal* p3=createDog("Polonije");

  animalPrintGreeting(p1);
  animalPrintGreeting(p2);
  animalPrintGreeting(p3);

  animalPrintMenu(p1);
  animalPrintMenu(p2);
  animalPrintMenu(p3);

  free(p1); free(p2); free(p3);
}

void createNDogs(struct Animal* animals, int n){
  for(int i = 0; i < n; i++){
    constructDog(animals + i, "Pas");
  }
}

void localAndHeap(){
    struct Animal lokalno;
    constructDog(&lokalno, "Rex");
    animalPrintGreeting(&lokalno);
    struct Animal *gomila = createDog("Fido");
    animalPrintGreeting(gomila);
    free(gomila);
}

int main(void){
    testAnimals();
    struct Animal dogs[10];
    createNDogs(dogs, 10);
    for(int i = 0; i < 10; i++){
      animalPrintGreeting(dogs + i);
    }
    localAndHeap();
    return 0;
}