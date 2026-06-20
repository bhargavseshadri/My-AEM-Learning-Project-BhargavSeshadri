//1. Variables
var city = "Hyderabad";

//Scope : Function Scoped -
                          function test() {
                              var x = 10;
                          }
                          console.log(x); // Error

//- But : var ignores block scope
if(true){
    var x = 10;
}
console.log(x); // 10

//---------------------------------------------------------
let country = "India";
//Scope : Block Scoped
if(true){
    let x = 10;
}
console.log(x); // Error

//---------------------------------------------------------
const pi = 3.14; //cannot be reassigned

//===================================================================================
//2 : Reference Types - Objects and arrays are reference types.

//a : Objects
let user = {
   name: "Bhargav"
};

//b. Arrays
let colors = ["red", "blue"];

//===================================================================================
//4. Hoisting - JavaScript moves declarations to the top internally before execution. and initialization stays at the same place.

//Example
console.log(name);
var name = "Bhargav";

// Internally this becomes
var name;
console.log(name);
name = "Bhargav";

//===================================================================================
//5. Functions - Most of the functions in js is similar to java, but js have some more types

//a. Function Expressions - Function stored in variable.
const greet = function() {
    console.log("Hello");
};
greet();

//b. Arrow funtions
const add = (a,b) => a+b;
console.log(add(10,20));

//---------------------------Very important function type-----------------------------
//c. imp - some most common function declaration type in aem called "IIFE = Immediately Invoked Function Expression"
// js wont take a functions without a name.

// this is not allowed js gives error, because js expects function declaration to have a name.
function() {
    console.log("Hello");
}

//So when you wrap it in parentheses, JavaScript now treats it as a Function Expression instead of a Function Declaration.
(function() {
    console.log("Hello");
})

// so the above function is created, and to execute it immediately give () at the end
(function() {
    console.log("Hello");
})();

//That last () means call this function right now
//op: Hello
//Simply the use of this IIFE is --> create function and immediately execute it

//Big Example
//IIFE
(function (document, Granite) {

    console.log("Inside function");

})(document, Granite);

//This simple means
function temp(document, Granite) {

    console.log("Inside function");

}
temp(document, Granite);

//===================================================================================
//6. Objects - Object stores related data.

const car = {
  brand: "Tata",
  model: "Nexon",
  start() { console.log(`${this.brand} starting...`); }
};

car.brand;          // dot notation
car["model"];        // bracket notation — needed for dynamic/variable keys
const key = "model";
car[key];             // "Nexon"

// Object methods you'll use constantly
Object.keys(car);     // ["brand", "model", "start"]
Object.values(car);   // ["Tata", "Nexon", function]
Object.entries(car);  // [["brand","Tata"], ["model","Nexon"], ...]

//===================================================================================
//7. Events
//8. Promises
//9. fetch calls
//10. async await
//self calling functions
//call back
























