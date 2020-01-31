//--------------------------------------------
// Recognizer for simple Java Class grammar
// Written by Jagan Chidella 9/3/2017
//
// to run on Athena (linux) -
// save as: JClassRecognizer.java
// compile: javac JClassRecognizer.java
// execute: java JClassRecognizer
//
// EBNF Grammar is -
// Java Class <jClass > ::= <className> B < varlist> {<method>} E
// Class Name <className> ::= C|D
// Variable List <varlist> ::= <vardef> {, <vardef>};
// Variable Declaration <vardef> ::=<type> <var>
// Type <type> ::= I | S
// Variable Names <var> ::= V|Z
// Class Method <method> ::= P <type> <mname> (<vardef> {,<vardef>}) B
// <stmnt> <returnstmnt> E
// Method Name <mname> ::= M|N
// Statement <stmnt> ::= <ifstmnt>|<assignstmnt>|<whilestmnt>
// If statement <ifstmnt> ::= F <cond> T B {<stmnt>} E [L B { <stmnt> } E]
// Assignment Statement <assignstmnt> ::= <var> = <digit>;
// While Statement <whilestmnt> ::= W <cond> T B <stmnt> {<stmnt>} E
// Condition <cond> ::= (<var> == <digit>)
// Return Statement <returnstmnt> ::= R <var>;
//
// KEY: C and D are names of two Java classes
// I stands for Integer
// S stands for String
// B stands for { brace
// E stands for } brace
// V and Z are names of two variables
// P stands for the word: public
// F stands for If
// T stands for Then
// L stands for Else
// W stands for While
// R stands for Return
// Valid (Legal)strings to ENTER for testing class method containing following
// statements: ENTER the corresponding string.
// Response ‘Legal’ validates string. Response ‘Errors Found’ invalidates string.
// IF statement: CBIV,SZ;PIM(IV)BF(V==9)TBV=8;ERV;EE$
// IF THEN ELSE statement: CBIV,SZ;PSM(SZ)BF(V==9)TBV=8;ELBV=0;ERV;EE$
// WHILE statement: CBIV,SZ;PIM(IV)BW(V==0)TBV=9;Z=2;ERV;EE$
// Assignment: CBIV,SZ;PIM(IV)BV=8;RV;EE$
// Invalid Strings: CBIV,SZ;PIM(IV)BF(V==9)TBV=8;E;RV;E$
// Invalid Strings: CBIV,SZ;PIM(IV)BF(V==9)TBV=8;E;RVEE$
//---------------------------------------------------------------
// Create FIRST and FOLLOW table 70% points by 10/4. Show to me, get it corrected.
// Then Write the Parser 30% points by 10/9. Show to me, get it corrected.
// Recursively  improve both for full points. See page 2,3 for snippet of program.

import java.util.*;
import java.io.IOException;

public class JClassParser
{
   static String inputString;
   static int index = 0;
   static int errorflag = 0;


private char token()
{
   return(inputString.charAt(index)); }

private void advancePtr()
{
   if (index < (inputString.length()-1)) index++; }

private void match(char T)
{
   if (T == token()) advancePtr(); else error(); }

private void error()
{

   System.out.println("error at position: " + index);
   errorflag = 1;
   advancePtr();
}


//----------------------


private void jClass()
{

   className();

   match('B');
   varlist();
   while(token() == 'P')
   {
   method();
   }
  
   match('E');
}
// WRITE YOUR REST OF THE PARSER HERE
// Class Name <className> ::= C|D
private void className()
{
   if (token() == 'C')
   
      match('C');
   else if (token() == 'D')
      match('D');
   else
      error();
            
}

// Variable List <varlist> ::= <vardef> {, <vardef>};
private void varlist()
{
   if ((token() == 'I') || (token() == 'S'))
   {
      vardef();
      while (token() == ',')
      {
         match(',');
         vardef();
      }
   
   if (token() == ';')
   
      match(';');
   
   else
   
      error();            
   
   }
}

// Variable Declaration <vardef> ::=<type> <var>
private void vardef()
{
   type();
   var();                    
}         

// Type <type> ::= I | S
private void type()
{
   if (token() == 'I')
   
      match('I');
   else if (token() == 'S')
      match('S');
   else
      error();
   
}

// Variable Names <var> ::= V|Z
private void var()
{
   if (token() == 'V')
   
      match('V');
   else if (token() == 'Z')
   
      match('Z');
   else
      error();
       
}

// Class Method <method> ::= P <type> <mname> (<vardef> {,<vardef>}) B
//                                             <stmnt> <returnstmnt> E
private void method()
{
   match('P');  
     
   type();
   
   mname();
   
   match('(');
   vardef();
   while (token() == ',')
   {
         match(',');
         vardef();
   }   
   
   match(')');
   match('B');
   
   stmnt();
   returnstmnt();
   match('E'); 
}

// Method Name <mname> ::= M|N
private void mname()
{
   if (token() == 'M')
   
      match('M');
   else if (token() == 'N')
      match('N');
   else
      error();
            
}

// Statement <stmnt> ::= <ifstmnt>|<assignstmnt>|<whilestmnt>
private void stmnt()
{
   if (token() == 'F')
   
      ifstmnt();
      
   else if ((token() == 'V') || (token() == 'Z'))
      assignstmnt();
      
   else if (token() == 'W')
      whilestmnt();
            
}

// If statement <ifstmnt> ::= F <cond> T B {<stmnt>} E [L B { <stmnt> } E]
private void ifstmnt()
{
   match('F');
   cond();
   match('T');
   match('B');
   while (token() == 'F' || token() == 'V' || token() == 'Z' || token() == 'W')
   {
      stmnt();
   }
   match('E');
   
   if (token() == 'L')
   {
      match('L');
      match('B');
      while (token() == 'F' || token() == 'V' || token() == 'Z' || token() == 'W')
      {
         stmnt();   
      }
      match('E');
   }
}

// Assignment Statement <assignstmnt> ::= <var> = <digit>;
private void assignstmnt()
{
   var();
   match('=');
   digit();
   match(';');
}

// While Statement <whilestmnt> ::= W <cond> T B <stmnt> {<stmnt>} E
private void whilestmnt()
{
   match('W');
   cond();
   match('T');
   match('B');
   stmnt();
   while (token() == 'F' || token() == 'V' || token() == 'Z' || token() == 'W')
   {
      stmnt();   
   }
   match('E');
} 

// Condition <cond> ::= (<var> == <digit>)
private void cond()
{
   match('(');
   var();
   match('=');
   match('=');
   digit();
   match(')');
}

// Return Statement <returnstmnt> ::= R <var>;
private void returnstmnt()
{
   match('R');
   var();
   match(';');
}

private void digit()
{
   if (token() == '0')
      match('0');
   else if (token() == '1')
            match('1');
   else if (token() == '2')
            match('2');
   else if (token() == '3')
            match('3');
   else if (token() == '4')
            match('4');
   else if (token() == '5')
            match('5');
   else if (token() == '6')
            match('6');
   else if (token() == '7')
            match('7');
   else if (token() == '8')
            match('8');
   else if (token() == '9')
            match('9');
   else
            error();
}         
   
//----------------------
private void start()
{
   jClass();
   match('$');
   if (errorflag == 0)
      System.out.println("legal." + "\n");
   else
      System.out.println("errors found." + "\n");
}


public static void main (String[] args) throws IOException
{
   JClassParser rec = new JClassParser();
   
   Scanner input = new Scanner(System.in);
   System.out.print("\n" + "enter an expression: ");
   inputString = input.nextLine();

   rec.start();


}

}