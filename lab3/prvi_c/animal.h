#ifndef ANIMAL_H
#define ANIMAL_H

typedef char const *(*PTRFUN)();

struct Animal
{
    PTRFUN *vtable;
    const char *name;
} typedef Animal;

void animalPrintGreeting(struct Animal *animal);

void animalPrintMenu(struct Animal *animal);

#endif