#include <stdlib.h>

typedef char const *(*PTRFUN)();

struct Parrot
{
    PTRFUN *vtable;
    const char *name;
} typedef Parrot;

const char *name(Parrot *this)
{
    return this->name;
}

const char *greet()
{
    return "hej";
}

const char *menu()
{
    return "sjemenke";
}

size_t sizeOf()
{
    return sizeof(Parrot);
}

PTRFUN vtable_impl[] = {name, greet, menu};
void construct(Parrot *parrot, const char *name)
{
    parrot->vtable = vtable_impl;
    parrot->name = name;
}

Parrot *create(const char *name)
{
    Parrot *parrot = (Parrot *)malloc(sizeof(Parrot));
    construct(parrot, name);
    return parrot;
}