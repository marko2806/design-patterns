#include <iostream>
#include <vector>
#include <set>

template <typename Iterator, typename Predicate>
Iterator mymax(Iterator first, Iterator last, Predicate pred)
{
    Iterator max = first;
    while (first != last)
    {
        if (pred(*first, *max))
        {
            max = first;
        }
        first++;
    }
    return max;
}

int gt_int(int x, int y)
{
    if (x > y)
        return 1;
    return 0;
}

int gt_char(char x, char y)
{
    if (x > y)
        return 1;
    return 0;
}

int gt_str(std::string x, std::string y)
{
    if (x > y)
        return 1;
    return 0;
}

int arr_int[] = {1, 3, 5, 7, 4, 6, 9, 2, 0};
std::vector<int> vector_int{1, 3, 5, 7, 4, 6, 9, 2, 0};
char arr_char[] = "Suncana strana ulice";
std::set<char> char_set = {'S', 'u', 'n', 'c', 'a', 'n', 'a', ' ',
                           's', 't', 'r', 'a', 'n', 'a', ' ', 'u', 'l', 'i', 'c', 'e'};
std::string arr_str[] = {
    "Gle", "malu", "vocku", "poslije", "kise",
    "Puna", "je", "kapi", "pa", "ih", "njise"};

int main()
{
    const int *maxint = mymax(&arr_int[0],
                              &arr_int[sizeof(arr_int) / sizeof(*arr_int)], gt_int);
    const int *maxintVector = mymax(&vector_int[0],
                                    &vector_int[vector_int.size()], gt_int);
    const char *maxchar = mymax(&arr_char[0],
                                &arr_char[sizeof(arr_char) / sizeof(*arr_char)], gt_char);
    auto maxcharSet = mymax(char_set.begin(),
                            char_set.end(), gt_char);
    std::string *maxstr = mymax(&arr_str[0],
                                &arr_str[sizeof(arr_str) / sizeof(*arr_str)], gt_str);
    std::cout << *maxint << "\n";
    std::cout << *maxintVector << "\n";
    std::cout << *maxchar << "\n";
    std::cout << *maxcharSet << "\n";
    std::cout << *maxstr << "\n";
}