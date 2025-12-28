function createShoppingCart() {
  let items = [];
  let discount = 0; // percentage

  function findItem(id) {
    return items.find(item => item.id === id);
  }

  return {
    addItem(product) {
      const existingItem = findItem(product.id);

      if (existingItem) {
        existingItem.quantity += product.quantity;
      } else {
        items.push({ ...product });
      }
    },

    updateQuantity(id, quantity) {
      const item = findItem(id);
      if (item) {
        item.quantity = quantity;
      }
    },

    removeItem(id) {
      items = items.filter(item => item.id !== id);
    },

    getItems() {
      return items.map(item => ({ ...item }));
    },

    getTotal() {
      const total = items.reduce(
        (sum, item) => sum + item.price * item.quantity,
        0
      );

      const discountedTotal = total - (total * discount) / 100;
      return Number(discountedTotal.toFixed(2));
    },

    getItemCount() {
      return items.reduce((count, item) => count + item.quantity, 0);
    },

    isEmpty() {
      return items.length === 0;
    },

    applyDiscount(code, percentage) {
      if (code && percentage > 0) {
        discount = percentage;
      }
    },

    clear() {
      items = [];
      discount = 0;
    }
  };
}
