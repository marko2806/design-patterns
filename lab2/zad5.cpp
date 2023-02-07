#include <iostream>
#include <fstream>
#include <vector>
#include <algorithm>
#include <iomanip>
#include <unistd.h>

class Izvor
{
public:
    virtual int dohvatiSljedeciBroj() = 0;
};
class Observer
{
public:
    virtual void update() = 0;
};

class SlijedBrojeva
{
public:
    std::vector<int> slijed;
    std::vector<Observer *> observers;
    Izvor *izvor;
    SlijedBrojeva(Izvor *izvor)
    {
        this->izvor = izvor;
    }
    void attach(Observer *observer)
    {
        observers.push_back(observer);
    }
    void notify()
    {
        for (int i = 0; i < observers.size(); i++)
        {
            observers[i]->update();
        }
    }
    void kreni()
    {
        int x = 0;
        while (x != -1)
        {
            sleep(1);
            x = izvor->dohvatiSljedeciBroj();
            if (x != -1)
            {
                slijed.push_back(x);
                notify();
            }
        }
    }
    void detach(Observer *observer)
    {
        for (int i = 0; i < observers.size(); i++)
        {
            if (observers[i] == observer)
            {
                observers.erase(observers.begin() + i);
                break;
            }
        }
    }
};

class TipkovnickiIzvor : public Izvor
{
public:
    virtual int dohvatiSljedeciBroj()
    {
        int x;
        std::cout << "Unos broja:";
        std::cin >> x;
        return x;
    }
};

class DatotecniIzvor : public Izvor
{
public:
    std::string path;
    std::fstream myfile;
    DatotecniIzvor(std::string path)
    {
        this->path = path;
        this->myfile = std::fstream(path, std::ios_base::in);
    }
    virtual int dohvatiSljedeciBroj()
    {
        int x;
        if (myfile >> x)
        {
            return x;
        }
        return -1;
    }
};

class DatotecniZapisObserver : public Observer
{
public:
    std::fstream myfile;
    DatotecniZapisObserver(SlijedBrojeva *slijedBrojeva, std::string path)
    {
        slijedBrojeva->attach(this);
        this->slijedBrojeva = slijedBrojeva;
        this->myfile = std::fstream(path, std::fstream::out);
    }
    SlijedBrojeva *slijedBrojeva;
    virtual void update()
    {
        auto t = std::time(nullptr);
        auto tm = *std::localtime(&t);
        this->myfile << "Time:" << std::put_time(&tm, "%d.%m.%Y %H:%M:%S") << " | ";
        for (int i = 0; i < slijedBrojeva->slijed.size(); i++)
        {
            this->myfile << slijedBrojeva->slijed[i] << "  ";
        }
        this->myfile << std::endl;
    }
    ~DatotecniZapisObserver()
    {
        slijedBrojeva->detach(this);
    }
};

class SumaObserver : public Observer
{
public:
    SumaObserver(SlijedBrojeva *slijedBrojeva)
    {
        slijedBrojeva->attach(this);
        this->slijedBrojeva = slijedBrojeva;
    }
    SlijedBrojeva *slijedBrojeva;
    virtual void update()
    {
        int sum = 0;
        for (int i = 0; i < slijedBrojeva->slijed.size(); i++)
        {
            sum += slijedBrojeva->slijed[i];
        }
        std::cout << "Suma:" << sum << std::endl;
    }
    ~SumaObserver()
    {
        slijedBrojeva->detach(this);
    }
};
class ProsjekObserver : public Observer
{
public:
    ProsjekObserver(SlijedBrojeva *slijedBrojeva)
    {
        slijedBrojeva->attach(this);
        this->slijedBrojeva = slijedBrojeva;
    }
    SlijedBrojeva *slijedBrojeva;

    virtual void update()
    {
        int sum = 0;
        for (int i = 0; i < slijedBrojeva->slijed.size(); i++)
        {
            sum += slijedBrojeva->slijed[i];
        }
        std::cout << "Prosjek:" << (double)sum / slijedBrojeva->slijed.size() << std::endl;
    }
    ~ProsjekObserver()
    {
        slijedBrojeva->detach(this);
    }
};
class MedijanObserver : public Observer
{
public:
    MedijanObserver(SlijedBrojeva *slijedBrojeva)
    {
        slijedBrojeva->attach(this);
        this->slijedBrojeva = slijedBrojeva;
    }
    SlijedBrojeva *slijedBrojeva;

    virtual void update()
    {
        std::vector<int> slijedBrojevaCopy;
        for (int i = 0; i < slijedBrojeva->slijed.size(); i++)
        {
            slijedBrojevaCopy.push_back(slijedBrojeva->slijed[i]);
        }
        std::sort(slijedBrojevaCopy.begin(), slijedBrojevaCopy.end());
        int index = (slijedBrojevaCopy.size() - 1) / 2;
        double medijan;
        if (slijedBrojevaCopy.size() % 2 == 0)
        {
            medijan = (slijedBrojevaCopy[index] +
                       slijedBrojevaCopy[index + 1]) /
                      2.0;
        }
        else
        {
            medijan = slijedBrojevaCopy[index];
        }
        std::cout << "Medijan:" << medijan << std::endl;
    }
    ~MedijanObserver()
    {
        slijedBrojeva->detach(this);
    }
};

int main(void)
{
    Izvor *izvor = new DatotecniIzvor("test.txt");
    //Izvor *izvor = new TipkovnickiIzvor();
    SlijedBrojeva *slijed = new SlijedBrojeva(izvor);
    Observer *suma = new SumaObserver(slijed);
    Observer *prosjek = new ProsjekObserver(slijed);
    Observer *medijan = new MedijanObserver(slijed);
    Observer *datoteka = new DatotecniZapisObserver(slijed, "test1");
    slijed->kreni();
    return 0;
}
