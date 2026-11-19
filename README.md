# Project 3 — First Prototype

## How to Run

Run Main in Main.java. press 1 to add a test party to the waitlist. Press 4 to display all parties in the waitlist and see your changes.

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

## Deliverable 4

### Reflection

#### How did your design absorb the change?



#### Did the change cross the model/view line?



#### What changed from your D3 plan?

- Wait time uses a per-party-size dining-time table instead of a single average wait time.
- Seating throws `OperationNotSupportedException` when the waitlist is empty or no table fits. Before the plan was to only “tell the user via a text display,” which is now also done with the exception message.

#### What doesn't work yet?

- Menu options 4 (clear table) and 6 (show tables) are stubs.
- Add party does not collect name/size; it always enqueues `"Demo party"` of size 5 (awkward with default capacity-4 tables).
- JavaFX GUI scaffolding exists but is not fully implemented.
- Save/load is not implemented.
- Max waitlist size and configurable table count are still open.

### Next Steps

#### What is left for the final build?

1. Complete controller class.
2. Implement table clearing.
3. Complete GUI and linking to controller.
4. Complete saving/loading.

#### Which stretch goals are you committing to, and which are you dropping?

Committing: MVP completeness and GUI, as well as saving and loading.

#### What will your 3-minute demo show?



#### Who does what?

- Alex — 
- Mehul — 
- Isaac — GUI, save/load, seating/wait-estimate improvement

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
