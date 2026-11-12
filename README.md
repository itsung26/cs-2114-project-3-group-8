# Project 3 — First Prototype

## How to Run

## Reflection

### What changed from the specification?

No change.

### Did the model really stand alone?

The model remains seperate from the view and controller. Only the controller has access to the view and model references.

### What happened to your unknowns?

- How many tables will the restaurant support?
  - This is a constant for now but the user will choose in the next version.
- What is the best way to show table availability?
  - A visual will show the user in the future.
- Whether party seating should be automatic or the host should manually seat parties.
  - Automatic.
- Whether there should be a maximum party size or wait list size.
  - Still unknown.
- If we implement table-size matching, what should happen when the first party in line cannot fit at any available table?
  - Tell the user via a text display.

### What doesn't work yet?

Smart restaurant seating logic. The restaurant model does not yet seat customers. Currently, only adding and removing to the waitlist is supported. Some classes are also missing tests.






## Next Steps

### What is left to finish the MVP?



### Change feature (Deliverable 4)

Estimation of wait time based on currently full tables.

This feature will require accessing the model to calculate when the next table will open up based on an average waiting time. This feature will not need to access the view, but will need to be accessed by the model so the model can display the estimate to the user through the view.

### Stretch goals

All stretch goals remain in place.

### What new tests will you need?

Tests for the queue, model, and Table are still needed.

### Who does what?

Alex - 
Mehul - 
Isaac - GUI and save/loading and smart seating algorithims.

## Attribution

| File                  | Contributors | How         |
| --------------------- | ------------ | ----------- |
| `src/Party.java`      | Isaac        | author      |
| `src/PartyTest.java`  | Isaac        | author      |
| `src/Table.java`      | Isaac        | author      |
| `src/WaitlistQueue.java`     | Alex         | author      |
| `src/WaitlistQueueTest.java` | Alex         | author      |
| `src/Restaurant.java` | Isaac        | author      |
| `src/View.java`       | Isaac        | author      |
| `src/ViewTest.java`   | Isaac        | author      |
| `src/Controller.java` | Mehul        | author      |
| `src/Main.java`       | Isaac        | author      |
| `README.md`           | Isaac, Alex, Mehul  | contributor |

### GenAI Note

GenAI was used to aid in writing documentation for classes, as well as managing the gitignore.
