# Sensor validation

## Outline

You are part of an IoT company shipping sensors to a vast amount of clients. Thes sensors have a firmware from their 
original manufacturer, which is pretty trustworthy. Your company, meaning you actually, you have to configure these 
sensors so that they send valuable information which is of interest to your vast amount of clients.

While testing the sensors with the latest configuration, we noticed a compatibility problem with older firmware. 
There are new features which can be configured but the old firmware doesn't know how to deal with those new features. So 
the sensors only do a part of what they should be doing. We can’t have that, our company stands for high quality and 
happy customers.

**Here is the challenge**: You need to write some software to validate all the sensons of an incoming shipment. The application
takes a list of sensor id's and shows which sensors are ok and which are not ok. The application also shows what is not ok if 
the sensor is not ready to be shipped. The application needs to verify if these sensors have a firmware version compatible 
with the latest configuration options. Luckily, our configurations also have a version. If the firmware is not compatible, 
you need to schedule a task to install the latest firmware over the internet. You have to realise that these sensors have 
4G connections. They can have temporarily bad connections and so tasks can be scheduled for firmware updates for a 
while. Before you schedule a new task, check if there isn’t one already. If there is one and it is older than 2 hours, you 
can reschedule. 

The manufacturer of the sensors has an API which you can use to get information about the sensors and any ongoing tasks. So
you need to use his API to get the job done. 


