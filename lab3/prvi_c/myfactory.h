#ifndef MY_FACTORY_H
#define MY_FACTORY_H
#include <stdio.h>
Animal *myfactory(char const *libname, char const *ctoarg);
Animal *myfactoryDynamic(char const *libname, char const *ctoarg, void *memorySpace);
size_t getLibrarySize(char const *libname);
#endif