var fun1 = function(name){
    print("Hi greeting from script" + name);
    return "greeting from js";
};

var fun2  = function(object){
    print("js class definition :" + Object.prototype.toString.call(object));
};