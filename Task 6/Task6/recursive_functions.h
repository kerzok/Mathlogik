#ifndef RECURSIVE_FUNCTIONS_H
#define RECURSIVE_FUNCTIONS_H
#include <memory>
#include <vector>
#include <cassert>
#include <iostream>

typedef long long int64;

//**********PRIMITIVES**********//

struct Z {
    static const int64 arg_number = 1;

    static const int64 result(std::vector<int64> const& arg) {
        return 0;
    }
};

struct N {
    static const int64 arg_number = 1;

    static const int64 result(std::vector<int64> const& arg) {
        assert(arg.size () == arg_number);
        return arg[0] + 1;
    }
};

template <int N, int K>
struct U {
    static const int64 arg_number = N;

    static const int64 result(std::vector<int64> const& arg) {
        assert(K <= arg_number && arg_number == arg.size ());
        return arg[K - 1];
    }
};

template<typename F, typename G, typename ... other_G>
struct S{
    static const int64 arg_number = G::arg_number;
    static const int64 g_function_number = (sizeof ... (other_G)) + 1;

    static const int64 result(std::vector<int64> const& arg) {
        assert(F::arg_number == g_function_number && arg.size () == arg_number);
        return F::result((std::vector<int64>) {G::result(arg), other_G::result(arg) ...});
    }
};

template<typename F, typename G>
struct R {
    static const int64 arg_number = F::arg_number + 1;

    static const int64 result(std::vector<int64> const& arg) {
        assert(arg_number + 1 == G::arg_number && arg_number == arg.size ());

        std::vector<int64> new_arg(arg.begin (), arg.end() - 1);
        if(arg.back ()) {
            new_arg.push_back (arg.back () - 1);
            new_arg.push_back (R<F, G>::result (new_arg));
            return G::result(new_arg);
        } else {
            return F::result(new_arg);
        }
    }
};

template<typename F>
struct M {
    static const int64 arg_number = F::arg_number - 1;

    static const int64 result(std::vector<int64> const& arg) {
        assert(arg_number == arg.size ());

        std::vector<int64> new_arg = arg;
        new_arg.push_back (0);
        for (int64 y = 0; F::result(new_arg); y++) {
            new_arg[arg_number] = y;
        }
        return new_arg[arg_number];
    }
};

//**********FUNCTIONS**********//

//*****REAL PART*****//

//number one
typedef S<N, Z> one;

//increment
typedef S<N, U<3, 3> > inc;

//additional
typedef R<U<1, 1>, inc> sum;

//multiplication
typedef R<Z, S<sum, U<3, 1>, U<3, 3> > > mul;

//degree
typedef R<one, S<mul, U<3, 1>, U<3, 3> > > deg;


//decrement
typedef S<R<Z, U<3, 2> >, U<1, 1>, U<1, 1> > dec;

//substraction
typedef R<U<1, 1>, S<dec, U<3, 3> > > sub;

//division
typedef S<dec, S<M<S<sub, U<3, 1>, S<mul, U<3, 2>, U<3, 3> > > >, S<N, U<2, 1> >, U<2, 2> > > _div;

//module
typedef S<sub, U<2, 1>, S<mul, U<2, 2>, _div> > _abs;

//*****LOGIC PART*****//

//not
typedef S<R<N, S<Z, U<3, 3> > >, U<1, 1>, U<1, 1> > _not;

//and
typedef S<S<_not, _not>, mul> _and;

//or
typedef S<S<_not, _not>, sum> _or;

//more
typedef S<S<_not, _not>, sub> is_more;

//less
typedef S<S<_not, _not>, S<sub, U<2, 2>, U<2, 1> > > is_less;

//more or equal
typedef S<_not, is_less> not_less;

//less or equal
typedef S<_not, is_more> not_more;

//equal
typedef S<_and, not_more, not_less> equal;

//if
typedef S<sub, S<sum, U<3, 3>, S<mul, S<S<_not, _not>, U<3, 1> >, U<3, 2> > >, S<mul,S<S<_not, _not>, U<3, 1> >, U<3, 3> > > _if;

//if div without mod then 1, else 0
typedef S<equal, _abs, Z> is_div;

//*****REAL PART*****//

// plog
typedef S<dec,M<S<is_div,U<3, 2>,S<deg, U<3, 1>, U<3, 3> > > > > plog;

// is prime
typedef S<
            R<S<is_more,U<1, 1 >, one>,
            S< S<_and, S<_abs, U<3, 1>, U<3, 2> >, U<3, 3> >, U<3, 1>, S<S<N, N>, U<3, 2> >, U<3, 3> > >,
            U<1, 1>,
            S<dec,S<_div, U<1, 1>, S<N, one> > >
        >  is_prime;

// previous prime number
typedef S<R<Z, S<sum, S<is_prime, U<3, 2> >, U<3, 3> > >, U<1, 1>, S<N, U<1, 1> > > prev_prime;

// nth prime
typedef S<M<S<is_more, U<2, 1>, S<prev_prime, U<2, 2> > > >, N> nth_prime;

struct run
{

    template < typename F, typename ... T >
    static void print(T ... x) {
        std::vector< int64 > arguments = { x ... };
        std::cout << F::result(arguments) << std::endl;
    }

    static void function_sum(int64 x, int64 y) {
        std::cout << x << " + " << y << " = ";
        run::print<sum>(x, y);
    }

    static void function_sub(int64 x, int64 y) {
        std::cout << x << " - " << y << " = ";
        run::print<sub>(x, y);
    }

    static void function_mul(int64 x, int64 y) {
        std::cout << x << " * " << y << " = ";
        run::print<mul>(x, y);
    }

    static void function_div(int64 x, int64 y) {
        std::cout << x << " / " << y << " = ";
        run::print<_div>(x, y);
    }

    static void function_mod(int64 x, int64 y) {
        std::cout << x << " % " << y << " = ";
        run::print<_abs>(x, y);
    }

    static void function_pow(int64 x, int64 y) {
        std::cout << x << " ^ " << y << " = ";
        run::print<deg>(x, y);
    }

    static void function_plog(int64 p, int64 x) {
        std::cout << "plog(" << p << ", " << x << ") = ";
        run::print<plog>(p, x);
    }

    static void function_is_less(int64 x, int64 y) {
        std::cout << x << " < " << y << " : ";
        run::print<is_less>(x, y);
    }

    static void function_no_more(int64 x, int64 y) {
        std::cout << x << " <= " << y << " = ";
        run::print<not_more>(x, y);
    }

    static void function_is_more(int64 x, int64 y) {
        std::cout << x << " > " << y << " = ";
        run::print<is_more>(x, y);
    }

    static void function_no_less(int64 x, int64 y) {
        std::cout << x << " >= " << y << " = ";
        run::print<not_less>(x, y);
    }

    static void function_equal(int64 x, int64 y) {
        std::cout << x << " == " << y << " = ";
        run::print<equal>(x, y);
    }

    static void function_if(int64 c, int64 x, int64 y) {
        std::cout << "if (" << c << ", "  << x << ", " << y << ") then ";
        run::print<_if>(c, x, y);
    }

    static void function_is_div(int64 x, int64 y) {
        std::cout << x << " mod " << y << " == 0 : ";
        run::print<is_div>(x, y);
    }

    static void function_is_prime(int64 x) {
        std::cout << x << " is prime = ";
        run::print<is_prime>(x);
    }

    static void function_nth_prime(int64 x) {
        if (x == 1) {
            std::cout << x << "st prime number = ";
        } else if (x == 2) {
            std::cout << x << "nd prime number = ";
        } else if (x == 3) {
            std::cout << x << "rd prime number = ";
        } else {
            std::cout << x << "th prime number = ";
        }
        run::print<nth_prime>(x);
    }

    static void show() {
       std::cout << "check sum: " << std::endl;
       function_sum(12, 5);
       function_sum(3, 3);
       function_sum(0, 6);

       std::cout << std::endl << "check sub: " << std::endl;
       function_sub(10, 17);
       function_sub(35, 17);

       std::cout << std::endl << "check mul: " << std::endl;
       function_mul(5, 7);
       function_mul(0, 64);

       std::cout << std::endl << "check div: " << std::endl;
       function_div(20, 5);
       function_div(20, 6);

       std::cout << std::endl << "check mod: " << std::endl;
       function_mod(20, 5);
       function_mod(20, 6);

       std::cout << std::endl << "check pow: " << std::endl;
       function_pow(5, 4);
       function_pow(2, 5);
       function_pow(1, 10);


       std::cout << std::endl << "check <: " << std::endl;
       function_is_less(17, 17);
       function_is_less(28, 10);
       function_is_less(10, 15);


       std::cout << std::endl << "check <=: " << std::endl;
       function_no_more(17, 17);
       function_no_more(28, 10);
       function_no_more(10, 15);

       std::cout << std::endl << "check >: " << std::endl;
       function_is_more(17, 17);
       function_is_more(28, 10);
       function_is_more(10, 15);

       std::cout << std::endl << "check >=: " << std::endl;
       function_no_less(17, 17);
       function_no_less(28, 10);
       function_no_less(10, 15);

       std::cout << std::endl << "check equal: " << std::endl;
       function_equal(20, 20);
       function_equal(25, 20);

       std::cout << std::endl << "check if: " << std::endl;
       function_if(0, 10, 20);
       function_if(1, 10, 20);

       std::cout << std::endl << "div without mod check: " << std::endl;
       function_is_div(20, 4);
       function_is_div(20, 3);

       std::cout << std::endl << "check plog: " << std::endl;
       function_plog(3, 18);
       function_plog(5, 7);
       function_plog(2, 15);
       function_plog(4, 16);

       std::cout << std::endl << "check prime: " << std::endl;
       function_is_prime(1);
       function_is_prime(2);
       function_is_prime(3);
       function_is_prime(15);
       function_is_prime(17);
       function_is_prime(113);

       std::cout << std::endl << "check next prime: " << std::endl;
       for (int i = 0; i < 11; ++i)
           function_nth_prime(i);

    }
};
#endif // RECURSIVE_FUNCTIONS_H
