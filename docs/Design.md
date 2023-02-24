[Go back](Index.md)

# Application design

## Important disclaimer
This document describes a systematic and evolutive approach that is not appropriate when the application is guaranteed to not
be evolved in the future (for example, when it is a temporary solution that does not meet the target solution or
enterprise architecture). Also, this design is influenced by the context I work in (enterprise software development)
and the programming language I'm the most proficient in (Java). Despite this, the approach explained is
technology-agnostic and can be used in a variety of different environments and technology stacks.

## Business
The required service is about validating incoming shipments of sensors, checking their status and providing this information
to the users.

## Possible volatilities
Here follows an analysis of the possible volatilities following the IDesign method, which answers two questions:
- what can change in the competitor's implementations at the same time.
- what can change in the same implementation during over time.

These insights will help defining parts of the codebase that need to be abstracted away to avoid coupling the 
application core with implementation details. This has not to be confused with speculative design, which would mean
altering the core business (e.g. what if we want to support validating microcontrollers as well, or what if we want to
give add the possibility to design new sensors).

### What can change in the competitor's implementations at the same time?
- how the shipment information is going to be provided. Different data formats (csv, json, yaml or xml, among others),
different channels (webservices, event driven, from mobile applications, from web frontends, etc.).
- competitors may use a different data source to fetch the sensor's status.
- competitors may not provide the information about compatibility of firmware and configuration. They may have this information
provided by an external data source.
- competitors may not need to schedule updates.

### What can change in the same implementation during over time?
- more channels and data formats may be added.
- interactions with the Manufacturer API may change, as well as the format of the data provided.
- a significantly higher amount of users may be using the application.
- a significantly higher amount of sensors per shipment may be delivered.
- more compatibility issues may be known, as well as false positives may be initially assumed and then debunked.
- different persistent storage can be used. It can also be made available externally rather than internally.
- the application may be deployed in a different environment, for example in the cloud as a set of lambda functions
and services on AWS or Microsoft Azure.
- identity access management (IAM) and security are for this exercise out of scope, but if implemented they
need to be abstracted away from the application core. In case such concepts are relevant in the application flow
they should be modelled and added as part of the application core or even the domain model.

### Intervention points
- the format of the shipment information as well as concepts about delivery channels may not be part of the application core.
- interactions with the Manufacturer API and data format of its responses must also be abstracted away in order to keep 
the application core stable.
- checking the sensors status should not be included in the same flow that reads the shipment information. The latter should
read and validate the provided information and make the information internally available through a persistent storage.
- checking the sensors status should be done at regular intervals (therefore scheduled, to avoid flooding of requests 
the Manufacturer API) and each check should be performed autonomously. A possible implementation is: every x seconds, fetch
the sensor with the earliest 'lastCheckTimestamp', if is longer than y minutes ago, refresh timestamp and check status.
- scheduling updates must be not included in the flow that checks the sensor status. However, the latter may trigger the former
asynchronously.
- every scheduling logic should be decoupled from the triggered flow, as it can change in the future. For example,
an agreement can be made with the manufacturer to provide an asynchronous channel to schedule updates, instead of the
current synchronous endpoint.
- cancelling updates must not be included in the same flow of scheduling updates or checking the sensor status.
- the application should be deployed on multiple nodes while sharing the same persistent storage and scheduling checks should
be coordinated to avoid double processing and race conditions.
- it is advised to externalise the information about compatibility issues (for example, in persistent storage or in an external
api) to avoid modifying the code of the application every time the information change. Fetching the information must be
abstracted away from the application core as it is also volatile.

## Detected events
### Shipment
- ShipmentReceived
- ShipmentNotValidated
- ShipmentValidated
- ShipmentValidationFailed

### Sensor
- SensorRegistered
- FirmwareUpdated
- FirmwareUpdateCancelled
- FirmwareUpdateFailed
- ConfigurationUpdated
- ConfigurationUpdateCancelled
- ConfigurationUpdateFailed

In the scenario there is no mention of the possibility of stopping
tracking the shipment or unregistering sensors. It seems sensible to foresee the such functionalities
would be requested in the future, but at the present moment it is not necessary to add domain events for them.

## Chosen architectural style
- A **Use Case Driven approach** seems to be appropriate as it allows to define a number of flows that represent business 
transactions that are unlikely to change. That would add an initial delay but in turn allow growing and modifying the
application fast. It would also allow to test the core logic in isolation and guarantee its correctness. Slicing
the application vertically in this way would also allow moving individual use cases to different environments, 
such as in the cloud, in an evolutive way.
- As a complement, a **rich domain model** would allow to guarantee the internal correctness of the information
manipulated by the use cases, as well as keeping them small both in size and in number.
- The chosen architecture is therefore a **Clean Architecture (Ports and Adapters)**, where the application core is sliced
in packages to keep the use cases logically separated. The domain model will be physically separated by the application 
core, which in turn will be physically separated by the infra (a.k.a. adapters) layer.