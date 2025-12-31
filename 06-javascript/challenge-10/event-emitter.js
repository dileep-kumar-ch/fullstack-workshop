const createEventEmitter = () => {
  // 🔒 Private memory (closure)
  const events = {};

  return {
    // Subscribe to an event
    on: (eventName, callback) => {
      if (!events[eventName]) {
        events[eventName] = [];
      }

      events[eventName].push(callback);

      // Return unsubscribe function
      return () => {
        events[eventName] = events[eventName].filter(
          fn => fn !== callback
        );
      };
    },

    // Subscribe only once
    once: (eventName, callback) => {
      const wrapper = data => {
        callback(data);
        emitter.off(eventName, wrapper);
      };

      const emitter = {
        off: (name, fn) => {
          events[name] = events[name]?.filter(cb => cb !== fn) || [];
        }
      };

      events[eventName] = events[eventName] || [];
      events[eventName].push(wrapper);
    },

    // Emit an event
    emit: (eventName, data) => {
      if (!events[eventName]) {
        return `No listeners registered for "${eventName}"`;
      }

      events[eventName].forEach(callback => callback(data));
      return `Event "${eventName}" emitted`;
    },

    // Remove all listeners for an event
    off: eventName => {
      if (events[eventName]) {
        delete events[eventName];
        return `Listeners removed for "${eventName}"`;
      }
      return `No listeners found for "${eventName}"`;
    }
  };
};
