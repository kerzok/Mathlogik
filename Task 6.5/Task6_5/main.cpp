#include "ackerman.h"
#include <ctime>

class test {
    static void print(uint64 a, uint64 b) {
        double start = clock();
        llvector in;
        in.push_back (a);
        in.push_back (b);
        std::cout << "A(" << in[0] << ", " << in[1] << ") = ";
        std::cout << Ackerman::result(in) << std::endl;
        //" in " << ((clock() - start) / CLOCKS_PER_SEC) << " sec" << std::endl;
    }
public:
    static void run(int n) {
        /*for (int i = 0; i < 3; i++) {
            for (int j = 0; j < n; j++) {
                print(i, j);
            }
        }*/
        print(5, 1);
        print(3, 1);
    }
};

int main() {
    test::run(7);
}

