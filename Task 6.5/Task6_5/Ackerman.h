#ifndef _ACKERMAN_H_
#define _ACKERMAN_H_
#include <cstdio>
#include <iostream>
#include <vector>
#include <cmath>
#include <algorithm>
#include <time.h>


typedef long long uint64;

typedef std::vector<uint64> llvector;

static const uint64 MAX = 100000000000;

///****************PRIMITIVES****************\\\

struct Z {
    static const uint64 result(llvector const& args) {
        return 0;
    }
};

struct N {
    static const uint64 result(llvector const& args) {
        return args[0] + 1;
    }
};

template <int N>
struct U {
    static const uint64 result(llvector const& args) {
        return args[N - 1];
    }
};

template<typename F, typename ... G>
struct S {
    static const uint64 result(llvector const& args) {
        return F::result((llvector) {G::result(args)...});
    }
};

template<typename F, typename G>
struct R {
    static const uint64 result(llvector const& args) {
        llvector a(args);
        uint64 y = a[a.size() - 1];
        a.pop_back();
        uint64 k = F::result(a);
        a.push_back(0);
        a.push_back(k);
        for (int i = 0; i < y; i++) {
            k = G::result(a);
            a[a.size() - 2]++;
            a[a.size() - 1] = k;
        }
        return k;
    }
};

template<typename F>
struct M {
    static const uint64 result(llvector const& args) {
        llvector a(args);
        a.push_back(0);
        for (uint64 i = 0; i < MAX ; i++) {
            a[a.size() - 1] = i;
            if (F::result(a) == 0)
                return i;
        }
        std::cout << "error \n";
        exit(0);
        return 0;
    }
};

///****************FUNCTIONS****************\\\


struct sum {
    static const uint64 result(llvector const& args) {
        if (args.size() == 2)
            return args[0] + args[1];
        std::cout << "sum error, args > 2\n";
        exit(0);
        return 0;
    }
};

struct mul {
    static const uint64 result(llvector const& args) {
        if (args.size() == 2)
            return args[0] * args[1];
        std::cout << "mul error, args > 2\n";
        exit(0);
        return 0;
    }
};

struct sub {
    static const uint64 result(llvector const& args) {
        if (args.size() == 2)
            return std::max((uint64)0, args[0] - args[1]);
        std::cout << "sub error, args > 2\n";
        exit(0);
        return 0;
    }
};

struct divide {
    static const uint64 result(llvector const& args) {
        if (args.size() == 2 && args[1] != 0)
            return (args[0] / args[1]);
        std::cout << "div error, args > 2 or args[1] == 0\n";
        exit(0);
        return 0;
    }
};

struct dec {
    static const uint64 result(llvector const& args) {
        return std::max((uint64)0, args[0] - 1);
    }
};

struct isZero {
    static const uint64 result(llvector const& args) {
        return (args[0] == 0);
    }
};

struct moreZero {
    static const uint64 result(llvector const& args) {
        return (args[0] > 0);
    }
};

struct isOne {
    static const uint64 result(llvector const& args) {
        return (args[0] == 1);
    }
};

struct isEqual {
    static const uint64 result(llvector const& args) {
        return (args[0] == args[1]);
    }
};

struct moreOne {
    static const uint64 result(llvector const& args) {
        return (args[0] > 1);
    }
};

struct mod {
    static const uint64 result(llvector const& args) {
        return args[0] % args[1];
    }
};

struct power {
    static const uint64 result(llvector const& args) {
        return (uint64)pow((double)args[0], (double)args[1]);
    }
};

///****************NUMBERS****************\\\

typedef S<N, Z> One;

typedef S<N, S<N, Z> > Two;

typedef S<N, Two> Three;

typedef S<N, Three> Four;

typedef S<N, S<N, Three> > Five;

///****************FUNCTIONS****************\\\

typedef S<sum, U<1>, S<isZero, U<1> > > unZero;

typedef S<R<Z, S<sum, S<sub, S<isZero, S<mod, U<1>, S<unZero, U<2> > > >, S<isZero, U<2> > >, U<3> > >, U<1>, U<1> > divCount;

typedef S<isOne, divCount> isPrime;

typedef S<sum, M<S<isZero, S<isPrime, S<sum, sum, One> > > >, S<sum, U<1>, One> > nextPrime;

typedef S<R<S<N, S<N, Z> >, S<nextPrime, U<3> > >, U<1>, U<1> > nthPrime;

typedef S<dec, M<S<isZero, S<mod, U<1>, S<power, U<2>, U<3> > > > > > plog;

typedef S<R<Z, U<1> >, U<2>, S<moreZero, U<1> > > ifElse;

///*****************STACK*****************\\\

typedef S<mul, S<mul, Four, S<power, Three, U<1> >  >, S<power, Five, U<2> > > stack;

typedef S<plog, U<1>, Two> stackSize;

typedef S<plog, U<1>, S<nthPrime, stackSize> > getLast;

typedef S<ifElse, S<moreOne, stackSize>, S<plog, U<1>, S<nthPrime, S<sub, stackSize, One> > > > getPrevLast;

typedef S<mul, S<mul, U<1>, Two>, S<power, S<nthPrime, S<sum, stackSize, One> >, U<2> > > push;

typedef S<sum, S<ifElse, S<moreZero, stackSize>, S<divide, S<divide, U<1>, S<power, S<nthPrime, stackSize>, getLast>  >, Two> >, S<isOne, U<1> > > pop;

///****************ACKERMAN*****************\\\

typedef S<push, S<pop, pop>, S<sum, getLast, One> > stepM0;

typedef S<push, S<push, S<pop, pop>, S<sub, getPrevLast, One> >, One> stepN0;

typedef S<push, S<push, S<push, S<pop, pop>, S<sub, getPrevLast, One> >, getPrevLast >, S<sub, getLast, One> > stepMN;

typedef S<ifElse, S<isZero, getPrevLast>, stepM0> outStepM0;

typedef S<ifElse, S<ifElse, S<moreZero, getPrevLast>, S<isZero, getLast> >, stepN0> outStepN0;

typedef S<ifElse, S<ifElse, S<moreZero, getPrevLast>, S<moreZero, getLast> >, stepMN> outStepMN;

typedef S<sum, S<ifElse, S<moreOne, stackSize>, S<sum, outStepM0, S<sum, outStepN0, outStepMN> > >,
    S<mul, S<sub, One, S<moreOne, stackSize> >, U<1> > > stackStep;

typedef S<sum, S<power, Two, S<M<S<moreOne, S<stackSize,
    S<R<stackStep, S<stackStep, U<3> > >, U<1>, S<sum, S<power, Two, U<2> >, Five> >
    > > >, stack > >, Five> getAckermanSteps;

typedef S<R<stackStep, S<stackStep, U<3> > >, stack, getAckermanSteps> innerAckerman;

typedef S<plog, innerAckerman, Three> Ackerman;

#endif //_ACKERMAN_H_
