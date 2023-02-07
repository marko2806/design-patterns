#include <stdio.h>
#include <string.h>
#include <stdlib.h>

const void *mymax(const void *base, size_t nmemb, size_t size,
                  int (*compar)(const void *, const void *))
{
    const char *maxVal = (const char *)base;
    for (int i = 0; i < nmemb - 1; i++)
    {
        if (compar((void *)(base + size * i), (void *)maxVal))
        {
            maxVal = base + size * i;
        }
    }
    return (void *)maxVal;
}

int arr_int[] = {1, 3, 5, 7, 4, 6, 9, 2, 0};
char arr_char[] = "Suncana strana ulice";
const char *arr_str[] = {
    "Gle", "malu", "vocku", "poslije", "kise",
    "Puna", "je", "kapi", "pa", "ih", "njise"};

int gt_int(const void *x, const void *y)
{
    int *x_int = (int *)x;
    int *y_int = (int *)y;
    if (*x_int > *y_int)
        return 1;
    return 0;
}

int gt_char(const void *x, const void *y)
{
    char *x_char = (char *)x;
    char *y_char = (char *)y;
    if (*x_char > *y_char)
    {
        return 1;
    }
    return 0;
}

int gt_str(const void *x, const void *y)
{
    const char **x_str = (const char **)x;
    const char **y_str = (const char **)y;
    if (strcmp(*x_str, *y_str) > 0)
    {
        return 1;
    }
    return 0;
}

int main(void)
{
    const int *maxInt = mymax(arr_int, 9, sizeof(int), gt_int);
    const char *maxChar = mymax(arr_char, 21, sizeof(char), gt_char);
    const char *const *maxStr = mymax(arr_str, 11, sizeof(const char *), gt_str);
    printf("max int  : %d\n", *maxInt);
    printf("max char : %c\n", *maxChar);
    printf("max str  : %s\n", *maxStr);
    return 0;
}