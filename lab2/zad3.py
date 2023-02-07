def mymax(iterable, key=lambda x: x):
    max_x = max_key = None
    for x in iterable:
        if max_x is None and max_key is None:
            max_x = x
            max_key = key(x)
        elif key(x) > max_key:
            max_x = x
            max_key = key(x)
    return max_x


maxint = mymax([1, 3, 5, 7, 4, 6, 9, 2, 0])
maxchar = mymax("Suncana strana ulice")
maxstring = mymax(["Gle", "malu", "vocku", "poslije", "kise",
                   "Puna", "je", "kapi", "pa", "ih", "njise"])
maxstring1 = mymax(["Gle", "malu", "vocku", "poslije", "kise",
                    "Puna", "je", "kapi", "pa", "ih", "njise"], lambda x: len(x))
lastPerson = mymax([('Rick', 'Astley'), ('John', 'Doe'), ('Marko', 'Tutic')])

D = {'burek': 8, 'buhtla': 5}
maxprice = mymax(D, lambda x: D.get(x))
maxlen = mymax(D, lambda x: len(x))
print(maxint)
print(maxchar)
print(maxstring)
print(maxstring1)
print(maxprice)
print(maxlen)
print(lastPerson)
