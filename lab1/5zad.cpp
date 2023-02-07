#include <iostream>

class B{
public:
  virtual int prva()=0;
  virtual int druga(int)=0;
};

class D: public B{
public:
  virtual int prva(){return 42;}
  virtual int druga(int x){return prva()+x;}
};

typedef int (*PTRFUN)(B*);
typedef int (*PTRFUN2)(B*,int);

void ispisiRezultate(B* pb){
    PTRFUN** p1 = (PTRFUN**)pb;
    PTRFUN2** p2 = (PTRFUN2**)pb;
    PTRFUN* p3 = *p1;
    PTRFUN2* p4 = *(p2) + 1;
    std::cout << (*p3)(pb) << std::endl;
    std::cout << (*p4)(pb,3) << std::endl;

}

int main(void){
  B* pb = new D();
  ispisiRezultate(pb);
  return 0;
}