const prompts = [
  { label: "Enter your name" },
  { label: "Enter an adjective" },
  { label: "Enter a noun" },
  { label: "Enter a verb" },
  { label: "Enter a place" }
];

// Collect inputs using array method
const answers = prompts.map(({ label }) => prompt(label)?.trim() || "");

const [name, adjective, noun, verb, place] = answers;

const story = `One day ${name} found a ${adjective} ${noun} that could ${verb} in the ${place}.`;

alert(story);
console.log(story);
