#include <stdlib.h>

typedef char const *(*PTRFUN)();

struct Tiger
{
    PTRFUN *vtable;
    const char *name;
} typedef Tiger;

const char *name(Tiger *this)
{
    return this->name;
}

const char *greet()
{
    return "grr";
}

const char *menu()
{
    return "meso";
}

size_t sizeOf()
{
    return sizeof(Tiger);
}

PTRFUN vtable_impl[] = {name, greet, menu};
void construct(Tiger *tiger, const char *name)
{
    tiger->vtable = vtable_impl;
    tiger->name = name;
}

Tiger *create(const char *name)
{
    Tiger *tiger = (Tiger *)malloc(sizeof(Tiger));
    construct(tiger, name);
    return tiger;
}