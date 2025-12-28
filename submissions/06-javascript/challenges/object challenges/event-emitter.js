function createEventEmitter() {
  // 🔒 Private memory (closure)
  const events = {};

  return {
    // Subscribe to an event
    on(eventName, callback) {
      if (!events[eventName]) {
        events[eventName] = [];
      }

      events[eventName].push(callback);

      // return unsubscribe function
      return () => {
        events[eventName] = events[eventName].filter(
          fn => fn !== callback
        );
      };
    },

    // Subscribe only once
    once(eventName, callback) {
      const wrapper = (data) => {
        callback(data);
        this.off(eventName, wrapper);
      };

      this.on(eventName, wrapper);
    },

    // Emit an event
    emit(eventName, data) {
      if (!events[eventName]) return;

      events[eventName].forEach(callback => {
        callback(data);
      });
    },

    // Remove listeners
    off(eventName) {
      if (events[eventName]) {
        delete events[eventName];
      }
    }
  };
}
