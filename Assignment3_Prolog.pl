/*
    ASSIGNMENT 3 (Due 12/13/2017) 
    Logic Programming in Prolog  
    CSC-135
      Group Members: 
	                Amritpal A.
	                David H.
	                Julya M.
	                Nagvit B.
	                Sarbjot D.
                    Son L.
*/

/********************     1     ********************/

factorial(N, F) :- N=1, F is 1, !.
factorial(N, F) :- N1 is N-1, factorial(N1, F1), F is N*F1.

/********************     2     ********************/

listpicket(A, [], [A|[]]).
listpicket(A, [H|T], [A, H|X]) :- listpicket(A, T, X), !.

listpicketall(A, [], [A|[]]).
listpicketall(A, [H|T], [A, X|Y]) :- is_list(H), listpicketall(A, H, X), listpicketall(A, T, Y); X=H, listpicketall(A, T, Y), !.

/********************     3     ********************/

posint([0,1,2,3,4,5,6,7,8,9]).

inList(X, [X|_]).
inList(X, [_|Y]) :- inList(X, Y).

crypto(C, K, L, M) :- posint(N),
                      inList(C, N), inList(K, N), inList(L, N), inList(M, N),
                      X is C + K - L - M,
                      Y is C + K + L + M,
                      Z is C - K + L - M,
                      X=4,
                      Y=14,
                      Z=2.

/********************     4     ********************/
reverse(X, Z) :- rev(X, [], Z).
rev([], Y, Y).
rev([A|X], Y, Z) :- rev(X, [A|Y], Z).

/*
1) reverse([3,5,6,2], W) :- rev([3,5,6,2], [], W).
3) rev([3|5,6,2], [], W) :- rev([5,6,2], [3|[]], W).
3) rev([5|6,2], [3], W) :- rev([6,2], [5|[3]], W).
3) rev([6|2], [5,3], W) :- rev([2], [6|[5,3]], W).
3) rev([2|[], [6,5,3], W) :- rev([], [2|[6,5,3]], W).
2) rev([], [2,6,5,3], [2,6,5,3]).
1) W = [2,6,5,3].
*/

/* tests

factorial(5, F).
listpicket(k, [a, b, [c, d], e], L).
listpicketall(k, [a, b, [c, d], e], L).
crypto(C, K, L, M).
reverse([3, 5, 6, 2], W).

*/







