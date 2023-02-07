#include <stdio.h>
#include <stdlib.h>

typedef double (*PTRFUN_DOUBLE)();
typedef void (*PTRFUN_VOID)();

struct Unary_Function_Method_Table{
  PTRFUN_VOID tabulate;
};

struct Unary_Function_Virtual_Table{
  PTRFUN_DOUBLE value_at;
  PTRFUN_DOUBLE negative_value_at;
};


//osnovni razred
struct Unary_Function{
  int lower_bound;
  int upper_bound;
  struct Unary_Function_Virtual_Table* virtualTable;
  struct Unary_Function_Method_Table* methodTable;
} typedef Unary_Function;

/*izvedeni razred Square. Razred nasljeđuje razred Unary_Function. Strukture tih 
dvaju razreda su identicne na pocetku kako bi se uspijesno moglo izvesti 
pretvaranje (cast) iz jednog razreda u drugi*/
struct Square{
  int lower_bound;
  int upper_bound;
  struct Unary_Function_Virtual_Table* virtualTable;
  struct Unary_Function_Method_Table* methodTable;
}typedef Square;

/* izvedeni razred Linear. Razred nasljeđuje razred Unary_Function. Prvi dio strukture
je identican strukturi razreda Unary_Function zbog pretvorbe. Dodatno, razred Linear
ima dodatne atribute a i b.
*/
struct Linear{
  int lower_bound;
  int upper_bound;
  struct Unary_Function_Virtual_Table* virtualTable;
  struct Unary_Function_Method_Table* methodTable;
  double a;
  double b;
}typedef Linear;

/* funkcije za pozivanje prikladne virtualne metode razreda*/
double value_at(Unary_Function* unary, double x){
  return unary -> virtualTable -> value_at(unary, x);
}

double negative_value_at(Unary_Function* unary, double x){
  return unary -> virtualTable -> negative_value_at(unary, x);
}

/*funkcija koja ce se pozivati prilikom poziva funkcije value_at za objekt tipa Square*/
double square_value_at(Unary_Function* unary, double x){
  Square* this = (Square*)unary;
  return x * x;
}
/*funkcija koja ce se pozivati prilikom poziva funkcije value_at za objekt tipa Linear*/
double linear_value_at(Unary_Function* unary, double x){
  Linear* this = (Linear*)unary;
  return this -> a * x + this -> b;
}

/*funkcija koja ce se pozivati prilikom poziva funkcije negative_value_at za objekt tipa Square*/
double square_negative_value_at(Unary_Function* this, double x){
  return -square_value_at(this, x);
}
/*funkcija koja ce se pozivati prilikom poziva funkcije negative_value_at za objekt tipa Linear*/
double linear_negative_value_at(Unary_Function* this, double x){
  return -linear_value_at(this, x);
}

/*funkcija koja se izvodi kao metoda objekta tipa Unary_Function, Kako navedena metoda
nije virtualna, prilikom stvaranja objekta se uvijek dodijeljuje u 
tablicu sa pokazivacima na metode. Neovisno o tome je li razred tipa Unary_Function,
Square ili Linear, dodijeljuje se ova funkcija*/
void tabulate(Unary_Function* this) {
  for(int x = this -> lower_bound; x <= this -> upper_bound; x++) {
    printf("f(%d)=%lf\n", x, value_at(this,x));
  }
};

/*Funkcija za inicijalizaciju objekta tipa Unary_Function*/
void constructUnaryFunction(struct Unary_Function *unary, int lb, int ub){
  unary -> virtualTable = (struct Unary_Function_Virtual_Table *)malloc(sizeof(struct Unary_Function_Virtual_Table));
  unary -> lower_bound = lb;
  unary -> upper_bound = ub;
  unary -> methodTable = (struct Unary_Function_Method_Table *)malloc(sizeof(struct Unary_Function_Method_Table));
  unary -> methodTable -> tabulate = tabulate;
}

/*Funkcija za zauzimanje memorije i pozivajne funkcije za konstrukciju objekta tipa Unary_Function.
Funkcija vraca inicijalizirani objekt*/
struct Unary_Function* createUnaryFunction(int lb, int ub){
  struct Unary_Function* result = (struct Unary_Function*)malloc(sizeof(struct Unary_Function));
  constructUnaryFunction(result, lb,ub);
  return result;
}

/*Funkcija za inicijalizaciju objekta tipa Square*/
void constructSquare(int lb, int ub, struct Square* square){
  constructUnaryFunction((Unary_Function*)square,lb,ub);
  square -> virtualTable -> value_at = square_value_at;
  square -> virtualTable -> negative_value_at = square_negative_value_at;
}

/*Funkcija za zauzimanje memorije i pozivajne funkcije za konstrukciju objekta tipa Square.
Funkcija vraca inicijalizirani objekt*/
Unary_Function* createSquare(int lb, int ub){
  struct Square* square = (struct Square*)malloc(sizeof(struct Square));
  constructSquare(lb, ub, square);
  return (Unary_Function*)square;
}

/*Funkcija za inicijalizaciju objekta tipa Linear*/
void constructLinear(int lb, int ub, double a_coef, double b_coef, struct Linear* linear){
  constructUnaryFunction((Unary_Function*)linear,lb,ub);
  linear -> a = a_coef;
  linear -> b = b_coef;
  linear -> virtualTable -> value_at = linear_value_at;
  linear -> virtualTable -> negative_value_at = linear_negative_value_at;
}
/*Funkcija za zauzimanje memorije i pozivajne funkcije za konstrukciju objekta tipa Linear.
Funkcija vraca inicijalizirani objekt*/
struct Unary_Function* createLinear(int lb, int ub, double a_coef, double b_coef){
  struct Linear* linear = (struct Linear*)malloc(sizeof(struct Linear));
  constructLinear(lb, ub, a_coef, b_coef, linear);
  return (Unary_Function*)linear;
}

/* Staticka metoda razreda Unary_Function. Metoda nije dio ni jedne tablice pokazivaca
jer po definiciji se metoda mora moci pozivati bez stvaranja objekta tipa Unary_Function.
*/
int same_functions_for_ints(Unary_Function *f1, Unary_Function *f2, double tolerance) {
  if(f1->lower_bound != f2->lower_bound) return 0;
  if(f1->upper_bound != f2->upper_bound) return 0;
  for(int x = f1->lower_bound; x <= f1->upper_bound; x++) {
    double delta = f1 -> virtualTable -> value_at(f1,x) - f2-> virtualTable -> value_at(f2,x);
    if(delta < 0) delta = -delta;
    if(delta > tolerance) return 0;
  }
  return 1;
};



int main() {
  struct Unary_Function *f1 = createSquare(2, 2);
  tabulate(f1);
  struct Unary_Function *f2 = createLinear(-2, 2, 5, -2);
  tabulate(f2);
  printf("f1==f2: %s\n", same_functions_for_ints(f1, f2, 1E-6) ? "DA" : "NE");
  printf("neg_val f2(1) = %lf\n", negative_value_at(f2,1.0));
  free(f1);
  free(f2);
  return 0;
}