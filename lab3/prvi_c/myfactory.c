#include "animal.h"
#include "myfactory.h"
#include <string.h>
#include <dlfcn.h>

typedef Animal *(*PTR_STATIC)(const char *);
typedef Animal *(*PTR_DYNAMIC)(Animal *, const char *);
typedef size_t (*PTR_SIZE)();

Animal *myfactory(char const *libname, char const *ctoarg)
{
    char libfile[50];
    strcpy(libfile, "./");
    strcat(libfile, libname);
    strcat(libfile, ".so");
    void *handle = dlopen(libfile, RTLD_LAZY);
    if (!handle)
    {
        return NULL;
    }
    PTR_STATIC createAnimal = dlsym(handle, "create");
    if (!createAnimal)
    {
        return NULL;
    }
    Animal *animal = createAnimal(ctoarg);
    return animal;
}

Animal *myfactoryDynamic(char const *libname, char const *ctoarg, void *memorySpace)
{
    char libfile[50];
    strcpy(libfile, "./");
    strcat(libfile, libname);
    strcat(libfile, ".so");
    void *handle = dlopen(libfile, RTLD_LAZY);
    if (!handle)
    {
        return NULL;
    }
    PTR_DYNAMIC constructAnimal = dlsym(handle, "construct");
    if (!constructAnimal)
    {
        return NULL;
    }
    Animal *animal = constructAnimal((Animal *)memorySpace, ctoarg);
    return animal;
}

size_t getLibrarySize(const char *libname)
{
    char libfile[50];
    strcpy(libfile, "./");
    strcat(libfile, libname);
    strcat(libfile, ".so");
    void *handle = dlopen(libfile, RTLD_LAZY);
    if (!handle)
    {
        return -1;
    }
    PTR_SIZE sizeOfObject = dlsym(handle, "sizeOf");
    if (!sizeOfObject)
    {
        return -1;
    }
    return sizeOfObject();
}