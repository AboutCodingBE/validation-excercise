[Go back](Index.md)

# Design document Evolution 1

## Changes to the existing codebase
* The changes to the existing codebase are minimal. Having designed the application by dividing it in use cases
representing atomic business operations, no pre-existing use case will be touched. Also, no class in the domain model
would be touched. 
* New entities will be added in order to represent the new operations, and two new domain events for the
sensor will be added in order to report the result of the periodic check (succeeded or failed). 
* Two new use cases are added: one that takes care of the received messages containing the combinations of
 digital input and output, and one that saves the result of the periodic check by publishing the result as domain event.
* New storage has to be added to save the messages.
* The process manager that orchestrates the use cases however will be expanded in order to schedule the two new use cases.
