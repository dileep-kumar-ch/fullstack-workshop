function createLibrary() {
  let books = [];
  let members = [];
  let borrowRecords = [];

  const BORROW_DAYS = 14;

  function findBook(isbn) {
    return books.find(book => book.isbn === isbn);
  }

  function findMember(id) {
    return members.find(member => member.id === id);
  }

  function isBookBorrowedByMember(memberId, isbn) {
    return borrowRecords.find(
      r => r.memberId === memberId && r.isbn === isbn && !r.returnedAt
    );
  }

  return {
    //  Add a book
    addBook(book) {
      const existing = findBook(book.isbn);
      if (existing) {
        existing.copies += book.copies;
      } else {
        books.push({ ...book });
      }
    },

    //  Add a member
    addMember(member) {
      members.push({ ...member });
    },

    //  Borrow a book
    borrowBook(memberId, isbn) {
      const member = findMember(memberId);
      const book = findBook(isbn);

      if (!member || !book || book.copies === 0) return;

      if (isBookBorrowedByMember(memberId, isbn)) return;

      book.copies--;

      borrowRecords.push({
        memberId,
        isbn,
        title: book.title,
        borrowedAt: new Date(),
        returnedAt: null
      });
    },

    //  Return a book
    returnBook(memberId, isbn) {
      const record = isBookBorrowedByMember(memberId, isbn);
      const book = findBook(isbn);

      if (!record || !book) return;

      record.returnedAt = new Date();
      book.copies++;
    },

    //  Available copies
    getAvailableCopies(isbn) {
      const book = findBook(isbn);
      return book ? book.copies : 0;
    },

    //  Member borrowing history
    getMemberHistory(memberId) {
      return borrowRecords
        .filter(r => r.memberId === memberId)
        .map(r => ({
          isbn: r.isbn,
          title: r.title,
          borrowedAt: r.borrowedAt,
          returnedAt: r.returnedAt
        }));
    },

    //  Overdue books (> 14 days)
    getOverdueBooks() {
      const now = new Date();

      return borrowRecords.filter(record => {
        if (record.returnedAt) return false;

        const diffDays =
          (now - record.borrowedAt) / (1000 * 60 * 60 * 24);

        return diffDays > BORROW_DAYS;
      });
    },

    //  Search books by title or author
    searchBooks(keyword) {
      const search = keyword.toLowerCase();

      return books.filter(
        book =>
          book.title.toLowerCase().includes(search) ||
          book.author.toLowerCase().includes(search)
      );
    }
  };
}
