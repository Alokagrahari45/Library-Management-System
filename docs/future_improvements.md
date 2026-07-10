# Suggestions for Future Improvements

1. **Fine calculation for overdue books** — compare the due date to the
   actual return date and calculate a late fee.
2. **Multiple user roles** — separate Admin and Librarian logins with
   different permissions, stored securely (hashed passwords).
3. **Book categories/genres** — add a category field and let students
   browse/search by genre.
4. **CSV/Excel export** — export the book catalogue or issued-books list
   to CSV for reporting.
5. **Resizable hash table** — automatically double the number of buckets
   and rehash when the load factor gets too high, keeping lookups close
   to O(1) even with thousands of books.
6. **Priority queue for the waiting list** — e.g. give faculty or final-year
   students priority over the strict FIFO order.
9. **Book reservation expiry** — automatically remove a student from the
   waiting queue if they don't collect the book within a set number of days.
7. **Email/SMS notifications** — notify a student automatically when a book
   they're waiting for becomes available.
8. **Undo/redo** for accidental deletes.
10. **Unit tests** using JUnit for the data structure classes
   (`BookLinkedList`, `BookHashTable`, `WaitingQueue`) to formally verify
   correctness.
11. **Dark mode / theming** in the GUI.
12. **Barcode/QR-code scanning** for faster Book ID entry using a webcam.
