import os
import importlib

def myfactory(name):
    module = importlib.import_module("plugins." + name)
    animal = getattr(module, name.capitalize())
    return animal


def printGreeting(pet):
    print(pet.name() + " kaze: " + pet.greet())

def printMenu(pet):
    print(pet.name() + " voli: " + pet.menu())

def test():
  pets=[]
  # obidi svaku datoteku kazala plugins
  for mymodule in os.listdir('plugins'):
    moduleName, moduleExt = os.path.splitext(mymodule)
    # ako se radi o datoteci s Pythonskim kodom ...
    if moduleExt=='.py':
      # instanciraj ljubimca ...
      ljubimac = myfactory(moduleName)('Ljubimac '+str(len(pets)))
      pets.append(ljubimac)

  # ispisi ljubimce
  for pet in pets:
    printGreeting(pet)
    printMenu(pet)

test()
