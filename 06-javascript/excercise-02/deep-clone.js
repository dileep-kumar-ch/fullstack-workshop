const original = {
  name: "John",
  address: {
    city: "New York",
    zip: "10001"
  },
  hobbies: ["reading", "gaming"]
};

const deepClone = obj => {
  if (obj === null || typeof obj !== "object") {
    return obj;
  }

  // Handle arrays
  if (Array.isArray(obj)) {
    return obj.map(item => deepClone(item));
  }

  // Handle objects
  return Object.keys(obj).reduce((clone, key) => {
    clone[key] = deepClone(obj[key]);
    return clone;
  }, {});
};

const cloned = deepClone(original);
cloned.address.city = "Boston";
cloned.hobbies.push("swimming");

console.log(original.address.city); // "New York"
console.log(original.hobbies);      // ["reading", "gaming"]
