#include <cstdio>
#include <cmath>
#define PI 3.14159265358979323846

using namespace std;

int main(void) {
    double xc, yc, xd, yd;
    long long rc, rd;

    scanf("%lf%lf%lld", &xc, &yc, &rc);
    scanf("%lf%lf%lld", &xd, &yd, &rd);

    double d = sqrt((xd - xc) * (xd - xc) + (yd - yc) * (yd - yc));
    double areaC = rc * rc * PI;
    double areaD = rd * rd * PI;
    
    if (d == 0 && rc == rd) { // the same cicle 
        printf("0.000000\n");
    } else {
        if(d < fabs(rc - rd)) { // one inside another print the bigger circle - small circle
            printf("%.6f\n", fabs(areaD - areaC));
        } else if(d > rc + rd){ // do not intersect
            printf("%.6f\n", areaC + areaD);
        } else {
            double a1 = rc * rc  acos((d * d + (rc * rc) - (rd * rd) ) / (2.0 * d * rc));
            double a2 = rd * rd * acos((d * d + (rd * rd) - (rc * rc) ) / (2.0 * d * rd));
            double a3 = 0.5 * sqrt((-1 * d + rc + rd) * (d + rc - rd) * (d - rc + rd) * (d + rc + rd));
            double areaOfIntersection = a1 + a2 - a3;
            double solution = areaC + areaD - 2 * areaOfIntersection;
            printf("%.6f\n", solution);
        }        
    }
    return 0;
}*
