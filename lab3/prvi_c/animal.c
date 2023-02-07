#include "animal.h"
#include <stdio.h>

void animalPrintGreeting(struct Animal *animal)
{
    printf("%s kaze: %s\n", animal->vtable[0](animal), animal->vtable[1]());
}
void animalPrintMenu(struct Animal *animal)
{
    printf("%s voli: %s\n", animal->vtable[0](animal), animal->vtable[2]());
}
