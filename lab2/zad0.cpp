#include <assert.h>
#include <stdlib.h>

#include <iostream>

struct Point
{
    int x;
    int y;
};
struct Shape
{
    enum EType
    {
        circle,
        square,
        rhomb
    };
    EType type_;
};
struct Circle
{
    Shape::EType type_;
    double radius_;
    Point center_;
};
struct Square
{
    Shape::EType type_;
    double side_;
    Point center_;
};
struct Rhomb
{
    Shape::EType type_;
    double first_diagonal_;
    double second_diagonal_;
    Point center_;
};

void drawSquare(struct Square *) { std::cerr << "in drawSquare\n"; }
void drawCircle(struct Circle *) { std::cerr << "in drawCircle\n"; }
void drawRhomb(struct Rhomb *) { std::cerr << "in drawRhomb\n"; }
void drawShapes(Shape **shapes, int n)
{
    for (int i = 0; i < n; ++i)
    {
        struct Shape *s = shapes[i];
        switch (s->type_)
        {
        case Shape::square:
            drawSquare((struct Square *)s);
            break;
        case Shape::circle:
            drawCircle((struct Circle *)s);
            break;
        case Shape::rhomb:
            drawRhomb((struct Rhomb *)s);
        default:
            assert(0);
            exit(0);
        }
    }
}

void moveCircle(struct Circle *s, int x, int y)
{
    s->center_.x += x;
    s->center_.y += y;
}

void moveSquare(struct Square *s, int x, int y)
{
    s->center_.x += x;
    s->center_.y += y;
}

void moveShapes(Shape **shapes, int n, int x, int y)
{
    for (int i = 0; i < n; i++)
    {
        struct Shape *s = shapes[i];
        switch (s->type_)
        {
        case Shape::square:
            moveSquare((struct Square *)s, x, y);
            break;
        case Shape::circle:
            moveCircle((struct Circle *)s, x, y);
            break;
        //nemamo romb te ce ovdje program puknuti
        default:
            assert(0);
            exit(0);
        }
    }
}

int main()
{
    Shape *shapes[4];
    shapes[0] = (Shape *)new Circle;
    shapes[0]->type_ = Shape::circle;
    shapes[1] = (Shape *)new Square;
    shapes[1]->type_ = Shape::square;
    shapes[2] = (Shape *)new Rhomb;
    shapes[2]->type_ = Shape::rhomb;
    shapes[3] = (Shape *)new Circle;
    shapes[3]->type_ = Shape::circle;

    drawShapes(shapes, 4);
}