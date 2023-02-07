#include <iostream>
#include <list>

struct Point
{
public:
    int x;
    int y;
} typedef Point;

class Shape
{
public:
    Point center_;
    virtual void draw() = 0;
    virtual void move(int x, int y) = 0;
};

class Circle : public Shape
{
public:
    double radius_;
    virtual void draw()
    {
        std::cerr << "in drawCircle\n";
    }
    virtual void move(int x, int y)
    {
        center_.x += x;
        center_.y += y;
    }
};

class Square : public Shape
{
public:
    double side_;
    virtual void draw()
    {
        std::cerr << "in drawSquare\n";
    }
    virtual void move(int x, int y)
    {
        center_.x += x;
        center_.y += y;
    }
};

class Rhomb : public Shape
{
public:
    double first_diagonal_;
    double second_diagonal_;
    virtual void draw()
    {
        std::cerr << "in drawRhomb\n";
    }
    virtual void move(int x, int y)
    {
        center_.x += x;
        center_.y += y;
    }
};

void drawShapes(const std::list<Shape *> &fig)
{
    std::list<Shape *>::const_iterator it;
    for (it = fig.begin(); it != fig.end(); it++)
    {
        (*it)->draw();
    }
}

void moveShapes(const std::list<Shape *> &fig)
{
    std::list<Shape *>::const_iterator it;
    for (it = fig.begin(); it != fig.end(); it++)
    {
        (*it)->move(1, 1);
        std::cerr << "Moved shape" << std::endl;
    }
}

int main()
{
    std::list<Shape *> shapes;
    shapes.push_back(new Circle());
    shapes.push_back(new Square());
    shapes.push_back(new Rhomb());
    shapes.push_back(new Circle());

    drawShapes(shapes);
    moveShapes(shapes);
}
