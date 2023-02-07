#include <iostream>
#include <algorithm>
#include <random>
#include <chrono>

class NumberGenerator
{
public:
    virtual void generateNumbers(int *array, int n) = 0;
};

class SequentialNumberGenerator : public NumberGenerator
{
public:
    int lower_bound;
    int upper_bound;
    int step;
    SequentialNumberGenerator(int lower_bound, int upper_bound, int step)
    {
        this->lower_bound = lower_bound;
        this->upper_bound = upper_bound;
        this->step = step;
    }
    void generateNumbers(int *array, int n)
    {
        int element_count = 0;
        for (int i = lower_bound; i <= upper_bound; i += step)
        {
            if (element_count < n)
            {
                array[element_count++] = i;
            }
            else
            {
                break;
            }
        }
    }
};

class RandomNumberGenerator : public NumberGenerator
{
public:
    int mean;
    double deviation;
    RandomNumberGenerator(int mean, double deviation)
    {
        this->mean = mean;
        this->deviation = deviation;
    }
    void generateNumbers(int *array, int n)
    {
        unsigned int seed = std::chrono::system_clock::now().time_since_epoch().count();
        std::default_random_engine generator(seed);
        std::normal_distribution<double> distribution(mean, deviation);
        for (int i = 0; i < n; i++)
        {
            array[i] = (int)distribution(generator);
        }
    }
};

class FibbonacciNumberGenerator : public NumberGenerator
{
public:
    void generateNumbers(int *array, int n)
    {
        int element = 1;
        for (int i = 0; i < n; i++)
        {
            if (i == 0 || i == 1)
            {
                array[i] = 1;
            }
            else
            {
                array[i] = array[i - 1] + array[i - 2];
            }
        }
    }
};

class PercentileCalculator
{
public:
    virtual int getPercentile(int *array, int n, int p) = 0;
};

class InterpolatedPercentileCalculator : public PercentileCalculator
{
private:
    double percentilePosition(int index, int n)
    {
        return 100 * (index + 0.5) / n;
    }

public:
    virtual int getPercentile(int *array, int n, int p)
    {
        int *arrayCopy = (int *)malloc(n * sizeof(int));
        for (int i = 0; i < n; i++)
        {
            arrayCopy[i] = array[i];
        }
        std::sort(arrayCopy, arrayCopy + n);
        if (p < percentilePosition(0, n))
        {
            return arrayCopy[0];
        }
        else if (p > percentilePosition(n - 1, n))
        {
            return arrayCopy[n - 1];
        }
        else
        {
            for (int i = 0; i < n - 1; i++)
            {
                if (percentilePosition(i, n) < p && p < percentilePosition(i + 1, n))
                {
                    int result = arrayCopy[i] + n * (p - percentilePosition(i, n)) *
                                                    (arrayCopy[i + 1] - arrayCopy[i]) / 100;
                    free(arrayCopy);
                    return result;
                }
            }
        }
        free(arrayCopy);
    }
};

class SequentialPercentileCalculator : public PercentileCalculator
{
public:
    virtual int getPercentile(int *array, int n, int p)
    {
        int *arrayCopy = (int *)malloc(n * sizeof(int));
        double n_p = (p * n) / 100 + 0.5;
        for (int i = 0; i < n; i++)
        {
            arrayCopy[i] = array[i];
        }
        std::sort(arrayCopy, arrayCopy + n);
        double distance = n;
        int index;
        for (int i = 0; i < n; i++)
        {
            if (abs(i - n_p) < distance)
            {
                distance = abs(i - n_p);
                index = i;
            }
        }
        int result = arrayCopy[index];
        free(arrayCopy);
        return result;
    }
};

class DistributionTester
{
public:
    int *array;
    int n;
    NumberGenerator *numberGenerator;
    PercentileCalculator *percentileCalculator;
    DistributionTester(int n, NumberGenerator *numberGenerator, PercentileCalculator *percentileCalculator)
    {
        this->n = n;
        array = (int *)malloc(sizeof(int) * n);
        this->numberGenerator = numberGenerator;
        this->percentileCalculator = percentileCalculator;
        this->numberGenerator->generateNumbers(array, n);
    }
    void printArray()
    {
        for (int i = 0; i < n; i++)
        {
            std::cout << "Array element " << i + 1 << ": " << array[i] << std::endl;
        }
    }
    void printTestPercentiles()
    {
        for (int i = 10; i < 100; i += 10)
        {
            std::cout << "P(" << i << ") = " << this->percentileCalculator->getPercentile(array, n, i) << std::endl;
        }
    }

    ~DistributionTester()
    {
        free(array);
    }
};

int main()
{
    NumberGenerator *sng = new RandomNumberGenerator(50, 10);
    PercentileCalculator *spc = new InterpolatedPercentileCalculator();
    DistributionTester *dt = new DistributionTester(20, sng, spc);
    dt->printArray();
    dt->printTestPercentiles();
    return 0;
}
