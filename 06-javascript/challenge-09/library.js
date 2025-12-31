const createLibrary = () => {
  let books = [];
  let members = [];
  let borrowRecords = [];

  const BORROW_DAYS = 14;

  const findBook = isbn => books.find(book => book.isbn === isbn);

  const findMember = id => members.find(member => member.id === id);

  const isBookBorrowedByMember = (memberId, isbn) =>
    borrowRecords.find(
      record =>
        record.memberId === memberId &&
        record.isbn === isbn &&
        record.returnedAt === null
    );

  return {
    // Add a book
    addBook: book => {
      const existing = findBook(book.isbn);
      if (existing) {
        existing.copies += book.copies;
      } else {
        books.push({ ...book });
      }
    },

    // Add a member
    addMember: member => {
      members.push({ ...member });
    },

    // Borrow a book
    borrowBook: (memberId, isbn) => {
      const member = findMember(memberId);
      const book = findBook(isbn);

      if (!member || !book || book.copies === 0) {
        return `Cannot borrow book with ISBN ${isbn}`;
      }

      if (isBookBorrowedByMember(memberId, isbn)) {
        return `Member ${memberId} has already borrowed this book`;
      }

      book.copies--;

      borrowRecords.push({
        memberId,
        isbn,
        title: book.title,
        borrowedAt: new Date(),
        returnedAt: null
      });

      return `Book "${book.title}" borrowed successfully`;
    },

    // Return a book
    returnBook: (memberId, isbn) => {
      const record = isBookBorrowedByMember(memberId, isbn);
      const book = findBook(isbn);

      if (!record || !book) {
        return `No active borrow record found`;
      }

      record.returnedAt = new Date();
      book.copies++;

      return `Book "${book.title}" returned successfully`;
    },

    // Available copies
    getAvailableCopies: isbn => {
      const book = findBook(isbn);
      return book ? book.copies : 0;
    },

    // Member borrowing history
    getMemberHistory: memberId =>
      borrowRecords
        .filter(record => record.memberId === memberId)
        .map(({ isbn, title, borrowedAt, returnedAt }) => ({
          isbn,
          title,
          borrowedAt,
          returnedAt
        })),

    // Overdue books (> 14 days)
    getOverdueBooks: () => {
      const now = new Date();

      return borrowRecords.filter(record => {
        if (record.returnedAt !== null) return false;

        const diffDays =
          (now - record.borrowedAt) / (1000 * 60 * 60 * 24);

        return diffDays > BORROW_DAYS;
      });
    },

    // Search books by title or author
    searchBooks: keyword => {
      const search = keyword.toLowerCase();

      return books.filter(
        book =>
          book.title.toLowerCase().includes(search) ||
          book.author.toLowerCase().includes(search)
      );
    }
  };
};
