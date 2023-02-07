#include <iostream>


/* Velicina razreda CoolClass je 16 bajtova.
Pokazivac na tablicu virtualnih funkcija iznosi 8 bajtova (64-bitno racunalo).
Pokazivac na tablicu virtualnih funkcija se nalazi na samom pocetku.
Atribut x_ ima velicinu 4 bajta, nalazi se iza pokazivaca na tablicu virtualnih funkcija.
Intuitivno bi razred CoolClass trebao imati 12 bajtova, no dogada se nadopunjavanje.
Zbog nadopunjavanja se nakon atributa x_ umece dodatnih 4 bajta koji sluze iskljucivo
za poravnanje strukture, kako bi svaki clan razreda zauzimao jednak broj bajtova.
*/
class CoolClass{
private:
  int x_;
public:
  virtual void set(int x){x_=x;};
  virtual int get(){return x_;};
};

/* Velicina objekta tipa PlainOldClass je 4 bajta, odnosno jednaka je velicini 
atributa x_. Kako nema virtualnih metoda, nije potreban pokazivac na tablicu 
virtualnih funckija. Klasicne funckije nisu dio strukture razreda, te stoga broj i vrste
tih funkcija ne utjecu na velicinu razreda.*/

class PlainOldClass{
public:
  void set(int x){x_=x;};
  int get(){return x_;};
private:
  int x_;
};

int main(){
    std::cout << sizeof(CoolClass) << std::endl;
    std::cout << sizeof(PlainOldClass) << std::endl;
    std::cout << sizeof(int) << std::endl;
}