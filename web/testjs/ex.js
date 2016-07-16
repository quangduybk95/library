"use strict"
var i, j, k = 0;
var cars = [{
    firstName: "John1",
    lastName: "Doe"
}, {
    firstName: "John2",
    lastName: "Doe"
}];
var mang = [1, 2, 3, 4, 5];
console.log(mang[0]);
console.log(cars[0].firstName);
var a = function(e) {
    console.log(e);
};

a("rdtgrtr");

function myFunction() {
    for (i = 2; i < 50; i++) {
        k = 0;
        for (j = 2; j <= Math.sqrt(i); j++) {
            if (i % j == 0) {
                k = 1;
                break;
            }
        }
        if (k != 1) {
            console.log(i);
        }
    }
}