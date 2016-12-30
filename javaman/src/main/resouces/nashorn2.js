var nashorn = Java.type("org.thushear.tut.nashorn.Nashorn2");

var result = nashorn.fun1("thushear kong");
print("\njs" + result);

nashorn.fun2(123);
nashorn.fun2(49.99);
nashorn.fun2(true);
nashorn.fun2("hi there");
nashorn.fun2(new Date());
nashorn.fun2(new RegExp());
nashorn.fun2({foo:"bar"});

print('passing object hash:');

nashorn.fun3({
    foo:'bar',
    bar:'foo'
});


print('passing custom person object:');

function Person(firstName,lastName){
    this.firstName = firstName;
    this.lastName = lastName;
    this.getFullName = function(){
        return this.firstName + "  " + this.lastName;
    }
}

var person = new Person("thushear","kong");
nashorn.fun3(person);
nashorn.fun4(person);


