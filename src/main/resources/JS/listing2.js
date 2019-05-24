

const pTest = "pTest";
$(pTest).text("test successful1");
$(document).ready(function() {
    $(pTest).text("test successful");
});




/*

let tbody = document.getElementById("leftTableBody");

let pb = productBunch(getRandomInt(9) + 2);

for (let i = 0; i < pb.length; i++) {
    tbody.innerText = "Test";
}

function productBunch(numNeeded) {
    this.products = new Array(numNeeded);
    for (let i = 0; i < numNeeded; i++) {
            let pr = callJSON();
            this.products[i] = new Product("Test name " + pr.type, i, pr.value.id + 0.50, pr.value.joke.substring(0, 25));
            const idForHTML = 'p-id' + i;
            $(idForHTML).text("Hello");
    }
    return this.products;
}

function Product(name, id, cost, content) {
    this.name = name;
    this.id = id;
    this.cost = cost;
    this.content = content;
    this.setID = function(id) {
        this.id = id;
    }
}

function callJSON() {

    return JSON.parse("http://api.icndb.com/jokes/random?exclude=explicit");
}


function getRandomInt(max) {
    return Math.floor(Math.random() * Math.floor(max));
}
*/