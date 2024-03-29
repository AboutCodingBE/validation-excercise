# Sensor validation

## Outline

You are part of an IoT company shipping sensors to a vast amount of clients. These sensors, which are of type TS50X,
have a firmware from their original manufacturer, which is pretty trustworthy. You have to configure these sensors so 
that they send valuable information which is of interest to your vast amount of clients.

While testing the sensors with the latest configuration, people noticed a compatibility problem with older firmware. There
are new features which can be configured, but some older firmware doesn't know how to deal with those new features. So
the sensors only do a part of what they should be doing. We can’t have that, our company stands for high quality and
happy customers.

**Here is the challenge**: You need to write some software to validate all the sensors of an incoming shipment. The
application takes a list of sensor id's and shows which sensors are ok and which are not ok. The application also shows
what is not ok if the sensor is not ready to be shipped. So if for a particular sensor the firmware is not ok, the 
application should say that the firmware is not ok. If the sensor had incompatible firmware and the latest configuration
not installed, the application should indicate this until the firmware and the configuration have been installed.

The application needs to verify if these sensors have a firmware version compatible with the latest configuration
options. The firmware is compatible starting from version FW:23-09-2022:03. Any sensors with a firmware version before 
this one need an update. If the firmware is not compatible, you need to schedule a task to install the latest firmware over the
internet. The shipments of new sensors do not have the latest configuration either installed yet. So you need to also schedule a task
to install this configuration. 

You have to realise that these sensors have 4G connections. They can have temporarily bad connections and so tasks can
be scheduled for firmware updates for a while. Before you schedule a new task, check if there isn’t one already. If
there is one and if it is older than 2 hours, you can reschedule.

The manufacturer of the sensors has [an API](Manufacturer_API.md) which you can use to get information about the sensors and any ongoing tasks.
So you need to use his API to get the job done.


## Summary of the challenge:

* The application takes in a list of unique sensor id's. These id's are numbers with a length of 17. You can choose
  how the input will happen: a csv file that is uploaded, for example, but it can be any other way.
* The application is something that keeps on running until you give it a new list. So it keeps on looping to return the
  most up to date status of all sensors when requested.
* Installation tasks might fail. So if a task has been scheduled, you need to check in future loops of the program if the task has
  completed successfully. If a task is still pending after 2 hours or there has been an error, you need to delete it and reschedule. You 
  can use the task id which has been returned upon creating a task to query the status of that task. 
* The application returns the latest update of the status of the list of sensors. So it shows which sensors are ok and
  which are not ok and why they are not ok. How this is returned is up to you: it can be a rest api, a webservice, it
  doesn't matter.
* A sensor is ok if the firmware is compatible with the latest configuration and if the latest configuration has been
  installed.
* There is a time cost to updating the firmware: This can easily take up to an hour. So if the firmware isn't the latest
  firmware, but is compatible with the configuration, you don't have to schedule an update.
* Since these sensors come straight from the manufacturer, they will never have the latest configuration installed. So you 
  always need to schedule a task for that in the first loop of the program. 
* The application checks for every sensor if the firmware version is compatible with the latest configuration. The
  firmware version always looks has the following structure: FW:DD-MM-YYYY#XX. So it is basically the letters FW, a
  colon, then the date of the firmware starting with day, then month, then year. There is '#'in between and then follows
  a number of 2 digits. This is because the firmware can get many updates over a day. The firmware is compatible starting from
  version FW:23-09-2022:03.
