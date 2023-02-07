#include "animal.h"
#include "myfactory.h"
#include <stdio.h>
#include <stdlib.h>

int main(int argc, char *argv[])
{
    printf("Gomila\n\n");
    for (int i = 0; i < argc; ++i)
    {
        Animal *p = (Animal *)myfactory(argv[i], "Modrobradi");
        if (!p)
        {
            printf("Creation of plug-in object %s failed.\n\n", argv[i]);
            continue;
        }
        animalPrintGreeting(p);
        animalPrintMenu(p);
        printf("\n");
        free(p);
    }
    printf("Stog\n\n");
    for (int i = 0; i < argc; ++i)
    {
        size_t memorySize = getLibrarySize(argv[i]);
        if (memorySize == -1)
        {
            printf("Creation of plug-in object %s failed.\n\n", argv[i]);
            continue;
        }
        char memoryForObject[memorySize];
        myfactoryDynamic(argv[i], "Svijetlobradi", memoryForObject);
        Animal *animal = (Animal *)&memoryForObject;
        animalPrintGreeting(animal);
        animalPrintMenu(animal);
        printf("\n");
    }
}