let original = {
    name: 'John',
    address: {
        city: 'New York',
        zip: '10001'
    },
    hobbies: ['reading', 'gaming']
};

function deepClone(obj) {
    // Your code here.......
    return structuredClone(obj);
    
}

let cloned = deepClone(original);
cloned.address.city = 'Boston';
cloned.hobbies.push('swimming');

console.log(original.address.city);  // Should still be 'New York'
console.log(original.hobbies);       // Should still be ['reading', 'gaming']