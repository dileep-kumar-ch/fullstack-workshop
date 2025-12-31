const getType = value => {
  if (value === null) return "null";

  if (Array.isArray(value)) return "array";

  if (typeof value === "object") return "object";

  return typeof value;
};
