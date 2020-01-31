;Group Flash 
;CSC 135 Section
;Assignment 2
;
;Q1 make5
;
(define (make5 A B)
   (cond((< A 100)  -2) ;checks if first input is less then 3 digits
     ((< B 10) -2)  ;checks if 2nd input is less than 2 digits
     ((= (modulo A 1000) 0) -2) ;checks if first input has three 0's at the back
         (else(+(right3 A)(left2 B)))))  ;run functions for each input and combine them
(define (right3 A)
  (*(modulo A 1000)100))  ;get the last 3 digits of input
(define (left2 B)
   (if(< (/ B 10) 10) (floor B) (left2 (/ B 10))))  ;get first 2 digits of input
;Q2 concatL
(define (concatL A B)
   (cond((null? A) '())   ;return if atom is null
      ((null? B) '())     ;return if atom is null
      (else (cons(string-append (car A)(car B))(concatL (cdr A)(cdr B)))
      )))       ;combine the car of each list and recurse with cdr as input
;Q3 buildList
(define (buildList N E)
  (if(= N 0) '()      ;return list when counter is 0
   (cons E (buildList (- N 1) E))))     ;add the list to recursion with counter-1
;Q4 listpicket
(define (listpicket P L)
  (if (null? L) (cons P '())      ;return if list is null and add P
      (cons P (cons (car L) (listpicket P (cdr L))))))    ;add P to car and recurse with cdr
;Q5 listpicketall
(define (listpicketall P L)
  (cond ((null? L) (cons P '()))    ;return if list is null and add P
     ((list? (car L)) (cons P (cons (listpicketall 'A (car L)) (listpicketall P (cdr L))))) ;if car L contains a list, do listpicketall for each atom inside
     (else (cons P (cons (car L) (listpicketall P (cdr L)))))))         ;add P to car and recurse with cdr
;Q6 selectN             
(define (selectN N)
  (lambda (lst) (counter N lst)))   ;use aux function with counter 
(define (counter L N )  ;L as counter, N as list input
  (if(= L 0) N (counter(- L 1)(cdr N)))) ;Recurse each time with cdr of list for each counter decrement, to eliminate first L of inputs
(define Last (selectN 3))